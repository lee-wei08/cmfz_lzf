package com.baizhi.controller;

import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("star")
public class StarController {
    @Autowired
    private StarService starService;

    @RequestMapping("findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = starService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String, Object> edit(String oper, Star star) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(oper);
            if ("add".equals(oper)) {
                System.out.println(star);
                String id = starService.add(star);
                map.put("message", id);
            }
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile photo, String id, HttpServletRequest request) {
        System.out.println("11111111111111");
        System.out.println(id);
        System.out.println(photo);
        Map<String, Object> map = new HashMap<>();
        try {
//          文件上传
            photo.transferTo(new File(request.getServletContext().getRealPath("/back/img"), photo.getOriginalFilename()));
//          修改banner对象中cover属性
            Star star = new Star();
            star.setId(id);
            star.setPhoto(photo.getOriginalFilename());
            starService.edit(star);
            map.put("status", true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status", false);
        }
        return map;
    }


    //查询所有明星
    @RequestMapping("All")
    public void selectAll(HttpServletResponse response) throws IOException {
        // select option  html
        List<Star> stars = starService.selectAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<select>");
        stars.forEach(star -> {
            sb.append("<option value=").append(star.getId()).append(">").append(star.getNickname()).append("</option>");
        });
        sb.append("</select>");
        //获取响应流
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(sb.toString());

    }
}
