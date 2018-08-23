
package com.rs.mobile.wportal.adapter.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.CommonUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.sm.SmAddAddressActivity;
import com.rs.mobile.wportal.activity.sm.SmAddAddressActivityCN;
import com.rs.mobile.wportal.activity.sm.SmAddressActivity;
import com.rs.mobile.wportal.biz.Address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class AddressAdapter extends BaseAdapter {

	private List<Address> listdata;

	private Context context;

	private Handler handler;

	private int flag = 14;

	static class ViewHolder {

		public CheckBox checkBox;

		public TextView textView_name, textView_phone, textView_address, textView_edit, textView_delete;

		public TextView textView001, text_deli;

		public RelativeLayout rela_edit;

		public LinearLayout chile_layout;
	}

	public AddressAdapter(List<Address> listdata, Context context, Handler handler) {
		this.listdata = listdata;
		this.context = context;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder itemView = null;
		ViewGroup viewGroup = (ViewGroup) convertView;

		if (convertView == null) {
			itemView = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_address, parent, false);
			itemView.textView_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
			itemView.textView_phone = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_phone);
			itemView.textView_address = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_address);
			itemView.checkBox = (CheckBox) convertView.findViewById(com.rs.mobile.wportal.R.id.checkBox);
			itemView.textView_edit = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_edit);
			itemView.textView_delete = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_delete);
			itemView.rela_edit = (RelativeLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.rela_edit);
//			itemView.text_deli = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_deli);
			itemView.chile_layout = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.chile_layout);
			convertView.setTag(itemView);
		} else {
			itemView = (ViewHolder) convertView.getTag();
		}
		itemView.textView_name.setText(listdata.get(position).getName());
		itemView.textView_phone.setText(listdata.get(position).getPhone());
		itemView.textView_address.setText(listdata.get(position).zipName + listdata.get(position).getAddress());
		itemView.textView_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				D.showDialog(context, -1, context.getString(com.rs.mobile.wportal.R.string.common_text001),
						context.getString(com.rs.mobile.wportal.R.string.common_text002), context.getString(com.rs.mobile.wportal.R.string.common_text003),
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								D.alertDialog.dismiss();
								delUserAddress(listdata.get(position).getId(), position);
							}
						}, context.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

							@Override
							public void onClick(View v) {
								D.alertDialog.dismiss();
							}
						});

			}
		});
		itemView.textView_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Bundle bundle = new Bundle();
				bundle.putString("activity", "edit");
				bundle.putString(C.KEY_JSON_FM_ID, listdata.get(position).getId());
				bundle.putString(C.KEY_JSON_FM_MOBILE, listdata.get(position).getPhone());
				bundle.putString(C.KEY_JSON_FM_LOCATION, listdata.get(position).getPosition());
				bundle.putString(C.KEY_JSON_FM_ADDRESS, listdata.get(position).getAddress());
				bundle.putBoolean(C.KEY_JSON_FM_HASDEFAULT, listdata.get(position).isSleceted());
				bundle.putString(C.KEY_JSON_FM_LATITUDE, listdata.get(position).getLatitude());
				bundle.putString(C.KEY_JSON_FM_LONGITUDE, listdata.get(position).getLongitude());
				bundle.putString(C.KEY_JSON_FM_NAME, listdata.get(position).getName());
				bundle.putString("zipCode", listdata.get(position).getZipCode());
				bundle.putString("zipName", listdata.get(position).zipName);
				if (CommonUtil.isZh(context)){
					PageUtil.jumpTo(context, SmAddAddressActivityCN.class, bundle);
				}else {
					PageUtil.jumpTo(context, SmAddAddressActivity.class, bundle);
				}

			}
		});
		if (listdata.get(position).isSleceted()) {
			itemView.checkBox.setChecked(true);
		} else {
			itemView.checkBox.setChecked(false);

		}

		final boolean isChecked = itemView.checkBox.isChecked();

		itemView.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				listdata.get(position).setSleceted(!isChecked);

				if (!isChecked) {

					handler.sendMessage(handler.obtainMessage(flag, position));
					setDefaultAddress(listdata.get(position).getId().toString());


					try {

						SmAddressActivity activity = (SmAddressActivity) context;

						if (activity.typeSelect == true) {

							Address address = listdata.get(position);

							Intent result = new Intent();

							result.putExtra("name", address.getName());

							result.putExtra("mobile", address.getPhone());

							result.putExtra("address", address.getPosition() + " " + address.getAddress());

							result.putExtra("id", address.getId());

							activity.setResult(Activity.RESULT_OK, result);

							activity.finish();

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				notifyDataSetChanged();

			}
		});
		boolean hasDelivery = listdata.get(position).isHasDelivery();
		if (hasDelivery) {
			itemView.text_deli.setVisibility(View.GONE);
			convertView.setEnabled(true);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					try {

						SmAddressActivity activity = (SmAddressActivity) context;

						if (activity.typeSelect == true) {

							Address address = listdata.get(position);

							Intent result = new Intent();

							result.putExtra("name", address.getName());

							result.putExtra("mobile", address.getPhone());

							result.putExtra("address", address.getPosition() + " " + address.getAddress());

							result.putExtra("id", address.getId());

							result.putExtra("hasDelivery", true);

							activity.setResult(Activity.RESULT_OK, result);

							activity.finish();

						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

		} else {
			convertView.setEnabled(false);
//			itemView.text_deli.setVisibility(View.VISIBLE);

			// itemView.text_deli.setLayoutParams(new
			// RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
			// LayoutParams.MATCH_PARENT));
			// new Handler().postDelayed(new Runnable() {
			//
			// @Override
			// public void run() {
			// // TODO Auto-generated method stub
			//
			// itemView.text_deli.setLayoutParams(new
			// RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
			// LayoutParams.MATCH_PARENT));
			//
			// }
			// }, 100);

		}
		try {

			SmAddressActivity activity = (SmAddressActivity) context;

			if (activity.typeSelect == true) {

				itemView.rela_edit.setVisibility(View.GONE);

			}

		} catch (Exception e) {

			L.e(e);

		}

		//
		return convertView;
	}

	private void delUserAddress(String id, final int position) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put("lang_type", AppConfig.LANG_TYPE);
			params.put("del_seq_no", id);
			params.put("token", S.get(context, C.KEY_JSON_TOKEN));
			params.put("custom_code", S.get(context, C.KEY_JSON_CUSTOM_CODE));
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

						String status = data.getString(C.KEY_JSON_FM_STATUS);

						if (status.equals("1")) {

							listdata.remove(position);

							notifyDataSetChanged();

							T.showToast(context, data.getString("message"));

							return;

						}

					} catch (Exception e) {
						// TODO: handle exception
						L.e(e);
					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.XS_BASE_URL + "MyInfo/MyInfoAddressDel", params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void setDefaultAddress(String id) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put("id", id);
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

						String status = data.getString(C.KEY_JSON_FM_STATUS);

						if (status.equals("1")) {

							T.showToast(context, data.getString("message"));

						}

					} catch (Exception e) {
						// TODO: handle exception
						L.e(e);
					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.SET_DEFAULT_SHOPADDRESS, params);

		} catch (Exception e) {

			L.e(e);

		}
	}
}
