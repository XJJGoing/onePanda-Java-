package com.justreading.onePanda.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author LYJ
 * @Description 定制统一返回规则
 * @date 2020 年 02 月 15 日 12:14
 */
@ApiModel("统一返回规则")
public class ApiResponse<T> {
   @ApiModelProperty(value = "返回的状态码",required = true)
   private Integer code;

   @ApiModelProperty(value = "返回的信息",required = true)
   private String  msg;

   @ApiModelProperty(value = "返回的数据")
   private T data;

   public Integer getCode() {
      return code;
   }

   public void setCode(Integer code) {
      this.code = code;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public T getData() {
      return data;
   }

   public void setData(T data) {
      this.data = data;
   }

   @Override
   public String toString() {
      return "ApiResponse{" +
              "code=" + code +
              ", msg='" + msg + '\'' +
              ", data=" + data +
              '}';
   }
}
