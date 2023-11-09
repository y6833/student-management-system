package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.service.CourseService;
import com.yangy.service.ExaminationService;
import com.yangy.service.ScoreService;
import com.yangy.service.StudentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/sms/admin/course")
@Component("adminCourseController")
@Slf4j
@Api(tags = "B端-课表相关接口")
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

}
