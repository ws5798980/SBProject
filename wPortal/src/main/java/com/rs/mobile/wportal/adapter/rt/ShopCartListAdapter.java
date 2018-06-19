package com.rs.mobile.wportal.adapter.rt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.common.L;
import com.rs.mobile.wportal.entity.StoreMenuListEntity1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopCartListAdapter extends BaseAdapter {

	private MenuChangeListener menuChangeListener;

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private List<StoreMenuListEntity1> arr;

	public ShopCartListAdapter(Context context, List<StoreMenuListEntity1>  arr, MenuChangeListener menuChangeListener){
		
		this.mContext = context;
		
		this.arr = arr;
		
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.menuChangeListener = menuChangeListener;
		
	}
	
	public List<StoreMenuListEntity1> getListItems() {
		
		if (arr == null) arr = new ArrayList<>();
		
		return arr;
		
	}
	
	public void setListItems(List<StoreMenuListEntity1>  arr) {
		
		this.arr = arr;
		
	}
	
	public void removeAllCart() {
		
		try {

			arr.clear();
			
			notifyDataSetChanged();
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	@Override
	public int getCount() {
		return arr == null ? 0 : arr.size();
	}

	@Override
	public Object getItem(int position) {
		
		try {
		
			return arr == null ? null : arr.get(position);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ViewHolder viewHolder;
		
		if(convertView == null){
			
			viewHolder = new ViewHolder();
			
			convertView = mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.layout_rt_cart_list_item, null);

			viewHolder.name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.name);
			
			viewHolder.price = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.price);
			
			viewHolder.minus_btn = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.minus_btn);
			
			viewHolder.count_text_view = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.count_text_view);
			
			viewHolder.plus_btn = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.plus_btn);

			convertView.setTag(viewHolder);
			
		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		
		//Setting values
		final StoreMenuListEntity1 item = (StoreMenuListEntity1) getItem(position);
		
		viewHolder.minus_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					int count = item.num;
					if (count > 0) {
						if(count==1)
						{
							arr.remove(position);
						}
						else {
							arr.get(position).num = arr.get(position).num - 1;
						}
					}
					else
					{
						arr.remove(position);
					}
					menuChangeListener.onChange(false);
					notifyDataSetChanged();

				} catch (Exception e) {
					L.e(e);
				}
				
			}
		});
		
		viewHolder.plus_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					int count = item.num;
					arr.get(position).num=arr.get(position).num+1;
					menuChangeListener.onChange(false);
					notifyDataSetChanged();

				} catch (Exception e) {

					L.e(e);

				}
				
			}
		});
		
		try {

			if(item.selectplistinfo.isSingle.equals("1")) {
				viewHolder.name.setText(item.selectplistinfo.item_name);
				viewHolder.price.setText(item.selectplistinfo.item_p);
			}
			else
			{
				viewHolder.name.setText(item.selectplistinfo.item_name+"("+item.selectfoodSpec.item_name+")");
				viewHolder.price.setText(item.selectfoodSpec.item_p);
			}

			viewHolder.count_text_view.setText("" + item.num);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}
	
	private class ViewHolder {

		public TextView name;
		
		public TextView price;
		
		public LinearLayout minus_btn;
		
		public TextView count_text_view;
		
		public LinearLayout plus_btn;
		
	}
	
	public interface MenuChangeListener {
		
		void onChange(boolean add);
		
	}
	
}
