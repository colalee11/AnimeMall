package com.dlsd.property.db;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject {
    private User user;
    private ArrayList<OrderGoods> goodsList;
    private Address address;
    //0 待支付   1待收货  2已完成  3已取消   4已退货
    private int state;
    private float totleFee;
    //0 支付宝  1 微信
    private int payWay;

    private long createTime;
    private long payTime;
    private long finishTime;
    private long sendTime;



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<OrderGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(ArrayList<OrderGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getTotleFee() {
        return totleFee;
    }

    public void setTotleFee(float totleFee) {
        this.totleFee = totleFee;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
