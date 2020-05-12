package com.justreading.onePanda.userComentPraise.mapper;

import com.justreading.onePanda.userComentPraise.entity.UserCommentPraise;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 18 日 19:08
 */
@Mapper
public interface UserCommentPraiseMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_user_comment_praise(user_id,comment_id) " +
            "values(#{userId},#{commentId})")
    public int insertUserCommentPraise(UserCommentPraise userCommentPraise);

    @DeleteProvider(type = UserCommentPraiseProvider.class,method = "deleteUserCommentPraiseBatch")
    public  void deleteUserCommentPraiseBatch(@Param("list")List<Integer> list);

    /**
     * 根据评论的id进行查找数据
     * @param id 评论的id
     */
    @Select("select *from t_user_comment_praise where comment_id = #{id}")
    public List<UserCommentPraise>  findUserCommentPraiseByCommentId(String id);
}
