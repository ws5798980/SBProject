package com.rs.mobile.wportal.adapter.kr;
//package com.rs.mobile.wportal.kr;
//
//import android.content.Context;
//import android.widget.FrameLayout;
//import android.widget.RelativeLayout;
//import io.vov.vitamio.widget.MediaController;
//
//public class KRMediaController extends MediaController
//{
//    private FrameLayout anchorView;
//
//    public KRMediaController(Context context, FrameLayout anchorView)
//    {
//        super(context);
//        this.anchorView = anchorView;       
//    }
//
//    @Override
//    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld)
//    {
//        super.onSizeChanged(xNew, yNew, xOld, yOld);
//
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) anchorView.getLayoutParams();
//        lp.setMargins(0, 0, 0, yNew);
//
//        anchorView.setLayoutParams(lp);
//        anchorView.requestLayout();
//    }       
//}
