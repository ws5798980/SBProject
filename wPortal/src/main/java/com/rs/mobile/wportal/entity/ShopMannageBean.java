package com.rs.mobile.wportal.entity;

import java.util.List;

public class ShopMannageBean {


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


        private InfoBean info;
        private List<DamdangBean> damdang;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<DamdangBean> getDamdang() {
            return damdang;
        }

        public void setDamdang(List<DamdangBean> damdang) {
            this.damdang = damdang;
        }

        public static class InfoBean {
            /**
             * CUSTOM_LEV1 :
             * CUSTOM_LEV2 :
             * CUSTOM_LEV3 :
             * LEVEL1_NAME :
             * LEVEL2_NAME :
             * LEVEL3_NAME :
             * custom_name : 11
             * company_num : 1212121212
             * comp_type : 55555
             * corp_no : 1235
             * comp_class : 55555
             * telephon : 123456789
             * FAX_NUM : 1234567546
             * zip_code : 1222
             * kor_addr : 1222
             * kor_addr_detail : 1222
             * regi_gubun : all
             * maker_code : 201109587
             * supplier_code : 201109587
             * saup_gubun : corp
             * tax_gubun : tax
             * contract_date : 1900/1/1 0:00:00
             * contract_type : contract_end
             * ceo_name : 5555
             * ceo_hp_no1 : 07022
             * ceo_hp_no2 : 123422
             * ceo_hp_no3 : 123422
             * jungsan_holder : 123422
             * jungsan_bank : 123123123122
             * jungsan_bank_code : 123422
             * company_homepage : 123123
             * regi_date : 2018/5/23 23:01:14
             * edit_date : 2018/7/4 9:40:06
             * work_memid : 1111111188862190
             * from_site :
             * from_site_code :
             * md_id :
             * md_commission_rate :
             * sso_regiKey : F7728238-B7A7-4A7E-8FB5-0622A9C3FA97
             */

            private String CUSTOM_LEV1;
            private String CUSTOM_LEV2;
            private String CUSTOM_LEV3;
            private String LEVEL1_NAME;
            private String LEVEL2_NAME;
            private String LEVEL3_NAME;
            private String custom_name;
            private String company_num;
            private String comp_type;
            private String corp_no;
            private String comp_class;
            private String telephon;
            private String FAX_NUM;
            private String zip_code;
            private String kor_addr;
            private String kor_addr_detail;
            private String regi_gubun;
            private String maker_code;
            private String supplier_code;
            private String saup_gubun;
            private String tax_gubun;
            private String contract_date;
            private String contract_type;
            private String ceo_name;
            private String ceo_hp_no1;
            private String ceo_hp_no2;
            private String ceo_hp_no3;
            private String jungsan_holder;
            private String jungsan_bank;
            private String jungsan_bank_code;
            private String company_homepage;
            private String regi_date;
            private String edit_date;
            private String work_memid;
            private String from_site;
            private String from_site_code;
            private String md_id;
            private String md_commission_rate;
            private String sso_regiKey;

            public String getCUSTOM_LEV1() {
                return CUSTOM_LEV1;
            }

            public void setCUSTOM_LEV1(String CUSTOM_LEV1) {
                this.CUSTOM_LEV1 = CUSTOM_LEV1;
            }

            public String getCUSTOM_LEV2() {
                return CUSTOM_LEV2;
            }

            public void setCUSTOM_LEV2(String CUSTOM_LEV2) {
                this.CUSTOM_LEV2 = CUSTOM_LEV2;
            }

            public String getCUSTOM_LEV3() {
                return CUSTOM_LEV3;
            }

            public void setCUSTOM_LEV3(String CUSTOM_LEV3) {
                this.CUSTOM_LEV3 = CUSTOM_LEV3;
            }

            public String getLEVEL1_NAME() {
                return LEVEL1_NAME;
            }

            public void setLEVEL1_NAME(String LEVEL1_NAME) {
                this.LEVEL1_NAME = LEVEL1_NAME;
            }

            public String getLEVEL2_NAME() {
                return LEVEL2_NAME;
            }

            public void setLEVEL2_NAME(String LEVEL2_NAME) {
                this.LEVEL2_NAME = LEVEL2_NAME;
            }

            public String getLEVEL3_NAME() {
                return LEVEL3_NAME;
            }

            public void setLEVEL3_NAME(String LEVEL3_NAME) {
                this.LEVEL3_NAME = LEVEL3_NAME;
            }

            public String getCustom_name() {
                return custom_name;
            }

            public void setCustom_name(String custom_name) {
                this.custom_name = custom_name;
            }

            public String getCompany_num() {
                return company_num;
            }

            public void setCompany_num(String company_num) {
                this.company_num = company_num;
            }

            public String getComp_type() {
                return comp_type;
            }

            public void setComp_type(String comp_type) {
                this.comp_type = comp_type;
            }

            public String getCorp_no() {
                return corp_no;
            }

            public void setCorp_no(String corp_no) {
                this.corp_no = corp_no;
            }

            public String getComp_class() {
                return comp_class;
            }

            public void setComp_class(String comp_class) {
                this.comp_class = comp_class;
            }

            public String getTelephon() {
                return telephon;
            }

            public void setTelephon(String telephon) {
                this.telephon = telephon;
            }

            public String getFAX_NUM() {
                return FAX_NUM;
            }

            public void setFAX_NUM(String FAX_NUM) {
                this.FAX_NUM = FAX_NUM;
            }

            public String getZip_code() {
                return zip_code;
            }

            public void setZip_code(String zip_code) {
                this.zip_code = zip_code;
            }

            public String getKor_addr() {
                return kor_addr;
            }

            public void setKor_addr(String kor_addr) {
                this.kor_addr = kor_addr;
            }

            public String getKor_addr_detail() {
                return kor_addr_detail;
            }

            public void setKor_addr_detail(String kor_addr_detail) {
                this.kor_addr_detail = kor_addr_detail;
            }

            public String getRegi_gubun() {
                return regi_gubun;
            }

            public void setRegi_gubun(String regi_gubun) {
                this.regi_gubun = regi_gubun;
            }

            public String getMaker_code() {
                return maker_code;
            }

            public void setMaker_code(String maker_code) {
                this.maker_code = maker_code;
            }

            public String getSupplier_code() {
                return supplier_code;
            }

            public void setSupplier_code(String supplier_code) {
                this.supplier_code = supplier_code;
            }

            public String getSaup_gubun() {
                return saup_gubun;
            }

            public void setSaup_gubun(String saup_gubun) {
                this.saup_gubun = saup_gubun;
            }

            public String getTax_gubun() {
                return tax_gubun;
            }

            public void setTax_gubun(String tax_gubun) {
                this.tax_gubun = tax_gubun;
            }

            public String getContract_date() {
                return contract_date;
            }

            public void setContract_date(String contract_date) {
                this.contract_date = contract_date;
            }

            public String getContract_type() {
                return contract_type;
            }

            public void setContract_type(String contract_type) {
                this.contract_type = contract_type;
            }

            public String getCeo_name() {
                return ceo_name;
            }

            public void setCeo_name(String ceo_name) {
                this.ceo_name = ceo_name;
            }

            public String getCeo_hp_no1() {
                return ceo_hp_no1;
            }

            public void setCeo_hp_no1(String ceo_hp_no1) {
                this.ceo_hp_no1 = ceo_hp_no1;
            }

            public String getCeo_hp_no2() {
                return ceo_hp_no2;
            }

            public void setCeo_hp_no2(String ceo_hp_no2) {
                this.ceo_hp_no2 = ceo_hp_no2;
            }

            public String getCeo_hp_no3() {
                return ceo_hp_no3;
            }

            public void setCeo_hp_no3(String ceo_hp_no3) {
                this.ceo_hp_no3 = ceo_hp_no3;
            }

            public String getJungsan_holder() {
                return jungsan_holder;
            }

            public void setJungsan_holder(String jungsan_holder) {
                this.jungsan_holder = jungsan_holder;
            }

            public String getJungsan_bank() {
                return jungsan_bank;
            }

            public void setJungsan_bank(String jungsan_bank) {
                this.jungsan_bank = jungsan_bank;
            }

            public String getJungsan_bank_code() {
                return jungsan_bank_code;
            }

            public void setJungsan_bank_code(String jungsan_bank_code) {
                this.jungsan_bank_code = jungsan_bank_code;
            }

            public String getCompany_homepage() {
                return company_homepage;
            }

            public void setCompany_homepage(String company_homepage) {
                this.company_homepage = company_homepage;
            }

            public String getRegi_date() {
                return regi_date;
            }

            public void setRegi_date(String regi_date) {
                this.regi_date = regi_date;
            }

            public String getEdit_date() {
                return edit_date;
            }

            public void setEdit_date(String edit_date) {
                this.edit_date = edit_date;
            }

            public String getWork_memid() {
                return work_memid;
            }

            public void setWork_memid(String work_memid) {
                this.work_memid = work_memid;
            }

            public String getFrom_site() {
                return from_site;
            }

            public void setFrom_site(String from_site) {
                this.from_site = from_site;
            }

            public String getFrom_site_code() {
                return from_site_code;
            }

            public void setFrom_site_code(String from_site_code) {
                this.from_site_code = from_site_code;
            }

            public String getMd_id() {
                return md_id;
            }

            public void setMd_id(String md_id) {
                this.md_id = md_id;
            }

            public String getMd_commission_rate() {
                return md_commission_rate;
            }

            public void setMd_commission_rate(String md_commission_rate) {
                this.md_commission_rate = md_commission_rate;
            }

            public String getSso_regiKey() {
                return sso_regiKey;
            }

            public void setSso_regiKey(String sso_regiKey) {
                this.sso_regiKey = sso_regiKey;
            }
        }

        public static class DamdangBean {
            /**
             * seq_no : 1
             * damdang_gubun : 2
             * damdang_name : 11111111111111
             * damdang_email : 11111111111111@111111111111111
             * damdang_tel_no : 1111222
             * damdang_hp_no : 11111111111
             */

            private String seq_no;
            private String damdang_gubun;
            private String damdang_name;
            private String damdang_email;
            private String damdang_tel_no;
            private String damdang_hp_no;

            public String getSeq_no() {
                return seq_no;
            }

            public void setSeq_no(String seq_no) {
                this.seq_no = seq_no;
            }

            public String getDamdang_gubun() {
                return damdang_gubun;
            }

            public void setDamdang_gubun(String damdang_gubun) {
                this.damdang_gubun = damdang_gubun;
            }

            public String getDamdang_name() {
                return damdang_name;
            }

            public void setDamdang_name(String damdang_name) {
                this.damdang_name = damdang_name;
            }

            public String getDamdang_email() {
                return damdang_email;
            }

            public void setDamdang_email(String damdang_email) {
                this.damdang_email = damdang_email;
            }

            public String getDamdang_tel_no() {
                return damdang_tel_no;
            }

            public void setDamdang_tel_no(String damdang_tel_no) {
                this.damdang_tel_no = damdang_tel_no;
            }

            public String getDamdang_hp_no() {
                return damdang_hp_no;
            }

            public void setDamdang_hp_no(String damdang_hp_no) {
                this.damdang_hp_no = damdang_hp_no;
            }
        }
    }
}
