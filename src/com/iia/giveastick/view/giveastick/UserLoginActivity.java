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

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.giveastick.HomeActivity;
import com.iia.giveastick.R;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

/**
 * Stick create Activity.
 * 
 * This only contains a StickCreateFragment.
 * 
 * @see android.app.Activity
 */
public class UserLoginActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final EditText email = (EditText) findViewById(R.id.userName);
		final EditText mdp = (EditText) findViewById(R.id.password);
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
		new InternetLogin().execute();
	}
	
	private class InternetLogin extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
		protected void onPostExecute(){
			//Bundle myDatas = new Bundle();
			//myDatas.putSerializable(Const.BUNDLE_USER, (User) myUser);
			Intent intentConnexion = new Intent(UserLoginActivity.this,
													StickListActivity.class);
			startActivity(intentConnexion);
			//intentConnexion.putExtras(myDatas);
		}
	}
}
