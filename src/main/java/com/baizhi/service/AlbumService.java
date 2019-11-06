package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    public Map<String, Object> findAll(Integer page, Integer rows);

    public String add(Album album);

    public void edit(Album album);

    public Album selectId(String id);
}
