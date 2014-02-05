/**************************************************************************
 * UserGroupProviderUtilsBase.java, giveastick Android
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

import com.iia.giveastick.criterias.UserGroupCriterias;
import com.iia.giveastick.criterias.UserCriterias;
import com.iia.giveastick.criterias.VoteForfeitCriterias;
import com.iia.giveastick.criterias.base.Criteria;
import com.iia.giveastick.criterias.base.Criteria.Type;
import com.iia.giveastick.criterias.base.value.ArrayValue;
import com.iia.giveastick.criterias.base.CriteriasBase;
import com.iia.giveastick.criterias.base.CriteriasBase.GroupType;
import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.VoteForfeitSQLiteAdapter;

import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.VoteForfeit;

import com.iia.giveastick.provider.UserGroupProviderAdapter;
import com.iia.giveastick.provider.UserProviderAdapter;
import com.iia.giveastick.provider.VoteForfeitProviderAdapter;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;

/**
 * UserGroup Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class UserGroupProviderUtilsBase
			extends ProviderUtilsBase<UserGroup> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "UserGroupProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public UserGroupProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final UserGroup item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		ContentValues itemValues = GiveastickContract.UserGroup.itemToContentValues(item);
		itemValues.remove(GiveastickContract.UserGroup.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				UserGroupProviderAdapter.USERGROUP_URI)
						.withValues(itemValues)
						.build());

		if (item.getUsers() != null && item.getUsers().size() > 0) {
			String usersSelection = GiveastickContract.User.COL_ID + " IN (";
			String[] usersSelectionArgs = new String[item.getUsers().size()];
			for (int i = 0; i < item.getUsers().size(); i++) {
				usersSelectionArgs[i] = String.valueOf(item.getUsers().get(i).getId());
				usersSelection += "? ";
				if (i != item.getUsers().size() - 1) {
					 usersSelection += ", ";
				}
			}
			usersSelection += ")";

			operations.add(ContentProviderOperation.newUpdate(UserProviderAdapter.USER_URI)
					.withValueBackReference(
							GiveastickContract.User
									.COL_USERGROUPUSERSINTERNAL,
							0)
					.withSelection(usersSelection, usersSelectionArgs)
					.build());
		}
		if (item.getVoteForfeits() != null && item.getVoteForfeits().size() > 0) {
			String voteForfeitsSelection = GiveastickContract.VoteForfeit.COL_ID + " IN (";
			String[] voteForfeitsSelectionArgs = new String[item.getVoteForfeits().size()];
			for (int i = 0; i < item.getVoteForfeits().size(); i++) {
				voteForfeitsSelectionArgs[i] = String.valueOf(item.getVoteForfeits().get(i).getId());
				voteForfeitsSelection += "? ";
				if (i != item.getVoteForfeits().size() - 1) {
					 voteForfeitsSelection += ", ";
				}
			}
			voteForfeitsSelection += ")";

			operations.add(ContentProviderOperation.newUpdate(VoteForfeitProviderAdapter.VOTEFORFEIT_URI)
					.withValueBackReference(
							GiveastickContract.VoteForfeit
									.COL_USERGROUPVOTEFORFEITSINTERNAL,
							0)
					.withSelection(voteForfeitsSelection, voteForfeitsSelectionArgs)
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
	 * @param item UserGroup
	 * @return number of row affected
	 */
	public int delete(final UserGroup item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				UserGroupProviderAdapter.USERGROUP_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return UserGroup
	 */
	public UserGroup query(final int id) {
		UserGroup result = null;
		ContentResolver prov = this.getContext().getContentResolver();

		UserGroupCriterias crits =
				new UserGroupCriterias(GroupType.AND);
		crits.add(GiveastickContract.UserGroup.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			UserGroupProviderAdapter.USERGROUP_URI,
			GiveastickContract.UserGroup.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = GiveastickContract.UserGroup.cursorToItem(cursor);
			cursor.close();

			result.setUsers(
				this.getAssociateUsers(result));
			result.setVoteForfeits(
				this.getAssociateVoteForfeits(result));
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<UserGroup>
	 */
	public ArrayList<UserGroup> queryAll() {
		ArrayList<UserGroup> result =
					new ArrayList<UserGroup>();
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				UserGroupProviderAdapter.USERGROUP_URI,
				GiveastickContract.UserGroup.ALIASED_COLS,
				null,
				null,
				null);

		result = GiveastickContract.UserGroup.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<UserGroup>
	 */
	public ArrayList<UserGroup> query(
				CriteriasBase<UserGroup> criteria) {
		ArrayList<UserGroup> result =
					new ArrayList<UserGroup>();
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				UserGroupProviderAdapter.USERGROUP_URI,
				GiveastickContract.UserGroup.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = GiveastickContract.UserGroup.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item UserGroup
	 
	 * @return number of rows updated
	 */
	public int update(final UserGroup item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = GiveastickContract.UserGroup.itemToContentValues(
				item);

		Uri uri = Uri.withAppendedPath(
				UserGroupProviderAdapter.USERGROUP_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		if (item.getUsers() != null && item.getUsers().size() > 0) {
			// Set new users for UserGroup
			UserCriterias usersCrit =
						new UserCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.User.COL_ID);
			crit.addValue(values);
			usersCrit.add(crit);


			for (int i = 0; i < item.getUsers().size(); i++) {
				values.addValue(String.valueOf(
						item.getUsers().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					UserProviderAdapter.USER_URI)
						.withValue(
								GiveastickContract.User
										.COL_USERGROUPUSERSINTERNAL,
								item.getId())
					.withSelection(
							usersCrit.toSQLiteSelection(),
							usersCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated users
			crit.setType(Type.NOT_IN);
			usersCrit.add(GiveastickContract.User.COL_USERGROUPUSERSINTERNAL,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					UserProviderAdapter.USER_URI)
						.withValue(
								GiveastickContract.User
										.COL_USERGROUPUSERSINTERNAL,
								null)
					.withSelection(
							usersCrit.toSQLiteSelection(),
							usersCrit.toSQLiteSelectionArgs())
					.build());
		}

		if (item.getVoteForfeits() != null && item.getVoteForfeits().size() > 0) {
			// Set new voteForfeits for UserGroup
			VoteForfeitCriterias voteForfeitsCrit =
						new VoteForfeitCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.VoteForfeit.COL_ID);
			crit.addValue(values);
			voteForfeitsCrit.add(crit);


			for (int i = 0; i < item.getVoteForfeits().size(); i++) {
				values.addValue(String.valueOf(
						item.getVoteForfeits().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					VoteForfeitProviderAdapter.VOTEFORFEIT_URI)
						.withValue(
								GiveastickContract.VoteForfeit
										.COL_USERGROUPVOTEFORFEITSINTERNAL,
								item.getId())
					.withSelection(
							voteForfeitsCrit.toSQLiteSelection(),
							voteForfeitsCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated voteForfeits
			crit.setType(Type.NOT_IN);
			voteForfeitsCrit.add(GiveastickContract.VoteForfeit.COL_USERGROUPVOTEFORFEITSINTERNAL,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					VoteForfeitProviderAdapter.VOTEFORFEIT_URI)
						.withValue(
								GiveastickContract.VoteForfeit
										.COL_USERGROUPVOTEFORFEITSINTERNAL,
								null)
					.withSelection(
							voteForfeitsCrit.toSQLiteSelection(),
							voteForfeitsCrit.toSQLiteSelectionArgs())
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
	 * Get associate Users.
	 * @param item UserGroup
	 * @return User
	 */
	public ArrayList<User> getAssociateUsers(
			final UserGroup item) {
		ArrayList<User> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor userCursor = prov.query(
				UserProviderAdapter.USER_URI,
				GiveastickContract.User.ALIASED_COLS,
				GiveastickContract.User.COL_USERGROUPUSERSINTERNAL
						+ "= ?",
				new String[]{String.valueOf(item.getId())},
				null);

		result = GiveastickContract.User.cursorToItems(
						userCursor);
		userCursor.close();

		return result;
	}

	/**
	 * Get associate VoteForfeits.
	 * @param item UserGroup
	 * @return VoteForfeit
	 */
	public ArrayList<VoteForfeit> getAssociateVoteForfeits(
			final UserGroup item) {
		ArrayList<VoteForfeit> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor voteForfeitCursor = prov.query(
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
				GiveastickContract.VoteForfeit.ALIASED_COLS,
				GiveastickContract.VoteForfeit.COL_USERGROUPVOTEFORFEITSINTERNAL
						+ "= ?",
				new String[]{String.valueOf(item.getId())},
				null);

		result = GiveastickContract.VoteForfeit.cursorToItems(
						voteForfeitCursor);
		voteForfeitCursor.close();

		return result;
	}

}
