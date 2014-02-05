/**************************************************************************
 * UserGroupSQLiteAdapterBase.java, giveastick Android
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
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.VoteForfeitSQLiteAdapter;
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.VoteForfeit;


import com.iia.giveastick.GiveastickApplication;


import com.iia.giveastick.provider.GiveastickContract;

/** UserGroup adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit UserGroupAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class UserGroupSQLiteAdapterBase
						extends SQLiteAdapterBase<UserGroup> {

	/** TAG for debug purpose. */
	protected static final String TAG = "UserGroupDBAdapter";


	/**
	 * Get the table name used in DB for your UserGroup entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return GiveastickContract.UserGroup.TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your UserGroup entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = GiveastickContract.UserGroup.TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the UserGroup entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return GiveastickContract.UserGroup.ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ GiveastickContract.UserGroup.TABLE_NAME	+ " ("
		
		 + GiveastickContract.UserGroup.COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
		 + GiveastickContract.UserGroup.COL_TAG	+ " VARCHAR NOT NULL"
		
		
		+ ");"
;
	}
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserGroupSQLiteAdapterBase(final Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert UserGroup entity to Content Values for database.
	 * @param item UserGroup entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final UserGroup item) {
		return GiveastickContract.UserGroup.itemToContentValues(item);
	}

	/**
	 * Convert Cursor of database to UserGroup entity.
	 * @param cursor Cursor object
	 * @return UserGroup entity
	 */
	public UserGroup cursorToItem(final Cursor cursor) {
		return GiveastickContract.UserGroup.cursorToItem(cursor);
	}

	/**
	 * Convert Cursor of database to UserGroup entity.
	 * @param cursor Cursor object
	 * @param result UserGroup entity
	 */
	public void cursorToItem(final Cursor cursor, final UserGroup result) {
		GiveastickContract.UserGroup.cursorToItem(cursor, result);
	}

	//// CRUD Entity ////
	/**
	 * Find & read UserGroup by id in database.
	 *
	 * @param id Identify of UserGroup
	 * @return UserGroup entity
	 */
	public UserGroup getByID(final int id) {
		final Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final UserGroup result = this.cursorToItem(cursor);
		cursor.close();

		final UserSQLiteAdapter usersAdapter =
				new UserSQLiteAdapter(this.ctx);
		usersAdapter.open(this.mDatabase);
		Cursor usersCursor = usersAdapter
					.getByUserGroupusersInternal(result.getId(), GiveastickContract.User.ALIASED_COLS, null, null, null);
		result.setUsers(usersAdapter.cursorToItems(usersCursor));
		final VoteForfeitSQLiteAdapter voteForfeitsAdapter =
				new VoteForfeitSQLiteAdapter(this.ctx);
		voteForfeitsAdapter.open(this.mDatabase);
		Cursor voteforfeitsCursor = voteForfeitsAdapter
					.getByUserGroupvoteForfeitsInternal(result.getId(), GiveastickContract.VoteForfeit.ALIASED_COLS, null, null, null);
		result.setVoteForfeits(voteForfeitsAdapter.cursorToItems(voteforfeitsCursor));
		return result;
	}


	/**
	 * Read All UserGroups entities.
	 *
	 * @return List of UserGroup entities
	 */
	public ArrayList<UserGroup> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<UserGroup> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a UserGroup entity into database.
	 *
	 * @param item The UserGroup entity to persist
	 * @return Id of the UserGroup entity
	 */
	public long insert(final UserGroup item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Insert DB(" + GiveastickContract.UserGroup.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.UserGroup.itemToContentValues(item);
		values.remove(GiveastickContract.UserGroup.COL_ID);
		int newid;
		if (values.size() != 0) {
			newid = (int) this.insert(
					null,
					values);
		} else {
			newid = (int) this.insert(
					GiveastickContract.UserGroup.COL_ID,
					values);
		}
		item.setId((int) newid);
		if (item.getUsers() != null) {
			UserSQLiteAdapterBase usersAdapter =
					new UserSQLiteAdapter(this.ctx);
			usersAdapter.open(this.mDatabase);
			for (User user
						: item.getUsers()) {
				usersAdapter.insertOrUpdateWithUserGroupUsers(
									user,
									newid);
			}
		}
		if (item.getVoteForfeits() != null) {
			VoteForfeitSQLiteAdapterBase voteForfeitsAdapter =
					new VoteForfeitSQLiteAdapter(this.ctx);
			voteForfeitsAdapter.open(this.mDatabase);
			for (VoteForfeit voteforfeit
						: item.getVoteForfeits()) {
				voteForfeitsAdapter.insertOrUpdateWithUserGroupVoteForfeits(
									voteforfeit,
									newid);
			}
		}
		return newid;
	}

	/**
	 * Either insert or update a UserGroup entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The UserGroup entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final UserGroup item) {
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
	 * Update a UserGroup entity into database.
	 *
	 * @param item The UserGroup entity to persist
	 * @return count of updated entities
	 */
	public int update(final UserGroup item) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Update DB(" + GiveastickContract.UserGroup.TABLE_NAME + ")");
		}

		final ContentValues values =
				GiveastickContract.UserGroup.itemToContentValues(item);
		final String whereClause =
				 GiveastickContract.UserGroup.COL_ID
				 + "=? ";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a UserGroup entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Delete DB(" + GiveastickContract.UserGroup.TABLE_NAME
					+ ") id : " + id);
		}

		
		final String whereClause =  GiveastickContract.UserGroup.COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param userGroup The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final UserGroup userGroup) {
		return this.delete(userGroup.getId());
	}

	/**
	 *  Internal Cursor.
	 * @param id id
	 *  @return A Cursor pointing to the UserGroup corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(final int id) {
		if (GiveastickApplication.DEBUG) {
			Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =  GiveastickContract.UserGroup.ALIASED_COL_ID
					 + "=? ";
		final String[] whereArgs = new String[] {String.valueOf(id) };

		return this.query(GiveastickContract.UserGroup.ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a UserGroup entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		return this.query(
				GiveastickContract.UserGroup.ALIASED_COLS,
				GiveastickContract.UserGroup.ALIASED_COL_ID + " = ?",
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
				GiveastickContract.UserGroup.ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)});
	}

}
