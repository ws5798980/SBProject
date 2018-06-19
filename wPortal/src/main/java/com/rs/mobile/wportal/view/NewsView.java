package com.rs.mobile.wportal.view;

import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsView extends LinearLayout{

	private Context context;
	
	private TextView titleTextView;
	
	private TextView contentTextView;
	
	private TextView dateTextView;
	
	private TextView countTextView;
	
	private WImageView imageView;
	
	public NewsView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}

	public NewsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
	}

	public NewsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
	}
	
	/**
	 * 초기화
	 * @param context
	 */
	public void initView(final Context context, LayoutInflater inflator) {
		
		try {
		
			this.context = context;

			View v = inflator.inflate(com.rs.mobile.wportal.R.layout.layout_news_view, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			titleTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			
			contentTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.content_text_view);
			
			dateTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.date_text_view);
			
			countTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.count_text_view);
			
			imageView = (WImageView)v.findViewById(com.rs.mobile.wportal.R.id.image_view);
			
			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void setTitle(String title) {
		
		try {
		
			this.titleTextView.setText(title);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
	}

	public void setContent(String content) {
		
		try {
		
			this.contentTextView.setText(content);
			
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

	public void setCount(String count) {
		
		try {
		
			this.countTextView.setText(count);
			
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
	
	public TextView getContentTextView() {
		
		return this.contentTextView;
		
	}

}
