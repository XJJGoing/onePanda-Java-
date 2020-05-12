package com.justreading.onePanda.article.mapper;

import com.justreading.onePanda.article.entity.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 17 日 19:25
 */
@Mapper
public interface ArticleMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_article(title,author,article_content,user_id,layout,article_kind1,article_kind2,article_abstract)" +
            "values(#{title}, #{author},#{articleContent},#{userId},#{layout},#{articleKind1},#{articleKind2},#{articleAbstract})")
    public int insertArticle(Article article);

    @DeleteProvider(type = ArticleProvider.class,method = "deleteArticleBatch")
    public void deleteArticleBatch(@Param("list") List<Integer> list);


    @UpdateProvider(type = ArticleProvider.class,method = "updateArticleBatch")
    public void updateArticleBatch(@Param("list") List<Article> list);


    @Select("select *from  t_article where id = #{id}")
    public Article findArticleById(String id);

    /**
     * 通过审核状态来查询文章 1:已审核 0:未通过  2:审核未通过
     * @param isCheck 审核状态
     * @return
     */
    @Select("select *from t_article where is_check = #{isCheck} order by id  desc")
    public List<Article> findAllByCheckStatus(String isCheck);

    /**
     * 通过用户的id 来查询用户的文章
     * @param userId 用户的id
     * @return
     */
    @Select("select *from t_article where user_id = #{useId}")
    public List<Article> findAllByUserId(String userId);

    /**
     * 分页查询所有的文章
     * @return
     */
    @Select("select *from t_article")
    public List<Article> findAllArticle();

    /**
     * 查询所有未审核的文章
     */
    @Select("select *from t_article where is_check = 0")
    public List<Article> findAllArticleUnCheck();

}
