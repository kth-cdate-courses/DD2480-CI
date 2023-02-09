package com.group1;

import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Output_parserTest {

    //Testing of a file which contains the output message of compile failure. method output_file_state_parser/1 should return
    //the enum Status.COMPILE_FAILED
    @Test
    public void compileFail() throws FileParsingFailedException {
        assertTrue(Output_parser.output_file_state_parser(new File("compileFail.txt")) == Status.COMPILE_FAILED);
    }

    //Testing of a file which contains the output message of a test failure. method output_file_state_parser/1 should return
    //the enum Status.TEST_FAILED
    @Test
    public void testFail() throws FileParsingFailedException {
        assertTrue(Output_parser.output_file_state_parser(new File("testFail.txt")) == Status.TEST_FAILED);
    }

    //Testing of a file which contains the output message of successful build and testing.
    //method output_file_state_parser/1 should return the enum Status.SUCCESS
    @Test
    public void testSuccess() throws FileParsingFailedException {
        assertTrue(Output_parser.output_file_state_parser(new File("testSuccess.txt")) == Status.SUCCESS);
    }

    //Testing of a file which does not exist. method output_file_state_parser/1 should throw FileParsingFailedException
    @Test(expected = FileParsingFailedException.class)
    public void nonexistentFile() throws FileParsingFailedException {
        Output_parser.output_file_state_parser(new File("nonexistentFile.txt"));
    }

    //Testing of a file which contains the output message of successful build and testing.
    //method output_file_state_parser/1 should return the enum Status.SUCCESS,
    //which should be not equal to the enum Status.COMPILE_FAILED
    @Test
    public void falseConclusion() throws FileParsingFailedException {
        assertFalse(Output_parser.output_file_state_parser(new File("testSuccess.txt")) == Status.COMPILE_FAILED);
    }

}