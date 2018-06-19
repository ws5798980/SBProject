
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.CollectionUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.ClassifyAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import okhttp3.Request;

public class SmClassifyActivity extends BaseActivity {

	private GridView gridView;

	private LinearLayout close_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_classify);
		gridView = (GridView) findViewById(R.id.grid_view);
		close_btn = (LinearLayout) findViewById(R.id.close_btn);
		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});
		getClassify();
	}

	private void getClassify() {

		Map<String, String> param = new HashMap<String, String>();
		param.put("appType", "6");
		OkHttpHelper okHttpHelper = new OkHttpHelper(SmClassifyActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);
					final List<Map<String, String>> listdata = CollectionUtil.jsonArrayToListMap(arr);

					gridView.setAdapter(new ClassifyAdapter(listdata, SmClassifyActivity.this));
					gridView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							// TODO Auto-generated method stub
							L.d(arg2 + "====");
							Bundle bundle = new Bundle();
							bundle.putString("level", listdata.get(arg2).get("level").toString());
							bundle.putString(C.KEY_JSON_FM_CATEGORY_CODE,
									listdata.get(arg2).get(C.KEY_JSON_FM_CATEGORY_CODE).toString());
							PageUtil.jumpTo(SmClassifyActivity.this, SmClassifyResultActivity.class, bundle);
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GET_CATEGORY_LIST, param);
	}
}
