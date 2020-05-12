package com.justreading.onePanda.feedback.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author LYJ
 * @Description 反馈的实体类
 * @date 2020 年 02 月 25 日 16:10
 */
@ApiModel("反馈的实体类")
public class Feedback {
    @ApiModelProperty(value = "反馈的id",example = "0")
    private Integer id;

    @ApiModelProperty(value = "反馈的内容")
    private String feedbackContent;

    @ApiModelProperty(value = "反馈提供的图片")
    private String feedbackImg;

    @ApiModelProperty(value = "反馈者的联系方式")
    private String contact;

    @ApiModelProperty(value = "软件的评分",example = "5")
    private Integer rate;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackImg() {
        return feedbackImg;
    }

    public void setFeedbackImg(String feedbackImg) {
        this.feedbackImg = feedbackImg;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        return "FeedBack{" +
                "id=" + id +
                ", feedbackContent='" + feedbackContent + '\'' +
                ", feedbackImg='" + feedbackImg + '\'' +
                ", contact='" + contact + '\'' +
                ", rate=" + rate +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
