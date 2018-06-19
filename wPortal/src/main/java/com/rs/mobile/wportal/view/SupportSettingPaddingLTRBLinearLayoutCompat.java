package com.rs.mobile.wportal.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zhaoyun
 * @desc 功能描述
 * @date 2015/12/21 19:57
 */
public class SupportSettingPaddingLTRBLinearLayoutCompat extends android.support.v7.widget.LinearLayoutCompat{

    private int mDividerPaddingLeft;
    private int mDividerPaddingTop;
    private int mDividerPaddingRight;
    private int mDividerPaddingBottom;

    public SupportSettingPaddingLTRBLinearLayoutCompat(Context context) {
        this(context, null);
    }

    public SupportSettingPaddingLTRBLinearLayoutCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SupportSettingPaddingLTRBLinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, com.rs.mobile.wportal.R.styleable.SupportSettingPaddingLTRBLinearLayoutCompat, 0, 0);
        mDividerPaddingLeft = a.getDimensionPixelSize(com.rs.mobile.wportal.R.styleable.SupportSettingPaddingLTRBLinearLayoutCompat_dividerPaddingLeft, getDividerPadding());
        mDividerPaddingTop = a.getDimensionPixelSize(com.rs.mobile.wportal.R.styleable.SupportSettingPaddingLTRBLinearLayoutCompat_dividerPaddingTop, getDividerPadding());
        mDividerPaddingRight = a.getDimensionPixelSize(com.rs.mobile.wportal.R.styleable.SupportSettingPaddingLTRBLinearLayoutCompat_dividerPaddingRight, getDividerPadding());
        mDividerPaddingBottom = a.getDimensionPixelSize(com.rs.mobile.wportal.R.styleable.SupportSettingPaddingLTRBLinearLayoutCompat_dividerPaddingBottom, getDividerPadding());
        a.recycle();
    }

    @Override
    public void setDividerPadding(int padding) {
        super.setDividerPadding(padding);
        mDividerPaddingLeft = padding;
        mDividerPaddingTop = padding;
        mDividerPaddingRight = padding;
        mDividerPaddingBottom = padding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDividerDrawable() == null) {
            return;
        }

        if (getOrientation() == VERTICAL) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    /**
     * @desc 垂直排列LinearLayout，画横向的divider
     * @param
     * @author zhaoyun
     */
    protected void drawDividersVertical(Canvas canvas) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                //根据show_divider的flag来判断接下来是否要画横向的divider
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int top = child.getTop() - lp.topMargin - getDividerHeight();
                    drawHorizontalDivider(canvas, top);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getChildAt(count - 1);
            int bottom = 0;
            if (child == null) {
                bottom = getHeight() - getPaddingBottom() - getDividerHeight();
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                bottom = child.getBottom() + lp.bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    /**
     * @desc 水平排列LinearLayout，画纵向的divider
     * @param
     * @author zhaoyun
     */
    protected void drawDividersHorizontal(Canvas canvas) {
        final int count = getChildCount();
        final boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int position;
                    if (isLayoutRtl) {
                        position = child.getRight() + lp.rightMargin;
                    } else {
                        position = child.getLeft() - lp.leftMargin - getDividerWidth();
                    }
                    drawVerticalDivider(canvas, position);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getChildAt(count - 1);
            int position;
            if (child == null) {
                if (isLayoutRtl) {
                    position = getPaddingLeft();
                } else {
                    position = getWidth() - getPaddingRight() - getDividerWidth();
                }
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position = child.getLeft() - lp.leftMargin - getDividerWidth();
                } else {
                    position = child.getRight() + lp.rightMargin;
                }
            }
            drawVerticalDivider(canvas, position);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        getDividerDrawable().setBounds(getPaddingLeft() + getDividerPaddingLeft(), top,
                getWidth() - getPaddingRight() - getDividerPaddingRight(), top + getDividerHeight());
        getDividerDrawable().draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        getDividerDrawable().setBounds(left, getPaddingTop() + getDividerPaddingTop(),
                left + getDividerWidth(), getHeight() - getPaddingBottom() - getDividerPaddingBottom());
        getDividerDrawable().draw(canvas);
    }

    public int getDividerPaddingLeft() {
        return mDividerPaddingLeft;
    }

    public void setDividerPaddingLeft(int dividerPaddingLeft) {
        this.mDividerPaddingLeft = dividerPaddingLeft;
    }

    public int getDividerPaddingTop() {
        return mDividerPaddingTop;
    }

    public void setDividerPaddingTop(int dividerPaddingTop) {
        this.mDividerPaddingTop = dividerPaddingTop;
    }

    public int getDividerPaddingRight() {
        return mDividerPaddingRight;
    }

    public void setDividerPaddingRight(int dividerPaddingRight) {
        this.mDividerPaddingRight = dividerPaddingRight;
    }

    public int getDividerPaddingBottom() {
        return mDividerPaddingBottom;
    }

    public void setDividerPaddingBottom(int dividerPaddingBottom) {
        this.mDividerPaddingBottom = dividerPaddingBottom;
    }

    public int getDividerHeight(){
        if (getDividerDrawable() != null) {
            return getDividerDrawable().getIntrinsicHeight();
        } else {
            return 0;
        }
    }

}
