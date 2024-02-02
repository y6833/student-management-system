package com.yangy.controller.user;

import com.yangy.common.Result;
import com.yangy.entity.Examination;
import com.yangy.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sms/user/examination")
@Component("userExaminationController")
@Slf4j
@Api(tags = "用户考试相关接口")
public class ExaminationController {

    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private SchedulesService schedulesService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private StudentService studentService;


    /**
     *
     * @param examType 考试类型1考试/2测试
     * @param semester 学期
     * @param releId 学号
     * @return
     */
    @GetMapping("/getExamListByExamTypeAndSemesterAndReleId")
    public Result getExamListByExamTypeAndSemesterAndReleId(@RequestParam Integer examType,
                                                            @RequestParam Integer semester,
                                                            @RequestParam String releId){
        List<Examination>  examList = new ArrayList<>();
        if(examType == 1){
            //通过学号获得班级id
            String classId = studentService.getClassIdById(releId);
            //通过班级id获得level等级
            Integer level= classService.getLevelByClassId(classId);
            //通过班级id，level，学期获得课表
            String scheduleName = schedulesService.getscheduleNameByClassIdAndLevelAndSemester1(classId,level,semester);
            //通过课表获取考试集合
            examList = examinationService.getEXamListByScheduleName(scheduleName);

        }else if(examType == 2){
//            通过学生id获取年级专业
            String gradeId = studentService.getGradeById(releId);
            String majorId = studentService.getMajorById(releId);
            examList = examinationService.getExamByGradeIdAndMajorId(gradeId,majorId);


        }
        return Result.success(examList);
    }

    /**
     * 获取课表考试列表
     * @return
     */
    @GetMapping("/getExamListks")
    public Result getExamListks(){
        List<String> examListks = examinationService.getExamListks();
        return Result.success(examListks);
    }


}
