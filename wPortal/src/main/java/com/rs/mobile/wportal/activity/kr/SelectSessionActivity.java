package com.rs.mobile.wportal.activity.kr;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.KrSelectSessionAdapter;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.adapter.VpAdapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

@SuppressWarnings("deprecation")
public class SelectSessionActivity extends BaseActivity {

	private ViewPager viewpager;
	private WImageView imageview;
	private VpAdapter adapter;
	private List<View> viewlist;
	private LinearLayout container;
	private String movieId;
	private RelativeLayout relativeLayout_001, relativeLayout_002, relativeLayout_003;
	private TextView textview_001, textview_002, textview_003, text_name, point_text_view, text_classify;
	private LinearLayout underLine001, underLine002, underLine003;
	private ListView listView;
	private KrSelectSessionAdapter adapter_001;
	private JSONArray arr_01;
	private JSONArray arr_02;
	private JSONArray arr_03;
	private RatingBar rationg_bar;
	private JSONArray arr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_kr_select_session);
		initView();
		initData();
		movieId = "2";
	}

	private Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			try {

				Object[] objs = (Object[]) msg.obj;

				final Bitmap bitmap = (Bitmap) objs[0];

				if (bitmap != null) {

					container.setBackgroundDrawable(ImageUtil.getFastblur(SelectSessionActivity.this, bitmap, 40));

				}

			} catch (Exception e) {

				L.e(e);

			}

		}
	};

	private void initView() {
		viewpager = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.viewpager);

		viewpager.setLayoutParams(new LinearLayout.LayoutParams(get_windows_width(SelectSessionActivity.this) / 5,
				(int) (get_windows_width(getApplicationContext()) / 3)));
		viewpager.setPageMargin(10);
		container = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.container);
		container.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return viewpager.dispatchTouchEvent(event);
			}
		});
		viewpager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = arr.getJSONObject(arg0);
					getFrescoCacheBitmap(handle, Uri.parse(obj.get("imgUrl").toString()));
					movieId = obj.getString("movieId");
					initData();
				} catch (Exception e) {
					// TODO: handle exception
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
		viewpager.setPageTransformer(true, new PageTransformer() {
			public static final float MAX_SCALE = 1.0f;
			public static final float MIN_SCALE = 0.6f;

			@Override
			public void transformPage(View page, float position) {
				// TODO Auto-generated method stub
				if (position < -1) {
					position = -1;
				} else if (position > 1) {
					position = 1;
				}

				float tempScale = position < 0 ? 1 + position : 1 - position;

				float slope = (MAX_SCALE - MIN_SCALE) / 1;
				// 一个公式
				float scaleValue = MIN_SCALE + tempScale * slope;
				page.setScaleX((float) (scaleValue));
				page.setScaleY((float) (scaleValue));

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
					page.getParent().requestLayout();
				}
			}
		});
		relativeLayout_001 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_001);
		relativeLayout_001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changStatus(0);
			}
		});
		relativeLayout_002 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_002);
		relativeLayout_002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changStatus(1);
			}
		});
		relativeLayout_003 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_003);
		relativeLayout_003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changStatus(2);
			}
		});
		textview_001 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_001);
		textview_002 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_002);
		textview_003 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_003);
		listView = (ListView) findViewById(com.rs.mobile.wportal.R.id.listView);
		underLine001 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine001);
		underLine002 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine002);
		underLine003 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine003);
		text_name = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_name);
		point_text_view = (TextView) findViewById(com.rs.mobile.wportal.R.id.point_text_view);
		text_classify = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_classify);
		rationg_bar = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.rationg_bar);
	}

	private void initData() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(SelectSessionActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					String status = data.get(C.KEY_JSON_STATUS).toString();
					if (status.equals("1")) {
						JSONObject obj = data.getJSONObject(C.KEY_JSON_DATA);
						arr = obj.getJSONArray("movies");
						JSONObject obj_1 = arr.getJSONObject(0);
						drawViewPaer(arr);
						JSONArray arr_1 = obj.getJSONArray("schedules");
						JSONObject object1 = arr_1.getJSONObject(0);
						textview_001.setText(object1.getString("title"));
						arr_01 = object1.getJSONArray("schedules");
						JSONObject object2 = arr_1.getJSONObject(1);
						textview_002.setText(object2.getString("title"));
						arr_02 = object1.getJSONArray("schedules");
						JSONObject object3 = arr_1.getJSONObject(2);
						textview_003.setText(object3.getString("title"));
						arr_03 = object1.getJSONArray("schedules");
						changStatus(0);
						text_name.setText(obj_1.getString("title"));
						text_classify.setText(obj_1.getString("moviePlot"));
						point_text_view.setText(obj_1.getString("point"));
						rationg_bar.setRating(Float.parseFloat(obj_1.getString("point")) / 2);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_KR + Constant.KR_SCREENING + movieId, params);

	}

	private void drawViewPaer(JSONArray arr) {
		viewpager.setOffscreenPageLimit(arr.length());

		adapter = new VpAdapter(SelectSessionActivity.this, arr);
		viewpager.setAdapter(adapter);
		viewpager.setCurrentItem(0);
		viewpager.setOffscreenPageLimit(arr.length());

	}

	private void changStatus(int i) {
		switch (i) {
		case 0:
			textview_001.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			underLine001.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			textview_002.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
			underLine002.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
			textview_003.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
			underLine003.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
			adapter_001 = new KrSelectSessionAdapter(arr_01, SelectSessionActivity.this);
			listView.setAdapter(adapter_001);
			break;
		case 1:
			textview_001.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
			underLine001.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
			textview_002.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			underLine002.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			textview_003.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
			underLine003.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
			adapter_001 = new KrSelectSessionAdapter(arr_02, SelectSessionActivity.this);
			listView.setAdapter(adapter_001);
			break;
		case 2:
			textview_001.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
			underLine001.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
			textview_002.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
			underLine002.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
			textview_003.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			underLine003.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			adapter_001 = new KrSelectSessionAdapter(arr_03, SelectSessionActivity.this);
			listView.setAdapter(adapter_001);
			break;
		default:
			break;
		}
	}

	public void getFrescoCacheBitmap(final Handler handler, Uri uri) {
		// final Bitmap frescoTepBm;
		try {

			ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
					.setProgressiveRenderingEnabled(true).build();
			ImagePipeline imagePipeline = Fresco.getImagePipeline();

			DataSource dataSource = imagePipeline.fetchDecodedImage(imageRequest, SelectSessionActivity.this);
			dataSource.subscribe(new BaseBitmapDataSubscriber() {
				@Override
				public void onNewResultImpl(Bitmap bitmap) {
					if (bitmap == null) {
						// Log.e(TAG,"保存图片失败啦,无法下载图片");
						handler.sendEmptyMessage(0);
						return;
					}
					Message message = new Message();
					message.obj = new Object[] { bitmap };
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
