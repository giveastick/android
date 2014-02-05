/**************************************************************************
 * UserProviderAdapterBase.java, giveastick Android
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



import com.iia.giveastick.entity.User;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;
import com.iia.giveastick.data.StickSQLiteAdapter;

/**
 * UserProviderAdapterBase.
 */
public abstract class UserProviderAdapterBase
				extends ProviderAdapterBase<User> {

	/** TAG for debug purpose. */
	protected static final String TAG = "UserProviderAdapter";

	/** USER_URI. */
	public	  static Uri USER_URI;

	/** user type. */
	protected static final String userType =
			"user";

	/** USER_ALL. */
	protected static final int USER_ALL =
			2645995;
	/** USER_ONE. */
	protected static final int USER_ONE =
			2645996;

	/** USER_USERGROUP. */
	protected static final int USER_USERGROUP =
			2645997;
	/** USER_VOTESTICKS. */
	protected static final int USER_VOTESTICKS =
			2645998;
	/** USER_GIVENSTICKS. */
	protected static final int USER_GIVENSTICKS =
			2645999;
	/** USER_RECEIVEDSTICKS. */
	protected static final int USER_RECEIVEDSTICKS =
			2646000;

	/**
	 * Static constructor.
	 */
	static {
		USER_URI =
				GiveastickProvider.generateUri(
						userType);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userType,
				USER_ALL);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userType + "/#",
				USER_ONE);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userType + "/#/usergroup",
				USER_USERGROUP);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userType + "/#/votesticks",
				USER_VOTESTICKS);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userType + "/#/givensticks",
				USER_GIVENSTICKS);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userType + "/#/receivedsticks",
				USER_RECEIVEDSTICKS);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public UserProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new UserSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(USER_ALL);
		this.uriIds.add(USER_ONE);
		this.uriIds.add(USER_USERGROUP);
		this.uriIds.add(USER_VOTESTICKS);
		this.uriIds.add(USER_GIVENSTICKS);
		this.uriIds.add(USER_RECEIVEDSTICKS);
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
			case USER_ALL:
				result = collection + "user";
				break;
			case USER_ONE:
				result = single + "user";
				break;
			case USER_USERGROUP:
				result = single + "user";
				break;
			case USER_VOTESTICKS:
				result = collection + "user";
				break;
			case USER_GIVENSTICKS:
				result = collection + "user";
				break;
			case USER_RECEIVEDSTICKS:
				result = collection + "user";
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
			case USER_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = GiveastickContract.User.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case USER_ALL:
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
			case USER_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(GiveastickContract.User.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							USER_URI,
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
		Cursor userCursor;
		int id = 0;

		switch (matchedUri) {

			case USER_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case USER_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case USER_USERGROUP:
				userCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (userCursor.getCount() > 0) {
					userCursor.moveToFirst();
					int userGroupId = userCursor.getInt(userCursor.getColumnIndex(
									GiveastickContract.User.COL_USERGROUP));
					
					UserGroupSQLiteAdapter userGroupAdapter = new UserGroupSQLiteAdapter(this.ctx);
					userGroupAdapter.open(this.getDb());
					result = userGroupAdapter.query(userGroupId);
				}
				break;

			case USER_VOTESTICKS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				VoteStickSQLiteAdapter voteSticksAdapter = new VoteStickSQLiteAdapter(this.ctx);
				voteSticksAdapter.open(this.getDb());
				result = voteSticksAdapter.getByUservoteSticksInternal(id, GiveastickContract.VoteStick.ALIASED_COLS, selection, selectionArgs, null);
				break;

			case USER_GIVENSTICKS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				StickSQLiteAdapter givenSticksAdapter = new StickSQLiteAdapter(this.ctx);
				givenSticksAdapter.open(this.getDb());
				result = givenSticksAdapter.getByUsergivenSticksInternal(id, GiveastickContract.Stick.ALIASED_COLS, selection, selectionArgs, null);
				break;

			case USER_RECEIVEDSTICKS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				StickSQLiteAdapter receivedSticksAdapter = new StickSQLiteAdapter(this.ctx);
				receivedSticksAdapter.open(this.getDb());
				result = receivedSticksAdapter.getByUserreceivedSticksInternal(id, GiveastickContract.Stick.ALIASED_COLS, selection, selectionArgs, null);
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
			case USER_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						GiveastickContract.User.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case USER_ALL:
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
		return USER_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = GiveastickContract.User.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					GiveastickContract.User.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

