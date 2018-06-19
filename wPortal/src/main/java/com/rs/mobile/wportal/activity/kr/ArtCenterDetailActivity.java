package com.rs.mobile.wportal.activity.kr;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.GridListAdapter;
import com.rs.mobile.wportal.view.kr.ConcertTicketView;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class ArtCenterDetailActivity extends BaseActivity {

	private PullToRefreshScrollView scrollView;
	
	private RelativeLayout info_layout;
	
	private WImageView image_view;
	
	private TextView title_text_view;

	private TextView info_text_view_01;
	
	private TextView info_text_view_02;
	
	private GridView time_list_view;
	
	private GridView seat_list_view;
	
	private WebView web_view;

	private TextView price_text_view;
	
	private TextView count_text_view;
	
	private TextView ticket_order_btn;
	
	private GridListAdapter timeGridListAdapter;
	
	private GridListAdapter seatGridListAdapter;
	
	private LinearLayout ticket_area;
	
	private int totalPrice = 0;
	
	public LayoutInflater inflator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			
			setContentView(com.rs.mobile.wportal.R.layout.activity_concert_detail);
			
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

					draw();
					
				}
	
				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub

					scrollView.onRefreshComplete();
					
				}
			});

			info_layout = (RelativeLayout)findViewById(com.rs.mobile.wportal.R.id.info_layout);
			
			image_view = (WImageView)findViewById(com.rs.mobile.wportal.R.id.image_view);
			
			DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(displayMetrics.heightPixels/6, displayMetrics.heightPixels/5);
			
			layoutParams.setMargins(0, 0, getResources().getDimensionPixelSize(com.rs.mobile.wportal.R.dimen.marginx2), 0);
			
			image_view.setLayoutParams(layoutParams);
			
			title_text_view = (TextView)findViewById(com.rs.mobile.wportal.R.id.condert_title_text_view);

			info_text_view_01 = (TextView)findViewById(com.rs.mobile.wportal.R.id.info_text_view_01);
			
			info_text_view_02 = (TextView)findViewById(com.rs.mobile.wportal.R.id.info_text_view_02);
			
			time_list_view = (GridView)findViewById(com.rs.mobile.wportal.R.id.time_list_view);
			time_list_view.setOnTouchListener(new View.OnTouchListener() {
			       
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
						
						if (Math.abs(lastY - event.getY()) > StringUtil.dip2px(ArtCenterDetailActivity.this, 100)) {
							
							scrollView.requestDisallowInterceptTouchEvent(false);
							
						}
						
						break;
					}
					return false;
		        }
		    });
			time_list_view.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
					// TODO Auto-generated method stub
					
					try {
					
						JSONObject item = (JSONObject) adapter.getItemAtPosition(position);
						
						timeGridListAdapter.setSelectedPosition(position);
						
						seatGridListAdapter = new GridListAdapter(ArtCenterDetailActivity.this, item.getJSONArray("seats"), GridListAdapter.TYPE_TYPE_TICKET_PRICE);
					
						seat_list_view.setAdapter(seatGridListAdapter);
						
						ticket_area.removeAllViews();

						ticket_area.invalidate();
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
				
			});
			seat_list_view = (GridView)findViewById(com.rs.mobile.wportal.R.id.seat_list_view);
			seat_list_view.setOnTouchListener(new View.OnTouchListener() {
			       
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
						
						if (Math.abs(lastY - event.getY()) > StringUtil.dip2px(ArtCenterDetailActivity.this, 100)) {
							
							scrollView.requestDisallowInterceptTouchEvent(false);
							
						}
						
						break;
					}
					return false;
		        }
		    });
			seat_list_view.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
					// TODO Auto-generated method stub
					
					if (seatGridListAdapter.selectedPositions.contains(position + "")) {
						
						removeTicketList((JSONObject)seatGridListAdapter.getItem(position));
						
					} else {
						
						drawTicketList((JSONObject)seatGridListAdapter.getItem(position));
						
					}
					
					seatGridListAdapter.setSelectedPositions(position + "");
					
				}
				
			});
			
			web_view = (WebView)findViewById(com.rs.mobile.wportal.R.id.web_view);
			web_view.setHorizontalScrollBarEnabled(false);
			web_view.setVerticalScrollBarEnabled(false);

			price_text_view = (TextView)findViewById(com.rs.mobile.wportal.R.id.price_text_view);
			
			count_text_view = (TextView)findViewById(com.rs.mobile.wportal.R.id.count_text_view);
			
			ticket_order_btn = (TextView)findViewById(com.rs.mobile.wportal.R.id.ticket_order_btn);
			ticket_order_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			ticket_area = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.ticket_area);
			
			draw();
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	private Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			try {
			
				Object[] objs = (Object[])msg.obj;
	
				final Bitmap bitmap = (Bitmap) objs[0];
				
				if (bitmap != null) {

					info_layout.setBackgroundDrawable(ImageUtil.getFastblur(ArtCenterDetailActivity.this, bitmap, 40));
					
				}
			
			} catch (Exception e) {
				
				L.e(e);
				
			}

		}
	};
	
	private void drawTicketList(final JSONObject item) {
		
		try {
			
			ConcertTicketView ticketView = new ConcertTicketView(ArtCenterDetailActivity.this);
			
			ticketView.draw(item);
			
			ticketView.setOnCountChangeListener(new ConcertTicketView.OnCountChangeListener() {
				
				@Override
				public void onChanged(int count) {
					// TODO Auto-generated method stub
					
					try {

						totalPrice = totalPrice + (Integer.parseInt(item.getString("price")) * count);
						
						price_text_view.setText(totalPrice + "" + getResources().getString(com.rs.mobile.wportal.R.string.money));
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
				}
			});
			
			ticket_area.addView(ticketView);
			
			ticket_area.invalidate();
			
			totalPrice = totalPrice + (Integer.parseInt(item.getString("price")));
			
			price_text_view.setText(totalPrice + "" + getResources().getString(com.rs.mobile.wportal.R.string.money));
			
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	private void removeTicketList(final JSONObject item) {
		
		try {
			
			showProgressBar();
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					try {
					
						int viewCount = ticket_area.getChildCount();
						
						if (viewCount > 1) {
						
							for (int i = 0; i < viewCount; i++) {
								
								ConcertTicketView v = (ConcertTicketView) ticket_area.getChildAt(i);
								
								if (v.getItem().getString("type").equals(item.getString("type"))) {
									
									ticket_area.removeViewAt(i);
									
									break;
									
								}
								
							}
						
						} else {
							
							ticket_area.removeAllViews();
							
						}
						
						ticket_area.invalidate();
						
						hideProgressBar();
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	private void draw() {
		
		try {
			
			OkHttpHelper okhttp = new OkHttpHelper(ArtCenterDetailActivity.this);
			
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
						
						String status = data.getString("status");
						
						if (status != null && status.equals("1")) {
						
							data = data.getJSONObject(C.KEY_JSON_DATA);
							
							Uri imageUri = Uri.parse(data.getString(C.KEY_JSON_IMAGE_URL));
							
							ImageUtil.removeImageOnDisk(imageUri);
							
							image_view.setImageURI(imageUri, ScalingUtils.ScaleType.CENTER_INSIDE);
	
							getFrescoCacheBitmap(handle, imageUri, ArtCenterDetailActivity.this);
							
							title_text_view.setText(data.getString("title"));

							info_text_view_01.setText(data.getString("range") + getResources().getString(com.rs.mobile.wportal.R.string.money));
							
							info_text_view_02.setText(getResources().getString(com.rs.mobile.wportal.R.string.kr_concert_detial_004) + " : " + data.getString("startDate") + " ~ " + data.getString("endDate"));

							JSONArray dArr = data.getJSONArray("screenings");
							
							JSONArray sArr = dArr.getJSONObject(0).getJSONArray("seats");
							
							timeGridListAdapter = new GridListAdapter(ArtCenterDetailActivity.this, dArr, GridListAdapter.TYPE_TYPE_TICKET_TIME);

							seatGridListAdapter = new GridListAdapter(ArtCenterDetailActivity.this, sArr, GridListAdapter.TYPE_TYPE_TICKET_PRICE);
							
							time_list_view.setAdapter(timeGridListAdapter);
							
							seat_list_view.setAdapter(seatGridListAdapter);
							
							String html = data.getString("introduce");
							
							web_view.loadData(html, "text/html", "UTF-8");
						
						} else {
							
							t(data.getString("msg"));
							
						}
						
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
			
			params.put("", "");
	
			String url = Constant.BASE_URL_KR + Constant.KR_ARTCENTER_DETAIL + getIntent().getStringExtra("contentID");
			
			okhttp.addPostRequest(callbackLogic, url, params);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void getFrescoCacheBitmap(final Handler handler, Uri uri, Context context) {
		// final Bitmap frescoTepBm;
		try {
		
			ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).setProgressiveRenderingEnabled(true)
					.build();
			ImagePipeline imagePipeline = Fresco.getImagePipeline();
	
			DataSource dataSource = imagePipeline.fetchDecodedImage(imageRequest, ArtCenterDetailActivity.this);
			dataSource.subscribe(new BaseBitmapDataSubscriber() {
				@Override
				public void onNewResultImpl(Bitmap bitmap) {
					if (bitmap == null) {
						// Log.e(TAG,"保存图片失败啦,无法下载图片");
						handler.sendEmptyMessage(0);
						return;
					}
					Message message = new Message();
					message.obj = new Object[] {bitmap};
					handler.sendMessage(message);
				}
	
				@Override
				public void onFailureImpl(DataSource dataSource) {
					handler.sendEmptyMessage(0);
				}
			}, CallerThreadExecutor.getInstance());
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
	}
	
}
