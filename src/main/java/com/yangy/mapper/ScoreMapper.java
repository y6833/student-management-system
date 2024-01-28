package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Examination;
import com.yangy.entity.Score;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Mapper
public interface ScoreMapper extends BaseMapper<Score> {



    @Select("SELECT * FROM tb_score WHERE student_id = #{studentId}")
    List<Score> findByStudentId(String studentId);

    @Select("select * from tb_examination")
    List<Examination> getExamTimes();

    @Select("SELECT * FROM tb_score WHERE student_id = #{id} AND exam_date=#{examDate}")
    List<Score> findScoreByStuIdAndExamDate(String id, Date examDate);

    @Select("SELECT RANK() OVER (PARTITION BY exam_date ORDER BY sum(score) DESC) AS rank FROM scores;")
    Integer getClassRanking(String id, String classId, Date examDate);

    @Update("update tb_score set score = #{scores} where student_id = #{id} AND course_id = #{courseid} AND exam_date = #{examDate}")
    boolean updataScore(String id, String courseid, Double scores,Date examDate);

    @Insert("insert into tb_score(student_id,course_id,score,exam_date) values(#{studentId},#{courseId},#{score},#{examDate})")
    boolean addStudentScore(Score score1);

    @Delete("delete from tb_score where student_id = #{studentId} AND exam_date = #{examDate}")
    boolean deleteStuScore(String studentId, Date examData);

    @Delete("delete from tb_score where id=#{id};")
    boolean deleteByScoreId(Integer id);


    @Select("select * from tb_score where course_id = #{courseId} AND exam_date = #{examDate}")
    List<Score> getStudentScore(Score score1);

    @Update("UPDATE tb_score SET score_max = #{subjectMaxScore}, average_class_score = #{clazz}, average_grade_score = #{grade} where id=#{id}")
    void updataAverageScore(Integer subjectMaxScore, double clazz, double grade,int id);

    @Update("UPDATE tb_score SET score_max = #{subjectMaxScore}, average_grade_score = #{grade} where id=#{id}")
    void updataAverageGradeScore(Integer subjectMaxScore,double grade,int id);

    @Select("select * from tb_score where student_id = #{id} AND course_id = #{courseId} AND exam_date = #{examDate}")
    Score getScoreByIdAndCourseAndExamDate(String id, String courseId, Date examDate);

    @Select("select average_class_score from tb_score where student_id = #{id} AND course_id = #{object} AND exam_date = #{examDate}")
    Double getClassAveByIdAndExamDate(String id, String object, Date examDate);

    @Select("select average_grade_score from tb_score where student_id = #{id} AND course_id = #{courseId} AND exam_date = #{examDate}")
    Double getGradeAveByIdAndExamDate(String id, String courseId, Date examDate);

    @Select("SELECT COUNT(DISTINCT student_id, exam_date) FROM tb_score")
    Integer getTotalScore();

    @Select("select score_id from ${tableName}")
    ArrayList<String> getExaminationScoreId(String tableName);

    @Select("select student_id from ${tableName} where score_id=#{scoreId}")
    String getStudentByScoreId(String tableName, String scoreId);

    @Select("select classRanking from ${tableName} where score_id=#{scoreId}")
    Integer getClassRankingByScoreId(String tableName, String scoreId);

    @Select("select gradeRanking from ${tableName} where score_id=#{scoreId}")
    Integer getGradeRankingByScoreId(String tableName, String scoreId);

    @Select("select ${s} from ${tableName} where score_id=#{scoreId}")
    Double getScoreByCourse(String s, String tableName, String scoreId);

    @Select("select ${courseId} from ${tableName}")
    List<Double> getObjectScoreList(String tableName, String courseId);

    @Select("select ${courseId} from ${tableName} where student_class=#{classId}")
    List<Double> getObjectByClassScoreList(String tableName,String classId, String courseId);

    @Update("update ${tableName} set ${courseName}=#{scores} where student_id=#{id}")
    boolean updataScore1(String tableName, String id, String courseName, Double scores);

    @Select("SELECT ${score} from ${tableName} where student_id=#{id}")
    Double getScoreByname(String tableName, String id, String score);

    @Update("update ${tableName} set sum=#{sum} where student_id=#{id}")
    void setSum(String tableName, String id, Double sum);

    //根据sum获取年级排名
//    @Select("SELECT RANK() OVER (ORDER BY sum DESC) AS gradeRanking FROM ${tableName} WHERE student_id = #{id}")
    @Select("SELECT gradeRanking FROM (SELECT student_id, student_class, sum, RANK() OVER (ORDER BY sum DESC) AS gradeRanking FROM ${tableName}) AS subquery  WHERE student_id = #{id}")
    Integer getGradeRankingByStudentId(String tableName, String id);
    //将年级排名存入
    @Update("update  ${tableName} set gradeRanking=#{gR} where student_id=#{id}")
    void setGradeRankingByStudentId(String tableName, String id, Integer gR);
    //根据sum获取班级排名
//    @Select("SELECT RANK() OVER (ORDER BY sum DESC) AS classRanking FROM ${tableName} WHERE student_id = #{id} AND student_class=#{classId}")
    @Select("SELECT classRanking FROM (SELECT student_id, student_class, sum, RANK() OVER (ORDER BY sum DESC) AS classRanking FROM ${tableName} where student_class=#{classId} ) AS subquery  WHERE student_id = #{id}")
    Integer getClassRankingByStudentId(String tableName, String id, String classId);

    @Update("update ${tableName} set classRanking=#{cR} where student_id = #{id} ")
    void setClassRankingByStudentId(String tableName, String id ,Integer cR);

    @Select("select student_id from ${tableName}")
    List<String> getStudentIdListByTableName(String tableName);

    @Insert("insert into ${tableName}( score_id, exam_id,student_id, student_name, student_class) values(#{scoreId} , #{examId}, #{studentId}, #{studentName}, #{studentClass})")
    boolean createScore(String scoreId, String examId, String studentId, String studentName, String studentClass, String tableName);

    @Update("update ${tableName} set ${key}=#{value} where score_id = #{scoreId}")
    boolean addScore(String tableName, String scoreId, String key, double value);

    @Delete("delete from ${tableName} where score_id=#{scoreId}")
    boolean deleteByScoreIdTS(String tableName, String scoreId);

    @Select("SELECT DISTINCT exam_name from ts_examination")
    List<String> getExamList();

    @Select("select proposal from ${tableName} where score_id = #{scoreId}")
    String getProposalByScoreId(String tableName, String scoreId);

    @Update("update ${tableName} set proposal=#{proposal} where score_id = #{scoreId}")
    boolean updataProposal(String tableName, String scoreId, String proposal);

    @Select("select AVG(${courseId}) from ${tableName} where student_class=#{classs}")
    Double getClassAveByobject(String tableName, String classs, String courseId);

    @Select("select AVG(${courseId}) from ${tableName}")
    Double getGradeAveByobject(String tableName, String courseId);

    @Select("select count(*) from ${tableName}")
    Integer getTableNum(String tableName);
    @Select("select max(${courseId}) from ${tableName}")
    Double getMaxScore(String tableName, String courseId);
    @Select("select min(${courseId}) from ${tableName}")
    Double getMinScore(String tableName, String courseId);
    @Select("select AVG(${courseId}) from ${tableName}")
    Double getAveScore(String tableName, String courseId);

    @Select("select ${courseId} from ${tableName}")
    List<Integer> getScoreCourseListByTableNameAndSubject(String tableName, String courseId);

    @Select("select score_id from ${tableName} ORDER BY #{courseId} DESC")
    List<String> getSubGradeRankingByScoreId(String tableName, String courseId);

    @Select("select score_id from ${tableName} where student_class =#{classId} ORDER BY #{courseId} DESC")
    List<String> getSubClassRankingByScoreId(String tableName, String courseId, String classId);
}
