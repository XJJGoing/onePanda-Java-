package com.justreading.onePanda.comment.entity;

import com.justreading.onePanda.answer.entity.Answer;
import com.justreading.onePanda.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author LYJ
 * @Description 评论模块
 * @date 2020 年 02 月 18 日 15:46
 */

@ApiModel("评论格式")
public class Comment implements Serializable {

    @ApiModelProperty(value = "评论的id",example = "1")
    private Integer id;

    @ApiModelProperty(value = "评论的内容")
    private String commentContent;

    @ApiModelProperty(value = "文章的id",required = true,example = "0")
    private Integer articleId;

    @ApiModelProperty(value = "评论的赞的个数",example = "0")
    private Integer praiseNumber;

    @ApiModelProperty(value = "评论的用户的id",required = true,example = "0")
    private Integer userId;

    @ApiModelProperty(value = "评论创建的时间")
    private String createTime;

    @ApiModelProperty(value = "评论的更新时间")
    private String updateTime;

    @ApiModelProperty(value = "评论者的用户信息,数据库中并无此字段")
    private User commentUser;

    @ApiModelProperty(value = "评论的回复列表,数据库中并无此字段")
    private List<Answer> answerList;


    public User getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(User commentUser) {
        this.commentUser = commentUser;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(Integer praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        return "Comment{" +
                "id=" + id +
                ", commentContent='" + commentContent + '\'' +
                ", articleId=" + articleId +
                ", praiseNumber=" + praiseNumber +
                ", userId=" + userId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", user=" + commentUser +
                ", answerList=" + answerList +
                '}';
    }
}
