/**************************************************************************
 * StickCreateFragment.java, giveastick Android
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.iia.giveastick.provider.utils.StickProviderUtils;
import com.iia.giveastick.provider.utils.UserProviderUtils;
import com.iia.giveastick.provider.utils.VoteStickProviderUtils;

/**
 * Stick create fragment.
 *
 * This fragment gives you an interface to create a Stick.
 */
public class StickCreateFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected Stick model = new Stick();

	/** Fields View. */
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

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
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
		this.voteSticksWidget =
			(MultiEntityWidget) view.findViewById(R.id.stick_votesticks_button);
		this.voteSticksWidget.setAdapter(this.voteSticksAdapter);
	}

	/** Load data from model to fields view. */
	public void loadData() {


		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
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
		final View view = inflater.inflate(
				R.layout.fragment_stick_create,
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
		private final Stick entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final StickCreateFragment fragment,
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
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new StickProviderUtils(this.ctx).insert(
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
		private StickCreateFragment fragment;
		/** giver list. */
		private ArrayList<User> giverList;
		/** receiver list. */
		private ArrayList<User> receiverList;
		/** voteSticks list. */
		private ArrayList<VoteStick> voteSticksList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final StickCreateFragment fragment) {
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
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.giverAdapter.loadData(this.giverList);
			this.fragment.receiverAdapter.loadData(this.receiverList);
			this.fragment.voteSticksAdapter.loadData(this.voteSticksList);
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
