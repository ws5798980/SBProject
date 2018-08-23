package com.rs.mobile.wportal.activity.sm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.StateButton;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.CityAdapter;
import com.rs.mobile.wportal.adapter.sm.DistrictAdapter;
import com.rs.mobile.wportal.adapter.sm.ProvinceAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class SmAddAddressActivityCN extends BaseActivity {

	private List<Map<String, String>> list_ProvinceName = new ArrayList<Map<String, String>>();

	private List<Map<String, String>> list_city = new ArrayList<Map<String, String>>();

	private List<Map<String, String>> list_area = new ArrayList<Map<String, String>>();

	private LinearLayout address_pop;

	private RelativeLayout rela_004;

	private TextView city_result, text_next, text_save;

	private TextView show001, show002, show003;

	private TextView  tv_addr01_txt;

	private int position_provinceName = 0;

	private int position_city = 0;

	private int position_district = 0;

	private String username, address, mobile;

	private ListView listview_ProvinceName, listview_city, listview_area;

	private ProvinceAdapter adapter001;

	private CityAdapter adapter002;

	private DistrictAdapter adapter003;

	private EditText editText_receiver, editText_phone, editText_address,tv_postNumber;

	private InputMethodManager imm;

	private LinearLayout linear_gone;

	private CheckBox checkBox;

	private String activity;

	private String id;

	private TextView title_text_view;

	private LinearLayout linear_back;

	private RelativeLayout rela_003;

	private String zipCode;

	private String zipName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {

			setContentView(R.layout.activity_sm_add_address_cn);

			imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

			initView();

			activity = getIntent().getStringExtra("activity");
			if (activity.equals("edit")) {

				initData();

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initData() {

		try {

			title_text_view.setText(getResources().getString(R.string.change_address));

			editText_address.setText(getIntent().getStringExtra(C.KEY_JSON_FM_ADDRESS));

			editText_phone.setText(getIntent().getStringExtra(C.KEY_JSON_FM_MOBILE));

			editText_receiver.setText(getIntent().getStringExtra(C.KEY_JSON_FM_NAME));

//			text_next.setText(getIntent().getStringExtra(C.KEY_JSON_FM_LOCATION));

			checkBox.setChecked(getIntent().getBooleanExtra(C.KEY_JSON_FM_HASDEFAULT, false));

			id = getIntent().getStringExtra(C.KEY_JSON_FM_ID);

			tv_postNumber.setText(getIntent().getStringExtra("zipCode"));

			tv_addr01_txt.setText(getIntent().getStringExtra("zipName"));
		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {

		try {

			city_item();

			list_city(0);

			list_area(0);

			linear_back = (LinearLayout) findViewById(R.id.close_btn);

			linear_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					finish();

				}

			});

			tv_postNumber = (EditText) findViewById(R.id.postNumber);
			tv_addr01_txt = (TextView)findViewById(R.id.addr01_txt);

			title_text_view = (TextView) findViewById(R.id.title_text_view);

			checkBox = (CheckBox) findViewById(R.id.checkBox);

			checkBox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

				}
			});

			editText_receiver = (EditText) findViewById(R.id.editText_receiver);

			editText_phone = (EditText) findViewById(R.id.editText_phone);

			address_pop = (LinearLayout) findViewById(R.id.address_pop);

			//text_next = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_next);

			editText_address = (EditText) findViewById(R.id.editText_address);



			rela_004 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela_004);

			rela_004.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

					address_pop.setVisibility(View.VISIBLE);

				}
			});

			rela_003 = (RelativeLayout) findViewById(R.id.rela_003);

			rela_003.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

				}
			});

			linear_gone = (LinearLayout) findViewById(R.id.linear_gone);

			linear_gone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					address_pop.setVisibility(View.GONE);

				}
			});

			show001 = (TextView) findViewById(R.id.TextView_city_show001);

			show002 = (TextView) findViewById(R.id.TextView_city_show002);

			show003 = (TextView) findViewById(R.id.TextView_city_show003);

			listview_ProvinceName = (ListView) findViewById(R.id.ListView_city001);

			listview_city = (ListView) findViewById(R.id.ListView_city002);

			listview_area = (ListView) findViewById(R.id.ListView_city003);

			adapter001 = new ProvinceAdapter(getApplicationContext(), list_ProvinceName);

			listview_ProvinceName.setAdapter(adapter001);

			adapter002 = new CityAdapter(getApplicationContext(), list_city);

			listview_city.setAdapter(adapter002);

			adapter003 = new DistrictAdapter(getApplicationContext(), list_area);

			listview_area.setAdapter(adapter003);

			listview_ProvinceName.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					position_provinceName = position;

					show001.setText("");

					show001.setText(list_ProvinceName.get(position).get("ProvinceName").toString());

					list_city.clear();

					list_area.clear();

					list_city(position);

					adapter002.notifyDataSetChanged();

					adapter003.notifyDataSetChanged();
				}
			});

			listview_city.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					position_city = position;

					show002.setText("");

					show002.setText(list_city.get(position).get("CityName").toString());

					list_area.clear();

					list_area(position);

					adapter003.notifyDataSetChanged();

				}
			});
			listview_area.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					position_district = position;

					show003.setText("");

					show003.setText(list_area.get(position).get("DistrictName").toString());

					address_pop.setVisibility(View.GONE);

					getZipCode(false);

					tv_addr01_txt.setText(show001.getText()+" "+show002.getText()+" "+show003.getText());
					// show_textview();

				}
			});
			text_save = (TextView) findViewById(R.id.text_save);

			text_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {

						username = editText_receiver.getText().toString();

						address = editText_address.getText().toString();

						mobile = editText_phone.getText().toString();
						zipCode = tv_postNumber.getText().toString();

						if (username.equals("") && address.equals("") && mobile.equals("")) {
							t(getResources().getString(R.string.plz_fill_text));
						} else {


//							if (zipCode == null || zipCode.equals("") || zipCode.equals("null")
//									|| zipCode.length() < 5) {
//
//								getZipCode(true);
//
//							} else {

								if (activity.equals("edit")) {

//									postEdit(Constant.SM_BASE_URL + Constant.UPDATE_USER_SHOPADDRESS);
									postEdit(Constant.SM_BASE_URL + "/FreshMart/User/UpdateUserShopAddress");

								} else {

//									postEdit(Constant.SM_BASE_URL + Constant.ADD_USERSHOP_ADDRESS);
									postEdit(Constant.SM_BASE_URL + "/FreshMart/User/AddUserShopAddress");
								}

//							}

						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	// 获取城市联动列表
	public void city_item() {

		try {

			InputStream is = getAssets().open("SCQ.txt");

			int size = is.available();

			byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			String text = new String(buffer, "utf-8");

			JSONObject object = new JSONObject(text);

			JSONArray array = object.getJSONArray("Data");

			for (int i = 0; i < array.length(); i++) {

				JSONObject object2 = array.getJSONObject(i);

				Map<String, String> map = new HashMap<String, String>();

				map.put("ProvinceId", object2.getString("ProvinceId").toString());

				map.put("ProvinceName", object2.getString("ProvinceName").toString());

				map.put("Citys", object2.getString("Citys").toString());

				list_ProvinceName.add(map);

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);

		switch(requestCode){

			case 1000: // requestCode가 B_ACTIVITY인 케이스

				if(resultCode == RESULT_OK){ //B_ACTIVITY에서 넘겨진 resultCode가 OK일때만 실행
					String postdata = intent.getExtras().getString("postaddr");
					String[] results = postdata.split("\\|");
					tv_postNumber.setText(results[0]);
					tv_addr01_txt.setText(results[1]);
			//		intent.getExtras.getInt("data"); //등과 같이 사용할 수 있는데, 여기서 getXXX()안에 들어있는 파라메터는 꾸러미 속 데이터의 이름표라고 보면된다.

				}

		}

	}

	// 省份-------城市
	public void list_city(int tt) {

		try {

			String citylist = list_ProvinceName.get(tt).get("Citys").toString();

			JSONArray array = new JSONArray(citylist);

			for (int i = 0; i < array.length(); i++) {

				JSONObject object = array.getJSONObject(i);

				Map<String, String> map = new HashMap<String, String>();

				map.put("CityId", object.getString("CityId").toString());

				map.put("CityName", object.getString("CityName").toString());

				if (object.isNull("Districts")) {

				} else {

					map.put("Districts", object.getString("Districts").toString());

				}

				list_city.add(map);

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	// 城市 -------区域
	public void list_area(int tt) {

		try {

			if (list_city.get(tt).get("Districts") == null) {

			} else {

				String citylist = list_city.get(tt).get("Districts").toString();

				JSONArray array = new JSONArray(citylist);

				for (int i = 0; i < array.length(); i++) {

					JSONObject object = array.getJSONObject(i);

					Map<String, String> map = new HashMap<String, String>();

					map.put("DistrictName", object.getString("DistrictName").toString());

					map.put("DistrictId", object.getString("DistrictId").toString());

					list_area.add(map);

				}

			}

		} catch (Exception e) {
			L.e(e);
		}
	}

	// // 选择的地址信息显示
	// public void show_textview() {
	//
	// try {
	//
	// text_next.setText(list_ProvinceName.get(position_provinceName).get("ProvinceName").toString()
	// + list_city.get(position_city).get("CityName").toString()
	// + list_area.get(position_district).get("DistrictName").toString());
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// }

	// public void postToService() {
	//
	// try {
	//
	// Map<String, String> params = new HashMap<String, String>();
	// params.put(C.KEY_JSON_TOKEN, S.get(getApplicationContext(),
	// C.KEY_JSON_TOKEN));
	// params.put(C.KEY_JSON_FM_DELIVER_ID, username);
	// params.put(C.KEY_JSON_FM_DEFAULT_ADD, checkBox.isChecked() + "");
	// params.put(C.KEY_JSON_FM_DELIVER_NAME, text_next.getText().toString());
	// params.put(C.KEY_JSON_FM_TO_ADDRESS,
	// editText_address.getText().toString());
	// params.put(C.KEY_JSON_FM_MOBILE, editText_phone.getText().toString());
	// params.put(C.KEY_JSON_FM_LATITUDE, "28.1928400");
	// params.put(C.KEY_JSON_FM_LONGITUDE, "113.0166470");
	// // params.put("", flag+"");
	// OkHttpHelper okhttp = new OkHttpHelper(SmAddAddressActivity.this);
	// okhttp.addSMPostRequest(new CallbackLogic() {
	//
	// @Override
	// public void onNetworkError(Request request, IOException e) {
	//
	// }
	//
	// @Override
	// public void onBizSuccess(String responseDescription, JSONObject data,
	// String flag) {
	// try {
	//
	// String status = data.getString(C.KEY_JSON_FM_STATUS);
	//
	// if (!status.equals("1")) {
	//
	// t(data.getString("message"));
	//
	// return;
	//
	// }
	//
	// finish();
	//
	// } catch (Exception e) {
	// L.e(e);
	// }
	// }
	//
	// @Override
	// public void onBizFailure(String responseDescription, JSONObject data,
	// String flag) {
	//
	// }
	// }, C.SM_BASE_URL + C.ADD_USERSHOP_ADDRESS, params);
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// }

	/**
	 * 배송지 정보를 서버에 저장한다
	 * 
	 * @param url
	 */
	public void postEdit(final String url) {

		try {

								/*JSONObject geocode = new JSONObject(json);

								String location = geocode.getString("location");*/

			HashMap<String, String> params = new HashMap<String, String>();

//								String token_four = S.getShare(SmAddAddressActivity.this, C.KEY_JSON_TOKEN, "") + "|" + S.getShare(SmAddAddressActivity.this, C.KEY_JSON_SSOID, "") + "|"
//										+ S.getShare(SmAddAddressActivity.this, C.KEY_JSON_CUSTOM_CODE, "") + "|" + Util.getDeviceId(getApplicationContext());
			params.put(C.KEY_JSON_TOKEN, S.get(SmAddAddressActivityCN.this, C.KEY_JSON_TOKEN));
			params.put(C.KEY_JSON_FM_DELIVER_ID, username);
			params.put(C.KEY_JSON_FM_DEFAULT_ADD, checkBox.isChecked() + "");
			params.put(C.KEY_JSON_FM_DELIVER_NAME, tv_addr01_txt.getText().toString());
			params.put(C.KEY_JSON_FM_TO_ADDRESS, editText_address.getText().toString());
			params.put(C.KEY_JSON_FM_MOBILE, editText_phone.getText().toString());
								/*params.put(C.KEY_JSON_FM_LATITUDE, "" + location.split(",")[1]);
								params.put(C.KEY_JSON_FM_LONGITUDE, "" + location.split(",")[0]);*/
			params.put(C.KEY_JSON_FM_LATITUDE, "37.434668" );
			params.put(C.KEY_JSON_FM_LONGITUDE, "122.160742");
			params.put("zip_code", tv_postNumber.getText().toString());

			if (id != null && !id.equals(""))
				params.put(C.KEY_JSON_FM_ID, id);
			OkHttpHelper okHttpHelper = new OkHttpHelper(SmAddAddressActivityCN.this);

			okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					try {
						String status = data.getString(C.KEY_JSON_FM_STATUS);

						if (!status.equals("1")) {

							t(data.getString("message"));

							return;

						}

						finish();

					} catch (Exception e) {
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}
			}, url, params);

		} catch (Exception e) {

			L.e(e);

		}

//		try {
//
//			/*
//			 * 입력된 주소로 로케이션을 가져온 후에 서버에 저장한다
//			 *
//			 * Util.getGeocodesByAddress : 로케이션 가져오기
//			 *
//			 */
//
//			// 입력값 확인
//			/*if (username == null || username.equals("") || // 사용자 이름
//					text_next.getText() == null || text_next.getText().toString().equals("") || // 지역
//																								// 주소
//					editText_address.getText() == null || editText_address.getText().toString().equals("") || // 상세주소
//					editText_phone.getText() == null || editText_phone.getText().toString().equals("")) { // 전화번호
//
//				t(getString(com.rs.mobile.wportal.R.string.rt_empty_msg));
//
//				return;
//
//			}*/
//
//			Util.getGeocodesByAddress(SmAddAddressActivity.this, "JSON",
//					tv_addr01_txt.getText().toString().replace(" ", "") + editText_address.getText().toString(),
//					new Util.GeocodesByAddressListener() {
//
//						@Override
//						public void onError(String error) {
//
//							t(error);
//
//						}
//
//						@Override
//						public void onError() {
//
//							t(getString(R.string.dp_text_033));
//
//						}
//
//						@Override
//						public void onComplete(String json) {
//
//
//
//						}
//					});
//
//		} catch (Exception e) {
//
//			L.e(e);
//
//		}

	}

	/**
	 * 선택된 주소로 zipcode가져오기
	 * 
	 * fromSave : 저장하는 시점에 zipcode가 없는 경우에 zipcode를 생성하고 저장을 진행한다
	 */
	public void getZipCode(final boolean fromSave) {

		/*try {

			/*text_next.setText(list_ProvinceName.get(position_provinceName).get("ProvinceName").toString() + " "
					+ list_city.get(position_city).get("CityName").toString() + " "
					+ list_area.get(position_district).get("DistrictName").toString());*/

			//Map<String, String> params = new HashMap<String, String>();

			//String province = list_ProvinceName.get(position_provinceName).get("ProvinceName").toString();

			//province = province.substring(0, province.length() - 1);

			//params.put("zip_name1", province);

			//String city = list_city.get(position_city).get("CityName").toString();

			//city = city.substring(0, city.length() - 1);

			//params.put("zip_name2", city);

			/*OkHttpHelper okhttp = new OkHttpHelper(SmAddAddressActivity.this);

			okhttp.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					try {

						String status = data.getString(C.KEY_JSON_FM_STATUS);

						t(data.getString("msg"));

						if (status.equals("1")) {

							zipCode = data.getString("zip_code");

							if (fromSave == true) {

								if (activity.equals("edit")) {

									postEdit(Constant.SM_BASE_URL + Constant.UPDATE_USER_SHOPADDRESS);

								} else {

									postEdit(Constant.SM_BASE_URL + Constant.ADD_USERSHOP_ADDRESS);

								}

							}

						}

					} catch (Exception e) {
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}
			}, C.GET_ZIP_CODE, params);

		} catch (Exception e) {

			L.e(e);

		}*/

	}
}