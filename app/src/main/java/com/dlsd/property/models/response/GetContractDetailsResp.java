package com.dlsd.property.models.response;

import java.io.Serializable;
import java.util.ArrayList;

public class GetContractDetailsResp {

    /**
     * code : 0
     * data : {"contractId":8,"contractStatus":1,"doctorUserId":"1261464286164377602","contractCostVO":{"totalAmount":1620,"contractBurningTime":"3个月","serveBenefitList":[{"id":32,"serveTitle":"服务权益","serveDetails":"服务权益"}]},"contractServeTimeVO":{"startDate":"2020-07-19 00:00:00","endDate":"2020-10-19 00:00:00","signServeTime":"3个月","cancellationTime":null,"actualServeTime":null},"contractInfoVO":{"contractId":8,"orderCreatedTime":null,"confirmTime":null,"payTime":null,"payChannel":null,"cancellationTime":null}}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * contractId : 8
         * contractStatus : 1
         * doctorUserId : 1261464286164377602
         * contractCostVO : {"totalAmount":1620,"contractBurningTime":"3个月","serveBenefitList":[{"id":32,"serveTitle":"服务权益","serveDetails":"服务权益"}]}
         * contractServeTimeVO : {"startDate":"2020-07-19 00:00:00","endDate":"2020-10-19 00:00:00","signServeTime":"3个月","cancellationTime":null,"actualServeTime":null}
         * contractInfoVO : {"contractId":8,"orderCreatedTime":null,"confirmTime":null,"payTime":null,"payChannel":null,"cancellationTime":null}
         */

        private String contractId;
        private int contractStatus;
        private String doctorUserId;
        private ContractCostVOBean contractCostVO;
        private ContractServeTimeVOBean contractServeTimeVO;
        private ContractInfoVOBean contractInfoVO;

        public String getContractId() {
            return contractId;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public int getContractStatus() {
            return contractStatus;
        }

        public void setContractStatus(int contractStatus) {
            this.contractStatus = contractStatus;
        }

        public String getDoctorUserId() {
            return doctorUserId;
        }

        public void setDoctorUserId(String doctorUserId) {
            this.doctorUserId = doctorUserId;
        }

        public ContractCostVOBean getContractCostVO() {
            return contractCostVO;
        }

        public void setContractCostVO(ContractCostVOBean contractCostVO) {
            this.contractCostVO = contractCostVO;
        }

        public ContractServeTimeVOBean getContractServeTimeVO() {
            return contractServeTimeVO;
        }

        public void setContractServeTimeVO(ContractServeTimeVOBean contractServeTimeVO) {
            this.contractServeTimeVO = contractServeTimeVO;
        }

        public ContractInfoVOBean getContractInfoVO() {
            return contractInfoVO;
        }

        public void setContractInfoVO(ContractInfoVOBean contractInfoVO) {
            this.contractInfoVO = contractInfoVO;
        }

        public static class ContractCostVOBean implements Serializable {
            /**
             * totalAmount : 1620
             * contractBurningTime : 3个月
             * serveBenefitList : [{"id":32,"serveTitle":"服务权益","serveDetails":"服务权益"}]
             */

            private String totalAmount;
            private String contractBurningTime;
            private ArrayList<ServeBenefitListBean> serveBenefitList;

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getContractBurningTime() {
                return contractBurningTime;
            }

            public void setContractBurningTime(String contractBurningTime) {
                this.contractBurningTime = contractBurningTime;
            }

            public ArrayList<ServeBenefitListBean> getServeBenefitList() {
                return serveBenefitList;
            }

            public void setServeBenefitList(ArrayList<ServeBenefitListBean> serveBenefitList) {
                this.serveBenefitList = serveBenefitList;
            }

            public static class ServeBenefitListBean implements Serializable {
                /**
                 * id : 32
                 * serveTitle : 服务权益
                 * serveDetails : 服务权益
                 */

                private int id;
                private String serveTitle;
                private String serveDetails;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getServeTitle() {
                    return serveTitle;
                }

                public void setServeTitle(String serveTitle) {
                    this.serveTitle = serveTitle;
                }

                public String getServeDetails() {
                    return serveDetails;
                }

                public void setServeDetails(String serveDetails) {
                    this.serveDetails = serveDetails;
                }
            }
        }

        public static class ContractServeTimeVOBean implements Serializable {
            /**
             * startDate : 2020-07-19 00:00:00
             * endDate : 2020-10-19 00:00:00
             * signServeTime : 3个月
             * cancellationTime : null
             * actualServeTime : null
             */

            private String startDate;
            private String endDate;
            private String signServeTime;
            private String cancellationTime;
            private String actualServeTime;

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getSignServeTime() {
                return signServeTime;
            }

            public void setSignServeTime(String signServeTime) {
                this.signServeTime = signServeTime;
            }

            public String getCancellationTime() {
                return cancellationTime;
            }

            public void setCancellationTime(String cancellationTime) {
                this.cancellationTime = cancellationTime;
            }

            public String getActualServeTime() {
                return actualServeTime;
            }

            public void setActualServeTime(String actualServeTime) {
                this.actualServeTime = actualServeTime;
            }
        }

        public static class ContractInfoVOBean implements Serializable {
            /**
             * contractId : 8
             * orderCreatedTime : null
             * confirmTime : null
             * payTime : null
             * payChannel : null
             * cancellationTime : null
             */

            private int contractId;
            private String orderCreatedTime;
            private String confirmTime;
            private String payTime;
            private String payChannel;
            private String cancellationTime;

            public int getContractId() {
                return contractId;
            }

            public void setContractId(int contractId) {
                this.contractId = contractId;
            }

            public String getOrderCreatedTime() {
                return orderCreatedTime;
            }

            public void setOrderCreatedTime(String orderCreatedTime) {
                this.orderCreatedTime = orderCreatedTime;
            }

            public String getConfirmTime() {
                return confirmTime;
            }

            public void setConfirmTime(String confirmTime) {
                this.confirmTime = confirmTime;
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

            public String getCancellationTime() {
                return cancellationTime;
            }

            public void setCancellationTime(String cancellationTime) {
                this.cancellationTime = cancellationTime;
            }
        }
    }
}
