package com.rs.mobile.wportal.view.kr;

import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieListItemView extends LinearLayout{

	private Context context;
	
	private TextView titleTextView;
	
	private ImageView icon3d;
	
	private TextView contentTextView;
	
	private TextView actorTextView;
	
	private RatingBar ratingBar;
	
	private TextView pointTextView;
	
	private WImageView imageView;

	private String contentID;
	
	public MovieListItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public MovieListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public MovieListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
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
			
			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View v = inflator.inflate(com.rs.mobile.wportal.R.layout.list_item_movie, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			titleTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			
			icon3d = (ImageView)v.findViewById(com.rs.mobile.wportal.R.id.icon_3d);
			
			contentTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.content_text_view);
			
			actorTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.actor_text_view);
			
			ratingBar = (RatingBar)v.findViewById(com.rs.mobile.wportal.R.id.rationg_bar);
			
			pointTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.point_text_view);
			
			imageView = (WImageView) v.findViewById(com.rs.mobile.wportal.R.id.image_view);

			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public String getContentID() {
		return contentID;
	}

	public void setContentID(String contentID) {
		this.contentID = contentID;
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
	
	public void setActor(String actor) {
		
		try {
			
			this.actorTextView.setText(actor);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void setRating(float rating) {
		
		try {
			
			this.ratingBar.setRating(rating);
			
			this.pointTextView.setText("" + rating + context.getResources().getString(com.rs.mobile.wportal.R.string.kr_movie_rating));
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void set3D(String is3d) {
		
		try {
			
			if (is3d != null && is3d.equals("yes")) {
				
				this.icon3d.setVisibility(View.VISIBLE);
				
			} else {
				
				this.icon3d.setVisibility(View.GONE);
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public WImageView getImageView() {
		
		return this.imageView;
		
	}

}
