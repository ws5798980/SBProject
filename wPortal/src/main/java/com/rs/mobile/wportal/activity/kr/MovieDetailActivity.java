package com.rs.mobile.wportal.activity.kr;

import java.io.IOException;
import java.util.HashMap;

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
import com.rs.mobile.common.view.HorizontalListView;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.adapter.HscollviewAdaptet;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.MainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class MovieDetailActivity extends BaseActivity {

	private PullToRefreshScrollView scrollView;
	
	private RelativeLayout movieInfoLayout;
	
	private WImageView movieImageView;
	
	private TextView movieTitleTextView;
	
	private RatingBar ratingBar;
	
	private TextView pointTextView;
	
	private TextView infoTextView01;
	
	private TextView infoTextView02;
	
	private TextView infoTextView03;
	
	private TextView summaryTextView;
	
	private TextView moreSummaryBtn;
	
	private HorizontalListView hscrollview;
	
	private LinearLayout detailListView;
	
	private TextView purchase_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			
			setContentView(R.layout.activity_movie_detail);
			
			findViewById(R.id.close_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					finish();
					
				}
			});
			
			scrollView = (PullToRefreshScrollView)findViewById(R.id.scroll_view);
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

			movieInfoLayout = (RelativeLayout)findViewById(R.id.movie_info_layout);
			
			movieImageView = (WImageView)findViewById(R.id.movie_image_view);
			
			DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(displayMetrics.heightPixels/6, displayMetrics.heightPixels/5);
			
			layoutParams.setMargins(0, 0, getResources().getDimensionPixelSize(R.dimen.marginx2), 0);
			
			movieImageView.setLayoutParams(layoutParams);
			
			movieTitleTextView = (TextView)findViewById(R.id.movie_title_text_view);
			
			ratingBar = (RatingBar)findViewById(R.id.rationg_bar);
			
			pointTextView = (TextView)findViewById(R.id.point_text_view);
			
			infoTextView01 = (TextView)findViewById(R.id.info_text_view_01);
			
			infoTextView02 = (TextView)findViewById(R.id.info_text_view_02);
			
			infoTextView03 = (TextView)findViewById(R.id.info_text_view_03);
			
			summaryTextView = (TextView)findViewById(R.id.summary_text_view);
			
			purchase_btn=(TextView)findViewById(R.id.purchase_btn);
			purchase_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PageUtil.jumpTo(MovieDetailActivity.this, SelectSessionActivity.class);
				}
			});
			
			moreSummaryBtn = (TextView)findViewById(R.id.more_summary_btn);
			
			moreSummaryBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (moreSummaryBtn.getText().toString().equals(getResources().getString(R.string.more))) {
					
						moreSummaryBtn.setText(getResources().getString(R.string.hide));
						
						summaryTextView.setSingleLine(false);
						
						summaryTextView.invalidate();
						
					} else {
						
						moreSummaryBtn.setText(getResources().getString(R.string.more));
						
						summaryTextView.setMaxLines(4);
						
						summaryTextView.invalidate();
						
					}
					
				}
			});
			hscrollview = (HorizontalListView)findViewById(R.id.hscrollview);
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
						
						if (Math.abs(lastY - event.getY()) > StringUtil.dip2px(MovieDetailActivity.this, 100)) {
							
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
			
			detailListView = (LinearLayout)findViewById(R.id.detail_list_view);

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

					movieInfoLayout.setBackgroundDrawable(ImageUtil.getFastblur(MovieDetailActivity.this, bitmap, 40));
					
				}
			
			} catch (Exception e) {
				
				L.e(e);
				
			}

		}
	};
	
	private void draw() {
		
		try {
			
			OkHttpHelper okhttp = new OkHttpHelper(MovieDetailActivity.this);
			
			CallbackLogic callbackLogic = new CallbackLogic() {
	
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
						
						Uri imageUri = Uri.parse(data.getString(C.KEY_JSON_IMAGE_URL));
						
						ImageUtil.removeImageOnDisk(imageUri);
						
						movieImageView.setImageURI(imageUri, ScalingUtils.ScaleType.CENTER_INSIDE);

						getFrescoCacheBitmap(handle, imageUri, MovieDetailActivity.this);
						
						movieTitleTextView.setText(data.getString("subject"));
						
						String point = data.getString("point");
						
						if (point == null || point.equals(""))
							point = "0";
						
						ratingBar.setRating(Float.parseFloat(point));
						
						pointTextView.setText(point + MovieDetailActivity.this.getResources().getString(R.string.kr_movie_rating));
						
						infoTextView01.setText(data.getString("plot"));
						
						infoTextView02.setText(data.getString("area") + "|" + data.getString("howLong"));
						
						infoTextView03.setText(MovieDetailActivity.this.getResources().getString(R.string.kr_movie_start) + " : " + data.getString("date"));
						
						summaryTextView.setText(data.getString("detail"));
						
						HscollviewAdaptet hscollviewAdaptet = new HscollviewAdaptet(MovieDetailActivity.this, data.getJSONArray("images"));
						
						hscrollview.setAdapter(hscollviewAdaptet);
						
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
			
			params.put("movieId", getIntent().getStringExtra("contentID"));
	
			okhttp.addGetRequest(callbackLogic, Constant.BASE_URL_KR + Constant.KR_CINEMA_DETAIL, params);
			
			
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
	
			DataSource dataSource = imagePipeline.fetchDecodedImage(imageRequest, MovieDetailActivity.this);
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
