package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    public void longin(Admin admin, String code, HttpServletRequest request);

    public void add(Admin admin, HttpServletRequest request, String code);
}
