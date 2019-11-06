package com.baizhi.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 此注解指明自定义注解要使用的位置 参数：在方法上使用
@Retention(RetentionPolicy.RUNTIME) // 指明注解的生效时机， 参数：运行时生效
public @interface ClearRedisCache {

}
