package com.justreading.onePanda.advertisement.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author LYJ
 * @Description 娱乐图片或者视频的实体，数据库中并没有该表
 * @date 2020 年 02 月 28 日 21:20
 */
@ApiModel("娱乐的实体")
public class Advertisement {
   @ApiModelProperty(value = "图片或者视频的id",example = "0")
   private Integer id;

   @ApiModelProperty(value = "图片或者视频的url")
   private String url;

   @ApiModelProperty(value = "作品的名称")
   private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
