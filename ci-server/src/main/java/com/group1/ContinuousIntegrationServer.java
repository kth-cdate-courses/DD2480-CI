package com.group1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;


import java.io.IOException;
import java.net.MalformedURLException;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

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
     * Downloads the code of the repository which triggered a request.
     * 
     * @param request contains information on the event and the repository
     */
    static void downloadCode(HttpServletRequest request){
        try {
        URL repoUrl = new URL(RequestExtraction.getRepositoryUrlFromRequest(request));
        Path repoFolder = Paths.get("./watched-repository");
        File outputFile = new File("./dowloadOutput.txt");
        cloneRepository(repoUrl, repoFolder, outputFile);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clones the watched repository to a given folder in the project.
     * Redirects the output to the given output file.
     * 
     * @param repoUrl url of the Git repository
     * @param repoFolder path to the folder where the repository will be cloned
     * @param outputFile file to save the output
     */
    static void cloneRepository(URL repoUrl, Path repoFolder, File outputFile) {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "cd " + repoFolder.toString() + " && git clone " + repoUrl.toString());
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