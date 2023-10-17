package com.yangy.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangy.entity.Student;
import com.yangy.service.ClassService;
import com.yangy.service.MajorService;
import com.yangy.service.StudentService;
import com.yangy.util.Pagetool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/sms/admin/student")
@Component("adminStudentController")
@Slf4j
@Api(tags = "学生信息相关接口")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    //新增
    @PostMapping
    public boolean save(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    //修改
    @PostMapping("/updata")
    public boolean updata(@RequestBody Student student){
        return studentService.updataStudent(student);
    }

    //查找所有
    @GetMapping
    public List<Student> findALl(){
        return studentService.findAll();
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return studentService.removeById(id);
    }

    /**
     * 分页查询
     */
//    @GetMapping("/page")
//    public Map<String,Object> findPage(@RequestParam Integer pageNum
//            , @RequestParam Integer pageSize
////            , String searchString
//    ){
//        pageNum = (pageNum - 1) * pageSize;

//        Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
//        stringStringMap.put("pageSize", String.valueOf(pageSize));
//        stringStringMap.put("pageNumber", String.valueOf(pageNum));
//        List<Student> data = studentService.selectInPage(stringStringMap);

//        List<Student> data = studentService.selectPage(pageNum, pageSize);
//        Integer total = studentService.selectTotal();
//        Map<String,Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total", total);
//        log.info("查询user数据：{}", res);
//        return res;
//    }


    //mybatis-plus分页查询
    @GetMapping("/page")
    public IPage<Student> findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        IPage<Student> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if(!"".equals(searchString)){
            Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
            Set<String> keys = stringStringMap.keySet();
            for (String key: keys) {
                String value = stringStringMap.get(key);
                if("class_id".equals(key)){
                    value = classService.getIdByclassName(value);
                }else if("major".equals(key)){
                    value = majorService.getIdByclassName(value);
                }
                queryWrapper.like(key, value);
            }

        }
        IPage<Student> studentpage = studentService.getPage(page,queryWrapper);
        return studentpage;
    }


}
