package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows, String starId) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        map.put("page", page);
        map.put("rows", list);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        map.put("page", page);
        map.put("rows", list);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    public void edit(User user) {
        int i = userDao.updateByPrimaryKeySelective(user);
        if (i == 0) throw new RuntimeException("修改失败");
    }

    @Override
    public List<User> queryAll() {
        return userDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, List<Integer>> selectReg() {
        HashMap<String, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            Integer count = userDao.selectReg("男", i);
            list.add(count);
        }
        map.put("man", list);
        List<Integer> list1 = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            Integer count = userDao.selectReg("女", i);
            list1.add(count);
        }
        map.put("nv", list1);

        return map;
    }
}
