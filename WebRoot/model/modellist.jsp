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
<link href="./images/style.css" type=text/css rel=stylesheet>
<link rel="stylesheet" type="text/css" media="screen"
	href="./css/tinyTips.css" />
</head>

<body>
	<div class=formzone>
		<DIV class=searchzone>
			<TABLE height=30 cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
					<TR>
						<TD height=30>模型列表</TD>
						<TD align=right colSpan=2>&nbsp;</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
			<DIV class=searchzone>
			<TABLE height=30 cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
					<TR>
						<TD height=30><b>操作</b></TD>
					</TR>
					<TR>
						<td>**<a
						href="daydealDownloadAction?name=${sessionScope.user.name }"><b>下载本日交易指南</b>
					</a>**</td>
					</TR>

				</TBODY>
			</TABLE>
		</DIV>
		<div class=tablezone>
			<table>
				<tr align="center" bgcolor="#ebf0f7">
					<td width="9%" height="25">证券代码</td>
					<td width="9%" height="25">模型阶段</td>
					<td width="9%" height="25">平均价格</td>
					<td width="9%" height="25">建仓基数</td>
					<td width="9%" height="25">建仓数量</td>
					<td width="9%" height="25">操作</td>
				</tr>
				<c:forEach var="modellist" items="${modellist }">
					<tr align='center' bgcolor='#FFFFFF'
						onmouseover='this.style.background="#F2FDFF"'
						onmouseout='this.style.background="#FFFFFF"'>

						<td height="24">${modellist.dm }</td>
						<td height="24">${modellist.model }</td>
						<td height="24">${modellist.price }</td>
						<td height="24">${modellist.base }</td>
						<td height="24">${modellist.sum }</td>
						<td><a href="modelUpdate?mid=${modellist.mid }">编辑</a>
						<%--| <a href="gpUpdate?dm=${gp.dm }&date=${gp.date }">删除</a> --%>
						</td>
					</tr>
				</c:forEach>
				<tr align="center" bgcolor="#ebf0f7">
					
				</tr>
				<DIV class=searchzone></DIV>
			</table>
		</div>
	</div>
</body>
</html>
