package com.rs.mobile.wportal.entity;

import java.io.Serializable;
import java.util.List;

public class Category1And2ListEntity implements Serializable{
    public String status;
    public String flag;
    public String msg;
    public List<lev1> lev1s;
    public List<lev2> lev2s;

    public class lev1{
        public String lev;
        public String lev_name;
    }

    public class lev2{
        public String lev1;
        public String lev;
        public String lev_name;
    }
}
