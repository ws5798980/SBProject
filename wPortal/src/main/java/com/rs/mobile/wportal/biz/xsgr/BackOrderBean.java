package com.rs.mobile.wportal.biz.xsgr;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BackOrderBean {
    /**
     * data : [{"dataitem":[{"item_name":"잠발라야 숯불 볶음밥","order_q":"1","order_o":"4900","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000606.PNG"}],"list_num":"16","order_num":"1021807040000000003W","cancel_num":"189C83C6-3D37-4D22","custom_code":"186743935020f829","custom_name":"JUSTBOSS","mobilepho":"01040105829","to_address":"경기도 안산시 상록구 각골동로 8 (본오동, 대성N.C.T)960-5 501호","cancel_date":"2018-07-04 10:23:42","tot_amt":"4900","online_order_status":"처리완료","status_classify":"고객취소"},{"dataitem":[{"item_name":"양지쌀국수","order_q":"1","order_o":"4200","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000603.PNG"},{"item_name":"포카이 매운 쌀국수...","order_q":"1","order_o":"4200","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000604.PNG"},{"item_name":"숯불 팟타이","order_q":"1","order_o":"4900","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000605.PNG"},{"item_name":"잠발라야 숯불 볶음밥","order_q":"1","order_o":"4900","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000606.PNG"},{"item_name":"포 직화 데리덮밥","order_q":"1","order_o":"5200","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000607.PNG"},{"item_name":"분짜","order_q":"1","order_o":"12500","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000608.PNG"}],"list_num":"15","order_num":"1021807030000000006W","cancel_num":"E5982407-AEE9-411B","custom_code":"186743935020f829","custom_name":"JUSTBOSS","mobilepho":"01040105829","to_address":"경기도 안산시 상록구 각골동로 8 (본오동, 대성N.C.T)960-5 501호","cancel_date":"","tot_amt":"35900","online_order_status":"처리완료","status_classify":"고객취소"},{"dataitem":[{"item_name":"분짜","order_q":"1","order_o":"12500","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000608.PNG"}],"list_num":"14","order_num":"1021807030000000004W","cancel_num":"4275C1AA-F3E8-4DC7","custom_code":"186743935020f829","custom_name":"JUSTBOSS","mobilepho":"01040105829","to_address":"경기도 안산시 상록구 각골동로 8 (본오동, 대성N.C.T)960-5 501호","cancel_date":"2018-07-03 10:54:09","tot_amt":"12500","online_order_status":"처리완료","status_classify":"고객취소"},{"dataitem":[{"item_name":"잠발라야 숯불 볶음밥","order_q":"1","order_o":"4900","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000606.PNG"}],"list_num":"13","order_num":"1021807030000000003W","cancel_num":"D905185D-D781-4DC1","custom_code":"186743935020f829","custom_name":"JUSTBOSS","mobilepho":"01040105829","to_address":"경기도 안산시 상록구 각골동로 8 (본오동, 대성N.C.T)960-5 501호","cancel_date":"2018-07-03 10:56:50","tot_amt":"4900","online_order_status":"처리완료","status_classify":"고객취소"},{"dataitem":[{"item_name":"포 직화 데리덮밥","order_q":"1","order_o":"5200","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000607.PNG"}],"list_num":"12","order_num":"1021807030000000001W","cancel_num":"9D9C21E4-37A7-4B65","custom_code":"186743935020f829","custom_name":"JUSTBOSS","mobilepho":"01040105829","to_address":"경기도 안산시 상록구 각골동로 8 (본오동, 대성N.C.T)960-5 501호","cancel_date":"2018-07-03 10:01:28","tot_amt":"5200","online_order_status":"처리완료","status_classify":"고객취소"}]
     * status : 1
     * flag : 1501
     * msg : 작업완료
     * pg : 1
     * tot_page : 4
     * tot_cnt : 16
     * start_page : 1
     * end_page : 4
     */

    private String status;
    private String flag;
    private String msg;
    private String pg;
    private String tot_page;
    private String tot_cnt;
    private String start_page;
    private String end_page;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * dataitem : [{"item_name":"잠발라야 숯불 볶음밥","order_q":"1","order_o":"4900","item_image_url":"http://fileserver.gigawon.cn:8588/item/1803000000606.PNG"}]
         * list_num : 16
         * order_num : 1021807040000000003W
         * cancel_num : 189C83C6-3D37-4D22
         * custom_code : 186743935020f829
         * custom_name : JUSTBOSS
         * mobilepho : 01040105829
         * to_address : 경기도 안산시 상록구 각골동로 8 (본오동, 대성N.C.T)960-5 501호
         * cancel_date : 2018-07-04 10:23:42
         * tot_amt : 4900
         * online_order_status : 처리완료
         * status_classify : 고객취소
         */

        private String list_num;
        private String order_num;
        private String cancel_num;
        private String custom_code;
        private String custom_name;
        private String mobilepho;
        private String to_address;
        private String cancel_date;
        private String tot_amt;
        private String online_order_status;
        private String status_classify;
        private List<DataitemBean> dataitem;

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

        public String getCancel_num() {
            return cancel_num;
        }

        public void setCancel_num(String cancel_num) {
            this.cancel_num = cancel_num;
        }

        public String getCustom_code() {
            return custom_code;
        }

        public void setCustom_code(String custom_code) {
            this.custom_code = custom_code;
        }

        public String getCustom_name() {
            return custom_name;
        }

        public void setCustom_name(String custom_name) {
            this.custom_name = custom_name;
        }

        public String getMobilepho() {
            return mobilepho;
        }

        public void setMobilepho(String mobilepho) {
            this.mobilepho = mobilepho;
        }

        public String getTo_address() {
            return to_address;
        }

        public void setTo_address(String to_address) {
            this.to_address = to_address;
        }

        public String getCancel_date() {
            return cancel_date;
        }

        public void setCancel_date(String cancel_date) {
            this.cancel_date = cancel_date;
        }

        public String getTot_amt() {
            return tot_amt;
        }

        public void setTot_amt(String tot_amt) {
            this.tot_amt = tot_amt;
        }

        public String getOnline_order_status() {
            return online_order_status;
        }

        public void setOnline_order_status(String online_order_status) {
            this.online_order_status = online_order_status;
        }

        public String getStatus_classify() {
            return status_classify;
        }

        public void setStatus_classify(String status_classify) {
            this.status_classify = status_classify;
        }

        public List<DataitemBean> getDataitem() {
            return dataitem;
        }

        public void setDataitem(List<DataitemBean> dataitem) {
            this.dataitem = dataitem;
        }

        public static class DataitemBean implements Parcelable {
            /**
             * item_name : 잠발라야 숯불 볶음밥
             * order_q : 1
             * order_o : 4900
             * item_image_url : http://fileserver.gigawon.cn:8588/item/1803000000606.PNG
             */

            private String item_name;
            private String order_q;
            private String order_o;
            private String item_image_url;

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

            public String getItem_image_url() {
                return item_image_url;
            }

            public void setItem_image_url(String item_image_url) {
                this.item_image_url = item_image_url;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.item_name);
                dest.writeString(this.order_q);
                dest.writeString(this.order_o);
                dest.writeString(this.item_image_url);
            }

            public DataitemBean() {
            }

            protected DataitemBean(Parcel in) {
                this.item_name = in.readString();
                this.order_q = in.readString();
                this.order_o = in.readString();
                this.item_image_url = in.readString();
            }

            public static final Creator<DataitemBean> CREATOR = new Creator<DataitemBean>() {
                @Override
                public DataitemBean createFromParcel(Parcel source) {
                    return new DataitemBean(source);
                }

                @Override
                public DataitemBean[] newArray(int size) {
                    return new DataitemBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.list_num);
            dest.writeString(this.order_num);
            dest.writeString(this.cancel_num);
            dest.writeString(this.custom_code);
            dest.writeString(this.custom_name);
            dest.writeString(this.mobilepho);
            dest.writeString(this.to_address);
            dest.writeString(this.cancel_date);
            dest.writeString(this.tot_amt);
            dest.writeString(this.online_order_status);
            dest.writeString(this.status_classify);
            dest.writeList(this.dataitem);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.list_num = in.readString();
            this.order_num = in.readString();
            this.cancel_num = in.readString();
            this.custom_code = in.readString();
            this.custom_name = in.readString();
            this.mobilepho = in.readString();
            this.to_address = in.readString();
            this.cancel_date = in.readString();
            this.tot_amt = in.readString();
            this.online_order_status = in.readString();
            this.status_classify = in.readString();
            this.dataitem = new ArrayList<DataitemBean>();
            in.readList(this.dataitem, DataitemBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
