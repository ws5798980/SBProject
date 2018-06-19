package com.rs.mobile.common.util;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Bitmap.Config;
import android.view.Gravity;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public final class EncodingHandler {
	private static final int BLACK = 0xff000000;
	
	public static Bitmap createQRCode(String str,int widthAndHeight) throws WriterException {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = BLACK;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
  
	public static int dip2px(Context context, float dpValue) {  
	       final float scale = context.getResources().getDisplayMetrics().density;  
	       return (int) (dpValue * scale + 0.5f);  
    } 
	 public static Bitmap CreateOneDCode(Context context,String content) throws WriterException {  
	        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败  
	        BitMatrix matrix = new MultiFormatWriter().encode(content,  
	                BarcodeFormat.CODE_128, dip2px(context,310),dip2px(context,90));  
	        int width = matrix.getWidth();  
	        int height = matrix.getHeight();  
	        int[] pixels = new int[width * height];  
	        for (int y = 0; y < height; y++) {  
	            for (int x = 0; x < width; x++) {  
	                if (matrix.get(x, y)) {  
	                    pixels[y * width + x] = 0xff000000;  
	                }  
	            }  
	        }  
	  
	        Bitmap bitmap = Bitmap.createBitmap(width, height,  
	                Bitmap.Config.ARGB_8888);  
	        // 通过像素数组生成bitmap,具体参考api  
	        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
	        return bitmap;  
 }
	 
	 public static Bitmap creatBarcode(Context context, String contents,
				int desiredWidth, int desiredHeight, boolean displayCode) {
			Bitmap ruseltBitmap = null;
			int marginW = 20;
			BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

			if (displayCode) {
				
				Bitmap barcodeBitmap = encodeAsBitmap(contents, barcodeFormat,
						desiredWidth, desiredHeight);
				
				Bitmap codeBitmap = creatCodeBitmap(contents, desiredWidth + 2
						* marginW, desiredHeight, context);
				ruseltBitmap = mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(
						0, desiredHeight));
			} else {
				ruseltBitmap = encodeAsBitmap(contents, barcodeFormat,
						desiredWidth, desiredHeight);
			}

			return ruseltBitmap;
		}
		
	   protected static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
				PointF fromPoint) {
			if (first == null || second == null || fromPoint == null) {
				return null;
			}
			int marginW = 20;
			Bitmap newBitmap = Bitmap.createBitmap(
					first.getWidth() + second.getWidth() + marginW,
					first.getHeight() + second.getHeight(), Config.ARGB_4444);
			Canvas cv = new Canvas(newBitmap);
			cv.drawBitmap(first, marginW, 0, null);
			cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
			cv.save(Canvas.ALL_SAVE_FLAG);
			cv.restore();

			return newBitmap;
		}
		protected static Bitmap encodeAsBitmap(String contents,
				BarcodeFormat format, int desiredWidth, int desiredHeight) {
			final int WHITE = 0xFFFFFFFF;
			final int BLACK = 0xFF000000;

			MultiFormatWriter writer = new MultiFormatWriter();
			BitMatrix result = null;
			try {
				result = writer.encode(contents, format, desiredWidth,
						desiredHeight, null);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int width = result.getWidth();
			int height = result.getHeight();
			int[] pixels = new int[width * height];
			// All are 0, or black, by default
			for (int y = 0; y < height; y++) {
				int offset = y * width;
				for (int x = 0; x < width; x++) {
					pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
				}
			}

			Bitmap bitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			return bitmap;
		}

		protected static Bitmap creatCodeBitmap(String contents, int width,
				int height, Context context) {
			TextView tv = new TextView(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			tv.setLayoutParams(layoutParams);
			tv.setText(contents);
			tv.setHeight(height);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			tv.setWidth(width);
			tv.setDrawingCacheEnabled(true);
			tv.setTextColor(Color.BLACK);
			tv.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

			tv.buildDrawingCache();
			Bitmap bitmapCode = tv.getDrawingCache();
			return bitmapCode;
		}
	 
	 
}

