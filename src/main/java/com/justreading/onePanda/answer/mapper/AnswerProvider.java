package com.justreading.onePanda.answer.mapper;

import com.justreading.onePanda.answer.entity.Answer;
import com.justreading.onePanda.grade.entity.Grade;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 18 日 16:53
 */
public class AnswerProvider {
    public String deleteAnswerBatch(List<Integer> list){
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < list.size() ; i++) {
            if(i == list.size() - 1){
                ids.append(list.get(i));
            }else{
                ids.append(list.get(i) + ",");
            }
        }
        return new SQL(){{
            DELETE_FROM("t_answer");
            WHERE("id in (" + ids + ")");
        }}.toString();
    }

    public String updateAnswerBatch(List<Answer> answers){
        StringBuffer updateSqlList = new StringBuffer();
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            SQL sql = new SQL();
            sql.UPDATE("t_answer");
            if(answer.getAnswerContent() != null){
                sql.SET("answer_content = '" + answer.getAnswerContent()+"'");
            }
            sql.WHERE("id = " + answer.getId());
            updateSqlList.append(sql.toString() + ";");
        }
        return updateSqlList.toString();
    }
}
