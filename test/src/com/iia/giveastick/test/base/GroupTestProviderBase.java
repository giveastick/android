/**************************************************************************
 * GroupTestProviderBase.java, giveastick Android
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

import com.iia.giveastick.provider.GroupProviderAdapter;
import com.iia.giveastick.provider.GiveastickContract;

import com.iia.giveastick.data.GroupSQLiteAdapter;

import com.iia.giveastick.entity.Group;


import java.util.ArrayList;
import com.iia.giveastick.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** Group database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit GroupTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class GroupTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected GroupSQLiteAdapter adapter;

	protected Group entity;
	protected ContentResolver provider;

	protected ArrayList<Group> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new GroupSQLiteAdapter(this.ctx);

		this.provider = this.getMockContext().getContentResolver();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		Uri result = null;
		if (this.entity != null) {
			Group group = GroupUtils.generateRandom(this.ctx);

			try {
				ContentValues values = GiveastickContract.Group.itemToContentValues(group);
				values.remove(GiveastickContract.Group.COL_ID);
				result = this.provider.insert(GroupProviderAdapter.GROUP_URI, values);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertNotNull(result);
			Assert.assertTrue(Integer.valueOf(result.getEncodedPath().substring(result.getEncodedPath().lastIndexOf("/")+1)) > 0);
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		Group result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(GroupProviderAdapter.GROUP_URI + "/" + this.entity.getId()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = GiveastickContract.Group.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			GroupUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<Group> result = null;
		try {
			Cursor c = this.provider.query(GroupProviderAdapter.GROUP_URI, this.adapter.getCols(), null, null, null);
			result = GiveastickContract.Group.cursorToItems(c);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertNotNull(result);
		if (result != null) {
			Assert.assertEquals(result.size(), this.nbEntities);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			Group group = GroupUtils.generateRandom(this.ctx);

			try {
				group.setId(this.entity.getId());

				ContentValues values = GiveastickContract.Group.itemToContentValues(group);
				result = this.provider.update(
					Uri.parse(GroupProviderAdapter.GROUP_URI
						+ "/"
						+ group.getId()),
					values,
					null,
					null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertTrue(result > 0);
		}
	}

	/** Test case UpdateAll Entity */
	@SmallTest
	public void testUpdateAll() {
		int result = -1;
		if (this.entities.size() > 0) {
			Group group = GroupUtils.generateRandom(this.ctx);

			try {
				ContentValues values = GiveastickContract.Group.itemToContentValues(group);
				values.remove(GiveastickContract.Group.COL_ID);

				result = this.provider.update(GroupProviderAdapter.GROUP_URI, values, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}

	/** Test case Delete Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			try {
				result = this.provider.delete(Uri.parse(GroupProviderAdapter.GROUP_URI + "/" + this.entity.getId()), null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}
			Assert.assertTrue(result >= 0);
		}

	}

	/** Test case DeleteAll Entity */
	@SmallTest
	public void testDeleteAll() {
		int result = -1;
		if (this.entities.size() > 0) {

			try {
				result = this.provider.delete(GroupProviderAdapter.GROUP_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
