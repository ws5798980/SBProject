//package com.rs.mobile.wportal.player;
//
//import com.rs.mobile.wportal.L;
//import R;
//import com.rs.mobile.wportal.activity.BaseActivity;
//
//import android.content.Context;
//import android.graphics.PixelFormat;
//import android.media.AudioManager;
//import android.os.Bundle;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.RelativeLayout;
//import io.vov.vitamio.MediaPlayer;
//import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
//import io.vov.vitamio.MediaPlayer.OnCompletionListener;
//import io.vov.vitamio.MediaPlayer.OnPreparedListener;
//import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
//import io.vov.vitamio.widget.CenterLayout;
//
///**
// * Outputs all JW Player Events to logging, with the exception of time events.
// */
//public class VideoPlayerView extends RelativeLayout implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {
//
//	private Context context;
//	
//	private int type;
//	
//	private CenterLayout centerLayout;
//	
//	private int mVideoWidth;
//	
//	private int mVideoHeight;
//	
//	private MediaPlayer mMediaPlayer;
//	
//	private SurfaceView mPreview;
//	
//	private SurfaceHolder holder;
//	
//	private String path;
//	
//	private Bundle extras;
//	
//	private boolean mIsVideoSizeKnown = false;
//	
//	private boolean mIsVideoReadyToBePlayed = false;
//	
//	public static final int TYPE_LOCAL_VIDEO = 0;
//	
//	public static final int TYPE_LIVE_VIDEO = 1;
//	
//	public VideoPlayerView(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//		
//		initView(context);
//		
//	}
//
//	public VideoPlayerView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//		
//		initView(context);
//		
//	}
//
//	public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
//		super(context, attrs, defStyleAttr);
//		// TODO Auto-generated constructor stub
//
//		initView(context);
//		
//	}
//	
//	public void initView(Context context) {
//		
//		try {
//			
//			this.context = context;
//			
//			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			
//			View v = inflator.inflate(R.layout.layout_video_player, null);
//			
//			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//	
//			centerLayout = (CenterLayout)v.findViewById(R.id.center_layout);
//			
//			mPreview = (SurfaceView) findViewById(R.id.surface);
//			
//			addView(v);
//			
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//		
//	}
//	
//	public void init(String path) {
//		
//		try {
//			
//			this.path = path;
//
//			holder = mPreview.getHolder();
//			
//			holder.addCallback(this);
//			
//			holder.setFormat(PixelFormat.RGBA_8888); 
//			
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//		
//	}
//	
//	public void playVideo() {
//		
//		try {
//
//			doCleanUp();
//
//			// Create a new media player and set the listeners
//			mMediaPlayer = new MediaPlayer(context);
//			mMediaPlayer.setDataSource(path);
//			mMediaPlayer.setDisplay(holder);
//			mMediaPlayer.prepareAsync();
//			mMediaPlayer.setOnBufferingUpdateListener(this);
//			mMediaPlayer.setOnCompletionListener(this);
//			mMediaPlayer.setOnPreparedListener(this);
//			mMediaPlayer.setOnVideoSizeChangedListener(this);
//			((BaseActivity)context).setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//	}
//	
//	
//	
//	private void releaseMediaPlayer() {
//		
//		try {
//		
//			if (mMediaPlayer != null) {
//				
//				mMediaPlayer.release();
//				mMediaPlayer = null;
//				
//			}
//		
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//		
//	}
//
//	private void doCleanUp() {
//		
//		try {
//		
//			mVideoWidth = 0;
//			
//			mVideoHeight = 0;
//			
//			mIsVideoReadyToBePlayed = false;
//			
//			mIsVideoSizeKnown = false;
//		
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//		
//	}
//
//	private void startVideoPlayback() {
//
//		try {
//		
//			holder.setFixedSize(mVideoWidth, mVideoHeight);
//			
//			mMediaPlayer.start();
//		
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//		
//	}
//
//	@Override
//	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void surfaceCreated(SurfaceHolder holder) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void surfaceDestroyed(SurfaceHolder holder) {
//		// TODO Auto-generated method stub
//		
//		playVideo();
//		
//	}
//
//	@Override
//	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//		// TODO Auto-generated method stub
//		
//		try {
//			
//			if (width == 0 || height == 0) {
//				
//				return;
//				
//			}
//			
//			mIsVideoSizeKnown = true;
//			
//			mVideoWidth = width;
//			
//			mVideoHeight = height;
//			
//			if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
//				
//				startVideoPlayback();
//				
//			}
//			
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//		
//	}
//
//	@Override
//	public void onPrepared(MediaPlayer mp) {
//		// TODO Auto-generated method stub
//		
//		try {
//			
//			if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
//				
//				startVideoPlayback();
//				
//			}
//			
//		} catch (Exception e) {
//			
//			L.e(e);
//			
//		}
//		
//	}
//
//	@Override
//	public void onCompletion(MediaPlayer mp) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onBufferingUpdate(MediaPlayer mp, int percent) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//}
