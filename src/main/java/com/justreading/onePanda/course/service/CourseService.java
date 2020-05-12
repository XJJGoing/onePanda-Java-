package com.justreading.onePanda.course.service;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.mapper.CourseProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 16 日 14:20
 */
public interface CourseService {
    /**
     * 插入学生的课程，先通过studentName和term查询有没有，存在即更新不存在即插入
     * @param course 学生的课程信息
     * @return
     */
    public ApiResponse<Course> insertCourse(Course course);


    /**
     * 通过id查询学生的课程
     * @param id 课程的id
     * @return
     */
    public ApiResponse<Course> findCourseById(String id);


    /**
     * 通过学期和学生的学号查询学生的课表,提供给学生查询用的
     * @param term 学期
     * @param studentUserName 学号
     * @return
     */
    public ApiResponse<List<Course>> findCourseByTermAndStudentUserName(String term, String studentUserName);


    /**
     * 查询所有学生的课程,并且做分页
     * @return
     */
    public ApiResponse<MyPageInfo<Course>> findAllCourse(String pageNum,String pageSize);


    /**
     * 批量删除课表
     */
    public ApiResponse deleteCourseBatch(List<Integer> list);

    /**
     * 批量更新课表 根据 studentName 和 term 去更新，更新这批数据，后台进行爬虫更新用的
     * @param courses  课表数据
     */
    public ApiResponse updateCourseBatch(@Param("courses") List<Course> courses);
}
