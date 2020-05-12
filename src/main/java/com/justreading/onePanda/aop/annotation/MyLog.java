package com.justreading.onePanda.aop.annotation;

import java.lang.annotation.*;

/**
 * @author LYJ
 * @Description 使用spring的apo技术切到自定义注解上
 * @date 2020 年 03 月 04 日 13:47
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    String value() default "";
}
