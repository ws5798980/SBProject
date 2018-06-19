package com.rs.mobile.wportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.ListItem;

import java.util.ArrayList;
import java.util.List;

public class ShopListGridListAdapter extends BaseAdapter{

	private int type = 0;

	public static final int TYPE_CATEGORY = 0;

	public static final int TYPE_SEARCH_HISTORY = 1;

	public static final int TYPE_SEARCH_RANKING = 2;

	public static final int TYPE_TYPE_TICKET_TIME = 3;

	public static final int TYPE_TYPE_TICKET_PRICE = 4;

	public static final int TYPE_LOCATION_CITY = 5;

	public static final int TYPE_RT_MENU = 6;

	private Object listItems;

	private Context context;

	private int selectedPosition = 0;

	public ArrayList<String> selectedPositions = new ArrayList<String>();
	private List<ListItem> allcatlist = new ArrayList<ListItem>();

	public void setSelectedPositions(String position) {

		if (selectedPositions.contains(position)) {

			selectedPositions.remove(position);

		} else {

			selectedPositions.add(position);

		}

		notifyDataSetChanged();

	}

	public void setSelectedPosition(int position) {

		selectedPosition = position;

		notifyDataSetChanged();

	}

	public ShopListGridListAdapter(Context context, List<ListItem>  listItems) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.allcatlist = listItems;

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return allcatlist.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		try {


				return (ListItem)allcatlist.get(position);

		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		try {
		
			if (convertView == null) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item_layout, parent, false);
				
			}

			TextView titleTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text);
			titleTextView.setText(allcatlist.get(position).custom_name);

			WImageView imgView = (WImageView)convertView.findViewById(com.rs.mobile.wportal.R.id.image);

			if(allcatlist.get(position).getUrl()!=null || !allcatlist.get(position).getUrl().equals("")) {
				ImageUtil.drawImageFromUri(allcatlist.get(position).getUrl(), imgView);
			}
			else
			{
				imgView.setBackgroundResource(R.drawable.icon_all);
			}


		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}

}
