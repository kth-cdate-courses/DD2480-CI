package com.group1;

import javax.servlet.http.HttpServletRequest;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

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
    public void getRepositoryUrlFromRequestTest()
    {
        String testUrl = "http://test.test";
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("ssh_url")).thenReturn(testUrl);

        String repoUrl = RequestExtraction.getRepositoryUrlFromRequest(request);
        assertTrue( repoUrl.equals(testUrl));
    }
}
