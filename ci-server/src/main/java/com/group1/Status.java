package com.group1;

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