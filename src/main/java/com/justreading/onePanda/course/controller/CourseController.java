package com.justreading.onePanda.course.controller;

import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.service.CourseService;
import com.justreading.onePanda.course.service.impl.CourseServiceImpl;
import com.justreading.onePanda.request.RefreshCourseRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LYJ
 * @Description 课程的接口
 * @date 2020 年 02 月 16 日 14:26
 */
@Api(tags = "课程模块")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 目前只放通这个学生查询课表的功能，其他功能定义在业务层，根据爬虫去更新。
     *
     * @param term            学期
     * @param studentUsername 学号
     * @return
     */
    @MyLog("根据学号和学期查询课表:/queryCourse")
    @GetMapping("/queryCourse")
    @ApiOperation("根据学号和学期查询课表")
    @ApiImplicitParams({@ApiImplicitParam(name = "term", value = "学期"), @ApiImplicitParam(name = "studentUsername", value = "学号")})
    public ApiResponse<List<Course>> findCourseByTermAndStudentName(@RequestParam(value = "term", required = true) String term,
                                                                    @RequestParam(value = "studentUsername", required = true) String studentUsername) {
        ApiResponse<List<Course>> courses = courseService.findCourseByTermAndStudentUserName(term, studentUsername);
        return courses;
    }

    @PostMapping("/refreshCourse")
    @ApiOperation("刷新课表")
    public ApiResponse<List<Course>> refreshCourse(@RequestBody(required = true) RefreshCourseRequest request) {
        List<Course> courses = courseService.refreshCourseService(request);
        return new ApiResponse<List<Course>>(200,"刷新成功",courses);
    }
}
