package com.group1;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class RequestExtraction {

    public static String getRepositoryUrlFromRequest(JsonNode request) {
        return request.get("repository").get("clone_url").toString();
    }

    public static String getLatestCommitSHA(JsonNode request) {
        return request.get("head_commit").get("id").toString(); 
    }
}