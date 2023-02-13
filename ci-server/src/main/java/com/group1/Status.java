package com.group1;

/**
 * Status of the commit.
 * 
 * Four possible status:
 * SUCCESS if every step passed
 * DOWLOAD_FAILED if the repository could not be cloned
 * COMPILE_FAILED if the code could not be compiled
 * TEST_FAILED if the commit did not pass the tests
 */
public enum Status {
    SUCCESS,
    DOWNLOAD_FAILED,
    COMPILE_FAILED,
    TEST_FAILED;

    public String toString() {
        switch(this) {
            case SUCCESS: return "success";
            case DOWNLOAD_FAILED: return "failed";
            case COMPILE_FAILED: return "failed";
            case TEST_FAILED: return "failed";
            default: throw new IllegalArgumentException();
        }
    }
}