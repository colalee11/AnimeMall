package com.dlsd.property.db;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {
    private String phone;
    private String pwd;

    private String nickName;
    private String realName;
    private String sex;
    private String headUrl;
    private String mood;
    private GoodsType recentType;//最近看的类型   只能推荐计算用

    //0 管理员  1用户
    private int role;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public GoodsType getRecentType() {
        return recentType;
    }

    public void setRecentType(GoodsType recentType) {
        this.recentType = recentType;
    }
}
