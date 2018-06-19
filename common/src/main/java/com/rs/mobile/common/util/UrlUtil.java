package com.rs.mobile.common.util;

import java.util.HashMap;
import java.util.Map;

import com.rs.mobile.common.L;


public class UrlUtil {
	private static final String LOG_TAG = UrlUtil.class.getSimpleName();
	/**
	 * 通过基础的url地址(不带?和任务参数)，请求参数，拼接成完成的请求地址，适合于GET和POST请求
	 * @param baseUrl
	 * @param params
	 * @return
	 */
	public static String concatUrlAndParams(String baseUrl, String params) {
		if(!StringUtil.isEmpty(params)) {
			return baseUrl + "?" + params;
		}
		return baseUrl;
	}
	
	/**
	 * 将不定长形式的参数转换为HTTP参数字符串（使用&连接）
	 * 出错时返回空字符串
	 * @param objects
	 * @return
	 */
	public static String createHttpParams(String ... stringObjects ) {
		if(stringObjects.length < 2 || stringObjects.length % 2 != 0) {
//			System.out.println("参数个数小于2或者参数个数不为偶数，退出。。。");
			return "";
		}
		Map<String, String> mapParams = CollectionUtil.createStringMap(stringObjects);
		if(mapParams != null) {
			return convertMapToHttpParams(mapParams);
		}
		
		return "";
	}
	/**
	 * 将数组形式的参数转换为HTTP参数字符串（使用&连接）
	 * 出错时返回空字符串
	 * @param objects
	 * @return
	 */
	public static String createHttpParamsFromArray(String[] stringObjects ) {
		if(stringObjects.length < 2 || stringObjects.length % 2 != 0) {
//			System.out.println("参数个数小于2或者参数个数不为偶数，退出。。。");
			return "";
		}
		Map<String, String> mapParams = CollectionUtil.createStringMap(stringObjects);
		if(mapParams != null) {
			return convertMapToHttpParams(mapParams);
		}
		
		return "";
	}
	
	/**
	 * 将Map形式的参数转换为HTTP参数字符串（使用&连接）
	 * @param params
	 * @return 出错时返回空字符串
	 */
	public static String convertMapToHttpParams(Map<String, String> params) {
		if(params == null || params.isEmpty()) {
		L.d( "convertMapToHttpParams() return an empty string");
			return "";
		}
		StringBuilder result = new StringBuilder();
		for(Map.Entry<String, String> entry : params.entrySet()) {
			result.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		//如果最后多一个&符号 返回移除最后一个&符号后的结果
		int index = result.lastIndexOf("&");
		if(result.length() - 1 == index) {
			L.d( "convertMapToHttpParams() return " + result.substring(0, index));
			return result.substring(0, index);
		}
		
		L.d( "convertMapToHttpParams() return " + result);
		return result.toString();
	}
	public static String convertMapToHttpParams1(Map<String, Object> params) {
		if(params == null || params.isEmpty()) {
		L.d( "convertMapToHttpParams() return an empty string");
			return "";
		}
		StringBuilder result = new StringBuilder();
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			result.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		//如果最后多一个&符号 返回移除最后一个&符号后的结果
		int index = result.lastIndexOf("&");
		if(result.length() - 1 == index) {
			L.d( "convertMapToHttpParams() return " + result.substring(0, index));
			return result.substring(0, index);
		}
		
		L.d( "convertMapToHttpParams() return " + result);
		return result.toString();
	}
	/**
	 * convertMapToHttpParams的反向方向
	 * @param params
	 * @return 出错时返回null
	 */
	public static Map<String, String> convertHttpParamsToMap(String params) {
		if(params == null || params.isEmpty()) {
			L.d("convertHttpParamsToMap() return null");
			return null;
		}
		String[] segmentKeyValue = params.split("&");
		Map<String, String> result = new HashMap<String, String>();
		for(String keyValue : segmentKeyValue) {
			String[] keyValueArray = keyValue.split("=");
			if(keyValueArray != null && keyValueArray.length == 2) {
				result.put(keyValueArray[0], keyValueArray[1]);
			} else {
				//SLog.w(LOG_TAG, "convertHttpParamsToMap() keyValueArray length != 2");
			}
		}
		
		return result.isEmpty() ? null : result;
	}
	
	private UrlUtil() {}
}
