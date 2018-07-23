package com.rs.mobile.wportal.biz.xsgr;

import java.util.List;

public class AddressSearch {

    private String status;
    private String message;
    private int total_Count;
    private int total_Page;
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

        private PostBean post;

        public PostBean getPost() {
            return post;
        }

        public void setPost(PostBean post) {
            this.post = post;
        }

        public static class PostBean {

            private PageinfoBean pageinfo;
            private ItemlistBean itemlist;

            public PageinfoBean getPageinfo() {
                return pageinfo;
            }

            public void setPageinfo(PageinfoBean pageinfo) {
                this.pageinfo = pageinfo;
            }

            public ItemlistBean getItemlist() {
                return itemlist;
            }

            public void setItemlist(ItemlistBean itemlist) {
                this.itemlist = itemlist;
            }

            public static class PageinfoBean {

                private TotalCountBean totalCount;
                private TotalPageBean totalPage;
                private CountPerPageBean countPerPage;
                private CurrentPageBean currentPage;

                public TotalCountBean getTotalCount() {
                    return totalCount;
                }

                public void setTotalCount(TotalCountBean totalCount) {
                    this.totalCount = totalCount;
                }

                public TotalPageBean getTotalPage() {
                    return totalPage;
                }

                public void setTotalPage(TotalPageBean totalPage) {
                    this.totalPage = totalPage;
                }

                public CountPerPageBean getCountPerPage() {
                    return countPerPage;
                }

                public void setCountPerPage(CountPerPageBean countPerPage) {
                    this.countPerPage = countPerPage;
                }

                public CurrentPageBean getCurrentPage() {
                    return currentPage;
                }

                public void setCurrentPage(CurrentPageBean currentPage) {
                    this.currentPage = currentPage;
                }

                public static class TotalCountBean {
                    /**
                     * cdatasection : 6
                     */

                    private String cdatasection;

                    public String getCdatasection() {
                        return cdatasection;
                    }

                    public void setCdatasection(String cdatasection) {
                        this.cdatasection = cdatasection;
                    }
                }

                public static class TotalPageBean {
                    /**
                     * cdatasection : 1
                     */

                    private String cdatasection;

                    public String getCdatasection() {
                        return cdatasection;
                    }

                    public void setCdatasection(String cdatasection) {
                        this.cdatasection = cdatasection;
                    }
                }

                public static class CountPerPageBean {
                    /**
                     * cdatasection : 10
                     */

                    private String cdatasection;

                    public String getCdatasection() {
                        return cdatasection;
                    }

                    public void setCdatasection(String cdatasection) {
                        this.cdatasection = cdatasection;
                    }
                }

                public static class CurrentPageBean {
                    /**
                     * cdatasection : 1
                     */

                    private String cdatasection;

                    public String getCdatasection() {
                        return cdatasection;
                    }

                    public void setCdatasection(String cdatasection) {
                        this.cdatasection = cdatasection;
                    }
                }
            }

            public static class ItemlistBean {
                private List<ItemBean> item;

                public List<ItemBean> getItem() {
                    return item;
                }

                public void setItem(List<ItemBean> item) {
                    this.item = item;
                }

                public static class ItemBean {
                    /**
                     * postcd : {"cdatasection":"58323"}
                     * address : {"cdatasection":"전라남도 나주시 정보화길 1 (빛가람동, 우정사업정보센터)"}
                     * addrjibun : {"cdatasection":"전라남도 나주시 빛가람동 219 (우정사업정보센터)"}
                     */

                    private PostcdBean postcd;
                    private AddressBean address;
                    private AddrjibunBean addrjibun;

                    public PostcdBean getPostcd() {
                        return postcd;
                    }

                    public void setPostcd(PostcdBean postcd) {
                        this.postcd = postcd;
                    }

                    public AddressBean getAddress() {
                        return address;
                    }

                    public void setAddress(AddressBean address) {
                        this.address = address;
                    }

                    public AddrjibunBean getAddrjibun() {
                        return addrjibun;
                    }

                    public void setAddrjibun(AddrjibunBean addrjibun) {
                        this.addrjibun = addrjibun;
                    }

                    public static class PostcdBean {
                        /**
                         * cdatasection : 58323
                         */

                        private String cdatasection;

                        public String getCdatasection() {
                            return cdatasection;
                        }

                        public void setCdatasection(String cdatasection) {
                            this.cdatasection = cdatasection;
                        }
                    }

                    public static class AddressBean {
                        /**
                         * cdatasection : 전라남도 나주시 정보화길 1 (빛가람동, 우정사업정보센터)
                         */

                        private String cdatasection;

                        public String getCdatasection() {
                            return cdatasection;
                        }

                        public void setCdatasection(String cdatasection) {
                            this.cdatasection = cdatasection;
                        }
                    }

                    public static class AddrjibunBean {
                        /**
                         * cdatasection : 전라남도 나주시 빛가람동 219 (우정사업정보센터)
                         */

                        private String cdatasection;

                        public String getCdatasection() {
                            return cdatasection;
                        }

                        public void setCdatasection(String cdatasection) {
                            this.cdatasection = cdatasection;
                        }
                    }
                }
            }
        }
    }
}
