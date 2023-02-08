package com.group1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;

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

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}