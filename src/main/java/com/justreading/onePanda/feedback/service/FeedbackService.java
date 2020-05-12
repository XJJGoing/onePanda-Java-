package com.justreading.onePanda.feedback.service;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.feedback.entity.Feedback;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 25 日 16:10
 */

public interface FeedbackService {

    /**
     *
     * @param multipartFiles 上传的一组图片
     * @param feedbackContent 反馈的内容
     * @param contact 反馈者的联系方式
     * @param rate 反馈的评分
     * @return
     */
    public ApiResponse<Feedback> insertFeedback(MultipartFile[] multipartFiles,String feedbackContent,String contact,Integer rate);

    /**
     * 插入反馈但是没有图片
     * @param feedbackContent
     * @param contact
     * @param rate
     * @return
     */
    public ApiResponse<Feedback> insertFeedbackWithoutImg(String feedbackContent,String contact,Integer rate);

}
