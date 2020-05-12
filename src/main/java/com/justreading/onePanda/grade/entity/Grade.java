package com.justreading.onePanda.grade.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 对应数据库中的t_grade表
 * @date 2020 年 02 月 16 日 15:46
 */
@ApiModel("学生的成绩实体类")
public class Grade implements Serializable {
    @ApiModelProperty(value = "成绩的id",example = "")
    private Integer id;

    @ApiModelProperty("学科号")
    private String gradeNumber;

    @ApiModelProperty("学科的名称")
    private String gradeName;

    @ApiModelProperty(value = "学分",example = "1.0")
    private Double gradeCredit;

    @ApiModelProperty("学时")
    private String gradeTime;

    @ApiModelProperty(value = "分数")
    private String score;

    @ApiModelProperty("考核方式")
    private String examMethod;

    @ApiModelProperty("课程的类型，校公选课不算绩点")
    private String gradeKind;

    @ApiModelProperty("学生的学号")
    private  String studentUsername;

    @ApiModelProperty("学期")
    private String term;

    @ApiModelProperty("创建时间")
    private  String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;


    public String getGradeKind() {
        return gradeKind;
    }

    public void setGradeKind(String gradeKind) {
        this.gradeKind = gradeKind;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeNumber() {
        return gradeNumber;
    }

    public void setGradeNumber(String gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Double getGradeCredit() {
        return gradeCredit;
    }

    public void setGradeCredit(Double gradeCredit) {
        this.gradeCredit = gradeCredit;
    }

    public String getGradeTime() {
        return gradeTime;
    }

    public void setGradeTime(String gradeTime) {
        this.gradeTime = gradeTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExamMethod() {
        return examMethod;
    }

    public void setExamMethod(String examMethod) {
        this.examMethod = examMethod;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
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
}
