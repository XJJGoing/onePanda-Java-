package com.justreading.onePanda.grade.service;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.grade.entity.Grade;
import com.justreading.onePanda.grade.entity.UserGrade;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author LYJ
 * @Description 成绩业务接口
 * @date 2020 年 02 月 16 日 16:25
 */
public interface GradeService {
    /**
     * 插入成绩
     * @param grade 成绩
     * @return
     */
    public ApiResponse<Grade> insertGrade(Grade grade);

    /**
     * 通过学期和学生的学号查询成绩,并且计算总绩点
     * @param term 学期
     * @param studentUsername 学生的学号
     * @return
     */
    public ApiResponse<List<Grade>> findGradeByTermAndStudentUserName(String term,String studentUsername);

    /**
     * 通过id查询 成绩
     * @param id 成绩的id
     * @return
     */
    public ApiResponse<Grade> findGradeById(String id);

    /**
     * 批量删除成绩
     * @param list 要删除的成绩的id
     * @return
     */
    public ApiResponse deleteGradeBatch(List<Integer> list);

    /**
     * 批量更新成绩
     * @param grades 批量更新的用户
     * @return
     */
    public ApiResponse updateGradeBatch(List<Grade> grades);

    /**
     * 辅导员或者班长查询成绩
     * @param majorNumber 专业号
     * @param term 学期
     * @return
     */
    public ApiResponse<List<UserGrade>>  findGradeByMajorNumberAndTerm(String majorNumber, String term);
}
