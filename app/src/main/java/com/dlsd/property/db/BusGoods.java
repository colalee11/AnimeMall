package com.dlsd.property.db;

import cn.bmob.v3.BmobObject;

public class BusGoods extends BmobObject {
    private int count;//添加数量
    private Goods goods;
    private User user;
    private boolean isCheck = false;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
