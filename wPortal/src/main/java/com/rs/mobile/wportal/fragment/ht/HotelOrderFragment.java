package com.rs.mobile.wportal.fragment.ht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.ht.HtOrderAdapter;
import com.rs.mobile.wportal.biz.ht.HtOrder;
import com.rs.mobile.wportal.fragment.BaseFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import okhttp3.Request;

@SuppressLint("ValidFragment")
public class HotelOrderFragment extends BaseFragment implements OnRefreshListener2<ListView> {
	private PullToRefreshListView lv;
	private String orderId;
	private String orderStatus;
	private int iPageIndex;
	private int iPageSize;
	private List<HtOrder> listData = new ArrayList<>();
	private HtOrderAdapter adapter;
	private int total;

	public HotelOrderFragment(String orderStatus) {
		// TODO Auto-generated constructor stub

		this.orderStatus = orderStatus;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(com.rs.mobile.wportal.R.layout.fragment_ht_myorder, container, false);
		iPageIndex = 1;
		iPageSize = 10;
		lv = (PullToRefreshListView) v.findViewById(com.rs.mobile.wportal.R.id.listview);
		lv.setOnRefreshListener(this);
		lv.setMode(Mode.BOTH);

		return v;
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> onrefreshView) {
		// TODO Auto-generated method stub
		iPageIndex = 1;
		initData();
	}

	@Override
	public void onPullUpToRefresh(final PullToRefreshBase<ListView> onrefreshView) {
		// TODO Auto-generated method stub
		if (iPageIndex * iPageSize >= total) {
			lv.postDelayed(new Runnable() {
				public void run() {
					T.showToast(getActivity(), getString(com.rs.mobile.wportal.R.string.common_text068));
					lv.onRefreshComplete();
				}
			}, 500);

		} else {
			iPageIndex++;
			initData();
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	private void initData() {
		hideNoData(getView());
		OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
		HashMap<String, String> params = new HashMap<>();
		params.put("token", S.getShare(getActivity(), C.KEY_JSON_TOKEN, ""));
		params.put("memid", S.getShare(getActivity(), C.KEY_REQUEST_MEMBER_ID, ""));
		params.put("OrderStatus", orderStatus);
		params.put("iPageIndex", iPageIndex + "");
		params.put("iPageSize", iPageSize + "");
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				lv.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				lv.onRefreshComplete();
				// TODO Auto-generated method stub
				try {
					if (data.get("status").toString().equals("1")) {
						if (iPageIndex == 1) {
							listData.clear();
						}
						total = data.getInt("total");
						if (total == 0) {
							showNoData(getView(), com.rs.mobile.wportal.R.drawable.nodata, "您还没有相关订单", null);
						}
						JSONArray arrdata = data.getJSONArray("data");
						for (int i = 0; i < arrdata.length(); i++) {
							JSONObject obj = arrdata.getJSONObject(i);
							HtOrder h = new HtOrder(obj.getString("orderId"), obj.getString("hotelName"),
									obj.getString("roomTypeID"), obj.getString("roomTypeName"),
									obj.getString("arriveTime"), obj.getString("leaveTime"),
									obj.get("roomPrice").toString(), obj.get("roomCount").toString(),
									obj.get("orderStatus").toString(), obj.getString("reserveTime"),
									obj.getString("currentTime"), obj.getString("overTime"));
							listData.add(h);
						}
						if (iPageIndex == 1) {
							adapter = new HtOrderAdapter(listData, getActivity());
							lv.setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}

					} else {
						T.showToast(getActivity(), data.getString("msg"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				lv.onRefreshComplete();
			}
		}, Constant.BASE_URL_HT + Constant.HT_GET_ORDERLIST, params);

	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}
}
