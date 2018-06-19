package com.rs.mobile.wportal.activity.ht;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.rs.mobile.common.L;
import com.rs.mobile.wportal.fragment.ht.HotelOrderFragment;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.adapter.ht.HtMyFragmentPageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HtOrderActivity extends BaseActivity {
	private TextView tv_title;
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private PagerSlidingTabStrip tabs;
	private ViewPager viewPager;
	private String[] s = new String[5];// = { getString(R.string.ht_text_095),
										// getString(R.string.ht_text_096),
	// getString(R.string.ht_text_097), getString(R.string.ht_text_098),
	// getString(R.string.ht_text_099) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		s[0] = getString(com.rs.mobile.wportal.R.string.ht_text_095);
		s[1] = getString(com.rs.mobile.wportal.R.string.ht_text_096);
		s[2] = getString(com.rs.mobile.wportal.R.string.ht_text_097);
		s[3] = getString(com.rs.mobile.wportal.R.string.ht_text_098);
		s[4] = getString(com.rs.mobile.wportal.R.string.ht_text_099);

		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_my0rder);
		initToolbar();
		initView();
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			iv_back.setVisibility(View.GONE);
			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_100));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		tabs = (PagerSlidingTabStrip) findViewById(com.rs.mobile.wportal.R.id.tabs);
		viewPager = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.viewPager);
		tabs.setDividerColor(0x00000000);
		tabs.setDividerPadding(StringUtil.dip2px(getApplicationContext(), 0));
		tabs.setTabPaddingLeftRight(StringUtil.dip2px(getApplicationContext(), 0));
		tabs.setIndicatorColorResource(com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected);
		tabs.setIndicatorHeight(StringUtil.dip2px(getApplicationContext(), 2));
		tabs.setSelectedTextColor(
				ContextCompat.getColor(getApplicationContext(), com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected));
		List<Fragment> flist = new ArrayList<>();
		HotelOrderFragment f1 = new HotelOrderFragment("-1");
		HotelOrderFragment f2 = new HotelOrderFragment("1");
		HotelOrderFragment f3 = new HotelOrderFragment("0");
		HotelOrderFragment f4 = new HotelOrderFragment("5");
		HotelOrderFragment f5 = new HotelOrderFragment("6");
		flist.add(f1);
		flist.add(f2);
		flist.add(f3);
		flist.add(f4);
		flist.add(f5);
		HtMyFragmentPageAdapter adapter = new HtMyFragmentPageAdapter(getSupportFragmentManager(), flist, s);
		viewPager.setAdapter(adapter);

		tabs.setViewPager(viewPager);
	}
}
