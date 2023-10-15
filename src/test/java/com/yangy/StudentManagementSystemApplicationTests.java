package com.yangy;

import com.yangy.entity.Teacher;
import com.yangy.entity.User;
import com.yangy.service.*;
import com.yangy.util.Pagetool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    UserService userService;
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
        User user = new User("yangyu","123456",1,"a001",2,null);
        System.out.println(user);
        userService.addUser(user);
        select();
    }


    @Test
    void select() {
        List<User> allUsers = userService.getAllUsers();
        for (User allUser : allUsers) {
            System.out.println(allUser);
        }
//        List<StudentQuestionTypeScore> studentQuestionTypeScores = studentQuestionTypeScoreService.selectAll();
//        for (StudentQuestionTypeScore studentQuestionTypeScore : studentQuestionTypeScores) {
//            System.out.println(studentQuestionTypeScore);
//        }

    }

    @Test
    void test(){
        Map<String, String> stringStringMap = Pagetool.parseParams("&name=yy&gender=");
//        Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
//        Set<String> keys = stringStringMap.keySet();
//        Collection<String> values = stringStringMap.values();
        System.out.println(stringStringMap);
//        System.out.println(keyValue.length);
//        for (String s : keyValue) {
//            System.out.println(s);
//        }
        }





}
