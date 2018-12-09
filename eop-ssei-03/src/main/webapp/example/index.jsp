<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>index</title>
</head>
<body>
<h1>This is index.</h1>
<form name='f' action='/eop-ssei/example/logoutProcess' method='POST'>
<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
<input name="submit" type="submit" value="Logout"/>
</form>
</body>
</html>