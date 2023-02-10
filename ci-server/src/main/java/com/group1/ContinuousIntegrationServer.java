package com.group1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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

        // EDIT THESE VARIABLES TO ACTUALLY BE HOOKED UP TO THE COMMIT LATER
        updateCommitStatusOnGithub("SOMETHING SOMETHING", false /* EDIT THIS LATER */);
    }

    public static boolean updateCommitStatusOnGithub(String commitSHA, boolean success) {
        String status = success ? "success" : "fail";

        if (System.getenv("GITHUB_API_TOKEN") == null) {
            return false;
        }

        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/repos/kth-cdate-courses/DD2480-CI/statuses/" + commitSHA))
            .header("Authorization", "Bearer " + System.getenv("GITHUB_API_TOKEN"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"status\":\"" + status + "\"}"))
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

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8001);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}