package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.ShoppingCartParentAdapter;
import com.rs.mobile.wportal.biz.ShoppingCartParent;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class DpShoppingCartActivity extends BaseActivity {

	private ListView listview;

	private static List<ShoppingCart> list;

	private List<ShoppingCartParent> listParent;

	private LinearLayout linear_back, linear_exchange;

	private CheckBox checkBox;

	private TextView textview;

	private TextView textview001, textView_complete, textView_edit, textView_002, textView_add, textView_delete;

	private ShoppingCartParentAdapter adapter;

	private LinearLayout linear_edit;

	public String Goods = "goods";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {

			setContentView(R.layout.activity_sm_shoppingcart);
			initView();

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	protected void onPause() {

		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	protected void onResume() {

		// TODO Auto-generated method stub
		super.onResume();
		initData();
		textView_002.setText(getResources().getString(R.string.rmb) + "0");
		checkBox.setChecked(false);
	}

	private Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			try {
				float price = getTotalPrice();
				if (msg.what == 10) { // 更改选中商品的总价格

					if (price > 0) {
						textView_002.setText(getResources().getString(R.string.rmb) + price);
					} else {
						textView_002.setText(getResources().getString(R.string.rmb) + "0");
					}
				} else if (msg.what == 11) {

					if (price > 0) {
						textView_002.setText(getResources().getString(R.string.rmb) + price);
					} else {
						textView_002.setText(getResources().getString(R.string.rmb) + "0");
					}

					boolean isChecked = true;

					for (int i = 0; i < list.size(); i++) {

						if (list.get(i).isChoosed() == false) {

							isChecked = false;

							break;

						}

					}
					if (list.size() == 0) {
						isChecked = false;
						textView_complete.setVisibility(View.GONE);
						textview001.setVisibility(View.VISIBLE);
						textView_002.setVisibility(View.VISIBLE);
						linear_edit.setVisibility(View.GONE);
						textView_edit.setVisibility(View.VISIBLE);
						textview.setVisibility(View.VISIBLE);
					}
					checkBox.setChecked(isChecked);

				}
				adapter.notifyDataSetChanged();
			} catch (Exception e) {

				L.e(e);

			}
		}
	};

	private void initData() {

		getMyshoppingCartList();
	}

	private void initView() {

		try {

			linear_back = (LinearLayout) findViewById(R.id.close_btn);
			linear_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					finish();
				}
			});

			linear_exchange = (LinearLayout) findViewById(R.id.linear_exchange);
			linear_exchange.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					t("服务准备中");
				}
			});
			listview = (ListView) findViewById(R.id.listview);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
					// TODO Auto-generated method stub

					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_ITEM_CODE, list.get(position).getId());
					bundle.putString(C.KEY_DIV_CODE, list.get(position).getDiv_code());
					PageUtil.jumpTo(DpShoppingCartActivity.this, DpGoodsDetailActivity.class, bundle);

				}
			});
			checkBox = (CheckBox) findViewById(R.id.checkbox);
			checkBox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					List<Integer> nums = new ArrayList<Integer>();// 列表中商品数量

					for (int i = 0; i < list.size(); i++) {
						int num = list.get(i).getNum();
						float price = list.get(i).getprice();
						nums.add(num);
					}
					if (checkBox.isChecked()) {
						for (int i = 0; i < list.size(); i++) {
							list.get(i).setChoosed(true);
						}
						for (int j = 0; j < listParent.size(); j++) {
							listParent.get(j).setChoosed_parent(true);
						}
						adapter.notifyDataSetChanged();

						textView_002.setText(getResources().getString(R.string.rmb) + getTotalPrice());
					} else {
						for (int i = 0; i < list.size(); i++) {
							list.get(i).setChoosed(false);
						}
						for (int j = 0; j < listParent.size(); j++) {
							listParent.get(j).setChoosed_parent(false);
						}
						adapter.notifyDataSetChanged();
						textView_002.setText(getResources().getString(R.string.rmb) + "0");
					}
				}
			});

			textView_002 = (TextView) findViewById(R.id.text_totoal002);

			textview001 = (TextView) findViewById(R.id.text_totoal001);

			textview = (TextView) findViewById(R.id.text_to_buy);
			textview.setOnClickListener(new OnClickListener() {

				@SuppressWarnings("unchecked")
				@Override
				public void onClick(View v) {

					// TODO Auto-generatet
					ArrayList<ShoppingCart> list001 = new ArrayList<ShoppingCart>();
					ArrayList<ShoppingCart> list002 = new ArrayList<ShoppingCart>();
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).isChoosed()) {
							list001.add(list.get(i));
						}
					}
					Bundle bundle = new Bundle();
					L.d("shop" + list001.toString());
					if (list001.size() == 0) {
						t(getResources().getString(R.string.sm_text_plz_choosegoods));
						return;
					}
					bundle.putParcelableArrayList(Goods, list001);
					bundle.putParcelableArrayList("attachment", list002);
					bundle.putString("total", textView_002.getText().toString());
					bundle.putString("onCartProcess", "true");
					PageUtil.jumpTo(DpShoppingCartActivity.this, DpConfirmActivity.class, bundle);

				}
			});
			textView_add = (TextView) findViewById(R.id.add_tofavorate);
			textView_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// 1 // t(" merry chrismas");
					ArrayList<ShoppingCart> list001 = new ArrayList<ShoppingCart>();

					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).isChoosed()) {
							list001.add(list.get(i));
						}
					}
					HashMap<String, String> params;
					if (list001.size() == 0 || list001 == null) {
						t(getResources().getString(R.string.sm_text_plz_choose_good));
					} else {
						for (int j = 0; j < list001.size(); j++) {
							params = new HashMap<>();
							params.put(C.KEY_JSON_FM_ITEM_CODE, list001.get(j).getId().toString());
							params.put("div_code", list001.get(j).getDiv_code());
							deleteFavourate(params);
						}
					}
				}
			});

			textView_complete = (TextView) findViewById(R.id.texttop_complete);
			textView_complete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					textView_complete.setVisibility(View.GONE);
					textview001.setVisibility(View.VISIBLE);
					textView_002.setVisibility(View.VISIBLE);
					linear_edit.setVisibility(View.GONE);
					textView_edit.setVisibility(View.VISIBLE);
					textview.setVisibility(View.VISIBLE);
				}
			});

			textView_edit = (TextView) findViewById(R.id.texttop_edit);
			textView_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					textview001.setVisibility(View.GONE);
					textView_002.setVisibility(View.GONE);
					linear_edit.setVisibility(View.VISIBLE);
					textView_complete.setVisibility(View.VISIBLE);
					textview.setVisibility(View.GONE);
					textView_edit.setVisibility(View.GONE);
				}
			});

			linear_edit = (LinearLayout) findViewById(R.id.linear_edit);
			textView_delete = (TextView) findViewById(R.id.delete);
			textView_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// TODO Auto-generated method stub

					// TODO Auto-generated method stub
					D.showDialog(DpShoppingCartActivity.this, -1, getString(R.string.common_text001),
							getString(R.string.common_text002), getString(R.string.common_text003),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();
									deleteShopCart();
								}
							}, getString(R.string.cancel), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();
								}
							});

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void getMyshoppingCartList() {

		try {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_TOKEN, S.getShare(DpShoppingCartActivity.this, C.KEY_JSON_TOKEN, ""));
			params.put("appType", "8");
			OkHttpHelper okHttpHelper = new OkHttpHelper(DpShoppingCartActivity.this);
			okHttpHelper.addSMPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					// TODO Auto-generated method stub
					String item_name, image_url, item_code, stock_unit, div_code;
					int item_quantity;
					float item_price;

					list = new ArrayList<ShoppingCart>();
					listParent = new ArrayList<>();
					ShoppingCart shoppingCart;

					try {

						JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);

						JSONArray itemArr;

						JSONObject object;

						for (int j = 0; j < arr.length(); j++) {
							hideNoData();
							ShoppingCartParent shoppingCartParent;
							List<ShoppingCart> shoppingCarts = new ArrayList<>();
							String div_code1 = new JSONObject(arr.get(j).toString()).getString("div_code");
							String div_name1 = new JSONObject(arr.get(j).toString()).getString("div_name");
							itemArr = new JSONObject(arr.get(j).toString()).getJSONArray("items");

							for (int i = 0; i < itemArr.length(); i++) {

								object = new JSONObject(itemArr.get(i).toString());

								item_name = object.getString("item_name");

								item_code = object.get("item_code").toString();

								stock_unit = object.getString("stock_unit");

								item_quantity = object.getInt("item_quantity");

								item_price = Float.parseFloat(object.get("item_price").toString());

								image_url = object.get("image_url").toString();

								div_code = object.getString("div_code");

								shoppingCart = new ShoppingCart(item_code, item_name, item_price, image_url,
										item_quantity, false, stock_unit, div_code, "");

								list.add(shoppingCart);
								shoppingCarts.add(shoppingCart);

							}
							shoppingCartParent = new ShoppingCartParent(div_code1, div_name1, false, shoppingCarts);
							listParent.add(shoppingCartParent);
						}

						adapter = new ShoppingCartParentAdapter(listParent, handler, DpShoppingCartActivity.this, 8);

						listview.setAdapter(adapter);
						checkStatus();
						textView_002.setText(getResources().getString(R.string.rmb) + "0");

					} catch (Exception e) {
						// TODO Auto-generated catch block
						L.e(e);

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.GET_USER_SHOPINGCARTLIST, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	public static void upDateShopcart(Context context) {

		JSONArray arr = new JSONArray();

		try {

			for (int i = 0; i < list.size(); i++) {

				JSONObject jsonObject = new JSONObject();

				jsonObject.put("item_code", list.get(i).getId());

				jsonObject.put("item_quantity", "" + list.get(i).getNum());

				jsonObject.put("div_code", list.get(i).getDiv_code());

				arr.put(jsonObject);

			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("arrCardModel", arr.toString());
			params.put(C.KEY_JSON_TOKEN, S.getShare(context, C.KEY_JSON_TOKEN, ""));
			OkHttpHelper okHttpHelper = new OkHttpHelper(context);
			okHttpHelper.addPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.UPDATE_USERSHOPCART, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
	}

	public void deleteShopCart() {

		String p = "";
		String d = "";

		try {
			if (list.size() == 0) {
				t(getString(R.string.dp_text_034));
				return;
			}
			for (int i = 0; i < list.size(); i++) {

				if (list.get(i).isChoosed() == true) {

					if (i == 0) {

						p = p + list.get(i).getId();
						d = d + list.get(i).getDiv_code();

					} else {

						p = p + "," + list.get(i).getId();
						d = d + "," + list.get(i).getDiv_code();

					}

				}

			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("arrItemCode", p);
			params.put("arrDivCode", d);
			params.put(C.KEY_JSON_TOKEN, S.getShare(DpShoppingCartActivity.this, C.KEY_JSON_TOKEN, ""));
			OkHttpHelper okHttpHelper = new OkHttpHelper(DpShoppingCartActivity.this);

			okHttpHelper.addSMPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

					// for (int i = 0; i < list.size(); i++) {
					// if (list.get(i).isChoosed()) {
					// list.remove(i);
					// i--;
					// }
					//
					// }

					initData();

					try {
						t(data.getString("message"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.DELUSERSHOPCART, params);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
	}

	private void deleteFavourate(Map<String, String> params) {

		try {

			OkHttpHelper okHttpHelper = new OkHttpHelper(DpShoppingCartActivity.this);
			okHttpHelper.addSMPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					String status;
					try {
						status = data.getString(C.KEY_JSON_FM_STATUS);
						// if (status.equals("1")) {
						// t(getResources().getString(R.string.complete));
						// }
					} catch (Exception e) {
						L.e(e);
					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}
			}, Constant.SM_BASE_URL + Constant.ADD_USER_FAVORITES, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public float getTotalPrice() {

		ShoppingCart bean = null;
		float totalPrice = 0;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);
			// BigDecimal bigDecimal=new bi
			if (bean.isChoosed()) {
				totalPrice += bean.getNum() * bean.getprice();
			}
		}
		return (float) (Math.round(totalPrice * 100)) / 100;
	}

	private void checkStatus() {
		if (list.size() == 0) {
			showNoData(R.drawable.icon_nogoods_shoppingcart, "购物车为空", new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
			textView_complete.setVisibility(View.GONE);
			textview001.setVisibility(View.VISIBLE);
			textView_002.setVisibility(View.VISIBLE);
			linear_edit.setVisibility(View.GONE);
			textView_edit.setVisibility(View.VISIBLE);
			textview.setVisibility(View.VISIBLE);
			checkBox.setChecked(false);
			textView_002.setText(getResources().getString(R.string.rmb) + "0");
		}
	}
}
