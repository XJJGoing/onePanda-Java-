package com.justreading.onePanda.comment.mapper;

import com.justreading.onePanda.comment.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LYJ
 * @Description 评论的mapper
 * @date 2020 年 02 月 18 日 15:52
 */
@Mapper
public interface CommentMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_comment(comment_content,article_id,praise_number,user_id) " +
            "values(#{commentContent},#{articleId},#{praiseNumber},#{userId})")
    public int insertComment(Comment comment);

    @DeleteProvider(type = CommentProvider.class,method = "deleteCommentBatch")
    public void deleteCommentBatch(@Param("list")List<Integer>list);

    @UpdateProvider(type = CommentProvider.class,method = "updateCommentBatch")
    public void updateCommentBatch(@Param("list")List<Comment> list);


    /**
     * 通过文章的id来查询评论,并且做分页。
     * @param id 文章的id
     */
    @Select("select *from t_comment where article_id = #{id}")
    public List<Comment> findCommentByArticleId(String id);
}
