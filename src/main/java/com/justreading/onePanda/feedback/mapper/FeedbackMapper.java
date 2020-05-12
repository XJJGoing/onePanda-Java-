package com.justreading.onePanda.feedback.mapper;

import com.justreading.onePanda.feedback.entity.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @author LYJ
 * @Description 反馈的mapper
 * @date 2020 年 02 月 25 日 16:16
 */
@Mapper
public interface FeedbackMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_feedback(feedback_content,feedback_img,contact,rate) " +
            "values(#{feedbackContent},#{feedbackImg},#{contact},#{rate})")
    public int insertFeedBack(Feedback feedBack);
}
