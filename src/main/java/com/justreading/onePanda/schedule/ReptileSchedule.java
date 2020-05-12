package com.justreading.onePanda.schedule;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.CONSTANT;
import com.justreading.onePanda.common.StudentMethod;
import com.justreading.onePanda.common.bean.ReptileCourse;
import com.justreading.onePanda.common.bean.ReptileCourseOption;
import com.justreading.onePanda.common.bean.ReptileGrade;
import com.justreading.onePanda.common.bean.ReptileGradeOption;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.service.CourseService;
import com.justreading.onePanda.grade.entity.Grade;
import com.justreading.onePanda.grade.service.GradeService;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import com.justreading.onePanda.user.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author LYJ
 * @Description 爬虫定时器,进行定时的爬虫，为保证实时性，建议2小时轮询一次
 * @date 2020 年 02 月 16 日 18:31
 */
@Component
public class ReptileSchedule {
    @Autowired
    private UserService userService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentMethod studentMethod;



    /**
     * 定期爬取成绩,并且对学号和密码进行去重处理
     */
    @Scheduled(cron = "0 0 23 0/1 * *")
    public void getGrade(){
        List<User> allUser = userService.findAllUserUsernameAndPassword();
        List<User> removeOverlapUsernameAndPassword = new ArrayList<>();
        for(int i = 0; i < allUser.size() ;i++){
            boolean flag = false;
            for(int j = i - 1; j >= 0 ;j-- ){
                if(allUser.get(i).getUsername().equals(allUser.get(j).getUsername())
                        && allUser.get(i).getPassword().equals(allUser.get(j).getPassword()))
                {
                    flag = true;
                }
            }
            if(!flag){
                removeOverlapUsernameAndPassword.add(allUser.get(i));
            }
        }
        if(removeOverlapUsernameAndPassword.size()>0){
            for(User user : removeOverlapUsernameAndPassword){
                String cookie = studentMethod.studentLogin(user);
                if(cookie.length() != 0){

                    //进行爬取成绩
                    ReptileGradeOption gradeOption = new ReptileGradeOption();
                    gradeOption.setKksj(CONSTANT.DEFAULT_TERM.getDefaultTerm());
                    ApiResponse<List<ReptileGrade>> studentGrade = studentMethod.getStudentGrade(cookie, gradeOption);
                    List<ReptileGrade> reptileList = studentGrade.getData();
                    List<Grade> grades = new ArrayList<>();
                    for (int i = 0; i < reptileList.size() ; i++) {
                        ReptileGrade reptileGrade = reptileList.get(i);
                        Grade grade = new Grade();
                        grade.setStudentUsername(user.getUsername());
                        grade.setTerm(CONSTANT.DEFAULT_TERM.getDefaultTerm());
                        grade.setGradeNumber(reptileGrade.getCourseNumber());
                        grade.setGradeName(reptileGrade.getCourseName());
                        grade.setExamMethod(reptileGrade.getExamMethod());
                        grade.setGradeCredit(Double.parseDouble(reptileGrade.getCredit()));
                        grade.setScore(reptileGrade.getGrade());
                        grade.setGradeTime(reptileGrade.getTime());
                        grades.add(grade);
                    }

                    //存在即更新，不存在就插入
                    List<Grade> beforeGradeList = gradeService.findGradeByTermAndStudentUserName(CONSTANT.DEFAULT_TERM.getDefaultTerm(), user.getUsername()).getData();
                    if(beforeGradeList.size() != 0 && grades.size() != 0 && beforeGradeList.size() < grades.size()){  //有新数据，且旧数据不为0,直接先删除，删除再插入
                        List<Integer> deleteIds = new ArrayList<>();
                        for (int i = 0; i < beforeGradeList.size(); i++) {
                            deleteIds.add(beforeGradeList.get(i).getId());
                        }
                        gradeService.deleteGradeBatch(deleteIds);

                        for (int i = 0; i < grades.size() ; i++) {
                            gradeService.insertGrade(grades.get(i));
                        }
                    }
                    if(beforeGradeList.size() == grades.size() && !ObjectUtils.isEmpty(beforeGradeList) && !ObjectUtils.isEmpty(grades)){             //无新数据，对源数据进行更新,直接根据id进行全部的更新
                        List<Grade> newGrades = new ArrayList<>();
                        for (int i = 0; i < grades.size();i++){
                            Grade newGrade = grades.get(i);
                            Grade beforeGrade = beforeGradeList.get(i);
                            newGrade.setId(beforeGrade.getId());
                            newGrades.add(newGrade);
                        }
                        gradeService.updateGradeBatch(newGrades);  //批量更新
                    }
                    if (grades.size() != 0 && beforeGradeList.size() == 0){   //新数据不为0，旧数据为0 直接插入
                        for (int i = 0; i < grades.size() ; i++) {
                            Grade grade = grades.get(i);
                            gradeService.insertGrade(grade);
                        }
                    }
                    studentMethod.studentLogout(cookie);
                }
            }
        }

    }

    /**
     * 定时更新课表,对查到的用户的学号和密码进行去重处理
     */
    @Scheduled(cron = "0 0 23 0/1 * *")
    public void getCourse(){
        List<User> allUser = userService.findAllUserUsernameAndPassword();
        List<User> removeOverlapUsernameAndPassword = new ArrayList<>();
        for(int i = 0; i < allUser.size() ;i++){
            boolean flag = false;
            for(int j = i - 1; j >= 0 ;j-- ){
                if(allUser.get(i).getUsername().equals(allUser.get(j).getUsername())
                        && allUser.get(i).getPassword().equals(allUser.get(j).getPassword()))
                {
                     flag = true;
                }
            }
            if(!flag){
                removeOverlapUsernameAndPassword.add(allUser.get(i));
            }
        }
        if(removeOverlapUsernameAndPassword.size()>0){
            for(User user : removeOverlapUsernameAndPassword){
                String cookie = studentMethod.studentLogin(user);
                if(cookie.length() != 0){

                    //进行课程的爬取
                    ReptileCourseOption courseOption = new ReptileCourseOption();
                    courseOption.setXnxq01id(CONSTANT.DEFAULT_TERM.getDefaultTerm());
                    ApiResponse<Map<String, Object>> apiResponse = studentMethod.getStudentCourse(cookie, courseOption);
                    List<ReptileCourse>  reptileList = (List<ReptileCourse>)apiResponse.getData().get("courses");
                    String bz = (String)apiResponse.getData().get("bz");
                    List<Course> courses = new ArrayList<>();
                    for (int i = 0; i < reptileList.size() ; i++) {
                        ReptileCourse reptileCourse = reptileList.get(i);
                        Course course = new Course();
                        course.setStudentUsername(user.getUsername());
                        course.setTerm(CONSTANT.DEFAULT_TERM.getDefaultTerm());
                        course.setCourseNumber(reptileCourse.getNumber());
                        course.setCourseName(reptileCourse.getName());
                        course.setCourseZc(reptileCourse.getZc());
                        course.setCourseJc(Integer.parseInt(reptileCourse.getJc()));
                        course.setCourseRoom(reptileCourse.getRoom());
                        course.setCourseTeacher(reptileCourse.getTeacher());
                        course.setCourseXq(Integer.parseInt(reptileCourse.getXq()));
                        course.setNote(bz);
                        courses.add(course);
                    }

                    //存在即更新，不存在就插入
                    List<Course> beforeCourseList = courseService.findCourseByTermAndStudentUserName(CONSTANT.DEFAULT_TERM.getDefaultTerm(), user.getUsername()).getData();
                    if(beforeCourseList.size() != 0 && courses.size() != 0 && beforeCourseList.size() < courses.size()){  //有新数据，且旧数据不为0,直接先删除，删除再插入
                        List<Integer> deleteIds = new ArrayList<>();
                        for (int i = 0; i < beforeCourseList.size(); i++) {
                            deleteIds.add(beforeCourseList.get(i).getId());
                        }
                        courseService.deleteCourseBatch(deleteIds);

                        for (int i = 0; i < courses.size() ; i++) {
                            courseService.insertCourse(courses.get(i));
                        }
                    }
                    if(beforeCourseList.size() == courses.size() && !ObjectUtils.isEmpty(beforeCourseList) && !ObjectUtils.isEmpty(courses)){             //无新数据，对源数据进行更新,直接根据id进行全部的更新
                        List<Course> newCourses = new ArrayList<>();
                        for (int i = 0; i < courses.size();i++){
                            Course newCourse = courses.get(i);
                            Course beforeCourse = beforeCourseList.get(i);
                            newCourse.setId(beforeCourse.getId());
                            newCourses.add(newCourse);
                        }
                        courseService.updateCourseBatch(newCourses);  //批量更新
                    }
                    if (courses.size() != 0 && beforeCourseList.size() == 0){   //新数据不为0，旧数据为0 直接插入
                        for (int i = 0; i < courses.size() ; i++) {
                            Course course = courses.get(i);
                            courseService.insertCourse(course);
                        }
                    }
                    studentMethod.studentLogout(cookie);
                }
            }
        }
    }

    /**
     * 定时更新体育打卡
     */

}
