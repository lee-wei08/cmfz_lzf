package com.baizhi.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 此注解指定 自定义注解所使用的位置
@Retention(RetentionPolicy.RUNTIME)// 指定注解生效时间
public @interface RedisCache {
}
