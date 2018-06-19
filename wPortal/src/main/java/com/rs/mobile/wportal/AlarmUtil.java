package com.rs.mobile.wportal;

import com.rs.mobile.common.L;
import com.rs.mobile.wportal.receiver.AlarmReceiver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmUtil {
	
	private final static int ALARM_TIME = 60 * 1000 * 5;
	
//	private final static int ALARM_TIME = 5000;

    private static AlarmUtil instance;

    public static AlarmUtil getInstance() {
    	
        if (instance == null) instance = new AlarmUtil();
        
        return instance;
    }

    @SuppressLint("NewApi")
	public void startAlram(Context context) {

    	try {

    		//알람 on/off
//    		String isContinueAlram = S.get(context, C.KEY_SHARED_AGREE_GET_MY_LOCATION);
//    		
//    		if (isContinueAlram != null && !isContinueAlram.equals("")) {
    		
		    	Intent alarmIntent = new Intent(context, AlarmReceiver.class);
		        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
		
		        // AlarmManager 호출
		        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		        // 1분뒤에 AlarmOneMinuteBroadcastReceiver 호출 한다.
		        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_TIME, pendingIntent);
		        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_TIME, pendingIntent);
		        } else {
		            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_TIME, pendingIntent);
		        }
	        
//    		}
        
    	} catch (Exception e) {
    		
    		L.e(e);
    		
    	}
    	
    }

}
