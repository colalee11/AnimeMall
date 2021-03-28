package com.dlsd.property.db;

import cn.bmob.v3.BmobObject;

public class Factory extends BmobObject {
    private String fName;
    private String fAddress;
    private String fIntroduction;
    /**
     * 工商号
     */
    private String fCardNo;
    /**
     * 法人
     */
    private String fLegalPerson;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfAddress() {
        return fAddress;
    }

    public void setfAddress(String fAddress) {
        this.fAddress = fAddress;
    }

    public String getfIntroduction() {
        return fIntroduction;
    }

    public void setfIntroduction(String fIntroduction) {
        this.fIntroduction = fIntroduction;
    }

    public String getfCardNo() {
        return fCardNo;
    }

    public void setfCardNo(String fCardNo) {
        this.fCardNo = fCardNo;
    }

    public String getfLegalPerson() {
        return fLegalPerson;
    }

    public void setfLegalPerson(String fLegalPerson) {
        this.fLegalPerson = fLegalPerson;
    }
}
