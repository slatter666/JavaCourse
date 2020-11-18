/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ContextException
 * Author:   Slatter
 * Date:     2020/11/17 17:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.springmvc.exception;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/11/17
 * @since 1.0.0
 * @description:自定义异常
 */
public class ContextException extends RuntimeException{
    public ContextException(String message) {
        super(message);
    }

    public ContextException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}