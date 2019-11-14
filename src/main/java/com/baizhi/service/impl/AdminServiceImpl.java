package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service("adminService") // 工厂创建
@Transactional
public class AdminServiceImpl implements AdminService {
    // 依赖dao

    @Autowired
    private AdminDao adminDao;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void longin(Admin admin, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");
        if (!securityCode.equals(code)) throw new RuntimeException("验证码错误");
        Admin admin1 = adminDao.selectOne(admin);
        if (admin1 == null) throw new RuntimeException("用户名或密码错误");
        session.setAttribute("admin", admin1);
    }

    @Override
    public void add(Admin admin, HttpServletRequest request, String code) {
        HttpSession session = request.getSession();
        // 取出短信验证码
        String note = (String) session.getAttribute("note");
        // 取出获取验证码的手机号
        String phone = (String) session.getAttribute("phone");
        if (!note.equals(code)) throw new RuntimeException("验证码不正确");
        if (!phone.equals(admin.getPhone())) throw new RuntimeException("所注册手机号不正确");
        // 生成id
        admin.setId(UUID.randomUUID().toString());
        // 密码 加密 散列
        Md5Hash md5Hash = new Md5Hash(admin.getPassword(), "abcd", 1024);
        // 把 Md5对象转换为 16进制数
        String password = md5Hash.toHex();
        // 密码
        admin.setPassword(password);
        // 盐
        admin.setSalt("abcd");
        adminDao.insertSelective(admin);
    }
}
