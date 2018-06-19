
package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.dp.DpMyCollectonAdapter;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.activity.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class DpCollectionActivity extends BaseActivity {

	private List<ShoppingCart> list;

	private PullToRefreshListView listView;

	private List<String> listId;

	private List<String> listDiv;

	private DpMyCollectonAdapter adapter;

	private TextView text_delete, text_shoppingcart;

	private CheckBox checkbox;

	private LinearLayout close_btn;

	private Handler handle = new Handler() {

		public void handleMessage(android.os.Message msg) {

			super.handleMessage(msg);

			try {

				if (msg.what == 0) {

					boolean isChecked = true;

					for (int i = 0; i < list.size(); i++) {

						if (list.get(i).isChoosed() == false) {

							isChecked = false;

							break;

						}

					}
					if (list.size() == 0) {
						isChecked = false;
					}
					checkbox.setChecked(isChecked);

				}

			} catch (Exception e) {

				L.e(e);

			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_collection);
		initView();
		initData();
	}

	private void initView() {

		try {

			close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					finish();
				}
			});
			listView = (PullToRefreshListView) findViewById(com.rs.mobile.wportal.R.id.xlistView);

			text_delete = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_delete);
			text_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub

					// TODO Auto-generated method stub
					D.showDialog(DpCollectionActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
							getString(com.rs.mobile.wportal.R.string.common_text002), getString(com.rs.mobile.wportal.R.string.common_text003),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();

									// TODO Auto-generated method stub
									Map<String, String> params = new HashMap<String, String>();

									listId = new ArrayList<String>();
									listDiv = new ArrayList<>();
									for (int i = 0; i < list.size(); i++) {
										L.d(list.toString());

										if (list.get(i).isChoosed()) {
											listId.add(list.get(i).getId().toString());
											listDiv.add(list.get(i).getDiv_code());
											list.remove(i);

											i--;
										}

									}
									if (listId.size() == 0) {
										t(getString(com.rs.mobile.wportal.R.string.dp_text_034));
										return;
									}
									JSONArray arr = new JSONArray();
									for (int j = 0; j < listId.size(); j++) {
										JSONObject obj = new JSONObject();

										try {
											obj.put(C.KEY_JSON_FM_ITEM_CODE, listId.get(j).toString());
											obj.put("div_code", listDiv.get(j));
											arr.put(obj);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										params.put("jsonString", arr.toString());
										deleteFavourate(params);
									}

									adapter.notifyDataSetChanged();

								}
							}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();
								}
							});

				}
			});
			text_shoppingcart = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_shoppingcart);
			text_shoppingcart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub

					// TODO Auto-generated method stub
					D.showDialog(DpCollectionActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
							getString(com.rs.mobile.wportal.R.string.common_text004), getString(com.rs.mobile.wportal.R.string.common_text003),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();
									// TODO Auto-generated method stub
									listId = new ArrayList<>();
									listDiv = new ArrayList<>();
									for (int i = 0; i < list.size(); i++) {
										L.d(list.toString());

										if (list.get(i).isChoosed()) {
											listId.add(list.get(i).getId().toString());
											listDiv.add(list.get(i).getDiv_code());
										}

									}
									for (int j = 0; j < listId.size(); j++) {
										addToShopcart(listId.get(j), listDiv.get(j));
									}
								}
							}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();
								}
							});

				}
			});
			checkbox = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.checkbox);
			checkbox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					for (int i = 0; i < list.size(); i++) {

						list.get(i).setChoosed(checkbox.isChecked());

					}

					adapter.notifyDataSetChanged();

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initData() {

		try {
			hideNoData();
			list = new ArrayList<ShoppingCart>();
			list.clear();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("appType", "8");
			OkHttpHelper okhelper = new OkHttpHelper(DpCollectionActivity.this);
			okhelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					// TODO Auto-generated method stub
					try {
						String id, name, imgurl, stock_unit, div_code, sale_custom_code;
						int num;
						float price;
						boolean isChoosed;
						JSONArray array = data.getJSONArray(C.KEY_JSON_DATA);
						if (array.length() == 0) {
							showNoData("您还没有收藏哦！", null);
						}
						for (int i = 0; i < array.length(); i++) {

							JSONObject jsonObject = new JSONObject(array.get(i).toString());
							id = jsonObject.get("item_code").toString();
							name = jsonObject.getString("item_name");
							price = Float.parseFloat(jsonObject.getString("price"));
							imgurl = jsonObject.getString("img_path");
							stock_unit = jsonObject.getString("stock_unit");
							num = 1;
							div_code = jsonObject.getString("div_code");
							sale_custom_code = jsonObject.getString("sale_custom_code");
							isChoosed = false;
							ShoppingCart shoppingCart = new ShoppingCart(id, name, price, imgurl, num, isChoosed,
									stock_unit, div_code, sale_custom_code);
							list.add(shoppingCart);
						}
						adapter = new DpMyCollectonAdapter(list, DpCollectionActivity.this, handle);
						listView.setAdapter(adapter);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.GET_USERFAVORITE_LIST, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void deleteFavourate(Map<String, String> params) {

		try {

			OkHttpHelper okHttpHelper = new OkHttpHelper(DpCollectionActivity.this);
			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					// TODO Auto-generated method stub
					String status;
					try {
						status = data.getString(C.KEY_JSON_FM_STATUS);
						if (status.equals("1")) {
							t(data.get("message").toString());
							handle.sendMessage(handle.obtainMessage(0, "1"));
							Constant.SMMY_REFRESH = true;
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
			}, Constant.SM_BASE_URL + Constant.DEL_USER_FAVOURITES, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void addToShopcart(String item_code, String div_code) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
			params.put(C.KEY_JSON_FM_ITEM_QUANTITI, "1");
			params.put("div_code", div_code);
			params.put(C.KEY_JSON_TOKEN, S.getShare(DpCollectionActivity.this, C.KEY_JSON_TOKEN, ""));
			OkHttpHelper okHttpHelper = new OkHttpHelper(DpCollectionActivity.this);
			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					// TODO Auto-generated method stub
					try {

						if (data.getString(C.KEY_JSON_FM_STATUS).equals("1")) {
							t(data.getString("message"));
						}

					} catch (Exception e) {

						L.e(e);

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_DP1 + Constant.ADD_USER_SHOPCART, params);

		} catch (Exception e) {

			L.e(e);

		}

	}
}
