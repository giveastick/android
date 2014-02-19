/**
 * 
 */
package com.iia.giveastick.util;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.iia.giveastick.util.RestClient.Verb;

/**
 * Calls the webservice, treats the errors and returns a JSON object from the WS
 * 
 * @author Maxime Lebastard
 *
 */
public class GetWsJson {
	private static final String TAG = "GetWsJson";
	
	/**
	 * The ressource url of the ws
	 */
	protected String ressource = "";
	/**
	 * Post data to send, encapsuled in a JSON Object
	 */
	protected JSONObject postData = null;
	/**
	 * Verb of the request (POST, GET, DELETE or PUT)
	 */
	protected Verb verb;
	
	/**
	 * The result, in JSONObject format
	 */
	protected JSONObject jsonResult;
	
	/**
	 * The result, in string format
	 */
	protected String stringResult;
	
	/**
	 * Constructor, builds the object
	 * 
	 * @param ressource		The URL ressource on the WS
	 * @param postData		Post data to send, in a JSON Object
	 * @param verb			Verb of the call
	 */
	public GetWsJson(String ressource, JSONObject postData, Verb verb){
		this.ressource = ressource;
		this.postData = postData;
		this.verb = verb;
		
		this.request();
	}
	
	/**
	 * Makes the request with a GetHttp object
	 * 
	 * @author Maxime Lebastard
	 */
	protected void request()
	{
		JSONObject result = null;
		String httpResult = "";
		JSONObject jsonRoot = null;
		JSONObject jsonAndroid = null;
		boolean jsonError = false;
		boolean jsonSuccess = false;
		String jsonErrorMessage = null;
		JSONObject jsonData = null;
		
		/** Get the URL content **/
		try{
			httpResult = GetHttp.getContent(this.ressource, this.postData, this.verb);
			this.stringResult = httpResult;
			Log.d(TAG, this.stringResult);
		}
		catch(Exception e)
		{
			Log.e(TAG, "Error when getting the HTTP Result");
		}
		
		/** Convert the string to a JSON Object **/
		try{
			jsonRoot = new JSONObject(httpResult);
		}
		catch(Exception e)
		{
			Log.e(TAG, "HTTP response seems to not be a JSON string");
			Log.e(TAG, e.getMessage());
		}
		
		/** Parse the android result **/
		try{
			jsonAndroid = jsonRoot
					.getJSONObject("android");
		}
		catch(Exception e)
		{
			Log.e(TAG, "No android root in http response");
		}
		
		/** Detect errors */
		try{
			jsonError = jsonAndroid.getBoolean("error");
			jsonErrorMessage = jsonAndroid.getString("message");
		}
		catch(Exception e)
		{}
		
		/** Detect success **/
		try
		{
			jsonSuccess = jsonAndroid.getBoolean("success");
		}
		catch(Exception e)
		{}
		
		if(!jsonError && !jsonSuccess)
		{
			Log.e(TAG, "No error, but no success...");
		}
		else if(jsonError && !jsonSuccess)
		{
			/** If not successful, return a clean error **/
			Log.e(TAG, "Webservice returned an error");
			try
			{
				result = new JSONObject();
				result.put("error", "true");
				result.put("message", jsonErrorMessage);
			}
			catch(Exception e){
				Log.e(TAG, "Error while putting error in JSONObject result");
			}
		}
		else
		{
			/** If successful, return the data **/
			try
			{
				this.jsonResult = jsonAndroid.getJSONObject("data");
			}
			catch(Exception e){
				Log.e(TAG, "Error while putting data in a JSONObject result");
			}
		}
	}
	
	/**
	 * stringResult getter
	 * @return	String stringResult
	 */
	public String getString()
	{
		return stringResult;
	}
	
	/**
	 * jsonResult getter
	 * @return	String jsonResult
	 */
	
	public JSONObject getJSONObject()
	{
		return jsonResult;
	}
	
	/**
	 * Analyses the returned JSONObject and treats the errors
	 * @param context	The Context object
	 * @param json		The JSONObject returned by GetWsJson
	 * @return
	 */
	public static boolean treatError(Context context, JSONObject json)
	{
		boolean result = false;
		try{
			if(json.has("error") && json.has("message"))
			{
				result = true;
				Log.e(TAG, "WS Error message: " + json.getString("message"));
			}
		}
		catch(Exception e)
		{
			result = true;
			Log.e(TAG, "json seems to not be a json object");
			Log.e(TAG, e.getMessage());
		}
		
		return result;
	}
}
