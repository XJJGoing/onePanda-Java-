package com.justreading.onePanda.comment.mapper;

import com.justreading.onePanda.comment.entity.Comment;
import com.justreading.onePanda.grade.entity.Grade;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 18 日 15:57
 */
public class CommentProvider {
    public String deleteCommentBatch(@Param("list") List<Integer> list){
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < list.size() ; i++) {
            if(i == list.size() - 1){
                ids.append(list.get(i));
            }else{
                ids.append(list.get(i) + ",");
            }
        }
        return new SQL(){{
            DELETE_FROM("t_comment");
            WHERE("id in (" + ids + ")");
        }}.toString();
    }

    public String updateCommentBatch(@Param("list") List<Comment> list){
        StringBuffer updateSqlList = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Comment comment = list.get(i);
            SQL sql = new SQL();
            sql.UPDATE("t_comment");
            if(comment.getCommentContent() != null){
                sql.SET("comment_content = '" + comment.getCommentContent() + "'");
            }if(comment.getPraiseNumber() != null){
                sql.SET("praise_number = " + comment.getPraiseNumber());
            }
            sql.WHERE("id = " + comment.getId());
            updateSqlList.append(sql.toString() + ";");
        }
        return updateSqlList.toString();
    }
}
