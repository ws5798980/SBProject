package com.rs.mobile.wportal.view;

import java.util.List;
import java.util.Map;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.StringUtil;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class GoodsFormatView extends LinearLayout {
	private List<Map<String, Object>> listData;
	public FlowGroupView flowGroupView;
	private Handler handler;
	private String flag;

	public GoodsFormatView(Context context, String flag, List<Map<String, Object>> listData, Handler handler) {
		super(context);
		// TODO Auto-generated constructor stub
		this.listData = listData;
		this.flag = flag;
		this.handler = handler;
		initView(context);

	}

	public GoodsFormatView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public GoodsFormatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	private void initView(Context context) {

		LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = inflator.inflate(com.rs.mobile.wportal.R.layout.goods_format_view, this);

		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		flowGroupView = (FlowGroupView) v.findViewById(com.rs.mobile.wportal.R.id.flowgroupview);

		for (int i = 0; i < listData.size(); i++) {
			Map<String, Object> map = listData.get(i);

			addTextView(context, map.get("id").toString(), map.get("parent_item_code").toString(),
					map.get("sub_item_code").toString(), map.get("sub_item_name").toString(),
					Float.parseFloat(map.get("sub_item_price").toString()),
					Integer.parseInt(map.get("sub_item_stock_q").toString()), map.get("sub_item_stock_unit").toString(),
					map.get("sub_item_option").toString(), map.get("sub_item_spec").toString());
		}

	}

	/**
	 * 动态添加布局
	 * 
	 * @param str
	 */
	private void addTextView(final Context context, String id, String parent_item_code, String sub_item_code,
			String sub_item_name, float sub_item_price, int sub_item_stock_q, String sub_item_stock_unit,
			String sub_item_option, String sub_item_spec) {

		try {

			ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.MarginLayoutParams.WRAP_CONTENT);
			lp.setMargins(StringUtil.dip2px(context, 0), StringUtil.dip2px(context, 0), StringUtil.dip2px(context, 5),
					StringUtil.dip2px(context, 5));

			// 新的TextView对象
			final FormatTextView tv = new FormatTextView(context, id, false, parent_item_code, sub_item_code,
					sub_item_name, sub_item_price, sub_item_stock_q, sub_item_stock_unit, sub_item_option,
					sub_item_spec);
			tv.setPadding(StringUtil.dip2px(context, 5), StringUtil.dip2px(context, 5), StringUtil.dip2px(context, 5),
					StringUtil.dip2px(context, 5));

			tv.setBackgroundResource(com.rs.mobile.wportal.R.drawable.goods_format_n);
			tv.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.inputblack));
			tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			tv.setText(sub_item_name);
			tv.setGravity(Gravity.CENTER);

			tv.setEllipsize(TruncateAt.END);
			tv.setMaxEms(15);
			tv.setSingleLine(true);
			if (listData.size() == 1 && flag.equals("1")) {
				tv.setIschoosed(true);
				tv.setBackgroundResource(com.rs.mobile.wportal.R.drawable.goods_format_s);
				tv.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.white));
			}
			tv.setCheckid(id);

			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (flag.equals("1")) {
						for (int i = 0; i < flowGroupView.getChildCount(); i++) {
							flowGroupView.getChildAt(i).setBackgroundResource(com.rs.mobile.wportal.R.drawable.goods_format_n);
							((FormatTextView) flowGroupView.getChildAt(i))
									.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.black));
							((FormatTextView) flowGroupView.getChildAt(i)).setIschoosed(false);
						}
						tv.setIschoosed(true);
						tv.setBackgroundResource(com.rs.mobile.wportal.R.drawable.goods_format_s);
						tv.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.white));

					} else {
						if (tv.ischoosed) {
							tv.setIschoosed(false);
							tv.setBackgroundResource(com.rs.mobile.wportal.R.drawable.goods_format_n);
							tv.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.inputblack));

						} else {
							tv.setIschoosed(true);
							tv.setBackgroundResource(com.rs.mobile.wportal.R.drawable.goods_format_s);
							tv.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.white));

						}

					}
					handler.sendEmptyMessage(10);
				}
			});

			flowGroupView.addView(tv, lp);
			flowGroupView.requestLayout();

		} catch (Exception e) {

			L.e(e);

		}

	}

}
