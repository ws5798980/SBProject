package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    Context context;
    List<Fragment> list;

    private String[] titles;

    public ViewPagerAdapter(FragmentManager fm, Context context, List list, String[] titles) {
        super(fm);
        this.context = context;
        this.list = list;
        this.titles = titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


}
