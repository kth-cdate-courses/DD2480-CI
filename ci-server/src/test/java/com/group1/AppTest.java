package com.group1;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
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
