package com.group1;

import org.junit.Test;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Output_parserTest {

    //Testing of a file which contains the output message of compile failure. method output_file_state_parser/1 should return
    //the enum Status.COMPILE_FAILED
    @Test
    public void compileFail() throws FileParsingFailedException, IOException {
        assertTrue(Output_parser.output_file_state_parser(new File("testing_resources/compileFail.txt")) == Status.COMPILE_FAILED);
    }

    //Testing of a file which contains the output message of a test failure. method output_file_state_parser/1 should return
    //the enum Status.TEST_FAILED
    @Test
    public void testFail() throws FileParsingFailedException, IOException {
        assertTrue(Output_parser.output_file_state_parser(new File("testing_resources/testFail.txt")) == Status.TEST_FAILED);
    }

    //Testing of a file which contains the output message of successful build and testing.
    //method output_file_state_parser/1 should return the enum Status.SUCCESS
    @Test
    public void testSuccess() throws FileParsingFailedException, IOException {
        assertTrue(Output_parser.output_file_state_parser(new File("testing_resources/testSuccess.txt")) == Status.SUCCESS);
    }

    //Testing of a file which does not exist. method output_file_state_parser/1 should throw FileParsingFailedException
    @Test(expected = IOException.class)
    public void nonexistentFile() throws FileParsingFailedException, IOException {
        Output_parser.output_file_state_parser(new File("testing_resources/nonexistentFile.txt"));
    }

    //Testing of a file which contains the output message of successful build and testing.
    //method output_file_state_parser/1 should return the enum Status.SUCCESS,
    //which should be not equal to the enum Status.COMPILE_FAILED
    @Test
    public void falseConclusion() throws FileParsingFailedException, IOException {
        assertFalse(Output_parser.output_file_state_parser(new File("testing_resources/testSuccess.txt")) == Status.COMPILE_FAILED);
    }

}