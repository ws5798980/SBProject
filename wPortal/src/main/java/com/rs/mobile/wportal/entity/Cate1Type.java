package com.rs.mobile.wportal.entity;

import java.util.List;

public class Cate1Type {

    private int status;
    private String message;
    private int total_Count;
    private int total_Page;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal_Count() {
        return total_Count;
    }

    public void setTotal_Count(int total_Count) {
        this.total_Count = total_Count;
    }

    public int getTotal_Page() {
        return total_Page;
    }

    public void setTotal_Page(int total_Page) {
        this.total_Page = total_Page;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<Cate1DepthBean> Cate1Depth;

        public List<Cate1DepthBean> getCate1Depth() {
            return Cate1Depth;
        }

        public void setCate1Depth(List<Cate1DepthBean> Cate1Depth) {
            this.Cate1Depth = Cate1Depth;
        }

        public static class Cate1DepthBean {
            /**
             * LEVEL1 : 1
             * LEVEL2 : *
             * LEVEL3 : *
             * LEVEL_NAME : 외식
             * IMAGE_URL : http://fileserver.gigawon.co.kr:8588/Store/category/level1/img_01.png
             * RANK : 1
             */

            private String LEVEL1;
            private String LEVEL2;
            private String LEVEL3;
            private String LEVEL_NAME;
            private String IMAGE_URL;
            private String RANK;

            public String getLEVEL1() {
                return LEVEL1;
            }

            public void setLEVEL1(String LEVEL1) {
                this.LEVEL1 = LEVEL1;
            }

            public String getLEVEL2() {
                return LEVEL2;
            }

            public void setLEVEL2(String LEVEL2) {
                this.LEVEL2 = LEVEL2;
            }

            public String getLEVEL3() {
                return LEVEL3;
            }

            public void setLEVEL3(String LEVEL3) {
                this.LEVEL3 = LEVEL3;
            }

            public String getLEVEL_NAME() {
                return LEVEL_NAME;
            }

            public void setLEVEL_NAME(String LEVEL_NAME) {
                this.LEVEL_NAME = LEVEL_NAME;
            }

            public String getIMAGE_URL() {
                return IMAGE_URL;
            }

            public void setIMAGE_URL(String IMAGE_URL) {
                this.IMAGE_URL = IMAGE_URL;
            }

            public String getRANK() {
                return RANK;
            }

            public void setRANK(String RANK) {
                this.RANK = RANK;
            }
        }
    }
}
