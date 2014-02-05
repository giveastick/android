/**************************************************************************
 * VoteForfeitSQLiteAdapterBase.java, giveastick Android
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
import com.iia.giveastick.data.VoteForfeitSQLiteAdapter;
import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.entity.UserGroup;

import com.iia.giveastick.harmony.util.DateUtils;
import com.iia.giveastick.GiveastickApplication;


import com.iia.giveastick.provider.GiveastickContract;

/** VoteForfeit adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit VoteForfeitAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class VoteForfeitSQLiteAdapterBase
						extends SQLiteAdapterBase<VoteForfeit> {

	/** TAG for debug purpose. */
	protected static final String TAG = "VoteForfeitDBAdapter";


	/**
	 * Get the table name used in DB for your VoteForfeit entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return GiveastickContract.VoteForfeit.TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your VoteForfeit entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = GiveastickContract.VoteForfeit.TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the VoteForfeit entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return GiveastickContract.VoteForfeit.ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ GiveastickContract.VoteForfeit.TABLE_NAME	+ " ("
		
		 + GiveastickContract.VoteForfeit.COL_USERGROUPVOTEFORFEITSINTERNAL	+ " INTEGER,"
		 + GiveastickContract.VoteForfeit.COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + GiveastickContract.VoteForfeit.COL_DATETIME	+ " DATE NOT NULL,"
		 + GiveastickContract.VoteForfeit.COL_USERGROUP	+ " INTEGER NOT NULL,"
		
		
		 + "FOREIGN KEY(" + GiveastickContract.VoteForfeit.COL_USERGROUPVOTEFORFEITSINTERNAL + ") REFERENCES " 
			 + GiveastickContract.UserGroup.TABLE_NAME 
				+ " (" + GiveastickContract.UserGroup.COL_ID + "),"
		 + "FOREIGN KEY(" + GiveastickContract.VoteForfeit.COL_USERGROUP + ") REFERENCES " 
			 + GiveastickContract.UserGroup.TABLE_NAME 
				+ " (" + GiveastickContract.UserGroup.COL_ID + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public VoteForfeitSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert VoteForfeit entity to Content Values for database.
	 * @param item VoteForfeit entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final VoteForfeit item) {
		return GiveastickContract.VoteForfeit.itemToContentValues(item);
	}

	/**
	 * Convert Cursor of database to VoteForfeit entity.
	 * @param cursor Cursor object
	 * @return VoteForfeit entity
	 */
	public VoteForfeit cursorToItem(final Cursor cursor) {
		return GiveastickContract.VoteForfeit.cursorToItem(cursor);
	}

	/**
	 * Convert Cursor of database to VoteForfeit entity.
	 * @param cursor Cursor object
	 * @param result VoteForfeit entity
	 */
	public void cursorToItem(final Cursor cursor, final VoteForfeit result) {
		GiveastickContract.VoteForfeit.cursorToItem(cursor, result);
	}

	//// CRUD Entity ////
	/**
	 * Find & read VoteForfeit by id in database.
	 *
	 * @param id Identify of VoteForfeit
	 * @return VoteForfeit entity
	 */
	public VoteForfeit getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final VoteForfeit result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getUserGroup() != null) {
			final UserGroupSQLiteAdapter userGroupAdapter =
					new UserGroupSQLiteAdapter(this.ctx);
			userGroupAdapter.open(this.mDatabase);
			
			result.setUserGroup(userGroupAdapter.getByID(
							result.getUserGroup().getId()));
		}
		return result;
	}

	/**
	 * Find & read VoteForfeit by UserGroupvoteForfeitsInternal.
	 * @param usergroupvoteforfeitsinternalId usergroupvoteforfeitsinternalId
	 * @param orderBy Order by string (can be null)
	 * @return List of VoteForfeit entities
	 */
	 public Cursor getByUserGroupvoteForfeitsInternal(final int usergroupvoteforfeitsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.VoteForfeit.COL_USERGROUPVOTEFORFEITSINTERNAL + "=?";
		String idSelectionArgs = String.valueOf(usergroupvoteforfeitsinternalId);
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
	 * Find & read VoteForfeit by userGroup.
	 * @param usergroupId usergroupId
	 * @param orderBy Order by string (can be null)
	 * @return List of VoteForfeit entities
	 */
	 public Cursor getByUserGroup(final int usergroupId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.VoteForfeit.COL_USERGROUP + "=?";
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
	 * Read All VoteForfeits entities.
	 *
	 * @return List of VoteForfeit entities
	 */
	public ArrayList<VoteForfeit> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<VoteForfeit> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a VoteForfeit entity into database.
	 *
	 * @param item The VoteForfeit entity to persist
	 * @return Id of the VoteForfeit entity
	 */
	public long insert(final VoteForfeit item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.VoteForfeit.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.VoteForfeit.itemToContentValues(item, 0);
		values.remove(GiveastickContract.VoteForfeit.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					GiveastickContract.VoteForfeit.COL_ID,
					values);
		}
		item.setId((int) newid);
		return newid;
	}

	/**
	 * Either insert or update a VoteForfeit entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The VoteForfeit entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final VoteForfeit item) {
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
	 * Update a VoteForfeit entity into database.
	 *
	 * @param item The VoteForfeit entity to persist
	 * @return count of updated entities
	 */
	public int update(final VoteForfeit item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.VoteForfeit.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.VoteForfeit.itemToContentValues(item, 0);
		final String whereClause =
				 GiveastickContract.VoteForfeit.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Update a VoteForfeit entity into database.
	 *
	 * @param item The VoteForfeit entity to persist
	 * @param usergroupId The usergroup id
	 * @return count of updated entities
	 */
	public int updateWithUserGroupVoteForfeits(
					VoteForfeit item, int usergroupId) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.VoteForfeit.TABLE_NAME + ")");
		}

		ContentValues values =
				GiveastickContract.VoteForfeit.itemToContentValues(item,
							usergroupId);
		String whereClause =
				 GiveastickContract.VoteForfeit.COL_ID
				 + "=? ";
		String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Either insert or update a VoteForfeit entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The VoteForfeit entity to persist
	 * @param usergroupId The usergroup id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWithUserGroupVoteForfeits(
			VoteForfeit item, int usergroupId) {
		int result = 0;
		if (this.getByID(item.getId()) != null) {
			// Item already exists => update it
			result = this.updateWithUserGroupVoteForfeits(item,
					usergroupId);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWithUserGroupVoteForfeits(item,
					usergroupId);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}


	/**
	 * Insert a VoteForfeit entity into database.
	 *
	 * @param item The VoteForfeit entity to persist
	 * @param usergroupId The usergroup id
	 * @return Id of the VoteForfeit entity
	 */
	public long insertWithUserGroupVoteForfeits(
			VoteForfeit item, int usergroupId) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.VoteForfeit.TABLE_NAME + ")");
		}

		ContentValues values = GiveastickContract.VoteForfeit.itemToContentValues(item,
				usergroupId);
		values.remove(GiveastickContract.VoteForfeit.COL_ID);
		int newid = (int) this.insert(
			null,
			values);


		return newid;
	}


	/**
	 * Delete a VoteForfeit entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + GiveastickContract.VoteForfeit.TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  GiveastickContract.VoteForfeit.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param voteForfeit The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final VoteForfeit voteForfeit) {
		return this.delete(voteForfeit.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the VoteForfeit corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  GiveastickContract.VoteForfeit.ALIASED_COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.query(GiveastickContract.VoteForfeit.ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a VoteForfeit entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				GiveastickContract.VoteForfeit.ALIASED_COLS,
				GiveastickContract.VoteForfeit.ALIASED_COL_ID + " = ?",
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
				GiveastickContract.VoteForfeit.ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)});
	}

}
