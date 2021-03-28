package com.dlsd.property.db;

import java.io.Serializable;

public class OrderGoods implements Serializable {
    private int count;//添加数量
    private Goods goods;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
