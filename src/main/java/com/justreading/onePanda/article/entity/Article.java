package com.justreading.onePanda.article.entity;

import com.justreading.onePanda.comment.entity.Comment;
import com.justreading.onePanda.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author LYJ
 * @Description 文章的实体类
 * @date 2020 年 02 月 17 日 18:59
 */
@ApiModel("文章的实体类")
public class Article implements Serializable,Comparable<Article> {
    @ApiModelProperty(value = "文章的id",example = "1")
    private Integer id;

    @ApiModelProperty("文章的标题")
    private String title;

    @ApiModelProperty("文章的作者")
    private String author;

    @ApiModelProperty("文章的内容")
    private String articleContent;

    @ApiModelProperty(value = "文章的状态,1可见 2不可见",example = "1")
    private Integer status;

    @ApiModelProperty(value = "文章浏览的次数",example = "1")
    private Integer scanTimes;

    @ApiModelProperty(value = "文章作者的id",example = "1")
    private  Integer userId;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty(value = "1: 审核通过 0:未审核 2: 审核未通过",example = "0")
    private Integer isCheck;

    @ApiModelProperty(value = "文章的配图")
    private String layout;

    @ApiModelProperty(value = "文章的类型1")
    private String articleKind1;

    @ApiModelProperty(value = "文章的类型2")
    private String articleKind2;

    @ApiModelProperty(value = "文章的摘要")
    private String articleAbstract;

    @ApiModelProperty(value = "文章的作者信息,数据库中并无该字段")
    private User articleUser;


    @ApiModelProperty(value = "文章的评论,数据库中并无该字段信息")
    private List<Comment> comments;

    public String getArticleKind1() {
        return articleKind1;
    }

    public void setArticleKind1(String articleKind1) {
        this.articleKind1 = articleKind1;
    }

    public String getArticleKind2() {
        return articleKind2;
    }

    public void setArticleKind2(String articleKind2) {
        this.articleKind2 = articleKind2;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public User getArticleUser() {
        return articleUser;
    }

    public void setArticleUser(User articleUser) {
        this.articleUser = articleUser;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getScanTimes() {
        return scanTimes;
    }

    public void setScanTimes(Integer scanTimes) {
        this.scanTimes = scanTimes;
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
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", status=" + status +
                ", scanTimes=" + scanTimes +
                ", userId=" + userId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isCheck=" + isCheck +
                ", layout='" + layout + '\'' +
                ", articleKind1='" + articleKind1 + '\'' +
                ", articleKind2='" + articleKind2 + '\'' +
                ", articleAbstract='" + articleAbstract + '\'' +
                ", articleUser=" + articleUser +
                ", comments=" + comments +
                '}';
    }

    @Override
    public int compareTo(Article o) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        TemporalAccessor parse = dateTimeFormatter.parse(this.getCreateTime());
        TemporalAccessor parse1 = dateTimeFormatter.parse(o.getCreateTime());
        LocalDateTime localDateTime = LocalDateTime.from(parse);
        LocalDateTime localDateTime1 = LocalDateTime.from(parse1);
        return (int) (Instant.from(localDateTime.atZone(ZoneId.systemDefault())).toEpochMilli() - Instant.from(localDateTime1.atZone(ZoneId.systemDefault())).toEpochMilli());
    }
}
