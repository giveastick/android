/**************************************************************************
 * UserGroupProviderAdapterBase.java, giveastick Android
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



import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;
import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.VoteForfeitSQLiteAdapter;

/**
 * UserGroupProviderAdapterBase.
 */
public abstract class UserGroupProviderAdapterBase
				extends ProviderAdapterBase<UserGroup> {

	/** TAG for debug purpose. */
	protected static final String TAG = "UserGroupProviderAdapter";

	/** USERGROUP_URI. */
	public	  static Uri USERGROUP_URI;

	/** userGroup type. */
	protected static final String userGroupType =
			"usergroup";

	/** USERGROUP_ALL. */
	protected static final int USERGROUP_ALL =
			1973690028;
	/** USERGROUP_ONE. */
	protected static final int USERGROUP_ONE =
			1973690029;

	/** USERGROUP_USERS. */
	protected static final int USERGROUP_USERS =
			1973690030;
	/** USERGROUP_VOTEFORFEITS. */
	protected static final int USERGROUP_VOTEFORFEITS =
			1973690031;

	/**
	 * Static constructor.
	 */
	static {
		USERGROUP_URI =
				GiveastickProvider.generateUri(
						userGroupType);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userGroupType,
				USERGROUP_ALL);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userGroupType + "/#",
				USERGROUP_ONE);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userGroupType + "/#/users",
				USERGROUP_USERS);
		GiveastickProvider.getUriMatcher().addURI(
				GiveastickProvider.authority,
				userGroupType + "/#/voteforfeits",
				USERGROUP_VOTEFORFEITS);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public UserGroupProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new UserGroupSQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(USERGROUP_ALL);
		this.uriIds.add(USERGROUP_ONE);
		this.uriIds.add(USERGROUP_USERS);
		this.uriIds.add(USERGROUP_VOTEFORFEITS);
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
			case USERGROUP_ALL:
				result = collection + "usergroup";
				break;
			case USERGROUP_ONE:
				result = single + "usergroup";
				break;
			case USERGROUP_USERS:
				result = collection + "usergroup";
				break;
			case USERGROUP_VOTEFORFEITS:
				result = collection + "usergroup";
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
			case USERGROUP_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = GiveastickContract.UserGroup.COL_ID
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				break;
			case USERGROUP_ALL:
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
			case USERGROUP_ALL:
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(GiveastickContract.UserGroup.COL_ID, values);
				}
				if (id > 0) {
					result = ContentUris.withAppendedId(
							USERGROUP_URI,
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
		int id = 0;

		switch (matchedUri) {

			case USERGROUP_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			case USERGROUP_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			case USERGROUP_USERS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				UserSQLiteAdapter usersAdapter = new UserSQLiteAdapter(this.ctx);
				usersAdapter.open(this.getDb());
				result = usersAdapter.getByUserGroupusersInternal(id, GiveastickContract.User.ALIASED_COLS, selection, selectionArgs, null);
				break;

			case USERGROUP_VOTEFORFEITS:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				VoteForfeitSQLiteAdapter voteForfeitsAdapter = new VoteForfeitSQLiteAdapter(this.ctx);
				voteForfeitsAdapter.open(this.getDb());
				result = voteForfeitsAdapter.getByUserGroupvoteForfeitsInternal(id, GiveastickContract.VoteForfeit.ALIASED_COLS, selection, selectionArgs, null);
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
			case USERGROUP_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values,
						GiveastickContract.UserGroup.COL_ID + " = "
						+ id,
						selectionArgs);
				break;
			case USERGROUP_ALL:
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
		return USERGROUP_URI;
	}

	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = GiveastickContract.UserGroup.ALIASED_COL_ID
						+ " = ?";

		String[] selectionArgs = new String[]{id};

		result = this.adapter.query(
					GiveastickContract.UserGroup.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

