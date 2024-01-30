package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Examination;
import com.yangy.mapper.ExaminationMapper;
import com.yangy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExaminationServiceImpl extends ServiceImpl<ExaminationMapper, Examination> implements ExaminationService {

    @Resource
    ExaminationMapper examinationMapper;

    @Autowired
    CourseService courseService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private StudentService studentService;


    @Override
    public Date getDateByName(String examName) {
        return examinationMapper.getDateByName(examName);
    }

    @Override
    public IPage<Examination> getPage(IPage<Examination> page, QueryWrapper<Examination> queryWrapper) {

        List<Examination> examinationList = page(page, queryWrapper).getRecords();

        for (Examination examination : examinationList) {
            examination.setExamMajor(majorService.getMajorName(examination.getExamMajor()));
        }
        IPage<Examination> examinationIPage = page(page, queryWrapper).setRecords(examinationList);
        return examinationIPage;
    }

    @Override
    public boolean addExamination(Examination examination) {
        try {
            boolean save = save(examination);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean createScoreTable(Examination examination, List<String> courses) {
        String tablename = "ts_score_"+examination.getId();
        String courseStr = "";
        for (String cours : courses) {
                String courseId = courseService.getCourseIdByName(cours);
            if(courseService.getTypeById(courseId) == 1){
                courseStr+= courseId + " double DEFAULT '0',";
            }

        }
        String sql = "CREATE TABLE " + tablename +"("
//                +"id int NOT NULL AUTO_INCREMENT,"
                +"score_id varchar(255) NOT NULL,"
                +"exam_id varchar(255) NOT NULL,"
                +"student_id varchar(255) NOT NULL,"
                +"student_name varchar(255) NOT NULL,"
                +"student_class varchar(255) NOT NULL,"
                +courseStr
                +"sum double DEFAULT '0',"
                +"classRanking int DEFAULT '0',"  //班级排名
                +"gradeRanking int DEFAULT '0'," //年级排名
                +"proposal varchar(255),"//教师建议
                +"PRIMARY KEY (score_id)"
                +")";
        try {
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        int i = examinationMapper.deleteById(id);
        if (i > 0){
         return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteTable(String id) {
        String sql = "DROP TABLE ts_score_" + id;
        try {
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Examination> getAll() {
        return examinationMapper.getAll();
    }

    @Override
    public Date getDateByExamId(String id) {
        return examinationMapper.getDateByExamId(id);
    }

    @Override
    public String getDateNameByExamId(String id) {
        return examinationMapper.getDateNameByExamId(id);
    }

    @Override
    public String getIdByNameAndDate(String examName, java.util.Date examDate) {
        return examinationMapper.getIdByNameAndDate(examName, examDate);
    }

    @Override
    public String getIdByExamName(String examName) {
        return examinationMapper.getIdByExamName(examName);
    }

    @Override
    public Examination getExamById(String examId) {
        return examinationMapper.getExamById(examId);
    }

    @Override
    public String getIdByExamNameAndGrade(String examValue, String gradeValue) {
        return examinationMapper.getIdByExamNameAndGrade(examValue,gradeValue);
    }

    @Override
    public String getIdByExamNameAndGradeAndMajorId(String examValue, String gradeValue,String major) {
        return examinationMapper.getIdByExamNameAndGradeAndMajorId(examValue,gradeValue,major);
    }

    @Override
    public List<String> getSubjectListByExamNameAndGradeAndMajor(String examValue, String gradeValue, String majorValue) {
        //根据考试名称，考试年级，考试专业得到考试id
        String examId = "";
        ArrayList<String> subjects = new ArrayList<>();
        if("".equals(majorValue)){
            examId = examinationMapper.getIdByExamNameAndGrade(examValue, gradeValue);
        }else{
            examId = examinationMapper.getIdByExamNameAndGradeAndMajorId(examValue, gradeValue,majorService.getmajorByName(majorValue));
        }
        //通过考试id获取科目列表
        if(examId == null){
            return null;
        }
        String tableName = "ts_score_"+examId;
        List<String> subjectListByTableName = scoreService.getSubjectListByTableName(tableName);
        for (String subject : subjectListByTableName) {
            subjects.add(courseService.getCourseNameById(subject));
        }
        return subjects;
    }

    @Override
    public List<String> getExamListks() {
        return examinationMapper.getExamListks();
    }

    @Override
    public List<String> getExamListksByGradeIdAndMajorId(String gradeId, String majorId) {
        return examinationMapper.etExamListksByGradeIdAndMajorId(gradeId, majorId);
    }

    @Override
    public List<String> getEXamNameListByScheduleName(String scheduleName) {
        return examinationMapper.getEXamNameListByScheduleName(scheduleName);
    }

//    通过考试名称获得考试和学生id获得考试id
    @Override
    public String getIdByExamNameAndStudentId(String examValue, String studentId) {

        //通过学生id获得年级，专业
        String grade = studentService.getGradeById(studentId);
        String major = studentService.getMajorById(studentId);
        String examId = examinationMapper.getIdByExamNameAndGradeAndMajorId(examValue, grade, major);


        return examId;
    }

    @Override
    public String getScheduleNameByExamName(String examValue) {
        return examinationMapper.getScheduleNameByExamName(examValue);
    }


    @Override
    public boolean updataExam(Examination examination) {
        return examinationMapper.updataExam(examination);
    }

    @Override
    public List<Examination> findAllOver() {
        List<Examination> list = list();
        for (Examination examination : list) {
            examination.setExamMajor(majorService.getMajorName(examination.getExamMajor()));
        }
        return list;
    }

    @Override
    public void saveExaminationList(List<Examination> list) {
        for (Examination examination : list) {
            examination.setExamMajor(majorService.getIdByclassName(examination.getExamMajor()));
        }

        saveBatch(list);
    }


}
