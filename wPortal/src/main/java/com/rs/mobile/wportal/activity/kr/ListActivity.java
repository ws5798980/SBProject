package com.rs.mobile.wportal.activity.kr;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.view.kr.CatogoryButtonView;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.activity.MainActivity;
import com.rs.mobile.wportal.view.kr.VodView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class ListActivity extends BaseActivity {

	/**
	 * type=1,내가 구매한 동영상 
	 * type=2,내가 구매한 공연티켓 
	 * type=3,내가 투표한 것 
	 * type=4,나의 복마크藏 
	 * type=5,역사기록录 
	 * type=6,나의메시지 
	 * type=7,나의거래내역
	 * type=8,내가구매한영화티켓 
	 */
	private LinearLayout rightBtn;
	
	private ImageView rightImg;
	
	private TextView titleTextView;

	private LinearLayout listView;
	
	private PullToRefreshScrollView scroll_view;
	
	public static final int TYPE_VOD_LIST = 1;
	
	public static final int TYPE_MOVIE_TICKET_LIST = 2;
	
	public static final int TYPE_VOTE_LIST = 3;
	
	public static final int TYPE_BOOKMARK_LIST = 4;
	
	public static final int TYPE_HISTORY_LIST = 5;
	
	public static final int TYPE_MESSAGE_LIST = 6;
	
	public static final int TYPE_PURCHAR_LIST = 7;
	
	public static final int TYPE_PLAY_TICKET_LIST = 8;
	
	public static final int TYPE_LOCAL_LIST = 9;
	
	private int type = TYPE_VOD_LIST;
	
	private String currentPage = "0";
	
	private String nextPage = "0";
	
	public LayoutInflater inflator;
	
	private LinearLayout category_area;
	
	private LinearLayout my_balance_area;
	
	private TextView balance_text_view;
	
	private TextView my_balance_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			
			setContentView(com.rs.mobile.wportal.R.layout.activity_kr_my_list);
			
			inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			type = getIntent().getIntExtra(C.KEY_JSON_TYPE, TYPE_VOD_LIST);
			
			findViewById(com.rs.mobile.wportal.R.id.close_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					finish();
					
				}
			});
			
			rightBtn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.right_navigation_btn);
			rightBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			rightImg = (ImageView)findViewById(com.rs.mobile.wportal.R.id.right_navigation_img);
			
			titleTextView = (TextView)findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			titleTextView.setText(getIntent().getStringExtra("title"));
			
			scroll_view = (PullToRefreshScrollView)findViewById(com.rs.mobile.wportal.R.id.scroll_view);
			scroll_view.setOnRefreshListener(new OnRefreshListener2() {
				
				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
	
					listView.removeAllViews();
					
					currentPage = "0";
					
					nextPage = "1";
					
					draw();
					
				}
	
				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
					
					if (nextPage != null && !nextPage.equals("") && !nextPage.equals("0")) {
						
						draw();
						
					} else {
						
						scroll_view.onRefreshComplete();
						
					}
					
				}
			});
			
			category_area = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.category_area);
			
			my_balance_area = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.my_balance_area);
			
			balance_text_view = (TextView)findViewById(com.rs.mobile.wportal.R.id.balance_text_view);
			
			my_balance_btn = (TextView)findViewById(com.rs.mobile.wportal.R.id.my_balance_btn);
			my_balance_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			listView = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.list_view);
			
			if (type == TYPE_PLAY_TICKET_LIST || type == TYPE_PLAY_TICKET_LIST) {
			
				drawTicketMenuList();
			
			} else if (type == TYPE_PURCHAR_LIST) {
				
				my_balance_area.setVisibility(View.VISIBLE);
				
			}
			
			draw();

		} catch (Exception e) {
			
			e(e);
			
		}

	}
	
	/**
	 * 리스트 그리기
	 */
	public void draw() {
		
		try {
			
			if (type == TYPE_LOCAL_LIST) {
				
				
				
			} else {

				HashMap<String, String> params = new HashMap<String, String>();
				
				params.put("userId", S.getShare(ListActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));
				
				params.put(C.KEY_JSON_TYPE, "" + type);
				
				params.put(C.KEY_JSON_CURRENT_PAGE, currentPage);
				
				params.put(C.KEY_JSON_NEXT_PAGE, nextPage);
			
				OkHttpHelper helper = new OkHttpHelper(ListActivity.this);
				helper.addGetRequest(new OkHttpHelper.CallbackLogic() {
					
					@Override
					public void onNetworkError(Request request, IOException e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
						// TODO Auto-generated method stub
						
						try {
							
							String status = data.getString("status");
							
							if (status != null && status.equals("1")) {
							
								data = data.getJSONObject(C.KEY_JSON_DATA);
						
								currentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);
								
								nextPage = data.getString(C.KEY_JSON_NEXT_PAGE);
								
								if (type == TYPE_VOD_LIST) {
									
									currentPage = data.getString("currentPage");
									
									nextPage = data.getString("nextPage");
									
									drawVodList(data.getJSONArray("videos"));
									
								} else if (type == TYPE_PLAY_TICKET_LIST) {
									
									
									
								} else if (type == TYPE_MOVIE_TICKET_LIST) {
									
									currentPage = data.getString("currentPage");
									
									nextPage = data.getString("nextPage");
									
									drawMovieTicketList(data.getJSONArray("showTickets"));
									
								} else if (type == TYPE_BOOKMARK_LIST) {
									
									currentPage = data.getString("currentPage");
									
									nextPage = data.getString("nextPage");
									
									drawVodList(data.getJSONArray("favorates"));
									
								} else if (type == TYPE_HISTORY_LIST) {
									
									currentPage = data.getString("currentPage");
									
									nextPage = data.getString("nextPage");
									
									drawVodList(data.getJSONArray("histories"));
									
								} else if (type == TYPE_PURCHAR_LIST) {
									
									currentPage = data.getString("currentPage");
									
									nextPage = data.getString("nextPage");
									
									drawBalanceList(data.getJSONObject("histories"));
									
								}
							
							}
							
						} catch (Exception e) {
							
							L.e(e);
							
						}
						
					}
					
					@Override
					public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
						// TODO Auto-generated method stub
						
					}
				}, Constant.BASE_URL_KR + Constant.KR_MY_PURCHAR, params);
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
			scroll_view.onRefreshComplete();
			
		}
		
	}
	
	/**
	 * vod
	 * @param data
	 */
	public void drawVodList(JSONArray arr) {
		
		try {

			if (arr != null) {
			
				if (currentPage.equals("0")) {
				
					listView.removeAllViews();
				
				}
				
				for (int i = 0; i < arr.length(); i++) {
					
					final VodView view = new VodView(ListActivity.this, (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE));
					
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MainActivity.newsHeight);

					view.setLayoutParams(layoutParams);
					
					view.invalidate();
					
					final JSONObject json = new JSONObject(arr.get(i).toString());

					view.setUniqueId(json.getString(C.KEY_JSON_UNIQUE_ID));
					
					view.setTitle(json.getString(C.KEY_JSON_TITLE));
					
					if (json.has(C.KEY_JSON_CONTENT)) {
					
						view.setContent(json.getString(C.KEY_JSON_CONTENT));
					
					} else {
						
						view.setContent(json.getString("description"));
						
					}

					view.setCount(json.getString(C.KEY_JSON_READ));
					
//					ImageUtil.drawImageViewBuFullUrl(ListActivity.this, view.getImageView(), json, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
					
					ImageUtil.drawImageView(ListActivity.this, view.getImageView(), json.getString(C.KEY_JSON_IMAGE_URL));

					view.invalidate();
					
					view.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							try {
//								if (videoTypeId.equals("M09")) {
//
//									Intent i = new Intent(ListActivity.this, SensorWebActivity.class);
//									i.putExtra(C.KEY_INTENT_URL, "http://222.240.51.144:89/WAP/VideoPlay?videoId=" + json.getString(C.KEY_JSON_UNIQUE_ID) + "&kind=3");
//									ListActivity.this.startActivity(i);
//									
//								} else {
//								
//									Intent i = new Intent(ListActivity.this, SensorWebActivity.class);
//									i.putExtra(C.KEY_INTENT_URL, C.BASE_PLAYER_URL_KR + "/WAP/VideoPlay?videoId=" + json.getString(C.KEY_JSON_UNIQUE_ID) + "&kind=2" +
//											"&userId=" + S.get(ListActivity.this, C.KEY_REQUEST_MEMBER_ID));
//									
//									ListActivity.this.startActivity(i);
									UiUtil.showVideoPlayer(ListActivity.this, json.getString(C.KEY_JSON_UNIQUE_ID), "2");
//								}
								
							} catch (Exception e) {
								
								L.e(e);
								
							}
							
						}
					});
					
					if (i > 0) {
					
						LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) view.getLayoutParams();
						p.topMargin = 1;
						view.setLayoutParams(p);
					
					}
					
					listView.addView(view);

				}
				
				listView.invalidate();
				
			}
		
		} catch (Exception e) {
			L.e(e);
		}
		
		scroll_view.onRefreshComplete();
		
	}
	
	/**
	 * ticket
	 * @param data
	 */
	public void drawMovieTicketList(JSONArray arr) {
		
		try {

			if (arr != null) {
			
				if (currentPage.equals("0")) {
				
					listView.removeAllViews();
				
				}
				
				for (int i = 0; i < arr.length(); i++) {
					
					View itemView = inflator.inflate(com.rs.mobile.wportal.R.layout.list_item_movie_ticket, null);

					TextView text_view_001 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_001);
					
					TextView text_view_002 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_002);
					
					TextView text_view_003 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_003);
					
					TextView text_view_004 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_004);
					
					TextView text_view_005 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_005);

					final JSONObject json = new JSONObject(arr.get(i).toString());

					text_view_001.setText(json.getString("title"));
					
					text_view_002.setText(json.getString("ticketCount") + getResources().getString(com.rs.mobile.wportal.R.string.kr_person_count));
					
					text_view_003.setText(json.getString("place"));
					
					String showDate = json.getString("showDate");
					
					text_view_004.setText(showDate);
					
					SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
					
					Date ticketDate = dateFormat.parse(showDate);
					
					Date currentDate = new Date(System.currentTimeMillis());
					
					if (ticketDate.after(currentDate)) {

						text_view_005.setBackgroundColor(Color.parseColor("#cccccc"));
						
						text_view_005.setText(getResources().getString(com.rs.mobile.wportal.R.string.kr_ticket_after));
						
					} else {
						
						text_view_005.setBackgroundColor(Color.parseColor("#fca73d"));
						
						text_view_005.setText(getResources().getString(com.rs.mobile.wportal.R.string.kr_ticket_before));
						
					}
					
					itemView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							try {
								
							} catch (Exception e) {
								L.e(e);
							}
							
						}
					});
					
					itemView.invalidate();

					listView.addView(itemView);

					listView.invalidate();
					
					LinearLayout.LayoutParams params = (LayoutParams) itemView.getLayoutParams();
					
					params.setMargins(0, 0, 0, 1);
					
					itemView.setLayoutParams(params);
					
				}
				
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		scroll_view.onRefreshComplete();
		
	}
	
	public void drawBalanceList(JSONObject jsonObject) {
		
		try {

			balance_text_view.setText(jsonObject.getString("balance"));
			
			JSONArray arr = jsonObject.getJSONArray("billingItems");

			if (arr != null) {
			
				if (currentPage.equals("0")) {
				
					listView.removeAllViews();
				
				}
				
				for (int i = 0; i < arr.length(); i++) {
					
					View itemView = inflator.inflate(com.rs.mobile.wportal.R.layout.list_item_balance, null);

					TextView text_view_001 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_001);
					
					TextView text_view_002 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_002);
					
					TextView text_view_003 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_003);
					
					TextView text_view_004 = (TextView)itemView.findViewById(com.rs.mobile.wportal.R.id.text_view_004);

					final JSONObject json = new JSONObject(arr.get(i).toString());

					text_view_001.setText(json.getString("name"));
					
					text_view_002.setText(json.getString("date"));
					
					text_view_003.setText(getResources().getString(com.rs.mobile.wportal.R.string.kr_my_balance_003) + json.getString("balance"));

					text_view_004.setText(json.getString("itemPrice"));

					itemView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							try {

								
								
							} catch (Exception e) {
								
								L.e(e);
								
							}
							
						}
					});
					
					itemView.invalidate();

					listView.addView(itemView);

					listView.invalidate();
					
					LinearLayout.LayoutParams params = (LayoutParams) itemView.getLayoutParams();
					
					params.setMargins(0, 0, 0, 1);
					
					itemView.setLayoutParams(params);
					
				}
				
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		scroll_view.onRefreshComplete();
		
	}
	
	public void drawTicketMenuList() {
		
		try {
			
			category_area.setVisibility(View.VISIBLE);

			final CatogoryButtonView playButton = new CatogoryButtonView(ListActivity.this);
			
			LinearLayout.LayoutParams playLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

			playLayoutParams.weight = 1;
			
			playButton.setLayoutParams(playLayoutParams);

			playButton.setUniqueId("" + TYPE_PLAY_TICKET_LIST);
				
			playButton.setTitle(getResources().getString(com.rs.mobile.wportal.R.string.kr_play_ticket));
				
			playButton.selectButton();
			
			playButton.invalidate();
			
			final CatogoryButtonView movieButton = new CatogoryButtonView(ListActivity.this);
			
			LinearLayout.LayoutParams movieLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

			movieLayoutParams.weight = 1;
			
			movieButton.setLayoutParams(movieLayoutParams);

			movieButton.setUniqueId("" + TYPE_MOVIE_TICKET_LIST);
				
			movieButton.setTitle(getResources().getString(com.rs.mobile.wportal.R.string.kr_movie_ticket));
				
			movieButton.unSelectButton();
			
			movieButton.invalidate();
			
			category_area.addView(playButton);
			
			category_area.addView(movieButton);
			
			category_area.invalidate();

			playButton.setOnClickListener(new OnClickListener() {
					
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					type = TYPE_PLAY_TICKET_LIST;
					
					int count = category_area.getChildCount();
					
					for (int i = 0; i < count; i++) {
						
						CatogoryButtonView view = (CatogoryButtonView) category_area.getChildAt(i);
						
						if (view.getUniqueId().equals(playButton.getUniqueId())) {
							
							view.selectButton();
							view.invalidate();
							
						} else {
							
							view.unSelectButton();
							view.invalidate();
							
						}
						
					}
					
					currentPage = "0";
					
					nextPage = "1";
					
					listView.removeAllViews();
					
					listView.invalidate();
					
					draw();
					
				}
			});
			
			movieButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					type = TYPE_MOVIE_TICKET_LIST;
					
					int count = category_area.getChildCount();
					
					for (int i = 0; i < count; i++) {
						
						CatogoryButtonView view = (CatogoryButtonView) category_area.getChildAt(i);
						
						if (view.getUniqueId().equals(movieButton.getUniqueId())) {
							
							view.selectButton();
							view.invalidate();
							
						} else {
							
							view.unSelectButton();
							view.invalidate();
							
						}
						
					}
					
					currentPage = "0";
					
					nextPage = "1";
					
					listView.removeAllViews();
					
					listView.invalidate();
					
					draw();
					
				}
			});
					
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

}