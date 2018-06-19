package com.rs.mobile.common.view;

import java.io.File;

import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class BringPhotoView extends LinearLayout{

	private Context context;
	
	private LinearLayout camera_btn;
	
	private LinearLayout local_btn;
	
	private LinearLayout close_btn;
	
	public static final int CAMERA = 0;
	
	public static final int LOCAL = 1;
	
	public static final int CLOSE = 2;
	
	public static final int PHOTO_REQUEST_TAKEPHOTO = 1;
	
	public static final int PHOTO_REQUEST_GALLERY = 2;
	
	private String fileName = "takephoto";
	
	public void setFileName(String fileName) {
		
		this.fileName = fileName;
		
	}
	
	public String getFileName() {
		
		return fileName;
		
	}
	
	public BringPhotoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public BringPhotoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public BringPhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
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
			
			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View v = inflator.inflate(com.rs.mobile.common.R.layout.bring_photo_view, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			camera_btn = (LinearLayout)v.findViewById(com.rs.mobile.common.R.id.camera_btn);
			camera_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
					
						if (onItemSeletedListener != null) 
							onItemSeletedListener.onItemClick(CAMERA);
						
						String status = Environment.getExternalStorageState();
						
						if (status.equals(Environment.MEDIA_MOUNTED)) {
							
							try {
								
								File dir = new File(Environment.getExternalStorageDirectory() + "/wportal");
								
								if (!dir.exists())
									dir.mkdirs();
	
								Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								
								File f = new File(dir, fileName + ".jpg");// localTempImgDir和localTempImageFileName是自己定义的名字
								
								Uri u = Uri.fromFile(f);
								
								intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
								
								intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
								
								((BaseActivity)context).startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
								
							} catch (ActivityNotFoundException e) {
								// TODO Auto-generated catch block
								T.showToast(context, "没有找到储存目录");
							}
						} else {
							T.showToast(context, "没有存储卡");
						}
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			local_btn = (LinearLayout)v.findViewById(com.rs.mobile.common.R.id.local_btn);
			local_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (onItemSeletedListener != null) 
						onItemSeletedListener.onItemClick(LOCAL);
					
					String status = Environment.getExternalStorageState();
					
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						
						try {
							
							Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

							intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
							
							((BaseActivity)context).startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							
							L.e(e);
							
							T.showToast(context, "没有找到储存目录");
						}
					} else {
						T.showToast(context, "没有存储卡");
					}
					
				}
			});

			close_btn = (LinearLayout)v.findViewById(com.rs.mobile.common.R.id.close_btn);
			close_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (onItemSeletedListener != null) 
						onItemSeletedListener.onItemClick(CLOSE);
					
				}
			});

//			if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
//			        != PackageManager.PERMISSION_GRANTED) {
//
//				ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.CAMERA}, 1);
//			}
//
//	        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//	            // We don't have permission so prompt the user
//	        	
//	        	int REQUEST_EXTERNAL_STORAGE = 1;
//	        	 String[] PERMISSIONS_STORAGE = {
//	        	        Manifest.permission.READ_EXTERNAL_STORAGE,
//	        	        Manifest.permission.WRITE_EXTERNAL_STORAGE
//	        	 };
//	        	
//	            ActivityCompat.requestPermissions(
//	            		(Activity)context,
//	                    PERMISSIONS_STORAGE,
//	                    REQUEST_EXTERNAL_STORAGE
//	            );
//	        }

			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	private OnItemSeletedListener onItemSeletedListener;
	
	public void setOnItemSeletedListener(OnItemSeletedListener onItemSeletedListener) {
		
		this.onItemSeletedListener = onItemSeletedListener;
		
	}

	public interface OnItemSeletedListener {
		
		void onItemClick(int position);
		
	}

}
