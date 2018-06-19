
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.lang.reflect.Array;
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
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.MyCollectonAdapter;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.activity.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Request;

public class SmCollectionActivity extends BaseActivity {

	private List<ShoppingCart> list;

	private PullToRefreshListView listView;

	private List<String> listId;

	private List<String> listDiv;

	private List<String> listCustomCd;

	private MyCollectonAdapter adapter;

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

					finish();
				}
			});
			listView = (PullToRefreshListView) findViewById(com.rs.mobile.wportal.R.id.xlistView);

			text_delete = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_delete);
			text_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(listId != null && listId.size()>0){
						D.showDialog(SmCollectionActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
								getString(com.rs.mobile.wportal.R.string.common_text002), getString(com.rs.mobile.wportal.R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										D.alertDialog.dismiss();

										Map<String, String> params = new HashMap<String, String>();

										listId = new ArrayList<>();
										listDiv = new ArrayList<>();
										listCustomCd = new ArrayList<>();
										for (int i = 0; i < list.size(); i++) {
											L.d(list.toString());

											if (list.get(i).isChoosed()) {
												listId.add(list.get(i).getId().toString());
												listDiv.add(list.get(i).getDiv_code());
												listCustomCd.add(list.get(i).sale_custom_code);
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
												obj.put(C.KEY_JSON_FM_ITEM_CODE, listId.get(j));
												obj.put("div_code", listDiv.get(j));
												obj.put("sale_custom_code", listCustomCd.get(j));
												obj.put("custom_code", "");
//											obj.put(C.KEY_JSON_TOKEN, S.get(SmCollectionActivity.this, C.KEY_JSON_TOKEN));
//											arr.put(obj);
//											params.put(C.KEY_JSON_FM_ITEM_CODE, listId.get(j).toString());
//											params.put("div_code", listDiv.get(j));
//											params.put("sale_custom_code", list.get(j).sale_custom_code);
												GsonUtils.createGsonString(params);
												arr.put(obj);
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											params.put(C.KEY_JSON_TOKEN, S.get(SmCollectionActivity.this, C.KEY_JSON_TOKEN));
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
					}else{
						//개별적으로 체크된 상품이 있는지 확인한다
						for (int i = 0; i < list.size(); i++)
						{
							if (list.get(i).isChoosed()) {
								listId = new ArrayList<>();
								listDiv = new ArrayList<>();
								listCustomCd = new ArrayList<>();
								if (list.get(i).isChoosed()) {
									listId.add(list.get(i).getId().toString());
									listDiv.add(list.get(i).getDiv_code());
									listCustomCd.add(list.get(i).sale_custom_code);
								}
							}
						}

						if(listId == null || listId.size() == 0) {
							Toast.makeText(SmCollectionActivity.this, "상품을 선택해주세요.", Toast.LENGTH_SHORT).show();
						} else {

							D.showDialog(SmCollectionActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
									getString(com.rs.mobile.wportal.R.string.common_text002), getString(com.rs.mobile.wportal.R.string.common_text003),
									new OnClickListener() {

										@Override
										public void onClick(View v) {
									D.alertDialog.dismiss();

									Map<String, String> params = new HashMap<String, String>();

									listId = new ArrayList<>();
									listDiv = new ArrayList<>();
									listCustomCd = new ArrayList<>();
									for (int i = 0; i < list.size(); i++) {
										L.d(list.toString());

										if (list.get(i).isChoosed()) {
											listId.add(list.get(i).getId().toString());
											listDiv.add(list.get(i).getDiv_code());
											listCustomCd.add(list.get(i).sale_custom_code);
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
											obj.put(C.KEY_JSON_FM_ITEM_CODE, listId.get(j));
											obj.put("div_code", listDiv.get(j));
											obj.put("sale_custom_code", listCustomCd.get(j));
											obj.put("custom_code", "");
//											obj.put(C.KEY_JSON_TOKEN, S.get(SmCollectionActivity.this, C.KEY_JSON_TOKEN));
//											arr.put(obj);
//											params.put(C.KEY_JSON_FM_ITEM_CODE, listId.get(j).toString());
//											params.put("div_code", listDiv.get(j));
//											params.put("sale_custom_code", list.get(j).sale_custom_code);
											GsonUtils.createGsonString(params);
											arr.put(obj);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										params.put(C.KEY_JSON_TOKEN, S.get(SmCollectionActivity.this, C.KEY_JSON_TOKEN));
										params.put("jsonString", arr.toString());
										deleteFavourate(params);
									}

									listId.clear();
									listDiv.clear();
									listCustomCd.clear();
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
					}
				}
			});
			text_shoppingcart = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_shoppingcart);
			text_shoppingcart.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if(listId != null && listId.size() > 0){
						D.showDialog(SmCollectionActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
								getString(com.rs.mobile.wportal.R.string.common_text004), getString(com.rs.mobile.wportal.R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {

										D.alertDialog.dismiss();

										listId = new ArrayList<>();
										listDiv = new ArrayList<>();
										listCustomCd = new ArrayList<>();
										for (int i = 0; i < list.size(); i++) {
											L.d(list.toString());

											if (list.get(i).isChoosed()) {
												listId.add(list.get(i).getId().toString());
												listDiv.add(list.get(i).getDiv_code());
												listCustomCd.add(list.get(i).sale_custom_code);
											}

										}
										for (int j = 0; j < listId.size(); j++) {
											addToShopcart(listId.get(j), listDiv.get(j), listCustomCd.get(j));
										}
										listId.clear();
										listDiv.clear();
										listCustomCd.clear();
									}
								}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
									}
								});
					}else{
						//개별적으로 체크된 상품이 있는지 확인한다
						for (int i = 0; i < list.size(); i++)
						{
							if (list.get(i).isChoosed()) {
								listId = new ArrayList<>();
								listDiv = new ArrayList<>();
								listCustomCd = new ArrayList<>();
								if (list.get(i).isChoosed()) {
									listId.add(list.get(i).getId().toString());
									listDiv.add(list.get(i).getDiv_code());
									listCustomCd.add(list.get(i).sale_custom_code);
								}
							}
						}

						if(listId == null || listId.size() == 0) {
							Toast.makeText(SmCollectionActivity.this, "상품을 선택해주세요.", Toast.LENGTH_SHORT).show();
						} else {
							D.showDialog(SmCollectionActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
									getString(com.rs.mobile.wportal.R.string.common_text004), getString(com.rs.mobile.wportal.R.string.common_text003),
									new OnClickListener() {

										@Override
										public void onClick(View v) {

											D.alertDialog.dismiss();

											listId = new ArrayList<>();
											listDiv = new ArrayList<>();
											listCustomCd = new ArrayList<>();
											for (int i = 0; i < list.size(); i++) {
												L.d(list.toString());

												if (list.get(i).isChoosed()) {
													listId.add(list.get(i).getId().toString());
													listDiv.add(list.get(i).getDiv_code());
													listCustomCd.add(list.get(i).sale_custom_code);
												}

											}
											for (int j = 0; j < listId.size(); j++) {
												addToShopcart(listId.get(j), listDiv.get(j), listCustomCd.get(j));
											}
											listId.clear();
											listDiv.clear();
											listCustomCd.clear();
										}
								}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											D.alertDialog.dismiss();
										}
							});
						}
					}
				}
			});
			checkbox = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.checkbox);
			checkbox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					listId = new ArrayList<>();
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setChoosed(checkbox.isChecked());
						listId.add(list.get(i).getId());
					}

					adapter.notifyDataSetChanged();

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initData(){
		try {
			hideNoData();
			list = new ArrayList<ShoppingCart>();
			list.clear();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("appType", "6");
			params.put("pageIndex", "1");
			params.put("pageSize", "10");
			params.put("token", S.get(SmCollectionActivity.this, C.KEY_JSON_TOKEN));
			OkHttpHelper okhelper = new OkHttpHelper(SmCollectionActivity.this);
			okhelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					try {
						String id, name, imgurl, stock_unit, div_code, sale_custom_code;
						int num;
						float price;
						boolean isChoosed;
						JSONArray array = data.getJSONArray(C.KEY_JSON_DATA);
						if (array.length() == 0) {
							showNoData((String)getResources().getText(R.string.no_collection), new OnClickListener() {
								@Override
								public void onClick(View v) {
									initData();
								}
							});
						}else{
							showData();
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
						adapter = new MyCollectonAdapter(list, SmCollectionActivity.this, handle);
						listView.setAdapter(adapter);
					} catch (Exception e) {
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}
			}, Constant.SM_BASE_URL + Constant.GET_USERFAVORITE_LIST, params);
		} catch (Exception e) {
			L.e(e);
		}
	}

	private void deleteFavourate(Map<String, String> params) {

		try {
			OkHttpHelper okHttpHelper = new OkHttpHelper(SmCollectionActivity.this);
			okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					String status;
					try {
						status = data.getString(C.KEY_JSON_FM_STATUS);
						if (status.equals("1")) {
							t(data.get("message").toString());
							handle.sendMessage(handle.obtainMessage(0, "1"));
							Constant.SMMY_REFRESH = true;
						}
					} catch (Exception e) {
						L.e(e);
					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}
			}, Constant.SM_BASE_URL + Constant.DEL_USER_FAVOURITES, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void addToShopcart(String item_code, String div_code, String sale_custom_code) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
			params.put(C.KEY_JSON_FM_ITEM_QUANTITI, "1");
			params.put("div_code", div_code);
			params.put("sale_custom_code", sale_custom_code);
			params.put(C.KEY_JSON_TOKEN, S.get(SmCollectionActivity.this, C.KEY_JSON_TOKEN));
			OkHttpHelper okHttpHelper = new OkHttpHelper(SmCollectionActivity.this);
			okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
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

				}
			}, Constant.SM_BASE_URL + Constant.ADD_USER_SHOPCART, params);

		} catch (Exception e) {

			L.e(e);

		}

	}
}
