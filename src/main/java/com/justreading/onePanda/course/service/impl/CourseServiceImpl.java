package com.justreading.onePanda.course.service.impl;

import com.github.pagehelper.PageHelper;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.CONSTANT;
import com.justreading.onePanda.common.MyPageInfo;
import com.justreading.onePanda.common.StudentMethod;
import com.justreading.onePanda.common.bean.ReptileCourse;
import com.justreading.onePanda.common.bean.ReptileCourseOption;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.mapper.CourseMapper;
import com.justreading.onePanda.course.service.CourseService;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import io.swagger.models.auth.In;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 16 日 14:21
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMethod studentMethod;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private CourseService courseService;


    @Override
    public ApiResponse<Course> insertCourse(Course course) {
         ApiResponse<Course> apiResponse = new ApiResponse<>();
         if(course.getTerm() != null && course.getStudentUsername() != null){
            courseMapper.insertCourse(course);
            apiResponse.setData(course);
            apiResponse.setMsg("插入成功");
         }else{
             apiResponse.setMsg("插入失败，学期和学号不能为空");
         }
          apiResponse.setCode(200);
         return apiResponse;
    }

    @Override
    public ApiResponse<Integer> insertBatch(List<Course> list) {
        ApiResponse<Integer> apiResponse = new ApiResponse<>();
        if(!ObjectUtils.isEmpty(list)){
            int i = courseMapper.insertCourseBatch(list);
            apiResponse.setData(i);
            apiResponse.setMsg("批量插入成功");
        }
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public ApiResponse<Course> findCourseById(String id) {
        ApiResponse<Course> apiResponse = new ApiResponse<>();
        Course course = courseMapper.findCourseById(id);
        apiResponse.setCode(200);
        apiResponse.setData(course);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    /**
     * 查询到的学期为空即 爬虫去查找并且插入数据库
     * @param term 学期
     * @param studentUserName 学号
     * @return
     */
    @Override
    public ApiResponse<List<Course>> findCourseByTermAndStudentUserName(String term, String studentUserName) {
        ApiResponse<List<Course>> apiResponse = new ApiResponse<>();
        if(term != "" && studentUserName != ""){
            List<Course> courses = courseMapper.findCourseByTermAndStudentUserName(term, studentUserName);
            if(!ObjectUtils.isEmpty(courses)){
                apiResponse.setData(courses);
            }else{
                List<User> userByUsername = userMapper.findUserByUsername(studentUserName);
                if(!ObjectUtils.isEmpty(userByUsername)){
                    String cookie = studentMethod.studentLogin(userByUsername.get(0));
                    ReptileCourseOption reptileCourseOption = new ReptileCourseOption();
                    reptileCourseOption.setXnxq01id(term);
                    ApiResponse<Map<String, Object>> reptileResponse = studentMethod.getStudentCourse(cookie, reptileCourseOption);

                    //爬虫爬回来的课程里面是没有备注的，所以这里要在每个课程加上备注,优先入库再返回
                    List<ReptileCourse> reptileCourses = (List<ReptileCourse>)reptileResponse.getData().get("courses");
                    String bz = (String) reptileResponse.getData().get("bz");
                    List<Course> returnCourse = new ArrayList<>();
                    for (int i = 0; i < reptileCourses.size() ; i++) {
                        ReptileCourse reptileCourse = reptileCourses.get(i);
                        Course course = new Course();
                        course.setStudentUsername(studentUserName);
                        course.setTerm(term);
                        course.setCourseNumber(reptileCourse.getNumber());
                        course.setCourseName(reptileCourse.getName());
                        course.setCourseZc(reptileCourse.getZc());
                        course.setCourseJc(Integer.parseInt(reptileCourse.getJc()));
                        course.setCourseRoom(reptileCourse.getRoom());
                        course.setCourseTeacher(reptileCourse.getTeacher());
                        course.setCourseXq(Integer.parseInt(reptileCourse.getXq()));
                        course.setNote(bz);
                        returnCourse.add(course);
                        courseMapper.insertCourse(course);
                    }

                    //开启插入线程
//                    threadPoolExecutor.execute(() -> {
//                        for(Course course : returnCourse){
//                            courseMapper.insertCourse(course);
//                        }
//                    });

                    //进行这个链接的退出登录
                    studentMethod.studentLogout(cookie);
                    apiResponse.setData(returnCourse);
                 }
            }
            apiResponse.setMsg("查询成功");
        }else{
            apiResponse.setMsg("学期和用户名不能为空");
        }
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public ApiResponse<List<Course>> findCourseByTermAndStudentUserName(String term, String studentUserName, String cookie) {
        ApiResponse<List<Course>> apiResponse = new ApiResponse<>();
        if(term != "" && studentUserName != ""){
            List<Course> courses = courseMapper.findCourseByTermAndStudentUserName(term, studentUserName);
            if(!ObjectUtils.isEmpty(courses)){
                apiResponse.setData(courses);
            }else{
                    ReptileCourseOption reptileCourseOption = new ReptileCourseOption();
                    reptileCourseOption.setXnxq01id(term);
                    ApiResponse<Map<String, Object>> reptileResponse = studentMethod.getStudentCourse(cookie, reptileCourseOption);

                    //爬虫爬回来的课程里面是没有备注的，所以这里要在每个课程加上备注,优先入库再返回
                    List<ReptileCourse> reptileCourses = (List<ReptileCourse>)reptileResponse.getData().get("courses");
                    String bz = (String) reptileResponse.getData().get("bz");
                    List<Course> returnCourse = new ArrayList<>();
                    for (int i = 0; i < reptileCourses.size() ; i++) {
                        ReptileCourse reptileCourse = reptileCourses.get(i);
                        Course course = new Course();
                        course.setStudentUsername(studentUserName);
                        course.setTerm(term);
                        course.setCourseNumber(reptileCourse.getNumber());
                        course.setCourseName(reptileCourse.getName());
                        course.setCourseZc(reptileCourse.getZc());
                        course.setCourseJc(Integer.parseInt(reptileCourse.getJc()));
                        course.setCourseRoom(reptileCourse.getRoom());
                        course.setCourseTeacher(reptileCourse.getTeacher());
                        course.setCourseXq(Integer.parseInt(reptileCourse.getXq()));
                        course.setNote(bz);
                        returnCourse.add(course);
                        courseMapper.insertCourse(course);
                    }

                    //开启插入线程
//                     threadPoolExecutor.execute(() -> {
//                         for(Course course : returnCourse){
//                             courseMapper.insertCourse(course);
//                         }
//                     });

                    //进行这个链接的退出登
                     apiResponse.setData(returnCourse);
                     studentMethod.studentLogout(cookie);
            }
            apiResponse.setMsg("查询成功");
        }else{
            apiResponse.setMsg("学期和用户名不能为空");
        }
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public ApiResponse<MyPageInfo<Course>> findAllCourse(String pageNum,String pageSize) {
        ApiResponse<MyPageInfo<Course>>  apiResponse = new ApiResponse<>();
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<Course> allCourse = courseMapper.findAllCourse();
        MyPageInfo<Course> myPageInfo = new MyPageInfo<>(allCourse);
        apiResponse.setMsg("查询工程");
        apiResponse.setCode(200);
        apiResponse.setData(myPageInfo);
        return apiResponse;
    }

    @Override
    public ApiResponse deleteCourseBatch(List<Integer> list) {
         ApiResponse apiResponse = new ApiResponse();
         courseMapper.deleteCourseBatch(list);
         apiResponse.setCode(200);
         apiResponse.setMsg("删除成功");
         return apiResponse;
    }

    @Override
    public ApiResponse updateCourseBatch(List<Course> courses) {
        ApiResponse apiResponse = new ApiResponse();
        courseMapper.updateCourseBatch(courses);
        apiResponse.setCode(200);
        apiResponse.setMsg("更新成功");
        return apiResponse;
    }
}
