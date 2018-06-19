package com.rs.mobile.common.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.adapter.PointsAdapter;
import com.rs.mobile.common.network.OkHttpHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class PointsActivity extends BaseActivity {
	
	private RelativeLayout[] relalist;
	
	private TextView[] textlist;
	
	private LinearLayout[] lineliss;
	
	private TextView text_mypoints;
	
	private LinearLayout close_btn;
	
	private PullToRefreshListView listview;
	
	private ArrayList<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

    private String type="1";
	
	private int PageIndex=1;
	
	private int PageSize=20; 
	
	private int total;
	
	private PointsAdapter adapter;
	
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(com.rs.mobile.common.R.layout.activity_points_rs);
		
		initView();
		
		changeState(0);
		
		getPointListView(type);
		
	}

	@SuppressWarnings("unchecked")
	private void initView() {
		tv=(TextView)findViewById(com.rs.mobile.common.R.id.tv);
		close_btn = (LinearLayout) findViewById(com.rs.mobile.common.R.id.close_btn);
		
		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		});
		
		listview = (PullToRefreshListView) findViewById(com.rs.mobile.common.R.id.lv_point);
		
		listview.setMode(Mode.BOTH);
		
		listview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				PageIndex=1;
				getPointListView(type);
	
			}
	
			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
//				if (condition) {
//					
//				}
				PageIndex++;
				getPointListView(type);
	
			}
	
		});
		text_mypoints = (TextView) findViewById(com.rs.mobile.common.R.id.text_mypoints);
		
		relalist = new RelativeLayout[3];
		
		relalist[0] = (RelativeLayout) findViewById(com.rs.mobile.common.R.id.relativeLayout_001);
		
		relalist[0].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				changeState(0);
				PageIndex=1;
				type="1";
				
				getPointListView(type);
				
			}
		});
		
		relalist[1] = (RelativeLayout) findViewById(com.rs.mobile.common.R.id.relativeLayout_002);
		
		relalist[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				changeState(1);
				PageIndex=1;
				type="2";
				
				getPointListView(type);
				
			}
		});
		
		relalist[2] = (RelativeLayout) findViewById(com.rs.mobile.common.R.id.relativeLayout_003);
		
		relalist[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				changeState(2);
				PageIndex=1;
				type="3";
				
				getPointListView(type);
				
			}
		});
		
		textlist = new TextView[3];
		
		textlist[0] = (TextView) findViewById(com.rs.mobile.common.R.id.textview_001);
		
		textlist[1] = (TextView) findViewById(com.rs.mobile.common.R.id.textview_002);
		
		textlist[2] = (TextView) findViewById(com.rs.mobile.common.R.id.textview_003);
		
		lineliss = new LinearLayout[3];
		
		lineliss[0] = (LinearLayout) findViewById(com.rs.mobile.common.R.id.underLine001);
		
		lineliss[1] = (LinearLayout) findViewById(com.rs.mobile.common.R.id.underLine002);
		
		lineliss[2] = (LinearLayout) findViewById(com.rs.mobile.common.R.id.underLine003);
		
	}

	private void changeState(int i) {
		
		for (int j = 0; j < relalist.length; j++) {
			
			if (i == j) {
				
				textlist[j].setTextColor(Color.parseColor("#21c043"));
				lineliss[j].setBackgroundColor(Color.parseColor("#21c043"));
				
			} else {
				
				textlist[j].setTextColor(Color.parseColor("#666666"));
				lineliss[j].setBackgroundColor(Color.parseColor("#ffffff"));
				
			}
		}
	}

	private void getPointListView(String type) {

		Map<String, String> params = new HashMap<String, String>();
		
		params.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		
		params.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));

		params.put("s_option", type);
		
		params.put("PageSize", PageSize+"");
		
		params.put("PageIndex", PageIndex+"");
		
		OkHttpHelper okHttpHelper = new OkHttpHelper(PointsActivity.this);
		
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				
				listview.onRefreshComplete();
				
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				
				listview.onRefreshComplete();

				try {
					
					if (data.get("status").toString().equals("1")) {
						listview.setVisibility(View.VISIBLE);
						tv.setVisibility(View.GONE);
						JSONObject js = data.getJSONObject("data");
						
						JSONObject js1 = js.getJSONObject("PointBalance");
						
						text_mypoints.setText(js1.get("tot_mysave").toString());
						
						JSONArray arr = js.getJSONArray("PointList");
						
						if (PageIndex==1) {
							listData.clear();
						}
						
						
						Map<String, Object> map;
						
						for (int i = 0; i < arr.length(); i++) {
							
							JSONObject j = arr.getJSONObject(i);
							
							map = new HashMap<String, Object>();
							
							map.put(C.KEY_JSON_FM_POINT_CONTENT, j.get("trade_type").toString());
							
							map.put(C.KEY_JSON_FM_POINT_TIME, j.get("trade_date").toString());
							
							map.put(C.KEY_JSON_FM_POINT_NUMBER, j.get("trade_money").toString());
							
							listData.add(map);
							
						}
						if (PageIndex==1) {
							adapter=new PointsAdapter(listData) ;
							listview.setAdapter(adapter);
						} else {
                           adapter.notifyDataSetChanged();
						}
                        

					}else {
						listview.setVisibility(View.GONE);
						tv.setVisibility(View.VISIBLE);
						
					}

				} catch (Exception e) {
					// TODO: handle exception
					
					e(e);
					
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				
				listview.onRefreshComplete();
				
			}
			
		}, C.URL_POINT, params);

	}
//
//	private void getPointListView() {
//		
//		listData = new ArrayList<Map<String, Object>>();
//		
//		Map<String, Object> map;
//		
//		for (int i = 0; i < 10; i++) {
//			
//			map = new HashMap<String, Object>();
//			
//			map.put(C.KEY_JSON_FM_POINT_CONTENT, "测试");
//			
//			map.put(C.KEY_JSON_FM_POINT_TIME, "2040_12_10");
//			
//			map.put(C.KEY_JSON_FM_POINT_NUMBER, "+25");
//			
//			listData.add(map);
//
//		}
//
//		listview.setAdapter(new SmPointAdapter(MyPointsActivity.this, listData));
//
//	}
}
