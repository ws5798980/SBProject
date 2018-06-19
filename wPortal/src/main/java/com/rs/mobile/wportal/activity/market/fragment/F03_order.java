package com.rs.mobile.wportal.activity.market.fragment;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.adapter.ht.HtMyFragmentPageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 订单
 */
public class F03_order extends Fragment {

	private View parentView = null;
	private boolean firstLoad;
	private PagerSlidingTabStrip tabs;
	private ViewPager viewpager_fragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		firstLoad = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (firstLoad) {
			firstLoad = false;
			parentView = inflater.inflate(com.rs.mobile.wportal.R.layout.activity_f03_order, container, false);
			init();
		}

		return parentView;
	}

	public void init() {
		tabs = (PagerSlidingTabStrip) parentView.findViewById(com.rs.mobile.wportal.R.id.tabs);
		viewpager_fragment = (ViewPager) parentView.findViewById(com.rs.mobile.wportal.R.id.viewpager_fragment);
		String[] s = { "无人超市", "吃吃喝喝" };
		List<Fragment> fList = new ArrayList<>();
		Fragment_Order f1 = new Fragment_Order();
		Fragment_Order f2 = new Fragment_Order();
		f1.setIsMaker(true);
		f2.setIsMaker(false);
		fList.add(f1);
		fList.add(f2);
		HtMyFragmentPageAdapter adapter = new HtMyFragmentPageAdapter(getChildFragmentManager(), fList, s);
		viewpager_fragment.setAdapter(adapter);
		tabs.setViewPager(viewpager_fragment);
		tabs.setShouldExpand(true);
		tabs.setTextSize(StringUtil.dip2px(getContext(), 14));
		tabs.setDividerColor(0x00000000);
		tabs.setDividerPadding(StringUtil.dip2px(getContext(), 0));
		tabs.setTabPaddingLeftRight(StringUtil.dip2px(getContext(), 0));
		tabs.setIndicatorColorResource(com.rs.mobile.wportal.R.color.mk_text_click);
		tabs.setIndicatorHeight(StringUtil.dip2px(getContext(), 2));

		tabs.setSelectedTextColor(ContextCompat.getColor(getContext(), com.rs.mobile.wportal.R.color.mk_text_click));
	}

}
