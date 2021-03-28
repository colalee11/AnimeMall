package com.dlsd.property.db;

import cn.bmob.v3.BmobObject;

public class GoodsType extends BmobObject {
    private String typeName;
    private String typeId;
    private int goodsCount = 0;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }
}
