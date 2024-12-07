package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangy.entity.Student;
import com.yangy.entity.Teacher;
import com.yangy.entity.Course;
import com.yangy.mapper.*;
import com.yangy.service.DashboardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MajorMapper majorMapper;

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> result = new HashMap<>();

        // 获取当前数量，转换Long为long
        QueryWrapper<Student> studentWrapper = new QueryWrapper<>();
        QueryWrapper<Teacher> teacherWrapper = new QueryWrapper<>();
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();

        long studentCount = studentMapper.selectCount(studentWrapper);
        long teacherCount = teacherMapper.selectCount(teacherWrapper);
        // 使用distinct统计班级数量
        long classCount = studentMapper.selectCount(new QueryWrapper<Student>().select("DISTINCT class_id"));
        long courseCount = courseMapper.selectCount(courseWrapper);

        // 获取上月数据（这里简单模拟，实际应该从历史记录表获取）
        int studentIncrease = (int) (Math.random() * 20) - 10; // 模拟增减
        int teacherIncrease = (int) (Math.random() * 4) - 2;
        int classIncrease = (int) (Math.random() * 2) - 1;
        int courseIncrease = (int) (Math.random() * 3) - 1;

        result.put("studentCount", studentCount);
        result.put("teacherCount", teacherCount);
        result.put("classCount", classCount);
        result.put("courseCount", courseCount);
        result.put("studentIncrease", studentIncrease);
        result.put("teacherIncrease", teacherIncrease);
        result.put("classIncrease", classIncrease);
        result.put("courseIncrease", courseIncrease);

        return result;
    }

    @Override
    public Map<String, Integer> getGenderRatio() {
        Map<String, Integer> result = new HashMap<>();

        // 统计男女生人数，使用gender字段
        QueryWrapper<Student> maleWrapper = new QueryWrapper<>();
        maleWrapper.eq("gender", "男");
        long maleCount = studentMapper.selectCount(maleWrapper);

        QueryWrapper<Student> femaleWrapper = new QueryWrapper<>();
        femaleWrapper.eq("gender", "女");
        long femaleCount = studentMapper.selectCount(femaleWrapper);

        result.put("male", (int)maleCount);
        result.put("female", (int)femaleCount);

        return result;
    }

    @Override
    public List<Map<String, Object>> getMajorDistribution() {
        List<Map<String, Object>> result = new ArrayList<>();
        
        // 获取所有专业
        List<String> majors = majorMapper.getMajorList();
        
        // 统计每个专业的学生数量
        for (String majorName : majors) {
            Map<String, Object> majorData = new HashMap<>();
            majorData.put("majorName", majorName);
            
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("major", majorName);
            long studentCount = studentMapper.selectCount(wrapper);
            
            majorData.put("studentCount", studentCount);
            result.add(majorData);
        }
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getStudentTrend() {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月");

        // 获取近6个月的数据
        for (int i = 5; i >= 0; i--) {
            LocalDate month = now.minusMonths(i);
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", month.format(formatter));
            
            // 获取该月的学生数量
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            String monthStr = month.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            wrapper.likeRight("id", monthStr); // 假设学号前缀包含年月信息
            long count = studentMapper.selectCount(wrapper);
            
            monthData.put("count", count);
            result.add(monthData);
        }

        return result;
    }
}
