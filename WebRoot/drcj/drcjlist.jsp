<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'drcjlist.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
</head>

<body>
	<table>
		<tr align="center" bgcolor="#ebf0f7">
			<td width="9%" height="25">日期 
</td>
			<td width="9%" height="25">交易类别 
</td>
			<td width="9%" height="25">证券代码 
</td>
			<td width="9%" height="25">证券名称 
</td>
			<td width="9%" height="25">成交价格 
</td>
			<td width="10%">操作</td>
		</tr>
		<c:forEach var="drcj" items="${decjList }">
			<tr align='center' bgcolor='#FFFFFF'
				onmouseover='this.style.background="#F2FDFF"'
				onmouseout='this.style.background="#FFFFFF"'>
				<td height="24">${drcj.rq }</td>
				<td height="24">${drcj.jylb }</td>
				<td height="24">${drcj.zqdm }</td>
				<td height="24">${drcj.zqmc }</td>
				<td height="24">${drcj.cjjg }</td>
				<td><a href="chengjiupdate.jsp?cjid=${cj.cjid }">编辑</a>| <a
					href="chengjidelete.jsp?cjid=${cj.cjid }"
					onClick="javascript:if(confirm('是否删除')){return
									true;}else{return false;}">删除</a>|<a
					href="chengjidetail.jsp?cjid=${cj.cjid }">查看</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
