package com.rs.mobile.wportal.adapter;

import java.util.List;

import com.rs.mobile.wportal.biz.ShoppingCartParent;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.adapter.sm.goodCartAdapter;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingCartParentAdapter extends BaseAdapter {
	public ShoppingCartParentAdapter(List<ShoppingCartParent> listdata, Handler handler, Activity activity, int type) {
		super();
		this.listdata = listdata;
		this.handler = handler;
		this.activity = activity;
		this.type = type;
	}

	private List<ShoppingCartParent> listdata;
	private Handler handler;
	private Activity activity;
	private int type;

	@Override
	public int getCount() {
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {

		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		SpViewholder viewholder = null;
		if (convertView == null) {
			viewholder = new SpViewholder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_shoppingcart_parent,
					parent, false);
			viewholder.checkBox = (CheckBox) convertView.findViewById(com.rs.mobile.wportal.R.id.checkbox);
			viewholder.listView = (ListView) convertView.findViewById(com.rs.mobile.wportal.R.id.listview);
			convertView.setTag(viewholder);

		} else {
			viewholder = (SpViewholder) convertView.getTag();
		}
		viewholder.checkBox.setChecked(listdata.get(position).isChoosed_parent());
	//	viewholder.checkBox.setText(listdata.get(position).getDiv_name());
		viewholder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listdata.get(position).setChoosed_parent(!listdata.get(position).isChoosed_parent());
				if (listdata.get(position).isChoosed_parent()) {
					for (int i = 0; i < listdata.get(position).getShoppingCarts().size(); i++) {
						listdata.get(position).getShoppingCarts().get(i).setChoosed(true);
					}
				} else {
					for (int i = 0; i < listdata.get(position).getShoppingCarts().size(); i++) {
						listdata.get(position).getShoppingCarts().get(i).setChoosed(false);
					}
				}

				handler.sendMessage(handler.obtainMessage(11, ""));
			}
		});
		goodCartAdapter adapter = new goodCartAdapter(listdata.get(position), activity, handler, type);
		viewholder.listView.setAdapter(adapter);
		UiUtil.setListViewHeight1(viewholder.listView);
		return convertView;
	}

	static class SpViewholder {
		public CheckBox checkBox;
		public ListView listView;
	}
}
