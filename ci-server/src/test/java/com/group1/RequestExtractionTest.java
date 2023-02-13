package com.group1;

import javax.servlet.http.HttpServletRequest;
import static org.mockito.Mockito.*;

import java.io.IOException;

import static org.junit.Assert.assertTrue;


import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Unit test for class RequestExtraction
 */
public class RequestExtractionTest 
{
    /**
     * Positive test for method getRepositoryUrlFromRequest
     * Using Mockito to mock HttpServletRequest
     */
     @Test
     public void getRepositoryUrlFromRequestTest() {
         String testUrl = "http://test.test";
         JsonNode requestNode = mock(JsonNode.class);
         JsonNode repoNode = mock(JsonNode.class);
         JsonNode urlNode = mock(JsonNode.class);
 
         when(requestNode.get("repository")).thenReturn(repoNode);
         when(repoNode.get("clone_url")).thenReturn(urlNode);
         when(urlNode.toString()).thenReturn(testUrl);
 
         String repoUrl = RequestExtraction.getRepositoryUrlFromRequest(requestNode);
         assertTrue( repoUrl.equals(testUrl));
     }
}
