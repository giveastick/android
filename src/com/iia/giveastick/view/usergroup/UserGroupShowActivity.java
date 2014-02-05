/**************************************************************************
 * UserGroupShowActivity.java, giveastick Android
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
import com.iia.giveastick.view.usergroup.UserGroupShowFragment.DeleteCallback;

import android.os.Bundle;

/** UserGroup show Activity.
 *
 * This only contains a UserGroupShowFragment.
 *
 * @see android.app.Activity
 */
public class UserGroupShowActivity 
		extends HarmonyFragmentActivity 
		implements DeleteCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_usergroup_show);
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
