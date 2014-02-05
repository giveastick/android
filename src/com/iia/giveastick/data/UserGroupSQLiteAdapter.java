/**************************************************************************
 * UserGroupSQLiteAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.data;

import com.iia.giveastick.data.base.UserGroupSQLiteAdapterBase;
import android.content.Context;

/**
 * UserGroup adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class UserGroupSQLiteAdapter extends UserGroupSQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserGroupSQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
