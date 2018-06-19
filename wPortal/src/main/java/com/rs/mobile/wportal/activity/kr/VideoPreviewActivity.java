//package com.rs.mobile.wportal.activity.kr;
//
//import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
//import R;
//import com.rs.mobile.wportal.activity.BaseActivity;
//import com.rs.mobile.wportal.kr.KRMediaController;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//import io.vov.vitamio.MediaPlayer;
//import io.vov.vitamio.MediaPlayer.OnTimedTextListener;
//import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
//import io.vov.vitamio.widget.MediaController;
//import io.vov.vitamio.widget.VideoView;
//
//public class VideoPreviewActivity extends BaseActivity {
//
//	private MediaController controller;
//	
//	private VideoView videoView;
//	
//	private FrameLayout controllerAnchor;
//	
//	private String path;
//	
//	private TextView titleTextView;
//	
//	private PullToRefreshScrollView scrollView;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.activity_kr_video_preview);
//		
//		videoView = (VideoView) findViewById(R.id.surface_view); 
//		
//		controllerAnchor = (FrameLayout)findViewById(R.id.controller_anchor);
//
//		controller = new KRMediaController(VideoPreviewActivity.this, controllerAnchor);
//		
//		videoView.setVideoPath("rtmp://v3c74ef06.live.126.net/live/6d0d6149795746209dc015dc81f352b4");
//		
//		videoView.requestFocus(); 
//		
//		videoView.setOnTimedTextListener(new OnTimedTextListener() {
//			
//			@Override
//			public void onTimedTextUpdate(byte[] pixels, int width, int height) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onTimedText(String text) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//
//		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//			@Override
//			public void onPrepared(MediaPlayer mediaPlayer) {
//				// optional need Vitamio 4.0
//
//				mediaPlayer.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {
//					
//					@Override
//					public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//						// TODO Auto-generated method stub
//
//						try {
//						
////						videoView.setMediaController(controller, controllerAnchor);
//
////						videoView.setMediaController(controller);
////						
////						controller.setVisibility(View.GONE);
////						
////						controller.setAnchorView(videoView);
////						
////						View v = (View) controller.getParent();
////						
////						((ViewGroup) v).removeView(controller);
////						
////						controllerAnchor.addView(controller);
////						
////						controller.setVisibility(View.INVISIBLE);
//						
//						} catch (Exception e) {
//							
//							e(e);
//							
//						}
//						
//					}
//				});
//				
//				videoView.start();
//				
////			    controller.setAnchorView(controllerAnchor);   
//			                
//			}
//		});
//		
//		videoView.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				
//				if (controller != null) {
//					controller.setVisibility(View.VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                        	controller.setVisibility(View.INVISIBLE);
//                        }
//                    }, 2000);
//                }
//				
//				return false;
//			}
//		});
//		
//	}
//	
//}