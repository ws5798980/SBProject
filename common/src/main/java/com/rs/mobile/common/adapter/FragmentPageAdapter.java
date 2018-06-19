
package com.rs.mobile.common.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class FragmentPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> list;

	public FragmentPageAdapter(FragmentManager fm, List<Fragment> list) {
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

	private final String[] TITLES = { "商品", "详情", "评价" };

	@Override
	public CharSequence getPageTitle(int position) {

		return TITLES[position];
	}
    
	
}