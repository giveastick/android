/**************************************************************************
 * VoteStickShowActivity.java, giveastick Android
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
import com.iia.giveastick.view.votestick.VoteStickShowFragment.DeleteCallback;

import android.os.Bundle;

/** VoteStick show Activity.
 *
 * This only contains a VoteStickShowFragment.
 *
 * @see android.app.Activity
 */
public class VoteStickShowActivity 
		extends HarmonyFragmentActivity 
		implements DeleteCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_votestick_show);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemDeleted() {
		this.finish();
	}
}
