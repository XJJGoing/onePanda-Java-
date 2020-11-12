package com.justreading.onePanda.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 学生课表查询条件的通用实体类
 * @date 2020 年 02 月 15 日 14:06
 */
@ApiModel("学生查询课表的条件")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReptileCourseOption implements Serializable {

    @ApiModelProperty("不传也行")
    private String cj0701id;

    @ApiModelProperty("周次")
    private String zc;

    @ApiModelProperty("demo")
    private String demo;

    @ApiModelProperty(value = "学期",required = true)
    private String xnxq01id;

    @ApiModelProperty("sfFD")
    private String  sfFD = "1";

    public String getCj0701id() {
        return cj0701id;
    }

    public void setCj0701id(String cj0701id) {
        this.cj0701id = cj0701id;
    }

    public String getZc() {
        return zc;
    }

    public void setZc(String zc) {
        this.zc = zc;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getXnxq01id() {
        return xnxq01id;
    }

    public void setXnxq01id(String xnxq01id) {
        this.xnxq01id = xnxq01id;
    }

    public String getSfFD() {
        return sfFD;
    }

    public void setSfFD(String sfFD) {
        this.sfFD = sfFD;
    }

    @Override
    public String toString() {
        return "CourseOption{" +
                "cj0701id='" + cj0701id + '\'' +
                ", zc='" + zc + '\'' +
                ", demo='" + demo + '\'' +
                ", xnxq01id='" + xnxq01id + '\'' +
                ", sfFD='" + sfFD + '\'' +
                '}';
    }
}