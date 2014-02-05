/**************************************************************************
 * UserListAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.user;

import java.util.List;

import com.iia.giveastick.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import com.iia.giveastick.entity.User;

/**
 * List adapter for User entity.
 */
public class UserListAdapter
		extends HeaderAdapter<User>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserListAdapter(Context ctx) {
		super(ctx);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public UserListAdapter(Context context,
			int resource,
			int textViewResourceId,
			List<User> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     *
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public UserListAdapter(Context context,
			int resource,
			int textViewResourceId,
			User[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 */
	public UserListAdapter(Context context,
			int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public UserListAdapter(Context context,
			int textViewResourceId,
			List<User> objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public UserListAdapter(Context context,
			int textViewResourceId,
			User[] objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 */
	public UserListAdapter(Context context,
			int textViewResourceId) {
		super(context, textViewResourceId);
	}

	/** Holder row. */
	private static class ViewHolder extends SelectionItemView {

		/**
		 * Constructor.
		 *
		 * @param context The context
		 */
		public ViewHolder(Context context) {
			this(context, null);
		}
		
		/**
		 * Constructor.
		 *
		 * @param context The context
		 * @param attrs The attribute set
		 */
		public ViewHolder(Context context, AttributeSet attrs) {
			super(context, attrs, R.layout.row_user);
		}

		/** Populate row with a User.
		 *
		 * @param model User data
		 */
		public void populate(final User model) {
			View convertView = this.getInnerLayout();
			TextView emailView =
				(TextView) convertView.findViewById(
						R.id.row_user_email);
			TextView passwordView =
				(TextView) convertView.findViewById(
						R.id.row_user_password);
			TextView nicknameView =
				(TextView) convertView.findViewById(
						R.id.row_user_nickname);
			TextView userGroupView =
				(TextView) convertView.findViewById(
						R.id.row_user_usergroup);


			if (model.getEmail() != null) {
				emailView.setText(model.getEmail());
			}
			if (model.getPassword() != null) {
				passwordView.setText(model.getPassword());
			}
			if (model.getNickname() != null) {
				nicknameView.setText(model.getNickname());
			}
			userGroupView.setText(
					String.valueOf(model.getUserGroup().getId()));
		}
	}

	/** Section indexer for this entity's list. */
	public static class UserSectionIndexer
					extends HeaderSectionIndexer<User>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public UserSectionIndexer(List<User> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(User item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				User item,
				int position) {
    	final ViewHolder view;
    	
    	if (itemView != null) {
    		view = (ViewHolder) itemView;
    	} else {
    		view = new ViewHolder(this.getContext());
		}

    	if (!((HarmonyFragmentActivity) this.getContext()).isDualMode()) {
    		view.setActivatedStateSupported(false);
		}
    	
    	view.populate(item);
        this.bindSectionHeaderAndDivider(view, position);
        
        return view;
    }

	@Override
	public int getPosition(User item) {
		int result = -1;
		if (item != null) {
			for (int i = 0; i < this.getCount(); i++) {
				if (item.getId() == this.getItem(i).getId()) {
					result = i;
				}
			}
		}
		return result;
	}
}
