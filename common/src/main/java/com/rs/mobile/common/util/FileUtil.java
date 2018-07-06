package com.rs.mobile.common.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileUtil {

	public static String upload(String url, List<String> filePath,Map<String, String> params,final Activity act) {
		
		final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("image/jpeg");

		final OkHttpClient client = new OkHttpClient();
         
		
//		MultipartBuilder builder=new MultipartBuilder().type(MultipartBuilder.FORM);
		MultipartBody.Builder builder = new MultipartBody.Builder(); 
		builder.setType(MultipartBody.FORM);
		L.d(filePath.toString());
		for (int i = 0; i < filePath.size(); i++) {
			File file=new File(filePath.get(i));
			if (file!=null) {
				builder.addFormDataPart("file", file.getName(),RequestBody.create(MEDIA_TYPE_MARKDOWN, file));
			}
		}
		
		if (params != null) {
		
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.addFormDataPart(entry.getKey(), entry.getValue());
			}
		
		}
		
		
        RequestBody requestBody=builder.build();
        
        Request request=new Request.Builder().url(url).post(requestBody).build();
       
		String string = null;
		
		
		try {
			client.newCall(request).enqueue(new Callback() {
				
				@Override
				public void onFailure(Call arg0, IOException arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onResponse(Call arg0, Response arg1)
						throws IOException {
					final String responseBody = arg1.body().string().trim();
   					L.d(responseBody);
					 act.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
					T.showToast(act, "评价成功");
					act.finish();
						}
					});
				}
			});
         
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);

		}
		
		return string;

	}
//public static String upload1(String url, List<String> filePath,Map<String, String> params,final Activity act) {
//	   
//		
//		final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("image/jpeg");
//
//		final OkHttpClient client = new OkHttpClient();
//         
//		
////		MultipartBuilder builder=new MultipartBuilder().type(MultipartBuilder.FORM);
//		 MultipartBody.Builder mBody = new MultipartBody.Builder("").setType(MultipartBody.FORM);
//		for (int i = 0; i < filePath.size(); i++) {
//			File file=new File(filePath.get(i));
//			if (file!=null) {
//				RequestBody filebody=RequestBody.create(MediaType.parse("application/octet-stream"), file);
//				mBody.addFormDataPart("files", file.getName(),filebody).build();
//				
//			}
//		}
//		
//		if (params != null) {
//		
//			for (Map.Entry<String, String> entry : params.entrySet()) {
//				mBody.addFormDataPart(entry.getKey(), entry.getValue()).build();
//			}
//		
//		}
//		
//		
//       
//        
//        Request request=new Request.Builder().url(url).post(mBody).build();
//       
//		String string = null;
//		
//		
//		try {
//			client.newCall(request).enqueue(new Callback() {
//				
//				@Override
//				public void onFailure(Call arg0, IOException arg1) {
//					// TODO Auto-generated method stub
//					
//				}
//
//				@Override
//				public void onResponse(Call arg0, Response arg1)
//						throws IOException {
//					final String responseBody = arg1.body().string().trim();
//   					L.d(responseBody);
//					 act.runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//					T.showToast(act, "评价成功");	
//					act.finish();
//						}
//					});
//				}
//			});
//         
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			L.e(e);
//
//		}
//		
//		return string;
//
//	}
	
public static void uploadrt(String url, List<String> filePath,Map<String, String> params,final Activity act,CallbackLogic1 callbackLogic) {
	   D.showProgressDialog(act, "", true);
		final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("image/jpeg");

		final OkHttpClient client = new OkHttpClient();
         
		
//		MultipartBuilder builder=new MultipartBuilder().type(MultipartBuilder.FORM);
		MultipartBody.Builder builder = new MultipartBody.Builder(); new MultipartBody.Builder();
		builder.setType(MultipartBody.FORM);
		for (int i = 0; i < filePath.size(); i++) {
			File file=new File(filePath.get(i));
			if (file!=null) {
				builder.addFormDataPart("files", file.getName(),RequestBody.create(MEDIA_TYPE_MARKDOWN, file));
			}
		}
		
		if (params != null) {
		
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.addFormDataPart(entry.getKey(), entry.getValue());
			}
		
		}
		
		
        RequestBody requestBody=builder.build();
        
        Request request=new Request.Builder().url(url).post(requestBody).build();
       
		
		
		
		try {
			client.newCall(request).enqueue(getCallBack(callbackLogic, act));
         
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);

		}
		
		

	}
	public static String upload(String url, List<String> filePath,Map<String, String> params, String fileKey) {
		
		try {
		
			final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("image/jpeg");
	
			OkHttpClient client = new OkHttpClient();
	
//			MultipartBuilder builder=new MultipartBuilder().type(MultipartBuilder.FORM);
			MultipartBody.Builder builder = new MultipartBody.Builder();
			builder.setType(MultipartBody.FORM);
			for (int i = 0; i < filePath.size(); i++) {
				File file=new File(filePath.get(i));
				if (file!=null) {
					builder.addFormDataPart("file", file.getName(),RequestBody.create(MEDIA_TYPE_MARKDOWN, file));
				}
			}
			
			if (params != null) {
			
				for (Map.Entry<String, String> entry : params.entrySet()) {
					builder.addFormDataPart(entry.getKey(), entry.getValue());
				}
			
			}

	        RequestBody requestBody=builder.build();
	        
	        Request request = new Request.Builder().url(url).post(requestBody).build();
			String string = null;
			Response response;
			
			try {
				response = client.newCall(request).execute();
				if (!response.isSuccessful()) {
					string = "";
				} else {
					string = response.body().string();
					Log.e("responseTAG",string);
				}
	         
			} catch (Exception e) {
				// TODO Auto-generated catch block
				L.e(e);
	
			}
			
			request = null;
			
			builder = null;
			
			client = null;
			
			return string;
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;

	}
	/**
	 * 
	 * @author ZZooN
	 *
	 */
	public interface CallbackLogic1 {

		void onBizSuccess(String responseDescription, JSONObject data, String flag);

		void onBizFailure(String responseDescription, JSONObject data, String flag);
		
		void onNetworkError(Request request, IOException e);
		
	}
	/**
	 * getCallBack
	 * 
	 * @param callbackLogic
	 * @return Callback
	 */
	public static Callback getCallBack(final CallbackLogic1 callbackLogic,final Context context) {

		return new Callback() {

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				try {
					D.hideProgressDialog();
					callbackLogic.onBizFailure("", null, "");
					if (callbackLogic != null) {
						
								T.showToast(context, "网络连接错误");
							

						
					}
				} catch (Exception ex) {
					L.e(ex);
				}
			}

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try {

					D.hideProgressDialog();

					final String responseBody = arg1.body().string().trim();
					L.d(responseBody);
					final JSONObject json = new JSONObject(responseBody);
                    ((Activity) context).runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								// 토큰 갱신
								if (json.has(C.KEY_JSON_FM_STATUS)) {
									String status = json
											.getString(C.KEY_JSON_FM_STATUS);
									if (status.equals("-9001")) {
										T.showToast(context,
												json.getString("message"));
										BaseActivity activity = (BaseActivity) context;
										Intent i = new Intent(context,
												LoginActivity.class);
										activity.startActivity(i);
										activity.finish();

										return;
									}
								}
								String flag = C.VALUE_RESPONSE_SUCCESS;
								if (json != null
										&& json.has(C.KEY_RESPONSE_FLAG)) {
									flag = json.getString(C.KEY_RESPONSE_FLAG);
								}
								callbackLogic.onBizSuccess(responseBody, json,
										flag);
							} catch (Exception e) {
								L.e(e);
							}
					
						}
					});
					L.d(responseBody);
				} catch (Exception e) {
					L.e(e);
					callbackLogic.onBizFailure("", null, "");
				}
			}

		};

	}
public static void uploadsm(String url, List<String> filePath,Map<String, String> params,final Activity act,CallbackLogic1 callbackLogic) {
	    
		final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("image/jpeg");

		final OkHttpClient client = new OkHttpClient();
         
		
//		MultipartBuilder builder=new MultipartBuilder().type(MultipartBuilder.FORM);
		MultipartBody.Builder builder = new MultipartBody.Builder(); new MultipartBody.Builder();
		builder.setType(MultipartBody.FORM);
		for (int i = 0; i < filePath.size(); i++) {
			File file=new File(filePath.get(i));
			if (file!=null) {
				builder.addFormDataPart("files", file.getName(),RequestBody.create(MEDIA_TYPE_MARKDOWN, file));
			}
		}
		
		if (params != null) {
		
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.addFormDataPart(entry.getKey(), entry.getValue());
			}
		
		}
		
		
        RequestBody requestBody=builder.build();
        
        Request request=new Request.Builder().url(url).post(requestBody).build();
       
		
		
		
		try {
			client.newCall(request).enqueue(getCallBack(callbackLogic, act));
         
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(e);

		}
		
		

	}
}
