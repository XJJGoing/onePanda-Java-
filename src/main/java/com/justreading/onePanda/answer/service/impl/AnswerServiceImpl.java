package com.justreading.onePanda.answer.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justreading.onePanda.answer.entity.Answer;
import com.justreading.onePanda.answer.mapper.AnswerMapper;
import com.justreading.onePanda.answer.service.AnswerService;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYJ
 * @Description 回复的业务
 * @date 2020 年 02 月 18 日 16:45
 */
@Service
public class AnswerServiceImpl implements AnswerService{

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponse<Answer> insertAnswer(Answer answer) {
        ApiResponse<Answer> apiResponse = new ApiResponse();
        answerMapper.insertAnswer(answer);
        apiResponse.setData(answer);
        apiResponse.setCode(200);
        apiResponse.setMsg("插入成功");
        return apiResponse;
    }

    @Override
    public ApiResponse deleteAnswerBatch(List<Integer> list) {
        ApiResponse apiResponse = new ApiResponse();
        answerMapper.deleteAnswerBatch(list);
        apiResponse.setMsg("删除成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public ApiResponse updateAnswerBatch(List<Answer> answers) {
        ApiResponse apiResponse = new ApiResponse();
        answerMapper.updateAnswerBatch(answers);
        apiResponse.setCode(200);
        apiResponse.setMsg("更新成功");
        return apiResponse;
    }

    @Override
    public ApiResponse<List<Answer>> findAnswerByCommentId(String id) {
        ApiResponse<List<Answer>> apiResponse = new ApiResponse<>();
        List<Answer> answerList = answerMapper.findAnswerByCommentId(id);
        for(Answer answer: answerList){
            Integer userId = answer.getUserId();
            User answerUser = userMapper.findUserByUserId(Integer.toString(userId));
            answer.setAnswerUser(answerUser);
        }
        apiResponse.setData(answerList);
        apiResponse.setMsg("查询成功");
        apiResponse.setCode(200);
        return apiResponse;
    }
}
