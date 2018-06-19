package com.rs.mobile.wportal.entity;

import java.io.Serializable;
import java.util.List;

public class Category3ListEntity implements Serializable{
    public String status;
    public String flag;
    public String msg;
    public List<lev3> lev3s;

    public class lev3{
        public String lev1;
        public String lev2;
        public String lev;
        public String lev_name;
    }
}
