package com.rs.mobile.common.util;



import com.rs.mobile.common.activity.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

/**
 * 页面切换和传送数据的工具类
 */
public class PageUtil {
	public static void jumpTo(final Context context, final Class<? extends BaseActivity> pageClassName) {
		jumpTo(context, pageClassName, null, 0);
	}
	
	public static void jumpTo(final Context context, final Class<? extends BaseActivity> pageClassName, final Bundle pageData) {
		jumpTo(context, pageClassName, pageData, 0);
	}
	
	public static void jumpTo(final Context context, final Class<? extends BaseActivity> pageClassName, final Bundle pageData, int delaySeconds) {
		if(delaySeconds == 0) {
			doJumpTo(context, pageClassName, pageData);
			return;
		}
		//延迟跳转
		new Handler().postDelayed(new Runnable(){    
		    public void run() {
		    	doJumpTo(context, pageClassName, pageData);
		    }    
		 }, delaySeconds * 1000L);
	}
	
	public static void jumpToWithFlag(final Context context, final Class<? extends BaseActivity> pageClassName) {
		jumpTo(context, pageClassName, null, 0, Intent.FLAG_ACTIVITY_SINGLE_TOP);
	}
	
	public static void jumpToWithFlag(final Context context, final Class<? extends BaseActivity> pageClassName, final Bundle pageData) {
		jumpTo(context, pageClassName, pageData, 0, Intent.FLAG_ACTIVITY_SINGLE_TOP);
	}
	
	public static void jumpTo(final Context context, final Class<? extends BaseActivity> pageClassName, final Bundle pageData, int delaySeconds, int flag) {
		if(delaySeconds == 0) {
			doJumpTo(context, pageClassName, pageData, flag);
			return;
		}
		//延迟跳转
		new Handler().postDelayed(new Runnable(){    
		    public void run() {
		    	doJumpTo(context, pageClassName, pageData);
		    }    
		 }, delaySeconds * 1000L);
	}
	
	private static void doJumpTo(Context context, final Class<? extends BaseActivity> pageClassName, Bundle pageData) {
		Intent intent = new Intent(context, pageClassName);
		if(pageData != null) {
			intent.putExtras(pageData);
		}
		context.startActivity(intent);
	}
	
	private static void doJumpTo(Context context, final Class<? extends BaseActivity> pageClassName, Bundle pageData, int flag) {
		Intent intent = new Intent(context, pageClassName);
		if(pageData != null) {
			intent.putExtras(pageData);
		}
		intent.addFlags(flag);
		context.startActivity(intent);
	}
	
	public static void callDial(Context context, String phoneNumber) {
	    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
	}

}