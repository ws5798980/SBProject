package com.rs.mobile.wportal.service;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.Util;
import com.rs.mobile.wportal.AlarmUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.ycaidl.IYCService;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import okhttp3.Request;

public class YCService extends Service implements LocationListener  {

	public static final String INTENT_FILTER_SEND_MY_LOCATION = "com.rs.mobile.wportal.service.send_my_location";
	
	private Location location;
	
	private double lat = -1; // 위도

	private double lon = -1; // 경도

	// 최소 GPS 정보 업데이트 거리 10미터
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

	// 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

	private LocationManager locationManager;
	
	private String token = "";
	
	private String id = "";
	
	@Override
	public void onCreate() {
		super.onCreate();

	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub		
		
		try {
		
			AlarmUtil.getInstance().startAlram(YCService.this);
			
			registerReceiver(alarmReceiver, new IntentFilter(INTENT_FILTER_SEND_MY_LOCATION));
			
			if (locationManager == null) {
				
				locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	
			}
			
			//gps setting
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setSpeedRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			
			location = new Location(locationManager.getBestProvider(criteria, true));
	
			//기본 위치 설정
			location.setLongitude(Double.parseDouble("113.027417"));
			
			location.setLatitude(Double.parseDouble("28.184747"));
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	@Override
	public void unbindService(ServiceConnection conn) {
		// TODO Auto-generated method stub
		super.unbindService(conn);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		unregisterReceiver(alarmReceiver);
		
		super.onDestroy();
	}
	
	IYCService.Stub binder = new IYCService.Stub() {

		@Override
		public Location getLocation() throws RemoteException {
			// TODO Auto-generated method stub
			return location;
		}

		@Override
		public void showLocationDialog() throws RemoteException {
			// TODO Auto-generated method stub
		}

		@Override
		public String getToken() throws RemoteException {
			// TODO Auto-generated method stub
			return token;
		}

		@Override
		public void setToken(String value) throws RemoteException {
			// TODO Auto-generated method stub
			token = value;
		}

		@Override
		public String getId() throws RemoteException {
			// TODO Auto-generated method stub
			return id;
		}

		@Override
		public void setId(String value) throws RemoteException {
			// TODO Auto-generated method stub
			id = value;
		}
	};

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		setMyLocation();
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
		setMyLocation();
		
	}
	
	public void setMyLocation() {

		try {

			if (locationManager == null) {
			
				locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

			}
			
			// GPS 정보 가져오기
			boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// 현재 네트워크 상태 값 알아오기
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			// 네트워크 정보로 부터 위치값 가져오기
			if (isNetworkEnabled) {
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

				if (locationManager != null) {
					location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (location != null) {
						// 위도 경도 저장
						lat = location.getLatitude();
						lon = location.getLongitude();
					}
				}
			}

			if (isGPSEnabled) {
				if (location == null) {
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							lat = location.getLatitude();
							lon = location.getLongitude();
						}
					}
				}
			}
			
			if (lat == -1 && lon == -1) {

				Intent i = new Intent(BaseActivity.ACTION_RECEIVE_LOCATION_DIALOG);
				sendBroadcast(i);
				
			}

		} catch (Exception e) {

			L.e(e);

		}

	}
	
	/**
	 * 실시간 위치 전송과 새로운 알람 요청
	 */
	private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {
			
				AlarmUtil.getInstance().startAlram(context);
				
				if (Util.checkNetwork(context) == true) {
				
					String id = S.getShare(context, C.KEY_REQUEST_MEMBER_ID, "");
					
					if (id != null && !id.equals("")) {
						
						setMyLocation();
						
						if (location != null) {
							
							HashMap<String, String> params = new HashMap<String, String>();
							
							params.put("memid", id);
							params.put("LonA", location.getLongitude() + "");
							params.put("LatA", location.getLatitude() + "");
							
//							params.put("LonA", "113.027310");
//							params.put("LatA", "28.184823");
							
							OkHttpHelper.CallbackLogic callbackLogic = new OkHttpHelper.CallbackLogic() {
								
								@Override
								public void onNetworkError(Request request, IOException e) {
									// TODO Auto-generated method stub	
								}
					
								@Override
								public void onBizSuccess(String responseDescription, JSONObject data, String all_data) {
									// TODO Auto-generated method stub
									
									try {
									
										L.d(all_data);
										
										L.d(data.toString());
										
									} catch (Exception e) {
										// TODO: handle exception
										L.e(e);
										
									}
			
								}
					
								@Override
								public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
									// TODO Auto-generated method stub
									
								}
							};
							
							OkHttpHelper okhttp = new OkHttpHelper(context, false);
							
							okhttp.addPostRequest(callbackLogic, Constant.BASE_URL_YC, params);
							
						}
					}
				
				}
			
			} catch (Exception e) {
				
				L.e(e);
				
			}
			
		}
	};
	
}
