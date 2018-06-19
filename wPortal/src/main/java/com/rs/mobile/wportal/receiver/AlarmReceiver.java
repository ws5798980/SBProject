package com.rs.mobile.wportal.receiver;

import com.rs.mobile.common.L;
import com.rs.mobile.wportal.service.YCService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent intent) {

		try {
			
			Intent i = new Intent(YCService.INTENT_FILTER_SEND_MY_LOCATION);
	        context.sendBroadcast(i);

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
    }

}
