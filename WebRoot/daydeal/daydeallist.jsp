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
			<td width="9%" height="25">日期 </td>
			<td width="9%" height="25">交易类别 </td>
			<td width="9%" height="25">证券代码 </td>
			<td width="9%" height="25">证券名称 </td>
			<td width="9%" height="25">成交价格 </td>
			<td width="9%" height="25">成交数量 </td>
			<td width="9%" height="25">模型阶段 </td>
			<td width="9%" height="25">操作账户 </td>
			<td width="9%" height="25">建仓基数 </td>
			<td width="9%" height="25">持有总量 </td>						
		</tr>
		<c:forEach var="daydeallist" items="${daydeallist }">
			<tr align='center' bgcolor='#FFFFFF'
				onmouseover='this.style.background="#F2FDFF"'
				onmouseout='this.style.background="#FFFFFF"'>
				<td height="24">${daydeallist.date }</td>
				<td height="24">${daydeallist.mmbz }</td>
				<td height="24">${daydeallist.dm }</td>
				<td height="24">${daydeallist.mc }</td>
				<td height="24">${daydeallist.cjjg }</td>
				<td height="24">${daydeallist.cjsl }</td>
				<td height="24">${daydeallist.model }</td>
				<td height="24">${daydeallist.username }</td>
				<td height="24">${daydeallist.base }</td>
				<td height="24">${daydeallist.sum }</td>
				
			</tr>
		</c:forEach>
		<tr align="center" bgcolor="#ebf0f7"><a href="daydealDownloadAction">下载本日交易指南</a></tr>
	</table>
</body>
</html>
