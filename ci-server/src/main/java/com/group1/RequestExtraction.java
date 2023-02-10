package com.group1;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class RequestExtraction {
    
    public static String getRepositoryUrlFromRequest(HttpServletRequest request){
        return request.getParameter("ssh_url");
    }

    public static String getLatestCommitSHA(HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode commit;
        try {
            commit = mapper.readTree(request.getParameter("head_commit"));
            return commit.get("id").toPrettyString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}