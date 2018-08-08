package com.imooc.miaosha.controller;

import com.imooc.miaosha.common.CodeMsg;
import com.imooc.miaosha.common.Result;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.service.UserService;
import com.imooc.miaosha.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserService userService;
    @RequestMapping("checkMsg")
    public Result checkMsg(@Valid @RequestBody User userParam){

        //相关参数校验省略
        User user = userService.getUserByAccount(userParam.getAccount());
        if(user == null){
            return Result.error(CodeMsg.ACCOUNT_EMPTY);
        }
        //数据库存在的密码与盐
        String dbPassword = user.getPassword();
        String salt = user.getSalt();
        //前端一次md5加密加固定盐之后的密码
        String passwordParam = userParam.getPassword();
        //前端传递过来的密码，加上数据库的随机盐，再与已经保存的密码比较。如果是一致的，则证明密码正确。
        String password = MD5Util.formPassToDBPass(passwordParam,salt);
        if(!password.equals(dbPassword)){
            return Result.error(CodeMsg.PASSWORD_EROOR);
        }
        return Result.success(CodeMsg.LOGIN_SUCCESS);
    }
}
