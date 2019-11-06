package com.baizhi.service.impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("chapterService")
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAll(Integer page, Integer rows, String albumId) {
        HashMap<String, Object> map = new HashMap<>();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chapter> list = chapterDao.selectByRowBounds(chapter, rowBounds);
        int count = chapterDao.selectCount(chapter);
        map.put("page", page);
        map.put("rows", list);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    public String add(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreateDate(new Date());
        int i = chapterDao.insertSelective(chapter);
        if (i == 0) throw new RuntimeException("添加失败");
        return chapter.getId();
    }

    public void edit(Chapter chapter) {
        int i = chapterDao.updateByPrimaryKeySelective(chapter);
        if (i == 0) throw new RuntimeException("修改章节失败");
    }

}
