
package com.rs.mobile.wportal.adapter.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.sm.ReturnProcessActivity;
import com.rs.mobile.wportal.activity.dp.DpReturnGoodsListActivity;
import com.rs.mobile.wportal.activity.sm.FillinLogicActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import okhttp3.Request;

public class ReturnGoodsListAdapter extends BaseAdapter {

	private JSONArray arr;

	private DpReturnGoodsListActivity context;

	private LayoutInflater minflater;

	public ReturnGoodsListAdapter(JSONArray arr, DpReturnGoodsListActivity context) {
		super();
		this.arr = arr;
		this.context = context;
		minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return arr.length();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		try {
			return arr.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflater.inflate(com.rs.mobile.wportal.R.layout.list_item_returngood, null);
		}
		WImageView imageView = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img);
		TextView text_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.itemName);
		TextView text_btn1 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_btn1);
		TextView text_apply = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.applyTime);
		TextView text_btn2 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_btn2);
		TextView text_btn3 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_btn3);
		try {
			JSONObject js = arr.getJSONObject(position);
			final String orderNum = js.get("orderNum").toString();
			final String itemCode = js.get("itemCode").toString();
			String status = js.get("status").toString();
			final String refundNo = js.get("refundNo").toString();
			String applyTime = js.get("applyTime").toString();
			String itemName = js.get("itemName").toString();
			String imageUrl = js.get("imageUrl").toString();
			ImageUtil.drawImageFromUri(imageUrl, imageView);
			text_name.setText(itemName);
			text_apply.setText(applyTime);

			switch (status) {
			case "1":
				text_btn1.setVisibility(View.VISIBLE);
				text_btn2.setVisibility(View.GONE);
				break;
			case "2":
				text_btn1.setVisibility(View.GONE);
				text_btn2.setVisibility(View.GONE);
				break;
			case "3":
				text_btn1.setVisibility(View.GONE);
				text_btn2.setVisibility(View.VISIBLE);
				break;

			default:
				text_btn1.setVisibility(View.GONE);
				text_btn2.setVisibility(View.GONE);
				break;
			}
			text_btn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					submitRefundApply(itemCode, orderNum, refundNo);
				}
			});
			text_btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString("refundNo", refundNo);
					PageUtil.jumpTo(context, FillinLogicActivity.class, bundle);

				}
			});
			text_btn3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString("refundNo", refundNo);
					PageUtil.jumpTo(context, ReturnProcessActivity.class, bundle);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
	}

	private void submitRefundApply(String item_code, String order_code, String refundNo) {

		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		HashMap<String, String> params = new HashMap<>();
		params.put("order_num", order_code);
		params.put("item_code", item_code);
		params.put("custom_code", S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
		params.put("reason_msg", "");
		params.put("isCancel", "1");
		params.put("refundNo", refundNo);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					T.showToast(context, data.getString("message"));
					if (data.get("status").toString().equals("1")) {
						context.getUserOrderList();
						notifyDataSetChanged();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP1 + Constant.SubmitRefundApply, params);

	}

}
