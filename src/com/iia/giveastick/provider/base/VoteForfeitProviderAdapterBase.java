/**************************************************************************
 * VoteForfeitProviderAdapterBase.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;
import com.iia.giveastick.data.VoteForfeitSQLiteAdapter;
import com.iia.giveastick.data.UserGroupSQLiteAdapter;

/**
 * VoteForfeitProviderAdapterBase.
 */
public abstract class VoteForfeitProviderAdapterBase
				extends ProviderAdapterBase<VoteForfeit> {

	/** TAG for debug purpose. */
	protected static final String TAG = "VoteForfeitProviderAdapter";

	/** VOTEFORFEIT_URI. */
	public	  static Uri VOTEFORFEIT_URI;

	/** voteForfeit type. */
	protected static final String voteForfeitType =
			"voteforfeit";

	/** VOTEFORFEIT_ALL. */
	protected static final int VOTEFORFEIT_ALL =
			1448712201;
	/** VOTEFORFEIT_ONE. */
	protected static final int VOTEFORFEIT_ONE =
			1448712202;

	/** VOTEFORFEIT_USERGROUP. */
	protected static final int VOTEFORFEIT_USERGROUP =
			1448712203;

	/**
	 * Static constructor.
	 */
	static {
		VOTEFORFEIT_URI =
				GiveastickProvider.generateUri(
						voteForfeitType);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				voteForfeitType,
				VOTEFORFEIT_ALL);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				voteForfeitType + "/#",
				VOTEFORFEIT_ONE);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				voteForfeitType + "/#/usergroup",
				VOTEFORFEIT_USERGROUP);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public VoteForfeitProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new VoteForfeitSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(VOTEFORFEIT_ALL);
		this.uriIds.add(VOTEFORFEIT_ONE);
		this.uriIds.add(VOTEFORFEIT_USERGROUP);
	}

	@Override
	public String getType(final Uri uri) {
		String result;
		final String single =
				"vnc.android.cursor.item/"
					+ GiveastickProvider.authority + ".";
		final String collection =
				"vnc.android.cursor.collection/"
					+ GiveastickProvider.authority + ".";

		int matchedUri = GiveastickProviderBase
				.getUriMatcher().match(uri);

		switch (matchedUri) {
			case VOTEFORFEIT_ALL:
				result = collection + "voteforfeit";
				break;
			case VOTEFORFEIT_ONE:
				result = single + "voteforfeit";
				break;
			case VOTEFORFEIT_USERGROUP:
				result = single + "voteforfeit";
				break;
			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public int delete(
			final Uri uri,
			String selection,
			String[] selectionArgs) {
		int matchedUri = GiveastickProviderBase
					.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			case VOTEFORFEIT_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = GiveastickContract.VoteForfeit.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case VOTEFORFEIT_ALL:
				result = this.adapter.delete(
							selection,
							selectionArgs);
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}
	
	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		int matchedUri = GiveastickProviderBase
				.getUriMatcher().match(uri);
		
		Uri result = null;
		int id = 0;
		switch (matchedUri) {
			case VOTEFORFEIT_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(GiveastickContract.VoteForfeit.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							VOTEFORFEIT_URI,
							id);
				}
				break;
			default:
				result = null;
				break;
		}
		return result;
	}

	@Override
	public Cursor query(final Uri uri,
						String[] projection,
						String selection,
						String[] selectionArgs,
						final String sortOrder) {

		int matchedUri = GiveastickProviderBase.getUriMatcher()
				.match(uri);
		Cursor result = null;
		Cursor voteForfeitCursor;
		

		switch (matchedUri) {

			case VOTEFORFEIT_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case VOTEFORFEIT_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case VOTEFORFEIT_USERGROUP:
				voteForfeitCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (voteForfeitCursor.getCount() > 0) {
					voteForfeitCursor.moveToFirst();
					int userGroupId = voteForfeitCursor.getInt(voteForfeitCursor.getColumnIndex(
									GiveastickContract.VoteForfeit.COL_USERGROUP));
					
					UserGroupSQLiteAdapter userGroupAdapter = new UserGroupSQLiteAdapter(this.ctx);
					userGroupAdapter.open(this.getDb());
					result = userGroupAdapter.query(userGroupId);
				}
				break;

			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public int update(
			final Uri uri,
			final ContentValues values,
			String selection,
			String[] selectionArgs) {
		
		
		int matchedUri = GiveastickProviderBase.getUriMatcher()
				.match(uri);
		int result = -1;
		switch (matchedUri) {
			case VOTEFORFEIT_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						GiveastickContract.VoteForfeit.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case VOTEFORFEIT_ALL:
				result = this.adapter.update(
							values,
							selection,
							selectionArgs);
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}



	/**
	 * Get the entity URI.
	 * @return The URI
	 */
	@Override
	public Uri getUri() {
		return VOTEFORFEIT_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = GiveastickContract.VoteForfeit.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					GiveastickContract.VoteForfeit.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

