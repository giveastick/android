/**************************************************************************
 * HomeActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.view.user.UserListActivity;
import com.iia.giveastick.view.usergroup.UserGroupListActivity;
import com.iia.giveastick.view.voteforfeit.VoteForfeitListActivity;
import com.iia.giveastick.view.votestick.VoteStickListActivity;
import com.iia.giveastick.view.stick.StickListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
		implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		this.initButtons();
	}

	/**
	 * Initialize the buttons click listeners.
	 */
	private void initButtons() {
		this.findViewById(R.id.user_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.usergroup_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.voteforfeit_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.votestick_list_button)
						.setOnClickListener(this);
		this.findViewById(R.id.stick_list_button)
						.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.user_list_button:
				intent = new Intent(this,
						UserListActivity.class);
				break;

			case R.id.usergroup_list_button:
				intent = new Intent(this,
						UserGroupListActivity.class);
				break;

			case R.id.voteforfeit_list_button:
				intent = new Intent(this,
						VoteForfeitListActivity.class);
				break;

			case R.id.votestick_list_button:
				intent = new Intent(this,
						VoteStickListActivity.class);
				break;

			case R.id.stick_list_button:
				intent = new Intent(this,
						StickListActivity.class);
				break;

			default:
				intent = null;
				break;
		}

		if (intent != null) {
			this.startActivity(intent);
		}
	}

}
