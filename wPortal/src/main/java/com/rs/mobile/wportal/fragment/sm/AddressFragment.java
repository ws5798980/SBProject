package com.rs.mobile.wportal.fragment.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.SmAddressActivity;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity;
import com.rs.mobile.wportal.biz.Address;
import com.rs.mobile.wportal.adapter.sm.AddressAdapter;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;
import com.rs.mobile.wportal.fragment.BaseFragment;
import com.rs.mobile.wportal.fragment.xsgr.XsMenuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import okhttp3.Request;

import static android.app.Activity.RESULT_OK;

public class AddressFragment extends BaseFragment {

	private View rootView;

	private ListView lv;

	private int flag = 14;

	private AddressAdapter adapter;

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

	protected boolean hasDelivery;

	private String div_code;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(com.rs.mobile.wportal.R.layout.fragment_address, container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		lv = (ListView) rootView.findViewById(com.rs.mobile.wportal.R.id.listView);

	}


	@Override
	public void onResume() {

		super.onResume();

		initData();

	}

	public void initData() {

		// address=new Address("宋仲基", "15200941824",
		// "湖南省长沙市麓谷小学宇成国际酒店1211技术部陈帅帅", false);
		list = new ArrayList<Address>();
		getMyAddressList();

	}

	private void getMyAddressList(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("lang_type", AppConfig.LANG_TYPE);
		params.put("custom_code", S.get(getActivity(), C.KEY_JSON_CUSTOM_CODE));
		params.put("token", S.get(getActivity(), C.KEY_JSON_TOKEN));
		params.put("pg", "1");
		params.put("pagesize", "10");

		OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				String id, name, mobile, address, position, latitude, longitude, zip_code, zip_name;
				boolean hasDefault;
				try {
					JSONArray arr = data.getJSONArray("bcm100ts");
					if (arr.length() == 0) {
                        showNoData(getView(), (String)getResources().getText(R.string.no_address), new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								getMyAddressList();
							}
						});
                    }else{
						showData(getView());
					}

					for (int i = 0; i < arr.length(); i++) {

						JSONObject obj = arr.getJSONObject(i);
						id = obj.get("seq_num").toString();
						name = obj.get("delivery_name").toString();
						mobile = obj.get("mobilepho").toString();
						hasDefault = obj.getBoolean("default_add");
						address = obj.get("to_address").toString();
						zip_name = obj.getString("zip_name");
						zip_code = obj.getString("zip_code");

//						position = obj.getString("location");
//						latitude = obj.getString("latitude");
//						longitude = obj.getString("longitude");
//						hasDelivery = obj.getBoolean("hasDelivery");
						hasDelivery = false;
						myAddress = new Address(name, mobile, address, hasDefault, id, "", "",
								"", zip_name, zip_code, hasDelivery);
						list.add(myAddress);
					}
					adapter = new AddressAdapter(list, getContext(), handle);
					lv.setAdapter(adapter);
					if(((SmAddressActivity)getActivity()).typeSelect){
						lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
								Intent intent = new Intent();
								intent.putExtra("id", list.get(i).getId());
								intent.putExtra("name", list.get(i).getName());
								intent.putExtra("address", list.get(i).getAddress());
								intent.putExtra("mobile", list.get(i).getPhone());
								getActivity().setResult(RESULT_OK, intent);
								getActivity().finish();
							}
						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
			}

			@Override
			public void onNetworkError(Request request, IOException e) {
			}
		}, Constant.XS_BASE_URL + "MyInfo/MyinfoAddressList", GsonUtils.createGsonString(params));
	}

	@Override
	protected void lazyLoad() {

	}
}
