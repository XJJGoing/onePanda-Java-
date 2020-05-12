package com.justreading.onePanda.aop.mapper;

import com.justreading.onePanda.aop.entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 03 月 04 日 13:52
 */
@Mapper
public interface SysLogMapper {

    @Insert("insert into t_log(operation,method,params,ip,error_message,response_message)" +
            "values(#{operation},#{method},#{params},#{ip},#{errorMessage},#{responseMessage})")
    public void insertLog(SysLog sysLog);
}
