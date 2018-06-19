package com.rs.mobile.wportal.adapter;

import java.util.ArrayList;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.activity.MainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersnalCenterListAdapter extends BaseAdapter{

	private ArrayList<JSONObject> listItems = new ArrayList<JSONObject>();
	
	private Context context;
	
	public int type;
	
	public static final int LIST_TYPE_BOOKMARK = 0;
	
	public static final int LIST_TYPE_SHARE = 1;
	
	public static final int LIST_TYPE_REPLY = 2;
	
	public static final int LIST_TYPE_SWEET = 3;
	
	public static final int LIST_TYPE_MY_SWEET = 4;
	
    private class ViewHolder{
    	
    	public TextView timeTextView;
    	
    	public TextView contentTextView;
    	
    	public TextView titleTextView;
    	
    	public TextView nickNameTextView;
    	
    	public TextView countTextView;
    	
    	public TextView msgTextView;
    	
    	public WImageView iconImageView;
    	
    }
    
    public int getType() {
    	
    	return type;
    	
    }
 
    public ArrayList<JSONObject> getListItems() {
    	
    	return listItems;
    	
    }
    
	public PersnalCenterListAdapter(Context context, int type, ArrayList<JSONObject> listItems) {
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
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder itemView = null;
		
		try {
		
			if (convertView == null) {
				
				itemView = new ViewHolder();
				
				if (type == LIST_TYPE_REPLY) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_reply, parent, false);
					
					itemView.timeTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.time_text_view);
			    	
					itemView.contentTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.content_text_view);
			    	
					itemView.titleTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			    	
				} else if (type == LIST_TYPE_SWEET) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_sweet, parent, false);
					
					itemView.timeTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.time_text_view);
			    	
					itemView.contentTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.content_text_view);
			    	
					itemView.titleTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			    	
					itemView.nickNameTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.name_text_view);
	
					itemView.msgTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.msg_text_view);
					
					itemView.iconImageView = (WImageView)convertView.findViewById(com.rs.mobile.wportal.R.id.icon_image_view);
					itemView.iconImageView.setReplyDrawableView(context);

				} else if (type == LIST_TYPE_MY_SWEET) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_sweet, parent, false);
					
					itemView.timeTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.time_text_view);
			    	
					itemView.contentTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.content_text_view);
			    	
					itemView.titleTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			    	
					itemView.nickNameTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.name_text_view);
					
					itemView.msgTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.msg_text_view);
	
					itemView.iconImageView = (WImageView)convertView.findViewById(com.rs.mobile.wportal.R.id.icon_image_view);
					itemView.iconImageView.setReplyDrawableView(context);
					
				} else {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.layout_news_view, parent, false);
					
					AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, MainActivity.newsHeight);

					convertView.setLayoutParams(layoutParams);
					
					itemView.titleTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
					
					itemView.contentTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.content_text_view);
					
					itemView.timeTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.date_text_view);
					
					itemView.countTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.count_text_view);
					
					itemView.iconImageView = (WImageView)convertView.findViewById(com.rs.mobile.wportal.R.id.image_view);
					
				}
	
				convertView.setTag(itemView);
				
			} else {
				
				itemView = (ViewHolder)convertView.getTag();
				
			}
			
			JSONObject itme = (JSONObject) getItem(position);
			
			if (type == LIST_TYPE_REPLY) {
	
				itemView.timeTextView.setText(itme.getString(C.KEY_JSON_DATE));
		    	
				itemView.contentTextView.setText(itme.getString(C.KEY_JSON_CONTENT));
		    	
				itemView.titleTextView.setText(itme.getString(C.KEY_JSON_TITLE));
		    	
			} else if (type == LIST_TYPE_SWEET) {
	
				itemView.timeTextView.setText(itme.getString(C.KEY_JSON_DATE));
		    	
				itemView.contentTextView.setText(itme.getString(C.KEY_JSON_CONTENT));
		    	
				itemView.titleTextView.setText(itme.getString(C.KEY_JSON_TITLE));
				
				itemView.nickNameTextView.setText(itme.getString(C.KEY_JSON_NICK_NAME));

				itemView.msgTextView.setText(com.rs.mobile.wportal.R.string.msg_have_sweet);
				
				itemView.msgTextView.setVisibility(View.GONE);

				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(context.getResources().getDimension(com.rs.mobile.wportal.R.dimen.marginx6)),
						(int)(context.getResources().getDimension(com.rs.mobile.wportal.R.dimen.marginx6)));

				itemView.iconImageView.setLayoutParams(lp);
				
				ImageUtil.drawImageView(context, itemView.iconImageView, itme.getString(C.KEY_JSON_IMAGE_URL));

				itemView.iconImageView.setRounding(true);
				
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(itemView.iconImageView.getWidth(),
//						itemView.iconImageView.getHeight());
				
			} else if (type == LIST_TYPE_MY_SWEET) {
	
				itemView.timeTextView.setText(itme.getString(C.KEY_JSON_DATE));
		    	
				itemView.contentTextView.setText(itme.getString(C.KEY_JSON_CONTENT));
		    	
				itemView.titleTextView.setText(itme.getString(C.KEY_JSON_TITLE));
				
				itemView.nickNameTextView.setText(itme.getString(C.KEY_JSON_NICK_NAME));
				
				itemView.msgTextView.setText(com.rs.mobile.wportal.R.string.msg_sweet);
				
				itemView.msgTextView.setVisibility(View.VISIBLE);
				
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(context.getResources().getDimension(com.rs.mobile.wportal.R.dimen.marginx6)),
						(int)(context.getResources().getDimension(com.rs.mobile.wportal.R.dimen.marginx6)));

				itemView.iconImageView.setLayoutParams(lp);
				
				ImageUtil.drawImageView(context, itemView.iconImageView, itme.getString(C.KEY_JSON_IMAGE_URL));

				itemView.iconImageView.setRounding(true);
				
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(itemView.iconImageView.getWidth(),
//						itemView.iconImageView.getHeight());
				
			} else {
				
				itemView.titleTextView.setText(itme.getString(C.KEY_JSON_TITLE));
				
				itemView.contentTextView.setText(itme.getString(C.KEY_JSON_CONTENT));
				
				itemView.timeTextView.setText(itme.getString(C.KEY_JSON_DATE));
				
				itemView.countTextView.setText(itme.getString(C.KEY_JSON_REPLY_COUNT));
				
				String imgUrl = itme.getString(C.KEY_JSON_IMAGE_URL);
				
				if (imgUrl == null || imgUrl.equals("")) {
					
					itemView.iconImageView.setVisibility(View.GONE);
					
					RelativeLayout contentArea = (RelativeLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.content_area);
					
					contentArea.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					
				} else {
					
					itemView.iconImageView.setVisibility(View.VISIBLE);
					
					ImageUtil.drawImageView(context, itemView.iconImageView, itme.getString(C.KEY_JSON_IMAGE_URL));
					
					itemView.iconImageView.setRounding(true);
					
				}
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}

}
