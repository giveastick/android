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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.iia.giveastick.HomeActivity;
import com.iia.giveastick.R;
import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

/** 
 * Stick create Activity.
 *
 * This only contains a StickCreateFragment.
 *
 * @see android.app.Activity
 */
public class UserRegisterActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_register);
		
		this.overridePendingTransition(R.anim.animation_leave,
                R.anim.animation_enter);
		
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		Button registerBt = (Button) this.findViewById(R.id.validation);
		
		registerBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				register();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, UserLoginActivity.class);
		this.startActivity(intent);
		return true;
	}
	
	@Override
	public void finish(){
	}
	
	protected void register() {
		Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
	}
}
