/**************************************************************************
 * UserGroupListAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.usergroup;

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
import com.iia.giveastick.entity.UserGroup;

/**
 * List adapter for UserGroup entity.
 */
public class UserGroupListAdapter
		extends HeaderAdapter<UserGroup>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public UserGroupListAdapter(Context ctx) {
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
	public UserGroupListAdapter(Context context,
			int resource,
			int textViewResourceId,
			List<UserGroup> objects) {
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
	public UserGroupListAdapter(Context context,
			int resource,
			int textViewResourceId,
			UserGroup[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 */
	public UserGroupListAdapter(Context context,
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
	public UserGroupListAdapter(Context context,
			int textViewResourceId,
			List<UserGroup> objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public UserGroupListAdapter(Context context,
			int textViewResourceId,
			UserGroup[] objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 */
	public UserGroupListAdapter(Context context,
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
			super(context, attrs, R.layout.row_usergroup);
		}

		/** Populate row with a UserGroup.
		 *
		 * @param model UserGroup data
		 */
		public void populate(final UserGroup model) {
			View convertView = this.getInnerLayout();
			TextView tagView =
				(TextView) convertView.findViewById(
						R.id.row_usergroup_tag);


			if (model.getTag() != null) {
				tagView.setText(model.getTag());
			}
		}
	}

	/** Section indexer for this entity's list. */
	public static class UserGroupSectionIndexer
					extends HeaderSectionIndexer<UserGroup>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public UserGroupSectionIndexer(List<UserGroup> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(UserGroup item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				UserGroup item,
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
	public int getPosition(UserGroup item) {
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
