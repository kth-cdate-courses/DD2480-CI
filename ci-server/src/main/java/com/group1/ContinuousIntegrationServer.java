package com.group1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.List;
import java.net.MalformedURLException;
import java.io.File;
import java.net.URL;
import org.apache.commons.io.FileUtils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Skeleton of a ContinuousIntegrationServer which acts as webhook
 * See the Jetty documentation for API documentation of those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        StringWriter sw = new StringWriter();
        request.getReader().transferTo(sw);
        JsonNode JSONrequest = new ObjectMapper().readTree(sw.toString());
        

        try {
            downloadCode(JSONrequest);
        } catch (DownloadFailedException e) {
            e.printStackTrace();
            System.out.println("\n\n\n ERROR REPO DOWNLOAD FAILED!!! NOT ABLE TO DO TASK \n\n\n");
            return;
        }

        String prefix = "";
        if (new File("ci-server").exists())
            prefix = "ci-server/";
        File testResultsOutputFile = new File(prefix + "testResultOutput");
        File repoToTest = new File(prefix + "watched-repository/ci-server");
        compileAndRunTests(repoToTest, testResultsOutputFile);
        
        Status testStatus;
        try {
            testStatus = Output_parser.output_file_state_parser(testResultsOutputFile);
        } catch (FileParsingFailedException e) {
            e.printStackTrace();
            return;
        }

        // update github status
        String HEADcommitSHA = RequestExtraction.getLatestCommitSHA(JSONrequest);
        if (testStatus == Status.SUCCESS) {
            updateCommitStatusOnGithub(HEADcommitSHA, true);
        } else {
            updateCommitStatusOnGithub(HEADcommitSHA, false);
        }

        // deploy site
        String deployArgs = HEADcommitSHA + " " + testStatus.toString() + " " + "'" + getLogs(testResultsOutputFile) + "'";
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "deploy.sh " + deployArgs);
        processBuilder.redirectError(new File("builder_error_file"));
        processBuilder.start();
    }

    public String getLogs(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            for (String s : Files.readAllLines(file.toPath())) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean updateCommitStatusOnGithub(String commitSHA, boolean success) {
        String status = success ? "success" : "failure";

        if (System.getenv("GITHUB_API_TOKEN") == null) {
            return false;
        }

        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/repos/kth-cdate-courses/DD2480-CI/statuses/" + commitSHA))
            .header("Authorization", "Bearer " + System.getenv("GITHUB_API_TOKEN"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"state\":\"" + status + "\"}"))
            .build();

        try {
            HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Tries to run the test from targeted maven directory and write results to
     * output file
     * 
     * @param targetDirectory a maven directory with code that is to be compiled and
     *                        tested
     * @param outputFile      file to where the output from the compilation and
     *                        tests will be written
     */
    void compileAndRunTests(File targetDirectory, File outputFile) {
        boolean isMavenProject = new File(targetDirectory.getAbsolutePath() + "/pom.xml").exists();
        if (!(targetDirectory.isDirectory() && isMavenProject))
            throw new IllegalArgumentException("targetDirectory is not a maven directory");

        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c",
                "./mvnw -f " + targetDirectory.getAbsolutePath() + " clean test");
        
        processBuilder.redirectOutput(outputFile);
        // depending on from where to method is called the default path may be different
        File ci_server_dir = new File("ci-server");
        if (ci_server_dir.exists()) {
            processBuilder.directory(ci_server_dir);
        }
        try {
            Process p = processBuilder.start();
            while (p.isAlive()) {
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to append a commit to a json file with a list of commits.
     * 
     * It is assumed that the json file contains a list tagged "commits" in the top
     * level.
     * 
     * @param file   json file
     * @param commit commit to be appended
     * @throws IllegalArgumentException Must be a .json file
     */
    void appendCommit(File file, Commit commit) throws IllegalArgumentException {
        boolean isJsonFile = file.getName().endsWith(".json");
        if (!file.exists() || !isJsonFile)
            throw new IllegalArgumentException("Not a json file");

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode dataFile = mapper.readTree(file);
            ArrayNode commits = (ArrayNode) dataFile.get("commits");
            if (commits == null)
                throw new IllegalArgumentException("Json file does not have top-level 'commits' attribute");
            commits.addPOJO(commit);
            mapper.writeValue(file, dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Totally empties directory with the given path.
     * If the directory does not exists, creates it.     
     * @param dirPath path to the directory to empty
     */
    static void emptyOrCreateDirectory(File dirPath){
        try{
            if (dirPath.exists()){
                // if it exists, empty the directory
                FileUtils.cleanDirectory(dirPath);
            }
            else {
                // if it doesn't exist, create the directory
                dirPath.mkdir();
            }    
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Downloads the code of the repository which triggered a request.
     * 
     * @param request contains information on the event and the repository
     */
    static void downloadCode(JsonNode request) throws DownloadFailedException {
        String repoUrl = RequestExtraction.getRepositoryUrlFromRequest(request);
        File repoDirectoryPath = new File("./watched-repository");
        // needed to ensure repo is cloned into ci-server/.watched-repository
        if (new File("ci-server").exists())
            repoDirectoryPath = new File("ci-server/watched-repository");
        emptyOrCreateDirectory(repoDirectoryPath);
        String branch = RequestExtraction.getBranch(request);
        cloneRepository(repoUrl, repoDirectoryPath, branch);
    }

    /**
     * Clones the watched repository to a given directory in the project.
     * Redirects the output to the given output file.
     * Requires the indicated directory to exist and be empty.
     * 
     * @param repoUrl url of the Git repository
     * @param repoDirectory file path to the folder where the repository will be cloned
     * @param branch branch to checkout, null means default
     * @param outputFile file to save the output
     */
    static void cloneRepository(String repoUrl, File repoDirectory, String branch) throws DownloadFailedException {
        if (repoUrl == null)
            throw new DownloadFailedException();
            
        CloneCommand c = new CloneCommand();
        c.setURI(repoUrl);
        c.setDirectory(repoDirectory);
        c.setBranch(branch);
        try {
            c.call();
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
            throw new DownloadFailedException();
        } catch (TransportException e) {
            e.printStackTrace();
            throw new DownloadFailedException();
        } catch (GitAPIException e) {
            e.printStackTrace();
            throw new DownloadFailedException();
        }
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8001);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}