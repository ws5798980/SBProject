package com.rs.mobile.wportal.biz.xsgr;

import java.io.Serializable;
import java.util.List;

public class QueryCategoryList implements Serializable{

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

        private String custom_code;
        private String id;
        private String pid;
        private String rid;
        private String level_name;
        private String image_url;
        private String rank;
        private boolean add;

        public boolean isAdd() {
            return add;
        }

        public void setAdd(boolean add) {
            this.add = add;
        }

        public String getCustom_code() {
            return custom_code;
        }

        public void setCustom_code(String custom_code) {
            this.custom_code = custom_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }
}
