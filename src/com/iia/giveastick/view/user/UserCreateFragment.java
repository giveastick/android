/**************************************************************************
 * UserCreateFragment.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.user;

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
import com.iia.giveastick.entity.User;
import com.iia.giveastick.entity.UserGroup;
import com.iia.giveastick.entity.VoteStick;
import com.iia.giveastick.entity.Stick;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.harmony.view.HarmonyFragment;
import com.iia.giveastick.harmony.widget.DateWidget;
import com.iia.giveastick.harmony.widget.MultiEntityWidget;
import com.iia.giveastick.harmony.widget.SingleEntityWidget;
import com.iia.giveastick.menu.SaveMenuWrapper.SaveMenuInterface;
import com.iia.giveastick.provider.utils.UserProviderUtils;
import com.iia.giveastick.provider.utils.UserGroupProviderUtils;
import com.iia.giveastick.provider.utils.VoteStickProviderUtils;
import com.iia.giveastick.provider.utils.StickProviderUtils;

/**
 * User create fragment.
 *
 * This fragment gives you an interface to create a User.
 */
public class UserCreateFragment extends HarmonyFragment
			implements SaveMenuInterface {
	/** Model data. */
	protected User model = new User();

	/** Fields View. */
	/** email View. */
	protected EditText emailView;
	/** password View. */
	protected EditText passwordView;
	/** nickname View. */
	protected EditText nicknameView;
	/** The userGroup chooser component. */
	protected SingleEntityWidget userGroupWidget;
	/** The userGroup Adapter. */
	protected SingleEntityWidget.EntityAdapter<UserGroup> 
				userGroupAdapter;
	/** The voteSticks chooser component. */
	protected MultiEntityWidget voteSticksWidget;
	/** The voteSticks Adapter. */
	protected MultiEntityWidget.EntityAdapter<VoteStick> 
				voteSticksAdapter;
	/** The givenSticks chooser component. */
	protected MultiEntityWidget givenSticksWidget;
	/** The givenSticks Adapter. */
	protected MultiEntityWidget.EntityAdapter<Stick> 
				givenSticksAdapter;
	/** The receivedSticks chooser component. */
	protected MultiEntityWidget receivedSticksWidget;
	/** The receivedSticks Adapter. */
	protected MultiEntityWidget.EntityAdapter<Stick> 
				receivedSticksAdapter;

	/** Initialize view of fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		this.emailView =
			(EditText) view.findViewById(R.id.user_email);
		this.passwordView =
			(EditText) view.findViewById(R.id.user_password);
		this.nicknameView =
			(EditText) view.findViewById(R.id.user_nickname);
		this.userGroupAdapter = 
				new SingleEntityWidget.EntityAdapter<UserGroup>() {
			@Override
			public String entityToString(UserGroup item) {
				return String.valueOf(item.getId());
			}
		};
		this.userGroupWidget =
			(SingleEntityWidget) view.findViewById(R.id.user_usergroup_button);
		this.userGroupWidget.setAdapter(this.userGroupAdapter);
		this.voteSticksAdapter = 
				new MultiEntityWidget.EntityAdapter<VoteStick>() {
			@Override
			public String entityToString(VoteStick item) {
				return String.valueOf(item.getId());
			}
		};
		this.voteSticksWidget =
			(MultiEntityWidget) view.findViewById(R.id.user_votesticks_button);
		this.voteSticksWidget.setAdapter(this.voteSticksAdapter);
		this.givenSticksAdapter = 
				new MultiEntityWidget.EntityAdapter<Stick>() {
			@Override
			public String entityToString(Stick item) {
				return String.valueOf(item.getId());
			}
		};
		this.givenSticksWidget =
			(MultiEntityWidget) view.findViewById(R.id.user_givensticks_button);
		this.givenSticksWidget.setAdapter(this.givenSticksAdapter);
		this.receivedSticksAdapter = 
				new MultiEntityWidget.EntityAdapter<Stick>() {
			@Override
			public String entityToString(Stick item) {
				return String.valueOf(item.getId());
			}
		};
		this.receivedSticksWidget =
			(MultiEntityWidget) view.findViewById(R.id.user_receivedsticks_button);
		this.receivedSticksWidget.setAdapter(this.receivedSticksAdapter);
	}

	/** Load data from model to fields view. */
	public void loadData() {

		if (this.model.getEmail() != null) {
			this.emailView.setText(this.model.getEmail());
		}
		if (this.model.getPassword() != null) {
			this.passwordView.setText(this.model.getPassword());
		}
		if (this.model.getNickname() != null) {
			this.nicknameView.setText(this.model.getNickname());
		}

		new LoadTask(this).execute();
	}

	/** Save data from fields view to model. */
	public void saveData() {

		this.model.setEmail(this.emailView.getEditableText().toString());

		this.model.setPassword(this.passwordView.getEditableText().toString());

		this.model.setNickname(this.nicknameView.getEditableText().toString());

		this.model.setUserGroup(this.userGroupAdapter.getSelectedItem());

		this.model.setVoteSticks(this.voteSticksAdapter.getCheckedItems());

		this.model.setGivenSticks(this.givenSticksAdapter.getCheckedItems());

		this.model.setReceivedSticks(this.receivedSticksAdapter.getCheckedItems());

	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		int error = 0;

		if (Strings.isNullOrEmpty(
					this.emailView.getText().toString().trim())) {
			error = R.string.user_email_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.passwordView.getText().toString().trim())) {
			error = R.string.user_password_invalid_field_error;
		}
		if (Strings.isNullOrEmpty(
					this.nicknameView.getText().toString().trim())) {
			error = R.string.user_nickname_invalid_field_error;
		}
		if (this.userGroupAdapter.getSelectedItem() == null) {
			error = R.string.user_usergroup_invalid_field_error;
		}
		if (this.voteSticksAdapter.getCheckedItems().isEmpty()) {
			error = R.string.user_votesticks_invalid_field_error;
		}
		if (this.givenSticksAdapter.getCheckedItems().isEmpty()) {
			error = R.string.user_givensticks_invalid_field_error;
		}
		if (this.receivedSticksAdapter.getCheckedItems().isEmpty()) {
			error = R.string.user_receivedsticks_invalid_field_error;
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
				R.layout.fragment_user_create,
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
		private final User entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public CreateTask(final UserCreateFragment fragment,
				final User entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.user_progress_save_title),
					this.ctx.getString(
							R.string.user_progress_save_message));
		}

		@Override
		protected Uri doInBackground(Void... params) {
			Uri result = null;

			result = new UserProviderUtils(this.ctx).insert(
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
								R.string.user_error_create));
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
		private UserCreateFragment fragment;
		/** userGroup list. */
		private ArrayList<UserGroup> userGroupList;
		/** voteSticks list. */
		private ArrayList<VoteStick> voteSticksList;
		/** givenSticks list. */
		private ArrayList<Stick> givenSticksList;
		/** receivedSticks list. */
		private ArrayList<Stick> receivedSticksList;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final UserCreateFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.user_progress_load_relations_title),
					this.ctx.getString(
							R.string.user_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			this.userGroupList = 
				new UserGroupProviderUtils(this.ctx).queryAll();
			this.voteSticksList = 
				new VoteStickProviderUtils(this.ctx).queryAll();
			this.givenSticksList = 
				new StickProviderUtils(this.ctx).queryAll();
			this.receivedSticksList = 
				new StickProviderUtils(this.ctx).queryAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.fragment.userGroupAdapter.loadData(this.userGroupList);
			this.fragment.voteSticksAdapter.loadData(this.voteSticksList);
			this.fragment.givenSticksAdapter.loadData(this.givenSticksList);
			this.fragment.receivedSticksAdapter.loadData(this.receivedSticksList);
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
