package com.ibm.toneanalysis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.ibm.toneanalysis.Config;
import com.ibm.toneanalysis.model.Essay;

/** Handles the GET /api/essayList endpoint.
 *  Returns a JSON representation of all of the Essay objects. 
 *  @author Gordon Powichroski **/
@Path("/essayList")
public class EssayListController 
{
	/** Return people who are current employees in the user's group. **/
	public static List<Essay> essayList = new ArrayList<Essay>();
			//Config.cc.getEssayList();
	@GET
	public Response handle()
	{
		System.out.println("EssayListController");
		essayList = Config.cc.getEssayList();
		System.out.println(essayList.toString());
		String json = JsonUtils.getListEssayJson(essayList);
		System.out.println(json.toString());
		return Response.ok(json.toString()).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	}
}