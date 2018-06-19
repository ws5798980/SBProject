
package com.rs.mobile.wportal.activity.dp;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.UiUtil;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class DpMainActivity extends TabActivity {

	public static DpMainActivity instance = null;

	private RadioGroup mTabButtonGroup;

	private RadioButton rButton1, rButton2, rButton3, rButton4;

	private TabHost mTabHost;

	private String TAB_HOMEPAGE = "homepage";

	private String TAB_CLASSIFY = "classify";

	private String TAB_MINE = "mine";

	private String TAB_SHOPPINGCART = "shoppingcart";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(com.rs.mobile.wportal.R.layout.activity_dp_main);
		initview();
	}

	private void initview() {

		try {

			mTabButtonGroup = (RadioGroup) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_radiogroup);

			rButton1 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_dp_home);

			rButton2 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_dp_classify);
			rButton3 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_dp_shoppingcart);
			rButton4 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_dp_mine);
			Intent i_homepage = new Intent(this, DpHomePageActivity.class);
			Intent i_shoppingcart = new Intent(this, DpShoppingCartActivity.class);
			Intent i_mine = new Intent(this, DpMyActivity.class);
			Intent i_classify = new Intent(this, DpClassifyActivity.class);
			mTabHost = getTabHost();
			mTabHost.addTab(mTabHost.newTabSpec(TAB_HOMEPAGE).setIndicator(TAB_HOMEPAGE).setContent(i_homepage));
			mTabHost.addTab(mTabHost.newTabSpec(TAB_CLASSIFY).setIndicator(TAB_CLASSIFY).setContent(i_classify));
			mTabHost.addTab(mTabHost.newTabSpec(TAB_SHOPPINGCART).setIndicator(TAB_SHOPPINGCART).setContent(i_shoppingcart));
			mTabHost.addTab(mTabHost.newTabSpec(TAB_MINE).setIndicator(TAB_MINE).setContent(i_mine));

			mTabHost.setCurrentTabByTag(TAB_HOMEPAGE);

			mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				private int lastId = com.rs.mobile.wportal.R.id.common_toolbar_dp_home;

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {

					
						switch (checkedId) {
						case com.rs.mobile.wportal.R.id.common_toolbar_dp_home:
							mTabHost.setCurrentTabByTag(TAB_HOMEPAGE);
							changeTextColor(checkedId);
							break;
						case com.rs.mobile.wportal.R.id.common_toolbar_dp_classify:
							mTabHost.setCurrentTabByTag(TAB_CLASSIFY);
							changeTextColor(checkedId);
							break;
						case com.rs.mobile.wportal.R.id.common_toolbar_dp_shoppingcart:

							if (UiUtil.checkLogin(DpMainActivity.this, lastId) == true) {

								mTabHost.setCurrentTabByTag(TAB_SHOPPINGCART);
								changeTextColor(checkedId);

							} else {
								checkedId = lastId;
								mTabButtonGroup.check(lastId);
								changeTextColor(lastId);

							}

							

							break;
						case com.rs.mobile.wportal.R.id.common_toolbar_dp_mine:

							if (UiUtil.checkLogin(DpMainActivity.this, lastId) == true) {

								mTabHost.setCurrentTabByTag(TAB_MINE);
								changeTextColor(checkedId);

							} else {
								checkedId = lastId;
								mTabButtonGroup.check(lastId);
								changeTextColor(lastId);

							}


							

							break;
						default:
							break;
						}
						lastId = checkedId;

				}	
				}
			);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void changeTextColor(int checkedId) {

		try {

			rButton1.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_normal));
			rButton2.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_normal));
			rButton3.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_normal));
			rButton4.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_normal));

			switch (checkedId) {
			case com.rs.mobile.wportal.R.id.common_toolbar_dp_home:
				rButton1.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_selected));
				break;
			case com.rs.mobile.wportal.R.id.common_toolbar_dp_classify:
				rButton2.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_selected));

				break;
			case com.rs.mobile.wportal.R.id.common_toolbar_dp_shoppingcart:
				rButton3.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_selected));
				break;
			case com.rs.mobile.wportal.R.id.common_toolbar_dp_mine:
				rButton4.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textcolor_dp_tabhost_tabspec_selected));
			default:
				break;
			}

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	public void onBackPressed() {

		// TODO Auto-generated method stub
		finish();
	}
}
