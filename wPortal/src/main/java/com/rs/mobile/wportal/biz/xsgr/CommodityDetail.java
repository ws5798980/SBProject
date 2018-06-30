package com.rs.mobile.wportal.biz.xsgr;

import java.io.Serializable;
import java.util.List;

public class CommodityDetail implements Serializable{

    private String status;
    private String message;
    private String total_Count;
    private String total_Page;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotal_Count() {
        return total_Count;
    }

    public void setTotal_Count(String total_Count) {
        this.total_Count = total_Count;
    }

    public String getTotal_Page() {
        return total_Page;
    }

    public void setTotal_Page(String total_Page) {
        this.total_Page = total_Page;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{

        private ItemBean item;
        private List<SpecBean> spec;
        private List<FlavorBean> Flavor;

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public List<SpecBean> getSpec() {
            return spec;
        }

        public void setSpec(List<SpecBean> spec) {
            this.spec = spec;
        }

        public List<FlavorBean> getFlavor() {
            return Flavor;
        }

        public void setFlavor(List<FlavorBean> Flavor) {
            this.Flavor = Flavor;
        }

        public static class ItemBean implements Serializable{

            private String ITEM_CODE;
            private String ITEM_NAME;
            private String dom_forign;
            private String basis_p;
            private String CUSTOM_ITEM_CODE;
            private String CUSTOM_ITEM_NAME;
            private String CUSTOM_ITEM_SPEC;
            private String ITEM_P;
            private String Image_url;

            public String getITEM_CODE() {
                return ITEM_CODE;
            }

            public void setITEM_CODE(String ITEM_CODE) {
                this.ITEM_CODE = ITEM_CODE;
            }

            public String getITEM_NAME() {
                return ITEM_NAME;
            }

            public void setITEM_NAME(String ITEM_NAME) {
                this.ITEM_NAME = ITEM_NAME;
            }

            public String getDom_forign() {
                return dom_forign;
            }

            public void setDom_forign(String dom_forign) {
                this.dom_forign = dom_forign;
            }

            public String getBasis_p() {
                return basis_p;
            }

            public void setBasis_p(String basis_p) {
                this.basis_p = basis_p;
            }

            public String getCUSTOM_ITEM_CODE() {
                return CUSTOM_ITEM_CODE;
            }

            public void setCUSTOM_ITEM_CODE(String CUSTOM_ITEM_CODE) {
                this.CUSTOM_ITEM_CODE = CUSTOM_ITEM_CODE;
            }

            public String getCUSTOM_ITEM_NAME() {
                return CUSTOM_ITEM_NAME;
            }

            public void setCUSTOM_ITEM_NAME(String CUSTOM_ITEM_NAME) {
                this.CUSTOM_ITEM_NAME = CUSTOM_ITEM_NAME;
            }

            public String getCUSTOM_ITEM_SPEC() {
                return CUSTOM_ITEM_SPEC;
            }

            public void setCUSTOM_ITEM_SPEC(String CUSTOM_ITEM_SPEC) {
                this.CUSTOM_ITEM_SPEC = CUSTOM_ITEM_SPEC;
            }

            public String getITEM_P() {
                return ITEM_P;
            }

            public void setITEM_P(String ITEM_P) {
                this.ITEM_P = ITEM_P;
            }

            public String getImage_url() {
                return Image_url;
            }

            public void setImage_url(String Image_url) {
                this.Image_url = Image_url;
            }
        }

        public static class SpecBean implements Serializable{

            private String groupid;
            private String item_code;
            private String item_name;
            private String item_p;
            private String defaultShow;

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public String getItem_code() {
                return item_code;
            }

            public void setItem_code(String item_code) {
                this.item_code = item_code;
            }

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getItem_p() {
                return item_p;
            }

            public void setItem_p(String item_p) {
                this.item_p = item_p;
            }

            public String getDefaultShow() {
                return defaultShow;
            }

            public void setDefaultShow(String defaultShow) {
                this.defaultShow = defaultShow;
            }
        }

        public static class FlavorBean implements Serializable{
            /**
             * groupid : G201805222051007367
             * flavorName : 冰的
             */

            private String groupid;
            private String flavorName;
            private String add;


            public String getAdd() {
                return add;
            }

            public void setAdd(String add) {
                this.add = add;
            }

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public String getFlavorName() {
                return flavorName;
            }

            public void setFlavorName(String flavorName) {
                this.flavorName = flavorName;
            }
        }
    }
}
