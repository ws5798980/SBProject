package com.rs.mobile.common.view;

import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rs.mobile.common.L;
import com.rs.mobile.common.R;

import android.content.Context;
import android.util.AttributeSet;

public class WImageView extends SimpleDraweeView{

	private Context context;
	
	public WImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public WImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public WImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}
	
	public void setCircle(boolean showBorder) {

		try {
			
			RoundingParams roundingParams = getHierarchy().getRoundingParams();
			
			if (roundingParams == null)
	            roundingParams = new RoundingParams();
			
			if (showBorder == true)
				roundingParams.setBorder(R.color.red, (float) 1.0);
			
			roundingParams.setRoundAsCircle(true);
			
			getHierarchy().setRoundingParams(roundingParams);
			
			getHierarchy().setActualImageScaleType(com.facebook.drawee.drawable.ScalingUtils.ScaleType.FIT_CENTER);
			
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void setCircle(boolean showBorder, int borderColor, float borderWidth, com.facebook.drawee.drawable.ScalingUtils.ScaleType scaleType) {

		try {
			
			RoundingParams roundingParams = getHierarchy().getRoundingParams();
			
			if (roundingParams == null)
	            roundingParams = new RoundingParams();
			
			if (showBorder == true)
				roundingParams.setBorder(borderColor, borderWidth);
			
			roundingParams.setRoundAsCircle(true);
			
			getHierarchy().setRoundingParams(roundingParams);
			
			getHierarchy().setActualImageScaleType(scaleType);
			
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void setRounding(boolean showBorder) {
		
		try {

			RoundingParams roundingParams = RoundingParams.fromCornersRadius(context.getResources().getDimension(R.dimen.margin));
			
			if (roundingParams == null)
				roundingParams = new RoundingParams();
			
			if (showBorder == true)
				roundingParams.setBorder(R.color.red, (float) 1.0);
			
			getHierarchy().setRoundingParams(roundingParams);
			
			getHierarchy().setActualImageScaleType(com.facebook.drawee.drawable.ScalingUtils.ScaleType.FIT_CENTER);
			
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void setRounding(boolean showBorder, int borderColor, float borderWidth, com.facebook.drawee.drawable.ScalingUtils.ScaleType scaleType, int round) {
		
		try {

			RoundingParams roundingParams = RoundingParams.fromCornersRadius(round);
			
			if (roundingParams == null)
				roundingParams = new RoundingParams();
			
			if (showBorder == true)
				roundingParams.setBorder(borderColor, borderWidth);
			
			getHierarchy().setRoundingParams(roundingParams);
			
			getHierarchy().setActualImageScaleType(scaleType);
			
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	/**
	 * 초기화
	 * @param context
	 */
	public void initView(final Context context) {
		
		try {
            
			this.context = context;
			
			GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
				.setFadeDuration(500)
				
//			     .setRoundingParams(RoundingParams.fromCornersRadius(5))
			     .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
				.setFailureImage(context.getResources().getDrawable(R.drawable.img_failed_to_load), ScalingUtils.ScaleType.CENTER)
				.setRetryImage(context.getResources().getDrawable(R.drawable.icon_reload), ScalingUtils.ScaleType.CENTER)
//	            .setProgressBarImage(new AutoRotateDrawable(context.getResources().getDrawable(R.drawable.icon_reload), 500), ScalingUtils.ScaleType.CENTER)
//				.setProgressBarImage(new AutoRotateDrawable(context.getResources().getDrawable(R.drawable.img_loading), 700), ScalingUtils.ScaleType.CENTER)
//	            .setProgressBarImage(ContextCompat.getDrawable(context, R.drawable.img_progress))
	            .build();
           
			setHierarchy(hierarchy);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void setWithoutDrawableView(final Context context) {
		
		try {

			GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
				.setFadeDuration(500)
				.setProgressBarImage(new AutoRotateDrawable(context.getResources().getDrawable(R.drawable.img_loading), 700), ScalingUtils.ScaleType.CENTER)
	            .build();

			setHierarchy(hierarchy);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	

	public void setReplyDrawableView(final Context context) {
		
		try {

			GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
				.setFadeDuration(500)
				.setFailureImage(context.getResources().getDrawable(R.drawable.img_headphoto_001), ScalingUtils.ScaleType.CENTER)
				.setRetryImage(context.getResources().getDrawable(R.drawable.img_headphoto_001), ScalingUtils.ScaleType.CENTER)
				.setProgressBarImage(new AutoRotateDrawable(context.getResources().getDrawable(R.drawable.img_loading), 700), ScalingUtils.ScaleType.CENTER)
	            .build();

			setHierarchy(hierarchy);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	
}
