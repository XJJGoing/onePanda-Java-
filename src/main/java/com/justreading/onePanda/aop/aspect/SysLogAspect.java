package com.justreading.onePanda.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.justreading.onePanda.aop.annotation.MyLog;
import com.justreading.onePanda.aop.entity.SysLog;
import com.justreading.onePanda.aop.service.SysLogService;
import org.apache.http.protocol.HttpContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author LYJ
 * @Description  切面类
 * @date 2020 年 03 月 04 日 13:50
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    /**
     *  切入点表达式
     */
    @Pointcut("@annotation(com.justreading.onePanda.aop.annotation.MyLog)")
    public void  logPointCut(){};


    /**
     * 报错异常通知
     */
    @AfterThrowing(value = "logPointCut()",throwing = "e")
    public void  saveErrorLog(JoinPoint joinPoint,Exception e){
         SysLog sysLog  = new SysLog();
         MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
         Method method = methodSignature.getMethod();

         MyLog myLog = method.getAnnotation(MyLog.class);
         String operation = myLog.value();
         String methodName = method.getName();
         String className = joinPoint.getTarget().getClass().getName();

         ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
         HttpServletRequest request = requestAttributes.getRequest();

         Object[] args = joinPoint.getArgs();

         sysLog.setOperation(operation);
         sysLog.setMethod(className + "." + methodName);
         sysLog.setIp(request.getRemoteAddr());
         sysLog.setParams(JSON.toJSONString(args));
         sysLog.setErrorMessage("报错:" + e);

         //插入日志记录
         sysLogService.insertLog(sysLog);
    }

    /**
     * 切面 配置通知,返回的消息,正常的不切了，不然消息太多了
     */
//    @AfterReturning(value = "logPointCut()",returning = "ret")
//    public void saveReturnLog(JoinPoint joinPoint,Object ret){
//        SysLog sysLog  = new SysLog();
//
//        //获取切入点所在的方法
//        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//
//        //获取操作
//        MyLog myLog = method.getAnnotation(MyLog.class);
//        if(myLog != null){
//            String value = myLog.value();
//            sysLog.setOperation(value);
//        }
//
//        //获取请求的类名
//        String className = joinPoint.getTarget().getClass().getName();
//
//        //获取请求的方法名
//        String methodName = method.getName();
//        sysLog.setMethod(className + "." + methodName);
//
//        //请求的参数
//        Object[] args = joinPoint.getArgs();
//        for(Object arg : args){
//        }
//
//        //将参数所在的数组转换成json
//        String params = JSON.toJSONString(args);
//        sysLog.setParams(params);
//
//         //获取用户的ip地址
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        sysLog.setIp(request.getRemoteAddr());
//
//        //设置返回的信息
//        sysLog.setResponseMessage("返回信息:" + JSON.toJSONString(ret));
//
//        //插入日志记录
//        sysLogService.insertLog(sysLog);
//    }
}
