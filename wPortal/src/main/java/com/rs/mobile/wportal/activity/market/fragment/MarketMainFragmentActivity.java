package com.rs.mobile.wportal.activity.market.fragment;



import com.rs.mobile.common.S;
import com.rs.mobile.wportal.activity.rt.RtMainActivity;
import com.rs.mobile.wportal.activity.market.MarketEscActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 搭载首页框架
 * 
 */
public class MarketMainFragmentActivity extends FragmentActivity {

	private F01_market f01_market;//线下超市
	private F02_food f02_food;//食品区
	private F03_order f03_order;//我的订单
	private Fragment currentFragment;//正在运行的页面
	private TextView e03_market_text, e03_food_text, e03_order_text,mk_car_title,mk_car_esc
			;
	private ImageView e03_market_img, e03_food_img, e03_order_img;
	int[][] img = {
			{ com.rs.mobile.wportal.R.drawable.icon_offline_shop_s, com.rs.mobile.wportal.R.drawable.icon_offline_shop_n },
			{ com.rs.mobile.wportal.R.drawable.icon_restaurant_s, com.rs.mobile.wportal.R.drawable.icon_restaurant_n },
			{ com.rs.mobile.wportal.R.drawable.icon_order_s, com.rs.mobile.wportal.R.drawable.icon_order_n} };
	int[][] color = { { com.rs.mobile.wportal.R.color.mk_text_click, com.rs.mobile.wportal.R.color.mk_text_no } };
	
	
	public void setEscMarket(){
		if(S.getShare(MarketMainFragmentActivity.this, "tickets", "").equals("")){
			findViewById(com.rs.mobile.wportal.R.id.mk_car_esc).setVisibility(View.GONE);
		}else{
			findViewById(com.rs.mobile.wportal.R.id.mk_car_esc).setVisibility(View.VISIBLE);
		}
		
	}
	
	// 回调
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);

			if (resultCode == Activity.RESULT_OK) {

				if (requestCode == 50) {
				if(f01_market != null)
					f01_market.rechange();
				}

			}

		}
		
		@Override  
	    protected void onNewIntent(Intent intent) {  
	        super.onNewIntent(intent);  
	        if(f01_market != null)
				f01_market.rechange();
	    } 

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_f0_fragment);
		initUI();
		initTab();
		
		//退出超市
		mk_car_esc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(MarketMainFragmentActivity.this,
						MarketEscActivity.class), 50);
			}
		});
		
		//返回
		findViewById(com.rs.mobile.wportal.R.id.mk_car_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		findViewById(com.rs.mobile.wportal.R.id.e03_bottom_01).setOnClickListener(
				new OnClickListener() {//无人超市

					@Override
					public void onClick(View arg0) {
						mk_car_title.setText(com.rs.mobile.wportal.R.string.mk_text07);
						setEscMarket();
						if (f01_market == null) {
							f01_market = new F01_market();
						}
						addOrShowFragment(getSupportFragmentManager()
								.beginTransaction(), f01_market);
						changeBottonView(0);
					}
				});

		findViewById(com.rs.mobile.wportal.R.id.e03_bottom_02).setOnClickListener(
				new OnClickListener() {//吃吃喝喝

					@Override
					public void onClick(View arg0) {
						
						startActivity(new Intent(MarketMainFragmentActivity.this,RtMainActivity.class));
//						mk_car_title.setText(R.string.mk_text08);
//						findViewById(R.id.mk_car_esc).setVisibility(View.GONE);
//						if (f02_food == null) {
//							f02_food = new F02_food();
//						}
//						addOrShowFragment(getSupportFragmentManager()
//								.beginTransaction(), f02_food);
//						changeBottonView(1);
					}
				});

		findViewById(com.rs.mobile.wportal.R.id.e03_bottom_03).setOnClickListener(
				new OnClickListener() {//订单详情

					@Override
					public void onClick(View arg0) {
						mk_car_title.setText(com.rs.mobile.wportal.R.string.mk_text09);
						findViewById(com.rs.mobile.wportal.R.id.mk_car_esc).setVisibility(View.GONE);
						if (f03_order == null) {
							f03_order = new F03_order();
						}
						addOrShowFragment(getSupportFragmentManager()
								.beginTransaction(), f03_order);
						changeBottonView(2);
					}
				});



	}

	public void intentSrevice() {//跳转到服务页面
		if (f03_order == null) {
			f03_order = new F03_order();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(),
				f03_order);
		changeBottonView(2);
	}

	private void initUI() {//初始化UI
		mk_car_esc = (TextView) findViewById(com.rs.mobile.wportal.R.id.mk_car_esc);
		mk_car_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.mk_car_title);
		e03_market_text = (TextView) findViewById(com.rs.mobile.wportal.R.id.e03_market_text);
		e03_food_text = (TextView) findViewById(com.rs.mobile.wportal.R.id.e03_food_text);
		e03_order_text = (TextView) findViewById(com.rs.mobile.wportal.R.id.e03_order_text);
		e03_market_img = (ImageView) findViewById(com.rs.mobile.wportal.R.id.e03_market_img);
		e03_food_img = (ImageView) findViewById(com.rs.mobile.wportal.R.id.e03_food_img);
		e03_order_img = (ImageView) findViewById(com.rs.mobile.wportal.R.id.e03_order_img);
	}

	/**
	 * 
	 * @method：底部切换页面按钮
	 * @param index   
	 * @return void  
	 * @exception   
	 * @since  1.0.0
	 */
	private void changeBottonView(int index) {
		switch (index) {
		case 0:
			e03_market_text.setTextColor(getResources().getColor(color[0][0]));
			e03_food_text.setTextColor(getResources().getColor(color[0][1]));
			e03_order_text.setTextColor(getResources().getColor(color[0][1]));
			e03_market_img.setImageResource(img[0][0]);
			e03_food_img.setImageResource(img[1][1]);
			e03_order_img.setImageResource(img[2][1]);
			break;
		case 1:
			e03_market_text.setTextColor(getResources().getColor(color[0][1]));
			e03_food_text.setTextColor(getResources().getColor(color[0][0]));
			e03_order_text.setTextColor(getResources().getColor(color[0][1]));
			e03_market_img.setImageResource(img[0][1]);
			e03_food_img.setImageResource(img[1][0]);
			e03_order_img.setImageResource(img[2][1]);
			break;
		case 2:
			e03_market_text.setTextColor(getResources().getColor(color[0][1]));
			e03_food_text.setTextColor(getResources().getColor(color[0][1]));
			e03_order_text.setTextColor(getResources().getColor(color[0][0]));
			e03_market_img.setImageResource(img[0][1]);
			e03_food_img.setImageResource(img[1][1]);
			e03_order_img.setImageResource(img[2][0]);

			break;

		default:
			e03_market_text.setTextColor(getResources().getColor(color[0][0]));
			e03_food_text.setTextColor(getResources().getColor(color[0][1]));
			e03_order_text.setTextColor(getResources().getColor(color[0][1]));
			e03_market_img.setImageResource(img[0][0]);
			e03_food_img.setImageResource(img[1][1]);
			e03_order_img.setImageResource(img[2][1]);
			break;
		}

	}

	/**
	 * 初始化底部标签
	 */
	private void initTab() {
		mk_car_title.setText(com.rs.mobile.wportal.R.string.mk_text07);
		setEscMarket();
		if (f01_market == null) {
			f01_market = new F01_market();
		}
		if (!f01_market.isAdded()) {
			// 提交事务
			getSupportFragmentManager().beginTransaction()
					.add(com.rs.mobile.wportal.R.id.content_layout, f01_market).commit();

			// 记录当前Fragment
			currentFragment = f01_market;
			// 设置图片文本的变化
			changeBottonView(0);
		}

	}
	
	
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//	}
//	@Override
//	protected void onResume()
//	{
//	    if(currentFragment instanceof f02_food)
//            ((f02_food)currentFragment).onReSum();
//	    
//	    super.onResume();
//	}

	/**
	 * 添加或者显示碎片
	 * 
	 * @param transaction
	 * @param fragment
	 */
	private void addOrShowFragment(FragmentTransaction transaction,
			Fragment fragment) {
		if (currentFragment == fragment)
			return;

		if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
			transaction.hide(currentFragment)
					.add(com.rs.mobile.wportal.R.id.content_layout, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}

		currentFragment = fragment;
	}

}
