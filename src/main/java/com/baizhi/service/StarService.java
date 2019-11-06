package com.baizhi.service;

import com.baizhi.entity.Star;

import java.util.List;
import java.util.Map;

public interface StarService {
    public Map<String, Object> findAll(Integer page, Integer rows);

    public String add(Star star);

    public void edit(Star star);

    public List<Star> selectAll();
}
