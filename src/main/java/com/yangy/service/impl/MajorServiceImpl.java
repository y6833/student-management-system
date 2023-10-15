package com.yangy.service.impl;

import com.yangy.mapper.MajorMapper;
import com.yangy.entity.Major;
import com.yangy.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorMapper majorMapper; // mapper对象注入

    @Override
    public void addMajor(Major major) { // 添加专业
        majorMapper.insert(major);
    }

    @Override
    public void deleteMajorById(String id) { // 根据ID删除专业
        majorMapper.deleteById(id);
    }

    @Override
    public void updateMajorById(Major major) { // 根据ID更新专业
        majorMapper.updateById(major);
    }

    @Override
    public Major getMajorById(String id) { // 根据ID获取专业信息
        return majorMapper.selectById(id);
    }

    @Override
    public List<Major> getAllMajors() { // 获取所有专业信息
        return majorMapper.selectAll();
    }

    @Override
    public String getMajorName(String major) {
        return majorMapper.getMajorName(major);
    }
}
