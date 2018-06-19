
package com.rs.mobile.wportal.adapter.sm;

import java.util.List;
import java.util.Map;

import com.rs.mobile.wportal.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProvinceAdapter extends BaseAdapter {

	private List<Map<String, String>> list;

	Context context;

	public ProvinceAdapter(Context context, List<Map<String, String>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_address_item, parent, false);
		}
		TextView name = (TextView) convertView.findViewById(R.id.text_name);
		name.setText(list.get(position).get("ProvinceName").toString());

		return convertView;
	}

}
