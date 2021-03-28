package com.dlsd.property.models.response;

import java.util.List;

public class GetConfigResp {

    /**
     * code : 0
     * msg : null
     * data : [{"id":16,"key":"RegisterAgreement","value":"https://passport.baidu.com/static/passpc-account/html/protocal.html","parentId":0,"name":"用户注册协议","comment":"要成为系统服务用户必须：\r\n\r\n(1)自行配备上网的所需设备， 包括个人电脑、调制解调器或其他必备上网装置。\r\n\r\n(2)自行负担个人上网所支付的与此服务有关的电话费用、 网络费用。\r\n\r\n为提高系统服务信息服务的准确性，用户应同意：\r\n\r\n(1)提供详尽、准确的公司或个人资料。\r\n\r\n(2)不断更新注册资料，符合及时、详尽、准确的要求。\r\n\r\n本服务不公开用户的电子邮箱和笔名， 除以下情况外：\r\n\r\n(1)用户授权透露这些信息。\r\n\r\n(2)相应的法律及程序要求提供用户的个人资料。\r\n\r\n如果用户提供的资料包含有不正确的信息，本服务系统保留结束用户使用网络服务资格的权利。","delFlag":"0","configs":[]}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 16
         * key : RegisterAgreement
         * value : https://passport.baidu.com/static/passpc-account/html/protocal.html
         * parentId : 0
         * name : 用户注册协议
         * comment : 要成为系统服务用户必须：

         (1)自行配备上网的所需设备， 包括个人电脑、调制解调器或其他必备上网装置。

         (2)自行负担个人上网所支付的与此服务有关的电话费用、 网络费用。

         为提高系统服务信息服务的准确性，用户应同意：

         (1)提供详尽、准确的公司或个人资料。

         (2)不断更新注册资料，符合及时、详尽、准确的要求。

         本服务不公开用户的电子邮箱和笔名， 除以下情况外：

         (1)用户授权透露这些信息。

         (2)相应的法律及程序要求提供用户的个人资料。

         如果用户提供的资料包含有不正确的信息，本服务系统保留结束用户使用网络服务资格的权利。
         * delFlag : 0
         * configs : []
         */

        private int id;
        private String key;
        private String value;
        private int parentId;
        private String name;
        private String comment;
        private String delFlag;
        private List<?> configs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public List<?> getConfigs() {
            return configs;
        }

        public void setConfigs(List<?> configs) {
            this.configs = configs;
        }
    }
}
