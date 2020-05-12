package com.justreading.onePanda.user.mapper;

import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.user.entity.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LYJ
 * @Description 用户的映射端口
 * @date 2020 年 02 月 15 日 13:15
 */

@Mapper
public interface UserMapper {
    /**
     *  插入用户
     * @param user 用户
     * @return
     */
    @Insert("insert into t_user(username,password,true_name,college,wx_nick_name,wx_avatar_url,wx_gender,wx_city,wx_province,wx_country,wx_language,wx_openid,wx_session_key," +
            "qq_nick_name,qq_avatar_url,qq_gender,qq_city,qq_province,qq_country,qq_language,qq_openid,qq_session_key," +
            "email,origin,is_assistant,major_number,student_major_name)" +
            "values(#{username},#{password},#{trueName},#{college},#{wxNickName},#{wxAvatarUrl},#{wxGender},#{wxCity},#{wxProvince},#{wxCountry},#{wxLanguage},#{wxOpenid},#{wxSessionKey}," +
            "#{qqNickName},#{qqAvatarUrl},#{qqGender},#{qqCity},#{qqProvince},#{qqCountry},#{qqLanguage},#{qqOpenid},#{qqSessionKey}," +
            "#{email},#{origin},#{isAssistant},#{majorNumber},#{studentMajorName})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int insertUser(User user);

    /**
     * 通过微信的openid查询用户
     * @param user
     * @return
     */
    @Select("select *from t_user where wx_openid = #{wxOpenid}")
    public User findUserByWxOpenid(User user);

    /**
     * 通过QQ的openid查询用户
     * @param user
     * @return
     */
    @Select("select *from t_user where qq_openid = #{qqOpenid}")
    public User findUserByQqOpenid(User user);

    /**
     * 不为空即更新用户的信息
     * @param user
     */
    @UpdateProvider(type = UserProvider.class,method = "updateUser")
    public void updateUser(User user);

    /**
     * 根据用户id查询用户
     * @return
     */
    @Select("select *from t_user where id = #{id}")
    public User findUserByUserId(String id);


    /**
     * 批量删除用户
     */
    @DeleteProvider(type = UserProvider.class,method = "deleteUserBatch")
    public void deleteBatch(@Param("list") List<Integer> list);

    /**
     * 查询所有的用户,分页返回给前端用
     * @return
     */
    @Select("select *from t_user")
    public List<User> findAllUser();


    /**
     * 查询所有学生用户的账户和密码，
     * 老师的is_assistant为1，班长的为0 ，这两者的major_number同时不为空,普通学生的为空
     * @return
     */
    @Select("select id,username,password from t_user where is_assistant = 0")
    public List<User> findAllUserUsernameAndPassword();

    /**
     * 辅导员进登录的时候进行查库，查到的就直接更新信息
     * @return
     */
    @Select("select id username,password from t_user where username = #{username} and password = #{password}")
    public List<User> findUserByUsernameAndPassword(String username,String password);

    /**
     * 用户当爬取的课表不存在的时候，进行爬虫获取课表
     * @param username 用户名
     * @return
     */
    @Select("select id,username,password, true_name from t_user where username = #{username}")
    public List<User> findUserByUsername(String username);

    /**
     * 教室登录的时候设置一个来源
     * @param username  老师的账号
     * @param password  老师的密码
     * @param origin     登录来源
     * @return
     */
    @Select("select id,username,password,is_assistant,major_number,is_root,true_name from t_user " +
            "where username = #{username} and password = #{password} and is_assistant = 1 and origin = #{origin}")
    public User teacherLogin(String username,String password,Integer origin);
}
