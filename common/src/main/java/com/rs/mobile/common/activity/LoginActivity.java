package com.rs.mobile.common.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.R;
import com.rs.mobile.common.S;
import com.rs.mobile.common.entity.LoginEntity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.EncryptUtils;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.Util;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;

import okhttp3.Request;

@SuppressLint("NewApi")
public class LoginActivity extends BaseActivity {

    // sms intent filter
    public static final String ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";

    private ImageView closeBtn;

    private ImageView login_tryangle, membership_tryangle;

    private RelativeLayout loginTabBtn, membershipTabBtn;

    private LinearLayout loginLayout, membershipLayout;

    private EditText idEditText, pwEditText;

    private TextView loginBtn;

    private EditText idEditTextM, pwEditTextM, pwEditTextMCheck, smsEditText, et_parent_id;

    private TextView smsBtn, membershipBtn;

    private TextView app_ver_login, app_ver_memvership;

    private TextView forget_password;

    private Message m = null;

    private CardView btnMember1, btnMember2, btnMember3, btnMember4, btnMember5, btnMember6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            setContentView(R.layout.activity_login_rs);

            initView();

            closeBtn = (ImageView) findViewById(R.id.close_btn);
            closeBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    finish();

                }
            });

            loginTabBtn = (RelativeLayout) findViewById(R.id.login_tab_btn);
            loginTabBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    try {
                        if (!loginLayout.isShown()) {
                            Animation inUnderAnimation = AnimationUtils
                                    .loadAnimation(LoginActivity.this,
                                            R.anim.scale_left_in);
                            Animation outUnderAnimation = AnimationUtils
                                    .loadAnimation(LoginActivity.this,
                                            R.anim.scale_left_out);
                            Animation inLayoutAnimation = AnimationUtils
                                    .loadAnimation(LoginActivity.this,
                                            R.anim.slide_left_in);
                            Animation outLayoutAnimation = AnimationUtils
                                    .loadAnimation(LoginActivity.this,
                                            R.anim.slide_left_out);

                            //loginUnderLine.startAnimation(inUnderAnimation);
                            //membershipUnderLine.startAnimation(outUnderAnimation);
                            loginLayout.startAnimation(inLayoutAnimation);
                            membershipLayout.startAnimation(outLayoutAnimation);

                            //loginUnderLine.setVisibility(View.VISIBLE);
                            //membershipUnderLine.setVisibility(View.GONE);
                            login_tryangle.setVisibility(View.VISIBLE);
                            loginLayout.setVisibility(View.VISIBLE);
                            membership_tryangle.setVisibility(View.INVISIBLE);
                            membershipLayout.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                        L.e(e);

                    }

                }
            });

            membershipTabBtn = (RelativeLayout) findViewById(R.id.membership_tab_btn);
            membershipTabBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub


                    Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                    intent.putExtra("title", getResources().getString(R.string.zhuce));
                    intent.putExtra("url", "http://join.gigaroom.com/10_Member/join_main");
                    startActivity(intent);

//
//
//
//					try {
//						if(!membershipLayout.isShown()){
//							Animation inUnderAnimation = AnimationUtils
//									.loadAnimation(LoginActivity.this,
//											R.anim.scale_right_in);
//							Animation outUnderAnimation = AnimationUtils
//									.loadAnimation(LoginActivity.this,
//											R.anim.scale_right_out);
//							Animation inLayoutAnimation = AnimationUtils
//									.loadAnimation(LoginActivity.this,
//											R.anim.slide_right_in);
//							Animation outLayoutAnimation = AnimationUtils
//									.loadAnimation(LoginActivity.this,
//											R.anim.slide_right_out);
//
//							//membershipUnderLine.startAnimation(inUnderAnimation);
//							//loginUnderLine.startAnimation(outUnderAnimation);
//							loginLayout.startAnimation(outLayoutAnimation);
//							membershipLayout.startAnimation(inLayoutAnimation);
//
//							//membershipUnderLine.setVisibility(View.VISIBLE);
//							//loginUnderLine.setVisibility(View.GONE);
//
//							loginLayout.setVisibility(View.GONE);
//							login_tryangle.setVisibility(View.INVISIBLE);
//							membership_tryangle.setVisibility(View.VISIBLE);
//							membershipLayout.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//
//						L.e(e);
//
//					}

                }
            });

            //loginUnderLine = (LinearLayout) findViewById(R.id.login_under_line);

            //membershipUnderLine = (LinearLayout) findViewById(R.id.membership_under_line);

            loginLayout = (LinearLayout) findViewById(R.id.login_layout);

            membershipLayout = (LinearLayout) findViewById(R.id.membership_layout);
            login_tryangle = (ImageView) findViewById(R.id.Login_Tryangle);
            membership_tryangle = (ImageView) findViewById(R.id.Member_Tryangle);

            idEditText = (EditText) findViewById(R.id.id_edt_text);
            idEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub

                    //checkLoginInput();

                }
            });

            pwEditText = (EditText) findViewById(R.id.pw_edt_text);
            pwEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub

                    //checkLoginInput();

                }
            });

            loginBtn = (TextView) findViewById(R.id.login_btn);
            loginBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    try {

                        final String id = idEditText.getText().toString();

                        String pw = pwEditText.getText().toString();

                        if (id == null || id.equals("")) {

                            t(getString(R.string.msg_empty_id));

                            return;

                        }

                        if (pw == null || pw.equals("")) {

                            t(getString(R.string.msg_empty_pw));

                            return;

                        }

                        login(id, pw);
//						HashMap<String, String> headers = new HashMap<>();
//						headers.put("Content-Type", "application/json;Charset=UTF-8");
//						HashMap<String, String> params = new HashMap<String, String>();
//
//						JSONObject j1=new JSONObject();
//						try {
//
//							j1.put(C.KEY_REQUEST_MEMBER_ID, id); //memid
//							j1.put(C.KEY_REQUEST_PW, encryption(pw)); //mempwd
//							j1.put(C.KEY_JSON_DEVICE_ID, Util.getDeviceId(LoginActivity.this));  //deviceNo
//							j1.put(C.KEY_S_ID,""); //s_id
//							j1.put(C.KEY_VER, "2"); //ver
//							j1.put(C.KEY_LANG_TYPE, "chn");  //lang_type
//							params.put("", j1.toString());
//
//							//Jason Type : memid,mempwd,deviceNo, s_id, ver, lang_type
//
//						} catch (JSONException e1) {
//							e1.printStackTrace();
//						}
//
//						OkHttpHelper helper = new OkHttpHelper(
//								LoginActivity.this);
//						helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
//
//							@Override
//							public void onNetworkError(Request request,
//									IOException e) {
//
//							}
//
//							@Override
//							public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
//								try {
//									if (flag.equals(C.VALUE_RESPONSE_SUCCESS) || flag.equals(C.VALUE_RESPONSE_SUCCESS_NUM)) {
//
//										if (C.INTERFACE_PARAMS == null) {
//
//											C.INTERFACE_PARAMS = new HashMap<String, String>();
//
//										}
//
//										C.INTERFACE_PARAMS.clear();
//
//										Iterator<String> keys = data.keys();
//
//										while (keys.hasNext()) {
//
//											String key = keys.next();
//											String value = data.getString(key);
//
//											C.INTERFACE_PARAMS.put(key, value);
//
//										}
//
//										// S.set(LoginActivity.this,
//										// C.KEY_JSON_TOKEN,
//										// data.getString(C.KEY_JSON_TOKEN));
//
//										//添加DEVICEID 加入token
//										String deviceid = Util.getDeviceId(LoginActivity.this) ;
//										if(deviceid != null && !deviceid.equals("")){
//											S.setShare(LoginActivity.this, C.KEY_JSON_TOKEN, data.getString(C.KEY_JSON_TOKEN) + "|"  + deviceid);
//										}else{
//											S.setShare(LoginActivity.this, C.KEY_JSON_TOKEN, data.getString(C.KEY_JSON_TOKEN));
//										}
//
////										S.setShare(LoginActivity.this, C.KEY_JSON_TOKEN, data.getString(C.KEY_JSON_TOKEN));
//
////										S.setShare(LoginActivity.this, C.KEY_REQUEST_MEMBER_ID, id);
//
//										S.setShare(LoginActivity.this, C.KEY_REQUEST_MEMBER_ID, data.getString("memid"));
//
//										if (data.has("custom_id"))
//											S.setShare(LoginActivity.this, C.KEY_SHARED_PHONE_NUMBER, data.getString("custom_id"));
//
//
//										//추천인
//										if (data.has(C.KEY_REQUEST_PARENT_ID)) {
//											S.setShare(LoginActivity.this, C.KEY_REQUEST_PARENT_ID, data.getString(C.KEY_REQUEST_PARENT_ID));
//											if(data.getString(C.KEY_REQUEST_PARENT_ID) == "1")
//											{
//												S.setShare(LoginActivity.this, C.KEY_REQUEST_CUSTOME_LEVEL1, data.getString(C.KEY_REQUEST_CUSTOME_LEVEL1));
//											} else {
//												S.setShare(LoginActivity.this, C.KEY_REQUEST_CUSTOME_LEVEL1, "null");
//											}
//										}
//
//
//
//										setResult(RESULT_OK);
//
//										xgPush();
//										finish();
//
//									}
//
//									t(data.getString("msg"));
//								} catch (Exception e) {
//									L.e(e);
//								}
//
//							}
//
//							@Override
//							public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
//
//							}
//						}, C.BASE_MEMBER_RS_URL + C.LOGIN_ENC_RS_URL, headers, j1.toString());
                    } catch (Exception e) {
                        L.e(e);
                    }
                }
            });

            app_ver_login = (TextView) findViewById(R.id.app_ver_login);

            forget_password = (TextView) findViewById(R.id.forget_password);
            forget_password.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            forget_password.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, ForgetPassWordActivity.class));
                }
            });
            app_ver_memvership = (TextView) findViewById(R.id.app_ver_memvership);

            try {

                // 자기 전화번호 가져오기
                TelephonyManager telephone = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String phoneNumber = telephone.getLine1Number();

                L.d("number : " + telephone.getSimSerialNumber());
                L.d("number : " + telephone.getLine1Number());

                if (phoneNumber != null) {

                    // 중국
                    if (phoneNumber.contains("+86"))
                        phoneNumber = phoneNumber.replace("+86", "");

                    // 한국
                    if (phoneNumber.contains("+82")) {

                        phoneNumber = phoneNumber.replace("+82", "");
                        phoneNumber = "0" + phoneNumber;
                    }

                } else {
                    phoneNumber = "";
                }

                idEditText.setText(phoneNumber);
                if (idEditTextM != null) {
                    idEditTextM.setText(phoneNumber);
                }

            } catch (Exception e) {

                L.e(e);

            }

            //버전 가져오기
            try {

                PackageManager pm = getPackageManager();

                PackageInfo info = null;

                info = pm.getPackageInfo(getApplicationContext().getPackageName(), 0);

                if (info != null) {

                    app_ver_login.setText(getResources().getString(R.string.app_name) +
                            "Version : v" + info.versionName + "(" + (C.BASE_URL.contains("portal.dxbhtm.com") ? "P" : "D") + ")");
                    if (app_ver_memvership != null) {
                        app_ver_memvership.setText(getResources().getString(R.string.app_name) +
                                "Version : v" + info.versionName + "(" + (C.BASE_URL.contains("portal.dxbhtm.com") ? "P" : "D") + ")");
                    }

                }

            } catch (Exception e) {

                L.e(e);

            }

        } catch (Exception e) {

            e(e);

        }

    }

    private void initView() {
        btnMember1 = (CardView) findViewById(R.id.btn_member1);
        btnMember2 = (CardView) findViewById(R.id.btn_member2);
        btnMember3 = (CardView) findViewById(R.id.btn_member3);
        btnMember4 = (CardView) findViewById(R.id.btn_member4);
        btnMember5 = (CardView) findViewById(R.id.btn_member5);
        btnMember6 = (CardView) findViewById(R.id.btn_member6);


        btnMember1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
                intent.putExtra("RegFlag", 1);
                startActivity(intent);
//				finish();
            }
        });
        btnMember2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
                intent.putExtra("RegFlag", 2);
                startActivity(intent);
//				finish();
            }
        });
        btnMember3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
                intent.putExtra("RegFlag", 3);
                startActivity(intent);
//				finish();
            }
        });
        btnMember4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
                intent.putExtra("RegFlag", 4);
                startActivity(intent);
//				finish();
            }
        });
        btnMember5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//				Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
//				intent.putExtra("RegFlag", 5);
//				startActivity(intent);
//				finish();
                dialog1();
            }
        });
        btnMember6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
                intent.putExtra("RegFlag", 6);
                startActivity(intent);
//				finish();
            }
        });
    }

    private void dialog1() {
        final Dialog dialog = new Dialog(this, R.style.LodingDialog);
        dialog.setContentView(R.layout.dialog_item_bg);
        ((TextView) dialog.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.tuanti_1));
        ((TextView) dialog.findViewById(R.id.tv_group1)).setText(getResources().getString(R.string.tuanti_2));
        ((TextView) dialog.findViewById(R.id.tv_group1)).setText(getResources().getString(R.string.tuanti_3));
        dialog.findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.rl_group1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.rl_group2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
                intent.putExtra("RegFlag", 53);
                startActivity(intent);
                dialog.dismiss();
//				finish();
            }
        });
        dialog.show();
    }

    private void dialog2() {
        final Dialog dialog = new Dialog(this, R.style.LodingDialog);
        dialog.setContentView(R.layout.dialog_item_bg);
        dialog.setContentView(R.layout.dialog_item_bg);
        ((TextView) dialog.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.shiye_1));
        ((TextView) dialog.findViewById(R.id.tv_group1)).setText(getResources().getString(R.string.shiye_2));
        ((TextView) dialog.findViewById(R.id.tv_group1)).setText(getResources().getString(R.string.shiye_3));
        dialog.findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.rl_group1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
                intent.putExtra("RegFlag", 51);
                startActivity(intent);
                dialog.dismiss();
//				finish();
            }
        });
        dialog.findViewById(R.id.rl_group2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Clause1Activity.class);
                intent.putExtra("RegFlag", 52);
                startActivity(intent);
                dialog.dismiss();
//				finish();
            }
        });
        dialog.show();
    }

    private void login(String tel, String pwd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("memid", tel);
        params.put("mempwd", EncryptUtils.SHA512(pwd));
        params.put("deviceNo", Util.getDeviceId(LoginActivity.this));

        OkHttpHelper okHttpHelper = new OkHttpHelper(LoginActivity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                LoginEntity entity = GsonUtils.changeGsonToBean(responseDescription, LoginEntity.class);
                if ("1".equals(entity.status)) {
                    S.set(LoginActivity.this, C.KEY_JSON_CUSTOM_CODE, entity.custom_code);
                    S.setShare(LoginActivity.this, C.KEY_JSON_CUSTOM_CODE, entity.custom_code);
                    S.set(LoginActivity.this, C.KEY_REQUEST_MEMBER_ID, entity.custom_code);
                    S.set(LoginActivity.this, C.KEY_JSON_CUSTOM_ID, entity.custom_id);
                    S.set(LoginActivity.this, C.KEY_JSON_CUSTOM_NAME, entity.custom_name);
                    S.set(LoginActivity.this, C.KEY_JSON_NICK_NAME, entity.nick_name);
//					C.KEY_JSON_TOKEN,
//							// data.getString(C.KEY_JSON_TOKEN));
//							data.getString(C.KEY_JSON_TOKEN) + "|"
//									+ data.getString("ssoId") + "|"
//									+ data.getString("custom_code")
//									+ "|" + Util.getDeviceId(context)
//					S.set(LoginActivity.this, C.KEY_JSON_TOKEN, entity.token + "|aaaaaa|" + entity.custom_code);
                    S.set(LoginActivity.this, C.KEY_JSON_TOKEN, entity.token + "|" + entity.ssoId + "|" + entity.custom_code + "|" + Util.getDeviceId(LoginActivity.this));
                    S.setShare(LoginActivity.this, C.KEY_JSON_TOKEN, entity.token + "|" + entity.ssoId + "|" + entity.custom_code + "|" + Util.getDeviceId(LoginActivity.this));
                    S.set(LoginActivity.this, C.KEY_JSON_PROFILE_IMG, entity.profile_img);
                    S.set(LoginActivity.this, C.KEY_JSON_DIV_CODE, entity.div_code);
                    S.set(LoginActivity.this, C.KEY_JSON_SSOID, entity.ssoId);

                    S.set(LoginActivity.this, C.KEY_JSON_SSO_REGIKEY, entity.sso_regiKey);
                    S.set(LoginActivity.this, C.KEY_JSON_MALL_HOME_ID, entity.mall_home_id);
                    S.set(LoginActivity.this, C.KEY_JSON_POINT_CARD_NO, entity.point_card_no);
                    S.set(LoginActivity.this, C.KEY_JSON_PARENT_ID, entity.parent_id);

//					AppConfig.custom_code = entity.custom_code;
//					AppConfig.custom_id = entity.custom_id;
//					AppConfig.custom_name = entity.custom_name;
//					AppConfig.nick_name = entity.nick_name;
//					AppConfig.token = entity.token + "|aaaaaa|" + entity.custom_code;
//					AppConfig.profile_img = entity.profile_img;
//					AppConfig.div_code = entity.div_code;
//					AppConfig.ssoId = entity.ssoId;
//					AppConfig.sso_regiKey = entity.sso_regiKey;
//					AppConfig.mall_home_id = entity.mall_home_id;
//					AppConfig.point_card_no = entity.point_card_no;
//					AppConfig.parent_id = entity.parent_id;
//
//					S.setShare(LoginActivity.this, C.KEY_JSON_TOKEN, entity.token);

                    Toast.makeText(LoginActivity.this, entity.msg, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, entity.msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://member.gigawon.co.kr:8808/api/Login/requestLoginCheck", GsonUtils.createGsonString(params));
    }

    /**
     * 2017.12.22 Jack Kim
     * checkLoginInput
     * 암호입력 EditText를 클릭하면 실행되는 함수
     * Background가 변경된다
     */
    public void checkLoginInput() {

        try {

            String id = idEditText.getText().toString();

            String pw = pwEditText.getText().toString();

            if (id != null && id.length() > 0 && pw != null && pw.length() > 0) {

                loginBtn.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.bg_login_n_btn));

            } else {

                loginBtn.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.bg_login_d_btn));

            }

        } catch (Exception e) {

            e(e);

        }

    }

    /**
     * checkMembershipInput
     */
    public void checkMembershipInput() {

        try {

            String id = idEditTextM.getText().toString();

            String pw = pwEditTextM.getText().toString();

            String pwCheck = pwEditTextMCheck.getText().toString();

            String sms = smsEditText.getText().toString();

            if (id != null && id.length() > 0 && pw != null && pw.length() > 0
                    && pwCheck != null && pwCheck.length() > 0 && sms != null && sms.length() > 0) {

                membershipBtn.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.bg_login_n_btn));

            } else {

                membershipBtn.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.bg_login_d_btn));

            }

        } catch (Exception e) {

            e(e);

        }

    }

    /**
     * receiver
     *
     * @author ZZooN
     */
    public class receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {

                if (intent.getAction().equals(ACTION_RECEIVE_SMS)) {

                    Bundle bundle = intent.getExtras();

                    String message = "";

                    if (bundle != null) {

                        Object[] pdus = (Object[]) bundle.get("pdus");

                        for (Object pdu : pdus) {

                            SmsMessage smsMessage = SmsMessage
                                    .createFromPdu((byte[]) pdu);
                            message += smsMessage.getMessageBody();

                        }

                    }

                    // if (message != null && mainFragment.phoneRequestNumber !=
                    // null) {
                    //
                    // if (message.contains(mainFragment.phoneRequestNumber)) {
                    //
                    // T.showToast(MainActivity.this,
                    // R.string.msg_cert_sms_completed);
                    //
                    // mainFragment.rPhoneEditTextC.setText(mainFragment.phoneRequestNumber);
                    //
                    // }
                    //
                    // }

                }

            } catch (Exception e) {

                L.e(e);

            }

        }

    }

    private String encryption(String userPassword) {

        MessageDigest md;

        String encritPassword = "";

        try {
            md = MessageDigest.getInstance("SHA-512");

            md.update(userPassword.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                encritPassword += s;
            }

        } catch (Exception e) {

            e(e);

        }

        return encritPassword;
    }

    /**
     * 푸시 요청
     */
    private void xgPush() {

        XGPushConfig.enableDebug(this, true);
        Handler handler = new HandlerExtension();
        m = handler.obtainMessage();
        // 注册接口

        XGPushManager.registerPush(this, S.getShare(this, C.KEY_SHARED_PHONE_NUMBER, ""), new XGIOperateCallback() {

            @Override
            public void onSuccess(Object data, int flag) {
                d("+++ register push sucess. token:" + data);
                m.obj = "+++ register push sucess. token:" + data;
                m.sendToTarget();

            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                d("+++ register push fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg);

                m.obj = "+++ register push fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg;
                m.sendToTarget();

            }
        });

    }

    private static class HandlerExtension extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
