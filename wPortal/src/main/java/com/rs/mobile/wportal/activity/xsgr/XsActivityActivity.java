package com.rs.mobile.wportal.activity.xsgr;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rs.mobile.common.L;
import com.rs.mobile.wportal.R;

public class XsActivityActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_activity);


        initWebview("http://www.gigawon.co.kr:1314/QnA/sub_01");
    }

    private void initWebview(String url) {

        webView = (WebView) findViewById(R.id.web_view);

        // webView.getSettings().setJavaScriptEnabled(true);
        // webView.getSettings().setRenderPriority(RenderPriority.HIGH);
        // webView.getSettings().setAppCacheEnabled(true);
        // webView.getSettings().setDomStorageEnabled(true);
        // webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webView.getSettings().setAllowFileAccess(true);
        // webView.getSettings().setDisplayZoomControls(false);
        // webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
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
