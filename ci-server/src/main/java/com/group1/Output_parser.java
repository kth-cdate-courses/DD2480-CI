package com.group1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Output_parser {

    /**
     * takes in a file handle as an argument and returns either:
     * State.COMPILE_FAILURE enum if the file provided at some point matches the regex "BUILD FAILURE",
     * State.SUCCESS  enum if the file provided at some point matches regex the "Failures: 0",
     * Status.TEST_FAILED enum if the file provided at some point matches the regex "Failures: [^0]+".
     *
     * @param  file a file which contains the output log from building/testing a maven project
     * @return a State enum, as specified above
     * @throws FileParsingFailedException if none of above conditions are met or if file does not exist
     */
    public static Status output_file_state_parser(File file) throws FileParsingFailedException, IOException {
        Pattern compileFailurePattern = Pattern.compile("BUILD FAILURE");
        Pattern successPattern = Pattern.compile("Failures: 0");
        Pattern testFailurePattern = Pattern.compile("Failures: [^0]+");
        boolean compileFailureFlag = false;
        boolean successFlag = false;
        boolean testFailureFlag = false;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while(line != null) {
            if(compileFailurePattern.matcher(line).find())
                compileFailureFlag = true;
            if(testFailurePattern.matcher(line).find())
                testFailureFlag = true;
            if(successPattern.matcher(line).find())
                successFlag = true;
            line = br.readLine();
        }
        if(testFailureFlag)
            return Status.TEST_FAILED;
        if(compileFailureFlag)
            return Status.COMPILE_FAILED;
        if(successFlag)
            return Status.SUCCESS;
        throw new FileParsingFailedException();
    }
}