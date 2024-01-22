package com.yangy.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangy.common.Result;
import com.yangy.entity.Major;
import com.yangy.service.MajorService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/admin/major")
@Slf4j
@Api(tags = "专业相关接口")
public class MajorController {

    @Autowired
    private MajorService majorService;

    /**
     * 获取专业列表
     *
     * @return
     */
    @GetMapping("/getMajorList")
    public Result getMajorList() {
        List<String> majorList = majorService.getMajorList();
        return Result.success(majorList);
    }

    @GetMapping("/getmajorPage")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        IPage<Major> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        if(!"".equals(searchString)) {
            queryWrapper.like("major_name", searchString);
        }
        IPage<Major> majorpage = majorService.getPage(page,queryWrapper);

        return Result.success(majorpage);
    }

    /**
     * 修改专业
     * @param major
     * @return
     */
    @PostMapping("/updatamajor")
    public Result updatamajor(@RequestBody Major major){
        boolean b = majorService.updatamajor(major);
        if(b){
            return Result.success();
        }
        return Result.error();
    }


    //删除
    @DeleteMapping("/deletemajor/{id}")
    public Result deletemajor(@PathVariable String id){
        boolean b = majorService.removeById(id);
        if (b){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 新增
     * @param major
     * @return
     */
    @PostMapping("/addmajor")
    public Result addmajor(@RequestBody Major major){
        boolean b = majorService.saveMajor(major);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }



}
