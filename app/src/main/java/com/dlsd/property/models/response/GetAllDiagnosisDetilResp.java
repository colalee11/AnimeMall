package com.dlsd.property.models.response;

import java.util.List;

public class GetAllDiagnosisDetilResp {


    /**
     * code : 0
     * data : {"diagnosisId":"1253903203255153044","status":"6","diagnosis":"你还行啊╮(￣▽￣)╭","patientUserId":"1266295578401361921","doctorUserId":"1261464286164377602","diagnosisTypeId":1,"remarks":null,"age":null,"mobile":null,"isAllergy":"1","allergyHistory":"头疼恶魔摸摸摸","isDisease":"1","diseaseHistory":"哦哦哦哦哦哦哦哦哦哦","familyMemberId":"57","idCardNumber":"110101199003079737","userName":"新患者测试","contractStatus":"false","imagesList":[],"patientProfilePhoto":null,"recipeDetailsList":[{"id":122,"recipeId":10,"drugId":1424,"count":13,"units":"瓶","use":"空腹口服","useFlag":0,"dose":"一天一次好几次","doseFlag":1,"eachDose":null,"eachDoseFlag":null,"amount":null,"treatment":null,"additionalInfo":"123","createdBy":null,"createdTime":"2020-06-19 22:42:38","updatedBy":null,"updatedTime":"2020-06-19 22:42:38","delFlag":"0","drugName":"杞菊地黄口服液","factoryName":"山东沃华医药科技股份有限公司","specification":"每支10ml","drugPrice":"676.00","usage":"口服，一次4片，一日3次，疗程7天","indicationsRange":"宣肺泄热，化痰止咳"},{"id":123,"recipeId":10,"drugId":1423,"count":3,"units":"瓶","use":"需要时外用","useFlag":0,"dose":"一天好几次","doseFlag":1,"eachDose":null,"eachDoseFlag":null,"amount":null,"treatment":null,"additionalInfo":"无","createdBy":null,"createdTime":"2020-06-19 22:42:38","updatedBy":null,"updatedTime":"2020-06-19 22:42:38","delFlag":"0","drugName":"夏枯草膏","factoryName":"四川逢春制药有限公司","specification":"每瓶装130g","drugPrice":"159.00","usage":"口服，一次4片，一日3次，疗程7天","indicationsRange":"宣肺泄热，化痰止咳"},{"id":124,"recipeId":10,"drugId":6148,"count":2,"units":"瓶","use":"饭后口服","useFlag":0,"dose":"无","doseFlag":1,"eachDose":null,"eachDoseFlag":null,"amount":null,"treatment":null,"additionalInfo":"无","createdBy":null,"createdTime":"2020-06-19 22:42:38","updatedBy":null,"updatedTime":"2020-06-19 22:42:38","delFlag":"0","drugName":"甲硝唑片","factoryName":"黑龙江龙桂制药有限公司","specification":"0.2g","drugPrice":"108.00","usage":"口服，一次4片，一日3次，疗程7天","indicationsRange":"宣肺泄热，化痰止咳"}],"recipeReferencePay":"943.0","medicalCertificate":null,"recipeId":"10","pharmacyId":null,"pharmacyDrugList":[{"drugID":1424,"drugName":"杞菊地黄口服液","factoryName":"山东沃华医药科技股份有限公司","unit":"瓶","count":13,"specification":"每支10ml","price":52},{"drugID":1423,"drugName":"夏枯草膏","factoryName":"四川逢春制药有限公司","unit":"瓶","count":3,"specification":"每瓶装130g","price":53},{"drugID":6148,"drugName":"甲硝唑片","factoryName":"黑龙江龙桂制药有限公司","unit":"瓶","count":2,"specification":"0.2g","price":54}],"pharmacyDrugPrice":"159.0","pharmacy":{"pharmacyId":2,"pharmacyName":"成大方圆星海店","img":"http://47.114.120.127:8000/icon-pharmacy.png","phone":"0411-53752131","address":"大连市沙河口中山路464号B1","hasAliapy":"N","mchidWxpay":null,"hasWxpay":"N","mchidAlipay":null,"longitude":null,"dimension":null,"accountName":"刘雷刚","bankCode":"1004","bankAbbr":null,"bankName":"浦发银行","bankAccount":"6217931678620438","createdBy":null,"createdTime":"2020-06-20 11:13:59","updatedBy":null,"updatedTime":"2020-06-22 16:27:14","delFlag":"0"},"drugOrder":{"drugOrderId":12,"orderId":1273788279743492097,"patientUserId":"3","recipeId":10,"pharmacyId":2,"status":0,"takeWay":2,"takeCode":null,"deliveryAddressId":57,"expressName":null,"expressNumber":null,"rejectReason":null,"deliveryTime":null,"pickupTime":null,"createdBy":null,"createdTime":"2020-06-19 09:23:11","updatedBy":null,"updatedTime":"2020-06-19 09:23:11","delFlag":"0","takeAwayName":"快递包邮","payTime":"2020-06-19 09:23:11","payChannel":"ALIPAY"},"drugOrderStatus":"0","expiredDate":null,"endAmount":"0.01","deliveryAddress":{"id":57,"patientUserId":"1","name":"测试618","mobile":"13542344325","provinces":"辽宁省-大连市-中山区","address":"青泥洼桥","defaultFlag":"0","createdBy":null,"createdTime":null,"updatedBy":null,"updatedTime":null,"delFlag":"0"}}
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
         * diagnosisId : 1253903203255153044
         * status : 6
         * diagnosis : 你还行啊╮(￣▽￣)╭
         * patientUserId : 1266295578401361921
         * doctorUserId : 1261464286164377602
         * diagnosisTypeId : 1
         * remarks : null
         * age : null
         * mobile : null
         * isAllergy : 1
         * allergyHistory : 头疼恶魔摸摸摸
         * isDisease : 1
         * diseaseHistory : 哦哦哦哦哦哦哦哦哦哦
         * familyMemberId : 57
         * idCardNumber : 110101199003079737
         * userName : 新患者测试
         * contractStatus : false
         * imagesList : []
         * patientProfilePhoto : null
         * recipeDetailsList : [{"id":122,"recipeId":10,"drugId":1424,"count":13,"units":"瓶","use":"空腹口服","useFlag":0,"dose":"一天一次好几次","doseFlag":1,"eachDose":null,"eachDoseFlag":null,"amount":null,"treatment":null,"additionalInfo":"123","createdBy":null,"createdTime":"2020-06-19 22:42:38","updatedBy":null,"updatedTime":"2020-06-19 22:42:38","delFlag":"0","drugName":"杞菊地黄口服液","factoryName":"山东沃华医药科技股份有限公司","specification":"每支10ml","drugPrice":"676.00","usage":"口服，一次4片，一日3次，疗程7天","indicationsRange":"宣肺泄热，化痰止咳"},{"id":123,"recipeId":10,"drugId":1423,"count":3,"units":"瓶","use":"需要时外用","useFlag":0,"dose":"一天好几次","doseFlag":1,"eachDose":null,"eachDoseFlag":null,"amount":null,"treatment":null,"additionalInfo":"无","createdBy":null,"createdTime":"2020-06-19 22:42:38","updatedBy":null,"updatedTime":"2020-06-19 22:42:38","delFlag":"0","drugName":"夏枯草膏","factoryName":"四川逢春制药有限公司","specification":"每瓶装130g","drugPrice":"159.00","usage":"口服，一次4片，一日3次，疗程7天","indicationsRange":"宣肺泄热，化痰止咳"},{"id":124,"recipeId":10,"drugId":6148,"count":2,"units":"瓶","use":"饭后口服","useFlag":0,"dose":"无","doseFlag":1,"eachDose":null,"eachDoseFlag":null,"amount":null,"treatment":null,"additionalInfo":"无","createdBy":null,"createdTime":"2020-06-19 22:42:38","updatedBy":null,"updatedTime":"2020-06-19 22:42:38","delFlag":"0","drugName":"甲硝唑片","factoryName":"黑龙江龙桂制药有限公司","specification":"0.2g","drugPrice":"108.00","usage":"口服，一次4片，一日3次，疗程7天","indicationsRange":"宣肺泄热，化痰止咳"}]
         * recipeReferencePay : 943.0
         * medicalCertificate : null
         * recipeId : 10
         * pharmacyId : null
         * pharmacyDrugList : [{"drugID":1424,"drugName":"杞菊地黄口服液","factoryName":"山东沃华医药科技股份有限公司","unit":"瓶","count":13,"specification":"每支10ml","price":52},{"drugID":1423,"drugName":"夏枯草膏","factoryName":"四川逢春制药有限公司","unit":"瓶","count":3,"specification":"每瓶装130g","price":53},{"drugID":6148,"drugName":"甲硝唑片","factoryName":"黑龙江龙桂制药有限公司","unit":"瓶","count":2,"specification":"0.2g","price":54}]
         * pharmacyDrugPrice : 159.0
         * pharmacy : {"pharmacyId":2,"pharmacyName":"成大方圆星海店","img":"http://47.114.120.127:8000/icon-pharmacy.png","phone":"0411-53752131","address":"大连市沙河口中山路464号B1","hasAliapy":"N","mchidWxpay":null,"hasWxpay":"N","mchidAlipay":null,"longitude":null,"dimension":null,"accountName":"刘雷刚","bankCode":"1004","bankAbbr":null,"bankName":"浦发银行","bankAccount":"6217931678620438","createdBy":null,"createdTime":"2020-06-20 11:13:59","updatedBy":null,"updatedTime":"2020-06-22 16:27:14","delFlag":"0"}
         * drugOrder : {"drugOrderId":12,"orderId":1273788279743492097,"patientUserId":"3","recipeId":10,"pharmacyId":2,"status":0,"takeWay":2,"takeCode":null,"deliveryAddressId":57,"expressName":null,"expressNumber":null,"rejectReason":null,"deliveryTime":null,"pickupTime":null,"createdBy":null,"createdTime":"2020-06-19 09:23:11","updatedBy":null,"updatedTime":"2020-06-19 09:23:11","delFlag":"0","takeAwayName":"快递包邮","payTime":"2020-06-19 09:23:11","payChannel":"ALIPAY"}
         * drugOrderStatus : 0
         * expiredDate : null
         * endAmount : 0.01
         * deliveryAddress : {"id":57,"patientUserId":"1","name":"测试618","mobile":"13542344325","provinces":"辽宁省-大连市-中山区","address":"青泥洼桥","defaultFlag":"0","createdBy":null,"createdTime":null,"updatedBy":null,"updatedTime":null,"delFlag":"0"}
         */

        private String diagnosisId;
        private String status;
        private String diagnosis;
        private String patientUserId;
        private String doctorUserId;
        private int diagnosisTypeId;
        private Object remarks;
        private Object age;
        private Object mobile;
        private String isAllergy;
        private String allergyHistory;
        private String isDisease;
        private String diseaseHistory;
        private String familyMemberId;
        private String idCardNumber;
        private String userName;
        private String contractStatus;
        private Object patientProfilePhoto;
        private String recipeReferencePay;
        private String medicalCertificate;
        private String recipeId;
        private Object pharmacyId;
        private String pharmacyDrugPrice;
        private PharmacyBean pharmacy;
        private DrugOrderBean drugOrder;
        private String drugOrderStatus;
        private Object expiredDate;
        private String endAmount;
        private DeliveryAddressBean deliveryAddress;
        private List<?> imagesList;
        private List<RecipeDetailsListBean> recipeDetailsList;
        private List<PharmacyDrugListBean> pharmacyDrugList;

        public String getDiagnosisId() {
            return diagnosisId;
        }

        public void setDiagnosisId(String diagnosisId) {
            this.diagnosisId = diagnosisId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDiagnosis() {
            return diagnosis;
        }

        public void setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
        }

        public String getPatientUserId() {
            return patientUserId;
        }

        public void setPatientUserId(String patientUserId) {
            this.patientUserId = patientUserId;
        }

        public String getDoctorUserId() {
            return doctorUserId;
        }

        public void setDoctorUserId(String doctorUserId) {
            this.doctorUserId = doctorUserId;
        }

        public int getDiagnosisTypeId() {
            return diagnosisTypeId;
        }

        public void setDiagnosisTypeId(int diagnosisTypeId) {
            this.diagnosisTypeId = diagnosisTypeId;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public String getIsAllergy() {
            return isAllergy;
        }

        public void setIsAllergy(String isAllergy) {
            this.isAllergy = isAllergy;
        }

        public String getAllergyHistory() {
            return allergyHistory;
        }

        public void setAllergyHistory(String allergyHistory) {
            this.allergyHistory = allergyHistory;
        }

        public String getIsDisease() {
            return isDisease;
        }

        public void setIsDisease(String isDisease) {
            this.isDisease = isDisease;
        }

        public String getDiseaseHistory() {
            return diseaseHistory;
        }

        public void setDiseaseHistory(String diseaseHistory) {
            this.diseaseHistory = diseaseHistory;
        }

        public String getFamilyMemberId() {
            return familyMemberId;
        }

        public void setFamilyMemberId(String familyMemberId) {
            this.familyMemberId = familyMemberId;
        }

        public String getIdCardNumber() {
            return idCardNumber;
        }

        public void setIdCardNumber(String idCardNumber) {
            this.idCardNumber = idCardNumber;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContractStatus() {
            return contractStatus;
        }

        public void setContractStatus(String contractStatus) {
            this.contractStatus = contractStatus;
        }

        public Object getPatientProfilePhoto() {
            return patientProfilePhoto;
        }

        public void setPatientProfilePhoto(Object patientProfilePhoto) {
            this.patientProfilePhoto = patientProfilePhoto;
        }

        public String getRecipeReferencePay() {
            return recipeReferencePay;
        }

        public void setRecipeReferencePay(String recipeReferencePay) {
            this.recipeReferencePay = recipeReferencePay;
        }

        public String getMedicalCertificate() {
            return medicalCertificate;
        }

        public void setMedicalCertificate(String medicalCertificate) {
            this.medicalCertificate = medicalCertificate;
        }

        public String getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(String recipeId) {
            this.recipeId = recipeId;
        }

        public Object getPharmacyId() {
            return pharmacyId;
        }

        public void setPharmacyId(Object pharmacyId) {
            this.pharmacyId = pharmacyId;
        }

        public String getPharmacyDrugPrice() {
            return pharmacyDrugPrice;
        }

        public void setPharmacyDrugPrice(String pharmacyDrugPrice) {
            this.pharmacyDrugPrice = pharmacyDrugPrice;
        }

        public PharmacyBean getPharmacy() {
            return pharmacy;
        }

        public void setPharmacy(PharmacyBean pharmacy) {
            this.pharmacy = pharmacy;
        }

        public DrugOrderBean getDrugOrder() {
            return drugOrder;
        }

        public void setDrugOrder(DrugOrderBean drugOrder) {
            this.drugOrder = drugOrder;
        }

        public String getDrugOrderStatus() {
            return drugOrderStatus;
        }

        public void setDrugOrderStatus(String drugOrderStatus) {
            this.drugOrderStatus = drugOrderStatus;
        }

        public Object getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(Object expiredDate) {
            this.expiredDate = expiredDate;
        }

        public String getEndAmount() {
            return endAmount;
        }

        public void setEndAmount(String endAmount) {
            this.endAmount = endAmount;
        }

        public DeliveryAddressBean getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(DeliveryAddressBean deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public List<?> getImagesList() {
            return imagesList;
        }

        public void setImagesList(List<?> imagesList) {
            this.imagesList = imagesList;
        }

        public List<RecipeDetailsListBean> getRecipeDetailsList() {
            return recipeDetailsList;
        }

        public void setRecipeDetailsList(List<RecipeDetailsListBean> recipeDetailsList) {
            this.recipeDetailsList = recipeDetailsList;
        }

        public List<PharmacyDrugListBean> getPharmacyDrugList() {
            return pharmacyDrugList;
        }

        public void setPharmacyDrugList(List<PharmacyDrugListBean> pharmacyDrugList) {
            this.pharmacyDrugList = pharmacyDrugList;
        }

        public static class PharmacyBean {
            /**
             * pharmacyId : 2
             * pharmacyName : 成大方圆星海店
             * img : http://47.114.120.127:8000/icon-pharmacy.png
             * phone : 0411-53752131
             * address : 大连市沙河口中山路464号B1
             * hasAliapy : N
             * mchidWxpay : null
             * hasWxpay : N
             * mchidAlipay : null
             * longitude : null
             * dimension : null
             * accountName : 刘雷刚
             * bankCode : 1004
             * bankAbbr : null
             * bankName : 浦发银行
             * bankAccount : 6217931678620438
             * createdBy : null
             * createdTime : 2020-06-20 11:13:59
             * updatedBy : null
             * updatedTime : 2020-06-22 16:27:14
             * delFlag : 0
             */

            private int pharmacyId;
            private String pharmacyName;
            private String img;
            private String phone;
            private String address;
            private String hasAliapy;
            private Object mchidWxpay;
            private String hasWxpay;
            private Object mchidAlipay;
            private Object longitude;
            private Object dimension;
            private String accountName;
            private String bankCode;
            private Object bankAbbr;
            private String bankName;
            private String bankAccount;
            private Object createdBy;
            private String createdTime;
            private Object updatedBy;
            private String updatedTime;
            private String delFlag;

            public int getPharmacyId() {
                return pharmacyId;
            }

            public void setPharmacyId(int pharmacyId) {
                this.pharmacyId = pharmacyId;
            }

            public String getPharmacyName() {
                return pharmacyName;
            }

            public void setPharmacyName(String pharmacyName) {
                this.pharmacyName = pharmacyName;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getHasAliapy() {
                return hasAliapy;
            }

            public void setHasAliapy(String hasAliapy) {
                this.hasAliapy = hasAliapy;
            }

            public Object getMchidWxpay() {
                return mchidWxpay;
            }

            public void setMchidWxpay(Object mchidWxpay) {
                this.mchidWxpay = mchidWxpay;
            }

            public String getHasWxpay() {
                return hasWxpay;
            }

            public void setHasWxpay(String hasWxpay) {
                this.hasWxpay = hasWxpay;
            }

            public Object getMchidAlipay() {
                return mchidAlipay;
            }

            public void setMchidAlipay(Object mchidAlipay) {
                this.mchidAlipay = mchidAlipay;
            }

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public Object getDimension() {
                return dimension;
            }

            public void setDimension(Object dimension) {
                this.dimension = dimension;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String bankCode) {
                this.bankCode = bankCode;
            }

            public Object getBankAbbr() {
                return bankAbbr;
            }

            public void setBankAbbr(Object bankAbbr) {
                this.bankAbbr = bankAbbr;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankAccount() {
                return bankAccount;
            }

            public void setBankAccount(String bankAccount) {
                this.bankAccount = bankAccount;
            }

            public Object getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(Object createdBy) {
                this.createdBy = createdBy;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public Object getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(Object updatedBy) {
                this.updatedBy = updatedBy;
            }

            public String getUpdatedTime() {
                return updatedTime;
            }

            public void setUpdatedTime(String updatedTime) {
                this.updatedTime = updatedTime;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }
        }

        public static class DrugOrderBean {
            /**
             * drugOrderId : 12
             * orderId : 1273788279743492097
             * patientUserId : 3
             * recipeId : 10
             * pharmacyId : 2
             * status : 0
             * takeWay : 2
             * takeCode : null
             * deliveryAddressId : 57
             * expressName : null
             * expressNumber : null
             * rejectReason : null
             * deliveryTime : null
             * pickupTime : null
             * createdBy : null
             * createdTime : 2020-06-19 09:23:11
             * updatedBy : null
             * updatedTime : 2020-06-19 09:23:11
             * delFlag : 0
             * takeAwayName : 快递包邮
             * payTime : 2020-06-19 09:23:11
             * payChannel : ALIPAY
             */

            private String drugOrderId;
            private long orderId;
            private String patientUserId;
            private int recipeId;
            private int pharmacyId;
            private int status;
            private int takeWay;
            private String takeCode;
            private int deliveryAddressId;
            private String expressName;
            private String expressNumber;
            private String rejectReason;
            private Object deliveryTime;
            private Object pickupTime;
            private Object createdBy;
            private String createdTime;
            private Object updatedBy;
            private String updatedTime;
            private String delFlag;
            private String takeAwayName;
            private String payTime;
            private String payChannel;

            public String getDrugOrderId() {
                return drugOrderId;
            }

            public void setDrugOrderId(String drugOrderId) {
                this.drugOrderId = drugOrderId;
            }

            public long getOrderId() {
                return orderId;
            }

            public void setOrderId(long orderId) {
                this.orderId = orderId;
            }

            public String getPatientUserId() {
                return patientUserId;
            }

            public void setPatientUserId(String patientUserId) {
                this.patientUserId = patientUserId;
            }

            public int getRecipeId() {
                return recipeId;
            }

            public void setRecipeId(int recipeId) {
                this.recipeId = recipeId;
            }

            public int getPharmacyId() {
                return pharmacyId;
            }

            public void setPharmacyId(int pharmacyId) {
                this.pharmacyId = pharmacyId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getTakeWay() {
                return takeWay;
            }

            public void setTakeWay(int takeWay) {
                this.takeWay = takeWay;
            }

            public String getTakeCode() {
                return takeCode;
            }

            public void setTakeCode(String takeCode) {
                this.takeCode = takeCode;
            }

            public int getDeliveryAddressId() {
                return deliveryAddressId;
            }

            public void setDeliveryAddressId(int deliveryAddressId) {
                this.deliveryAddressId = deliveryAddressId;
            }

            public String getExpressName() {
                return expressName;
            }

            public void setExpressName(String expressName) {
                this.expressName = expressName;
            }

            public String getExpressNumber() {
                return expressNumber;
            }

            public void setExpressNumber(String expressNumber) {
                this.expressNumber = expressNumber;
            }

            public String getRejectReason() {
                return rejectReason;
            }

            public void setRejectReason(String rejectReason) {
                this.rejectReason = rejectReason;
            }

            public Object getDeliveryTime() {
                return deliveryTime;
            }

            public void setDeliveryTime(Object deliveryTime) {
                this.deliveryTime = deliveryTime;
            }

            public Object getPickupTime() {
                return pickupTime;
            }

            public void setPickupTime(Object pickupTime) {
                this.pickupTime = pickupTime;
            }

            public Object getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(Object createdBy) {
                this.createdBy = createdBy;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public Object getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(Object updatedBy) {
                this.updatedBy = updatedBy;
            }

            public String getUpdatedTime() {
                return updatedTime;
            }

            public void setUpdatedTime(String updatedTime) {
                this.updatedTime = updatedTime;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getTakeAwayName() {
                return takeAwayName;
            }

            public void setTakeAwayName(String takeAwayName) {
                this.takeAwayName = takeAwayName;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public String getPayChannel() {
                return payChannel;
            }

            public void setPayChannel(String payChannel) {
                this.payChannel = payChannel;
            }
        }

        public static class DeliveryAddressBean {
            /**
             * id : 57
             * patientUserId : 1
             * name : 测试618
             * mobile : 13542344325
             * provinces : 辽宁省-大连市-中山区
             * address : 青泥洼桥
             * defaultFlag : 0
             * createdBy : null
             * createdTime : null
             * updatedBy : null
             * updatedTime : null
             * delFlag : 0
             */

            private int id;
            private String patientUserId;
            private String name;
            private String mobile;
            private String provinces;
            private String address;
            private String defaultFlag;
            private Object createdBy;
            private Object createdTime;
            private Object updatedBy;
            private Object updatedTime;
            private String delFlag;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPatientUserId() {
                return patientUserId;
            }

            public void setPatientUserId(String patientUserId) {
                this.patientUserId = patientUserId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvinces() {
                return provinces;
            }

            public void setProvinces(String provinces) {
                this.provinces = provinces;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getDefaultFlag() {
                return defaultFlag;
            }

            public void setDefaultFlag(String defaultFlag) {
                this.defaultFlag = defaultFlag;
            }

            public Object getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(Object createdBy) {
                this.createdBy = createdBy;
            }

            public Object getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(Object createdTime) {
                this.createdTime = createdTime;
            }

            public Object getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(Object updatedBy) {
                this.updatedBy = updatedBy;
            }

            public Object getUpdatedTime() {
                return updatedTime;
            }

            public void setUpdatedTime(Object updatedTime) {
                this.updatedTime = updatedTime;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }
        }

        public static class RecipeDetailsListBean {
            /**
             * id : 122
             * recipeId : 10
             * drugId : 1424
             * count : 13
             * units : 瓶
             * use : 空腹口服
             * useFlag : 0
             * dose : 一天一次好几次
             * doseFlag : 1
             * eachDose : null
             * eachDoseFlag : null
             * amount : null
             * treatment : null
             * additionalInfo : 123
             * createdBy : null
             * createdTime : 2020-06-19 22:42:38
             * updatedBy : null
             * updatedTime : 2020-06-19 22:42:38
             * delFlag : 0
             * drugName : 杞菊地黄口服液
             * factoryName : 山东沃华医药科技股份有限公司
             * specification : 每支10ml
             * drugPrice : 676.00
             * usage : 口服，一次4片，一日3次，疗程7天
             * indicationsRange : 宣肺泄热，化痰止咳
             */

            private int id;
            private int recipeId;
            private int drugId;
            private int count;
            private String units;
            private String use;
            private int useFlag;
            private String dose;
            private int doseFlag;
            private Object eachDose;
            private Object eachDoseFlag;
            private Object amount;
            private Object treatment;
            private String additionalInfo;
            private Object createdBy;
            private String createdTime;
            private Object updatedBy;
            private String updatedTime;
            private String delFlag;
            private String drugName;
            private String factoryName;
            private String specification;
            private String drugPrice;
            private String usage;
            private String indicationsRange;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getRecipeId() {
                return recipeId;
            }

            public void setRecipeId(int recipeId) {
                this.recipeId = recipeId;
            }

            public int getDrugId() {
                return drugId;
            }

            public void setDrugId(int drugId) {
                this.drugId = drugId;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getUnits() {
                return units;
            }

            public void setUnits(String units) {
                this.units = units;
            }

            public String getUse() {
                return use;
            }

            public void setUse(String use) {
                this.use = use;
            }

            public int getUseFlag() {
                return useFlag;
            }

            public void setUseFlag(int useFlag) {
                this.useFlag = useFlag;
            }

            public String getDose() {
                return dose;
            }

            public void setDose(String dose) {
                this.dose = dose;
            }

            public int getDoseFlag() {
                return doseFlag;
            }

            public void setDoseFlag(int doseFlag) {
                this.doseFlag = doseFlag;
            }

            public Object getEachDose() {
                return eachDose;
            }

            public void setEachDose(Object eachDose) {
                this.eachDose = eachDose;
            }

            public Object getEachDoseFlag() {
                return eachDoseFlag;
            }

            public void setEachDoseFlag(Object eachDoseFlag) {
                this.eachDoseFlag = eachDoseFlag;
            }

            public Object getAmount() {
                return amount;
            }

            public void setAmount(Object amount) {
                this.amount = amount;
            }

            public Object getTreatment() {
                return treatment;
            }

            public void setTreatment(Object treatment) {
                this.treatment = treatment;
            }

            public String getAdditionalInfo() {
                return additionalInfo;
            }

            public void setAdditionalInfo(String additionalInfo) {
                this.additionalInfo = additionalInfo;
            }

            public Object getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(Object createdBy) {
                this.createdBy = createdBy;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public Object getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(Object updatedBy) {
                this.updatedBy = updatedBy;
            }

            public String getUpdatedTime() {
                return updatedTime;
            }

            public void setUpdatedTime(String updatedTime) {
                this.updatedTime = updatedTime;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getDrugName() {
                return drugName;
            }

            public void setDrugName(String drugName) {
                this.drugName = drugName;
            }

            public String getFactoryName() {
                return factoryName;
            }

            public void setFactoryName(String factoryName) {
                this.factoryName = factoryName;
            }

            public String getSpecification() {
                return specification;
            }

            public void setSpecification(String specification) {
                this.specification = specification;
            }

            public String getDrugPrice() {
                return drugPrice;
            }

            public void setDrugPrice(String drugPrice) {
                this.drugPrice = drugPrice;
            }

            public String getUsage() {
                return usage;
            }

            public void setUsage(String usage) {
                this.usage = usage;
            }

            public String getIndicationsRange() {
                return indicationsRange;
            }

            public void setIndicationsRange(String indicationsRange) {
                this.indicationsRange = indicationsRange;
            }
        }

        public static class PharmacyDrugListBean {
            /**
             * drugID : 1424
             * drugName : 杞菊地黄口服液
             * factoryName : 山东沃华医药科技股份有限公司
             * unit : 瓶
             * count : 13
             * specification : 每支10ml
             * price : 52
             */

            private int drugID;
            private String drugName;
            private String factoryName;
            private String unit;
            private int count;
            private String specification;
            private int price;

            public int getDrugID() {
                return drugID;
            }

            public void setDrugID(int drugID) {
                this.drugID = drugID;
            }

            public String getDrugName() {
                return drugName;
            }

            public void setDrugName(String drugName) {
                this.drugName = drugName;
            }

            public String getFactoryName() {
                return factoryName;
            }

            public void setFactoryName(String factoryName) {
                this.factoryName = factoryName;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getSpecification() {
                return specification;
            }

            public void setSpecification(String specification) {
                this.specification = specification;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
