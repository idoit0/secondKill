package com.imooc.miaosha.utils;

public class ValidatorUtil {
    /**
     * 校验手机号
     *
     * @param phone
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String phone) {
        if(phone.length()!=11){
            return false;
        }
        return true;
    }
}
