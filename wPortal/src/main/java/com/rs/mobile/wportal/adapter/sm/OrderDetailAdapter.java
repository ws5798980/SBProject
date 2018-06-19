
package com.rs.mobile.wportal.adapter.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.sm.ReturnGoodActivity;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.wportal.biz.ShoppingCart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import okhttp3.Request;

public class OrderDetailAdapter extends BaseAdapter {

	private JSONArray listdata;

	private Context context;

	private String order_code;

	private ShoppingCart cart;

	private String ONLINE_ORDER_STATUS;

	public OrderDetailAdapter(JSONArray listdata, Context context, String order_code, String ONLINE_ORDER_STATUS) {
		this.context = context;
		this.listdata = listdata;
		this.order_code = order_code;
		this.ONLINE_ORDER_STATUS = ONLINE_ORDER_STATUS;
	}

	@Override
	public int getCount() {

		return listdata.length();
	}

	@Override
	public Object getItem(int position) {

		try {
			return listdata.get(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		try {

			if (convertView == null) {

				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_order_detail, parent,
						false);

			}
			WImageView imageView = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img_goods);

			TextView goodName = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.good_name);

			TextView text_back = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_back);

			TextView goodNum = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.good_num);

			JSONObject js = listdata.getJSONObject(position);

			ImageUtil.drawImageFromUri(js.getString("image_url"), imageView);

			goodName.setText(js.get("item_name").toString());

			goodNum.setText("x" + js.get("item_quantity").toString());

			String refund_status = js.get("refund_status").toString();

			final String item_code = js.get("item_code").toString();

			final String image_url = js.getString("image_url");

			final String item_name = js.getString("item_name");

			final int item_quantity = js.getInt("item_quantity");

			final float item_price = Float.parseFloat(js.get("item_price").toString());

			final String stock_unit = js.get("stock_unit").toString();

			final String div_code = js.getString(C.KEY_DIV_CODE);

			if (ONLINE_ORDER_STATUS.equals("3")) {
				switch (refund_status) {
				case "0":
					text_back.setVisibility(View.VISIBLE);
					text_back.setText(context.getString(com.rs.mobile.wportal.R.string.common_text053));
					text_back.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							Bundle bundle = new Bundle();
							bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
							cart = new ShoppingCart(item_code, item_name, item_price, image_url, item_quantity, false,
									stock_unit, div_code, "");
							ArrayList<ShoppingCart> arrlist = new ArrayList<>();
							arrlist.add(cart);
							bundle.putString("order_code", order_code);
							bundle.putParcelableArrayList("goods", arrlist);
							PageUtil.jumpTo(context, ReturnGoodActivity.class, bundle);
						}
					});

					break;

				// case "1":
				// text_back.setVisibility(View.VISIBLE);
				// text_back.setText("取消退款申请");
				// text_back.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// submitRefundApply(item_code);
				// }
				// });
				// break;
				case "2":
					text_back.setVisibility(View.VISIBLE);
					text_back.setText(context.getString(com.rs.mobile.wportal.R.string.common_text053));
					text_back.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							ArrayList<ShoppingCart> arrlist = new ArrayList<>();
							Bundle bundle = new Bundle();
							bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
							cart = new ShoppingCart(item_code, item_name, item_price, image_url, item_quantity, false,
									stock_unit, div_code, "");
							arrlist.add(cart);
							bundle.putString("order_code", order_code);
							bundle.putParcelableArrayList("goods", arrlist);
							PageUtil.jumpTo(context, ReturnGoodActivity.class, bundle);
						}
					});
					break;
				// case "3":
				// text_back.setVisibility(View.VISIBLE);
				// text_back.setText("填写快递单号");
				//
				// break;
				// case "4":
				// text_back.setVisibility(View.VISIBLE);
				// text_back.setText("退款中");
				// break;
				// case "5":
				// text_back.setVisibility(View.GONE);
				// break;
				// case "6":
				// text_back.setVisibility(View.VISIBLE);
				// text_back.setText("退款完成");
				// break;
				// case "7":
				// text_back.setVisibility(View.VISIBLE);
				// text_back.setText("退款完成");
				// break;
				default:
					break;
				}
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
					bundle.putString(C.KEY_DIV_CODE, div_code);
					PageUtil.jumpTo(context, SmGoodsDetailActivity.class, bundle);

				}
			});

		} catch (Exception e) {

			L.e(e);

		}
		return convertView;
	}

	private void submitRefundApply(String item_code) {

		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		HashMap<String, String> params = new HashMap<>();
		params.put("order_num", order_code);
		params.put("item_code", item_code);
		params.put("custom_code", S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
		params.put("reason_msg", "");
		params.put("isCancel", "true");

		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {

			}
		}, Constant.SM_BASE_URL + Constant.SubmitRefundApply, params);
	}
}
