package com.dlsd.property.models.response;

import java.util.List;

public class DistrictListResp {

    /**
     * code : 0
     * msg : null
     * data : [{"provinceId":"210000","provinceName":"辽宁省","cities":[{"cityId":"210200","cityName":"大连市","provinceId":null,"districts":[{"districtId":"210202","districtName":"中山区","cityId":null},{"districtId":"210203","districtName":"西岗区","cityId":null},{"districtId":"210204","districtName":"沙河口区","cityId":null},{"districtId":"210211","districtName":"甘井子区","cityId":null},{"districtId":"210212","districtName":"旅顺口区","cityId":null},{"districtId":"210213","districtName":"金州区","cityId":null},{"districtId":"210214","districtName":"普兰店区","cityId":null},{"districtId":"210224","districtName":"长海县","cityId":null},{"districtId":"210281","districtName":"瓦房店市","cityId":null},{"districtId":"210283","districtName":"庄河市","cityId":null}]}]}]
     */

    private int code;
    private Object msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
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
         * provinceId : 210000
         * provinceName : 辽宁省
         * cities : [{"cityId":"210200","cityName":"大连市","provinceId":null,"districts":[{"districtId":"210202","districtName":"中山区","cityId":null},{"districtId":"210203","districtName":"西岗区","cityId":null},{"districtId":"210204","districtName":"沙河口区","cityId":null},{"districtId":"210211","districtName":"甘井子区","cityId":null},{"districtId":"210212","districtName":"旅顺口区","cityId":null},{"districtId":"210213","districtName":"金州区","cityId":null},{"districtId":"210214","districtName":"普兰店区","cityId":null},{"districtId":"210224","districtName":"长海县","cityId":null},{"districtId":"210281","districtName":"瓦房店市","cityId":null},{"districtId":"210283","districtName":"庄河市","cityId":null}]}]
         */

        private String provinceId;
        private String provinceName;
        private List<CitiesBean> cities;

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public List<CitiesBean> getCities() {
            return cities;
        }

        public void setCities(List<CitiesBean> cities) {
            this.cities = cities;
        }

        public static class CitiesBean {
            /**
             * cityId : 210200
             * cityName : 大连市
             * provinceId : null
             * districts : [{"districtId":"210202","districtName":"中山区","cityId":null},{"districtId":"210203","districtName":"西岗区","cityId":null},{"districtId":"210204","districtName":"沙河口区","cityId":null},{"districtId":"210211","districtName":"甘井子区","cityId":null},{"districtId":"210212","districtName":"旅顺口区","cityId":null},{"districtId":"210213","districtName":"金州区","cityId":null},{"districtId":"210214","districtName":"普兰店区","cityId":null},{"districtId":"210224","districtName":"长海县","cityId":null},{"districtId":"210281","districtName":"瓦房店市","cityId":null},{"districtId":"210283","districtName":"庄河市","cityId":null}]
             */

            private String cityId;
            private String cityName;
            private Object provinceId;
            private List<DistrictsBean> districts;

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public Object getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(Object provinceId) {
                this.provinceId = provinceId;
            }

            public List<DistrictsBean> getDistricts() {
                return districts;
            }

            public void setDistricts(List<DistrictsBean> districts) {
                this.districts = districts;
            }

            public static class DistrictsBean {
                /**
                 * districtId : 210202
                 * districtName : 中山区
                 * cityId : null
                 */

                private String districtId;
                private String districtName;
                private Object cityId;

                public String getDistrictId() {
                    return districtId;
                }

                public void setDistrictId(String districtId) {
                    this.districtId = districtId;
                }

                public String getDistrictName() {
                    return districtName;
                }

                public void setDistrictName(String districtName) {
                    this.districtName = districtName;
                }

                public Object getCityId() {
                    return cityId;
                }

                public void setCityId(Object cityId) {
                    this.cityId = cityId;
                }
            }
        }
    }
}
