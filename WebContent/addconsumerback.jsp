<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="java.util.*" %>
<%@ page import="com.quinhai.pos.beans.*, com.quinhai.pos.DBUtil.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户退货添加</title>
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
function check(){
  	if(document.all.eid.value.trim()==""){
  		alert("销售ID不能为空!!!");
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
        <font color="#FFFFFF" size="5">客户退货管理</font>
        <font color="#FFFFFF" size="2">--退货添加</font>
      </td></tr>
	</table>
	<table>
	  <tr><td><a href="javascript:history.back()">
	    <img border="0" src="img/back.jpg"/></a>
	  </td></tr>
	</table>
	<hr color="black" size="1"/>
    <br/><br/><br/><br/><br/>
    <form action="ManageServlet" method="post" id="mf">
    <center>
      请选择要退货的表单号:
  	  <select name="eid" id="eid">
  	  <% 
		//获取WebApplicationContext
		WebApplicationContext wac=
		   WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		DBUtil db = (DBUtil)wac.getBean("dbutil");
		String hql = "SELECT ei.Eid FROM SellInfo AS ei";
		List<String> eid = (List<String>)db.getInfo(hql);
		for(String name:eid){
			name = new String(name.getBytes(),"UTF-8");
  	    %>
  	    	<option value="<%= name %>"><%= name %></option>
  	    <% 
  	    	}
  	     %>
  	  </select>
    <br/><br/><img border="0" src="img/xg.gif" id="xg" onclick="JavaScript:check()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.xg.src='img/xga.gif'"
          	  onmouseout="document.all.xg.src='img/xg.gif'"
          	  onmouseup="document.all.xg.src='img/xga.gif'"        	
          	  onmousedown="document.all.xg.src='img/xgb.gif'"/>
	    <img border="0" src="img/cze.gif" id="cz" onclick="JavaScript:document.all.mf.reset()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.cz.src='img/czd.gif'"
          	  onmouseout="document.all.cz.src='img/cze.gif'"
          	  onmouseup="document.all.cz.src='img/czd.gif'"        	
          	  onmousedown="document.all.cz.src='img/czc.gif'"/>
    <input type="hidden" name="action" value="addConsumerBack"/>
    </center>
    </form>
</body>
</html>