package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Major;

import java.util.List;

public interface MajorService {

    String getMajorName(String major);

    List<String> getMajorList();

    String getIdByclassName(String value);

    IPage<Major> getPage(IPage<Major> page, QueryWrapper<Major> queryWrapper);

    boolean updatamajor(Major major);

    boolean removeById(String id);

    boolean saveMajor(Major major);

    String getmajorByName(String majorValue);
}
