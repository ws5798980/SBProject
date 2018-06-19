package com.rs.mobile.wportal.fragment.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.biz.Address;
import com.rs.mobile.wportal.adapter.dp.DpAddressAdapter;
import com.rs.mobile.wportal.fragment.BaseFragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import okhttp3.Request;

public class DpAddressFragment extends BaseFragment {

	private View rootView;

	private ListView lv;

	private int flag = 14;

	private DpAddressAdapter adapter;

	private Address myAddress;

	private List<Address> list;

	private Handler handle = new Handler() {

		public void handleMessage(android.os.Message msg) {

			super.handleMessage(msg);
			int position = (Integer) msg.obj;
			if (msg.what == flag) {

				for (int i = 0; i < list.size(); i++) {
					if (i != position) {
						if (list.get(i).isSleceted()) {
							list.get(i).setSleceted(false);

						}

					}
				}

				adapter.notifyDataSetChanged();
			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		rootView = inflater.inflate(com.rs.mobile.wportal.R.layout.fragment_address, container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		lv = (ListView) rootView.findViewById(com.rs.mobile.wportal.R.id.listView);

	}

	@Override
	public void onResume() {

		// TODO Auto-generated method stub
		super.onResume();

		initData();

	}

	public void initData() {

		// address=new Address("宋仲基", "15200941824",
		// "湖南省长沙市麓谷小学宇成国际酒店1211技术部陈帅帅", false);
		list = new ArrayList<Address>();
		getMyAddressList();

	}

	private void getMyAddressList() {

		try {
			hideNoData(getView());
			Map<String, String> params = new HashMap<String, String>();

			// 빈값을 던저야 오류가 나지 않는다??? 왜???
			params.put("", "");

			OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

					String id, name, mobile, address, position, latitude, longitude, zip_code, zip_name;

					boolean hasDefault;

					try {

						JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);
						if (arr.length() == 0) {
							showNoData(getView(), "您还没有收货地址哦！", null);
						}
						for (int i = 0; i < arr.length(); i++) {

							JSONObject obj = arr.getJSONObject(i);
							id = obj.get("id").toString();
							name = obj.get("name").toString();
							mobile = obj.get("mobile").toString();
							hasDefault = obj.getBoolean("hasDefault");
							address = obj.get("address").toString();
							position = obj.getString("location");
							latitude = obj.getString("latitude");
							longitude = obj.getString("longitude");
							zip_code = obj.getString("zip_code");
							zip_name = obj.getString("zip_name");
							myAddress = new Address(name, mobile, address, hasDefault, id, position, latitude,
									longitude, zip_code, zip_name, true);
							list.add(myAddress);

						}
						adapter = new DpAddressAdapter(list, getContext(), handle);
						lv.setAdapter(adapter);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						L.e(e);
					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_DP1 + Constant.GET_USER_SHOPADDRESS_LIST, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}
}
