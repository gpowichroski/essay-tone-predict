package com.ibm.toneanalysis.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.toneanalysis.Config;
import com.ibm.toneanalysis.ToneConstants;
import com.ibm.toneanalysis.controller.JsonUtils;
import com.ibm.toneanalysis.model.Essay;

/** A class for communicating with the Cloudant datastore. 
 *  See main() for example usage.
 *  
 *  @author Gordon Powichroski**/
public class CloudantClient 
{
	private HttpClient httpClient;
	private CouchDbConnector dbc;
	
	private int port;
	private String name;
	private String host;
	private String username;
	private String password;
	
	private JSONArray cloudant;
    private JSONObject cloudantInstance;
    private JSONObject cloudantCredentials;
	
	public CloudantClient()
	{
		this.httpClient = null;

		 try {
            String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
            System.out.println("VCAP_SERVICES = "+VCAP_SERVICES);
            JSONObject vcap;
            vcap = (JSONObject) JSONObject.parse(VCAP_SERVICES);
            System.out.println("vcap = "+vcap);
            cloudant = (JSONArray) vcap.get("cloudantNoSQLDB");
            cloudantInstance = (JSONObject) cloudant.get(0);
            cloudantCredentials = (JSONObject) cloudantInstance.get("credentials");
        } catch (IOException e) {
            e.printStackTrace();
        }
		this.port = Config.CLOUDANT_PORT;
		this.host = (String) cloudantCredentials.get("host");
		this.username = (String) cloudantCredentials.get("username");
		this.password = (String) cloudantCredentials.get("password");
		this.name = Config.CLOUDANT_NAME;
		
		
		System.out.println("		cloudant = "+cloudant);
		System.out.println("cloudantInstance = "+cloudantInstance);
		System.out.println("cloudantCretials = "+cloudantCredentials);
		System.out.println("			port = "+port);
		System.out.println("			host = "+host);
		System.out.println("		username = "+username);
		System.out.println("		password = "+password);
		System.out.println("			name = "+name);
		
		
		
		this.dbc = this.createDBConnector();
	}
	
	/** Put a Essay into Cloudant using person.name as the unique id.
	 *  Stored as :
	 *  { 
	 *  	id: person.name, 
	 *  	type: Essay.class, 
	 *  	group: person.group, 
	 *  	json: toJSON(person) 
	 *  }
	 */
	public void putEssay(Essay p)
	{
		HashMap<String, Object> data = new HashMap<String, Object>();
		String id = generateId();
		data.put(ToneConstants.ID_KEY, id);
		data.put(ToneConstants.TYPE_KEY, Essay.class.getName());
		data.put(ToneConstants.JSON_KEY, JsonUtils.getJson(p));
		this.putItem(data);
	}
	public String generateId(){
	    //generate random UUIDs
	    UUID idOne = UUID.randomUUID();
	    System.out.println("UUID One: " + idOne);
		return idOne.toString();
	}
	
	/** Get a Essay from Cloudant using name as the unique id. **/
	public Essay getEssay(String name)
	{
		name = name.toUpperCase();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> obj = this.dbc.get(HashMap.class, name);
		Essay p = JsonUtils.getEssayFromJson((String)obj.get(ToneConstants.JSON_KEY));
		return p;
	}
	
	/** Get all Essay objects from Cloudant. **/
	public List<Essay> getEssayList()
	{
		System.out.println("getEssayList");
		List<Essay> people = new ArrayList<Essay>();
		List<String> docIds = dbc.getAllDocIds();
		for(String docId : docIds)
		{
			System.out.println("docId = "+docId);
			@SuppressWarnings("unchecked")
			HashMap<String, Object> obj = this.dbc.get(HashMap.class, docId);
			if (obj.get(ToneConstants.TYPE_KEY) != null && 
				obj.get(ToneConstants.TYPE_KEY).equals(Essay.class.getName()))
			{
				System.out.println("docId next");
				String json = (String)obj.get(ToneConstants.JSON_KEY);
				Essay p = JsonUtils.getEssayFromJson(json);
				people.add(p);
				try {
				    Thread.sleep(500);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
		}
		System.out.println(
			String.format("Retrieved %d Essay entries.", people.size()));
		return people;
	}
	
	
	public void deleteEssay(Essay p)
	{
		String name = p.essay.toUpperCase();
		List<String> docIds = this.dbc.getAllDocIds();
		int startSize = docIds.size();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> obj = this.dbc.get(HashMap.class, name);
		this.dbc.delete(obj);
		docIds = this.dbc.getAllDocIds();
		int endSize = docIds.size();
		System.out.println(
				String.format(
					"Deleted entry %s. Starting size: %d. Current size: %d.",
					name, startSize, endSize));
	}
	
	/** Put a generic item modeled as Key-Value pairs into Cloudant. **/
	private void putItem(HashMap<String, Object> data)
	{
		if (data == null) 
		{ 
			System.err.println("data cannot be null in putItem()"); 
			return;
		}
		String id = (String)data.get(ToneConstants.ID_KEY);
		if (id == null)   
		{ 
			System.err.println("data must have an _id field."); 
			return;
		}
		if (this.dbc.contains(id)) 
		{ 
			System.err.println("Didn't putItem. _id=" + id + " already exists."); 
			return;
		}
		this.dbc.create(data);
		System.out.println("Put _id=" + id + " into the datastore."); 
	}
	
	private CouchDbConnector createDBConnector() 
	{
		CouchDbInstance dbInstance = null;
		
		System.out.println("Creating CouchDB instance...");
		System.out.println(this.username);
		this.httpClient = new StdHttpClient.Builder()
		.host(this.host)
		.port(this.port)
		.username(this.username)
		.password(this.password)
		.enableSSL(true)
		.relaxedSSLSettings(true)
		.build();

		dbInstance = new StdCouchDbInstance(this.httpClient);
		CouchDbConnector dbc = new StdCouchDbConnector(this.name, dbInstance);
		dbc.createDatabaseIfNotExists();
		if (dbc.getAllDocIds().size() == 0) {
			dbc.replicateFrom("https://jsloyer.cloudant.com/talent-manager");
			while (true) {
				if (dbc.getAllDocIds().size() == 117) {
					break;
				}
				else {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return dbc;
	}
	
	private void closeDBConnector()
	{
		if (httpClient != null)
		{
			httpClient.shutdown();
		}
	}
	
	/** Example usage. **/
	public static void main(String[] args) throws Exception
	{
		CloudantClient cc = new CloudantClient();
		
		/*Essay p = new Essay("Alan Xia", null, null);
		p.group = "group1";
		cc.putEssay(p);
		
		List<Tone> traits = new ArrayList<Tone>();
		traits.add(new Tone("programming", .9));
		traits.add(new Tone("being awesome", .95));
		Essay p2 = new Essay("Dev God", traits, null);
		p2.group = "group2";
		cc.putEssay(p2);
		
		Essay testEssay = cc.getEssay("Dev God");
		System.out.println(testEssay.name);
		System.out.println(testEssay.traits);
		
		List<Essay> people = cc.getAllPeople();
		System.out.println("There are " + people.size() + " people.");
		
		List<Essay> g1ppl = cc.getAllPeopleInGroup("group1");
		System.out.println("There are " + g1ppl.size() + " people in group1.");
		
		List<Essay> notg1ppl = cc.getAllPeopleNotInGroup("group1");
		System.out.println(notg1ppl.get(0).name + " is not in group1.");
		
		cc.deleteEssay(p);
		cc.deleteEssay(p2);*/
		
		cc.closeDBConnector();
	}
}