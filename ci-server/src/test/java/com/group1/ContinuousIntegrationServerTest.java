package com.group1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Unit testing for the ContinuousIntegrationServer class
 */
public class ContinuousIntegrationServerTest 
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
     * @throws DownloadFailedException
     */
    @Test
    public void repositoryNotEmptyCloningTest() throws DownloadFailedException, MalformedURLException {
        String repoUrl = "https://github.com/kth-cdate-courses/DD2480-CI.git";
        File repoDirectory = new File("./watched-repository");

        ContinuousIntegrationServer.emptyOrCreateDirectory(repoDirectory);
        ContinuousIntegrationServer.cloneRepository(repoUrl, repoDirectory, null);
        String[] files = repoDirectory.list();
        assertTrue(files != null);
    }

    /**
     * Negative test for method cloneRepository.
     * The url of the repo was not found ie is null.
     */
    @Test(expected = DownloadFailedException.class)
    public void noRepoUrlCloningTest() throws DownloadFailedException {
        File repoDirectory = new File("./watched-repository");

        ContinuousIntegrationServer.cloneRepository(null, repoDirectory, null);
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

    @Test(expected = IllegalArgumentException.class)
    public void compileAndRunTests_throws_IllegalArgumentException_for_non_maven_Directory() {
        File targetDir = new File("testing_resources");
        File outputFile = new File("testing_resources/tempUnitTestFile");

        new ContinuousIntegrationServer().compileAndRunTests(targetDir, outputFile);
    }

    /**
     * Creates a json object with a field "commits" that is an empty list, then
     * tasks appendCommit method to add a commit to that file. Then checks that json
     * structure is as expected.
     * 
     * @throws IOException if reading or creating temporary files went wrong
     */
    @Test
    public void appendCommit_appends_commit() throws IOException {
        File jsonFile = new File("testing_resources/json_unit_testing/appendCommit_test.json");
        File expectedJsonResFile = new File("testing_resources/json_unit_testing/expected_res_json_unit_test.json");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode data = mapper.createObjectNode();
        ArrayNode emptyList = mapper.createArrayNode();
        data.set("commits", emptyList);
        mapper.writeValue(jsonFile, data);

        Commit commit = new Commit("0", "1", "2");
        new ContinuousIntegrationServer().appendCommit(jsonFile, commit);

        emptyList.addPOJO(commit);
        mapper.writeValue(expectedJsonResFile, data);

        JsonNode dataOnFile = mapper.readTree(jsonFile);
        JsonNode expectedDataOnFile = mapper.readTree(expectedJsonResFile);

        assertEquals(dataOnFile, expectedDataOnFile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void appendCommit_throws_exception_for_non_json_file() {
        File file = new File("testing_resources");
        new ContinuousIntegrationServer().appendCommit(file, new Commit("1", "2", "3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void appendCommit_throws_exception_for_json_file_without_top_level_commit_attribute() throws IOException {
        File jsonFile = new File("testing_resources/json_unit_testing/appendCommit_test.json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode data = mapper.createObjectNode();
        mapper.writeValue(jsonFile, data);

        new ContinuousIntegrationServer().appendCommit(jsonFile, new Commit("1", "1", "1"));
    }
}
