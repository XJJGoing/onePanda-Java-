package com.justreading.onePanda.user.mapper;

import com.justreading.onePanda.user.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author LYJ
 * @Description  User表使用动态SET的provider类
 * @date 2020 年 02 月 15 日 17:47
 */
public class UserProvider {
    public String updateUser(User user){
        return new SQL(){{
            UPDATE("t_user");
            if(user.getUsername() != null){
                SET("username = #{username}");
            }if(user.getPassword() != null){
                SET("password = #{password}");
            }if(user.getCollege() != null){
                SET("college = #{college}");
            }if(user.getOrigin() != null){
                SET("origin = #{origin}");
            }if(user.getEmail() != null){
                SET("email = #{email}");
            }if(user.getIsAssistant() != null){
                SET("is_assistant = #{isAssistant}");
            }if(user.getMajorNumber() != null){
                SET("major_number = #{majorNumber}");
            }

            if(user.getWxSessionKey() != null){
                SET("wx_session_key = #{wxSessionKey}");
            }if(user.getWxOpenid() != null){
                SET("wx_openid = #{wxOpenid}");
            }if(user.getWxNickName() != null){
                SET("wx_nick_name = #{wxNickName}");
            }if(user.getWxAvatarUrl() != null){
                SET("wx_avatar_url = #{wxAvatarUrl}");
            }if(user.getWxCity() != null){
                SET("wx_city = #{wxCity}");
            }if(user.getWxCountry() != null){
                SET("wx_country = #{wxCountry}");
            }if(user.getWxProvince() != null){
                SET("wx_province = #{wxProvince}");
            }if(user.getWxGender() != null){
                SET("wx_gender = #{wxGender}");
            }if(user.getWxLanguage() != null){
                SET("wx_language = #{wxLanguage}");
            }

            if(user.getQqOpenid() != null){
                SET("qq_openid = #{qqOpenid}");
            }if(user.getQqNickName() != null){
                SET("qq_nick_name = #{qqNickName}");
            }if(user.getQqAvatarUrl() != null){
                SET("qq_avatar_url = #{qqAvatarUrl}");
            }if(user.getQqCity() != null){
                SET("qq_city = #{qqCity}");
            }if(user.getQqCountry() != null){
                SET("qq_country = #{qqCountry}");
            }if(user.getQqProvince() != null){
                SET("qq_province = #{qqProvince}");
            }if(user.getQqGender() != null){
                SET("qq_gender = #{qqGender}");
            }if(user.getQqLanguage() != null){
                SET("qq_language = #{qqLanguage}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    public String deleteUserBatch(List<Integer> list){
       StringBuffer ids = new StringBuffer();
        for (int i = 0; i < list.size() ; i++) {
            if(i == list.size() - 1){
                ids.append(list.get(i));
            }else{
                ids.append(list.get(i) + ",");
            }
        }
       return new SQL(){{
           DELETE_FROM("t_user");
           WHERE("id in (" + ids +")");
       }}.toString();
    }
}
