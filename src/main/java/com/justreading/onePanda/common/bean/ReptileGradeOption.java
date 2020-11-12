package com.justreading.onePanda.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 学生查询成绩条件的通用实体类
 * @date 2020 年 02 月 15 日 14:07
 */
@ApiModel("学生查询成绩需的条件")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReptileGradeOption implements Serializable {
    @ApiModelProperty(value = "学期",required = true)
    private String kksj;

    @ApiModelProperty(value = "可不传")
    private String kcxz;

    @ApiModelProperty(value = "可不传")
    private  String kcmc;

    @ApiModelProperty(value = "学生分数，默认all")
    private  String xsfs = "all";

    public String getKksj() {
        return kksj;
    }

    public void setKksj(String kksj) {
        this.kksj = kksj;
    }

    public String getKcxz() {
        return kcxz;
    }

    public void setKcxz(String kcxz) {
        this.kcxz = kcxz;
    }

    public String getKcmc() {
        return kcmc;
    }

    public void setKcmc(String kcmc) {
        this.kcmc = kcmc;
    }

    public String getXsfs() {
        return xsfs;
    }

    public void setXsfs(String xsfs) {
        this.xsfs = xsfs;
    }

    @Override
    public String toString() {
        return "GradeOption{" +
                "kksj='" + kksj + '\'' +
                ", kcxz='" + kcxz + '\'' +
                ", kcmc='" + kcmc + '\'' +
                ", xsfs='" + xsfs + '\'' +
                '}';
    }
}
