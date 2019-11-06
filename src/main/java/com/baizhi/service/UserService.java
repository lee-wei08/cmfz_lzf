package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String, Object> findAll(Integer page, Integer rows, String starId);

    Map<String, Object> selectAll(Integer page, Integer rows);

    public void edit(User user);

    public List<User> queryAll();

    public Map<String, List<Integer>> selectReg();
}
