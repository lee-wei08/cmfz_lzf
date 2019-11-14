package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //    @RequestMapping("login")
//    public Map<String, Object> login(Admin admin, String code, HttpServletRequest request) {
//        System.out.println("111111111111111111");
//        HashMap<String, Object> map = new HashMap<>();
//        try {
//            adminService.longin(admin, code, request);
//            map.put("status", true);
//        } catch (Exception e) {
//            map.put("status", false);
//            map.put("message", e.getMessage());
//        }
//        return map;
//    }
    @RequestMapping("login")
    public Map<String, Object> login(Admin admin, String code, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");

        if (!securityCode.equals(code)) {
            map.put("status", false);
            map.put("message", "验证码错误！");
        } else {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
            try {
                subject.login(token);
                System.out.println("login success");
                map.put("status", true);
            } catch (UnknownAccountException e) {
                System.out.println("username is error");
                map.put("status", false);
                map.put("message", "该用户不存在！");
            } catch (IncorrectCredentialsException e) {
                System.out.println("password is error");
                map.put("status", false);
                map.put("message", "输入密码错误！");
            }
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
