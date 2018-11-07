<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.quinhai.pos.beans.*" %>
<%@ page import="com.quinhai.pos.DBUtil.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购查看</title>
</head>

<%
	StockInfo si = (StockInfo)request.getAttribute("si");
	List<StockDetail> list = (List<StockDetail>)request.getAttribute("list");
	WebApplicationContext wac = WebApplicationContextUtils.
			getWebApplicationContext(this.getServletContext());
	DBUtil db = (DBUtil)wac.getBean("dbutil");
	ProviderInfo pi = (ProviderInfo)db.getObject("ProviderInfo", si.getPid());
%>

<body bgcolor="#EBF5FD">
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
	<table width="100%" height="44" bgcolor="#206AB3">
  		<tr align="center"><td>
        	<font color="#FFFFFF" size="5">采购信息管理</font>
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
	  <caption>采购信息</caption>
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>表单号</th>
	    <th>供应商</th>
	    <th>采购日期</th>
	  	<th>总价</th>
	  	<th>采购人</th>
	  </tr>
	  <tr bgcolor="white" align="center">
	    <td><%=si.getSid() %></td>
	    <td><%=new String(pi.getPname().getBytes(), "UTF-8") %></td>
	    <td><%=si.getSdate().toString() %></td>
	    <td><%=si.getStotalprice() %></td>
	    <td><%=new String(si.getSbuyer().getBytes(), "UTF-8") %></td>
	  </tr>
	</table>
	<%
		if(!list.isEmpty()){
	%>
	<br/>
	<table>
	  <caption>采购明细</caption>
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>商品名称</th>
	    <th>商品数量</th>
	    <th>商品单价</th>
	    <th>商品总价</th>
	  </tr>
	  <%
	  	int i  = 0;
	  for(StockDetail sd:list) {
	  	GoodsInfo gi = (GoodsInfo)db.getObject("GoodsInfo", sd.getGid());
	  	if(i % 2 == 0){
			out.println("<tr bgcolor='white' align='center'>");
	  	}else {
			out.println("<tr bgcolor='#EBF5FD' align='center'>");
	  	}
	  	i++;
	  %>
	   <td><%=new String(gi.getGname().getBytes(), "UTF-8") %></td>
	   <td><%=sd.getSDamount() %></td>
	   <td><%=sd.getSDprice() %></td>
	   <td><%=sd.getSDtotalprice() %></td>
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