package com.rs.mobile.wportal.activity.rt;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.fragment.BaseFragment;
import com.rs.mobile.wportal.fragment.rt.RtReserveClassifyFragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 订单分类列表页
 * 
 * @author ZhaoYun
 * @date 2017-3-11
 */
public class RtReserveActivity extends BaseActivity implements OnClickListener {

	private final String[] mTabTitles = new String[4];

	public static final String[] mReserveTags = { "A", "B", "C", "D" };

	// toolbar
	private Toolbar toolbar;
	private TextView tv_title;
	private LinearLayout iv_back;

	// content
	private PagerSlidingTabStrip vpi_reserveclassify;
	private ViewPager vp_content;
	public List<BaseFragment> mListFragment = new ArrayList<BaseFragment>();
	private TabPageIndicatorAdapter pagerAdapter;

	// 현재 위치
	public int currentPosition = 0;

	// 최초 접속 flag
	private boolean isInit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_reserve);
		mTabTitles[0] = getString(com.rs.mobile.wportal.R.string.common_text036);
		mTabTitles[1] = getString(com.rs.mobile.wportal.R.string.common_text037);
		mTabTitles[2] = getString(com.rs.mobile.wportal.R.string.common_text038);
		mTabTitles[3] = getString(com.rs.mobile.wportal.R.string.common_text039);
		initToolbar();
		initViews();
		initListeners();
		initDatas();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (isInit == true) {

			RtReserveClassifyFragment fragment = (RtReserveClassifyFragment) mListFragment.get(currentPosition);

			fragment.resetCurrentPage();

			fragment.requestReserveList(true);

		}

		isInit = true;

	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
		setSupportActionBar(toolbar);

		tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text040));
	}

	private void initViews() {
		vpi_reserveclassify = (PagerSlidingTabStrip) findViewById(com.rs.mobile.wportal.R.id.vpi_reserveclassify);
		vp_content = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.vp_content);
		vpi_reserveclassify.setTypeface(Typeface.DEFAULT, -1);
		vpi_reserveclassify.setDividerPadding(0);
		vpi_reserveclassify.setDividerColor(Color.TRANSPARENT);
	}

	private void initListeners() {
		iv_back.setOnClickListener(this);
	}

	private void initDatas() {

		// vpi_reserveclassify.setTextSize(StringUtil.dip2px(
		// getApplicationContext(), 17));
		// vpi_reserveclassify.setDividerColor(0x00000000);
		// vpi_reserveclassify.setDividerPadding(StringUtil.dip2px(
		// getApplicationContext(), 0));
		// vpi_reserveclassify.setTabPaddingLeftRight(StringUtil.dip2px(
		// getApplicationContext(), 0));
		// vpi_reserveclassify.setIndicatorColorResource(R.color.mainblue001);
		// vpi_reserveclassify.setIndicatorHeight(StringUtil.dip2px(
		// getApplicationContext(), 2));

		for (int i = 0; i < mReserveTags.length; i++) {
//			mListFragment.add(RtReserveClassifyFragment.newInstance(mReserveTags[i]));
			mListFragment.add(new RtReserveClassifyFragment());
		}

		pagerAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
		vp_content.setAdapter(pagerAdapter);
		vp_content.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub

//				if (currentPosition != position) {

					RtReserveClassifyFragment fragment = (RtReserveClassifyFragment) mListFragment.get(position);

					fragment.resetCurrentPage();

					fragment.requestReserveList(true);

//				}

				currentPosition = position;

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
		vpi_reserveclassify.setViewPager(vp_content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case com.rs.mobile.wportal.R.id.iv_back:
			onBack();
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	private void onBack() {
		finish();
	}

	class TabPageIndicatorAdapter extends FragmentPagerAdapter {

		public TabPageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			
			Bundle bundle = new Bundle();
			bundle.putString("mReserveStatus", mReserveTags[position]);
			mListFragment.get(position).setArguments(bundle);
			
			return mListFragment.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTabTitles[position];
		}

		@Override
		public int getCount() {
			return mListFragment.size();
		}

	}

}
