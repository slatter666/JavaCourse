<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FileUpload</title>
</head>
<body>
<form action="upload" enctype="multipart/form-data" method="post" >
    <input type="file" name="filename">
    <button type="submit">上传</button>
    <br>
    <p style="color: deepskyblue">${requestScope.message}</p>
    <br>
    <a href="/">返回主界面</a>
</form>
</body>
</html>
