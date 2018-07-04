package com.rs.mobile.common.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.umeng.analytics.MobclickAgent;

import java.util.Locale;

/**
 * 将BaseActivity改成继承于{@link AppCompatActivity} modify by zhaoyun
 *
 * @author Lee
 * @date 2017-3-11
 */
@SuppressLint("NewApi")
public class BaseActivity extends AppCompatActivity {

    private Receiver receiver;

    protected void showNoData() {
        findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.VISIBLE);
    }

    protected void hideNoData() {
        findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.GONE);
    }

    protected void showNoData(int drawable, String text,
                              final OnClickListener onClickListener) {
        findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.VISIBLE);
        ImageView img = (ImageView) findViewById(com.rs.mobile.common.R.id.no_data_img);
        TextView no_data_text = (TextView) findViewById(com.rs.mobile.common.R.id.no_data_text);
        final TextView no_data_btn = (TextView) findViewById(com.rs.mobile.common.R.id.no_data_btn);

        if (drawable != 0)
            img.setImageResource(drawable);
        if (!text.equals(""))
            no_data_text.setText(text);
        if (onClickListener != null) {
            no_data_btn.setVisibility(View.VISIBLE);
            no_data_btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.GONE);
                    onClickListener.onClick(arg0);
                }
            });
        }

    }

    protected void showNoData(String text,
                              final OnClickListener onClickListener) {
        findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.VISIBLE);
        ImageView img = (ImageView) findViewById(com.rs.mobile.common.R.id.no_data_img);
        img.setVisibility(View.GONE);
        TextView no_data_text = (TextView) findViewById(com.rs.mobile.common.R.id.no_data_text);
        final TextView no_data_btn = (TextView) findViewById(com.rs.mobile.common.R.id.no_data_btn);


        if (!text.equals(""))
            no_data_text.setText(text);
        if (onClickListener != null) {
            no_data_btn.setVisibility(View.VISIBLE);
            no_data_btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.GONE);
                    onClickListener.onClick(arg0);
                }
            });
        }

    }

    protected void showData() {
        findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.GONE);
        findViewById(com.rs.mobile.common.R.id.no_data_text).setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 应用内配置语言
//		if(S.get(this, "LANGUEGE_STATUS", "0").equals("1")){
//		Resources resources = getResources();// 获得res资源对象
//		Configuration config = resources.getConfiguration();// 获得设置对象
//		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
//		if (S.get(this, "SET_LANGUAGE", "0").equals("2")) {
//			config.locale = Locale.KOREA; // 韩文
//		} else if (S.get(this, "SET_LANGUAGE", "0").equals("1")) {
//			//config.locale = Locale.SIMPLIFIED_CHINESE; // 简体中文
//			config.locale = Locale.KOREA;
//		} else {
//			config.locale = Locale.getDefault();// 系统默认
//		}
//		resources.updateConfiguration(config, dm);
//		}
        receiver = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_RECEIVE_FINISH);
        filter.addAction(ACTION_RECEIVE_LOCATION_DIALOG);
        registerReceiver(receiver, filter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Log.i("xyz", getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {

        try {

            unregisterReceiver(receiver);

            D.hideProgressDialog();

            if (D.alertDialog != null && D.alertDialog.isShowing() == true
                    && D.alertDialog.getOwnerActivity() == this) {

                D.alertDialog.dismiss();

            }

        } catch (Exception e) {

            L.e(e);

        }

        super.onDestroy();
    }

    /**
     * showToast
     *
     * @param msg
     */
    public void t(String msg) {

        T.showToast(BaseActivity.this, msg);

    }

    /**
     * d
     *
     * @param msg
     */
    public void d(String msg) {

        L.d(msg);

    }

    /**
     * e
     *
     * @param e
     */
    public void e(Exception e) {

        L.e(e);

    }

    /**
     * showDialog
     *
     * @param title
     * @param msg
     * @param selectText
     * @param selectListener
     */
    public void showAlertDialog(String title, String msg, String selectText,
                                OnClickListener selectListener) {

        D.showAlertDialog(BaseActivity.this, -1, title, msg, selectText,
                selectListener);

    }

    public void showDialog(String title, String msg, String selectText,
                           OnClickListener selectListener) {

        D.showDialog(BaseActivity.this, -1, title, msg, selectText,
                selectListener);

    }

    public void showDialog(String title, String msg, String okText,
                           OnClickListener okListener, String cancelText,
                           OnClickListener cancelListener) {

        D.showDialog(BaseActivity.this, -1, title, msg, okText, okListener,
                cancelText, cancelListener);

    }

    public void showDialog(String title, String msg, String okText,
                           OnClickListener okListener, String cancelText,
                           OnClickListener cancelListener, boolean cancelAble) {

        D.showDialog(BaseActivity.this, -1, title, msg, okText, okListener,
                cancelText, cancelListener, cancelAble);

    }

    public void showSingleChoiceDialog(int icon, int title, int msg,
                                       int posButton, int negButton,
                                       DialogInterface.OnClickListener posListener,
                                       DialogInterface.OnClickListener negListener, CharSequence[] items,
                                       int selectedPosition,
                                       DialogInterface.OnClickListener selectListener, boolean cancelAble) {

        D.showSingleChoiceDialog(BaseActivity.this, icon, title, msg,
                posButton, negButton, posListener, negListener, items,
                selectedPosition, selectListener, cancelAble);

    }

    public void showEditTextDialog(String title, String hint, String msg,
                                   String selectText, OnClickListener selectListener,
                                   String cancelText, OnClickListener cancelListener) {

        D.showEditTextDialog(BaseActivity.this, -1, title, hint, msg,
                selectText, selectListener, cancelText, cancelListener);

    }

    public void showProgressBar() {

        D.showProgressDialog(BaseActivity.this, "", true);

    }

    public void hideProgressBar() {

        D.hideProgressDialog();

    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return int screen width by px
     */
    public static int get_windows_width(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return int screen height by px
     */
    public static int get_windows_height(Context context) {

        return context.getResources().getDisplayMetrics().heightPixels;
    }

    // listview 高度计算
    public void setListViewHeight(ListView listView) {
        if (listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }

    public final static String ACTION_RECEIVE_FINISH = "com.rs.mobile.wportal.action_finish";

    public final static String ACTION_RECEIVE_LOCATION_DIALOG = "com.rs.mobile.wportal.action_location_dialog";

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {

                if (intent.getAction().equals(ACTION_RECEIVE_FINISH)) {

                    finish();

                } else if (intent.getAction().equals(
                        ACTION_RECEIVE_LOCATION_DIALOG)
                        && C.alreadyOpenLocationDialog == false) {

                    C.alreadyOpenLocationDialog = true;

                    showDialog(
                            getString(com.rs.mobile.common.R.string.gps_msg_title),
                            getString(com.rs.mobile.common.R.string.gps_msg),
                            getString(com.rs.mobile.common.R.string.gps_setting_go),
                            new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub

                                    D.alertDialog.dismiss();
                                    Intent intent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);

                                }
                            }, getString(com.rs.mobile.common.R.string.gps_setting_cancel),
                            new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    D.alertDialog.dismiss();
                                }
                            });

                }

            } catch (Exception e) {

                L.e(e);

            }

        }

    }

}
