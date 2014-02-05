/**************************************************************************
 * VoteStickSQLiteAdapterBase.java, giveastick Android
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
import com.iia.giveastick.data.VoteStickSQLiteAdapter;
import com.iia.giveastick.data.StickSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.Stick;

import com.iia.giveastick.harmony.util.DateUtils;
import com.iia.giveastick.GiveastickApplication;


import com.iia.giveastick.provider.GiveastickContract;

/** VoteStick adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit VoteStickAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class VoteStickSQLiteAdapterBase
						extends SQLiteAdapterBase<VoteStick> {

	/** TAG for debug purpose. */
	protected static final String TAG = "VoteStickDBAdapter";


	/**
	 * Get the table name used in DB for your VoteStick entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return GiveastickContract.VoteStick.TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your VoteStick entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = GiveastickContract.VoteStick.TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the VoteStick entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return GiveastickContract.VoteStick.ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ GiveastickContract.VoteStick.TABLE_NAME	+ " ("
		
		 + GiveastickContract.VoteStick.COL_STICKVOTESTICKSINTERNAL	+ " INTEGER,"
		 + GiveastickContract.VoteStick.COL_USERVOTESTICKSINTERNAL	+ " INTEGER,"
		 + GiveastickContract.VoteStick.COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + GiveastickContract.VoteStick.COL_ANSWER	+ " BOOLEAN NOT NULL,"
		 + GiveastickContract.VoteStick.COL_DATETIME	+ " DATE NOT NULL,"
		 + GiveastickContract.VoteStick.COL_USER	+ " INTEGER NOT NULL,"
		 + GiveastickContract.VoteStick.COL_STICK	+ " INTEGER NOT NULL,"
		
		
		 + "FOREIGN KEY(" + GiveastickContract.VoteStick.COL_STICKVOTESTICKSINTERNAL + ") REFERENCES " 
			 + GiveastickContract.Stick.TABLE_NAME 
				+ " (" + GiveastickContract.Stick.COL_ID + "),"
		 + "FOREIGN KEY(" + GiveastickContract.VoteStick.COL_USERVOTESTICKSINTERNAL + ") REFERENCES " 
			 + GiveastickContract.User.TABLE_NAME 
				+ " (" + GiveastickContract.User.COL_ID + "),"
		 + "FOREIGN KEY(" + GiveastickContract.VoteStick.COL_USER + ") REFERENCES " 
			 + GiveastickContract.User.TABLE_NAME 
				+ " (" + GiveastickContract.User.COL_ID + "),"
		 + "FOREIGN KEY(" + GiveastickContract.VoteStick.COL_STICK + ") REFERENCES " 
			 + GiveastickContract.Stick.TABLE_NAME 
				+ " (" + GiveastickContract.Stick.COL_ID + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public VoteStickSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert VoteStick entity to Content Values for database.
	 * @param item VoteStick entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final VoteStick item) {
		return GiveastickContract.VoteStick.itemToContentValues(item);
	}

	/**
	 * Convert Cursor of database to VoteStick entity.
	 * @param cursor Cursor object
	 * @return VoteStick entity
	 */
	public VoteStick cursorToItem(final Cursor cursor) {
		return GiveastickContract.VoteStick.cursorToItem(cursor);
	}

	/**
	 * Convert Cursor of database to VoteStick entity.
	 * @param cursor Cursor object
	 * @param result VoteStick entity
	 */
	public void cursorToItem(final Cursor cursor, final VoteStick result) {
		GiveastickContract.VoteStick.cursorToItem(cursor, result);
	}

	//// CRUD Entity ////
	/**
	 * Find & read VoteStick by id in database.
	 *
	 * @param id Identify of VoteStick
	 * @return VoteStick entity
	 */
	public VoteStick getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final VoteStick result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getUser() != null) {
			final UserSQLiteAdapter userAdapter =
					new UserSQLiteAdapter(this.ctx);
			userAdapter.open(this.mDatabase);
			
			result.setUser(userAdapter.getByID(
							result.getUser().getId()));
		}
		if (result.getStick() != null) {
			final StickSQLiteAdapter stickAdapter =
					new StickSQLiteAdapter(this.ctx);
			stickAdapter.open(this.mDatabase);
			
			result.setStick(stickAdapter.getByID(
							result.getStick().getId()));
		}
		return result;
	}

	/**
	 * Find & read VoteStick by StickvoteSticksInternal.
	 * @param stickvotesticksinternalId stickvotesticksinternalId
	 * @param orderBy Order by string (can be null)
	 * @return List of VoteStick entities
	 */
	 public Cursor getByStickvoteSticksInternal(final int stickvotesticksinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.VoteStick.COL_STICKVOTESTICKSINTERNAL + "=?";
		String idSelectionArgs = String.valueOf(stickvotesticksinternalId);
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
	 * Find & read VoteStick by UservoteSticksInternal.
	 * @param uservotesticksinternalId uservotesticksinternalId
	 * @param orderBy Order by string (can be null)
	 * @return List of VoteStick entities
	 */
	 public Cursor getByUservoteSticksInternal(final int uservotesticksinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.VoteStick.COL_USERVOTESTICKSINTERNAL + "=?";
		String idSelectionArgs = String.valueOf(uservotesticksinternalId);
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
	 * Find & read VoteStick by user.
	 * @param userId userId
	 * @param orderBy Order by string (can be null)
	 * @return List of VoteStick entities
	 */
	 public Cursor getByUser(final int userId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.VoteStick.COL_USER + "=?";
		String idSelectionArgs = String.valueOf(userId);
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
	 * Find & read VoteStick by stick.
	 * @param stickId stickId
	 * @param orderBy Order by string (can be null)
	 * @return List of VoteStick entities
	 */
	 public Cursor getByStick(final int stickId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.VoteStick.COL_STICK + "=?";
		String idSelectionArgs = String.valueOf(stickId);
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
	 * Read All VoteSticks entities.
	 *
	 * @return List of VoteStick entities
	 */
	public ArrayList<VoteStick> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<VoteStick> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a VoteStick entity into database.
	 *
	 * @param item The VoteStick entity to persist
	 * @return Id of the VoteStick entity
	 */
	public long insert(final VoteStick item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.VoteStick.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.VoteStick.itemToContentValues(item, 0, 0);
		values.remove(GiveastickContract.VoteStick.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					GiveastickContract.VoteStick.COL_ID,
					values);
		}
		item.setId((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a VoteStick entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The VoteStick entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final VoteStick item) {
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
	 * Update a VoteStick entity into database.
	 *
	 * @param item The VoteStick entity to persist
	 * @return count of updated entities
	 */
	public int update(final VoteStick item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.VoteStick.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.VoteStick.itemToContentValues(item, 0, 0);
		final String whereClause =
				 GiveastickContract.VoteStick.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Update a VoteStick entity into database.
	 *
	 * @param item The VoteStick entity to persist
	 * @param stickId The stick id
	 * @return count of updated entities
	 */
	public int updateWithStickVoteSticks(
					VoteStick item, int stickId) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.VoteStick.TABLE_NAME + ")");
		}

		ContentValues values =
				GiveastickContract.VoteStick.itemToContentValues(item,
							stickId, 0);
		String whereClause =
				 GiveastickContract.VoteStick.COL_ID
				 + "=? ";
		String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Either insert or update a VoteStick entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The VoteStick entity to persist
	 * @param stickId The stick id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWithStickVoteSticks(
			VoteStick item, int stickId) {
		int result = 0;
		if (this.getByID(item.getId()) != null) {
			// Item already exists => update it
			result = this.updateWithStickVoteSticks(item,
					stickId);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWithStickVoteSticks(item,
					stickId);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}


	/**
	 * Insert a VoteStick entity into database.
	 *
	 * @param item The VoteStick entity to persist
	 * @param stickId The stick id
	 * @return Id of the VoteStick entity
	 */
	public long insertWithStickVoteSticks(
			VoteStick item, int stickId) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.VoteStick.TABLE_NAME + ")");
		}

		ContentValues values = GiveastickContract.VoteStick.itemToContentValues(item,
				stickId,
				0);
		values.remove(GiveastickContract.VoteStick.COL_ID);
		int newid = (int) this.insert(
			null,
			values);


		return newid;
	}


	/**
	 * Update a VoteStick entity into database.
	 *
	 * @param item The VoteStick entity to persist
	 * @param userId The user id
	 * @return count of updated entities
	 */
	public int updateWithUserVoteSticks(
					VoteStick item, int userId) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.VoteStick.TABLE_NAME + ")");
		}

		ContentValues values =
				GiveastickContract.VoteStick.itemToContentValues(item, 0,
							userId);
		String whereClause =
				 GiveastickContract.VoteStick.COL_ID
				 + "=? ";
		String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Either insert or update a VoteStick entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The VoteStick entity to persist
	 * @param userId The user id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWithUserVoteSticks(
			VoteStick item, int userId) {
		int result = 0;
		if (this.getByID(item.getId()) != null) {
			// Item already exists => update it
			result = this.updateWithUserVoteSticks(item,
					userId);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWithUserVoteSticks(item,
					userId);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}


	/**
	 * Insert a VoteStick entity into database.
	 *
	 * @param item The VoteStick entity to persist
	 * @param userId The user id
	 * @return Id of the VoteStick entity
	 */
	public long insertWithUserVoteSticks(
			VoteStick item, int userId) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.VoteStick.TABLE_NAME + ")");
		}

		ContentValues values = GiveastickContract.VoteStick.itemToContentValues(item,
				0,
				userId);
		values.remove(GiveastickContract.VoteStick.COL_ID);
		int newid = (int) this.insert(
			null,
			values);


		return newid;
	}


	/**
	 * Delete a VoteStick entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + GiveastickContract.VoteStick.TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  GiveastickContract.VoteStick.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param voteStick The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final VoteStick voteStick) {
		return this.delete(voteStick.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the VoteStick corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  GiveastickContract.VoteStick.ALIASED_COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.query(GiveastickContract.VoteStick.ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a VoteStick entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				GiveastickContract.VoteStick.ALIASED_COLS,
				GiveastickContract.VoteStick.ALIASED_COL_ID + " = ?",
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
				GiveastickContract.VoteStick.ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)});
	}

}
