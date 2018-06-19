package com.rs.mobile.wportal.adapter.ht;

import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.biz.ht.HotelPhoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class HtAlbumAdapter1 extends BaseAdapter {
	List<HotelPhoto> arr1 = new ArrayList<>();
	List<HotelPhoto> arr2 = new ArrayList<>();
	List<HotelPhoto> arr3 = new ArrayList<>();
	List<HotelPhoto> arr4 = new ArrayList<>();
	private List<List<HotelPhoto>> arrtotal;

	public HtAlbumAdapter1(List<HotelPhoto> data, Context context) {
		super();
		this.data = data;
		this.context = context;
		
		arr1 = new ArrayList<>();
		arr2 = new ArrayList<>();
		arr3 = new ArrayList<>();
		arr4 = new ArrayList<>();
		arrtotal = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			int type = data.get(i).getImagetype();
			switch (type) {
			case 1:
				arr1.add(data.get(i));
				break;
			case 2:
				arr2.add(data.get(i));
				break;
			case 3:
				arr3.add(data.get(i));
				break;
			case 4:
				arr4.add(data.get(i));
				break;

			default:
				break;
			}
		}
		if (arr1.size() > 0) {
			arrtotal.add(arr1);
		} ;
		if (arr2.size() > 0) {
			arrtotal.add(arr2);
		} ; 
		if (arr3.size() > 0) {
			arrtotal.add(arr3);
		} ; 
		if (arr4.size() > 0) {
			arrtotal.add(arr4);
		}
		
	}

	private List<HotelPhoto> data;
	private Context context;
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrtotal.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrtotal.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolderAlbum vh;
		if (convertView == null) {
			vh = new ViewHolderAlbum();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.ht_list_item_hotel_album, parent,
					false);
			vh.text_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
			vh.gv = (GridView) convertView.findViewById(com.rs.mobile.wportal.R.id.gv);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolderAlbum) convertView.getTag();
		}
		vh.text_name.setText(initText(arrtotal.get(position).get(0).getImagetype())+"("+arrtotal.get(position).size()+")");
		// 固定列宽，有多少列
		HtPhotoAdapter adapter = new HtPhotoAdapter(context, 2, arrtotal.get(position),(float)1.3);
		vh.gv.setAdapter(adapter);
		UiUtil.setGridViewHeight(vh.gv, 2);
//		AbsListView.LayoutParams layoutparams=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.FILL_PARENT);
//		convertView.setLayoutParams(layoutparams);
		return convertView;
	}

	static class ViewHolderAlbum {
		TextView text_name;
		GridView gv;
	}
    private String initText(int i){
    	switch (i) {
		case 1:
			return context.getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type1);
		case 2:
			return context.getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type2);
		case 3:
			return context.getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type3);
		case 4:
			return context.getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type4);
		default:
			return "";
		}
    }
}
