/**************************************************************************
 * VoteStickCreateFragment.java, giveastick Android
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

/**
 * VoteStick create fragment.
 *
 * This fragment gives you an interface to create a VoteStick.
 */
public class VoteStickCreateFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected VoteStick model = new VoteStick();

	/** Fields View. */
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

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.answerView =
				(CheckBox) view.findViewById(R.id.votestick_answer);
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

	/** Load data from model to fields view. */
	public void loadData() {

		this.answerView.setChecked(this.model.isAnswer());

		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
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
		final View view = inflater.inflate(
				R.layout.fragment_votestick_create,
				container,
				false);

		this.initializeComponent(view);
		this.loadData();
		return view;
	}

	/**
	 * This class will save the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class CreateTask extends AsyncTask<Void, Void, Uri> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Entity to persist. */
		private final VoteStick entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final VoteStickCreateFragment fragment,
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
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new VoteStickProviderUtils(this.ctx).insert(
						this.entity);

			return result;
		}

		@Override
		protected void onPostExecute(Uri result) {
			super.onPostExecute(result);
			if (result != null) {
				final HarmonyFragmentActivity activity =
										 (HarmonyFragmentActivity) this.ctx;
				activity.finish();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(
						this.ctx.getString(
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
		private VoteStickCreateFragment fragment;
		/** user list. */
		private ArrayList<User> userList;
		/** stick list. */
		private ArrayList<Stick> stickList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final VoteStickCreateFragment fragment) {
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
			this.fragment.userAdapter.loadData(this.userList);
			this.fragment.stickAdapter.loadData(this.stickList);
			this.progress.dismiss();
		}
	}

	@Override
	public void onClickSave() {
		if (this.validateData()) {
			this.saveData();
			new CreateTask(this, this.model).execute();
		}
	}
}
