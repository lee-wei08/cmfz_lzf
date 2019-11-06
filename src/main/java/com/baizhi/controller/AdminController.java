package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public Map<String, Object> login(Admin admin, String code, HttpServletRequest request) {
        System.out.println("111111111111111111");
        HashMap<String, Object> map = new HashMap<>();
        try {
            adminService.longin(admin, code, request);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("reg")
    public Map<String, Object> reg(Admin admin, HttpServletRequest request, String code) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            adminService.add(admin, request, code);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }
}
