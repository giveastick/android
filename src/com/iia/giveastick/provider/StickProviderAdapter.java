/**************************************************************************
 * StickProviderAdapter.java, giveastick Android
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

import com.iia.giveastick.provider.base.StickProviderAdapterBase;

/**
 * StickProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class StickProviderAdapter
					extends StickProviderAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public StickProviderAdapter(final Context ctx) {
		this(ctx, null);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public StickProviderAdapter(final Context ctx,
												 final SQLiteDatabase db) {
		super(ctx, db);
	}
}

