package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Tclass;
import com.yangy.mapper.ClassMapper;
import com.yangy.service.ClassService;
import com.yangy.service.MajorService;
import com.yangy.service.TeacherService;
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
    @Autowired
    TeacherService teacherService;

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

    @Override
    public IPage<Tclass> getPage(IPage<Tclass> page, QueryWrapper<Tclass> queryWrapper) {

        List<Tclass> classList = page(page, queryWrapper).getRecords();

        for (Tclass tclass : classList) {
            tclass.setMajorId(majorService.getMajorName(tclass.getMajorId()));
//            tclass.setHeadTeacherId(teacherService.getNameById(tclass.getHeadTeacherId()));
        }
        IPage<Tclass> studentIPage = page(page, queryWrapper).setRecords(classList);
        return studentIPage;
    }

    @Override
    public boolean addClass(Tclass tclass) {
        tclass.setMajorId(majorService.getIdByclassName(tclass.getMajorId()));
        int insert = classMapper.insert(tclass);
        if (insert >0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updataClass(Tclass tclass) {
        tclass.setMajorId(majorService.getIdByclassName(tclass.getMajorId()));
        boolean b = saveOrUpdate(tclass);
        return b;
    }

    @Override
    public boolean removeclassById(String id) {
        return classMapper.removeclassById(id);
    }

    @Override
    public List<Tclass> findAllOver() {
        List<Tclass> classlist = list();
        for (Tclass tclass : classlist) {
            tclass.setMajorId(majorService.getMajorName(tclass.getMajorId()));
            tclass.setHeadTeacherId(teacherService.getNameById(tclass.getHeadTeacherId()));
        }
        return classlist;
    }

    @Override
    public void saveClassList(List<Tclass> list) {
        for (Tclass tclass : list) {
            tclass.setMajorId(majorService.getIdByclassName(tclass.getMajorId()));
            tclass.setHeadTeacherId(teacherService.getIdByName(tclass.getHeadTeacherId()));
            Tclass tclazz = new Tclass(tclass.getClassId(),tclass.getClassName(),tclass.getGradeId(),tclass.getMajorId(),tclass.getHeadTeacherId());
        }
        saveBatch(list);
    }

    @Override
    public List<String> getClassIdList() {
        return classMapper.getClassIdList();
    }

    @Override
    public String getGradeIdByclassId(String classId) {
        return classMapper.getGradeIdByclassId(classId);
    }


    @Override
    public String getMajorIdByclassId(String classId) {
        return classMapper.getMajorIdByclassId(classId);
    }

    @Override
    public List<String> getClassIdListBygradeId(String gradeValue) {
        return classMapper.getClassIdListBygradeId(gradeValue);
    }

    @Override
    public List<String> getClassIdListBygradeIdAndMajorId(String gradeValue, String getmajorByName) {
        return classMapper.getClassIdListBygradeIdAndMajorId(gradeValue,getmajorByName);
    }

    @Override
    public String getMajorIdByclassName(String name) {
        return classMapper.getMajorIdByclassName(name);
    }

}
