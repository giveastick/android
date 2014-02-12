/**************************************************************************
 * StickCreateActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Maxime Lebastard
 * Licence     : 
 * Last update : Feb 10, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.giveastick;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.giveastick.R;
import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

/**
 * Stick create Activity.
 * 
 * This only contains a StickCreateFragment.
 * 
 * @see android.app.Activity
 */
public class StickListActivity extends HarmonyFragmentActivity {

	Button mGetServerData;
	EditText mServerUrl;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_stick_list_giveastick);
		
		mGetServerData = (Button) findViewById(R.id.GetServerData);
		mServerUrl = (EditText) findViewById(R.id.serverText);
        
        mGetServerData.setOnClickListener(
	        new View.OnClickListener() {
		        @Override
		        public void onClick(View arg0) {
		             
		            // WebServer Request URL
		            String serverURL = "http://ws.giveastick.com/login";
		             
		            // Use AsyncTask execute Method To Prevent ANR Problem
		            new GetJsonDataOperation().execute(serverURL);
		        }
	        }); 
		}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private class GetJsonDataOperation extends AsyncTask<String, Void, Void> {
		// Required initialization
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(
				StickListActivity.this);
		String data = "";
		TextView uiUpdate = (TextView) findViewById(R.id.output);
		TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
		int sizeData = 0;
		EditText serverText = (EditText) findViewById(R.id.serverText);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();

			try {
				// Set Request parameter
				data += "&" + URLEncoder.encode("data", "UTF-8") + "="
						+ serverText.getText();

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {
			/************ Make Post Call To Web Server ***********/
			BufferedReader reader = null;
			// Send data
			try {
				// Defined URL where to send data
				URL url = new URL(urls[0]);
				// Send POST data request
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				wr.write(data);
				wr.flush();
				// Get the server response
				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;
				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + " ");
				}

				// Append Server Response To Content String
				Content = sb.toString();
			} catch (Exception ex) {
				Error = ex.getMessage();
			} finally {
				try {
					reader.close();
				} catch (Exception ex) {
				}
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			Dialog.dismiss();

			if (Error != null) {
				Toast.makeText(StickListActivity.this, Error.toString(), Toast.LENGTH_LONG).show();
			} else {
				// Show Response Json On Screen (activity)
				uiUpdate.setText("Voley::::" + Content);
				JSONObject jsonResponse;

				try {
					jsonResponse = new JSONObject(Content);
					
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("android");

					

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
