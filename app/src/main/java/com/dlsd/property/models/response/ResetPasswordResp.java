package com.dlsd.property.models.response;

public class ResetPasswordResp {
    /**
     * code : 0
     * msg : null
     * data : null
     */

    private int code;
    private Object msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
