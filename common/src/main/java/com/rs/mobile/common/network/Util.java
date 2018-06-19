package com.rs.mobile.common.network;

import com.rs.mobile.common.D;
import com.rs.mobile.common.L;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;

public class Util {
	
	/**
	 * checkNetwork
	 * @param context
	 * @return network able
	 */
	public static boolean checkNetwork(final Context context) {
	   	
		try {
		
		    ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		    
		    if (networkInfo != null && networkInfo.isConnected()) {
		    	
		        return true;
		    	
		    }
		    
		    D.showDialog(context, -1, context.getString(com.rs.mobile.common.R.string.title_network_error), context.getString(com.rs.mobile.common.R.string.msg_network_error), context.getString(com.rs.mobile.common.R.string.finish), new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					((Activity)context).finish();
					
				}
			});
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return false;
		
	}
	
}
