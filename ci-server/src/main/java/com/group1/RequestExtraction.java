package com.group1;

import com.fasterxml.jackson.databind.JsonNode;

public class RequestExtraction {
    
    public static String getRepositoryUrlFromRequest(JsonNode request) {
        return request.get("repository").get("clone_url").toString().replaceAll("\"", "");
    }

    public static String getLatestCommitSHA(JsonNode request) {
        return request.get("head_commit").get("id").toString().replaceAll("\"", ""); 
    }
}