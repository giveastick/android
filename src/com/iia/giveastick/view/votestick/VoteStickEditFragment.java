/**************************************************************************
 * VoteStickEditFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.votestick;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.sqlite.SQLiteException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.iia.giveastick.R;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.Stick;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.widget.DateWidget;

import com.iia.giveastick.harmony.widget.SingleEntityWidget;
import com.iia.giveastick.menu.SaveMenuWrapper.SaveMenuInterface;

import com.iia.giveastick.provider.utils.VoteStickProviderUtils;
import com.iia.giveastick.provider.utils.UserProviderUtils;
import com.iia.giveastick.provider.utils.StickProviderUtils;
import com.iia.giveastick.provider.GiveastickContract;

/** VoteStick create fragment.
 *
 * This fragment gives you an interface to edit a VoteStick.
 *
 * @see android.app.Fragment
 */
public class VoteStickEditFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected VoteStick model = new VoteStick();

	/** curr.fields View. */
	/** answer View. */
	protected CheckBox answerView;
	/** The user chooser component. */
	protected SingleEntityWidget userWidget;
	/** The user Adapter. */
	protected SingleEntityWidget.EntityAdapter<User>
			userAdapter;
	/** The stick chooser component. */
	protected SingleEntityWidget stickWidget;
	/** The stick Adapter. */
	protected SingleEntityWidget.EntityAdapter<Stick>
			stickAdapter;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		this.answerView = (CheckBox) view.findViewById(
				R.id.votestick_answer);
		this.userAdapter =
				new SingleEntityWidget.EntityAdapter<User>() {
			@Override
			public String entityToString(User item) {
				return String.valueOf(item.getId());
			}
		};
		this.userWidget =
			(SingleEntityWidget) view.findViewById(R.id.votestick_user_button);
		this.userWidget.setAdapter(this.userAdapter);
		this.stickAdapter =
				new SingleEntityWidget.EntityAdapter<Stick>() {
			@Override
			public String entityToString(Stick item) {
				return String.valueOf(item.getId());
			}
		};
		this.stickWidget =
			(SingleEntityWidget) view.findViewById(R.id.votestick_stick_button);
		this.stickWidget.setAdapter(this.stickAdapter);
	}

	/** Load data from model to curr.fields view. */
	public void loadData() {

		this.answerView.setChecked(this.model.isAnswer());

		new LoadTask(this).execute();
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {

		this.model.setAnswer(this.answerView.isChecked());

		this.model.setUser(this.userAdapter.getSelectedItem());

		this.model.setStick(this.stickAdapter.getSelectedItem());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (this.userAdapter.getSelectedItem() == null) {
			error = R.string.votestick_user_invalid_field_error;
		}
		if (this.stickAdapter.getSelectedItem() == null) {
			error = R.string.votestick_stick_invalid_field_error;
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
				inflater.inflate(R.layout.fragment_votestick_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model = (VoteStick) intent.getParcelableExtra(
				VoteStick.PARCEL);

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
		private final VoteStick entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final VoteStickEditFragment fragment,
					final VoteStick entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.votestick_progress_save_title),
					this.ctx.getString(
							R.string.votestick_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			try {
				result = new VoteStickProviderUtils(this.ctx).update(
					this.entity);
			} catch (SQLiteException e) {
				Log.e("VoteStickEditFragment", e.getMessage());
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
						R.string.votestick_error_create));
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
		private VoteStickEditFragment fragment;
		/** user list. */
		private ArrayList<User> userList;
		/** stick list. */
		private ArrayList<Stick> stickList;

		/**
		 * Constructor of the task.
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final VoteStickEditFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
				this.ctx.getString(
					R.string.votestick_progress_load_relations_title),
				this.ctx.getString(
					R.string.votestick_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.userList = 
				new UserProviderUtils(this.ctx).queryAll();
			this.stickList = 
				new StickProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.onUserLoaded(this.userList);
			this.fragment.onStickLoaded(this.stickList);

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
	 * Called when user have been loaded.
	 * @param items The loaded items
	 */
	protected void onUserLoaded(ArrayList<User> items) {
		this.userAdapter.loadData(items);
		
		for (User item : items) {
			if (item.getId() == this.model.getUser().getId()) {
				this.userAdapter.selectItem(item);
			}
		}
	}
	/**
	 * Called when stick have been loaded.
	 * @param items The loaded items
	 */
	protected void onStickLoaded(ArrayList<Stick> items) {
		this.stickAdapter.loadData(items);
		
		for (Stick item : items) {
			if (item.getId() == this.model.getStick().getId()) {
				this.stickAdapter.selectItem(item);
			}
		}
	}
}
