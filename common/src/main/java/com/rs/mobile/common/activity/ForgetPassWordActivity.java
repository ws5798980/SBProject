package com.rs.mobile.common.activity;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import okhttp3.Request;

public class ForgetPassWordActivity extends BaseActivity {

	private EditText id_edt_text;
	
	private EditText sms_edt_text_m;
	
	private TextView sms_btn;
	
	private EditText id_edt_text_m;
	
	private EditText id_edt_text_m1;
	
	private TextView confirm_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.common.R.layout.activity_forget_password);
		initView();
		
	}
    private void initView(){
    	
    	id_edt_text = (EditText)findViewById(com.rs.mobile.common.R.id.id_edt_text);
    	
    	sms_edt_text_m = (EditText)findViewById(com.rs.mobile.common.R.id.sms_edt_text_m);
    	
    	sms_btn = (TextView)findViewById(com.rs.mobile.common.R.id.sms_btn);
    	
    	sms_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					//인증 중...
					if (!sms_btn.getText().toString().equals(getString(com.rs.mobile.common.R.string.receive_vercode))) {
						
						return;
						
					}
					
					String id = id_edt_text.getText().toString();

					if (id == null || id.equals("")) {

						t(getString(com.rs.mobile.common.R.string.msg_empty_id));

						return;

					}

					HashMap<String, String> params = new HashMap<String, String>();
					params.put("HPNum", id);

					OkHttpHelper helper = new OkHttpHelper(ForgetPassWordActivity.this);
					helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

						@Override
						public void onNetworkError(Request request,
								IOException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
							// TODO Auto-generated method stub

							try {
								
								String status = data.getString("status");
								
								String msg = data.getString("msg");
								
								t(msg);
								
								if (status != null && status.equals("1")) {

									smsTimerCount = 60;
									
									sms_btn.setText(smsTimerCount + getString(com.rs.mobile.common.R.string.common1_text014));
									
									smsTimer.postDelayed(smsTimerRunnable, 1000);
									
								}
								
							} catch (Exception e) {
								
								L.e(e);
								
							}

						}

						@Override
						public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
							// TODO Auto-generated method stub

						}
					}, C.BASE_URL + C.CHANGE_PASSWORD_SMS, params);

				} catch (Exception e) {

					L.e(e);

				}

			}
		});
    	
    	id_edt_text_m = (EditText)findViewById(com.rs.mobile.common.R.id.id_edt_text_m);
    	
    	id_edt_text_m1 = (EditText)findViewById(com.rs.mobile.common.R.id.id_edt_text_m1);
    	
    	confirm_btn = (TextView)findViewById(com.rs.mobile.common.R.id.confirm_btn);
    	
    	confirm_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					String id = id_edt_text.getText().toString();

					String pw = id_edt_text_m.getText().toString();
					
					String pwCheck = id_edt_text_m1.getText().toString();

					String an = sms_edt_text_m.getText().toString();

					if (id == null || id.equals("")) {

						t(getString(com.rs.mobile.common.R.string.msg_empty_id));

						return;

					}

					if (pw == null || pw.equals("")) {

						t(getString(com.rs.mobile.common.R.string.msg_empty_pw));

						return;

					}
					
					if (pwCheck == null || pwCheck.equals("")) {

						t(getString(com.rs.mobile.common.R.string.msg_pls_input_yourpassword_check));

						return;

					}

					if (an == null || an.equals("")) {

						t(getString(com.rs.mobile.common.R.string.msg_empty_sms));

						return;

					}
					
					if (!pw.equals(pwCheck)) {
						
						t(getString(com.rs.mobile.common.R.string.msg_equals_pw));
						
						return;
					}

					HashMap<String, String> params = new HashMap<String, String>();
					params.put("HPNum", id);
					params.put("Password", encryption(pw));
					params.put("AuthNum", an);

					OkHttpHelper helper = new OkHttpHelper(ForgetPassWordActivity.this);
					helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

						@Override
						public void onNetworkError(Request request,
								IOException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
							// TODO Auto-generated method stub

							try {
								
								String status = data.getString("status");
								
								String msg = data.getString("msg");
								
								t(msg);
								
								if (status != null && status.equals("1")) {

									finish();
									
								}
								
							} catch (Exception e) {
								
								L.e(e);
								
							}

						}

						@Override
						public void onBizFailure(
								String responseDescription,
								JSONObject data, String responseCode) {
							// TODO Auto-generated method stub

						}
					}, C.BASE_URL + C.SET_PASSWORD, params);

				} catch (Exception e) {

					L.e(e);

				}

			}
		});
    	
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

    private int smsTimerCount = 60;
    
    private Handler smsTimer = new Handler();
    
    private Runnable smsTimerRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if (smsTimerCount > 0) {
				
				smsTimerCount--;
				
				sms_btn.setText(smsTimerCount + getString(com.rs.mobile.common.R.string.common1_text014));
				
				smsTimer.postDelayed(smsTimerRunnable, 1000);
				
			} else {
				
				smsTimerCount = 60;
				
				sms_edt_text_m.setText("");
				
				sms_btn.setText(getString(com.rs.mobile.common.R.string.receive_vercode));
				
				smsTimer.removeCallbacks(smsTimerRunnable);
				
			}
			
		}
	};
    
}