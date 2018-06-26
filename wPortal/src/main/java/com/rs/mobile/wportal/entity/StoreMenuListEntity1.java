package com.rs.mobile.wportal.entity;

import java.io.Serializable;
import java.util.List;

public class StoreMenuListEntity1 implements Serializable{
    public String status;
    public String flag;
    public String msg;
    public datainfo data;
    public foodSpec selectfoodSpec;
    public foodFlavor selectfoodFlavor;
    public plistinfo selectplistinfo;
    public int num=1;
    public class datainfo implements Serializable{
        public storeInfo StoreInfo;
        public List<plistinfo> plist;
        public List<foodSpec> FoodSpec;
        public List<foodFlavor> FoodFlavor;
        public List<categoryinfo> category;
    }

    public class storeInfo implements Serializable{
        public String custom_code;
        public String custom_name;
        public String shop_thumnail_image;
        public String sale_cnt;
        public String fav_cnt;
        public String distance;
        public String score;
        public String cnt;
        public String sale_custom_cnt;
        public boolean favorites;
        public String shop_latitude;
        public String shop_longitude;
    }

    public class plistinfo implements Serializable{
        public String ANum;
        public String item_code;
        public String image_url;
        public String item_name;
        public String item_p;
        public String MonthSaleCount;
        public String GroupId;
        public String isSingle;
        public String synopsis;
        public String Remark;
        public String ITEM_DETAILS;
    }

    public class foodSpec implements Serializable{
        public String item_code;
        public String item_name;
        public String item_p;
    }

    public class foodFlavor implements Serializable{
        public String flavorName;
    }

    public class categoryinfo implements Serializable{
        public String id;
        public String level_name;
    }

}
