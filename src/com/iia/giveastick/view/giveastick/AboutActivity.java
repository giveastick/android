package com.iia.giveastick.view.giveastick;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.iia.giveastick.R;
import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

/**
 * The about activity
 * 
 * @author Maxime Lebastard
 *
 */
public class AboutActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		// Enabling the back navigation
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.about, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean result = false;
		switch(item.getItemId()){
			case R.id.homeAsUp:
				result = true;
			break;
			
			default:
				result = super.onOptionsItemSelected(item);
			break;
		}
		
		return result;
	}

}
