/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: FileService
 * Author:   Slatter
 * Date:     2020/11/18 13:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.slatter.service;

import com.slatter.pojo.Model;
import com.springmvc.annotation.Service;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/11/18
 * @since 1.0.0
 */
@Service
public class FileService {
    private final String uploadDir = "/WEB-INF/upload";
    private final String tempDir = "/WEB-INF/temp";
    private final List<String> fileType = Arrays.asList("gif", "jpeg", "jpg", "png", "txt", "pdf");
    private final Set<Model> modelSet = new HashSet<Model>();   // 将所上传的文件信息放入modelSet中（同名则进行替换）

    // 上传文件处理
    public void uploadFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 上传文件的保存目录,不允许外界直接访问,保证上传文件的安全
        String savePath = req.getServletContext().getRealPath(uploadDir);

        // 上传文件时生成的临时文件保存目录
        String tempPath = req.getServletContext().getRealPath(tempDir);
        File tempFile = new File(tempPath);

        if (!tempFile.exists()) {
            // 创建临时目录
            tempFile.mkdir();
        }

        String message = "";

        boolean multipartContent = ServletFileUpload.isMultipartContent(req);

        try {
            // 需要检查表单提交内容是否是文件, 如果不是file则
            if (multipartContent) {
                // 创建磁盘工厂
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // 设置工厂缓冲区大小,当上传的文件超过缓冲区的大小时候,会生成一个临时文件存放到临时目录中
                factory.setSizeThreshold(1024 * 1024);
                // 设置上传时文件的保存目录
                factory.setRepository(new File(tempPath));

                // 得到文件上传解析器
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setHeaderEncoding("UTF-8");     // 解决上传文件名的中文乱码
                upload.setFileSizeMax(1024 * 1024);        // 限制上传文件大小

                // 监听文件上传进度
                upload.setProgressListener(new ProgressListener() {
                    @Override
                    public void update(long bytesRead, long contentLength, int i) {
                        System.out.println("文件大小为:" + contentLength + ",当前已处理:" + bytesRead);
                    }
                });
                // 调用解析器解析上传数据
                List<FileItem> list = upload.parseRequest(req);

                // 遍历list,得到用于封装第一个上传输入项目数据fileItem对象
                for (FileItem item : list) {
                    if (item.isFormField()) {
                        // 得到的是普通输入项
                        String name = item.getFieldName();    // 得到输入项的名称
                        String value = item.getString("UTF-8");
                        System.out.println(name + ":" + value);
                    } else {
                        // 得到上传文件名
                        String filename = item.getName();
                        // 得到文件后缀名
                        String fileExeName = filename.substring(filename.lastIndexOf(".") + 1);
                        if (!fileType.contains(fileExeName)) {
                            message = "上传失败!文件类型只能是gif、jpeg、jpg、png、txt、pdf";
                        } else {
                            InputStream in = item.getInputStream();

                            int len = 0;
                            byte[] buffer = new byte[1024];

                            String path = savePath + File.separator + filename;
                            FileOutputStream out = new FileOutputStream(path);
                            while ((len = in.read(buffer)) > 0) {
                                out.write(buffer, 0, len);
                            }

                            in.close();
                            out.flush();
                            out.close();
                            item.delete();
                            message = "文件上传成功";
                            modelSet.add(new Model(filename, path));
                        }
                    }
                }
            } else {
                message = "未上传任何文件";
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            e.printStackTrace();
            message = "单个文件大小超过最大值";
        } catch (FileUploadException e) {
            message = "文件上传失败";
            e.printStackTrace();
        } finally {
            req.setAttribute("message", message);
        }
    }


    // 文件下载处理
    public boolean downloadFile(HttpServletRequest req, HttpServletResponse resp, String name) throws IOException {
        boolean isFind = false;
        List<Model> modelList = new ArrayList<>(modelSet);
        boolean hasFile = modelList.size() > 0;
        for (Model model : modelList) {
            String fileName = model.getFilename();
            if (fileName.equals(name)) {
                isFind = true;
                // 使其文件下载名不乱码
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
                String filepath = model.getFilepath();
                try {
                    FileInputStream in = new FileInputStream(filepath);
                    OutputStream out = resp.getOutputStream();
                    resp.reset();
                    // 设置输出格式
                    resp.setContentType("bin");
                    resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                    // 循环取出流中的数据
                    byte[] b = new byte[1024];
                    int len;
                    while ((len = in.read(b)) > 0) {
                        out.write(b, 0, len);
                    }
                    in.close();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("文件已被成功下载");
                }
            }
        }

        req.setAttribute("modelList", modelList);

        if (!hasFile) {
            String message = "抱歉！文件库中暂时没有文件";
            req.setAttribute("message", message);
        } else {
            req.setAttribute("message", null);
        }
        return isFind;
    }
}