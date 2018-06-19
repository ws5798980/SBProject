
package com.rs.mobile.wportal.adapter.sm;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddressDetailAdapter extends BaseAdapter {

	List<Map<String, String>> listdata;

	private TextView textView;

	private Context context;

	public AddressDetailAdapter(List<Map<String, String>> listdata, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listdata = listdata;
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.fragment_address_item, parent, false);

		}
		textView = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
		textView.setText(listdata.get(position).get("address"));
		return convertView;
	}

}
