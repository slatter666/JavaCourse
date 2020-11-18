<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>FileDownload</title>
</head>
<body>

<%--跳转网址参数可以任意设置,可以不为filename,但是需要到UserController中@RequestParam("filename")将名字进行相应替换，不推荐更改--%>
<p>${requestScope.model}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/download?filename=${requestScope.model}">下载</a></p>
<a href="/">返回主界面</a>
</body>
</html>
