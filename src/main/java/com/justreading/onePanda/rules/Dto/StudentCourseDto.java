package com.justreading.onePanda.rules.Dto;

import com.justreading.onePanda.common.bean.ReptileCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 11 月 03 日 16:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseDto {
    private String bz;
    private List<ReptileCourse> courses;
    private List<String>  weeks;
    private List<String> terms;
}
