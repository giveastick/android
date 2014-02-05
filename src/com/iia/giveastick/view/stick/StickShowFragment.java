/**************************************************************************
 * StickShowFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.stick;

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
import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.harmony.util.DateUtils;
import com.iia.giveastick.harmony.view.DeleteDialog;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.view.MultiLoader;
import com.iia.giveastick.harmony.view.MultiLoader.UriLoadedCallback;
import com.iia.giveastick.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import com.iia.giveastick.provider.utils.StickProviderUtils;
import com.iia.giveastick.provider.StickProviderAdapter;
import com.iia.giveastick.provider.GiveastickContract;

/** Stick show fragment.
 *
 * This fragment gives you an interface to show a Stick.
 * 
 * @see android.app.Fragment
 */
public class StickShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback {
	/** Model data. */
	protected Stick model;

	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;

	/* This entity's fields views */
	/** giver View. */
	protected TextView giverView;
	/** receiver View. */
	protected TextView receiverView;
	/** voteSticks View. */
	protected TextView voteSticksView;
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no Stick. */
	protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
		this.giverView =
			(TextView) view.findViewById(
					R.id.stick_giver);
		this.receiverView =
			(TextView) view.findViewById(
					R.id.stick_receiver);
		this.voteSticksView =
			(TextView) view.findViewById(
					R.id.stick_votesticks);

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.stick_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.stick_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);


		if (this.model.getGiver() != null) {
			this.giverView.setText(
					String.valueOf(this.model.getGiver().getId()));
		}
		if (this.model.getReceiver() != null) {
			this.receiverView.setText(
					String.valueOf(this.model.getReceiver().getId()));
		}
		if (this.model.getVoteSticks() != null) {
			String voteSticksValue = "";
			for (VoteStick item : this.model.getVoteSticks()) {
				voteSticksValue += item.getId() + ",";
			}
			this.voteSticksView.setText(voteSticksValue);
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
        				R.layout.fragment_stick_show,
        				container,
        				false);
        
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Stick) intent.getParcelableExtra(Stick.PARCEL));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The Stick to get the data from.
	 */
	public void update(Stick item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					StickProviderAdapter.STICK_URI 
					+ "/" 
					+ this.model.getId();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					StickShowFragment.this.onStickLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/giver"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					StickShowFragment.this.onGiverLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/receiver"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					StickShowFragment.this.onReceiverLoaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			loader.addUri(Uri.parse(baseUri + "/votesticks"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					StickShowFragment.this.onVoteSticksLoaded(c);
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
	public void onStickLoaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			
			GiveastickContract.Stick.cursorToItem(
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
	public void onGiverLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					GiveastickContract.User.cursorToItem(c);
					this.loadData();
				}
			} else {
				this.model.setGiver(null);
					this.loadData();
			}
		}
	}
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void onReceiverLoaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
					GiveastickContract.User.cursorToItem(c);
					this.loadData();
				}
			} else {
				this.model.setReceiver(null);
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
	 * Calls the StickEditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									StickEditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable("Stick", this.model);
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
		private Stick item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build StickSQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final Stick item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new StickProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				StickShowFragment.this.onPostDelete();
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

