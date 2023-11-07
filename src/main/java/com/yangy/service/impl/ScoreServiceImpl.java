package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Examination;
import com.yangy.entity.Score;
import com.yangy.entity.StudentScores;
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
        List<Score> studentScoreList = scoreMapper.findScoreByStuIdAndExamDate(id, examDate);
        Map<String, Double> studentScore = new HashMap<String, Double>();
//        Double sum = 0.0;
        for (Score score : studentScoreList) {
            studentScore.put(courseService.getCourseNameById(score.getCourseId()), score.getScore());
//            sum += score.getScore();
        }
//        studentScore.put("sum", sum);//总分
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
    public boolean updataScore(String id, String courseName, Double scores, Date examDate) {
        String courseId = courseService.getCourseIdByName(courseName);
        boolean b = scoreMapper.updataScore(id, courseId, scores, examDate);
        Score score = scoreMapper.getScoreByIdAndCourseAndExamDate(id, courseId, examDate);
        updataAver(score);
        return b;
    }

    @Override
    public List<String> getExamList() {
        return scoreMapper.getExamList();
    }

    public void updataAver(Score score1) {
        Double classsum = 0.0;
        Double gradeSum = 0.0;
        int classn = 0;//计数
        int graden = 0;
        //首先查找这个同学是哪个班的
        String classIdById = studentService.getClassIdById(score1.getStudentId());
        //查看这个学生是哪个年级，哪个专业的
        String gradeById = studentService.getGradeById(score1.getStudentId());
        String majorById = studentService.getMajorById(score1.getStudentId());

        //根据科目查看最大值
        Integer subjectMaxScore = courseService.getSubjectMaxScore(score1.getCourseId());
        //通过科目和日期获取成绩表中所有信息
        List<Score> studentScore = scoreMapper.getStudentScore(score1);
        for (Score score : studentScore) {
            //获取班级该科目总分
            if (studentService.getClassIdById(score.getStudentId()).equals(classIdById)) {
                classsum += score.getScore();
                gradeSum += score.getScore();
                classn++;
                graden++;
            } else if (studentService.getGradeById(score.getStudentId()).equals(gradeById) && studentService.getMajorById(score.getStudentId()).equals(majorById)) {
                gradeSum += score.getScore();
                graden++;
            }
        }
        for (Score score : studentScore) {
            if (studentService.getClassIdById(score.getStudentId()).equals(classIdById)) {
                //存入班级平均分
                scoreMapper.updataAverageScore(subjectMaxScore, classsum / classn, gradeSum / graden, score.getId());
            } else if (studentService.getGradeById(score.getStudentId()).equals(gradeById) && studentService.getMajorById(score.getStudentId()).equals(majorById)) {
                //存年级平均分
                scoreMapper.updataAverageGradeScore(subjectMaxScore, gradeSum / graden, score.getId());
            }
        }

    }

    @Override
    public boolean addStudentScore(Score score1) {
        try {
            boolean b = scoreMapper.addStudentScore(score1);
            updataAver(score1);
            return b;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteStuScore(String studentId, Date examData) {
        try {
            return scoreMapper.deleteStuScore(studentId, examData);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Score> findScoreStuByStuIdAndExamDate(String studentId, Date examDate) {
        return scoreMapper.findScoreByStuIdAndExamDate(studentId, examDate);
    }

    @Override
    public boolean deleteById(Integer id) {
        return scoreMapper.deleteByScoreId(id);
    }

    @Override
    public Double getClassAveByIdAndExamDate(String id, String object, Date examDate) {
        String courseId = courseService.getCourseIdByName(object);
        return scoreMapper.getClassAveByIdAndExamDate(id,courseId,examDate);
    }

    @Override
    public Double getGradeAveByIdAndExamDate(String id, String object, Date examDate) {
        String courseId = courseService.getCourseIdByName(object);
        return scoreMapper.getGradeAveByIdAndExamDate(id, courseId, examDate);
    }

    @Override
    public Integer getScoreTotal() {
        return scoreMapper.getTotalScore();
    }

    /**
     * 查询所有数据
     * @return
     */
    @Override
    public List<StudentScores> findAllOver() {
        ArrayList<StudentScores> studentScores = new ArrayList<>();
        List<Score> list = list();
        for (Score score : list) {
            StudentScores studentScore = new StudentScores();
            studentScore.setStudent(studentService.getStudentById(score.getStudentId()));
        }
        return studentScores;
    }


}
