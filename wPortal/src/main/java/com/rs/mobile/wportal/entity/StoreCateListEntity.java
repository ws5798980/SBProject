package com.rs.mobile.wportal.entity;

import java.io.Serializable;
import java.util.List;

public class StoreCateListEntity implements Serializable{
    public List<Lev2> lev2s;
    public List<Lev3> lev3s;
    public List<Store> storelist;
    public String status;
    public String flag;
    public String msg;
    public String start_page;
    public String end_page;
    public String pg;
    public String tot_page;
    public String tot_cnt;
    public List<CatList> data;

    public class Lev2 implements Serializable{
        public String lev1;
        public String lev;
        public String lev_name;
        public String image_url;
    }

    public class Lev3 implements Serializable{
        public String lev1;
        public String lev2;
        public String lev;
        public String lev_name;
        public String image_url;
    }

    public class Store implements Serializable{
        public String shop_thumnail_image;
        public String custom_code;
        public String custom_name;
        public String kor_addr;
        public String distance;
        public String score;
        public String cnt;
        public String sale_custom_cnt;
    }

    public class CatList implements Serializable{
        public String custom_lev1;
        public String custom_lev2;
        public String custom_lev3;
        public String level_name;
        public String image_url;

    }

}
