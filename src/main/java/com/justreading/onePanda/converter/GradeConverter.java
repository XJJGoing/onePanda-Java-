package com.justreading.onePanda.converter;

import com.justreading.onePanda.common.bean.ReptileGrade;
import com.justreading.onePanda.grade.entity.Grade;
import com.justreading.onePanda.rules.Dto.StudentGradeDto;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @Description 成绩转换的，类
 * @date 2020 年 11 月 04 日 20:49
 */
public class GradeConverter {
    public static List<Grade> converterGrade(StudentGradeDto studentGradeDto,String studentUserName,String term){
        List<Grade> grades = new ArrayList<>();
        if(!ObjectUtils.isEmpty(studentGradeDto)){
            for (ReptileGrade reptileGrade : studentGradeDto.getGrades()){
                Grade grade = Grade.builder()
                        .studentUsername(studentUserName)
                        .term(term)
                        .gradeNumber(reptileGrade.getCourseNumber())
                        .gradeName(reptileGrade.getCourseName())
                        .gradeTime(reptileGrade.getTime())
                        .gradeCredit(Double.parseDouble(reptileGrade.getCredit()))
                        .examMethod(reptileGrade.getExamMethod())
                        .score(reptileGrade.getGrade())
                        .gradeKind(reptileGrade.getCourseKind())
                        .build();
                grades.add(grade);
            }
        }
        return grades;
    }
}
