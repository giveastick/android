/**************************************************************************
 * GiveastickSQLiteOpenHelper.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.data;

import com.iia.giveastick.data.base.GiveastickSQLiteOpenHelperBase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class GiveastickSQLiteOpenHelper
					extends GiveastickSQLiteOpenHelperBase {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param name name
	 * @param factory factory
	 * @param version version
	 */
	public GiveastickSQLiteOpenHelper(final Context ctx,
		   final String name, final CursorFactory factory, final int version) {
		super(ctx, name, factory, version);
	}

}
