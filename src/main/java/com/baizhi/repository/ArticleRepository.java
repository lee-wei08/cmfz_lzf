package com.baizhi.repository;

import com.baizhi.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

// 第一个参数是你创建es的type 名字
// 第二个是你Article对象主键的类型
public interface ArticleRepository extends ElasticsearchCrudRepository<Article, String> {
}
