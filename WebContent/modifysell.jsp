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
<title>销售修改</title>
<link rel=stylesheet href="css/general.css" type="text/css">
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
function checkInfo(){
  	var date = document.all.edate.value.trim();
  	reg = /^\d{4}-((0?[1-9])|(1[0-2]))-((0?[1-9])|([1-2][0-9])|(3[0-1]))$/
  	if(!reg.test(date)){
  		alert("日期格式不对,应为yyyy-mm-dd");
  		return;
  	}
  	if(document.all.eseller.value.trim()==""){
  		alert("销售人不能为空!!!");
  		return;
  	}    	
  	alert("=====成功=====");
  	document.all.mf.submit();
  }
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
	SellInfo ei = (SellInfo)request.getAttribute("ei");
	List<SellDetail> list = (List<SellDetail>)request.getAttribute("list");
	WebApplicationContext wac = WebApplicationContextUtils.
		getWebApplicationContext(this.getServletContext());
	DBUtil db = (DBUtil)wac.getBean("dbutil");
	ConsumerInfo ci = (ConsumerInfo)db.getObject("ConsumerInfo",ei.getCid());
%>

<body>
<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean"></jsp:useBean>
	<table width="100%" height="44" bgcolor="#206AB3">
      <tr align="center"><td>
        <font color="#FFFFFF" size="5">销售信息管理</font>
        <font color="#FFFFFF" size="2">--销售修改</font>
      </td></tr>
	</table>
	<table>
	  <tr><td><a href="ManageServlet?action=changePage&page=<%= userBean.getNowPage() %>&pagename=/sellmanage.jsp">
	    <img border="0" src="img/back.jpg"/></a>
	  </td></tr>
	</table>
	<hr color="black" size="1"/>
	<form action="ManageServlet" method="post" id="mf">
	<table width="100%" border="0" cellspacing="1" bgcolor="black">
	  <caption>销售信息</caption>
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>表单号</th>
	    <th>客户名称</th>
	    <th>销售日期</th>
	  	<th>总价</th>
	  	<th>销售人</th>
	  </tr>
	  <tr bgcolor="white" align="center">
	   <td><%=ei.getEid() %></td>
	   <td>
	   	<select name="cname">
		<%
			List<String> cname = db.getConsumer();
			for(String name:cname){
				String flag ="";
				if(name.equals(ci.getCname())){
					flag = "selected";
				}
				name = new String(name.getBytes(), "UTF-8");
		%>
		<option value="<%=name %>" <%=flag %>><%=name %></option>
		<%
			}
		%>	  	
	  	</select>
	   </td>
	   <td><input name="edate" id="edate" value="<%= ei.getEdate().toString() %>"/></td>
	   <td><%= ei.getEtotalprice() %></td>
	   <td><input name="eseller" id="eseller" value="<%= new String(ei.getEseller().getBytes(),"UTF-8") %>" /></td>
	  </tr>
	</table>
	<table align="center">
	 <tr>
	 	<td><img border="0" src="img/xg.gif" id="xg" onclick="JavaScript:checkInfo()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.xg.src='img/xga.gif'"
          	  onmouseout="document.all.xg.src='img/xg.gif'"
          	  onmouseup="document.all.xg.src='img/xga.gif'"        	
          	  onmousedown="document.all.xg.src='img/xgb.gif'"/></td>
	    <td><img border="0" src="img/cze.gif" id="cz" onclick="JavaScript:document.all.mf.reset()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.cz.src='img/czd.gif'"
          	  onmouseout="document.all.cz.src='img/cze.gif'"
          	  onmouseup="document.all.cz.src='img/czd.gif'"        	
          	  onmousedown="document.all.cz.src='img/czc.gif'"/></td>
	 </tr>
	</table>
	  <input type="hidden" name="action" value="modifySell"/>
	  <input type="hidden" name="eid" value="<%= ei.getEid() %>"/>
	</form>
	<%
		if(!list.isEmpty()){
	%>
	<table width="100%" border="0" cellspacing="1" bgcolor="black">
	  <caption>销售明细</caption>
	  <tr bgcolor="#D1F1FE" align="center">
	    <th>商品名称</th>
	    <th>商品数量</th>
	    <th>商品单价</th>
	    <th>商品总价</th>
	    <th>修&nbsp;&nbsp;改</th>
	    <th>删&nbsp;&nbsp;除</th>
	  </tr>	
	  <%
	  	int i = 0;
	  	for(SellDetail ed:list){
		GoodsInfo gi = (GoodsInfo)db.getObject("GoodsInfo",ed.getGid());
		if(i%2==0){
			out.println("<tr bgcolor='white' align='center'>");
		}
		else{
			out.println("<tr bgcolor='#EBF5FD' align='center'>");
		}
		i++;
	  %>
	   <form id="mfd<%= i%>" method="post" action="ManageServlet">
	    <input type="hidden" name="action" value="modifySellDetail"/>
	   	<input type="hidden" name="edid" value="<%= ed.getEDid() %>"/>
	  	<td><%= new String(gi.getGname().getBytes(),"UTF-8") %></td>
	  	<td><input name="edamount" id="amount<%= i %>" value="<%= ed.getEDamount() %>"/></td>
	  	<td><%= ed.getEDprice() %></td>
	  	<td><%= ed.getEDtotalprice() %></td>
	  	<td width="100">
	  		<a href="JavaScript:checkDetail(document.all.mfd<%= i %>,document.all.amount<%= i %>.value)">
	  			<img border="0" src="img/mod.gif" height="16" width="16"/>修改</a></td>
	  	<td width="100">
	  		<a href="JavaScript:delete_sure('ManageServlet?action=deleteSellDetail&edid=<%= ed.getEDid() %>&eid=<%= ed.getEid() %>')">
	  	<img border="0" src="img/del.gif"/>删除</a></td>
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