package com.justreading.onePanda.aop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 日志的实体类，对应数据库中t_log
 * @date 2020 年 03 月 04 日 13:33
 */
@ApiModel("日志的实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLog  implements Serializable {
    @ApiModelProperty(value = "创建的id",example = "0")
    private String id;

    @ApiModelProperty(value = "操作")
    private String operation;

    @ApiModelProperty(value = "方法名")
    private String method;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "报错信息,当无报错的时候这个信息为空")
    private String errorMessage;

    @ApiModelProperty(value = "用户操作返回的信息")
    private String responseMessage;

    @ApiModelProperty(value = "创建时间")
    private  String createTime;
}
