/**************************************************************************
 * VoteStickListAdapter.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.votestick;

import java.util.List;

import com.iia.giveastick.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import com.iia.giveastick.entity.VoteStick;

/**
 * List adapter for VoteStick entity.
 */
public class VoteStickListAdapter
		extends HeaderAdapter<VoteStick>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public VoteStickListAdapter(Context ctx) {
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
	public VoteStickListAdapter(Context context,
			int resource,
			int textViewResourceId,
			List<VoteStick> objects) {
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
	public VoteStickListAdapter(Context context,
			int resource,
			int textViewResourceId,
			VoteStick[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 */
	public VoteStickListAdapter(Context context,
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
	public VoteStickListAdapter(Context context,
			int textViewResourceId,
			List<VoteStick> objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public VoteStickListAdapter(Context context,
			int textViewResourceId,
			VoteStick[] objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 */
	public VoteStickListAdapter(Context context,
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
			super(context, attrs, R.layout.row_votestick);
		}

		/** Populate row with a VoteStick.
		 *
		 * @param model VoteStick data
		 */
		public void populate(final VoteStick model) {
			View convertView = this.getInnerLayout();
			CheckBox answerView =
				(CheckBox) convertView.findViewById(
						R.id.row_votestick_answer);
			answerView.setEnabled(false);
			TextView userView =
				(TextView) convertView.findViewById(
						R.id.row_votestick_user);
			TextView stickView =
				(TextView) convertView.findViewById(
						R.id.row_votestick_stick);


			answerView.setChecked(model.isAnswer());
			userView.setText(
					String.valueOf(model.getUser().getId()));
			stickView.setText(
					String.valueOf(model.getStick().getId()));
		}
	}

	/** Section indexer for this entity's list. */
	public static class VoteStickSectionIndexer
					extends HeaderSectionIndexer<VoteStick>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public VoteStickSectionIndexer(List<VoteStick> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(VoteStick item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				VoteStick item,
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
	public int getPosition(VoteStick item) {
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
