package com.rs.mobile.wportal.biz;

import android.os.Parcel;
import android.os.Parcelable;

public class Coupon implements Parcelable{
	public Coupon(String id, String coupon_no, String coupon_name, String coupon_type, String coupon_range,
			String coupon_range_remark, String over_amount, String dc_amount, String upper_limit_amount,
			String over_amount_remark, String start_date, String end_date, String is_valid, String receive_date,
			String platform, boolean canchecked, boolean cpchecked) {
		super();
		this.id = id;
		this.coupon_no = coupon_no;
		this.coupon_name = coupon_name;
		this.coupon_type = coupon_type;
		this.coupon_range = coupon_range;
		this.coupon_range_remark = coupon_range_remark;
		this.over_amount = over_amount;
		this.dc_amount = dc_amount;
		this.upper_limit_amount = upper_limit_amount;
		this.over_amount_remark = over_amount_remark;
		this.start_date = start_date;
		this.end_date = end_date;
		this.is_valid = is_valid;
		this.receive_date = receive_date;
		this.platform = platform;
		this.canchecked = canchecked;
		this.cpchecked = cpchecked;
	}

	private String id;
	private String coupon_no;
	private String  coupon_name;
	private String coupon_type;
	private String coupon_range;
	private String coupon_range_remark;
	private String over_amount;
	private String dc_amount;
	private String upper_limit_amount;
	private String over_amount_remark;
	private String start_date;
	private String end_date;
	private String is_valid;
	private String receive_date;
	private String platform;
    private boolean canchecked;
    private boolean cpchecked;
	   public String getId() {
		return id;
	}

	
	public boolean isCanchecked() {
		return canchecked;
	}


	public void setCanchecked(boolean canchecked) {
		this.canchecked = canchecked;
	}


	public boolean isCpchecked() {
		return cpchecked;
	}


	public void setCpchecked(boolean cpchecked) {
		this.cpchecked = cpchecked;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getCoupon_no() {
		return coupon_no;
	}

	public void setCoupon_no(String coupon_no) {
		this.coupon_no = coupon_no;
	}

	public String getCoupon_name() {
		return coupon_name;
	}

	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}

	public String getCoupon_type() {
		return coupon_type;
	}

	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}

	public String getCoupon_range() {
		return coupon_range;
	}

	public void setCoupon_range(String coupon_range) {
		this.coupon_range = coupon_range;
	}

	public String getCoupon_range_remark() {
		return coupon_range_remark;
	}

	public void setCoupon_range_remark(String coupon_range_remark) {
		this.coupon_range_remark = coupon_range_remark;
	}

	public String getOver_amount() {
		return over_amount;
	}

	public void setOver_amount(String over_amount) {
		this.over_amount = over_amount;
	}

	public String getDc_amount() {
		return dc_amount;
	}

	public void setDc_amount(String dc_amount) {
		this.dc_amount = dc_amount;
	}

	public String getUpper_limit_amount() {
		return upper_limit_amount;
	}

	public void setUpper_limit_amount(String upper_limit_amount) {
		this.upper_limit_amount = upper_limit_amount;
	}

	public String getOver_amount_remark() {
		return over_amount_remark;
	}

	public void setOver_amount_remark(String over_amount_remark) {
		this.over_amount_remark = over_amount_remark;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}

	public String getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public static Parcelable.Creator<Coupon> getCreator() {
		return CREATOR;
	}

	public Coupon() {
		// TODO Auto-generated constructor stub
	}
    
	 @SuppressWarnings("unchecked")
		public static final Parcelable.Creator<Coupon> CREATOR = new Creator(){ 
	    	   
	        @Override 
	        public Coupon createFromParcel(Parcel source) { 
	            // TODO Auto-generated method stub 
	            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错 
	            Coupon p = new Coupon(); 
	            p.setId(source.readString()); 
	            p.setCoupon_no(source.readString());
	            p.setCoupon_name(source.readString());
	            p.setCoupon_type(source.readString());
	            p.setCoupon_range(source.readString());
	            p.setCoupon_range_remark(source.readString());
	            p.setOver_amount(source.readString());
	            p.setDc_amount(source.readString());
	            p.setUpper_limit_amount(source.readString());
	            p.setOver_amount_remark(source.readString());
	            p.setStart_date(source.readString());
	            p.setEnd_date(source.readString());
	            p.setIs_valid(source.readString());
	            p.setReceive_date(source.readString());
	            p.setPlatform(source.readString());
	            p.setCanchecked(source.readByte() != 0);
	            p.setCpchecked(source.readByte() != 0);
	            return p; 
	        } 
	
	 
	  @Override 
      public Coupon[] newArray(int size) { 
          // TODO Auto-generated method stub 
          return new Coupon[size]; 
      } 
  };

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(coupon_no);
		dest.writeString(coupon_name);
		dest.writeString(coupon_type);
		dest.writeString(coupon_range);
		dest.writeString(coupon_range_remark);
		dest.writeString(over_amount);
		dest.writeString(dc_amount);
		dest.writeString(upper_limit_amount);
		dest.writeString(over_amount_remark);
		dest.writeString(start_date);
		dest.writeString(end_date);
		dest.writeString(is_valid);
		dest.writeString(receive_date);
		dest.writeString(platform);
		dest.writeByte((byte)(canchecked? 1 : 0));
		dest.writeByte((byte)(cpchecked? 1 : 0));

	} 




	}