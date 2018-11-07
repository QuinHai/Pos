<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.quinhai.pos.beans.*, com.quinhai.pos.DBUtil.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购明细</title>
<script type="text/javascript" src= "script/trim.js"></script>
<script type="text/javascript">
function check(){
  	if(document.all.gname.value.trim()==""){
  		alert("商品名称不能为空,请添加商品!!!");
  		return false;
  	}
  	var reg = /^[1-9][0-9]*$/;
  	if(!reg.test(document.all.sdamount.value.trim())){
  	  	alert("数量格式不对,请重新输入!!!");
  		return false;
  	}
  	document.all.mf.submit();
  }
</script>
</head>

<%
	StockInfo si = (StockInfo)request.getAttribute("si");
	List<StockDetail> list = (List<StockDetail>) request.getAttribute("list");
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
        <font color="#FFFFFF" size="2">--明细添加</font>
      </td></tr>
	</table>
	<table>
	 <tr><td>
	 <a href="ManageServlet?action=changePage&page=<%=userBean.getNowPage()%>&pagename=/stockmanage.jsp">
	  <img alt="back" src="img/back.jpg"></a>
	 </td></tr>
	</table>
	<hr color="black" size="1">
	<table>
	 <caption>采购信息</caption>
	 <tr bgcolor="#D1F1FE" align="center">
	 	<th>表单号</th>
	    <th>供应商</th>
	    <th>采购日期</th>
	  	<th>总价</th>
	  	<th>采购人</th>
	 </tr>
	 <tr>
	 	<td><%=si.getSid() %></td>
	 	<td><%=new String(pi.getPname().getBytes(), "UTF-8") %></td>
	 	<td><%=si.getSdate().toString() %></td>
	 	<td><%=si.getStotalprice() %></td>
	 	<td><%=new String(si.getSbuyer().getBytes(), "UTF-8") %></td>
	 </tr>
	</table>
	<form action="ManageServlet" method="post" id="mf">
		<font color="red" size="3">请在下表添加采购商品.</font>
		<table width="100%" border="0" cellspacing="1" bgcolor="black">
		 <caption>采购明细</caption>
		 <tr bgcolor="#D1F1FE" align="center">
	   		<th>商品名称</th>
	    	<th>商品数量</th>
	    	<th>添加</th>
	  	 </tr>
	  	 <%
	  	  int i = 0;
	  	  for(StockDetail sd:list) {
	  		  GoodsInfo gi = (GoodsInfo)db.getObject("GoodsInfo", sd.getGid());
	  		  if(i%2==0){
	  			  out.println("<tr bgcolor='white' align='center'>");
	  		  }else{
	  			  out.println("<tr bgcolor='#EBF5FD' align='center'>");
	  		  }
	  		  i++;
	  	 %>
	  	 <td><%=new String(gi.getGname().getBytes(), "UTF-8") %></td>
	  	 <td><%=sd.getSDamount() %></td>
	  	 <td>--</td>
	  	 </tr>
	  	 <%  
	  	 	}
	  	 %>
	  	 <tr bgcolor="white" align="center">
	  	 	<td>
	  	 	 <select name="gname" id="gname">
	  	 	 <%
	  	 	 	List<String> gname = db.getGoods();
	  	 	 	for(String name:gname){
	  	 	 %>
	  	 	 	<option value="<%=name %>"><%=name %> </option>
	  	 	 <%
	  	 	 	}
	  	 	 %>
	  	 	 </select>
	  	 	</td>
	  	 	<td><input name="sdamount" id="sdamount"/></td>
	    	<td><img border="0" src="img/tj.gif" id="tj" onclick="JavaScript:check()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.tj.src='img/tja.gif'"
          	  onmouseout="document.all.tj.src='img/tj.gif'"
          	  onmouseup="document.all.tj.src='img/tja.gif'"        	
          	  onmousedown="document.all.tj.src='img/tjb.gif'"/></td>
	  	 </tr>
		</table>
		<input type="hidden" name="action" value="addStockDetail"/>
	  	<input type="hidden" name="sid" value="<%= si.getSid() %>"/>	
	</form>
</body>
</html>