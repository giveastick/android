/**************************************************************************
 * UserGroupUtilsBase.java, giveastick Android
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
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.test.utils.*;



import com.iia.giveastick.entity.User;
import com.iia.giveastick.fixture.UserDataLoader;
import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.fixture.VoteForfeitDataLoader;
import java.util.ArrayList;

public abstract class UserGroupUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static UserGroup generateRandom(Context ctx){
		UserGroup userGroup = new UserGroup();

		userGroup.setId(TestUtils.generateRandomInt(0,100) + 1);
		userGroup.setTag("tag_"+TestUtils.generateRandomString(10));
		ArrayList<User> userss =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		ArrayList<User> relatedUserss = new ArrayList<User>();
		if (!userss.isEmpty()) {
			relatedUserss.add(userss.get(TestUtils.generateRandomInt(0, userss.size())));
			userGroup.setUsers(relatedUserss);
		}
		ArrayList<VoteForfeit> voteForfeitss =
			new ArrayList<VoteForfeit>(VoteForfeitDataLoader.getInstance(ctx).getMap().values());
		ArrayList<VoteForfeit> relatedVoteForfeitss = new ArrayList<VoteForfeit>();
		if (!voteForfeitss.isEmpty()) {
			relatedVoteForfeitss.add(voteForfeitss.get(TestUtils.generateRandomInt(0, voteForfeitss.size())));
			userGroup.setVoteForfeits(relatedVoteForfeitss);
		}

		return userGroup;
	}

	public static boolean equals(UserGroup userGroup1, UserGroup userGroup2){
		boolean ret = true;
		Assert.assertNotNull(userGroup1);
		Assert.assertNotNull(userGroup2);
		if (userGroup1!=null && userGroup2 !=null){
			Assert.assertEquals(userGroup1.getId(), userGroup2.getId());
			Assert.assertEquals(userGroup1.getTag(), userGroup2.getTag());
			if (userGroup1.getUsers() != null
					&& userGroup2.getUsers() != null) {
				Assert.assertEquals(userGroup1.getUsers().size(),
					userGroup2.getUsers().size());
				for (int i=0;i<userGroup1.getUsers().size();i++){
					Assert.assertEquals(userGroup1.getUsers().get(i).getId(),
								userGroup2.getUsers().get(i).getId());
				}
			}
			if (userGroup1.getVoteForfeits() != null
					&& userGroup2.getVoteForfeits() != null) {
				Assert.assertEquals(userGroup1.getVoteForfeits().size(),
					userGroup2.getVoteForfeits().size());
				for (int i=0;i<userGroup1.getVoteForfeits().size();i++){
					Assert.assertEquals(userGroup1.getVoteForfeits().get(i).getId(),
								userGroup2.getVoteForfeits().get(i).getId());
				}
			}
		}

		return ret;
	}
}

