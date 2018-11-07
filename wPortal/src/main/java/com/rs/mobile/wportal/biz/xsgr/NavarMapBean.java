package com.rs.mobile.wportal.biz.xsgr;

import java.util.List;

public class NavarMapBean {
    /**
     * result : {"total":2,"userquery":"127.1141382,37.3599968","items":[{"address":"경기도 성남시 분당구 정자동 257-1","addrdetail":{"country":"대한민국","sido":"경기도","sigugun":"성남시 분당구","dongmyun":"정자동","ri":"","rest":"257-1"},"isAdmAddress":false,"isRoadAddress":false,"point":{"x":127.1164925,"y":37.3597611}},{"address":"경기도 성남시 분당구 정자3동 257-1","addrdetail":{"country":"대한민국","sido":"경기도","sigugun":"성남시 분당구","dongmyun":"정자3동","ri":"","rest":"257-1"},"isAdmAddress":true,"isRoadAddress":false,"point":{"x":127.1195385,"y":37.3607965}}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : 2
         * userquery : 127.1141382,37.3599968
         * items : [{"address":"경기도 성남시 분당구 정자동 257-1","addrdetail":{"country":"대한민국","sido":"경기도","sigugun":"성남시 분당구","dongmyun":"정자동","ri":"","rest":"257-1"},"isAdmAddress":false,"isRoadAddress":false,"point":{"x":127.1164925,"y":37.3597611}},{"address":"경기도 성남시 분당구 정자3동 257-1","addrdetail":{"country":"대한민국","sido":"경기도","sigugun":"성남시 분당구","dongmyun":"정자3동","ri":"","rest":"257-1"},"isAdmAddress":true,"isRoadAddress":false,"point":{"x":127.1195385,"y":37.3607965}}]
         */

        private int total;
        private String userquery;
        private List<ItemsBean> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getUserquery() {
            return userquery;
        }

        public void setUserquery(String userquery) {
            this.userquery = userquery;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * address : 경기도 성남시 분당구 정자동 257-1
             * addrdetail : {"country":"대한민국","sido":"경기도","sigugun":"성남시 분당구","dongmyun":"정자동","ri":"","rest":"257-1"}
             * isAdmAddress : false
             * isRoadAddress : false
             * point : {"x":127.1164925,"y":37.3597611}
             */

            private String address;
            private AddrdetailBean addrdetail;
            private boolean isAdmAddress;
            private boolean isRoadAddress;
            private PointBean point;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public AddrdetailBean getAddrdetail() {
                return addrdetail;
            }

            public void setAddrdetail(AddrdetailBean addrdetail) {
                this.addrdetail = addrdetail;
            }

            public boolean isIsAdmAddress() {
                return isAdmAddress;
            }

            public void setIsAdmAddress(boolean isAdmAddress) {
                this.isAdmAddress = isAdmAddress;
            }

            public boolean isIsRoadAddress() {
                return isRoadAddress;
            }

            public void setIsRoadAddress(boolean isRoadAddress) {
                this.isRoadAddress = isRoadAddress;
            }

            public PointBean getPoint() {
                return point;
            }

            public void setPoint(PointBean point) {
                this.point = point;
            }

            public static class AddrdetailBean {
                /**
                 * country : 대한민국
                 * sido : 경기도
                 * sigugun : 성남시 분당구
                 * dongmyun : 정자동
                 * ri :
                 * rest : 257-1
                 */

                private String country;
                private String sido;
                private String sigugun;
                private String dongmyun;
                private String ri;
                private String rest;

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getSido() {
                    return sido;
                }

                public void setSido(String sido) {
                    this.sido = sido;
                }

                public String getSigugun() {
                    return sigugun;
                }

                public void setSigugun(String sigugun) {
                    this.sigugun = sigugun;
                }

                public String getDongmyun() {
                    return dongmyun;
                }

                public void setDongmyun(String dongmyun) {
                    this.dongmyun = dongmyun;
                }

                public String getRi() {
                    return ri;
                }

                public void setRi(String ri) {
                    this.ri = ri;
                }

                public String getRest() {
                    return rest;
                }

                public void setRest(String rest) {
                    this.rest = rest;
                }
            }

            public static class PointBean {
                /**
                 * x : 127.1164925
                 * y : 37.3597611
                 */

                private double x;
                private double y;

                public double getX() {
                    return x;
                }

                public void setX(double x) {
                    this.x = x;
                }

                public double getY() {
                    return y;
                }

                public void setY(double y) {
                    this.y = y;
                }
            }
        }
    }
}
