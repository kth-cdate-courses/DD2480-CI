package com.group1;

import org.junit.Test;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test of the Output_parser class
 */
public class Output_parserTest {

    /**
     * Testing of a file which contains the output message of compile failure. 
     * Method output_file_state_parser/1 should return the enum Status.COMPILE_FAILED
     * @throws FileParsingFailedException
     * @throws IOException 
     */
    @Test
    public void compileFail() throws FileParsingFailedException, IOException {
        assertTrue(Output_parser.output_file_state_parser(new File("testing_resources/compileFail.txt")) == Status.COMPILE_FAILED);
    }

    /**
     * Testing of a file which contains the output message of test failure. 
     * Method output_file_state_parser/1 should return the enum Status.TEST_FAILED
     * @throws FileParsingFailedException
     * @throws IOException 
     */
    @Test
    public void testFail() throws FileParsingFailedException, IOException {
        assertTrue(Output_parser.output_file_state_parser(new File("testing_resources/testFail.txt")) == Status.TEST_FAILED);
    }

    /**
     * Testing of a file which contains the output message of successful build and testing.
     * Method output_file_state_parser/1 should return the enum Status.SUCCESS
     * @throws FileParsingFailedException
     * @throws IOException
     */
    @Test
    public void testSuccess() throws FileParsingFailedException, IOException {
        assertTrue(Output_parser.output_file_state_parser(new File("testing_resources/testSuccess.txt")) == Status.SUCCESS);
    }


    /**
     * Testing of a file which does not exist.
     * Method output_file_state_parser/1 should throw FileParsingFailedException
     * @throws FileParsingFailedException
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void nonexistentFile() throws FileParsingFailedException, IOException {
        Output_parser.output_file_state_parser(new File("testing_resources/nonexistentFile.txt"));
    }

    /**
     * Testing of a file which contains the output message of successful build and testing.
     * Method output_file_state_parser/1 should return the enum Status.SUCCESS,
     * which should be not equal to the enum Status.COMPILE_FAILED
     * @throws FileParsingFailedException
     * @throws IOException
     */
    @Test
    public void falseConclusion() throws FileParsingFailedException, IOException {
        assertFalse(Output_parser.output_file_state_parser(new File("testing_resources/testSuccess.txt")) == Status.COMPILE_FAILED);
    }

}