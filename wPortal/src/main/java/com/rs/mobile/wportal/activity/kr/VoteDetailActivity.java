package com.rs.mobile.wportal.activity.kr;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.drawee.drawable.ScalingUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.HorizontalListView;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.HscollviewAdaptet;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.activity.MainActivity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class VoteDetailActivity extends BaseActivity {

	private PullToRefreshScrollView scrollView;

	private WImageView imageView;
	
	private TextView voteTitleTextView;

	private TextView infoTextView01;
	
	private TextView infoTextView02;
	
	private TextView voteBtn;
	
	private TextView summaryTextView;
	
	private TextView moreSummaryBtn;
	
	private HorizontalListView hscrollview;
	
	private LinearLayout detailListView;
	
	private LayoutInflater inflator;
	
	private DisplayMetrics displayMetrics;
	
	private String currentPage = "0";
	
	private String nextPage = "0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			
			setContentView(com.rs.mobile.wportal.R.layout.activity_vote_detail);
			
			inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			findViewById(com.rs.mobile.wportal.R.id.close_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					finish();
					
				}
			});
			
			scrollView = (PullToRefreshScrollView)findViewById(com.rs.mobile.wportal.R.id.scroll_view);
			scrollView.setOnRefreshListener(new OnRefreshListener2() {
				
				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub

					currentPage = "0";
					
					nextPage = "1";
					
					detailListView.removeAllViews();
					
					draw();
					
				}
	
				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub

					if (nextPage != null && !nextPage.equals("") && !nextPage.equals("0")) {
						
						draw();
						
					} else {
						
						scrollView.onRefreshComplete();
						
					}
					
				}
			});

			imageView = (WImageView)findViewById(com.rs.mobile.wportal.R.id.image_view);
			
			displayMetrics = getResources().getDisplayMetrics();

			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(displayMetrics.heightPixels/6, displayMetrics.heightPixels/5);
			
			layoutParams.setMargins(0, 0, getResources().getDimensionPixelSize(com.rs.mobile.wportal.R.dimen.marginx2), 0);
			
			imageView.setLayoutParams(layoutParams);
			
			voteTitleTextView = (TextView)findViewById(com.rs.mobile.wportal.R.id.vote_title_text_view);

			infoTextView01 = (TextView)findViewById(com.rs.mobile.wportal.R.id.info_text_view_01);
			
			infoTextView02 = (TextView)findViewById(com.rs.mobile.wportal.R.id.info_text_view_02);
			
			voteBtn = (TextView)findViewById(com.rs.mobile.wportal.R.id.vote_btn);
			voteBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					doVote(getIntent().getStringExtra("aid"), getIntent().getStringExtra("id"));
					
				}
			});
			
			summaryTextView = (TextView)findViewById(com.rs.mobile.wportal.R.id.summary_text_view);
			
			moreSummaryBtn = (TextView)findViewById(com.rs.mobile.wportal.R.id.more_summary_btn);
			moreSummaryBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (moreSummaryBtn.getText().toString().equals(getResources().getString(com.rs.mobile.wportal.R.string.more))) {
					
						moreSummaryBtn.setText(getResources().getString(com.rs.mobile.wportal.R.string.hide));
						
						summaryTextView.setSingleLine(false);
						
						summaryTextView.invalidate();
						
					} else {
						
						moreSummaryBtn.setText(getResources().getString(com.rs.mobile.wportal.R.string.more));
						
						summaryTextView.setMaxLines(4);
						
						summaryTextView.invalidate();
						
					}
					
				}
			});
			hscrollview = (HorizontalListView)findViewById(com.rs.mobile.wportal.R.id.hscrollview);
			hscrollview.setOnTouchListener(new View.OnTouchListener() {
			       
				int lastY = 0; 

				@Override
		        public boolean onTouch(View v, MotionEvent event) {
	
		        	scrollView.requestDisallowInterceptTouchEvent(true);

					int action = event.getActionMasked();
					switch (action) {
					case MotionEvent.ACTION_DOWN:
						lastY = (int) event.getY();
						break;
					case MotionEvent.ACTION_CANCEL:
						scrollView.requestDisallowInterceptTouchEvent(false);
						break;
					case MotionEvent.ACTION_UP:
						scrollView.requestDisallowInterceptTouchEvent(false);
						break;
					case MotionEvent.ACTION_MOVE:
						
						if (Math.abs(lastY - event.getY()) > StringUtil.dip2px(VoteDetailActivity.this, 100)) {
							
							scrollView.requestDisallowInterceptTouchEvent(false);
							
						}
						
						break;
					}
					return false;
		        }
		    });
			LinearLayout.LayoutParams hLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MainActivity.displayHeight/4);
			
			hscrollview.setLayoutParams(hLayoutParams);
			
			hscrollview.invalidate();
			
			detailListView = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.detail_list_view);

			draw();
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	private void draw() {
		
		try {
			
			OkHttpHelper okhttp = new OkHttpHelper(VoteDetailActivity.this);
			
			OkHttpHelper.CallbackLogic callbackLogic = new OkHttpHelper.CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String all_data) {
					// TODO Auto-generated method stub
					
					try {
					
						L.d(all_data);
						
						data = data.getJSONObject(C.KEY_JSON_DATA);
						
						currentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);
						
						nextPage = data.getString(C.KEY_JSON_NEXT_PAGE);
						
						if (data.has(C.KEY_JSON_IMAGE_URL)) {
							
							Uri imageUri = Uri.parse(data.getString(C.KEY_JSON_IMAGE_URL));
							
							ImageUtil.removeImageOnDisk(imageUri);
							
							imageView.setImageURI(imageUri, ScalingUtils.ScaleType.CENTER_INSIDE);
							
						}
						
						if (data.has("title"))
							voteTitleTextView.setText(data.getString("title"));

						if (data.has("rank"))
							infoTextView01.setText(getResources().getString(com.rs.mobile.wportal.R.string.kr_vote_detail_001) + " : " + data.getString("rank"));
						
						if (data.has("votedCount"))
							infoTextView02.setText(getResources().getString(com.rs.mobile.wportal.R.string.kr_vote_detail_002) + " : " + data.getString("votedCount"));

						if (data.has("detail"))
							summaryTextView.setText(data.getString("detail"));
						
						if (data.has("otherImage")) {
							
							LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(displayMetrics.heightPixels/6, displayMetrics.heightPixels/5);
							
							HscollviewAdaptet hscollviewAdaptet = new HscollviewAdaptet(VoteDetailActivity.this, data, "otherImage", 3, layoutParams);
							
							hscrollview.setAdapter(hscollviewAdaptet);
						
						}
						
						JSONArray arr = data.getJSONArray("votedMans");
						
						drawList(arr);
						
						scrollView.onRefreshComplete();
						
					} catch (Exception e) {
						// TODO: handle exception
						L.e(e);
						
						scrollView.onRefreshComplete();
						
					}

				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
				}
			};
			
			HashMap<String, String> params = new HashMap<String, String>();
			
			params.put("id", getIntent().getStringExtra("id"));
			
//			params.put("id", "1");
	
			okhttp.addGetRequest(callbackLogic, Constant.BASE_URL_KR + Constant.KR_VOTE_DETAIL, params);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawList(JSONArray arr) {
		
		try {
			
			if (arr != null) {
			
				if (currentPage.equals("0")) {
				
					detailListView.removeAllViews();
					
				}
				
				for (int i = 0; i <arr.length(); i++) {
				
					try {
					
						final JSONObject item = arr.getJSONObject(i);
						
						View itemView = inflator.inflate(com.rs.mobile.wportal.R.layout.list_item_voted_persons, null);
					
						WImageView image_view = (WImageView)itemView.findViewById(com.rs.mobile.wportal.R.id.image_view);
						
						TextView name_text_view = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.name_text_view);
						
						TextView date_text_view = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.date_text_view);
						
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(getResources().getDimension(com.rs.mobile.wportal.R.dimen.marginx6)), (int)(getResources().getDimension(com.rs.mobile.wportal.R.dimen.marginx6)));
						
						ImageUtil.drawIamge(image_view, Uri.parse(item.getString("headPhoto")), params);
						
						name_text_view.setText(item.getString("nickName"));
						
						date_text_view.setText(item.getString("date"));
						
						detailListView.addView(itemView);
						
						View line = new View(VoteDetailActivity.this);
						
						line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
						
						line.setBackgroundColor(Color.parseColor("#f2f2f2"));
						
						detailListView.addView(line);
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
					detailListView.invalidate();
				}
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void doVote(String projectId, String itemId) {
		
		try {

			OkHttpHelper helper = new OkHttpHelper(VoteDetailActivity.this);
			
			HashMap<String, String> params = new HashMap<String, String>();

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
				
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
					
					try {

						String status = data.getString("status");
						
						if (status != null && status.equals("1")) {
							
							T.showToast(VoteDetailActivity.this, com.rs.mobile.wportal.R.string.kr_vote_complete);
							
						} else {
							
							T.showToast(VoteDetailActivity.this, data.getString("msg"));
							
						}
					
					} catch (Exception e) {
						
						L.e(e);
						
					}

				}
				
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub오늘
					
				}
			}, Constant.BASE_URL_KR + Constant.KR_VOTE_DETAIL + "?projectId=" + projectId + "&itemId=" + itemId + "&userId=" 
				+ S.getShare(VoteDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, ""), params);

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
}
