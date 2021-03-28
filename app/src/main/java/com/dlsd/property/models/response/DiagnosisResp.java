package com.dlsd.property.models.response;

import java.io.Serializable;

public class DiagnosisResp {


    /**
     * code : 0
     * msg : 成功
     * data : {"prepayId":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2021001157687040&biz_content=%7B%22body%22%3A%22%E7%BE%A4%E5%8C%BB%E6%B1%87%E4%BA%92%E8%81%94%E7%BD%91%E8%AF%8A%E7%96%97%E5%B9%B3%E5%8F%B0-%E5%9C%A8%E7%BA%BF%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A%22ALI1255191529197899778%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%BE%A4%E5%8C%BB%E6%B1%87%E4%BA%92%E8%81%94%E7%BD%91%E8%AF%8A%E7%96%97%E5%B9%B3%E5%8F%B0-%E5%9C%A8%E7%BA%BF%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2259.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2F116.62.107.188%3A9003%2Fnotify%2FaliPayNotify&return_url=www.baidu.com&sign=ezmr%2FkkQrNUXB8c0kIACfO3QEAYLy9VFrR8VHYqGv5ZjBlzgUO8vV%2BCTxkmWS5uL3sLUH73amfV5hNWd93e1sl1GMgS6cXYdBOzL8Dn2JySC%2FU4eWS8J%2Fiw4Acqh8GkbZFiTbYjBB6WCBhCUrRI2D4qPq%2BKJFey%2F4sWuf4%2FIQzY0XS1n11s2UB%2BujGATbjBIRnu85NyjsDwnOb1NIsT1VnFh%2BXO8xwziEDZzGXxjMx0khzlK41b85k4r8%2BAo8Jt0uM%2Bd2goNNFXreD7051L8J0culY4M5ATiw82fP0jREunc2CB3FGZdfCqLEV7VywZalG5lOADigiuiWJ5%2BLrOo5g%3D%3D&sign_type=RSA2×tamp=2020-04-29+01%3A46%3A14&version=1.0"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * prepayId : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2021001157687040&biz_content=%7B%22body%22%3A%22%E7%BE%A4%E5%8C%BB%E6%B1%87%E4%BA%92%E8%81%94%E7%BD%91%E8%AF%8A%E7%96%97%E5%B9%B3%E5%8F%B0-%E5%9C%A8%E7%BA%BF%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A%22ALI1255191529197899778%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%BE%A4%E5%8C%BB%E6%B1%87%E4%BA%92%E8%81%94%E7%BD%91%E8%AF%8A%E7%96%97%E5%B9%B3%E5%8F%B0-%E5%9C%A8%E7%BA%BF%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2259.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2F116.62.107.188%3A9003%2Fnotify%2FaliPayNotify&return_url=www.baidu.com&sign=ezmr%2FkkQrNUXB8c0kIACfO3QEAYLy9VFrR8VHYqGv5ZjBlzgUO8vV%2BCTxkmWS5uL3sLUH73amfV5hNWd93e1sl1GMgS6cXYdBOzL8Dn2JySC%2FU4eWS8J%2Fiw4Acqh8GkbZFiTbYjBB6WCBhCUrRI2D4qPq%2BKJFey%2F4sWuf4%2FIQzY0XS1n11s2UB%2BujGATbjBIRnu85NyjsDwnOb1NIsT1VnFh%2BXO8xwziEDZzGXxjMx0khzlK41b85k4r8%2BAo8Jt0uM%2Bd2goNNFXreD7051L8J0culY4M5ATiw82fP0jREunc2CB3FGZdfCqLEV7VywZalG5lOADigiuiWJ5%2BLrOo5g%3D%3D&sign_type=RSA2×tamp=2020-04-29+01%3A46%3A14&version=1.0
         */

        private String prepayId;
        private String appId;
        private String mchId;
        private String extendMsg;
        private String nonceStr;
        private String timestamp;
        private String sign;

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getExtendMsg() {
            return extendMsg;
        }

        public void setExtendMsg(String extendMsg) {
            this.extendMsg = extendMsg;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
