package com.rs.mobile.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import okhttp3.Request;

public class Util {
	public static String formatStr(String str) {
		return isNull(str) ? "" : str;
	}
	/**
	 * 判断给定字符串是否空白串。
	 *
	 * @param input
	 * @return boolean
	 */
	public static boolean isNull(String input) {
		//return str == null || "".equals(str) || str.trim().equals("null") ? true : false;
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	public static boolean isNumeric(String s) {
		
		return s.replaceAll("[+-]?\\d+", "").equals("");
		
	}
	
	public interface GeocodesByAddressListener {
		
		void onComplete(String json);
		
		void onError();
		
		void onError(String error);
		
	}
	
	/**
	 * 주소를 로케이션으로 변환한다
	 * 
	 * @param context
	 * @param output
	 * @param key
	 * @param address
	 * @param listener
	 * @return "116.481488,39.990464"
	 */
	public static void getGeocodesByAddress(Context context, String output, String address, final GeocodesByAddressListener listener) {

		try {
		
			OkHttpHelper okhttp = new OkHttpHelper(context);
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("output", output);
			
			params.put("key", C.GEOCODE_KEY);
			
			params.put("address", address);
			
			okhttp.addGetRequest(new CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					
					try {
					
//						{
//							"status" : "1",
//							"info" : "OK",
//							"infocode" : "10000",
//							"count" : "1",
//							"geocodes" : [ 
//							              {
//											"formatted_address" : "北京市朝阳区方恒国际中心|A座",
//											"province" : "北京市",
//											"citycode" : "010",
//											"city" : "北京市",
//											"district" : "朝阳区",
//											"township" : [ ],
//											"neighborhood" : { … },
//											"building" : { … },
//											"adcode" : "110105",
//											"street" : [ ],
//											"number" : [ ],
//											"location" : "116.480724,39.989584",
//											"level" : "门牌号"
//										}
//									]
//						}
						
						String status = data.getString("status");
						
						if (status.equals("1")) {
	
							JSONArray geocodes = data.getJSONArray("geocodes");
							
							if (geocodes == null || geocodes.length() == 0) {
								
								listener.onError();
								
							}
							
							JSONObject geocode = geocodes.getJSONObject(0);
							
							listener.onComplete(geocode.toString());
							
						} else {
							
							listener.onError(data.getString("info"));
							
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						L.e(e);
						
						listener.onError(e.getClass().getName());
					}
				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
	
				}
			}, C.GET_GEOCODE_LOCATION, params);
			
		} catch (Exception e) {
			
			L.e(e);
			
			listener.onError(e.getClass().getName());
			
		}
		
	}

	
	/**
	 * 获取deviceID
	 * @param context
	 * @return
	 */
    public static String getDeviceId(Context context) {  
            String deviceId = "";  
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);  
            deviceId = tm.getDeviceId();   
            
            if (deviceId == null || "".equals(deviceId)) {  
                try {  
                    deviceId = getAndroidId(context);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            return deviceId;  
        }  
	
        // Android Id  
        private static String getAndroidId(Context context) {  
            String androidId = Settings.Secure.getString(  
                    context.getContentResolver(), Settings.Secure.ANDROID_ID);  
            return androidId;  
        }  
      
        /**
         * 保存生成的deviceid
         * @param context
         * @param str
         */
        public static void saveDeviceID(Context context ,String str) {  
            try {
            	FileOutputStream fos = 	context.openFileOutput("file_out.txt",  
                        Context.MODE_PRIVATE); 
            	fos.write(str.getBytes());  
            	fos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
      
        /**
         * 读取存储的deviceid
         * @param context
         * @return
         */
        public static String readDeviceID(Context context) { 
        	 try {  
        	 FileInputStream fis= context.openFileInput("file_out.txt");  
        	    byte[] buffer = new byte[1024];  
        	    fis.read(buffer);  
        	    // 对读取的数据进行编码以防止乱码  
        	    String fileContent = new String(buffer, "UTF-8");  
                return fileContent;  
            } catch (IOException e) {  
                e.printStackTrace();  
                return null;  
            }  
        }

        public static void Debug_Toast_Message(Context context, String msg)
		{
			//Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}

		public static void Real_Service_Toast_Message(Context context, String msg)
		{
			//Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	
}
