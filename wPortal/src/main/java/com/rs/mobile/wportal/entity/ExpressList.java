package com.rs.mobile.wportal.entity;

import java.util.List;

public class ExpressList {

    private int status;
    private String message;
    private int total_Count;
    private int total_Page;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * CDCODE : 1010
         * CDNAME : 邮政EMS
         * CDDESC2 :
         * CDORDER : 10
         */

        private String CDCODE;
        private String CDNAME;
        private String CDDESC2;
        private String CDORDER;

        public String getCDCODE() {
            return CDCODE;
        }

        public void setCDCODE(String CDCODE) {
            this.CDCODE = CDCODE;
        }

        public String getCDNAME() {
            return CDNAME;
        }

        public void setCDNAME(String CDNAME) {
            this.CDNAME = CDNAME;
        }

        public String getCDDESC2() {
            return CDDESC2;
        }

        public void setCDDESC2(String CDDESC2) {
            this.CDDESC2 = CDDESC2;
        }

        public String getCDORDER() {
            return CDORDER;
        }

        public void setCDORDER(String CDORDER) {
            this.CDORDER = CDORDER;
        }
    }
}
