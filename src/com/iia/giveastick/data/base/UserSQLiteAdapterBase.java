/**************************************************************************
 * UserSQLiteAdapterBase.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.data.base;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;
import com.iia.giveastick.data.StickSQLiteAdapter;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.entity.Stick;

import com.iia.giveastick.harmony.util.DateUtils;
import com.iia.giveastick.GiveastickApplication;


import com.iia.giveastick.provider.GiveastickContract;

/** User adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit UserAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class UserSQLiteAdapterBase
						extends SQLiteAdapterBase<User> {

	/** TAG for debug purpose. */
	protected static final String TAG = "UserDBAdapter";


	/**
	 * Get the table name used in DB for your User entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return GiveastickContract.User.TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your User entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = GiveastickContract.User.TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the User entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return GiveastickContract.User.ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ GiveastickContract.User.TABLE_NAME	+ " ("
		
		 + GiveastickContract.User.COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + GiveastickContract.User.COL_EMAIL	+ " VARCHAR NOT NULL,"
		 + GiveastickContract.User.COL_PASSWORD	+ " VARCHAR NOT NULL,"
		 + GiveastickContract.User.COL_NICKNAME	+ " VARCHAR NOT NULL,"
		 + GiveastickContract.User.COL_CREATED	+ " DATE NOT NULL,"
		 + GiveastickContract.User.COL_ANDROIDGCMATOKEN	+ " VARCHAR,"
		 + GiveastickContract.User.COL_USERGROUP	+ " INTEGER NOT NULL,"
		 + GiveastickContract.User.COL_USERGROUPUSERSINTERNAL	+ " INTEGER,"
		
		
		 + "FOREIGN KEY(" + GiveastickContract.User.COL_USERGROUP + ") REFERENCES " 
			 + GiveastickContract.UserGroup.TABLE_NAME 
				+ " (" + GiveastickContract.UserGroup.COL_ID + "),"
		 + "FOREIGN KEY(" + GiveastickContract.User.COL_USERGROUPUSERSINTERNAL + ") REFERENCES " 
			 + GiveastickContract.UserGroup.TABLE_NAME 
				+ " (" + GiveastickContract.UserGroup.COL_ID + ")"
		+ ", UNIQUE(" + GiveastickContract.User.COL_EMAIL + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert User entity to Content Values for database.
	 * @param item User entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final User item) {
		return GiveastickContract.User.itemToContentValues(item);
	}

	/**
	 * Convert Cursor of database to User entity.
	 * @param cursor Cursor object
	 * @return User entity
	 */
	public User cursorToItem(final Cursor cursor) {
		return GiveastickContract.User.cursorToItem(cursor);
	}

	/**
	 * Convert Cursor of database to User entity.
	 * @param cursor Cursor object
	 * @param result User entity
	 */
	public void cursorToItem(final Cursor cursor, final User result) {
		GiveastickContract.User.cursorToItem(cursor, result);
	}

	//// CRUD Entity ////
	/**
	 * Find & read User by id in database.
	 *
	 * @param id Identify of User
	 * @return User entity
	 */
	public User getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final User result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getUserGroup() != null) {
			final UserGroupSQLiteAdapter userGroupAdapter =
					new UserGroupSQLiteAdapter(this.ctx);
			userGroupAdapter.open(this.mDatabase);
			
			result.setUserGroup(userGroupAdapter.getByID(
							result.getUserGroup().getId()));
		}
		final VoteStickSQLiteAdapter voteSticksAdapter =
				new VoteStickSQLiteAdapter(this.ctx);
		voteSticksAdapter.open(this.mDatabase);
		Cursor votesticksCursor = voteSticksAdapter
					.getByUservoteSticksInternal(result.getId(), GiveastickContract.VoteStick.ALIASED_COLS, null, null, null);
		result.setVoteSticks(voteSticksAdapter.cursorToItems(votesticksCursor));
		final StickSQLiteAdapter givenSticksAdapter =
				new StickSQLiteAdapter(this.ctx);
		givenSticksAdapter.open(this.mDatabase);
		Cursor givensticksCursor = givenSticksAdapter
					.getByGiver(result.getId(), GiveastickContract.Stick.ALIASED_COLS, null, null, null);
		result.setGivenSticks(givenSticksAdapter.cursorToItems(givensticksCursor));
		final StickSQLiteAdapter receivedSticksAdapter =
				new StickSQLiteAdapter(this.ctx);
		receivedSticksAdapter.open(this.mDatabase);
		Cursor receivedsticksCursor = receivedSticksAdapter
					.getByReceiver(result.getId(), GiveastickContract.Stick.ALIASED_COLS, null, null, null);
		result.setReceivedSticks(receivedSticksAdapter.cursorToItems(receivedsticksCursor));
		return result;
	}

	/**
	 * Find & read User by userGroup.
	 * @param usergroupId usergroupId
	 * @param orderBy Order by string (can be null)
	 * @return List of User entities
	 */
	 public Cursor getByUserGroup(final int usergroupId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.User.COL_USERGROUP + "=?";
		String idSelectionArgs = String.valueOf(usergroupId);
		if (!Strings.isNullOrEmpty(selection)) {
			selection += " AND " + idSelection;
			selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
		} else {
			selection = idSelection;
			selectionArgs = new String[]{idSelectionArgs};
		}
		final Cursor cursor = this.query(
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);

		return cursor;
	 }
	/**
	 * Find & read User by UserGroupusersInternal.
	 * @param usergroupusersinternalId usergroupusersinternalId
	 * @param orderBy Order by string (can be null)
	 * @return List of User entities
	 */
	 public Cursor getByUserGroupusersInternal(final int usergroupusersinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.User.COL_USERGROUPUSERSINTERNAL + "=?";
		String idSelectionArgs = String.valueOf(usergroupusersinternalId);
		if (!Strings.isNullOrEmpty(selection)) {
			selection += " AND " + idSelection;
			selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
		} else {
			selection = idSelection;
			selectionArgs = new String[]{idSelectionArgs};
		}
		final Cursor cursor = this.query(
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);

		return cursor;
	 }

	/**
	 * Read All Users entities.
	 *
	 * @return List of User entities
	 */
	public ArrayList<User> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<User> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a User entity into database.
	 *
	 * @param item The User entity to persist
	 * @return Id of the User entity
	 */
	public long insert(final User item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.User.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.User.itemToContentValues(item, 0);
		values.remove(GiveastickContract.User.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					GiveastickContract.User.COL_ID,
					values);
		}
		item.setId((int) newid);
		if (item.getVoteSticks() != null) {
			VoteStickSQLiteAdapterBase voteSticksAdapter =
					new VoteStickSQLiteAdapter(this.ctx);
			voteSticksAdapter.open(this.mDatabase);
			for (VoteStick votestick
						: item.getVoteSticks()) {
				voteSticksAdapter.insertOrUpdateWithUserVoteSticks(
									votestick,
									newid);
			}
		}
		if (item.getGivenSticks() != null) {
			StickSQLiteAdapterBase givenSticksAdapter =
					new StickSQLiteAdapter(this.ctx);
			givenSticksAdapter.open(this.mDatabase);
			for (Stick stick
						: item.getGivenSticks()) {
				stick.setGiver(item);
				givenSticksAdapter.insertOrUpdate(stick);
			}
		}
		if (item.getReceivedSticks() != null) {
			StickSQLiteAdapterBase receivedSticksAdapter =
					new StickSQLiteAdapter(this.ctx);
			receivedSticksAdapter.open(this.mDatabase);
			for (Stick stick
						: item.getReceivedSticks()) {
				stick.setReceiver(item);
				receivedSticksAdapter.insertOrUpdate(stick);
			}
		}
		return newid;
	}

	/**
	 * Either insert or update a User entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The User entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final User item) {
		int result = 0;
		if (this.getByID(item.getId()) != null) {
			// Item already exists => update it
			result = this.update(item);
		} else {
			// Item doesn't exist => create it
			final long id = this.insert(item);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}

	/**
	 * Update a User entity into database.
	 *
	 * @param item The User entity to persist
	 * @return count of updated entities
	 */
	public int update(final User item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.User.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.User.itemToContentValues(item, 0);
		final String whereClause =
				 GiveastickContract.User.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Update a User entity into database.
	 *
	 * @param item The User entity to persist
	 * @param usergroupId The usergroup id
	 * @return count of updated entities
	 */
	public int updateWithUserGroupUsers(
					User item, int usergroupId) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.User.TABLE_NAME + ")");
		}

		ContentValues values =
				GiveastickContract.User.itemToContentValues(item,
							usergroupId);
		String whereClause =
				 GiveastickContract.User.COL_ID
				 + "=? ";
		String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Either insert or update a User entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The User entity to persist
	 * @param usergroupId The usergroup id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWithUserGroupUsers(
			User item, int usergroupId) {
		int result = 0;
		if (this.getByID(item.getId()) != null) {
			// Item already exists => update it
			result = this.updateWithUserGroupUsers(item,
					usergroupId);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWithUserGroupUsers(item,
					usergroupId);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}


	/**
	 * Insert a User entity into database.
	 *
	 * @param item The User entity to persist
	 * @param usergroupId The usergroup id
	 * @return Id of the User entity
	 */
	public long insertWithUserGroupUsers(
			User item, int usergroupId) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.User.TABLE_NAME + ")");
		}

		ContentValues values = GiveastickContract.User.itemToContentValues(item,
				usergroupId);
		values.remove(GiveastickContract.User.COL_ID);
		int newid = (int) this.insert(
			null,
			values);

		VoteStickSQLiteAdapter voteSticksAdapter =
				new VoteStickSQLiteAdapter(this.ctx);
		voteSticksAdapter.open(this.mDatabase);
		if (item.getVoteSticks() != null) {
			for (VoteStick votestick : item.getVoteSticks()) {
				voteSticksAdapter.updateWithUserVoteSticks(
						votestick, newid);
			}
		}
		StickSQLiteAdapter givenSticksAdapter =
				new StickSQLiteAdapter(this.ctx);
		givenSticksAdapter.open(this.mDatabase);
		if (item.getGivenSticks() != null) {
			for (Stick stick : item.getGivenSticks()) {
				stick.setGiver(item);
				givenSticksAdapter.update(
						stick);
			}
		}
		StickSQLiteAdapter receivedSticksAdapter =
				new StickSQLiteAdapter(this.ctx);
		receivedSticksAdapter.open(this.mDatabase);
		if (item.getReceivedSticks() != null) {
			for (Stick stick : item.getReceivedSticks()) {
				stick.setReceiver(item);
				receivedSticksAdapter.update(
						stick);
			}
		}

		return newid;
	}


	/**
	 * Delete a User entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + GiveastickContract.User.TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  GiveastickContract.User.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param user The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final User user) {
		return this.delete(user.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the User corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  GiveastickContract.User.ALIASED_COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.query(GiveastickContract.User.ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a User entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				GiveastickContract.User.ALIASED_COLS,
				GiveastickContract.User.ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)},
				null,
				null,
				null);
	}

	/**
	 * Deletes the given entity.
	 * @param id The ID of the entity to delete
	 * @return the number of token deleted
	 */
	public int delete(final int id) {
		return this.delete(
				GiveastickContract.User.ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)});
	}

}
