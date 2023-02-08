package com.group1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;

public class RequestExtraction {
    
    public String getRepositoryUrlFromRequest(HttpServletRequest request){
        return request.getParameter("ssh_url");
    }

}