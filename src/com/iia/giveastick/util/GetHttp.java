/**
 * 
 */
package com.iia.giveastick.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import com.iia.giveastick.util.RestClient.Verb;

import android.util.Log;

/**
 * @author maxime
 * 
 */
public class GetHttp {
	
	private static final String TAG = "GetHttp";
	private static final String WS_URL = "ws.giveastick.com";

	public static String getContent(String ressource, JSONObject jsonParams, Verb method) {
		String result = null;
		
		try {
			RestClient restClient = new RestClient(GetHttp.WS_URL, 80, RestClient.SCHEME_HTTP);
			
			List<Header> headers =  new ArrayList<Header>();
			Header authHeader = new BasicHeader("Authentification", "XX-myauthkey-XX");
			headers.add(authHeader);
			
			result = restClient.invoke(Verb.POST, ressource, jsonParams, headers);
			
		} catch (Exception ex) {
			result = ex.getMessage();
			Log.e(TAG, "Issue on connection");
		}

		return result;
	}
}
