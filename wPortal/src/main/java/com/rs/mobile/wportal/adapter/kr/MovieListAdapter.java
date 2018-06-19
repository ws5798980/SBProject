package com.rs.mobile.wportal.adapter.kr;

import java.util.ArrayList;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.MainActivity;
import com.rs.mobile.common.image.ImageUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieListAdapter extends BaseAdapter{

	private ArrayList<JSONObject> listItems =  new ArrayList<JSONObject>();
	
	private Context context;
	
	public int type;
	
	public static final int LIST_TYPE_MOVIE = 0;

    public int getType() {
    	
    	return type;
    	
    }

    public ArrayList<JSONObject> getListItems() {
    	
    	return listItems;
    	
    }
    
	public MovieListAdapter(Context context, int type, ArrayList<JSONObject> listItems) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		this.type = type;
		
		this.listItems = listItems;

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		try {

			return listItems.get(position);

		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub

		try {
		
			if (v == null) {

				if (type == LIST_TYPE_MOVIE) {
					
					v = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_movie, parent, false);

				} 

			}
			
			JSONObject item = (JSONObject) getItem(position);
			
			if (type == LIST_TYPE_MOVIE) {
	
				TextView titleTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
				
				ImageView icon3d = (ImageView)v.findViewById(com.rs.mobile.wportal.R.id.icon_3d);
				
				TextView contentTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.content_text_view);
				
				TextView actorTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.actor_text_view);
				
				RatingBar ratingBar = (RatingBar)v.findViewById(com.rs.mobile.wportal.R.id.rationg_bar);
				
				TextView pointTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.point_text_view);
				
				titleTextView.setText(item.getString("subject"));
				
				contentTextView.setText(item.getString("description"));
				
				actorTextView.setText(item.getString("actors"));
				
				ratingBar = (RatingBar)v.findViewById(com.rs.mobile.wportal.R.id.rationg_bar);
				
				pointTextView.setText(item.getString("points"));
				
				TextView bookingBtn = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.booking_btn);
				bookingBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
				
				WImageView imageView = (WImageView) v.findViewById(com.rs.mobile.wportal.R.id.image_view);
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MainActivity.displayHeight/4);
		
				imageView.setLayoutParams(params);
				
				ImageUtil.drawImageViewBuFullUrl(context, imageView, item, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);

				String is3d = item.getString("is3D");
		    	
				if (is3d != null && is3d.equals("yes")) {
					
					icon3d.setVisibility(View.VISIBLE);
					
				} else {
					
					icon3d.setVisibility(View.GONE);
					
				}
				
				float rating = (float) item.getDouble("points");
				
				ratingBar.setRating(rating);
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return v;
	}

}
