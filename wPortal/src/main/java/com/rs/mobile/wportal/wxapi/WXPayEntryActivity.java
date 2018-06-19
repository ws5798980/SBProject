package com.rs.mobile.wportal.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

//import cn.rsapp.im.R;
//import cn.ycapp.im.ui.alipay.ExternalPartner;
//import cn.ycapp.im.App;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, "wx98da3da69fcf2bcc");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("TAG", "onPayFinish, errCode = " + resp.errCode);
        Toast.makeText(this,"微信支付返回信息"+resp.errCode+resp.errStr, Toast.LENGTH_SHORT).show();


        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if(resp.errCode==0)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("支付成功!");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        PageUtil.jumpTo(getApplicationContext(), MyOrderActivity.class);
                        finish();
                    }
                });
                builder.show();

                //PageUtil.jumpTo(getApplicationContext(), MyOrderActivity.class);

            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("微信支付失败!");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        PageUtil.jumpTo(getApplicationContext(), MyOrderActivity.class);
                        finish();
                    }
                });
                builder.show();
            }

        }
    }
}