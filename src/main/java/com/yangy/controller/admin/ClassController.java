package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.service.ClassService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sms/admin/classs")
@Slf4j
@Api(tags = "班级相关接口")
public class ClassController {

    @Autowired
    ClassService classService;

    @GetMapping("/getClassList")
    public Result getClassList(){
        List<String> classList = classService.getClassList();
        return Result.success(classList);
    }

    @GetMapping("/getGradeList")
    public Result getGradeList(){
        List<String> gradeList = classService.getGradeList();
        return Result.success(gradeList);
    }

    //通过年级获得班级列表
    @GetMapping("/getClassListBygradeId")
    public Result getClassList(@RequestParam String gradeId){
        List<String> classListBygradeId = classService.getClassListBygradeId(gradeId);
        return Result.success(classListBygradeId);
    }

    //通过班级获得专业
    @GetMapping("/getMajorByclassId")
    public Result getMajorListByclassId(@RequestParam String classId){
        List<String> majorByclassId = classService.getMajorByclassId(classId);
        return Result.success(majorByclassId);
    }

    //通过班级获得年级
    @GetMapping("/getGradeByclassId")
    public Result getGradeByclassId(@RequestParam String classId){
        List<String> gradeByclassId = classService.getGradeByclassId(classId);
        return Result.success(gradeByclassId);
    }


}
