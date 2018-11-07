package com.rs.mobile.wportal.entity;

import java.util.List;

public class OtherInfoNew {

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
        private OtherInfoBean otherInfo;
        private List<FilesBean> files;

        public OtherInfoBean getOtherInfo() {
            return otherInfo;
        }

        public void setOtherInfo(OtherInfoBean otherInfo) {
            this.otherInfo = otherInfo;
        }

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        public static class OtherInfoBean {


            private String delivery_corp;
            private String delivery_corp_code;
            private String delivery_url;
            private String return_as_tel;
            private String return_post_no;
            private String return_addr1;
            private String return_addr2;

            public String getDelivery_corp() {
                return delivery_corp;
            }

            public void setDelivery_corp(String delivery_corp) {
                this.delivery_corp = delivery_corp;
            }

            public String getDelivery_corp_code() {
                return delivery_corp_code;
            }

            public void setDelivery_corp_code(String delivery_corp_code) {
                this.delivery_corp_code = delivery_corp_code;
            }

            public String getDelivery_url() {
                return delivery_url;
            }

            public void setDelivery_url(String delivery_url) {
                this.delivery_url = delivery_url;
            }

            public String getReturn_as_tel() {
                return return_as_tel;
            }

            public void setReturn_as_tel(String return_as_tel) {
                this.return_as_tel = return_as_tel;
            }

            public String getReturn_post_no() {
                return return_post_no;
            }

            public void setReturn_post_no(String return_post_no) {
                this.return_post_no = return_post_no;
            }

            public String getReturn_addr1() {
                return return_addr1;
            }

            public void setReturn_addr1(String return_addr1) {
                this.return_addr1 = return_addr1;
            }

            public String getReturn_addr2() {
                return return_addr2;
            }

            public void setReturn_addr2(String return_addr2) {
                this.return_addr2 = return_addr2;
            }
        }

        public static class FilesBean {
            /**
             * seq_no : 1
             * add_file_name : 상가 이미지
             * f_name : write_a_bike-05.jpg
             * add_file_url : http://gigamerchantmanager.gigawon.co.kr:8825//uploadfiles/5104de79-b2be-45df-934e-22053586c25b.jpg
             */

            private String seq_no;
            private String add_file_name;
            private String f_name;
            private String add_file_url;

            public String getSeq_no() {
                return seq_no;
            }

            public void setSeq_no(String seq_no) {
                this.seq_no = seq_no;
            }

            public String getAdd_file_name() {
                return add_file_name;
            }

            public void setAdd_file_name(String add_file_name) {
                this.add_file_name = add_file_name;
            }

            public String getF_name() {
                return f_name;
            }

            public void setF_name(String f_name) {
                this.f_name = f_name;
            }

            public String getAdd_file_url() {
                return add_file_url;
            }

            public void setAdd_file_url(String add_file_url) {
                this.add_file_url = add_file_url;
            }
        }
    }
}
