package com.group1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

class Output_parser {
    /*
     * takes in a file handle as an argument and returns either:
     * State.COMPILE_FAILURE enum if the file provided contains the regex "BUILD FAILURE".
     * State.SUCCESS  enum if the file provided contains the regex "Failures: 0".
     * Status.TEST_FAILED enum if the file provided contains the regex "Failures: [^0]+".
     * Status.DOWNLOAD_FAILED enum if none of the above conditions can be met or if the method throws an IOException.
     */
    public Status output_file_state_parser(File file) {
        Pattern compileFailurePattern = Pattern.compile("BUILD FAILURE");
        Pattern successPattern = Pattern.compile("Failures: 0");
        Pattern testFailurePattern = Pattern.compile("Failures: [^0]+");
        Status outToken = Status.DOWNLOAD_FAILED;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line != null) {
                if(compileFailurePattern.matcher(line).find()) {
                    outToken = Status.COMPILE_FAILED;
                    break;
                }
                if(successPattern.matcher(line).find()) {
                    outToken = Status.SUCCESS;
                    break;
                }
                if(testFailurePattern.matcher(line).find()) {
                    outToken = Status.TEST_FAILED;
                    break;
                }
                line = br.readLine();
            }
            br.close();
            return outToken;
        } catch(IOException e) {
            return Status.DOWNLOAD_FAILED;
        }
    }
}