<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>统一认证中心</title>
</head>
<body background="/images/background.jpg">

<form id="login" method="post" action="/login" style="align-content: center">
    <p><input type="hidden" name="redirectUrl" value="${param.redirectUrl}"></p>
    <p><font color="white">用户名:</font><input type="text" name="username" ></p>
    <p><font color="white">密码:</font><input type="password" name="password" ></p>
    <p><input type="submit" value="登录"></p>
</form>
</body>
</html>
