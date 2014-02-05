/**************************************************************************
 * StickEditFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.stick;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.iia.giveastick.R;
import com.iia.giveastick.entity.Stick;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.VoteStick;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.widget.DateWidget;
import com.iia.giveastick.harmony.widget.MultiEntityWidget;
import com.iia.giveastick.harmony.widget.SingleEntityWidget;
import com.iia.giveastick.menu.SaveMenuWrapper.SaveMenuInterface;
import com.iia.giveastick.provider.StickProviderAdapter;
import com.iia.giveastick.provider.utils.StickProviderUtils;
import com.iia.giveastick.provider.utils.UserProviderUtils;
import com.iia.giveastick.provider.utils.VoteStickProviderUtils;
import com.iia.giveastick.data.VoteStickSQLiteAdapter;
import com.iia.giveastick.provider.GiveastickContract;

/** Stick create fragment.
 *
 * This fragment gives you an interface to edit a Stick.
 *
 * @see android.app.Fragment
 */
public class StickEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected Stick model = new Stick();

	/** curr.fields View. */
	/** The giver chooser component. */
	protected SingleEntityWidget giverWidget;
	/** The giver Adapter. */
	protected SingleEntityWidget.EntityAdapter<User>
			giverAdapter;
	/** The receiver chooser component. */
	protected SingleEntityWidget receiverWidget;
	/** The receiver Adapter. */
	protected SingleEntityWidget.EntityAdapter<User>
			receiverAdapter;
	/** The voteSticks chooser component. */
	protected MultiEntityWidget voteSticksWidget;
	/** The voteSticks Adapter. */
	protected MultiEntityWidget.EntityAdapter<VoteStick>
			voteSticksAdapter;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.giverAdapter =
				new SingleEntityWidget.EntityAdapter<User>() {
			@Override
			public String entityToString(User item) {
				return String.valueOf(item.getId());
			}
		};
		this.giverWidget =
			(SingleEntityWidget) view.findViewById(R.id.stick_giver_button);
		this.giverWidget.setAdapter(this.giverAdapter);
		this.receiverAdapter =
				new SingleEntityWidget.EntityAdapter<User>() {
			@Override
			public String entityToString(User item) {
				return String.valueOf(item.getId());
			}
		};
		this.receiverWidget =
			(SingleEntityWidget) view.findViewById(R.id.stick_receiver_button);
		this.receiverWidget.setAdapter(this.receiverAdapter);
		this.voteSticksAdapter =
				new MultiEntityWidget.EntityAdapter<VoteStick>() {
			@Override
			public String entityToString(VoteStick item) {
				return String.valueOf(item.getId());
			}
		};
		this.voteSticksWidget = (MultiEntityWidget) view.findViewById(
						R.id.stick_votesticks_button);
		this.voteSticksWidget.setAdapter(this.voteSticksAdapter);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {


		new LoadTask(this).execute();
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setGiver(this.giverAdapter.getSelectedItem());

		this.model.setReceiver(this.receiverAdapter.getSelectedItem());

		this.model.setVoteSticks(this.voteSticksAdapter.getCheckedItems());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (this.giverAdapter.getSelectedItem() == null) {
			error = R.string.stick_giver_invalid_field_error;
		}
		if (this.receiverAdapter.getSelectedItem() == null) {
			error = R.string.stick_receiver_invalid_field_error;
		}
		if (this.voteSticksAdapter.getCheckedItems().isEmpty()) {
			error = R.string.stick_votesticks_invalid_field_error;
		}
	
		if (error > 0) {
			Toast.makeText(this.getActivity(),
				this.getActivity().getString(error),
				Toast.LENGTH_SHORT).show();
		}
		return error == 0;
	}
	@Override
	public View onCreateView(
				LayoutInflater inflater,
				ViewGroup container,
				Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		final View view =
				inflater.inflate(R.layout.fragment_stick_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (Stick) intent.getParcelableExtra(
				Stick.PARCEL);

		this.initializeComponent(view);
		this.loadData();

		return view;
	}

	/**
	 * This class will update the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class EditTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Entity to update. */
		private final Stick entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final StickEditFragment fragment,
					final Stick entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.stick_progress_save_title),
					this.ctx.getString(
							R.string.stick_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new StickProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("StickEditFragment", e.getMessage());
			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result > 0) {
				final HarmonyFragmentActivity activity =
						(HarmonyFragmentActivity) this.ctx;
				activity.setResult(HarmonyFragmentActivity.RESULT_OK);
				activity.finish();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(this.ctx.getString(
						R.string.stick_error_create));
				builder.setPositiveButton(
						this.ctx.getString(android.R.string.yes),
						new Dialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
																int which) {

							}
						});
				builder.show();
			}

			this.progress.dismiss();
		}
	}


	/**
	 * This class will save the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class LoadTask extends AsyncTask<Void, Void, Void> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Progress Dialog. */
		private ProgressDialog progress;
		/** Fragment. */
		private StickEditFragment fragment;
		/** giver list. */
		private ArrayList<User> giverList;
		/** receiver list. */
		private ArrayList<User> receiverList;
		/** voteSticks list. */
		private ArrayList<VoteStick> voteSticksList;
	/** voteSticks list. */
		private ArrayList<VoteStick> associatedVoteSticksList;

		/**
		 * Constructor of the task.
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final StickEditFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
				this.ctx.getString(
					R.string.stick_progress_load_relations_title),
				this.ctx.getString(
					R.string.stick_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.giverList = 
				new UserProviderUtils(this.ctx).queryAll();
			this.receiverList = 
				new UserProviderUtils(this.ctx).queryAll();
			this.voteSticksList = 
				new VoteStickProviderUtils(this.ctx).queryAll();
			Uri voteSticksUri = StickProviderAdapter.STICK_URI;
			voteSticksUri = Uri.withAppendedPath(voteSticksUri, 
									String.valueOf(this.fragment.model.getId()));
			voteSticksUri = Uri.withAppendedPath(voteSticksUri, "voteSticks");
			Cursor voteSticksCursor = 
					this.ctx.getContentResolver().query(
							voteSticksUri,
							new String[]{GiveastickContract.VoteStick.ALIASED_COL_ID},
							null,
							null, 
							null);
			
			if (voteSticksCursor != null && voteSticksCursor.getCount() > 0) {
				this.associatedVoteSticksList = new ArrayList<VoteStick>();
				while (voteSticksCursor.moveToNext()) {
					int voteSticksId = voteSticksCursor.getInt(
							voteSticksCursor.getColumnIndex(
									GiveastickContract.VoteStick.COL_ID));
					for (VoteStick voteSticks : this.voteSticksList) {
						if (voteSticks.getId() == voteSticksId) {
							this.associatedVoteSticksList.add(voteSticks);
						}
					}
				}
				voteSticksCursor.close();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.onGiverLoaded(this.giverList);
			this.fragment.onReceiverLoaded(this.receiverList);
			this.fragment.model.setVoteSticks(this.associatedVoteSticksList);
			this.fragment.onVoteSticksLoaded(this.voteSticksList);

			this.progress.dismiss();
		}
	}

	@Override
	public void onClickSave() {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}

	/**
	 * Called when giver have been loaded.
	 * @param items The loaded items
	 */
	protected void onGiverLoaded(ArrayList<User> items) {
		this.giverAdapter.loadData(items);
		
		for (User item : items) {
			if (item.getId() == this.model.getGiver().getId()) {
				this.giverAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when receiver have been loaded.
	 * @param items The loaded items
	 */
	protected void onReceiverLoaded(ArrayList<User> items) {
		this.receiverAdapter.loadData(items);
		
		for (User item : items) {
			if (item.getId() == this.model.getReceiver().getId()) {
				this.receiverAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when voteSticks have been loaded.
	 * @param items The loaded items
	 */
	protected void onVoteSticksLoaded(ArrayList<VoteStick> items) {
		this.voteSticksAdapter.loadData(items);
		this.voteSticksAdapter.setCheckedItems(this.model.getVoteSticks());
	}
}
