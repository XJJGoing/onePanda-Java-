package com.justreading.onePanda.feedback.service.impl;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.TenXunUtil;
import com.justreading.onePanda.feedback.entity.Feedback;
import com.justreading.onePanda.feedback.mapper.FeedbackMapper;
import com.justreading.onePanda.feedback.service.FeedbackService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.apache.xmlbeans.impl.common.SniffedXmlInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 25 日 16:10
 */
@Service
public class FeedbackServiceImpl  implements FeedbackService {

    @Autowired
    private COSClient cosClient;

    @Autowired
    private TenXunUtil tenXunUtil;

    @Autowired
    private FeedbackMapper feedBackMapper;

    /**
     *  由于小程序端无法实现多图片上传
     * @param multipartFiles 上传的一组图片
     * @param feedbackContent 反馈的内容
     * @param contact 反馈者的联系方式
     * @param rate 反馈的评分
     * @return
     */
    @Override
    public ApiResponse<Feedback> insertFeedback(MultipartFile[] multipartFiles, String feedbackContent, String contact, Integer rate) {
        ApiResponse<Feedback> apiResponse = new ApiResponse<>();
        StringBuffer feedbackImg = new StringBuffer();
        String dir = "feedback";
        String bucketName = tenXunUtil.getOnePandaBucket();
        for(MultipartFile multipartFile :multipartFiles){
            String originalFileName = multipartFile.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName =  UUID.randomUUID() + "";
            String key = dir + "/" + fileName + suffix;
            try {

                //创建文件模板
                File tempFile = File.createTempFile(fileName,suffix);
                multipartFile.transferTo(tempFile);
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, tempFile);
                PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

                //返回访问的url,并且拼接进入img存入数据库
                feedbackImg.append(tenXunUtil.getPath() + key );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Feedback feedback  = new Feedback();
        feedback.setFeedbackImg(feedbackImg.toString());
        feedback.setContact(contact);
        feedback.setRate(rate);
        feedback.setFeedbackContent(feedbackContent);
        feedBackMapper.insertFeedBack(feedback);
        apiResponse.setData(feedback);
        apiResponse.setMsg("反馈成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public ApiResponse<Feedback> insertFeedbackWithoutImg(String feedbackContent, String contact, Integer rate) {
        ApiResponse<Feedback> apiResponse = new ApiResponse<>();
        Feedback feedback = new Feedback();
        feedback.setFeedbackContent(feedbackContent);
        feedback.setContact(contact);
        feedback.setRate(rate);
        feedBackMapper.insertFeedBack(feedback);
        apiResponse.setData(feedback);
        apiResponse.setCode(200);
        apiResponse.setMsg("反馈成功");
        return  apiResponse;
    }


}
