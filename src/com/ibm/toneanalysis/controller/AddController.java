package com.ibm.toneanalysis.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.toneanalysis.Config;
import com.ibm.toneanalysis.model.AddEssayRequest;
import com.ibm.toneanalysis.model.Essay;

/** Handles the POST /api/add endpoint.
 *  Returns status 204 if a essay was successfully added. 
 *  @author Gordon Powichroski **/
@Path("/add")
public class AddController 
{
	/** Create a new Essay. **/
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handle(String json)
	{
		System.out.println(json);
		Essay p = essayFromRequest(json);
		System.out.println(p);
		Config.cc.putEssay(p);
		//addToGlobalList(p);
		System.out.println("X = "+p.getTone().getResponseText().toString());
		return Response.ok(p.getTone().getResponseText()).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	 }
	
	protected static Essay essayFromRequest(String json)
	{
		AddEssayRequest apr = JsonUtils.getAPRFromJson(json);
		Essay p = AddEssayRequest.toEssay(apr);
		return p;
	}
	
	protected static void addToGlobalList(Essay p)
	{
		EssayListController.essayList.add(p);
	}
	
}