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
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.ht.HtEvaluateAdapter;
import com.rs.mobile.wportal.biz.ht.HotelEvaluate;
import com.rs.mobile.wportal.biz.ht.HotelPhoto;
import com.rs.mobile.wportal.fragment.BaseFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import okhttp3.Request;

@SuppressLint("ValidFragment")
public class HotelEvaluateListFragment extends BaseFragment implements OnRefreshListener2<ListView> {
	private int total;
	private int iPageIndex;
	private int iPageSize;
	private List<HotelEvaluate> listData = new ArrayList<>();

	public HotelEvaluateListFragment(String hotelInfoID, String opIndex) {
		super();
		HotelInfoID = hotelInfoID;
		OpIndex = opIndex;
	}

	private RatingBar ratingbar;
	private TextView text_score, text_score1, text_score2, text_score3, text_score4;
	private ProgressBar sv1, sv2, sv3, sv4;
	private String HotelInfoID;
	private String OpIndex;
	private PullToRefreshListView lv_evaluate;
	protected HtEvaluateAdapter adapter;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_ht_evaluate_list, container, false);
		iPageIndex = 1;
		iPageSize = 10;
		initView(v);
		initdata();
		return v;
	}

	private void initView(View v) {
		ratingbar = (RatingBar) v.findViewById(R.id.ratingbar);
		text_score = (TextView) v.findViewById(R.id.text_score);
		text_score1 = (TextView) v.findViewById(R.id.text_score1);
		text_score2 = (TextView) v.findViewById(R.id.text_score2);
		text_score3 = (TextView) v.findViewById(R.id.text_score3);
		text_score4 = (TextView) v.findViewById(R.id.text_score4);
		lv_evaluate = (PullToRefreshListView) v.findViewById(R.id.lv_evaluate);
		lv_evaluate.setOnRefreshListener(this);
		lv_evaluate.setMode(Mode.BOTH);
		sv1 = (ProgressBar) v.findViewById(R.id.sv1);
		sv2 = (ProgressBar) v.findViewById(R.id.sv2);
		sv3 = (ProgressBar) v.findViewById(R.id.sv3);
		sv4 = (ProgressBar) v.findViewById(R.id.sv4);

	}

	private void initdata() {
		hideNoData(v);
		OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
		HashMap<String, String> params = new HashMap<>();
		params.put("HotelInfoID", HotelInfoID);
		params.put("OpIndex", OpIndex);
		params.put("iPageIndex", iPageIndex + "");
		params.put("iPageSize", iPageSize + "");
		okHttpHelper.addPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				lv_evaluate.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				lv_evaluate.onRefreshComplete();
				try {
					if (data.get("status").toString().equals("1")) {
						total = data.getInt("total");
						JSONObject obj = data.getJSONObject("data");
						JSONArray HotelRated = obj.getJSONArray("HotelRated");
						JSONObject objRated = HotelRated.getJSONObject(0);
						ratingbar.setRating(Float.parseFloat(objRated.get("total_score").toString()));
						text_score.setText(objRated.get("total_score").toString());
						sv1.setProgress((int) (Float.parseFloat(objRated.get("servicescore").toString()) * 20));
						text_score1.setText(objRated.get("servicescore").toString());
						sv2.setProgress((int) (Float.parseFloat(objRated.get("facilitiesproportion").toString()) * 20));
						text_score2.setText(objRated.get("facilitiesproportion").toString());
						sv3.setProgress((int) (Float.parseFloat(objRated.get("environmentalscore").toString()) * 20));
						text_score3.setText(objRated.get("environmentalscore").toString());
						sv4.setProgress((int) (Float.parseFloat(objRated.get("hygienescore").toString()) * 20));
						text_score4.setText(objRated.get("hygienescore").toString());
						JSONArray RatedList = obj.getJSONArray("RatedList");
						if (iPageIndex == 1) {
							listData.clear();
						}
						if (RatedList.length() == 0) {
							showNoData(v, "没有相关评价哦！", null);
						}
						for (int i = 0; i < RatedList.length(); i++) {
							JSONObject RateObj = RatedList.getJSONObject(i);
							JSONArray imgurl = RateObj.getJSONArray("imgurl");
							List<HotelPhoto> imgurl1 = new ArrayList<>();
							for (int j = 0; j < imgurl.length(); j++) {
								JSONObject objimg = imgurl.getJSONObject(j);
								HotelPhoto h = new HotelPhoto(0, objimg.getString("ImageID"),
										objimg.getString("ImageURL"));
								imgurl1.add(h);
							}

							HotelEvaluate he = new HotelEvaluate(RateObj.getString("ratedid"),
									RateObj.getString("nick_name"), Float.parseFloat(RateObj.getString("total_score")),
									RateObj.getString("ratedtime"), RateObj.getString("roomtypename"), imgurl1,
									RateObj.getString("context"), RateObj.getString("parent_rated"));

							listData.add(he);
						}
						if (iPageIndex == 1) {
							adapter = new HtEvaluateAdapter(listData, getActivity());
							lv_evaluate.setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}

					} else {
						T.showToast(getContext(), data.getString("msg"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				lv_evaluate.onRefreshComplete();
			}
		}, Constant.BASE_URL_HT + Constant.HT_GET_RATELIST, params);

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> lv_evaluate) {
		// TODO Auto-generated method stub
		iPageIndex = 1;
		initdata();

	}

	@Override
	public void onPullUpToRefresh(final PullToRefreshBase<ListView> lv_evaluate) {
		// TODO Auto-generated method stub
		if (iPageIndex * iPageSize >= total) {
			lv_evaluate.postDelayed(new Runnable() {
				public void run() {
					T.showToast(getActivity(), getString(R.string.common_text068));
					lv_evaluate.onRefreshComplete();
				}
			}, 500);

		} else {
			iPageIndex++;
			initdata();
		}

	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

}
