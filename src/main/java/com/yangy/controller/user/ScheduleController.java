package com.yangy.controller.user;

import com.yangy.common.Result;
import com.yangy.entity.ScheduleDTO;
import com.yangy.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/user/schedule")
@Component("userScheduleController")
@Slf4j
@Api(tags = "学生课表相关接口")
public class ScheduleController {

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
     * 通过课程id获取课程信息
     * @param id
     * @return
     */
    @GetMapping("/getscheduleWorkList/{id}")
    public Result getscheduleWorkList(@PathVariable String id){
        List<ScheduleDTO> scheduleDTOS = scheduleService.getscheduleWorkList(id);

        if(scheduleDTOS.size()>0){
            return Result.success(scheduleDTOS);
        }
        return Result.error();
    }

    /**
     * 通过学号
     * @param semester
     * @param releIdVariable
     * @return
     */
    @GetMapping("/getscheduleWorkListByStudentId")
    public Result getscheduleWorkListByStudentId(
                                                 @RequestParam Integer semester,
                                                 @RequestParam String releId){
        //通过学号获得班级，专业
        //通过班级专业获得level
        //通过学号获得班级id
        String classId = studentService.getClassIdById(releId);
        //通过班级id获得level等级
        Integer level= classService.getLevelByClassId(classId);
        //通过班级id，level，学期获得课表
        String scheduleName = schedulesService.getscheduleNameByClassIdAndLevelAndSemester1(classId,level,semester);
        //通过课表名称获得课表id
        String id = schedulesService.getscheduleIdByName(scheduleName);

        List<ScheduleDTO> scheduleDTOS = scheduleService.getscheduleWorkList(id);

        if(scheduleDTOS.size()>0){
            return Result.success(scheduleDTOS);
        }
        return Result.error();
    }
}
