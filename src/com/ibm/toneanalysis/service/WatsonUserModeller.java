package com.ibm.toneanalysis.service;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.toneanalysis.Config;
import com.ibm.toneanalysis.model.EmotionTone;
import com.ibm.toneanalysis.model.LanguageTone;
import com.ibm.toneanalysis.model.SocialTendenciesTone;
import com.ibm.toneanalysis.model.TonePerEssay;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.*;

/** A wrapper for accessing the Watson User Model API.
 *  Usage: 
 *  	WatsonUserModeller WUM = new WatsonUserModeller();
 *  	List<Tone> tones = WUM.getTonesList("all of the twitter text");
 *    
 *  @author Gordon Powichroski **/

public class WatsonUserModeller 
{
	private String username;
	private String password;
	private String base_url;
	private String toneanalysis_api;
	
	private static JSONArray watson;
    private static JSONObject watsonInstance;
    private static JSONObject watsonCredentials;
	
	private Executor executor;
	
	public WatsonUserModeller()
	{
		try {
            String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
            JSONObject vcap;
            vcap = (JSONObject) JSONObject.parse(VCAP_SERVICES);
            
          watson = (JSONArray) vcap.get("tone_analyzer");
          System.out.println("watson = "+watson);      
          watsonInstance = (JSONObject) watson.get(0);
          watsonCredentials = (JSONObject) watsonInstance.get("credentials");
        } catch (IOException e) {
            e.printStackTrace();
        }
		this.username = (String) watsonCredentials.get("username");
		this.password = (String) watsonCredentials.get("password");
		this.base_url = (String) watsonCredentials.get("url");
		this.toneanalysis_api = Config.WATSON_TONEANALYSIS_API;
		
		//this.executor = Executor.newInstance().auth(username, password);
        System.out.println("username = "+username); 
        System.out.println("password = "+watson); 
        System.out.println("base_url = "+base_url); 
        System.out.println("toneanalysis_api = "+toneanalysis_api); 
        System.out.println("executor = "+executor); 
        
		//if (this.executor == null) 
		//{ 
		//	System.err.println("Authentication failed in WatsonUserModeller.");
		//}
	}
	
	/** Get the list of Tones for this text using the data 
	 *  returned by Watson. **/
	public String getTonesEasyList(String text)
	{
		System.out.println("getTonesEasyList text = "+text);
		
		ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
		//service.setEndPoint(base_url + toneanalysis_api);
		service.setUsernameAndPassword(username, password);

		String text1 =
		  "I know the times are difficult! Our sales have been "
		      + "disappointing for the past three quarters for our data analytics "
		      + "product suite. We have a competitive data analytics product "
		      + "suite in the industry. But we need to do our job selling it! "
		      + "We need to acknowledge and fix our sales challenges. "
		      + "We can’t blame the economy for our lack of execution! "
		      + "We are missing critical sales opportunities. "
		      + "Our product is in no way inferior to the competitor products. "
		      + "Our clients are hungry for analytical tools to improve their "
		      + "business outcomes. Economy has nothing to do with it.";

		// Call the service and get the tone
		System.out.println("username = "+username);
		System.out.println("password = "+password);		
		ToneAnalysis tone = (ToneAnalysis) service.getTone(text, null).execute();
		return tone.toString();
	}	
	
	/** Get the list of Tones for this text using the data 
	 *  returned by Watson. **/
	/*public TonePerEssay getTonesList(String text)
	{
		System.out.println("getTonesList text = "+text);
		TonePerEssay tones = new TonePerEssay();
		String json = getProfileJSON(text);
		System.out.println("getTonesList json = "+json);
		tones = formatTones(json);
		return tones;
	}*/
	
	/** Produce a visualization of a Essay's tones based
	 *  on the text of their tweets. Uses the Watson API. **/
	/*public String getEssayVizHTML(Essay p)
	{
		if (p == null) { return null; }
		//String profileJSON = this.getProfileJSON(combine(p.qaResponses));
		String profileJSON = this.getProfileJSON(p.essay);
		String vizHTML = this.getVizHTML(profileJSON);
		return vizHTML;
	}*/
	
	/** Get a personality profile from Watson based on the input text.
	 *  Returns a string of JSON. **/
	/*private String getProfileJSON(String text)
	{
		
		System.out.println("getProfileJSON base_url = "+base_url);
		System.out.println("getProfileJSON toneanalysis_api = "+toneanalysis_api);
		createToneAnalyzer(base_url, toneanalysis_api, buildContent(text).toString());
		return makePOST(base_url, toneanalysis_api, buildContent(text).toString());
	}*/
	
	/** Get a personality profile from Watson based on the input text.
	 *  Returns a string of JSON. **/
	/*private String createToneAnalyzer(String username, String password, String text)
	{

		return "";//.toString();
	}*/	

	
	/** Get a visualization from Watson based on the input personality profile,
	 *  e.g. from the output of getProfileJSON(). Returns a string of HTML. **/
	/*private String getVizHTML(String profileJSON)
	{
		String vizHTML = null;
		try
		{
			byte[] vizBytes = makePOSTByte(base_url, toneanalysis_api, profileJSON);
			vizHTML = new String(vizBytes, "utf-8");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return vizHTML;
	}*/
	
	/*private static TonePerEssay formatTones(String profileJson){
		TonePerEssay arr = new TonePerEssay();
		try
		{
			System.out.println("formatTones formatTones = "+profileJson);
			JSONObject tree = JSONObject.parse(profileJson);
			System.out.println("formatTones formatTonesxx ");
			formatTree((JSONObject)tree.get("tree"), 0, arr);
		} catch(Exception e) { e.printStackTrace(); }
		return arr;
	}*/
	
	private static void formatTree(JSONObject node, int level, TonePerEssay arr) {	
		if (node == null) return;
		TonePerEssay tone = new TonePerEssay();
		JSONArray toneCategories = (JSONArray)node.get("tone_categories");
		String text = (String)node.get("text");
		double s = (Double)node.get("sentence_id");
		System.out.println("formatTree toneCategories = "+toneCategories);
		System.out.println("formatTree text = "+text);
		System.out.println("formatTree s = "+s);
		tone.setToneText(text);
		tone.setSentenceId(s);
		
		EmotionTone emotionTone = new EmotionTone();
		LanguageTone languageTone = new LanguageTone();
		SocialTendenciesTone socTendenciesTone = new SocialTendenciesTone();
		
		for (int i = 0; i < toneCategories.size(); i++) {
			JSONObject jsonTone = (JSONObject) toneCategories.get(i);
			//String categoryId = (String) jsonTone.get("category_id");
			//String categoryName = (String) jsonTone.get("category_name");
			JSONArray jsonArrayTones = (JSONArray) toneCategories.get(i);
			
			System.out.println("formatTree jsonArrayTones = "+jsonArrayTones);
			
			for (int j = 0; j < jsonArrayTones.size(); j++) {
				JSONObject jsonObjTone = (JSONObject) toneCategories.get(j);
				//String toneId = (String) jsonTone.get("tone_id");
				
				System.out.println("formatTree jsonArrayTones = "+jsonArrayTones);
				
				String toneName = (String) jsonObjTone.get("tone_name");
				double score = 0.0;
				
				System.out.println("formatTree toneName = "+toneName);
				
				if (jsonTone.containsKey("score")) {
					Double p = (Double)jsonTone.get("score");
					score = Math.floor(p * 100.0);
				}
				
				System.out.println("formatTree score = "+score);
				
				if (toneName.equalsIgnoreCase("anger")) {
					emotionTone.setAnger(score);
				} else if (toneName.equalsIgnoreCase("disgust")) {
					emotionTone.setDisgust(score);
				} else if (toneName.equalsIgnoreCase("fear")) {
					emotionTone.setFear(score);
				} else if (toneName.equalsIgnoreCase("joy")) {
					emotionTone.setJoy(score);
				} else if (toneName.equalsIgnoreCase("sadness")) {
					emotionTone.setSadness(score);
				} else if (toneName.equalsIgnoreCase("analytical")) {
					languageTone.setAnalytical(score);
				} else if (toneName.equalsIgnoreCase("confident")) {
					languageTone.setConfident(score);
				} else if (toneName.equalsIgnoreCase("tentative")) {
					languageTone.setTentative(score);
				} else if (toneName.equalsIgnoreCase("agreeableness")) {
					socTendenciesTone.setAgreeableness(score);
				} else if (toneName.equalsIgnoreCase("conscientiousness")) {
					socTendenciesTone.setConcientiousness(score);
				} else if (toneName.equalsIgnoreCase("emotional range")) {
					socTendenciesTone.setEmotionRange(score);
				} else if (toneName.equalsIgnoreCase("extraversion")) {
					socTendenciesTone.setExtraversion(score);
				} else if (toneName.equalsIgnoreCase("openness")) {
					socTendenciesTone.setOpenness(score);
				}
				System.out.println("formatTree DONE1");
			}
		}	
		System.out.println("formatTree DONE2");
		arr.seteTone(emotionTone);
		arr.setlTone(languageTone);
		arr.setStTone(socTendenciesTone);
		System.out.println("formatTree DONE3");
	}
	
	/*private String makePOST(String base, String suffix, String content)
	{
		try
		{
						
			System.out.println("makePOST base = "+base);
			System.out.println("makePOST suffix = "+suffix);
			System.out.println("makePOST content = "+content);
			URI uri = new URI(base + suffix).normalize();
			String profileJSON = executor.execute(Request.Post(uri)
    			    .setHeader("Accept", "application/json")
    			    .bodyString(content, ContentType.APPLICATION_JSON)
    			    ).returnContent().asString();
			System.out.println("makePOST DONE");
    		return profileJSON;
    		
    		
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "An error occured on POST to " + base + suffix;
	}*/
	
	/*private byte[] makePOSTByte(String base, String suffix, String content)
	{
		byte[] vizHTMLData = null;
		try
		{
			URI uri = new URI(base + suffix).normalize();
			vizHTMLData = executor.execute(Request.Post(uri)
    			    .setHeader("Accept", "text/html")
    			    .bodyString(content, ContentType.APPLICATION_JSON)
    			    ).returnContent().asBytes();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("An error occured on POST to " + base + suffix);
		}
		return vizHTMLData;
	}*/
	
	/*private JSONObject buildContent(String text)
	{
		JSONObject contentItem = new JSONObject();
    	contentItem.put("userid", UUID.randomUUID().toString());
    	contentItem.put("id", UUID.randomUUID().toString());
    	contentItem.put("sourceid", "freetext");
    	contentItem.put("contenttype", "text/plain");
    	contentItem.put("language", "en");
    	contentItem.put("content", text);
    	
    	JSONObject content = new JSONObject();
    	JSONArray contentItems = new JSONArray();
    	content.put("contentItems", contentItems);
    	contentItems.add(contentItem);
    	return content;
	}*/
	
	/*private static String combine(List<String> lst)
	{
		String out = "";
		for (String s: lst)
		{
			out += s;
		}
		return out;
	}*/
	
}