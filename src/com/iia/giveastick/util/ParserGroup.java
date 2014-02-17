/**
 * 
 */
package com.iia.giveastick.util;

import java.security.acl.Group;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.UserGroup;

/**
 * @author maxime
 *
 */
public class ParserGroup {
	private static final String TAG = "ParserGroup";
	
	public static UserGroup getGroup(JSONObject jsonResult, Context context){
		UserGroup result = null;
		
		try {
			boolean error = !jsonResult.getBoolean("success");
			
			if(!error)
			{	
				JSONObject jsonGroup = jsonResult.getJSONObject("group");
				int groupId = jsonGroup.getInt("id");
				String groupTag = jsonGroup.getString("tag");
			
				result = new UserGroup();
				result.setId(groupId);
				result.setTag(groupTag);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
			Log.e(TAG, "Error while parsing JSON Object to Group object");
		}
		
		return result;
	}
}
