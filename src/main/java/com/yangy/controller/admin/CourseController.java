package com.yangy.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangy.common.Result;
import com.yangy.entity.Course;
import com.yangy.service.CourseService;
import com.yangy.service.ExaminationService;
import com.yangy.service.ScoreService;
import com.yangy.service.StudentService;
import com.yangy.util.Pagetool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/sms/admin/course")
@Slf4j
@Api(tags = "B端-课程相关接口")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private StudentService studentService;

    /**
     * 获取课程分页
     * @param pageNum
     * @param pageSize
     * @param searchString
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        IPage<Course> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!"".equals(searchString)) {
            Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
            Set<String> keys = stringStringMap.keySet();
            for (String key : keys) {
                String value = stringStringMap.get(key);
                queryWrapper.like(key, value);
            }

        }
        IPage<Course> coursepage = courseService.getPage(page, queryWrapper);

        return Result.success(coursepage);
    }

    /**
     * 通过课程名称获取id
     * @param name
     * @return
     */
    @GetMapping("/getCourseIdByName/{name}")
    public Result getCourseIdByName(@PathVariable String name){
        String courseIdByName = courseService.getCourseIdByName(name);
        return Result.success(courseIdByName);
    }

    /**
     * 新增
     * @param course
     * @return
     */
    @PostMapping("/addcourse")
    public Result addcourse(@RequestBody Course course){
        boolean b = courseService.saveCourse(course);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 修改课程
     * @param course
     * @return
     */
    @PostMapping("/updatacourse")
    public Result updatamajor(@RequestBody Course course){
        boolean b = courseService.updatacourse(course);
        if(b){
            return Result.success();
        }
        return Result.error();
    }


    //删除
    @DeleteMapping("/deletecourse/{id}")
    public Result deletemajor(@PathVariable String id){
        boolean b = courseService.removeById(id);
        if (b){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 获取科目列表
     *
     * @return
     */
    @GetMapping("/getSubjectList")
    public Result getSubjectList() {
        List<String> subjectList = courseService.getSubjectList();
        return Result.success(subjectList);
    }

    /**
     * 通过考试名称获取考试科目
     * @param examName
     * @return
     */
    @GetMapping("/getSubjectListByExamName")
    public Result getSubjectListByExamName(@RequestParam String examName){
        //通过考试名称获得考试成绩表名称
        String tableName = "ts_score_" + examinationService.getIdByExamName(examName);
        //通过考试成绩表获取考试科目
        List<String> subjectList = scoreService.getSubjectListByTableName(tableName);
        List<String> subjectListName = new ArrayList<>();
        //通过科目id获取科目名称
        for (String s : subjectList) {
            subjectListName.add(courseService.getCourseNameById(s));
        }

        return Result.success(subjectListName);
    }

    /**
     * 获取该科目最大值
     */
    @PostMapping("/getSubjectMax")
    public Result getSubjectMax(@RequestBody List<String> subjects) {
        // 判断参数是否合法
        if (subjects == null || subjects.size() == 0) {
            return Result.error("无考试科目");
        }
        Map subjectMax = new HashMap();
        for (String subject : subjects) {
            //通过科目名称获取科目满分
            Integer subjectMaxScore = courseService.getSubjectNameMaxScore(subject);
            subjectMax.put(subject, subjectMaxScore);
        }
        List<Map> indicator = new ArrayList<>();
        subjectMax.forEach((key, value) -> {
            Map<String, Object> temp = new HashMap<>();
            temp.put("max", value);
            temp.put("name", key);
            indicator.add(temp);
        });
        return Result.success(indicator);

    }

    /**
     * 获取科目该考试该班级的的平均分
     *
     * @param examDate
     * @param objects
     * @return
     */
    @GetMapping("/getClassAve")
    public Result getClassAve(@RequestParam String examName,
            @RequestParam Date examDate
            , @RequestParam String id
            , @RequestParam String objects) {
        objects = objects.replaceAll("\"", ""); // 去掉双引号
        String[] arr = objects.split(","); // 使用逗号分割字符串
        List<String> objectlist = Arrays.asList(arr); // 将数组转换为集合
        HashMap<String, Double> classAve = new HashMap<>();
        //先通过考试时间和考试日期获得成绩表
        String tableName = "ts_score_"+ examinationService.getIdByNameAndDate(examName, examDate);
        //通过学生id获得班级
        String classId = studentService.getClassIdById(id);
        for (String object : objectlist) {
            Double value = scoreService.getClassAveByScoreId(tableName,classId,courseService.getCourseIdByName(object));
            classAve.put(object, value);
        }
        return Result.success(classAve);

    }

    /**
     * 获取该科目该考试的年级平均分
     * @param examDate
     * @param objects
     * @return
     */
    @GetMapping("/getGradeAve")
    public Result getGradeAve(@RequestParam String examName,
            @RequestParam Date examDate
            , @RequestParam String id
            , @RequestParam String objects) {
        objects = objects.replaceAll("\"", ""); // 去掉双引号
        String[] arr = objects.split(","); // 使用逗号分割字符串
        List<String> objectlist = Arrays.asList(arr); // 将数组转换为集合
        HashMap<String, Double> gradeAve = new HashMap<>();
        //先通过考试时间和考试日期获得成绩表
        String tableName = "ts_score_"+ examinationService.getIdByNameAndDate(examName, examDate);
        for (String object : objectlist) {
            Double value = scoreService.getGradeAveByScoreId(tableName,courseService.getCourseIdByName(object));
            gradeAve.put(object, value);
        }
        return Result.success(gradeAve);

    }


    /**
     * 获取课程id列表
     * @return
     */
    @GetMapping("/getCourseIdList")
    public Result getCourseIdList(){
        List<String> courseList = courseService.getCourseIdList();
        if(courseList.size() >0){
            return Result.success(courseList);
        }
        return Result.error();
    }


    @GetMapping("/getCourseNameById/{id}")
    public Result getCourseNameById(@PathVariable String id){
        String courseNameById = courseService.getCourseNameById(id);
        if("".equals(courseNameById)){
            return Result.error();
        }
        return Result.success(courseNameById);
    }

}
