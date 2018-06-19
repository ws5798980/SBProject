package com.rs.mobile.wportal.fragment.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.SmViewPagerAdapter1;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import okhttp3.Request;

public class GoodsFragment2 extends Fragment {

	private WebView webView;

	private String item_code;

	private View rootView;

	private String div_code;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		rootView = inflater.inflate(com.rs.mobile.wportal.R.layout.activity_web, container, false);// 关联布局文件
		Bundle bundle = getArguments();
		item_code = bundle.getString(C.KEY_JSON_FM_ITEM_CODE);
		div_code = bundle.getString(C.KEY_DIV_CODE);

		initdata();

		return rootView;

	}

	public static GoodsFragment2 newInstance(String s, String div_code) {

		GoodsFragment2 myFragment = new GoodsFragment2();
		Bundle bundle = new Bundle();
		bundle.putString(C.KEY_JSON_FM_ITEM_CODE, s);
		bundle.putString(C.KEY_DIV_CODE, div_code);
		myFragment.setArguments(bundle);
		return myFragment;
	}

	private void initdata() {

		HashMap<String, String> param = new HashMap<String, String>();
		param.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
		param.put("div_code", div_code);
		param.put("userId", S.getShare(getContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		param.put(C.KEY_JSON_FM_SUPPLIER_ID, "yc01");

		OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			private SmViewPagerAdapter1 adapter;

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {

					JSONObject jsonObject = data.getJSONObject(C.KEY_JSON_DATA);
					initWebview(rootView, jsonObject.getString("linkUrl"));

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP1 + Constant.GET_GOODSOFDETAILS, param);
	}

	private void initWebview(View rootView, String url) {

		webView = (WebView) rootView.findViewById(com.rs.mobile.wportal.R.id.web_view);

		// webView.getSettings().setJavaScriptEnabled(true);
		// webView.getSettings().setRenderPriority(RenderPriority.HIGH);
		// webView.getSettings().setAppCacheEnabled(true);
		// webView.getSettings().setDomStorageEnabled(true);
		// webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		// webView.getSettings().setAllowFileAccess(true);
		// webView.getSettings().setDisplayZoomControls(false);
		// webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);
		webView.getSettings().setDisplayZoomControls(false);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginState(PluginState.ON);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {

				// TODO Auto-generated method stub
				return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				// TODO Auto-generated method stub

				try {

				} catch (Exception e) {

				}

				return true;
			}

			@Override
			public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
				// TODO Auto-generated method stub

				try {

				} catch (Exception e) {

				}

				return true;
			}

		});

		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub

				try {

					L.d("shouldOverrideUrlLoading : " + url);

				} catch (Exception e) {

				}

				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub

				L.d("onPageStarted : " + url);

				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub

				L.d("onPageFinished : " + url);

				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// TODO Auto-generated method stub

				view.loadUrl("about:blank");

				super.onReceivedError(view, errorCode, description, failingUrl);

			}

			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub

				view.loadUrl("about:blank");

				super.onReceivedSslError(view, handler, error);
			}

		});

		// webView.getSettings().setUseWideViewPort(true);
		// webView.getSettings().setLoadWithOverviewMode(true);
		// webView.loadUrl("http://pic2.ooopic.com/12/67/17/40bOOOPICa5_1024.jpg");
		webView.loadUrl(url);

	};
}
