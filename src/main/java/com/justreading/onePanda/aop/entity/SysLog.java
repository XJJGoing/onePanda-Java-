package com.justreading.onePanda.aop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 日志的实体类，对应数据库中t_log
 * @date 2020 年 03 月 04 日 13:33
 */
@ApiModel("日志的实体类")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getOperation() {
        return operation;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
