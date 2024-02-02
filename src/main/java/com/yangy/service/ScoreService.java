package com.yangy.service;

import com.yangy.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScoreService {



    List<Score> findByStudentId(String studentId);

    List<Map<String, Double>> findScoreByStuId(String id);

    List<Examination> getExamTimes();

//获取成绩键值对
    Map<String, Double> findScoreByStuIdAndExamDate(String id, Date examDate);
//获取班级排名
    Integer getClassRanking(String id, String classId, Date examDate);
//获取年级排名，需要知道学生id，学生所在年级d,专业,已考试的时间 同年级不同专业无法排名
    Integer getGradeRanking(String id, String grade, String major, Date examDate);

    boolean updataScore(String id, String courseName, Double scores,Date examDate);

    List<String> getExamList();

    boolean addStudentScore(Score score1);

    boolean deleteStuScore(String studentId, Date examData);

    List<Score> findScoreStuByStuIdAndExamDate(String studentId, Date examData);

    boolean deleteById(Integer id);

    Double getClassAveByIdAndExamDate(String id, String object, Date examDate);

    Double getGradeAveByIdAndExamDate(String id, String object, Date examDate);

    Integer getScoreTotal();

    List<StudentScores> findAllOver();

    List<StudentScores> getAll();
    //通过成绩表名获取科目名称
    List<String > getSubjectListByTableName(String tableName);

    //通过学号筛选
    List<StudentScores> getstudentScoresListByStudentId(String studentId, List<StudentScores> studentScoresList);
    //通过姓名筛选
    List<StudentScores> getstudentScoresListByStudentName(String studentName, List<StudentScores> studentScoresList);
    //通过性别筛选
    List<StudentScores> getstudentScoresListByStudentGender(String gender, List<StudentScores> studentScoresList);
    //通过年级筛选
    List<StudentScores> getstudentScoresListByGrade(String grade, List<StudentScores> studentScoresList);
    //通过班级筛选
    List<StudentScores> getstudentScoresListByClassId(String classId, List<StudentScores> studentScoresList);
    //通过专业筛选
    List<StudentScores> getstudentScoresListByMajor(String major, List<StudentScores> studentScoresList);
    //通过考试日期筛选
    List<StudentScores> getstudentScoresListByExamDate(String examDate, List<StudentScores> studentScoresList);
    //通过考试名称筛选
    List<StudentScores> getstudentScoresListByExamName(String examName, List<StudentScores> studentScoresList);

    Double getGradeAveByScoreId(String tableName, String courseId);

    Double getClassAveByScoreId(String tableName, String classId, String courseId);

    boolean updataScore1(String tableName, String id, String courseName, Double scores);

    void setSum(String tableName, String id, Double sum);
    //创建一条学生成绩信息
    boolean createScore(String scoreId, String examId, String studentId, String studentName, String studentClass, String tableName);

    //添加成绩记录
    boolean addScore(String tableName, String scoreId, String key, double value);

    boolean deleteByScoreId(String tableName, String scoreId);

    List<StudentScores> getScoreMessageByTableName(Examination examById);

    boolean updataProposal(String tableName, String id, String proposal);

    List<AveScoreDTO> getAveTable(String examValue, String gradeValue,String majorValue, String choiceSubject);


    GradeNumDTO getGradeNum(String examValue, String gradeValue, String majorValue, String choiceSubject);

    List<Integer> getAbscissa(String examValue, String gradeValue, String majorId, String choiceSubject);

    List<Integer> getScoreListByExamAndGradeAndSubject(String examValue, String gradeValue, String majorValue, String choiceSubject);

    List<StudentScores> getstudentScoresListBySubject(String choiceSubject, List<StudentScores> studentScoresList);

    List<StudentScores> getstudentScoresListByRankingRange(int num1, int num2,String choiceSubject, List<StudentScores> studentScoresList);

    GradeNumDTO getClassNum(String examValue, String gradeValue, String majorValue, String classValue, String choiceSubject);

    Map<String,Double>  getExamClassAve(String gradeId, String majorName,String classId, List<String> examNameList, String choiceSubject);

    List<Integer> getScoreListByExamAndClassAndSubject(String examValue, String classValue, String choiceSubject);

    List<StudentScores> getstudentScoresListByClass(String classValue, List<StudentScores> studentScoresList);

    List<StudentScores> getRankingRangeNumByclassIdAndchoiceSubject(String examName, String rankingRange, String classValue, String choiceSubject);

    List<SubRankDTO> getInformationByexamIdAndStudentId(String examId, String studentValue);

    double getScoreByStudentIdAndCourseId(String tableName, String studentValue, String s);

    boolean addScoreInfoList(Examination examination);

    boolean updataExamRoom(String tableName, String id, String examRoom);

    void updataActive(String tableName, String id);

    List<StudentScores> getstudentScoresListByActive(List<StudentScores> studentScoresList);

    String getProposal(String tableName, String scoresId);
}
