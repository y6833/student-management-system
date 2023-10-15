package com.yangy.service;

import com.yangy.entity.Major;

import java.util.List;

public interface MajorService {
    void addMajor(Major major); // 添加专业

    void deleteMajorById(String id); // 根据ID删除专业

    void updateMajorById(Major major); // 根据ID更新专业

    Major getMajorById(String id); // 根据ID获取专业信息

    List<Major> getAllMajors(); // 获取所有专业信息

    String getMajorName(String major);
}
