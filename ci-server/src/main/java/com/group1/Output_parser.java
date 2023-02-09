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
    public static Status output_file_state_parser(File file) throws FileParsingFailedException {
        Pattern compileFailurePattern = Pattern.compile("BUILD FAILURE");
        Pattern successPattern = Pattern.compile("Failures: 0");
        Pattern testFailurePattern = Pattern.compile("Failures: [^0]+");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while(line != null) {
                if(compileFailurePattern.matcher(line).find()) {
                    return Status.COMPILE_FAILED;
                }
                if(successPattern.matcher(line).find()) {
                    return Status.SUCCESS;
                }
                if(testFailurePattern.matcher(line).find()) {
                    return Status.TEST_FAILED;
                }
                line = br.readLine();
            }
        } catch(IOException e) {
            throw new FileParsingFailedException();
        }
        throw new FileParsingFailedException();
    }
}