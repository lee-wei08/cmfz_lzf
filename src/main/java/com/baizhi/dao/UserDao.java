package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface UserDao extends Mapper<User> {
    Integer selectReg(@Param("sex") String sex, @Param("month") Integer month);

    List<User> select11();
}
