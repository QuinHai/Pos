<%@ page import="java.util.*" %>
<%@ page import="com.quinhai.pos.beans.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员添加</title>
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
function check(){
  	if(document.all.aname.value.trim()==""){
  	  alert("管理员名称不能为空!!!");
  	  return false;
  	}
  	if(document.all.apwd.value.trim()==""){
  	  alert("密码不能为空!!!");
  	  return false;
  	}
  	if(document.all.apwd.value.trim()!=document.all.fpwd.value.trim()){
  	  alert("两次密码输入不一致!!!");
  	  return false;
  	}
  	document.all.mf.submit();
  }
</script>
</head>
<body bgcolor="#EBF5FD">
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td>
        <font color="#FFFFFF" size="5">管理员管理</font>
        <font color="#FFFFFF" size="2">--管理员添加</font>
      </td></tr>
	</table>
	<table>
	  <tr><td><a href="javascript:history.back()">
	    <img border="0" src="img/back.jpg"/></a>
	  </td></tr>
	</table>
	<hr color="black" size="1"/>
	<form action="ManageServlet" method="post" id="mf">
	<table width="80%" border="0" cellspacing="1" bgcolor="black" align="center">	
	  <tr bgcolor="white">
	    <td align="center">管理员名称:</td>
	    <td><input name="aname" id="aname"/></td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">管理员密码:</td>
	    <td><input type="password" name="apwd" id="apwd"/></td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">确认密码:</td>
	    <td><input type="password" name="fpwd" id="fpwd"/></td>
	  </tr>
	</table>
	<table align="center">
	  <tr>
	    <td align="right">
	    <img border="0" src="img/xg.gif" id="xg" onclick="JavaScript:check()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.xg.src='img/xga.gif'"
          	  onmouseout="document.all.xg.src='img/xg.gif'"
          	  onmouseup="document.all.xg.src='img/xga.gif'"        	
          	  onmousedown="document.all.xg.src='img/xgb.gif'"/>
	    </td>
	    <td align="left">
	    <img border="0" src="img/cze.gif" id="cz" onclick="JavaScript:document.all.mf.reset()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.cz.src='img/czd.gif'"
          	  onmouseout="document.all.cz.src='img/cze.gif'"
          	  onmouseup="document.all.cz.src='img/czd.gif'"        	
          	  onmousedown="document.all.cz.src='img/czc.gif'"/>
	    </td>
	  </tr>
	</table>
	<input type="hidden" name="action" value="addAdmin"/>
	</form>
</body>
</html>