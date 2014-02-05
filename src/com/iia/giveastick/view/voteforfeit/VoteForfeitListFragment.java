/**************************************************************************
 * VoteForfeitListFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.voteforfeit;

import java.util.ArrayList;

import com.iia.giveastick.criterias.VoteForfeitCriterias;
import com.iia.giveastick.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.iia.giveastick.provider.VoteForfeitProviderAdapter;
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
import com.iia.giveastick.entity.VoteForfeit;

/** VoteForfeit list fragment.
 *
 * This fragment gives you an interface to list all your VoteForfeits.
 *
 * @see android.app.Fragment
 */
public class VoteForfeitListFragment
		extends HarmonyListFragment<VoteForfeit>
		implements CrudCreateMenuInterface {

	/** The adapter which handles list population. */
	protected VoteForfeitListAdapter mAdapter;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		final View view =
				inflater.inflate(R.layout.fragment_voteforfeit_list,
						null);

		this.initializeHackCustomList(view,
				R.id.voteforfeitProgressLayout,
				R.id.voteforfeitListContainer);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		this.setEmptyText(
				getString(
						R.string.voteforfeit_empty_list));

		// Create an empty adapter we will use to display the loaded data.
		((PinnedHeaderListView) this.getListView())
					.setPinnedHeaderEnabled(false);
		this.mAdapter = new VoteForfeitListAdapter(this.getActivity());

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
		VoteForfeitCriterias crit = null;
		if (bundle != null) {
			crit = (VoteForfeitCriterias) bundle.get(
						VoteForfeitCriterias.PARCELABLE);
		}

		if (crit != null) {
			result = new VoteForfeitListLoader(this.getActivity(),
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
				GiveastickContract.VoteForfeit.ALIASED_COLS,
				crit,
				null);
		} else {
			result = new VoteForfeitListLoader(this.getActivity(),
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI,
				GiveastickContract.VoteForfeit.ALIASED_COLS,
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
				VoteForfeitProviderAdapter.VOTEFORFEIT_URI);

		ArrayList<VoteForfeit> users = GiveastickContract.VoteForfeit.cursorToItems(data);
		this.mAdapter.setNotifyOnChange(false);
		this.mAdapter.setData(
				new VoteForfeitListAdapter
					.VoteForfeitSectionIndexer(users));
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
					VoteForfeitCreateActivity.class);
		this.startActivity(intent);
	}

}
