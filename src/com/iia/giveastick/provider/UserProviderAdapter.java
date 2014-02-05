/**************************************************************************
 * UserProviderAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.iia.giveastick.provider.base.UserProviderAdapterBase;

/**
 * UserProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class UserProviderAdapter
					extends UserProviderAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserProviderAdapter(final Context ctx) {
		this(ctx, null);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public UserProviderAdapter(final Context ctx,
												 final SQLiteDatabase db) {
		super(ctx, db);
	}
}

