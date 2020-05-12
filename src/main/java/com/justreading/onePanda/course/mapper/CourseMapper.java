package com.justreading.onePanda.course.mapper;

import com.justreading.onePanda.course.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LYJ
 * @Description 课程的mapper  课程是一条记录   课表是一组记录
 * @date 2020 年 02 月 16 日 14:21
 */
@Mapper
public interface CourseMapper {
    /**
     * 插入学生的课程，先通过studentName和term查询有没有，存在即更新不存在即插入
     * @param course
     * @return
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into " +
            "t_course(course_name,course_number,course_zc,course_jc,course_xq,course_teacher,course_room,student_username,term,note)" +
            "values(#{courseName},#{courseNumber},#{courseZc},#{courseJc},#{courseXq},#{courseTeacher},#{courseRoom},#{studentUsername},#{term},#{note})")
    public int insertCourse(Course course);


    /**
     * 通过id查询学生的课程
     * @param id
     * @return
     */
    @Select("select *from t_course where id = #{id}")
    public Course findCourseById(String id);


    /**
     * 通过学期和学生的学号查询学生的课表
     * @param term
     * @param studentUserName
     * @return
     */
    @Select("select *from t_course where term = #{term} and student_username = #{studentUserName}")
    public List<Course> findCourseByTermAndStudentUserName(@Param("term") String term,@Param("studentUserName") String studentUserName);


    /**
     * 查询所有学生的课程
     * @return
     */
    @Select("select *from t_course")
    public List<Course> findAllCourse();


    /**
     * 批量删除课表
     */
    @DeleteProvider(type = CourseProvider.class,method = "deleteCourseBatch")
    public void deleteCourseBatch(List<Integer> list);

    /**
     * 批量更新课表 根据 studentName 和 term 去更新，更新这批数据
     * @param list  课表数据
     */
    @UpdateProvider(type = CourseProvider.class,method = "updateCourseBatch")
    public void updateCourseBatch(@Param("list") List<Course> list);
}
