package com.justreading.onePanda.common;

import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.common.bean.ReptileCourse;
import com.justreading.onePanda.common.bean.ReptileCourseOption;
import com.justreading.onePanda.common.bean.ReptileGrade;
import com.justreading.onePanda.common.bean.ReptileGradeOption;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;
import java.util.*;

/**
 * @author LYJ
 * @Description 学生爬取教务处通用的方法
 * @date 2020 年 02 月 15 日 13:14
 */
@Component
@Slf4j
public class StudentMethod {

    @Autowired
    private RestTemplate restTemplate;



    /**
     *  学生的登录,登录成功返回拼接后的cookie
     * @return  cookie
     */
    @MyLog("学生登录教务系统")
    public String studentLogin(User user){
        String url = URL.STUDENT_LOGIN.getUrl() +"?"+ "USERNAME=" + user.getUsername() +"&PASSWORD="+ user.getPassword();
        StringBuffer finalCookie = new StringBuffer();


        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        HttpHeaders responseHeader = null;
        ResponseEntity responseEntity = null;
        try{
            responseEntity = restTemplate.exchange(url, HttpMethod.POST ,entity, String.class);
            responseHeader = responseEntity.getHeaders();
            if(responseHeader.get("Location") != null ){
                if(ObjectUtils.isEmpty(responseHeader.get("Set-Cookie"))){

                }else{
                    List<String> cookie1 = responseHeader.get("Set-Cookie");
                    for (int i = 0; i < cookie1.size(); i++) {
                        String s = cookie1.get(i);
                        String[] split = s.split(";");
                        finalCookie.append(split[0]);
                        if(i == 0){
                            finalCookie.append(";");
                        }
                    }
                }
            }
        }catch (ResourceAccessException e){   //出现资源错误的时候进行重试登录以免登录不成功
//            responseEntity = restTemplate.exchange(url, HttpMethod.POST ,entity, String.class);
//            responseHeader = responseEntity.getHeaders();
//            if(responseHeader.get("Location") != null ){
//                if(ObjectUtils.isEmpty(responseHeader.get("Set-Cookie"))){
//
//                }else{
//                    List<String> cookie1 = responseHeader.get("Set-Cookie");
//                    for (int i = 0; i < cookie1.size(); i++) {
//                        String s = cookie1.get(i);
//                        String[] split = s.split(";");
//                        finalCookie.append(split[0]);
//                        if(i == 0){
//                            finalCookie.append(";");
//                        }
//                    }
//                }
//            }
        }catch (Exception e){
            log.info("找不到目标主机：：UnknownHostException" + e.getMessage());
        }finally {
            return finalCookie.toString();
        }
    }

    /**
     *  获取学生真实 学院、专业、姓名等信息.
     * @param cookie 学生cookie
     * @return
     */
    public ApiResponse<Map<String,Object>> getStudentInfo(String cookie){
        ApiResponse<Map<String,Object>> apiResponse = new ApiResponse<>();
        Map<String,Object> studentInfo = new HashMap<>();
        String url = URL.STUDENT_INFO.getUrl();
        HttpHeaders headers = new HttpHeaders();
           headers.add("Content-type",MediaType.APPLICATION_FORM_URLENCODED.toString());
           headers.add("Cookie",cookie);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        Document $ = Jsoup.parse(responseEntity.getBody());
        Elements content = $.select("#xjkpTable");
        String college = content.get(0).childNodes().get(1).childNodes().get(4).childNodes().get(1).childNodes().get(0).toString().substring(3);
        String studentMajorName = content.get(0).childNodes().get(1).childNodes().get(4).childNodes().get(3).childNodes().get(0).toString().substring(3);
        String trueName = content.get(0).childNodes().get(1).childNodes().get(6).childNodes().get(3).childNodes().get(0).toString().substring(6);
        studentInfo.put("college",college);
        studentInfo.put("studentMajorName",studentMajorName);
        studentInfo.put("trueName",trueName);
        apiResponse.setData(studentInfo);
        apiResponse.setMsg("查询学生个人信息成功成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    /**
     *  获取学生的成绩信息
     * @param cookie 学生cookie
     * @param reptileGradeOption  成绩条件格式
     * @return
     */
    public ApiResponse<List<ReptileGrade>> getStudentGrade(String cookie, ReptileGradeOption reptileGradeOption) {
        ApiResponse<List<ReptileGrade>> apiResponse = new ApiResponse<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        httpHeaders.add("Cookie",cookie);
        String url = URL.STUDENT_GRADE.getUrl() +"?" +"kksj="+ reptileGradeOption.getKksj()+ "&"
                + "xsfs="+ reptileGradeOption.getXsfs() + "&"
                + "kcmc=" + reptileGradeOption.getKcmc() + "&"
                + "kcxz=" + reptileGradeOption.getKcxz();
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if(responseEntity.getStatusCodeValue() == 200){
            Document $ = Jsoup.parse(responseEntity.getBody());
            Elements elements = $.select("#dataList tr");
            List<ReptileGrade> reptileGrades = new ArrayList<>();
            for (int i = 1; i < elements.size() ; i++) {
                String courseNumber = elements.get(i).childNodes().get(5).childNodes().get(0).toString();
                String courseName = elements.get(i).childNodes().get(7).childNodes().get(0).toString();
                String score = elements.get(i).childNodes().get(9).childNodes().get(0).toString();
                String credit = "0";
                String time = "0";
                if(elements.get(i).childNodes().get(11).childNodes().size() != 0){
                    credit = elements.get(i).childNodes().get(11).childNodes().get(0).toString();
                }
                if(elements.get(i).childNodes().get(13).childNodes().size() != 0){
                    time = elements.get(i).childNodes().get(13).childNodes().get(0).toString();
                }
                StringBuffer examMethod = new StringBuffer();

                //15为考试方式，有时候有些校公选为空，当为空的时候直接不进行添加，gradeKind为类型，用于判断是否计算绩点用的，任选课不计算绩点
                if(elements.get(i).childNodes().get(15).childNodes().size() == 0){
                     examMethod.append("暂无");
                }else {
                    examMethod.append(elements.get(i).childNodes().get(15).childNodes().get(0).toString());
                }
                String courseKind = "转专业替代课程";
                if(elements.get(i).childNodes().get(17).childNodes().size() != 0){
                    courseKind = elements.get(i).childNodes().get(17).childNodes().get(0).toString();
                }
                ReptileGrade reptileGrade = new ReptileGrade();
                reptileGrade.setCourseNumber(courseNumber);
                reptileGrade.setCourseName(courseName);
                reptileGrade.setGrade(score);
                reptileGrade.setCredit(credit);
                reptileGrade.setTime(time);
                reptileGrade.setExamMethod(examMethod.toString());
                reptileGrade.setCourseKind(courseKind);
                reptileGrades.add(reptileGrade);
            }
            apiResponse.setCode(200);
            apiResponse.setMsg("查询成功");
            apiResponse.setData(reptileGrades);
        }else{
            apiResponse.setCode(500);
            apiResponse.setMsg("查询不到");
        }
        return apiResponse;
    }

    /**
     *  获取学生的成绩信息，通过成绩申代的方法处进行爬虫
     * @param cookie 学生cookie
     * @param reptileGradeOption  成绩条件格式
     * @return
     */
    public ApiResponse<List<ReptileGrade>> getStudentGradeByOther(String cookie, ReptileGradeOption reptileGradeOption) {
        ApiResponse<List<ReptileGrade>> apiResponse = new ApiResponse<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        httpHeaders.add("Cookie",cookie);
        String url = URL.STUDENT_GRADE_EVALUATION.getUrl()+ "?kch=&xnxq01id=" + reptileGradeOption.getKksj();
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if(responseEntity.getStatusCodeValue() == 200){
            Document $ = Jsoup.parse(responseEntity.getBody());
            Elements elements = $.select("#dataList tr");
            List<ReptileGrade> reptileGrades = new ArrayList<>();
            for (int i = 1; i < elements.size() ; i++) {
                String courseNumber = elements.get(i).childNodes().get(7).childNodes().get(0).toString();
                String courseName = elements.get(i).childNodes().get(9).childNodes().get(0).toString();
                String score = elements.get(i).childNodes().get(11).childNodes().get(0).toString();
                String credit = elements.get(i).childNodes().get(13).childNodes().get(0).toString();
                String time = "暂无";

                StringBuffer examMethod = new StringBuffer();

                //15为考试方式，有时候有些校公选为空，当为空的时候直接不进行添加，gradeKind为类型，用于判断是否计算绩点用的，任选课不计算绩点
                if(elements.get(i).childNodes().get(15).childNodes().size() == 0){
                    examMethod.append("暂无");
                }else {
                    examMethod.append(elements.get(i).childNodes().get(15).childNodes().get(0).toString());
                }
                String courseKind = elements.get(i).childNodes().get(17).childNodes().get(0).toString();
                ReptileGrade reptileGrade = new ReptileGrade();
                reptileGrade.setCourseNumber(courseNumber);
                reptileGrade.setCourseName(courseName);
                reptileGrade.setGrade(score);
                reptileGrade.setCredit(credit);
                reptileGrade.setTime(time);
                reptileGrade.setExamMethod(examMethod.toString());
                reptileGrade.setCourseKind(courseKind);
                reptileGrades.add(reptileGrade);
            }
            apiResponse.setCode(200);
            apiResponse.setMsg("查询成功");
            apiResponse.setData(reptileGrades);
        }else{
            apiResponse.setCode(500);
            apiResponse.setMsg("查询不到");
        }
        return apiResponse;
    }




    /**
     * 获取学生的课表信息
     * @param cookie 学生cookie
     * @param  reptileCourseOption  课表查询条件
     * @return
     */
    public ApiResponse<Map<String,Object>> getStudentCourse(String cookie, ReptileCourseOption reptileCourseOption) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type",MediaType.APPLICATION_FORM_URLENCODED.toString());
        httpHeaders.add("Cookie",cookie);
        String url = URL.STUDENT_COURSE.getUrl() + "?"
                + "sfFD=" + reptileCourseOption.getSfFD() + "&"
                + "xnxq01id=" + reptileCourseOption.getXnxq01id();
        HttpEntity<String>  httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        ApiResponse<Map<String,Object>> apiResponse = new ApiResponse<>();
        Map<String,Object> apiResponseData = new HashMap<>();
        if(responseEntity.getStatusCodeValue() == 200){
            Document $ = Jsoup.parse(responseEntity.getBody());
            ArrayList<String> weeksList = new ArrayList<>();
            ArrayList<String> termsList = new ArrayList<>();
            ArrayList<ReptileCourse> coursesList = new ArrayList<>();

            Elements weeks = $.select("#zc");
            Elements terms = $.select("#xnxq01id");
            Elements course = $.select("#kbtable");
            for (int i = 3; i <= 41 ; i+=2) {
                weeksList.add(weeks.get(0).childNodes().get(i).childNodes().get(0).toString());
            }
            apiResponseData.put("weeks",weeksList);
            for (int i = 3; i <= 15 ; i+=2 ) {
                termsList.add(terms.get(0).childNodes().get(i).childNodes().get(0).toString());
            }
            apiResponseData.put("terms",termsList);

            //先获取每个tr的div的id,课表的排序是从左到右,每个tr对应一个id
            ArrayList<String> divId = new ArrayList<>();
            for (int i = 2; i < course.get(0).childNodes().get(1).childNodes().size() ; i+=2) {

                //最后面的tr为备注
                if(i == course.get(0).childNodes().get(1).childNodes().size() - 2 ){
                    if(course.get(0).childNodes().get(1).childNodes().get(i).childNodes().get(3).childNodes().size()!= 0){
                        apiResponseData.put("bz",course.get(0).childNodes().get(1).childNodes().get(i).childNodes().get(3).childNodes().get(0).toString());
                    }else{

                        //这里可以用作广告位，没有备注的时候
                        apiResponseData.put("bz","");
                    }
                }else{
                    divId.add("#" + course.get(0).childNodes().get(1).childNodes().get(i).childNodes().get(3).childNodes().get(7).attr("id"));
                }
            }

//            解析课表
            for (int i = 0; i < divId.size() ; i++) {
                String courseId = divId.get(i);
                for (int j = 1; j <= 7; j++) {
                    Elements courseContent = $.select(courseId);

//                    判断是否有课程，即课程的div，普通课程长度10  体育课长度8,采用从前面＋下去的算法，有的多的就跳过2
                    List<Node> div = courseContent.get(0).childNodes();
                    Integer length = div.size();

                    //分割线的索引列表
                    List<Integer> separatorIndexList = new ArrayList<>();
                    for (int k = 0; k < div.size() ; k++) {
                         if(!div.get(k).hasAttr("childNodes") && div.get(k).toString().equals("---------------------")){
                             separatorIndexList.add(k);
                         }
                    }
                    Integer beginIndex = 0;
                    if(!ObjectUtils.isEmpty(separatorIndexList)){   //拥有多门课
                        for(Integer index : separatorIndexList){
                            Integer len = index - beginIndex;
                            ReptileCourse reptileCourseObject = new ReptileCourse();
                            if(len.equals(10)){
                                reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                                reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                                reptileCourseObject.setTeacher(div.get(beginIndex + 4).childNodes().get(0).toString());
                                reptileCourseObject.setZc(div.get(beginIndex + 6).childNodes().get(0).toString());
                                reptileCourseObject.setRoom(div.get(beginIndex + 8).childNodes().get(0).toString());
                            }else if(len.equals(6)){
                                reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                                reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                                reptileCourseObject.setZc(div.get(beginIndex + 4).childNodes().get(0).toString());
                            }else if(len.equals(8)){
                                reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                                reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                                reptileCourseObject.setTeacher(div.get(beginIndex + 4).childNodes().get(0).toString());
                                reptileCourseObject.setZc(div.get(beginIndex + 6).childNodes().get(0).toString());
                            }
                            reptileCourseObject.setJc(Integer.toString(j));
                            reptileCourseObject.setXq(Integer.toString( i + 1));
                            coursesList.add(reptileCourseObject);
                            beginIndex = index + 2;
                        }
                        Integer resCourseLength = div.size() - beginIndex;
                        ReptileCourse reptileCourseObject = new ReptileCourse();
                        if(resCourseLength >= 9 ){
                            reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                            reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                            reptileCourseObject.setTeacher(div.get(beginIndex + 4).childNodes().get(0).toString());
                            reptileCourseObject.setZc(div.get(beginIndex + 6).childNodes().get(0).toString());
                            reptileCourseObject.setRoom(div.get(beginIndex + 8).childNodes().get(0).toString());
                        }else if(resCourseLength > 6 && resCourseLength <= 8){
                            reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                            reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                            reptileCourseObject.setTeacher(div.get(beginIndex + 4).childNodes().get(0).toString());
                            reptileCourseObject.setZc(div.get(beginIndex + 6).childNodes().get(0).toString());
                        }else if(resCourseLength <= 6){
                            reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                            reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                            reptileCourseObject.setZc(div.get(beginIndex + 4).childNodes().get(0).toString());
                        }
                        reptileCourseObject.setJc(Integer.toString(j));
                        reptileCourseObject.setXq(Integer.toString( i + 1));
                        coursesList.add(reptileCourseObject);
                    }else{   //只有一门课
                        Integer len = div.size();
                        ReptileCourse reptileCourseObject = new ReptileCourse();
                        if(len >= 9 ){
                            reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                            reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                            reptileCourseObject.setTeacher(div.get(beginIndex + 4).childNodes().get(0).toString());
                            reptileCourseObject.setZc(div.get(beginIndex + 6).childNodes().get(0).toString());
                            reptileCourseObject.setRoom(div.get(beginIndex + 8).childNodes().get(0).toString());
                            reptileCourseObject.setJc(Integer.toString(j));
                            reptileCourseObject.setXq(Integer.toString( i + 1));
                            coursesList.add(reptileCourseObject);
                        }else if(len > 6 && len <= 8){
                            reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                            reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                            reptileCourseObject.setTeacher(div.get(beginIndex + 4).childNodes().get(0).toString());
                            reptileCourseObject.setZc(div.get(beginIndex + 6).childNodes().get(0).toString());
                            reptileCourseObject.setJc(Integer.toString(j));
                            reptileCourseObject.setXq(Integer.toString( i + 1));
                            coursesList.add(reptileCourseObject);
                        }else if(len <= 6 & len > 4){
                            reptileCourseObject.setNumber(div.get(beginIndex).toString().substring(1));
                            reptileCourseObject.setName( div.get(beginIndex + 2).toString());
                            reptileCourseObject.setZc(div.get(beginIndex + 4).childNodes().get(0).toString());
                            reptileCourseObject.setJc(Integer.toString(j));
                            reptileCourseObject.setXq(Integer.toString( i + 1));
                            coursesList.add(reptileCourseObject);
                        }
                    }





//                    if(length > 1){
//                        Integer len = 0;
//                        while (length > 0){
//                            ReptileCourse reptileCourseObject = new ReptileCourse();
//                            reptileCourseObject.setNumber(div.get(len).toString().substring(1));
//                            String courseName = div.get(len + 2).toString();
//                            reptileCourseObject.setName(courseName);
//                            if((len + 6) == div.size()){
//                                reptileCourseObject.setZc(div.get(len + 4).childNodes().get(0).toString());
//                            }else{
//                                reptileCourseObject.setTeacher(div.get(len + 4).childNodes().get(0).toString());
//                                reptileCourseObject.setZc(div.get(len + 6).childNodes().get(0).toString());
//                            }
////                            System.out.println("获取的div" + div.size());
//                            Boolean  flag = false;
//                            if(courseName.equals("体育1") || courseName.equals("体育2") || courseName.equals("体育3")
//                                    || courseName.equals("体育4") || courseName.equals("三年级体育专项")
//                                   || (div.size() > len + 8 ? div.get(len + 8).toString().equals("---------------------"):false)
//
//                            )
//                            {   //这里加上是否有教室，因为有些不是体育课的也没教室,可能出现空指针
//
//                               len += 10;
//                            }else{
//                                if(div.size() > len + 8){
//                                    reptileCourseObject.setRoom(div.get(len + 8).childNodes().get(0).toString());
//                                }
//                                len += 12;
//                            }
//
//                            //设置节次,和属于哪一行的tr
//                            reptileCourseObject.setJc(Integer.toString(j));
//                            reptileCourseObject.setXq(Integer.toString( i + 1));
//                            coursesList.add(reptileCourseObject);
//                            length -= 12;
//                        }
//                    }

                    //进行id的替换
                    courseId = new String(courseId.replaceFirst("-[0-9]-", "-" + (j + 1) + "-"));
                }
            }
            apiResponseData.put("courses",coursesList);
            apiResponse.setCode(200);
            apiResponse.setMsg("查询成功");
            apiResponse.setData(apiResponseData);
        }else{
            apiResponse.setMsg("查询失败");
            apiResponse.setCode(500);
        }
        return apiResponse;
    }

    /**
     * 当小程序端登录成功的时候就退出教务端的登录,以免发生连接不中断的情况
     */
    public void studentLogout(String cookie){
        String url = URL.STUDENT_LOGOUT.getUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",cookie);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
    }
}
