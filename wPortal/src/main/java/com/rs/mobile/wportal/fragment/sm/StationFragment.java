package com.rs.mobile.wportal.fragment.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.AddressDetailAdapter;
import com.rs.mobile.wportal.adapter.sm.AddressStationAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class StationFragment extends Fragment {

	View rootView;

	ListView listView_area, listView_detail;

	private AddressDetailAdapter address_001;

	private AddressStationAdapter address_002;

	private List<Map<String, String>> listArea, listDetail;

	private Map<String, String> map1 = new HashMap<String, String>();

	private Map<String, String> map2 = new HashMap<String, String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		rootView = inflater.inflate(com.rs.mobile.wportal.R.layout.fragment_station, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();

	}

	private void initData() {

		listArea = new ArrayList<Map<String, String>>();
		listDetail = new ArrayList<Map<String, String>>();
		map1.put("address", "天心区");

		for (int i = 0; i < 7; i++) {
			listArea.add(map1);
		}

		address_001 = new AddressDetailAdapter(listArea, getContext());
		listView_area.setAdapter(address_001);

	}

	private void initView() {

		listView_area = (ListView) rootView.findViewById(com.rs.mobile.wportal.R.id.lv_area);
		listView_area.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				// TODO Auto-generated method stub
				TextView textView = (TextView) arg1.findViewById(com.rs.mobile.wportal.R.id.text_name);
				textView.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
				arg1.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.common_bg));
				for (int i = 0; i < address_001.getCount(); i++) {
					if (i != arg2) {
						TextView textView1 = (TextView) arg0.getChildAt(i).findViewById(com.rs.mobile.wportal.R.id.text_name);
						arg0.getChildAt(i).setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
						textView1.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
					}
				}

				getPickupList();
			}
		});
		listView_detail = (ListView) rootView.findViewById(com.rs.mobile.wportal.R.id.lv_detail);

		listView_detail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub

				arg1.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.common_bg));
				for (int i = 0; i < address_002.getCount(); i++) {
					if (i != arg2) {
						arg0.getChildAt(i).setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
					}
				}

			}
		});
	}

	private void getPickupList() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("", "");
		OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					String site_id, site_name;
					listDetail.clear();
					JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);
					for (int i = 0; i < arr.length(); i++) {
						JSONObject obj = new JSONObject(arr.get(i).toString());
						site_id = obj.get("site_id").toString();
						site_name = obj.get("site_name").toString();
						map2.put("site_id", site_id);
						map2.put("site_name", site_name);
						listDetail.add(map2);
					}
					address_002 = new AddressStationAdapter(listDetail, getContext());
					listView_detail.setAdapter(address_002);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GET_PICKUP_SITELIST, params);
	}
}
