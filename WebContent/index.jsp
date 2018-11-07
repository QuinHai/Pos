<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	if(session.getAttribute("admin") == null){
		response.sendRedirect("adminlogin.jsp");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
</head>
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
<!--
	frameset 属性定义每个框架所占大小(可为百分比，可为像素大小) 
	这里是第一个框架占22%,第二个框架占剩余全部 
	cols-列占比 rows-行占比
-->
<frameset cols="22%,*">
<!-- frameborder~规定是否显示框架周围的边框。
	 noresize~规定无法调整框架的大小。
	 scrolling~规定是否在框架中显示滚动条。
	 name~规定框架名称
 -->
	<frame frameborder="0" name="leftFrame" scrolling="no" noresize src="tree.jsp">
	<frame frameborder="0" name="mainFrame" noresize src="main.jsp">
</frameset>
<body>

</body>
</html>