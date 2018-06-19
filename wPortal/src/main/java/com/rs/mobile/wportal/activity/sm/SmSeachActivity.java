
package com.rs.mobile.wportal.activity.sm;

import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.activity.xsgr.XsShopListActivity;
import com.rs.mobile.wportal.view.FlowGroupView;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SmSeachActivity extends BaseActivity {

	private SearchView serchView;

	private TextView text_cancel, text_delete;

	private FlowGroupView flowView;

	private List<String> mHistoryKeywords;

	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_seach);

		try {

			initView();
			initData();
			initSearchHistory();

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initData() {

		S.get(SmSeachActivity.this, C.KEY_SP_HISTORYKEY);

	}

	private void initView() {

		try {

			editText = (EditText) findViewById(com.rs.mobile.wportal.R.id.edit_text);
			editText.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub

					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						String query = editText.getText().toString();
						save(query);
//						Bundle bundle = new Bundle();
//						bundle.putString("keyWord", query);
//						PageUtil.jumpTo(SmSeachActivity.this, SmSearchResultActivity.class, bundle);
						Intent intent = new Intent(SmSeachActivity.this, XsShopListActivity.class);
						intent.putExtra("keyWord", query);
						startActivity(intent);

						return true;
					}

					return false;
				}
			});
			text_cancel = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_cancel);
			text_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					finish();

				}
			});
			text_delete = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_delete);
			text_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					flowView.removeAllViews();
					S.set(getApplicationContext(), C.KEY_SP_HISTORYKEY, "");
				}
			});
			flowView = (FlowGroupView) findViewById(com.rs.mobile.wportal.R.id.flowView);

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 动态添加布局
	 * 
	 * @param str
	 */
	private void addTextView(String str) {

		try {

			ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
			lp.setMargins(StringUtil.dip2px(getApplicationContext(), 10), StringUtil.dip2px(getApplicationContext(), 10), StringUtil.dip2px(getApplicationContext(), 10), StringUtil.dip2px(getApplicationContext(), 10));
			// 新的TextView对象
			TextView tv = new TextView(this);
			tv.setPadding(StringUtil.dip2px(getApplicationContext(), 10), StringUtil.dip2px(getApplicationContext(), 10), StringUtil.dip2px(getApplicationContext(), 10), StringUtil.dip2px(getApplicationContext(), 10));
			tv.setBackgroundResource(com.rs.mobile.wportal.R.color.white);
			tv.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.inputblack));

			tv.setText(str);
			tv.setGravity(Gravity.CENTER);
			tv.setLines(1);
			initEvents(tv);

			flowView.addView(tv, lp);
			flowView.requestLayout();

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 为每个view 添加点击事件
	 * 
	 * @param tv
	 */
	private void initEvents(final TextView tv) {

		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
//				Bundle bundle = new Bundle();
//				bundle.putString("keyWord", tv.getText().toString());
//				PageUtil.jumpTo(SmSeachActivity.this, SmSearchResultActivity.class, bundle);
				Intent intent = new Intent(SmSeachActivity.this, XsShopListActivity.class);
				intent.putExtra("keyWord", tv.getText().toString());
				startActivity(intent);

			}
		});
	}

	public void initSearchHistory() {

		mHistoryKeywords = new ArrayList<String>();
		String history = S.get(SmSeachActivity.this, C.KEY_SP_HISTORYKEY);
		if (!TextUtils.isEmpty(history)) {
			List<String> list = new ArrayList<String>();
			for (Object o : history.split(",")) {
				list.add((String) o);
				addTextView(o.toString());
			}
			mHistoryKeywords = list;
		}
	}

	public void save(String text) {

		// String text = serchView.getText().toString();
		String oldText = S.get(getApplicationContext(), C.KEY_SP_HISTORYKEY);

		if (!TextUtils.isEmpty(text) && !oldText.contains(text)) {

			S.set(getApplicationContext(), C.KEY_SP_HISTORYKEY, text + "," + oldText);
			mHistoryKeywords.add(0, text);
		}

	}

}