package com.justreading.onePanda.rules.Dto;

import com.justreading.onePanda.common.bean.ReptileGrade;
import com.justreading.onePanda.grade.entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 11 月 04 日 20:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGradeDto {
    private List<ReptileGrade> grades;
}
