/**************************************************************************
 * VoteForfeitEditActivity.java, giveastick Android
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

/** VoteForfeit edit Activity.
 *
 * This only contains a VoteForfeitEditFragment.
 *
 * @see android.app.Activity
 */
public class VoteForfeitEditActivity extends HarmonyFragmentActivity {

	@Override
  	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_voteforfeit_edit);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
