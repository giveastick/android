/**************************************************************************
 * UserGroupCreateFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.usergroup;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.iia.giveastick.R;
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.VoteForfeit;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.widget.MultiEntityWidget;
import com.iia.giveastick.menu.SaveMenuWrapper.SaveMenuInterface;
import com.iia.giveastick.provider.utils.UserGroupProviderUtils;
import com.iia.giveastick.provider.utils.UserProviderUtils;
import com.iia.giveastick.provider.utils.VoteForfeitProviderUtils;

/**
 * UserGroup create fragment.
 *
 * This fragment gives you an interface to create a UserGroup.
 */
public class UserGroupCreateFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected UserGroup model = new UserGroup();

	/** Fields View. */
	/** tag View. */
	protected EditText tagView;
	/** The users chooser component. */
	protected MultiEntityWidget usersWidget;
	/** The users Adapter. */
	protected MultiEntityWidget.EntityAdapter<User> 
				usersAdapter;
	/** The voteForfeits chooser component. */
	protected MultiEntityWidget voteForfeitsWidget;
	/** The voteForfeits Adapter. */
	protected MultiEntityWidget.EntityAdapter<VoteForfeit> 
				voteForfeitsAdapter;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.tagView =
			(EditText) view.findViewById(R.id.usergroup_tag);
		this.usersAdapter = 
				new MultiEntityWidget.EntityAdapter<User>() {
			@Override
			public String entityToString(User item) {
				return String.valueOf(item.getId());
			}
		};
		this.usersWidget =
			(MultiEntityWidget) view.findViewById(R.id.usergroup_users_button);
		this.usersWidget.setAdapter(this.usersAdapter);
		this.voteForfeitsAdapter = 
				new MultiEntityWidget.EntityAdapter<VoteForfeit>() {
			@Override
			public String entityToString(VoteForfeit item) {
				return String.valueOf(item.getId());
			}
		};
		this.voteForfeitsWidget =
			(MultiEntityWidget) view.findViewById(R.id.usergroup_voteforfeits_button);
		this.voteForfeitsWidget.setAdapter(this.voteForfeitsAdapter);
	}

	/** Load data from model to fields view. */
	public void loadData() {

		if (this.model.getTag() != null) {
			this.tagView.setText(this.model.getTag());
		}

		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
	public void saveData() {

		this.model.setTag(this.tagView.getEditableText().toString());

		this.model.setUsers(this.usersAdapter.getCheckedItems());

		this.model.setVoteForfeits(this.voteForfeitsAdapter.getCheckedItems());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (Strings.isNullOrEmpty(
					this.tagView.getText().toString().trim())) {
			error = R.string.usergroup_tag_invalid_field_error;
		}
		if (this.usersAdapter.getCheckedItems().isEmpty()) {
			error = R.string.usergroup_users_invalid_field_error;
		}
		if (this.voteForfeitsAdapter.getCheckedItems().isEmpty()) {
			error = R.string.usergroup_voteforfeits_invalid_field_error;
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
				R.layout.fragment_usergroup_create,
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
		private final UserGroup entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final UserGroupCreateFragment fragment,
				final UserGroup entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.usergroup_progress_save_title),
					this.ctx.getString(
							R.string.usergroup_progress_save_message));
		}

		@Override
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new UserGroupProviderUtils(this.ctx).insert(
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
								R.string.usergroup_error_create));
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
		private UserGroupCreateFragment fragment;
		/** users list. */
		private ArrayList<User> usersList;
		/** voteForfeits list. */
		private ArrayList<VoteForfeit> voteForfeitsList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final UserGroupCreateFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.usergroup_progress_load_relations_title),
					this.ctx.getString(
							R.string.usergroup_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.usersList = 
				new UserProviderUtils(this.ctx).queryAll();
			this.voteForfeitsList = 
				new VoteForfeitProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.usersAdapter.loadData(this.usersList);
			this.fragment.voteForfeitsAdapter.loadData(this.voteForfeitsList);
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
