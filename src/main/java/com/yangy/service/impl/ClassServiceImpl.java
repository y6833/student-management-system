package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Tclass;
import com.yangy.mapper.ClassMapper;
import com.yangy.service.ClassService;
import com.yangy.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Tclass> implements ClassService {

    @Resource
    ClassMapper classMapper;
    @Autowired
    MajorService majorService;

    @Override
    public String getClassName(String id) {
        return classMapper.getClassName(id);
    }

    @Override
    public List<String> getClassList() {
        return classMapper.getClassList();
    }

    @Override
    public List<String> getGradeList() {
        return classMapper.getGradeList();
    }

    @Override
    public String getIdByclassName(String value) {
        return classMapper.getIdByclassName(value);
    }

    @Override
    public List<String> getClassListBygradeId(String gradeId) {
        return classMapper.getClassListBygradeId(gradeId);
    }

    @Override
    public List<String> getMajorByclassId(String classId) {
        //传过来的是班级名例如计科2002
        //通过getIdByclassName方法将计科2002转换成jk2002
        classId = classMapper.getIdByclassName(classId);
        //在通过getMajorByclassId方法将jk2002转换成jk001
        String majorid = classMapper.getMajorByclassId(classId);
        //再通过getMajorName方法将jk001转成计算机科学与技术
        ArrayList<String> strings = new ArrayList<>();
        strings.add(majorService.getMajorName(majorid));
        return strings;
    }

    @Override
    public List<String> getGradeByclassId(String classId) {
        classId = classMapper.getIdByclassName(classId);
        return classMapper.getGradeByclassId(classId);
    }

}
