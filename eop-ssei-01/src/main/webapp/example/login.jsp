<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My Login Page</title>
</head>
<body onload='document.f.username.focus();'>
<h3 style="color:red">Login with Username and Password</h3><form name='f' action='/eop-ssm/example/loginProcess' method='POST'>
<table>
	<tr><td>User:</td><td><input type='text' name='username' value=''></td></tr>
	<tr><td>Password:</td><td><input type='password' name='password'/></td></tr>
	<tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
	<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
</table>
</form></body>
</html>