package com.rs.mobile.common.activity;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.adapter.WListAdapter;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.adapter.GridListAdapter;
import com.rs.mobile.common.network.Util;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class LocationActivity1 extends BaseActivity {

	private TextView title_text_view;
	
	private TextView current_location;
	
	private TextView next_page;
	
	private TextView current_city_text_view;
	
	private GridView grid_view;
	
	private String zipcode;
	
	private String currentCity;
	
	private LinearLayout page_01_layout;
	
	private LinearLayout page_02_layout;
	
	private TextView current_city;
	
	private ListView borough_list_view;
	
	private ListView affiliste_list_view;
	
	private WListAdapter boroughAdapter;
	
	private JSONArray areaAndSquare;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.common.R.layout.activity_location);
		
		try {

			if (Util.checkNetwork(this) == true) {

				findViewById(com.rs.mobile.common.R.id.close_btn).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						try {
						
							if (page_02_layout.getVisibility() == View.VISIBLE) {
								
								page_01_layout.setVisibility(View.VISIBLE);
								
								page_02_layout.setVisibility(View.GONE);
								
								title_text_view.setText(getString(com.rs.mobile.common.R.string.lc_title));
								
							} else {
								
								finish();
								
							}
						
						} catch (Exception e) {
							
							L.e(e);
							
						}
						
					}
				});
				
				page_01_layout = (LinearLayout)findViewById(com.rs.mobile.common.R.id.page_01_layout);
				
				page_02_layout = (LinearLayout)findViewById(com.rs.mobile.common.R.id.page_02_layout);
				
				title_text_view = (TextView)findViewById(com.rs.mobile.common.R.id.title_text_view);
				
				current_location = (TextView)findViewById(com.rs.mobile.common.R.id.current_location);
				
				next_page = (TextView)findViewById(com.rs.mobile.common.R.id.next_page);
				next_page.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {
							
							page_01_layout.setVisibility(View.GONE);
							
							page_02_layout.setVisibility(View.VISIBLE);
							
							drawAffiliate(zipcode, currentCity);
							
							title_text_view.setText(getString(com.rs.mobile.common.R.string.lc_detail_title));
							
						} catch (Exception e) {
							
							L.e(e);
							
						}
						
					}
				});
				
				current_city_text_view = (TextView)findViewById(com.rs.mobile.common.R.id.current_city_text_view);
			    current_city_text_view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent result = new Intent();
						
						result.putExtra("zipcode", zipcode);
						
						result.putExtra("cityName", currentCity);
						
                        setResult(RESULT_OK, result);
						
						finish();
					}
				});
				
				current_city = (TextView)findViewById(com.rs.mobile.common.R.id.current_city);
				
				grid_view = (GridView)findViewById(com.rs.mobile.common.R.id.grid_view);
				grid_view.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
						// TODO Auto-generated method stub
						
						try {
							
							JSONObject obj = (JSONObject) adapter.getItemAtPosition(position);
							
							zipcode = obj.getString("cityZipcode");
							
							currentCity = obj.getString("cityName");
							
							current_city_text_view.setText(currentCity);
							
							current_city.setText(getString(com.rs.mobile.common.R.string.lc_current_city) + " : " + currentCity);
							
							Intent result = new Intent();
							
							result.putExtra("zipcode", zipcode);
							
							result.putExtra("cityName", currentCity);
							
                            setResult(RESULT_OK, result);
							
							finish();

						} catch (Exception e) {
							
							L.e(e);
							
						}
						
					}
				});
				
				borough_list_view = (ListView)findViewById(com.rs.mobile.common.R.id.borough_list_view);
				borough_list_view.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
						// TODO Auto-generated method stub
						
						try {
						
							boroughAdapter.setSelectedPosition(position);
							
							boroughAdapter.notifyDataSetChanged();
							
							WListAdapter wAdapter = new WListAdapter(LocationActivity1.this, 
									areaAndSquare.getJSONObject(position).getJSONArray("squareList"), WListAdapter.TYPE_AFFILIATE);
							
							affiliste_list_view.setAdapter(wAdapter);
						
						} catch (Exception e) {
							
							L.e(e);
							
						}
						
					}
				});
				
				affiliste_list_view = (ListView)findViewById(com.rs.mobile.common.R.id.affiliste_list_view);
				affiliste_list_view.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
						// TODO Auto-generated method stub
						
						try {
						
							Intent result = new Intent();
							
							JSONObject obj = (JSONObject) adapter.getItemAtPosition(position);
							
                            result.putExtra("zipcode", zipcode);
							
							result.putExtra("cityName", currentCity);
							
							setResult(RESULT_OK, result);
							
							finish();
							
						} catch (Exception e) {
							L.e(e);
						}
						
					}

				});
				
				
				
				OkHttpHelper helper = new OkHttpHelper(this);
				
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("", "");
				
				helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
					
					@Override
					public void onNetworkError(Request request, IOException e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onBizSuccess(String responseDescription, final JSONObject data, final String all_data) {
						// TODO Auto-generated method stub
						
						try {
						
							JSONObject divInfo = data.getJSONObject("divInfo");

							zipcode = divInfo.getString("zipCode");
							
							currentCity = data.getString("currentCity");
							
							current_location.setText(getResources().getString(com.rs.mobile.common.R.string.lc_current_location) + " " +
									divInfo.getString("areaName") + ", " + divInfo.getString("divName"));
							
							current_city_text_view.setText(currentCity);
							
							current_city.setText(getString(com.rs.mobile.common.R.string.lc_current_city) + " : " + currentCity);
							
							JSONArray arr = data.getJSONArray("availableCityList");
							
							GridListAdapter adapter = new GridListAdapter(LocationActivity1.this, arr);
						
							grid_view.setAdapter(adapter);
							
						} catch (Exception e) {
							L.e(e);
						}

					}
					
					@Override
					public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
						// TODO Auto-generated method stub
						
					} 
				}, C.BASE_URL + C.LOCATION_GET_MY_LOCATION + getIntent().getStringExtra("type") +
						"&lon=" + getIntent().getStringExtra("lon") + "&lat=" + getIntent().getStringExtra("lat"), params);
			
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void drawAffiliate(String zipcode, String cityName) {
		
		try {
			

			OkHttpHelper helper = new OkHttpHelper(this);
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("", "");
			
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
				
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onBizSuccess(String responseDescription, final JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
					
					try {
					
						areaAndSquare = data.getJSONArray("areaAndSquare");
						
						boroughAdapter = new WListAdapter(LocationActivity1.this, areaAndSquare, WListAdapter.TYPE_BOROUGH);
						
						borough_list_view.setAdapter(boroughAdapter);
						
						WListAdapter affilisteListAdapter = new WListAdapter(LocationActivity1.this, areaAndSquare.getJSONObject(0).getJSONArray("squareList"), WListAdapter.TYPE_AFFILIATE);
						
						affiliste_list_view.setAdapter(affilisteListAdapter);
						
					} catch (Exception e) {
						
						L.e(e);
						
					}

				}
				
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					
				} 
			}, C.BASE_URL + C.LOCATION_GET_MY_AREA_SQUARE + zipcode + "&cityName=" + cityName, params);
		
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		try {
			
			if (page_02_layout.getVisibility() == View.VISIBLE) {
				
				page_01_layout.setVisibility(View.VISIBLE);
				
				page_02_layout.setVisibility(View.GONE);
				
				title_text_view.setText(getString(com.rs.mobile.common.R.string.lc_title));
				
			} else {
				
				finish();
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
	}
	
}