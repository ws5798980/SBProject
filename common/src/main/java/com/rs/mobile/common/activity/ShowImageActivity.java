package com.rs.mobile.common.activity;

import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.common.adapter.FragmentPageAdapter;
import com.rs.mobile.common.fragment.FragmentShowImage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

public class ShowImageActivity extends BaseActivity {

	private ViewPager cViewPager;

	private FragmentPageAdapter fragmentPagerAdapter;

	private List<Fragment> fragmentList1 = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(com.rs.mobile.common.R.layout.activity_showimg);
		
		String[] imgs = getIntent().getStringArrayExtra("images");
		
		int position=getIntent().getIntExtra("position", 0);
		
		cViewPager = (ViewPager) findViewById(com.rs.mobile.common.R.id.viewpager_showimg);
		
		for (int j = 0; j < imgs.length; j++) {
			
			Bundle bundle = new Bundle();
			
			bundle.putString("url", imgs[j]);
			
			FragmentShowImage ff = new FragmentShowImage().newInstance(bundle);
			
			fragmentList1.add(ff);
		}
		
		fragmentPagerAdapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentList1);
		
		cViewPager.setAdapter(fragmentPagerAdapter);
		
		cViewPager.setCurrentItem(position);
		
	}

	@Override
	public void onBackPressed() {

		// TODO Auto-generated method stub
		finish();
	}
}
