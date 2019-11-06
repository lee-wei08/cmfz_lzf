package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = articleService.findeAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Article article, HttpServletRequest request, String id) {
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)) {
                articleService.add(article);
            }
            if ("upload".equals(oper)) {
                articleService.edit(article);
            }
            if ("del".equals(oper)) {
                articleService.del(id);
            }
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }
//    @RequestMapping("")
//    public Map<String,Object> upload(){
//
//    }

}
