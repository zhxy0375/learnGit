package com.example.demo_cloud.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Aspect
@Configuration
public class WebLogAspectConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 定义切点Pointcut
     * 第一个*号：表示返回类型， *号表示所有的类型
     * 第二个*号：表示类名，*号表示所有的类
     * 第三个*号：表示方法名，*号表示所有的方法
     * 后面括弧里面表示方法的参数，两个句点表示任何参数
     */
    @Pointcut("execution(* com.example.*.controller..*(..))")
    public void executionService() {
 
    }
    
 
    /**
     * 方法调用之前调用
     * @param joinPoint
     */
    @Before(value = "executionService()")
    public void doBefore(JoinPoint joinPoint){
 
        //添加日志打印
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put("logthreadId", requestId);
        logger.info("=====>@Before：请求url：{}",((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        Object[] obj = joinPoint.getArgs();
        if (obj.length > 0) {
            Object param = obj[0];
            if(!(param instanceof HttpServletRequest)){
            	logger.info("=====>@Before：请求参数为：{}", JSON.toJSONString(param));
            }else{
            	logger.info("=====>@Before：请求参数为：{}","HttpServletRequest");
            }
            
        }
 
    }
 
    /**
     * 方法之后调用
     * @param joinPoint
     * @param returnValue 方法返回值
     */
    @AfterReturning(pointcut = "executionService()",returning="returnValue")
    public void  doAfterReturning(JoinPoint joinPoint, Object returnValue){
 
        logger.info("=====>@AfterReturning：响应参数为：{}", JSON.toJSONString(returnValue));
        // 处理完请求，返回内容
        MDC.clear();
    }
 
    /**
     * 统计方法执行耗时Around环绕通知
     * @param joinPoint
     * @return
     */
    @Around("executionService()")
    public Object timeAround(ProceedingJoinPoint joinPoint) {
 
        //获取开始执行的时间
        long startTime = System.currentTimeMillis();
 
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error("=====>统计某方法执行耗时环绕通知出错", e);
        }
 
        // 获取执行结束的时间
        long endTime = System.currentTimeMillis();
        // 打印耗时的信息
        logger.info("=====>处理本次请求共耗时：{} ms",endTime-startTime);
        return obj;
    }
}