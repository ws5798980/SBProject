package com.rs.mobile.wportal.entity;

import java.util.List;

public class DateDataBean {

    /**
     * data : [{"list_num":"22","order_num":"1021807020000000022W","order_seq":"000000022","mobilepho":"01040105829","create_date":"2018-07-02 15:36:30","online_order_status":"4","order_status":"환불","tot_amt":"4200"},{"list_num":"21","order_num":"1021807020000000021W","order_seq":"000000021","mobilepho":"01040105829","create_date":"2018-07-02 15:36:07","online_order_status":"4","order_status":"환불","tot_amt":"4200"},{"list_num":"20","order_num":"1021807020000000020W","order_seq":"000000020","mobilepho":"01040105829","create_date":"2018-07-02 15:35:45","online_order_status":"4","order_status":"환불","tot_amt":"20200"},{"list_num":"19","order_num":"1021807020000000019W","order_seq":"000000019","mobilepho":"01040105829","create_date":"2018-07-02 15:35:12","online_order_status":"0","order_status":"매출","tot_amt":"4900"},{"list_num":"18","order_num":"1021807020000000018W","order_seq":"000000018","mobilepho":"01040105829","create_date":"2018-07-02 14:45:04","online_order_status":"31","order_status":"매출","tot_amt":"17400"}]
     * status : 1
     * flag : 1501
     * msg : 작업완료
     * pg : 1
     * tot_page : 5
     * tot_cnt : 22
     * start_page : 1
     * end_page : 5
     * sale_cnt : 18
     * sale_order_o : 291400
     * return_cnt : 4
     * return_order_o : 41100
     */

    private String status;
    private String flag;
    private String msg;
    private String pg;
    private String tot_page;
    private String tot_cnt;
    private String start_page;
    private String end_page;
    private String sale_cnt;
    private String sale_order_o;
    private String return_cnt;
    private String return_order_o;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPg() {
        return pg;
    }

    public void setPg(String pg) {
        this.pg = pg;
    }

    public String getTot_page() {
        return tot_page;
    }

    public void setTot_page(String tot_page) {
        this.tot_page = tot_page;
    }

    public String getTot_cnt() {
        return tot_cnt;
    }

    public void setTot_cnt(String tot_cnt) {
        this.tot_cnt = tot_cnt;
    }

    public String getStart_page() {
        return start_page;
    }

    public void setStart_page(String start_page) {
        this.start_page = start_page;
    }

    public String getEnd_page() {
        return end_page;
    }

    public void setEnd_page(String end_page) {
        this.end_page = end_page;
    }

    public String getSale_cnt() {
        return sale_cnt;
    }

    public void setSale_cnt(String sale_cnt) {
        this.sale_cnt = sale_cnt;
    }

    public String getSale_order_o() {
        return sale_order_o;
    }

    public void setSale_order_o(String sale_order_o) {
        this.sale_order_o = sale_order_o;
    }

    public String getReturn_cnt() {
        return return_cnt;
    }

    public void setReturn_cnt(String return_cnt) {
        this.return_cnt = return_cnt;
    }

    public String getReturn_order_o() {
        return return_order_o;
    }

    public void setReturn_order_o(String return_order_o) {
        this.return_order_o = return_order_o;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list_num : 22
         * order_num : 1021807020000000022W
         * order_seq : 000000022
         * mobilepho : 01040105829
         * create_date : 2018-07-02 15:36:30
         * online_order_status : 4
         * order_status : 환불
         * tot_amt : 4200
         */

        private String list_num;
        private String order_num;
        private String order_seq;
        private String mobilepho;
        private String create_date;
        private String online_order_status;
        private String order_status;
        private String tot_amt;

        public String getList_num() {
            return list_num;
        }

        public void setList_num(String list_num) {
            this.list_num = list_num;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getOrder_seq() {
            return order_seq;
        }

        public void setOrder_seq(String order_seq) {
            this.order_seq = order_seq;
        }

        public String getMobilepho() {
            return mobilepho;
        }

        public void setMobilepho(String mobilepho) {
            this.mobilepho = mobilepho;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getOnline_order_status() {
            return online_order_status;
        }

        public void setOnline_order_status(String online_order_status) {
            this.online_order_status = online_order_status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getTot_amt() {
            return tot_amt;
        }

        public void setTot_amt(String tot_amt) {
            this.tot_amt = tot_amt;
        }
    }
}
