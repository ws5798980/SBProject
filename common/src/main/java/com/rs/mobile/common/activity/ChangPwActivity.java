package com.rs.mobile.common.activity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.R;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;

import okhttp3.Request;

public class ChangPwActivity extends BaseActivity{
     private LinearLayout close_btn;
	private EditText id_edt_text_m0;
	private EditText id_edt_text_m;
	private EditText id_edt_text_m1;
	private TextView confirm_btn;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(com.rs.mobile.common.R.layout.activity_change_pw);
    	initView();
    }
    private void initView(){
    	close_btn=(LinearLayout)findViewById(com.rs.mobile.common.R.id.close_btn);
    	close_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    	id_edt_text_m0=(EditText)findViewById(com.rs.mobile.common.R.id.id_edt_text_m0);
    	id_edt_text_m0.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				checkInput();
			}
		});
		
    	id_edt_text_m=(EditText)findViewById(com.rs.mobile.common.R.id.id_edt_text_m);
    	id_edt_text_m.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				checkInput();
			}
		});
    	id_edt_text_m1=(EditText)findViewById(com.rs.mobile.common.R.id.id_edt_text_m1);
    	id_edt_text_m1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				checkInput();
			}
		});    	
    	confirm_btn=(TextView)findViewById(com.rs.mobile.common.R.id.confirm_btn);
    	confirm_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if (id_edt_text_m.getText().toString().equals(id_edt_text_m1.getText().toString())) {
				changpw();
			} else {
              t("两次密码输入不同");
			}	
			}
		});
    	
    }
    
    /**
	 * checkMembershipInput
	 */
	public void checkInput() {

		try {

			String oldpw = id_edt_text_m0.getText().toString();

			String npw1 = id_edt_text_m.getText().toString();
			
			String npw2 = id_edt_text_m1.getText().toString();

			

			if (oldpw!= null && oldpw.length() > 0 && npw1!= null && npw1.length() > 0
					&& npw2 != null && npw2.length() > 0 ) {

				confirm_btn.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.bg_rs_login_n_btn));
				confirm_btn.setEnabled(true);

			} else {

				confirm_btn.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.bg_rs_login_d_btn));
				confirm_btn.setEnabled(false);

			}

		} catch (Exception e) {

			e(e);

		}

	}
	private void changpw(){
		OkHttpHelper okHttpHelper=new OkHttpHelper(ChangPwActivity.this);
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;Charset=UTF-8");
		HashMap<String, String> paramsKeyValue=new HashMap<>();
		JSONObject j1=new JSONObject();
		try {
			
			j1.put("oriPassword", encryption(id_edt_text_m0.getText().toString()));
			
			j1.put("newPassword", encryption(id_edt_text_m.getText().toString()));
			j1.put("custom_code", S.get(getApplicationContext(), C.KEY_JSON_CUSTOM_CODE));
			paramsKeyValue.put("", j1.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
			
			@Override
			public void onNetworkError(Request request, IOException e) {
			}
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				try {
					if (data.get("status").toString().equals("1")) {
						
						finish();
					}
					t(data.getString("msg"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
			}
		}, C.CHANGE_PW, headers,j1.toString());
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
}
