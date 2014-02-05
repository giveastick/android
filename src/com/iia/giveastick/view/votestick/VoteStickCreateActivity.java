/**************************************************************************
 * VoteStickCreateActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.votestick;

import com.iia.giveastick.R;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * VoteStick create Activity.
 *
 * This only contains a VoteStickCreateFragment.
 *
 * @see android.app.Activity
 */
public class VoteStickCreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_votestick_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
