package com.justreading.onePanda.userComentPraise.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 18 日 19:09
 */
public class UserCommentPraiseProvider {
    public String deleteUserCommentPraiseBatch(List<Integer> list){
        StringBuffer ids = new StringBuffer();
        for (int i = 0; i < list.size() ; i++) {
            if(i == list.size() - 1){
                ids.append(list.get(i));
            }else{
                ids.append(list.get(i) + ",");
            }
        }
        return  new SQL(){{
            DELETE_FROM("t_user_comment_praise");
            WHERE("id in (" + ids + ")");
        }}.toString();
    }
}
