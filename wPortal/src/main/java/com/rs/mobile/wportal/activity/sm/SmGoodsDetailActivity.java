package com.rs.mobile.wportal.activity.sm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.MyFragmentPageAdapter;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.fragment.sm.GoodsFragment1;
import com.rs.mobile.wportal.fragment.sm.GoodsFragment2;
import com.rs.mobile.wportal.fragment.sm.GoodsFragment3;
import com.rs.mobile.wportal.view.AmountView;
import com.rs.mobile.wportal.view.FormatTextView;
import com.rs.mobile.wportal.view.GoodsFormatView;
import com.umeng.socialize.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class SmGoodsDetailActivity extends BaseActivity {

	private ViewPager viewPager;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	private GoodsFragment1 goodsFragment1;

	private GoodsFragment2 goodsFragment2;

	private GoodsFragment3 goodsFragment3;

	private PagerSlidingTabStrip tabs;

	private RelativeLayout relativeLayout;

	private static String sale_custom_code;

	private String item_code;

	public TextView sm_goods_to_buy, sm_goods_add_shoppingcart, service_button;

	private LinearLayout close_btn;

	private TextView shoping_cart;

	private TextView text_total, text_to_buy;

	private CheckBox collection_checkbox;

	private ScrollView scrollView;

	private LinearLayout line_format, line_format_requer;

	private RelativeLayout pop_window, rela_top;

	private GoodsFormatView goodsFormatView;

	private GoodsFormatView goodsFormatView1;

	private ArrayList<ShoppingCart> list_adjust;

	private ArrayList<ShoppingCart> list_attach;

	private ImageView img_cls;

	private int popType = 1;

	private static String mToken;

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			switch (msg.what) {
			case 10:
				getTotalPrice();
				break;

			default:
				break;
			}
		};

	};

	private float price;

	private static int IntentStatus = 0; // 扫描跳转判断标记

	private ShoppingCart shopingcart, shopingcart_attach;

	private AmountView amoutView1;

	private static String div_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		try {

			setContentView(R.layout.activity_sm_goods_detail);
			list_adjust = new ArrayList<>();
			list_attach = new ArrayList<>();
			mToken = S.get(SmGoodsDetailActivity.this, C.KEY_JSON_TOKEN);
			initview();

		} catch (Exception e) {

			L.e(e);

		}
	}

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	private void initview() {
		IntentStatus = getIntent().getIntExtra("IntentStatus", 0);
		try {
			scrollView = (ScrollView) findViewById(R.id.scrollView);
			amoutView1 = (AmountView) findViewById(R.id.amoutView1);
			line_format = (LinearLayout) findViewById(R.id.line_format);
			line_format_requer = (LinearLayout) findViewById(R.id.line_format_requer);
			shoping_cart = (TextView) findViewById(R.id.shoping_cart);
			rela_top = (RelativeLayout) findViewById(R.id.rela_top);
			img_cls = (ImageView) findViewById(R.id.img_cls);
			img_cls.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					pop_window.setVisibility(View.INVISIBLE);
				}
			});
			RelativeLayout.LayoutParams parms = (RelativeLayout.LayoutParams) rela_top
					.getLayoutParams();
			parms.height = (int) (get_windows_width(getApplicationContext()) / 4);
			parms.width = get_windows_width(getApplicationContext());
			rela_top.setLayoutParams(parms);
			text_total = (TextView) findViewById(R.id.text_total);

			//팝업 후 상품 구매 버튼
			text_to_buy = (TextView) findViewById(R.id.text_to_buy);
			text_to_buy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d("rsapp", "text_to_buy ====> onclick IN");
					if (popType == 1) {
						Log.d("rsapp", "text_to_buy ====> popType");
//						if (UiUtil.checkLogin(SmGoodsDetailActivity.this) == true) {
						//if(!S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, "").isEmpty()) {
//						}
						if(S.get(SmGoodsDetailActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(SmGoodsDetailActivity.this, C.KEY_JSON_TOKEN).isEmpty()){
//						if(UiUtil.checkLogin(SmGoodsDetailActivity.this) == true) {
							Log.d("rsapp", "text_to_buy =====> checkLogin Success");
							Bundle bundle = new Bundle();
							if (list_adjust.size() <= 0) {
								Log.d("rsapp", "list_adjust");

								t(getString(R.string.sm_text_plz_choose_good));
								return;
							}
							try {
								list_adjust.get(0).setNum(amoutView1.getAmount());
								bundle.putParcelableArrayList("goods", list_adjust);
								bundle.putParcelableArrayList("attachment",
										list_attach);

								bundle.putString("total", price + "");
								bundle.putString("onCartProcess", "false");
								bundle.putString("sale_custom_code", sale_custom_code);
								PageUtil.jumpTo(SmGoodsDetailActivity.this,
										SmConfirmActivity.class, bundle);
								finish();
							} catch (Exception ex){
								Log.d("rsapp", "text_to_buy Exception =====> " + ex.toString());
							}
						}else{
							Intent intent = new Intent(SmGoodsDetailActivity.this, LoginActivity.class);
							startActivity(intent);
						}
					} else {
//						if (UiUtil.checkLogin(SmGoodsDetailActivity.this)) {
//						}
						if(S.get(SmGoodsDetailActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(SmGoodsDetailActivity.this, C.KEY_JSON_TOKEN).isEmpty()){
							Log.d("rsapp", "text_to_buy =====> checkLogin Success2");
							if (list_adjust.size() <= 0) {
								t(getString(R.string.sm_text_plz_choose_good));
								return;
							}
							list_adjust.get(0).setNum(amoutView1.getAmount());
//							Toast.makeText(SmGoodsDetailActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
							addToShopcart(list_adjust.get(0).getId(),
									list_adjust.get(0).getNum() + "",
									SmGoodsDetailActivity.this, div_code, sale_custom_code);
						}else{
							Intent intent = new Intent(SmGoodsDetailActivity.this, LoginActivity.class);
							startActivity(intent);
						}
					}

					/*
						Thread의 여파로 인하여 Listener에서 에러가 떨어짐 이후 두번째 Thread 진입시
						제대로 로그인 체크가 이루어지므로 해당 루틴에 소스를 추가
					*/

//					Log.d("rsapp", "text_to_buy =====> checkLogin Success");
//					Bundle bundle = new Bundle();
//					if (list_adjust.size() <= 0) {
//						Log.d("rsapp", "list_adjust");
//
//						t(getString(R.string.sm_text_plz_choose_good));
//						return;
//					}
//					try {
//						list_adjust.get(0).setNum(amoutView1.getAmount());
//						bundle.putParcelableArrayList("goods", list_adjust);
//						bundle.putParcelableArrayList("attachment",
//								list_attach);
//
//						bundle.putString("total", price + "");
//						bundle.putString("onCartProcess", "false");
//						PageUtil.jumpTo(SmGoodsDetailActivity.this,
//								SmConfirmActivity.class, bundle);
//						finish();
//					} catch (Exception ex){
//						Log.d("rsapp", "text_to_buy Exception =====> " + ex.toString());
//					}
					/*=========================================================================*/
					pop_window.setVisibility(View.GONE);
				}
			});
			shoping_cart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

//					if (UiUtil.checkLogin(SmGoodsDetailActivity.this)) {
//						PageUtil.jumpTo(SmGoodsDetailActivity.this,
//								SmShoppingCart.class);
//					}
					if(S.get(SmGoodsDetailActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(SmGoodsDetailActivity.this, C.KEY_JSON_TOKEN).isEmpty()){
						PageUtil.jumpTo(SmGoodsDetailActivity.this,
								SmShoppingCart.class);
					}else {
						PageUtil.jumpTo(SmGoodsDetailActivity.this,
								LoginActivity.class);
					}

				}
			});
			close_btn = (LinearLayout) findViewById(R.id.btn_back);
			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					finish();
				}
			});
//			service_button = (TextView) findViewById(R.id.service_button);
//			service_button.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					/*Bundle bundle = new Bundle();
//					bundle.putString(C.KEY_INTENT_URL,
//							"http://ssadmin.dxbhtm.com:8090/20_CM/cmList.aspx");
//					PageUtil.jumpTo(SmGoodsDetailActivity.this,
//							WebActivity.class, bundle);*/
//					UtilClear.IntentToLongLiao(getApplicationContext(),
//							"cn.rsapp.im.ui.activity.MainActivity",S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
//
//				}
//			});
//			item_code = getIntent().getStringExtra(C.KEY_JSON_FM_ITEM_CODE);
			div_code = getIntent().getStringExtra(C.KEY_DIV_CODE);
			sale_custom_code = getIntent().getStringExtra("SaleCustomCode");
			item_code = getIntent().getStringExtra("ItemCode");
			sm_goods_add_shoppingcart = (TextView) findViewById(R.id.sm_goods_add_shoppingcart);
			sm_goods_add_shoppingcart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popType = 2;
					showPop();
				}
			});

			//20180328  수정 했음
			sm_goods_to_buy = (TextView) findViewById(R.id.sm_goods_to_buy);
			sm_goods_to_buy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
/*					UtilClear.CheckLogin(SmGoodsDetailActivity.this,new UtilCheckLogin.CheckListener(){

						@Override
						public void onDoNext() {
							//PageUtil.jumpToWithFlag(SmGoodsDetailActivity.this, MyOrderActivity.class);*/
							try {
								popType = 1;
								showPop();
							} catch (Exception e) {

								L.e(e);

							}
/*						}

					});*/
				}
			});

			collection_checkbox = (CheckBox) findViewById(R.id.collection_checkbox);

			collection_checkbox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

//					if (UiUtil.checkLogin(SmGoodsDetailActivity.this) == true) {
						addToCollection();
						Constant.SMMY_REFRESH = true;
//					}

				}
			});
			viewPager = (ViewPager) findViewById(R.id.viewpager_fragment);
			viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				@Override
				public void onPageSelected(int index) {

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {

				}
			});
			tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
			relativeLayout = (RelativeLayout) findViewById(R.id.navigation_area);
			pop_window = (RelativeLayout) findViewById(R.id.pop_window);

			LayoutParams params_rela = relativeLayout.getLayoutParams();
			params_rela.height = getimgheight();
			relativeLayout.setLayoutParams(params_rela);
//			goodsFragment1 = GoodsFragment1.newInstance(sale_custom_code, item_code);
//			goodsFragment2 = GoodsFragment2.newInstance(sale_custom_code, item_code);
//			goodsFragment3 = GoodsFragment3.newInstance(sale_custom_code, item_code);
			goodsFragment1 = GoodsFragment1.newInstance(item_code, div_code, sale_custom_code);
			goodsFragment2 = GoodsFragment2.newInstance(item_code, div_code, sale_custom_code);
			goodsFragment3 = GoodsFragment3.newInstance(item_code, div_code);
			fragmentList.add(goodsFragment1);
			fragmentList.add(goodsFragment2);
			fragmentList.add(goodsFragment3);
			viewPager.setAdapter(new MyFragmentPageAdapter(
					getSupportFragmentManager(), fragmentList,
					SmGoodsDetailActivity.this));
			tabs.setViewPager(viewPager);
			tabs.setTextSize(StringUtil.dip2px(getApplicationContext(), 17));
			tabs.setDividerColor(0x00000000);
			tabs.setDividerPadding(StringUtil
					.dip2px(getApplicationContext(), 0));
			tabs.setTabPaddingLeftRight(StringUtil.dip2px(
					getApplicationContext(), 0));
			tabs.setIndicatorColorResource(R.color.mainblue001);
			tabs.setIndicatorHeight(StringUtil.dip2px(getApplicationContext(),
					2));
			tabs.setSelectedTextColor(ContextCompat.getColor(
					getApplicationContext(), R.color.mainblue001));
		} catch (Exception e) {

			L.e(e);

		}

	}

	private int getimgheight() {

		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.img_logo);
		int height = bitmap.getHeight();
		return height + StringUtil.dip2px(getApplicationContext(), 20);

	}

	public static void addToShopcart(String item_code, String item_quantity, final Context context, String div_code, String sale_custom_code){
		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
			params.put(C.KEY_JSON_FM_ITEM_QUANTITI, item_quantity);
			params.put(C.KEY_JSON_TOKEN, mToken);
			params.put("div_code", "2");
			params.put("sale_custom_code", sale_custom_code);
			OkHttpHelper okHttpHelper = new OkHttpHelper(context);
			okHttpHelper.addPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					try {
						if(data.getString("status").equals("1") ){

							if(IntentStatus == 1){
								D.showDialog(context, -1,
										"提醒", "是否回到扫码页面继续购物", "继续购物", new OnClickListener() {

											@Override
											public void onClick(View view) {
												D.alertDialog.dismiss();
												Activity activity = (Activity) context;
												activity.startActivityForResult(new Intent(context, CaptureActivity.class), 2000);

											}
										},
										"暂时不去", new OnClickListener() {

											@Override
											public void onClick(View view) {
												D.alertDialog.dismiss();
											}
										});
							}else{
								T.showToast(context, data.getString("message"));
							}

						}
					} catch (Exception e) {
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				}
			}, Constant.SM_BASE_URL + Constant.ADD_USER_SHOPCART, params);
		} catch (Exception e) {
			L.e(e);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 2000) { // captureActivity
				finish();
				CaptureUtil.handleResultScaning(SmGoodsDetailActivity.this, data.getStringExtra("result"), "");
			}
		}
	}
	
	private void addToCollection(){
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
			params.put("div_code", div_code);
			params.put("sale_custom_code", sale_custom_code);
			params.put("token", S.get(SmGoodsDetailActivity.this, C.KEY_JSON_TOKEN));
			OkHttpHelper okHttpHelper = new OkHttpHelper(
                    SmGoodsDetailActivity.this);
			okHttpHelper.addPostRequest(new CallbackLogic() {
                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					try {
						t(data.getString("message"));
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
            }, Constant.SM_BASE_URL + "/FreshMart/User/AddUserFavorites", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeCollection(boolean checked) {

		collection_checkbox.setChecked(checked);
	}

	private void getTotalPrice() {

		price = 0;
		list_adjust.clear();
		for (int i = 0; i < goodsFragment1.justOptionList.size(); i++) {
			if (((FormatTextView) goodsFormatView1.flowGroupView.getChildAt(i))
					.isIschoosed()) {
				FormatTextView tv = (FormatTextView) goodsFormatView1.flowGroupView
						.getChildAt(i);

				price = price
						+ ((FormatTextView) goodsFormatView1.flowGroupView
								.getChildAt(i)).getSub_item_price();

				shopingcart = new ShoppingCart(tv.getSub_item_code(),
						tv.getSub_item_name(), tv.getSub_item_price(),
						goodsFragment1.imgurl, amoutView1.getAmount(), false,
						tv.getSub_item_stock_unit(), div_code, "");

				list_adjust.add(shopingcart);

			}
		}
		for (int i = 0; i < goodsFragment1.attachOptionList.size(); i++) {
			if (((FormatTextView) goodsFormatView.flowGroupView.getChildAt(i))
					.isIschoosed()) {
				FormatTextView tv1 = (FormatTextView) goodsFormatView.flowGroupView
						.getChildAt(i);
				price = price + tv1.getSub_item_price();
				ShoppingCart shop_cart = new ShoppingCart(
						tv1.getSub_item_code(), tv1.getSub_item_name(),
						tv1.getSub_item_price(), goodsFragment1.imgurl, 1,
						false, tv1.getSub_item_stock_unit(), div_code, "");
				list_attach.add(shop_cart);
			}
		}
		String priceStr = price + "";
		text_total.setText(priceStr.substring(0, priceStr.indexOf(".")));

	}

	private void showPop() {

		if (pop_window.getVisibility() == View.GONE) {
			WImageView wimg = (WImageView) findViewById(R.id.wimg);
			RelativeLayout.LayoutParams parms = (RelativeLayout.LayoutParams) wimg
					.getLayoutParams();
			parms.width = (int) (get_windows_width(getApplicationContext()) / 4);
			wimg.setLayoutParams(parms);
			wimg.setAspectRatio(1);
			ImageUtil.drawImageFromUri(goodsFragment1.imgurl, wimg);

			goodsFormatView = new GoodsFormatView(SmGoodsDetailActivity.this,
					"0", goodsFragment1.attachOptionList, handler);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			lp.setMargins(StringUtil.dip2px(SmGoodsDetailActivity.this, 10),
					StringUtil.dip2px(SmGoodsDetailActivity.this, 0),
					StringUtil.dip2px(SmGoodsDetailActivity.this, 20),
					StringUtil.dip2px(SmGoodsDetailActivity.this, 20));

			goodsFormatView.setLayoutParams(lp);
			line_format.addView(goodsFormatView);

			line_format.invalidate();

			goodsFormatView1 = new GoodsFormatView(SmGoodsDetailActivity.this,
					"1", goodsFragment1.justOptionList, handler);

			goodsFormatView1.setLayoutParams(lp);
			line_format_requer.addView(goodsFormatView1);

			goodsFormatView.invalidate();

			line_format_requer.invalidate();
			getTotalPrice();
		}

		// ArrayList<ShoppingCart> list = new
		// ArrayList<ShoppingCart>();
		// list.add(shopingcart);
		//
		// Bundle bundle = new Bundle();
		// bundle.putParcelableArrayList("goods", list);
		// bundle.putString("total", price+"");
		// bundle.putString("onCartProcess", "false");
		// PageUtil.jumpTo(SmGoodsDetailActivity.this,
		// SmConfirmActivity.class, bundle);

		pop_window.setVisibility(View.VISIBLE);
	}
}
