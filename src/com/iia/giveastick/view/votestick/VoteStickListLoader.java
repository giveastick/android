/**************************************************************************
 * VoteStickListLoader.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.votestick;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.iia.giveastick.criterias.VoteStickCriterias;

/**
 * VoteStick Loader.
 */
public class VoteStickListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit VoteStickCriterias
	 */
	public VoteStickListLoader(
			final Context ctx,
			final VoteStickCriterias crit) {
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
	public VoteStickListLoader(
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
	 * @param criterias VoteStickCriterias
	 * @param sortOrder The sort order
	 */
	public VoteStickListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					VoteStickCriterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
