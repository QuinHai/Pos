<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录界面</title>
<link rel=stylesheet href="css/style.css" type="text/css">
<!-- html5中不支持<script>标签的language属性 -->
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
function check(){
	var uname = document.all.uname.value;
	var upwd = document.all.upwd.value;
	if(uname.trim()==""){
		alert("用户名为空,请重新输入!!!");
		return;
	}
	if(upwd.trim()==""){
		alert("密码为空,请重新输入!!!");
		return;
	}
	document.all.mf.submit();
}
</script>
</head>
<body>
<div class="out">
	<div class="login_style">
		<center>
			<form action="ManageServlet" method="post" id="mf" target="bottom">
				<table>
					<tr>
						<td>用户名：</td>
						<td><input type="text" id="uname" name="uname" value="zrk"/></td>
					</tr>
					<tr>
						<td>密 码：</td>
						<td><input type="password" id="upwd" name="upwd" value="12345"/></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
						<!-- 多数浏览器不兼容cursor:hand -->
						<!-- style表示表内使用的css格式 -->
						<!-- cursor:hand ~ 鼠标移上去变为手的形状 -->
						<img border="0" src="img/ddl.gif" id="lg" onclick="JavaScript:check()"
							style="cursor:pointer"
							onmouseover="document.all.lg.src='img/ddla.gif'"
							onmouseout="document.all.lg.src='img/ddl.gif'"
							onmouseup="document.all.lg.src='img/ddla.gif'"
							onmousedown="document.all.lg.src='img/ddlb.gif'"
						/>
						<img border="0" src="img/cz.gif" id="cz" onclick="JavaScript:document.all.mf.reset()"
							style="cursor:pointer"
							onmouseover="document.all.cz.src='img/cza.gif'"
							onmouseout="document.all.cz.src='img/cz.gif'"
							onmouseup="document.all.cz.src='img/cza.gif'"
							onmousedown="document.all.cz.src='img/czb.gif'"
						/>
					</tr>
				</table>
				<!-- 隐藏域，告诉servlet当前界面为登录 -->
				<input type="hidden" name="action" value="login">
			</form>
		</center>
	</div>
</div>
</body>
</html>