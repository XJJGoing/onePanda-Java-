package com.justreading.onePanda.course.mapper;

import com.justreading.onePanda.course.entity.Course;
import org.apache.ibatis.jdbc.SQL;

import javax.security.auth.login.AccountException;
import java.util.List;

/**
 * @author LYJ
 * @Description Course的动态sql语句
 * @date 2020 年 02 月 16 日 14:26
 */
public class CourseProvider {

    public String deleteCourseBatch(List<Integer> list){
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < list.size() ; i++) {
            if(i == list.size() - 1){
                ids.append(list.get(i));
            }else{
                ids.append(list.get(i) + ",");
            }
        }
        return new SQL(){{
            DELETE_FROM("t_course");
            WHERE("id in (" + ids + ")");
        }}.toString();
    }

    public String updateCourseBatch(List<Course> list){
        StringBuffer updateSqlList = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Course course = list.get(i);
            SQL sql = new SQL();
            sql.UPDATE("t_course");
            if(course.getCourseName() != null){
                sql.SET("course_name = '" + course.getCourseName() +"'" );
            }if(course.getCourseNumber() != null){
                sql.SET("course_number = '" + course.getCourseNumber() + "'");
            }if(course.getCourseZc() != null){
                sql.SET("course_zc = '" + course.getCourseZc() + "'");
            }if(course.getCourseJc() != null){
                sql.SET("course_jc = " + course.getCourseJc());
            }if(course.getCourseXq() != null){
                sql.SET("course_xq = " + course.getCourseXq());
            }if(course.getCourseTeacher() != null){
                sql.SET("course_teacher = '" + course.getCourseTeacher() + "'");
            }if(course.getCourseRoom() != null){
                sql.SET("course_room = '" + course.getCourseRoom() + "'");
            }if(course.getStudentUsername() != null){
                sql.SET("student_username = '" + course.getStudentUsername() + "'");
            }if(course.getTerm() != null){
                sql.SET("term = '" + course.getTerm() + "'");
            }if(course.getNote() != null){
                sql.SET("note = '" + course.getNote() + "'");
            }
            sql.WHERE("id = " + course.getId());
            updateSqlList.append(sql.toString() + ";");
        }
        return  updateSqlList.toString();
    }

    /**
     * @param list
     * @return
     */
    public String insertCourseBatch(List<Course> list){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into " +
                "`t_course`(student_username,term,course_number,course_name,course_zc,course_jc,course_room,course_teacher,course_xq,note) values"
        );
        for (int i = 0; i < list.size() ; i++) {
            Course course = list.get(i);
            stringBuilder.append("(" +
                    "'" + course.getStudentUsername() +"'" + "," + "'" + course.getTerm() + "'" + "," + "'" + course.getCourseNumber() + "'" + "," +
                    "'" + course.getCourseName() + "'" + "," + "'" + course.getCourseZc() + "'" + "," + course.getCourseJc() + "," + "'" + course.getCourseRoom() + "'" +"," +
                    "'" + course.getCourseTeacher() + "'" + "," + course.getCourseXq() + "," + "'" + course.getNote() + "'" + ")");
            if(i != list.size() - 1){
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }
}
