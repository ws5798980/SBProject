package com.rs.mobile.common.fragment;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.rs.mobile.common.C;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.UiUtil;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FragmentShowImage extends Fragment {

	WImageView image;
	boolean load;
	private View rootView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		load=true;
		
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		
		// TODO Auto-generated method stub
		if (load) {
		   rootView = inflater.inflate(com.rs.mobile.common.R.layout.fragment_showimg, container, false);// 关联布局文件
			initViews(rootView);
			load=false;
		}
		
		return rootView;
	}

	public FragmentShowImage newInstance(Bundle args) {

		FragmentShowImage f = new FragmentShowImage();
		f.setArguments(args);
		return f;
	}

	private void initViews(final View rootView) {

		Bundle bundle = getArguments();
		final String url = bundle.getString("url", C.EXCOMPLE_URL);
		image = (WImageView) rootView.findViewById(com.rs.mobile.common.R.id.img_show);
		ImageUtil.drawImageFromUri(url, image);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
		ImagePipeline imagePipeline = Fresco.getImagePipeline();
		DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, getContext());
		dataSource.subscribe(new BaseBitmapDataSubscriber() {

			@Override
			public void onNewResultImpl(@Nullable final Bitmap bitmap) {

				// You can use the bitmap in only limited ways
				// No need to do any cleanup.
				float a=(float)bitmap.getWidth() / (float)bitmap.getHeight();
				float b=(float)UiUtil.get_windows_width(getActivity())/(float)UiUtil.get_windows_height(getActivity());
				final float aR=a<b?b:a;
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							image.setAspectRatio(aR);
						}	});	
				
				
			}

			@Override
			public void onFailureImpl(DataSource dataSource) {

				// No cleanup required here.
			}
		}, CallerThreadExecutor.getInstance());
		
	}
}
