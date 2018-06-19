package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class DpFillinLogicActivity extends BaseActivity {

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

		setContentView(R.layout.activity_sm_filllogictis);

		refundNo = getIntent().getStringExtra("refundNo");

		initToolbar();

		initView();

	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);

			iv_back = (LinearLayout) findViewById(R.id.iv_back);

			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					finish();

				}
			});

			tv_title = (TextView) findViewById(R.id.tv_title);

			tv_title.setText(getString(R.string.sm_text017));

			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {

		editText = (EditText) findViewById(R.id.text_logictisno);

		text_commit = (TextView) findViewById(R.id.text_commit);

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

		OkHttpHelper okHttpHelper = new OkHttpHelper(DpFillinLogicActivity.this);

		HashMap<String, String> params = new HashMap<>();

		params.put("refundNo", refundNo);

		params.put("delivery_no", editText.getText().toString());

		okHttpHelper.addSMPostRequest(new CallbackLogic() {

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
		}, Constant.BASE_URL_DP1 + Constant.SubmitRefundDeliveryInfo, params);
	}
}