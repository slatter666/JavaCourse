package com.springmvc.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)   // 元注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    // 不命名会自动使用变量名,不推荐使用不命名方式
    String value() default "";
}
