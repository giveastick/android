/**************************************************************************
 * UserUtilsBase.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.test.utils.base;

import android.content.Context;
import junit.framework.Assert;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.test.utils.*;



import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.fixture.UserGroupDataLoader;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.fixture.VoteStickDataLoader;
import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.fixture.StickDataLoader;
import java.util.ArrayList;

public abstract class UserUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static User generateRandom(Context ctx){
		User user = new User();

		user.setId(TestUtils.generateRandomInt(0,100) + 1);
		user.setEmail("email_"+TestUtils.generateRandomString(10));
		user.setPassword("password_"+TestUtils.generateRandomString(10));
		user.setNickname("nickname_"+TestUtils.generateRandomString(10));
		user.setCreated(TestUtils.generateRandomDate());
		user.setAndroidGcmaToken("androidGcmaToken_"+TestUtils.generateRandomString(10));
		ArrayList<UserGroup> userGroups =
			new ArrayList<UserGroup>(UserGroupDataLoader.getInstance(ctx).getMap().values());
		if (!userGroups.isEmpty()) {
			user.setUserGroup(userGroups.get(TestUtils.generateRandomInt(0, userGroups.size())));
		}
		ArrayList<VoteStick> voteStickss =
			new ArrayList<VoteStick>(VoteStickDataLoader.getInstance(ctx).getMap().values());
		ArrayList<VoteStick> relatedVoteStickss = new ArrayList<VoteStick>();
		if (!voteStickss.isEmpty()) {
			relatedVoteStickss.add(voteStickss.get(TestUtils.generateRandomInt(0, voteStickss.size())));
			user.setVoteSticks(relatedVoteStickss);
		}
		ArrayList<Stick> givenStickss =
			new ArrayList<Stick>(StickDataLoader.getInstance(ctx).getMap().values());
		ArrayList<Stick> relatedGivenStickss = new ArrayList<Stick>();
		if (!givenStickss.isEmpty()) {
			relatedGivenStickss.add(givenStickss.get(TestUtils.generateRandomInt(0, givenStickss.size())));
			user.setGivenSticks(relatedGivenStickss);
		}
		ArrayList<Stick> receivedStickss =
			new ArrayList<Stick>(StickDataLoader.getInstance(ctx).getMap().values());
		ArrayList<Stick> relatedReceivedStickss = new ArrayList<Stick>();
		if (!receivedStickss.isEmpty()) {
			relatedReceivedStickss.add(receivedStickss.get(TestUtils.generateRandomInt(0, receivedStickss.size())));
			user.setReceivedSticks(relatedReceivedStickss);
		}

		return user;
	}

	public static boolean equals(User user1, User user2){
		boolean ret = true;
		Assert.assertNotNull(user1);
		Assert.assertNotNull(user2);
		if (user1!=null && user2 !=null){
			Assert.assertEquals(user1.getId(), user2.getId());
			Assert.assertEquals(user1.getEmail(), user2.getEmail());
			Assert.assertEquals(user1.getPassword(), user2.getPassword());
			Assert.assertEquals(user1.getNickname(), user2.getNickname());
			Assert.assertEquals(user1.getCreated(), user2.getCreated());
			Assert.assertEquals(user1.getAndroidGcmaToken(), user2.getAndroidGcmaToken());
			if (user1.getUserGroup() != null
					&& user2.getUserGroup() != null) {
				Assert.assertEquals(user1.getUserGroup().getId(),
						user2.getUserGroup().getId());

			}
			if (user1.getVoteSticks() != null
					&& user2.getVoteSticks() != null) {
				Assert.assertEquals(user1.getVoteSticks().size(),
					user2.getVoteSticks().size());
				for (int i=0;i<user1.getVoteSticks().size();i++){
					Assert.assertEquals(user1.getVoteSticks().get(i).getId(),
								user2.getVoteSticks().get(i).getId());
				}
			}
			if (user1.getGivenSticks() != null
					&& user2.getGivenSticks() != null) {
				Assert.assertEquals(user1.getGivenSticks().size(),
					user2.getGivenSticks().size());
				for (int i=0;i<user1.getGivenSticks().size();i++){
					Assert.assertEquals(user1.getGivenSticks().get(i).getId(),
								user2.getGivenSticks().get(i).getId());
				}
			}
			if (user1.getReceivedSticks() != null
					&& user2.getReceivedSticks() != null) {
				Assert.assertEquals(user1.getReceivedSticks().size(),
					user2.getReceivedSticks().size());
				for (int i=0;i<user1.getReceivedSticks().size();i++){
					Assert.assertEquals(user1.getReceivedSticks().get(i).getId(),
								user2.getReceivedSticks().get(i).getId());
				}
			}
		}

		return ret;
	}
}

