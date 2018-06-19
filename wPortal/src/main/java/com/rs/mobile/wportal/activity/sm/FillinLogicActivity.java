package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class FillinLogicActivity extends BaseActivity {

	private Toolbar toolbar;

	private LinearLayout iv_back;

	private TextView tv_title;

	private EditText editText;

	private TextView text_commit;

	private String refundNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_filllogictis);

		refundNo = getIntent().getStringExtra("refundNo");

		initToolbar();

		initView();

	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);

			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);

			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					finish();

				}
			});

			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);

			tv_title.setText(getString(com.rs.mobile.wportal.R.string.sm_text017));

			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {

		editText = (EditText) findViewById(com.rs.mobile.wportal.R.id.text_logictisno);

		text_commit = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_commit);

		text_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				initData();

			}
		});
		;

	}

	private void initData() {

		OkHttpHelper okHttpHelper = new OkHttpHelper(FillinLogicActivity.this);

		HashMap<String, String> params = new HashMap<>();

		params.put("refundNo", refundNo);

		params.put("delivery_no", editText.getText().toString());

		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				try {

					String status = data.getString(C.KEY_JSON_STATUS);

					t(data.getString("message"));

					if (status.equals("1")) {

						finish();

					} else {

					}
				} catch (Exception e) {
					// TODO: handle exception

					e(e);

				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.SubmitRefundDeliveryInfo, params);
	}
}