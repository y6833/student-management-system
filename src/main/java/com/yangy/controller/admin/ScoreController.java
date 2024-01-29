package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.entity.*;
import com.yangy.service.*;
import com.yangy.util.Pagetool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
                switch (key) {
                    case "id":
                        studentScoresList = scoreService.getstudentScoresListByStudentId(value, studentScoresList); //通过学号筛选
                        break;
                    case "name":
                        studentScoresList = scoreService.getstudentScoresListByStudentName(value, studentScoresList);//通过姓名筛选
                        break;
                    case "gender":
                        studentScoresList = scoreService.getstudentScoresListByStudentGender(value, studentScoresList);//通过性别筛选
                        break;
                    case "grade":
                        studentScoresList = scoreService.getstudentScoresListByGrade(value, studentScoresList);//通过年级筛选
                        break;
                    case "class_id":
                        studentScoresList = scoreService.getstudentScoresListByClassId(value, studentScoresList);//通过班级筛选
                        break;
                    case "major":
                        studentScoresList = scoreService.getstudentScoresListByMajor(value, studentScoresList);//通过专业筛选
                        break;
                    case "examDate":
                        studentScoresList = scoreService.getstudentScoresListByExamDate(value, studentScoresList);//通过考试日期筛选
                        break;
                    case "examName":
                        studentScoresList = scoreService.getstudentScoresListByExamName(value, studentScoresList);//通过考试名称筛选
                        break;
                }
            }
        }
        List<StudentScores> studentScoresLists = new ArrayList<>();
        //进行分页
        for (int i = (pageNum - 1) * pageSize; i < pageSize * pageNum; i++) {
            if (studentScoresList.size() > i && studentScoresList.get(i) != null) {
                studentScoresLists.add(studentScoresList.get(i));
            }
        }

        StudentScoresDTO studentScoresDTO = new StudentScoresDTO(studentScoresLists, studentScoresList.size());

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
    public ArrayList<StudentScores> getStudentScoesList(List<Student> studentList) {
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
                if (scoreStuByStuIdAndExamDate.size() > 0) {
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

    /**
     * 更新评语
     *
     * @param id
     * @param proposal
     * @param examDate
     * @param examName
     * @return
     */
    @GetMapping("/updataProposal")
    public Result updataProposal(@RequestParam String id,
                                 @RequestParam String proposal,
                                 @RequestParam Date examDate,
                                 @RequestParam String examName) {
        String examId = examinationService.getIdByNameAndDate(examName, examDate);//考试id
        String tableName = "ts_score_" + examId;
        boolean b = scoreService.updataProposal(tableName, id, proposal);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 、
     * 更新数据
     *
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
        String tableName = "ts_score_" + examId;
        String coureseId = courseService.getCourseIdByName(courseName);
        boolean result = scoreService.updataScore1(tableName, id, coureseId, scores);
        return Result.success(result);

    }

    /**
     * 根据学号，考试名称添加学生成绩
     *
     * @param studentId
     * @param examName
     * @param scoreList
     * @return
     */
    @GetMapping("/addStudentScore")
    public Result addStudentScore(@RequestParam String studentId, @RequestParam String examName, @RequestParam String scoreList) {

        //通过考试名称获取考试成绩表名
        String examId = examinationService.getIdByExamName(examName); //考试id
        //判断这个学生是不是考试年级的
        if (!studentService.getStudentById(studentId).getGrade().equals(examinationService.getExamById(examId).getExamGrade())) {
            return Result.error("该学生不能添加到这个年级的考试");
        }
        String scoreId = examId + "_" + studentId;//成绩id
        String studentName = studentService.getStudentById(studentId).getName();//学生姓名
        String studentClass = studentService.getStudentById(studentId).getClassId();//获取班级id
        String tableName = "ts_score_" + examId;
        // 创建学生创建信息
        boolean b2 = scoreService.createScore(scoreId, examId, studentId, studentName, studentClass, tableName);

        if (b2) {
            Boolean b = true;
            scoreList = scoreList.replaceAll("\"", ""); // 去掉双引号
            String[] arr = scoreList.split(","); // 使用逗号分割字符串
            List<String> scoreLists = Arrays.asList(arr); // 将数组转换为集合
            for (String score : scoreLists) {
                //将成绩提取出来
                String[] parts = score.split(":");
                String key = courseService.getCourseIdByName(parts[0]);
                double value = Double.parseDouble(parts[1]);
                //将这个科目的成绩添加到数据库
                boolean b3 = scoreService.addScore(tableName, scoreId, key, value);
                b = b & b3;
                if (b == false) {
                    return Result.error(key + "成绩添加失败");
                }
            }
            return Result.success();
        } else {
            return Result.error("该学生创建信息已存在");
        }

    }

    /**
     * 删除根据学号和考试名称
     *
     * @param studentId
     * @param examName
     * @return
     */
    @DeleteMapping("/delStudentScore")
    public Result delStudentScore(@RequestParam String studentId, @RequestParam String examName) {
        boolean b = false;
        //通过考试名称获取表名
        String examId = examinationService.getIdByExamName(examName);
        String tableName = "ts_score_" + examId;
        String scoreId = examId + "_" + studentId;
        b = scoreService.deleteByScoreId(tableName, scoreId);
        if (b) {
            return Result.success("该学生成绩删除");
        } else {
            return Result.error("删除失败");
        }
    }


    /**
     * 获取考试列表
     *
     * @return
     */
    @GetMapping("/getExamList")
    public Result getExamList() {
        List<String> examList = scoreService.getExamList();
        return Result.success(examList);
    }

    /**
     * 导出接口
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public Result export(HttpServletResponse response, String examName) throws Exception {
        //根据考试名称获取成绩单数据库
        String examId = examinationService.getIdByExamName(examName);
        String tableName = "ts_score_" + examId;
        Examination examById = examinationService.getExamById(examId);
        List<StudentScores> list = scoreService.getScoreMessageByTableName(examById);
        //通过表名获取科目名称
        List<String> subjectList = scoreService.getSubjectListByTableName(tableName);


        //直接写出到磁盘路径
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("学生成绩");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("学号");
        headerRow.createCell(1).setCellValue("学生姓名");
        headerRow.createCell(2).setCellValue("班级");
        int rowi = 3;
        for (int i = 0; i < subjectList.size(); i++) {
            headerRow.createCell(rowi).setCellValue(courseService.getCourseNameById(subjectList.get(i)));
            rowi++;
        }
        headerRow.createCell(rowi++).setCellValue("班级排名");
        headerRow.createCell(rowi++).setCellValue("年级排名");
        headerRow.createCell(rowi++).setCellValue("考试日期");
        headerRow.createCell(rowi++).setCellValue("考试名称");


        // Create data rows
        for (int i = 0; i < list.size(); i++) {
            StudentScores score = list.get(i);
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(score.getStudent().getId());
            dataRow.createCell(1).setCellValue(score.getStudent().getName());
            dataRow.createCell(2).setCellValue(score.getStudent().getClassId());
            int cellIndex = 3;
            for (Map.Entry<String, Double> entry : score.getScores().entrySet()) {
                dataRow.createCell(cellIndex).setCellValue(entry.getValue().toString());
                cellIndex++;
            }
            dataRow.createCell(cellIndex).setCellValue(score.getClassRanking().toString());
            dataRow.createCell(cellIndex + 1).setCellValue(score.getGradeRanking().toString());
            dataRow.createCell(cellIndex + 2).setCellValue(score.getExamDate().toString());
            dataRow.createCell(cellIndex + 3).setCellValue(score.getExamName());
        }


        for (int i = 0; i < rowi; i++) {
            sheet.autoSizeColumn(i);
        }
//        sheet.calculateColumnWidths();

        // Save the workbook to a file
        try (FileOutputStream outputStream = new FileOutputStream("D:/学生成绩.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.success();

    }

    /**
     * 导入接口
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            //获取科目列表
            ArrayList<String> subjectList = new ArrayList<>();
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.hasNext();
            Row head = rowIterator.next();
            Iterator<Cell> headIterator = head.iterator();

            while (headIterator != null) {
                String value = "";
                try {
                    Cell next = headIterator.next();
                    value = getCellValueAsString(next);
                } catch (Exception e) {
                    break;
                }
                //找出表头的科目 ---->可以将所有的字段调用科目表，有这个科目就返回true就加入
                switch (value) {
                    case "学号":
                    case "学生姓名":
                    case "班级":
                    case "考试名称":
                        break;
                    default:
                        subjectList.add(courseService.getCourseIdByName(value));
                }
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                String studentId = getCellValueAsString(cellIterator.next());
                String studentName = getCellValueAsString(cellIterator.next());
                String className = getCellValueAsString(cellIterator.next());
                //通过班级名称获得班级id
                String classId = classService.getIdByclassName(className);
                String examName = getCellValueAsString(cellIterator.next());
                //通过考试名称获得考试id
                String examId = examinationService.getIdByExamName(examName);
                String tableName = "ts_score_" + examId; //考试成绩表
                String scoreId = examId + "_" + studentId;
                //创建成绩信息
                scoreService.createScore(scoreId, examId, studentId, studentName, classId, tableName);
                for (String subject : subjectList) {
                    scoreService.addScore(tableName, scoreId, subject, Double.parseDouble(getCellValueAsString(cellIterator.next())));
                }

            }
            workbook.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getCellValueAsString(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    break;
            }
        }
        return cellValue;
    }


    //获得考试平均分列表

    @GetMapping("/getAveTableData")
    public Result getAveTableData(@RequestParam String examValue,
                                  @RequestParam String gradeValue,
                                  @RequestParam String majorValue,
                                  @RequestParam String choiceSubject) {


        //判断这场考试存不存在

        //如果专业为空只要找在这张考试表中寻找年级为这个的学生
        List<AveScoreDTO> aveTable = scoreService.getAveTable(examValue, gradeValue, majorValue, choiceSubject);
        if (aveTable == null) {
            return Result.error("考试不存在");
        }
        return Result.success(aveTable);
    }

    //获取考试科目
    @GetMapping("/getSubjectListByExamNameAndGradeAndMajor")
    public Result getSubjectListByExamNameAndGradeAndMajor(@RequestParam String examValue,
                                                           @RequestParam String gradeValue,
                                                           @RequestParam String majorValue
    ) {
        try {
            List<String> subjectList = examinationService.getSubjectListByExamNameAndGradeAndMajor(examValue, gradeValue, majorValue);
            if (subjectList == null) {
                return Result.error("考试不存在");
            }
            return Result.success(subjectList);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    /**
     * 得到年级考试人数
     * @param examValue
     * @param gradeValue
     * @param majorValue
     * @param choiceSubject
     * @return
     */
    @GetMapping("/getGradeNum")
    public Result getGradeNum(@RequestParam String examValue,
                              @RequestParam String gradeValue,
                              @RequestParam String majorValue,
                              @RequestParam String choiceSubject) {
        try {
            GradeNumDTO gradeNumDTO = scoreService.getGradeNum(examValue, gradeValue, majorValue, choiceSubject);
            if (gradeNumDTO == null) {
                return Result.error("考试不存在");
            }
            return Result.success(gradeNumDTO);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    /**
     * 班级考试人数
     * @param examValue
     * @param gradeValue
     * @param majorValue
     * @param classValue
     * @param choiceSubject
     * @return
     */
    @GetMapping("/getClassNum")
    public Result getClassNum(@RequestParam String examValue,
                              @RequestParam String gradeValue,
                              @RequestParam String majorValue,
                              @RequestParam String classValue,
                              @RequestParam String choiceSubject) {
        try {
            GradeNumDTO gradeNumDTO = scoreService.getClassNum(examValue, gradeValue, majorValue,classValue, choiceSubject);
            if (gradeNumDTO == null) {
                return Result.error("考试不存在");
            }
            return Result.success(gradeNumDTO);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    /**
     * 通过科目获取横坐标
     *
     * @param examValue
     * @param gradeValue
     * @param majorValue
     * @param choiceSubject
     * @return
     */
    @GetMapping("/getAbscissa")
    public Result getAbscissa(@RequestParam String examValue,
                              @RequestParam String gradeValue,
                              @RequestParam String majorValue,
                              @RequestParam String choiceSubject) {

        //通过班级获得专业
        List<Integer> abscissa = scoreService.getAbscissa(examValue, gradeValue, majorValue, choiceSubject);
        return Result.success(abscissa);


    }
    /**
     * 通过科目获取横坐标
     *
     * @param choiceSubject
     * @return
     */
    @GetMapping("/getAbscissa1")
    public Result getAbscissa1(@RequestParam String classValue,
                               @RequestParam String examValue,
                              @RequestParam String choiceSubject) {
        String gradeId = classService.getGradeIdByclassName(classValue);
//        String classId = classService.getIdByclassName(classValue);
        String majorId = classService.getMajorIdByclassName(classValue);
        String majorName = majorService.getMajorName(majorId);
        //通过班级获得专业
        List<Integer> abscissa = scoreService.getAbscissa(examValue, gradeId, majorName,choiceSubject);
        return Result.success(abscissa);


    }

    /**
     * 获取分数集合
     *
     * @param examValue
     * @param classValue
     * @param choiceSubject
     * @return
     */
    @GetMapping("/getScoreListByExamAndClassAndSubject")
    public Result getScoreListByExamAndClassAndSubject(@RequestParam String examValue,
                                                       @RequestParam String classValue,
                                                       @RequestParam String choiceSubject) {
        List<Integer> abscissa = scoreService.getScoreListByExamAndClassAndSubject(examValue,classValue, choiceSubject);
        return Result.success(abscissa);
    }


    /**
     * 获取分数集合
     *
     * @param examValue
     * @param gradeValue
     * @param majorValue
     * @param choiceSubject
     * @return
     */
    @GetMapping("/getScoreListByExamAndGradeAndSubject")
    public Result getScoreListByExamAndGradeAndSubject(@RequestParam String examValue,
                                                       @RequestParam String gradeValue,
                                                       @RequestParam String majorValue,
                                                       @RequestParam String choiceSubject) {
        List<Integer> abscissa = scoreService.getScoreListByExamAndGradeAndSubject(examValue, gradeValue, majorValue, choiceSubject);
        return Result.success(abscissa);
    }


    @GetMapping("/getStuScoreGradeRankList")
    public Result getStuScoreGradeRankList(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String rankingRange,
                                           @RequestParam String examValue,
                                           @RequestParam String gradeValue,
                                           @RequestParam String majorValue,
                                           @RequestParam String choiceSubject) {

        List<StudentScores> studentScoresList = scoreService.getAll();
        String[] parts = rankingRange.split("-");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);
        //查看这条数据是不是这场考试的
        studentScoresList = scoreService.getstudentScoresListByExamName(examValue, studentScoresList);
        //查看这条数据是不是这个年级的
        studentScoresList = scoreService.getstudentScoresListByGrade(gradeValue, studentScoresList);
        //查看这条数据是不是这个专业的
        if (!"".equals(majorValue)) {
            studentScoresList = scoreService.getstudentScoresListByMajor(majorValue, studentScoresList);
        }
        //重置这条数据的成绩值
        studentScoresList = scoreService.getstudentScoresListBySubject(choiceSubject, studentScoresList);

        //通过排名筛选
        studentScoresList = scoreService.getstudentScoresListByRankingRange(num1,num2,choiceSubject,studentScoresList);


        List<StudentScores> studentScoresLists = new ArrayList<>();
        //进行分页
        for (int i = (pageNum - 1) * pageSize; i < pageSize * pageNum; i++) {
            if (studentScoresList.size() > i && studentScoresList.get(i) != null) {
                studentScoresLists.add(studentScoresList.get(i));
            }
        }

        StudentScoresDTO studentScoresDTO = new StudentScoresDTO(studentScoresLists, studentScoresLists.size());

//        Result page = studentController.findPage(pageNum, pageSize, searchStr);

        return Result.success(studentScoresDTO);
    }


    @GetMapping("/getStuScoreClassRankList")
    public Result getStuScoreClassRankList(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String rankingRange,
                                           @RequestParam String examValue,
                                           @RequestParam String gradeValue,
                                           @RequestParam String majorValue,
                                           @RequestParam String classValue,
                                           @RequestParam String choiceSubject) {

        List<StudentScores> studentScoresList = scoreService.getAll();
        String[] parts = rankingRange.split("-");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);
        //查看这条数据是不是这场考试的
        studentScoresList = scoreService.getstudentScoresListByExamName(examValue, studentScoresList);
        //查看这条数据是不是这个年级的
        studentScoresList = scoreService.getstudentScoresListByGrade(gradeValue, studentScoresList);
        //查看这条数据是不是这个专业的
        if (!"".equals(majorValue)) {
            studentScoresList = scoreService.getstudentScoresListByMajor(majorValue, studentScoresList);
        }
        //查了这条数据是不是这个班级
        studentScoresList = scoreService.getstudentScoresListByClass(classValue, studentScoresList);
        //重置这条数据的成绩值
        studentScoresList = scoreService.getstudentScoresListBySubject(choiceSubject, studentScoresList);

        //通过排名筛选
        studentScoresList = scoreService.getstudentScoresListByRankingRange(num1,num2,choiceSubject,studentScoresList);


        List<StudentScores> studentScoresLists = new ArrayList<>();
        //进行分页
        for (int i = (pageNum - 1) * pageSize; i < pageSize * pageNum; i++) {
            if (studentScoresList.size() > i && studentScoresList.get(i) != null) {
                studentScoresLists.add(studentScoresList.get(i));
            }
        }

        StudentScoresDTO studentScoresDTO = new StudentScoresDTO(studentScoresLists, studentScoresLists.size());

//        Result page = studentController.findPage(pageNum, pageSize, searchStr);

        return Result.success(studentScoresDTO);
    }



    @GetMapping("/getExamClassAve")
    public Result getExamClassAve(@RequestParam String classValue,
                                  @RequestParam String choiceSubject){
        //获得班级id
        //通过班级名称获得年级专业id
        String gradeId = classService.getGradeIdByclassName(classValue);
        String classId = classService.getIdByclassName(classValue);
        String majorId = classService.getMajorIdByclassName(classValue);
        String majorName = majorService.getMajorName(majorId);
        //通过年级和专业获得考试名称
       List<String> examNameList = examinationService.getExamListksByGradeIdAndMajorId(gradeId,majorId);
       Map<String,Double> examClassAve =  scoreService.getExamClassAve(gradeId,majorName,classId,examNameList,choiceSubject);
    return Result.success(examClassAve);

    }

}
