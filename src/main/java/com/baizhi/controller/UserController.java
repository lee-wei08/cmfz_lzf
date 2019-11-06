package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows, String starId) {
        Map<String, Object> map = userService.findAll(page, rows, starId);
        return map;
    }

    @RequestMapping("selectAll")
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Map<String, Object> map = userService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile phone, String id, HttpServletRequest request) {
        System.out.println(phone);
        System.out.println(id);
        Map<String, Object> map = new HashMap<>();
        try {
            phone.transferTo(new File(request.getServletContext().getRealPath("/back/img"), phone.getOriginalFilename()));
            User user = new User();
            user.setId(id);
            user.setPhoto(phone.getOriginalFilename());
            userService.edit(user);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            e.printStackTrace();

        }
        return map;
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response) {

        // 准备数据
        List<User> users = userService.queryAll();
        for (User user : users) {
            user.setPhoto("D:/ideaWorkSpaces/cmfz_lzf/src/main/webapp/back/img/" + user.getPhoto());
        }
//        response.setHeader("content-disposition","attachment");
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "测试1", "测试2"),
                User.class, users);
        // 定义文件名
        String fileName = "用户报表(" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ").xls";
        try {
            // 处理中文下载名乱码
            fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");

            //设置 response
            response.setContentType("application/vnd.ms-excel");
            // 设置响应头
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            // 以响应流输出
            workbook.write(response.getOutputStream());
            // 关闭流
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @RequestMapping("reg")

    public Map<String, List<Integer>> selectReg() {
        Map<String, List<Integer>> map = userService.selectReg();
        return map;
    }
}
