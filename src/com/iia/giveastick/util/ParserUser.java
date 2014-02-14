/**
 * 
 */
package com.iia.giveastick.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.iia.giveastick.data.UserGroupSQLiteAdapter;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.UserGroup;

/**
 * @author maxime
 *
 */
public class ParserUser {
	private static final String TAG = "ParserUser";
	
	public static User getUser(JSONObject jsonResult, Context context){
		User result = null;
		
		try {
			boolean error = jsonResult.getBoolean("success");
			
			if(!error)
			{	
				JSONObject jsonUser = jsonResult.getJSONObject("user");
				String userEmail = jsonUser.getString("email");
				String userGroupTag = jsonUser.getString("group");
				
				UserGroupSQLiteAdapter daoUser = new UserGroupSQLiteAdapter(context);
				UserGroup userGroup = daoUser.getByTag(userGroupTag);
				
				if(userGroup != null)
				{
					result = new User();
					result.setEmail(userEmail);
					result.setUserGroup(userGroup);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
			Log.e(TAG, "Error while parsing JSON Object to User object");
		}
		
		return result;
	}
}
