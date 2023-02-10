package com.group1;

import java.net.URL;
import java.io.File;
import java.net.MalformedURLException;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit testing for the ContinuousIntegrationServer class
 */
public class AppTest 
{
     /**
     * Tests that the repository where we cloned the git project is not empty
     */
    @Test
    public void repositoryNotEmptyCloningTest()
    {
        try{
        URL repoUrl = new URL("git@github.com:kth-cdate-courses/DD2480-CI.git");
        File repoDirectory = new File("./watched-repository");
        File outputFile = new File("./dowloadOutput.txt");

        ContinuousIntegrationServer.cloneRepository(repoUrl, repoDirectory, outputFile);
        String[] files = repoDirectory.list();
        assertTrue(files.length > 0);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
