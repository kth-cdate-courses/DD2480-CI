package com.group1;

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
