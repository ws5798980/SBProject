package com.rs.mobile.wportal.entity;

import java.io.Serializable;
import java.util.List;

public class XsStoreDetailMenuItem implements Serializable{
    public int imgRes;
    public String productNm;
    public String price;

    public String status;
    public String flag;
    public String msg;
    public List<Storeitem> data;
    public List<datafav> dataFav;
    public class Storeitem implements Serializable{
        public String level1;
        public String level2;
        public String level3;
        public String level_name;
        public String image_url;


        public datafav toStoreitem(){

            datafav bean=new datafav();
            bean.level1=level1;
            bean.level2=level2;
            bean.level3=level3;
            bean.level_name=level_name;
            bean.image_url=image_url;
            return bean;
        }
    }
    public class datafav implements Serializable{
        public String level1;
        public String level2;
        public String level3;
        public String level_name;
        public String image_url;

    }
}
