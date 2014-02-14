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

import android.content.Context;
import android.database.Cursor;

import com.iia.giveastick.data.base.UserGroupSQLiteAdapterBase;
import com.iia.giveastick.entity.UserGroup;

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
	
	public UserGroup getByTag(String tag){
		UserGroup result = null;
		
		Cursor c = this.mDatabase.query(this.getTableName(), this.getCols(), "tag=?", new String[]{ tag }, null, null, null);
		c.moveToFirst();
		
		if(c.getCount() > 0)
		{
			result = this.cursorToItems(c).get(0);
		}
		
		return result;
	}
}
