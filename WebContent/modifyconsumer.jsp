<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.quinhai.pos.beans.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改客户</title>
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
function check(){
    if(document.all.clinkman.value.trim()==""){
    	alert("联系人不能为空!!!");
    	return;
    }
    if(document.all.caddress.value.trim()==""){
    	alert("公司地址不能为空!!!");
    	return;
    }
    if(document.all.ctel.value.trim()==""){
    	alert("公司电话不能为空!!!");
    	return;
    }
  	 document.all.mf.submit();
  }
</script>
</head>
<%
	ConsumerInfo ci = (ConsumerInfo)request.getAttribute("object");
%>
<body bgcolor="#EBF5FD">
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
	<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td>
        <font color="#FFFFFF" size="5">客户资料管理</font>
        <font color="#FFFFFF" size="2">--客户修改</font>
      </td></tr>
	</table>
	<table>
	  <tr><td><a href="javascript:history.back()">
	    <img border="0" src="img/back.jpg"/></a>
	  </td></tr>
	</table>
	<hr color="black" size="1" width="100%"/>
	
	<form action="ManageServlet" method="post" id="mf">
 	<table width="80%" border="0" cellspacing="1" bgcolor="black" align="center">
	  <tr bgcolor="white">
	    <td align="center">客户名称:</td>
		<td><input size="20" name="cname" id="cname"
			value="<%= new String(ci.getCname().getBytes(), "UTF-8") %>"/></td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">联&nbsp;系&nbsp;人:</td>
		<td><input size="20" name="clinkman" id="clinkman"
			value="<%= new String(ci.getClinkman().getBytes(), "UTF-8") %>"/></td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">公司地址:</td>
		<td><input size="20" name="caddress" id="caddress"
			value="<%= new String(ci.getCaddress().getBytes(), "UTF-8") %>"/></td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">公司电话:</td>
		<td><input size="20" name="ctel" id="ctel"
			value="<%= new String(ci.getCtel().getBytes(), "UTF-8") %>"/></td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">E-mail:</td>
		<td><input size="20" name="cemail" id="cemail"
			value="<%= new String(ci.getCemail().getBytes(), "UTF-8") %>"/></td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">备&nbsp;&nbsp;&nbsp;&nbsp;注:</td>
		<td><input size="20" name="cremark" id="cremark"
			value="<%= new String(ci.getCremark().getBytes(), "UTF-8") %>"/></td>
	  </tr>
	</table>
	<table align="left" width="80%">
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
	<input type="hidden" name="action" value="modifyConsumer"/>
	<input type="hidden" name="cid" value="<%= ci.getCid() %>"/>
	<input type="hidden" name="cname" value="<%= new String(ci.getCname().getBytes(),"UTF-8") %>"/>
 	</form>
</body>
</html>