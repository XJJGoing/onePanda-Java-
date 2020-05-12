package com.justreading.onePanda.course.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 数据库中course表对应的学生实体
 * @date 2020 年 02 月 16 日 14:10
 */
@ApiModel("学生课表的实体")
public class Course implements Serializable {

    @ApiModelProperty(value = "课程的id",example = "1")
    private Integer id;

    @ApiModelProperty("课程名称")
    private  String courseName;

    @ApiModelProperty("课程号")
    private  String courseNumber;

    @ApiModelProperty("课程的周次")
    private  String courseZc;

    @ApiModelProperty(value = "课程的横过来的节次",example = "0")
    private  Integer courseJc;

    @ApiModelProperty(value = "课程的竖下来的节次",example = "0")
    private  Integer courseXq;

    @ApiModelProperty(value = "课程的老师")
    private  String  courseTeacher;

    @ApiModelProperty(value = "课程的教室")
    private  String  courseRoom;

    @ApiModelProperty(value = "学生的学号")
    private  String  studentUsername;

    @ApiModelProperty(value = "属于的学期")
    private  String  term;

    @ApiModelProperty(value = "创建的时间")
    private  String createTime;

    @ApiModelProperty(value = "修改的时间")
    private  String updateTime;

    @ApiModelProperty(value = "课程的备注信息")
    private  String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseZc() {
        return courseZc;
    }

    public void setCourseZc(String courseZc) {
        this.courseZc = courseZc;
    }

    public Integer getCourseJc() {
        return courseJc;
    }

    public void setCourseJc(Integer courseJc) {
        this.courseJc = courseJc;
    }

    public Integer getCourseXq() {
        return courseXq;
    }

    public void setCourseXq(Integer courseXq) {
        this.courseXq = courseXq;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
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

    @Override
    public String toString() {
        return "StudentCourse{" +
                "courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", courseZc='" + courseZc + '\'' +
                ", courseJc=" + courseJc +
                ", courseXq=" + courseXq +
                ", courseTeacher='" + courseTeacher + '\'' +
                ", courseRoom='" + courseRoom + '\'' +
                ", studentUsername='" + studentUsername + '\'' +
                ", term='" + term + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
