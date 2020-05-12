package com.justreading.onePanda.article.mapper;

import com.justreading.onePanda.article.entity.Article;
import com.justreading.onePanda.grade.entity.Grade;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 17 日 19:25
 */
public class ArticleProvider {
    public String deleteArticleBatch(@Param("list") List<Integer> list){
        StringBuffer ids = new StringBuffer();
        for (int i = 0; i < list.size() ; i++) {
            if(i == list.size() - 1){
                ids.append(list.get(i));
            }else{
                ids.append(list.get(i) + ",");
            }
        }
       return  new SQL(){{
            DELETE_FROM("t_article");
            WHERE("id in (" + ids + ")");
        }}.toString();
    }

    public String updateArticleBatch(@Param("list") List<Article> list){
        StringBuffer updateSqlList = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Article article = list.get(i);
            SQL sql = new SQL();
            sql.UPDATE("t_article");
            if(article.getTitle() != null){
                sql.SET("title = '" + article.getTitle()+"'");
            }if(article.getAuthor() != null){
                sql.SET("author = '" + article.getAuthor() + "'");
            }if(article.getArticleContent() != null){
                sql.SET("article_content = '" + article.getArticleContent()+"'");
            }if(article.getScanTimes() != null){
                sql.SET("scan_times = " + article.getScanTimes());
            }if(article.getUserId() != null){
                sql.SET("user_id = " + article.getUserId());
            }if(article.getIsCheck() != null){
                sql.SET("is_check = " + article.getIsCheck());
            }if(article.getLayout() != null){
                sql.SET("layout='" + article.getLayout() + "'");
            }if(article.getArticleKind1() != null){
                sql.SET("article_kind1 = '" + article.getArticleKind1() +"'");
            }if(article.getArticleKind2() != null){
                sql.SET("article_kind2 = '" + article.getArticleKind2() +"'");
            }if(article.getArticleAbstract() != null){
                sql.SET("article_abstract = '" + article.getArticleAbstract() +"'");
            }
            sql.WHERE("id = " + article.getId());
            updateSqlList.append(sql.toString() + ";");
        }
        return updateSqlList.toString();
    }
}
