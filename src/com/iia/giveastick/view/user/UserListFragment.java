/**************************************************************************
 * UserListFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.user;

import java.util.ArrayList;

import com.iia.giveastick.criterias.UserCriterias;
import com.iia.giveastick.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.iia.giveastick.provider.UserProviderAdapter;
import com.iia.giveastick.provider.GiveastickContract;
import com.iia.giveastick.harmony.view.HarmonyListFragment;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.iia.giveastick.R;
import com.iia.giveastick.entity.User;

/** User list fragment.
 *
 * This fragment gives you an interface to list all your Users.
 *
 * @see android.app.Fragment
 */
public class UserListFragment
		extends HarmonyListFragment<User>
		implements CrudCreateMenuInterface {

	/** The adapter which handles list population. */
	protected UserListAdapter mAdapter;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		final View view =
				inflater.inflate(R.layout.fragment_user_list,
						null);

		this.initializeHackCustomList(view,
				R.id.userProgressLayout,
				R.id.userListContainer);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		this.setEmptyText(
				getString(
						R.string.user_empty_list));

		// Create an empty adapter we will use to display the loaded data.
		((PinnedHeaderListView) this.getListView())
					.setPinnedHeaderEnabled(false);
		this.mAdapter = new UserListAdapter(this.getActivity());

		// Start out with a progress indicator.
		this.setListShown(false);

		// Prepare the loader.  Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		/* Do click action inside your fragment here. */
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		Loader<Cursor> result = null;
		UserCriterias crit = null;
		if (bundle != null) {
			crit = (UserCriterias) bundle.get(
						UserCriterias.PARCELABLE);
		}

		if (crit != null) {
			result = new UserListLoader(this.getActivity(),
				UserProviderAdapter.USER_URI,
				GiveastickContract.User.ALIASED_COLS,
				crit,
				null);
		} else {
			result = new UserListLoader(this.getActivity(),
				UserProviderAdapter.USER_URI,
				GiveastickContract.User.ALIASED_COLS,
				null,
				null,
				null);
		}
		return result;
	}

	@Override
	public void onLoadFinished(
			Loader<Cursor> loader,
			Cursor data) {

		// Set the new data in the adapter.
		data.setNotificationUri(this.getActivity().getContentResolver(),
				UserProviderAdapter.USER_URI);

		ArrayList<User> users = GiveastickContract.User.cursorToItems(data);
		this.mAdapter.setNotifyOnChange(false);
		this.mAdapter.setData(
				new UserListAdapter
					.UserSectionIndexer(users));
		this.mAdapter.setNotifyOnChange(true);
		this.mAdapter.notifyDataSetChanged();
		this.mAdapter.setPinnedPartitionHeadersEnabled(false);
		this.mAdapter.setSectionHeaderDisplayEnabled(false);

		if (this.getListAdapter() == null) {
			this.setListAdapter(this.mAdapter);
		}

		// The list should now be shown.
		if (this.isResumed()) {
			this.setListShown(true);
		} else {
			this.setListShownNoAnimation(true);
		}

		super.onLoadFinished(loader, data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Clear the data in the adapter.
		this.mAdapter.clear();
	}

	@Override
	public void onClickAdd() {
		Intent intent = new Intent(this.getActivity(),
					UserCreateActivity.class);
		this.startActivity(intent);
	}

}
