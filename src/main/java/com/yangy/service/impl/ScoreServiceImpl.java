package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Examination;
import com.yangy.entity.Score;
import com.yangy.mapper.ScoreMapper;
import com.yangy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
    @Resource
    private ScoreMapper scoreMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private StudentService studentService;

    @Override
    public List<Score> findByStudentId(String studentId) {
        return scoreMapper.findByStudentId(studentId);
    }

    @Override
    public List<Map<String, Double>> findScoreByStuId(String id) {
        List<Score> studentScoreList = scoreMapper.findByStudentId(id);
        List<Map<String, Double>> StudentScoremaps = new ArrayList<Map<String, Double>>();
        for (Score score : studentScoreList) {
            Map<String, Double> studentScore = new HashMap<String, Double>();
            studentScore.put(score.getCourseId(), score.getScore());
            StudentScoremaps.add(studentScore);
        }
        return StudentScoremaps;
    }

    @Override
    public List<Examination> getExamTimes() {
        return scoreMapper.getExamTimes();
    }

    @Override
    public Map<String, Double> findScoreByStuIdAndExamDate(String id, Date examDate) {
        List<Score> studentScoreList = scoreMapper.findScoreByStuIdAndExamDate(id,examDate);
        Map<String, Double> studentScore = new HashMap<String, Double>();
        Double sum = 0.0;
        for (Score score : studentScoreList) {
            studentScore.put(courseService.getCourseNameById(score.getCourseId()), score.getScore());
            sum+=score.getScore();
        }
        studentScore.put("sum",sum);//总分
//        studentScore.put("average", sum / studentScoreList.size()); //班级平均分
//        studentScore.put("average", sum / studentScoreList.size()); //年级平均分
        return studentScore;
    }

    @Override
    public Integer getClassRanking(String id, String classId, Date examDate) {
        return 1;
    }

    @Override
    public Integer getGradeRanking(String id, String grade, String major, Date examDate) {
        return 1;
    }

    @Override
    public boolean updataScore(String id, String courseName, Double scores,Date examDate) {
        String courseId = courseService.getCourseIdByName(courseName);
        return scoreMapper.updataScore(id,courseId,scores,examDate);
    }

    @Override
    public List<String> getExamList() {
        return scoreMapper.getExamList();
    }

    @Override
    public boolean addStudentScore(Score score1) {
        try {
            return scoreMapper.addStudentScore(score1);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteStuScore(String studentId, Date examData) {
        try {
            return scoreMapper.deleteStuScore(studentId, examData);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Score> findScoreStuByStuIdAndExamDate(String studentId, Date examDate) {
        return scoreMapper.findScoreByStuIdAndExamDate(studentId,examDate);
    }

    @Override
    public boolean deleteById(Integer id) {
        return scoreMapper.deleteByScoreId(id);
    }




}
