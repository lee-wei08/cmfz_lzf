package com.baizhi.service.impl;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("starService")
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Star star = new Star();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Star> list = starDao.selectByRowBounds(star, rowBounds);
        int count = starDao.selectCount(star);
        map.put("page", page);
        map.put("rows", list);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    public String add(Star star) {
        star.setId(UUID.randomUUID().toString());
        star.setBir(new Date());
        int i = starDao.insertSelective(star);
        if (i == 0) throw new RuntimeException("添加数据失败");
        return star.getId();
    }

    @Override
    public void edit(Star star) {
        if (" ".equals(star.getPhoto())) {
            star.setPhoto(null);
        }
        try {
            starDao.updateByPrimaryKeySelective(star);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Star> selectAll() {
        List<Star> list = starDao.selectAll();
        return list;
    }
}
