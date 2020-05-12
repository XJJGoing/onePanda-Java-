package com.justreading.onePanda.answer.service;

import com.justreading.onePanda.answer.entity.Answer;
import com.justreading.onePanda.common.ApiResponse;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 18 日 16:45
 */
public interface AnswerService {
    /**
     * 插入回复
     * @param answer 回复的id
     * @return
     */
    public ApiResponse<Answer> insertAnswer(Answer answer);

    /**
     * 删除回复
     * @param list 回复的id list
     * @return
     */
    public ApiResponse deleteAnswerBatch(List<Integer> list);

    /**
     * 更新回复
     * @param answers 回复
     * @return
     */
    public ApiResponse updateAnswerBatch(List<Answer> answers);

    /**
     * 通过评论的id查询回复
     * @param id 评论的id
     * @return
     */
    public ApiResponse<List<Answer>> findAnswerByCommentId(String id);
}
