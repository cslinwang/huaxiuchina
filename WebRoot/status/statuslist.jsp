<%@ page language="java" import="java.util.*,java.text.DecimalFormat"
	pageEncoding="utf-8"%>
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
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
</head>
<%
	java.text.DecimalFormat df = new DecimalFormat("#.00"); // 格式化double
%>
<body>
	<div class=formzone>
		<DIV class=searchzone>
			<TABLE height=30 cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
					<TR>
						<TD height=30>做T列表</TD>
						<TD align=right colSpan=2>&nbsp;</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
		<DIV class=searchzone>
			<TABLE height=30 cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
					<TR>
						<TD height=30><b>操作</b>
						</TD>
					</TR>

					<TR>
						<td>**<a
							href="tDownloadAction?name=${sessionScope.user.name }"><b>下载本日做T情况</b>
						</a>**</td>
					</TR>


				</TBODY>
			</TABLE>
		</DIV>
		<div class=tablezone>
			<table>
				<tr align="center" bgcolor="#ebf0f7">
					<td width="9%" height="25">交易日期</td>
					<td width="9%" height="25">证券代码</td>
					<td width="9%" height="25">证券名称</td>
					<td width="9%" height="25">交易方向</td>
					<td width="9%" height="25">平均价格</td>
					<td width="9%" height="25">交易数量</td>
					<td width="9%" height="25">交易金额</td>
					<td width="9%" height="25">收益</td>
				</tr>
				<c:forEach var="statuslist" items="${statuslist }">
					<tr align='center' bgcolor='#FFFFFF'
						onmouseover='this.style.background="#F2FDFF"'
						onmouseout='this.style.background="#FFFFFF"'>
						<td height="24">${statuslist.date }</td>
						<td height="24">${statuslist.dm }</td>
						<td height="24">${statuslist.mc }</td>
						<td height="24">${statuslist.fc }</td>
						<td height="24">${statuslist.jg }</td>
						<td height="24">${statuslist.sl }</td>
						<td height="24"><fmt:formatNumber
								value=" ${statuslist.sl*statuslist.jg }" pattern="##.##"
								minFractionDigits="2"></fmt:formatNumber>
						</td>
						<td height="24">${statuslist.sy }</td>
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
