package com.justreading.onePanda.userComentPraise.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 记录该用户是否赞过该评论的实体,这张表就这个作用
 * @date 2020 年 02 月 18 日 19:04
 */
@ApiModel("进来该用户是否赞过该评论的实体")
public class UserCommentPraise implements Serializable {
    @ApiModelProperty(value = "id",example = "0")
    private Integer id;

    @ApiModelProperty(value = "用户的id",example = "0")
    private Integer userId;

    @ApiModelProperty(value = "评论的id",example = "0")
    private Integer commentId;

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
        return "UserCommentPraise{" +
                "id=" + id +
                ", userId=" + userId +
                ", commentId=" + commentId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
