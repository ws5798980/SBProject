package com.rs.mobile.wportal.activity.ht;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.persnal.PersnalCenterActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

@SuppressWarnings("deprecation")
public class HtMainActivity extends TabActivity{
	public static HtMainActivity instance = null;

	private RadioGroup mTabButtonGroup;

	private RadioButton rButton1, rButton2, rButton3;

	private TabHost mTabHost;

	private String TAB_HOTEL = "hotel";

	private String TAB_ORDER = "order";

	private String TAB_MINE = "mine";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	instance=HtMainActivity.this;
    	setContentView(R.layout.activity_ht_main);
    	initView();
    	
    }
    private void initView(){
    try {

		mTabButtonGroup = (RadioGroup) findViewById(R.id.common_toolbar_radiogroup);

		rButton1 = (RadioButton) findViewById(R.id.common_toolbar_ht_hotel);

		rButton2 = (RadioButton) findViewById(R.id.common_toolbar_ht_order);
		rButton3 = (RadioButton) findViewById(R.id.common_toolbar_ht_mine);
		
		Intent i_hotel = new Intent(this, HtHotelActivity.class);
		Intent i_order = new Intent(this, HtOrderActivity.class);
		Intent i_mine = new Intent(this, PersnalCenterActivity.class);
		
		mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec(TAB_HOTEL).setIndicator(TAB_HOTEL).setContent(i_hotel));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_ORDER).setIndicator(TAB_ORDER).setContent(i_order));
		
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MINE).setIndicator(TAB_MINE).setContent(i_mine));

		mTabHost.setCurrentTabByTag(TAB_HOTEL);

		mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			private int lastId = R.id.common_toolbar_ht_hotel;

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				
					switch (checkedId) {
					case R.id.common_toolbar_ht_hotel:
						mTabHost.setCurrentTabByTag(TAB_HOTEL);
						changeTextColor(checkedId);
						break;
					
					case R.id.common_toolbar_ht_order:

						if (UiUtil.checkLogin(instance, lastId) == true) {

							mTabHost.setCurrentTabByTag(TAB_ORDER);
							changeTextColor(checkedId);

						} else {
							checkedId = lastId;
							mTabButtonGroup.check(lastId);
							changeTextColor(lastId);

						}

						

						break;
					case R.id.common_toolbar_ht_mine:

						if (UiUtil.checkLogin(instance, lastId) == true) {

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
		// TODO: handle exception
		L.e(e);
	}	
    }
    private void changeTextColor(int checkedId) {

		try {

			rButton1.setTextColor(ContextCompat.getColor(instance, R.color.textcolor_ht_tabhost_tabspec_normal));
			rButton2.setTextColor(ContextCompat.getColor(instance, R.color.textcolor_ht_tabhost_tabspec_normal));
			rButton3.setTextColor(ContextCompat.getColor(instance, R.color.textcolor_ht_tabhost_tabspec_normal));
			

			switch (checkedId) {
			case R.id.common_toolbar_ht_hotel:
				rButton1.setTextColor(ContextCompat.getColor(instance, R.color.textcolor_ht_tabhost_tabspec_selected));
				break;
			case R.id.common_toolbar_ht_order:
				rButton2.setTextColor(ContextCompat.getColor(instance, R.color.textcolor_ht_tabhost_tabspec_selected));

				break;
			case R.id.common_toolbar_ht_mine:
				rButton3.setTextColor(ContextCompat.getColor(instance, R.color.textcolor_ht_tabhost_tabspec_selected));
				break;
			
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
