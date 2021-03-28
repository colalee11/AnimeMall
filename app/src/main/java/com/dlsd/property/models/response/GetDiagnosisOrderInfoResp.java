package com.dlsd.property.models.response;

public class GetDiagnosisOrderInfoResp {


    /**
     * code : 0
     * data : {"diagnosisId":1253903203255152924,"amount":0.01,"diagnosisStartTime":"2020-05-24 21:59:41","diagnosisEndTime":"2020-05-24 22:58:41","recipeTime":null,"checkTime":null}
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
         * diagnosisId : 1253903203255152924
         * amount : 0.01
         * diagnosisStartTime : 2020-05-24 21:59:41
         * diagnosisEndTime : 2020-05-24 22:58:41
         * recipeTime : null
         * checkTime : null
         */

        private long diagnosisId;
        private double amount;
        private String diagnosisStartTime;
        private String diagnosisEndTime;
        private String recipeTime;
        private String checkTime;

        public long getDiagnosisId() {
            return diagnosisId;
        }

        public void setDiagnosisId(long diagnosisId) {
            this.diagnosisId = diagnosisId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getDiagnosisStartTime() {
            return diagnosisStartTime;
        }

        public void setDiagnosisStartTime(String diagnosisStartTime) {
            this.diagnosisStartTime = diagnosisStartTime;
        }

        public String getDiagnosisEndTime() {
            return diagnosisEndTime;
        }

        public void setDiagnosisEndTime(String diagnosisEndTime) {
            this.diagnosisEndTime = diagnosisEndTime;
        }

        public String getRecipeTime() {
            return recipeTime;
        }

        public void setRecipeTime(String recipeTime) {
            this.recipeTime = recipeTime;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
}
