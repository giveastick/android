/**************************************************************************
 * UserGroupShowFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.usergroup;

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
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.harmony.view.DeleteDialog;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.view.MultiLoader;
import com.iia.giveastick.harmony.view.MultiLoader.UriLoadedCallback;
import com.iia.giveastick.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.iia.giveastick.provider.utils.UserGroupProviderUtils;
import com.iia.giveastick.provider.UserGroupProviderAdapter;
import com.iia.giveastick.provider.GiveastickContract;

/** UserGroup show fragment.
 *
 * This fragment gives you an interface to show a UserGroup.
 * 
 * @see android.app.Fragment
 */
public class UserGroupShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected UserGroup model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** tag View. */
	protected TextView tagView;
	/** users View. */
	protected TextView usersView;
	/** voteForfeits View. */
	protected TextView voteForfeitsView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no UserGroup. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.tagView =
			(TextView) view.findViewById(
					R.id.usergroup_tag);
		this.usersView =
			(TextView) view.findViewById(
					R.id.usergroup_users);
		this.voteForfeitsView =
			(TextView) view.findViewById(
					R.id.usergroup_voteforfeits);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.usergroup_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.usergroup_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getTag() != null) {
			this.tagView.setText(this.model.getTag());
		}
		if (this.model.getUsers() != null) {
			String usersValue = "";
			for (User item : this.model.getUsers()) {
				usersValue += item.getId() + ",";
			}
			this.usersView.setText(usersValue);
		}
		if (this.model.getVoteForfeits() != null) {
			String voteForfeitsValue = "";
			for (VoteForfeit item : this.model.getVoteForfeits()) {
				voteForfeitsValue += item.getId() + ",";
			}
			this.voteForfeitsView.setText(voteForfeitsValue);
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
        				R.layout.fragment_usergroup_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((UserGroup) intent.getParcelableExtra(UserGroup.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The UserGroup to get the data from.
	 */
	public void update(UserGroup item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					UserGroupProviderAdapter.USERGROUP_URI 
					+ "/" 
					+ this.model.getId();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserGroupShowFragment.this.onUserGroupLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/users"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserGroupShowFragment.this.onUsersLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/voteforfeits"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserGroupShowFragment.this.onVoteForfeitsLoaded(c);
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
	public void onUserGroupLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			
			GiveastickContract.UserGroup.cursorToItem(
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
	public void onUsersLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
			GiveastickContract.User.cursorToItem(c);
			this.loadData();
			} else {
				this.model.setUsers(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onVoteForfeitsLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
			GiveastickContract.VoteForfeit.cursorToItem(c);
			this.loadData();
			} else {
				this.model.setVoteForfeits(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the UserGroupEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									UserGroupEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("UserGroup", this.model);
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
		private UserGroup item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build UserGroupSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final UserGroup item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new UserGroupProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				UserGroupShowFragment.this.onPostDelete();
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

