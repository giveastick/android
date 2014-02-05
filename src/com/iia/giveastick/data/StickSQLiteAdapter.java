/**************************************************************************
 * StickSQLiteAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.data;

import com.iia.giveastick.data.base.StickSQLiteAdapterBase;
import android.content.Context;

/**
 * Stick adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class StickSQLiteAdapter extends StickSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public StickSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
