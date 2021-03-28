package com.dlsd.property.models.response;

import java.util.List;

public class GetPatientListResp {

    /**
     * code : 0
     * data : [{"relationId":1,"relationName":"本人","isAllergy":"0","allergyHistory":"花粉过敏","disease":"精神病历史","isDefault":"0"}]
     */

    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * relationId : 1
         * relationName : 本人
         * isAllergy : 0
         * allergyHistory : 花粉过敏
         * disease : 精神病历史
         * isDefault : 0
         */

        private int relationId;
        private String relationName;
        private String isAllergy;
        private String allergyHistory;
        private String disease;
        private String isDefault;

        public int getRelationId() {
            return relationId;
        }

        public void setRelationId(int relationId) {
            this.relationId = relationId;
        }

        public String getRelationName() {
            return relationName;
        }

        public void setRelationName(String relationName) {
            this.relationName = relationName;
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

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }
    }
}
