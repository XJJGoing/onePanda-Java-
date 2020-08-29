package com.justreading.onePanda.testCourse;

import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinYongJian
 * @date 2020 年 08 月 28 日 6:16 下午
 * @description
 */
@SpringBootTest
public class TestCourse {
    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void testDelete(){
         List<Integer> list = new ArrayList<>();
         list.add(92250);
         list.add(92251);
         list.add(92252);
         courseMapper.deleteCourseBatch(list);
    }

    @Test
    public void testUpdate(){
        List<Course> courseList = new ArrayList<>();
        Course course = new Course();
        course.setStudentUsername("172210409226");
        course.setId(92261);
        course.setCourseName("小贱贱");
        courseList.add(course);
        courseMapper.updateCourseBatch(courseList);
    }
}
