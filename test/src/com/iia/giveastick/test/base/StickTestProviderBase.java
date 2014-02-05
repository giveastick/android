/**************************************************************************
 * StickTestProviderBase.java, giveastick Android
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

import com.iia.giveastick.provider.StickProviderAdapter;
import com.iia.giveastick.provider.GiveastickContract;

import com.iia.giveastick.data.StickSQLiteAdapter;

import com.iia.giveastick.entity.Stick;


import java.util.ArrayList;
import com.iia.giveastick.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** Stick database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit StickTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class StickTestProviderBase extends TestDBBase {
	protected Context ctx;

	protected StickSQLiteAdapter adapter;

	protected Stick entity;
	protected ContentResolver provider;

	protected ArrayList<Stick> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getMockContext();

		this.adapter = new StickSQLiteAdapter(this.ctx);

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
			Stick stick = StickUtils.generateRandom(this.ctx);

			try {
				ContentValues values = GiveastickContract.Stick.itemToContentValues(stick);
				values.remove(GiveastickContract.Stick.COL_ID);
				result = this.provider.insert(StickProviderAdapter.STICK_URI, values);

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
		Stick result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(StickProviderAdapter.STICK_URI + "/" + this.entity.getId()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = GiveastickContract.Stick.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			StickUtils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<Stick> result = null;
		try {
			Cursor c = this.provider.query(StickProviderAdapter.STICK_URI, this.adapter.getCols(), null, null, null);
			result = GiveastickContract.Stick.cursorToItems(c);
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
			Stick stick = StickUtils.generateRandom(this.ctx);

			try {
				stick.setId(this.entity.getId());

				ContentValues values = GiveastickContract.Stick.itemToContentValues(stick);
				result = this.provider.update(
					Uri.parse(StickProviderAdapter.STICK_URI
						+ "/"
						+ stick.getId()),
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
			Stick stick = StickUtils.generateRandom(this.ctx);

			try {
				ContentValues values = GiveastickContract.Stick.itemToContentValues(stick);
				values.remove(GiveastickContract.Stick.COL_ID);

				result = this.provider.update(StickProviderAdapter.STICK_URI, values, null, null);
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
				result = this.provider.delete(Uri.parse(StickProviderAdapter.STICK_URI + "/" + this.entity.getId()), null, null);

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
				result = this.provider.delete(StickProviderAdapter.STICK_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}
}
