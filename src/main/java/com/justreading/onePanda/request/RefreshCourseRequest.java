package com.justreading.onePanda.request;

import lombok.*;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 刷新成绩用的实体类
 * @date 2020 年 11 月 04 日 13:14
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RefreshCourseRequest implements Serializable {
    private String term;
    private String userId;
    private String username;
    private String password;
}
