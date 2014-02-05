/**************************************************************************
 * UserTestDBBase.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.iia.giveastick.data.UserSQLiteAdapter;
import com.iia.giveastick.entity.User;


import java.util.ArrayList;

import com.iia.giveastick.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** User database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit UserTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class UserTestDBBase extends TestDBBase {
	protected Context ctx;

	protected UserSQLiteAdapter adapter;

	protected User entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new UserSQLiteAdapter(this.ctx);
		this.adapter.open();

	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		this.adapter.close();

		super.tearDown();
	}

	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		int result = -1;
		if (this.entity != null) {
			User user = UserUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(user);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		User result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation

			UserUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			User user = UserUtils.generateRandom(this.ctx);
			user.setId(this.entity.getId());

			result = (int)this.adapter.update(user);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId());
			Assert.assertTrue(result >= 0);
		}
	}
}
