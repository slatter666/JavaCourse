package com.springmvc.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)   // 元注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
