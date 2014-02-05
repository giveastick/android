/**************************************************************************
 * StickProviderUtilsBase.java, giveastick Android
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

import com.iia.giveastick.criterias.StickCriterias;
import com.iia.giveastick.criterias.UserCriterias;
import com.iia.giveastick.criterias.VoteStickCriterias;
import com.iia.giveastick.criterias.base.Criteria;
import com.iia.giveastick.criterias.base.Criteria.Type;
import com.iia.giveastick.criterias.base.value.ArrayValue;
import com.iia.giveastick.criterias.base.CriteriasBase;
import com.iia.giveastick.criterias.base.CriteriasBase.GroupType;
import com.iia.giveastick.data.StickSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;

import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.VoteStick;

import com.iia.giveastick.provider.StickProviderAdapter;
import com.iia.giveastick.provider.UserProviderAdapter;
import com.iia.giveastick.provider.VoteStickProviderAdapter;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;

/**
 * Stick Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class StickProviderUtilsBase
			extends ProviderUtilsBase<Stick> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "StickProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public StickProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final Stick item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		ContentValues itemValues = GiveastickContract.Stick.itemToContentValues(item);
		itemValues.remove(GiveastickContract.Stick.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				StickProviderAdapter.STICK_URI)
						.withValues(itemValues)
						.build());

		if (item.getVoteSticks() != null && item.getVoteSticks().size() > 0) {
			String voteSticksSelection = GiveastickContract.VoteStick.COL_ID + " IN (";
			String[] voteSticksSelectionArgs = new String[item.getVoteSticks().size()];
			for (int i = 0; i < item.getVoteSticks().size(); i++) {
				voteSticksSelectionArgs[i] = String.valueOf(item.getVoteSticks().get(i).getId());
				voteSticksSelection += "? ";
				if (i != item.getVoteSticks().size() - 1) {
					 voteSticksSelection += ", ";
				}
			}
			voteSticksSelection += ")";

			operations.add(ContentProviderOperation.newUpdate(VoteStickProviderAdapter.VOTESTICK_URI)
					.withValueBackReference(
							GiveastickContract.VoteStick
									.COL_STICKVOTESTICKSINTERNAL,
							0)
					.withSelection(voteSticksSelection, voteSticksSelectionArgs)
					.build());
		}

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
	 * @param item Stick
	 * @return number of row affected
	 */
	public int delete(final Stick item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				StickProviderAdapter.STICK_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return Stick
	 */
	public Stick query(final int id) {
		Stick result = null;
		ContentResolver prov = this.getContext().getContentResolver();

		StickCriterias crits =
				new StickCriterias(GroupType.AND);
		crits.add(GiveastickContract.Stick.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			StickProviderAdapter.STICK_URI,
			GiveastickContract.Stick.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = GiveastickContract.Stick.cursorToItem(cursor);
			cursor.close();

			if (result.getGiver() != null) {
				result.setGiver(
					this.getAssociateGiver(result));
			}
			if (result.getReceiver() != null) {
				result.setReceiver(
					this.getAssociateReceiver(result));
			}
			result.setVoteSticks(
				this.getAssociateVoteSticks(result));
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<Stick>
	 */
	public ArrayList<Stick> queryAll() {
		ArrayList<Stick> result =
					new ArrayList<Stick>();
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				StickProviderAdapter.STICK_URI,
				GiveastickContract.Stick.ALIASED_COLS,
				null,
				null,
				null);

		result = GiveastickContract.Stick.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<Stick>
	 */
	public ArrayList<Stick> query(
				CriteriasBase<Stick> criteria) {
		ArrayList<Stick> result =
					new ArrayList<Stick>();
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				StickProviderAdapter.STICK_URI,
				GiveastickContract.Stick.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = GiveastickContract.Stick.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item Stick
	 
	 * @return number of rows updated
	 */
	public int update(final Stick item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = GiveastickContract.Stick.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				StickProviderAdapter.STICK_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		if (item.getVoteSticks() != null && item.getVoteSticks().size() > 0) {
			// Set new voteSticks for Stick
			VoteStickCriterias voteSticksCrit =
						new VoteStickCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.VoteStick.COL_ID);
			crit.addValue(values);
			voteSticksCrit.add(crit);


			for (int i = 0; i < item.getVoteSticks().size(); i++) {
				values.addValue(String.valueOf(
						item.getVoteSticks().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					VoteStickProviderAdapter.VOTESTICK_URI)
						.withValue(
								GiveastickContract.VoteStick
										.COL_STICKVOTESTICKSINTERNAL,
								item.getId())
					.withSelection(
							voteSticksCrit.toSQLiteSelection(),
							voteSticksCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated voteSticks
			crit.setType(Type.NOT_IN);
			voteSticksCrit.add(GiveastickContract.VoteStick.COL_STICKVOTESTICKSINTERNAL,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					VoteStickProviderAdapter.VOTESTICK_URI)
						.withValue(
								GiveastickContract.VoteStick
										.COL_STICKVOTESTICKSINTERNAL,
								null)
					.withSelection(
							voteSticksCrit.toSQLiteSelection(),
							voteSticksCrit.toSQLiteSelectionArgs())
					.build());
		}


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
	 * Get associate Giver.
	 * @param item Stick
	 * @return User
	 */
	public User getAssociateGiver(
			final Stick item) {
		User result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor userCursor = prov.query(
				UserProviderAdapter.USER_URI,
				GiveastickContract.User.ALIASED_COLS,
				GiveastickContract.User.COL_ID + "= ?",
				new String[]{String.valueOf(item.getGiver().getId())},
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
	 * Get associate Receiver.
	 * @param item Stick
	 * @return User
	 */
	public User getAssociateReceiver(
			final Stick item) {
		User result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor userCursor = prov.query(
				UserProviderAdapter.USER_URI,
				GiveastickContract.User.ALIASED_COLS,
				GiveastickContract.User.COL_ID + "= ?",
				new String[]{String.valueOf(item.getReceiver().getId())},
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
	 * Get associate VoteSticks.
	 * @param item Stick
	 * @return VoteStick
	 */
	public ArrayList<VoteStick> getAssociateVoteSticks(
			final Stick item) {
		ArrayList<VoteStick> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor voteStickCursor = prov.query(
				VoteStickProviderAdapter.VOTESTICK_URI,
				GiveastickContract.VoteStick.ALIASED_COLS,
				GiveastickContract.VoteStick.COL_STICKVOTESTICKSINTERNAL
						+ "= ?",
				new String[]{String.valueOf(item.getId())},
				null);

		result = GiveastickContract.VoteStick.cursorToItems(
						voteStickCursor);
		voteStickCursor.close();

		return result;
	}

}
