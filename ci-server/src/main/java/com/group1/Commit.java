package com.group1;

/**
 * Contains commit information to send to Git API.
 */
public class Commit {
    public String sha;
    public String status;
    public String log;
    
    public Commit(String sha, String status, String log) {
        this.sha = sha;
        this.status = status;
        this.log = log;
    }
}
