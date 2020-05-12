package com.justreading.onePanda.userComentPraise.controller;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.userComentPraise.entity.UserCommentPraise;
import com.justreading.onePanda.userComentPraise.service.UserCommentPraiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LYJ
 * @Description 确定用户是否赞过评论的模块
 * @date 2020 年 02 月 18 日 19:29
 */
@Api(tags = "确定用户是否赞过评论模块")
@RestController
@RequestMapping("/userCommentPraise")
public class UserCommentPraiseController {

    @Autowired
    private UserCommentPraiseService userCommentPraiseService;


    @ApiOperation("插入")
    @PostMapping("/insert")
    public ApiResponse<UserCommentPraise> insert(@RequestBody(required = true) UserCommentPraise userCommentPraise){
        ApiResponse<UserCommentPraise> apiResponse = userCommentPraiseService.insertUserCommentPraise(userCommentPraise);
        return apiResponse;
    }

    @ApiOperation("删除")
    @PostMapping("/deleteBatch")
    public ApiResponse deleteBatch(@RequestBody(required = true) List<Integer> list){
        ApiResponse apiResponse = userCommentPraiseService.deleteUserCommentPraiseBatch(list);
        return apiResponse;
    }

    @ApiOperation("通过评论的id进行查询")
    @GetMapping("/queryByCommentId")
    public ApiResponse<List<UserCommentPraise>> findByCommentId(@RequestParam(value = "id",required = true)String id){
        ApiResponse<List<UserCommentPraise>> apiResponse = userCommentPraiseService.findUserCommentPraiseByCommentId(id);
        return apiResponse;
    }
}
