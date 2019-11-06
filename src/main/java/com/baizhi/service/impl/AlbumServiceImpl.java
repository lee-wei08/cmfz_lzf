package com.baizhi.service.impl;

import com.baizhi.annotaion.ClearRedisCache;
import com.baizhi.annotaion.RedisCache;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("albumService")
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private StarDao starDao;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    @RedisCache
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Album> list = albumDao.selectByRowBounds(album, rowBounds);
        for (Album album1 : list) {
            Star star = new Star();
            star.setId(album1.getStarId());
            Star star1 = starDao.selectOne(star);
            album1.setStar(star1);
        }
        int count = albumDao.selectCount(album);
        map.put("page", page);// 当前页数
        map.put("rows", list);// 当前页的内容
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);// 总计有几页
        map.put("records", count);// 总共有多少条数据
        return map;
    }

    @Override
    @ClearRedisCache
    public String add(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCreateDate(new Date());
        album.setCount(0);
        int i = albumDao.insertSelective(album);
        if (i == 0) throw new RuntimeException("添加专辑失败");
        return album.getId();
    }

    @Override
    @ClearRedisCache
    public void edit(Album album) {
        if ("".equals(album.getCover())) {
            album.setCover(null);
        }
        try {
            albumDao.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public Album selectId(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }
}
