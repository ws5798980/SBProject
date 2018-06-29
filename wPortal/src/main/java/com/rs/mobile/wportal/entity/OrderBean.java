package com.rs.mobile.wportal.entity;

import java.util.List;

public class OrderBean {
    /**
     * data : [{"dataitem":[{"item_name":"데리야끼 치킨 라이스","order_q":"1","order_o":"310000"}],"order_num":"1021804200000000011W","order_seq":"000000011","custom_name":"기가피플","delivery_name":"오세포","mobilepho":"18670315833","zip_code":"08511 ","to_address":" 서울특별시 금천구 디지털로9길 33 (가산동, IT미래TOWER)1307호","order_date":"20180420","create_date":"2018-04-20 17:36:02.870","online_order_status":"1","delivery_o":"0","tot_amt":"310000"},{"dataitem":[{"item_name":"데리야끼 치킨 라이스","order_q":"1","order_o":"310000"}],"order_num":"1021804200000000010W","order_seq":"000000010","custom_name":"기가피플","delivery_name":"오세포","mobilepho":"18670315833","zip_code":"08511 ","to_address":" 서울특별시 금천구 디지털로9길 33 (가산동, IT미래TOWER)1307호","order_date":"20180420","create_date":"2018-04-20 17:28:27.280","online_order_status":"1","delivery_o":"0","tot_amt":"310000"},{"dataitem":[{"item_name":"봄나물무침을 곁들인 대패삼겹살 덮밥과 바지락 된장찌개","order_q":"1","order_o":"260000"}],"order_num":"1021804130000000015W","order_seq":"000000015","custom_name":"기가피플","delivery_name":"오세포","mobilepho":"18670315833","zip_code":"08511 ","to_address":" 서울특별시 금천구 디지털로9길 33 (가산동, IT미래TOWER)1307호","order_date":"20180413","create_date":"2018-04-13 16:53:55.113","online_order_status":"1","delivery_o":"0","tot_amt":"260000"}]
     * status : 1
     * flag : 1501
     * msg : 작업완료
     */

    private String status;
    private String flag;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dataitem : [{"item_name":"데리야끼 치킨 라이스","order_q":"1","order_o":"310000"}]
         * order_num : 1021804200000000011W
         * order_seq : 000000011
         * custom_name : 기가피플
         * delivery_name : 오세포
         * mobilepho : 18670315833
         * zip_code : 08511
         * to_address :  서울특별시 금천구 디지털로9길 33 (가산동, IT미래TOWER)1307호
         * order_date : 20180420
         * create_date : 2018-04-20 17:36:02.870
         * online_order_status : 1
         * delivery_o : 0
         * tot_amt : 310000
         */

        private String order_num;
        private String order_seq;
        private String custom_name;
        private String delivery_name;
        private String mobilepho;
        private String zip_code;
        private String to_address;
        private String order_date;
        private String create_date;
        private String online_order_status;
        private String delivery_o;
        private String tot_amt;
        private List<DataitemBean> dataitem;

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

        public String getCustom_name() {
            return custom_name;
        }

        public void setCustom_name(String custom_name) {
            this.custom_name = custom_name;
        }

        public String getDelivery_name() {
            return delivery_name;
        }

        public void setDelivery_name(String delivery_name) {
            this.delivery_name = delivery_name;
        }

        public String getMobilepho() {
            return mobilepho;
        }

        public void setMobilepho(String mobilepho) {
            this.mobilepho = mobilepho;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        public String getTo_address() {
            return to_address;
        }

        public void setTo_address(String to_address) {
            this.to_address = to_address;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
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

        public String getDelivery_o() {
            return delivery_o;
        }

        public void setDelivery_o(String delivery_o) {
            this.delivery_o = delivery_o;
        }

        public String getTot_amt() {
            return tot_amt;
        }

        public void setTot_amt(String tot_amt) {
            this.tot_amt = tot_amt;
        }

        public List<DataitemBean> getDataitem() {
            return dataitem;
        }

        public void setDataitem(List<DataitemBean> dataitem) {
            this.dataitem = dataitem;
        }

        public static class DataitemBean {
            /**
             * item_name : 데리야끼 치킨 라이스
             * order_q : 1
             * order_o : 310000
             */

            private String item_name;
            private String order_q;
            private String order_o;

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getOrder_q() {
                return order_q;
            }

            public void setOrder_q(String order_q) {
                this.order_q = order_q;
            }

            public String getOrder_o() {
                return order_o;
            }

            public void setOrder_o(String order_o) {
                this.order_o = order_o;
            }
        }
    }
}
