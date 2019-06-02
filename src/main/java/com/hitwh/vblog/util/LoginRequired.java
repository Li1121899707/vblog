package com.hitwh.vblog.util;

import java.lang.annotation.*;

/**
 * @ClassName YfLog
 * @Description TODO
 * @Author yf
 * @Date 2018/10/15 10:18
 * @Version 1.0
 */

@Target(ElementType.METHOD)             //在方法上执行
@Retention(RetentionPolicy.RUNTIME)     //在运行时，可通过反射获取到
@Documented                             //javac 注释
@Inherited                              //可被继承
public @interface LoginRequired {
//    boolean required() default false;
}
