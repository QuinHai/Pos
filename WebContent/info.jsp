<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提示信息</title>
</head>
<body bgcolor="#EBF5FD">
	<center><!-- 对其所包括的文本进行水平居中。 -->
		<br/><br/><br/><br/><br/><br/>
		<h1>
			<%= request.getAttribute("msg") %>
			<p/>
			<a href="JavaScript:history.back()">
				<img alt="back" src="img/back.jpg" >
			</a>
		</h1>		
	</center>
</body>
</html>