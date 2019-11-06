package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotaion.ClearRedisCache;
import com.baizhi.annotaion.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

//@Configuration // 一般Bean可以用的注解 也可以使用
//@Aspect
public class ReadisCacheAop {
    // 依赖j d sen
    @Autowired
    private Jedis jedis;


    @Around("execution(* com.baizhi.service.impl.*.findAll(..))") // 自定义切入点
    public Object around(ProceedingJoinPoint pro) throws Throwable {
        //1. 判断目标方法上是否存在RedisCache 注解
        //2. 如果存在 则需要做缓存
        //3. 如果不存在，则没有缓存，直接放行方法

        // 获取目标方法
        Object target = pro.getTarget();// 获取目标方法所在的 类的对象 target：target:com.baizhi.service.impl.BannerServiceImpl@193b3b18
        MethodSignature methodSignature = (MethodSignature) pro.getSignature();// 获取目标方法 返回值+包名+类名+方法名+参数表
        Object[] args = pro.getArgs();// 获取方法的的参数值
        Method method = methodSignature.getMethod();// 拿到method对象
        boolean b = method.isAnnotationPresent(RedisCache.class);// 判断我当前的方法上是否有这个注解 参数是 （注解）类对象
        if (b) {
            // 目标方法之上存在RedisCache注解
            // 直接访问redis数据库 通过key取到数据
            String classname = target.getClass().getName();// 拿到当前类的类名
            String methodName = method.getName();// 拿到方法名
            StringBuilder sb = new StringBuilder(); // 做Key的拼接
            sb.append(classname).append(".").append(methodName).append("(");
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i == args.length - 1) {
                    break;
                }
                sb.append(",");
            }
            sb.append(")");// 拼接完成
            String key = sb.toString();// 生成完整的key
            // 判断redis 缓存中是否有这个key
            if (jedis.exists(key)) {
                // 存在
                String s = jedis.get(key);// 拿到string类型的结果
                return JSONObject.parse(s); // 返回一个Object结果
            } else {
                // 不存在
                Object result = pro.proceed();// 方法放行 拿到结果
                jedis.set(key, JSONObject.toJSONString(result)); // 存入redis缓存
                return result;// 返回
            }

        } else {
            // 目标方法之上不存在这个注解 放行
            Object result = pro.proceed();
            return result;
        }
    }

    // 执行增删改 是清空缓存
    // @AfterReturning:目标方法一旦有异常，则不会执行切面中的代码（删除缓存）
    @AfterReturning("execution(* com.baizhi.service.impl.*.*(..)) && !execution(* com.baizhi.service.impl.*.findAll(..)))")
    public void after(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget(); // 获取类的对象
        String classname = target.getClass().getName();// 拿到类的类名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();// 获取目标方法 返回值+包名+类名+方法名+参数表
        Object[] args = joinPoint.getArgs();// 拿到参数值
        Method method = methodSignature.getMethod();// 拿到方法名
        boolean b = method.isAnnotationPresent(ClearRedisCache.class);// b = true 代表方法上有这个注解
        if (b) {
            // 清除缓存
            Set<String> keys = jedis.keys("*");// 拿到所有的kay
            for (String key : keys) {
                if (key.startsWith(classname)) {// 比较方法名
                    jedis.del(key); // 相等删除
                }
            }
        }
    }
}
