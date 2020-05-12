package com.justreading.onePanda.grade.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @Description 这个实体类用于存放返回每个学生，学生里面包含成绩的数组,数据库中并不存在此实体
 * @date 2020 年 02 月 27 日 19:46
 */
@ApiModel("用户成绩一对多实体类")
public class UserGrade {
    private Integer id;

    private String trueName;

    private String username;

    private List<Grade> grades = new ArrayList<>();

    private Double gradePoint;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Double gradePoint) {
        this.gradePoint = gradePoint;
    }

    @Override
    public String toString() {
        return "UserGrade{" +
                "id=" + id +
                ", trueName='" + trueName + '\'' +
                ", username='" + username + '\'' +
                ", grades=" + grades +
                ", gradePoint=" + gradePoint +
                '}';
    }
}
