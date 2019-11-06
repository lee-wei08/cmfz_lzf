package com.baizhi.service.impl;

import com.baizhi.annotaion.ClearRedisCache;
import com.baizhi.annotaion.RedisCache;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service("bannerService")
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @RedisCache
    public Map<String, Object> findAll(Integer page, Integer rows) {
        // 查询所有并分页
        Banner banner = new Banner();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Banner> list = bannerDao.selectByRowBounds(banner, rowBounds);
        Map<String, Object> map = new HashMap<>();
        int count = bannerDao.selectCount(banner);
        map.put("page", page);// 当前页数
        map.put("rows", list);// 当前页的内容
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);// 总计有几页
        map.put("records", count);// 总共有多少条数据
        return map;
    }

    @Override
    @ClearRedisCache
    public String add(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreate_date(new Date());
        int i = bannerDao.insertSelective(banner);
        if (i == 0) throw new RuntimeException("添加失败");
        return banner.getId();// 添加普通文本 返回ID 用作于文件上传的依据
    }

    @Override
    @ClearRedisCache
    public void edit(Banner banner) {
        if ("".equals(banner.getCover())) {
            banner.setCover(null);
        }
        try {
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    @ClearRedisCache
    public void delete(String id, HttpServletRequest request) {
        Banner banner = bannerDao.selectByPrimaryKey(id);
        int i = bannerDao.deleteByPrimaryKey(id);
        if (i == 0) {
            throw new RuntimeException("删除失败");
        } else {
            String cover = banner.getCover();
            File file = new File(request.getServletContext().getRealPath("/back/img"), cover);
            boolean b = file.delete();
            if (b == false) {
                throw new RuntimeException("删除轮播图文件失败");
            }
        }


    }
}
