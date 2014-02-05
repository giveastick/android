/**************************************************************************
 * VoteForfeitSQLiteAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.data;

import com.iia.giveastick.data.base.VoteForfeitSQLiteAdapterBase;
import android.content.Context;

/**
 * VoteForfeit adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class VoteForfeitSQLiteAdapter extends VoteForfeitSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public VoteForfeitSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
