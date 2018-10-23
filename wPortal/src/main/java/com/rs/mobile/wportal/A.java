package com.rs.mobile.wportal;

import java.util.List;
import java.util.Locale;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.SharedConfigUtils;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import com.rs.mobile.wportal.service.YCService;
import com.rs.mobile.ycaidl.IYCService;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;

public class A extends Application {

	private static A INSTANCE;

	// public static JSONArray branchArr;

	private static IYCService YC_SERVICE_BINDER = null;

	public static boolean LANGUAGE_STATUS = false;

	public static SharedConfigUtils sharedConfig;

	/**
	 * token 가져오기, 서비스가 연결되어 있지 않는 경우에는 서비스 연결을 재 요청 한다
	 * 
	 * @return
	 */
	public static String getId() {

		try {

			if (YC_SERVICE_BINDER != null) {

				return YC_SERVICE_BINDER.getId();

			}

			bindService();

		} catch (Exception e) {

			L.e(e);

		}

		return "";

	}

	/**
	 * 토큰 저장
	 *
	 */
	public static void setId(String id) {

		try {

			if (YC_SERVICE_BINDER != null) {

				YC_SERVICE_BINDER.setId(id);

			} else {

				bindService();

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * token 가져오기, 서비스가 연결되어 있지 않는 경우에는 서비스 연결을 재 요청 한다
	 * 
	 * @return
	 */
	public static String getToken() {

		try {

			if (YC_SERVICE_BINDER != null) {

				return YC_SERVICE_BINDER.getToken();

			}

			bindService();

		} catch (Exception e) {

			L.e(e);

		}

		return "";

	}

	/**
	 * 토큰 저장
	 * 
	 * @param token
	 */
	public static void setToken(String token) {

		try {

			if (YC_SERVICE_BINDER != null) {

				YC_SERVICE_BINDER.setToken(token);

			} else {

				bindService();

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 위치 정보 가져오기
	 * 
	 * @return
	 */
	public static Location getLocation() {

		try {

			if (YC_SERVICE_BINDER != null) {

				return YC_SERVICE_BINDER.getLocation();

			}

			bindService();

		} catch (Exception e) {

			L.e(e);

		}

		return null;

	}

	/**
	 * 서비스 연결
	 */
	private static ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

			YC_SERVICE_BINDER = null;

			bindService();

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub

			YC_SERVICE_BINDER = IYCService.Stub.asInterface(service);

		}
	};

	public static void bindService() {

		try {

			Intent service = new Intent(getBaseAppContext(), YCService.class);

			getBaseAppContext().startService(service);

			getBaseAppContext().bindService(service, connection,
					BIND_AUTO_CREATE);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void unBindService() {

		try {

			if (connection != null) {

				getBaseAppContext().unbindService(connection);

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void loadLanguage(Context context) {
		S.set(context, "LANGUEGE_STATUS", "0");
		// 应用内配置语言
		Resources resources = context.getResources();// 获得res资源对象
		Configuration config = resources.getConfiguration();// 获得设置对象
		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
		if (S.get(context, "SET_LANGUAGE", "0").equals("2")) {
			config.locale = Locale.KOREA; // 韩文
		} else if (S.get(context, "SET_LANGUAGE", "0").equals("1")) {
			//config.locale = Locale.SIMPLIFIED_CHINESE; // 简体中文
			config.locale = Locale.KOREA;
		} else {
			//config.locale = Locale.getDefault();// 系统默认
			config.locale = Locale.KOREA;
		}
		resources.updateConfiguration(config, dm);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		INSTANCE = this;
//		loadLanguage(this);
		try {
			init();
			// 在主进程设置信鸽相关的内容
			if (isMainProcess()) {
				// 为保证弹出通知前一定调用本方法，需要在application的onCreate注册
				// 收到通知时，会调用本回调函数。
				// 相当于这个回调会拦截在信鸽的弹出通知之前被截取
				// 一般上针对需要获取通知内容、标题，设置通知点击的跳转逻辑等等
				XGPushManager
						.setNotifactionCallback(new XGPushNotifactionCallback() {

							@Override
							public void handleNotify(XGNotifaction xGNotifaction) {
								Log.i("test", "处理信鸽通知：" + xGNotifaction);
								// 获取标签、内容、自定义内容
								String title = xGNotifaction.getTitle();
								String content = xGNotifaction.getContent();
								String customContent = xGNotifaction
										.getCustomContent();
								// 其它的处理
								// 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
								xGNotifaction.doNotify();
							}
						});
			}
		} catch (Exception e) {
			L.e(e);
		}

	}

	public static A getBaseAppContext() {
		return INSTANCE;
	}

	private void init() {

		Fresco.initialize(getApplicationContext(), ImageUtil
				.getDefaultImagePipelineConfig(getApplicationContext()));
		 	Config.DEBUG = true;
		 	QueuedWork.isUseThreadPool = false;
	        UMShareAPI.get(this);
	        PlatformConfig.setWeixin("wx8ab5a1f852d8663c", "b57c0626ccab1ef315b4ad7225e08da3");
	        PlatformConfig.setSinaWeibo("3859364376", "1f3a02ed0032b94a661bd9e8e8864754","http://pay."+(AppConfig.CHOOSE.equals("CN")?"osunggiga.cn":"osunggiga.com")+"");
	        PlatformConfig.setQQZone("1105955455", "sh0sSUOnMosPpbhZ");
//	        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
//	        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
//	        PlatformConfig.setAlipay("2015111700822536");
//	        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
//	        PlatformConfig.setPinterest("1439206");
//	        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
//	        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
//	        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
//	        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
//	        PlatformConfig.setYnote("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");

//		if((System.currentTimeMillis()/1000) > 1525163331){
//				System.exit(0);
//			}
		sharedConfig = SharedConfigUtils.getInstance(this);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();

		T.showToast(this, "onLowMemory");
	}

	public boolean isMainProcess() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = android.os.Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}
}