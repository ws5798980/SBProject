
package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.astuetz.PagerSlidingTabStrip;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.MyFragmentPageAdapter;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.fragment.dp.GoodsFragment1;
import com.rs.mobile.wportal.fragment.dp.GoodsFragment2;
import com.rs.mobile.wportal.fragment.dp.GoodsFragment3;
import com.rs.mobile.wportal.view.AmountView;
import com.rs.mobile.wportal.view.FormatTextView;
import com.rs.mobile.wportal.view.GoodsFormatView;

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
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import okhttp3.Request;

public class DpGoodsDetailActivity extends BaseActivity {

	private ViewPager viewPager;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	private GoodsFragment1 goodsFragment1;

	private GoodsFragment2 goodsFragment2;

	private GoodsFragment3 goodsFragment3;

	private PagerSlidingTabStrip tabs;

	private RelativeLayout relativeLayout;

	private String item_code;

	public TextView sm_goods_to_buy, sm_goods_add_shoppingcart, service_button;

	private LinearLayout close_btn;

	private TextView shoping_cart;

	private TextView text_total, text_to_buy;

	private static CheckBox collection_checkbox;

	private ScrollView scrollView;

	private LinearLayout line_format, line_format_requer;

	private RelativeLayout pop_window, rela_top;

	private GoodsFormatView goodsFormatView;

	private GoodsFormatView goodsFormatView1;

	private ArrayList<ShoppingCart> list_adjust;

	private ArrayList<ShoppingCart> list_attach;
	
	private static int IntentStatus = 0; // 扫描跳转判断标记

	private ImageView img_cls;

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

	private ShoppingCart shopingcart, shopingcart_attach;

	private AmountView amoutView1;

	protected int popType;

	private boolean isScan;

	private String div_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {

			setContentView(com.rs.mobile.wportal.R.layout.activity_sm_goods_detail);

			list_adjust = new ArrayList<>();
			list_attach = new ArrayList<>();
			initview();

		} catch (Exception e) {

			L.e(e);

		}

	}

	@SuppressLint("ResourceAsColor")
	private void initview() {

		try {
			IntentStatus = getIntent().getIntExtra("IntentStatus", 0);
			scrollView = (ScrollView) findViewById(com.rs.mobile.wportal.R.id.scrollView);
			amoutView1 = (AmountView) findViewById(com.rs.mobile.wportal.R.id.amoutView1);
			line_format = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_format);
			line_format_requer = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_format_requer);
			shoping_cart = (TextView) findViewById(com.rs.mobile.wportal.R.id.shoping_cart);
			rela_top = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela_top);
			img_cls = (ImageView) findViewById(com.rs.mobile.wportal.R.id.img_cls);
//			service_button = (TextView) findViewById(com.rs.mobile.wportal.R.id.service_button);
//			service_button.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//
//					Bundle bundle2 = new Bundle();
//					bundle2.putString(C.KEY_INTENT_URL, "http://ssadmin.dxbhtm.com:8090/20_CM/cmList.aspx");
//					PageUtil.jumpTo(DpGoodsDetailActivity.this, WebActivity.class, bundle2);
//				}
//			});
			img_cls.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					pop_window.setVisibility(View.INVISIBLE);
				}
			});
			RelativeLayout.LayoutParams parms = (android.widget.RelativeLayout.LayoutParams) rela_top.getLayoutParams();
			parms.height = (int) (get_windows_width(getApplicationContext()) / 4);
			parms.width = get_windows_width(getApplicationContext());
			rela_top.setLayoutParams(parms);
			text_total = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_total);
			text_to_buy = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_to_buy);
			text_to_buy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (popType == 1) {

						if (UiUtil.checkLogin(DpGoodsDetailActivity.this)) {
							Bundle bundle = new Bundle();
							if (list_adjust.size() <= 0) {
								t(getString(com.rs.mobile.wportal.R.string.dp_text_034));
								return;
							}
							list_adjust.get(0).setNum(amoutView1.getAmount());
							bundle.putParcelableArrayList("goods", list_adjust);
							bundle.putParcelableArrayList("attachment", list_attach);

							bundle.putString("total", price + "");
							bundle.putString("onCartProcess", "false");

							PageUtil.jumpTo(DpGoodsDetailActivity.this, DpConfirmActivity.class, bundle);

							finish();

						}
					} else {
						if (UiUtil.checkLogin(DpGoodsDetailActivity.this)) {
							if (list_adjust.size() <= 0) {
								t(getString(com.rs.mobile.wportal.R.string.dp_text_034));
								return;
							}
							list_adjust.get(0).setNum(amoutView1.getAmount());
							addToShopcart(list_adjust.get(0).getId(), list_adjust.get(0).getNum() + "",
									DpGoodsDetailActivity.this, div_code);

						}
					}
					pop_window.setVisibility(View.INVISIBLE);
				}
			});
			shoping_cart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					if (UiUtil.checkLogin(DpGoodsDetailActivity.this)) {
						PageUtil.jumpTo(DpGoodsDetailActivity.this, DpShoppingCartActivity.class);
					}

				}
			});
			close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.btn_back);
			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					finish();
				}
			});

			item_code = getIntent().getStringExtra(C.KEY_JSON_FM_ITEM_CODE);
			div_code = getIntent().getStringExtra(C.KEY_DIV_CODE);
			sm_goods_add_shoppingcart = (TextView) findViewById(com.rs.mobile.wportal.R.id.sm_goods_add_shoppingcart);
			sm_goods_add_shoppingcart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					if (UiUtil.checkLogin(DpGoodsDetailActivity.this)) {
						popType = 2;
						showPop();
					}

					// PageUtil.jumpTo(SmGoodsDetailActivity.this,
					// SmShoppingCart.class);
				}
			});
			sm_goods_to_buy = (TextView) findViewById(com.rs.mobile.wportal.R.id.sm_goods_to_buy);
			sm_goods_to_buy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						popType = 1;
						showPop();
					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			collection_checkbox = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.collection_checkbox);

			collection_checkbox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					if (UiUtil.checkLogin(DpGoodsDetailActivity.this) == true) {

						addToCollection();
						Constant.DPMY_REFRESH = true;

					}

				}
			});
			viewPager = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.viewpager_fragment);
			viewPager.addOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int index) {
					// TODO Auto-generated method stub

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
			tabs = (com.astuetz.PagerSlidingTabStrip) findViewById(com.rs.mobile.wportal.R.id.tabs);
			relativeLayout = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.navigation_area);
			pop_window = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.pop_window);

			LayoutParams params_rela = relativeLayout.getLayoutParams();
			params_rela.height = getimgheight();
			relativeLayout.setLayoutParams(params_rela);
			goodsFragment1 = GoodsFragment1.newInstance(item_code, div_code);
			goodsFragment2 = GoodsFragment2.newInstance(item_code, div_code);
			goodsFragment3 = GoodsFragment3.newInstance(item_code, div_code);
			fragmentList.add(goodsFragment1);
			fragmentList.add(goodsFragment2);
			fragmentList.add(goodsFragment3);
			viewPager.setAdapter(
					new MyFragmentPageAdapter(getSupportFragmentManager(), fragmentList, DpGoodsDetailActivity.this));
			tabs.setViewPager(viewPager);
			tabs.setTextSize(StringUtil.dip2px(getApplicationContext(), 17));
			tabs.setDividerColor(0x00000000);
			tabs.setDividerPadding(StringUtil.dip2px(getApplicationContext(), 0));
			tabs.setTabPaddingLeftRight(StringUtil.dip2px(getApplicationContext(), 0));
			tabs.setIndicatorColorResource(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_selected);
			tabs.setIndicatorHeight(StringUtil.dip2px(getApplicationContext(), 2));

		} catch (Exception e) {
			// TODO: handle exception

			L.e(e);

		}

	}

	private int getimgheight() {

		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), com.rs.mobile.wportal.R.drawable.img_logo);
		int height = bitmap.getHeight();
		return height + StringUtil.dip2px(getApplicationContext(), 20);

	}

	public static void addToShopcart(String item_code, String item_quantity, final Context context, String div_code) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
			params.put(C.KEY_JSON_FM_ITEM_QUANTITI, item_quantity);
			params.put(C.KEY_JSON_TOKEN, S.getShare(context, C.KEY_JSON_TOKEN, ""));
			params.put("div_code", div_code);
			OkHttpHelper okHttpHelper = new OkHttpHelper(context);
			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					// TODO Auto-generated method stub
					try {

						if (data.getString(C.KEY_JSON_FM_STATUS).equals("1")) {
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
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_DP1 + Constant.ADD_USER_SHOPCART, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void addToCollection() {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
			params.put("div_code", div_code);
			OkHttpHelper okHttpHelper = new OkHttpHelper(DpGoodsDetailActivity.this);
			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

					try {

						t(data.getString("message"));

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_DP1 + Constant.ADD_USER_FAVORITES, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void changeCollection(boolean checked) {

		collection_checkbox.setChecked(checked);
	}

	private void delToCollection() {

		Map<String, String> params = new HashMap<String, String>();
		params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpGoodsDetailActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				try {

					if (!data.getString(C.KEY_JSON_FM_STATUS).equals("1")) {

						t(data.getString("message"));

					}

				} catch (Exception e) {

					L.e(e);

				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.DEL_USER_FAVOURITES, params);
	}

	private void getTotalPrice() {

		price = 0;
		list_adjust.clear();
		for (int i = 0; i < goodsFragment1.justOptionList.size(); i++) {
			if (((FormatTextView) goodsFormatView1.flowGroupView.getChildAt(i)).isIschoosed()) {
				FormatTextView tv = (FormatTextView) goodsFormatView1.flowGroupView.getChildAt(i);

				price = price + ((FormatTextView) goodsFormatView1.flowGroupView.getChildAt(i)).getSub_item_price();

				shopingcart = new ShoppingCart(tv.getSub_item_code(), tv.getSub_item_name(), tv.getSub_item_price(),
						goodsFragment1.imgurl, amoutView1.getAmount(), false, tv.getSub_item_stock_unit(), div_code, "");

				list_adjust.add(shopingcart);

			}
		}
		for (int i = 0; i < goodsFragment1.attachOptionList.size(); i++) {
			if (((FormatTextView) goodsFormatView.flowGroupView.getChildAt(i)).isIschoosed()) {
				FormatTextView tv1 = (FormatTextView) goodsFormatView.flowGroupView.getChildAt(i);
				price = price + tv1.getSub_item_price();
				ShoppingCart shop_cart = new ShoppingCart(tv1.getSub_item_code(), tv1.getSub_item_name(),
						tv1.getSub_item_price(), goodsFragment1.imgurl, 1, false, tv1.getSub_item_stock_unit(),
						div_code, "");
				list_attach.add(shop_cart);
			}
		}
		text_total.setText(price + "");

	}

	private void showPop() {

		if (pop_window.getVisibility() == View.GONE) {

			WImageView wimg = (WImageView) findViewById(com.rs.mobile.wportal.R.id.wimg);
			RelativeLayout.LayoutParams parms = (android.widget.RelativeLayout.LayoutParams) wimg.getLayoutParams();
			parms.width = (int) (get_windows_width(getApplicationContext()) / 4);
			wimg.setLayoutParams(parms);
			wimg.setAspectRatio(1);
			ImageUtil.drawImageFromUri(goodsFragment1.imgurl, wimg);

			goodsFormatView = new GoodsFormatView(DpGoodsDetailActivity.this, "0", goodsFragment1.attachOptionList,
					handler);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			lp.setMargins(StringUtil.dip2px(DpGoodsDetailActivity.this, 10),
					StringUtil.dip2px(DpGoodsDetailActivity.this, 0), StringUtil.dip2px(DpGoodsDetailActivity.this, 20),
					StringUtil.dip2px(DpGoodsDetailActivity.this, 20));

			goodsFormatView.setLayoutParams(lp);
			line_format.addView(goodsFormatView);

			line_format.invalidate();

			goodsFormatView1 = new GoodsFormatView(DpGoodsDetailActivity.this, "1", goodsFragment1.justOptionList,
					handler);

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
