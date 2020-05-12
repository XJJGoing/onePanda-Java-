package com.justreading.onePanda.comment.service.impl;

import com.github.pagehelper.PageHelper;
import com.justreading.onePanda.answer.entity.Answer;
import com.justreading.onePanda.answer.service.AnswerService;
import com.justreading.onePanda.comment.entity.Comment;
import com.justreading.onePanda.comment.mapper.CommentMapper;
import com.justreading.onePanda.comment.service.CommentService;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.MyPageInfo;
import com.justreading.onePanda.user.entity.User;
import com.justreading.onePanda.user.mapper.UserMapper;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

/**
 * @author LYJ
 * @Description 评论的接口实现类
 * @date 2020 年 02 月 18 日 16:09
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper  userMapper;

    @Autowired
    private AnswerService answerService;


    @Override
    public ApiResponse<Comment> insertComment(Comment comment) {
        ApiResponse<Comment> apiResponse = new ApiResponse<>();
        String commentContent = comment.getCommentContent();

        //处理Emoji
        String parseToAliases = EmojiParser.parseToAliases(commentContent);
        comment.setCommentContent(parseToAliases);
        commentMapper.insertComment(comment);
        apiResponse.setCode(200);
        apiResponse.setMsg("插入成功");
        apiResponse.setData(comment);
        return apiResponse;
    }

    @Override
    public ApiResponse deleteCommentBatch(List<Integer> list) {
        ApiResponse apiResponse = new ApiResponse();
        commentMapper.deleteCommentBatch(list);
        apiResponse.setCode(200);
        apiResponse.setMsg("批量删除成功");
        return apiResponse;
    }

    @Override
    public ApiResponse updateCommentBatch(List<Comment> list) {
        ApiResponse apiResponse = new ApiResponse();
        commentMapper.updateCommentBatch(list);
        apiResponse.setCode(200);
        apiResponse.setMsg("更新成功");
        return apiResponse;
    }


    /**
     * 暂时先不查找评论的回复
     * @param id 文章的id
     * @return
     */
    @Override
    public ApiResponse<List<Comment>> findCommentByArticleId(String id) {
        ApiResponse<List<Comment>> apiResponse = new ApiResponse<>();
        List<Comment> comments = commentMapper.findCommentByArticleId(id);
        for(Comment comment : comments){

            //还原
            String decoderComment =  EmojiParser.parseToUnicode(comment.getCommentContent());
            comment.setCommentContent(decoderComment);
            Integer userId = comment.getUserId();
            User user = userMapper.findUserByUserId(Integer.toString(userId));
            comment.setCommentUser(user);
        }
        apiResponse.setData(comments);
        apiResponse.setMsg("查询成功");
        apiResponse.setCode(200);
        return apiResponse;
    }
}
