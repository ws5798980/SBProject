package com.rs.mobile.wportal.adapter.ht;

import java.util.List;

import com.rs.mobile.wportal.activity.ht.HtContactActivity;
import com.rs.mobile.wportal.biz.ht.HtContact;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class HtSelectRoomAdapter extends BaseAdapter {
	public HtSelectRoomAdapter(List<HtContact> listdata, Activity context) {
		super();
		this.listdata = listdata;
		this.context = context;
	}

	private List<HtContact> listdata;
	private Activity context;

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
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final HsViewHodler vh;
		if (convertView == null) {
			vh = new HsViewHodler();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.ht_list_item_fillorder, parent,
					false);
			vh.text_name = (EditText) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
			vh.text_phone = (EditText) convertView.findViewById(com.rs.mobile.wportal.R.id.text_phone);
			vh.roomNum = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.roomNum);
			vh.button_contact = (ImageButton) convertView.findViewById(com.rs.mobile.wportal.R.id.button_contact);
			convertView.setTag(vh);
		} else {
			vh = (HsViewHodler) convertView.getTag();
		}
		final HtContact h = listdata.get(position);
		vh.text_name.setText(h.getUserName());
		vh.text_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				h.setUserName(vh.text_name.getText().toString());
			}
		});
		vh.text_phone.setText(h.getPhonenum());
		vh.text_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				h.setPhonenum(vh.text_phone.getText().toString());
			}
		});
		vh.roomNum.setText(context.getString(com.rs.mobile.wportal.R.string.common_text091) + (1 + position) + ")");
		vh.button_contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, HtContactActivity.class);
				i.putExtra("position", position);
				context.startActivityForResult(i, 1000);
			}
		});
		return convertView;
	}

	static class HsViewHodler {
		TextView roomNum;
		EditText text_name, text_phone;
		ImageButton button_contact;

	}
}
