package com.justreading.onePanda.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LYJ
 * @Description 对应数据库中的实体表
 * @date 2020 年 02 月 15 日 12:11
 */
@ApiModel("用户的实体类")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User implements Serializable {
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

    public Integer getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentMajorName() {
        return studentMajorName;
    }

    public void setStudentMajorName(String studentMajorName) {
        this.studentMajorName = studentMajorName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getWxAvatarUrl() {
        return wxAvatarUrl;
    }

    public void setWxAvatarUrl(String wxAvatarUrl) {
        this.wxAvatarUrl = wxAvatarUrl;
    }

    public Integer getWxGender() {
        return wxGender;
    }

    public void setWxGender(Integer wxGender) {
        this.wxGender = wxGender;
    }

    public String getWxCity() {
        return wxCity;
    }

    public void setWxCity(String wxCity) {
        this.wxCity = wxCity;
    }

    public String getWxProvince() {
        return wxProvince;
    }

    public void setWxProvince(String wxProvince) {
        this.wxProvince = wxProvince;
    }

    public String getWxCountry() {
        return wxCountry;
    }

    public void setWxCountry(String wxCountry) {
        this.wxCountry = wxCountry;
    }

    public String getWxLanguage() {
        return wxLanguage;
    }

    public void setWxLanguage(String wxLanguage) {
        this.wxLanguage = wxLanguage;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getWxSessionKey() {
        return wxSessionKey;
    }

    public void setWxSessionKey(String wxSessionKey) {
        this.wxSessionKey = wxSessionKey;
    }

    public String getQqNickName() {
        return qqNickName;
    }

    public void setQqNickName(String qqNickName) {
        this.qqNickName = qqNickName;
    }

    public String getQqAvatarUrl() {
        return qqAvatarUrl;
    }

    public void setQqAvatarUrl(String qqAvatarUrl) {
        this.qqAvatarUrl = qqAvatarUrl;
    }

    public Integer getQqGender() {
        return qqGender;
    }

    public void setQqGender(Integer qqGender) {
        this.qqGender = qqGender;
    }

    public String getQqCity() {
        return qqCity;
    }

    public void setQqCity(String qqCity) {
        this.qqCity = qqCity;
    }

    public String getQqProvince() {
        return qqProvince;
    }

    public void setQqProvince(String qqProvince) {
        this.qqProvince = qqProvince;
    }

    public String getQqCountry() {
        return qqCountry;
    }

    public void setQqCountry(String qqCountry) {
        this.qqCountry = qqCountry;
    }

    public String getQqLanguage() {
        return qqLanguage;
    }

    public void setQqLanguage(String qqLanguage) {
        this.qqLanguage = qqLanguage;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getQqSessionKey() {
        return qqSessionKey;
    }

    public void setQqSessionKey(String qqSessionKey) {
        this.qqSessionKey = qqSessionKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getIsAssistant() {
        return isAssistant;
    }

    public void setIsAssistant(Integer isAssistant) {
        this.isAssistant = isAssistant;
    }

    public String getMajorNumber() {
        return majorNumber;
    }

    public void setMajorNumber(String majorNumber) {
        this.majorNumber = majorNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isRoot=" + isRoot +
                ", trueName='" + trueName + '\'' +
                ", college='" + college + '\'' +
                ", wxNickName='" + wxNickName + '\'' +
                ", wxAvatarUrl='" + wxAvatarUrl + '\'' +
                ", wxGender=" + wxGender +
                ", wxCity='" + wxCity + '\'' +
                ", wxProvince='" + wxProvince + '\'' +
                ", wxCountry='" + wxCountry + '\'' +
                ", wxLanguage='" + wxLanguage + '\'' +
                ", wxOpenid='" + wxOpenid + '\'' +
                ", wxSessionKey='" + wxSessionKey + '\'' +
                ", qqNickName='" + qqNickName + '\'' +
                ", qqAvatarUrl='" + qqAvatarUrl + '\'' +
                ", qqGender=" + qqGender +
                ", qqCity='" + qqCity + '\'' +
                ", qqProvince='" + qqProvince + '\'' +
                ", qqCountry='" + qqCountry + '\'' +
                ", qqLanguage='" + qqLanguage + '\'' +
                ", qqOpenid='" + qqOpenid + '\'' +
                ", qqSessionKey='" + qqSessionKey + '\'' +
                ", email='" + email + '\'' +
                ", origin=" + origin +
                ", isAssistant=" + isAssistant +
                ", majorNumber='" + majorNumber + '\'' +
                ", studentMajorName='" + studentMajorName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
