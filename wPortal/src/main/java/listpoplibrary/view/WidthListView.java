package listpoplibrary.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * Created by radio on 2017/10/31.
 */

public class WidthListView extends ListView {
private int screenWidth;
    public WidthListView(Context context) {
        this(context,null);
    }

    public WidthListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WidthListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = ((Activity)context).getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getMaxWidthOfChildren() + getPaddingLeft() + getPaddingRight();//计算listview的宽度
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), heightMeasureSpec);//设置listview的宽高

    }

    /**
     * 计算item的最大宽度
     *
     * @return
     */
    private int getMaxWidthOfChildren() {
        int maxWidth = 0;
        View view = null;
        int count = getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            view = getAdapter().getView(i, view, this);
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            if (view.getMeasuredWidth() > maxWidth){
                maxWidth = view.getMeasuredWidth();}
        }
        if (maxWidth>=screenWidth){
            return screenWidth;
        }else{
            return maxWidth;

        }
    }
}
