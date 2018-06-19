package com.rs.mobile.wportal.activity.rt;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * 奢厨餐厅框架页
 * 
 * @author ZhaoYun
 * @date 2017-3-11
 */
public class RtMainActivity extends TabActivity {

	public final static String ACTION_RECEIVE_RT_FINISH = "com.rs.mobile.wportal.action_rt_finish";
	
	public static int displayWidth = 720;
	
	// @BindView(android.R.id.tabcontent)
	private FrameLayout tabcontent;
	// @BindView(android.R.id.tabs)
	private TabWidget tabs;
	// @BindView(android.R.id.tabhost)
	private TabHost tabhost;

	private Intent homePage;
	private Intent reserve;
	private Intent mine;

	private static final String ACTION_CHANGETAB = "RtMainActivity.action.changetab";// (没有实例则创建实例)changeTabIndex
	private static final String EXTRA_TABINDEX = "RtMainActivity.extra.tabindex";
	private static final String EXTRA_DATE = "RtMainActivity.extra.date";

	public static void startActionChangeTab(Context context, int tabIndex,
			Bundle date) {
		Intent changeTabIntent = new Intent(context, RtMainActivity.class);
		changeTabIntent.setAction(ACTION_CHANGETAB);
		changeTabIntent.putExtra(EXTRA_TABINDEX, tabIndex);
		changeTabIntent.putExtra(EXTRA_DATE, date);
		context.startActivity(changeTabIntent);
	}
	
	private Receiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_main);
		
		receiver = new Receiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(RtMainActivity.ACTION_RECEIVE_RT_FINISH);
		registerReceiver(receiver, filter);
		
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		
		displayWidth = displayMetrics.widthPixels;
		
		initTabHost();
		handleActionChangeTab(getIntent());
	}
	
	@Override
	protected void onDestroy() {
		
		unregisterReceiver(receiver);

		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	private void initTabHost() {
		initViews();
		initIntent();
		setupIntent();
	}

	/**
	 * 初始化TabHost的基本框架
	 * 
	 * @author ZhaoYun
	 * @date 2017-3-11
	 */
	private void initViews() {
		tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
		tabs = (TabWidget) findViewById(android.R.id.tabs);
		tabhost = (TabHost) findViewById(android.R.id.tabhost);
	}

	/**
	 * 初始化各个Tab页具体所对应的Activity所需要的Intent
	 * 
	 * @author ZhaoYun
	 * @date 2017-3-11
	 */
	private void initIntent() {
		homePage = new Intent(this, RtHomePageActivity.class);
		reserve = new Intent(this, RtReserveActivity.class);
		mine = new Intent(this, RtMineActivity.class);
	}

	/**
	 * 将各个初始化过的Tab安装进TabHost中的TabWidget
	 * 
	 * @author ZhaoYun
	 * @date 2017-3-11
	 */
	private void setupIntent() {
		if (tabhost != null) {
			tabhost.addTab(buildTabSpec(0, RtHomePageActivity.class.getName(), homePage));
			tabhost.addTab(buildTabSpec(1, RtReserveActivity.class.getName(), reserve));
			tabhost.addTab(buildTabSpec(2, RtMineActivity.class.getName(), mine));
			tabhost.getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					UtilClear.CheckLogin(RtMainActivity.this, new UtilCheckLogin.CheckListener() {
						
						@Override
						public void onDoNext() {
							tabhost.setCurrentTab(1);
						}
					},new UtilCheckLogin.CheckError() {
						
						@Override
						public void onError() {
							tabhost.setCurrentTab(0);
						}
					} );
					
				}
			});
			tabhost.getTabWidget().getChildAt(2).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					UtilClear.CheckLogin(RtMainActivity.this, new UtilCheckLogin.CheckListener() {
						
						@Override
						public void onDoNext() {
							tabhost.setCurrentTab(2);
						}
					},new UtilCheckLogin.CheckError() {
						
						@Override
						public void onError() {
							tabhost.setCurrentTab(0);
						}
					} );
				}
			});
			return;
		}
		throw new NullPointerException("TabHost init failed");
	}

	/**
	 * 设置TabHost中每个TabSpec所对应的View样式，标签以及切换后所显示内容
	 * 
	 * @param index
	 * @param tag
	 * @param intent
	 * @return
	 */
	private TabHost.TabSpec buildTabSpec(int index, String tag, Intent intent) {
		View view = View
				.inflate(this, com.rs.mobile.wportal.R.layout.layout_rt_tabhost_tabspec, null);
		if (view == null) {
			throw new NullPointerException("TabSpec's layout inflate failed");
		}
		ImageView iv_tab_icon = (ImageView) view.findViewById(com.rs.mobile.wportal.R.id.iv_tab_icon);
		TextView tv_tab_label = (TextView) view.findViewById(com.rs.mobile.wportal.R.id.tv_tab_label);
		switch (index) {
		case 0:
			iv_tab_icon.setImageResource(com.rs.mobile.wportal.R.drawable.icon_rt_tabhost_tabspec_homepage);
			tv_tab_label.setText(com.rs.mobile.wportal.R.string.rt_main_homepage_tab);
			break;

		case 1:
			iv_tab_icon
					.setImageResource(com.rs.mobile.wportal.R.drawable.icon_rt_tabhost_tabspec_order);
			tv_tab_label.setText(com.rs.mobile.wportal.R.string.rt_main_order_tab);
			break;

		case 2:
			iv_tab_icon
					.setImageResource(com.rs.mobile.wportal.R.drawable.icon_rt_tabhost_tabspec_mine);
			tv_tab_label.setText(com.rs.mobile.wportal.R.string.rt_main_mine_tab);
			break;

		default:
			break;
		}
		return tabhost.newTabSpec(tag).setIndicator(view).setContent(intent);
	}

	/**
	 * 根据传递的Intent所携带date进行Tab操作
	 * 
	 * @param intent
	 * @author ZhaoYun
	 * @date 2017-3-11
	 */
	private void handleActionChangeTab(Intent intent) {
		if (intent != null) {
			String action = intent.getAction();
			if (action != null) {
				switch (action) {
				case ACTION_CHANGETAB:
					int tabIndex = intent.getIntExtra(EXTRA_TABINDEX, 0);
					tabhost.setCurrentTab(tabIndex);
					
					break;

				default:
					break;
				}
			}
			return;
		}
		throw new NullPointerException("The intent to be passed is null");
	}

	/**
	 * 对Back键点击事件的处理
	 * 
	 * @author ZhaoYun
	 * @date 2017-3-11
	 */
	private void onBack() {
		// TODO
		// 根据业务需要，以后也许要把该TabHost单独分离，成为独立APP，对Back的处理将更为复杂(比如判断启动该Ativity的途径，或者加入双击(或对话框)退出等)
		finish();
	}
	
	public class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			try {
				
				if (intent.getAction().equals(ACTION_RECEIVE_RT_FINISH)) {
					
//					tabhost.setCurrentTab(1);
					
				}
			
			} catch (Exception e) {
				
				L.e(e);
				
			}
			
		}

	}

}
