package com.group1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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

        System.out.println(target);

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code

        response.getWriter().println("CI job done");
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
            commits.addPOJO(commit);
            mapper.writeValue(file, dataFile);
        } catch (IOException e) {
            e.printStackTrace();
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