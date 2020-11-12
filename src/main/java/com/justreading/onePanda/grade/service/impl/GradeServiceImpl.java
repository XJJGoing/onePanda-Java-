package com.justreading.onePanda.grade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.CONSTANT;
import com.justreading.onePanda.common.MyUtils;
import com.justreading.onePanda.common.StudentMethod;
import com.justreading.onePanda.common.bean.ReptileCourse;
import com.justreading.onePanda.common.bean.ReptileGrade;
import com.justreading.onePanda.common.bean.ReptileGradeOption;
import com.justreading.onePanda.converter.GradeConverter;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.grade.entity.Grade;
import com.justreading.onePanda.grade.entity.UserGrade;
import com.justreading.onePanda.grade.mapper.*;
import com.justreading.onePanda.grade.service.GradeService;
import com.justreading.onePanda.rules.Dto.StudentGradeDto;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author LYJ
 * @Description 成绩的接口的实现
 * @date 2020 年 02 月 16 日 16:25
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper,Grade> implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;


    @Autowired
    private StudentMethod studentMethod;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyUtils myUtils;

    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * 插入学生的grade
     *
     * @param grade 成绩
     * @return
     */
    @MyLog(value = "插入学生成绩")
    @Override
    public ApiResponse<Grade> insertGrade(Grade grade) {
        ApiResponse<Grade> apiResponse = new ApiResponse<>();
        if (grade.getStudentUsername() != "" && grade.getTerm() != "") {
            gradeMapper.insertGrade(grade);
            apiResponse.setData(grade);
            apiResponse.setMsg("插入成功");
        } else {
            apiResponse.setMsg("插入失败,学号和学期不能为空");
        }
        apiResponse.setCode(200);
        return apiResponse;
    }

    /**
     * 成绩的查询,没有改学期的成绩的时候，直接爬虫去获取并且入库
     *
     * @param term            学期
     * @param studentUsername 学生的学号
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<List<Grade>> findGradeByTermAndStudentUserName(String term, String studentUsername) {
        List<User> userList = userMapper.findUserByUsername(studentUsername);
        String cookie = studentMethod.studentLogin(userList.get(userList.size() - 1));
        List<Grade> resGrade = null;
        if (ObjectUtils.isEmpty(cookie)) {
            resGrade = gradeMapper.findGradeByTermAndStudentUserName(term, studentUsername);
        } else {
            StudentGradeDto studentGradeDto = studentMethod.getStudentGradeDtoByTowMethod(cookie, ReptileGradeOption.builder().kksj(term).build());
            List<Grade> grades = GradeConverter.converterGrade(studentGradeDto, studentUsername, term);
            if(ObjectUtils.isEmpty(grades)){
                grades = gradeMapper.findGradeByTermAndStudentUserName(term, studentUsername);
            } else {
                final List<Grade> finalGrades = grades;
                executor.execute(() -> {    //开启一个线程去访问,及时更新成绩(删除更新)
                    List<Grade> dbGradeList = gradeMapper.findGradeByTermAndStudentUserName(term, studentUsername);
                    List<Grade> newGrade = new ArrayList<>();
                    for(Grade grade : finalGrades){
                        boolean flag = false;
                        for(Grade dbGrade : dbGradeList){
                            if(grade.getGradeNumber().equals(dbGrade.getGradeNumber())){
                                grade.setId(dbGrade.getId());
                                gradeMapper.updateById(grade);
                                flag = true;
                            }
                        }
                        if(!flag){
                            newGrade.add(grade);
                        }
                    }
                    saveBatch(newGrade);
                });
            }
            resGrade = grades;
        }
        return new ApiResponse<List<Grade>>(200,"查询成功",resGrade);
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
     * 辅导员或者学业老师根据专业号和学期查询学生的成绩,并且整个成一个UserGrade数组，实现1对多
     *
     * @param majorNumber 专业号
     * @param term        学期
     * @return
     */
    @Override
    public ApiResponse<List<UserGrade>> findGradeByMajorNumberAndTerm(String majorNumber, String term) {
        ApiResponse<List<UserGrade>> apiResponse = new ApiResponse<>();
        List<Grade> grades = gradeMapper.findGradeByMajorNumberAndTerm(majorNumber, term);
        List<UserGrade> userGradeList = new ArrayList<>();
        Integer id = 1;
        List<String> usernameList = grades.stream().map(value -> {
            return value.getStudentUsername();
        }).distinct().collect(Collectors.toList());
        for (String username : usernameList) {
            UserGrade userGrade = new UserGrade();
            List<User> students = userMapper.findUserByUsername(username);
            if (!ObjectUtils.isEmpty(students)) {
                userGrade.setUsername(students.get(0).getUsername());
                userGrade.setTrueName(students.get(0).getTrueName());
            } else {
                continue;
            }
            userGrade.setId(id++);
            List<Grade> gradeList = new ArrayList<>();
            for (Grade grade : grades) {
                if (grade.getStudentUsername().equals(username)) {
                    gradeList.add(grade);
                }
            }
            userGrade.setGrades(gradeList);
            userGradeList.add(userGrade);
        }
        List<UserGrade> returnUserGrades = myUtils.calculateGradePoint(userGradeList);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        apiResponse.setData(returnUserGrades);
        return apiResponse;
    }

    @Override
    public ApiResponse<Map<String, List<Grade>>> getStudentAllTermGradeListService(String username) {
        Map<String,List<Grade>> map = null;
        List<Grade> allGrade = gradeMapper.findAllGrade(username);
        if(allGrade.size() != 0){
            map = new HashMap<>();
            for (Grade grade : allGrade){
                List<Grade> list = map.getOrDefault(grade.getTerm(), null);
                if(list != null){
                    list.add(grade);
                    map.put(grade.getTerm(),list);
                }else{
                    list = new ArrayList<>();
                    list.add(grade);
                    map.put(grade.getTerm(),list);
                }
            }
        }
        return new ApiResponse<>(200,"查询成功",map);
    }
}
