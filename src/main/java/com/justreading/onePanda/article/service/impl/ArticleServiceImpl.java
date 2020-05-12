package com.justreading.onePanda.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.justreading.onePanda.article.entity.Article;
import com.justreading.onePanda.article.mapper.ArticleMapper;
import com.justreading.onePanda.article.service.ArticleService;
import com.justreading.onePanda.comment.entity.Comment;
import com.justreading.onePanda.comment.mapper.CommentMapper;
import com.justreading.onePanda.comment.service.CommentService;
import com.justreading.onePanda.comment.service.impl.CommentServiceImpl;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import com.justreading.onePanda.common.TenXunUtil;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author LYJ
 * @Description 文章业务的实现接口
 * @date 2020 年 02 月 17 日 20:07
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper  articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TenXunUtil tenXunUtil;

    @Autowired
    private COSClient cosClient;


    /**
     * 调用云存储插入图片
     * @param article 文章
     * @return
     */
    @Override
    public ApiResponse<Article> insertArticle(MultipartFile[] layouts, Article article) {
          ApiResponse<Article> apiResponse = new ApiResponse<>();
          String bucketName = tenXunUtil.getOnePandaBucket();
          String dir = "article";
          for(MultipartFile layout : layouts){
              String originalFilename = layout.getOriginalFilename();
              String suffix = originalFilename.substring(originalFilename.indexOf("."));
              String fileName = UUID.randomUUID().toString();
              String key = dir + "/" + fileName + suffix;
              try {
                  File tempFile = File.createTempFile(fileName, suffix);
                  layout.transferTo(tempFile);

                  PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, tempFile);
                  PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
                  article.setLayout(tenXunUtil.getPath() + key);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
          articleMapper.insertArticle(article);
          apiResponse.setCode(200);
          apiResponse.setMsg("插入成功");
          apiResponse.setData(article);
          return apiResponse;
    }

    @Override
    public ApiResponse deleteArticleBatch(List<Integer> list) {
        ApiResponse apiResponse = new ApiResponse();
        articleMapper.deleteArticleBatch(list);
        apiResponse.setMsg("删除成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    @Override
    public ApiResponse updateArticleBatch(List<Article> list) {
        ApiResponse apiResponse = new ApiResponse();
        articleMapper.updateArticleBatch(list);
        apiResponse.setMsg("更新成功");
        apiResponse.setCode(200);
        return apiResponse;
    }

    /**
     *并且查询文章的评论、回复等信息
     * @return
     * @param id 文章的id
     */
    @Override
    public ApiResponse<Article> findArticleById(String id) {
        ApiResponse<Article> apiResponse = new ApiResponse<>();
        Article article = articleMapper.findArticleById(id);
        Integer articleId = article.getId();
        Integer userId = article.getUserId();
        ApiResponse<List<Comment>> commentByArticleId = commentService.findCommentByArticleId(Integer.toString(articleId));
        User articleUser = userMapper.findUserByUserId(Integer.toString(userId));
        List<Comment> commentList = commentByArticleId.getData();
        article.setComments(commentList);
        article.setArticleUser(articleUser);
        apiResponse.setMsg("查询成功");
        apiResponse.setCode(200);
        apiResponse.setData(article);
        return apiResponse;
    }

    /**
     * 返回审核状态的文章，包括文章的评论，评论的回复等.并且做倒序处理
     * @param isCheck 审核状态 1 审核通过 返回 文章的评论，评论的回复等.  否则只返回文章的内容
     * @return
     */
    @Override
    public ApiResponse<MyPageInfo<Article>> findAllByCheckStatus(String isCheck,String pageNum,String pageSize) {
        ApiResponse<MyPageInfo<Article>> apiResponse = new ApiResponse<>();
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<Article> articles = articleMapper.findAllByCheckStatus(isCheck);
        if(isCheck.equals("1")){
            for (Article article : articles){
                Integer userId = article.getUserId();
                Integer id = article.getId();
                User user = userMapper.findUserByUserId(Integer.toString(userId));
                ApiResponse<List<Comment>> commentsByArticleId = commentService.findCommentByArticleId(Integer.toString(id));
                List<Comment> commentList = commentsByArticleId.getData();
                article.setComments(commentList);
                article.setArticleUser(user);
            }
        }
        MyPageInfo<Article> pageInfo = new MyPageInfo<>(articles);
        apiResponse.setData(pageInfo);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    /**
     * 分页查询用户的所有的文章,先反转，再返回
     * @param userId 用户的id
     * @return
     */
    @Override
    public ApiResponse<MyPageInfo<Article>> findAllByUserId(String userId,String pageNum,String pageSize) {
        ApiResponse<MyPageInfo<Article>> apiResponse = new ApiResponse<>();
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<Article> articles = articleMapper.findAllByUserId(userId);
        Collections.reverse(articles);
        MyPageInfo<Article> pageInfo = new MyPageInfo<>(articles);
        apiResponse.setData(pageInfo);
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        return apiResponse;
    }

    /**
     * 往存储桶中放入文章的图片
     * @return
     */
    @Override
    public ApiResponse<String> insertArticleImagesToBucket(MultipartFile multipartFile) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        String bucketName =  tenXunUtil.getOnePandaBucket();
        String path = tenXunUtil.getPath();
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        String dir = "article";
        String fileName = UUID.randomUUID().toString();
        String key = dir + "/" + fileName + suffix;
        StringBuffer imgUrl = new StringBuffer();
        try {
            File tempFile = File.createTempFile(fileName, suffix);
            multipartFile.transferTo(tempFile);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, tempFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            imgUrl.append(path + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        apiResponse.setData(imgUrl.toString());
        apiResponse.setCode(200);
        apiResponse.setMsg("返回图片的链接");
        return apiResponse;
    }

    /**
     * 查询所有未审核的文章,包括作者的信息
     * @return
     */
    @Override
    public ApiResponse<List<Article>> findAllArticleUnCheck() {
        ApiResponse<List<Article>> apiResponse = new ApiResponse<>();
        List<Article> allArticleUnCheck = articleMapper.findAllArticleUnCheck();
        for (Article article : allArticleUnCheck){
            Integer userId = article.getUserId();
            User articleUser = userMapper.findUserByUserId(Integer.toString(userId));
            article.setArticleUser(articleUser);
        }
        apiResponse.setData(allArticleUnCheck);
        apiResponse.setMsg("查询成功");
        apiResponse.setCode(200);
        return apiResponse;
    }
}
