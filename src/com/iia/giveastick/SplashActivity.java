package com.iia.giveastick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.actionbarsherlock.app.ActionBar;
import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.view.giveastick.UserLoginActivity;

public class SplashActivity extends HarmonyFragmentActivity {
	private static final int SPLASH_TIMER = 2000; // The splashscreen timer in milliseconds
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		ActionBar actionbar = getSupportActionBar();
		actionbar.hide();
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				Intent intentLogin = new Intent(SplashActivity.this, UserLoginActivity.class);
				startActivity(intentLogin);
				
				finish();
			}
		}, SPLASH_TIMER);
	}
}
