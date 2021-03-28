package com.dlsd.property.models.response;

import java.io.Serializable;
import java.util.List;

public class AddressListResp {


    /**
     * code : 0
     * data : [{"recipientId":"25","recipientName":"113131","recipientMobile":"1231321","provinceCode":"辽宁省","cityCode":"大连市","regionCode":"西岗区","recipientAddress":"131231","isDefault":"1"},{"recipientId":"26","recipientName":"李四","recipientMobile":"13840865823","provinceCode":"辽宁省","cityCode":"大连市","regionCode":"西岗区","recipientAddress":"红旗西路995号","isDefault":"1"},{"recipientId":"27","recipientName":"1321312","recipientMobile":"1231312","provinceCode":"辽宁省","cityCode":"大连市","regionCode":"普兰店区","recipientAddress":"1231312","isDefault":"1"},{"recipientId":"28","recipientName":"123131","recipientMobile":"123123","provinceCode":"辽宁省","cityCode":"大连市","regionCode":"长海县","recipientAddress":"123123","isDefault":"1"},{"recipientId":"29","recipientName":"dddd1111111","recipientMobile":"12131232131","provinceCode":"辽宁省","cityCode":"大连市","regionCode":"中山区","recipientAddress":"Dsadasdadadaaaaaaaaa","isDefault":"0"},{"recipientId":"30","recipientName":"李四","recipientMobile":"13840865823","provinceCode":"辽宁省","cityCode":"大连市","regionCode":"甘井子区","recipientAddress":"红旗西路995号","isDefault":"1"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * recipientId : 25
         * recipientName : 113131
         * recipientMobile : 1231321
         * provinceCode : 辽宁省
         * cityCode : 大连市
         * regionCode : 西岗区
         * recipientAddress : 131231
         * isDefault : 1
         */

        private String recipientId;
        private String recipientName;
        private String recipientMobile;
        private String provinceCode;
        private String cityCode;
        private String regionCode;
        private String recipientAddress;
        private String isDefault;

        public String getRecipientId() {
            return recipientId;
        }

        public void setRecipientId(String recipientId) {
            this.recipientId = recipientId;
        }

        public String getRecipientName() {
            return recipientName;
        }

        public void setRecipientName(String recipientName) {
            this.recipientName = recipientName;
        }

        public String getRecipientMobile() {
            return recipientMobile;
        }

        public void setRecipientMobile(String recipientMobile) {
            this.recipientMobile = recipientMobile;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getRecipientAddress() {
            return recipientAddress;
        }

        public void setRecipientAddress(String recipientAddress) {
            this.recipientAddress = recipientAddress;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }
    }
}
