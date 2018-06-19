package com.rs.mobile.wportal.view.dp;

import com.rs.mobile.common.L;
import com.rs.mobile.wportal.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DpCatogoryButtonView extends LinearLayout{

	private Context context;
	
	private TextView titleTextView;
	
	private LinearLayout ungerLine;
	
	public boolean isChoosed() {
		return choosed;
	}

	public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}

	private int selectColor, unSelectColor;
	
	private String uniqueId;
	
	private boolean choosed=false;
	
	public DpCatogoryButtonView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public DpCatogoryButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public DpCatogoryButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
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
			
			selectColor = Color.parseColor("#ED3E42");
			
			unSelectColor = Color.parseColor("#333333");
			
			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View v = inflator.inflate(R.layout.dp_list_item_floor_menu, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			titleTextView = (TextView)v.findViewById(R.id.title_text_view);
			
			ungerLine = (LinearLayout)v.findViewById(R.id.under_line);
			
			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setTitle(String title) {
		
		try {
		
			this.titleTextView.setText(title);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
	}

	public boolean selectButton() {
		
		try {
			
			this.titleTextView.setTextColor(selectColor);
			
			this.ungerLine.setVisibility(View.VISIBLE);
			
			return true;
			
		} catch(Exception e) {
			
			L.e(e);
			
		}
		return false;
		
	}
	
	public void unSelectButton() {
		
		try {
			
			this.titleTextView.setTextColor(unSelectColor);
			
			this.ungerLine.setVisibility(View.INVISIBLE);
			
		} catch(Exception e) {
			
			L.e(e);
			
		}
		
	}

}
