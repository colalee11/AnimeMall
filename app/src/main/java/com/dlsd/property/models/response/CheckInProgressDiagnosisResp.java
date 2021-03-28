package com.dlsd.property.models.response;

public class CheckInProgressDiagnosisResp {


    /**
     * code : 0
     * msg : 成功
     * data : {"contractTag":"0"}
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

    public static class DataBean {
        /**
         * contractTag : 0
         */

        private String contractTag;

        public String getContractTag() {
            return contractTag;
        }

        public void setContractTag(String contractTag) {
            this.contractTag = contractTag;
        }
    }
}
