package com.yangy.controller.admin;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangy.common.Result;
import com.yangy.entity.Tclass;
import com.yangy.service.ClassService;
import com.yangy.service.MajorService;
import com.yangy.service.TeacherService;
import com.yangy.util.Pagetool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/sms/admin/classs")
@Slf4j
@Api(tags = "班级相关接口")
public class ClassController {

    @Autowired
    ClassService classService;

    @Autowired
    MajorService majorService;

    @Autowired
    TeacherService teacherService;

    @GetMapping("/getClassList")
    public Result getClassList(){
        List<String> classList = classService.getClassList();
        return Result.success(classList);
    }


    @GetMapping("/getClassIdList")
    public Result getClassIdList(){
        List<String> classList = classService.getClassIdList();
        return Result.success(classList);
    }
    @GetMapping("/getGradeList")
    public Result getGradeList(){
        List<String> gradeList = classService.getGradeList();
        return Result.success(gradeList);
    }

    //通过班级id获取班级名称
    @GetMapping("/getClassNameById/{id}")
    public Result getClassNameById(@PathVariable String id){
        return Result.success(classService.getClassName(id));
    }

    //通过班级名称获取班级id
    @GetMapping("/getClassIdbyName/{name}")
    public Result getClassIdbyNmae(@PathVariable String name){
        return Result.success(classService.getIdByclassName(name));
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


    @GetMapping("/getclassPage")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        IPage<Tclass> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Tclass> queryWrapper = new QueryWrapper<>();
        if(!"".equals(searchString)){
            Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
            Set<String> keys = stringStringMap.keySet();
            for (String key: keys) {
                String value = stringStringMap.get(key);
                if("major_id".equals(key)){
                    value = majorService.getIdByclassName(value);
                }else if("head_teacher_id".equals(key)){
                    value = teacherService.getIdByName(value);
                }
                queryWrapper.like(key, value);
            }

        }
        IPage<Tclass> classPage = classService.getPage(page,queryWrapper);

        return Result.success(classPage);
    }

    /**
     * 新增班级
     * @param tclass
     * @return
     */
    @PostMapping("/addClass")
    public Result addClass(@RequestBody Tclass tclass){
        boolean b = classService.addClass(tclass);
        if (b){
            return Result.success("添加班级成功");
        }else{
            return Result.error();
        }
    }

    /**
     * 修改
     * @param tclass
     * @return
     */
    @PostMapping("/updataClass")
    public Result updataClass(@RequestBody Tclass tclass){
        boolean b =  classService.updataClass(tclass);
        if (b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    @DeleteMapping("/removeclass/{id}")
    public Result removeclass(@PathVariable String id){
       boolean b = classService.removeclassById(id);
        if (b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    /**
     * 导出接口
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询所有的数据
        List<Tclass> list = classService.findAllOver();

        //通过工具类创建writer写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("classId","班级ID");
        writer.addHeaderAlias("className","班级名称");
        writer.addHeaderAlias("gradeId","年级");
        writer.addHeaderAlias("majorId","专业");
        writer.addHeaderAlias("headTeacherId","班主任");


        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("班级信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();


    }

    /**
     * 导入接口
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        reader.addHeaderAlias("班级ID","classId");
        reader.addHeaderAlias("班级名称","className");
        reader.addHeaderAlias("年级","gradeId");
        reader.addHeaderAlias("专业","majorId");
        reader.addHeaderAlias("班主任","headTeacherId");

        List<Tclass> list = reader.readAll(Tclass.class);
//        System.out.println(list);
        classService.saveClassList(list);
        return true;
    }


}
