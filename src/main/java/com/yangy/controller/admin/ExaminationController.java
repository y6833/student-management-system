package com.yangy.controller.admin;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangy.common.Result;
import com.yangy.entity.Examination;
import com.yangy.entity.ExaminationDTO;
import com.yangy.service.*;
import com.yangy.util.DateHelp;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/sms/admin/examination")
@Slf4j
@Api(tags = "考试相关接口")
public class ExaminationController {

    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private SchedulesService schedulesService;

    @Autowired
    private ScheduleService scheduleService;



    //mybatis-plus分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        IPage<Examination> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Examination> queryWrapper = new QueryWrapper<>();
        if(!"".equals(searchString)){
            Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
            Set<String> keys = stringStringMap.keySet();
            for (String key: keys) {
                String value = stringStringMap.get(key);
                 if("exam_major".equals(key)){
                    value = majorService.getIdByclassName(value);
                }else if("exam_date".equals(key)){
                    value = DateHelp.dataToString(value);
                }
                queryWrapper.like(key, value);
            }

        }
        IPage<Examination> examinationpage = examinationService.getPage(page,queryWrapper);

        return Result.success(examinationpage);
    }


    @GetMapping("/getCurrSchedule")
    public Result getCurrSchedule(@RequestParam String name){
        //通过名称获得id
        String scheduleId = schedulesService.getscheduleIdByName(name);
        //通过名称获得班级id
        String classId= schedulesService.getClassIdByName(name);
        //通过班级id获取年级
        String gradeId = classService.getGradeIdByclassId(classId);
        //通过班级id获取专业
        String majorId = classService.getMajorIdByclassId(classId);
        //通过专业id获取专业名称
        String majorName = majorService.getMajorName(majorId);
        //通过课表id获得课表课程列表
        List<String> courseList = scheduleService.getCourseListById(scheduleId);

        Map<String, Object> data = new HashMap<>();
        data.put("courses", courseList);
        data.put("examGrade", gradeId);
        data.put("examMajor", majorName);
        return Result.success(data);


    }

    /**
     * 添加考试信息，创建成绩表
     * @param examinationDTO
     * @return
     */
    @PostMapping("/addExamination")
    public Result addExamination(@RequestBody ExaminationDTO examinationDTO){
        Examination examination = examinationDTO.getExamination();
        examination.setExamMajor(majorService.getIdByclassName(examination.getExamMajor()));
        if(examinationDTO.getCourses().size()>0){
            boolean b = examinationService.addExamination(examination);
            if(b){
                boolean scoreTable = examinationService.createScoreTable(examination, examinationDTO.getCourses());
                if(scoreTable){
                    return Result.success("创建成功");
                }else{
                    return Result.error("创建失败");
                }
            }
            return Result.success("考试信息添加成功");
        }
        return Result.error("无考试科目,添加考试信息失败！");

    }

    /**
     * 删除考试信息，删除表
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result removeExamination(@PathVariable String id){
        boolean b = examinationService.deleteById(id);
        if(b){
            boolean b1 = examinationService.deleteTable(id);
            if(b1){
                return Result.success("删除成绩表成功");
            }
            return Result.success("删除成功！");
        }else{
            return Result.error("删除失败!");
        }
    }

    /**
     * 更新考试信息
     * @param examination
     * @return
     */
    @PostMapping("/updateExamination")
    public Result updateExamination(@RequestBody Examination examination){
        examination.setExamMajor(majorService.getIdByclassName(examination.getExamMajor()));
        boolean b = examinationService.updataExam(examination);
        if(b){
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
        List<Examination> list = examinationService.findAllOver();

        //通过工具类创建writer写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id","考试编号");
        writer.addHeaderAlias("examName","考试名称");
        writer.addHeaderAlias("examGrade","年级");
        writer.addHeaderAlias("examMajor","专业");
        writer.addHeaderAlias("examDate","考试日期");

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("考试信息", "UTF-8");
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

        reader.addHeaderAlias("考试编号","id");
        reader.addHeaderAlias("考试名称","examName");
        reader.addHeaderAlias("年级","examGrade");
        reader.addHeaderAlias("专业","examMajor");
        reader.addHeaderAlias("考试日期","examDate");

        List<Examination> list = reader.readAll(Examination.class);
//        System.out.println(list);
        examinationService.saveExaminationList(list);
        return true;
    }


    /**
     * 获取课表考试列表
     * @return
     */
    @GetMapping("/getExamListks")
    public Result getExamListks(){
        List<String> examListks = examinationService.getExamListks();
        return Result.success(examListks);
    }


}
