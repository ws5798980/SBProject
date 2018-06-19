package com.rs.mobile.wportal.view.kr;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.S;
import com.rs.mobile.common.view.HorizontalListView;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.adapter.HscollviewAdaptet;
import com.rs.mobile.wportal.adapter.kr.CViewPagerAdapter;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.kr.MainActivity;
import com.rs.mobile.wportal.adapter.GridListAdapter;
import com.rs.mobile.wportal.view.CustomViewPager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import okhttp3.Request;

public class MainView extends RelativeLayout{

	public boolean isInit = false;
	
	private Context context;
	
	private PullToRefreshScrollView scrollView;
	
	private HorizontalListView horizontalScrollView;

	private WImageView[] imgview;
	
	private WImageView imgview_vote1, imgview_vote2, imgview_vote3;
	
	private TextView[] textview;
	
	private JSONObject liveData;
	
	private JSONArray jsonarry, jsonarryvote;
	
	private RadioGroup radioGroup;
	
	private int contentHeight;
	
	private int type = 0; //0 : vod, 1 : vote
	
	public LinearLayout viewPagerLayout;
	
	private CustomViewPager viewPager;
	
	private int selectColor = Color.parseColor("#fca73d");
	
	private int unSelectColor = Color.parseColor("#ffffff");
	
	private EditText editText;
	
	private LinearLayout searchArea;
	
	private LinearLayout contentArea;
	
	private LinearLayout deleteHistoryBtn;
	
	private GridView historyGridView, rankingGridView;
	
	private String orderBy = "";
	
	private MainView mainView;
	
	private LinearLayout typeBtn;
	
	private LinearLayout typeLayout;
	
	private TextView typeVodBtn;
	
	private TextView typeVoteBtn;
	
	private RelativeLayout liveBtn;
	
	private WImageView liveImageView;
	
	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}
	
	/**
	 * 초기화
	 * @param context
	 */
	public void initView(final Context context) {
		
		try {
		
			this.context = context;

			final View v = ((MainActivity)context).inflator.inflate(R.layout.activity_crazymedia_highlight, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			v.findViewById(R.id.img_serch).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					
					showSearchArea(v);
					
				}
			});
			
			DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
			
			contentHeight = displayMetrics.heightPixels/4;
			
			scrollView = (PullToRefreshScrollView)v.findViewById(R.id.scroll_view);
			scrollView.setOnRefreshListener(new OnRefreshListener2() {
				
				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
	
					drawview();
					
				}
	
				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
					
					drawview();
					
				}
			});
			
			viewPagerLayout = (LinearLayout)v.findViewById(R.id.view_pager_layout);
			
			viewPager = (CustomViewPager) v.findViewById(R.id.highlight_viewpager);
			
			int margin = (int) getResources().getDimension(R.dimen.marginx4);
			
			viewPager.setPadding(margin/2, margin, margin/2, margin);
			
			viewPager.setClipToPadding(false);
			
	//		viewPager.setPageMargin(40);
			
			horizontalScrollView = (HorizontalListView)v.findViewById(R.id.highlight_hscrollview);
			horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
		       
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
						
						if (Math.abs(lastY - event.getY()) > StringUtil.dip2px(context, 100)) {
							
							scrollView.requestDisallowInterceptTouchEvent(false);
							
						}
						
						break;
					}
					return false;
		        }
		    });
			
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, contentHeight);
			
			layoutParams.addRule(RelativeLayout.BELOW, R.id.rela_viewpager);
			
			horizontalScrollView.setLayoutParams(layoutParams);
			
			horizontalScrollView.invalidate();
			
			liveBtn = (RelativeLayout)v.findViewById(R.id.live_btn);
			liveBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
//						Uri uri = Uri.parse("http://192.168.2.233:89/WAP/VideoPlay?videoId=" + liveData.getString("uniqueId") + "&kind=3");
//						Intent i = new Intent(Intent.ACTION_VIEW, uri);
//						context.startActivity(i);
//						Intent i = new Intent(context, SensorWebActivity.class);
//						i.putExtra(C.KEY_INTENT_URL, "http://192.168.2.163:89/WAP/VideoPlay?videoId=" + liveData.getString("uniqueId") + "&kind=3");
//						
//						i.putExtra(C.KEY_INTENT_URL, "http://222.240.51.144:89/WAP/VideoPlay?videoId=" + liveData.getString("uniqueId") + "&kind=3");
//						i.putExtra(C.KEY_INTENT_URL, "http://192.168.2.233:89/WAP/VideoPlay?videoId=" + liveData.getString("uniqueId") + "&kind=3" +
//								"&userId=" + S.get(context, C.KEY_REQUEST_MEMBER_ID));
//						
//						i.putExtra(C.KEY_INTENT_URL, C.BASE_PLAYER_URL_KR + "/WAP/VideoPlay?videoId=" + liveData.getString("uniqueId") + "&kind=3");
//						
//						context.startActivity(i);
						
						UiUtil.showVideoPlayer(context, liveData.getString("uniqueId"), "3");
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			liveImageView = (WImageView)v.findViewById(R.id.live_image_view);
			
			imgview = new WImageView[4];
			imgview[0] = (WImageView)v.findViewById(R.id.tuijian_img1);
			imgview[0].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
					
						UiUtil.showVideoPlayer(context, new JSONObject(jsonarry.get(0).toString()).getString("uniqueId"), "2");
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			imgview[1] = (WImageView)v.findViewById(R.id.tuijian_img2);
			imgview[1].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						UiUtil.showVideoPlayer(context, new JSONObject(jsonarry.get(1).toString()).getString("uniqueId"), "2");
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			imgview[2] = (WImageView)v.findViewById(R.id.tuijian_img3);
			imgview[2].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						UiUtil.showVideoPlayer(context, new JSONObject(jsonarry.get(2).toString()).getString("uniqueId"), "2");
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			imgview[3] = (WImageView)v.findViewById(R.id.tuijian_img4);
			imgview[3].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						UiUtil.showVideoPlayer(context, new JSONObject(jsonarry.get(3).toString()).getString("uniqueId"), "2");
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			textview = new TextView[4];
			textview[0] = (TextView)v.findViewById(R.id.tuijian_text1);
			textview[1] = (TextView)v.findViewById(R.id.tuijian_text2);
			textview[2] = (TextView)v.findViewById(R.id.tuijian_text3);
			textview[3] = (TextView)v.findViewById(R.id.tuijian_text4);
			imgview_vote1 = (WImageView)v.findViewById(R.id.vote_img1);
			imgview_vote1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
					
						JSONObject obj = jsonarryvote.getJSONObject(0);
						
						MainActivity activity = (MainActivity)context;
						
						activity.showVoteDetail(obj.getString(C.KEY_JSON_UNIQUE_ID));
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			imgview_vote2 = (WImageView)v.findViewById(R.id.vote_img2);
			imgview_vote2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
					
						JSONObject obj = jsonarryvote.getJSONObject(1);
						
						MainActivity activity = (MainActivity)context;
						
						activity.showVoteDetail(obj.getString(C.KEY_JSON_UNIQUE_ID));
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			imgview_vote3 = (WImageView)v.findViewById(R.id.vote_img3);
			imgview_vote3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
					
						JSONObject obj = jsonarryvote.getJSONObject(2);
						
						MainActivity activity = (MainActivity)context;
						
						activity.showVoteDetail(obj.getString(C.KEY_JSON_UNIQUE_ID));
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			radioGroup = (RadioGroup)v.findViewById(R.id.radiogroup002);
			
			searchArea = (LinearLayout)v.findViewById(R.id.search_area);
			searchArea.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					hideSearchArea(v);
					
				}
			});
			
			contentArea = (LinearLayout)v.findViewById(R.id.content_area);
			
			deleteHistoryBtn = (LinearLayout)v.findViewById(R.id.delete_history_btn);
			deleteHistoryBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					S.set(context, C.KEY_KR_SEARCH_HISTORY, "");
					
					drawSearchArea(v);
					
				}
			});
			
			historyGridView = (GridView)v.findViewById(R.id.histroy_grid_view);
			historyGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
					// TODO Auto-generated method stub
					
					try {

						String text = (String) adapter.getItemAtPosition(position);

						goSearchView(text);
						
						hideSearchArea(v);
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});

			rankingGridView = (GridView)v.findViewById(R.id.ranking_grid_view);
			rankingGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
					// TODO Auto-generated method stub
				
					try {
						
						JSONObject item = (JSONObject) adapter.getItemAtPosition(position);
						
						String text = item.getString(C.KEY_JSON_TITLE);
						
						goSearchView(text);
						
						hideSearchArea(v);
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			editText = (EditText)v.findViewById(R.id.edit_text);
			editText.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						
						String searchKey = editText.getText().toString();
						
						if (searchKey == null || searchKey.equals("")) {
							
							hideSearchArea(v);
							
						} else {

							String[] searchHistoryList = S.getList(context, C.KEY_KR_SEARCH_HISTORY);
							
							if (searchHistoryList != null && searchHistoryList.length > 3) {
								
								S.deleteList(context, C.KEY_KR_SEARCH_HISTORY, 0);
								
							}
							
							S.addList(context, C.KEY_KR_SEARCH_HISTORY, editText.getText().toString());
							
							goSearchView(editText.getText().toString());
							
							hideSearchArea(v);
							
							
						}

						return true;
					}
					
					return false;
				}
			});
			
			typeBtn = (LinearLayout)v.findViewById(R.id.type_btn);
			typeBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (typeLayout.getVisibility() == View.VISIBLE) {
						
						hideTypeArea();
						
					} else {
						
						showTypeArea();
						
					}
					
				}
			});
			
			typeLayout = (LinearLayout)v.findViewById(R.id.type_layout);
			
			typeVodBtn = (TextView)v.findViewById(R.id.type_vod_btn);
			typeVodBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					type = 0;
					
					hideTypeArea();
					
				}
			});
			typeVoteBtn = (TextView)v.findViewById(R.id.type_vote_btn);
			typeVoteBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					type = 1;
					
					hideTypeArea();
					
				}
			});
			addView(v);
			
			mainView = this;
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void showSearchArea(View v) {
		
		try {
			
			v.findViewById(R.id.search_navigation_area).setVisibility(View.VISIBLE);
			
			Animation fadeAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
			Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_bottom_in);
			
			searchArea.setVisibility(View.VISIBLE);
			contentArea.setVisibility(View.VISIBLE);
			
			searchArea.startAnimation(fadeAnimation);
			contentArea.startAnimation(scaleAnimation);
			
			drawSearchArea(v);

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void hideSearchArea(View v) {
		
		try {
			
			v.findViewById(R.id.search_navigation_area).setVisibility(View.GONE);
			
			Animation fadeAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
			Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_top_out);

			searchArea.setVisibility(View.GONE);
			contentArea.setVisibility(View.GONE);

			searchArea.startAnimation(fadeAnimation);
			contentArea.startAnimation(scaleAnimation);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void showTypeArea() {
		
		try {

			Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_bottom_in);
			
			typeLayout.setVisibility(View.VISIBLE);
			
			typeLayout.startAnimation(scaleAnimation);
			
			if (type == 0) {
				
				typeVodBtn.setTextColor(selectColor);
				typeVoteBtn.setTextColor(unSelectColor);
				
			} else {
				
				typeVodBtn.setTextColor(unSelectColor);
				typeVoteBtn.setTextColor(selectColor);
				
			}

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void hideTypeArea() {
		
		try {

			Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_top_out);

			typeLayout.setVisibility(View.GONE);

			typeLayout.startAnimation(scaleAnimation);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawSearchArea(View v) {
		
		try {
			
			String[] items = S.getList(context, C.KEY_KR_SEARCH_HISTORY);
			
			if (items != null && items.length > 0) {
			
				v.findViewById(R.id.history_area).setVisibility(View.VISIBLE);
				
				GridListAdapter adapter = new GridListAdapter(context, S.getList(context, C.KEY_KR_SEARCH_HISTORY), GridListAdapter.TYPE_SEARCH_HISTORY);
				
				historyGridView.setAdapter(adapter);
			
			} else {
				
				v.findViewById(R.id.history_area).setVisibility(View.GONE);
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawview() {

		try {
			
			isInit = true;
			
			OkHttpHelper okhttp = new OkHttpHelper(context);
			okhttp.addGetRequest(new CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
					isInit = false;
					
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String all_data) {
					// TODO Auto-generated method stub
					
					try {
					
						L.d(all_data);
						
						data = data.getJSONObject(C.KEY_JSON_DATA);
						
						final CViewPagerAdapter viewPagerAdapter = new CViewPagerAdapter(context, data, mainView);
						
						viewPager.setAdapter(viewPagerAdapter);
						
						HscollviewAdaptet hscollviewAdaptet = new HscollviewAdaptet(context, data);
						
						horizontalScrollView.setAdapter(hscollviewAdaptet);
		
						radioGroup.removeAllViews();
						
						int bannerCount = viewPagerAdapter.count;
						
						Resources res = getResources();
						
						for (int i = 0; i <bannerCount; i++) {
							
							RadioButton btn = new RadioButton(context);
							btn.setPadding(5, 5, 5, 5);
							btn.setButtonDrawable(res.getDrawable(R.drawable.bg_viewpage_yuan));
							btn.setGravity(Gravity.CENTER);
							
							radioGroup.addView(btn);
							
						}
		
						radioGroup.check(radioGroup.getChildAt(0).getId());
						viewPager.addOnPageChangeListener(new OnPageChangeListener() {
		
							@Override
							public void onPageSelected(int arg0) {
								// TODO Auto-generated method stub
								
								try {
									
									radioGroup.check(radioGroup.getChildAt(arg0).getId());
			
									Drawable d = viewPagerAdapter.viewPagerBg[arg0];
									
									viewPagerLayout.setBackgroundDrawable(d);
									
								} catch (Exception e) {
									
									L.e(e);
									
								}
								
							}
		
							@Override
							public void onPageScrolled(int arg0, float arg1, int arg2) {
								// TODO Auto-generated method stub
		
							}
		
							@Override
							public void onPageScrollStateChanged(int arg0) {
								// TODO Auto-generated method stub
		
							}
						});
		//				zhizhen = 0;
		//				timer = new Timer();
		//
		//				start_anmition();

						liveData = data.getJSONObject("live");
						
						ImageUtil.drawImageView(context, "", liveImageView, liveData, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);

						jsonarry = data.getJSONArray(C.KEY_JSON_BROADCAST);
						jsonarryvote = data.getJSONArray(C.KEY_JSON_VOTE);
						for (int i = 0; i < jsonarry.length(); i++) {
	
							ImageUtil.drawImageView(context, "", imgview[i], new JSONObject(jsonarry.get(i).toString()),
									C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
							textview[i].setText(new JSONObject(jsonarry.get(i).toString()).getString(C.KEY_JSON_TITLE));
	
						}
						ImageUtil.drawImageView(context, "", imgview_vote1, new JSONObject(jsonarryvote.get(0).toString()),
								C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
						ImageUtil.drawImageView(context, "", imgview_vote2, new JSONObject(jsonarryvote.get(1).toString()),
								C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
						ImageUtil.drawImageView(context, "", imgview_vote3, new JSONObject(jsonarryvote.get(2).toString()),
								C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
						
						JSONArray arr = data.getJSONArray(C.KEY_JSON_HOT_SEARCH);
						
						GridListAdapter adapter = new GridListAdapter(context, arr, GridListAdapter.TYPE_SEARCH_RANKING);
						
						rankingGridView.setAdapter(adapter);
						
						scrollView.onRefreshComplete();
						
					} catch (Exception e) {
						// TODO: handle exception
						L.e(e);
						
						scrollView.onRefreshComplete();
						
						isInit = false;
						
					}

				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
					isInit = false;
					
				}
			}, Constant.BASE_URL_KR + Constant.KR_MAIN_JSON);
		
		} catch (Exception e) {
			
			L.e(e);
			
			scrollView.onRefreshComplete();
			
		}
	}
	
	public void goSearchView(String text) {
		
		try {
			
			MainActivity activity = (MainActivity)context;
			
			if (type == 0) {
				
				activity.tabRadioGroup.check(R.id.common_toolbar_1_discovery);
				activity.movePage(R.id.common_toolbar_1_discovery);
				activity.searchVodView.editText.setText(text);
				activity.searchVodView.searchVod();
			
			} else {
				
				activity.tabRadioGroup.check(R.id.common_toolbar_1_vote);
				activity.movePage(R.id.common_toolbar_1_vote);
				activity.searchVoteView.editText.setText(text);
				activity.searchVoteView.searchVote();
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
}
