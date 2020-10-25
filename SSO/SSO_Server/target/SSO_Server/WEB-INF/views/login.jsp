<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>统一认证中心</title>
</head>
<body>
<form id="login" method="post" action="/login">
    <p><input type="hidden" name="redirectUrl" value="${param.redirectUrl}"></p>
    <p><label>用户名:</label><input type="text" name="username" ></p>
    <p><label>密码:</label><input type="password" name="password" ></p>
    <p><input type="submit" value="登录"></p>
</form>
</body>
</html>
