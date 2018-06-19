package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.ShowImageActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

/**
 * 
 * @author ZhaoYun
 * @date 2017-3-13
 */
public class RtEtJoinActivity extends BaseActivity {

	/*
	 * xml 
	 */
	private Toolbar toolbar;
	
	private TextView tv_title;
	
	private LinearLayout iv_back;
	
	private WImageView iv_thumbnail;
	
	private TextView title_text_view;

	private LinearLayout join_btn;
	
	private LinearLayout call_btn;
	
	private LinearLayout address_btn;
	
	private LinearLayout photo_btn;
	
	private TextView tv_phone_number;
	
	private TextView tv_address;
	
	private TextView tv_bz_hour;
	
	/*
	 * variable
	 */
	private String groupId;
	
	private String restaurantCode;
	
	private String phoneNumber;
	
	private String address;
	
	private String[] images;
	
	private JSONObject resultData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_et_join);
		
		groupId = getIntent().getStringExtra("groupID");

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
			
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.rt_et_join_title));
			
			iv_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			
			iv_thumbnail = (WImageView)findViewById(com.rs.mobile.wportal.R.id.iv_thumbnail);
			
			title_text_view = (TextView)findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			
			join_btn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.join_btn);
			
			join_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					try {
						
						
						UtilClear.CheckLogin(RtEtJoinActivity.this, new UtilCheckLogin.CheckError() {
							
							@Override
							public void onError() {
								return ;
							}
						});
						
						OkHttpHelper helper = new OkHttpHelper(RtEtJoinActivity.this);
				
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
									
									t(data.getString("msg"));
				
									if (status != null && status.equals("1")) {
										
										Intent i = new Intent(RtEtJoinActivity.this, RtETBoardActivity.class);
										
										i.putExtra("groupID", groupId);
										
										i.putExtra("restaurantCode", restaurantCode);
										
										startActivity(i);
										
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
						}, Constant.BASE_URL_RT + Constant.RT_JOIN_GROUP + "?groupMemberId=" + S.getShare(RtEtJoinActivity.this, C.KEY_REQUEST_MEMBER_ID, "") +
								"&groupId=" + groupId +"&token=" + S.getShare(RtEtJoinActivity.this, C.KEY_JSON_TOKEN, ""), params);
					      
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			call_btn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.call_btn);
			
			call_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						Uri uri = Uri.parse("tel:" + resultData.getString("phone"));
						Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
						startActivity(callIntent);
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});

			address_btn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.address_btn);
			
			address_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						Intent intent = new Intent(); 
			            Bundle bundle = new Bundle(); 
			            bundle.putDouble("location_lat", Double.parseDouble(resultData.getString("latitude"))); 
			            bundle.putDouble("location_lng", Double.parseDouble(resultData.getString("longitude")));
			            bundle.putString("location_name", resultData.getString("restaurantName"));
			            intent.setClassName("cn.ycapp.im", "cn.ycapp.im.ui.activity.AMAPLocationActivity");
			            intent.putExtras(bundle); 
			            startActivity(intent);
			        
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			photo_btn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.photo_btn);
			
			photo_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Bundle bundle = new Bundle();
					
					bundle.putStringArray("images", images);

					PageUtil.jumpTo(RtEtJoinActivity.this, ShowImageActivity.class, bundle);
					
				}
			});
			
			tv_phone_number = (TextView)findViewById(com.rs.mobile.wportal.R.id.tv_phone_number);
			
			tv_address = (TextView)findViewById(com.rs.mobile.wportal.R.id.tv_address);
			
			tv_bz_hour = (TextView)findViewById(com.rs.mobile.wportal.R.id.tv_bz_hour);
			
	
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void initDates() {

		try {
			
			UtilClear.CheckLogin(RtEtJoinActivity.this, new UtilCheckLogin.CheckError() {
				
				@Override
				public void onError() {
					finish();
				}
			});
		
			OkHttpHelper helper = new OkHttpHelper(RtEtJoinActivity.this);
	
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
						
//						{
//						    "status": 1,
//						    "msg": "success",
//						    "data": {
//						        "status": "F",
//						        "restaurantCode": "FA03",
//						        "restaurantName": "鱼满仓（宇成店）",
//						        "imgUrl": "http://222.240.51.146:8488//wsRestaurant/restaurant/store/FA03/FA03_MAIN.PNG",
//						        "rate": "0",
//						        "address": "长沙市开福区宇成朝阳广场2-1209",
//						        "businessTime": "09:00 ~ 20:00",
//						        "phone": "536254526",
//						        "info": "鱼米满仓是以鱼肉（约250克）、玉米粒适量、西芹一根、红椒适量（点缀用）、饺子皮等为主要食材做成的一道美食。",
//						        "rateCount": "0",
//						        "score1": "0",
//						        "score2": "0",
//						        "score3": "0",
//						        "latitude": "28.196093",
//						        "longitude": "113.033972",
//						        "groupDesc": null,
//						        "goldenBellYN": "N",
//						        "goldenbellType": "",
//						        "images": [
//						            {
//						                "imgUrl": "http://222.240.51.146:8488//wsRestaurant/restaurant/store/FA03/FA03_SUB1.PNG",
//						                "ver": "1.0.0"
//						            },
//						            {
//						                "imgUrl": "http://222.240.51.146:8488//wsRestaurant/restaurant/store/FA03/FA03_SUB2.PNG",
//						                "ver": "1.0.0"
//						            },
//						            {
//						                "imgUrl": "http://222.240.51.146:8488//wsRestaurant/restaurant/store/FA03/FA03_SUB3.PNG",
//						                "ver": "1.0.0"
//						            },
//						            {
//						                "imgUrl": "http://222.240.51.146:8488//wsRestaurant/restaurant/store/FA03/FA03_SUB4.PNG",
//						                "ver": "1.0.0"
//						            }
//						        ]
//						    }
//						}
	
						String status = data.getString("status");
						
						t(data.getString("msg"));
	
						if (status != null && status.equals("1")) {
							
							data = data.getJSONObject("data");
							
							resultData = data;
							
							restaurantCode = data.getString("restaurantCode");
							
							status = data.getString("status");
							
							if (status != null && status.equals("S")) {
							
								//아직 참가 되지 않은 인원
								
								ImageUtil.drawImageFromUri(data.getString("imgUrl"), iv_thumbnail);
								
								title_text_view.setText(data.getString("restaurantName"));
								
								tv_phone_number.setText(data.getString("phone"));
								
								tv_address.setText(data.getString("address"));
								
								tv_bz_hour.setText(getString(com.rs.mobile.wportal.R.string.rt_bz_hour) + " : " + data.getString("businessTime"));
	
								phoneNumber = data.getString("phone").replaceAll("-", "").replaceAll(" ", "");
								
								address = data.getString("address");
								
								JSONArray photoArr = data.getJSONArray("images");
								
								images = new String[photoArr.length()];

								for (int i = 0; i < photoArr.length(); i++) {
									
									images[i] = photoArr.getJSONObject(i).getString("imgUrl");
									
								}
							
							} else {
								
								//이미 참가한 인원
								
								Intent i = new Intent(RtEtJoinActivity.this, RtETBoardActivity.class);
								
								i.putExtra("groupID", groupId);
								
								i.putExtra("restaurantCode", restaurantCode);
								
								startActivity(i);
								
								finish();
								
							}
							
						} else {
							
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
			}, Constant.BASE_URL_RT + Constant.RT_GET_GROUP_INVITE_INFO + "?groupID=" + groupId +
					"&memberId=" + S.getShare(RtEtJoinActivity.this, C.KEY_REQUEST_MEMBER_ID, "") +
					"&token=" + S.getShare(RtEtJoinActivity.this, C.KEY_JSON_TOKEN, ""), params);
		
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
		super.onDestroy();
	}
	
}