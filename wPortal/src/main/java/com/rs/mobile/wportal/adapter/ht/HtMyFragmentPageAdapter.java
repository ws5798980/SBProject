
package com.rs.mobile.wportal.adapter.ht;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HtMyFragmentPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> list;

	public HtMyFragmentPageAdapter(FragmentManager fm, List<Fragment> list,String[] TITLES) {
		super(fm);
		this.list = list;
		this.TITLES=TITLES;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Fragment getItem(int arg0) {

		return list.get(arg0);
	}

	private final String[] TITLES ;

	@Override
	public CharSequence getPageTitle(int position) {

		return TITLES[position];
	}
   
}