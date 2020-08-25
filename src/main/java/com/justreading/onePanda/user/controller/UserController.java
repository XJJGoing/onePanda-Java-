package com.justreading.onePanda.user.controller;

import com.github.pagehelper.PageInfo;
import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import com.justreading.onePanda.common.StudentMethod;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 15 日 12:12
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentMethod studentMethod;

    /**
     * 授权登录
     * 微信或者qq用户的code 存放在user中
     * @param user 用户的信息
     * @return
     */
    @MyLog("用户登录: /login")
    @PostMapping("/login")
    @ApiOperation("用户授权登录,根据qq_openid或者wx_openid做唯一标识，存在即更新，不存在即插入")
    public ApiResponse<Map<String, Object>> userLogin(@RequestBody(required = true) User user){
        ApiResponse<Map<String, Object>> apiResponse = userService.userLogin(user);
        return apiResponse;
    }

    /**
     *  根据id查询用户的信息
     * @param id
     * @return
     */
    @MyLog("通过id查询用户信息: /queryById")
    @PostMapping("/queryById")
    @ApiOperation("根据id查询用户的信息")
    public ApiResponse<Map<String,Object>> queryUserById(@RequestParam("id")String id){
        ApiResponse<Map<String, Object>> apiResponse = userService.findUserById(id);
        return apiResponse;
    }

    /**
     * 更新用户信息
     * @param user 更新的用户信息
     * @return
     */
    @MyLog("更新用户的信息: /update")
    @PostMapping("/update")
    @ApiOperation("更新用户信息")
    public ApiResponse<User> updateUser(@RequestBody(required = true)User user){
        ApiResponse<User> apiResponse = userService.updateUser(user);
        return apiResponse;
    }

    /**
     * 批量删除用户
     * @param list
     * @return
     */
    @MyLog("批量删除用户: /deleteBatch")
    @PostMapping("/deleteBatch")
    @ApiOperation("批量删除用户")
    public ApiResponse deleteBatchUser(@RequestBody(required = true)List<Integer> list){
        ApiResponse apiResponse = userService.deleteUserBatch(list);
        return apiResponse;
    }

    @MyLog("查询所有的用户:/queryAll")
    @GetMapping("/queryAll")
    @ApiOperation("查询所有的用户")
    public ApiResponse<MyPageInfo<User>> findAllUser(@RequestParam(required = true,defaultValue = "1")String pageNum, @RequestParam(required = true,defaultValue = "5")String pageSize){
        ApiResponse<MyPageInfo<User>> allUser = userService.findAllUser(pageNum, pageSize);
        return  allUser;
    }

    /**
     * 帮张同学写的接口,只有学生的学号和密码
     * @param user
     * @return
     */
    @ApiOperation("帮张同学写的接口")
    @PostMapping("/studentLogin")
    public ApiResponse<User> studentLogin(@RequestBody(required = true) User user){
        String cookie = studentMethod.studentLogin(user);
        ApiResponse<User> apiResponse = new ApiResponse<>();
        if(!ObjectUtils.isEmpty(cookie)){
            apiResponse.setMsg("登录成功");
            apiResponse.setCode(200);
            apiResponse.setData(user);
        }else{
            apiResponse.setMsg("登录失败");
            apiResponse.setCode(400);
        }
        return apiResponse;
    }
}
