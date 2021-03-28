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

    //在线问诊-医生列表
    public static final String GET_DOCTOR_LIST = BASEURL + "treat/doctor/getDoctorList";

    //在线问诊-筛选条件查询
    public static final String DOCTOR_SEARCH = BASEURL + "treat/onlineDiagnosis/doctorSearch";

    //关注医生 收藏
    public static final String SAVE_FAVORITE = BASEURL + "treat/doctor/saveFavorite";

    //医生详情 （包含评价）
    public static final String GET_DOCTOR_INFO = BASEURL + "treat/doctor/getDoctorInfo";

    //患者列表
    public static final String PATIENT_LIST = BASEURL + "treat/patient/list";

    //患者身份枚举
    public static final String KINSFOLK_LIST = BASEURL + "treat/patient/kinsfolkList";

    //添加患者
    public static final String PATIENT_ADD = BASEURL + "treat/patient/add";

    //更新患者
    public static final String PATIENT_UPDATE = BASEURL + "treat/patient/update";

    //删除患者
    public static final String PATIENT_DEL = BASEURL + "treat/patient/del";

    //地区接口
    public static final String DISTRICT_LIST = BASEURL + "treat/district/list";

    //地址列表
    public static final String ADDRESS_LIST = BASEURL + "treat/address/list";

    //添加地址
    public static final String ADDRESS_ADD = BASEURL + "treat/address/add";

    //更新地址
    public static final String ADDRESS_UPDATE = BASEURL + "treat/address/update";

    //删除地址
    public static final String ADDRESS_DEL = BASEURL + "treat/address/del";

    //查询医生支持哪种支付方式
    public static final String GET_DOCTOR_PAY_CHANNEL = BASEURL + "treat/doctor/getDoctorPayChannel";

    //下单检查
    public static final String CHECK_IN_PROGRESS_DIAGNOSIS = BASEURL + "treat/order/checkInProgressDiagnosis";

    //获取新OrderID
    public static final String GET_NEW_ORDERID = BASEURL + "treat/order/getNewOrderId";

    //下单接口支付接口
    public static final String DIAGNOSIS = BASEURL + "treat/order/diagnosis/";

    //我的问诊-问诊订单
    public static final String MY_ORDER = BASEURL + "treat/patient/myOrder";

    //注册发送验证码
    public static final String SEND_CODE = BASEURL + "treat/rongCloud/sendCode";

    //校验验证码
    public static final String VERIFY_CODE = BASEURL + "treat/rongCloud/verifyCode";

    //用户注册
    public static final String PATIENT_REGISTER = BASEURL + "admin/access/patientRegister";

    //推荐手机号是否注册
    public static final String REFERRER_CHECK = BASEURL + "admin/access/referrerCheck";

    //获取协议、说明等
    public static final String GET_CONFIG = BASEURL + "admin/access/getConfig";

    //获取用户信息
    public static final String USER_INFO = BASEURL + "admin/user/info";

    //患者-我的
    public static final String MY_OWN = BASEURL + "treat/patient/myOwn";

    //我的收藏（医生）
    public static final String MY_FAVORITE = BASEURL + "treat/patient/myFavorite";

    //是否开方
    public static final String NEED_RECIPE = BASEURL + "treat/patient/needRecipe";

    //查询当前问诊的详情
    public static final String GET_ALL_DIAGNOSIS_DETAIL = BASEURL + "treat/diagnosis/getAllDiagnosisDetil";

    //问诊信息 ()
    public static final String GET_DIAGNOSIS_ORDER_INFO = BASEURL + "treat/doctor/getDiagnosisOrderInfo";

    //可选药房列表
    public static final String CHOOSE_PHARMACY = BASEURL + "treat/patient/choosePharmacy";

    //药品列表（购药-2）
    public static final String PHARMACY_DRUG = BASEURL + "treat/patient/pharmacyDrug";

    //买药下单
    public static final String BUG_MEDICINE = BASEURL + "treat/order/buyMedicine";

    //获取购药列表
    public static final String GET_PAY_DRUG_LIST = BASEURL + "treat/patient/getPayDrugList";

    //更新药品订单状态 状态：0-已提交(待支付)、1-已支付(待发货)、2-已发货(待收货)、3-已收货(待评价)、4-已完成(已评价)、5-已取消  6-用户拒收
    public static final String UPDATE_DRUG_ORDER_STATUS = BASEURL + "treat/drug/updateDrugOrderStatus";

    //更新处方状态接口
    public static final String UPDATE_RECIPE_STATUS = BASEURL + "treat/recipe/updateRecipeStatus";

    //获取签约费用明细
    public static final String GET_CONTRACT_PREMIUM_TRIAL = BASEURL + "treat/contract/getContractPremiumTrial";

    //患者发起签约
    public static final String CONTRACT_SIGN = BASEURL + "treat/contract/sign";

    //签约订单
    public static final String CONTRACT_ORDER = BASEURL + "treat/patient/contractOrder";


    //获取签约详情
    public static final String GET_CONTRACT_DETAILS = BASEURL + "treat/contract/getContractDetails";


    //签约付款
    public static final String PAY_CONTRACT = BASEURL + "treat/order/payContract";

    //修改签约订单状态
    public static final String UPDATE_CONTRACT_STATUS = BASEURL + "treat/contract/updateContractStatus";

    //我的-签约医生
    public static final String MY_CONTRACT_DOCTOR = BASEURL + "treat/patient/myContractDoctor";

}
