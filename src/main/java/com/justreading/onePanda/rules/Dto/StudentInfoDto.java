package com.justreading.onePanda.rules.Dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 11 月 03 日 16:02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class StudentInfoDto implements Serializable {
    private String college;
    private String studentMajorName;
    private String trueName;
}
