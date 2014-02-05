/**************************************************************************
 * StickUtilsBase.java, giveastick Android
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
import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.test.utils.*;



import com.iia.giveastick.entity.User;
import com.iia.giveastick.fixture.UserDataLoader;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.fixture.VoteStickDataLoader;
import java.util.ArrayList;

public abstract class StickUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static Stick generateRandom(Context ctx){
		Stick stick = new Stick();

		stick.setId(TestUtils.generateRandomInt(0,100) + 1);
		stick.setGiven(TestUtils.generateRandomDate());
		stick.setPulledoff(TestUtils.generateRandomDate());
		ArrayList<User> givers =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		if (!givers.isEmpty()) {
			stick.setGiver(givers.get(TestUtils.generateRandomInt(0, givers.size())));
		}
		ArrayList<User> receivers =
			new ArrayList<User>(UserDataLoader.getInstance(ctx).getMap().values());
		if (!receivers.isEmpty()) {
			stick.setReceiver(receivers.get(TestUtils.generateRandomInt(0, receivers.size())));
		}
		ArrayList<VoteStick> voteStickss =
			new ArrayList<VoteStick>(VoteStickDataLoader.getInstance(ctx).getMap().values());
		ArrayList<VoteStick> relatedVoteStickss = new ArrayList<VoteStick>();
		if (!voteStickss.isEmpty()) {
			relatedVoteStickss.add(voteStickss.get(TestUtils.generateRandomInt(0, voteStickss.size())));
			stick.setVoteSticks(relatedVoteStickss);
		}

		return stick;
	}

	public static boolean equals(Stick stick1, Stick stick2){
		boolean ret = true;
		Assert.assertNotNull(stick1);
		Assert.assertNotNull(stick2);
		if (stick1!=null && stick2 !=null){
			Assert.assertEquals(stick1.getId(), stick2.getId());
			Assert.assertEquals(stick1.getGiven(), stick2.getGiven());
			Assert.assertEquals(stick1.getPulledoff(), stick2.getPulledoff());
			if (stick1.getGiver() != null
					&& stick2.getGiver() != null) {
				Assert.assertEquals(stick1.getGiver().getId(),
						stick2.getGiver().getId());

			}
			if (stick1.getReceiver() != null
					&& stick2.getReceiver() != null) {
				Assert.assertEquals(stick1.getReceiver().getId(),
						stick2.getReceiver().getId());

			}
			if (stick1.getVoteSticks() != null
					&& stick2.getVoteSticks() != null) {
				Assert.assertEquals(stick1.getVoteSticks().size(),
					stick2.getVoteSticks().size());
				for (int i=0;i<stick1.getVoteSticks().size();i++){
					Assert.assertEquals(stick1.getVoteSticks().get(i).getId(),
								stick2.getVoteSticks().get(i).getId());
				}
			}
		}

		return ret;
	}
}

