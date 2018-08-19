package com.imooc.miaosha.controller;

import com.imooc.miaosha.common.CodeMsg;
import com.imooc.miaosha.common.Result;
import com.imooc.miaosha.domain.MiaoShaUser;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.MiaoShaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    private static final String COOKIE_NAME_TOKEN = "token";
    @RequestMapping("getUserById")
    @ResponseBody
    public Result getUserById(@Param("id") Integer id){
        if(id == null){
            return Result.error(new CodeMsg(0,"Id不能为空"));
        }
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @RequestMapping("checkToken")
    @ResponseBody
    public Result checkToken(HttpServletResponse response, @CookieValue(value = COOKIE_NAME_TOKEN,required = false) String cookieToken, @RequestParam(value=COOKIE_NAME_TOKEN,required = false) String paramToken){
        if(StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)){
            return Result.error( CodeMsg.PARAMETER_ERROR);
        }
        String token = StringUtils.isBlank(paramToken)?cookieToken:paramToken;
        MiaoShaUser miaoShaUser = redisService.get(MiaoShaUserKey.token,token,MiaoShaUser.class);

        if(miaoShaUser != null){
            // 延长有效期，重新设置cookie
            Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
            cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
            cookie.setPath("/");
            response.addCookie(cookie);

            return Result.success(CodeMsg.LOGIN_SUCCESS);
        }
        return Result.error(CodeMsg.PARAMETER_ERROR);
    }

}
