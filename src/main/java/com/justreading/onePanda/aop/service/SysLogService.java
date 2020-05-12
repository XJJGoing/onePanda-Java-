package com.justreading.onePanda.aop.service;

import com.justreading.onePanda.aop.entity.SysLog;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 03 月 04 日 13:51
 */
public interface SysLogService {
    /**
     * 插入日志记录
     * @param sysLog
     */
    public void insertLog(SysLog sysLog);
}
