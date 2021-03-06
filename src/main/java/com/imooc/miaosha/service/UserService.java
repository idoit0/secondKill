package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    UserDao userDao;
    public User getUserById(int id){
        return userDao.getUserById(id);
    }

    public User getUserByAccount(String account){
        return userDao.getUserByAccount(account);
    }
}
