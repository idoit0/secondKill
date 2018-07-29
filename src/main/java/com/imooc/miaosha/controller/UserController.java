package com.imooc.miaosha.controller;

import com.imooc.common.CodeMsg;
import com.imooc.common.Result;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("getUserById")
    @ResponseBody
    public Result getUserById(@Param("id") Integer id){
        if(id == null){
            return Result.error(new CodeMsg(0,"Id不能为空"));
        }
        User user = userService.getUserById(id);
        return Result.success(user);
    }

}
