package com.dlsd.property.utils;

import java.text.DecimalFormat;

public class PriceUtil {
    private static String getValueOfNumStr(String num) {
        if (null == num) {
            num = "0.00";
        }
        try {
            float f = Float.parseFloat(num);
            DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            return decimalFormat.format(f);
        } catch (Exception e) {
        }
        return "0.00";
    }
}
