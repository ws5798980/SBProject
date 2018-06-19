package com.rs.mobile.wportal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FormatTextView extends TextView{
    public String checkid;
    public boolean ischoosed;
	public String parent_item_code;
	public String sub_item_code;
	public String sub_item_name;
	public float sub_item_price;
	public int sub_item_stock_q;
	public String sub_item_stock_unit;
	public String sub_item_option;
	public String sub_item_spec;

	public String getParent_item_code() {
		return parent_item_code;
	}



	public void setParent_item_code(String parent_item_code) {
		this.parent_item_code = parent_item_code;
	}



	public String getSub_item_code() {
		return sub_item_code;
	}



	public void setSub_item_code(String sub_item_code) {
		this.sub_item_code = sub_item_code;
	}



	public String getSub_item_name() {
		return sub_item_name;
	}



	public void setSub_item_name(String sub_item_name) {
		this.sub_item_name = sub_item_name;
	}



	public float getSub_item_price() {
		return sub_item_price;
	}



	public void setSub_item_price(float sub_item_price) {
		this.sub_item_price = sub_item_price;
	}



	public int getSub_item_stock_q() {
		return sub_item_stock_q;
	}



	public void setSub_item_stock_q(int sub_item_stock_q) {
		this.sub_item_stock_q = sub_item_stock_q;
	}



	public String getSub_item_stock_unit() {
		return sub_item_stock_unit;
	}



	public void setSub_item_stock_unit(String sub_item_stock_unit) {
		this.sub_item_stock_unit = sub_item_stock_unit;
	}



	public String getSub_item_option() {
		return sub_item_option;
	}



	public void setSub_item_option(String sub_item_option) {
		this.sub_item_option = sub_item_option;
	}



	public String getSub_item_spec() {
		return sub_item_spec;
	}



	public void setSub_item_spec(String sub_item_spec) {
		this.sub_item_spec = sub_item_spec;
	}



	public FormatTextView(Context context, String checkid, boolean ischoosed, String parent_item_code,
			String sub_item_code, String sub_item_name, float sub_item_price, int sub_item_stock_q,
			String sub_item_stock_unit, String sub_item_option, String sub_item_spec) {
		super(context);
		this.checkid = checkid;
		this.ischoosed = ischoosed;
		this.parent_item_code = parent_item_code;
		this.sub_item_code = sub_item_code;
		this.sub_item_name = sub_item_name;
		this.sub_item_price = sub_item_price;
		this.sub_item_stock_q = sub_item_stock_q;
		this.sub_item_stock_unit = sub_item_stock_unit;
		this.sub_item_option = sub_item_option;
		this.sub_item_spec = sub_item_spec;
	}

	    
	
	public boolean isIschoosed() {
		return ischoosed;
	}

	public void setIschoosed(boolean ischoosed) {
		this.ischoosed = ischoosed;
	}

	public String getCheckid() {
		return checkid;
	}

	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
    
	public FormatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public FormatTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FormatTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
    
    
    
}
