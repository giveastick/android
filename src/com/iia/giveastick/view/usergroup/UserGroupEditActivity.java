/**************************************************************************
 * UserGroupEditActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.usergroup;

import com.iia.giveastick.R;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** UserGroup edit Activity.
 *
 * This only contains a UserGroupEditFragment.
 *
 * @see android.app.Activity
 */
public class UserGroupEditActivity extends HarmonyFragmentActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_usergroup_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
