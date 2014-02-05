/**************************************************************************
 * VoteForfeitUtilsBase.java, giveastick Android
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
import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.test.utils.*;



import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.fixture.UserGroupDataLoader;
import java.util.ArrayList;

public abstract class VoteForfeitUtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static VoteForfeit generateRandom(Context ctx){
		VoteForfeit voteForfeit = new VoteForfeit();

		voteForfeit.setId(TestUtils.generateRandomInt(0,100) + 1);
		voteForfeit.setDatetime(TestUtils.generateRandomDate());
		ArrayList<UserGroup> userGroups =
			new ArrayList<UserGroup>(UserGroupDataLoader.getInstance(ctx).getMap().values());
		if (!userGroups.isEmpty()) {
			voteForfeit.setUserGroup(userGroups.get(TestUtils.generateRandomInt(0, userGroups.size())));
		}

		return voteForfeit;
	}

	public static boolean equals(VoteForfeit voteForfeit1, VoteForfeit voteForfeit2){
		boolean ret = true;
		Assert.assertNotNull(voteForfeit1);
		Assert.assertNotNull(voteForfeit2);
		if (voteForfeit1!=null && voteForfeit2 !=null){
			Assert.assertEquals(voteForfeit1.getId(), voteForfeit2.getId());
			Assert.assertEquals(voteForfeit1.getDatetime(), voteForfeit2.getDatetime());
			if (voteForfeit1.getUserGroup() != null
					&& voteForfeit2.getUserGroup() != null) {
				Assert.assertEquals(voteForfeit1.getUserGroup().getId(),
						voteForfeit2.getUserGroup().getId());

			}
		}

		return ret;
	}
}

