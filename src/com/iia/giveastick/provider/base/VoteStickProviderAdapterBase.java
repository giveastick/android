/**************************************************************************
 * VoteStickProviderAdapterBase.java, giveastick Android
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



import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.StickSQLiteAdapter;

/**
 * VoteStickProviderAdapterBase.
 */
public abstract class VoteStickProviderAdapterBase
				extends ProviderAdapterBase<VoteStick> {

	/** TAG for debug purpose. */
	protected static final String TAG = "VoteStickProviderAdapter";

	/** VOTESTICK_URI. */
	public	  static Uri VOTESTICK_URI;

	/** voteStick type. */
	protected static final String voteStickType =
			"votestick";

	/** VOTESTICK_ALL. */
	protected static final int VOTESTICK_ALL =
			2095841370;
	/** VOTESTICK_ONE. */
	protected static final int VOTESTICK_ONE =
			2095841371;

	/** VOTESTICK_USER. */
	protected static final int VOTESTICK_USER =
			2095841372;
	/** VOTESTICK_STICK. */
	protected static final int VOTESTICK_STICK =
			2095841373;

	/**
	 * Static constructor.
	 */
	static {
		VOTESTICK_URI =
				GiveastickProvider.generateUri(
						voteStickType);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				voteStickType,
				VOTESTICK_ALL);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				voteStickType + "/#",
				VOTESTICK_ONE);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				voteStickType + "/#/user",
				VOTESTICK_USER);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				voteStickType + "/#/stick",
				VOTESTICK_STICK);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public VoteStickProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new VoteStickSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(VOTESTICK_ALL);
		this.uriIds.add(VOTESTICK_ONE);
		this.uriIds.add(VOTESTICK_USER);
		this.uriIds.add(VOTESTICK_STICK);
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
			case VOTESTICK_ALL:
				result = collection + "votestick";
				break;
			case VOTESTICK_ONE:
				result = single + "votestick";
				break;
			case VOTESTICK_USER:
				result = single + "votestick";
				break;
			case VOTESTICK_STICK:
				result = single + "votestick";
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
			case VOTESTICK_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = GiveastickContract.VoteStick.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case VOTESTICK_ALL:
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
			case VOTESTICK_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(GiveastickContract.VoteStick.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							VOTESTICK_URI,
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
		Cursor voteStickCursor;
		

		switch (matchedUri) {

			case VOTESTICK_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case VOTESTICK_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case VOTESTICK_USER:
				voteStickCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (voteStickCursor.getCount() > 0) {
					voteStickCursor.moveToFirst();
					int userId = voteStickCursor.getInt(voteStickCursor.getColumnIndex(
									GiveastickContract.VoteStick.COL_USER));
					
					UserSQLiteAdapter userAdapter = new UserSQLiteAdapter(this.ctx);
					userAdapter.open(this.getDb());
					result = userAdapter.query(userId);
				}
				break;

			case VOTESTICK_STICK:
				voteStickCursor = this.queryById(uri.getPathSegments().get(1));
				
				if (voteStickCursor.getCount() > 0) {
					voteStickCursor.moveToFirst();
					int stickId = voteStickCursor.getInt(voteStickCursor.getColumnIndex(
									GiveastickContract.VoteStick.COL_STICK));
					
					StickSQLiteAdapter stickAdapter = new StickSQLiteAdapter(this.ctx);
					stickAdapter.open(this.getDb());
					result = stickAdapter.query(stickId);
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
			case VOTESTICK_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						GiveastickContract.VoteStick.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case VOTESTICK_ALL:
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
		return VOTESTICK_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = GiveastickContract.VoteStick.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					GiveastickContract.VoteStick.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

