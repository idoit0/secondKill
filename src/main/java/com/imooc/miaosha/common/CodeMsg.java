package com.imooc.miaosha.common;

import java.util.Date;

public class CodeMsg {
    private int code;
    private String msg;

    /**
     * code:错误编码
     * msg:错误信息
     * @param code
     * @param msg
     */
    public CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    /**
     * 登录模块
     */
    //通用错误码
    public static CodeMsg BIND_ERROR = new CodeMsg(100001,"绑定异常:%s");
    //失败
    public static CodeMsg ACCOUNT_EMPTY = new CodeMsg(500001,"账号不存在");
    public static CodeMsg PASSWORD_EROOR = new CodeMsg(500002,"密码错误");
    public static CodeMsg SERVICE_EROOR = new CodeMsg(500500,"服务器异常");
    //成功
    public static CodeMsg LOGIN_SUCCESS = new CodeMsg(500100,"登录成功");

    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code,message);
    }
}
