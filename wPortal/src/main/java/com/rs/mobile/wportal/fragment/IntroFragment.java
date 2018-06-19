package com.rs.mobile.wportal.fragment;

import com.rs.mobile.common.L;
import com.rs.mobile.common.network.Util;
import com.rs.mobile.wportal.activity.MainActivity;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class IntroFragment extends Fragment {
	
	private MainActivity activity;
	
	private Context context;
	
	private View rootView;
	
	public ViewPager viewPager;
	
	public ViewPagerAdapter viewPagerAdapter;

	public TextView countTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		rootView = inflater.inflate(com.rs.mobile.wportal.R.layout.activity_intro, container, false);
		
		context = container.getContext();
		
		activity = (MainActivity)context;
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		try {

			if (Util.checkNetwork(context) == true) {

				viewPager = (ViewPager)rootView.findViewById(com.rs.mobile.wportal.R.id.view_pager);
				viewPager.setOnPageChangeListener(new OnPageChangeListener() {
					
					@Override
					public void onPageSelected(int arg0) {
						// TODO Auto-generated method stub
					
						activity.closeCount = 6;
						
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
				countTextView = (TextView)rootView.findViewById(com.rs.mobile.wportal.R.id.count_text_view);
				countTextView.setVisibility(View.GONE);
				countTextView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						activity.startMainFragment();

					}
				});

			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}
	
}
