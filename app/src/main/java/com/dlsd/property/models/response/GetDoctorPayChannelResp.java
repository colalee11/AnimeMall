package com.dlsd.property.models.response;

import java.util.List;

public class GetDoctorPayChannelResp {

    /**
     * code : 0
     * data : [{"payChannel":"alipay","paySatus":"Y"},{"payChannel":"wxpay","paySatus":"Y"}]
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

    public static class DataBean {
        /**
         * payChannel : alipay
         * paySatus : Y
         */

        private String payChannel;
        private String paySatus;

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getPaySatus() {
            return paySatus;
        }

        public void setPaySatus(String paySatus) {
            this.paySatus = paySatus;
        }
    }
}
