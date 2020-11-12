package com.justreading.onePanda.user.service;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import com.justreading.onePanda.request.UserRequest;
import com.justreading.onePanda.user.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author LYJ
 * @Description  用户的通用服务
 * @date 2020 年 02 月 15 日 12:12
 */
public interface UserService {
    /**
     *  用户的登录
     * @param user  用户的信息
     */
     ApiResponse<Map<String,Object>> userLogin(User user);

    /**
     * 根据id查询用户
     * @param id 用户的id
     * @return
     */
     ApiResponse<Map<String,Object>> findUserById(String id);

    /**
     * 批量删除用户
     * @param list 用户的id数组
     */
     ApiResponse deleteUserBatch(List<Integer> list);

    /**
     * @Description 查询所有的用户
     * @return
     */
     ApiResponse<MyPageInfo<User>>  findAllUser(String pageNum, String pageSize);

    /**
     *  更新用户信息
     * @param user
     * @return
     */
     ApiResponse<User> updateUser(User user);

    /**
     * 查询学生的所有的账号和密码用于爬虫
     * @return
     */
     List<User> findAllUserUsernameAndPassword();

    /**
     * 改造登录接口
     * @param userRequest 改造登录接口
     * @return
     */
     ApiResponse<Map<String,Object>> exchangeLoginService(UserRequest userRequest);
    
}
