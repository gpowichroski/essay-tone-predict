package com.ibm.toneanalysis.controller;

import java.util.List;

import com.google.gson.Gson;
import com.ibm.toneanalysis.model.AddEssayRequest;
import com.ibm.toneanalysis.model.Essay;

public class JsonUtils 
{
	public static String getJson(Essay p)
	{
		Gson gson = new Gson();
		String json = gson.toJson(p);
		return json;
	}

	
	public static String getListEssayJson(List<Essay> people)
	{
		Gson gson = new Gson();
		String json = gson.toJson(people);
		return json;
	}
	
	public static Essay getEssayFromJson(String json)
	{
		Gson gson = new Gson();
		Essay p = gson.fromJson(json, Essay.class);
		return p;
	}
	
	public static AddEssayRequest getAPRFromJson(String json)
	{
		Gson gson = new Gson();
		AddEssayRequest p = gson.fromJson(json, AddEssayRequest.class);
		return p;
	}
	
}
