package com.yangy.controller.admin;

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
    public List<String> getClassList(){
        return classService.getClassList();
    }

    @GetMapping("/getGradeList")
    public List<String> getGradeList(){
        return classService.getGradeList();
    }

    //通过年级获得班级列表
    @GetMapping("/getClassListBygradeId")
    public List<String> getClassList(@RequestParam String gradeId){
        return classService.getClassListBygradeId(gradeId);
    }

    //通过班级获得专业
    @GetMapping("/getMajorByclassId")
    public List<String> getMajorListByclassId(@RequestParam String classId){
        return classService.getMajorByclassId(classId);
    }

    //通过班级获得年级
    @GetMapping("/getGradeByclassId")
    public List<String> getGradeByclassId(@RequestParam String classId){
        return classService.getGradeByclassId(classId);
    }


}
