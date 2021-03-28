package com.dlsd.property.utils;

import android.content.Context;

import com.dlsd.property.MyApp;
import com.dlsd.property.db.User;


public class AppLoginUtil {

    private static final long THREE_DAYS = 3 * 24 * 60 * 60 * 1000;

    public static boolean hasLogin() {
        Context context = MyApp.getInstance();
        if (SPUtil.get(context, SPUtil.IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }


    public static User createLoginModel() {
        Context context = MyApp.getInstance();
        User loginModel = new User();
        loginModel.setPhone(SPUtil.get(context, SPUtil.MOBILE, ""));
        loginModel.setNickName(SPUtil.get(context, SPUtil.NICK_NAME, ""));
        loginModel.setPwd(SPUtil.get(context, SPUtil.PASSWORD, ""));
        loginModel.setRealName(SPUtil.get(context, SPUtil.USER_NAME, ""));
        loginModel.setMood(SPUtil.get(context, SPUtil.MOOD, ""));
        loginModel.setSex(SPUtil.get(context, SPUtil.SEX, ""));
        loginModel.setHeadUrl(SPUtil.get(context, SPUtil.HEAD_URL, ""));
        loginModel.setRole(SPUtil.get(context, SPUtil.ROLE, 1));
        loginModel.setObjectId(SPUtil.get(context, SPUtil.USER_OBJECT_ID, ""));
        return loginModel;
    }
}
