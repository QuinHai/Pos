<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.quinhai.pos.beans.*, com.quinhai.pos.DBUtil.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>明细查看</title>
</head>

<% 
	ProviderBack pb = (ProviderBack)request.getAttribute("pb");
	List<ProviderBackDetail> list = (List<ProviderBackDetail>)request.getAttribute("list");
	WebApplicationContext wac=
	   WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	DBUtil db = (DBUtil)wac.getBean("dbutil");
	ProviderInfo pi = (ProviderInfo)db.getObject("ProviderInfo",pb.getPid());
 %>

<body bgcolor="#EBF5FD">
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
	<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td>
        <font color="#FFFFFF" size="5">采购退货管理</font>
        <font color="#FFFFFF" size="2">--明细查看</font>
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
	    <th>供应商</th>
	    <th>采购表号</th>
	  	<th>退货时间</th>	
	  </tr>
	  <tr bgcolor="white" align="center">
	     <td><%= pb.getPBid() %></td>
	     <td><%= new String(pi.getPname().getBytes(),"UTF-8") %></td>
	     <td><%= pb.getSid() %></td>
	     <td><%= pb.getPBdate().toString() %></td>
	  </tr>
	</table>
	<% 
		if(!list.isEmpty()){
	%>
	<br/>
	<table width="100%" border="0" cellspacing="1" bgcolor="black">
	<caption>退货明细</caption>
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>商品名称</th>
	    <th>商品数量</th>
	    <th>商品进价</th>
	    <th>商品总价</th>
	  </tr>
	  <% 
	  	int i = 0;
	  	for(ProviderBackDetail pbd:list){
		GoodsInfo gi = (GoodsInfo)db.getObject("GoodsInfo",pbd.getGid());
		if(i%2==0){
			i++;
			out.println("<tr bgcolor='white' align='center'>");
		}
		else{
			i++;
			out.println("<tr bgcolor='#EBF5FD' align='center'>");
		}
	   %>
	  	<td><%= new String(gi.getGname().getBytes(),"UTF-8") %></td>
	  	<td><%= pbd.getPBDamount() %></td>
	  	<td><%= pbd.getPBDprice() %></td>
	  	<td><%= pbd.getPBDtotalprice() %></td>
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