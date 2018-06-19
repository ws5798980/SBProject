package com.rs.mobile.wportal.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rs.mobile.common.image.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CommonViewHolder {
	
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private ViewGroup mParent;
	
	private CommonViewHolder(Context context , ViewGroup parent , int layoutId , int position) {
		this.mPosition = position;
		this.mParent = parent;
		this.mViews = new SparseArray<View>();
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}

	public ViewGroup getParent(){
		return mParent;
	}
	
	/**
	 * 构造方法不对外提供，只对外提供实例方法
	 * 每次调用已存在实例时，更新position
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static CommonViewHolder get(Context context , View convertView , ViewGroup parent , int layoutId , int position){
		if(convertView == null){
			//setTag进去
			return new CommonViewHolder(context, parent, layoutId, position);
		}else{
			CommonViewHolder viewHolder = (CommonViewHolder) convertView.getTag();
			viewHolder.mPosition = position;
			return viewHolder;
		}
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	
	/**
	 * 通过viewId获取控件
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 设置某个ListView的adapter
	 * @param listViewId
	 * @param adapter
	 * @return
	 */
	public <T extends BaseAdapter>CommonViewHolder setListViewAdapter(int listViewId , T adapter){
		ListView lv = getView(listViewId);
		if(lv.getAdapter() != null && lv.getAdapter().equals(adapter)){
			((T)lv.getAdapter()).notifyDataSetChanged();
		}else{
			lv.setAdapter(adapter);
		}
		return this;
	}

	/**
	 * 设置某个TextView的text
	 * @param textViewId
	 * @param text
	 * @return
	 */
	public CommonViewHolder setText(int textViewId , CharSequence text){
		TextView tv = getView(textViewId);
		tv.setText(text);
		return this;
	}
	
	/**
	 * 设置某个ImageView的resId
	 * @param imageViewId
	 * @param resId
	 * @return
	 */
	public CommonViewHolder setImageResource(int imageViewId , int resId){
		ImageView iv = getView(imageViewId);
		iv.setImageResource(resId);
		return this;
	}
	
	/**
	 * 设置某个ImageView的bitmap
	 * @param imageViewId
	 * @param bitmap
	 * @return
	 */
	public CommonViewHolder setImageBitmap(int imageViewId , Bitmap bitmap){
		ImageView iv = getView(imageViewId);
		iv.setImageBitmap(bitmap);
		return this;
	}
	
	/**
	 * 设置某个ImageView的drawable
	 * @param imageViewId
	 * @param drawable
	 * @return
	 */
	public CommonViewHolder setImageDrawable(int imageViewId , Drawable drawable){
		ImageView iv = getView(imageViewId);
		iv.setImageDrawable(drawable);
		return this;
	}
	
	/**
	 * 通过网络URI加载图片
	 * @param imageViewId
	 * @param url
	 * @return
	 */
	public CommonViewHolder setImageURI(int imageViewId , String url){
//		ImageView iv = getView(imageViewId);
//		ImageLoader.getInstance().displayImage(url , iv);
		SimpleDraweeView iv = getView(imageViewId);
		ImageUtil.drawImageFromUri1(url, iv);
		return this;
	}
	
//	/**
//	 * 通过网络URI加载图片，并通过options设置显示方式
//	 * @param imageViewId
//	 * @param url
//	 * @param options
//	 * @return
//	 */
//	public CommonViewHolder setImageURIDisplayWithOptions(int imageViewId , String url , DisplayImageOptions options){
//		ImageView iv = getView(imageViewId);
//		ImageLoader.getInstance().displayImage(url , iv , options);
//		return this;
//	}
	
	/**
	 * 通过网络URI加载图片，并设置默认加载图
	 * @param imageViewId
	 * @param url
	 * @param defaultImgId
	 * @return
	 */
	public CommonViewHolder setImageURIDisplayWithDefaultIcon(int imageViewId , String url , int defaultImgId){
//		ImageView iv = getView(imageViewId);
//		ImageUtil.drawIamge(imageView, bitmap)
		SimpleDraweeView iv = getView(imageViewId);
		ImageUtil.drawImageFromUri1(url, iv);
		return this;
	}
	
	/**
	 * 
	 * @param imageViewId
	 * @param url
	 * @param width
	 * @param height
	 * @return
	 */
//	public CommonViewHolder setImageURIBySize(int imageViewId , String url , int width , int height){
//		StringBuffer urlBuffer = new StringBuffer(NetworkConfig.BASE_FILE_PATH_IMG);
//		if(!TextUtils.isEmpty(url)){
//			try {
//				urlBuffer.append("?src="+URLEncoder.encode(url,"UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//		if(width > 0){
//			urlBuffer.append("&width=" + width);
//		}
//		if(height > 0){
//			urlBuffer.append("&height=" + height);
//		}
//		ImageView iv = getView(imageViewId);
//		ImageLoader.getInstance().displayImage(urlBuffer.toString(), iv);
//		return this;
//	}
	
	/**
	 * 
	 * @return
	 */
	public CommonViewHolder setCheckBoxChecked(int checkBoxId , boolean isChecked){
		CheckBox ck = getView(checkBoxId);
		ck.setChecked(isChecked);
		return this;
	}
	
	/**
	 * 显示某个View
	 * @param viewId
	 * @return
	 */
	public CommonViewHolder showView(int viewId){
		getView(viewId).setVisibility(View.VISIBLE);
		return this;
	}
	
	/**
	 * 隐藏某个View
	 * @param viewId
	 * @return
	 */
	public CommonViewHolder hideView(int viewId){
		getView(viewId).setVisibility(View.GONE);
		return this;
	}
	
	public int getPosition() {
		return mPosition;
	}
	
}
