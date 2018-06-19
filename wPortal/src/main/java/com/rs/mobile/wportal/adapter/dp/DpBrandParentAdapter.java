package com.rs.mobile.wportal.adapter.dp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.activity.dp.DpSerchResultActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class DpBrandParentAdapter extends BaseAdapter{
	
	public DpBrandParentAdapter(JSONArray arr, Context context) {
		super();
		this.arr = arr;
		this.context = context;
	}

	private JSONArray arr;
	private Context context;
   
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		
		return arr.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return arr.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null) {
			convertView=LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.dp_list_item_brandparent, parent, false);
		}
		
		TextView brand_type=(TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.brand_type);
		GridView gv=(GridView)convertView.findViewById(com.rs.mobile.wportal.R.id.gv);
		try {
			JSONObject obj=arr.getJSONObject(position);
			brand_type.setText(obj.getString("brand_type"));
			final JSONArray imgarr=obj.getJSONArray("brandList");
			
			DpbrandImageAdapter adapter=new DpbrandImageAdapter(imgarr, context,3);
			gv.setAdapter(adapter);
			gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					try {
						JSONObject obj=imgarr.getJSONObject(position);
						String custom_code = obj.getString("custom_code");
						Bundle bundle=new Bundle();
						bundle.putString("custom_code", custom_code);
						bundle.putString("flag", "custom_code");
						PageUtil.jumpTo(context, DpSerchResultActivity.class,bundle);} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			// 固定列宽，有多少列
			int col = 3;// listView.getNumColumns();
			int totalHeight = 0;
			// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
			// listAdapter.getCount()小于等于8时计算两次高度相加
			for (int i = 0; i < adapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = adapter.getView(i, null, gv);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
			}
			// 获取listview的布局参数
			ViewGroup.LayoutParams params = gv.getLayoutParams();
			// 设置高度
			params.height = totalHeight;
			
			// 设置参数
			gv.setLayoutParams(params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
	}

}
