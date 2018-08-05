package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from sys_user where id = #{id}")
    public User getUserById(@Param("id") int id);

    @Select("select * from sys_user where account = #{account}")
    public User getUserByAccount(@Param("account") String account);
}
