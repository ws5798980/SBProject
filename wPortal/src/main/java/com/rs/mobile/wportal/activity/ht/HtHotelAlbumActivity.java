package com.rs.mobile.wportal.activity.ht;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.adapter.ht.HtMyFragmentPageAdapter;
import com.rs.mobile.wportal.fragment.ht.HotelAlbumFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import android.widget.TextView;

public class HtHotelAlbumActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
    private String HotelInfoID;
	private ViewPager viewPager;
	private PagerSlidingTabStrip tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_hotelalbum);
		HotelInfoID=getIntent().getStringExtra("HotelInfoID");
		initToolbar();
		initView();

	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
			tv_title.setText(getResources().getString(com.rs.mobile.wportal.R.string.ht_text_hotelalbum));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.viewPager);
		List<Fragment> list = new ArrayList<>();
		HotelAlbumFragment f1 = new HotelAlbumFragment(HotelInfoID,"0");
		HotelAlbumFragment f2 = new HotelAlbumFragment(HotelInfoID,"1");
		HotelAlbumFragment f3 = new HotelAlbumFragment(HotelInfoID,"2");
		HotelAlbumFragment f4 = new HotelAlbumFragment(HotelInfoID,"3");
		HotelAlbumFragment f5 = new HotelAlbumFragment(HotelInfoID,"4");
		list.add(f1);
		list.add(f2);
		list.add(f3);
		list.add(f4);
		list.add(f5);
		String[] s = { getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type0), getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type1),getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type2),
				getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type3), getResources().getString(com.rs.mobile.wportal.R.string.ht_text_type4) };
		HtMyFragmentPageAdapter adapter = new HtMyFragmentPageAdapter(getSupportFragmentManager(), list, s);
		viewPager.setAdapter(adapter);
		tabs = (PagerSlidingTabStrip) findViewById(com.rs.mobile.wportal.R.id.tabs);
		tabs.setViewPager(viewPager);

		tabs.setTextSize(StringUtil.dip2px(getApplicationContext(), 14));
		tabs.setDividerColor(0x00000000);
		tabs.setDividerPadding(StringUtil.dip2px(getApplicationContext(), 0));
		tabs.setTabPaddingLeftRight(StringUtil.dip2px(getApplicationContext(), 0));
		tabs.setIndicatorColorResource(com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected);
		tabs.setIndicatorHeight(StringUtil.dip2px(getApplicationContext(), 2));
		tabs.setSelectedTextColor(
				ContextCompat.getColor(getApplicationContext(), com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected));
	}

}
