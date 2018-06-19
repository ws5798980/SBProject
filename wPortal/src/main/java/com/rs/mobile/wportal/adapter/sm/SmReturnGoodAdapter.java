
package com.rs.mobile.wportal.adapter.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.sm.ReturnProcessActivity;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.activity.sm.OrderDetailActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class SmReturnGoodAdapter extends BaseAdapter {

	private Context context;

	private JSONArray jsonArray;

	static class ViewHolder {

		private TextView text_orderTime;

		private TextView text_orderStatus;

		private ListView listView;

		private TextView text_totoal, cancelBtn, paymentBtn;
	}

	public SmReturnGoodAdapter(Context context, JSONArray jsonArray) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.jsonArray = jsonArray;

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return jsonArray.length();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		try {
			return jsonArray.get(position);
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

		try {

			ViewHolder itemView = null;

			if (convertView == null) {

				itemView = new ViewHolder();
				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_myorder_common,
						parent, false);
				itemView.listView = (ListView) convertView.findViewById(com.rs.mobile.wportal.R.id.listView);
				itemView.text_orderStatus = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_orderStatus);
				itemView.text_orderTime = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_orderTime);
				itemView.text_totoal = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_totoal);
				itemView.cancelBtn = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.cancel_btn);
				itemView.paymentBtn = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.payment_btn);
				convertView.setTag(itemView);

			} else {

				itemView = (ViewHolder) convertView.getTag();

			}

			final JSONObject jsonObject = new JSONObject(getItem(position).toString());

			itemView.text_orderStatus.setText(context.getString(com.rs.mobile.wportal.R.string.common_text110));
			itemView.text_orderTime.setText(jsonObject.get(C.KEY_JSON_FM_ORDERDATE).toString());

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						Bundle bundle = new Bundle();
						bundle.putString(C.KEY_JSON_FM_ITEM_CODE, jsonObject.getString("order_code"));
						PageUtil.jumpTo(context, ReturnProcessActivity.class, bundle);

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			itemView.text_totoal.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.order_bewrite1)
					+ jsonObject.get(C.KEY_JSON_FM_ORDERNUM) + context.getResources().getString(com.rs.mobile.wportal.R.string.order_bewrite2)
					+ jsonObject.get(C.KEY_JSON_FM_ORDERPRICE));

			JSONArray arr = jsonObject.getJSONArray(C.KEY_JSON_FM_ORDER_GROUPGOODLIST);
			final ArrayList<ShoppingCart> list = new ArrayList<ShoppingCart>();

			for (int i = 0; i < arr.length(); i++) {
				JSONObject jsobj = arr.getJSONObject(i);
				ShoppingCart shoppingCart = new ShoppingCart(jsobj.get(C.KEY_JSON_FM_ITEM_CODE).toString(),
						jsobj.get(C.KEY_JSON_FM_ITEM_NAME).toString(),
						Float.parseFloat(jsobj.get(C.KEY_JSON_FM_ITEM_PRICE).toString()),
						jsobj.get(C.KEY_JSON_FM_ITEM_IMAGE_URL).toString(), jsobj.getInt(C.KEY_JSON_FM_ITEM_QUANTITI),
						false, jsobj.get(C.KEY_JSON_FM_STOCK_UNIT).toString(), C.DIV_CODE, "");
				list.add(shoppingCart);
			}

			// 취소
			itemView.cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});

			// 결제/ 평가
			itemView.paymentBtn.setText(context.getString(com.rs.mobile.wportal.R.string.rt_check_process));
			itemView.paymentBtn.setVisibility(View.VISIBLE);
			itemView.paymentBtn.setTextColor(context.getResources().getColor(com.rs.mobile.wportal.R.color.black));
			itemView.paymentBtn
					.setBackground(context.getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_black_stroke_solid_white_cor_2));
			itemView.paymentBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					if (UiUtil.checkLogin(context) == true) {

						Bundle bundle = new Bundle();
						try {
							bundle.putString(C.KEY_JSON_FM_ITEM_CODE, jsonObject.getString("order_code"));
							PageUtil.jumpTo(context, ReturnProcessActivity.class, bundle);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// bundle.putString(c.keyjsonfmui, value);

					}
				}
			});

			itemView.listView.setAdapter(new confirmOrderGoodAdapter(list, context));
			itemView.listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

					// TODO Auto-generated method stub
					try {

						// ShoppingCart shoppingCart = list.get(position);
						//
						// String item_code = shoppingCart.getId();
						//
						// Bundle bundle=new Bundle();
						//
						// bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
						//
						// PageUtil.jumpTo(context,
						// SmGoodsDetailActivity.class,bundle);
						Bundle bundle = new Bundle();
						bundle.putString(C.KEY_JSON_FM_ORDERCODE, jsonObject.getString("order_code"));
						PageUtil.jumpTo(context, OrderDetailActivity.class, bundle);

					} catch (Exception e) {

						L.e(e);

					}

				}
			});
			((BaseActivity) context).setListViewHeight(itemView.listView);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
		return convertView;
	}

	// private void confirmGetGoods(String order_id) {
	//
	// try {
	//
	// Map<String, String> params = new HashMap<String, String>();
	// params.put(C.KEY_JSON_FM_ORDERCODE, order_id);
	// OkHttpHelper okHttpHelper = new OkHttpHelper(context);
	// okHttpHelper.addSMPostRequest(new CallbackLogic() {
	//
	// @Override
	// public void onNetworkError(Request request, IOException e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onBizSuccess(String responseDescription, JSONObject data,
	// String flag) {
	//
	// // TODO Auto-generated method stub
	// try {
	// if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {
	// T.showToast(context, context.getResources().getString(R.string.success));
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// L.e(e);
	// }
	// }
	//
	// @Override
	// public void onBizFailure(String responseDescription, JSONObject data,
	// String flag) {
	// // TODO Auto-generated method stub
	//
	// }
	// }, Constant.SM_BASE_URL + Constant.CONFIRM_ORDER_DELIVERY, params);
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	// }

	private void remindOrder(String order_id) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ORDERCODE, order_id);
			OkHttpHelper okHttpHelper = new OkHttpHelper(context);
			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					// TODO Auto-generated method stub
					try {
						if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {
							T.showToast(context, context.getResources().getString(com.rs.mobile.wportal.R.string.success));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.ORDER_REMIND, params);

		} catch (Exception e) {

			L.e(e);

		}
	}
}
