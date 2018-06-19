package com.rs.mobile.wportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.ListItem;

import java.util.ArrayList;
import java.util.List;

public class ShopListGridListAdapter2 extends BaseAdapter{

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

	public ShopListGridListAdapter2(Context context, List<ListItem>  listItems) {
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

					convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_shopcat_item1, parent, false);

			}

			ImageView imgView = (ImageView)convertView.findViewById(R.id.img_list_item);

			if(allcatlist.get(position).getUrl()!=null || !allcatlist.get(position).getUrl().equals("")) {
				Glide.with(context).load(allcatlist.get(position).getUrl()).into(imgView);
			}


		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}

}
