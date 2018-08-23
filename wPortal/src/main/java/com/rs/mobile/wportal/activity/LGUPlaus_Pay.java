package com.rs.mobile.wportal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Browser;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class LGUPlaus_Pay extends Activity {

    WebView mWeb;
    Toast toast = null;

    LinearLayout ll_item01;
    EditText et_url;
    Button btn_urlgo;

    private static final int LAUNCHED_ACTIVITY = 0;

    public String OrderNumber = "";
    public String OrderMoney = "";
    public String OrderUserName = "";
    public String GiftInfo = "";

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lguplaus__pay);

        /*intent.putExtra("OrderNumber", orderid);
        intent.putExtra("OrderMoney", real_amount);
        intent.putExtra("OrderUserName", name);
        intent.putExtra("GiftInfo","api새로 받을것");*/

        Intent intent = getIntent();
        OrderNumber = toUtf8(intent.getStringExtra("OrderNumber"));
        OrderMoney = toUtf8(intent.getStringExtra("OrderMoney"));
        OrderUserName = toUtf8(intent.getStringExtra("OrderUserName"));
        GiftInfo = toUtf8(intent.getStringExtra("GiftInfo"));


        mWeb = (WebView) findViewById(R.id.web);
        ll_item01 = (LinearLayout) findViewById(R.id.ll_item01);
        et_url = (EditText) findViewById(R.id.et_url);
        et_url.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // ??? ??µ? ?????? ???????? ã??? ?????? ??
                switch (actionId) {
                    case EditorInfo.IME_ACTION_GO:
                        btn_urlgo.performClick();
                        break;
                }
                return false;
            }
        });
        btn_urlgo = (Button) findViewById(R.id.btn_urlgo);
        btn_urlgo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mWeb.loadUrl(et_url.getText().toString());
                ll_item01.setVisibility(View.GONE);
            }
        });

        mWeb.setWebViewClient(new MyWebClient());
        mWeb.setWebChromeClient(new MyWebChromeClient());
        WebSettings set = mWeb.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        mWeb.addJavascriptInterface(new JavascriptInterface(), "myJSInterfaceName");


        /*OrderNumber = intent.getStringExtra("OrderNumber");
        OrderMoney = intent.getStringExtra("OrderMoney");
        OrderUserName = intent.getStringExtra("OrderUserName");
        GiftInfo = intent.getStringExtra("GiftInfo");*/

        String getParameter = "?OrderNumber=" + OrderNumber + "&OrderMoney=" + OrderMoney + "&OrderUserName=" + OrderUserName + "&GiftInfo=" + GiftInfo;

        mWeb.loadUrl("http://lgpay.gigawon.cn:8083" + getParameter); // ????URL?? ????????
    }

    class MyWebChromeClient extends WebChromeClient {
        ProgressBar pb_item01 = (ProgressBar) findViewById(R.id.pb_item01);

        public void onProgressChanged(WebView view, int progress) {
            pb_item01.setProgress(progress); // ProgressBar?? ????

            if (progress == 100) { // ??? ????? Progressbar?? ????
                pb_item01.setVisibility(View.GONE);
            } else {
                pb_item01.setVisibility(View.VISIBLE);
            }
        }

        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
            new AlertDialog.Builder(LGUPlaus_Pay.this).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setCancelable(false).create().show();
            // showToast(message);
            // result.confirm();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(LGUPlaus_Pay.this).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).create().show();
            return true;
        }
    }

    public void showAlert(String message, String positiveButton, DialogInterface.OnClickListener positiveListener, String negativeButton, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setPositiveButton(positiveButton, positiveListener);
        alert.setNegativeButton(negativeButton, negativeListener);
        alert.show();
    }

    class MyWebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            Log.d("================url::", url);
            if ((url.startsWith("http://") || url.startsWith("https://")) && url.endsWith(".apk")) {
                downloadFile(url);
                return super.shouldOverrideUrlLoading(view, url);
            } else if ((url.startsWith("http://") || url.startsWith("https://")) && (url.contains("market.android.com") || url.contains("m.ahnlab.com/kr/site/download"))) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent);
                    return true;
                } catch (ActivityNotFoundException e) {
                    return false;
                }
            } else if (url.startsWith("http://") || url.startsWith("https://")) {
                view.loadUrl(url);
                return true;
            } else if (url != null
                    && (url.contains("vguard") || url.contains("droidxantivirus") || url.contains("smhyundaiansimclick://")
                    || url.contains("smshinhanansimclick://") || url.contains("smshinhancardusim://") || url.contains("smartwall://") || url.contains("appfree://")
                    || url.contains("v3mobile") || url.endsWith(".apk") || url.contains("market://") || url.contains("ansimclick")
                    || url.contains("market://details?id=com.shcard.smartpay") || url.contains("shinhan-sr-ansimclick://"))) {
                return callApp(url);
            } else if (url.startsWith("smartxpay-transfer://")) {
                boolean isatallFlag = isPackageInstalled(getApplicationContext(), "kr.co.uplus.ecredit");
                if (isatallFlag) {
                    boolean override = false;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());

                    try {
                        startActivity(intent);
                        override = true;
                    } catch (ActivityNotFoundException ex) {
                    }
                    return override;
                } else {
                    showAlert("??????? ?????ø? ?????÷???? ???????.", "???", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(("market://details?id=kr.co.uplus.ecredit")));
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                    }, "???", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    return true;
                }
            } else if (url.startsWith("ispmobile://")) {
                boolean isatallFlag = isPackageInstalled(getApplicationContext(), "kvp.jjy.MispAndroid320");
                if (isatallFlag) {
                    boolean override = false;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());

                    try {
                        startActivity(intent);
                        override = true;
                    } catch (ActivityNotFoundException ex) {
                    }
                    return override;
                } else {
                    showAlert("??????? ?????ø? ?????÷???? ???????.", "???", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            view.loadUrl("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp");
                        }
                    }, "???", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    return true;
                }
            } else if (url.startsWith("paypin://")) {
                boolean isatallFlag = isPackageInstalled(getApplicationContext(), "com.skp.android.paypin");
                if (isatallFlag) {
                    boolean override = false;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());

                    try {
                        startActivity(intent);
                        override = true;
                    } catch (ActivityNotFoundException ex) {
                    }
                    return override;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(("market://details?id=com.skp.android.paypin&feature=search_result#?t=W251bGwsMSwxLDEsImNvbS5za3AuYW5kcm9pZC5wYXlwaW4iXQ..")));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
            } else if (url.startsWith("lguthepay://")) {
                boolean isatallFlag = isPackageInstalled(getApplicationContext(), "com.lguplus.paynow");
                if (isatallFlag) {
                    boolean override = false;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());

                    try {
                        startActivity(intent);
                        override = true;
                    } catch (ActivityNotFoundException ex) {
                    }
                    return override;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(("market://details?id=com.lguplus.paynow")));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
            } else {
                return callApp(url);
            }
        }

        // ??? ?? ???
        public boolean callApp(String url) {
            Intent intent = null;
            // ????? ????? ü? : 2014 .01???
            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                Log.e("intent getScheme     +++===>", intent.getScheme());
                Log.e("intent getDataString +++===>", intent.getDataString());
            } catch (URISyntaxException ex) {
                Log.e("Browser", "Bad URI " + url + ":" + ex.getMessage());
                return false;
            }
            try {
                boolean retval = true;
                //chrome ???? ??? : 2014.01 ???
                if (url.startsWith("intent")) { // chrome ???? ???
                    // ???? ü??? ????.
                    if (getPackageManager().resolveActivity(intent, 0) == null) {
                        String packagename = intent.getPackage();
                        if (packagename != null) {
                            Uri uri = Uri.parse("market://search?q=pname:" + packagename);
                            intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                            retval = true;
                        }
                    } else {
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setComponent(null);
                        try {
                            if (startActivityIfNeeded(intent, -1)) {
                                retval = true;
                            }
                        } catch (ActivityNotFoundException ex) {
                            retval = false;
                        }
                    }
                } else { // ?? ???
                    Uri uri = Uri.parse(url);
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    retval = true;
                }
                return retval;
            } catch (ActivityNotFoundException e) {
                Log.e("error ===>", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            int keyCode = event.getKeyCode();
            if ((keyCode == KeyEvent.KEYCODE_DPAD_LEFT) && mWeb.canGoBack()) {
                mWeb.goBack();
                return true;
            } else if ((keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) && mWeb.canGoForward()) {
                mWeb.goForward();
                return true;
            }
            return false;
        }

        // DownloadFileTask???? ?? ????
        private void downloadFile(String mUrl) {
            new DownloadFileTask().execute(mUrl);
        }

        // AsyncTask<Params,Progress,Result>
        private class DownloadFileTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... urls) {
                URL myFileUrl = null;
                try {
                    myFileUrl = new URL(urls[0]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();

                    // ??? ??? ?????? ???? sdcard/ ?? ?????? sdcard?? ????????? uses-permission??
                    // android.permission.WRITE_EXTERNAL_STORAGE?? ??????? ????.
                    String mPath = "sdcard/v3mobile.apk";
                    FileOutputStream fos;
                    File f = new File(mPath);
                    if (f.createNewFile()) {
                        fos = new FileOutputStream(mPath);
                        int read;
                        while ((read = is.read()) != -1) {
                            fos.write(read);
                        }
                        fos.close();
                    }

                    return "v3mobile.apk";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "";
                }
            }

            @Override
            protected void onPostExecute(String filename) {
                if (!"".equals(filename)) {
                    Toast.makeText(getApplicationContext(), "download complete", Toast.LENGTH_LONG).show();

                    // ??????? ????? ??????? ????? ???ø?????? ???.
                    File apkFile = new File(Environment.getExternalStorageDirectory() + "/" + filename);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    startActivity(intent);
                }
            }
        }
    }

    // App ü? ???? // ????:true, ????????????:false
    public static boolean isPackageInstalled(Context ctx, String pkgName) {
        try {
            ctx.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e("===============", "onNewIntent!!");
        if (intent != null) {
            if (Intent.ACTION_VIEW.equals(intent.getAction())) {
                Uri uri = intent.getData();
                Log.e("================uri", uri.toString());
                if (String.valueOf(uri).startsWith("ISP ??????????? ????????")) { // ISP ??????????? ????????
                    String result = uri.getQueryParameter("result");
                    if ("success".equals(result)) {
                        mWeb.loadUrl("javascript:doPostProcess();");
                    } else if ("cancel".equals(result)) {
                        mWeb.loadUrl("javascript:doCancelProcess();");
                    } else {
                        mWeb.loadUrl("javascript:doNoteProcess();");
                    }
                } else if (String.valueOf(uri).startsWith("??????ü ??????????? ????????")) { // ??????ü ??????????? ????????
                    // ??????ü?? WebView?? ??????? ???? ???? ??
                } else if (String.valueOf(uri).startsWith("paypin ??????????? ????????")) { // paypin ??????????? ????????
                    mWeb.loadUrl("javascript:doPostProcess();");
                } else if (String.valueOf(uri).startsWith("paynow ??????????? ????????")) { // paynow ??????????? ????????
                    // paynow?? WebView?? ??????? ???? ???? ??
                }
            }
        }
    }

    private void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(message);
            toast.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWeb.canGoBack()) {
            mWeb.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (ll_item01.getVisibility() == View.GONE) {
                ll_item01.setVisibility(View.VISIBLE);
            } else {
                ll_item01.setVisibility(View.GONE);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    final class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void callMethodName(final String str) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(), "hello!!! pay success", Toast.LENGTH_LONG).show();
                    Intent intentOrder = new Intent(LGUPlaus_Pay.this, MyOrderActivity.class);
                    startActivity(intentOrder);
                }
            });
        }
    }

    public static String toUtf8(String str) {
        String result = null;
        try {
            result = new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


}

