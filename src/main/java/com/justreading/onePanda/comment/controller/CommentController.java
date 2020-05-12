package com.justreading.onePanda.comment.controller;

import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.comment.entity.Comment;
import com.justreading.onePanda.comment.service.CommentService;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

/**
 * @author LYJ
 * @Description 评论的接口
 * @date 2020 年 02 月 18 日 16:25
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @MyLog("插入评论：/insert")
    @ApiOperation("插入评论")
    @PostMapping("/insert")
    public ApiResponse<Comment> insertComment(@RequestBody Comment comment){
        ApiResponse<Comment> apiResponse = commentService.insertComment(comment);
        return apiResponse;
    }

    @MyLog("批量删除评论:/deleteCommentBatch")
    @ApiOperation("批量删除评论")
    @PostMapping("/deleteCommentBatch")
    public ApiResponse deleteCommentBatch(@RequestBody(required = true) List<Integer> list){
        ApiResponse apiResponse = commentService.deleteCommentBatch(list);
        return apiResponse;
    }

    @MyLog("批量更新评论:/updateCommentBatch")
    @ApiOperation("批量更新")
    @PostMapping("/updateCommentBatch")
    public ApiResponse updateCommentBatch(@RequestBody(required = true) List<Comment> list){
        ApiResponse apiResponse = commentService.updateCommentBatch(list);
        return apiResponse;
    }

//    @ApiOperation("通过文章的id查询评论")
//    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "文章的id"),
//            @ApiImplicitParam(name = "pageNum",value = "第几页"),
//            @ApiImplicitParam(name = "pageSize",value = "显示的个数")})
//    @GetMapping("/queryCommentByArticleId")
//    public ApiResponse<MyPageInfo<Comment>> findCommentByArticleId(@RequestParam(value = "id",required = true)String id,
//                                                                   @RequestParam(value = "pageNum",defaultValue = "1")String pageNum,
//                                                                   @RequestParam(value = "pageSize",defaultValue = "5")String pageSize){
//        ApiResponse<MyPageInfo<Comment>> apiResponse = commentService.findCommentByArticleId(id, pageNum, pageSize);
//        return apiResponse;
//    }
}
