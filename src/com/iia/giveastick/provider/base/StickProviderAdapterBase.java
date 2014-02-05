/**************************************************************************
 * StickProviderAdapterBase.java, giveastick Android
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



import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;
import com.iia.giveastick.data.StickSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;

/**
 * StickProviderAdapterBase.
 */
public abstract class StickProviderAdapterBase
				extends ProviderAdapterBase<Stick> {

	/** TAG for debug purpose. */
	protected static final String TAG = "StickProviderAdapter";

	/** STICK_URI. */
	public	  static Uri STICK_URI;

	/** stick type. */
	protected static final String stickType =
			"stick";

	/** STICK_ALL. */
	protected static final int STICK_ALL =
			80212080;
	/** STICK_ONE. */
	protected static final int STICK_ONE =
			80212081;

	/** STICK_GIVER. */
	protected static final int STICK_GIVER =
			80212082;
	/** STICK_RECEIVER. */
	protected static final int STICK_RECEIVER =
			80212083;
	/** STICK_VOTESTICKS. */
	protected static final int STICK_VOTESTICKS =
			80212084;

	/**
	 * Static constructor.
	 */
	static {
		STICK_URI =
				GiveastickProvider.generateUri(
						stickType);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				stickType,
				STICK_ALL);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				stickType + "/#",
				STICK_ONE);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				stickType + "/#/giver",
				STICK_GIVER);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				stickType + "/#/receiver",
				STICK_RECEIVER);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				stickType + "/#/votesticks",
				STICK_VOTESTICKS);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public StickProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new StickSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(STICK_ALL);
		this.uriIds.add(STICK_ONE);
		this.uriIds.add(STICK_GIVER);
		this.uriIds.add(STICK_RECEIVER);
		this.uriIds.add(STICK_VOTESTICKS);
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
			case STICK_ALL:
				result = collection + "stick";
				break;
			case STICK_ONE:
				result = single + "stick";
				break;
			case STICK_GIVER:
				result = single + "stick";
				break;
			case STICK_RECEIVER:
				result = single + "stick";
				break;
			case STICK_VOTESTICKS:
				result = collection + "stick";
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
			case STICK_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = GiveastickContract.Stick.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case STICK_ALL:
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
			case STICK_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(GiveastickContract.Stick.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							STICK_URI,
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
		Cursor stickCursor;
		int id = 0;

		switch (matchedUri) {

			case STICK_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case STICK_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case STICK_GIVER:
				stickCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (stickCursor.getCount() > 0) {
					stickCursor.moveToFirst();
					int giverId = stickCursor.getInt(stickCursor.getColumnIndex(
									GiveastickContract.Stick.COL_GIVER));
					
					UserSQLiteAdapter userAdapter = new UserSQLiteAdapter(this.ctx);
					userAdapter.open(this.getDb());
					result = userAdapter.query(giverId);
				}
				break;

			case STICK_RECEIVER:
				stickCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (stickCursor.getCount() > 0) {
					stickCursor.moveToFirst();
					int receiverId = stickCursor.getInt(stickCursor.getColumnIndex(
									GiveastickContract.Stick.COL_RECEIVER));
					
					UserSQLiteAdapter userAdapter = new UserSQLiteAdapter(this.ctx);
					userAdapter.open(this.getDb());
					result = userAdapter.query(receiverId);
				}
				break;

			case STICK_VOTESTICKS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				VoteStickSQLiteAdapter voteSticksAdapter = new VoteStickSQLiteAdapter(this.ctx);
				voteSticksAdapter.open(this.getDb());
				result = voteSticksAdapter.getByStickvoteSticksInternal(id, GiveastickContract.VoteStick.ALIASED_COLS, selection, selectionArgs, null);
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
			case STICK_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						GiveastickContract.Stick.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case STICK_ALL:
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
		return STICK_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = GiveastickContract.Stick.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					GiveastickContract.Stick.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

