package com.justreading.onePanda.aop.service.impl;

import com.justreading.onePanda.aop.entity.SysLog;
import com.justreading.onePanda.aop.mapper.SysLogMapper;
import com.justreading.onePanda.aop.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 03 月 04 日 13:52
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void insertLog(SysLog sysLog) {
        sysLogMapper.insertLog(sysLog);
    }
}
