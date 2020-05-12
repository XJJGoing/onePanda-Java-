package com.justreading.onePanda.article.controller;

import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.article.entity.Article;
import com.justreading.onePanda.article.service.ArticleService;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

/**
 * @author LYJ
 * @Description 文章的接口
 * @date 2020 年 02 月 17 日 20:47
 */
@Api(tags = "文章模块(表白信息)")
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @MyLog("插入文章:/insert")
    @PostMapping("/insert")
    @ApiOperation("插入文章")
    public ApiResponse<Article> insertArticle(@RequestParam(name = "layouts") MultipartFile[] layouts,
                                              @RequestParam(name = "title")String title,
                                              @RequestParam(name = "author")String author,
                                              @RequestParam(name = "articleContent")String articleContent,
                                              @RequestParam(name = "userId")String userId,
                                              @RequestParam(name = "articleKind1")String articleKind1,
                                              @RequestParam(name = "articleKind2")String articleKind2,
                                              @RequestParam(name = "articleAbstract")String articleAbstract){
        Article article = new Article();
        article.setTitle(title);
        article.setAuthor(author);
        article.setArticleContent(articleContent);
        article.setUserId(Integer.parseInt(userId));
        article.setArticleKind1(articleKind1);
        article.setArticleKind2(articleKind2);
        article.setArticleAbstract(articleAbstract);
        ApiResponse<Article> apiResponse = articleService.insertArticle(layouts,article);
        return apiResponse;
    }

    @MyLog("批量删除文章:/deleteArticleBatch")
    @ApiOperation("批量删除文章")
    @PostMapping("/deleteArticleBatch")
    public ApiResponse deleteArticleBatch(@RequestBody(required = true) List<Integer> list){
        ApiResponse apiResponse = articleService.deleteArticleBatch(list);
        return apiResponse;
    }


    @MyLog("批量更新文章:/updateArticleBatch")
    @ApiOperation("批量更新文章")
    @PostMapping("/updateArticleBatch")
    public ApiResponse updateArticleBatch(@RequestBody(required = true) List<Article> list){
        ApiResponse apiResponse = articleService.updateArticleBatch(list);
        return apiResponse;
    }

    @MyLog("通过用户id进行查询文章 /queryArticleByUserId")
    @ApiOperation("通过用户的id进行查询文章")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId",value = "用户的id",required = true),
            @ApiImplicitParam(name = "pageNum",value = "当前页 默认1"),
            @ApiImplicitParam(name = "pageSize",value = "当前页显示的个数 默认10")})
    @GetMapping("/queryArticleByUserId")
    public ApiResponse<MyPageInfo<Article>> findArticleByUserId(@RequestParam(value = "userId",required = true)String useId,
                                                                @RequestParam(value = "pageNum",required = true,defaultValue = "1")String pageNum,
                                                                @RequestParam(value = "pageSize",required = true,defaultValue = "10")String pageSize){
        ApiResponse<MyPageInfo<Article>> apiResponse = articleService.findAllByUserId(useId, pageNum, pageSize);
        return apiResponse;
    }

    @MyLog("通过审核状态来查询文章:/queryArticleByCheckStatus")
    @ApiOperation("通过审核状态来查询文章 1:审核通过 0: 未审核  2:审核未通过")
    @ApiImplicitParams({@ApiImplicitParam(name = "isCheck",value = "审核的状态",required = true),
            @ApiImplicitParam(name = "pageNum",value = "当前页 默认1"),
            @ApiImplicitParam(name = "pageSize",value = "当前页显示的个数 默认10")})
    @GetMapping("/queryArticleByCheckStatus")
    public ApiResponse<MyPageInfo<Article>> findArticleByCheckStatus(@RequestParam(value = "isCheck",required = true)String isCheck,
                                                                @RequestParam(value = "pageNum",required = true,defaultValue = "1")String pageNum,
                                                                @RequestParam(value = "pageSize",required = true,defaultValue = "10")String pageSize){
        ApiResponse<MyPageInfo<Article>> apiResponse = articleService.findAllByCheckStatus(isCheck, pageNum, pageSize);
        return apiResponse;
    }

    @MyLog("通过文章的id查找文章:/queryArticleById")
    @ApiOperation("通过文章的id查找文章")
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "文章的id",required = true)})
    @GetMapping("/queryArticleById")
    public ApiResponse<Article> findArticleById(@RequestParam(value = "id",required = true)String id){
        ApiResponse<Article> apiResponse = articleService.findArticleById(id);
        return apiResponse;
    }

    @MyLog("将文章的图片放入存储桶:/insertArticleContentImg")
    @ApiOperation("将文章的图片放入存储桶")
    @PostMapping("/insertArticleContentImg")
    public ApiResponse<String> insertArticleImagesToBucket(@RequestParam(value = "file",required = true)MultipartFile file){
        ApiResponse<String> apiResponse = articleService.insertArticleImagesToBucket(file);
        return apiResponse;
    }

    @MyLog("查询所有未审核的文章:/queryUnCheckArticle")
    @ApiOperation("查询所有未审核的文章")
    @GetMapping("/queryUnCheckArticle")
    public ApiResponse<List<Article>> findAllArticleUnCheck(){
        ApiResponse<List<Article>> apiResponse = articleService.findAllArticleUnCheck();
        return apiResponse;
    }
}

