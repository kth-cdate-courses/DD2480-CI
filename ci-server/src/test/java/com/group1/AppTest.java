package com.group1;

import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import org.apache.commons.io.FileUtils;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit testing for the ContinuousIntegrationServer class
 */
public class AppTest 
{
    /**
     * Test for method emptyOrCreateRepository.
     * Tests that the given repository is emptied if it exists
     */
    @Test
    public void repositoryEmptiedTest() {
        try{
            File dir = new File("./file_test");
            File test_file = new File(dir, "test_file.txt");
            test_file.createNewFile();

            ContinuousIntegrationServer.emptyOrCreateDirectory(dir);
            String[] files = dir.list();
            assertTrue(files.length == 0);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Test for method emptyOrCreateRepository.
     * Tests that the given repository is created if it does not exist
     */
    @Test
    public void repositoryCreatedTest() {
        File dir = new File("./file_test");

        dir.delete();
        ContinuousIntegrationServer.emptyOrCreateDirectory(dir);
        assertTrue(dir.exists());
    }

     /**
     * Positive test for method cloneRepository.
     * Tests that the repository where we cloned the git project is not empty.
     */
    @Test
    public void repositoryNotEmptyCloningTest() throws DownloadFailedException
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

    /**
     * Negative test for method cloneRepository.
     * The url of the repo was not found ie is null.
     */
    @Test(expected = NullPointerException.class)
    public void noRepoUrlCloningTest() throws DownloadFailedException
    {
        File repoDirectory = new File("./watched-repository");
        File outputFile = new File("./dowloadOutput.txt");

        ContinuousIntegrationServer.cloneRepository(null, repoDirectory, outputFile);
    }

    @Test
    public void output_from_compileAndRunTests_is_written_to_file() {
        File targetDir = new File("testing_resources/maven_project_for_unit_testing");
        File outputFile = new File("testing_resources/tempUnitTestFile");

        if (outputFile.exists())
            outputFile.delete();
            
        new ContinuousIntegrationServer().compileAndRunTests(targetDir, outputFile);
        
        assertTrue(outputFile.length() > 0);
    }

    @Test
    public void compileAndRunTests_creates_directory_called_target_for_well_formed_maven_project() {
        File targetDir = new File("testing_resources/maven_project_for_unit_testing");
        File outputFile = new File("testing_resources/tempUnitTestFile");

        if (outputFile.exists())
            outputFile.delete();
            
        new ContinuousIntegrationServer().compileAndRunTests(targetDir, outputFile);
        
        File maven_target_dir = new File(targetDir, "target");
        assertTrue(maven_target_dir.exists());
    }

    @Test (expected = IllegalArgumentException.class)
    public void compileAndRunTests_throws_IllegalArgumentException_for_non_maven_Directory() {
        File targetDir = new File("testing_resources");
        File outputFile = new File("testing_resources/tempUnitTestFile");

        new ContinuousIntegrationServer().compileAndRunTests(targetDir, outputFile);
    }
}
