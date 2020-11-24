<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>FileDownload</title>
</head>
<body>

<c:forEach items="${modelList}" var="model">
    <p>${model.getFilename()}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/download?filename=${model.getFilename()}">下载</a></p>
</c:forEach>

<a href="/">返回主界面</a>
</body>
</html>
