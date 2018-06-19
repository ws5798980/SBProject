package com.rs.mobile.wportal.view.kr;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.HorizontalListView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.HscollviewAdaptet;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.activity.kr.ArtCenterDetailActivity;
import com.rs.mobile.wportal.activity.kr.MainActivity;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter.ItemClickListener;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import okhttp3.Request;


public class PlayView extends RelativeLayout{

	public boolean isInit = false;
	
	private Context context;
	
	private PullToRefreshScrollView scrollView;
	
	private ViewPager viewPager;
	
	private ViewPagerAdapter viewPagerAdapter;
	
	private RadioGroup radioGroup;
	
	private LinearLayout categoryListView;
	
	private HorizontalListView horizontalScrollView;
	
	private LinearLayout listView;
	
	private String currentPage = "";
	
	private String nextPage = "";
	
	private String type = "";
	
	public PlayView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public PlayView(Context context, AttributeSet attrs, int defStyleAttr) {
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

			View v = ((MainActivity)context).inflator.inflate(com.rs.mobile.wportal.R.layout.layout_play, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			scrollView = (PullToRefreshScrollView)v.findViewById(com.rs.mobile.wportal.R.id.scroll_view);
			scrollView.setOnRefreshListener(new OnRefreshListener2() {
				
				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
	
					nextPage = "";
					
					currentPage = "";
					
					listView.removeAllViews();
					
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
			viewPager = (ViewPager)v.findViewById(com.rs.mobile.wportal.R.id.view_pager);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, com.rs.mobile.wportal.activity.MainActivity.displayHeight/4);
			
			params.addRule(RelativeLayout.BELOW, com.rs.mobile.wportal.R.id.rela_top);
			
			viewPager.setLayoutParams(params);
			
//			radioGroup = (RadioGroup)v.findViewById(R.id.content_text_view);
			
			categoryListView = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.horizontal_scroll_area);
			
			horizontalScrollView = (HorizontalListView)v.findViewById(com.rs.mobile.wportal.R.id.h_scroll_view);
			
			params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, com.rs.mobile.wportal.activity.MainActivity.newsHeight);
			
			params.addRule(RelativeLayout.BELOW, com.rs.mobile.wportal.R.id.category_area);
			
			horizontalScrollView.setLayoutParams(params);
			
			horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
	
		            scrollView.requestDisallowInterceptTouchEvent(true);
	
		            int action = event.getActionMasked();
		            switch (action) {
		                case MotionEvent.ACTION_UP:
		                    scrollView.requestDisallowInterceptTouchEvent(false);
		                    break;
		            }
		            return false;
		        }
		    });
			
			listView = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.list_area);

			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void draw() {
		
		try {

			isInit = true;
			
			OkHttpHelper okhttp = new OkHttpHelper(context);
			
			OkHttpHelper.CallbackLogic callbackLogic = new OkHttpHelper.CallbackLogic() {
	
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
						
						if (nextPage != null && !nextPage.equals("") && !nextPage.equals("0")) {
							
							data = data.getJSONObject("show");
							
							drawPlayList(data);
							
						} else {
							
							drawViewPager(data);
							
							drawMenuList(type, data);
							
							drawHorizontalScrllView(data);
							
							listView.removeAllViews();

							drawPlayList(data.getJSONObject("show"));

						}

					} catch (Exception e) {
						// TODO: handle exception
						L.e(e);
						
						isInit = false;
						
					}

					scrollView.onRefreshComplete();
					
				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
					isInit = false;
					
				}
			};
			
			if (currentPage != null && !currentPage.equals("") && nextPage != null && !nextPage.equals("")) {
				
				HashMap<String, String> params = new HashMap<String, String>();
				
				params.put("typeId", type);
				
				params.put(C.KEY_JSON_CURRENT_PAGE, currentPage);
				
				params.put(C.KEY_JSON_NEXT_PAGE, nextPage);
		
				okhttp.addGetRequest(callbackLogic, Constant.BASE_URL_KR + Constant.KR_PLAY_LIST, params);
				
			} else {
				
				okhttp.addGetRequest(callbackLogic,Constant.BASE_URL_KR + Constant.KR_PLAY_LIST);
				
			}
		
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawViewPager(final JSONObject data) {
		
		try {

			new Handler().post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub

					if (viewPagerAdapter != null) {
					
						int c = viewPagerAdapter.items.size();
						
						for (int i = 0; i < c; i++) {
							
							viewPagerAdapter.destroyItem(viewPager, i, null);
							
						}
						
						viewPagerAdapter.items.clear();
						
						viewPagerAdapter.notifyDataSetChanged();
						
						viewPager.removeAllViews();

						viewPagerAdapter.views.clear();
						
						viewPager.setAdapter(null);
						
						viewPager.invalidate();
						
					} 
					
					viewPagerAdapter = new ViewPagerAdapter((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), data, "banner");
					viewPagerAdapter.setOnItemClickListener(new ItemClickListener() {
						
						@Override
						public void onClick(Object obj) {
							// TODO Auto-generated method stub
							
							try {
							
//								Intent i = new Intent(context, WebActivity.class);
//								i.putExtra(C.KEY_INTENT_URL, ((JSONObject)obj).getString(C.KEY_JSON_URL));
//								context.startActivity(i);
							
							} catch (Exception e) {
								
								L.e(e);
								
							}
							
						}
					});
					viewPager.setAdapter(viewPagerAdapter);
					
				}
			});
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawMenuList(String id, JSONObject data) {
		
		try {
			
			if (data.has("type")) {
			
//				JSONArray arr = data.getJSONArray(C.KEY_JSON_VIDEO_TYPES);
				
				JSONArray arr = data.getJSONArray("type");
				
				if (arr != null) {
				
					categoryListView.removeAllViews();
					
					for (int i = 0; i < arr.length(); i++) {
						
						final CatogoryButtonView button = new CatogoryButtonView(context);
						
						RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	
						button.setLayoutParams(layoutParams);
						
						button.invalidate();
						
						final JSONObject json = new JSONObject(arr.get(i).toString());
						
						button.setUniqueId(json.getString(C.KEY_JSON_UNIQUE_ID));
						
						button.setTitle(json.getString(C.KEY_JSON_NAME));
						if (i == 0 && (id == null || id.equals(""))) {
							
							button.selectButton();
							
						} else {
						
							if (id.equals(json.getString(C.KEY_JSON_UNIQUE_ID))) {
								
								button.selectButton();
								
							} else {
								
								button.unSelectButton();
								
							}
						
						}
						
						button.invalidate();
						
						button.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
	
								try {
								
									int count = categoryListView.getChildCount();
									
									for (int i = 0; i < count; i++) {
										
										CatogoryButtonView view = (CatogoryButtonView) categoryListView.getChildAt(i);
										
										String selectedId = view.getUniqueId();
										
										String btnId = button.getUniqueId();
										
										if (selectedId.equals(btnId)) {
											
											view.selectButton();
											view.invalidate();
											
										} else {
											
											view.unSelectButton();
											view.invalidate();
											
										}
										
									}
									
									currentPage = null;
									
									nextPage = null;
									
									listView.removeAllViews();
									
									listView.invalidate();
									
									type = json.getString(C.KEY_JSON_UNIQUE_ID);
									
									draw();
								
								} catch (Exception e) {
									
									L.e(e);
									
								}
								
							}
						});
						
						categoryListView.addView(button);
						
					}
					
				}
				
			}
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawHorizontalScrllView(JSONObject data) {
		
		try {
			
			HscollviewAdaptet hscollviewAdaptet = new HscollviewAdaptet(context, data, "recommand");
			
			horizontalScrollView.setAdapter(hscollviewAdaptet);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawPlayList(JSONObject data) {
		
		try {

			if (data.has("show")) {
				
				currentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);
				
				nextPage = data.getString(C.KEY_JSON_NEXT_PAGE);
				
				JSONArray arr = data.getJSONArray("show");
				
				if (arr != null) {

					for (int i = 0; i < arr.length(); i++) {
						
						final VoteView v = new VoteView(context);
						
						RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, com.rs.mobile.wportal.activity.MainActivity.newsHeight);
	
						v.setLayoutParams(layoutParams);
						
						v.invalidate();
						
						final JSONObject json = new JSONObject(arr.get(i).toString());
						
						v.setUniqueId(json.getString(C.KEY_JSON_UNIQUE_ID));
						
						v.setTitle(json.getString(C.KEY_JSON_TITLE));
						
						v.getStatusTextView().setVisibility(View.GONE);
	
						v.setDate(json.getString(C.KEY_JSON_DATE));
						
						ImageUtil.drawImageViewBuFullUrl(context, v.getImageView(), json, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);

						v.invalidate();
						
						v.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								try {
								
									Intent i = new Intent(context, ArtCenterDetailActivity.class);
									i.putExtra("contentID", json.getString(C.KEY_JSON_UNIQUE_ID));
									context.startActivity(i);
									
								} catch (Exception e) {
									
									L.e(e);
									
								}
								
							}
						});
						
						if (i > 0) {
						
							RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) v.getLayoutParams();
							p.topMargin = 10;
							v.setLayoutParams(p);
						
						}
						
						listView.addView(v);

					}
				
				}
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
}
