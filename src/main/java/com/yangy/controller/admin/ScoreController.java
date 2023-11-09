package com.yangy.controller.admin;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yangy.common.Result;
import com.yangy.entity.*;
import com.yangy.service.*;
import com.yangy.util.Pagetool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/sms/admin/score")
@Component("adminScoreController")
@Slf4j
@Api(tags = "成绩相关接口")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    StudentController studentController;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    ExaminationService examinationService;

    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    //分页查询
    @GetMapping("/getStuScoreList")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        List<StudentScores> studentScoresList = scoreService.getAll();
        if (!"".equals(searchString)) {
            Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
            Set<String> keys = stringStringMap.keySet();

            for (String key : keys) {
                String value = stringStringMap.get(key);
                switch (key){
                    case "id":
                        studentScoresList = scoreService.getstudentScoresListByStudentId(value,studentScoresList); //通过学号筛选
                        break;
                    case "name":
                        studentScoresList = scoreService.getstudentScoresListByStudentName(value,studentScoresList);//通过姓名筛选
                        break;
                    case "gender":
                        studentScoresList = scoreService.getstudentScoresListByStudentGender(value,studentScoresList);//通过性别筛选
                        break;
                    case "grade":
                        studentScoresList = scoreService.getstudentScoresListByGrade(value,studentScoresList);//通过年级筛选
                        break;
                    case "class_id":
                        studentScoresList = scoreService.getstudentScoresListByClassId(value,studentScoresList);//通过班级筛选
                        break;
                    case "major":
                        studentScoresList = scoreService.getstudentScoresListByMajor(value,studentScoresList);//通过专业筛选
                        break;
                    case "examDate":
                        studentScoresList = scoreService.getstudentScoresListByExamDate(value,studentScoresList);//通过考试日期筛选
                        break;
                    case "examName":
                        studentScoresList = scoreService.getstudentScoresListByExamName(value,studentScoresList);//通过考试名称筛选
                        break;
                }
            }
        }
        List<StudentScores> studentScoresLists = new ArrayList<>();
        //进行分页
        for (int i = (pageNum -1)*pageSize; i < pageSize*pageNum; i++) {
            if(studentScoresList.size()> i && studentScoresList.get(i) !=null){
                studentScoresLists.add(studentScoresList.get(i));
            }
        }

        StudentScoresDTO studentScoresDTO = new StudentScoresDTO(studentScoresLists,studentScoresList.size());

//        Result page = studentController.findPage(pageNum, pageSize, searchStr);

        return Result.success(studentScoresDTO);
    }

//    @PostMapping("/getStuScorePage")
//    public Result Page(@RequestBody List<Student> studentList) {
//        ArrayList<StudentScores> studentScoresList = new ArrayList<>();
//        studentScoresList = getStudentScoesList(studentList);
//        //获取考试次数
////        List<Examination> examTimes = scoreService.getExamTimes();
////        for (Examination examTime : examTimes) {
////            for (Student student : studentList) {
////                StudentScores studentScores = new StudentScores();
////                //注入学生信息
////                studentScores.setStudent(student);
////                //注入考试成绩
////                studentScores.setScores(scoreService.findScoreByStuIdAndExamDate(student.getId(), examTime.getExamDate()));
////                //注入班级考试排名
////                studentScores.setClassRanking(scoreService.getClassRanking(student.getId(), student.getClassId(), examTime.getExamDate()));
////                //注入年级排名
////                studentScores.setGradeRanking(scoreService.getGradeRanking(student.getId(), student.getGrade(), student.getMajor(), examTime.getExamDate()));
////                //注入考试日期
////                studentScores.setExamDate(examTime.getExamDate());
////                //注入考试名称
////                studentScores.setExamName(examTime.getExamName());
////                List<Score> scoreStuByStuIdAndExamDate = scoreService.findScoreStuByStuIdAndExamDate(student.getId(), examinationService.getDateByName(examTime.getExamName()));
////                if(scoreStuByStuIdAndExamDate.size() > 0){
////                    //将此次考试信息加入列表
////                    studentScoresList.add(studentScores);
////                }
////
////            }
////        }
//
//        return Result.success(studentScoresList);
//    }
//
    public ArrayList<StudentScores> getStudentScoesList(List<Student> studentList){
        ArrayList<StudentScores> studentScoresList = new ArrayList<>();
        //获取考试次数
        List<Examination> examTimes = scoreService.getExamTimes();
        for (Examination examTime : examTimes) {
            for (Student student : studentList) {
                StudentScores studentScores = new StudentScores();
                //注入学生信息
                studentScores.setStudent(student);
                //注入考试成绩
                studentScores.setScores(scoreService.findScoreByStuIdAndExamDate(student.getId(), examTime.getExamDate()));
                //注入班级考试排名
                studentScores.setClassRanking(scoreService.getClassRanking(student.getId(), student.getClassId(), examTime.getExamDate()));
                //注入年级排名
                studentScores.setGradeRanking(scoreService.getGradeRanking(student.getId(), student.getGrade(), student.getMajor(), examTime.getExamDate()));
                //注入考试日期
                studentScores.setExamDate(examTime.getExamDate());
                //注入考试名称
                studentScores.setExamName(examTime.getExamName());
                List<Score> scoreStuByStuIdAndExamDate = scoreService.findScoreStuByStuIdAndExamDate(student.getId(), examinationService.getDateByName(examTime.getExamName()));
                if(scoreStuByStuIdAndExamDate.size() > 0){
                    //将此次考试信息加入列表
                    studentScoresList.add(studentScores);
                }

            }
        }
        return studentScoresList;
    }
//
//
//    @GetMapping("/getScoreTotal")
//    public Result getScoreTotal(){
//        Integer scoreTotal = scoreService.getScoreTotal();
//        return Result.success(scoreTotal);
//    }


    /**、
     * 更新数据
     * @param id
     * @param courseName
     * @param scores
     * @param examDate
     * @return
     */
    @GetMapping("/updataScore")
    public Result updataScore(@RequestParam String id,
                              @RequestParam String courseName,
                              @RequestParam Double scores,
                              @RequestParam Date examDate,
                              @RequestParam String examName) {
        String examId = examinationService.getIdByNameAndDate(examName, examDate);//考试id
        String tableName = "ts_score_"+examId;
        String coureseId = courseService.getCourseIdByName(courseName);
        boolean result = scoreService.updataScore1(tableName,id, coureseId, scores);
        return Result.success(result);

    }

    /**
     * 根据学号，考试名称添加学生成绩
     * @param studentId
     * @param examName
     * @param score
     * @return
     */
    @GetMapping("/addStudentScore")
    public Result addStudentScore(@RequestParam String studentId,  @RequestParam String examName, @RequestParam String score){
        //将成绩提取出来
        String[] parts = score.split(":");
        String key = parts[0];
        double value = Double.parseDouble(parts[1]);
        String coureseId = courseService.getCourseIdByName(key);
        java.sql.Date examData = examinationService.getDateByName(examName);
        Score score1 = new Score(studentId, coureseId, value, examData);
        boolean b = scoreService.addStudentScore(score1);
        if(b){
            return Result.success();
        }else{
            return Result.error("该学生成绩已存在");
        }
    }

    /**
     * 删除根据学号和考试名称
     * @param studentId
     * @param examName
     * @return
     */
    @DeleteMapping("/delStudentScore")
    public Result delStudentScore(@RequestParam String studentId,@RequestParam String examName){
        boolean b = false;
        Date examDate = examinationService.getDateByName(examName);
        List<Score> scoreStuByStuIdAndExamDate = scoreService.findScoreStuByStuIdAndExamDate(studentId, examDate);
        for (Score score : scoreStuByStuIdAndExamDate) {
            b = scoreService.deleteById(score.getId());
        }
        if(b){
            return Result.success("该学生成绩删除");
        }else{
            return Result.error("删除失败");
        }
    }


    /**
     * 获取考试列表
     * @return
     */
    @GetMapping("/getExamList")
    public Result getExamList(){
        List<String> examList = scoreService.getExamList();
        return Result.success(examList);
    }

    /**
     * 导出接口
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询所有的数据
        List<Student> studentList = studentService.findAllOver();
        List<StudentScores> list = getStudentScoesList(studentList);

        //通过工具类创建writer写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");

//        在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("student.id","学号");
        writer.addHeaderAlias("student.name","姓名");
        writer.addHeaderAlias("student.grade","年级");
        writer.addHeaderAlias("student.classId","班级");
        writer.addHeaderAlias("student.major","专业");
        writer.addHeaderAlias("scores","成绩");
        writer.addHeaderAlias("examDate","考试日期");
        writer.addHeaderAlias("examName","考试名称");


        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生成绩信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("学生成绩");
//
//        // Create header row
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("学生姓名");
//        headerRow.createCell(1).setCellValue("班级排名");
//        headerRow.createCell(2).setCellValue("年级排名");
//        headerRow.createCell(3).setCellValue("考试日期");
//        headerRow.createCell(4).setCellValue("考试名称");
////        headerRow.createCell(5).setCellValue("科目");
////        headerRow.createCell(6).setCellValue("成绩");
//
//        // Create data rows
//        for (int i = 0; i < list.size(); i++) {
//            StudentScores score = list.get(i);
//            Row dataRow = sheet.createRow(i + 1);
//            dataRow.createCell(0).setCellValue(score.getStudent().getName());
//            dataRow.createCell(1).setCellValue(score.getClassRanking().toString());
//            dataRow.createCell(2).setCellValue(score.getGradeRanking().toString());
//            dataRow.createCell(3).setCellValue(score.getExamDate().toString() );
//            dataRow.createCell(4).setCellValue(score.getExamName());
//            int cellIndex = 5;
//            for (Map.Entry<String, Double> entry : score.getScores().entrySet()) {
//                headerRow.createCell(cellIndex).setCellValue(entry.getKey());
//                dataRow.createCell(cellIndex).setCellValue(entry.getValue().toString());
//                cellIndex++;
//            }
//        }
//
//        // Auto-size columns and adjust layout
//        for (int i = 0; i < 5; i++) {
//            sheet.autoSizeColumn(i);
//        }
////        sheet.calculateColumnWidths();
//
//        // Save the workbook to a file
//        try (FileOutputStream outputStream = new FileOutputStream("D:/学生成绩.xlsx")) {
//            workbook.write(outputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


    }


}
