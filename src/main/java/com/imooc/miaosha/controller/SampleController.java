package com.imooc.miaosha.controller;

import com.imooc.common.CodeMsg;
import com.imooc.common.Result;
import com.imooc.miaosha.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    RedisService redisService;

    @RequestMapping("thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","zhc");
        System.out.println("访问到了。。。。。。。");
        return "hello";
    }

    //restAPI Json输出接口
    @RequestMapping("testSuccessJson")
    @ResponseBody()
    public Result testSuccessJson(){
        Set<String> set = new HashSet<String>();
        set.add("张浩纯");
        set.add("25岁");
        set.add("未婚");
        return Result.success(set);
    }
    @RequestMapping("testErrorJson")
    @ResponseBody()
    public Result testErrorJson(){
        return Result.error(new CodeMsg(500,"服务器异常"));
    }
    @RequestMapping("testRedisGet")
    @ResponseBody
    public Result testRedisGet(){
        String userName = redisService.get("userName",String.class);
        return Result.success(userName);
    }

    @RequestMapping("testRedis")
    @ResponseBody
    public Result testRedis(){
        redisService.set("userName","张浩纯");
        String userName = redisService.get("userName",String.class);
        return Result.success(userName);
    }

}