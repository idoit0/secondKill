package com.imooc.miaosha.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String SALT = "1a2b3c4d";

    /**
     * 从前端输入密码加密
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass){
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String input,String saltDB){
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        //把formPassWord转化成DBPassWord
        System.out.println(formPassToDBPass(inputPassToFormPass("123456"),"cdh1020 "));
        System.out.println(inputPassToDbPass("123456","cdh1020"));
    }
}
