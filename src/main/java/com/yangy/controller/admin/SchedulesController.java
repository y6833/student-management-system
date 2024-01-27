package com.yangy.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangy.common.Result;
import com.yangy.entity.Schedules;
import com.yangy.service.SchedulesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/admin/schedules")
@Slf4j
@Api(tags = "课表相关接口")
public class SchedulesController {

    @Autowired
    public SchedulesService schedulesService;

    /**
     * 获取课表分页
     * @param pageNum
     * @param pageSize
     * @param searchString
     * @return
     */
    @GetMapping("/getschedulePage")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        IPage<Schedules> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Schedules> queryWrapper = new QueryWrapper<>();
        if(!"".equals(searchString)) {
            queryWrapper.like("name", searchString);
        }
        IPage<Schedules> schedulespage = schedulesService.getPage(page,queryWrapper);

        return Result.success(schedulespage);
    }

    /**
     * 修改专业
     * @param schedules
     * @return
     */
    @PostMapping("/updataschedule")
    public Result updataschedule(@RequestBody Schedules schedules){
        boolean b = schedulesService.updataschedule(schedules);
        if(b){
            return Result.success();
        }
        return Result.error();
    }


    //删除
    @DeleteMapping("/deleteschedule/{id}")
    public Result deleteschedule(@PathVariable String id){
        boolean b = schedulesService.removeById(id);
        if (b){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 新增
     * @param schedules
     * @return
     */
    @PostMapping("/addschedule")
    public Result addschedule(@RequestBody Schedules schedules){
        boolean b = schedulesService.saveschedules(schedules);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 根据id获取名称
     * @param id
     * @return
     */
    @GetMapping("/getscheduleNameById/{id}")
    public Result getscheduleNameById(@PathVariable String id){
       String scheduleName =  schedulesService.getscheduleNameById(id);
       if("".equals(scheduleName)){
           return Result.error();
       }
       return Result.success(scheduleName);
    }

    /**
     * 获取课表id列表
     * @return
     */
    @GetMapping("/getscheduleList")
    public Result getscheduleList(){
        List<String> strings = schedulesService.getscheduleList();
        if(strings.size()>0){
            return Result.success(strings);
        }
        return Result.error();
    }

}
