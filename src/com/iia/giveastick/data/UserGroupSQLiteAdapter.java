/**************************************************************************
 * UserGroupSQLiteAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.data;

import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.iia.giveastick.data.base.UserGroupSQLiteAdapterBase;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.util.GetWsJson;
import com.iia.giveastick.util.ParserGroup;
import com.iia.giveastick.util.ParserUser;
import com.iia.giveastick.util.RestClient.Verb;
import com.iia.giveastick.view.giveastick.UserLoginActivity;

/**
 * UserGroup adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class UserGroupSQLiteAdapter extends UserGroupSQLiteAdapterBase {
	
	Context ctx;

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserGroupSQLiteAdapter(final Context ctx) {
		super(ctx);
		this.ctx = ctx;
	}
	
	public UserGroup getByTag(String tag){
 		UserGroup result = null;
		
 		try{
 			Cursor c = this.mDatabase.query(this.getTableName(), this.getCols(), "tag=?", new String[]{ tag }, null, null, null);
 			c.moveToFirst();
 			
 			if(c.getCount() > 0)
 			{
 				result = this.cursorToItems(c).get(0);
 			}
 			
 		}
 		catch(Exception e)
 		{}
 		
 		if(result == null)
 		{
 			
 			UserGroup newUserGroup  = new WsGroup().execute(new String[]{tag});
 		}

		return result;
	}
	
	private class WsGroup extends AsyncTask<String, Void, UserGroup> {
		@Override
		protected UserGroup doInBackground(String...strings) {
			UserGroup result = null;
			
			try{                                                                                                                                                   
				JSONObject postData = new JSONObject();
				
				JSONObject jsonResult = new GetWsJson("/group/" + strings[0], postData, Verb.GET).getJSONObject();	
				if(!GetWsJson.treatError(UserGroupSQLiteAdapter.this.ctx, jsonResult))
				{
					result = ParserGroup.getGroup(jsonResult, UserGroupSQLiteAdapter.this.ctx);
				}
				
			}
			catch(Exception e)
			{
				Log.e(TAG, "Error while fetching group details");
			}
			
			return result;
		}
	}
}


