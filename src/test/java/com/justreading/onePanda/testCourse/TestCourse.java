package com.justreading.onePanda.testCourse;

import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.mapper.CourseMapper;
import com.justreading.onePanda.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void testDistinct(){
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUsername("172210409226");
        user.setId(1);

        User user1 = new User();
        user1.setUsername("172210409226");
        user1.setId(2);

        User user2 = new User();
        user2.setUsername("172210409226");
        user2.setId(3);

        User user3 = new User();
        user3.setUsername("172210409226");
        user3.setId(4);

        list.add(user);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        Map<String, List<User>> collect1 = list.stream().collect(Collectors.groupingBy(User::getUsername));
        collect1.forEach((k,v) ->{
            System.out.println(k + " " + v);
        });
//        List<User> collect = list.stream().distinct().collect(Collectors.toList());
//        System.out.println(collect);
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
