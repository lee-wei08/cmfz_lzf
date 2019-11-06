package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotaion.ClearRedisCache;
import com.baizhi.annotaion.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

@Configuration // 一般Bean 可以用的注解 都可以使用 利如Autowired
@Aspect // 作用 是把当前类标识为一个切面供容器读取
public class RedisCacheAopHash {
    // 依赖jedjs
    @Autowired
    private Jedis jedis;


    @Around("execution(* com.baizhi.service.impl.*.findAll(..))") // 自定义切入点
    public Object aroud(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object target = proceedingJoinPoint.getTarget();// 获取目标方法的类对象
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();// 获取目标方法 返回值+包名+类名+方法名+参数表
        Object[] args = proceedingJoinPoint.getArgs();// 获取当前方法的参数值
        Method method = methodSignature.getMethod();// 获取当前方法的方法名
        String classname = target.getClass().getName();// 获取当前方法的类名
        boolean b = method.isAnnotationPresent(RedisCache.class);// 判断方法上是否有这个注解
        if (b) {
            // 存在 判断缓存中是否有
            StringBuilder sb = new StringBuilder();
            // 获取内层key
            sb.append(method).append("(");
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i == args.length - 1) {
                    break;
                }
                sb.append(")");
            }
            // 判断redis中是否有对应的key
            if (jedis.hexists(classname, sb.toString())) {
                // 存在 直接从缓存中取到 返回
                String result = jedis.hget(classname, sb.toString());
                return result;
            } else {
                // 缓存中不存在 加入缓存
                Object proceed = proceedingJoinPoint.proceed(); // 放行后 返回查询结果
                jedis.hset(classname, sb.toString(), JSONObject.toJSONString(proceed));
                return proceed;
            }
        } else {
            Object proceed = proceedingJoinPoint.proceed();// 不存在注解直接放行
            return proceed;
        }

    }

    @AfterReturning("execution(* com.baizhi.service.impl.*.*(..)) && !execution(* com.baizhi.service.impl.*.findAll(..))")
    public void after(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();// 获取目标方法对象
        Object[] args = joinPoint.getArgs();// 获取参数值
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();// 获取目标方法 返回值+包名+类名+方法名+参数表
        Method method = methodSignature.getMethod();// 获取当前目标方法方法名
        String classname = joinPoint.getClass().getName();// 获取目标 的类名
        if (method.isAnnotationPresent(ClearRedisCache.class)) {
            // 存在 清空缓存
            jedis.del(classname);
        }


    }


}
