package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.entity.Examination;
import com.yangy.entity.Student;
import com.yangy.entity.StudentScores;
import com.yangy.service.ScoreService;
import com.yangy.util.Pagetool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/sms/admin/score")
@Component("adminScoreController")
@Slf4j
@Api(tags = "成绩相关接口")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    StudentController studentController;

    //mybatis-plus分页查询
    @GetMapping("/getStuScoreList")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {
        String searchStr = "";
        if (!"".equals(searchString)) {
            Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
            Set<String> keys = stringStringMap.keySet();
            for (String key : keys) {
//                switch (key){
//                    case "id":
//                    case "name":
//                    case "gender":
//                    case "grade":
//                    case "class_id":
//                    case "major":
                searchStr += "&" + key + "=" + stringStringMap.get(key);
//                break;
//                }
            }
        }

        Result page = studentController.findPage(pageNum, pageSize, searchStr);

        return Result.success(page);
    }

    @PostMapping("/getStuScorePage")
    public Result Page(@RequestBody List<Student> studentList) {
        ArrayList<StudentScores> studentScoresList = new ArrayList<>();
        //获取考试次数
        List<Examination> examTimes = scoreService.getExamTimes();
        for (Examination examTime : examTimes) {
            for (Student student : studentList) {
                StudentScores studentScores = new StudentScores();
                //注入学生信息
                studentScores.setStudent(student);
                //注入考试成绩
                studentScores.setScores(scoreService.findScoreByStuIdAndExamDate(student.getId(), examTime.getExamDate()));
                //注入班级考试排名
                studentScores.setClassRanking(scoreService.getClassRanking(student.getId(), student.getClassId(), examTime.getExamDate()));
                //注入年级排名
                studentScores.setGradeRanking(scoreService.getGradeRanking(student.getId(), student.getGrade(), student.getMajor(), examTime.getExamDate()));
                //注入考试日期
                studentScores.setExamDate(examTime.getExamDate());
                //注入考试名称
                studentScores.setExamName(examTime.getExamName());


                //将此次考试信息加入列表
                studentScoresList.add(studentScores);
            }
        }


        return Result.success(studentScoresList);
    }

    @GetMapping("/updataScore")
    public Result updataScore(@RequestParam String id,
                              @RequestParam String courseName,
                              @RequestParam Double scores,
                              @RequestParam Date examDate) {
        boolean result = scoreService.updataScore(id, courseName, scores, examDate);
        return Result.success(result);

    }

    /**
     * 获取考试列表
     * @return
     */
    @GetMapping("/getExamList")
    public Result getExamList(){
        List<String> examList = scoreService.getExamList();
        return Result.success(examList);
    }


}
