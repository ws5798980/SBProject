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
import com.rs.mobile.common.S;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.GridListAdapter;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.activity.kr.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import okhttp3.Request;

public class SearchVodView extends RelativeLayout {

	public boolean isInit = false;
	
	private Context context;
	
	private int listType = 0;
	
	private final int TYPE_DEFAULT = 0;
	
	private final int TYPE_SEARCH = 1;
	
	public EditText editText;
	
	private PullToRefreshScrollView scrollView;
	
	private LinearLayout horizontalScrollArea;
	
	private LinearLayout menuBtn;
	
	public LinearLayout listView;
	
	private String currentPage = "0";
	
	private String nextPage = "1";
	
	private String videoTypeId = "0";
	
	private LinearLayout categoryPopup;
	
	private LinearLayout categoryListPopup;
	
	private GridView categoryListView;
	
	private LinearLayout categoryListBtn001;
	
	private LinearLayout categoryListBtn002;
	
	private LinearLayout categoryListBtn003;
	
	private TextView categoryListText001;
	
	private TextView categoryListText002;
	
	private TextView categoryListText003;

	private String orderBy = "";

	public SearchVodView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public SearchVodView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public SearchVodView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}
	
	public void initView(final Context context) {

		try {
		
			this.context = context;
			
			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			final View v = inflator.inflate(com.rs.mobile.wportal.R.layout.activity_kr_search_video, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	
			editText = (EditText)v.findViewById(com.rs.mobile.wportal.R.id.edit_text);
			editText.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						
						listView.removeAllViews();
						
						String searchKey = editText.getText().toString();
						
						if (searchKey == null || searchKey.equals("")) {
							
							listType = TYPE_DEFAULT;
							
							getVideoList(videoTypeId);
							
						} else {
							
							listType = TYPE_SEARCH;
							
							searchVod();
							
							String[] searchHistoryList = S.getList(context, C.KEY_KR_SEARCH_HISTORY);
							
							if (searchHistoryList != null && searchHistoryList.length > 3) {
								
								S.deleteList(context, C.KEY_KR_SEARCH_HISTORY, 0);
								
							}
							
							S.addList(context, C.KEY_KR_SEARCH_HISTORY, editText.getText().toString());
							
						}
	
						return true;
					}
					
					return false;
				}
			});
	
			scrollView = (PullToRefreshScrollView)v.findViewById(com.rs.mobile.wportal.R.id.scroll_view);
			scrollView.setOnRefreshListener(new OnRefreshListener2() {
	
				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
	
					listView.removeAllViews();
					
					currentPage = "0";
					
					nextPage = "1";
					
					if (listType == TYPE_SEARCH) {
						
						searchVod();
						
					} else {
					
						getVideoList(videoTypeId);
					
					}
					
				}
	
				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
					
					if (nextPage != null && !nextPage.equals("") && !nextPage.equals("0")) {
						
						if (listType == TYPE_SEARCH) {
							
							searchVod();
							
						} else {
						
							getVideoList(videoTypeId);
						
						}
						
					} else {
						
						scrollView.onRefreshComplete();
						
					}
					
				}
			});
			horizontalScrollArea = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.horizontal_scroll_area);
			
			menuBtn = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.menu_btn);
			menuBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					showCategoryPopup();
					
				}
			});
			
			listView = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.list_view);
			
			v.findViewById(com.rs.mobile.wportal.R.id.popup_layout).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			categoryPopup = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.cetegory_pop_up);
			
			categoryListPopup = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.search_pop_up);
			categoryListPopup.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					hideCategoryPopup();
					
				}
			});
			
			categoryListView = (GridView)v.findViewById(com.rs.mobile.wportal.R.id.category_pop_up_list_view);
			categoryListView.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
					// TODO Auto-generated method stub
					
					try {
					
						hideCategoryPopup();
						
						JSONObject obj = (JSONObject) adapter.getItemAtPosition(position);
						
						String uniqueId = obj.getString(C.KEY_JSON_UNIQUE_ID);
						
						listType = TYPE_DEFAULT;
						
						int count = horizontalScrollArea.getChildCount();
						
						for (int i = 0; i < count; i++) {
							
							CatogoryButtonView cv = (CatogoryButtonView) horizontalScrollArea.getChildAt(i);
							
							if (cv.getUniqueId().equals(uniqueId)) {
								
								cv.selectButton();
								cv.invalidate();
								
							} else {
								
								cv.unSelectButton();
								cv.invalidate();
								
							}
							
						}
						
						currentPage = "0";
						
						nextPage = "1";
						
						listView.removeAllViews();
						
						listView.invalidate();
						
						getVideoList(uniqueId);
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			categoryListText001 = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.pup_text_001);
			categoryListText002 = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.pup_text_002);
			categoryListText003 = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.pup_text_003);
			
			categoryListBtn001 = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.pup_btn_001);
			categoryListBtn001.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						orderBy = "";
						
						categoryListText001.setTextColor(MainActivity.unSelectColor);
						categoryListText002.setTextColor(MainActivity.selectColor);
						categoryListText003.setTextColor(MainActivity.selectColor);
						
						categoryListText001.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round));
						categoryListText002.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round_empty));
						categoryListText003.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round_empty));
						
						hideCategoryPopup();
						
						searchVod();
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			categoryListBtn002 = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.pup_btn_002);
			categoryListBtn002.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						orderBy = "1";
						
						categoryListText001.setTextColor(MainActivity.selectColor);
						categoryListText002.setTextColor(MainActivity.unSelectColor);
						categoryListText003.setTextColor(MainActivity.selectColor);
						
						categoryListText001.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round_empty));
						categoryListText002.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round));
						categoryListText003.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round_empty));
						
						hideCategoryPopup();
						
						searchVod();
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			categoryListBtn003 = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.pup_btn_003);
			categoryListBtn003.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						orderBy = "2";
						
						categoryListText001.setTextColor(MainActivity.selectColor);
						categoryListText002.setTextColor(MainActivity.selectColor);
						categoryListText003.setTextColor(MainActivity.unSelectColor);
						
						categoryListText001.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round_empty));
						categoryListText002.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round_empty));
						categoryListText003.setBackgroundDrawable(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_yellow_round));
						
						hideCategoryPopup();
						
						searchVod();
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			v.findViewById(com.rs.mobile.wportal.R.id.pop_up_close_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					hideCategoryPopup();
					
				}
			});
			
			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void showCategoryPopup() {
		
		try {
			
			Animation fadeAnimation = AnimationUtils.loadAnimation(context, com.rs.mobile.wportal.R.anim.fade_in);
			Animation scaleAnimation = AnimationUtils.loadAnimation(context, com.rs.mobile.wportal.R.anim.scale_bottom_in);
			
			categoryListPopup.setVisibility(View.VISIBLE);
			categoryPopup.setVisibility(View.VISIBLE);
			
			categoryListPopup.startAnimation(fadeAnimation);
			categoryPopup.startAnimation(scaleAnimation);

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void hideCategoryPopup() {
		
		try {
			
			Animation fadeAnimation = AnimationUtils.loadAnimation(context, com.rs.mobile.wportal.R.anim.fade_out);
			Animation scaleAnimation = AnimationUtils.loadAnimation(context, com.rs.mobile.wportal.R.anim.scale_top_out);
			
			categoryListPopup.setVisibility(View.GONE);
			categoryPopup.setVisibility(View.GONE);
			
			categoryListPopup.startAnimation(fadeAnimation);
			categoryPopup.startAnimation(scaleAnimation);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void getVideoList(final String id) {
		
		try {
		
			isInit = true;
			
			videoTypeId = id;
			
			HashMap<String, String> params = new HashMap<String, String>();
			
			if (id != null && !id.equals(""))
				params.put(C.KEY_REQUEST_ID, id);
			
			params.put(C.KEY_JSON_CURRENT_PAGE, currentPage);
			
			params.put(C.KEY_JSON_NEXT_PAGE, nextPage);
			
			OkHttpHelper helper = new OkHttpHelper(context);
			helper.addGetRequest(new OkHttpHelper.CallbackLogic() {
				
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
					isInit = false;
					
				}
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					
					try {
						
						data = data.getJSONObject(C.KEY_JSON_DATA);
						
						drawMenuList(id, data);
						
						drawVodList(data);
						
						currentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);
						
						nextPage = data.getString(C.KEY_JSON_NEXT_PAGE);
				
					} catch (Exception e) {
						
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
			}, Constant.BASE_URL_KR + Constant.KR_VIDEO_LIST, params);
		
		} catch (Exception e) {
			
			L.e(e);
		
			scrollView.onRefreshComplete();
			
			isInit = false;
			
		}
		
	}

	public void searchVod() {
		
		try {

			HashMap<String, String> params = new HashMap<String, String>();
			
			params.put(C.KEY_REQUEST_SEARCH_KEY, editText.getText().toString());
			
			params.put(C.KEY_JSON_TYPE, videoTypeId);
			
			params.put(C.KEY_ORDER_BY, orderBy);
			
			params.put(C.KEY_JSON_CURRENT_PAGE, currentPage);
			
			params.put(C.KEY_JSON_NEXT_PAGE, nextPage);
			
			OkHttpHelper helper = new OkHttpHelper(context);
			helper.addGetRequest(new OkHttpHelper.CallbackLogic() {
				
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
				}
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					
					try {
						
						data = data.getJSONObject(C.KEY_JSON_DATA);

						drawVodList(data);
						
						currentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);
						
						nextPage = data.getString(C.KEY_JSON_NEXT_PAGE);
				
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
					scrollView.onRefreshComplete();
					
				}
				
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					
					scrollView.onRefreshComplete();
					
				}
			}, Constant.BASE_URL_KR + Constant.KR_VIDEO_LIST_SEARCH, params);
		
		} catch (Exception e) {
			
			L.e(e);
		
			scrollView.onRefreshComplete();
			
		}
		
	}
	
	public void searchVod(String text) {
		
		try {

			editText.setText(text);
			
			HashMap<String, String> params = new HashMap<String, String>();
			
			params.put(C.KEY_REQUEST_SEARCH_KEY, editText.getText().toString());
			
			params.put(C.KEY_JSON_TYPE, videoTypeId);
			
			params.put(C.KEY_ORDER_BY, orderBy);
			
			params.put(C.KEY_JSON_CURRENT_PAGE, currentPage);
			
			params.put(C.KEY_JSON_NEXT_PAGE, nextPage);
			
			OkHttpHelper helper = new OkHttpHelper(context);
			helper.addGetRequest(new OkHttpHelper.CallbackLogic() {
				
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
				}
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					
					try {
						
						data = data.getJSONObject(C.KEY_JSON_DATA);

						drawVodList(data);
						
						currentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);
						
						nextPage = data.getString(C.KEY_JSON_NEXT_PAGE);
				
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
					scrollView.onRefreshComplete();
					
				}
				
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					
					scrollView.onRefreshComplete();
					
				}
			}, Constant.BASE_URL_KR + Constant.KR_VIDEO_LIST_SEARCH, params);
		
		} catch (Exception e) {
			
			L.e(e);
		
			scrollView.onRefreshComplete();
			
		}
		
	}
	
	public void drawVodList(JSONObject data) {
		
		try {
		
			if (data.has(C.KEY_JSON_VIDEO_LIST)) {
				
				JSONArray arr = data.getJSONArray(C.KEY_JSON_VIDEO_LIST);
				
				if (arr != null) {
				
					if (currentPage.equals("0")) {
					
						listView.removeAllViews();
					
					}
					
					for (int i = 0; i < arr.length(); i++) {
						
						final VodView view = new VodView(context, ((MainActivity)context).inflator);
						
						RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, com.rs.mobile.wportal.activity.MainActivity.newsHeight);
	
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
						
						ImageUtil.drawImageViewBuFullUrl(context, view.getImageView(), json, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
	
	//					view.setContent(json.getString("shortDescription"));
	//
	//					view.setCount(json.getString("browseCount"));
	//					
	//					ImageUtil.drawImageViewBuFullUrl(SearchActivity.this, view.getImageView(), json, "imagePath", C.KEY_JSON_VERSION);
	
						view.invalidate();
						
						view.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								try {
								
									if (videoTypeId.equals("M09")) {
										
										UiUtil.showVideoPlayer(context, json.getString(C.KEY_JSON_UNIQUE_ID), "3");
										
									} else {
									
										UiUtil.showVideoPlayer(context, json.getString(C.KEY_JSON_UNIQUE_ID), "2");
									
									}
									
								} catch (Exception e) {
									
									L.e(e);
									
								}
								
							}
						});
						
						if (i > 0) {
						
							RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) view.getLayoutParams();
							p.topMargin = 10;
							view.setLayoutParams(p);
						
						}
						
						listView.addView(view);
						
					}
					
					listView.invalidate();
				
				}
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawMenuList(String id, JSONObject data) {
		
		try {
			
			if (data.has(C.KEY_JSON_VIDEO_TYPES)) {
			
				JSONArray arr = data.getJSONArray(C.KEY_JSON_VIDEO_TYPES);
				
				if (arr != null) {
				
					horizontalScrollArea.removeAllViews();
					
					for (int i = 0; i < arr.length(); i++) {
						
						final CatogoryButtonView button = new CatogoryButtonView(context);
						
						RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	
						button.setLayoutParams(layoutParams);
						
						button.invalidate();
						
						final JSONObject json = new JSONObject(arr.get(i).toString());
						
						button.setUniqueId(json.getString(C.KEY_JSON_UNIQUE_ID));
						
						button.setTitle(json.getString(C.KEY_JSON_DISPLAY_NAME));
						
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
								
								listType = TYPE_DEFAULT;
								
								int count = horizontalScrollArea.getChildCount();
								
								for (int i = 0; i < count; i++) {
									
									CatogoryButtonView view = (CatogoryButtonView) horizontalScrollArea.getChildAt(i);
									
									if (view.getUniqueId().equals(button.getUniqueId())) {
										
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
								
								getVideoList(button.getUniqueId());
								
							}
						});
						
						horizontalScrollArea.addView(button);
						
					}
					
					GridListAdapter adapter = new GridListAdapter(context, arr, GridListAdapter.TYPE_CATEGORY);
					
					categoryListView.setAdapter(adapter);
					
				}
				
			}
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

}