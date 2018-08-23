package com.rs.mobile.wportal.entity;

import java.util.List;

public class CommentBean {
    /**
     * ShopAssessData : [{"list_num":"17","id":"92","custom_name":"JUSTBOSS","img_path":"http://portal.gigawon.cn:8488/MediaUploader/wsProfile/WPortal20180416112634.jpg","reg_date":"2018-05-22 20:28:24.460","score":"4","rep_content":"评论 内容 添加评论 test","sale_content":"商家模块切图标注感谢您的支持与喜欢商商家模块切图标注商家模块切图标注商家模块切图标注商家模块切图标注","sale_reg_date":"2018-07-02 13:27:10.400"},{"list_num":"16","id":"79","custom_name":"yechung Mart","img_path":"http://portal.gigawon.cn:8488/MediaUploader/wsProfile/WPortal20180330094823.jpg","reg_date":"2018-05-22 20:02:20.790","score":"5","rep_content":"평가하기 테스트","sale_content":"感谢您的支持与喜欢！我们会努力做到更好","sale_reg_date":"2018-07-02 13:09:21.440"},{"list_num":"15","id":"80","custom_name":"","img_path":"http://fileserver.gigawon.cn:8588/18874097957.jpg","reg_date":"2018-05-22 20:02:20.790","score":"4","rep_content":"평가하기 테스트1","sale_content":"","sale_reg_date":""},{"list_num":"14","id":"81","custom_name":"","img_path":"http://fileserver.gigawon.cn:8588/18874097957.jpg","reg_date":"2018-05-22 20:02:20.790","score":"3","rep_content":"평가하기 테스트2","sale_content":"","sale_reg_date":""},{"list_num":"13","id":"82","custom_name":"","img_path":"http://fileserver.gigawon.cn:8588/18874097957.jpg","reg_date":"2018-05-22 20:02:20.790","score":"4","rep_content":"평가하기 테스트3","sale_content":"","sale_reg_date":""}]
     * status : 1
     * flag : 1501
     * msg : 작업완료
     * pg : 1
     * tot_page : 4
     * tot_cnt : 17
     * start_page : 1
     * end_page : 4
     * rating1 : 2
     * rating2 : 1
     * rating3 : 4
     * rating4 : 6
     * rating5 : 4
     * assess_cnt : 17
     * assess_avg : 3.5
     * not_recom : 2
     * not_conf : 0
     */

    private String status;
    private String flag;
    private String msg;
    private String pg;
    private String tot_page;
    private String tot_cnt;
    private String start_page;
    private String end_page;
    private String rating1;
    private String rating2;
    private String rating3;
    private String rating4;
    private String rating5;
    private String assess_cnt;
    private String assess_avg;
    private String not_recom;
    private String not_conf;
    private List<ShopAssessDataBean> ShopAssessData;

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

    public String getRating1() {
        return rating1;
    }

    public void setRating1(String rating1) {
        this.rating1 = rating1;
    }

    public String getRating2() {
        return rating2;
    }

    public void setRating2(String rating2) {
        this.rating2 = rating2;
    }

    public String getRating3() {
        return rating3;
    }

    public void setRating3(String rating3) {
        this.rating3 = rating3;
    }

    public String getRating4() {
        return rating4;
    }

    public void setRating4(String rating4) {
        this.rating4 = rating4;
    }

    public String getRating5() {
        return rating5;
    }

    public void setRating5(String rating5) {
        this.rating5 = rating5;
    }

    public String getAssess_cnt() {
        return assess_cnt;
    }

    public void setAssess_cnt(String assess_cnt) {
        this.assess_cnt = assess_cnt;
    }

    public String getAssess_avg() {
        return assess_avg;
    }

    public void setAssess_avg(String assess_avg) {
        this.assess_avg = assess_avg;
    }

    public String getNot_recom() {
        return not_recom;
    }

    public void setNot_recom(String not_recom) {
        this.not_recom = not_recom;
    }

    public String getNot_conf() {
        return not_conf;
    }

    public void setNot_conf(String not_conf) {
        this.not_conf = not_conf;
    }

    public List<ShopAssessDataBean> getShopAssessData() {
        return ShopAssessData;
    }

    public void setShopAssessData(List<ShopAssessDataBean> ShopAssessData) {
        this.ShopAssessData = ShopAssessData;
    }

    public static class ShopAssessDataBean {
        /**
         * list_num : 17
         * id : 92
         * custom_name : JUSTBOSS
         * img_path : http://portal.gigawon.cn:8488/MediaUploader/wsProfile/WPortal20180416112634.jpg
         * reg_date : 2018-05-22 20:28:24.460
         * score : 4
         * rep_content : 评论 内容 添加评论 test
         * sale_content : 商家模块切图标注感谢您的支持与喜欢商商家模块切图标注商家模块切图标注商家模块切图标注商家模块切图标注
         * sale_reg_date : 2018-07-02 13:27:10.400
         */

        private String list_num;
        private String id;
        private String custom_name;
        private String img_path;
        private String reg_date;
        private String score;
        private String rep_content;
        private String sale_content;
        private String sale_reg_date;

        public String getList_num() {
            return list_num;
        }

        public void setList_num(String list_num) {
            this.list_num = list_num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustom_name() {
            return custom_name;
        }

        public void setCustom_name(String custom_name) {
            this.custom_name = custom_name;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getReg_date() {
            return reg_date;
        }

        public void setReg_date(String reg_date) {
            this.reg_date = reg_date;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getRep_content() {
            return rep_content;
        }

        public void setRep_content(String rep_content) {
            this.rep_content = rep_content;
        }

        public String getSale_content() {
            return sale_content;
        }

        public void setSale_content(String sale_content) {
            this.sale_content = sale_content;
        }

        public String getSale_reg_date() {
            return sale_reg_date;
        }

        public void setSale_reg_date(String sale_reg_date) {
            this.sale_reg_date = sale_reg_date;
        }
    }
}
