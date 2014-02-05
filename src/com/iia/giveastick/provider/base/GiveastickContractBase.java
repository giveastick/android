/**************************************************************************
 * GiveastickContractBase.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.provider.base;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.entity.Stick;

import com.iia.giveastick.provider.GiveastickContract;

import com.iia.giveastick.harmony.util.DateUtils;

/** Giveastick contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class GiveastickContractBase {

	/**
	 * Columns names and aliases for User entity.
	 */
	public interface UserColumns {
		/** id. */
		public static final String COL_ID =
				"id";
		/** Alias. */
		public static final String ALIASED_COL_ID =
				GiveastickContract.User.TABLE_NAME + "." + COL_ID;
		/** email. */
		public static final String COL_EMAIL =
				"email";
		/** Alias. */
		public static final String ALIASED_COL_EMAIL =
				GiveastickContract.User.TABLE_NAME + "." + COL_EMAIL;
		/** password. */
		public static final String COL_PASSWORD =
				"password";
		/** Alias. */
		public static final String ALIASED_COL_PASSWORD =
				GiveastickContract.User.TABLE_NAME + "." + COL_PASSWORD;
		/** nickname. */
		public static final String COL_NICKNAME =
				"nickname";
		/** Alias. */
		public static final String ALIASED_COL_NICKNAME =
				GiveastickContract.User.TABLE_NAME + "." + COL_NICKNAME;
		/** created. */
		public static final String COL_CREATED =
				"created";
		/** Alias. */
		public static final String ALIASED_COL_CREATED =
				GiveastickContract.User.TABLE_NAME + "." + COL_CREATED;
		/** androidGcmaToken. */
		public static final String COL_ANDROIDGCMATOKEN =
				"androidGcmaToken";
		/** Alias. */
		public static final String ALIASED_COL_ANDROIDGCMATOKEN =
				GiveastickContract.User.TABLE_NAME + "." + COL_ANDROIDGCMATOKEN;
		/** userGroup. */
		public static final String COL_USERGROUP =
				"userGroup";
		/** Alias. */
		public static final String ALIASED_COL_USERGROUP =
				GiveastickContract.User.TABLE_NAME + "." + COL_USERGROUP;
		/** UserGroup_users_internal. */
		public static final String COL_USERGROUPUSERSINTERNAL =
				"UserGroup_users_internal";
		/** Alias. */
		public static final String ALIASED_COL_USERGROUPUSERSINTERNAL =
				GiveastickContract.User.TABLE_NAME + "." + COL_USERGROUPUSERSINTERNAL;
	}

	/**
	 * Contract base class for User Entity.
	 */
	public static class UserBase implements UserColumns {
		/** Table name of SQLite database. */
		public static final String TABLE_NAME = "User";
		/** Global Fields. */
		public static final String[] COLS = new String[] {
	
			UserColumns.COL_ID	,
			UserColumns.COL_EMAIL	,
			UserColumns.COL_PASSWORD	,
			UserColumns.COL_NICKNAME	,
			UserColumns.COL_CREATED	,
			UserColumns.COL_ANDROIDGCMATOKEN	,
			UserColumns.COL_USERGROUP	,
			UserColumns.COL_USERGROUPUSERSINTERNAL
		};

		/** Global Fields. */
		public static final String[] ALIASED_COLS = new String[] {
	
			UserColumns.ALIASED_COL_ID	,
			UserColumns.ALIASED_COL_EMAIL	,
			UserColumns.ALIASED_COL_PASSWORD	,
			UserColumns.ALIASED_COL_NICKNAME	,
			UserColumns.ALIASED_COL_CREATED	,
			UserColumns.ALIASED_COL_ANDROIDGCMATOKEN	,
			UserColumns.ALIASED_COL_USERGROUP	,
			UserColumns.ALIASED_COL_USERGROUPUSERSINTERNAL
		};

		/** Convert User entity to Content Values for database.
		 *
		 * @param item User entity object
		 * @param usergroupId usergroup id
		 * @return ContentValues object
		 */
		public static ContentValues itemToContentValues(final User item,
					int usergroupId) {
			final ContentValues result = GiveastickContract.User.itemToContentValues(item);
			result.put(COL_USERGROUPUSERSINTERNAL,
					String.valueOf(usergroupId));
			return result;
		}

		/**
		 * Converts a User into a content values.
		 *
		 * @param item The User to convert
		 *
		 * @return The content values
		 */
		public static ContentValues itemToContentValues(final User item) {
			final ContentValues result = new ContentValues();

			result.put(COL_ID,
				String.valueOf(item.getId()));

			if (item.getEmail() != null) {
				result.put(COL_EMAIL,
					item.getEmail());
			}

			if (item.getPassword() != null) {
				result.put(COL_PASSWORD,
					item.getPassword());
			}

			if (item.getNickname() != null) {
				result.put(COL_NICKNAME,
					item.getNickname());
			}

			if (item.getCreated() != null) {
				result.put(COL_CREATED,
					item.getCreated().toString(ISODateTimeFormat.dateTime()));
			}

			if (item.getAndroidGcmaToken() != null) {
				result.put(COL_ANDROIDGCMATOKEN,
					item.getAndroidGcmaToken());
			} else {
				result.put(COL_ANDROIDGCMATOKEN, (String) null);
			}

			if (item.getUserGroup() != null) {
				result.put(COL_USERGROUP,
					item.getUserGroup().getId());
			}


			return result;
		}

		/**
		 * Converts a Cursor into a User.
		 *
		 * @param cursor The cursor to convert
		 *
		 * @return The extracted User 
		 */
		public static User cursorToItem(final Cursor cursor) {
			User result = new User();
			GiveastickContract.User.cursorToItem(cursor, result);
			return result;
		}

		/**
		 * Convert Cursor of database to User entity.
		 * @param cursor Cursor object
		 * @param result User entity
		 */
		public static void cursorToItem(final Cursor cursor, final User result) {
			if (cursor.getCount() != 0) {
				int index;

				index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_EMAIL);
			result.setEmail(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_PASSWORD);
			result.setPassword(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_NICKNAME);
			result.setNickname(
					cursor.getString(index));

			index = cursor.getColumnIndexOrThrow(COL_CREATED);
			final DateTime dtCreated =
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));
			if (dtCreated != null) {
					result.setCreated(
							dtCreated);
			} else {
				result.setCreated(new DateTime());
			}

			index = cursor.getColumnIndexOrThrow(COL_ANDROIDGCMATOKEN);
			if (!cursor.isNull(index)) {
				result.setAndroidGcmaToken(
					cursor.getString(index));
			}

			index = cursor.getColumnIndexOrThrow(COL_USERGROUP);
			final UserGroup userGroup = new UserGroup();
			userGroup.setId(cursor.getInt(index));
			result.setUserGroup(userGroup);


			}
		}

		/**
		 * Convert Cursor of database to Array of User entity.
		 * @param cursor Cursor object
		 * @return Array of User entity
		 */
		public static ArrayList<User> cursorToItems(final Cursor cursor) {
			final ArrayList<User> result = new ArrayList<User>(cursor.getCount());

			if (cursor.getCount() != 0) {
				cursor.moveToFirst();

				User item;
				do {
					item = GiveastickContract.User.cursorToItem(cursor);
					result.add(item);
				} while (cursor.moveToNext());
			}

			return result;
		}
	}
	/**
	 * Columns names and aliases for UserGroup entity.
	 */
	public interface UserGroupColumns {
		/** id. */
		public static final String COL_ID =
				"id";
		/** Alias. */
		public static final String ALIASED_COL_ID =
				GiveastickContract.UserGroup.TABLE_NAME + "." + COL_ID;
		/** tag. */
		public static final String COL_TAG =
				"tag";
		/** Alias. */
		public static final String ALIASED_COL_TAG =
				GiveastickContract.UserGroup.TABLE_NAME + "." + COL_TAG;
	}

	/**
	 * Contract base class for UserGroup Entity.
	 */
	public static class UserGroupBase implements UserGroupColumns {
		/** Table name of SQLite database. */
		public static final String TABLE_NAME = "UserGroup";
		/** Global Fields. */
		public static final String[] COLS = new String[] {
	
			UserGroupColumns.COL_ID	,
			UserGroupColumns.COL_TAG
		};

		/** Global Fields. */
		public static final String[] ALIASED_COLS = new String[] {
	
			UserGroupColumns.ALIASED_COL_ID	,
			UserGroupColumns.ALIASED_COL_TAG
		};


		/**
		 * Converts a UserGroup into a content values.
		 *
		 * @param item The UserGroup to convert
		 *
		 * @return The content values
		 */
		public static ContentValues itemToContentValues(final UserGroup item) {
			final ContentValues result = new ContentValues();

			result.put(COL_ID,
				String.valueOf(item.getId()));

			if (item.getTag() != null) {
				result.put(COL_TAG,
					item.getTag());
			}


			return result;
		}

		/**
		 * Converts a Cursor into a UserGroup.
		 *
		 * @param cursor The cursor to convert
		 *
		 * @return The extracted UserGroup 
		 */
		public static UserGroup cursorToItem(final Cursor cursor) {
			UserGroup result = new UserGroup();
			GiveastickContract.UserGroup.cursorToItem(cursor, result);
			return result;
		}

		/**
		 * Convert Cursor of database to UserGroup entity.
		 * @param cursor Cursor object
		 * @param result UserGroup entity
		 */
		public static void cursorToItem(final Cursor cursor, final UserGroup result) {
			if (cursor.getCount() != 0) {
				int index;

				index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_TAG);
			result.setTag(
					cursor.getString(index));


			}
		}

		/**
		 * Convert Cursor of database to Array of UserGroup entity.
		 * @param cursor Cursor object
		 * @return Array of UserGroup entity
		 */
		public static ArrayList<UserGroup> cursorToItems(final Cursor cursor) {
			final ArrayList<UserGroup> result = new ArrayList<UserGroup>(cursor.getCount());

			if (cursor.getCount() != 0) {
				cursor.moveToFirst();

				UserGroup item;
				do {
					item = GiveastickContract.UserGroup.cursorToItem(cursor);
					result.add(item);
				} while (cursor.moveToNext());
			}

			return result;
		}
	}
	/**
	 * Columns names and aliases for VoteForfeit entity.
	 */
	public interface VoteForfeitColumns {
		/** UserGroup_voteForfeits_internal. */
		public static final String COL_USERGROUPVOTEFORFEITSINTERNAL =
				"UserGroup_voteForfeits_internal";
		/** Alias. */
		public static final String ALIASED_COL_USERGROUPVOTEFORFEITSINTERNAL =
				GiveastickContract.VoteForfeit.TABLE_NAME + "." + COL_USERGROUPVOTEFORFEITSINTERNAL;
		/** id. */
		public static final String COL_ID =
				"id";
		/** Alias. */
		public static final String ALIASED_COL_ID =
				GiveastickContract.VoteForfeit.TABLE_NAME + "." + COL_ID;
		/** datetime. */
		public static final String COL_DATETIME =
				"datetime";
		/** Alias. */
		public static final String ALIASED_COL_DATETIME =
				GiveastickContract.VoteForfeit.TABLE_NAME + "." + COL_DATETIME;
		/** userGroup. */
		public static final String COL_USERGROUP =
				"userGroup";
		/** Alias. */
		public static final String ALIASED_COL_USERGROUP =
				GiveastickContract.VoteForfeit.TABLE_NAME + "." + COL_USERGROUP;
	}

	/**
	 * Contract base class for VoteForfeit Entity.
	 */
	public static class VoteForfeitBase implements VoteForfeitColumns {
		/** Table name of SQLite database. */
		public static final String TABLE_NAME = "VoteForfeit";
		/** Global Fields. */
		public static final String[] COLS = new String[] {
	
			VoteForfeitColumns.COL_USERGROUPVOTEFORFEITSINTERNAL	,
			VoteForfeitColumns.COL_ID	,
			VoteForfeitColumns.COL_DATETIME	,
			VoteForfeitColumns.COL_USERGROUP
		};

		/** Global Fields. */
		public static final String[] ALIASED_COLS = new String[] {
	
			VoteForfeitColumns.ALIASED_COL_USERGROUPVOTEFORFEITSINTERNAL	,
			VoteForfeitColumns.ALIASED_COL_ID	,
			VoteForfeitColumns.ALIASED_COL_DATETIME	,
			VoteForfeitColumns.ALIASED_COL_USERGROUP
		};

		/** Convert VoteForfeit entity to Content Values for database.
		 *
		 * @param item VoteForfeit entity object
		 * @param usergroupId usergroup id
		 * @return ContentValues object
		 */
		public static ContentValues itemToContentValues(final VoteForfeit item,
					int usergroupId) {
			final ContentValues result = GiveastickContract.VoteForfeit.itemToContentValues(item);
			result.put(COL_USERGROUPVOTEFORFEITSINTERNAL,
					String.valueOf(usergroupId));
			return result;
		}

		/**
		 * Converts a VoteForfeit into a content values.
		 *
		 * @param item The VoteForfeit to convert
		 *
		 * @return The content values
		 */
		public static ContentValues itemToContentValues(final VoteForfeit item) {
			final ContentValues result = new ContentValues();

			result.put(COL_ID,
				String.valueOf(item.getId()));

			if (item.getDatetime() != null) {
				result.put(COL_DATETIME,
					item.getDatetime().toString(ISODateTimeFormat.dateTime()));
			}

			if (item.getUserGroup() != null) {
				result.put(COL_USERGROUP,
					item.getUserGroup().getId());
			}


			return result;
		}

		/**
		 * Converts a Cursor into a VoteForfeit.
		 *
		 * @param cursor The cursor to convert
		 *
		 * @return The extracted VoteForfeit 
		 */
		public static VoteForfeit cursorToItem(final Cursor cursor) {
			VoteForfeit result = new VoteForfeit();
			GiveastickContract.VoteForfeit.cursorToItem(cursor, result);
			return result;
		}

		/**
		 * Convert Cursor of database to VoteForfeit entity.
		 * @param cursor Cursor object
		 * @param result VoteForfeit entity
		 */
		public static void cursorToItem(final Cursor cursor, final VoteForfeit result) {
			if (cursor.getCount() != 0) {
				int index;

				index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_DATETIME);
			final DateTime dtDatetime =
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));
			if (dtDatetime != null) {
					result.setDatetime(
							dtDatetime);
			} else {
				result.setDatetime(new DateTime());
			}

			index = cursor.getColumnIndexOrThrow(COL_USERGROUP);
			final UserGroup userGroup = new UserGroup();
			userGroup.setId(cursor.getInt(index));
			result.setUserGroup(userGroup);


			}
		}

		/**
		 * Convert Cursor of database to Array of VoteForfeit entity.
		 * @param cursor Cursor object
		 * @return Array of VoteForfeit entity
		 */
		public static ArrayList<VoteForfeit> cursorToItems(final Cursor cursor) {
			final ArrayList<VoteForfeit> result = new ArrayList<VoteForfeit>(cursor.getCount());

			if (cursor.getCount() != 0) {
				cursor.moveToFirst();

				VoteForfeit item;
				do {
					item = GiveastickContract.VoteForfeit.cursorToItem(cursor);
					result.add(item);
				} while (cursor.moveToNext());
			}

			return result;
		}
	}
	/**
	 * Columns names and aliases for VoteStick entity.
	 */
	public interface VoteStickColumns {
		/** Stick_voteSticks_internal. */
		public static final String COL_STICKVOTESTICKSINTERNAL =
				"Stick_voteSticks_internal";
		/** Alias. */
		public static final String ALIASED_COL_STICKVOTESTICKSINTERNAL =
				GiveastickContract.VoteStick.TABLE_NAME + "." + COL_STICKVOTESTICKSINTERNAL;
		/** User_voteSticks_internal. */
		public static final String COL_USERVOTESTICKSINTERNAL =
				"User_voteSticks_internal";
		/** Alias. */
		public static final String ALIASED_COL_USERVOTESTICKSINTERNAL =
				GiveastickContract.VoteStick.TABLE_NAME + "." + COL_USERVOTESTICKSINTERNAL;
		/** id. */
		public static final String COL_ID =
				"id";
		/** Alias. */
		public static final String ALIASED_COL_ID =
				GiveastickContract.VoteStick.TABLE_NAME + "." + COL_ID;
		/** answer. */
		public static final String COL_ANSWER =
				"answer";
		/** Alias. */
		public static final String ALIASED_COL_ANSWER =
				GiveastickContract.VoteStick.TABLE_NAME + "." + COL_ANSWER;
		/** datetime. */
		public static final String COL_DATETIME =
				"datetime";
		/** Alias. */
		public static final String ALIASED_COL_DATETIME =
				GiveastickContract.VoteStick.TABLE_NAME + "." + COL_DATETIME;
		/** user. */
		public static final String COL_USER =
				"user";
		/** Alias. */
		public static final String ALIASED_COL_USER =
				GiveastickContract.VoteStick.TABLE_NAME + "." + COL_USER;
		/** stick. */
		public static final String COL_STICK =
				"stick";
		/** Alias. */
		public static final String ALIASED_COL_STICK =
				GiveastickContract.VoteStick.TABLE_NAME + "." + COL_STICK;
	}

	/**
	 * Contract base class for VoteStick Entity.
	 */
	public static class VoteStickBase implements VoteStickColumns {
		/** Table name of SQLite database. */
		public static final String TABLE_NAME = "VoteStick";
		/** Global Fields. */
		public static final String[] COLS = new String[] {
	
			VoteStickColumns.COL_STICKVOTESTICKSINTERNAL	,
			VoteStickColumns.COL_USERVOTESTICKSINTERNAL	,
			VoteStickColumns.COL_ID	,
			VoteStickColumns.COL_ANSWER	,
			VoteStickColumns.COL_DATETIME	,
			VoteStickColumns.COL_USER	,
			VoteStickColumns.COL_STICK
		};

		/** Global Fields. */
		public static final String[] ALIASED_COLS = new String[] {
	
			VoteStickColumns.ALIASED_COL_STICKVOTESTICKSINTERNAL	,
			VoteStickColumns.ALIASED_COL_USERVOTESTICKSINTERNAL	,
			VoteStickColumns.ALIASED_COL_ID	,
			VoteStickColumns.ALIASED_COL_ANSWER	,
			VoteStickColumns.ALIASED_COL_DATETIME	,
			VoteStickColumns.ALIASED_COL_USER	,
			VoteStickColumns.ALIASED_COL_STICK
		};

		/** Convert VoteStick entity to Content Values for database.
		 *
		 * @param item VoteStick entity object
		 * @param stickId stick id
		 * @param userId user id
		 * @return ContentValues object
		 */
		public static ContentValues itemToContentValues(final VoteStick item,
					int stickId,
					int userId) {
			final ContentValues result = GiveastickContract.VoteStick.itemToContentValues(item);
			result.put(COL_STICKVOTESTICKSINTERNAL,
					String.valueOf(stickId));
			result.put(COL_USERVOTESTICKSINTERNAL,
					String.valueOf(userId));
			return result;
		}

		/**
		 * Converts a VoteStick into a content values.
		 *
		 * @param item The VoteStick to convert
		 *
		 * @return The content values
		 */
		public static ContentValues itemToContentValues(final VoteStick item) {
			final ContentValues result = new ContentValues();

			result.put(COL_ID,
				String.valueOf(item.getId()));

			result.put(COL_ANSWER,
				item.isAnswer());

			if (item.getDatetime() != null) {
				result.put(COL_DATETIME,
					item.getDatetime().toString(ISODateTimeFormat.dateTime()));
			}

			if (item.getUser() != null) {
				result.put(COL_USER,
					item.getUser().getId());
			}

			if (item.getStick() != null) {
				result.put(COL_STICK,
					item.getStick().getId());
			}


			return result;
		}

		/**
		 * Converts a Cursor into a VoteStick.
		 *
		 * @param cursor The cursor to convert
		 *
		 * @return The extracted VoteStick 
		 */
		public static VoteStick cursorToItem(final Cursor cursor) {
			VoteStick result = new VoteStick();
			GiveastickContract.VoteStick.cursorToItem(cursor, result);
			return result;
		}

		/**
		 * Convert Cursor of database to VoteStick entity.
		 * @param cursor Cursor object
		 * @param result VoteStick entity
		 */
		public static void cursorToItem(final Cursor cursor, final VoteStick result) {
			if (cursor.getCount() != 0) {
				int index;

				index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_ANSWER);
			result.setAnswer(
					cursor.getInt(index) == 1);

			index = cursor.getColumnIndexOrThrow(COL_DATETIME);
			final DateTime dtDatetime =
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));
			if (dtDatetime != null) {
					result.setDatetime(
							dtDatetime);
			} else {
				result.setDatetime(new DateTime());
			}

			index = cursor.getColumnIndexOrThrow(COL_USER);
			final User user = new User();
			user.setId(cursor.getInt(index));
			result.setUser(user);

			index = cursor.getColumnIndexOrThrow(COL_STICK);
			final Stick stick = new Stick();
			stick.setId(cursor.getInt(index));
			result.setStick(stick);


			}
		}

		/**
		 * Convert Cursor of database to Array of VoteStick entity.
		 * @param cursor Cursor object
		 * @return Array of VoteStick entity
		 */
		public static ArrayList<VoteStick> cursorToItems(final Cursor cursor) {
			final ArrayList<VoteStick> result = new ArrayList<VoteStick>(cursor.getCount());

			if (cursor.getCount() != 0) {
				cursor.moveToFirst();

				VoteStick item;
				do {
					item = GiveastickContract.VoteStick.cursorToItem(cursor);
					result.add(item);
				} while (cursor.moveToNext());
			}

			return result;
		}
	}
	/**
	 * Columns names and aliases for Stick entity.
	 */
	public interface StickColumns {
		/** id. */
		public static final String COL_ID =
				"id";
		/** Alias. */
		public static final String ALIASED_COL_ID =
				GiveastickContract.Stick.TABLE_NAME + "." + COL_ID;
		/** given. */
		public static final String COL_GIVEN =
				"given";
		/** Alias. */
		public static final String ALIASED_COL_GIVEN =
				GiveastickContract.Stick.TABLE_NAME + "." + COL_GIVEN;
		/** pulledoff. */
		public static final String COL_PULLEDOFF =
				"pulledoff";
		/** Alias. */
		public static final String ALIASED_COL_PULLEDOFF =
				GiveastickContract.Stick.TABLE_NAME + "." + COL_PULLEDOFF;
		/** giver. */
		public static final String COL_GIVER =
				"giver";
		/** Alias. */
		public static final String ALIASED_COL_GIVER =
				GiveastickContract.Stick.TABLE_NAME + "." + COL_GIVER;
		/** receiver. */
		public static final String COL_RECEIVER =
				"receiver";
		/** Alias. */
		public static final String ALIASED_COL_RECEIVER =
				GiveastickContract.Stick.TABLE_NAME + "." + COL_RECEIVER;
	}

	/**
	 * Contract base class for Stick Entity.
	 */
	public static class StickBase implements StickColumns {
		/** Table name of SQLite database. */
		public static final String TABLE_NAME = "Stick";
		/** Global Fields. */
		public static final String[] COLS = new String[] {
	
			StickColumns.COL_ID	,
			StickColumns.COL_GIVEN	,
			StickColumns.COL_PULLEDOFF	,
			StickColumns.COL_GIVER	,
			StickColumns.COL_RECEIVER
		};

		/** Global Fields. */
		public static final String[] ALIASED_COLS = new String[] {
	
			StickColumns.ALIASED_COL_ID	,
			StickColumns.ALIASED_COL_GIVEN	,
			StickColumns.ALIASED_COL_PULLEDOFF	,
			StickColumns.ALIASED_COL_GIVER	,
			StickColumns.ALIASED_COL_RECEIVER
		};


		/**
		 * Converts a Stick into a content values.
		 *
		 * @param item The Stick to convert
		 *
		 * @return The content values
		 */
		public static ContentValues itemToContentValues(final Stick item) {
			final ContentValues result = new ContentValues();

			result.put(COL_ID,
				String.valueOf(item.getId()));

			if (item.getGiven() != null) {
				result.put(COL_GIVEN,
					item.getGiven().toString(ISODateTimeFormat.dateTime()));
			}

			if (item.getPulledoff() != null) {
				result.put(COL_PULLEDOFF,
					item.getPulledoff().toString(ISODateTimeFormat.dateTime()));
			}

			if (item.getGiver() != null) {
				result.put(COL_GIVER,
					item.getGiver().getId());
			}

			if (item.getReceiver() != null) {
				result.put(COL_RECEIVER,
					item.getReceiver().getId());
			}


			return result;
		}

		/**
		 * Converts a Cursor into a Stick.
		 *
		 * @param cursor The cursor to convert
		 *
		 * @return The extracted Stick 
		 */
		public static Stick cursorToItem(final Cursor cursor) {
			Stick result = new Stick();
			GiveastickContract.Stick.cursorToItem(cursor, result);
			return result;
		}

		/**
		 * Convert Cursor of database to Stick entity.
		 * @param cursor Cursor object
		 * @param result Stick entity
		 */
		public static void cursorToItem(final Cursor cursor, final Stick result) {
			if (cursor.getCount() != 0) {
				int index;

				index = cursor.getColumnIndexOrThrow(COL_ID);
			result.setId(
					cursor.getInt(index));

			index = cursor.getColumnIndexOrThrow(COL_GIVEN);
			final DateTime dtGiven =
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));
			if (dtGiven != null) {
					result.setGiven(
							dtGiven);
			} else {
				result.setGiven(new DateTime());
			}

			index = cursor.getColumnIndexOrThrow(COL_PULLEDOFF);
			final DateTime dtPulledoff =
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));
			if (dtPulledoff != null) {
					result.setPulledoff(
							dtPulledoff);
			} else {
				result.setPulledoff(new DateTime());
			}

			index = cursor.getColumnIndexOrThrow(COL_GIVER);
			final User giver = new User();
			giver.setId(cursor.getInt(index));
			result.setGiver(giver);

			index = cursor.getColumnIndexOrThrow(COL_RECEIVER);
			final User receiver = new User();
			receiver.setId(cursor.getInt(index));
			result.setReceiver(receiver);


			}
		}

		/**
		 * Convert Cursor of database to Array of Stick entity.
		 * @param cursor Cursor object
		 * @return Array of Stick entity
		 */
		public static ArrayList<Stick> cursorToItems(final Cursor cursor) {
			final ArrayList<Stick> result = new ArrayList<Stick>(cursor.getCount());

			if (cursor.getCount() != 0) {
				cursor.moveToFirst();

				Stick item;
				do {
					item = GiveastickContract.Stick.cursorToItem(cursor);
					result.add(item);
				} while (cursor.moveToNext());
			}

			return result;
		}
	}
}
