/**************************************************************************
 * GroupUtilsBase.java, giveastick Android
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
import com.iia.giveastick.entity.Group;
import com.iia.giveastick.test.utils.*;



import com.iia.giveastick.entity.User;
import com.iia.giveastick.fixture.UserDataLoader;
import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.fixture.VoteForfeitDataLoader;
import java.util.ArrayList;

public abstract class GroupUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Group generateRandom(Context ctx){
		Group group = new Group();

		group.setId(TestUtils.generateRandomInt(0,100) + 1);
		group.setTag("tag_"+TestUtils.generateRandomString(10));
		ArrayList<User> userss =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		ArrayList<User> relatedUserss = new ArrayList<User>();
		if (!userss.isEmpty()) {
			relatedUserss.add(userss.get(TestUtils.generateRandomInt(0, userss.size())));
			group.setUsers(relatedUserss);
		}
		ArrayList<VoteForfeit> voteForfeitss =
			new ArrayList<VoteForfeit>(VoteForfeitDataLoader.getInstance(ctx).getMap().values());
		ArrayList<VoteForfeit> relatedVoteForfeitss = new ArrayList<VoteForfeit>();
		if (!voteForfeitss.isEmpty()) {
			relatedVoteForfeitss.add(voteForfeitss.get(TestUtils.generateRandomInt(0, voteForfeitss.size())));
			group.setVoteForfeits(relatedVoteForfeitss);
		}

		return group;
	}

	public static boolean equals(Group group1, Group group2){
		boolean ret = true;
		Assert.assertNotNull(group1);
		Assert.assertNotNull(group2);
		if (group1!=null && group2 !=null){
			Assert.assertEquals(group1.getId(), group2.getId());
			Assert.assertEquals(group1.getTag(), group2.getTag());
			if (group1.getUsers() != null
					&& group2.getUsers() != null) {
				Assert.assertEquals(group1.getUsers().size(),
					group2.getUsers().size());
				for (int i=0;i<group1.getUsers().size();i++){
					Assert.assertEquals(group1.getUsers().get(i).getId(),
								group2.getUsers().get(i).getId());
				}
			}
			if (group1.getVoteForfeits() != null
					&& group2.getVoteForfeits() != null) {
				Assert.assertEquals(group1.getVoteForfeits().size(),
					group2.getVoteForfeits().size());
				for (int i=0;i<group1.getVoteForfeits().size();i++){
					Assert.assertEquals(group1.getVoteForfeits().get(i).getId(),
								group2.getVoteForfeits().get(i).getId());
				}
			}
		}

		return ret;
	}
}

