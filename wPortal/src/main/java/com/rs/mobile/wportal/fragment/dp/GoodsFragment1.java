package com.rs.mobile.wportal.fragment.dp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.CollectionUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.dp.DpGoodsDetailActivity;
import com.rs.mobile.wportal.adapter.dp.DpViewPagerAdapter1;
import com.rs.mobile.wportal.view.CustomViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import okhttp3.Request;

@SuppressLint("NewApi")
public class GoodsFragment1 extends Fragment {

	private Context context;

	private CustomViewPager viewpager;

	private RadioGroup radioGroup;

	private TextView textView001, textView002, goods_fragment_text_2_1, textView003, goods_fragment_text_3_1,
			textView004, textView005, textView006, textView007, textView008, textView009, textView010, textView011,
			textView012, textView013, goods_fragment_text_4_deliver;

	private LinearLayout line_change;

	private ImageView imageview_share;

	// private static AmountView amoutView;

	private View rootView;

	private String unit_price, supplier_code, business_code;

	public boolean hasCollection;

	public static boolean load;

	public ShoppingCart shoppingCart;

	private String name;

	public String imgurl;

	private String price;

	private String unit;

	private String item_code;

	private TextView goods_fragment_text_4_stock;

	private JSONObject jsonObject;

	public List<Map<String, Object>> justOptionList;

	public List<Map<String, Object>> attachOptionList;

	private String div_code;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		load = true;

		Bundle bundle = getArguments();

		item_code = bundle.getString(C.KEY_JSON_FM_ITEM_CODE);

		div_code = bundle.getString(C.KEY_DIV_CODE);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		context = container.getContext();

		if (load) {

			rootView = inflater.inflate(R.layout.goods_fragment_detail, container, false);// 关联布局文件

			initview();
			initdata();

		}

		return rootView;

	}

	@Override
	public void onResume() {

		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		load = false;

	}

	/**
	 * initview
	 */
	private void initview() {

		try {

			textView001 = (TextView) rootView.findViewById(R.id.fragment_goods_text_1);

//			imageview_share = (ImageView) rootView.findViewById(R.id.goods_fragment_share);
//
//			imageview_share.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//
//					try {
//
//						JSONArray arr = jsonObject.getJSONArray(C.KEY_JSON_IMAGES);
//
//						JSONObject obj = arr.getJSONObject(0);
//
//						UiUtil.share(context, C.TYPE_FRESH_MART, item_code, name,
//								getResources().getString(R.string.rmb) + price + "/" + unit, obj.getString("url"));
//
//					} catch (Exception e) {
//
//						L.e(e);
//
//					}
//				}
//			});
			textView002 = (TextView) rootView.findViewById(R.id.goods_fragment_text_2);

			textView003 = (TextView) rootView.findViewById(R.id.goods_fragment_text_3);
			textView003.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线

			textView004 = (TextView) rootView.findViewById(R.id.goods_fragment_text_4);

			goods_fragment_text_4_stock = (TextView) rootView.findViewById(R.id.goods_fragment_text_4_stock);

			textView005 = (TextView) rootView.findViewById(R.id.goods_fragment_text_5);

			textView006 = (TextView) rootView.findViewById(R.id.goods_fragment_text_6);

			goods_fragment_text_4_deliver = (TextView) rootView.findViewById(R.id.goods_fragment_text_4_deliver);

			// amoutView = (AmountView)
			// rootView.findViewById(R.id.goods_fragment_amountview);
			// amoutView.setOnAmountChangeListener(new OnAmountChangeListener()
			// {
			//
			// @Override
			// public void onAmountChange(View view, int amount) {
			// // TODO Auto-generated method stub
			//
			// }
			// });
			line_change = (LinearLayout) rootView.findViewById(R.id.line_change);
			// textView007 = (TextView)
			// rootView.findViewById(R.id.goods_fragment_text_7);

			textView008 = (TextView) rootView.findViewById(R.id.goods_fragment_text_8);

			textView009 = (TextView) rootView.findViewById(R.id.goods_fragment_text_9);

			textView010 = (TextView) rootView.findViewById(R.id.goods_fragment_text_10);

			textView011 = (TextView) rootView.findViewById(R.id.goods_fragment_text_11);

			textView012 = (TextView) rootView.findViewById(R.id.goods_fragment_text_12);

			textView013 = (TextView) rootView.findViewById(R.id.goods_fragment_text_13);

			goods_fragment_text_2_1 = (TextView) rootView.findViewById(R.id.goods_fragment_text_2_1);

			goods_fragment_text_3_1 = (TextView) rootView.findViewById(R.id.goods_fragment_text_3_1);

			viewpager = (CustomViewPager) rootView.findViewById(R.id.goods_fragemnt_viewpager);
			radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initdata() {

		try {

			HashMap<String, String> param = new HashMap<String, String>();

			param.put(C.KEY_JSON_FM_ITEM_CODE, item_code);

			param.put("div_code", div_code);

			param.put(C.KEY_JSON_FM_SUPPLIER_ID, "yc01");

			param.put("userId", S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));

			OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
			okHttpHelper.addPostRequest(new CallbackLogic() {

				private DpViewPagerAdapter1 adapter;

				private String stock;

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

					try {

						// {"status":-1,"msg":"库存信息不存在"}

						String status = data.getString("status");

						if (status != null && status.equals("-1")) {

							Activity activity = (Activity) rootView.getContext();

							activity.finish();

							T.showToast(activity, data.getString("message"));

							return;

						}

						jsonObject = data.getJSONObject(C.KEY_JSON_DATA);
						JSONArray arrjust = jsonObject.getJSONArray("justOptionList");
						JSONArray arrattach = jsonObject.getJSONArray("attachOptionList");
						attachOptionList = CollectionUtil.jsonArrayToListMapObject(arrattach);
						justOptionList = CollectionUtil.jsonArrayToListMapObject(arrjust);
						JSONArray arr = jsonObject.getJSONArray("images");
						imgurl = arr.getJSONObject(0).getString("url");

						adapter = new DpViewPagerAdapter1(getActivity(), jsonObject, 1);
						viewpager.setAdapter(adapter);
						int movieCount = adapter.getCount();

						Resources res = getResources();

						for (int i = 0; i < movieCount; i++) {

							RadioButton btn = new RadioButton(getActivity());
							btn.setPadding(5, 5, 5, 5);
							btn.setButtonDrawable(res.getDrawable(R.drawable.bg_viewpage_yuan));
							btn.setGravity(Gravity.CENTER);

							radioGroup.addView(btn);

						}
						radioGroup.check(radioGroup.getChildAt(0).getId());
						radioGroup.setVisibility(View.GONE);
						viewpager.addOnPageChangeListener(new OnPageChangeListener() {

							@Override
							public void onPageSelected(int arg0) {

								// TODO Auto-generated method stub
								// radioGroup.check(radioGroup.getChildAt(arg0).getId());
								adapter.notifyDataSetChanged();
							}

							@Override
							public void onPageScrolled(int arg0, float arg1, int arg2) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onPageScrollStateChanged(int arg0) {
								// TODO Auto-generated method stub

							}
						});

						name = jsonObject.getString("title");

						price = jsonObject.getString("price");

						unit = jsonObject.getString("unit");

						stock = jsonObject.get("stock").toString();

						textView001.setText(name);

						textView002.setText(getResources().getString(R.string.rmb) + price);

						goods_fragment_text_2_1.setText("/" + unit);

						textView003
								.setText(getResources().getString(R.string.rmb) + jsonObject.getString("markeyPrice"));

						goods_fragment_text_3_1.setText("/" + unit);

						goods_fragment_text_4_stock.setText(getString(R.string.common_text111) + stock);

						goods_fragment_text_4_deliver
								.setText(getString(R.string.common_text112) + jsonObject.getString("expressAmount"));

						textView004.setText(
								getResources().getString(R.string.sm_text_sold) + jsonObject.getString("sold"));

						textView005.setText(jsonObject.getString("city"));

						JSONArray featuresArr = jsonObject.getJSONArray("features");

						textView008.setText(featuresArr.get(0).toString());

						textView009.setText(featuresArr.get(1).toString());

						textView010.setText(featuresArr.get(2).toString());

						boolean canChange = jsonObject.getBoolean("hasChangeBuy");

						if (canChange) {
							line_change.setVisibility(View.VISIBLE);
							textView011.setText(jsonObject.getString("hasChangeBuyTitle"));
						} else {
							line_change.setVisibility(View.GONE);
						}

						textView012.setText(
								getResources().getString(R.string.sm_text_storage) + jsonObject.getString("storage"));

						textView013.setText(getResources().getString(R.string.sm_text_good_code)
								+ jsonObject.getString("itemCode"));

						hasCollection = jsonObject.getBoolean("hasFav");

						int k = (int) Float.parseFloat(stock);

						// amoutView.setGoods_storage(k);

						if (k == 0) {
							// T.showToast(context,
							// getString(R.string.common_text113));
							// amoutView.setEditText(0);
							((DpGoodsDetailActivity) context).sm_goods_to_buy.setEnabled(false);
							((DpGoodsDetailActivity) context).sm_goods_to_buy
									.setBackgroundColor(context.getResources().getColor(R.color.hui));

						}

						((DpGoodsDetailActivity) context).changeCollection(hasCollection);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_DP1 + Constant.GET_GOODSOFDETAILS, param);

		} catch (Exception e) {

			L.e(e);

		}
	}

	public static GoodsFragment1 newInstance(String s, String divCode) {

		GoodsFragment1 myFragment = new GoodsFragment1();
		Bundle bundle = new Bundle();
		bundle.putString(C.KEY_JSON_FM_ITEM_CODE, s);
		bundle.putString(C.KEY_DIV_CODE, divCode);
		myFragment.setArguments(bundle);
		return myFragment;
	}

	// public String getGoodAmount() {
	// return amoutView.getAmount() + "";
	// }
	// public String totalPrice(){
	// return Float.parseFloat(price)*Integer.parseInt(getGoodAmount())+"";
	// }
	// public ShoppingCart getShoppingcart() {
	//
	// shoppingCart = new ShoppingCart(item_code, name, Float.parseFloat(price),
	// imgurl,
	// Integer.parseInt(getGoodAmount()), false, unit);
	//
	// return shoppingCart;
	// }
}
