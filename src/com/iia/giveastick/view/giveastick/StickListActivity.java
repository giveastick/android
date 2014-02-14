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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iia.giveastick.R;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.util.GetHttp;
import com.iia.giveastick.util.ParserUser;

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
		            new GetJsonDataOperation().execute();
		        }
	        }); 
		}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private class GetJsonDataOperation extends AsyncTask<Void, Void, User> {
		
		protected void onPreExecute() {

		}

		// Call after onPreExecute method
		protected User doInBackground(Void... arg0) {
			User result = null;

			
			return result;
		}

		@Override
		protected void onPostExecute(User result) {
			
		}
	}
}
