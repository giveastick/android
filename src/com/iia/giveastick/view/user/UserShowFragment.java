/**************************************************************************
 * UserShowFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.user;

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
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.harmony.util.DateUtils;
import com.iia.giveastick.harmony.view.DeleteDialog;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.view.MultiLoader;
import com.iia.giveastick.harmony.view.MultiLoader.UriLoadedCallback;
import com.iia.giveastick.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.iia.giveastick.provider.utils.UserProviderUtils;
import com.iia.giveastick.provider.UserProviderAdapter;
import com.iia.giveastick.provider.GiveastickContract;

/** User show fragment.
 *
 * This fragment gives you an interface to show a User.
 * 
 * @see android.app.Fragment
 */
public class UserShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected User model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** email View. */
	protected TextView emailView;
	/** password View. */
	protected TextView passwordView;
	/** nickname View. */
	protected TextView nicknameView;
	/** userGroup View. */
	protected TextView userGroupView;
	/** voteSticks View. */
	protected TextView voteSticksView;
	/** givenSticks View. */
	protected TextView givenSticksView;
	/** receivedSticks View. */
	protected TextView receivedSticksView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no User. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.emailView =
			(TextView) view.findViewById(
					R.id.user_email);
		this.passwordView =
			(TextView) view.findViewById(
					R.id.user_password);
		this.nicknameView =
			(TextView) view.findViewById(
					R.id.user_nickname);
		this.userGroupView =
			(TextView) view.findViewById(
					R.id.user_usergroup);
		this.voteSticksView =
			(TextView) view.findViewById(
					R.id.user_votesticks);
		this.givenSticksView =
			(TextView) view.findViewById(
					R.id.user_givensticks);
		this.receivedSticksView =
			(TextView) view.findViewById(
					R.id.user_receivedsticks);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.user_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.user_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getEmail() != null) {
			this.emailView.setText(this.model.getEmail());
		}
		if (this.model.getPassword() != null) {
			this.passwordView.setText(this.model.getPassword());
		}
		if (this.model.getNickname() != null) {
			this.nicknameView.setText(this.model.getNickname());
		}
		if (this.model.getUserGroup() != null) {
			this.userGroupView.setText(
					String.valueOf(this.model.getUserGroup().getId()));
		}
		if (this.model.getVoteSticks() != null) {
			String voteSticksValue = "";
			for (VoteStick item : this.model.getVoteSticks()) {
				voteSticksValue += item.getId() + ",";
			}
			this.voteSticksView.setText(voteSticksValue);
		}
		if (this.model.getGivenSticks() != null) {
			String givenSticksValue = "";
			for (Stick item : this.model.getGivenSticks()) {
				givenSticksValue += item.getId() + ",";
			}
			this.givenSticksView.setText(givenSticksValue);
		}
		if (this.model.getReceivedSticks() != null) {
			String receivedSticksValue = "";
			for (Stick item : this.model.getReceivedSticks()) {
				receivedSticksValue += item.getId() + ",";
			}
			this.receivedSticksView.setText(receivedSticksValue);
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
        				R.layout.fragment_user_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((User) intent.getParcelableExtra(User.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The User to get the data from.
	 */
	public void update(User item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					UserProviderAdapter.USER_URI 
					+ "/" 
					+ this.model.getId();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserShowFragment.this.onUserLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/usergroup"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserShowFragment.this.onUserGroupLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/votesticks"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserShowFragment.this.onVoteSticksLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/givensticks"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserShowFragment.this.onGivenSticksLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/receivedsticks"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					UserShowFragment.this.onReceivedSticksLoaded(c);
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
	public void onUserLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			
			GiveastickContract.User.cursorToItem(
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
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onVoteSticksLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
			GiveastickContract.VoteStick.cursorToItem(c);
			this.loadData();
			} else {
				this.model.setVoteSticks(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onGivenSticksLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
			GiveastickContract.Stick.cursorToItem(c);
			this.loadData();
			} else {
				this.model.setGivenSticks(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onReceivedSticksLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
			GiveastickContract.Stick.cursorToItem(c);
			this.loadData();
			} else {
				this.model.setReceivedSticks(null);
					this.loadData();
			}
		}
	}

	/**
	 * Calls the UserEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									UserEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("User", this.model);
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
		private User item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build UserSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final User item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new UserProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				UserShowFragment.this.onPostDelete();
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

