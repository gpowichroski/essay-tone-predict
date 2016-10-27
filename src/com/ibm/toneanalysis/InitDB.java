package com.ibm.toneanalysis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.ibm.toneanalysis.db.CloudantClient;

public class InitDB extends HttpServlet
{
 
    public void init() throws ServletException
    {
    	CloudantClient cc = new CloudantClient();
    	System.out.println("Initalizing DB tbd........");
    }
}