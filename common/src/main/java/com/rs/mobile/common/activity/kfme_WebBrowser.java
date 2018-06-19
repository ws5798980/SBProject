package com.rs.mobile.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.rs.mobile.common.R;

public class kfme_WebBrowser extends Activity {

    private String urls  = "";
    private WebView wv_webActive;
    private TextView tv_closebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kfme__web_browser);

        wv_webActive = (WebView)findViewById(R.id.webActive);
        tv_closebtn = (TextView)findViewById(R.id.closebtn);

        Intent intent = getIntent();
        urls = intent.getExtras().getString("url");

        wv_webActive.loadUrl(urls);

        tv_closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
