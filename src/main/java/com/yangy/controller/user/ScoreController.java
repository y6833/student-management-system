package com.yangy.controller.user;

import com.yangy.common.Result;
import com.yangy.controller.admin.StudentController;
import com.yangy.entity.SubRankDTO;
import com.yangy.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sms/user/score")
@Component("userScoreController")
@Slf4j
@Api(tags = "用户成绩相关接口")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    StudentController studentController;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    ExaminationService examinationService;

    @GetMapping("/getSubRank")
    public Result getSubRank(@RequestParam String examValue,
                             @RequestParam String studentValue) {
//    考试名称和学生id
        //首先通过考试名称获得考试和学生id获得考试id
        String examId = examinationService.getIdByExamNameAndStudentId(examValue, studentValue);
        List<SubRankDTO> SubRankDTOList = scoreService.getInformationByexamIdAndStudentId(examId, studentValue);
        return Result.success(SubRankDTOList);

    }

    @GetMapping("/getProposal")
    public Result getProposal(@RequestParam String examValue,
                             @RequestParam String studentValue) {
//    考试名称和学生id
        //首先通过考试名称获得考试和学生id获得考试id
        String examId = examinationService.getIdByExamNameAndStudentId(examValue, studentValue);
        String scoresId = examId + "_" + studentValue;
        String tableName = "ts_score_" + examId;
        String proposal = scoreService.getProposal(tableName,scoresId);
        return Result.success(proposal);

    }
}
