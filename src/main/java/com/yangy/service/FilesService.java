package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Files;

import java.util.List;

public interface FilesService {
    void savefile(Files saveFile);

    List<Files> getList(QueryWrapper<Files> queryWrapper);

    Files findByURL(String fileURL);

    IPage<Files> findPage(Integer pageNum, Integer pageSize, String name,String type);

    Files findById(Integer id);

    boolean updateById_(Files files);

    List<Files> getlist(QueryWrapper<Files> queryWrapper);

    List<String> findFileTypeList();
}
