/**************************************************************************
 * VoteStickSQLiteAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.data;

import com.iia.giveastick.data.base.VoteStickSQLiteAdapterBase;
import android.content.Context;

/**
 * VoteStick adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class VoteStickSQLiteAdapter extends VoteStickSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public VoteStickSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
