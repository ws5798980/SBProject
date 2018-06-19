package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.dp.DpFloorListAdapter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class DpFloorListActivity extends BaseActivity implements OnRefreshListener2<ListView>{
	
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private PullToRefreshListView sv_floorlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dp_floorlist);
		initToolbar();
		initView();
		initData();
	}
	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);
			iv_back = (LinearLayout) findViewById(R.id.iv_back);
			tv_title = (TextView) findViewById(R.id.tv_title);
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		tv_title.setText(getString(R.string.dp_text_allfloor));

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		sv_floorlist=(PullToRefreshListView)findViewById(R.id.sv_floorlist);
		sv_floorlist.setOnRefreshListener(this);
		sv_floorlist.setMode(Mode.PULL_FROM_START);
	}
	private void initData(){

		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();

		try {

			obj.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
			obj.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
			obj.put("lang_type", "chn");
			obj.put("div", S.getShare(getApplicationContext(), C.EXTRA_KEY_DIVCODE, "1"));
			obj1.put("myData", obj.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;Charset=UTF-8");
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpFloorListActivity.this);
		okHttpHelper.addPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
       sv_floorlist.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_floorlist.onRefreshComplete();

				try {
					JSONObject obj = new JSONObject();

					obj = new JSONObject(data.toString());
					
					JSONArray category = obj.getJSONArray("category");
											
					DpFloorListAdapter adapter=new DpFloorListAdapter(category, DpFloorListActivity.this);
			
			       sv_floorlist.setAdapter(adapter);
				} catch (Exception e) {
					// TODO: handle exception
					L.e(e);
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_floorlist.onRefreshComplete();

			}
		}, Constant.BASE_URL_DP + Constant.DP_GETHOMEPAGE, headers, obj.toString());
	
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		initData();
		
	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
}
