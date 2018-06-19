package com.rs.mobile.wportal.view;

import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SMListItemView extends LinearLayout{

	private Context context;
	
	private LinearLayout sm_hzlistview_item;
	
	private WImageView sm_hzlistview_item_img;
	
	private TextView sm_hzlistview_item_textview_name;
	
	private TextView sm_hzlistview_item_textview_price;
	
	private ImageView sm_hzlistview_cart;

	public LinearLayout getSm_hzlistview_item() {
		return sm_hzlistview_item;
	}

	public void setSm_hzlistview_item(LinearLayout sm_hzlistview_item) {
		this.sm_hzlistview_item = sm_hzlistview_item;
	}

	public WImageView getSm_hzlistview_item_img() {
		return sm_hzlistview_item_img;
	}

	public void setSm_hzlistview_item_img(WImageView sm_hzlistview_item_img) {
		this.sm_hzlistview_item_img = sm_hzlistview_item_img;
	}

	public TextView getSm_hzlistview_item_textview_name() {
		return sm_hzlistview_item_textview_name;
	}

	public void setSm_hzlistview_item_textview_name(TextView sm_hzlistview_item_textview_name) {
		this.sm_hzlistview_item_textview_name = sm_hzlistview_item_textview_name;
	}

	public TextView getSm_hzlistview_item_textview_price() {
		return sm_hzlistview_item_textview_price;
	}

	public void setSm_hzlistview_item_textview_price(TextView sm_hzlistview_item_textview_price) {
		this.sm_hzlistview_item_textview_price = sm_hzlistview_item_textview_price;
	}

	public ImageView getSm_hzlistview_cart() {
		return sm_hzlistview_cart;
	}

	public void setSm_hzlistview_cart(ImageView sm_hzlistview_cart) {
		this.sm_hzlistview_cart = sm_hzlistview_cart;
	}

	public SMListItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public SMListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public SMListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}
	
	/**
	 * 초기화
	 * @param context
	 */
	public void initView(final Context context) {
		
		try {
		
			this.context = context;
			
			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View v = inflator.inflate(com.rs.mobile.wportal.R.layout.sm_hzlistview_item, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			sm_hzlistview_item = (LinearLayout)v.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_item);
			
			sm_hzlistview_item_img = (WImageView)v.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_item_img);
			
			sm_hzlistview_item_textview_name = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_item_textview_name);
			
			sm_hzlistview_item_textview_price = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_item_textview_price);
			
			sm_hzlistview_cart = (ImageView)v.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_cart);
			
			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void setName(String title) {
		
		try {
		
			this.sm_hzlistview_item_textview_name.setText(title);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
	}

	public void setPrice(String price) {
		
		try {
		
			this.sm_hzlistview_item_textview_price.setText(price);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

}
