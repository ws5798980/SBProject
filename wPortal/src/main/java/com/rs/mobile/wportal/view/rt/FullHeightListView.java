package com.rs.mobile.wportal.view.rt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 满屏listview
 *
 * @author lijing
 */
public class FullHeightListView extends ListView {

//    private int mDividerPadding;
//    private int mDividerPaddingLeft;
//    private int mDividerPaddingTop;
//    private int mDividerPaddingRight;
//    private int mDividerPaddingBottom;

    public FullHeightListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        TypedArray a = context.obtainStyledAttributes(attrs , R.styleable.XList);
//        mDividerPadding = a.getDimensionPixelSize(R.styleable.XList_xListDividerPadding, 0);
//        mDividerPaddingLeft = a.getDimensionPixelSize(R.styleable.XList_xListDividerPaddingLeft, mDividerPadding);
//        mDividerPaddingTop = a.getDimensionPixelSize(R.styleable.XList_xListDividerPaddingTop, mDividerPadding);
//        mDividerPaddingRight = a.getDimensionPixelSize(R.styleable.XList_xListDividerPaddingRight, mDividerPadding);
//        mDividerPaddingBottom = a.getDimensionPixelSize(R.styleable.XList_xListDividerPaddingBottom, mDividerPadding);
//        a.recycle();
    }

    public FullHeightListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullHeightListView(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    void drawDivider(Canvas canvas, Rect bounds, int childIndex) {
//        // This widget draws the same divider for all children
//        final Drawable divider = getDivider();
//        Rect cloneBounds = new Rect(bounds.left + mDividerPaddingLeft , bounds.top + mDividerPaddingTop , bounds.right - mDividerPaddingRight , bounds.bottom - mDividerPaddingBottom);
//        divider.setBounds(cloneBounds);
//        divider.draw(canvas);
//    }

}
