package com.rs.mobile.wportal.activity.dp;

import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.fragment.dp.DpMyOrderCommonFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DpMyOrderActivity extends BaseActivity {

	private List<TextView> tabTextList = new ArrayList<TextView>();

	private List<LinearLayout> tabBtnList = new ArrayList<LinearLayout>();

	private TextView textview_001, textview_002, textview_003, textview_004;

	private LinearLayout underLine001, underLine002, underLine003, underLine004;

	private RelativeLayout relativeLayout_001, relativeLayout_002, relativeLayout_003, relativeLayout_004;

	private LinearLayout close_btn;

	private TextView textview_005;

	private LinearLayout underLine005;

	private RelativeLayout relativeLayout_005;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {

			setContentView(com.rs.mobile.wportal.R.layout.activity_sm_myorder);

			initView(getIntent().getIntExtra("position", 0));

		} catch (Exception e) {

			L.e(e);

		}
	}

	// public static void(int i){}
	/**
	 * 切换到已付款、待付款、服务中或待评价，同时带上要传递的数据
	 * 
	 * @param fragment
	 *            未包含要传递的数据Bundle
	 * @param data
	 *            需要传递给fragment的数据
	 */
	private void replaceFragmentPageWithData(Fragment fragment, Bundle data) {

		try {

			if (data != null) {
				fragment.setArguments(data);
			}

			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(com.rs.mobile.wportal.R.id.my_order_xml_fragment_root, fragment)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void setTabBarCorlor(int i) {

		try {

			for (int j = 0; j < tabTextList.size(); j++) {
				if (i == j) {
					tabTextList.get(j).setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
					tabBtnList.get(j).setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
				} else {
					tabTextList.get(j).setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
					tabBtnList.get(j).setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
				}

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initView(int position) {

		try {

			textview_001 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_001);
			textview_002 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_002);
			textview_003 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_003);
			textview_004 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_004);
			textview_005 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_005);

			underLine001 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine001);
			underLine002 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine002);
			underLine003 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine003);
			underLine004 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine004);
			underLine005 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine005);

			close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			relativeLayout_001 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_001);
			relativeLayout_001.setOnClickListener(tabclickListener);

			relativeLayout_002 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_002);
			relativeLayout_002.setOnClickListener(tabclickListener);

			relativeLayout_003 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_003);
			relativeLayout_003.setOnClickListener(tabclickListener);

			relativeLayout_004 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_004);
			relativeLayout_004.setOnClickListener(tabclickListener);

			relativeLayout_005 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_005);
			relativeLayout_005.setOnClickListener(tabclickListener);

			tabTextList.add(textview_001);
			tabTextList.add(textview_002);
			tabTextList.add(textview_003);
			tabTextList.add(textview_004);
			tabTextList.add(textview_005);

			tabBtnList.add(underLine001);
			tabBtnList.add(underLine002);
			tabBtnList.add(underLine003);
			tabBtnList.add(underLine004);
			tabBtnList.add(underLine005);

			draw(position);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private OnClickListener tabclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {

			case com.rs.mobile.wportal.R.id.relativeLayout_001:

				draw(0);

				break;

			case com.rs.mobile.wportal.R.id.relativeLayout_002:

				draw(1);

				break;

			case com.rs.mobile.wportal.R.id.relativeLayout_003:

				draw(3);

				break;

			case com.rs.mobile.wportal.R.id.relativeLayout_004:

				draw(5);

				break;
			case com.rs.mobile.wportal.R.id.relativeLayout_005:

				draw(2);

				break;

			}

		}
	};

	private void draw(int index) {

		try {
			if (index == 1) {
				setTabBarCorlor(1);
			} else if (index == 3) {
				setTabBarCorlor(2);
			} else if (index == 5) {
				setTabBarCorlor(3);
			} else if (index == 0) {
				setTabBarCorlor(0);
			} else {
				setTabBarCorlor(4);
			}

			Bundle bundle = new Bundle();
			bundle.putString(C.KEY_JSON_FM_ORDERSTATUS, "" + index);
			replaceFragmentPageWithData(new DpMyOrderCommonFragment(), bundle);

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}
