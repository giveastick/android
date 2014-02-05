/**************************************************************************
 * VoteForfeitShowFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.voteforfeit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iia.giveastick.R;
import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.harmony.util.DateUtils;
import com.iia.giveastick.harmony.view.DeleteDialog;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.view.MultiLoader;
import com.iia.giveastick.harmony.view.MultiLoader.UriLoadedCallback;
import com.iia.giveastick.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.iia.giveastick.provider.utils.VoteForfeitProviderUtils;
import com.iia.giveastick.provider.VoteForfeitProviderAdapter;
import com.iia.giveastick.provider.GiveastickContract;

/** VoteForfeit show fragment.
 *
 * This fragment gives you an interface to show a VoteForfeit.
 * 
 * @see android.app.Fragment
 */
public class VoteForfeitShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected VoteForfeit model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** userGroup View. */
	protected TextView userGroupView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no VoteForfeit. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.userGroupView =
			(TextView) view.findViewById(
					R.id.voteforfeit_usergroup);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.voteforfeit_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.voteforfeit_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getUserGroup() != null) {
			this.userGroupView.setText(
					String.valueOf(this.model.getUserGroup().getId()));
		}
		} else {
    		this.dataLayout.setVisibility(View.GONE);
    		this.emptyText.setVisibility(View.VISIBLE);
    	}
    }

    @Override
    public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

    	// Inflate the layout for this fragment
        final View view =
        		inflater.inflate(
        				R.layout.fragment_voteforfeit_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((VoteForfeit) intent.getParcelableExtra(VoteForfeit.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The VoteForfeit to get the data from.
	 */
	public void update(VoteForfeit item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					VoteForfeitProviderAdapter.VOTEFORFEIT_URI 
					+ "/" 
					+ this.model.getId();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					VoteForfeitShowFragment.this.onVoteForfeitLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/usergroup"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					VoteForfeitShowFragment.this.onUserGroupLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.init();
		}
    }

	/**
	 * Called when the entity has been loaded.
	 * 
	 * @param c The cursor of this entity
	 */
	public void onVoteForfeitLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			
			GiveastickContract.VoteForfeit.cursorToItem(
						c,
						this.model);
			this.loadData();
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onUserGroupLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					GiveastickContract.UserGroup.cursorToItem(c);
					this.loadData();
				}
			} else {
				this.model.setUserGroup(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the VoteForfeitEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									VoteForfeitEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("VoteForfeit", this.model);
		intent.putExtras(extras);

		this.getActivity().startActivity(intent);
	}

	/**
	 * Shows a confirmation dialog.
	 */
	@Override
	public void onClickDelete() {
		new DeleteDialog(this.getActivity(), this).show();
	}

	@Override
	public void onDeleteDialogClose(boolean ok) {
		if (ok) {
			new DeleteTask(this.getActivity(), this.model).execute();
		}
	}
	
	/** 
	 * Called when delete task is done.
	 */	
	public void onPostDelete() {
		if (this.deleteCallback != null) {
			this.deleteCallback.onItemDeleted();
		}
	}

	/**
	 * This class will remove the entity into the DB.
	 * It runs asynchronously.
	 */
	private class DeleteTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private Context ctx;
		/** Entity to delete. */
		private VoteForfeit item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build VoteForfeitSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final VoteForfeit item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new VoteForfeitProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				VoteForfeitShowFragment.this.onPostDelete();
			}
			super.onPostExecute(result);
		}
		
		

	}
	
	/**
	 * Callback for item deletion.
	 */ 
	public interface DeleteCallback {
		/** Called when current item has been deleted. */
		void onItemDeleted();
	}
}

