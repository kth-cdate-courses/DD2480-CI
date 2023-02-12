package com.group1;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Handles extraction of information from the request to process a commit.
 */
public class RequestExtraction {
    
    /**
     * Gets the URL from the repository of the processed commit.
     * 
     * @param request
     * @return string representing the cloning url of the repository
     */
    public static String getRepositoryUrlFromRequest(JsonNode request) {
        return request.get("repository").get("clone_url").toString().replaceAll("\"", "");
    }

    /**
     * Gets the SHA id from the latest commit in the processed repository.
     * 
     * @param request
     * @return string representing the SHA
     */
    public static String getLatestCommitSHA(JsonNode request) {
        return request.get("head_commit").get("id").toString().replaceAll("\"", ""); 
    }

    /**
     * Gets the branch on which the processed commit was made.
     * 
     * @param request
     * @return string of the name of the branch
     */
    public static String getBranch(JsonNode request) {
        return request.get("ref").toString().replaceAll("\"", ""); 
    }
}