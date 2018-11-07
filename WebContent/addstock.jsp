<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.quinhai.pos.DBUtil.*, 
				com.quinhai.pos.beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购信息添加</title>
<script type="text/javascript" src= "script/trim.js"></script>
<script type="text/javascript">
function check(){
    var reg = /^\d+(\.\d+)?$/;
    var stp = document.all.stp.value.trim();
  	if(!reg.test(stp)){
  	  alert("采购总价格式不对,请重新输入!!!");
  	  return false;
  	}
  	if(document.all.sbuyer.value.trim()==""){
  	  alert("采购人不能为空!!!");
  	  return false;
  	}
  	if(document.all.pname.value.trim()==""){
  	  alert("供应商不能为空,请添加供应商!!!");
  	  return false;
  	}
	alert("=====成功=====");
  	document.all.mf.submit();
  }
</script>
</head>
<body>
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
	<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td>
        <font color="#FFFFFF" size="5">采购信息管理</font>
        <font color="#FFFFFF" size="2">--采购添加</font>
      </td></tr>
	</table>
	<table>
	  <tr><td><a href="javascript:history.back()">
	    <img src="img/back.jpg"/></a>
	  </td></tr>
	</table>
	<hr color="black" size="1"/>	
	<form action="ManageServlet" method="post" id="mf">
	 <table width="80%" border="0" cellspacing="1" bgcolor="black" align="center">
	  <tr bgcolor="white">
	   <td align="center">供&nbsp;应&nbsp;商:</td>
	   <td>
	    <select name="pname" id="pname">
	    	<%
	    		WebApplicationContext wac = WebApplicationContextUtils.
	    			getWebApplicationContext(this.getServletContext());
	    		DBUtil db = (DBUtil)wac.getBean("dbutil");
	    		List<String> pname = db.getProvider();
	    		for(String name:pname){
	    			name = new String (name.getBytes(), "UTF-8");
	    	%>
	    	<option value="<%= name %>"><%= name %></option>
	    	<%
	    		}
	    	%>
	    </select>
	   </td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">采购总价:</td>
	    <td><input name="stp" id="stp"/></td>
	  </tr>
	  <tr bgcolor="white">
	    <td align="center">采&nbsp;购&nbsp;人:</td>
	    <td><input name="sbuyer" id="sbuyer"/></td>
	  </tr>
	 </table>
	 <table align="center">
	  <tr>
	   <td align="center">
	   <img alt="xg" src="img/xg.gif" id="xg" onclick="javascript:check()"
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
	 <input type="hidden" name="action" value="addStock">
	</form>
</body>
</html>