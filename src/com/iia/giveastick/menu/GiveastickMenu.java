/**************************************************************************
 * GiveastickMenu.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.menu;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.iia.giveastick.menu.base.GiveastickMenuBase;

/**
 * GiveastickMenu.
 * 
 * This class is an engine used to manage the different menus of your application.
 * Its use is quite simple :
 * Create a class called [YourMenuName]MenuWrapper in this package and
 * make it implement the interface MenuWrapperBase.
 * (For examples, please see CrudCreateMenuWrapper and CrudEditDeleteMenuWrapper in
 * this package.)
 * When this is done, just call this harmony command :
 * script/console.sh orm:menu:update.
 * This will auto-generate a group id for your menu.
 */
public class GiveastickMenu
				extends GiveastickMenuBase {

	/** Singleton unique instance. */
	private static volatile GiveastickMenu singleton;

	/**
	 * Constructor.
	 * @param ctx The Context
	 * @throws Exception If something bad happened
	 */
	public GiveastickMenu(final Context ctx) throws Exception {
		super(ctx);
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 * @param fragment The parent fragment
	 * @throws Exception If something bad happened
	 */
	public GiveastickMenu(final Context ctx,
						final Fragment fragment) throws Exception {
		super(ctx, fragment);
	}

	/** Get unique instance.
	 * @param ctx The context
	 * @return GiveastickMenu instance
	 * @throws Exception If something bad happened
	 */
	public static final synchronized GiveastickMenu getInstance(
						final Context ctx) throws Exception {
		return getInstance(ctx, null);
	}

	/** Get unique instance.
	 * @param ctx The context
	 * @param fragment The parent fragment
	 * @return GiveastickMenu instance
	 * @throws Exception If something bad happened
	 */
	public static final synchronized GiveastickMenu getInstance(
			final Context ctx, final Fragment fragment) throws Exception {
		if (singleton == null) {
			singleton = new GiveastickMenu(ctx, fragment);
		}  else {
			singleton.ctx = ctx;
			singleton.fragment = fragment;
		}

		return singleton;
	}
}
