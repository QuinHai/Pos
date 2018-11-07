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
<title>明细添加</title>
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
function check(){
  	if(document.all.gname.value.trim()==""){
  		alert("商品名称不能为空,请添加商品!!!");
  		return false;
  	}
  	var reg = /^[1-9][0-9]*$/;
  	if(!reg.test(document.all.cbdamount.value.trim())){
  	  	alert("数量格式不对,请重新输入!!!");
  		return false;
  	}
  	document.all.mf.submit();
  }
</script>
</head>

<% 
	ConsumerBack cb = (ConsumerBack)request.getAttribute("cb");
	List<ConsumerBackDetail> list = (List<ConsumerBackDetail>)request.getAttribute("list");
	WebApplicationContext wac=
	   WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	DBUtil db = (DBUtil)wac.getBean("dbutil");
	ConsumerInfo ci = (ConsumerInfo)db.getObject("ConsumerInfo",cb.getCid());
 %>

<body bgcolor="#EBF5FD">
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
	<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td>
        <font color="#FFFFFF" size="5">客户退货管理</font>
        <font color="#FFFFFF" size="2">--明细添加</font>
      </td></tr>
	</table>
	<table>
	  <tr><td><a href="ManageServlet?action=changePage&page=<%= userBean.getNowPage() %>&pagename=/consumerbackmanage.jsp">
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
	     <td><%= cb.getCBdate().toString() %> </td>
	  </tr>
	</table>
	<form method="post" action="ManageServlet" id="mf">
	<font color="red" size="3">请在下表添加退货商品及数量.</font>
	<table width="100%" border="0" cellspacing="1" bgcolor="black">
	<caption>退货明细</caption>
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>商品名称</th>
	    <th>商品数量</th>
	    <th>添加</th>
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
	  	<td><%= new String(gi.getGname().getBytes("ISO-8859-1"),"gbk") %></td>
	  	<td><%= cbd.getCBDamount() %></td>
	  	<td>--</td>
	  </tr> 
	  <% 
	  	}
	   %>
	  <tr bgcolor="white" align="center">
	    <td>
      	  <select name="gname" id="gname">
      	  <% 
      	  	String hql = "SELECT gi.Gname FROM GoodsInfo AS gi "+
      	  			"WHERE gi.Gid IN(SELECT sd.Gid FROM SellDetail "+
      	  			"AS sd WHERE sd.Eid='"+cb.getEid()+"')";
			List<String> gname = (List<String>)db.getInfo(hql);
			for(String name:gname){
				name = new String(name.getBytes(),"UTF-8");
      	    %>
      	    	<option value="<%= name %>"><%= name %></option>
      	    <% 
      	    	}
      	     %>
      	  </select>
	    </td>
	    <td><input name="cbdamount" id="cbdamount"/></td>
	    <td><img border="0" src="img/tj.gif" id="tj" onclick="JavaScript:check()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.tj.src='img/tja.gif'"
          	  onmouseout="document.all.tj.src='img/tj.gif'"
          	  onmouseup="document.all.tj.src='img/tja.gif'"        	
          	  onmousedown="document.all.tj.src='img/tjb.gif'"/></td>
	  </tr>
	</table>
		<input type="hidden" name="action" value="addConsumerBackDetail"/>
	 	<input type="hidden" name="cbid" value="<%= cb.getCBid() %>"/>
	</form>
</body>
</html>