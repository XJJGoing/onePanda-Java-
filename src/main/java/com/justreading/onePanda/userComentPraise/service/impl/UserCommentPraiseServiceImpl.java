package com.justreading.onePanda.userComentPraise.service.impl;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.userComentPraise.entity.UserCommentPraise;
import com.justreading.onePanda.userComentPraise.mapper.UserCommentPraiseMapper;
import com.justreading.onePanda.userComentPraise.service.UserCommentPraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 18 日 19:18
 */
@Service
public class UserCommentPraiseServiceImpl implements UserCommentPraiseService {

    @Autowired
    private UserCommentPraiseMapper userCommentPraiseMapper;

    @Override
    public ApiResponse<UserCommentPraise> insertUserCommentPraise(UserCommentPraise userCommentPraise) {
        ApiResponse<UserCommentPraise> apiResponse = new ApiResponse<>();
        userCommentPraiseMapper.insertUserCommentPraise(userCommentPraise);
        apiResponse.setCode(200);
        apiResponse.setMsg("插入成功");
        apiResponse.setData(userCommentPraise);
        return apiResponse;
    }

    @Override
    public ApiResponse deleteUserCommentPraiseBatch(List<Integer> list) {
        ApiResponse apiResponse = new ApiResponse();
        userCommentPraiseMapper.deleteUserCommentPraiseBatch(list);
        apiResponse.setMsg("删除成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public ApiResponse<List<UserCommentPraise>> findUserCommentPraiseByCommentId(String id) {
        ApiResponse<List<UserCommentPraise>> apiResponse = new ApiResponse<>();
        List<UserCommentPraise> userCommentPraises = userCommentPraiseMapper.findUserCommentPraiseByCommentId(id);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        apiResponse.setData(userCommentPraises);
        return apiResponse;
    }
}
