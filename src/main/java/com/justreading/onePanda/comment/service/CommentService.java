package com.justreading.onePanda.comment.service;

import com.justreading.onePanda.article.entity.Article;
import com.justreading.onePanda.comment.entity.Comment;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;

import java.util.List;

/**
 * @author LYJ
 * @Description 评论的接口
 * @date 2020 年 02 月 18 日 16:09
 */
public interface CommentService {
    /**
     * 插入评论
     * @param comment 评论
     * @return
     */
    public ApiResponse<Comment> insertComment(Comment comment);

    /**
     * 删除评论
     * @param list 评论列表
     * @return
     */
    public ApiResponse deleteCommentBatch(List<Integer> list);

    /**
     * 批量修改评论
     * @return
     */
    public ApiResponse updateCommentBatch(List<Comment> list);

    /**
     * 查询完文章的评论，并且去查询文章评论的评论者的信息以及，回复列表。
     * @param id 文章的id
     * @return
     */
    public ApiResponse<List<Comment>> findCommentByArticleId(String id);

}
