package com.rs.mobile.wportal.biz.xsgr;

import java.io.Serializable;
import java.util.List;

public class CommodityList implements Serializable{

    private String status;
    private String message;
    private String total_Count;
    private String total_Page;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotal_Count() {
        return total_Count;
    }

    public void setTotal_Count(String total_Count) {
        this.total_Count = total_Count;
    }

    public String getTotal_Page() {
        return total_Page;
    }

    public void setTotal_Page(String total_Page) {
        this.total_Page = total_Page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{

        private String ANum;
        private String item_code;
        private String image_url;
        private String item_name;
        private String item_p;
        private String MonthSaleCount;
        private String GroupId;
        private String isSingle;
        private String ITEM_LEVEL1;
        private String level_name;

        public String getITEM_LEVEL1() {
            return ITEM_LEVEL1;
        }

        public void setITEM_LEVEL1(String ITEM_LEVEL1) {
            this.ITEM_LEVEL1 = ITEM_LEVEL1;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getANum() {
            return ANum;
        }

        public void setANum(String ANum) {
            this.ANum = ANum;
        }

        public String getItem_code() {
            return item_code;
        }

        public void setItem_code(String item_code) {
            this.item_code = item_code;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_p() {
            return item_p;
        }

        public void setItem_p(String item_p) {
            this.item_p = item_p;
        }

        public String getMonthSaleCount() {
            return MonthSaleCount;
        }

        public void setMonthSaleCount(String MonthSaleCount) {
            this.MonthSaleCount = MonthSaleCount;
        }

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String GroupId) {
            this.GroupId = GroupId;
        }

        public String getIsSingle() {
            return isSingle;
        }

        public void setIsSingle(String isSingle) {
            this.isSingle = isSingle;
        }
    }
}
