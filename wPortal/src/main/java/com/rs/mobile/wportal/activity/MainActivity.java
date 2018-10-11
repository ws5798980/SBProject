package com.rs.mobile.wportal.activity;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.rt.RtEtJoinActivity;
import com.rs.mobile.wportal.activity.rt.RtSellerDetailActivity;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.wportal.fragment.IntroFragment;
import com.rs.mobile.wportal.fragment.MainFragment;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.rs.mobile.wportal.A;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.activity.dp.DpGoodsDetailActivity;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter.ItemClickListener;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import okhttp3.Request;

public class MainActivity extends BaseActivity {

	private JSONObject mainData = null;

	public static int newsHeight = 150;

	public static int displayHeight = 150;

	private IntroFragment introFragment;

	private MainFragment mainFragment;

	private Message m = null;

	public int closeCount = 2;

	public Handler closeHandler = new Handler();

	/**
	 * 인트로 화면 종료 Runnable
	 */
	public Runnable closeRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {
				if (introFragment.countTextView.getVisibility() == View.GONE)
					introFragment.countTextView.setVisibility(View.VISIBLE);


				if (closeCount < 1) {
					startMainFragment();

				} else {
					closeHandler.postDelayed(closeRunnable, 1000);


					closeCount = closeCount - 1;

					if (introFragment != null
							&& introFragment.countTextView != null) {

						// introFragment.countTextView.setText(getResources().getString(R.string.skip)
						// + " " + closeCount);

						introFragment.countTextView.setText(getResources()
								.getString(com.rs.mobile.wportal.R.string.skip));

					}

				}

			} catch (Exception e) {

				L.e(e);

				closeHandler.removeCallbacks(closeRunnable);

			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		// // 应用内配置语言
		// Resources resources = getResources();// 获得res资源对象
		// Configuration config = resources.getConfiguration();// 获得设置对象
		// DisplayMetrics dm = resources.getDisplayMetrics();//
		// 获得屏幕参数：主要是分辨率，像素等。
		// if (S.get(this, "SET_LANGUAGE", "0").equals("2")) {
		// config.locale = Locale.KOREA; // 韩文
		// } else if (S.get(this, "SET_LANGUAGE", "0")
		// .equals("1")) {
		// config.locale = Locale.SIMPLIFIED_CHINESE; // 简体中文
		// } else {
		// config.locale = Locale.getDefault();// 系统默认
		// }
		// resources.updateConfiguration(config, dm);
		// try {

		setContentView(com.rs.mobile.wportal.R.layout.activity_root);

		requestPermission();
		xgPush();
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// View decorView = getWindow().getDecorView();
		// int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
		// View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
		// | View.SYSTEM_UI_FLAG_FULLSCREEN;
		// decorView.setSystemUiVisibility(uiOptions);
		// }

		// } catch (Exception e) {
		//
		// L.e(e);
		//
		// }

	}

	private void initView() {

		try {

			A.bindService();

			introFragment = new IntroFragment();
			introFragment = new IntroFragment();

			mainFragment = new MainFragment();

			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();

			String shareUrl = getIntent().getStringExtra(C.KEY_INTENT_URL);

			if (shareUrl != null && !shareUrl.equals("")) {

				transaction.replace(com.rs.mobile.wportal.R.id.container, mainFragment);

			} else {
				transaction.replace(com.rs.mobile.wportal.R.id.container, introFragment);

			}

			transaction.addToBackStack(null);

			transaction.commit();

			DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

			displayHeight = displayMetrics.heightPixels;

			int height = displayMetrics.heightPixels / 4;

			newsHeight = (int) (height / 2 * 1.4);

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try {

			if (closeCount <= 0) {

				startMainFragment();

			}

		} catch (Exception e) {

			e(e);

		}

	}

	private static class HandlerExtension extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	}

	private void xgPush() {
		UtilClear.CheckLogin(this, new UtilCheckLogin.CheckListener() {

			@Override
			public void onDoNext() {
				XGPushConfig.enableDebug(MainActivity.this, true);
				Handler handler = new HandlerExtension();
				m = handler.obtainMessage();
				// 注册接口

				XGPushManager.registerPush(MainActivity.this, S.getShare(MainActivity.this,
						C.KEY_SHARED_PHONE_NUMBER, ""),
						new XGIOperateCallback() {

							@Override
							public void onSuccess(Object data, int flag) {
								d("+++ register push sucess. token:" + data);
								m.obj = "+++ register push sucess. token:"
										+ data;
								m.sendToTarget();

							}

							@Override
							public void onFail(Object data, int errCode,
									String msg) {
								d("+++ register push fail. token:" + data
										+ ", errCode:" + errCode + ",msg:"
										+ msg);

								m.obj = "+++ register push fail. token:" + data
										+ ", errCode:" + errCode + ",msg:"
										+ msg;
								m.sendToTarget();

							}
						});
			}
		},null,false);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		A.unBindService();

		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		Intent in = getIntent() ;
		if (intent.getIntExtra("status", 0) != 0) {
			D.showDialog(this, -1, "通知", intent.getStringExtra("message"),
					"确定", new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							D.alertDialog.dismiss();
						}
					});
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 请求过期后读取缓存数据
	 */
	private void loadCatch() {
		try {
			String obj = S.get(MainActivity.this, "indexCatch", "");
			if (!obj.equals("")) {
				mainData = new JSONObject(obj);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						draw();

					}
				});

			} else {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						T.showToast(MainActivity.this,
								getString(com.rs.mobile.wportal.R.string.msg_app_error));
					}
				});

				finish();
			}

		} catch (Exception e) {
			L.e(e);

		}

	}

	public void initData() {

		try {

			OkHttpHelper helper = new OkHttpHelper(this, false);

			HashMap<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_REQUEST_MEMBER_ID,
					S.getShare(this, C.KEY_REQUEST_MEMBER_ID, ""));
			params.put(C.KEY_JSON_TOKEN, S.getShare(this, C.KEY_JSON_TOKEN, ""));
			params.put("type", S.getShare(this, C.KEY_JSON_TOKEN, ""));

			Location location = A.getLocation();

			String lon, lat;

			if (location != null) {

				lon = "" + location.getLongitude();

				lat = "" + location.getLatitude();

			} else {

				// 기본 위치 설정
				lon = "" + "122.1712303162";

				lat = "" + "37.4192539597";
			}

			params.put("lon", lon);
			params.put("lat", lat);
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
					//
					// D.showDialog(MainActivity.this, -1,
					// getString(R.string.title_network_error),
					// getString(R.string.msg_init_network_error),
					// getString(R.string.finish),
					// new OnClickListener() {
					//
					// @Override
					// public void onClick(View v) {
					// // TODO Auto-generated method stub
					//
					// finish();
					//
					// }
					// }, false);

				}

				@Override
				public void onBizSuccess(String responseDescription,
						final JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								try {

									S.set(MainActivity.this, "indexCatch",
											data.toString());

									mainData = data;

									// div
									C.DIV_CODE = mainData.getString("divCode");

									C.DIV_NAME = mainData.getString("divName");

									// 신규 토근 재 발급
									String token = mainData
											.getString(C.KEY_JSON_TOKEN);

									if (C.INTERFACE_PARAMS == null) {

										C.INTERFACE_PARAMS = new HashMap<String, String>();

									}

									C.INTERFACE_PARAMS.clear();

									C.INTERFACE_PARAMS.put(C.MEMID, S.getShare(
											MainActivity.this,
											C.KEY_REQUEST_MEMBER_ID, ""));

									C.INTERFACE_PARAMS.put(C.KEY_JSON_TOKEN,
											token);

									if (token == null)
										token = "";

									S.setShare(MainActivity.this,
											C.KEY_JSON_TOKEN, token);

									A.setToken(token);

									A.setId(S.getShare(MainActivity.this,
											C.KEY_REQUEST_MEMBER_ID, ""));

									// 버전체크
									checkVersion();

								} catch (Exception e) {

									L.e(e);

									T.showToast(MainActivity.this,
											getString(com.rs.mobile.wportal.R.string.msg_app_error));

									finish();

								}

							}
						});

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {

					// T.showToast(MainActivity.this,
					// getString(R.string.msg_app_error));
					// finish();
					loadCatch();
				}

			}, C.BASE_URL + Constant.INTRO_JSON_URL, params);

		} catch (Exception e) {
			loadCatch();
			L.e(e);

		}

	}

	/**
	 * 공지사항
	 */
	public void checkNotice() {

		try {

			String notice = mainData.getString("notice");

			if (notice != null && !notice.equals("")) {

				final int savedNoticeVer = Integer.parseInt(S.get(
						MainActivity.this, C.KEY_SHARED_NOTICE_VER, "0"));

				final int newNoticeVer = Integer.parseInt(mainData
						.getString("notice_ver"));

				if (newNoticeVer > savedNoticeVer) {

					// 신규 공지사항 버전
					showDialog(getString(com.rs.mobile.wportal.R.string.notice), notice,
							getString(com.rs.mobile.wportal.R.string.check_never_see_again),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									D.alertDialog.dismiss();

									S.set(MainActivity.this,
											C.KEY_SHARED_NOTICE_VER, ""
													+ newNoticeVer);

									checkEvent();

								}
							}, getString(com.rs.mobile.wportal.R.string.ok), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									D.alertDialog.dismiss();

									checkEvent();

								}
							}, false);

					return;

				}

			}

		} catch (Exception e) {

			e(e);

		}

		checkEvent();

	}

	/**
	 * 버전 체크
	 * 
	 */
	public void checkVersion() {

		try {

			int AppVersion = 100;

			int ForceVersion = 100;

			int VersionName = 100;

			/*
			 * 버전 체크
			 */
			try {

				// 얩 버전
				AppVersion = Integer.parseInt(mainData.getString("android_ver")
						.replaceAll("\\.", ""));

				// 강제 업데이트 버전
				ForceVersion = Integer.parseInt(mainData.getString(
						"android_force_ver").replaceAll("\\.", ""));

				// apk의 버전
				VersionName = AppVersion;

				PackageManager pm = getPackageManager();

				PackageInfo info = null;

				info = pm.getPackageInfo(getApplicationContext()
						.getPackageName(), 0);

				if (info != null) {

					VersionName = Integer.parseInt(info.versionName.replaceAll(
							"\\.", ""));

				}

			} catch (Exception e) {

				e(e);

				AppVersion = 100;

				ForceVersion = 100;

				VersionName = 100;

			}

			if (VersionName < ForceVersion) { // 강제 업데이트

				showDialog(getString(com.rs.mobile.wportal.R.string.update),
						getString(com.rs.mobile.wportal.R.string.msg_force_update),
						getString(com.rs.mobile.wportal.R.string.update), new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								D.alertDialog.dismiss();

								goMarket();

								finish();

							}
						}, getString(com.rs.mobile.wportal.R.string.finish), new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								D.alertDialog.dismiss();

								finish();

							}
						}, false);

				return;

			} else {

				if (VersionName < AppVersion) { // 선택 업데이트

					showDialog(getString(com.rs.mobile.wportal.R.string.update),
							getString(com.rs.mobile.wportal.R.string.msg_update),
							getString(com.rs.mobile.wportal.R.string.update), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									goMarket();

									D.alertDialog.dismiss();

									finish();

								}
							}, getString(com.rs.mobile.wportal.R.string.run), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									D.alertDialog.dismiss();

									checkNotice();

								}
							}, false);

					return;

				}

			}

		} catch (Exception e) {

			L.e(e);

		}

		checkNotice();

	}

	/**
	 * 이벤트 체크
	 * 
	 * @param data
	 */
	private void checkEvent() {

		try {

			String eventUrl = mainData.getString("event_url");

			if (eventUrl != null && !eventUrl.equals("")) {

				final int savedEventVer = Integer.parseInt(S.get(
						MainActivity.this, C.KEY_SHARED_EVENT_VER, "0"));

				final int newEventVer = Integer.parseInt(mainData
						.getString("event_ver"));

				if (newEventVer > savedEventVer && newEventVer > 0) {

					Intent i = new Intent(MainActivity.this, WebActivity.class);

					i.putExtra(C.KEY_INTENT_URL, eventUrl);

					startActivityForResult(i, 3000);

					return;

				}

			}

		} catch (Exception e) {

			L.e(e);

		}

		draw();

	}

	/**
	 * 화면 그리기
	 */
	private void draw() {

		try {

			L.d("main draw");

			String shareUrl = getIntent().getStringExtra(C.KEY_INTENT_URL);

			if (shareUrl != null && !shareUrl.equals("")) {

				startMainFragment();

			} else {

				try {

					JSONArray arr = new JSONArray(mainData.get(
							C.KEY_JSON_BANNER).toString());

					if (arr == null || arr.length() == 0) {

						startMainFragment();

						return;

					}

				} catch (Exception e) {

					e(e);

				}

				// viewPager
				introFragment.viewPagerAdapter = new ViewPagerAdapter(
						(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
						mainData);
				introFragment.viewPagerAdapter
						.setOnItemClickListener(new ItemClickListener() {

							@Override
							public void onClick(Object obj) {
								// TODO Auto-generated method stub

								try {

									JSONObject jsonObject = (JSONObject) obj;

									String url = jsonObject
											.getString(C.KEY_JSON_URL);

									if (url.contains("main")) {

										startMainFragment();

									}

								} catch (Exception e) {

									L.e(e);

								}

							}
						});

				if (introFragment != null && introFragment.viewPager != null
						&& introFragment.viewPagerAdapter != null) {

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							closeHandler.postDelayed(closeRunnable, 1000);

							introFragment.viewPager
									.setAdapter(introFragment.viewPagerAdapter);

						}
					}, 1000);

				}

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 공유 기능 시작
	 * 
	 * @param shareUrl
	 */
	public void startShareActivity(String shareUrl) {

		try {

			/*
			 * 메신저에서 공유 기능으로 실행될때.
			 */
			String[] data = shareUrl.split("\\$");

			if (data[1].equals(C.TYPE_FRESH_MART)) {

				Bundle bundle = new Bundle();
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, data[2]);
				bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
				PageUtil.jumpTo(MainActivity.this, SmGoodsDetailActivity.class,
						bundle);

			} else if (data[1].equals(C.TYPE_PORTAL)) {

				Intent i = new Intent(MainActivity.this, WebActivity.class);
				i.putExtra(C.KEY_INTENT_URL, C.BASE_URL
						+ "/NewsView/News?NewsSeq=" + data[2]);
				startActivity(i);

			} else if (data[1].equals(C.TYPE_RESTRAUNT)) {

				Intent sellerDetailIntent = new Intent(this,
						RtSellerDetailActivity.class);
				sellerDetailIntent.putExtra("restaurantCode", data[2]);
				startActivity(sellerDetailIntent);

			} else if (data[1].equals(C.TYPE_EATING_TOGETHER)) {

				Intent etIntent = new Intent(this, RtEtJoinActivity.class);
				etIntent.putExtra("groupID", data[2]);
				startActivity(etIntent);

			} else if (data[1].equals(C.TYPE_DEPARTMENT_STORE)) {

				Bundle bundle = new Bundle();
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, data[2]);
				bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
				PageUtil.jumpTo(MainActivity.this, DpGoodsDetailActivity.class,
						bundle);

			}

			getIntent().putExtra(C.KEY_INTENT_URL, "");

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 인트로 종료, 메인 시작
	 */
	public void startMainFragment() {

		try {

			if (closeHandler != null && closeRunnable != null) {

				closeHandler.removeCallbacks(closeRunnable);

				closeHandler = null;

				closeRunnable = null;

			}

		} catch (Exception e) {

			e(e);

		}

		try {

			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();

			transaction.replace(com.rs.mobile.wportal.R.id.container, mainFragment);

			transaction.addToBackStack(null);

			transaction.commit();

		} catch (Exception e) {

			e(e);

		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {

		requestPermission(grantResults.length > 0
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED);

	}

	/**
	 * 권한 요청
	 * 
	 * @param granted
	 *            요청 성공 여부
	 */
	private void requestPermission(boolean granted) {

		try {

			if (granted == true) {

				requestPermission();

			} else {

				finish();

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 권한 요청
	 */
	private void requestPermission() {

		try {

			// 위치 서비스 요청
			if (ActivityCompat.checkSelfPermission(MainActivity.this,
					Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

				ActivityCompat
						.requestPermissions(
								MainActivity.this,
								new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
								C.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

				return;

			}

			// 파일 읽기 쓰기 요청
			if (ActivityCompat.checkSelfPermission(this,
					Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

				String[] PERMISSIONS_STORAGE = {
						Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE };

				ActivityCompat
						.requestPermissions(
								MainActivity.this,
								PERMISSIONS_STORAGE,
								C.PERMISSIONS_REQUEST_ACCESS_READ_WRITE_EXTERNAL_STORAGE);

				return;

			}

			// 카메라 사용 요청
			if (ActivityCompat.checkSelfPermission(this,
					Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

				ActivityCompat.requestPermissions(this,
						new String[] { Manifest.permission.CAMERA },
						C.PERMISSIONS_REQUEST_ACCESS_CAMERA);

				return;

			}

			// SMS 사용 요청
			if (ActivityCompat.checkSelfPermission(this,
					Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

				ActivityCompat.requestPermissions(this,
						new String[] { Manifest.permission.READ_PHONE_STATE },
						C.PERMISSIONS_REQUEST_ACCESS_READ_PHONE_STATE);

				return;

			}

			// 모든 권한 요청이 완료된 후에 실행한다.
			initView();

			initData();

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void getInstallAppFlag() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		OkHttpHelper okHttpHelper = new OkHttpHelper(MainActivity.this);
		okHttpHelper.addGetRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription,
					JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizFailure(String responseDescription,
					JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, "http://api.osunggiga.cn/imver001.txt", params);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// 이벤트 호출인 경우
		if (requestCode == 3000) {

			if (mainData == null) {

				initData();

			} else {

				draw();

			}

		} else {

			if (resultCode == Activity.RESULT_OK) {

				if (requestCode == 1000) {

					C.DIV_CODE = data.getStringExtra("divCode");

					C.DIV_NAME = data.getStringExtra("divName");

					mainFragment.refresh();

				} else if (requestCode == 2000) { // captureActivity

					CaptureUtil.handleResultScaning(MainActivity.this,
							data.getStringExtra("result"), "");

				}

			}

		}
	}

	/**
	 * 마켓으로 이동
	 */
	private void goMarket() {

		try {

			Uri uri = Uri.parse("market://details?id=" + getPackageName());
			Intent installIntent = new Intent(Intent.ACTION_VIEW, uri);
			installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(installIntent);

		} catch (Exception e) {

			L.e(e);

		}

	}

}