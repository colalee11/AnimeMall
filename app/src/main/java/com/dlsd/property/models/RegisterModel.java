package com.dlsd.property.models;

import java.io.Serializable;

public class RegisterModel implements Serializable {
    private boolean isZk;
    private String phone = "";
    private String referrerPhone = "";
    private String password = "";

    public boolean isZk() {
        return isZk;
    }

    public void setZk(boolean zk) {
        isZk = zk;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferrerPhone() {
        return referrerPhone;
    }

    public void setReferrerPhone(String referrerPhone) {
        this.referrerPhone = referrerPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
