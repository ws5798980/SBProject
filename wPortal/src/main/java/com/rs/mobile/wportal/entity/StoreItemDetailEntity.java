package com.rs.mobile.wportal.entity;

import java.io.Serializable;
import java.util.List;

public class StoreItemDetailEntity implements Serializable{
    public String status;
    public String flag;
    public String msg;
    public String start_page;
    public String end_page;
    public String pg;
    public String tot_page;
    public String tot_cnt;
    public String custom_code;
    public String custom_name;
    public String shop_thumnail_image;
    public String sale_cnt;
    public String fav_cnt;
    public String distance;
    public String score;
    public String cnt;
    public String sale_custom_cnt;
    public String favorites;
    public List<Storeitem> Storeitems;

    public class Storeitem implements Serializable{
        public String item_code;
        public String item_name;
        public String image_url;
        public String market_p;
        public String item_p;
        public String dis_rate;
        public String rum;
        public String num;
    }
}
