package com.rs.mobile.wportal.fragment.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.XListView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.MyOrderAdapter;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import okhttp3.Request;

public class MyOrderCommonFragment extends ListFragment implements XListView.IXListViewListener {

	private JSONArray array;

	private int TotalCount = 0;

	private int currentPage = 1;

	private String orderCode;

	private RelativeLayout id_ad;

	private Boolean isFirstLoad;

	private MyOrderAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		orderCode = bundle.getString(C.KEY_JSON_FM_ORDERSTATUS);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(com.rs.mobile.wportal.R.layout.fragment_my_order, container, false);
		id_ad = (RelativeLayout) v.findViewById(com.rs.mobile.wportal.R.id.id_ad);
		return v;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		try {
			array = new JSONArray("[]");

			((XListView) getListView()).setXListViewListener(this);

			((XListView) getListView()).setPullLoadEnable(true);

		} catch (Exception e) {

			L.e(e);

		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onResume() {

		super.onResume();
		currentPage = 1;
		isFirstLoad = true;
		getUserOrderList();

	}

	public void getUserOrderList() {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ORDERSTATUS, orderCode);
			params.put(C.KEY_JSON_FM_PAGEINDEX, currentPage + "");
			params.put(C.KEY_JSON_FM_PAGESIZE, "20");
			params.put(C.KEY_JSON_TOKEN, S.getShare(getActivity(), C.KEY_JSON_TOKEN, ""));
			params.put("appType", "6");
			OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					try {
						if (currentPage == 1) {
							array = new JSONArray("[]");
						}
						String string = data.getString("total");
						String serverTime = data.getString("serverTime");
						TotalCount = Integer.parseInt(string);
						if (TotalCount - currentPage * 20 <= 0) {
							((XListView) getListView()).set_booter_gone();
						}
						JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);
						if (arr.length() == 0) {
							getListView().setVisibility(View.GONE);
							id_ad.setVisibility(View.VISIBLE);
						} else {
							getListView().setVisibility(View.VISIBLE);
							id_ad.setVisibility(View.GONE);

						}
						for (int i = 0; i < arr.length(); i++) {
							array.put(arr.get(i));
						}
						if (isFirstLoad || currentPage == 1) {
							adapter = new MyOrderAdapter(getActivity(), array, MyOrderCommonFragment.this, serverTime);
							setListAdapter(adapter);

						} else {
							adapter.notifyDataSetChanged();
						}

						isFirstLoad = false;

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}
			}, Constant.SM_BASE_URL + Constant.GET_USERORDER_LIST, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	public void onRefresh() {

		try {

			currentPage = 1;
			try {
				array = new JSONArray("[]");
				getUserOrderList();
			} catch (Exception e) {
				L.e(e);
			}

			((XListView) getListView()).stopLoadMore();
			((XListView) getListView()).stopRefresh();

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	public void onLoadMore() {

		try {

			if (TotalCount - currentPage * 20 <= 0) {
				Toast.makeText(getActivity(), getString(com.rs.mobile.wportal.R.string.common_text068), Toast.LENGTH_SHORT).show();
				((XListView) getListView()).stopLoadMore();
				((XListView) getListView()).stopRefresh();

				return;
			} else {
				currentPage++;
				getUserOrderList();

				((XListView) getListView()).stopLoadMore();
				((XListView) getListView()).stopRefresh();
			}

		} catch (Exception e) {

			L.e(e);

		}
	}
}
