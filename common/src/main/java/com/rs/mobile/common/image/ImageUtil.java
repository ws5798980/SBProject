package com.rs.mobile.common.image;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.util.CommonUtil;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageUtil {
	/**
	 * 
	 * author: dongjun
	 * 
	 * time : 2016/11/23 desc : img
	 * 
	 */
	// 分配的可用内存
	private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

	// 使用的缓存数量
	private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

	// 小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
	private static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 20 * ByteConstants.MB;

	// 小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
	private static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 60 * ByteConstants.MB;

	// 默认图极低磁盘空间缓存的最大值
	private static final int MAX_DISK_CACHE_VERYLOW_SIZE = 20 * ByteConstants.MB;

	// 默认图低磁盘空间缓存的最大值
	private static final int MAX_DISK_CACHE_LOW_SIZE = 60 * ByteConstants.MB;

	// 默认图磁盘缓存的最大值
	private static final int MAX_DISK_CACHE_SIZE = 100 * ByteConstants.MB;

	// 小图所放路径的文件夹名
	private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "ImagePipelineCacheSmall";

	// 默认图所放路径的文件夹名
	private static final String IMAGE_PIPELINE_CACHE_DIR = "YUchengguoji";

	public static ImagePipelineConfig getDefaultImagePipelineConfig(Context context) {

		// 内存配置
		final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(MAX_MEMORY_CACHE_SIZE, // 内存缓存中总图片的最大大小,以字节为单位。
				Integer.MAX_VALUE, // 内存缓存中图片的最大数量。
				MAX_MEMORY_CACHE_SIZE, // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
				Integer.MAX_VALUE, // 内存缓存中准备清除的总图片的最大数量。
				Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。

		// 修改内存图片缓存数量，空间策略（这个方式有点恶心）
		Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
			@Override
			public MemoryCacheParams get() {
				return bitmapCacheParams;
			}
		};

		// 小图片的磁盘配置
		DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder()
				.setBaseDirectoryPath(context.getApplicationContext().getCacheDir())// 缓存图片基路径
				.setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)// 文件夹名
				.setMaxCacheSize(MAX_DISK_CACHE_SIZE)// 默认缓存的最大大小。
				.setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)// 缓存的最大大小,使用设备时低磁盘空间。
				.setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)// 缓存的最大大小,当设备极低磁盘空间
				.setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance()).build();

		// 默认图片的磁盘配置
		DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()
				.setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile())// 缓存图片基路径
				.setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)// 文件夹名
				.setMaxCacheSize(MAX_DISK_CACHE_SIZE)// 默认缓存的最大大小。
				.setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)// 缓存的最大大小,使用设备时低磁盘空间。
				.setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)// 缓存的最大大小,当设备极低磁盘空间
				.setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance()).build();

		// 缓存图片配置
		ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)

				.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
				.setSmallImageDiskCacheConfig(diskSmallCacheConfig).setMainDiskCacheConfig(diskCacheConfig)
				.setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance())
				.setResizeAndRotateEnabledForNetwork(true)
				.setDownsampleEnabled(true);

		// 就是这段代码，用于清理缓存
		NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable(new MemoryTrimmable() {
			@Override
			public void trim(MemoryTrimType trimType) {
				final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();

				if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
						|| MemoryTrimType.OnSystemLowMemoryWhileAppInBackground
								.getSuggestedTrimRatio() == suggestedTrimRatio
						|| MemoryTrimType.OnSystemLowMemoryWhileAppInForeground
								.getSuggestedTrimRatio() == suggestedTrimRatio) {
					ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
				}
			}
		});

		return configBuilder.build();
	}
	
	/**
	 * drawImageView
	 * 
	 * @param context
	 * @param imageView
	 * @param json
	 * @param imgUrlKey
	 * @param verKey
	 */
	public static void drawImageView(Context context, SimpleDraweeView imageView, String imgUrl) {

		try {

			Uri uri = Uri.parse(imgUrl);

			CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(uri));

			if (cacheKey != null) {
			
				ImagePipelineFactory.getInstance().getMainDiskStorageCache().remove(cacheKey);
			
			}
			
			imageView.setImageURI(uri);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void drawImageViewBuFullUrl(Context context, SimpleDraweeView imageView, JSONObject json, String imgUrlKey,
			String verKey, ViewGroup.LayoutParams params) {

		try {

			String imgUrl = json.getString(imgUrlKey);

			Uri uri = Uri.parse(imgUrl);

			CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(uri));

			String currentVer = json.getString(verKey);

			String localData = S.get(context, imgUrl);

			String lastVer = "";

			if (localData != null && !localData.equals("")) {

				lastVer = new JSONObject(S.get(context, imgUrl)).getString(verKey);

			}

			if (!currentVer.equals(lastVer)) {

				// 이전 데이터 삭제

				ImagePipelineFactory.getInstance().getMainDiskStorageCache().remove(cacheKey);

			}

			imageView.setLayoutParams(params);
			
			imageView.setImageURI(uri);

			S.set(context, imgUrl, json.toString());

		} catch (Exception e) {

			L.e(e);

		}

	}
	
	public static void drawImageViewBuFullUrl(Context context, SimpleDraweeView imageView, JSONObject json, String imgUrlKey,
			String verKey) {

		try {

			String imgUrl = json.getString(imgUrlKey);

			Uri uri = Uri.parse(imgUrl);

			CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(uri));

			String currentVer = json.getString(verKey);

			String localData = S.get(context, imgUrl);

			String lastVer = "";

			if (localData != null && !localData.equals("")) {

				lastVer = new JSONObject(S.get(context, imgUrl)).getString(verKey);

			}

			if (!currentVer.equals(lastVer)) {

				// 이전 데이터 삭제

				ImagePipelineFactory.getInstance().getMainDiskStorageCache().remove(cacheKey);

			}

			imageView.setImageURI(uri);

			S.set(context, imgUrl, json.toString());

		} catch (Exception e) {

			L.e(e);

		}

	}
	
	public static void drawImageView(Context context, String baseUrl, SimpleDraweeView imageView, JSONObject json, String imgUrlKey,
			String verKey) {

		try {

			String imgUrl = json.getString(imgUrlKey);

			Uri uri = Uri.parse(baseUrl + imgUrl);

			CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(uri));

			String currentVer = json.getString(verKey);

			String localData = S.get(context, imgUrl);

			String lastVer = "";

			if (localData != null && !localData.equals("")) {

				lastVer = new JSONObject(S.get(context, imgUrl)).getString(verKey);

			}

			if (!currentVer.equals(lastVer)) {

				// 이전 데이터 삭제

				ImagePipelineFactory.getInstance().getMainDiskStorageCache().remove(cacheKey);

			}

			imageView.setImageURI(uri);

			S.set(context, imgUrl, json.toString());

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void drawImageView1(Context context, SimpleDraweeView imageView, JSONObject json, String imageUrlKey,
			String verKey) {

		try {
            if (json.has(verKey)) {
            	String imgUrl = json.getString(imageUrlKey);

    			Uri uri = Uri.parse( imgUrl);

    			CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(uri));

    			String currentVer = json.getString(verKey);

    			String localData = S.get(context, imgUrl);

    			String lastVer = "";

    			if (localData != null && !localData.equals("")) {

    				lastVer = new JSONObject(S.get(context, imgUrl)).getString(verKey);

    			}

    			if (!currentVer.equals(lastVer)) {

    				// 이전 데이터 삭제

    				ImagePipelineFactory.getInstance().getMainDiskStorageCache().remove(cacheKey);

    			}

    			imageView.setImageURI(uri);

    			S.set(context, imgUrl, json.toString());
			}else {
				String imgUrl = json.getString(imageUrlKey);

    			
				drawImageFromUri1(imgUrl, imageView);
			}
			

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * getCachedImageOnDisk
	 * 
	 * @param cacheKey
	 * @return
	 */
	public static File getCachedImageOnDisk(CacheKey cacheKey) {

		File localFile = null;

		if (cacheKey != null) {

			if (ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey)) {
				BinaryResource resource = ImagePipelineFactory.getInstance().getMainDiskStorageCache()
						.getResource(cacheKey);
				localFile = ((FileBinaryResource) resource).getFile();
			} else if (ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().hasKey(cacheKey)) {
				BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache()
						.getResource(cacheKey);
				localFile = ((FileBinaryResource) resource).getFile();
			}
		}

		return localFile;
	}
	
	public static void removeImageOnDisk(Uri uri) {

		try {
			
			if (uri != null) {
				
	            ImagePipeline imagePipeline=Fresco.getImagePipeline();
	            
				imagePipeline.evictFromCache(uri);
				
				imagePipeline.evictFromDiskCache(uri);
				
				imagePipeline.evictFromMemoryCache(uri);
	
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}

	/**
	 * 按正方形裁切图片
	 */
	public static Bitmap ImageCrop(Bitmap bitmap) {
		int w = bitmap.getWidth(); // 得到图片的宽，高
		int h = bitmap.getHeight();

		int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

		int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
		int retY = w > h ? 0 : (h - w) / 2;

		// 下面这句是关键
		return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
	}
	
	/**
	 * 将图片保存到本地图片上
	 */
	public static String savePublishPicture(Bitmap bitmap) {
		File dir = new File(Environment.getExternalStorageDirectory() + "/wportal");
		if (!dir.exists())
			dir.mkdirs();
		String publishfilename = System.currentTimeMillis() + ".jpg";
		File f = new File(dir, publishfilename);
		try {
			if (f.exists()) {
				f.delete();
			}

			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fOut);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);// 把Bitmap对象解析成流
			fOut.flush();
			fOut.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Environment.getExternalStorageDirectory() + "/wportal/" + publishfilename;
	}
	
	public static String savePublishPicture(Bitmap bitmap, String fileName) {
		File dir = new File(Environment.getExternalStorageDirectory() + "/wportal");
		if (!dir.exists())
			dir.mkdirs();
		String publishfilename = "wportal" + fileName + ".jpg";
		File f = new File(dir, publishfilename);
		try {
			if (f.exists()) {
				f.delete();
			}

			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fOut);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);// 把Bitmap对象解析成流
			fOut.flush();
			fOut.close();

		} catch (Exception e) {
			L.e(e);
		}
		return Environment.getExternalStorageDirectory() + "/wportal/" + publishfilename;
	}
	
	        public static String savePublishPicture( String url) {
	        	Bitmap image ;
	        	L.d("url+"+url);
				if (!new File(url).exists()) {  
	                System.err.println("getBitmapFromPath: file not exists");  
	                return "";  
	            }else {
	            	 image =  getBitmapFromPath(url);
	            	 if (image==null) {
						return "";
					}
				} 
		    
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			
			if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
				baos.reset();
				image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
			}
			
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
			newOpts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

			int w = newOpts.outWidth;
			int h = newOpts.outHeight;

			// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
			float hh = 800f;// 这里设置高度为800f
			float ww = 480f;// 这里设置宽度为480f
			// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			if (h > w && h > hh) {
				newOpts.inSampleSize = (int) Math.ceil(h / hh);

			} else if (h <= w && w > ww) {
				newOpts.inSampleSize = (int) Math.ceil(w / ww);

			}
//			newOpts.inSampleSize = inSampleSize;
			// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			isBm = new ByteArrayInputStream(baos.toByteArray());
			newOpts.inJustDecodeBounds = false;
			newOpts.inPreferredConfig=Bitmap.Config.RGB_565;
			newOpts.inPurgeable=true;
			newOpts.inInputShareable=true;
			
			bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
			if (!new File(CommonUtil.getDataPath()).exists())
				new File(CommonUtil.getDataPath()).mkdirs();
		String	tempPath = CommonUtil.getDataPath()  + System.currentTimeMillis() + ".jpg";
			File nFile=new File(tempPath);
			try {
				FileOutputStream outputStream=new FileOutputStream(nFile);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			File file = new File(url);
//	        if (file.isFile() && file.exists()) {
//	         file.delete();
//	        }

		return tempPath;
	}
	
//	// 质量压缩
//	public static Bitmap compressImage(Bitmap image) {
//
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		
//		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//		int tt = baos.toByteArray().length / 1024000;
//		// do {
//		// image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//
//		// 这里压缩options%，把压缩后的数据存放到baos中
//		// } while (tt>1);
//		if (tt > 1) {
//			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩options%，把压缩后的数据存放到baos中
//		} else {
//			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 这里压缩options%，把压缩后的数据存放到baos中
//		}
//		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
//		
//		return bitmap;
//	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static Bitmap comp( Bitmap image) {
      
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		if (h > w && h > hh) {
			newOpts.inSampleSize = (int) Math.ceil(h / hh);

		} else if (h <= w && w > ww) {
			newOpts.inSampleSize = (int) Math.ceil(w / ww);

		}
//		newOpts.inSampleSize = inSampleSize;
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		newOpts.inJustDecodeBounds = false;
		newOpts.inPreferredConfig=Bitmap.Config.RGB_565;
		newOpts.inPurgeable=true;
		newOpts.inInputShareable=true;
		
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		L.d("@@@@@@@@@@@@@"+bitmap.getByteCount()+"------------");
		
		return bitmap;// 压缩好比例大小后再进行质量压缩
	}
	
	public static Bitmap getBitmapFromUri(Context context, Uri uri) {
		try {
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}
	}
	
	public static void drawIamge(WImageView imageView, Bitmap bitmap) {
		
		try {
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getWidth(),
					imageView.getHeight());
			
			imageView.setLayoutParams(lp);
			
			imageView.setBackground(new BitmapDrawable(bitmap));
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	
	
	@SuppressLint("NewApi")
	public static void drawIamge(ImageView imageView, Drawable drawable) {
		
		try {
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getWidth(),
					imageView.getHeight());

			imageView.setBackground(drawable);
			
			imageView.setLayoutParams(lp);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	@SuppressLint("NewApi")
	public static void drawIamge(ImageView imageView, Bitmap bitmap) {
		
		try {
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getWidth(),
					imageView.getHeight());
			
			imageView.setBackground(new BitmapDrawable(bitmap));
			
			imageView.setLayoutParams(lp);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public static void drawIamge(SimpleDraweeView imageView, Uri uri) {
		
		try {

			removeImageOnDisk(uri);
			
			imageView.destroyDrawingCache();

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getWidth(),
					imageView.getHeight());
			
			imageView.setLayoutParams(lp);

			imageView.setImageURI(uri);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public static void drawIamge(SimpleDraweeView imageView, Uri uri, ViewGroup.LayoutParams params) {
		
		try {

			removeImageOnDisk(uri);
			
			imageView.destroyDrawingCache();
			
			imageView.setLayoutParams(params);

			imageView.setImageURI(uri);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public static void drawImageFromUri(String url,WImageView imageView){
		try {
			imageView.setImageURI(Uri.parse(url));
			
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}
	}
	//本地图
	public static void drawImageFromLocal(String url,WImageView imageView){
		try {
			imageView.setImageURI(Uri.parse("file://"+url));
			
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}
	}
	public static void drawImageFromUri1(String url,SimpleDraweeView imageView){
		try {
			imageView.setImageURI(Uri.parse(url));
			
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}
	}
	/**
	 * 根据图片名生成从Assets目录加载的URI
	 * 业务相关
	 */
	public static Uri getImageUriFromAssets(String imageName) {
		try {
			if(!StringUtil.isEmpty(imageName)) {
				
				String imgFullUrl = "asset:///" + imageName;
				return Uri.parse(imgFullUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	} 
	
	public static Drawable getFastblur(Context context, Bitmap sentBitmap, int radius) {
		
		if (radius < 1) {
			
			return null;
			
		}
//		
//		if (viewPagerBg[position] != null) {
//			
//			layout.setBackgroundDrawable(viewPagerBg[position]);
//			
//			return null;
//			
//		}
		
		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int temp = 256 * divsum;
		int dv[] = new int[temp];
		for (i = 0; i < temp; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		bitmap.setPixels(pix, 0, w, 0, 0, w, h);

		Drawable d =new BitmapDrawable(bitmap);

		return d;
		
//		layout.setBackgroundDrawable(d);
//
//		viewPagerBg[position] = d;

	}
	//根据路径获得bitmap
	public static Bitmap getBitmapFromPath(String path) {  
		  
        if (!new File(path).exists()) {  
            System.err.println("getBitmapFromPath: file not exists");  
            return null;  
        }  
        FileInputStream fis;
		Bitmap bitmap=null;
		try {
			fis = new FileInputStream(path);
			bitmap=BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		 return null;
		} 
        
  
        return bitmap;  
    }  
}
