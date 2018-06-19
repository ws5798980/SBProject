package com.rs.mobile.wportal.activity.market.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.market.MarketOrderDetailActivity;
import com.rs.mobile.wportal.activity.market.util.CommonAdapter;
import com.rs.mobile.wportal.activity.market.util.ListOrder;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.activity.market.util.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import okhttp3.Request;

public class Fragment_Order extends Fragment implements OnRefreshListener2<ListView> {


	public void setIsMaker(boolean isMarket){
		this.isMarket = isMarket;
	}

	View parentView = null;
	private boolean isFirstLoad;
	private PullToRefreshListView sv_order;
	private int CurrentPage = 1;
	private CommonAdapter<ListOrder> adapter;
	private List<ListOrder> listData = new ArrayList<>();
	private int tot_cnt;
	private boolean isMarket;
	private String baseUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isFirstLoad = true;
		if (isMarket) {
			baseUrl = Constant.BASE_URL_ORDER + Constant.REQEUSTORDERLIST;
		} else {
			baseUrl = Constant.BASE_URL_ORDER + Constant.MK_FOODORDERLIST;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (isFirstLoad) {
			parentView = inflater.inflate(com.rs.mobile.wportal.R.layout.activity_fragment_order, container, false);
			sv_order = (PullToRefreshListView) parentView.findViewById(com.rs.mobile.wportal.R.id.sv_order);
			sv_order.setOnRefreshListener(this);
			sv_order.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getContext(), MarketOrderDetailActivity.class);
					intent.putExtra("order_num", listData.get(arg2 - 1).getOrder_num());
					intent.putExtra("order_date", listData.get(arg2 - 1).getOrder_date());
					intent.putExtra("order_amout", listData.get(arg2 - 1).getReal_amount());
					intent.putExtra("isMarket", isMarket);
					startActivity(intent);
				}
			});
			adapter = new CommonAdapter<ListOrder>(getContext(), listData, com.rs.mobile.wportal.R.layout.list_item_mk_order) {

				@Override
				public void convert(ViewHolder holder, ListOrder t, int position, View convertView) {
					// TODO Auto-generated method stub
					WImageView imageView = holder.getView(com.rs.mobile.wportal.R.id.mk_goods_img);
					ImageUtil.drawImageFromUri(t.getItem_image_url(), imageView);
					holder.setText(com.rs.mobile.wportal.R.id.item_name_cnt, t.getItem_name_cnt());
					holder.setText(com.rs.mobile.wportal.R.id.rnum, "商品数量：" + t.getRnum());
					holder.setText(com.rs.mobile.wportal.R.id.order_date, t.getOrder_date());
					holder.setText(com.rs.mobile.wportal.R.id.real_amount, getString(com.rs.mobile.wportal.R.string.rmb) + t.getReal_amount());

				}
			};
			sv_order.setAdapter(adapter);
			initData();

		}

		return parentView;
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		CurrentPage = 1;
		initData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		if (CurrentPage * 10 >= tot_cnt) {
			sv_order.postDelayed(new Runnable() {
				public void run() {
					sv_order.onRefreshComplete();
					T.showToast(getContext(), getResources().getString(com.rs.mobile.wportal.R.string.xlistview_no_more_data));
				}
			}, 500);

		} else {
			CurrentPage++;
			initData();
		}

	}

	private void initData() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
		HashMap<String, String> params = new HashMap<>();
		params.put("lang_type", "2");
		params.put("custom_code", S.getShare(getContext(), C.KEY_REQUEST_MEMBER_ID, "0"));
		params.put("CurrentPage", CurrentPage + "");
		params.put("RowCount", "10");
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				sv_order.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					sv_order.onRefreshComplete();
					if (data.optString("status").equals("1")) {
						JSONArray array;
						if (isMarket) {
							array = data.getJSONArray("orderList");
						} else {
							array = data.getJSONArray("foodOrderList");
						}

						if (CurrentPage == 1) {
							listData.clear();
							for (int i = 0; i < array.length(); i++) {
								if (isMarket) {
									listData.add(ListOrder.setOrderMarket(array.getJSONObject(i)));
								} else {

									listData.add(ListOrder.setOrderFood(array.getJSONObject(i)));
								}

							}

							adapter.setDatas(listData);
						} else {
							for (int i = 0; i < array.length(); i++) {
								if (isMarket) {
									listData.add(ListOrder.setOrderMarket(array.getJSONObject(i)));
								} else {
									listData.add(ListOrder.setOrderFood(array.getJSONObject(i)));
								}
							}
							adapter.notifyDataSetChanged();
						}
						tot_cnt = data.optInt("tot_cnt");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_order.onRefreshComplete();
			}
		}, baseUrl, params);
	}
}
