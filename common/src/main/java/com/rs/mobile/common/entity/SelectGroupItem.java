package com.rs.mobile.common.entity;

import java.io.Serializable;
import java.util.List;

public class SelectGroupItem implements Serializable{
    public String status;
    public String flag;
    public String msg;
    public String pg;
    public String start_page;
    public String end_page;
    public String tot_cnt;
    public String tot_page;
    public List<data> data;

    public class data implements Serializable{
        public String rum;
        public String custom_code;
        public String custom_name;
        public String top_name;
        public String telephon;
        public String kor_addr;
        public String addr;
        public String shop_thumnail_image;
    }
}
