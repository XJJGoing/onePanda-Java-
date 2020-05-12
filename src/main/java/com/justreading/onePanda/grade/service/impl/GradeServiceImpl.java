package com.justreading.onePanda.grade.service.impl;

import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyUtils;
import com.justreading.onePanda.common.StudentMethod;
import com.justreading.onePanda.common.bean.ReptileCourse;
import com.justreading.onePanda.common.bean.ReptileGrade;
import com.justreading.onePanda.common.bean.ReptileGradeOption;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.grade.entity.Grade;
import com.justreading.onePanda.grade.entity.UserGrade;
import com.justreading.onePanda.grade.mapper.GradeMapper;
import com.justreading.onePanda.grade.service.GradeService;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @Description 成绩的接口的实现
 * @date 2020 年 02 月 16 日 16:25
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;


    @Autowired
    private StudentMethod studentMethod;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyUtils myUtils;

    /**
     * 插入学生的grade
     * @param grade 成绩
     * @return
     */
    @MyLog(value = "插入学生成绩")
    @Override
    public ApiResponse<Grade> insertGrade(Grade grade) {
        ApiResponse<Grade> apiResponse = new ApiResponse<>();
        if(grade.getStudentUsername() != "" && grade.getTerm() != ""){
            gradeMapper.insertGrade(grade);
            apiResponse.setData(grade);
            apiResponse.setMsg("插入成功");
        }else{
            apiResponse.setMsg("插入失败,学号和学期不能为空");
        }
        apiResponse.setCode(200);
        return apiResponse;
    }

    /**
     * 成绩的查询,没有改学期的成绩的时候，直接爬虫去获取并且入库
     * @param term 学期
     * @param studentUsername 学生的学号
     * @return
     */
    @Override
    public ApiResponse<List<Grade>> findGradeByTermAndStudentUserName(String term, String studentUsername) {
        ApiResponse<List<Grade>> apiResponse = new ApiResponse<>();
        List<Grade> grades = gradeMapper.findGradeByTermAndStudentUserName(term, studentUsername);
        if(!ObjectUtils.isEmpty(grades)){
           apiResponse.setData(grades);
        }else{
            List<User> userByUsername = userMapper.findUserByUsername(studentUsername);
            String cookie = studentMethod.studentLogin(userByUsername.get(0));
            ReptileGradeOption reptileGradeOption = new ReptileGradeOption();
            reptileGradeOption.setKksj(term);
            ApiResponse<List<ReptileGrade>> reptileResponse = studentMethod.getStudentGrade(cookie, reptileGradeOption);

            List<ReptileGrade> reptileGrades = reptileResponse.getData();
            List<Grade> returnGradeList = new ArrayList<>();
            for (int i = 0; i < reptileGrades.size() ; i++) {
                ReptileGrade reptileGrade = reptileGrades.get(i);
                Grade grade = new Grade();
                grade.setStudentUsername(studentUsername);
                grade.setTerm(term);
                grade.setGradeNumber(reptileGrade.getCourseNumber());
                grade.setGradeName(reptileGrade.getCourseName());
                grade.setGradeTime(reptileGrade.getTime());
                grade.setGradeCredit(Double.parseDouble(reptileGrade.getCredit()));
                grade.setExamMethod(reptileGrade.getExamMethod());
                grade.setScore(reptileGrade.getGrade());
                grade.setGradeKind(reptileGrade.getCourseKind());
                gradeMapper.insertGrade(grade);
                returnGradeList.add(grade);
            }

            //进行这个链接的退出登录
            studentMethod.studentLogout(cookie);
            apiResponse.setData(returnGradeList);
        }
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return  apiResponse;
    }

    @Override
    public ApiResponse<Grade> findGradeById(String id) {
       ApiResponse<Grade> apiResponse = new ApiResponse<>();
        Grade grade = gradeMapper.findGradeById(id);
        apiResponse.setData(grade);
        apiResponse.setMsg("查询成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public ApiResponse deleteGradeBatch(List<Integer> list) {
        ApiResponse apiResponse = new ApiResponse();
        gradeMapper.deleteGradeBatch(list);
        apiResponse.setCode(200);
        apiResponse.setMsg("删除成功");
        return apiResponse;
    }

    @Override
    public ApiResponse updateGradeBatch(List<Grade> grades) {
        ApiResponse apiResponse = new ApiResponse();
        gradeMapper.updateGradeBatch(grades);
        apiResponse.setMsg("更新成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    /**
     *  辅导员或者学业老师根据专业号和学期查询学生的成绩,并且整个成一个UserGrade数组，实现1对多
     * @param majorNumber 专业号
     * @param term 学期
     * @return
     */
    @Override
    public ApiResponse<List<UserGrade>> findGradeByMajorNumberAndTerm(String majorNumber, String term) {
        ApiResponse<List<UserGrade>> apiResponse = new ApiResponse<>();
        List<Grade> grades = gradeMapper.findGradeByMajorNumberAndTerm(majorNumber,term);
        List<UserGrade> userGradeList = new ArrayList<>();
        Integer id = 1;
        for (int i = 0; i < grades.size()  ; i++) {
            boolean flag = false;
            for(int j = 0; j < userGradeList.size(); j++){
                if(userGradeList.get(j).getUsername().equals(grades.get(i).getStudentUsername())){
                    List<Grade> beforeGradeList = userGradeList.get(j).getGrades();
                    beforeGradeList.add(grades.get(i));
                    userGradeList.get(j).setGrades(beforeGradeList);
                    flag = true;
                }
            }
            if(!flag){
                UserGrade userGrade = new UserGrade();
                userGrade.setId(id++);
                userGrade.setUsername(grades.get(i).getStudentUsername());
                List<User> students = userMapper.findUserByUsername(grades.get(i).getStudentUsername());
                String studentTrueName = students.get(0).getTrueName();
                userGrade.setTrueName(studentTrueName);
                List<Grade> beforeGrade = userGrade.getGrades();
                beforeGrade.add(grades.get(i));
                userGrade.setGrades(beforeGrade);
                userGradeList.add(userGrade);
            }
        }
        List<UserGrade> returnUserGrades = myUtils.calculateGradePoint(userGradeList);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        apiResponse.setData(returnUserGrades);
        return apiResponse;
    }



}
