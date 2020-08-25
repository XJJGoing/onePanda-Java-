package com.justreading.onePanda.grade.mapper;

import com.justreading.onePanda.course.entity.Course;
import com.justreading.onePanda.grade.entity.Grade;
import com.justreading.onePanda.grade.entity.Grade;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author LYJ
 * @Description grade的动态sql提供类
 * @date 2020 年 02 月 16 日 15:59
 */
public class GradeProvider {
    public String deleteGradeBatch(List<Integer> list){
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < list.size() ; i++) {
            if(i == list.size() - 1){
                ids.append(list.get(i));
            }else{
                ids.append(list.get(i) + ",");
            }
        }
        return new SQL(){{
            DELETE_FROM("t_grade");
            WHERE("id in (" + ids + ")");
        }}.toString();
    }

    public String updateGradeBatch(List<Grade> list){
        StringBuffer updateSqlList = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Grade grade = list.get(i);
            SQL sql = new SQL();
            sql.UPDATE("t_grade");
            if(grade.getGradeName() != null){
                sql.SET("grade_name = '" + grade.getGradeName()+"'");
            }if(grade.getGradeNumber() != null){
                sql.SET("grade_number = '" + grade.getGradeNumber() + "'");
            }if(grade.getGradeCredit() != null){
                sql.SET("grade_credit = " + grade.getGradeCredit());
            }if(grade.getGradeTime() != null){
                sql.SET("grade_time = '" + grade.getGradeTime() + "'");
            }if(grade.getScore() != null){
                sql.SET("score = '" + grade.getScore() + "'");
            }if(grade.getExamMethod() != null){
                sql.SET("exam_method = '" + grade.getExamMethod() + "'");
            }if(grade.getGradeKind() != null){
                sql.SET("grade_kind = '" + grade.getGradeKind() + "'");
            }if(grade.getStudentUsername() != null){
                sql.SET("student_username = '" + grade.getStudentUsername() + "'" );
            }if(grade.getTerm() != null){
                sql.SET("term = '" + grade.getTerm() +"'");
            }
            sql.WHERE("id = " + grade.getId());
            updateSqlList.append(sql.toString() + ";");
        }
        return updateSqlList.toString();
    }

    public String findGradeByMajorNumberAndTerm(@Param("majorNumber") String majorNumber,@Param("term")String term){
        return "select *from t_grade where student_username like '" + majorNumber + "%' and term =  '" + term + "';";
    }

    public String insertGradeBatch(List<Grade> list){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into " +
                "`t_grade`(grade_number,grade_name,grade_credit,grade_time,score,exam_method,student_username,term,grade_kind) values"
        );
        for (int i = 0; i < list.size() ; i++) {
            Grade grade = list.get(i);
            stringBuilder.append("(" +
                    "'" + grade.getGradeNumber() +"'" + "," + "'" + grade.getGradeName() + "'" + ","  + grade.getGradeCredit() + "," +
                    "'" + grade.getGradeTime() + "'" + "," + "'" + grade.getScore() + "'" + "," + "'" + grade.getExamMethod() + "'" + "," + "'" + grade.getStudentUsername() + "'" +"," +
                    "'" + grade.getTerm() + "'" + "," + "'" + grade.getGradeKind() + "')");
            if(i != list.size() - 1){
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }
}
