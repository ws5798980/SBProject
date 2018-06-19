package com.rs.mobile.wportal.biz.ht;



public class HotelRate{
        public HotelRate(String userName, String ratedName, float total_score, String ratedContext) {
		super();
		this.userName = userName;
		this.ratedName = ratedName;
		this.total_score = total_score;
		this.ratedContext = ratedContext;
	}
		private String userName;
        private String ratedName;
        private float  total_score;
        private String ratedContext;
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getRatedName() {
			return ratedName;
		}
		public void setRatedName(String ratedName) {
			this.ratedName = ratedName;
		}
		public float getTotal_score() {
			return total_score;
		}
		public void setTotal_score(float total_score) {
			this.total_score = total_score;
		}
		public String getRatedContext() {
			return ratedContext;
		}
		public void setRatedContext(String ratedContext) {
			this.ratedContext = ratedContext;
		}
}
