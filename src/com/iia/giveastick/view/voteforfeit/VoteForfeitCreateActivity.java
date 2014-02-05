/**************************************************************************
 * VoteForfeitCreateActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.voteforfeit;

import com.iia.giveastick.R;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * VoteForfeit create Activity.
 *
 * This only contains a VoteForfeitCreateFragment.
 *
 * @see android.app.Activity
 */
public class VoteForfeitCreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_voteforfeit_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
