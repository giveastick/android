/**************************************************************************
 * VoteStickProviderUtilsBase.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.iia.giveastick.criterias.VoteStickCriterias;
import com.iia.giveastick.criterias.StickCriterias;
import com.iia.giveastick.criterias.UserCriterias;
import com.iia.giveastick.criterias.base.CriteriasBase;
import com.iia.giveastick.criterias.base.CriteriasBase.GroupType;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.StickSQLiteAdapter;

import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.Stick;

import com.iia.giveastick.provider.VoteStickProviderAdapter;
import com.iia.giveastick.provider.UserProviderAdapter;
import com.iia.giveastick.provider.StickProviderAdapter;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;

/**
 * VoteStick Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class VoteStickProviderUtilsBase
			extends ProviderUtilsBase<VoteStick> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "VoteStickProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public VoteStickProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final VoteStick item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		ContentValues itemValues = GiveastickContract.VoteStick.itemToContentValues(item);
		itemValues.remove(GiveastickContract.VoteStick.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				VoteStickProviderAdapter.VOTESTICK_URI)
						.withValues(itemValues)
						.build());


		try {
			ContentProviderResult[] results = 
					prov.applyBatch(GiveastickProvider.authority, operations);
			if (results[0] != null) {
				result = results[0].uri;
			}
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/**
	 * Insert into DB.
	 * @param item VoteStick to insert
	 * @param stickvoteSticksInternalId stickvoteSticksInternal Id* @param uservoteSticksInternalId uservoteSticksInternal Id
	 * @return number of rows affected
	 */
	public Uri insert(final VoteStick item,
							 final int stickvoteSticksInternalId,
							 final int uservoteSticksInternalId) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		ContentValues itemValues = GiveastickContract.VoteStick.itemToContentValues(item,
					stickvoteSticksInternalId,
					uservoteSticksInternalId);
		itemValues.remove(GiveastickContract.VoteStick.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				VoteStickProviderAdapter.VOTESTICK_URI)
			    	.withValues(itemValues)
			    	.build());



		try {
			ContentProviderResult[] results =
				prov.applyBatch(GiveastickProvider.authority, operations);
			if (results[0] != null) {
				result = results[0].uri;
			}
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/**
	 * Delete from DB.
	 * @param item VoteStick
	 * @return number of row affected
	 */
	public int delete(final VoteStick item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				VoteStickProviderAdapter.VOTESTICK_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return VoteStick
	 */
	public VoteStick query(final int id) {
		VoteStick result = null;
		ContentResolver prov = this.getContext().getContentResolver();

		VoteStickCriterias crits =
				new VoteStickCriterias(GroupType.AND);
		crits.add(GiveastickContract.VoteStick.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			VoteStickProviderAdapter.VOTESTICK_URI,
			GiveastickContract.VoteStick.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = GiveastickContract.VoteStick.cursorToItem(cursor);
			cursor.close();

			if (result.getUser() != null) {
				result.setUser(
					this.getAssociateUser(result));
			}
			if (result.getStick() != null) {
				result.setStick(
					this.getAssociateStick(result));
			}
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<VoteStick>
	 */
	public ArrayList<VoteStick> queryAll() {
		ArrayList<VoteStick> result =
					new ArrayList<VoteStick>();
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				VoteStickProviderAdapter.VOTESTICK_URI,
				GiveastickContract.VoteStick.ALIASED_COLS,
				null,
				null,
				null);

		result = GiveastickContract.VoteStick.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<VoteStick>
	 */
	public ArrayList<VoteStick> query(
				CriteriasBase<VoteStick> criteria) {
		ArrayList<VoteStick> result =
					new ArrayList<VoteStick>();
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				VoteStickProviderAdapter.VOTESTICK_URI,
				GiveastickContract.VoteStick.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = GiveastickContract.VoteStick.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item VoteStick
	 * @return number of rows updated
	 */
	public int update(final VoteStick item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = GiveastickContract.VoteStick.itemToContentValues(item);

		Uri uri = Uri.withAppendedPath(
				VoteStickProviderAdapter.VOTESTICK_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		try {
			ContentProviderResult[] results = prov.applyBatch(GiveastickProvider.authority, operations);
			result = results[0].count;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item VoteStick
	 * @param stickvoteSticksInternalId stickvoteSticksInternal Id* @param uservoteSticksInternalId uservoteSticksInternal Id
	 * @return number of rows updated
	 */
	public int update(final VoteStick item,
							 final int stickvoteSticksInternalId,
							 final int uservoteSticksInternalId) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = GiveastickContract.VoteStick.itemToContentValues(
				item,
				stickvoteSticksInternalId,
				uservoteSticksInternalId);

		Uri uri = Uri.withAppendedPath(
				VoteStickProviderAdapter.VOTESTICK_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());



		try {
			ContentProviderResult[] results = prov.applyBatch(GiveastickProvider.authority, operations);
			result = results[0].count;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/** Relations operations. */
	/**
	 * Get associate User.
	 * @param item VoteStick
	 * @return User
	 */
	public User getAssociateUser(
			final VoteStick item) {
		User result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor userCursor = prov.query(
				UserProviderAdapter.USER_URI,
				GiveastickContract.User.ALIASED_COLS,
				GiveastickContract.User.COL_ID + "= ?",
				new String[]{String.valueOf(item.getUser().getId())},
				null);

		if (userCursor.getCount() > 0) {
			userCursor.moveToFirst();
			result = GiveastickContract.User.cursorToItem(userCursor);
		} else {
			result = null;
		}
		userCursor.close();

		return result;
	}

	/**
	 * Get associate Stick.
	 * @param item VoteStick
	 * @return Stick
	 */
	public Stick getAssociateStick(
			final VoteStick item) {
		Stick result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor stickCursor = prov.query(
				StickProviderAdapter.STICK_URI,
				GiveastickContract.Stick.ALIASED_COLS,
				GiveastickContract.Stick.COL_ID + "= ?",
				new String[]{String.valueOf(item.getStick().getId())},
				null);

		if (stickCursor.getCount() > 0) {
			stickCursor.moveToFirst();
			result = GiveastickContract.Stick.cursorToItem(stickCursor);
		} else {
			result = null;
		}
		stickCursor.close();

		return result;
	}

}
