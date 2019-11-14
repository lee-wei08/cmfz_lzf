package com.baizhi.service.impl;

import com.baizhi.annotaion.ClearRedisCache;
import com.baizhi.annotaion.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository;
import com.baizhi.service.ArticleService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


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
        articleRepository.save(article);
        if (i == 0) throw new RuntimeException("添加失败");
    }

    @Override
    @ClearRedisCache
    public void edit(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if (i == 0) {
            throw new RuntimeException("修改失败");
        } else {
            Article a = articleDao.selectByPrimaryKey(article.getId());
            articleRepository.save(a);
        }

    }

    @Override
    @ClearRedisCache
    public void del(String id) {
        int i = articleDao.deleteByPrimaryKey(id);
        if (i == 0) {
            throw new RuntimeException("删除失败");
        } else {
            articleRepository.deleteById(id);
        }
    }

    @Override
    public List<Article> search(String content) {
        if ("".equals(content)) {
            Iterable<Article> all = articleRepository.findAll();
            List<Article> list = IterableUtils.toList(all);
            return list;
        } else {
            HighlightBuilder highlightBuilder = new HighlightBuilder()
                    .field("*")
                    .preTags("<span color='red'>")
                    .postTags("</span>")
                    .requireFieldMatch(false);


            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("author").field("brief").field("content"))
                    .withSort(SortBuilders.scoreSort())
                    .withHighlightBuilder(highlightBuilder)
                    .build();
            AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(nativeSearchQuery, Article.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                    SearchHits searchHits = response.getHits();
                    SearchHit[] hits = searchHits.getHits();
                    List<Article> list = new ArrayList<>();
                    for (SearchHit hit : hits) {
                        Map<String, Object> map = hit.getSourceAsMap();
                        Article article = new Article();
                        article.setId(map.get("id").toString());
                        article.setTitle(map.get("title").toString());
                        article.setAuthor(map.get("author").toString());
                        article.setBrief(map.get("brief").toString());
                        article.setContent(map.get("content").toString());
                        String date = map.get("createDate").toString();
                        article.setCreateDate(new Date(Long.valueOf(date)));

                        Map<String, HighlightField> fieldMap = hit.getHighlightFields();
                        if (fieldMap.get("title") != null) {
                            System.out.println("fieldMap.get('title'):" + fieldMap.get("title"));
                            System.out.println(fieldMap.get("title").getFragments());
                            article.setTitle(fieldMap.get("title").getFragments()[0].toString());
                        }
                        if (fieldMap.get("author") != null) {
                            article.setAuthor(fieldMap.get("author").getFragments()[0].toString());
                        }
                        if (fieldMap.get("brief") != null) {
                            article.setBrief(fieldMap.get("brief").getFragments()[0].toString());
                        }
                        if (fieldMap.get("content") != null) {
                            article.setContent(fieldMap.get("content").getFragments()[0].toString());
                        }
                        list.add(article);
                    }
                    return new AggregatedPageImpl<T>((List<T>) list);
                }

                @Override
                public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                    return null;
                }

            });

            return articles.getContent();
        }
    }

}
