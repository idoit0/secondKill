package com.imooc.miaosha.common.exception;

public class Constant {
    private Constant() throws IllegalAccessException {
        throw new IllegalAccessException("静态变量，不用创建对象");
    }
    private static final String COOKIE_NAME_TOKEN = "token";
}
