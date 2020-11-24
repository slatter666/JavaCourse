/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: UserController
 * Author:   Slatter
 * Date:     2020/11/17 14:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.slatter.controller;

import com.slatter.service.FileService;
import com.springmvc.annotation.AutoWired;
import com.springmvc.annotation.Controller;
import com.springmvc.annotation.RequestMapping;
import com.springmvc.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/11/17
 * @since 1.0.0
 */
@Controller          // 默认是类名 首字母小写 userController
public class UserController {

    // 持有业务逻辑层对象
    @AutoWired
    FileService fileService;


    @RequestMapping("/upload")
    public String upoadFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        fileService.uploadFile(req, resp);
        return "/upload.jsp";
    }

    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest req, HttpServletResponse resp, @RequestParam("filename") String filename) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        boolean isFind = fileService.downloadFile(req, resp, filename);
        if (isFind) {
            return null;
        } else {
            return "/download.jsp";
        }
    }
}