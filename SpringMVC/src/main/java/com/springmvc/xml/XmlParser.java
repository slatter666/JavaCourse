/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: XmlParser
 * Author:   Slatter
 * Date:     2020/11/17 16:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.springmvc.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/11/17
 * @since 1.0.0
 * @description:解析springmvc.xml文件
 */
public class XmlParser {
    public static String getbasePackage(String xml){
        try{
            SAXReader saxReader = new SAXReader();
            InputStream resourceAsStream = XmlParser.class.getClassLoader().getResourceAsStream(xml);
            // XML文档对象
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            Element componentScan = rootElement.element("component-scan");
            Attribute attribute = componentScan.attribute("base-package");
            String basePackage = attribute.getText();
            return basePackage;
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {

        }
        return "";
    }
}