package com.rs.mobile.wportal.biz.rt;

public class SellerType {
	
	private String divCode;
	private String seq;
	private String mainCode;
	private String subCode;
	private String displayName;
	private String imgUrl;
	private String ver;
	public String getDivCode() {
		return divCode;
	}
	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getMainCode() {
		return mainCode;
	}
	public void setMainCode(String mainCode) {
		this.mainCode = mainCode;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	
	public SellerType(String divCode, String seq, String mainCode, String subCode, String displayName, String imgUrl,
			String ver) {
		super();
		this.divCode = divCode;
		this.seq = seq;
		this.mainCode = mainCode;
		this.subCode = subCode;
		this.displayName = displayName;
		this.imgUrl = imgUrl;
		this.ver = ver;
	}
	
	
}
