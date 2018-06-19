package com.rs.mobile.wportal.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.CollectionUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.MainActivity;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HscollviewAdaptet extends BaseAdapter {
	
    public Context context;
    
    public JSONArray arr;
    
    public int acount;
    
    private int type = 0;
    
    public ViewGroup.LayoutParams params;
    
    public HscollviewAdaptet(Context context,JSONObject data) {
		// TODO Auto-generated constructor stub
    	this.context=context;
    	
    	try {
    		
    		type = 0;
    				
			this.arr = data.getJSONArray(C.KEY_JSON_MOVIE);
			
			this.acount=arr.length();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
    	
	}
    
    public HscollviewAdaptet(Context context,JSONObject data, String key) {
		// TODO Auto-generated constructor stub
    	this.context=context;
    	
    	try {
    		
    		type = 1;
    		
			this.arr = data.getJSONArray(key);
			
			this.acount=arr.length();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
    	
	}
    
    public HscollviewAdaptet(Context context,JSONObject data, String key, int type, ViewGroup.LayoutParams params) {
		// TODO Auto-generated constructor stub
    	this.context=context;
    	
    	try {
    		
    		this.type = type;
    		
			this.arr = data.getJSONArray(key);
			
			this.acount=arr.length();
			
			this.params = params;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
    	
	}
    
    public HscollviewAdaptet(Context context, JSONArray arr) {
		// TODO Auto-generated constructor stub
    	this.context=context;
    	
    	try {
    		
    		type = 2;
    		
			this.arr = arr;
			
			this.acount=arr.length();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
    	
	}
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return acount;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
	return	CollectionUtil.jsonArrayToListMap(arr).get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (convertView==null) {
			
			if (type == 2 || type == 3) {
				
				convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_actor, parent,false);
				
			} else {
			
				convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chslistview_item, parent,false);
			
			}
			
		}
		
		WImageView wImageView = (WImageView) convertView.findViewById(R.id.chclistview_item_freimg);
		
		TextView name_text_view = (TextView) convertView.findViewById(R.id.name_text_view);
		
		TextView title_text_view = (TextView) convertView.findViewById(R.id.title_text_view);
		
		try {
			
			final JSONObject jsonObject = new JSONObject(arr.get(position).toString());
			
			if (type == 0) {
			
				ImageUtil.drawImageView(context, "", wImageView, jsonObject, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
      
				name_text_view.setVisibility(View.GONE);
				
				title_text_view.setVisibility(View.GONE);
				
			} else if (type == 1) {
				
				ImageUtil.drawImageViewBuFullUrl(context, wImageView, jsonObject, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
				
				name_text_view.setVisibility(View.GONE);
				
				title_text_view.setVisibility(View.GONE);
				
			} else if (type == 2) {
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MainActivity.displayHeight/7,
						MainActivity.displayHeight/6);
			 	
				wImageView.setLayoutParams(params);
				
				wImageView.setImageURI(Uri.parse(jsonObject.getString(C.KEY_JSON_IMAGE_URL)));
				
//				ImageUtil.drawIamge(wImageView, Uri.parse(jsonObject.getString(C.KEY_JSON_IMAGE_URL)), params);
				
				name_text_view.setVisibility(View.VISIBLE);
				
				title_text_view.setVisibility(View.VISIBLE);
				
				name_text_view.setText(jsonObject.getString("name"));
				
				title_text_view.setText(jsonObject.getString("title"));
				
			} else if (type == 3) {

				wImageView.setLayoutParams(params);
				
				wImageView.setImageURI(Uri.parse(jsonObject.getString(C.KEY_JSON_IMAGE_URL)));
				
				name_text_view.setVisibility(View.GONE);
				
				title_text_view.setVisibility(View.GONE);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);
			
		}

		return convertView;
	}

}
