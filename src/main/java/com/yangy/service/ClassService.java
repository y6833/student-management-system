package com.yangy.service;

import java.util.List;

public interface ClassService {

    String getClassName(String id);

    List<String> getClassList();

    List<String> getGradeList();

    String getIdByclassName(String value);

    List<String> getClassListBygradeId(String gradeId);

    List<String> getMajorByclassId(String classId);

    List<String> getGradeByclassId(String classId);
}
