package com.justreading.onePanda.grade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.justreading.onePanda.grade.entity.Grade;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.jsoup.Connection;

import java.util.List;

/**
 * @author LYJ
 * @Description 查询成绩的mapper
 * @date 2020 年 02 月 16 日 15:59
 */
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_grade(grade_number,grade_name,grade_credit,grade_time,score,exam_method,student_username,term,grade_kind)" +
            "values(#{gradeNumber},#{gradeName},#{gradeCredit},#{gradeTime},#{score},#{examMethod},#{studentUsername},#{term},#{gradeKind})")
    int insertGrade(Grade grade);

    @InsertProvider(type = GradeProvider.class,method = "insertGradeBatch")
    int insertGradeBatch(@Param("list") List<Grade> list);

    /**
     * 根据学期和学号查询成绩
     * @param term 学期
     * @param studentUsername 学号
     * @return
     */
    @Select("select *from t_grade where student_username = #{studentUsername} and term = #{term}")
    List<Grade> findGradeByTermAndStudentUserName(@Param("term") String term,@Param("studentUsername")String studentUsername);

    @Select("select *from t_grade where id = #{id}")
    Grade findGradeById(String id);

    @DeleteProvider(type = GradeProvider.class,method = "deleteGradeBatch")
    void deleteGradeBatch(List<Integer> list);

    /**
     * 根据 学号 和 学期 更新
     * @param list 批量的成绩
     */
    @UpdateProvider(type = GradeProvider.class,method = "updateGradeBatch")
    int updateGradeBatch(@Param("list") List<Grade> list);

    /**
     * 学业老师或者辅导员根据专业号或者学期查询自己专业的学生的成绩
     * @param majorNumber 专业号
     * @param term 学期
     * @return
     */
    @SelectProvider(type = GradeProvider.class,method = "findGradeByMajorNumberAndTerm")
    List<Grade> findGradeByMajorNumberAndTerm(@Param("majorNumber")String majorNumber,@Param("term")String term);

    /**
     * 查询学生的全部成绩
     * @param studentUsername 学生的学号
     * @return
     */
    @Select("select *from t_grade where  student_username = #{studentUsername}")
    List<Grade> findAllGrade(String studentUsername);


    /**
     * 根据学生的学科号和学号进行成绩的查询
     * @param gradeNumber
     * @param username
     * @return
     */
    @Select("select *from t_grade where  grade_number = #{gradeNumber} and username = #{username}")
    Grade findGradeByGradeNumberAndUsername(String gradeNumber,String username);
}
