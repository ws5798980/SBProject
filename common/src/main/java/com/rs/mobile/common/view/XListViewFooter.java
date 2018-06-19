package com.rs.mobile.common.view;



import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;



public class XListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	private int curr_state;

	private Context mContext;

	private View mContentView;
	private TextView mHintView;
	private LinearLayout mLoad;
	private TextView mReady;

	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public void setState(int state) {
		mHintView.setVisibility(View.GONE);
		mLoad.setVisibility(View.GONE);
		mReady.setVisibility(View.GONE);

		if (state == STATE_READY) {
			mReady.setVisibility(View.VISIBLE);
		} else if (state == STATE_LOADING) {
			mLoad.setVisibility(View.VISIBLE);
		} else {
			mHintView.setVisibility(View.VISIBLE);
		}
		curr_state = state;
	}

	// get current status (add by LSC)
	public int getState() {
		return curr_state;
	}

	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mLoad.setVisibility(View.GONE);
		mReady.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mLoad.setVisibility(View.VISIBLE);
		mReady.setVisibility(View.GONE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);

		// TODO 设置隐藏 add by LSC 20131101
		mContentView.setVisibility(View.GONE);
	}

	/**
	 * show footer
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);

		// TODO 设置隐藏 add by lsc 20131101
		mContentView.setVisibility(View.VISIBLE);

	}
	public void set_booter_text(String text){
		mHintView.setText(text);
		
	}
	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(com.rs.mobile.common.R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(com.rs.mobile.common.R.id.xlistview_footer_content);
		mHintView = (TextView) moreView.findViewById(com.rs.mobile.common.R.id.xlistview_footer_hint_textview);
          
		mLoad = (LinearLayout) moreView.findViewById(com.rs.mobile.common.R.id.xlistview_footer_load);
		mReady = (TextView) moreView.findViewById(com.rs.mobile.common.R.id.xlistview_footer_hint_ready);
	}

}
