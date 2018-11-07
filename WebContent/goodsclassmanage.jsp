<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.quinhai.pos.beans.GoodsClassInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品类别管理</title>
<link rel="stylesheet" href="css/general.css" type="text/css">
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
<%
	List<GoodsClassInfo> list = (List<GoodsClassInfo>)request.getAttribute("goodslist");
%>
</head>
<body bgcolor="#EBF5FD">
	<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"></jsp:useBean>
	<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td><font color="#FFFFFF" size="5">商品类别管理</font></td></tr>
	</table>
	
	<form action="ManageServlet" method="post" id="smf">
	<table>
	<tr>
	<td>
	<table height="42" style="background:url(img/add_sear.jpg) no-repeat">
		<tr>
			<td>
			 <img alt="log" src="img/log.gif" style="cursor:pointer" 
				  onclick="document.all.key.focus()" />
			</td>
			<td>
			 <input name="key" id="key" value="请输入类别名称" style="border: 0; width: 210px" size="28" 
					onfocus="document.all.key.value=''"/>
			</td>
			<td width="85" align="right">
			 <img alt="search" src="img/sear.jpg" id="mg" style="cursor:pointer;"
			 	onclick="JavaScript:check()"
			 	onmousedown="document.all.mg.src='img/sear1.jpg'"
			 	onmouseup="document.all.mg.src='img/sear.jpg'"/>
			</td>
			<td width="90" align="center">
			 <a href="addgoodsclass.jsp" target="mainFrame">
			 	<font size="2" color="white">添加类别</font></a>			
			</td>
		</tr>
		</table>
		</td>
		<td>
			<input type="hidden" name="action" value="search">
			<input type="hidden" name="type" value="goodsclassinfo">
		</td>
		</tr>
	</table>
	</form>
	<!-- 横隔线 -->
	<hr size="1" width="100%" color="black"/>
	<%
		if(list.isEmpty()){
			out.println("<br/><br/><br/><center><h2>没有搜索到你要找的类别!!!</h2></center>");
		}else {
	%>
	<table align="center" width="80%" border="0" cellspacing="1" bgcolor="black">
		<tr bgcolor="#D1F1FE" align="center">
			<th>类别名称</th>
	  		<th>查看/修改</th>
	  		<th>删除</th>
		</tr>
		<%
			int i = 0;
			for(GoodsClassInfo gci:list){
				if(i%2 == 0){
					out.println("<tr bgcolor='white' align='center'>");
				}else {
					out.println("<tr bgcolor='#EBF5FD' align='center'>");
				}
				i++;
		%>
			<td><%= new String((gci.getGCname()).getBytes(),"UTF-8") %></td>
			<td width="33%"><a href="ManageServlet?action=lookGoodsClass&gcid=<%= gci.getGCid() %>" target="mainFrame">
				<img alt="mod" src="img/mod.gif" height="16" width="16"/>查看/修改</a></td>
			<td width="33%"><a href="JavaScript:delete_sure('ManageServlet?action=deleteGoodsClass&gcid=<%= gci.getGCid() %>')" target="mainFrame">
				<img alt="del" src="img/del.gif"/>删除</a></td>
		</tr>
		<%
			} 
		%>
	</table>
	<form method="post" id="mf" action="ManageServlet">
	<table width="100%">
		<tr>
		 <td align="left">
		  <font size="2">共<%= userBean.getTotalPage() %>页&nbsp;&nbsp;当前页：<%= userBean.getNowPage() %>></font>
		 </td>
		 <td align="right">
		 	<%
		 		if(userBean.getNowPage()>1){
		 	%>
		 	<a href="ManageServlet?action=changePage&pagename=/goodsclassmanage.jsp&page=<%= userBean.getNowPage()-1 %>" target="mainFrame">
		 	 <img alt="prev" src="img/prev.gif"></a>
		 	<%
		 		}
		 		if(userBean.getNowPage() < userBean.getTotalPage()){
		 	%>
		 	<a href="ManageServlet?action=changePage&pagename=/goodsclassmanage.jsp&page=<%= userBean.getNowPage()+1 %>" target="mainFrame">
		 	 <img alt="next" src="img/next.gif"></a>
		 	<%
		 		}
		 		else{
		 			out.println("<img src='img/next.jpg' style='visibility:hidden'>");
		 		}
		 	%>
		 	<font size="2">第<input name="page" id="page" value="<%= userBean.getNowPage() %>" size="2" onfocus="document.all.page.value=''">页</font>
		 	<input type="hidden" name="action" value="changePage"/>
		 	<input type="hidden" name="pagename" value="/goodsclassmanage.jsp"/>
		 </td>
		 <td>
		 <img alt="go" src="img/go.gif" style="cursor:pointer" 
		 	onclick="JavaScript:checkPage(<%= userBean.getTotalPage() %>)">
		 </td>
		</tr>
	</table>
	</form>
	<%
		}
	%>	
</body>
</html>