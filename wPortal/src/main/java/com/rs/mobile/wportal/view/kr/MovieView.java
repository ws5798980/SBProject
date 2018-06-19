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
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.kr.MovieDetailActivity;
import com.rs.mobile.wportal.adapter.kr.MovieListAdapter;
import com.rs.mobile.wportal.activity.kr.MainActivity;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter.ItemClickListener;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class MovieView extends RelativeLayout{

	public boolean isInit = false;
	
	private Context context;
	
	private TextView titleTextView;
	
	private PullToRefreshScrollView scrollView;
	
	private ViewPager viewPager;
	
	private ViewPagerAdapter viewPagerAdapter;
	
	private LinearLayout listView;
	
	private MovieListAdapter adapter;
	
	private String currentPage = "";
	
	private String nextPage = "";
	
	private String type = ""; 
	
	private CatogoryButtonView liveBtn, expectedBtn;
	
	private RelativeLayout listLayout;
	
	private LinearLayout detailLayout;
//	
//	private RelativeLayout movieInfoLayout;
//	
//	private WImageView movieImageView;
//	
//	private TextView movieTitleTextView;
//	
//	private RatingBar ratingBar;
//	
//	private TextView pointTextView;
//	
//	private TextView infoTextView01;
//	
//	private TextView infoTextView02;
//	
//	private TextView infoTextView03;
//	
//	private TextView summaryTextView;
//	
//	private TextView moreSummaryBtn;
//	
//	private HorizontalListView hscrollview;
//	
//	private LinearLayout detailListView;
	
	public MovieView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public MovieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public MovieView(Context context, AttributeSet attrs, int defStyleAttr) {
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

			View v = ((MainActivity)context).inflator.inflate(com.rs.mobile.wportal.R.layout.layout_movie, null);
			
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
			
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			
			viewPager.setLayoutParams(params);
			
			LinearLayout categoryArea = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.category_area);
			
			params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			
			params.addRule(RelativeLayout.BELOW, com.rs.mobile.wportal.R.id.view_pager);
			
			categoryArea.setLayoutParams(params);
			
			listView = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.list_view);
			
			params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			
			params.addRule(RelativeLayout.BELOW, com.rs.mobile.wportal.R.id.category_area);
			
			listView.setLayoutParams(params);
			
			liveBtn = (CatogoryButtonView)v.findViewById(com.rs.mobile.wportal.R.id.live_movie_btn);
			liveBtn.setTitle(getResources().getString(com.rs.mobile.wportal.R.string.kr_movie_live));
			liveBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					expectedBtn.unSelectButton();
					liveBtn.selectButton();
					
					type = "1";
					
					currentPage = "";
					
					nextPage = "";
					
					listView.removeAllViews();
					
					draw();
					
				}
			});
			
			expectedBtn = (CatogoryButtonView)v.findViewById(com.rs.mobile.wportal.R.id.expected_movie_btn);
			expectedBtn.setTitle(getResources().getString(com.rs.mobile.wportal.R.string.kr_movie_expire));
			expectedBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					liveBtn.unSelectButton();
					expectedBtn.selectButton();
					
					type = "2";

					currentPage = "";
					
					nextPage = "";
					
					listView.removeAllViews();
					
					draw();
					
				}
			});
			
			liveBtn.selectButton();
			
//			v.findViewById(R.id.close_btn).setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					
//					titleTextView.setText(context.getResources().getString(R.string.kr_movie_title));
//					
//					listLayout.setVisibility(View.VISIBLE);
//					
//					detailLayout.setVisibility(View.GONE);
//					
//				}
//			});
			
			listLayout = (RelativeLayout)v.findViewById(com.rs.mobile.wportal.R.id.list_layout);
			
//			detailLayout = (LinearLayout)v.findViewById(R.id.detail_layout);
//			
//			movieInfoLayout = (RelativeLayout)v.findViewById(R.id.movie_info_layout);
//			
//			movieImageView = (WImageView)v.findViewById(R.id.movie_image_view);
			
			titleTextView = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			
//			movieTitleTextView = (TextView)v.findViewById(R.id.movie_title_text_view);
//			
//			ratingBar = (RatingBar)v.findViewById(R.id.rationg_bar);
//			
//			pointTextView = (TextView)v.findViewById(R.id.point_text_view);
//			
//			infoTextView01 = (TextView)v.findViewById(R.id.info_text_view_01);
//			
//			infoTextView02 = (TextView)v.findViewById(R.id.info_text_view_02);
//			
//			infoTextView03 = (TextView)v.findViewById(R.id.info_text_view_03);
//			
//			summaryTextView = (TextView)v.findViewById(R.id.summary_text_view);
//			
//			moreSummaryBtn = (TextView)v.findViewById(R.id.more_summary_btn);
//			
//			hscrollview = (HorizontalListView)v.findViewById(R.id.hscrollview);
//			
//			detailListView = (LinearLayout)v.findViewById(R.id.detail_list_view);
			
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
						
						if (adapter != null && nextPage != null && !nextPage.equals("") && !nextPage.equals("0")) {

							drawMovieList(data);
						
						} else {
							drawMovieList(data.getJSONObject("movie"));
							drawViewPager(data);
						}

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
			};
			
			if (currentPage != null && !currentPage.equals("") && nextPage != null && !nextPage.equals("")) {
				
				HashMap<String, String> params = new HashMap<String, String>();
				
				params.put(C.KEY_JSON_TYPE, type);
				
				params.put(C.KEY_JSON_CURRENT_PAGE, currentPage);
				
				params.put(C.KEY_JSON_NEXT_PAGE, nextPage);
		
				okhttp.addGetRequest(callbackLogic, Constant.BASE_URL_KR + Constant.KR_CINEMA_LIST, params);
				
			} else if (type != null && !type.equals("")) {
				
				HashMap<String, String> params = new HashMap<String, String>();
				
				params.put(C.KEY_JSON_TYPE, type);
		
				okhttp.addGetRequest(callbackLogic, Constant.BASE_URL_KR + Constant.KR_CINEMA_LIST, params);
				
			} else {
				
				okhttp.addGetRequest(callbackLogic, Constant.BASE_URL_KR + Constant.KR_CINEMA_LIST);
				
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
	
//	private Handler handle = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			
//			try {
//			
//				Object[] objs = (Object[])msg.obj;
//	
//				final Bitmap bitmap = (Bitmap) objs[0];
//				
//				final int position = (Integer) objs[1];
//				
//				if (bitmap != null) {
//
//					movieInfoLayout.setBackgroundDrawable(ImageUtil.getFastblur(context, bitmap, 40));
//					
//				}
//			
//			} catch (Exception e) {
//				
//				L.e(e);
//				
//			}
//
//		}
//	};
	
	public void drawMovieList(JSONObject data) {
		
		try {
			
			JSONArray arr = data.getJSONArray("movie");
			
			currentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);
			
			nextPage = data.getString(C.KEY_JSON_NEXT_PAGE);
			
			if (arr != null) {

				for (int i = 0; i < arr.length(); i++) {
					
					final MovieListItemView v = new MovieListItemView(context);
					
					RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, com.rs.mobile.wportal.activity.MainActivity.newsHeight);

					v.setLayoutParams(layoutParams);
					
					v.invalidate();
					
					final JSONObject json = new JSONObject(arr.get(i).toString());
					
					v.setContentID(json.getString("contentID"));
					
					v.setTitle(json.getString("subject"));
					
					if (json.has("description"))
						v.setContent(json.getString("description"));
					
					v.setActor(json.getString("actors"));
					
					v.setRating((float) json.getDouble("points"));

					v.set3D(json.getString("is3D"));
					
					ImageUtil.drawImageViewBuFullUrl(context, v.getImageView(), json, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);

					v.invalidate();
					
					v.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							try {

								Intent i = new Intent(context, MovieDetailActivity.class);
								
								i.putExtra("contentID", json.getString("contentID"));
								
								context.startActivity(i);
								
//								listLayout.setVisibility(View.GONE);
//								
//								detailLayout.setVisibility(View.VISIBLE);
//								
//								titleTextView.setText(context.getResources().getString(R.string.kr_movie_detail));
//								
//								OkHttpHelper okhttp = new OkHttpHelper(context);
//								
//								CallbackLogic callbackLogic = new CallbackLogic() {
//						
//									@Override
//									public void onNetworkError(Request request, IOException e) {
//										// TODO Auto-generated method stub
//						
//										scrollView.onRefreshComplete();
//										
//									}
//						
//									@Override
//									public void onBizSuccess(String responseDescription, JSONObject data, String all_data) {
//										// TODO Auto-generated method stub
//										
//										try {
//										
//											L.d(all_data);
//											
//											data = data.getJSONObject(C.KEY_JSON_DATA);
//											
//											Uri imageUri = Uri.parse(data.getString(C.KEY_JSON_IMAGE_URL));
//											
//											ImageUtil.drawIamge(movieImageView, imageUri);
//
//											getFrescoCacheBitmap(handle, imageUri, context);
//											
//											movieTitleTextView.setText(data.getString("subject"));
//											
//											String point = data.getString("point");
//											
//											if (point == null || point.equals(""))
//												point = "0";
//											
//											ratingBar.setRating(Float.parseFloat(point));
//											
//											pointTextView.setText(point + context.getResources().getString(R.string.kr_movie_rating));
//											
//											infoTextView01.setText(data.getString("plot"));
//											
//											infoTextView02.setText(data.getString("area") + "|" + data.getString("howLong"));
//											
//											infoTextView03.setText(context.getResources().getString(R.string.kr_movie_start) + " : " + data.getString("date"));
//											
//											summaryTextView.setText(data.getString("detail"));
//											
//											HscollviewAdaptet hscollviewAdaptet = new HscollviewAdaptet(context, data.getJSONArray("images"));
//											
//											hscrollview.setAdapter(hscollviewAdaptet);
//											
////											scrollView.onRefreshComplete();
//											
//										} catch (Exception e) {
//											// TODO: handle exception
//											L.e(e);
//											
//											scrollView.onRefreshComplete();
//											
//										}
//
//									}
//						
//									@Override
//									public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
//										// TODO Auto-generated method stub
//						
//										scrollView.onRefreshComplete();
//										
//									}
//								};
//								
//								HashMap<String, String> params = new HashMap<String, String>();
//								
//								params.put("movieId", json.getString("contentID"));
//						
//								okhttp.addGetRequest(callbackLogic, C.BASE_URL_KR + C.KR_CINEMA_DETAIL, params);
//								
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

					listView.invalidate();
				}
			
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
//	
//	public void getFrescoCacheBitmap(final Handler handler, Uri uri, Context context) {
//		// final Bitmap frescoTepBm;
//		try {
//		
//			ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).setProgressiveRenderingEnabled(true)
//					.build();
//			ImagePipeline imagePipeline = Fresco.getImagePipeline();
//	
//			DataSource dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
//			dataSource.subscribe(new BaseBitmapDataSubscriber() {
//				@Override
//				public void onNewResultImpl(Bitmap bitmap) {
//					if (bitmap == null) {
//						// Log.e(TAG,"保存图片失败啦,无法下载图片");
//						handler.sendEmptyMessage(0);
//						return;
//					}
//					Message message = new Message();
//					message.obj = new Object[] {bitmap};
//					handler.sendMessage(message);
//				}
//	
//				@Override
//				public void onFailureImpl(DataSource dataSource) {
//					handler.sendEmptyMessage(0);
//				}
//			}, CallerThreadExecutor.getInstance());
//		
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//	}
//	
}
