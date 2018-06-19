package com.rs.mobile.wportal.adapter.kr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.view.kr.MainView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CViewPagerAdapter extends PagerAdapter {
	
	private Context context;
	
	public JSONArray arr;
	
	public int count;
	
	private Bitmap bitmap1;
	
	public Drawable[] viewPagerBg;
	
	private boolean isInit = false;
	
	private MainView mainView;
	
	private int type = 0; //0 : 메인, 1 : 공연

	public CViewPagerAdapter(Context context, JSONObject data, MainView mainView) {
		// TODO Auto-generated constructor stub
		this.context = context;
		try {
			
			this.arr = data.getJSONArray(C.KEY_JSON_BANNER);
			
			this.count = arr.length();
			
			viewPagerBg = new Drawable[count];
			
			this.mainView = mainView;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
	}
	
	public CViewPagerAdapter(Context context, JSONObject data, MainView mainView, String key) {
		// TODO Auto-generated constructor stub
		this.context = context;
		try {
			
			this.arr = data.getJSONArray(key);
			
			this.count = arr.length();
			
			viewPagerBg = new Drawable[count];
			
			this.mainView = mainView;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View view = null;
		view = LayoutInflater.from(context).inflate(R.layout.layout_cviewpager_item, null);
//		ImageView imageView = (ImageView) view.findViewById(R.id.cviewpager_item_img);

		WImageView wImageView = (WImageView) view.findViewById(R.id.cviewpager_item_freimg);
		
		try {
			
			final JSONObject jsonObject = new JSONObject(arr.get(position).toString());
			
			ImageUtil.drawImageViewBuFullUrl(context, wImageView, jsonObject, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);

			if (viewPagerBg[position] == null) {

				String imgUrl = jsonObject.getString(C.KEY_JSON_IMAGE_URL);
				
				Uri uri = Uri.parse(imgUrl);
				
				getFrescoCacheBitmap(position, handle, uri, context);
				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			L.e(e);
		}

		container.addView(view);
		return view;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
	private Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			try {
			
				Object[] objs = (Object[])msg.obj;
	
				final Bitmap bitmap = (Bitmap) objs[0];
				
				final int position = (Integer) objs[1];
				
				if (bitmap != null) {
					
					viewPagerBg[position] = ImageUtil.getFastblur(context, bitmap, 40);
	
					if (position == 0 && isInit == false) {
						
						mainView.viewPagerLayout.setBackgroundDrawable(viewPagerBg[position]);
						
					}
					
				}
			
			} catch (Exception e) {
				
				L.e(e);
				
			}

		}
	};
	
	public void getFrescoCacheBitmap(final int position, final Handler handler, Uri uri, Context context) {
		// final Bitmap frescoTepBm;
		try {
		
			ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).setProgressiveRenderingEnabled(true)
					.build();
			ImagePipeline imagePipeline = Fresco.getImagePipeline();
	
			DataSource dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
			dataSource.subscribe(new BaseBitmapDataSubscriber() {
				@Override
				public void onNewResultImpl(Bitmap bitmap) {
					if (bitmap == null) {
						// Log.e(TAG,"保存图片失败啦,无法下载图片");
						handler.sendEmptyMessage(0);
						return;
					}
					Message message = new Message();
					message.obj = new Object[] {bitmap, position};
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
