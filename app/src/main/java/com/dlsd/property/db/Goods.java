package com.dlsd.property.db;

import cn.bmob.v3.BmobObject;

public class Goods extends BmobObject {
    private String goodsName;
    private Factory goodsFactory;
    private String goodsIntroduction;
    private float goodsPrice;
    private int goodsCount;//库存量
    private String goodsUrl;
    private GoodsType goodsType;//商品类别
    private int GoodsSeeCount;//浏览量

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Factory getGoodsFactory() {
        return goodsFactory;
    }

    public void setGoodsFactory(Factory goodsFactory) {
        this.goodsFactory = goodsFactory;
    }

    public String getGoodsIntroduction() {
        return goodsIntroduction;
    }

    public void setGoodsIntroduction(String goodsIntroduction) {
        this.goodsIntroduction = goodsIntroduction;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public int getGoodsSeeCount() {
        return GoodsSeeCount;
    }

    public void setGoodsSeeCount(int goodsSeeCount) {
        GoodsSeeCount = goodsSeeCount;
    }
}
