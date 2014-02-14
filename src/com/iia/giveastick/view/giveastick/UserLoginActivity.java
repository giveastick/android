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
	
	protected void login(){
		if(!this.mEmail.getText().toString().matches("") && !this.mPassword.getText().toString().matches(""))
		{
			new WsLogin().execute();
		}
		else
		{
			Toast.makeText(this, "@string/login_empty", Toast.LENGTH_LONG).show();
		}
	}
	
	private class WsLogin extends AsyncTask<Void, Void, User> {
		@Override
		protected User doInBackground(Void...voids) {
			User result = null;
			
			try{                                                                                                                                                   
				JSONObject postData = new JSONObject();
				postData.put("email", mEmail.getText().toString());
				postData.put("password", mPassword.getText().toString());
				
				//JSONObject jsonResult = new GetWsJson("/debug", postData, Verb.POST).getJSONObject();	
			    String jsonResult = new GetWsJson("/login", postData, Verb.POST).getString();	
			    Log.d(TAG, jsonResult);
				//if(!GetWsJson.treatError(UserLoginActivity.this, jsonResult))
				//{
				//	result = ParserUser.getUser(jsonResult, UserLoginActivity.this);
				//}
				
			}
			catch(Exception e)
			{
				Log.e(TAG, "Error while fetching user details");
			}
			
			return result;
		}
		
		protected void onPostExecute(User user){
			if(user != null)
			{
				Toast.makeText(UserLoginActivity.this, "Welcome!", Toast.LENGTH_SHORT);
				//Bundle myDatas = new Bundle();
				//myDatas.putSerializable(Const.BUNDLE_USER, (User) user);
				//Intent intentConnexion = new Intent(UserLoginActivity.this,
				//		StickListActivity.class);
				//startActivity(intentConnexion);
				//intentConnexion.putExtras(myDatas);
			}
			else
			{
				Toast.makeText(UserLoginActivity.this, "@string/login_incorrect", Toast.LENGTH_SHORT).show();
			}
			
		}
	}
}
