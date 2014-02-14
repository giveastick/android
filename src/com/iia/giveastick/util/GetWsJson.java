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
 * @author maxime
 *
 */
public class GetWsJson {
	private static final String TAG = "GetWsJson";
	
	protected String ressource = "";
	protected JSONObject postData = null;
	protected Verb verb;
	
	protected JSONObject jsonResult;
	protected String stringResult;
	
	
	public GetWsJson(String ressource, JSONObject postData, Verb verb){
		this.ressource = ressource;
		this.postData = postData;
		this.verb = verb;
		
		this.request();
	}
	
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
				jsonData = jsonAndroid.getJSONObject("data");
			}
			catch(Exception e){
				Log.e(TAG, "Error while putting data in a JSONObject result");
			}
		}
		
		jsonResult = result;
	}
	
	public String getString()
	{
		return stringResult;
	}
	
	public JSONObject getJSONObject()
	{
		return jsonResult;
	}
	
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
		}
		
		return result;
	}
}
