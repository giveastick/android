/**************************************************************************
 * StickSQLiteAdapterBase.java, giveastick Android
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
import com.iia.giveastick.data.StickSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;
import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.VoteStick;

import com.iia.giveastick.harmony.util.DateUtils;
import com.iia.giveastick.GiveastickApplication;


import com.iia.giveastick.provider.GiveastickContract;

/** Stick adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit StickAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class StickSQLiteAdapterBase
						extends SQLiteAdapterBase<Stick> {

	/** TAG for debug purpose. */
	protected static final String TAG = "StickDBAdapter";


	/**
	 * Get the table name used in DB for your Stick entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return GiveastickContract.Stick.TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your Stick entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = GiveastickContract.Stick.TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the Stick entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return GiveastickContract.Stick.ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ GiveastickContract.Stick.TABLE_NAME	+ " ("
		
		 + GiveastickContract.Stick.COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + GiveastickContract.Stick.COL_GIVEN	+ " DATE NOT NULL,"
		 + GiveastickContract.Stick.COL_PULLEDOFF	+ " DATE NOT NULL,"
		 + GiveastickContract.Stick.COL_GIVER	+ " INTEGER NOT NULL,"
		 + GiveastickContract.Stick.COL_RECEIVER	+ " INTEGER NOT NULL,"
		
		
		 + "FOREIGN KEY(" + GiveastickContract.Stick.COL_GIVER + ") REFERENCES " 
			 + GiveastickContract.User.TABLE_NAME 
				+ " (" + GiveastickContract.User.COL_ID + "),"
		 + "FOREIGN KEY(" + GiveastickContract.Stick.COL_RECEIVER + ") REFERENCES " 
			 + GiveastickContract.User.TABLE_NAME 
				+ " (" + GiveastickContract.User.COL_ID + ")"
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public StickSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert Stick entity to Content Values for database.
	 * @param item Stick entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Stick item) {
		return GiveastickContract.Stick.itemToContentValues(item);
	}

	/**
	 * Convert Cursor of database to Stick entity.
	 * @param cursor Cursor object
	 * @return Stick entity
	 */
	public Stick cursorToItem(final Cursor cursor) {
		return GiveastickContract.Stick.cursorToItem(cursor);
	}

	/**
	 * Convert Cursor of database to Stick entity.
	 * @param cursor Cursor object
	 * @param result Stick entity
	 */
	public void cursorToItem(final Cursor cursor, final Stick result) {
		GiveastickContract.Stick.cursorToItem(cursor, result);
	}

	//// CRUD Entity ////
	/**
	 * Find & read Stick by id in database.
	 *
	 * @param id Identify of Stick
	 * @return Stick entity
	 */
	public Stick getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final Stick result = this.cursorToItem(cursor);
		cursor.close();

		if (result.getGiver() != null) {
			final UserSQLiteAdapter giverAdapter =
					new UserSQLiteAdapter(this.ctx);
			giverAdapter.open(this.mDatabase);
			
			result.setGiver(giverAdapter.getByID(
							result.getGiver().getId()));
		}
		if (result.getReceiver() != null) {
			final UserSQLiteAdapter receiverAdapter =
					new UserSQLiteAdapter(this.ctx);
			receiverAdapter.open(this.mDatabase);
			
			result.setReceiver(receiverAdapter.getByID(
							result.getReceiver().getId()));
		}
		final VoteStickSQLiteAdapter voteSticksAdapter =
				new VoteStickSQLiteAdapter(this.ctx);
		voteSticksAdapter.open(this.mDatabase);
		Cursor votesticksCursor = voteSticksAdapter
					.getByStickvoteSticksInternal(result.getId(), GiveastickContract.VoteStick.ALIASED_COLS, null, null, null);
		result.setVoteSticks(voteSticksAdapter.cursorToItems(votesticksCursor));
		return result;
	}

	/**
	 * Find & read Stick by giver.
	 * @param giverId giverId
	 * @param orderBy Order by string (can be null)
	 * @return List of Stick entities
	 */
	 public Cursor getByGiver(final int giverId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.Stick.COL_GIVER + "=?";
		String idSelectionArgs = String.valueOf(giverId);
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
	 * Find & read Stick by receiver.
	 * @param receiverId receiverId
	 * @param orderBy Order by string (can be null)
	 * @return List of Stick entities
	 */
	 public Cursor getByReceiver(final int receiverId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = GiveastickContract.Stick.COL_RECEIVER + "=?";
		String idSelectionArgs = String.valueOf(receiverId);
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
	 * Read All Sticks entities.
	 *
	 * @return List of Stick entities
	 */
	public ArrayList<Stick> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<Stick> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a Stick entity into database.
	 *
	 * @param item The Stick entity to persist
	 * @return Id of the Stick entity
	 */
	public long insert(final Stick item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.Stick.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.Stick.itemToContentValues(item);
		values.remove(GiveastickContract.Stick.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					GiveastickContract.Stick.COL_ID,
					values);
		}
		item.setId((int) newid);
		if (item.getVoteSticks() != null) {
			VoteStickSQLiteAdapterBase voteSticksAdapter =
					new VoteStickSQLiteAdapter(this.ctx);
			voteSticksAdapter.open(this.mDatabase);
			for (VoteStick votestick
						: item.getVoteSticks()) {
				voteSticksAdapter.insertOrUpdateWithStickVoteSticks(
									votestick,
									newid);
			}
		}
		return newid;
	}

	/**
	 * Either insert or update a Stick entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Stick entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final Stick item) {
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
	 * Update a Stick entity into database.
	 *
	 * @param item The Stick entity to persist
	 * @return count of updated entities
	 */
	public int update(final Stick item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.Stick.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.Stick.itemToContentValues(item);
		final String whereClause =
				 GiveastickContract.Stick.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a Stick entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + GiveastickContract.Stick.TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  GiveastickContract.Stick.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param stick The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final Stick stick) {
		return this.delete(stick.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the Stick corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  GiveastickContract.Stick.ALIASED_COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.query(GiveastickContract.Stick.ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a Stick entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				GiveastickContract.Stick.ALIASED_COLS,
				GiveastickContract.Stick.ALIASED_COL_ID + " = ?",
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
				GiveastickContract.Stick.ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)});
	}

}
