package com.justreading.onePanda.article.service;

import com.justreading.onePanda.article.entity.Article;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author LYJ
 * @Description 文章的业务接口
 * @date 2020 年 02 月 17 日 20:04
 */
public interface ArticleService {
    /**
     * 插入文章
     * @param article 文章
     * @return
     */
    public ApiResponse<Article> insertArticle(MultipartFile[] layouts, Article article);

    /**
     * 批量删除文章
     * @param list 文章的删除id数组
     * @return
     */
    public ApiResponse deleteArticleBatch(List<Integer> list);

    /**
     * 批量更新文章
     * @param list 文章集合
     * @return
     */
    public ApiResponse updateArticleBatch(List<Article> list);


    /**
     * 并且查询文章的评论、回复等信息
     * @param id 通过问文章的id查询文章
     * @return
     */
    public ApiResponse<Article> findArticleById(String id);

    /**
     * 通过审核状态来查询文章 1:已审核 0:未通过  2:审核未通过;
     * @param isCheck 审核状态
     * @return
     */
    public ApiResponse<MyPageInfo<Article>> findAllByCheckStatus(String isCheck,String pageNum,String pageSize);

    /**
     * 通过用户的id 来查询用户的文章
     * @param userId 用户的id
     * @return
     */
    public ApiResponse<MyPageInfo<Article>> findAllByUserId(String userId,String pageNum,String pageSize);


    /**
     * 往存储桶中加入图片并且返回图片的链接
     * @return
     */
    public ApiResponse<String> insertArticleImagesToBucket(MultipartFile multipartFiles);

    /**
     * 查询所有未审核的文章  0 为未审核
     * @return
     */
    public ApiResponse<List<Article>> findAllArticleUnCheck();

}
