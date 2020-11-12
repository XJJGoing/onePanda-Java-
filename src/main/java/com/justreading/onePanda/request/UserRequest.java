package com.justreading.onePanda.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 用户登录请求的实体
 * @date 2020 年 11 月 03 日 15:25
 */
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {
    @ApiModelProperty(value = "用户的id",example = "1")
    private Integer id;

    @ApiModelProperty(value = "学生的学号",required = true)
    private String username;

    @ApiModelProperty(value = "学生的密码",required = true)
    private String password;

    @ApiModelProperty(value = "是否为超级用户 1:超级用户 0:普通用户",example = "0")
    private Integer isRoot;

    @ApiModelProperty(value = "学生的真实姓名")
    private String trueName;

    @ApiModelProperty(value = "学生的学院")
    private String college;

    @ApiModelProperty(value = "微信用户的昵称")
    private String wxNickName;

    @ApiModelProperty(value = "微信用户的头像")
    private String wxAvatarUrl;

    @ApiModelProperty(value = "微信用户的性别",example = "1")
    private Integer wxGender;

    @ApiModelProperty(value = "微信用户的城市")
    private String wxCity;

    @ApiModelProperty(value = "微信用户的省份")
    private String wxProvince;

    @ApiModelProperty(value = "微信用户的国家")
    private String wxCountry;

    @ApiModelProperty(value = "微信用户的语言")
    private  String wxLanguage;

    @ApiModelProperty(value = "微信用户的openid")
    private  String wxOpenid;

    @ApiModelProperty(value = "微信用户的session_key")
    private String wxSessionKey;


    @ApiModelProperty(value = "QQ用户的昵称")
    private String qqNickName;

    @ApiModelProperty(value = "QQ用户的头像")
    private String qqAvatarUrl;

    @ApiModelProperty(value = "QQ用户的性别",example = "1")
    private Integer qqGender;

    @ApiModelProperty(value = "QQ用户的城市")
    private String qqCity;

    @ApiModelProperty(value = "QQ用户的省份")
    private String qqProvince;

    @ApiModelProperty(value = "QQ用户的国家")
    private String qqCountry;

    @ApiModelProperty(value = "QQ用户的语言")
    private  String qqLanguage;

    @ApiModelProperty(value = "QQ用户的openid")
    private  String qqOpenid;

    @ApiModelProperty(value = "QQ用户的session_key")
    private String qqSessionKey;

    @ApiModelProperty(value = "用户的邮箱")
    private String email;

    @ApiModelProperty(value = "用户的平台 1 微信 2 QQ",example = "1")
    private Integer origin;

    @ApiModelProperty(value = "是否是学业导师或者辅导员",example = "1")
    private Integer isAssistant;

    @ApiModelProperty(value = "辅导员或者学业导师的专业号")
    private  String majorNumber;

    @ApiModelProperty(value = "学生的专业名称")
    private  String studentMajorName;

    @ApiModelProperty(value = "创建时间")
    private String  createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "微信或者qq用户的code,不进数据库存储")
    private  String code;
}
