package com.justreading.onePanda.answer.entity;

import com.justreading.onePanda.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 回复实体类
 * @date 2020 年 02 月 18 日 16:46
 */
@ApiModel("回复的实体类")
public class Answer implements Serializable {

    @ApiModelProperty(value = "回复的id",required = true,example = "0")
    private Integer id;

    @ApiModelProperty(value = "回复的内容",required = true)
    private String answerContent;

    @ApiModelProperty(value = "回复者的id",required = true,example = "0")
    private Integer userId;

    @ApiModelProperty(value = "回复的评论的id",required = true,example = "0")
    private Integer commentId;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "评论回复者的用户信息,数据库中并无此消息")
    private User answerUser;


    public User getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(User answerUser) {
        this.answerUser = answerUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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
        return "Answer{" +
                "id=" + id +
                ", answerContent='" + answerContent + '\'' +
                ", userId=" + userId +
                ", commentId=" + commentId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", user=" + answerUser +
                '}';
    }
}
