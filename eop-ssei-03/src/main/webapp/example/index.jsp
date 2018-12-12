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
<input name="${_csrf.parameterName}" id="csrf" type="hidden" value="${_csrf.token}" />
<input name="submit" type="submit" value="Logout"/>
</form>
<br/>
<input type="button" name="ajaxSubmit" value="Ajax Submit" onclick="ajaxSubmit()" />
</body>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
function ajaxSubmit() {
	var csrf = $("#csrf").val();
	var data = {"_csrf":csrf};
	$.ajax({
		  type: 'POST',
		  url: '/eop-ssei/example/logoutProcess',
		  data: data,
		  success: function(data){
			  alert(data.code + ":" + data.desc);
			  location.href = './login';
		  },
		  dataType: "json"
		});
}
</script>
</html>