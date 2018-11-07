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
<title>退货修改</title>
<link rel=stylesheet href="css/general.css" type="text/css">
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
function checkDetail(myform,amount){
  	reg = /^[1-9][0-9]*$/
  	if(!reg.test(amount.trim())){
  		alert("商品数量格式不对!!!");
  		return;
  	}
  	myform.submit();
  }
</script>
</head>

<% 
	ConsumerBack cb = (ConsumerBack)request.getAttribute("cb");
	List<ConsumerBackDetail> list = (List<ConsumerBackDetail>)request.getAttribute("list");
	//获取WebApplicationContext
	WebApplicationContext wac=
	   WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	DBUtil db = (DBUtil)wac.getBean("dbutil");
	ConsumerInfo ci = (ConsumerInfo)db.getObject("ConsumerInfo",cb.getCid());
 %>

<body bgcolor="#EBF5FD">
 	<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"/>
	<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td>
        <font color="#FFFFFF" size="5">客户退货管理</font>
        <font color="#FFFFFF" size="2">--退货修改</font>
      </td></tr>
	</table>
	<table>
	  <tr><td><a href="JavaScript:history.back()">
	    <img border="0" src="img/back.jpg"/></a>
	  </td></tr>
	</table>
	<hr color="black" size="1"/>
	<table width="100%" border="0" cellspacing="1" bgcolor="black">
	<caption>退货信息</caption>
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>表单号</th>
	    <th>客户名称</th>
	    <th>销售表号</th>
	  	<th>退货时间</th>	
	  </tr>
	  <tr bgcolor="white" align="center">
	     <td><%= cb.getCBid() %></td>
	     <td><%= new String(ci.getCname().getBytes(),"UTF-8") %></td>
	     <td><%= cb.getEid() %></td>
	     <td><%= cb.getCBdate().toString() %></td>
	  </tr>
	</table>
	<% 
		if(!list.isEmpty()){
	 %>
	<table width="100%" border="0" cellspacing="1" bgcolor="black">
	<caption>退货明细</caption>
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>商品名称</th>
	    <th>商品数量</th>
	    <th>商品售价</th>
	    <th>商品总价</th>
	    <th>修改</th>
	    <th>删除</th>
	  </tr>
	  <% 
	  	int i = 0;
	  	for(ConsumerBackDetail cbd:list){
		GoodsInfo gi = (GoodsInfo)db.getObject("GoodsInfo",cbd.getGid());
		if(i%2==0){
			i++;
			out.println("<tr bgcolor='white' align='center'>");
		}
		else{
			i++;
			out.println("<tr bgcolor='#EBF5FD' align='center'>");
		}
	   %>
	   	<form id="mfd<%= i %>" method="post" action="ManageServlet">
	   	<input type="hidden" name="action" value="modifyConsumerBackDetail"/>
	   	<input type="hidden" name="cbdid" value="<%= cbd.getCBDid() %>"/>
	  	<td><%= new String(gi.getGname().getBytes(),"UTF-8") %></td>
	  	<td><input name="cbdamount" id="amount<%= i %>" value="<%= cbd.getCBDamount() %>"/></td>
	  	<td><%= cbd.getCBDprice() %></td>
	  	<td><%= cbd.getCBDtotalprice() %></td>
	  	<td width="100"><a href="JavaScript:checkDetail(document.all.mfd<%= i %>,document.all.amount<%= i %>.value)"><img border="0" src="img/mod.gif" height="16" width="16"/>修改</a></td>
	  	<td width="100"><a href="JavaScript:delete_sure('ManageServlet?action=deleteConsumerBackDetail&cbdid=<%= cbd.getCBDid() %>&cbid=<%= cbd.getCBid() %>')"><img border="0" src="img/del.gif"/>删除</a></td>
	  	</form>
	  </tr> 
	  <% 
	  	}
	   %>
	</table>
	<% 
		}
	 %>
  </body>
</html>