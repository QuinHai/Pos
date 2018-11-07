<%@page import="java.util.logging.Logger"%>
<%@page import="jdk.nashorn.internal.runtime.logging.Loggable"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.quinhai.pos.beans.*, 
				 org.springframework.web.context.*, 
				 com.quinhai.pos.DBUtil.*, 
				 java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看/修改商品</title>
<script type="text/javascript src="script/trim.js"></script>
<script type="text/javascript">
	function check() {
		 var gunit = document.all.gunit.value.trim();		//得到商品单位
       	 var gpin = document.all.gpin.value.trim();			//得到商品进价
       	 var gpout = document.all.gpout.value.trim();		//得到商品售价
       	 var gamount = document.all.gamount.value.trim();	//得到商品数量
       	 
       	 var reg = /^([1-9][0-9]*(\.?[0-9]+)?)|(0\.[0-9]+)$/;//定义正则式
       	 var reg1 = /^[0-9]*$/;								//定义正则式,只匹配整数(除0外)
       	 
       	 if(gunit == ""){
       		 alert("商品单位不能为空！");
       		 return;
       	 }
       	 if(!reg.test(gpin)){
       		alert("商品进价格式不对!!!");
       	 	return;
       	 }
       	if(!reg.test(gpout)){								//当售价格式不对时
       	 	alert("商品售价格式不对!!!");
       	 	return;
       	 }
    	 if(!reg1.test(gamount)){							//库存格式错误
        	 	alert("商品数量格式不对!!!");
        	 	return;
        	 }
         document.all.mf.submit();
	}
</script>
</head>
<body bgcolor="#EBF5FD">
	<jsp:useBean id="userBean" class="com.quinhai.pos.beans.UserBean" scope="session"/>
	<table width="100%" height="44" bgcolor="#206AB3">
		<tr><td>
			<font color="#FFFFFF" size="5">商品资料管理</font>
			<font color="#FFFFFF" size="2">--商品修改</font>
		</td></tr>
	</table>
	<table>
		<tr>
		<td>
			<a href="ManageServlet?action=search&key=&type=goodsinfo" target="mainFrame">
			<img alt="back" src="img/back.jpg"> </a>
		</td>
		</tr>
	</table>
	<hr color="black" size="1">
	<%
		GoodsInfo gi = (GoodsInfo)request.getAttribute("object");
	 %>
	 <form action="ManageServlet" method="post" id="mf">
	 <table width="80%" border="0" cellspacing="1" bgcolor="black" align="center">	
	  <tr bgcolor="white">
	    <td align="center">商品名称:</td>
	    <td><%= new String(gi.getGname().getBytes(),"utf-8") %></td>
	  </tr>
	  <tr bgcolor="white">
	  	<td align="center">商品类别:</td>
	  	<td>
	  		<select name="gcname">
	  			<%
	  				WebApplicationContext wac = 
	  					WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	  				DBUtil db = (DBUtil)wac.getBean("dbutil");
	  				List<String> gcname = (List<String>)db.getGoodsClass();
	  				GoodsClassInfo gci = (GoodsClassInfo) db.getObject("GoodsClassInfo", gi.getGCid());
	  				for(String name:gcname){
	  					name = new String(name.getBytes(), "UTF-8");
	  					String flag ="";
	  					if(name.equals(new String(gci.getGCname().getBytes(), "UTF-8"))) {
	  						flag = "selected";
	  					}
	  			 %>
	  			 	<option value="<%= name%>" <%=flag%>><%= name %></option>
	  			 <%
	  			 	}
	  			 %>
	  		</select>
	  	</td>
	  </tr>
	  <tr bgcolor="white">
	  	<td align="center">计量单位:</td>
	  	<td><input name="gunit" id="gunit" 
	  		value="<%= new String(gi.getGunit().getBytes(),"UTF-8") %>"/></td>
	  </tr>
	  <tr bgcolor="white">
	  	<td align="center">进&nbsp;&nbsp;&nbsp;&nbsp;价:</td>
	  	<td><input name="gpin" id="gpin" value="<%= gi.getGpin() %>"/></td>
	  </tr>
	  <tr bgcolor="white">
	  	<td align="center">售&nbsp;&nbsp;&nbsp;&nbsp;价:</td>
	  	<td><input name="gpout" id="gpout" value="<%= gi.getGpout() %>"/></td>
	  </tr>
	  <tr bgcolor="white">
	  	<td align="center">商品数量:</td>
	  	<td><input name="gamount" id="gamount" value="<%= gi.getGamount() %>"/></td>
	  </tr>	
	 </table>
	 <table align="center">
	 	<tr>
	 		<td>
	 			<img alt="xg" src="img/xg.gif" id ="xg" onclick="javascript:check()"
	 				style="cursor:pointer"
	 				onmouseover="document.all.xg.src='img/xga.gif'"
          	  		onmouseout="document.all.xg.src='img/xg.gif'"
          	  		onmouseup="document.all.xg.src='img/xga.gif'"        	
          	  		onmousedown="document.all.xg.src='img/xgb.gif'"/>
	 		</td>
	 		<td>
	 			<img alt="cze" src="img/cze.gif" id="cz" onclick="javascript:document.all.mf.reset()"
	 				style="cursor:pointer"
	 				onmouseover="document.all.cz.src='img/czd.gif'"
          	  		onmouseout="document.all.cz.src='img/cze.gif'"
          	  		onmouseup="document.all.cz.src='img/czd.gif'"        	
          	  		onmousedown="document.all.cz.src='img/czc.gif'"/>
	 		</td>
	 	</tr>
	 </table>
	 <input type="hidden" name="action" value="modifyGoods"/>
	 <input type="hidden" name="gid" value="<%= gi.getGid() %>"/>
	 <input type="hidden" name="gname" value="<%= new String(gi.getGname().getBytes(),"UTF-8") %>"/>
	 </form>
</body>
</html>