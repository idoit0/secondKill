package com.imooc.common;

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
}
