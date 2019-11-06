package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface BannerService {
    public Map<String, Object> findAll(Integer page, Integer rows);

    public String add(Banner banner);

    public void edit(Banner banner);

    public void delete(String id, HttpServletRequest request);
}
