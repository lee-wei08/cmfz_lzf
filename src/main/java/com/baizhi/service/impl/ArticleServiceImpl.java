package com.baizhi.service.impl;

import com.baizhi.annotaion.ClearRedisCache;
import com.baizhi.annotaion.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @RedisCache
    public Map<String, Object> findeAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> list = articleDao.selectByRowBounds(article, rowBounds);
        int count = articleDao.selectCount(article);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);// 当前页数
        map.put("rows", list);// 当前页的内容
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);// 总计有几页
        map.put("records", count);// 总共有多少条数据
        return map;
    }

    @Override
    @ClearRedisCache
    public void add(Article article) {
        article.setCreateDate(new Date());
        article.setId(UUID.randomUUID().toString());
        int i = articleDao.insertSelective(article);
        if (i == 0) throw new RuntimeException("添加失败");
    }

    @Override
    @ClearRedisCache
    public void edit(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if (i == 0) throw new RuntimeException("修改失败");
    }

    @Override
    @ClearRedisCache
    public void del(String id) {
        int i = articleDao.deleteByPrimaryKey(id);
        if (i == 0) throw new RuntimeException("删除失败");
    }

}
