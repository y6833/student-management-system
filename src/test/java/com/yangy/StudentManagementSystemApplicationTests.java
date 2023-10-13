package com.yangy;

import com.yangy.pojo.StudentQuestionTypeScore;
import com.yangy.pojo.Teacher;
import com.yangy.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@SpringBootTest
class StudentManagementSystemApplicationTests {

    @Autowired
    AdminService adminService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    QuestionTypesService questionTypesService;

    @Autowired
    StudentQuestionTypeScoreService studentQuestionTypeScoreService;
    @Test
    void contextLoads() throws ParseException {

        Date date = new Date();

//        Admin admin = new Admin("a003", "杨成","123456", "男", "example@nnthink.com", "13874561122",date);
//        Student student = new Student("s002", "杨宇", "1234785", "男", date, "2020", "jk2002", "18156845@qq.com", "18365465525", "湖南涉外经济学院", "jk001");
        Teacher teacher = new Teacher("t002", "左春华", "1231546", "女", date, "2020", "jk2002", "199625855@qq.com", "18965653322", "湖南涉外经济学院");
        System.out.println(teacher);
        teacherService.insert(teacher);
//        studentService.insert(student);
//                adminService.addAdmin(admin);
        teacherService.findAll();



    }

    @Test
    void add(){
        studentQuestionTypeScoreService.insert(new StudentQuestionTypeScore("s001","c001",1, 40.0F,new Date()));
        select();
    }


    @Test
    void select() {
        List<StudentQuestionTypeScore> studentQuestionTypeScores = studentQuestionTypeScoreService.selectAll();
        for (StudentQuestionTypeScore studentQuestionTypeScore : studentQuestionTypeScores) {
            System.out.println(studentQuestionTypeScore);
        }
    }



}
