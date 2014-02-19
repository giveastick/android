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

import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.giveastick.R;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.util.Const;
import com.iia.giveastick.util.GetWsJson;
import com.iia.giveastick.util.ParserUser;
import com.iia.giveastick.util.RestClient.Verb;

/**
 * Stick create Activity.
 * 
 * This only contains a StickCreateFragment.
 * 
 * @see android.app.Activity
 */
public class UserLoginActivity extends HarmonyFragmentActivity {
	EditText mEmail;
	EditText mPassword;
	
	private static final String TAG = "UserLoginActivity";
	
	// Android methods
	////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mEmail = (EditText) findViewById(R.id.userName);
		mPassword = (EditText) findViewById(R.id.password);
		
		Button login = (Button) findViewById(R.id.login);
		TextView register = (TextView) findViewById(R.id.register);

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentRegister = new Intent(UserLoginActivity.this,
						UserRegisterActivity.class);
				startActivity(intentRegister);
			}
		});

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserLoginActivity.this.login();
			}
		});
	}
	
	// Activity methods
	////////////////////////////
	/**
	 * Tries to login from the Webservice
	 *
	 * @author Maxime Lebastard
	 */
	protected void login(){
		if(!this.mEmail.getText().toString().matches("") && !this.mPassword.getText().toString().matches(""))
		{
			new WsLogin().execute();
		}
		else
		{
			Toast.makeText(this, R.string.login_empty, Toast.LENGTH_LONG).show();
		}
	}
	
	// Async Task
	////////////////////////////
	/**
	 * Calls the webservice to login
	 * 
	 * @author Maxime Lebastard
	 *
	 */
	private class WsLogin extends AsyncTask<Void, Void, User> {
		/**
		 * Makes a webservice call in background
		 */
		@Override
		protected User doInBackground(Void...voids) {
			User result = null;
			
			try{                                                                                                                                                   
				JSONObject postData = new JSONObject();
				postData.put("email", mEmail.getText().toString());
				postData.put("password", mPassword.getText().toString());
				
				JSONObject jsonResult = new GetWsJson("/login", postData, Verb.POST).getJSONObject();	
				if(!GetWsJson.treatError(UserLoginActivity.this, jsonResult))
				{
					result = ParserUser.getUser(jsonResult, UserLoginActivity.this);
				}
				
			}
			catch(Exception e)
			{
				Log.e(TAG, "Error while fetching user details");
			}
			
			return result;
		}
		
		/**
		 * Treats the return of the webservice
		 */
		protected void onPostExecute(User user){
			if(user != null)
			{
				Bundle myDatas = new Bundle();
				myDatas.putSerializable(Const.BUNDLE_USER, (User) user);
				Intent intentConnexion = new Intent(UserLoginActivity.this,
						StickListActivity.class);
				intentConnexion.putExtras(myDatas);
				startActivity(intentConnexion);
			}
			else
			{
				Toast.makeText(UserLoginActivity.this, R.string.false_login, Toast.LENGTH_SHORT).show();
			}
			
		}
	}
}
