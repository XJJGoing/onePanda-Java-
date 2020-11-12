package com.justreading.onePanda.converter;

import com.justreading.onePanda.common.bean.ReptileCourse;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.rules.Dto.StudentCourseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 11 月 03 日 16:44
 */
public class CourseConverter {

    /**
     * 将爬虫格式的课表进行转换成普通的课表
     * @return
     */
    public static List<Course> converterCourse(StudentCourseDto studentCourseDto,String studentUserName,String term){
        String bz = studentCourseDto.getBz();
        List<ReptileCourse> courses = studentCourseDto.getCourses();
        List<Course> resCourse = new ArrayList<>();
        for(ReptileCourse reptileCourse : courses){
            Course course = Course.builder()
                    .studentUsername(studentUserName)
                    .term(term)
                    .courseName(reptileCourse.getName())
                    .courseNumber(reptileCourse.getNumber())
                    .courseZc(reptileCourse.getZc())
                    .courseJc(Integer.parseInt(reptileCourse.getJc()))
                    .courseRoom(reptileCourse.getRoom())
                    .courseTeacher(reptileCourse.getTeacher())
                    .courseXq(Integer.parseInt(reptileCourse.getXq()))
                    .note(bz)
                    .build();
            resCourse.add(course);
        }
        return resCourse;
    }
}
