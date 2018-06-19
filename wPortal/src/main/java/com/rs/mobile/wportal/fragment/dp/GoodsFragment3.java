package com.rs.mobile.wportal.fragment.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.S;
import com.rs.mobile.common.C;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class GoodsFragment3 extends Fragment {

	public static String FRAGMENT_DATA_STATUS = "commentStatus";

	public static String FRAGMENT_DATA_HAS_IMAGE = "has_imgs";

	private View v;

	private LinearLayout linearlayout_001, linearlayout_002, linearlayout_003, linearlayout_004, linearlayout_005;

	TextView text1, text2, text3, text4, text5;

	TextView text6;

	TextView text7;

	TextView text8;

	TextView text9;

	TextView text10;

	private String item_code;

	private String div_code;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.goods_fragment_evaluate, container, false);
		Bundle bundle = getArguments();
		item_code = bundle.getString(C.KEY_JSON_FM_ITEM_CODE);
		div_code = bundle.getString(C.KEY_DIV_CODE);
		initView();
		initData();
		return v;
	}

	/**
	 * 切换到已付款、待付款、服务中或待评价，同时带上要传递的数据
	 * 
	 * @param fragment
	 *            未包含要传递的数据Bundle
	 * @param data
	 *            需要传递给fragment的数据
	 */
	private void replaceFragmentPageWithData(Fragment fragment, Bundle data) {

		if (data != null) {
			fragment.setArguments(data);
		}

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.my_order_xml_fragment_root, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	private void initView() {

		text1 = (TextView) v.findViewById(R.id.textview_001);

		text2 = (TextView) v.findViewById(R.id.textview_002);

		text3 = (TextView) v.findViewById(R.id.textview_003);

		text4 = (TextView) v.findViewById(R.id.textview_004);

		text5 = (TextView) v.findViewById(R.id.textview_005);

		text6 = (TextView) v.findViewById(R.id.textview_006);

		text7 = (TextView) v.findViewById(R.id.textview_007);

		text8 = (TextView) v.findViewById(R.id.textview_008);

		text9 = (TextView) v.findViewById(R.id.textview_009);

		text10 = (TextView) v.findViewById(R.id.textview_010);

		linearlayout_001 = (LinearLayout) v.findViewById(R.id.linearlayout_001);

		linearlayout_001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				text1.setTextColor(getResources().getColor(R.color.red));
				text6.setTextColor(getResources().getColor(R.color.red));
				text2.setTextColor(getResources().getColor(R.color.black));
				text7.setTextColor(getResources().getColor(R.color.black));
				text3.setTextColor(getResources().getColor(R.color.black));
				text8.setTextColor(getResources().getColor(R.color.black));
				text4.setTextColor(getResources().getColor(R.color.black));
				text9.setTextColor(getResources().getColor(R.color.black));
				text5.setTextColor(getResources().getColor(R.color.black));
				text10.setTextColor(getResources().getColor(R.color.black));

				Bundle bundle = new Bundle();
				bundle.putString(FRAGMENT_DATA_HAS_IMAGE, "false");
				bundle.putString(FRAGMENT_DATA_STATUS, "0");
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
				bundle.putString(C.KEY_DIV_CODE, div_code);
				replaceFragmentPageWithData(new DpEvaluateCommonPage(), bundle);

			}
		});
		linearlayout_002 = (LinearLayout) v.findViewById(R.id.linearlayout_002);

		linearlayout_002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				text2.setTextColor(getResources().getColor(R.color.red));
				text7.setTextColor(getResources().getColor(R.color.red));
				text1.setTextColor(getResources().getColor(R.color.black));
				text6.setTextColor(getResources().getColor(R.color.black));
				text3.setTextColor(getResources().getColor(R.color.black));
				text8.setTextColor(getResources().getColor(R.color.black));
				text4.setTextColor(getResources().getColor(R.color.black));
				text9.setTextColor(getResources().getColor(R.color.black));
				text5.setTextColor(getResources().getColor(R.color.black));
				text10.setTextColor(getResources().getColor(R.color.black));

				Bundle bundle = new Bundle();
				bundle.putString(FRAGMENT_DATA_HAS_IMAGE, "false");
				bundle.putString(FRAGMENT_DATA_STATUS, "1");
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
				bundle.putString(C.KEY_DIV_CODE, div_code);
				replaceFragmentPageWithData(new DpEvaluateCommonPage(), bundle);
			}
		});

		linearlayout_003 = (LinearLayout) v.findViewById(R.id.linearlayout_003);

		linearlayout_003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				text3.setTextColor(getResources().getColor(R.color.red));
				text8.setTextColor(getResources().getColor(R.color.red));
				text2.setTextColor(getResources().getColor(R.color.black));
				text7.setTextColor(getResources().getColor(R.color.black));
				text1.setTextColor(getResources().getColor(R.color.black));
				text6.setTextColor(getResources().getColor(R.color.black));
				text4.setTextColor(getResources().getColor(R.color.black));
				text9.setTextColor(getResources().getColor(R.color.black));
				text5.setTextColor(getResources().getColor(R.color.black));
				text10.setTextColor(getResources().getColor(R.color.black));

				Bundle bundle = new Bundle();
				bundle.putString(FRAGMENT_DATA_HAS_IMAGE, "false");
				bundle.putString(FRAGMENT_DATA_STATUS, "2");
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
				bundle.putString(C.KEY_DIV_CODE, div_code);
				replaceFragmentPageWithData(new DpEvaluateCommonPage(), bundle);
			}
		});

		linearlayout_004 = (LinearLayout) v.findViewById(R.id.linearlayout_004);

		linearlayout_004.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				text4.setTextColor(getResources().getColor(R.color.red));
				text9.setTextColor(getResources().getColor(R.color.red));
				text2.setTextColor(getResources().getColor(R.color.black));
				text7.setTextColor(getResources().getColor(R.color.black));
				text3.setTextColor(getResources().getColor(R.color.black));
				text8.setTextColor(getResources().getColor(R.color.black));
				text1.setTextColor(getResources().getColor(R.color.black));
				text6.setTextColor(getResources().getColor(R.color.black));
				text5.setTextColor(getResources().getColor(R.color.black));
				text10.setTextColor(getResources().getColor(R.color.black));

				Bundle bundle = new Bundle();
				bundle.putString(FRAGMENT_DATA_HAS_IMAGE, "false");
				bundle.putString(FRAGMENT_DATA_STATUS, "3");
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
				bundle.putString(C.KEY_DIV_CODE, div_code);
				replaceFragmentPageWithData(new DpEvaluateCommonPage(), bundle);
			}
		});

		linearlayout_005 = (LinearLayout) v.findViewById(R.id.linearlayout_005);

		linearlayout_005.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				text5.setTextColor(getResources().getColor(R.color.red));
				text10.setTextColor(getResources().getColor(R.color.red));
				text2.setTextColor(getResources().getColor(R.color.black));
				text7.setTextColor(getResources().getColor(R.color.black));
				text3.setTextColor(getResources().getColor(R.color.black));
				text8.setTextColor(getResources().getColor(R.color.black));
				text4.setTextColor(getResources().getColor(R.color.black));
				text9.setTextColor(getResources().getColor(R.color.black));
				text1.setTextColor(getResources().getColor(R.color.black));
				text6.setTextColor(getResources().getColor(R.color.black));

				Bundle bundle = new Bundle();
				bundle.putString(FRAGMENT_DATA_HAS_IMAGE, "true");
				bundle.putString(FRAGMENT_DATA_STATUS, "0");
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
				bundle.putString(C.KEY_DIV_CODE, div_code);
				replaceFragmentPageWithData(new DpEvaluateCommonPage(), bundle);
			}
		});

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = new Bundle();
		bundle.putString(FRAGMENT_DATA_STATUS, "0");
		bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
		bundle.putString(C.KEY_DIV_CODE, div_code);
		replaceFragmentPageWithData(new DpEvaluateCommonPage(), bundle);
	}

	private void initData() {

		HashMap<String, String> param = new HashMap<String, String>();

		param.put("itemCode", item_code);
		param.put("commnetStatus", "0");
		param.put("has_imgs", "false");
		param.put("pageIndex", "1");
		param.put("div_code", div_code);
		param.put("pageSize", "20");
		param.put("userId", S.getShare(getContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
		okHttpHelper.addSMPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = data.getJSONObject(C.KEY_JSON_DATA);

					text6.setText(jsonObject.get("all_count").toString());
					text7.setText(jsonObject.get("good_count").toString());
					text8.setText(jsonObject.get("middle_count").toString());
					text9.setText(jsonObject.get("difference_count").toString());
					text10.setText(jsonObject.get("img_count").toString());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP1 + Constant.GET_GOODSOFCOMMENT, param);

	}

	/**
	 * 设置Fragment顶部的切换条的状态
	 * 
	 * @param pageIndex
	 *            第几页面（从0开始）
	 */
	public int getbuttonHeight() {

		LinearLayout l = (LinearLayout) v.findViewById(R.id.line_buttom);
		return l.getHeight();
	}

	// ViewTreeObserver vto = imageView.getViewTreeObserver();
	// vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	// @Override
	// public void onGlobalLayout() {
	// imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	// imageView.getHeight();
	// imageView.getWidth();
	// }
	// });
	public static GoodsFragment3 newInstance(String s, String divCode) {

		GoodsFragment3 myFragment = new GoodsFragment3();
		Bundle bundle = new Bundle();
		bundle.putString(C.KEY_JSON_FM_ITEM_CODE, s);
		bundle.putString(C.KEY_DIV_CODE, divCode);
		myFragment.setArguments(bundle);
		return myFragment;
	}

}
