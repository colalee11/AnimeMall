package com.dlsd.property.constant;

/**
 * 描述：存放整个项目的Url，必须写注释
 */

public class UrlConfig {

    // 基础URL
    //public static final String BASEURL = "";

    public static final String BASEURL = "";
    /**
     * 分类
     */

    //登录获取token
    public static final String LOGIN_TOKEN = BASEURL + "auth/oauth/token";

    //重置密码
    public static final String RESET_PASSWORD = BASEURL + "admin/access/resetPassword";

    //文件上传
    public static final String UPLOAD_FILE_BY_BASE64 = BASEURL + "treat/file/uploadFileByBase64";

    //获取新OrderID
    public static final String GET_NEW_ORDERID = BASEURL + "treat/order/getNewOrderId";

    //下单接口支付接口
    public static final String DIAGNOSIS = BASEURL + "treat/order/diagnosis/";


    //注册发送验证码
    public static final String SEND_CODE = BASEURL + "treat/rongCloud/sendCode";

    //校验验证码
    public static final String VERIFY_CODE = BASEURL + "treat/rongCloud/verifyCode";

    //用户注册
    public static final String PATIENT_REGISTER = BASEURL + "admin/access/patientRegister";






}
