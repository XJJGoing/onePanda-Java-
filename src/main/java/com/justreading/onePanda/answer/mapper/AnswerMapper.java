package com.justreading.onePanda.answer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.justreading.onePanda.answer.entity.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LYJ
 * @Description 回复的mapper
 * @date 2020 年 02 月 18 日 16:53
 */
@Mapper
public interface AnswerMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_answer (answer_content,user_id,comment_id) " +
            "values(#{answerContent},#{userId},#{commentId})")
    public int insertAnswer(Answer answer);

    @DeleteProvider(type = AnswerProvider.class,method = "deleteAnswerBatch")
    public void deleteAnswerBatch(@Param("list")List<Integer> list);

    @UpdateProvider(type = AnswerProvider.class,method = "updateAnswerBatch")
    public void updateAnswerBatch(@Param("answers")List<Answer> answers);

    /**
     * 根据评论的id查询所有的回复
     * @param id 回复
     * @return
     */
    @Select("select *from t_answer where comment_id = #{id}")
    public  List<Answer> findAnswerByCommentId(String id);
}
