/**************************************************************************
 * HarmonyFragmentActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.harmony.view;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.iia.giveastick.menu.GiveastickMenu;
import com.iia.giveastick.GiveastickApplication;
import com.iia.giveastick.GiveastickApplicationBase.DeviceType;

/**
 * Custom FragmentActivity for harmony projects.
 * This fragment activity helps you use the menu wrappers, detect alone if
 * you're in tablet/dual mode.
 */
public abstract class HarmonyFragmentActivity extends SherlockFragmentActivity {
	/** Hack number for support v4 onActivityResult. */
	protected static final int SUPPORT_V4_RESULT_HACK = 0xFFFF;

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean result = true;

		try {
			GiveastickMenu.getInstance(this).clear(menu);
			GiveastickMenu.getInstance(this).updateMenu(menu,
																		  this);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		if (result) {
			result = super.onPrepareOptionsMenu(menu);
		}

		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result;
		try {
			result = GiveastickMenu.getInstance(this).dispatch(
																	item, this);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
																  Intent data) {
		try {
			GiveastickMenu.getInstance(this).onActivityResult(
										   requestCode, resultCode, data, this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Is this device in tablet mode ?
	 *
	 * @return true if tablet mode
	 */
	public boolean isDualMode() {
		return GiveastickApplication.getDeviceType(this).equals(DeviceType.TABLET);
	}
}
