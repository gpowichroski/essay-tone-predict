package com.ibm.toneanalysis;

import com.ibm.toneanalysis.db.CloudantClient;

public class Config 
{
    
    /** Cloudant **/
    public static final int CLOUDANT_PORT = 443;
    public static final String CLOUDANT_NAME = "teacher_db";
    
    /** Watson User Modeling **/
    
    public static final String WATSON_TONEANALYSIS_API = "/v3/tone";
    
    public static CloudantClient cc = new CloudantClient();

    /* Mobile Data Config */
    public static final String MOBILE_DATA_APP_ID = "";
    public static final String MOBILE_DATA_APP_SECRET = "";
}
