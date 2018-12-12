<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My Login Page</title>
</head>
<body onload='document.f.username.focus();'>
<h3 style="color:red">Login with 用户名 and 密码</h3>
<form name='f' action='/eop-ssei/example/loginProcess' method='POST'>
<table>
	<tr><td>User:</td><td><input type='text' name='username' id="username"></td></tr>
	<tr><td>Password:</td><td><input type='password' name='password' id="password"/></td></tr>
	<tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
	<input name="${_csrf.parameterName}" id="csrf" type="hidden" value="${_csrf.token}" />
</table>
</form>
<br/>
<input type="button" name="ajaxSubmit" value="Ajax Submit" onclick="ajaxSubmit()" />
</body>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
function ajaxSubmit() {
	var username = $("#username").val();
	var password = $("#password").val();
	var csrf = $("#csrf").val();
	var data = {username:username,password:password,"_csrf":csrf};
	$.ajax({
		  type: 'POST',
		  url: '/eop-ssei/example/loginProcess',
		  data: data,
		  success: function(data){
			  alert(data.code + ":" + data.desc);
			  if (data.code == 0) {
			  	location.href = './index';
			  }
		  },
		  dataType: "json"
		});
}
</script>
</html>