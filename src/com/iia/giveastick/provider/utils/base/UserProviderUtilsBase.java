/**************************************************************************
 * UserProviderUtilsBase.java, giveastick Android
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

import com.iia.giveastick.criterias.UserCriterias;
import com.iia.giveastick.criterias.UserGroupCriterias;
import com.iia.giveastick.criterias.VoteStickCriterias;
import com.iia.giveastick.criterias.StickCriterias;
import com.iia.giveastick.criterias.base.Criteria;
import com.iia.giveastick.criterias.base.Criteria.Type;
import com.iia.giveastick.criterias.base.value.ArrayValue;
import com.iia.giveastick.criterias.base.CriteriasBase;
import com.iia.giveastick.criterias.base.CriteriasBase.GroupType;
import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;
import com.iia.giveastick.data.StickSQLiteAdapter;

import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.entity.Stick;

import com.iia.giveastick.provider.UserProviderAdapter;
import com.iia.giveastick.provider.UserGroupProviderAdapter;
import com.iia.giveastick.provider.VoteStickProviderAdapter;
import com.iia.giveastick.provider.StickProviderAdapter;
import com.iia.giveastick.provider.GiveastickProvider;
import com.iia.giveastick.provider.GiveastickContract;

/**
 * User Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class UserProviderUtilsBase
			extends ProviderUtilsBase<User> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "UserProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public UserProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final User item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		ContentValues itemValues = GiveastickContract.User.itemToContentValues(item);
		itemValues.remove(GiveastickContract.User.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				UserProviderAdapter.USER_URI)
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
									.COL_USERVOTESTICKSINTERNAL,
							0)
					.withSelection(voteSticksSelection, voteSticksSelectionArgs)
					.build());
		}
		if (item.getGivenSticks() != null && item.getGivenSticks().size() > 0) {
			String givenSticksSelection = GiveastickContract.Stick.COL_ID + " IN (";
			String[] givenSticksSelectionArgs = new String[item.getGivenSticks().size()];
			for (int i = 0; i < item.getGivenSticks().size(); i++) {
				givenSticksSelectionArgs[i] = String.valueOf(item.getGivenSticks().get(i).getId());
				givenSticksSelection += "? ";
				if (i != item.getGivenSticks().size() - 1) {
					 givenSticksSelection += ", ";
				}
			}
			givenSticksSelection += ")";

			operations.add(ContentProviderOperation.newUpdate(StickProviderAdapter.STICK_URI)
					.withValueBackReference(
							GiveastickContract.Stick
									.COL_GIVER,
							0)
					.withSelection(givenSticksSelection, givenSticksSelectionArgs)
					.build());
		}
		if (item.getReceivedSticks() != null && item.getReceivedSticks().size() > 0) {
			String receivedSticksSelection = GiveastickContract.Stick.COL_ID + " IN (";
			String[] receivedSticksSelectionArgs = new String[item.getReceivedSticks().size()];
			for (int i = 0; i < item.getReceivedSticks().size(); i++) {
				receivedSticksSelectionArgs[i] = String.valueOf(item.getReceivedSticks().get(i).getId());
				receivedSticksSelection += "? ";
				if (i != item.getReceivedSticks().size() - 1) {
					 receivedSticksSelection += ", ";
				}
			}
			receivedSticksSelection += ")";

			operations.add(ContentProviderOperation.newUpdate(StickProviderAdapter.STICK_URI)
					.withValueBackReference(
							GiveastickContract.Stick
									.COL_RECEIVER,
							0)
					.withSelection(receivedSticksSelection, receivedSticksSelectionArgs)
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
	 * Insert into DB.
	 * @param item User to insert
	 * @param userGroupusersInternalId userGroupusersInternal Id
	 * @return number of rows affected
	 */
	public Uri insert(final User item,
							 final int userGroupusersInternalId) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		ContentValues itemValues = GiveastickContract.User.itemToContentValues(item,
					userGroupusersInternalId);
		itemValues.remove(GiveastickContract.User.COL_ID);

		operations.add(ContentProviderOperation.newInsert(
				UserProviderAdapter.USER_URI)
			    	.withValues(itemValues)
			    	.build());


		if (item.getVoteSticks() != null && item.getVoteSticks().size() > 0) {
			UserCriterias userCrit =
						new UserCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.VoteStick.COL_ID);
			crit.addValue(values);
			userCrit.add(crit);


			for (int i = 0; i < item.getVoteSticks().size(); i++) {
				values.addValue(String.valueOf(
						item.getVoteSticks().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					VoteStickProviderAdapter.VOTESTICK_URI)
						.withValueBackReference(
								GiveastickContract.VoteStick
										.COL_USERVOTESTICKSINTERNAL,
								0)
					.withSelection(
							userCrit.toSQLiteSelection(),
							userCrit.toSQLiteSelectionArgs())
					.build());
		}
		if (item.getGivenSticks() != null && item.getGivenSticks().size() > 0) {
			UserCriterias userCrit =
						new UserCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.Stick.COL_ID);
			crit.addValue(values);
			userCrit.add(crit);


			for (int i = 0; i < item.getGivenSticks().size(); i++) {
				values.addValue(String.valueOf(
						item.getGivenSticks().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValueBackReference(
								GiveastickContract.Stick
										.COL_GIVER,
								0)
					.withSelection(
							userCrit.toSQLiteSelection(),
							userCrit.toSQLiteSelectionArgs())
					.build());
		}
		if (item.getReceivedSticks() != null && item.getReceivedSticks().size() > 0) {
			UserCriterias userCrit =
						new UserCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.Stick.COL_ID);
			crit.addValue(values);
			userCrit.add(crit);


			for (int i = 0; i < item.getReceivedSticks().size(); i++) {
				values.addValue(String.valueOf(
						item.getReceivedSticks().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValueBackReference(
								GiveastickContract.Stick
										.COL_RECEIVER,
								0)
					.withSelection(
							userCrit.toSQLiteSelection(),
							userCrit.toSQLiteSelectionArgs())
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
	 * @param item User
	 * @return number of row affected
	 */
	public int delete(final User item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				UserProviderAdapter.USER_URI,
				String.valueOf(item.getId()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return User
	 */
	public User query(final int id) {
		User result = null;
		ContentResolver prov = this.getContext().getContentResolver();

		UserCriterias crits =
				new UserCriterias(GroupType.AND);
		crits.add(GiveastickContract.User.ALIASED_COL_ID,
					String.valueOf(id));

		Cursor cursor = prov.query(
			UserProviderAdapter.USER_URI,
			GiveastickContract.User.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = GiveastickContract.User.cursorToItem(cursor);
			cursor.close();

			if (result.getUserGroup() != null) {
				result.setUserGroup(
					this.getAssociateUserGroup(result));
			}
			result.setVoteSticks(
				this.getAssociateVoteSticks(result));
			result.setGivenSticks(
				this.getAssociateGivenSticks(result));
			result.setReceivedSticks(
				this.getAssociateReceivedSticks(result));
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<User>
	 */
	public ArrayList<User> queryAll() {
		ArrayList<User> result =
					new ArrayList<User>();
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				UserProviderAdapter.USER_URI,
				GiveastickContract.User.ALIASED_COLS,
				null,
				null,
				null);

		result = GiveastickContract.User.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<User>
	 */
	public ArrayList<User> query(
				CriteriasBase<User> criteria) {
		ArrayList<User> result =
					new ArrayList<User>();
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				UserProviderAdapter.USER_URI,
				GiveastickContract.User.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = GiveastickContract.User.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Updates the DB.
	 * @param item User
	 * @return number of rows updated
	 */
	public int update(final User item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = GiveastickContract.User.itemToContentValues(item);

		Uri uri = Uri.withAppendedPath(
				UserProviderAdapter.USER_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());

		if (item.getVoteSticks() != null && item.getVoteSticks().size() > 0) {
			// Set new voteSticks for User
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
										.COL_USERVOTESTICKSINTERNAL,
								item.getId())
					.withSelection(
							voteSticksCrit.toSQLiteSelection(),
							voteSticksCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated voteSticks
			crit.setType(Type.NOT_IN);
			voteSticksCrit.add(GiveastickContract.VoteStick.COL_USERVOTESTICKSINTERNAL,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					VoteStickProviderAdapter.VOTESTICK_URI)
						.withValue(
								GiveastickContract.VoteStick
										.COL_USERVOTESTICKSINTERNAL,
								null)
					.withSelection(
							voteSticksCrit.toSQLiteSelection(),
							voteSticksCrit.toSQLiteSelectionArgs())
					.build());
		}

		if (item.getGivenSticks() != null && item.getGivenSticks().size() > 0) {
			// Set new givenSticks for User
			StickCriterias givenSticksCrit =
						new StickCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.Stick.COL_ID);
			crit.addValue(values);
			givenSticksCrit.add(crit);


			for (int i = 0; i < item.getGivenSticks().size(); i++) {
				values.addValue(String.valueOf(
						item.getGivenSticks().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValue(
								GiveastickContract.Stick
										.COL_GIVER,
								item.getId())
					.withSelection(
							givenSticksCrit.toSQLiteSelection(),
							givenSticksCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated givenSticks
			crit.setType(Type.NOT_IN);
			givenSticksCrit.add(GiveastickContract.Stick.COL_GIVER,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValue(
								GiveastickContract.Stick
										.COL_GIVER,
								null)
					.withSelection(
							givenSticksCrit.toSQLiteSelection(),
							givenSticksCrit.toSQLiteSelectionArgs())
					.build());
		}

		if (item.getReceivedSticks() != null && item.getReceivedSticks().size() > 0) {
			// Set new receivedSticks for User
			StickCriterias receivedSticksCrit =
						new StickCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.Stick.COL_ID);
			crit.addValue(values);
			receivedSticksCrit.add(crit);


			for (int i = 0; i < item.getReceivedSticks().size(); i++) {
				values.addValue(String.valueOf(
						item.getReceivedSticks().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValue(
								GiveastickContract.Stick
										.COL_RECEIVER,
								item.getId())
					.withSelection(
							receivedSticksCrit.toSQLiteSelection(),
							receivedSticksCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated receivedSticks
			crit.setType(Type.NOT_IN);
			receivedSticksCrit.add(GiveastickContract.Stick.COL_RECEIVER,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValue(
								GiveastickContract.Stick
										.COL_RECEIVER,
								null)
					.withSelection(
							receivedSticksCrit.toSQLiteSelection(),
							receivedSticksCrit.toSQLiteSelectionArgs())
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

	/**
	 * Updates the DB.
	 * @param item User
	 * @param userGroupusersInternalId userGroupusersInternal Id
	 * @return number of rows updated
	 */
	public int update(final User item,
							 final int userGroupusersInternalId) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = GiveastickContract.User.itemToContentValues(
				item,
				userGroupusersInternalId);

		Uri uri = Uri.withAppendedPath(
				UserProviderAdapter.USER_URI,
				String.valueOf(item.getId()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		if (item.getVoteSticks() != null && item.getVoteSticks().size() > 0) {
			// Set new voteSticks for User
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
										.COL_USERVOTESTICKSINTERNAL,
								item.getId())
					.withSelection(
							voteSticksCrit.toSQLiteSelection(),
							voteSticksCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated voteSticks
			crit.setType(Type.NOT_IN);
			voteSticksCrit.add(GiveastickContract.VoteStick.COL_USERVOTESTICKSINTERNAL,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					VoteStickProviderAdapter.VOTESTICK_URI)
						.withValue(
								GiveastickContract.VoteStick
										.COL_USERVOTESTICKSINTERNAL,
								null)
					.withSelection(
							voteSticksCrit.toSQLiteSelection(),
							voteSticksCrit.toSQLiteSelectionArgs())
					.build());
		}

		if (item.getGivenSticks() != null && item.getGivenSticks().size() > 0) {
			// Set new givenSticks for User
			StickCriterias givenSticksCrit =
						new StickCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.Stick.COL_ID);
			crit.addValue(values);
			givenSticksCrit.add(crit);


			for (int i = 0; i < item.getGivenSticks().size(); i++) {
				values.addValue(String.valueOf(
						item.getGivenSticks().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValue(
								GiveastickContract.Stick
										.COL_GIVER,
								item.getId())
					.withSelection(
							givenSticksCrit.toSQLiteSelection(),
							givenSticksCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated givenSticks
			crit.setType(Type.NOT_IN);
			givenSticksCrit.add(GiveastickContract.Stick.COL_GIVER,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValue(
								GiveastickContract.Stick
										.COL_GIVER,
								null)
					.withSelection(
							givenSticksCrit.toSQLiteSelection(),
							givenSticksCrit.toSQLiteSelectionArgs())
					.build());
		}

		if (item.getReceivedSticks() != null && item.getReceivedSticks().size() > 0) {
			// Set new receivedSticks for User
			StickCriterias receivedSticksCrit =
						new StickCriterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(GiveastickContract.Stick.COL_ID);
			crit.addValue(values);
			receivedSticksCrit.add(crit);


			for (int i = 0; i < item.getReceivedSticks().size(); i++) {
				values.addValue(String.valueOf(
						item.getReceivedSticks().get(i).getId()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValue(
								GiveastickContract.Stick
										.COL_RECEIVER,
								item.getId())
					.withSelection(
							receivedSticksCrit.toSQLiteSelection(),
							receivedSticksCrit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated receivedSticks
			crit.setType(Type.NOT_IN);
			receivedSticksCrit.add(GiveastickContract.Stick.COL_RECEIVER,
					String.valueOf(item.getId()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					StickProviderAdapter.STICK_URI)
						.withValue(
								GiveastickContract.Stick
										.COL_RECEIVER,
								null)
					.withSelection(
							receivedSticksCrit.toSQLiteSelection(),
							receivedSticksCrit.toSQLiteSelectionArgs())
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
	 * Get associate UserGroup.
	 * @param item User
	 * @return UserGroup
	 */
	public UserGroup getAssociateUserGroup(
			final User item) {
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

	/**
	 * Get associate VoteSticks.
	 * @param item User
	 * @return VoteStick
	 */
	public ArrayList<VoteStick> getAssociateVoteSticks(
			final User item) {
		ArrayList<VoteStick> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor voteStickCursor = prov.query(
				VoteStickProviderAdapter.VOTESTICK_URI,
				GiveastickContract.VoteStick.ALIASED_COLS,
				GiveastickContract.VoteStick.COL_USERVOTESTICKSINTERNAL
						+ "= ?",
				new String[]{String.valueOf(item.getId())},
				null);

		result = GiveastickContract.VoteStick.cursorToItems(
						voteStickCursor);
		voteStickCursor.close();

		return result;
	}

	/**
	 * Get associate GivenSticks.
	 * @param item User
	 * @return Stick
	 */
	public ArrayList<Stick> getAssociateGivenSticks(
			final User item) {
		ArrayList<Stick> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor stickCursor = prov.query(
				StickProviderAdapter.STICK_URI,
				GiveastickContract.Stick.ALIASED_COLS,
				GiveastickContract.Stick.COL_GIVER
						+ "= ?",
				new String[]{String.valueOf(item.getId())},
				null);

		result = GiveastickContract.Stick.cursorToItems(
						stickCursor);
		stickCursor.close();

		return result;
	}

	/**
	 * Get associate ReceivedSticks.
	 * @param item User
	 * @return Stick
	 */
	public ArrayList<Stick> getAssociateReceivedSticks(
			final User item) {
		ArrayList<Stick> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor stickCursor = prov.query(
				StickProviderAdapter.STICK_URI,
				GiveastickContract.Stick.ALIASED_COLS,
				GiveastickContract.Stick.COL_RECEIVER
						+ "= ?",
				new String[]{String.valueOf(item.getId())},
				null);

		result = GiveastickContract.Stick.cursorToItems(
						stickCursor);
		stickCursor.close();

		return result;
	}

}
