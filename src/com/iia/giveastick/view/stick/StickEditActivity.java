/**************************************************************************
 * StickEditActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.stick;

import com.iia.giveastick.R;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Stick edit Activity.
 *
 * This only contains a StickEditFragment.
 *
 * @see android.app.Activity
 */
public class StickEditActivity extends HarmonyFragmentActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_stick_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
