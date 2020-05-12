package com.justreading.onePanda.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author LYJ
 * @Description 学生课程的通用实体类
 * @date 2020 年 02 月 15 日 14:05
 */
@ApiModel("学生的课程信息")
public class ReptileCourse {
    @ApiModelProperty("课程号")
    private String number;

    @ApiModelProperty("教室")
    private String room;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("节次")
    private String  jc;

    @ApiModelProperty("周次")
    private String  zc;

    @ApiModelProperty("老师")
    private String  teacher;

    @ApiModelProperty("xq")
    private String xq;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    public String getZc() {
        return zc;
    }

    public void setZc(String zc) {
        this.zc = zc;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    @Override
    public String toString() {
        return "Course{" +
                "number='" + number + '\'' +
                ", room='" + room + '\'' +
                ", name='" + name + '\'' +
                ", jc='" + jc + '\'' +
                ", zc='" + zc + '\'' +
                ", teacher='" + teacher + '\'' +
                ", xq='" + xq + '\'' +
                '}';
    }
}

