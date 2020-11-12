package com.justreading.onePanda.grade.controller;

import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.grade.entity.Grade;
import com.justreading.onePanda.grade.entity.UserGrade;
import com.justreading.onePanda.grade.service.GradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author LYJ
 * @Description 学生查询成绩接口
 * @date 2020 年 02 月 16 日 17:11
 */
@Api(tags = "成绩的模块")
@RestController
@RequestMapping("/grade")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @MyLog("学生查询成绩：/queryGrade")
    @ApiOperation("学生查询成绩")
    @ApiImplicitParams({@ApiImplicitParam(name = "term", value = "查询的学期"), @ApiImplicitParam(name = "studentUsername", value = "学生的学号")})
    @GetMapping("/queryGrade")
    public ApiResponse<List<Grade>> findGradeByTermAndStudentUsername(@RequestParam(value = "term", required = true) String term,
                                                                      @RequestParam(value = "studentUsername", required = true) String studentUsername) {
        return gradeService.findGradeByTermAndStudentUserName(term, studentUsername);
    }

    @MyLog("老师查询班级成绩:/teacherQueryGrade")
    @ApiOperation("老师查班级成绩")
    @GetMapping("/teacherQueryGrade")
    @ApiImplicitParams({@ApiImplicitParam(name = "majorNumber", value = "老师的专业号"), @ApiImplicitParam(name = "term", value = "学期")})
    public ApiResponse<List<UserGrade>> findGradeByMajorNumberAndTerm(@RequestParam(value = "majorNumber", required = true) String majorNumber,
                                                                      @RequestParam(value = "term", required = true) String term) {

        ApiResponse<List<UserGrade>> userGradeList = gradeService.findGradeByMajorNumberAndTerm(majorNumber, term);
        return userGradeList;
    }

    @MyLog("老师下载文档:/gradeDownLoad")
    @ApiOperation("下载专业学生成绩的表格")
    @GetMapping("/gradeDownLoad")
    public void downLoadGradeExcel(@RequestParam(name = "majorNumber", required = true) String majorNumber,
                                   @RequestParam(name = "term", required = true) String term,
                                   HttpServletResponse response) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(majorNumber);
        String[] headerLineName = {"序号", "学号", "姓名", "科目", "成绩", "绩点"};
        String fileName = majorNumber + "-" + term + ".xls";
        ApiResponse<List<UserGrade>> gradeByMajorNumberAndTerm = gradeService.findGradeByMajorNumberAndTerm(majorNumber, term);
        List<UserGrade> userGradeList = gradeByMajorNumberAndTerm.getData();
        HSSFRow headLine = sheet.createRow(0);
        for (int i = 0; i < headerLineName.length; i++) {
            headLine.createCell(i).setCellValue(new HSSFRichTextString(headerLineName[i]));
        }
        int countRow = 1;
        for (int i = 0; i < userGradeList.size(); i++) {
            UserGrade userGrade = userGradeList.get(i);
            List<Grade> gradeList = userGrade.getGrades();
            for (int j = 0; j < gradeList.size(); j++) {
                Grade grade = gradeList.get(j);
                HSSFRow row = sheet.createRow(countRow++);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(grade.getStudentUsername());
                row.createCell(2).setCellValue(userGrade.getTrueName());
                row.createCell(3).setCellValue(grade.getGradeName());
                row.createCell(4).setCellValue(grade.getScore());
                row.createCell(5).setCellValue(userGrade.getGradePoint());
            }
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("获取学生所有学期对应的成绩")
    @GetMapping("/getStudentAllTermGradeList/{username}")
    public ApiResponse<Map<String, List<Grade>>> getStudentAllTermGradeList(@PathVariable("username") String username) {
        return gradeService.getStudentAllTermGradeListService(username);
    }
}
