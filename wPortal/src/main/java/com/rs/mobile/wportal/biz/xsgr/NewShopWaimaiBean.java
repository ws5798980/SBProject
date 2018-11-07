package com.rs.mobile.wportal.biz.xsgr;

import java.util.List;

public class NewShopWaimaiBean {


    private DataBean data;
    private String status;
    private String flag;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        private List<NewStoreBean> NewStore;

        public List<NewStoreBean> getNewStore() {
            return NewStore;
        }

        public void setNewStore(List<NewStoreBean> NewStore) {
            this.NewStore = NewStore;
        }

        public static class NewStoreBean {
            /**
             * CUSTOM_CODE : 01071390067abcde
             * CUSTOM_NAME : 悟空酒汤
             * SHOP_THUMNAIL_IMAGE : http://fileserver.gigawon.co.kr:8588/store/01071390067.PNG
             * SHOP_INFO :
             업체정보

             영업시간 10:00 - 03:00

             부가정보 세스코멤버스 사업장


             결제정보

             최소주문금액 20,000원

             결제수단 신용카드 ,  현금 , 요기서결제


             사업자정보

             상호명 마왕족발삼성점

             사업자등록번호 899-46-00107


             원산지정보



             *왕족(스페인,독일) *돼지고기(스페인) *미니족(스페인,독일) *돼지고기(스페인) *돼지껍데기(국내산) *쌀(국내산)
             * FAV_CNT : 0
             * SCORE : 0.00
             * distance : 428.43
             */

            private String CUSTOM_CODE;
            private String CUSTOM_NAME;
            private String SHOP_THUMNAIL_IMAGE;
            private String SHOP_INFO;
            private String FAV_CNT;
            private String SCORE;
            private String distance;

            public String getCUSTOM_CODE() {
                return CUSTOM_CODE;
            }

            public void setCUSTOM_CODE(String CUSTOM_CODE) {
                this.CUSTOM_CODE = CUSTOM_CODE;
            }

            public String getCUSTOM_NAME() {
                return CUSTOM_NAME;
            }

            public void setCUSTOM_NAME(String CUSTOM_NAME) {
                this.CUSTOM_NAME = CUSTOM_NAME;
            }

            public String getSHOP_THUMNAIL_IMAGE() {
                return SHOP_THUMNAIL_IMAGE;
            }

            public void setSHOP_THUMNAIL_IMAGE(String SHOP_THUMNAIL_IMAGE) {
                this.SHOP_THUMNAIL_IMAGE = SHOP_THUMNAIL_IMAGE;
            }

            public String getSHOP_INFO() {
                return SHOP_INFO;
            }

            public void setSHOP_INFO(String SHOP_INFO) {
                this.SHOP_INFO = SHOP_INFO;
            }

            public String getFAV_CNT() {
                return FAV_CNT;
            }

            public void setFAV_CNT(String FAV_CNT) {
                this.FAV_CNT = FAV_CNT;
            }

            public String getSCORE() {
                return SCORE;
            }

            public void setSCORE(String SCORE) {
                this.SCORE = SCORE;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}
