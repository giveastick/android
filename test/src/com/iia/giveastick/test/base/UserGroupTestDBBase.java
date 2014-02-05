/**************************************************************************
 * UserGroupTestDBBase.java, giveastick Android
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

import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.entity.UserGroup;


import java.util.ArrayList;

import com.iia.giveastick.test.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** UserGroup database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit UserGroupTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class UserGroupTestDBBase extends TestDBBase {
	protected Context ctx;

	protected UserGroupSQLiteAdapter adapter;

	protected UserGroup entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new UserGroupSQLiteAdapter(this.ctx);
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
			UserGroup userGroup = UserGroupUtils.generateRandom(this.ctx);

			result = (int)this.adapter.insert(userGroup);

			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		UserGroup result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation

			UserGroupUtils.equals(result, this.entity);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			UserGroup userGroup = UserGroupUtils.generateRandom(this.ctx);
			userGroup.setId(this.entity.getId());

			result = (int)this.adapter.update(userGroup);

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
