package com.rs.mobile.wportal.view.rt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ReEditGridView extends GridView {
    public ReEditGridView(Context context) {
        super(context);
    }

    public ReEditGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReEditGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
