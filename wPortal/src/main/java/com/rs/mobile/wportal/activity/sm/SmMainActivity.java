
package com.rs.mobile.wportal.activity.sm;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsMyActivity;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity;
import com.rs.mobile.wportal.activity.xsgr.XsStoreListActivity;
import com.rs.mobile.wportal.activity.xsgr.kfmemain;

@SuppressWarnings("deprecation")
public class SmMainActivity extends TabActivity {

	public static SmMainActivity instance = null;

	private RadioGroup mTabButtonGroup;

	private RadioButton rButton1, rButton2, rButton3, rButton4, rButton5;

	private TabHost mTabHost;

	private String TAB_HIGHLIGHTS = "highlights";

	private String TAB_CLASSIFY = "classify";

	private String TAB_SELF = "selfticket";

	private String TAB_SHOPPINGCART = "shoppingcart";

	private long exitTime = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_sm_main);
		initview();
	}

	private void initview() {

		try {

			mTabButtonGroup = (RadioGroup) findViewById(R.id.common_toolbar_radiogroup);

			rButton1 = (RadioButton) findViewById(R.id.common_toolbar_2_home);

			rButton3 = (RadioButton) findViewById(R.id.common_toolbar_2_classify);
			rButton4 = (RadioButton) findViewById(R.id.common_toolbar_2_shoppingcart);
			rButton5 = (RadioButton) findViewById(R.id.common_toolbar_2_mine);
			Intent i_homepage = new Intent(this, SmHomePageActivity.class);
			Intent i_shoppingcart = new Intent(this, SmShoppingCart.class);
			i_shoppingcart.putExtra("closeFlag", true);
			Intent i_self = new Intent(this, SmMyActivity.class);
			//Intent i_self = new Intent(this, PersnalCenterActivity.class);
			Intent i_classify = new Intent(this, SmClassifyActivity.class);

			Intent xsHomeIntent = new Intent(this, kfmemain.class);
			Intent xsStoreListIntent = new Intent(this, XsStoreListActivity.class);
			Intent xsStoreDetailIntent = new Intent(this, XsStoreDetailActivity.class);
			Intent xsMyIntent = new Intent(this, XsMyActivity.class);

			mTabHost = getTabHost();
			mTabHost.addTab(mTabHost.newTabSpec(TAB_HIGHLIGHTS).setIndicator(TAB_HIGHLIGHTS).setContent(xsHomeIntent));
			mTabHost.addTab(mTabHost.newTabSpec(TAB_CLASSIFY).setIndicator(TAB_CLASSIFY).setContent(xsStoreListIntent));
			mTabHost.addTab(mTabHost.newTabSpec(TAB_SHOPPINGCART).setIndicator(TAB_SHOPPINGCART).setContent(i_shoppingcart));
			mTabHost.addTab(mTabHost.newTabSpec(TAB_SELF).setIndicator(TAB_SELF).setContent(xsMyIntent));

//			mTabHost.addTab(mTabHost.newTabSpec(TAB_HIGHLIGHTS).setIndicator(TAB_HIGHLIGHTS).setContent(i_homepage));
//			mTabHost.addTab(mTabHost.newTabSpec(TAB_CLASSIFY).setIndicator(TAB_CLASSIFY).setContent(i_homepage));
//			mTabHost.addTab(mTabHost.newTabSpec(TAB_SHOPPINGCART).setIndicator(TAB_SHOPPINGCART).setContent(i_classify));
//			mTabHost.addTab(mTabHost.newTabSpec(TAB_SELF).setIndicator(TAB_SELF).setContent(i_self));

			mTabHost.setCurrentTabByTag(TAB_HIGHLIGHTS);

			mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				private int lastId = R.id.common_toolbar_2_home;

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					try {

//						if (lastId == R.id.common_toolbar_2_shoppingcart) {
//
//							SmShoppingCart.upDateShopcart(SmMainActivity.this);
//
//						}
						int checked = checkedId;
						switch (checkedId) {
						case R.id.common_toolbar_2_home:
							
							try {
							
								mTabHost.setCurrentTabByTag(TAB_HIGHLIGHTS);
								changeTextColor(checkedId);
							
							} catch (Exception e) {
								
								L.e(e);
								
							}
							
							break;
						case R.id.common_toolbar_2_classify:
							
							try {
							
								mTabHost.setCurrentTabByTag(TAB_CLASSIFY);
								changeTextColor(checkedId);
							
							} catch (Exception e) {
								
								L.e(e);
								
							}
							
							break;
						case R.id.common_toolbar_2_shoppingcart:

							try {
								if(S.get(SmMainActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(SmMainActivity.this, C.KEY_JSON_TOKEN).isEmpty()){
									mTabHost.setCurrentTabByTag(TAB_SHOPPINGCART);
									changeTextColor(checkedId);
								}else{
									Intent intent = new Intent(SmMainActivity.this, LoginActivity.class);
									startActivity(intent);
									checkedId = lastId;
									mTabButtonGroup.check(lastId);
									changeTextColor(lastId);
								}
							
//								if (UiUtil.checkLogin(SmMainActivity.this, lastId) == true) {
//									mTabHost.setCurrentTabByTag(TAB_SHOPPINGCART);
//									changeTextColor(checkedId);
//								} else {
//									checkedId = lastId;
//									mTabButtonGroup.check(lastId);
//									changeTextColor(lastId);
//								}
							
							} catch (Exception e) {
								
								L.e(e);
								
							}

							break;
						case R.id.common_toolbar_2_mine:

							try {
								if(S.get(SmMainActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(SmMainActivity.this, C.KEY_JSON_TOKEN).isEmpty()){
									mTabHost.setCurrentTabByTag(TAB_SELF);
									changeTextColor(checkedId);
								}else{
									Intent intent = new Intent(SmMainActivity.this, LoginActivity.class);
									startActivity(intent);
									checkedId = lastId;
									mTabButtonGroup.check(lastId);
									changeTextColor(lastId);
								}

//								if (UiUtil.checkLogin(SmMainActivity.this, lastId) == true) {
//
//									mTabHost.setCurrentTabByTag(TAB_SELF);
//									changeTextColor(checkedId);
//
//								} else {
//									checkedId = lastId;
//									mTabButtonGroup.check(lastId);
//									changeTextColor(lastId);
//
//								}
							} catch (Exception e) {
								L.e(e);
							}
							break;
						default:
							break;
						}
						lastId = checkedId;
					} catch (Exception e) {
						L.e(e);
					}
				}
			});
		} catch (Exception e) {
			L.e(e);
		}
	}

	private void changeTextColor(int checkedId) {

		try {

			rButton1.setTextColor(getResources().getColor(R.color.inputblack));
			rButton3.setTextColor(getResources().getColor(R.color.inputblack));
			rButton4.setTextColor(getResources().getColor(R.color.inputblack));
			rButton5.setTextColor(getResources().getColor(R.color.inputblack));

			switch (checkedId) {
			case R.id.common_toolbar_2_home:
				rButton1.setTextColor(getResources().getColor(R.color.mainblue001));
				break;
			case R.id.common_toolbar_2_classify:
				rButton3.setTextColor(getResources().getColor(R.color.mainblue001));

				break;
			case R.id.common_toolbar_2_shoppingcart:
				rButton4.setTextColor(getResources().getColor(R.color.mainblue001));
				break;
			case R.id.common_toolbar_2_mine:
				rButton5.setTextColor(getResources().getColor(R.color.mainblue001));
			default:
				break;
			}

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN&&
				(System.currentTimeMillis() - exitTime) > 2000
				){
			Toast.makeText(getApplicationContext(), "다시 한번 클릭하면 프로그램을 종료합니다.",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
