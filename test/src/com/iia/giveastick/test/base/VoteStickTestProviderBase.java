/**************************************************************************
 * VoteStickTestProviderBase.java, giveastick Android
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

import com.iia.giveastick.provider.VoteStickProviderAdapter;
import com.iia.giveastick.provider.GiveastickContract;

import com.iia.giveastick.data.VoteStickSQLiteAdapter;

import com.iia.giveastick.entity.VoteStick;


import java.util.ArrayList;
import com.iia.giveastick.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** VoteStick database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit VoteStickTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class VoteStickTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected VoteStickSQLiteAdapter adapter;

	protected VoteStick entity;
	protected ContentResolver provider;

	protected ArrayList<VoteStick> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new VoteStickSQLiteAdapter(this.ctx);

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
			VoteStick voteStick = VoteStickUtils.generateRandom(this.ctx);

			try {
				ContentValues values = GiveastickContract.VoteStick.itemToContentValues(voteStick, 0, 0);
				values.remove(GiveastickContract.VoteStick.COL_ID);
				result = this.provider.insert(VoteStickProviderAdapter.VOTESTICK_URI, values);

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
		VoteStick result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(VoteStickProviderAdapter.VOTESTICK_URI + "/" + this.entity.getId()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = GiveastickContract.VoteStick.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			VoteStickUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<VoteStick> result = null;
		try {
			Cursor c = this.provider.query(VoteStickProviderAdapter.VOTESTICK_URI, this.adapter.getCols(), null, null, null);
			result = GiveastickContract.VoteStick.cursorToItems(c);
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
			VoteStick voteStick = VoteStickUtils.generateRandom(this.ctx);

			try {
				voteStick.setId(this.entity.getId());

				ContentValues values = GiveastickContract.VoteStick.itemToContentValues(voteStick, 0, 0);
				result = this.provider.update(
					Uri.parse(VoteStickProviderAdapter.VOTESTICK_URI
						+ "/"
						+ voteStick.getId()),
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
			VoteStick voteStick = VoteStickUtils.generateRandom(this.ctx);

			try {
				ContentValues values = GiveastickContract.VoteStick.itemToContentValues(voteStick, 0, 0);
				values.remove(GiveastickContract.VoteStick.COL_ID);

				result = this.provider.update(VoteStickProviderAdapter.VOTESTICK_URI, values, null, null);
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
				result = this.provider.delete(Uri.parse(VoteStickProviderAdapter.VOTESTICK_URI + "/" + this.entity.getId()), null, null);

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
				result = this.provider.delete(VoteStickProviderAdapter.VOTESTICK_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
