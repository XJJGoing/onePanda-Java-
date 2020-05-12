package com.justreading.onePanda.feedback.controller;

import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.feedback.entity.Feedback;
import com.justreading.onePanda.feedback.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LYJ
 * @Description 反馈的controller
 * @date 2020 年 02 月 25 日 16:24
 */
@RestController
@Api(tags = "反馈模块")
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @MyLog("插入反馈信息:/insert")
    @PostMapping("/insert")
    @ApiOperation("插入用户反馈信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "multipartFiles",value = "反馈图片"),
            @ApiImplicitParam(name = "feedbackContent",value = "反馈内容",required = true),
            @ApiImplicitParam(name = "contact",value = "反馈者的联系方式"),
            @ApiImplicitParam(name = "rate",value = "评分")})
    public ApiResponse<Feedback> insertFeedback(@RequestParam("filePath") MultipartFile[] filePath,
                                                @RequestParam("feedbackContent") String feedbackContent,
                                                @RequestParam(value = "contact")String contact,
                                                @RequestParam("rate")Integer rate
                                                ){

          ApiResponse<Feedback> apiResponse = feedbackService.insertFeedback(filePath, feedbackContent, contact, rate);
          return apiResponse;
    }

    @MyLog("插入反馈信息:/insertWithoutImg")
    @ApiOperation("插入用户反馈信息---没有图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedbackContent",value = "反馈内容"),
            @ApiImplicitParam(name = "contact",value = "反馈者的联系方式"),
            @ApiImplicitParam(name = "rate",value = "评分")})
    @PostMapping("/insertWithoutImg")
    public ApiResponse<Feedback> insertFeedbackWithoutImg(@RequestParam(name = "feedbackContent") String feedbackContent,
                                                          @RequestParam(name = "contact")String contact,
                                                          @RequestParam(name = "rate")Integer rate){

        ApiResponse<Feedback> apiResponse = feedbackService.insertFeedbackWithoutImg(feedbackContent, contact, rate);
        return  apiResponse;
    }

}
