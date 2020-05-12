package com.justreading.onePanda.common;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LYJ
 * @Description 向ioc容器中添加一些通用的bean，同步锁lock
 * @date 2020 年 02 月 15 日 21:45
 */
@Component
public class MyClassBean {

    @Bean("reentrantLock")
    public ReentrantLock reentrantLock(){
        return new ReentrantLock();
    }
}
