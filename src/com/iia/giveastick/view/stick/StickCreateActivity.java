/**************************************************************************
 * StickCreateActivity.java, giveastick Android
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

/** 
 * Stick create Activity.
 *
 * This only contains a StickCreateFragment.
 *
 * @see android.app.Activity
 */
public class StickCreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_stick_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
