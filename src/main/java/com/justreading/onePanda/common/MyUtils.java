package com.justreading.onePanda.common;

import com.justreading.onePanda.grade.entity.Grade;
import com.justreading.onePanda.grade.entity.UserGrade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @Description 封装一些公用的方法
 * @date 2020 年 02 月 27 日 20:14
 */
@Component
public class MyUtils {
    /**
     *
     * @param userGradeList 返回给老师的学生：绩点 = 1:n 模式的对象数组
     * @return
     */
    public List<UserGrade> calculateGradePoint(List<UserGrade> userGradeList){
        List<UserGrade> userGrades = new ArrayList<>();
           for (UserGrade userGrade : userGradeList){
               UserGrade userGrade1 = new UserGrade();
               userGrade1.setTrueName(userGrade.getTrueName());
               userGrade1.setUsername(userGrade.getUsername());
               userGrade1.setId(userGrade.getId());
               List<Grade> grades = userGrade.getGrades();
               userGrade1.setGrades(grades);
               List<Grade> calculateGradeList = new ArrayList<>();    //用于计算绩点的数组
               for (Grade grade : grades){
                   if(grade.getGradeName().equals("体育1") ||  grade.getGradeName().equals( "体育2")
                           || grade.getGradeName().equals("体育3") || grade.getGradeName().equals("体育4")
                           || grade.getGradeName().equals("三年级体育专项") || grade.getGradeKind().equals("任选"))
                   {
                       continue;
                   }
                   switch (grade.getScore()){
                       case "优":
                           grade.setScore("95");
                           break;
                       case "良":
                           grade.setScore("85");
                           break;
                       case "中":
                           grade.setScore("75");
                           break;
                       case "及格":
                           grade.setScore("65");
                           break;
                       case "不及格":
                           grade.setScore("0");
                           break;
                       case "通过":
                           grade.setScore("75");
                           break;
                       case  "不通过":
                           grade.setScore("0");
                           break;
                       default:
                           break;
                   }
                   if(Integer.parseInt(grade.getScore()) >= 60){
                       calculateGradeList.add(grade);
                   }
               }
               Double gradePoint = calculate(calculateGradeList);
               userGrade1.setGradePoint(gradePoint);
               userGrades.add(userGrade1);
           }
           return userGrades;
    }

    /**
     * 用于计算绩点
     * @param grades 用户计算绩点的成绩
     * @return
     */
    private Double calculate(List<Grade> grades){
        Double allMolecule = 0.0;           //分子上的总合
        Double allDenominator = 0.0;        //分母总合
        for (Grade grade : grades){
            Double credit = grade.getGradeCredit();
            Double score =  (Double.parseDouble(grade.getScore())/10) - 5.0;
            allMolecule = allMolecule + (credit * score);
            allDenominator = allDenominator + credit;
        }
        return (double)Math.round((allMolecule / allDenominator)*100)/100;
    }
}
