package com.rs.mobile.wportal.network.rt;

import java.util.concurrent.TimeUnit;

import com.rs.mobile.wportal.network.retrofit.rt.HttpAPI;
import com.rs.mobile.wportal.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @description RestFul API Apdater 
 * @author ZhaoYun
 * @date 2017-3-21
 */
public class NetworkAdapter {

	private static OkHttpClient sOkHttpClient;
	private static Retrofit sRetrofitAdapter;
	private static HttpAPI sHttpAPI;
	
	private static OkHttpClient provideOkHttpClient() {
		if (sOkHttpClient == null) {
			sOkHttpClient = new OkHttpClient.Builder()
					.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
					.readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
					.build();
		}
		return sOkHttpClient;
	}

	private static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
		if(sRetrofitAdapter == null){
			sRetrofitAdapter = new Retrofit.Builder()
					.baseUrl(Constant.BASE_URL_RT)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
					.client(okHttpClient)
					.build();
		}
		return sRetrofitAdapter;
	}
	
	public static HttpAPI httpAPI(){
		if(sHttpAPI == null){
			sHttpAPI = provideRetrofit(provideOkHttpClient()).create(HttpAPI.class);
		}
		return sHttpAPI;
	}
	
}
