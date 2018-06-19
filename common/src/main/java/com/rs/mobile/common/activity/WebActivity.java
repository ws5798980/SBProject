package com.rs.mobile.common.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.R;
import com.rs.mobile.common.S;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.view.ShareView;
import com.umeng.socialize.UMShareAPI;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WebActivity extends BaseActivity {

	// 0 :get, 1: post
	public int method = 0;

	public WebView webView;

	public View customView = null;

	public FrameLayout videoView;

	public Uri callUri;
	
	private long timeout = 10000;
	
	private ProgressBar web_progressBar ;
    private TextView title_text;

    private Handler mHandler = new Handler(){
    	
    	@Override
    	public void handleMessage(Message msg) {
    		super.handleMessage(msg);
    		if(msg.what == 1 && webView.getProgress() < 50){
    			showNoData(0, "网页没有加载出来，请检查网络", new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						webView.reload(); 
					}
				});
    			
    			
    		}else{
    			  web_progressBar.setVisibility(View.GONE);
    		}
    	}
    	
    };
    

    private Timer timer;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.common.R.layout.activity_web_lode);
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	            Window window = getWindow();
	            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	            window.setStatusBarColor(getResources().getColor(com.rs.mobile.common.R.color.umeng_blue));
	        }

		title_text= (TextView) findViewById(R.id.title_text);

		webView = (WebView) findViewById(com.rs.mobile.common.R.id.web_view);
		web_progressBar = (ProgressBar) findViewById(com.rs.mobile.common.R.id.web_progressBar);
		web_progressBar.setProgress(0);
		findViewById(com.rs.mobile.common.R.id.title_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				WebActivity.this.finish();
			}
		});
		videoView = (FrameLayout) findViewById(com.rs.mobile.common.R.id.video_view);
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);
		webView.getSettings().setDisplayZoomControls(false);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginState(PluginState.ON);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

		JavaScriptInterface JSInterface = new JavaScriptInterface(this); ////------
		webView.addJavascriptInterface(JSInterface, "sbapp");

		try {

			// 이전 버전에서 적용되지 않는다
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
				webView.getSettings().setMixedContentMode(
						WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

		} catch (Exception e) {

			L.e(e);

		}

		webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

		String url = getIntent().getStringExtra(C.KEY_INTENT_URL);
		String title=getIntent().getStringExtra("title");
		if(title!=null && !title.equals(""))
		{
			title_text.setText(title);
		}



		webView.setWebChromeClient(new WebChromeClient() {

			private CustomViewCallback customViewCallback;

			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				 if(newProgress == 100)
		            {
		                web_progressBar.setVisibility(View.GONE);
		            } else
		            {
		                if(web_progressBar.getVisibility() == View.GONE)
		                	web_progressBar.setVisibility(View.VISIBLE);
		                web_progressBar.setProgress(newProgress);
		            }
				
				
			}
			
			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				// super.onShowCustomView(view, callback);
				if (customView != null) {
					callback.onCustomViewHidden();
					return;
				}
				customView = view;
				customView.setVisibility(View.VISIBLE);
				customViewCallback = callback;
				videoView.addView(customView);
				videoView.setVisibility(View.VISIBLE);
				videoView.bringToFront();

				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

				getWindow().setFlags(
						WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}

			@Override
			public void onHideCustomView() {

				if (customView == null) {
					return;
				}

				try {
					customViewCallback.onCustomViewHidden();
				} catch (Exception e) {

					L.e(e);

				}
				customView.setVisibility(View.GONE);
				videoView.removeView(customView);
				customView = null;
				videoView.setVisibility(View.GONE);

				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				final WindowManager.LayoutParams attrs = getWindow()
						.getAttributes();
				attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
				getWindow().setAttributes(attrs);
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			}

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog,
					boolean isUserGesture, Message resultMsg) {
				// TODO Auto-generated method stub
				return super.onCreateWindow(view, isDialog, isUserGesture,
						resultMsg);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				// TODO Auto-generated method stub

				L.d("onJsAlert : " + url + " / " + message);

				showAlertDialog(getResources().getString(com.rs.mobile.common.R.string.alert),
						message, getResources().getString(com.rs.mobile.common.R.string.ok),
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								D.alertDialog.dismiss();

								result.confirm();

							}
						});
				D.alertDialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub

						result.cancel();

					}
				});
				return true;
			}

			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {
				// TODO Auto-generated method stub

				L.d("onJsConfirm : " + url + " / " + message);

				showDialog(getResources().getString(com.rs.mobile.common.R.string.alert), message,
						getResources().getString(com.rs.mobile.common.R.string.ok),
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								D.alertDialog.dismiss();

								result.confirm();

							}
						}, getResources().getString(com.rs.mobile.common.R.string.cancel),
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								D.alertDialog.dismiss();

								result.cancel();

							}
						});

				D.alertDialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub

						result.cancel();

                    }
				});

				return true;
			}

		});

		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub

				try {

					L.d("shouldOverrideUrlLoading : " + url);

					Uri uri = Uri.parse(url);

					String scheme = uri.getScheme();

					String host = uri.getHost();

					if (scheme.equals(C.SCHEME)) {

						if (host.equals(C.HOST_CALL_CLOSE_WEB)) {

							finish();

						} else if (host.equals(C.HOST_HISTORY_BACK)) {

							if (webView.canGoBack() == true) {

								webView.goBack();

							} else {

								finish();

							}

							return true;

						} else if (host.equals(C.HOST_CALL_SCRIPT)) {

							// yucheng://callScript?scriptName=callLogin&key=id&key=pw
							// web에서 요청하는 javascript 호출

							callScript(uri);

							// String scriptName =
							// uri.getQueryParameter(C.SCRIPT_NAME);
							//
							// String script = C.JAVA_SCRIPT + scriptName + "(";
							//
							// List<String> keys =
							// uri.getQueryParameters(C.KEY);
							//
							// if (keys != null) {
							//
							// for (int i = 0; i < keys.size(); i++) {
							//
							// String key = keys.get(i);
							//
							// String value = C.INTERFACE_PARAMS.get(key);
							//
							// if (value == null) {
							//
							// value = "";
							//
							// }
							//
							// if (i == 0) {
							//
							// script = script + "'" + value + "'";
							//
							// } else {
							//
							// script = script + ",'" + value + "'";
							//
							// }
							//
							// }
							//
							// }
							//
							// //javascript:callLogin('01012345678')
							//
							// script = script + ")";
							//
							// webView.loadUrl(script);

							return true;

						} else if (host.equals(C.HOST_CALL_SCRIPT_AFTER_LOGIN)) {

							// yucheng://callScript?scriptName=callLogin&key=id&key=pw
							// web에서 요청하는 javascript 호출

							if (UiUtil.checkLogin(WebActivity.this) == false) {

								callUri = uri;

								Intent i = new Intent(WebActivity.this,
										LoginActivity.class);
								startActivityForResult(i, 1001);

							} else {

								callScript(uri);

							}

							// String scriptName =
							// uri.getQueryParameter(C.SCRIPT_NAME);
							//
							// String script = C.JAVA_SCRIPT + scriptName + "(";
							//
							// List<String> keys =
							// uri.getQueryParameters(C.KEY);
							//
							// if (keys != null) {
							//
							// for (int i = 0; i < keys.size(); i++) {
							//
							// String key = keys.get(i);
							//
							// String value = C.INTERFACE_PARAMS.get(key);
							//
							// if (value == null) {
							//
							// value = "";
							//
							// }
							//
							// if (i == 0) {
							//
							// script = script + "'" + value + "'";
							//
							// } else {
							//
							// script = script + ",'" + value + "'";
							//
							// }
							//
							// }
							//
							// }
							//
							// //javascript:callLogin('01012345678')
							//
							// script = script + ")";
							//
							// webView.loadUrl(script);

							return true;

						} else if (host.equals(C.HOST_CALL_LOGIN)) {

							Intent i = new Intent(WebActivity.this,
									LoginActivity.class);
							startActivityForResult(i,
									C.ACTIVITY_REQUEST_CODE_LOGIN);

							return true;

						} else if (host.equals(C.HOST_SHARE_AS_MESSENGER)) {
							
							String param = uri
									.getQueryParameter(C.KEY_INTENT_URL);

							// yucheng://shareAsMessenger?url=
							// slapp://phone$
							// 8$
							// 112$
							// png$
							// title%E5%AE%87%E6%88%90%E6%9C%9D%E9%98%B3%E5%B9%BF%E5%9C%BA%E4%BF%A9%E6%9C%88%E9%94%80%E5%94%AE%E9%A2%9D%E8%B6%858%E4%BA%BF$%E6%91%98%E8%A6%81%EF%BC%9A%E2%80%9C%E9%87%91%E4%B9%9D%E9%93%B6%E5%8D%81%E2%80%9D%EF%BC%8C%E9%95%BF%E6%B2%99%EF%BC%8C%E6%88%BF%E5%9C%B0%E4%BA%A7%E5%B1%A1%E7%8E%B0%E4%BC%A0%E5%A5%87%E7%9A%84%E4%B8%A4%E4%B8%AA%E6%9C%88%E3%80%82%E9%95%BF%E6%B2%99%E5%AE%87%E6%88%90%E6%9C%9D%E9%98%B3%E5%B9%BF%E5%9C%BA%E7%9A%84%E9%94%80%E5%94%AE%E4%B8%9A%E7%BB%A9%E6%9B%B4%E6%98%AF%E5%82%B2%E8%A7%86%E4%BC%97%E9%9B%84%EF%BC%8C%E7%8B%AC%E5%8D%A0%E9%B3%8C%E5%A4%B4%E3%80%82%E9%95%BF%E6%B2%99%E5%AE%87%E6%88%90%E6%9C%9D%E9%98%B3%E5%B9%BF%E5%9C%BA%E7%BB%8F%E8%BF%87%E5%89%8D%E6%9C%9F%E5%8F%8D%E5%A4%8D%E7%AD%B9%E5%88%92%EF%BC%8C%E4%BB%8A%E5%B9%B4%E5%9B%9B%E6%9C%88%E4%B8%8B%E6%97%AC%E8%80%80%E4%B8%96%E5%85%A5%E5%B8%82%E3%80%82%E9%A6%96%E6%8E%A8%E4%BA%A7%E5%93%81%E5%8D%B3%E9%80%89%E6%8B%A9%E4%BA%86%E5%95%86%E4%B8%9A%E4%BB%B7%E5%80%BC%E6%9C%80%E9%AB%98%E7%9A%84%E5%85%AC%E5%AF%93%E6%A5%BCT1%E6%A0%8B%E3%80%82%E5%85%A5%E5%B8%827%E5%A4%A9%EF%BC%8C%E8%BF%98%E6%B2%A1%E7%AD%89%E5%BC%80%E5%8F%91%E5%95%86%E7%9A%84%E5%B7%A5%E4%BD%9C%E4%BA%BA%E5%91%98%E5%8F%8D%E2%80%A6%E2%80%A6

							String[] data = param.split("\\$");
//							UiUtil.share(WebActivity.this, data[1], data[2],
//									data[4], data[5], data[3]);
							ShareView.showPop(WebActivity.this, data[4], data[5],getIntent().getStringExtra(C.KEY_INTENT_URL) , data[3], com.rs.mobile.common.R.id.web_line,data[1], data[2]);
//							ShareView sv = new ShareView(WebActivity.this);
//							sv.showPop(R.id.web_line);
//							  showPop(R.id.web_line);
							return true;

						} else if (host.equals(C.HOST_CALL_CUSTOM_CENTER)) {

							// 고객센터 호출

							String phone = uri.getQueryParameter("centerNum");

							Intent intent = new Intent();
							Bundle bundle = new Bundle();
							bundle.putString("phone", phone);
							intent.setClassName("cn.ycapp.im",
									"cn.ycapp.im.ui.activity.CustomerActivity");
							intent.putExtras(bundle);
							startActivity(intent);

							return true;

						} else if (host.equals(C.HOST_WRITE_SHARED)) {

							// 파일에 쓰기

							String key = uri.getQueryParameter(C.KEY);

							String value = uri.getQueryParameter(C.VALUE);

							S.set(WebActivity.this, key, value);

							return true;

						}

					}

				} catch (Exception e) {

					e(e);

				}

				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(final WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub

//				showProgressBar();

				L.d("onPageStarted : " + url);

				super.onPageStarted(view, url, favicon);
				
				 timer = new Timer();
	                TimerTask tt = new TimerTask() {
	                    @Override
	                    public void run() {
	                        /*
	                         * 超时后,首先判断页面加载进度,超时并且进度小于100,就执行超时后的动作
	                         */
	                            Message msg = new Message();
	                            msg.what = 1;
	                            mHandler.sendMessage(msg);
	                        	hideProgressBar();
	                            timer.cancel();
	                            timer.purge();
	                    }
	                };
	                timer.schedule(tt, timeout, 1);
				
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				L.d("onPageFinished : " + url);
				 web_progressBar.setVisibility(View.GONE);
				super.onPageFinished(view, url);
				if(timer != null){
				  timer.cancel();
	                timer.purge();
				}
			}

			@Override
			public void onReceivedError(final WebView view, int errorCode,
					String description, String failingUrl) {
				showNoData(0, "网页没有加载出来，请检查网络", new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						view.reload(); 
					}
				});

				super.onReceivedError(view, errorCode, description, failingUrl);

			}

			@Override
			public void onReceivedSslError(final WebView view,
					SslErrorHandler handler, SslError error) {

				showNoData(0, "网页没有加载出来，请检查网络", new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						view.reload(); 
					}
				});

				super.onReceivedSslError(view, handler, error);
			}

		});

		d("url : " + url);

		method = getIntent().getIntExtra("method", 0);

		if (method == 0) {

			webView.loadUrl(url);

		} else {

			String postData = getIntent().getStringExtra("postData");

			webView.postUrl(url, postData.getBytes());

		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		webView.saveState(outState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		try {

			if (webView != null) {

				L.d("webView.getUrl() : " + webView.getUrl());

				if (webView.getUrl().contains("/DinnerTable/ReserveTableList")) {

					finish();

					return;

				}

				if (webView.canGoBack() == true) {

					webView.goBack();

					return;

				}

			}

		} catch (Exception e) {

			L.e(e);

		}

		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (resultCode == RESULT_OK) {

			if (requestCode == C.ACTIVITY_REQUEST_CODE_LOGIN) {

				webView.loadUrl(C.JAVA_SCRIPT
						+ "fncGetID('"
						+ S.getShare(WebActivity.this, C.KEY_REQUEST_MEMBER_ID,
								"") + "')");

			} else if (requestCode == 1001) {

				callScript(callUri);

			}else{
				  UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void callScript(Uri uri) {

		try {

			String scriptName = uri.getQueryParameter(C.SCRIPT_NAME);

			String script = C.JAVA_SCRIPT + scriptName + "(";

			List<String> keys = uri.getQueryParameters(C.KEY);

			if (keys != null) {

				for (int i = 0; i < keys.size(); i++) {

					String key = keys.get(i);

					String value = C.INTERFACE_PARAMS.get(key);

					if (value == null) {

						value = "";

					}

					if (i == 0) {

						script = script + "'" + value + "'";

					} else {

						script = script + ",'" + value + "'";

					}

				}

			}

			List<String> values = uri.getQueryParameters(C.VALUE);

			if (values != null) {

				for (int i = 0; i < values.size(); i++) {

					String value = values.get(i);

					if ((keys == null || keys.size() == 0) && i == 0) {

						script = script + "'" + value + "'";

					} else {

						script = script + ",'" + value + "'";

					}

				}

			}

			// javascript:callLogin('01012345678')

			script = script + ")";

			webView.loadUrl(script);

		} catch (Exception e) {

			L.e(e);

		}

	}
	public class JavaScriptInterface {
		Context mContext;

		JavaScriptInterface(Context c) {
			mContext = c;
		}

		@JavascriptInterface
		public void getLocation() {

		}

		@JavascriptInterface
		public void closeActivity() {
			finish();
		}
	}

	@Override
	protected void onStart() {
	    super.onStart();
	    if(isNeedRestart()){
	        Intent intent = new Intent(WebActivity.this, this.getClass());
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //清除栈顶的activity
	        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//不显示多余的动画，假装没有重新启动 //记得带需要的参数
	        startActivity(intent);
	    }

	}
	private boolean isNeedRestart(){

	    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
	    if (!tasks.isEmpty()) {
	        ComponentName topActivity = tasks.get(0).topActivity;
	        ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
	        if (topActivity.getPackageName().equals(getPackageName())) {
	        // 若当前栈顶界面是AssistActivity，则需要手动关闭
	            if (topActivity.getClassName().equals("com.tencent.connect.common.AssistActivity"))
	            return true;
	        }
	    }
	    return false;
	}
}
