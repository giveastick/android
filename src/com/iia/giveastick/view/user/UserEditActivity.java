/**************************************************************************
 * UserEditActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.user;

import com.iia.giveastick.R;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** User edit Activity.
 *
 * This only contains a UserEditFragment.
 *
 * @see android.app.Activity
 */
public class UserEditActivity extends HarmonyFragmentActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
