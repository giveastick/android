/**************************************************************************
 * VoteStickUtilsBase.java, giveastick Android
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
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.test.utils.*;



import com.iia.giveastick.entity.User;
import com.iia.giveastick.fixture.UserDataLoader;
import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.fixture.StickDataLoader;
import java.util.ArrayList;

public abstract class VoteStickUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static VoteStick generateRandom(Context ctx){
		VoteStick voteStick = new VoteStick();

		voteStick.setId(TestUtils.generateRandomInt(0,100) + 1);
		voteStick.setAnswer(TestUtils.generateRandomBool());
		voteStick.setDatetime(TestUtils.generateRandomDate());
		ArrayList<User> users =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		if (!users.isEmpty()) {
			voteStick.setUser(users.get(TestUtils.generateRandomInt(0, users.size())));
		}
		ArrayList<Stick> sticks =
			new ArrayList<Stick>(StickDataLoader.getInstance(ctx).getMap().values());
		if (!sticks.isEmpty()) {
			voteStick.setStick(sticks.get(TestUtils.generateRandomInt(0, sticks.size())));
		}

		return voteStick;
	}

	public static boolean equals(VoteStick voteStick1, VoteStick voteStick2){
		boolean ret = true;
		Assert.assertNotNull(voteStick1);
		Assert.assertNotNull(voteStick2);
		if (voteStick1!=null && voteStick2 !=null){
			Assert.assertEquals(voteStick1.getId(), voteStick2.getId());
			Assert.assertEquals(voteStick1.isAnswer(), voteStick2.isAnswer());
			Assert.assertEquals(voteStick1.getDatetime(), voteStick2.getDatetime());
			if (voteStick1.getUser() != null
					&& voteStick2.getUser() != null) {
				Assert.assertEquals(voteStick1.getUser().getId(),
						voteStick2.getUser().getId());

			}
			if (voteStick1.getStick() != null
					&& voteStick2.getStick() != null) {
				Assert.assertEquals(voteStick1.getStick().getId(),
						voteStick2.getStick().getId());

			}
		}

		return ret;
	}
}

