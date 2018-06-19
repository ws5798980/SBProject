
package com.rs.mobile.wportal.activity.dp;

import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.fragment.dp.DpAddressFragment;
import com.rs.mobile.wportal.fragment.sm.StationFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DpAddressActivity extends BaseActivity {

	private ViewPager viewPager;

	private List<Fragment> fragmentList;

	private DpAddressFragment addressFragment;

	private StationFragment stationFragment;

	private LinearLayout address_tab_layout;

	private RelativeLayout address_tab_btn, my_station_tab_btn;

	private LinearLayout address_under_line, my_station_under_line;

	private TextView address_text_view, my_station_text_view, text_add;

	private LinearLayout close_btn;

	public boolean typeSelect = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {

			setContentView(com.rs.mobile.wportal.R.layout.activity_sm_address);

			typeSelect = getIntent().getBooleanExtra("typeSelect", false);

			initView();

			initDate();

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {

		try {

			close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);

			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					finish();
				}
			});

			address_tab_layout = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.address_tab_layout);

			address_tab_layout.setVisibility(View.GONE);

			address_tab_btn = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.address_tab_btn);

			address_tab_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					viewPager.setCurrentItem(0);

					address_under_line.setVisibility(View.VISIBLE);

					address_text_view.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));

					my_station_text_view.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));

					my_station_under_line.setVisibility(View.GONE);

				}

			});
			my_station_tab_btn = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.my_station_tab_btn);

			my_station_tab_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					viewPager.setCurrentItem(1);

					my_station_under_line.setVisibility(View.VISIBLE);

					my_station_text_view.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));

					address_text_view.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));

					address_under_line.setVisibility(View.GONE);

				}
			});

			address_under_line = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.address_under_line);

			my_station_under_line = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.my_station_under_line);

			address_text_view = (TextView) findViewById(com.rs.mobile.wportal.R.id.address_text_view);

			my_station_text_view = (TextView) findViewById(com.rs.mobile.wportal.R.id.my_station_text_view);

			text_add = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_add);

			text_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Bundle bundle = new Bundle();

					bundle.putString("activity", "add");

					PageUtil.jumpTo(DpAddressActivity.this, DpAddAddressActivity.class, bundle);

				}
			});
			viewPager = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.viewpager_fragment);

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	protected void onResume() {

		// TODO Auto-generated method stub
		super.onResume();

	}

	private void initDate() {

		try {

			fragmentList = new ArrayList<Fragment>();
			addressFragment = new DpAddressFragment();
			stationFragment = new StationFragment();
			fragmentList.add(addressFragment);
			// fragmentList.add(stationFragment);
			MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
			viewPager.setAdapter(adapter);
			viewPager.addOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {

					// TODO Auto-generated method stub
					if (arg0 == 0) {
						address_under_line.setVisibility(View.VISIBLE);
						address_text_view.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
						my_station_text_view.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
						my_station_under_line.setVisibility(View.GONE);

					} else {
						// my_station_under_line.setVisibility(View.VISIBLE);
						// my_station_text_view.setTextColor(getResources().getColor(R.color.mainblue001));
						// address_text_view.setTextColor(getResources().getColor(R.color.black));
						// address_under_line.setVisibility(View.GONE);
						viewPager.setCurrentItem(0);
					}
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

		} catch (Exception e) {

			L.e(e);

		}

	}

	class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public int getCount() {

			return list.size();
		}

		@Override
		public Fragment getItem(int arg0) {

			return list.get(arg0);
		}

	}
}
