/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: MyHandler
 * Author:   Slatter
 * Date:     2020/11/17 21:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.springmvc.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/11/17
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyHandler {
    private String url;
    private Object controller;
    private Method method;
}