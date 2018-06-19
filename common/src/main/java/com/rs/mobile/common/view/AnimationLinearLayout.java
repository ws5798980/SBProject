/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.rs.mobile.common.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnimationLinearLayout extends LinearLayout
{

    public AnimationLinearLayout(Context context)
    {
        super(context);
        f = 10F;
        down = false;
        isClickEvent = true;
        roundRect = new RectF();
        rect_adius = 0.0F;
        maskPaint = new Paint();
        zonePaint = new Paint();
        isAnimationEffect = true;
        init(context);
    }

    public AnimationLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        f = 10F;
        down = false;
        isClickEvent = true;
        roundRect = new RectF();
        rect_adius = 0.0F;
        maskPaint = new Paint();
        zonePaint = new Paint();
        isAnimationEffect = true;
        init(context);
    }

    @SuppressLint("NewApi")
	public AnimationLinearLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        f = 10F;
        down = false;
        isClickEvent = true;
        roundRect = new RectF();
        rect_adius = 0.0F;
        maskPaint = new Paint();
        zonePaint = new Paint();
        isAnimationEffect = true;
        init(context);
    }

    public void setOnClickListener(android.view.View.OnClickListener l)
    {
        this.l = l;
    }

    public void setTextSize(float size)
    {
        tv.setTextSize(size);
    }

    public void setTextSize(int unit, float size)
    {
        tv.setTextSize(unit, size);
    }

    public void setTextColor(int color)
    {
        tv.setTextColor(color);
    }

    public void setTextColor(ColorStateList colors)
    {
        tv.setTextColor(colors);
    }

    public final void setText(int resid)
    {
        tv.setText(resid);
        addView(tv, new android.widget.LinearLayout.LayoutParams(-2, -2));
    }

    public final void setText(int resid, android.widget.TextView.BufferType type)
    {
        tv.setText(resid, type);
        addView(tv, new android.widget.LinearLayout.LayoutParams(-2, -2));
    }

    public final void setText(CharSequence text)
    {
        tv.setText(text);
        addView(tv, new android.widget.LinearLayout.LayoutParams(-2, -2));
    }

    public void setText(CharSequence text, android.widget.TextView.BufferType type)
    {
        tv.setText(text, type);
        addView(tv, new android.widget.LinearLayout.LayoutParams(-2, -2));
    }

    private void init(Context context)
    {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(-1);
        float density = context.getResources().getDisplayMetrics().density;
        rect_adius *= density;
            tv = new TextView(context);
            return;
    }

    public void setRectAdius(float adius)
    {
        rect_adius = adius;
        invalidate();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0.0F, 0.0F, w, h);
    }

    public void draw(Canvas canvas)
    {
        canvas.saveLayer(roundRect, zonePaint, 31);
        canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
        canvas.saveLayer(roundRect, maskPaint, 31);
        super.draw(canvas);
        canvas.restore();
    }

    public boolean isClickEvent()
    {
        return isClickEvent;
    }

    public void setClickEvent(boolean isClickEvent)
    {
        this.isClickEvent = isClickEvent;
    }

    public boolean isAnimationEffect()
    {
        return isAnimationEffect;
    }

    public void setAnimationEffect(boolean isAnimationEffect)
    {
        this.isAnimationEffect = isAnimationEffect;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        if(isClickEvent)
        {
            ScaleAnimation acaleAnimation = null;
            Rect viewRect = new Rect();
            getLocalVisibleRect(viewRect);
            boolean b = event.getX() < (float)viewRect.right && event.getX() > (float)viewRect.left && event.getY() < (float)viewRect.bottom && event.getY() > (float)viewRect.top;
            switch(event.getAction())
            {
            case 0: // '\0'
                if(down)
                    break;
                acaleAnimation = new ScaleAnimation(1.0F, getF(), 1.0F, getF(), 1, 0.5F, 1, 0.5F);
                acaleAnimation.setDuration(100L);
                acaleAnimation.setFillAfter(true);
                if(isAnimationEffect)
                    startAnimation(acaleAnimation);
                down = true;
                setPressed(true);
                break;

            case 2: // '\002'
                if(!b)
                    clearAnimation(acaleAnimation, b, false);
                break;

            case 1: // '\001'
                clearAnimation(acaleAnimation, b, true);
                break;

            case 3: // '\003'
            default:
                clearAnimation(acaleAnimation, b, false);
                break;
            }
            return true;
        } else
        {
            return super.onTouchEvent(event);
        }
    }

    public float getF()
    {
            return 1.0F - (float)Math.round(TypedValue.applyDimension(1, f, getResources().getDisplayMetrics())) / (float)getWidth();
    }

    private void clearAnimation(ScaleAnimation acaleAnimation, final boolean b, boolean up)
    {
        setPressed(false);
        if(down)
        {
            down = false;
            acaleAnimation = new ScaleAnimation(getF(), 1.0F, getF(), 1.0F, 1, 0.5F, 1, 0.5F);
            acaleAnimation.setDuration(100L);
            if(up)
                acaleAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

                    public void onAnimationStart(Animation animation)
                    {
                    }

                    public void onAnimationRepeat(Animation animation)
                    {
                    }

                    public void onAnimationEnd(Animation paramAnimation)
                    {
                        if(l != null && b)
                            l.onClick(AnimationLinearLayout.this);
                    }
                }
);
            if(isAnimationEffect)
                startAnimation(acaleAnimation);
            else
            if(up && l != null && b)
                l.onClick(this);
        }
    }

    private float f;
    private TextView tv;
    private boolean down;
    private boolean isClickEvent;
    private final RectF roundRect;
    private float rect_adius;
    private final Paint maskPaint;
    private final Paint zonePaint;
    private boolean isAnimationEffect;
    private android.view.View.OnClickListener l;

}
