package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.*;
import com.yangy.mapper.ScoreMapper;
import com.yangy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.Date;
import java.util.*;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
    @Resource
    private ScoreMapper scoreMapper;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private ExaminationService examinationService;

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
        return scoreMapper.getClassAveByIdAndExamDate(id, courseId, examDate);
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
     *
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

    /**
     * 获取成绩信息
     *
     * @return
     */
    @Override
    public List<StudentScores> getAll() {
        List<StudentScores> studentScoresList = new ArrayList<>();
        List<Examination> examinationList = examinationService.getAll();
        for (Examination examination : examinationList) {
            String tableName = "ts_score_" + examination.getId(); //获得成绩表名称
            List<String> tableFieldList = getTableFieldList(tableName); //通过成绩表名称获取表中字段
            //通过表名获取考试成绩id列表
            ArrayList<String> examinationScoreIdList = scoreMapper.getExaminationScoreId(tableName);
            //通过表名刷新排名
            RefreshResults(tableName);
            //通过获取的成绩id查询值
            for (String scoreId : examinationScoreIdList) {
                StudentScores studentScores = new StudentScores();
                //先通过表名和考试成绩id获得学生id，然后通过学生id查到学生信息，然后存入studentscore对象中
                studentScores.setStudent(studentService.getStudentByIdGM(scoreMapper.getStudentByScoreId(tableName, scoreId)));
                studentScores.setScoreId(scoreId);//获取成绩id
                studentScores.setClassRanking(scoreMapper.getClassRankingByScoreId(tableName, scoreId));//获取班级排名
                studentScores.setGradeRanking(scoreMapper.getGradeRankingByScoreId(tableName, scoreId));//获取年级排名
                studentScores.setExamDate(examinationService.getDateByExamId(examination.getId()));
                studentScores.setExamName(examinationService.getDateNameByExamId(examination.getId()));
                studentScores.setScores(getScoreMap(tableName, scoreId, tableFieldList));//需要表名称，考试成绩id， 表的字段名称
                studentScores.setProposal(scoreMapper.getProposalByScoreId(tableName, scoreId));
                studentScores.setType(examination.getScheduleName().equals("测试") ? 0 : 1);
                studentScores.setActive(scoreMapper.getExamActive(tableName, scoreId));
                studentScores.setExamRoom(scoreMapper.getExamRoom(tableName, scoreId));
                studentScoresList.add(studentScores);//将这条成绩添加到里面
            }

        }
        return studentScoresList;
    }

    //通过表名称获得这张成绩表的所有成绩信息
    @Override
    public List<StudentScores> getScoreMessageByTableName(Examination examination) {
        List<StudentScores> studentScoresList = new ArrayList<>();
        String tableName = "ts_score_" + examination.getId(); //获得成绩表名称
        List<String> tableFieldList = getTableFieldList(tableName); //通过成绩表名称获取表中字段
        //通过表名获取考试成绩id列表
        ArrayList<String> examinationScoreIdList = scoreMapper.getExaminationScoreId(tableName);
        //通过表名刷新排名
        RefreshResults(tableName);
        //通过获取的成绩id查询值
        for (String scoreId : examinationScoreIdList) {
            StudentScores studentScores = new StudentScores();
            //先通过表名和考试成绩id获得学生id，然后通过学生id查到学生信息，然后存入studentscore对象中
            studentScores.setStudent(studentService.getStudentByIdGM(scoreMapper.getStudentByScoreId(tableName, scoreId)));
            studentScores.setScoreId(scoreId);//获取成绩id
            studentScores.setClassRanking(scoreMapper.getClassRankingByScoreId(tableName, scoreId));//获取班级排名
            studentScores.setGradeRanking(scoreMapper.getGradeRankingByScoreId(tableName, scoreId));//获取年级排名
            studentScores.setExamDate(examinationService.getDateByExamId(examination.getId()));
            studentScores.setExamName(examinationService.getDateNameByExamId(examination.getId()));
            studentScores.setScores(getScoreMap(tableName, scoreId, tableFieldList));//需要表名称，考试成绩id， 表的字段名称
            studentScores.setProposal(scoreMapper.getProposalByScoreId(tableName, scoreId));
            studentScoresList.add(studentScores);//将这条成绩添加到里面
        }
        return studentScoresList;
    }

    @Override
    public boolean updataProposal(String tableName, String id, String proposal) {
        return scoreMapper.updataProposal(tableName, id, proposal);
    }

    @Override
    public List<AveScoreDTO> getAveTable(String examValue, String gradeValue, String majorValue, String choiceSubject) {
        AveScoreDTO aveScoreDTO;
        List<String> classList = new ArrayList<>();
        String courseId;
        //        通过考试名称获取考试id
        String examId = "";
        if (choiceSubject.equals("总分")) {
            courseId = "sum";
        } else {
            //获得科目id
            courseId = courseService.getCourseIdByName(choiceSubject);
        }
        if ("".equals(majorValue)) {
            classList = classService.getClassIdListBygradeId(gradeValue);
            examId = examinationService.getIdByExamNameAndGrade(examValue, gradeValue);

        } else {
            classList = classService.getClassIdListBygradeIdAndMajorId(gradeValue, majorService.getmajorByName(majorValue));
            examId = examinationService.getIdByExamNameAndGradeAndMajorId(examValue, gradeValue, majorService.getmajorByName(majorValue));


        }
        if (examId == null) {
            return null;
        }
        String tableName = "ts_score_" + examId; //获得成绩表名称

        List<AveScoreDTO> aveScoreDTOS = new ArrayList<>();
        for (String classs : classList) {
            Double gradeAveClass = scoreMapper.getClassAveByobject(tableName, classs, courseId);
            Double gradeAveGrade = scoreMapper.getGradeAveByobject(tableName, courseId);
            if (gradeAveClass != null) {
                aveScoreDTO = new AveScoreDTO();
                aveScoreDTO.setGrade(gradeValue);
                aveScoreDTO.setClasss(classService.getClassName(classs));
                aveScoreDTO.setExamName(examValue);
                aveScoreDTO.setAverageClass(Math.floor(gradeAveClass * 10) / 10);
                aveScoreDTO.setAverageGrade(Math.floor(gradeAveGrade * 10) / 10);
                aveScoreDTOS.add(aveScoreDTO);
            }

        }
        //通过年级
        return aveScoreDTOS;
    }

    @Override
    public GradeNumDTO getClassNum(String examValue, String gradeValue, String majorValue, String classValue, String choiceSubject) {

//获取科目id
        String courseId;
        if (choiceSubject.equals("总分")) {
            courseId = "sum";
        } else {
            //获得科目id
            courseId = courseService.getCourseIdByName(choiceSubject);

        }
        //获取考试id
        String examId = "";
        String classId = classService.getIdByclassName(classValue);
        if ("".equals(majorValue)) {
            examId = examinationService.getIdByExamNameAndGrade(examValue, gradeValue);
        } else {
            examId = examinationService.getIdByExamNameAndGradeAndMajorId(examValue, gradeValue, majorService.getmajorByName(majorValue));
        }
        if (examId == null) {
            return null;
        }
        String tableName = "ts_score_" + examId; //获得成绩表名称

        //通过表名获取这张表考试的人数
        Integer tableNum = scoreMapper.getTableNumByClassId(tableName, classId);
        Double maxScore = scoreMapper.getMaxScoreByClassId(tableName, courseId, classId);
        Double minScore = scoreMapper.getMinScoreByClassId(tableName, courseId, classId);
        Double aveScore = scoreMapper.getAveScoreByClassId(tableName, courseId, classId);

        GradeNumDTO gradeNumDTO = new GradeNumDTO(tableNum, maxScore, minScore, Math.floor(aveScore * 10) / 10);
//        gradeNumDTO.setGradePeople(tableNum);
//        //查看最高分通过科目
//        gradeNumDTO.setMaxScore(maxScore);
//        //查看最低分通过科目
//        gradeNumDTO.setMinScore(minScore);
//        //查看平均分通过科目
//        gradeNumDTO.setAveScore(aveScore);
        return gradeNumDTO;
    }

    @Override
    public Map<String, Double> getExamClassAve(String gradeId, String majorName, String classId, List<String> examNameList, String choiceSubject) {
        Map<String, Double> examClassAve = new HashMap<>();
        String courseId;
        if (choiceSubject.equals("总分")) {
            courseId = "sum";
        } else {
            //获得科目id
            courseId = courseService.getCourseIdByName(choiceSubject);

        }
        for (String exmaName : examNameList) {
            String examId = getExamIdByExamNameAndGradeAndMajor(exmaName, gradeId, majorName);
            String tableName = "ts_score_" + examId; //获得成绩表名称
            Double classAveByScoreId = scoreService.getClassAveByScoreId(tableName, classId, courseId);
            examClassAve.put(exmaName, classAveByScoreId);
        }
        return examClassAve;
    }


    @Override
    public GradeNumDTO getGradeNum(String examValue, String gradeValue, String majorValue, String choiceSubject) {
        String courseId;
        if (choiceSubject.equals("总分")) {
            courseId = "sum";
        } else {
            //获得科目id
            courseId = courseService.getCourseIdByName(choiceSubject);

        }
        String examId = "";

        if ("".equals(majorValue)) {
            examId = examinationService.getIdByExamNameAndGrade(examValue, gradeValue);
        } else {
            examId = examinationService.getIdByExamNameAndGradeAndMajorId(examValue, gradeValue, majorService.getmajorByName(majorValue));
        }
        if (examId == null) {
            return null;
        }
        String tableName = "ts_score_" + examId; //获得成绩表名称
        //通过表名获取这张表考试的人数
        Integer tableNum = scoreMapper.getTableNum(tableName);
        Double maxScore = scoreMapper.getMaxScore(tableName, courseId);
        Double minScore = scoreMapper.getMinScore(tableName, courseId);
        Double aveScore = scoreMapper.getAveScore(tableName, courseId);

        GradeNumDTO gradeNumDTO = new GradeNumDTO(tableNum, maxScore, minScore, Math.floor(aveScore * 10) / 10);
//        gradeNumDTO.setGradePeople(tableNum);
//        //查看最高分通过科目
//        gradeNumDTO.setMaxScore(maxScore);
//        //查看最低分通过科目
//        gradeNumDTO.setMinScore(minScore);
//        //查看平均分通过科目
//        gradeNumDTO.setAveScore(aveScore);
        return gradeNumDTO;
    }

    public String getExamIdByExamNameAndGradeAndMajor(String examValue, String gradeValue, String majorValue) {

//        通过考试名称获取考试id
        String examId = "";

        if ("".equals(majorValue)) {
            examId = examinationService.getIdByExamNameAndGrade(examValue, gradeValue);
        } else {
            examId = examinationService.getIdByExamNameAndGradeAndMajorId(examValue, gradeValue, majorService.getmajorByName(majorValue));
        }
        return examId;
    }

    @Override
    public List<Integer> getAbscissa(String examValue, String gradeValue, String majorValue, String choiceSubject) {
        List<Integer> integers = new ArrayList<>();
        int sum = 0;
        int i;
        Integer minScore = 0;
        String examId = getExamIdByExamNameAndGradeAndMajor(examValue, gradeValue, majorValue);

        if (examId == null) {
            return null;
        }
        String tableName = "ts_score_" + examId; //获得成绩表名称
        if (choiceSubject.equals("总分")) {
            List<String> tableFieldList = getTableFieldList(tableName);
            List<String> scorelist = new ArrayList<>();
            for (String s : tableFieldList) {
                switch (s) {
                    case "score_id":
                    case "exam_id":
                    case "student_id":
                    case "student_name":
                    case "student_class":
                    case "sum":
                    case "classRanking":
                    case "gradeRanking":
                    case "proposal":
                    case "active":
                    case "exam_room":
                        break;
                    default:
                        scorelist.add(s);
                }
            }
            for (String s : scorelist) {
                sum += courseService.getFullmarksById(s);
            }
            minScore = (int) (sum * 0.6);
            for (i = 0; minScore < sum; i++) {
                integers.add(i, minScore);
                minScore += sum / 10;
            }
            integers.add(i, sum);

        } else {
            //获得科目名称
//            courseId = courseService.getCourseIdByName(choiceSubject);
//            通过科目获取最大值
            Integer fullmarks = courseService.getFullmarksByName(choiceSubject);
            minScore = (int) (fullmarks * 0.6) / 10 * 10;
            for (i = 0; minScore < fullmarks; i++) {
                integers.add(i, minScore);
                minScore += fullmarks / 10;
            }
            integers.add(i, fullmarks);
        }
        return integers;
    }

    @Override
    public List<Integer> getScoreListByExamAndClassAndSubject(String examValue, String classValue, String choiceSubject) {
        String courseId;
        if (choiceSubject.equals("总分")) {
            courseId = "sum";
        } else {
            //获得科目id
            courseId = courseService.getCourseIdByName(choiceSubject);

        }
        //通过班级名称获得年级专业id
        String gradeValue = classService.getGradeIdByclassName(classValue);
        String classId = classService.getIdByclassName(classValue);
        String majorId = classService.getMajorIdByclassName(classValue);
        String majorValue = majorService.getMajorName(majorId);
        String examId = getExamIdByExamNameAndGradeAndMajor(examValue, gradeValue, majorValue);
        String tableName = "ts_score_" + examId; //获得成绩表名称
        List<Integer> scoreCourseListByTableNameAndSubject = scoreMapper.getScoreCourseListByTableNameAndSubjectAndClassId(tableName, courseId, classId);
        return scoreCourseListByTableNameAndSubject;

    }


    @Override
    public List<Integer> getScoreListByExamAndGradeAndSubject(String examValue, String gradeValue, String majorValue, String choiceSubject) {
        String courseId;
        if (choiceSubject.equals("总分")) {
            courseId = "sum";
        } else {
            //获得科目id
            courseId = courseService.getCourseIdByName(choiceSubject);

        }
        String examId = getExamIdByExamNameAndGradeAndMajor(examValue, gradeValue, majorValue);
        String tableName = "ts_score_" + examId; //获得成绩表名称
        List<Integer> scoreCourseListByTableNameAndSubject = scoreMapper.getScoreCourseListByTableNameAndSubject(tableName, courseId);
        return scoreCourseListByTableNameAndSubject;
    }

    @Override
    public List<StudentScores> getstudentScoresListBySubject(String choiceSubject, List<StudentScores> studentScoresList) {
        Map<String, Double> stringDoubleMap;//用来存成绩
        String courseId;
        Double sum = 0.0;

        for (StudentScores studentScores : studentScoresList) {
            stringDoubleMap = new HashMap<>();
            if (choiceSubject.equals("总分")) {
                sum = 0.0;
                for (Double value : studentScores.getScores().values()) {
                    sum += value;
                }
                stringDoubleMap.put("总分", sum);
            } else {
                Map<String, Double> scores = studentScores.getScores();
                Double aDouble = scores.get(choiceSubject);
                stringDoubleMap.put(choiceSubject, aDouble);
            }
            studentScores.setScores(stringDoubleMap);

        }
        return studentScoresList;
    }

    @Override
    public List<StudentScores> getstudentScoresListByRankingRange(int num1, int num2, String choiceSubject, List<StudentScores> studentScoresList) {
        int max;
        int min;
        String courseId = "";
        List<StudentScores> studentScoresCopy = new ArrayList<>();
        if (num1 > num2) {
            max = num1;
            min = num2;
        } else {
            max = num2;
            min = num1;
        }
        //通过考试名称获取考试id
        String examId = examinationService.getIdByExamName(studentScoresList.get(0).getExamName());
        String tableName = "ts_score_" + examId; //获得成绩表名称
        if (choiceSubject.equals("总分")) {
            courseId = "sum";
        } else {
            //获得科目id
            courseId = courseService.getCourseIdByName(choiceSubject);

        }
        List<String> subGradeRankingByScoreId = scoreMapper.getSubGradeRankingByScoreId(tableName, courseId);
//        studentScoresList.sort(Comparator.comparingDouble(o -> o.getScores().values().iterator().next()));
        for (StudentScores studentScores : studentScoresList) {
            //进行排名
            List<String> subClassRankingByScoreId = scoreMapper.getSubClassRankingByScoreId(tableName, courseId, classService.getIdByclassName(studentScores.getStudent().getClassId()));
            Integer subGradeRanking = subGradeRankingByScoreId.indexOf(studentScores.getScoreId()) + 1;
            Integer subClassRanking = subClassRankingByScoreId.indexOf(studentScores.getScoreId()) + 1;
            studentScores.setGradeRanking(subGradeRanking);
            studentScores.setClassRanking(subClassRanking);

        }
        for (StudentScores studentScores : studentScoresList) {
            if (studentScores.getGradeRanking() >= min && studentScores.getGradeRanking() <= max) {
                studentScoresCopy.add(studentScores);
            }

        }

        return studentScoresCopy;
    }


    /**
     * 通过学号获取所有的考试成绩
     *
     * @param studentId
     * @param studentScoresList
     * @return
     */
    @Override
    public List<StudentScores> getstudentScoresListByStudentId(String studentId, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (studentId.equals(studentScore.getStudent().getId())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    /**
     * 通过学生姓名获取所有的考试成绩
     *
     * @param studentName
     * @param studentScoresList
     * @return
     */
    @Override
    public List<StudentScores> getstudentScoresListByStudentName(String studentName, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (studentName.equals(studentScore.getStudent().getName())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    /**
     * 通过性别获取所有的考试成绩
     *
     * @param gender
     * @param studentScoresList
     * @return
     */
    @Override
    public List<StudentScores> getstudentScoresListByStudentGender(String gender, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (gender.equals(studentScore.getStudent().getGender())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    /**
     * 通过年级获取所有的考试信息
     *
     * @param grade
     * @param studentScoresList
     * @return
     */
    @Override
    public List<StudentScores> getstudentScoresListByGrade(String grade, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (grade.equals(studentScore.getStudent().getGrade())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    /**
     * 通过班级id获取考试信息
     *
     * @param classId
     * @param studentScoresList
     * @return
     */
    @Override
    public List<StudentScores> getstudentScoresListByClassId(String classId, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (classId.equals(studentScore.getStudent().getClassId())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    /**
     * 通过专业获取所有的考信息
     *
     * @param major
     * @param studentScoresList
     * @return
     */
    @Override
    public List<StudentScores> getstudentScoresListByMajor(String major, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (major.equals(studentScore.getStudent().getMajor())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    @Override
    public List<StudentScores> getstudentScoresListByClass(String classValue, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (classValue.equals(studentScore.getStudent().getClassId())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    @Override
    public List<StudentScores> getRankingRangeNumByclassIdAndchoiceSubject(String examValue, String rankingRange, String classValue, String choiceSubject) {
        String[] parts = rankingRange.split("-");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);
        List<StudentScores> studentScoresList = scoreService.getAll();
        //查看这条数据是不是这场考试的
        studentScoresList = scoreService.getstudentScoresListByExamName(examValue, studentScoresList);
        studentScoresList = scoreService.getstudentScoresListByActive(studentScoresList);
        //通过排名筛选
        studentScoresList = scoreService.getstudentScoresListByRankingRange(num1, num2, choiceSubject, studentScoresList);
        return studentScoresList;
    }


    public List<String> getExamTableSubjects(String tableName) {
        List<String> tableFieldList = getTableFieldList(tableName);
        List<String> scorelist = new ArrayList<>();
        for (String s : tableFieldList) {
            switch (s) {
                case "score_id":
                case "exam_id":
                case "student_id":
                case "student_name":
                case "student_class":
                case "classRanking":
                case "gradeRanking":
                case "proposal":
                case "active":
                case "exam_room":
                    break;
                default:
                    scorelist.add(s);
            }
        }
        return scorelist;
    }


    @Override
    public List<SubRankDTO> getInformationByexamIdAndStudentId(String examId, String studentValue) {
        String tableName = "ts_score_" + examId; //获得成绩表名称
        List<String> scorelist = getExamTableSubjects(tableName);
        //通过考试id和学号获得考试成绩id
        String scoreId = scoreMapper.getScoreIdBystudentId(tableName, studentValue);
        List<SubRankDTO> SubRankDTOList = new ArrayList<>();
        //循环科目
        Double sum = 0.0;
        for (String courseId : scorelist) {
            SubRankDTO subRankDTO = new SubRankDTO();
            //通过科目id获得科目名称
            subRankDTO.setCourse("sum".equals(courseId) ? "总分" : courseService.getCourseNameById(courseId));
            //通过科目id获取科目最大值
            subRankDTO.setMax(courseService.getSubjectMaxScore(courseId));
            //通过成绩表学生id获得科目分数
            double courseScore = scoreService.getScoreByStudentIdAndCourseId(tableName, studentValue, courseId);
            subRankDTO.setSubject(courseScore);
            sum += courseScore;
            List<String> subGradeRankingByScoreId = scoreMapper.getSubGradeRankingByScoreId(tableName, courseId);
//        studentScoresList.sort(Comparator.comparingDouble(o -> o.getScores().values().iterator().next()));
            //获取科目的年级平均分
            Double aveScore = scoreMapper.getAveScore(tableName, courseId);
            subRankDTO.setAverage(Math.floor(aveScore * 10) / 10);
            //进行排名
            List<String> subClassRankingByScoreId = scoreMapper.getSubClassRankingByScoreId(tableName, courseId, studentService.getClassIdById(studentValue));
            Integer subGradeRanking = subGradeRankingByScoreId.indexOf(scoreId) + 1;
            Integer subClassRanking = subClassRankingByScoreId.indexOf(scoreId) + 1;
            //通过成绩表学生id获得科目年级排名
            subRankDTO.setGradeRangk(subGradeRanking);
            //通过成绩表学生id获得科目班级排名
            subRankDTO.setClassRangk(subClassRanking);
            SubRankDTOList.add(subRankDTO);


        }

        return SubRankDTOList;
    }

    @Override
    public double getScoreByStudentIdAndCourseId(String tableName, String studentValue, String s) {
        return scoreMapper.getScoreByStudentIdAndCourseId(tableName, studentValue, s);
    }

    @Override
    public boolean addScoreInfoList(Examination examination) {
        //通过年级和专业获取学生列表列表
        List<Student> studentList = new ArrayList<>();
        if ("".equals(examination.getExamMajor())) {
            //通过年级获取学生列表
            studentList = studentService.getstudentListByGrade(examination.getExamGrade());
        } else {
            studentList = studentService.getstudentListByGradeAndMajor(examination.getExamGrade(), examination.getExamMajor());
        }
        for (Student student : studentList) {
            String scoreId = examination.getId() + "_" + student.getId();//成绩id
            String studentName = studentService.getStudentById(student.getId()).getName();//学生姓名
            String studentClass = studentService.getStudentById(student.getId()).getClassId();//获取班级id
            String tableName = "ts_score_" + examination.getId();
            // 创建学生创建信息
            boolean b2 = scoreService.createScore(scoreId, examination.getId(), student.getId(), studentName, studentClass, tableName);
            if (b2 == false) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean updataExamRoom(String tableName, String id, String examRoom) {
        return scoreMapper.updataExamRoom(tableName, id, examRoom);
    }

    @Override
    public void updataActive(String tableName, String id) {
        scoreMapper.updataActive(tableName,id);
    }

    @Override
    public List<StudentScores> getstudentScoresListByActive(List<StudentScores> studentScoresList) {
        List<StudentScores> studentScoresCopy = new ArrayList<>();
        for (StudentScores studentScores : studentScoresList) {
            if (studentScores.getActive()  == 1) {
                studentScoresCopy.add(studentScores);
            }
        }
        return studentScoresCopy;
    }

    @Override
    public String getProposal(String tableName, String scoresId) {
        return scoreMapper.getProposal(tableName,scoresId);
    }

    /**
     * 通过考试日期获取所有的考试信息
     *
     * @param examDate
     * @param studentScoresList
     * @return
     */
    @Override
    public List<StudentScores> getstudentScoresListByExamDate(String examDate, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (examDate.equals(studentScore.getExamDate())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    /**
     * 通过考试名称筛选考试信息
     *
     * @param examName
     * @param studentScoresList
     * @return
     */
    @Override
    public List<StudentScores> getstudentScoresListByExamName(String examName, List<StudentScores> studentScoresList) {
        List<StudentScores> studentScores = new ArrayList<>();
        for (StudentScores studentScore : studentScoresList) {
            if (examName.equals(studentScore.getExamName())) {
                studentScores.add(studentScore);
            }
        }
        return studentScores;
    }

    /**
     * 通过表名，科目id
     *
     * @param tableName
     * @param courseId
     * @return
     */
    @Override
    public Double getGradeAveByScoreId(String tableName, String courseId) {
        List<Double> scoreList = scoreMapper.getObjectScoreList(tableName, courseId);
        double sum = 0.0;
        for (Double score : scoreList) {
            sum += score;
        }
        return sum / scoreList.size();

    }

    /**
     * 通过表名，班级id，科目id
     *
     * @param tableName
     * @param classId
     * @param courseId
     * @return
     */
    @Override
    public Double getClassAveByScoreId(String tableName, String classId, String courseId) {
        List<Double> scoreList = scoreMapper.getObjectByClassScoreList(tableName, classId, courseId);
        double sum = 0.0;
        for (Double score : scoreList) {
            sum += score;
        }
        return sum / scoreList.size();
    }

    /**
     * 更新成绩
     *
     * @param tableName
     * @param id
     * @param courseName
     * @param scores
     * @return
     */
    @Override
    public boolean updataScore1(String tableName, String id, String courseName, Double scores) {
        boolean b = scoreMapper.updataScore1(tableName, id, courseName, scores);
        boolean b1 = setSumFunc(tableName, id, b);
//        if(b){
//            List<String> tableFieldList = getTableFieldList(tableName);
//            List<String> scorelist = new ArrayList<>();
//            for (String s : tableFieldList) {
//                switch (s){
//                    case "score_id":
//                    case "exam_id":
//                    case "student_id":
//                    case "student_name":
//                    case "student_class":
//                    case "sum":
//                    case "classRanking":
//                    case "gradeRanking":break;
//                    default:scorelist.add(s);
//                }
//            }
//            Double sum = 0.0;
//            for (String score : scorelist) {
//                sum+=scoreMapper.getScoreByname(tableName,id,score);
//            }
//            setSum(tableName,id,sum);
//            return true;
//        }
        return b1;
    }

    /**
     * 通过表名，学生id,来进行sum值的设置
     *
     * @param tableName
     * @param id
     * @param b
     * @return
     */
    public boolean setSumFunc(String tableName, String id, Boolean b) {
        if (b) {
            List<String> tableFieldList = getTableFieldList(tableName);
            List<String> scorelist = new ArrayList<>();
            for (String s : tableFieldList) {
                switch (s) {
                    case "score_id":
                    case "exam_id":
                    case "student_id":
                    case "student_name":
                    case "student_class":
                    case "sum":
                    case "classRanking":
                    case "gradeRanking":
                    case "proposal":
                    case "active":
                    case "exam_room":
                        break;
                    default:
                        scorelist.add(s);
                }
            }
            Double sum = 0.0;
            for (String score : scorelist) {
                sum += scoreMapper.getScoreByname(tableName, id, score);
            }
            setSum(tableName, id, sum);
            return true;
        }
        return false;
    }

    /**
     * 存入总成绩
     *
     * @param tableName
     * @param id
     * @param sum
     */
    @Override
    public void setSum(String tableName, String id, Double sum) {
        scoreMapper.setSum(tableName, id, sum);//将总分存入数据库
        //获取这张表中所有学生的id，用来更新所有学生的排名
        RefreshResults(tableName);

    }

    /**
     * 创建成绩信息
     *
     * @param scoreId
     * @param examId
     * @param studentId
     * @param studentName
     * @param studentClass
     * @param tableName
     * @return
     */
    @Override
    public boolean createScore(String scoreId, String examId, String studentId, String studentName, String studentClass, String tableName) {
        try {
            boolean score = scoreMapper.createScore(scoreId, examId, studentId, studentName, studentClass, tableName);
            return score;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 添加成绩
     *
     * @param tableName
     * @param scoreId
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean addScore(String tableName, String scoreId, String key, double value) {
        boolean b = scoreMapper.addScore(tableName, scoreId, key, value);
        if (b) {
            String studentId = scoreMapper.getStudentByScoreId(tableName, scoreId);
            boolean b1 = setSumFunc(tableName, studentId, true);
            return b1;
        }
        return b;
    }

    /**
     * 删除某成绩表的成绩信息
     *
     * @param tableName
     * @param scoreId
     * @return
     */
    @Override
    public boolean deleteByScoreId(String tableName, String scoreId) {
        boolean b = scoreMapper.deleteByScoreIdTS(tableName, scoreId);
        if (b) {
            RefreshResults(tableName);//刷新排名
        }
        return b;
    }

    /**
     * 获取这张表中所有学生的id，用来更新所有学生的排名
     *
     * @param tableName
     */
    public void RefreshResults(String tableName) {
        List<String> studentIdList = scoreMapper.getStudentIdListByTableName(tableName);
        for (String sid : studentIdList) {
            String classId = studentService.getClassIdById(sid);
            //更新排名，根据表名，学号获取该学生的年级排名
            Integer gR = scoreMapper.getGradeRankingByStudentId(tableName, sid);
            //存进去
            scoreMapper.setGradeRankingByStudentId(tableName, sid, gR);
            //获取班级排名
            Integer cR = scoreMapper.getClassRankingByStudentId(tableName, sid, classId);
            //存入班级排名
            scoreMapper.setClassRankingByStudentId(tableName, sid, cR);
        }
    }

    /**
     * 获取成绩集合
     *
     * @param tableName
     * @param scoreId
     * @param tableFieldList
     * @return
     */
    public Map<String, Double> getScoreMap(String tableName, String scoreId, List<String> tableFieldList) {
        Map<String, Double> stringDoubleMap = new HashMap<>();//用来存成绩
        for (String s : tableFieldList) {
            switch (s) {
                case "score_id":
                case "exam_id":
                case "student_id":
                case "student_name":
                case "student_class":
                case "sum":
                case "classRanking":
                case "gradeRanking":
                case "proposal":
                case "active":
                case "exam_room":
                    break;
                default:
                    stringDoubleMap.put(courseService.getCourseNameById(s), scoreMapper.getScoreByCourse(s, tableName, scoreId));
            }
        }
        return stringDoubleMap;
    }

    /**
     * 获取某表的科目集合
     *
     * @param tableName
     * @return
     */
    @Override
    public List<String> getSubjectListByTableName(String tableName) {
        List<String> tableFieldList = getTableFieldList(tableName);
        List<String> subjectList = new ArrayList<>();
        for (String s : tableFieldList) {
            switch (s) {
                case "score_id":
                case "exam_id":
                case "student_id":
                case "student_name":
                case "student_class":
                case "sum":
                case "classRanking":
                case "gradeRanking":
                case "proposal":
                case "active":
                case "exam_room":
                    break;
                default:
                    subjectList.add(s);
            }
        }
        return subjectList;

    }


    /**
     * 获取表的字段
     */
    public List<String> getTableFieldList(String tableName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/student_management_system?useSSL=false&serverTimezone=Asia/Shanghai";//填入所需的数据库名
            String user = "root";//填入所需的用户名
            String password = "root";//填入所需的密码
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM " + tableName;
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<String> columnNameList = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNameList.add(rsmd.getColumnName(i));
            }
            return columnNameList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
