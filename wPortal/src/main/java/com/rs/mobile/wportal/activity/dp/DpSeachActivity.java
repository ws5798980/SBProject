
package com.rs.mobile.wportal.activity.dp;

import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.view.FlowGroupView;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.StringUtil;

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

public class DpSeachActivity extends BaseActivity {

	private SearchView serchView;

	private TextView text_cancel, text_delete;

	private FlowGroupView flowView;

	private List<String> mHistoryKeywords;

	private EditText editText;

	private final String SHKEY = "dp_sp_history";

	private String level1;

	private String custom_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_dp_seach);

		try {

			initView();
			initData();
			initSearchHistory();
			level1 = getIntent().getStringExtra("level1");
			level1 = level1 == null ? "" : level1;
			custom_code = getIntent().getStringExtra("custom_code");
			custom_code = custom_code == null ? "" : custom_code;
		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initData() {

		S.get(DpSeachActivity.this, SHKEY);

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
						Bundle bundle = new Bundle();
						bundle.putString("keyWord", query);
						bundle.putString("flag", "keyWord");
						bundle.putString("level1", level1);
						bundle.putString("custom_code", custom_code);
						PageUtil.jumpTo(DpSeachActivity.this, DpSerchResultActivity.class, bundle);
						finish();
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
					S.set(getApplicationContext(), SHKEY, "");
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

			ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.MarginLayoutParams.WRAP_CONTENT);
			lp.setMargins(StringUtil.dip2px(getApplicationContext(), 10),
					StringUtil.dip2px(getApplicationContext(), 10), StringUtil.dip2px(getApplicationContext(), 10),
					StringUtil.dip2px(getApplicationContext(), 10));
			// 新的TextView对象
			TextView tv = new TextView(this);
			tv.setPadding(StringUtil.dip2px(getApplicationContext(), 10),
					StringUtil.dip2px(getApplicationContext(), 10), StringUtil.dip2px(getApplicationContext(), 10),
					StringUtil.dip2px(getApplicationContext(), 10));
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
				Bundle bundle = new Bundle();
				bundle.putString("keyWord", tv.getText().toString());
				bundle.putString("flag", "keyWord");
				bundle.putString("level1", level1);
				bundle.putString("custom_code", custom_code);
				PageUtil.jumpTo(DpSeachActivity.this, DpSerchResultActivity.class, bundle);
				finish();
			}
		});
	}

	public void initSearchHistory() {

		mHistoryKeywords = new ArrayList<String>();
		String history = S.get(DpSeachActivity.this, SHKEY);
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
		String oldText = S.get(getApplicationContext(), SHKEY);

		if (!TextUtils.isEmpty(text) && !oldText.contains(text)) {

			S.set(getApplicationContext(), SHKEY, text + "," + oldText);
			mHistoryKeywords.add(0, text);
		}

	}

}