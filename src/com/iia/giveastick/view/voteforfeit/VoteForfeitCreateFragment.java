/**************************************************************************
 * VoteForfeitCreateFragment.java, giveastick Android
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
import com.iia.giveastick.entity.VoteForfeit;
import com.iia.giveastick.entity.UserGroup;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.widget.DateWidget;

import com.iia.giveastick.harmony.widget.SingleEntityWidget;
import com.iia.giveastick.menu.SaveMenuWrapper.SaveMenuInterface;
import com.iia.giveastick.provider.utils.VoteForfeitProviderUtils;
import com.iia.giveastick.provider.utils.UserGroupProviderUtils;

/**
 * VoteForfeit create fragment.
 *
 * This fragment gives you an interface to create a VoteForfeit.
 */
public class VoteForfeitCreateFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected VoteForfeit model = new VoteForfeit();

	/** Fields View. */
	/** The userGroup chooser component. */
	protected SingleEntityWidget userGroupWidget;
	/** The userGroup Adapter. */
	protected SingleEntityWidget.EntityAdapter<UserGroup> 
				userGroupAdapter;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.userGroupAdapter = 
				new SingleEntityWidget.EntityAdapter<UserGroup>() {
			@Override
			public String entityToString(UserGroup item) {
				return String.valueOf(item.getId());
			}
		};
		this.userGroupWidget =
			(SingleEntityWidget) view.findViewById(R.id.voteforfeit_usergroup_button);
		this.userGroupWidget.setAdapter(this.userGroupAdapter);
	}

	/** Load data from model to fields view. */
	public void loadData() {


		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
	public void saveData() {

		this.model.setUserGroup(this.userGroupAdapter.getSelectedItem());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (this.userGroupAdapter.getSelectedItem() == null) {
			error = R.string.voteforfeit_usergroup_invalid_field_error;
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
				R.layout.fragment_voteforfeit_create,
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
		private final VoteForfeit entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final VoteForfeitCreateFragment fragment,
				final VoteForfeit entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.voteforfeit_progress_save_title),
					this.ctx.getString(
							R.string.voteforfeit_progress_save_message));
		}

		@Override
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new VoteForfeitProviderUtils(this.ctx).insert(
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
								R.string.voteforfeit_error_create));
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
		private VoteForfeitCreateFragment fragment;
		/** userGroup list. */
		private ArrayList<UserGroup> userGroupList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final VoteForfeitCreateFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.voteforfeit_progress_load_relations_title),
					this.ctx.getString(
							R.string.voteforfeit_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.userGroupList = 
				new UserGroupProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.userGroupAdapter.loadData(this.userGroupList);
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
