package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Tclass;

import java.util.List;

public interface ClassService {

    String getClassName(String id);

    List<String> getClassList();

    List<String> getGradeList();

    String getIdByclassName(String value);

    List<String> getClassListBygradeId(String gradeId);

    List<String> getMajorByclassId(String classId);

    List<String> getGradeByclassId(String classId);


    IPage<Tclass> getPage(IPage<Tclass> page, QueryWrapper<Tclass> queryWrapper);

    boolean addClass(Tclass tclass);

    boolean updataClass(Tclass tclass);

    boolean removeclassById(String id);

    List<Tclass> findAllOver();

    void saveClassList(List<Tclass> list);
}
