package com.springmvc.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)   // 元注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
