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
<title>客户退货管理</title>
<link rel=stylesheet href="css/general.css" type="text/css">
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
function check(){
  	var key = document.all.key.value.trim();
  	if(key==""){
		alert("关键字为空,请重新输入!!!");
		return;
  	}
  	document.all.smf.submit();
  }
  function checkPage(temp){
  	var page = document.all.page.value.trim();
		var reg = /^[1-9][0-9]*$/;
	if((reg.test(page.trim()))&&(page<=temp)){
		document.all.mf.submit();
	}
	else{
		alert("输入不合法,请重新输入!!!");
		return;
	}
  }
</script>
</head>

<% 
	List<ConsumerBack> list = (List<ConsumerBack>)request.getAttribute("goodslist");
		WebApplicationContext wac=
		   WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		DBUtil db = (DBUtil)wac.getBean("dbutil");
 %>

<body bgcolor="#EBF5FD">
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
	<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td><font color="#FFFFFF" size="5">客户退货管理</font></td></tr>
	</table>
	
	<form action="ManageServlet" method="post" id="smf">
	<table>
	  <tr>	    
		<td>
		<table height="42" style="background:url(img/add_sear.jpg) no-repeat">
		  <tr>
		    <td>
		      &nbsp;<img src="img/log.gif" border="0" style="cursor:hand" onclick="document.all.key.focus()"/>
		    </td>
		    <td>
		      <input name="key" id="key" value="请输入要搜索的表单号" style="border:0;width:210px"
		      		 size="28"	onfocus="document.all.key.value=''"/>		    
		    </td>
		    <td width="85" align="right">
		    <img src="img/sear.jpg" id="mg" border="0"
		      style="cursor:hand"
		      onclick="JavaScript:check()"
		      onmousedown="document.all.mg.src='img/sear1.jpg'"
		      onmouseup="document.all.mg.src='img/sear.jpg'"/>
		    </td>
	    	<td width="90" align="center"><a href="addconsumerback.jsp" target="mainFrame"><font color="white" size="2">添加退货</font></a></td>
		  </tr>
		</table>
	    </td>
	    <td>
	      <input type="hidden" name="action" value="search" />
	      <input type="hidden" name="type" value="consumerback"/>
	    </td>	   
	  </tr>
	  </table>
	</form>

	<hr size="1" width="100%" color="black"/>
  
	<% 
		if(list.isEmpty()){
			out.println("<br/><br/><br/><center><h2>没有搜索到你要的退货表!!!</h2></center>");
		}
		else{
	 %>	
	<table width="100%" border="0" cellspacing="1" bgcolor="black">
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>表单号</th>
	    <th>客户名称</th>
	    <th>销售表号</th>
	  	<th>退货时间</th>
	  	<th>查看</th>
	  	<th>修改</th>
	  	<th>删除</th>
	  	<th>添加明细</th>
	  </tr>
	  <% 
	  	int i = 0;
		for(ConsumerBack cb:list){
		ConsumerInfo ci = (ConsumerInfo)db.getObject("ConsumerInfo",cb.getCid());
		if(i%2==0){
			i++;
			out.println("<tr bgcolor='white' align='center'>");
		}
		else{
			i++;
			out.println("<tr bgcolor='#EBF5FD' align='center'>");
		}
	   %>
	     <td><%= cb.getCBid() %></td>
	     <td><%= new String(ci.getCname().getBytes(),"UTF-8") %></td>
	     <td><%= cb.getEid() %></td>
	     <td><%= cb.getCBdate().toString() %> </td>
	     <td width="60"><a href="ManageServlet?action=lookConsumerBack&cbid=<%= cb.getCBid() %>&type=look" target="mainFrame"><img border="0" src="img/file.gif"/>查看</a></td>
	     <td width="60"><a href="ManageServlet?action=lookConsumerBack&cbid=<%= cb.getCBid() %>&type=modify" target="mainFrame"><img border="0" src="img/mod.gif" height="16" width="16"/>修改</a></td>
	     <td width="60"><a href="JavaScript:delete_sure('ManageServlet?action=deleteConsumerBack&cbid=<%= cb.getCBid() %>')" target="mainFrame"><img border="0" src="img/del.gif"/>删除</a></td>
	     <td width="100"><a href="ManageServlet?action=lookConsumerBack&cbid=<%= cb.getCBid() %>&type=lookdetail" target="mainFrame"><img border="0" src="img/det.gif"/>添加明细</a></td>
	  </tr>
	  <% 
	  	}
	   %>
	</table>  
 	<table width="100%">
	<form method="post" action="ManageServlet" id="mf">
	  <tr>
	    <td align="left">
	      <font size="2">共<%= userBean.getTotalPage() %>页&nbsp;&nbsp;当前页:<%= userBean.getNowPage() %></font>
	    </td>
	    <td align="right">
	      <% 
	      	if(userBean.getNowPage()>1){
	       %>
	      <a href="ManageServlet?action=changePage&pagename=/consumerbackmanage.jsp&page=<%= userBean.getNowPage()-1 %>" target="mainFrame"><img src="img/prev.gif" border="0"/></a>
	      <% 
	      	}
	      	if(userBean.getNowPage()<userBean.getTotalPage()){
	       %>	       
	      <a href="ManageServlet?action=changePage&pagename=/consumerbackmanage.jsp&page=<%= userBean.getNowPage()+1 %>" target="mainFrame"><img src="img/next.gif" border="0"/></a>
	      <% 
	      	}
	      	else{
	      		out.println("<img src='img/next.gif' style='visibility:hidden'/>");
	      	}
	       %>
	      <font size="2">第<input name="page" id="page" size="2" value="<%= userBean.getNowPage() %>" onfocus="document.all.page.value=''"/>页</font>
	      <input type="hidden" name="action" value="changePage" />
	      <input type="hidden" name="pagename" value="/consumerbackmanage.jsp"/>
	    </td>
	    <td width="10">
	      <img src="img/go.gif" border="0" style="cursor:pointer" onclick="JavaScript:checkPage(<%= userBean.getTotalPage() %>)"/>
	    </td>
	  </tr>	
	</form>
	</table>
  	<% 
  		}
  	 %>

</body>
</html>