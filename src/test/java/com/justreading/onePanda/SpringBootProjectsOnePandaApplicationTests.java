package com.justreading.onePanda;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.justreading.onePanda.common.*;
import com.justreading.onePanda.common.bean.ReptileCourse;
import com.justreading.onePanda.common.bean.ReptileCourseOption;
import com.justreading.onePanda.common.bean.ReptileGrade;
import com.justreading.onePanda.common.bean.ReptileGradeOption;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.service.CourseService;
import com.justreading.onePanda.feedback.service.FeedbackService;
import com.justreading.onePanda.schedule.ReptileSchedule;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import com.justreading.onePanda.user.service.UserService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.Bucket;
import io.swagger.models.auth.In;
import org.apache.http.client.methods.HttpHead;
import org.apache.ibatis.jdbc.SQL;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.*;

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

    @Autowired
    private RestTemplate restTemplate;


    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("172210409226");
        user.setPassword("18178595973qwe");
        ReptileCourseOption option = new ReptileCourseOption();

        option.setXnxq01id("2020-2021-1");
//?kch=&xnxq01id=2019-2020-2
        String cookie = studentMethod.studentLogin(user);
        ApiResponse<Map<String, Object>> studentCourse = studentMethod.getStudentCourse(cookie, option);
        Map<String, Object> data = studentCourse.getData();
        List<ReptileCourse>  courses = (List<ReptileCourse>)data.get("courses");
        System.out.println(courses);
    }

    @Test
    public void test2(){
//        User user = new User();
//        user.setUsername("192210303117");
//        user.setPassword("200146lhb");
//        String cookie = studentMethod.studentLogin(user);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED.toString());
//        httpHeaders.add("Cookie",cookie);
//        String url = URL.STUDENT_GRADE_EVALUATION.getUrl()+ "?kch=&xnxq01id=" + "2019-2020-2";
//        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
//        if(responseEntity.getStatusCodeValue() == 200){
//            Document $ = Jsoup.parse(responseEntity.getBody());
//            Elements elements = $.select("#dataList tr");
//            List<ReptileGrade> reptileGrades = new ArrayList<>();
//            for (int i = 1; i < elements.size() ; i++) {
//                String courseNumber = elements.get(i).childNodes().get(7).childNodes().get(0).toString();
//                String courseName = elements.get(i).childNodes().get(9).childNodes().get(0).toString();
//                String score = elements.get(i).childNodes().get(11).childNodes().get(0).toString();
//                String credit = elements.get(i).childNodes().get(13).childNodes().get(0).toString();
//                String time = elements.get(i).childNodes().get(13).childNodes().get(0).toString();
//                StringBuffer examMethod = new StringBuffer();
//
//                //15为考试方式，有时候有些校公选为空，当为空的时候直接不进行添加，gradeKind为类型，用于判断是否计算绩点用的，任选课不计算绩点
//                if(elements.get(i).childNodes().get(15).childNodes().size() == 0){
//                    examMethod.append("暂无");
//                }else {
//                    examMethod.append(elements.get(i).childNodes().get(15).childNodes().get(0).toString());
//                }
//                String courseKind = elements.get(i).childNodes().get(17).childNodes().get(0).toString();
//                ReptileGrade reptileGrade = new ReptileGrade();
//                reptileGrade.setCourseNumber(courseNumber);
//                reptileGrade.setCourseName(courseName);
//                reptileGrade.setGrade(score);
//                reptileGrade.setCredit(credit);
//                reptileGrade.setTime(time);
//                reptileGrade.setExamMethod(examMethod.toString());
//                reptileGrade.setCourseKind(courseKind);
//                reptileGrades.add(reptileGrade);
//            }
//      }
    }

    @Test
    public void testPingJiao(){
        User user = new User();
        user.setUsername("172210409226");
        user.setPassword("18178595973qwe");
        String cookie = studentMethod.studentLogin(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",cookie);
        headers.add("application",MediaType.APPLICATION_FORM_URLENCODED.toString());
        String url = "http://jwgl.just.edu.cn:8080/jsxsd/xspj/xspj_save.do?issubmit=1&" +
                "pj09id=A91D730E29276B14E05316FAA8C01C6B&" +
                "pj01id=1434B5CEDF4247FFA5E548DE488B3D26&" +
                "pj0502id=4D5B667B27D24B76AE080DB90B5689A3&" +
                "jg0101id=A02036&" +
                "jx0404id=201920202008773&" +
                "xsflid=&" +
                "xnxq01id="+CONSTANT.DEFAULT_TERM.getDefaultTerm()+"&" +
                "jx02id=04002013b&" +
                "pj02id=32274A37753448D9BCBE5830D4BA53F4&" +
                "tjxt=1&" +
                "pj06xh=2&" +
                "pj0601id_2=3A7F85336E804F138818D4EF4FCD9F11&" +
                "pj0601fz_2_3A7F85336E804F138818D4EF4FCD9F11=30&" +
                "pj0601fz_2_B6A795C5F0844FBDABECFF1FC88C093F=24&" +
                "pj0601fz_2_37782765799F448FB7B1BF5C2C77E79D=18&" +
                "pj0601fz_2_8A89AF6FCCD34B83A85FA39B54E473D3=12&" +
                "pj0601fz_2_50C9E1A27D7E493E8D786857A91DD993=6&" +
                "pj06xh=3&" +
                "pj0601id_3=318AE88935894B308C3B8AFB9DDF9482&" +
                "pj0601fz_3_318AE88935894B308C3B8AFB9DDF9482=20&" +
                "pj0601fz_3_F4310A4E66544F0B92F098F0A4434E64=16&" +
                "pj0601fz_3_C2F74FE024B34810B71D09D9B3D397D4=12&" +
                "pj0601fz_3_84E95C6F34B44847A78E452FB4826D8E=8&" +
                "pj0601fz_3_12713841B3AF422D9E8A447E96E7A254=4&" +
                "pj06xh=1&" +
                "pj0601id_1=45626B877BC04A8EB2E141127506681C&" +
                "pj0601fz_1_45626B877BC04A8EB2E141127506681C=20&" +
                "pj0601fz_1_64C47180D909495FAFF0C2E2D20BF6C3=16&" +
                "pj0601fz_1_46D0C9EE033F4B03AA983397924771AD=12&" +
                "pj0601fz_1_15E1EAC6FAE64BA487A61A7C24962182=8&" +
                "pj0601fz_1_611030AEACC3498C9EE3266467B54BDE=4&" +
                "pj06xh=4&" +
                "pj0601fz_4_9B0244EEE2DA465F98965CECE2EC5E83=30&" +
                "pj0601id_4=A86C05BE34894BE1A875929D4AA4267F&" +
                "pj0601fz_4_A86C05BE34894BE1A875929D4AA4267F=24&" +
                "pj0601fz_4_BC0F291E4F4A4FAE8A6E46C661A8D095=18&" +
                "pj0601fz_4_34975A308B194D79A8E304AA16F4DE62=12&" +
                "pj0601fz_4_FE7B2498DFAE4BC990958E14BA93729F=6&" +
                "pj03id=060A6856F1A94631B2D11633A288B805&" +
                "jynr=";
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String body = responseEntity.getBody();
        Document $ = Jsoup.parse(body);
    }

}

