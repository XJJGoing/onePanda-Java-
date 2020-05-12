package com.justreading.onePanda.userComentPraise.service;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.userComentPraise.entity.UserCommentPraise;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 18 日 19:18
 */
public interface UserCommentPraiseService {
    /**
     * 插入
     * @param userCommentPraise 记录
     * @return
     */
    public ApiResponse<UserCommentPraise> insertUserCommentPraise(UserCommentPraise userCommentPraise);

    /**
     * 批量删除这样用户有没有赞过，取消赞就删除
     * @param list 批量删除
     * @return
     */
    public ApiResponse deleteUserCommentPraiseBatch(List<Integer> list);

    /**
     * 通过评论的id查找
     * @param id 评论的id
     * @return
     */
    public ApiResponse<List<UserCommentPraise>> findUserCommentPraiseByCommentId(String id);

}
