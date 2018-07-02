package com.rs.mobile.wportal.biz.xsgr;

import java.io.Serializable;
import java.util.List;

public class SaveAndGetShelves implements Serializable {
    private List<SpecBean> spec;
    private List<FlavorBean> Flavor;

    public List<SpecBean> getSpec() {
        return spec;
    }

    public void setSpec(List<SpecBean> spec) {
        this.spec = spec;
    }

    public List<FlavorBean> getFlavor() {
        return Flavor;
    }

    public void setFlavor(List<FlavorBean> flavor) {
        Flavor = flavor;
    }

    public static class SpecBean implements Serializable {
        private String specName;
        private String specPrice;

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public String getSpecPrice() {
            return specPrice;
        }

        public void setSpecPrice(String specPrice) {
            this.specPrice = specPrice;
        }
    }

    public static class FlavorBean implements Serializable {
        private String flavorName;

        public String getFlavorName() {
            return flavorName;
        }

        public void setFlavorName(String flavorName) {
            this.flavorName = flavorName;
        }
    }
}
