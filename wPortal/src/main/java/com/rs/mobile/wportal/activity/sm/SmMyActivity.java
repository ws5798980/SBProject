
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.PointsActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class SmMyActivity extends BaseActivity {

	private TextView text_points, text_coupon, text_collect, text_name, text_knick_name;

	private TextView text_nonpay_num, text_nonpay_num1, text_nonpay_num2, text_to_order, text_nonpay_num3,
			text_nonpay_num4, txt_nonpay1, txt_nonpay, txt_nonpay2, txt_nonpay3, txt_nonpay4;

	private List<TextView> list;

	private WImageView img_icon;

	private LinearLayout line_aboutus, text_to_address, line_coupon, line_backmoney;

	private LinearLayout line_collect;

	private LinearLayout line_point;

	private LinearLayout line_comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_my);
		initView();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// if (Constant.SMMY_REFRESH) {
		initData();
		// }
	}

	private void initView() {

		img_icon = (WImageView) findViewById(com.rs.mobile.wportal.R.id.img_icon);
		// android.view.ViewGroup.LayoutParams parms=img_icon.getLayoutParams();
		// parms.width=get_windows_width(SmMyActivity.this)/5;
		//// parms.height=get_windows_width(SmMyActivity.this)/5;
		//
		// img_icon.setLayoutParams(parms);
		img_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				// PageUtil.jumpTo(SmMyActivity.this,
				// SmLogisticsActivity.class);
			}
		});
		line_coupon = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_coupon);
		line_coupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				PageUtil.jumpTo(SmMyActivity.this, SmCouponActivity.class);

			}
		});
		line_collect = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_collect);
		line_collect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				PageUtil.jumpTo(SmMyActivity.this, SmCollectionActivity.class);
			}
		});
		line_point = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_point);
		line_point.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				PageUtil.jumpTo(SmMyActivity.this, PointsActivity.class);
			}
		});
		text_to_address = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.text_to_address);
		text_to_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				PageUtil.jumpTo(SmMyActivity.this, SmAddressActivity.class);
			}
		});
		line_comment = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_comment);
		line_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(SmMyActivity.this, MyEvaluateActivity.class);
			}
		});
		text_to_order = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_to_order);
		text_to_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				UtilClear.CheckLogin(SmMyActivity.this,new UtilCheckLogin.CheckListener(){

					@Override
					public void onDoNext() {
						PageUtil.jumpToWithFlag(SmMyActivity.this, MyOrderActivity.class);
					}
					
				});
			}
		});

		txt_nonpay = (TextView) findViewById(com.rs.mobile.wportal.R.id.txt_nonpay);

		txt_nonpay3 = (TextView) findViewById(com.rs.mobile.wportal.R.id.txt_nonpay3);
		txt_nonpay4 = (TextView) findViewById(com.rs.mobile.wportal.R.id.txt_nonpay4);

		text_name = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_name);

		text_knick_name = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_knick_name);

		text_points = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_points);
		text_points.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				UtilClear.CheckLogin(SmMyActivity.this,new UtilCheckLogin.CheckListener(){

					@Override
					public void onDoNext() {
						PageUtil.jumpTo(SmMyActivity.this, PointsActivity.class);
					}
					
				});
			}
		});

		text_coupon = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_coupon);
		text_collect = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_collect);

		text_nonpay_num = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_nonpay_num);

		text_nonpay_num3 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_nonpay_num3);
		text_nonpay_num4 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_nonpay_num4);

		line_aboutus = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_aboutus);
		line_aboutus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				PageUtil.jumpTo(SmMyActivity.this, SmAboutUs.class);
			}
		});

		list = new ArrayList<TextView>();

		list.add(text_nonpay_num);

		list.add(text_nonpay_num3);
		list.add(text_nonpay_num4);

		findViewById(com.rs.mobile.wportal.R.id.pay_type_001_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putInt("position", 1);
				PageUtil.jumpToWithFlag(SmMyActivity.this, MyOrderActivity.class, bundle);

			}
		});

		findViewById(com.rs.mobile.wportal.R.id.pay_type_004_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putInt("position", 3);
				PageUtil.jumpToWithFlag(SmMyActivity.this, MyOrderActivity.class, bundle);

			}
		});

		findViewById(com.rs.mobile.wportal.R.id.pay_type_005_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putInt("position", 5);
				PageUtil.jumpToWithFlag(SmMyActivity.this, MyOrderActivity.class, bundle);

			}
		});
		line_backmoney = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_backmoney);
		line_backmoney.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				PageUtil.jumpToWithFlag(SmMyActivity.this, SmReturnGoodsListActivity.class);
			}
		});

	}

	private void initData() {

		getUserInfo();
	}

	private void getUserInfo() {

		try {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setVisibility(View.GONE);
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_TOKEN, S.getShare(SmMyActivity.this, C.KEY_JSON_TOKEN, ""));
			params.put("appType", "6");
			OkHttpHelper okHttpHelper = new OkHttpHelper(SmMyActivity.this);
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

						
						text_name.setText(jsonObject.get("custom_id").toString());
						S.setShare(SmMyActivity.this, C.KEY_SHARED_PHONE_NUMBER, jsonObject.get("custom_id").toString());
						
						text_knick_name.setText(jsonObject.get("nick_name").toString());

						text_collect.setText(jsonObject.get(C.KEY_JSON_FM_COLLECTION_COUNT).toString());

						text_coupon.setText(jsonObject.get(C.KEY_JSON_FM_COUPONS_COUNT).toString());

						text_points.setText(jsonObject.get(C.KEY_JSON_FM_POINT).toString());

						S.set(SmMyActivity.this, "point", jsonObject.get(C.KEY_JSON_FM_POINT).toString());

						img_icon.setImageURI(Uri.parse(jsonObject.get("head_url").toString()));

						// getMyPhoto();

						JSONArray jsonArray = jsonObject.getJSONArray(C.KEY_JSON_FM_ORDERS);
						for (int i = 0; i < jsonArray.length(); i++) {

							JSONObject jsObject = new JSONObject(jsonArray.get(i).toString());
							String status = jsObject.get("ONLINE_ORDER_STATUS").toString();

							if (!jsObject.get(C.KEY_JSON_FM_ORDER_COUNT).toString().equals("0")) {

								if (status.equals("1")) {
									list.get(0).setVisibility(View.VISIBLE);
									list.get(0).setText(jsObject.get(C.KEY_JSON_FM_ORDER_COUNT).toString());
								} else if (status.equals("3")) {
									list.get(1).setVisibility(View.VISIBLE);
									list.get(1).setText(jsObject.get(C.KEY_JSON_FM_ORDER_COUNT).toString());
								} else if (status.equals("5")) {
									list.get(2).setVisibility(View.VISIBLE);
									list.get(2).setText(jsObject.get(C.KEY_JSON_FM_ORDER_COUNT).toString());
								}

							}

						}
						Constant.SMMY_REFRESH = false;
					} catch (Exception e) {
						L.e(e);
					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.GET_USERINFO, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	public void getMyPhoto() {

		try {

			showProgressBar();

			OkHttpHelper helper = new OkHttpHelper(SmMyActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_REQUEST_MEMBER_ID, S.getShare(SmMyActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, final JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						String imgPath = data.getString("imagePath");

						Uri uri = Uri.parse(imgPath);

						RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(img_icon.getWidth(),
								img_icon.getHeight());

						ImageUtil.drawIamge(img_icon, uri, lp);

					} catch (Exception e) {

						L.e(e);

					}

					hideProgressBar();

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, C.BASE_URL + Constant.PERSNAL_GET_INFO, params);

		} catch (Exception e) {

			L.e(e);

			hideProgressBar();

		}

	}

}
