package com.rs.mobile.common.view;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

import com.rs.mobile.common.AppConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.rs.mobile.common.util.UiUtil;

public class ShareView {
		private static PopupWindow  popupWindow  = null;
		private static Activity activity ;
//	   private ProgressDialog dialog;
	   public ArrayList<String> styles = new ArrayList<String>();
	
//	public  ShareView(Activity context ){
//		this.context = context ;
//		 dialog = new ProgressDialog(context);
//	}
	
	 /**
     *分享view
     * 
     */
    public  static void showPop(final Activity context,final String title ,final String content ,final String url 
    		,final String imgUrl,int id_below,final String type, final String itemCode)
    {
    	
    	activity = context ;
        final UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setDescription(content);
        web.setThumb(new UMImage(context,imgUrl.equals("")?"http://portal."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8488/img/11.png":imgUrl));
        
    	
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
        ViewGroup menuView = (ViewGroup) context.getLayoutInflater().inflate(com.rs.mobile.common.R.layout.share_view,
                null);
        popupWindow = new PopupWindow(menuView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT, true);
       
       
        //龙聊分享
        menuView.findViewById(com.rs.mobile.common.R.id.share_longliao).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                popupWindow.dismiss();
              
                UiUtil.share(context, type, itemCode,
                		title, content,imgUrl);
                 
                
            }
        });
        
        
        //微信分享
        menuView.findViewById(com.rs.mobile.common.R.id.share_weixin).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                popupWindow.dismiss();
                
                
                new ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .setCallback(shareListener)//回调监听器
                .withMedia(web)
                .share();
                
            }
        });
        
        //微信朋友圈分享
        menuView.findViewById(com.rs.mobile.common.R.id.share_pyq).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                popupWindow.dismiss();
                
                
                new ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                .setCallback(shareListener)//回调监听器
                .withMedia(web)
                .share();
            }
        });  
        
        //微信收藏分享
        menuView.findViewById(com.rs.mobile.common.R.id.share_fav).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                popupWindow.dismiss();
                
                new ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN_FAVORITE)//传入平台
                .setCallback(shareListener)//回调监听器
                .withMedia(web)
                .share();
            }
        });  
        
        
        //新浪分享
        menuView.findViewById(com.rs.mobile.common.R.id.share_sina).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                popupWindow.dismiss();
                
                new ShareAction(context)
                .setPlatform(SHARE_MEDIA.SINA)//传入平台
                .setCallback(shareListener)//回调监听器
                .withMedia(web)
                .share();
                 
                
            }
        });  
        
        
       
       //QQ分享
       menuView.findViewById(com.rs.mobile.common.R.id.share_qq).setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View arg0)
           {
               popupWindow.dismiss();
             
               new ShareAction(context)
               .setPlatform(SHARE_MEDIA.QQ)//传入平台
               .setCallback(shareListener)//回调监听器
               .withMedia(web)
               .share();
                
               
           }
       });
       
     //QQ空间分享
       menuView.findViewById(com.rs.mobile.common.R.id.share_qq_space).setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View arg0)
           {
               popupWindow.dismiss();
             
               new ShareAction(context)
               .setPlatform(SHARE_MEDIA.QZONE)//传入平台
               .setCallback(shareListener)//回调监听器
               .withMedia(web)
               .share();
                
               
           }
       });

        menuView.findViewById(com.rs.mobile.common.R.id.share_calse).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                popupWindow.dismiss();
            }
        });


        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight =context. getWindow().getDecorView().getHeight();
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0,winHeight-rect.bottom);
//        popupWindow.showAsDropDown(context.findViewById(id_below));
        popupWindow.update();
        popupWindow.setOnDismissListener(new OnDismissListener()
        {

            @Override
            public void onDismiss()
            {
            }
        });
    }
    
    
    private static UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
        	if(platform == SHARE_MEDIA.WEIXIN){
        		Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
        	}
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };

}
