package com.springmvc.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)   // 元注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoWired {
    String value() default "";
}
