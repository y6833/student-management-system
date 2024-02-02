package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.entity.ScheduleDTO;
import com.yangy.service.ScheduleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/admin/schedule")
@Component("adminScheduleController")
@Slf4j
@Api(tags = "管理课表相关接口")
public class ScheduleController {

    @Autowired
    public ScheduleService scheduleService;




    /**
     * 保存课表信息
     * @param scheduleDTO
     * @return
     */
    @PostMapping("/saveschedule")
    public Result saveschedule(@RequestBody ScheduleDTO scheduleDTO){
        if(scheduleDTO.getWeekly().size() >0){
            boolean saveschedule = scheduleService.saveschedule(scheduleDTO);
            if(saveschedule){
                return Result.success();
            }else{
                return Result.error();
            }
        }
        return Result.error("周次不能为空");
    }

    /**
     *
     * 修改课表信息
     * @return
     */
    @PostMapping("/updatascheduleInFo")
    public Result updatascheduleInFo(@RequestBody ScheduleDTO scheduleDTO)
    {
        boolean updataschedule = scheduleService.updatascheduleInFo(scheduleDTO);
        if(updataschedule){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 删除课表内容
     * @param scheduleDTO
     * @return
     */
    @PostMapping("/deleteScheduleInFo")
    public Result deleteScheduleInFo(@RequestBody ScheduleDTO scheduleDTO){
        boolean b = scheduleService.deleteScheduleInFo(scheduleDTO);
        if(b){
            return Result.success();
        }
        return  Result.error();
    }


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

}
