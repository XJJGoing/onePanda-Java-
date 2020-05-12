package com.justreading.onePanda.answer.controller;

import com.justreading.onePanda.answer.entity.Answer;
import com.justreading.onePanda.answer.service.AnswerService;
import com.justreading.onePanda.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LYJ
 * @Description 评论模块
 * @date 2020 年 02 月 18 日 17:20
 */
@Api(tags = "回复模块")
@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @ApiOperation("插入回复")
    @PostMapping("/insertAnswer")
    public ApiResponse insertAnswer(@RequestBody(required = true) Answer answer){
        ApiResponse apiResponse = answerService.insertAnswer(answer);
        return apiResponse;
    }

    @ApiOperation("批量删除回复")
    @PostMapping("/deleteAnswerBatch")
    public ApiResponse deleteAnswerBatch(@RequestBody(required = true) List<Integer> list){
        ApiResponse apiResponse = answerService.deleteAnswerBatch(list);
        return apiResponse;
    }

    @ApiOperation("批量更新")
    @PostMapping("/updateAnswerBatch")
    public ApiResponse updateAnswerBatch(@RequestBody(required = true) List<Answer> answers){
        ApiResponse apiResponse = answerService.updateAnswerBatch(answers);
        return apiResponse;
    }
}
