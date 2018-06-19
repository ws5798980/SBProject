package com.rs.mobile.wportal.fragment.ht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.ht.HtAlbumAdapter1;
import com.rs.mobile.wportal.biz.ht.HotelPhoto;
import com.rs.mobile.wportal.fragment.BaseFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import okhttp3.Request;

@SuppressLint("ValidFragment")
public class HotelAlbumFragment extends BaseFragment implements OnRefreshListener2<ListView> {
	public HotelAlbumFragment(String HotelInfoID, String iType) {
		super();
		this.iType = iType;
		this.HotelInfoID = HotelInfoID;
	}

	public void setHotelAlbumFragment(String HotelInfoID, String iType) {
		this.iType = iType;
		this.HotelInfoID = HotelInfoID;
	}

	private String HotelInfoID;
	private String iType;
	private PullToRefreshListView lv;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(com.rs.mobile.wportal.R.layout.fragment_ht_hotel_album, container, false);
		lv = (PullToRefreshListView) view.findViewById(com.rs.mobile.wportal.R.id.lv);
		lv.setOnRefreshListener(this);
		lv.setMode(Mode.PULL_FROM_START);
		initData();
		return view;

	}

	private void initData() {
		hideNoData(view);
		OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
		HashMap<String, String> params = new HashMap<>();
		params.put("HotelInfoID", HotelInfoID);
		params.put("iType", iType);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				lv.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {

					lv.onRefreshComplete();
					String status = data.get("status").toString();
					List<HotelPhoto> list = new ArrayList<>();

					if (status.equals("1")) {
						JSONArray arr = data.getJSONArray("data");
						if (arr.length() == 0) {
							showNoData(view, "没有相关图片哦！", null);
						}
						for (int i = 0; i < arr.length(); i++) {
							JSONObject j = arr.getJSONObject(i);
							list.add(new HotelPhoto(j.getInt("imagetype"), j.getString("imageid"),
									j.getString("imgurl")));
						}

						HtAlbumAdapter1 adapter = new HtAlbumAdapter1(list, getContext());

						lv.setAdapter(adapter);

					} else {
						T.showToast(getActivity(), data.getString("msg"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				lv.onRefreshComplete();
			}
		}, Constant.BASE_URL_HT + Constant.HT_GET_ALBUM, params);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		initData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

}
