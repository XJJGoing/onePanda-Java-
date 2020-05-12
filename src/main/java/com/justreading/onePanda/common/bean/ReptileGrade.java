package com.justreading.onePanda.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 学生成绩的通用实体类
 * @date 2020 年 02 月 15 日 14:07
 */
@ApiModel("返回的成绩的格式")
public class ReptileGrade implements Serializable {
    @ApiModelProperty(value = "课程号",required = true)
    private String courseNumber;

    @ApiModelProperty(value = "课程名称",required = true)
    private String  courseName;

    @ApiModelProperty(value = "成绩",required = true)
    private  String grade;

    @ApiModelProperty(value = "学分",required = true)
    private  String  credit;

    @ApiModelProperty(value = "总学时",required = true)
    private  String  time;

    @ApiModelProperty(value = "考核方式",required = true)
    private  String  examMethod;

    @ApiModelProperty(value = "课程的类型,公共选课不算绩点",required = true)
    private  String courseKind;


    public ReptileGrade() {
    }

    public String getCourseKind() {
        return courseKind;
    }

    public void setCourseKind(String courseKind) {
        this.courseKind = courseKind;
    }

    public String getExamMethod() {
        return examMethod;
    }

    public void setExamMethod(String examMethod) {
        this.examMethod = examMethod;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ReptileGrade{" +
                "courseNumber='" + courseNumber + '\'' +
                ", courseName='" + courseName + '\'' +
                ", grade='" + grade + '\'' +
                ", credit='" + credit + '\'' +
                ", time='" + time + '\'' +
                ", examMethod='" + examMethod + '\'' +
                ", gradeKind='" + courseKind + '\'' +
                '}';
    }
}
