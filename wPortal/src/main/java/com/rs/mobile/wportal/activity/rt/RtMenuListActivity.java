package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.rt.CartListAdapter;
import com.rs.mobile.wportal.adapter.rt.MenuCategoryListAdapter;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.adapter.rt.MenuListAdapter;
import com.rs.mobile.wportal.adapter.rt.MenuListAdapter.MenuChangeListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import okhttp3.Request;

/**
 * 
 * @author ZhaoYun
 * @date 2017-3-13
 */
public class RtMenuListActivity extends BaseActivity {

	private String currentPage = "1";

	private String nextPage = "1";

	private Toolbar toolbar;
	private TextView tv_title;
	private LinearLayout iv_back;

	private TextView categoryTextView;
	
	private ListView categoryListView;
	private ListView menuListView;
	
	private MenuCategoryListAdapter menuCategoryListAdapter;
	
	private MenuListAdapter menuListAdapter;
	
	private CartListAdapter cartListAdapter;

	private PopupWindow menu;
	
	//메뉴 갯수
	private int menuCount = 0;
	
	//총 금액
	private double totalPrice;
	
	private ImageView cart_icon;
	
	private TextView cart_count_text_view;
	
	private TextView cart_state_text_view;
	
	private TextView pay_btn;
	
	private LinearLayout cart_area;
	
	private TextView cart_count_text_view_2;
	
	private TextView delete_all_btn;;
	
	private ListView cart_list_view;
	
	private String reserveID;
	
	private String restaurantCode;

	private Receiver receiver;
	
	//0 : 같이 먹기
	private String from;
	
	//같이 먹기 그룹아이디
	private String groupId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		receiver = new Receiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(RtMainActivity.ACTION_RECEIVE_RT_FINISH);
		registerReceiver(receiver, filter);
		
		from = getIntent().getStringExtra("from");
		
		groupId = getIntent().getStringExtra("groupId");
		
		reserveID = getIntent().getStringExtra("reserveID");
		
		restaurantCode = getIntent().getStringExtra("restaurantCode");
		
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_menu_list);
		initToolbar();
		initViews();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		initDates();
		
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initViews() {
		
		try {
			
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.rt_menu));
			
			iv_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			
			categoryTextView = (TextView)findViewById(com.rs.mobile.wportal.R.id.category_text_view);
			
			categoryListView = (ListView) findViewById(com.rs.mobile.wportal.R.id.left_list);
	
			categoryListView.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					// TODO Auto-generated method stub
					
					try {
					
						JSONObject item = (JSONObject) menuCategoryListAdapter.getItem(position);
						
						menuListView.setSelection(item.getInt("postion"));
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			menuListView = (ListView) findViewById(com.rs.mobile.wportal.R.id.right_list);
			menuListView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					
					try {
					
						Log.d("onScroll", firstVisibleItem + " / " + visibleItemCount + " / " + totalItemCount);
						
						if (menuListAdapter != null) {
							
							JSONObject item = (JSONObject) menuListAdapter.getItem(firstVisibleItem);
							
							categoryTextView.setText(item.getString("groupName"));
							
							menuCategoryListAdapter.setSelectedPosition(item.getInt("parentPostion"));
							
						}
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			cart_icon = (ImageView)findViewById(com.rs.mobile.wportal.R.id.cart_icon);
			cart_icon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					drawCart();
					
				}
			});
			
			cart_count_text_view = (TextView)findViewById(com.rs.mobile.wportal.R.id.cart_count_text_view);
			
			cart_state_text_view = (TextView)findViewById(com.rs.mobile.wportal.R.id.cart_state_text_view);
			
			pay_btn = (TextView)findViewById(com.rs.mobile.wportal.R.id.pay_btn);
			pay_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
	
						JSONArray orgArr = menuListAdapter.getListItems();

						JSONArray arr = new JSONArray();
						
						for (int j = 0; j < orgArr.length(); j++) {
							
							JSONObject item = orgArr.getJSONObject(j);
							
							if (item.getString("type").equals("menu")) {
							
								int count = item.getInt("count");
								
								if (count > 0) {
				
									arr.put(item);
									
								}
								
							}
							
						}
						
						if (arr == null || arr.length() == 0) {

							return;
							
						}
						
						if (from != null && from.equals("0")) {
							
							//같이 먹기
							addGroupOrder(arr);
							
						} else if (reserveID == null || reserveID.equals("")) {
							
							Intent i = new Intent(RtMenuListActivity.this,
									RtReserveSelectActivity.class);
	
							i.putExtra("cartArr", arr.toString());
							i.putExtra("restaurantCode", restaurantCode);
							startActivity(i);
							
						} else {
							//결제
							
							final JSONArray payArr = arr;
							
							showDialog(getString(com.rs.mobile.wportal.R.string.rt_start_payment), getString(com.rs.mobile.wportal.R.string.rt_msg_payment), getString(com.rs.mobile.wportal.R.string.rt_start_payment),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
						
										D.alertDialog.dismiss();
										
										addOrder(payArr);
							
								}
							}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {
		
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();
								}
							});
							
							
						}
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			cart_area = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.cart_area);
			cart_area.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
					
						cart_area.setVisibility(View.GONE);
						
						cart_icon.setVisibility(View.VISIBLE);
						
						cart_count_text_view.setVisibility(View.VISIBLE);
						
						drawTotalInfomation();
						
						JSONArray cartArr = cartListAdapter.getListItems();
						
						JSONArray menuArr = menuListAdapter.getListItems();
						
						for (int i = 0; i < cartArr.length(); i++) {
							
							String itemCode = cartArr.getJSONObject(i).getString("itemCode");
							
							int count  = cartArr.getJSONObject(i).getInt("count");
							
							for (int j = 0; j < menuArr.length(); j++) {
								
								JSONObject item = menuArr.getJSONObject(j);
								
								if (item.getString("type").equals("menu")) {
								
									String menuItemCode = item.getString("itemCode");
									
									if (itemCode.equals(menuItemCode)) {
										
										item.put("count", count);
										
										menuArr.put(j, item);
										
										break;
										
									}
								
								}
								
							}
							
						}
						
						menuListAdapter.setListItems(menuArr);
						
						menuListAdapter.notifyDataSetChanged();
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			cart_count_text_view_2 = (TextView)findViewById(com.rs.mobile.wportal.R.id.cart_count_text_view_2);
			
			delete_all_btn = (TextView)findViewById(com.rs.mobile.wportal.R.id.delete_all_btn);
			delete_all_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					cart_area.setVisibility(View.GONE);
					
					cart_icon.setVisibility(View.VISIBLE);
					
					menuListAdapter.removeAllCart();
					
					drawTotalInfomation();
					
				}
			});

			cart_list_view = (ListView)findViewById(com.rs.mobile.wportal.R.id.cart_list_view);

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void initDates() {

		try {
		
			OkHttpHelper helper = new OkHttpHelper(RtMenuListActivity.this);
	
			HashMap<String, String> params = new HashMap<String, String>();
	
			params.put("", "");
	
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
	
					try {
	
						L.d(all_data);
	
						String status = data.getString("status");
	
						if (status != null && status.equals("1")) {
	
							data = data.getJSONObject("data");
	
							JSONArray orgMenuList = data.getJSONArray("Menu");
	
							JSONArray menuList = new JSONArray();
							
							JSONArray categoryList = new JSONArray();
							
							/*
							 * 원본 리스트를 가져와서 레벨을 한개로 조정한다
							 * 메뉴 리스트에 카테고리와 메뉴를 같은 레벨로 만들어 화면에 나타나도록 수정한다
							 */
							for (int i = 0; i < orgMenuList.length(); i++) {
								
								//카테고리 정보를 가져와 하위 리스트를 삭제 후에 "type", "category"를 추가 한다
								JSONObject parentObj = orgMenuList.getJSONObject(i);
								
								JSONArray childList = parentObj.getJSONArray("MenuList");
								
								parentObj.put("type", "category");
								
								parentObj.remove("MenuList");
								
								parentObj.put("postion", menuList.length());
								
								parentObj.put("parentPostion", categoryList.length());
								
								//메뉴리스트에 추가
								menuList.put(parentObj);
								
								//카테고리리스트에 추가
								categoryList.put(parentObj);
								
								//하위 메뉴를 추가
								for (int j = 0; j < childList.length(); j++) { 
								
									JSONObject childObj = childList.getJSONObject(j);
								
									childObj.put("parentPostion", categoryList.length() - 1);
									
									childObj.put("type", "menu");
									
									childObj.put("count", 0);
									
									//메뉴리스트에 추가
									menuList.put(childObj);
									
								}
								
							}
							
							//이전 메뉴복원
							if (menuListAdapter != null) {
								
								JSONArray orgArr = menuListAdapter.getListItems();
	
								for (int j = 0; j < orgArr.length(); j++) {
									
									JSONObject item = orgArr.getJSONObject(j);
									
									if (item.getString("type").equals("menu")) {
									
										int count = item.getInt("count");
										
										if (count > 0) {
						
											menuList.getJSONObject(j).put("count", "" + count);
											
										}
										
									}
									
								}
								
								drawTotalInfomation();
								
							}
							
							menuCategoryListAdapter = new MenuCategoryListAdapter(RtMenuListActivity.this, categoryList);
							
							menuListAdapter = new MenuListAdapter(RtMenuListActivity.this, menuList, new MenuChangeListener() {
								
								@Override
								public void onChange(boolean add) {
									// TODO Auto-generated method stub
									
									drawTotalInfomation();
									
								}
							});
							
							categoryListView.setAdapter(menuCategoryListAdapter);
							
							menuListView.setAdapter(menuListAdapter);
	
						}
	
					} catch (Exception e) {
	
						L.e(e);
	
					}
	
				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
				}
			}, Constant.BASE_URL_RT + Constant.RT_MENU_LIST + getIntent().getStringExtra("restaurantCode"), params);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	public void onBack() {
		finish();
	}

	@Override
	protected void onDestroy() {
		
		unregisterReceiver(receiver);
		
		if (menu != null && menu.isShowing()) {
			menu.dismiss();
		}
		super.onDestroy();
	}
	
	public void drawTotalInfomation() {
		// TODO Auto-generated method stub
		
		try {
		
			menuCount = 0;
			
			totalPrice = 0;

			JSONArray arr = menuListAdapter.getListItems();

			for (int i = 0; i < arr.length(); i++) {
				
				JSONObject item = arr.getJSONObject(i);
				
				int count = 0;
				
				if (item.getString("type").equals("menu")) {
				
					count = item.getInt("count");
					
					double price = Double.parseDouble(arr.getJSONObject(i).getString("itemAmount"));
				
					//총 금액
					totalPrice = totalPrice + (count * price);
					
				}
				
				//총 갯수
				menuCount = menuCount + count;

			}
			
			cart_count_text_view.setText("" + menuCount);
			
			if (menuCount > 0) {
				
				cart_count_text_view.setVisibility(View.VISIBLE);
				
				cart_icon.setImageResource(com.rs.mobile.wportal.R.drawable.icon_gwcs);
				
				cart_state_text_view.setText(getString(com.rs.mobile.wportal.R.string.rt_sum) + totalPrice);

				cart_count_text_view.setText("" + menuCount);
				
				pay_btn.setBackgroundColor(Color.parseColor("#ed3e42"));
				
			} else {
			
				cart_count_text_view.setVisibility(View.GONE);
				
				cart_icon.setImageResource(com.rs.mobile.wportal.R.drawable.icon_gwc);
				
				cart_state_text_view.setText(getString(com.rs.mobile.wportal.R.string.rt_empty_cart));
			
				pay_btn.setBackgroundColor(Color.parseColor("#424242"));
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawCart() {
		// TODO Auto-generated method stub
		
		try {
		
			menuCount = 0;
			
			cart_area.setVisibility(View.VISIBLE);
			
			cart_icon.setVisibility(View.GONE);
			
			cart_count_text_view.setVisibility(View.GONE);

			JSONArray orgArr = menuListAdapter.getListItems();

			JSONArray arr = new JSONArray();
			
			for (int i = 0; i < orgArr.length(); i++) {
				
				JSONObject item = orgArr.getJSONObject(i);
				
				if (item.getString("type").equals("menu")) {
				
					int count = item.getInt("count");
					
					if (count > 0) {
	
						arr.put(item);
						
						//총 갯수
						menuCount = menuCount + count;
						
					}
					
				}
				
			}
			
			cart_count_text_view_2.setText("" + menuCount);
			
			cartListAdapter = new CartListAdapter(RtMenuListActivity.this, arr, new CartListAdapter.MenuChangeListener() {
				
				@Override
				public void onChange(boolean add) {
					// TODO Auto-generated method stub
					
					try {
						
						menuCount = 0;
						
						totalPrice = 0;

						JSONArray arr = cartListAdapter.getListItems();
						
						for (int i = 0; i < arr.length(); i++) {
							
							int count = 0;
							
							JSONObject item = arr.getJSONObject(i);
							
							if (item.getString("type").equals("menu")) {
							
								 count = item.getInt("count");
								
								double price = Double.parseDouble(arr.getJSONObject(i).getString("itemAmount"));
							
								//총 금액
								totalPrice = totalPrice + (count * price);
								
							}
							
							//총 갯수
							menuCount = menuCount + count;

						}
						
						cart_count_text_view_2.setText("" + menuCount);
						
						cart_state_text_view.setText(getString(com.rs.mobile.wportal.R.string.rt_sum) + totalPrice);
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			cart_list_view.setAdapter(cartListAdapter);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}


	public void addOrder(JSONArray arr) {

		try {

			if (arr == null || arr.length() == 0) {
				
				return;
				
			}
			
			OkHttpHelper helper = new OkHttpHelper(RtMenuListActivity.this);
	
			HashMap<String, String> headers = new HashMap<String, String>();
			
			headers.put("Content-Type","application/json;Charset=UTF-8");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
	
					try {
	
						L.d(all_data);
	
						String status = data.getString("status");
	
						if (status != null && status.equals("1")) {

							Intent i = new Intent(RtMenuListActivity.this, OrderDetailActivity.class);
							i.putExtra("reserveID", reserveID);
							i.putExtra("orderNumber", data.getString("data"));
							startActivity(i);
	
						}
	
					} catch (Exception e) {
	
						L.e(e);
	
					}
	
				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
				}
			}, Constant.BASE_URL_RT + Constant.RT_ADD_ORDER + "?divCode=" + C.DIV_CODE
					+ "&reserveID=" + reserveID + "&restaurantCode=" + restaurantCode 
					+ "&userID=" + S.getShare(RtMenuListActivity.this, C.KEY_REQUEST_MEMBER_ID, "")
					+ "&token=" + S.getShare(RtMenuListActivity.this, C.KEY_JSON_TOKEN, ""), headers, arr.toString());
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}
	
	public void addGroupOrder(JSONArray arr) {

		try {

			if (arr == null || arr.length() == 0) {
				
				return;
				
			}
			
			OkHttpHelper helper = new OkHttpHelper(RtMenuListActivity.this);
	
			HashMap<String, String> headers = new HashMap<String, String>();
			
			headers.put("Content-Type","application/json;Charset=UTF-8");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
	
					try {
	
						L.d(all_data);
	
						String status = data.getString("status");
						
						t(data.getString("msg"));
	
						if (status != null && status.equals("1")) {

							finish();
	
						}
	
					} catch (Exception e) {
	
						L.e(e);
	
					}
	
				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
				}
			}, Constant.BASE_URL_RT + Constant.RT_ADD_GROUP_ORDER + "?groupMemberId=" + S.getShare(RtMenuListActivity.this, C.KEY_REQUEST_MEMBER_ID, "") 
					+ "&groupId=" + groupId 
					+ "&token=" + S.getShare(RtMenuListActivity.this, C.KEY_JSON_TOKEN, ""), headers, arr.toString());
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}
	
	public class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			try {
				
				if (intent.getAction().equals(RtMainActivity.ACTION_RECEIVE_RT_FINISH)) {
					
					finish();
					
				}
			
			} catch (Exception e) {
				
				L.e(e);
				
			}
			
		}

	}
	
}