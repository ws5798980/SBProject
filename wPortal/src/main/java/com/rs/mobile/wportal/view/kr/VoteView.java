package com.rs.mobile.wportal.view.kr;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.L;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.kr.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VoteView extends LinearLayout{

	private Context context;
	
	private TextView titleTextView;
	
	private TextView statusTextView;
	
	private TextView dateTextView;
	
	private WImageView imageView;
	
	private String uniqueId;
	
	public VoteView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public VoteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public VoteView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}
	
	/**
	 * 초기화
	 * @param context
	 */
	public void initView(final Context context) {
		
		try {
		
			this.context = context;

			View v = ((MainActivity)context).inflator.inflate(R.layout.list_item_vote, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			titleTextView = (TextView)v.findViewById(R.id.title_text_view);
			
			statusTextView = (TextView)v.findViewById(R.id.status_text_view);
			
			dateTextView = (TextView)v.findViewById(R.id.date_text_view);
			
			imageView = (WImageView)v.findViewById(R.id.image_view);
			
			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setTitle(String title) {
		
		try {
		
			this.titleTextView.setText(title);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
	}

	public void setStatus(String status) {
		
		try {
		
			if (status.equals("1")) {
				
				this.statusTextView.setText(context.getResources().getString(R.string.kr_type_vote_before));
				this.statusTextView.setBackgroundColor(((MainActivity)context).grayColor);
				
			} else if (status.equals("2")) {
				
				this.statusTextView.setText(context.getResources().getString(R.string.kr_type_vote_live));
				this.statusTextView.setBackgroundColor(((MainActivity)context).selectColor);
				
			} else if (status.equals("3")) {
				
				this.statusTextView.setText(context.getResources().getString(R.string.kr_type_vote_after));
				this.statusTextView.setBackgroundColor(((MainActivity)context).grayColor);
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void setDate(String date) {
		
		try {
		
			this.dateTextView.setText(date);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public WImageView getImageView() {
		return this.imageView;
	}
	
	public TextView getTitleTextView() {
		
		return this.titleTextView;
		
	}
	
	public TextView getStatusTextView() {
		
		return this.statusTextView;
		
	}

}
