package com.justreading.onePanda.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.common.*;
import com.justreading.onePanda.common.bean.ReptileCourse;
import com.justreading.onePanda.common.bean.ReptileCourseOption;
import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.course.service.CourseService;
import com.justreading.onePanda.schedule.ReptileSchedule;
import com.justreading.onePanda.schedule.WeekSchedule;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import com.justreading.onePanda.user.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LYJ
 * @Description 用户接口的实现模块
 * @date 2020 年 02 月 15 日 13:46
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StudentMethod studentMethod;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ReentrantLock reentrantLock;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     *根据id判断 存在即更新，不存在即插入，并且返回一个带id的跟数据库中id相同的user，其他信息不一定相同
     * @param user 用户信息
     */
    private  User toGetOpenIdAndInsert(User user){

        //这部分还没测试
        if(user.getOrigin().equals(2)){ //QQ用户
            RestTemplate restTemplate1 = new RestTemplate();
            String qqUrl = "https://api.q.qq.com/sns/jscode2session?appid=1110191312&secret=8y7tW9JifFGfaBEu&js_code=" + user.getCode() + "&grant_type=authorization_code";
            String qqString = restTemplate1.getForObject(qqUrl, String.class);
            HashMap qqUserInfo = JSON.parseObject(qqString, HashMap.class);
            user.setQqOpenid(qqUserInfo.get("openid").toString());
            user.setQqSessionKey(qqUserInfo.get("session_key").toString());

            //根据qq_openid查询数据库，存在即更新，不存在即增加
            User userByQqOpenId   = userMapper.findUserByQqOpenid(user);
            if(!ObjectUtils.isEmpty(userByQqOpenId)){
                user.setId(userByQqOpenId.getId());
                userMapper.updateUser(user);
            }else{
                if(user.getIsAssistant().equals(1)){   //如果为老师时得删除无openid的脏数据,再实行插入
                    List<Integer> ids =  new ArrayList<>();
                    ids.add(user.getId());
                    userMapper.deleteBatch(ids);
                }
                userMapper.insertUser(user);
            }
        }else if(user.getOrigin().equals(1)){  //微信用户

            RestTemplate restTemplate1 = new RestTemplate();
            String wxUrl =  "https://api.weixin.qq.com/sns/jscode2session?appid=wx1d031c6778593c73&secret=d8ddaa58e49f4aebed641e9fe51b605e&js_code=" + user.getCode() + "&grant_type=authorization_code";
            String wxString = restTemplate1.getForObject(wxUrl, String.class);
            HashMap wxUserInfo = JSON.parseObject(wxString, HashMap.class);
            user.setWxOpenid(wxUserInfo.get("openid").toString());
            user.setWxSessionKey(wxUserInfo.get("session_key").toString());

            //根据wx_openid查询数据库，存在即更新，不存在即增加
            User userByWxOpenid = userMapper.findUserByWxOpenid(user);
            if(!ObjectUtils.isEmpty(userByWxOpenid)){
                user.setId(userByWxOpenid.getId());
                userMapper.updateUser(user);
            }else {
                if(user.getIsAssistant().equals(1)){   //如果为老师时得删除无openid的脏数据,再实行插入
                    List<Integer> ids =  new ArrayList<>();
                    ids.add(user.getId());
                    userMapper.deleteBatch(ids);
                }

                userMapper.insertUser(user);
            }
        }
        return user;
    }



    /**
     * 前端获取code，如果教务的账号和密码正确就登录成功，并且获取openid和session，整合成user给前端返回
     * @param user  用户的信息
     */
    @Override
    public ApiResponse<Map<String,Object>> userLogin(User user) {

        ApiResponse<Map<String,Object>> apiResponse = new ApiResponse<>();

        //辅导员登录，直接使用账户和密码进行登录，账户和密码设置在数据库中，查库进行插入相应的数据,辅导员跟学生的处理机制一样，可以存在多个，但是openid不一样
        if(user.getIsAssistant().equals(1) && user.getCode().length() >0){
            User teacher = userMapper.teacherLogin(user.getUsername(), user.getPassword(),user.getOrigin());
            if(StringUtils.isEmpty(teacher)){
                apiResponse.setMsg("辅导员登录失败");
                apiResponse.setCode(200);
            }else{
                teacher.setOrigin(user.getOrigin());
                teacher.setCode(user.getCode());
                teacher.setIsAssistant(1);
                if(!ObjectUtils.isEmpty(user.getWxAvatarUrl())){teacher.setWxAvatarUrl(user.getWxAvatarUrl());}
                if(!ObjectUtils.isEmpty(user.getWxCity())){teacher.setWxCity(user.getWxCity());}
                if(!ObjectUtils.isEmpty(user.getWxCountry())){teacher.setWxCountry(user.getWxCountry());}
                if(!ObjectUtils.isEmpty(user.getWxGender())){teacher.setWxGender(user.getWxGender());}
                if(!ObjectUtils.isEmpty(user.getWxLanguage())){teacher.setWxLanguage(user.getWxLanguage());}
                if(!ObjectUtils.isEmpty(user.getWxNickName())){teacher.setWxNickName(user.getWxNickName());}
                if(!ObjectUtils.isEmpty(user.getWxProvince())){teacher.setWxProvince(user.getWxProvince());}

                if(!ObjectUtils.isEmpty(user.getQqAvatarUrl())){teacher.setQqAvatarUrl(user.getQqAvatarUrl());}
                if(!ObjectUtils.isEmpty(user.getQqCity())){teacher.setQqCity(user.getQqCity());}
                if(!ObjectUtils.isEmpty(user.getQqCountry())){teacher.setQqCountry(user.getQqCountry());}
                if(!ObjectUtils.isEmpty(user.getQqGender())){teacher.setQqGender(user.getQqGender());}
                if(!ObjectUtils.isEmpty(user.getQqLanguage())){teacher.setQqLanguage(user.getQqLanguage());}
                if(!ObjectUtils.isEmpty(user.getQqNickName())){teacher.setQqNickName(user.getQqNickName());}
                if(!ObjectUtils.isEmpty(user.getQqProvince())){teacher.setQqProvince(user.getQqProvince());}

                User afterDetailUser = toGetOpenIdAndInsert(teacher);

                //返回一个跟数据库中完全匹配的用户信息
                User teacherUserInfo = userMapper.findUserByUserId(Integer.toString(afterDetailUser.getId()));
                apiResponse.setCode(200);
                Map<String,Object> map = new HashMap<>();
                map.put("user",teacherUserInfo);
                apiResponse.setData(map);
                apiResponse.setMsg("教师登录成功");
            }
        }else{           //不为1 则学生进行登录
            String cookie = studentMethod.studentLogin(user);
            if(cookie.length() >0 && user.getCode().length() > 0){
                ApiResponse<Map<String, Object>> studentTrueInfo = studentMethod.getStudentInfo(cookie);
                user.setTrueName((String)studentTrueInfo.getData().get("trueName"));
                user.setCollege((String)studentTrueInfo.getData().get("college"));
                user.setStudentMajorName((String)studentTrueInfo.getData().get("studentMajorName"));


                User afterDetailUser = toGetOpenIdAndInsert(user);
                User studentUserInfo = userMapper.findUserByUserId(Integer.toString(afterDetailUser.getId()));

                //登录成功,进行课表的爬取,数据库不存在即去爬虫
                ApiResponse<List<Course>> dbResponse= courseService.findCourseByTermAndStudentUserName(CONSTANT.DEFAULT_TERM.getDefaultTerm(), studentUserInfo.getUsername(),cookie);
                Map<String,Object> dataMap = new HashMap<>();
                List<Course> dbCourse = dbResponse.getData();
                if(dbCourse.size() != 0){  //返回现在的周数和课表
                    dataMap.put("courses",dbCourse);
                    dataMap.put("nowWeek",WeekSchedule.NOW_WEEK);
                    dataMap.put("user",studentUserInfo);
                    dataMap.put("nowTerm",CONSTANT.DEFAULT_TERM.getDefaultTerm());
                }else{
                    dbCourse = new ArrayList<>();
                    dataMap.put("courses",dbCourse);
                    dataMap.put("nowWeek",WeekSchedule.NOW_WEEK);
                    dataMap.put("user",studentUserInfo);
                    dataMap.put("nowTerm",CONSTANT.DEFAULT_TERM.getDefaultTerm());
                }
//                else{
//                    ReptileCourseOption reptileCourseOption = new ReptileCourseOption();
//                    reptileCourseOption.setXnxq01id(CONSTANT.DEFAULT_TERM.getDefaultTerm());
//                    ApiResponse<Map<String, Object>> reptileResponse = studentMethod.getStudentCourse(cookie, reptileCourseOption);
//
//                    //爬虫爬回来的课程里面是没有备注的，所以这里要在每个课程加上备注,优先入库再返回
//                    List<ReptileCourse> reptileCourses = (List<ReptileCourse>)reptileResponse.getData().get("courses");
//                    String bz = (String) reptileResponse.getData().get("bz");
//                    List<Course> returnCourse = new ArrayList<>();
//                    for (int i = 0; i < reptileCourses.size() ; i++) {
//                        ReptileCourse reptileCourse = reptileCourses.get(i);
//                        Course course = new Course();
//                        course.setStudentUsername(user.getUsername());
//                        course.setTerm(CONSTANT.DEFAULT_TERM.getDefaultTerm());
//                        course.setCourseNumber(reptileCourse.getNumber());
//                        course.setCourseName(reptileCourse.getName());
//                        course.setCourseZc(reptileCourse.getZc());
//                        course.setCourseJc(Integer.parseInt(reptileCourse.getJc()));
//                        course.setCourseRoom(reptileCourse.getRoom());
//                        course.setCourseTeacher(reptileCourse.getTeacher());
//                        course.setCourseXq(Integer.parseInt(reptileCourse.getXq()));
//                        course.setNote(bz);
//                        returnCourse.add(course);
//                    }
//                    threadPoolExecutor.execute(() ->{  //开辟一个新的线程去插入
//                        ApiResponse<Integer> apiResponse1 = courseService.insertBatch(returnCourse);
//                    });
//                    dataMap.put("courses", returnCourse);
//                    dataMap.put("nowWeek", WeekSchedule.NOW_WEEK);
//                    dataMap.put("user",studentUserInfo);
//                    dataMap.put("nowTerm",CONSTANT.DEFAULT_TERM.getDefaultTerm());
//                }
                apiResponse.setCode(200);
                apiResponse.setMsg("学生登录成功");
                studentMethod.studentLogout(cookie);
                apiResponse.setData(dataMap);
            }
        }
        return apiResponse;
    }

    /**
     * 通过id查询用户的信息,包括用户的课表、周次、学期,用于每次启动项目同步用户的信息到数据库的信息
     * @param id 用户的id
     * @return
     */
    @Override
    public ApiResponse<Map<String,Object>> findUserById(String id) {
        ApiResponse<Map<String,Object>> apiResponse = new ApiResponse<>();
        User user = userMapper.findUserByUserId(id);
        Map<String,Object> mapData = new HashMap<>();
        if(!ObjectUtils.isEmpty(user) && !user.getIsAssistant().equals(1)){    //为学生的时候去同步信息
            ApiResponse<List<Course>> apiResponse1 = courseService.findCourseByTermAndStudentUserName(CONSTANT.DEFAULT_TERM.getDefaultTerm(), user.getUsername());
            List<Course> courses = apiResponse1.getData();
            mapData.put("courses",courses);
            mapData.put("nowWeek",WeekSchedule.getNowWeek());
            mapData.put("nowTerm",CONSTANT.DEFAULT_TERM.getDefaultTerm());
            mapData.put("user",user);
            apiResponse.setData(mapData);
        }else if (!ObjectUtils.isEmpty(user)){  //不为学生
            mapData.put("user",user);
            apiResponse.setData(mapData);
        }else{
            apiResponse.setData(new HashMap<>());
        }
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    /**
     * @Description 用户的id数组
     * @param list 用户的id数组
     */
    @Override
    public ApiResponse deleteUserBatch(List<Integer> list) {
           ApiResponse apiResponse = new ApiResponse<>();
           userMapper.deleteBatch(list);
           apiResponse.setCode(200);
           apiResponse.setMsg("删除成功");
           return apiResponse;
    }

    /**
     * 查询所有的用户,并且做分页
     * @return
     */
    @Override
    public ApiResponse<MyPageInfo<User>> findAllUser(String pageNum, String pageSize) {
        ApiResponse<MyPageInfo<User>> apiResponse = new ApiResponse<>();
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<User> userList = userMapper.findAllUser();
        MyPageInfo<User> pageInfo = new MyPageInfo<>(userList);
        apiResponse.setData(pageInfo);

        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    /**
     * 更新用户信息，并且做同步锁，先更新后再查库获取
     * @param user
     * @return
     */
    @Override
    public ApiResponse<User> updateUser(User user) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        reentrantLock.lock();
        try{
            userMapper.updateUser(user);
        }finally {
            reentrantLock.unlock(); 
        }
        User updateUser = userMapper.findUserByUserId(user.getId().toString());
        apiResponse.setData(updateUser);
        apiResponse.setMsg("更新成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public List<User> findAllUserUsernameAndPassword() {
        List<User> allUserUsernameAndPassword = userMapper.findAllUserUsernameAndPassword();
        return allUserUsernameAndPassword;
    }
}
