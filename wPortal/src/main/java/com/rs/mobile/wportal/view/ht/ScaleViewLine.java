package com.rs.mobile.wportal.view.ht;

import com.rs.mobile.common.util.StringUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ScaleViewLine extends View {
    private int mWidth;
   
    private Paint mPaint;

	private RectF mRect1;
	
    
    public ScaleViewLine(Context context) {
        super(context);
        
        init(context);
    }

    public ScaleViewLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleViewLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        setWillNotDraw(false);
        int height = StringUtil.dip2px(context, 5);
        mWidth = context.getResources().getDisplayMetrics().widthPixels-StringUtil.dip2px(context, 40);
        mRect1 = new RectF(0,0,0, height);
       

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#F0F2F5"));
        mPaint.setColor(Color.parseColor("#fccb0b"));
        canvas.drawRoundRect(mRect1, 0, 0, mPaint);
        
    }

    /**
     * 给数据赋值
     * @param scales
     */
    public void setScales(double scale0){
        
        
        mRect1.right = (int) (mWidth*scale0/5.6);
       
        invalidate();
    }
}