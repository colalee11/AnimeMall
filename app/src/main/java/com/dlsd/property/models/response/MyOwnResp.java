package com.dlsd.property.models.response;

public class MyOwnResp {

    /**
     * code : 0
     * data : {"userId":"1257576942646538242","userName":null,"profilePhoto":null,"mobile":"13238070252","sumDiagnosis":0,"sumContract":0,"sumConsume":0,"toConfirm":0,"diagnosing":0,"diagnosed":0,"appraised":0,"diagnosisRefund":0,"toPay":0,"toSend":0,"toReceive":0,"toEvaluate":0,"medicineRefund":0}
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
         * userId : 1257576942646538242
         * userName : null
         * profilePhoto : null
         * mobile : 13238070252
         * sumDiagnosis : 0
         * sumContract : 0
         * sumConsume : 0.0
         * toConfirm : 0
         * diagnosing : 0
         * diagnosed : 0
         * appraised : 0
         * diagnosisRefund : 0
         * toPay : 0
         * toSend : 0
         * toReceive : 0
         * toEvaluate : 0
         * complete : 0
         * medicineRefund : 0
         */

        private String userId;
        private Object userName;
        private Object profilePhoto;
        private String mobile;
        private int sumDiagnosis;
        private int sumContract;
        private double sumConsume;
        private int toConfirm;
        private int diagnosing;
        private int diagnosed;
        private int appraised;
        private int diagnosisRefund;
        private int toPay;
        private int toSend;
        private int toReceive;
        private int toEvaluate;
        private int complete;
        private int medicineRefund;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public Object getProfilePhoto() {
            return profilePhoto;
        }

        public void setProfilePhoto(Object profilePhoto) {
            this.profilePhoto = profilePhoto;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getSumDiagnosis() {
            return sumDiagnosis;
        }

        public void setSumDiagnosis(int sumDiagnosis) {
            this.sumDiagnosis = sumDiagnosis;
        }

        public int getSumContract() {
            return sumContract;
        }

        public void setSumContract(int sumContract) {
            this.sumContract = sumContract;
        }

        public double getSumConsume() {
            return sumConsume;
        }

        public void setSumConsume(double sumConsume) {
            this.sumConsume = sumConsume;
        }

        public int getToConfirm() {
            return toConfirm;
        }

        public void setToConfirm(int toConfirm) {
            this.toConfirm = toConfirm;
        }

        public int getDiagnosing() {
            return diagnosing;
        }

        public void setDiagnosing(int diagnosing) {
            this.diagnosing = diagnosing;
        }

        public int getDiagnosed() {
            return diagnosed;
        }

        public void setDiagnosed(int diagnosed) {
            this.diagnosed = diagnosed;
        }

        public int getAppraised() {
            return appraised;
        }

        public void setAppraised(int appraised) {
            this.appraised = appraised;
        }

        public int getDiagnosisRefund() {
            return diagnosisRefund;
        }

        public void setDiagnosisRefund(int diagnosisRefund) {
            this.diagnosisRefund = diagnosisRefund;
        }

        public int getToPay() {
            return toPay;
        }

        public void setToPay(int toPay) {
            this.toPay = toPay;
        }

        public int getToSend() {
            return toSend;
        }

        public void setToSend(int toSend) {
            this.toSend = toSend;
        }

        public int getToReceive() {
            return toReceive;
        }

        public void setToReceive(int toReceive) {
            this.toReceive = toReceive;
        }

        public int getToEvaluate() {
            return toEvaluate;
        }

        public void setToEvaluate(int toEvaluate) {
            this.toEvaluate = toEvaluate;
        }

        public int getComplete() {
            return complete;
        }

        public void setComplete(int complete) {
            this.complete = complete;
        }

        public int getMedicineRefund() {
            return medicineRefund;
        }

        public void setMedicineRefund(int medicineRefund) {
            this.medicineRefund = medicineRefund;
        }
    }
}
