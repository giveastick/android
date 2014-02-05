/**************************************************************************
 * UserGroupListLoader.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.usergroup;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.iia.giveastick.criterias.UserGroupCriterias;

/**
 * UserGroup Loader.
 */
public class UserGroupListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit UserGroupCriterias
	 */
	public UserGroupListLoader(
			final Context ctx,
			final UserGroupCriterias crit) {
		super(ctx);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param selection The selection
	 * @param selectionArgs The selection Args
	 * @param sortOrder The sort order
	 */
	public UserGroupListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					String selection,
					String[] selectionArgs,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				selection,
				selectionArgs,
				sortOrder);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param criterias UserGroupCriterias
	 * @param sortOrder The sort order
	 */
	public UserGroupListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					UserGroupCriterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
