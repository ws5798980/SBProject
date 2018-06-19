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
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.XListView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.ElvuateAdapter;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.fragment.BaseFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Request;

public class EvaluateCommonPage extends BaseFragment implements XListView.IXListViewListener {

	private TextView textView;

	private String commentStatus, has_imgs;

	private List<Map<String, Object>> listdata;

	private GoodsFragment3 fragment;

	private String item_code;

	private int pageIndex;

	private int pageSize;

	private int TotalCount;

	private JSONArray array;

	private String div_code;

	private XListView list;

	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		v = inflater.inflate(com.rs.mobile.wportal.R.layout.goods_fragment_common, container, false);
		list = (XListView) v.findViewById(com.rs.mobile.wportal.R.id.list);
		Bundle bundle = getArguments();
		commentStatus = bundle.getString(GoodsFragment3.FRAGMENT_DATA_STATUS, "0");
		has_imgs = bundle.getString(GoodsFragment3.FRAGMENT_DATA_HAS_IMAGE, "false");

		item_code = bundle.getString(C.KEY_JSON_FM_ITEM_CODE, "");

		div_code = bundle.getString(C.KEY_DIV_CODE);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		list.setXListViewListener(this);

		list.setPullLoadEnable(true);
		pageIndex = 1;
		pageSize = 20;

		listdata = new ArrayList<Map<String, Object>>();
		try {
			array = new JSONArray("[]");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initData();

		// getListView().getLayoutParams().height=StringUtil.dip2px(getContext(),
		// 465);
		//
		// getListView().setLayoutParams(getListView().getLayoutParams());

	}

	public int getwindowswidth() {

		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 窗口的宽度
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	public int getwindowsheight() {

		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 窗口的宽度
		int screenheight = dm.heightPixels;
		return screenheight;
	}

	private int getimgheight() {

		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), com.rs.mobile.wportal.R.drawable.img_logo);
		int height = bitmap.getHeight();
		return height + StringUtil.dip2px(getContext(), 20);
	}

	public void initData() {

		// listView.setOnScrollListener(new AbsListView.OnScrollListener() {
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		// switch (scrollState) {
		// case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
		// case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
		// //ImageLoader.pauseLoader();
		// if (!Fresco.getImagePipeline().) {
		// Fresco.getImagePipeline().pause();
		// }
		// break;
		// case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
		// //ImageLoader.resumeLoader();
		//
		// Fresco.getImagePipelineFactory().re;
		//
		// break;
		// }
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem, int
		// visibleItemCount, int totalItemCount) {
		//
		// }
		// });
		hideNoData(v);
		HashMap<String, String> param = new HashMap<String, String>();

		param.put("itemCode", item_code);
		param.put("pageIndex", pageIndex + "");
		param.put("pageSize", pageSize + "");
		param.put("div_code", div_code);
		param.put("commnetStatus", commentStatus);
		param.put("has_imgs", has_imgs);
		param.put("userId", S.getShare(getContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {

					JSONObject jsonObject = data.getJSONObject(C.KEY_JSON_DATA);

					String string = data.getString("total");

					TotalCount = Integer.parseInt(string);
					if (TotalCount == 0) {
						showNoData(v, "데이터가 없습니다", null);
					}
					if (TotalCount - pageIndex * pageSize <= 0) {
						list.set_booter_gone();
					}
					JSONArray jsonArray = jsonObject.getJSONArray("comment_list");

					for (int i = 0; i < jsonArray.length(); i++) {

						array.put(jsonArray.get(i));
					}

					// listdata=CollectionUtil.jsonArrayToListMapObject(jsonArray);
					ElvuateAdapter adapter = new ElvuateAdapter(array, getContext(), getwindowswidth() / 3, false);
					list.setAdapter(adapter);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GET_GOODSOFCOMMENT, param);

	}

	// private void initData() {
	// HashMap<String, String> param=new HashMap<String, String>();
	//
	// param.put(C.KEY_JSON_FM_ITEM_CODE, "100");
	// param.put("commnetStatus", "0");
	//
	// OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
	// okHttpHelper.addPostRequest(new CallbackLogic() {
	//
	// private SmViewPagerAdapter1 adapter;
	//
	// @Override
	// public void onNetworkError(Request request, IOException e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onBizSuccess(String responseDescription, JSONObject data,
	// String flag) {
	// // TODO Auto-generated method stub
	// try {
	//
	// JSONObject jsonObject=data.getJSONObject(C.KEY_JSON_DATA);
	//// text6.setText(jsonObject.get("all_count").toString());
	//// text7.setText(jsonObject.get("good_count").toString());
	//// text8.setText(jsonObject.get("middle_count").toString());
	//// text9.setText(jsonObject.get("difference_count").toString());
	//// text10.setText(jsonObject.get("img_count").toString());
	//
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// @Override
	// public void onBizFailure(String responseDescription, JSONObject data,
	// String flag) {
	// // TODO Auto-generated method stub
	//
	// }
	// }, C.SM_BASE_URL + C.GET_GOODSOFCOMMENT,param);
	//
	// }
	@Override
	public void onRefresh() {

		// TODO Auto-generated method stub
		try {

			pageIndex = 1;
			try {
				array = new JSONArray("[]");
				initData();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				L.e(e);
			}

			list.stopLoadMore();
			list.stopRefresh();

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	public void onLoadMore() {

		// TODO Auto-generated method stub
		try {

			if (TotalCount - pageIndex * 20 <= 0) {
				Toast.makeText(getActivity(), getString(com.rs.mobile.wportal.R.string.common_text068), Toast.LENGTH_SHORT).show();

				list.stopLoadMore();
				list.stopRefresh();

				return;
			} else {
				pageIndex++;
				initData();

				list.stopLoadMore();
				list.stopRefresh();
			}

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

}
