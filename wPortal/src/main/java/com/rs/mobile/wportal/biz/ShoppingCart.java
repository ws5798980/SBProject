package com.rs.mobile.wportal.biz;

import android.os.Parcel;
import android.os.Parcelable;

public class ShoppingCart implements Parcelable {
	private String id;

	private String name;

	private float price;

	private String imgurl;

	private String stock_unit;

	private int num;// 商品数量

	private boolean isChoosed; // 商品是否在购物车中被选中

	private int sumPrice;

	private String div_code;

	private String refund_status = "0";

	public String sale_custom_code;

	/***
	 * 
	 * @param id
	 * @param name
	 * @param price
	 * @param imgurl
	 * @param sumPrice
	 * @param num
	 * @param isChoosed
	 */

	public ShoppingCart(String id, String name, float price, String imgurl, int num, boolean isChoosed,
			String stock_unit, String div_code, String sale_custom_code) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.imgurl = imgurl;
		this.num = num;
		this.isChoosed = isChoosed;
		this.stock_unit = stock_unit;
		this.div_code = div_code;
		this.sale_custom_code = sale_custom_code;
	}

	public ShoppingCart() {

	}

	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	public String getStock_unit() {
		return stock_unit;
	}

	public void setStock_unit(String stock_unit) {
		this.stock_unit = stock_unit;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getprice() {
		return price;
	}

	public void setprice(float price) {
		this.price = price;
	}

	public String getimgurl() {
		return imgurl;
	}

	public void setimgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public int getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}

	public boolean isChoosed() {
		return isChoosed;
	}

	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}

	public void setSale_custom_code(String sale_custom_code){
		this.sale_custom_code = sale_custom_code;
	}

	public String getSale_custom_code(){
		return sale_custom_code;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", name=" + name + ", price=" + price + ", imgurl=" + imgurl + ", num=" + num
				+ ", isChoosed=" + isChoosed + ", sumPrice=" + sumPrice + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(name);
		dest.writeFloat(price);
		dest.writeString(imgurl);
		dest.writeString(stock_unit);
		dest.writeInt(num);
		dest.writeByte((byte) (isChoosed ? 1 : 0));
		dest.writeInt(sumPrice);
		dest.writeString(div_code);
		dest.writeString(sale_custom_code);
	}

	@SuppressWarnings("unchecked")
	public static final Parcelable.Creator<ShoppingCart> CREATOR = new Creator() {

		@Override
		public ShoppingCart createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			ShoppingCart p = new ShoppingCart();
			p.setId(source.readString());
			p.setName(source.readString());
			p.setprice(source.readFloat());
			p.setimgurl(source.readString());
			p.setStock_unit(source.readString());
			p.setNum(source.readInt());
			p.setChoosed(source.readByte() != 0);
			p.setSumPrice(source.readInt());
			p.setDiv_code(source.readString());
			p.setSale_custom_code(source.readString());
			return p;
		}

		@Override
		public ShoppingCart[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ShoppingCart[size];
		}
	};

	public String getDiv_code() {
		return div_code;
	}

	public void setDiv_code(String div_code) {
		this.div_code = div_code;
	}
}
