<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加类别</title>
<script type="text/javascript" src="script/trim.js"></script>
<script type="text/javascript">
	function check() {
		var gcname = document.all.gcname.value.trim();
		if(gcname==""){
			alert("类别名称不能为空!!!");
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
			<font color="#FFFFFF" size="5">商品类别管理</font>
        	<font color="#FFFFFF" size="2">--类别添加</font>
	</td></tr>
</table>
<table>
	<tr><td>
		<a href="javascript:history.back()">
		<img alt="back" src="img/back.jpg"> </a>
	</td></tr>
</table>
<hr color="black" size="1"/>
<form action="ManageServlet" method="post" id="mf">
<table width="8-%" border="0" cellspacing="1" bgcolor="black" align="center">
	<tr bgcolor="white">
	 <td align="center">类别名称</td>
	 <td><input size="20" name="gcname" id="gcname"/> </td>
	</tr>
</table>
<br/>
<table align="left" width="70%">
	<tr>
	 <td align="right">
	    <img border="0" src="img/xg.gif" id="xg" onclick="JavaScript:check()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.xg.src='img/xga.gif'"
          	  onmouseout="document.all.xg.src='img/xg.gif'"
          	  onmouseup="document.all.xg.src='img/xga.gif'" 
          	  onmousedown="document.all.xg.src='img/xgb.gif'"/>
	 </td>
	 <td align="left">
	 	 <img border="0" src="img/cze.gif" id="cz" onclick="JavaScript:document.all.mf.reset()"
          	  style="cursor:pointer"
          	  onmouseover="document.all.cz.src='img/czd.gif'"
          	  onmouseout="document.all.cz.src='img/cze.gif'"
          	  onmouseup="document.all.cz.src='img/czd.gif'"        	
          	  onmousedown="document.all.cz.src='img/czc.gif'"/>
	 </td>
	</tr>
</table>
<input type="hidden" name="action" value="addGoodsClass"/>
</form>
</body>
</html>