package com.imooc.common;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功时调用
     * data:成功时返回的数据
     * @return
     */
    public static <T>Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 初始化调用成功的构造函数
     * @param data
     */
    private Result(T data){
        this.code = 1;
        this.msg = "success";
        this.data = data;
    }

    /**
     * 失败时调用
     * codeMsg
     * code:错误编码
     * msg:错误信息
     * @param codeMsg
     * @return
     */
    public static Result error(CodeMsg codeMsg){
        return new Result(codeMsg);
    }

    /**
     * 初始化调用失败的构造函数
     * @return
     */
    private Result(CodeMsg codeMsg){
        if(codeMsg == null)  return;
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
