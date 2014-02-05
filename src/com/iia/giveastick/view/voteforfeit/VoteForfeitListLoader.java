/**************************************************************************
 * VoteForfeitListLoader.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.voteforfeit;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.iia.giveastick.criterias.VoteForfeitCriterias;

/**
 * VoteForfeit Loader.
 */
public class VoteForfeitListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit VoteForfeitCriterias
	 */
	public VoteForfeitListLoader(
			final Context ctx,
			final VoteForfeitCriterias crit) {
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
	public VoteForfeitListLoader(
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
	 * @param criterias VoteForfeitCriterias
	 * @param sortOrder The sort order
	 */
	public VoteForfeitListLoader(
					Context ctx,
					Uri uri,
					String[] projection,
					VoteForfeitCriterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
