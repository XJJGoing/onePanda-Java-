package com.justreading.onePanda;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import com.justreading.onePanda.common.StudentMethod;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.service.CourseService;
import com.justreading.onePanda.feedback.service.FeedbackService;
import com.justreading.onePanda.schedule.ReptileSchedule;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import com.justreading.onePanda.user.service.UserService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.Bucket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringBootProjectsOnePandaApplicationTests {

    @Autowired
    private StudentMethod studentMethod;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ReptileSchedule reptileSchedule;

    @Autowired
    private COSClient cosClient;

    @Autowired
    private FeedbackService feedbackService;


    @Test
    void contextLoads() {
    }

    /**
     * 测试学生的登录和退出
     */
//    @Test
//    public void testLoginAndLoginOut(){
//        User user = new User();
//        user.setUsername("172210409226");
//        user.setPassword("18178595973qwe");
//        String cookie = studentMethod.studentLogin(user);
//        studentMethod.studentLogout(cookie);
//    }

//    @Test
//    public  void testSchedule(){
////        reptileSchedule.getGrade();
//          reptileSchedule.getCourse();
//    }


}
