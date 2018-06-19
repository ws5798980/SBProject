
package com.rs.mobile.wportal.adapter.sm;

import java.util.List;

import com.rs.mobile.wportal.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> list;

	public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> list, Context context) {
		super(fm);
		this.list = list;
		TITLES[0] = context.getString(R.string.common_text106);
		TITLES[1] = context.getString(R.string.common_text107);
		TITLES[2] = context.getString(R.string.common_text108);
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Fragment getItem(int arg0) {

		return list.get(arg0);
	}

	private final String[] TITLES = new String[3];

	@Override
	public CharSequence getPageTitle(int position) {

		return TITLES[position];
	}

}