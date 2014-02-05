/**************************************************************************
 * VoteForfeitProviderUtilsBase.java, giveastick Android
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

import com.iia.giveastick.criterias.VoteForfeitCriterias;
import com.iia.giveastick.criterias.UserGroupCriterias;
import com.iia.giveastick.criterias.base.CriteriasBase;
import com.iia.giveastick.criterias.base.CriteriasBase.GroupType;
import com.iia.giveastick.data.VoteForfeitSQLiteAdapter;
import com.iia.giveastick.data.UserGroupSQLiteAdapter;

import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.entity.UserGroup;

import com.iia.giveastick.provider.VoteForfeitProviderAdapter;
import com.iia.giveastick.provider.UserGroupProviderAdapter;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;

/**
 * VoteForfeit Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class VoteForfeitProviderUtilsBase
			extends ProviderUtilsBase<VoteForfeit> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "VoteForfeitProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public VoteForfeitProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final VoteForfeit item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		ContentValues itemValues = GiveastickContract.VoteForfeit.itemToContentValues(item);
		itemValues.remove(GiveastickContract.VoteForfeit.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI)
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
	 * @param item VoteForfeit to insert
	 * @param userGroupvoteForfeitsInternalId userGroupvoteForfeitsInternal Id
	 * @return number of rows affected
	 */
	public Uri insert(final VoteForfeit item,
							 final int userGroupvoteForfeitsInternalId) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		ContentValues itemValues = GiveastickContract.VoteForfeit.itemToContentValues(item,
					userGroupvoteForfeitsInternalId);
		itemValues.remove(GiveastickContract.VoteForfeit.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI)
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
	 * @param item VoteForfeit
	 * @return number of row affected
	 */
	public int delete(final VoteForfeit item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return VoteForfeit
	 */
	public VoteForfeit query(final int id) {
		VoteForfeit result = null;
		ContentResolver prov = this.getContext().getContentResolver();

		VoteForfeitCriterias crits =
				new VoteForfeitCriterias(GroupType.AND);
		crits.add(GiveastickContract.VoteForfeit.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
			GiveastickContract.VoteForfeit.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = GiveastickContract.VoteForfeit.cursorToItem(cursor);
			cursor.close();

			if (result.getUserGroup() != null) {
				result.setUserGroup(
					this.getAssociateUserGroup(result));
			}
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<VoteForfeit>
	 */
	public ArrayList<VoteForfeit> queryAll() {
		ArrayList<VoteForfeit> result =
					new ArrayList<VoteForfeit>();
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
				GiveastickContract.VoteForfeit.ALIASED_COLS,
				null,
				null,
				null);

		result = GiveastickContract.VoteForfeit.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<VoteForfeit>
	 */
	public ArrayList<VoteForfeit> query(
				CriteriasBase<VoteForfeit> criteria) {
		ArrayList<VoteForfeit> result =
					new ArrayList<VoteForfeit>();
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
				GiveastickContract.VoteForfeit.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = GiveastickContract.VoteForfeit.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item VoteForfeit
	 * @return number of rows updated
	 */
	public int update(final VoteForfeit item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = GiveastickContract.VoteForfeit.itemToContentValues(item);

		Uri uri = Uri.withAppendedPath(
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
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
	 * @param item VoteForfeit
	 * @param userGroupvoteForfeitsInternalId userGroupvoteForfeitsInternal Id
	 * @return number of rows updated
	 */
	public int update(final VoteForfeit item,
							 final int userGroupvoteForfeitsInternalId) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = GiveastickContract.VoteForfeit.itemToContentValues(
				item,
				userGroupvoteForfeitsInternalId);

		Uri uri = Uri.withAppendedPath(
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
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
	 * Get associate UserGroup.
	 * @param item VoteForfeit
	 * @return UserGroup
	 */
	public UserGroup getAssociateUserGroup(
			final VoteForfeit item) {
		UserGroup result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor userGroupCursor = prov.query(
				UserGroupProviderAdapter.USERGROUP_URI,
				GiveastickContract.UserGroup.ALIASED_COLS,
				GiveastickContract.UserGroup.COL_ID + "= ?",
				new String[]{String.valueOf(item.getUserGroup().getId())},
				null);

		if (userGroupCursor.getCount() > 0) {
			userGroupCursor.moveToFirst();
			result = GiveastickContract.UserGroup.cursorToItem(userGroupCursor);
		} else {
			result = null;
		}
		userGroupCursor.close();

		return result;
	}

}
