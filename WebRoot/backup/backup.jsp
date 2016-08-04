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

<title>My JSP 'gplist.jsp' starting page</title>

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
						<TD height=30>备份</TD>
						<TD align=right colSpan=2>&nbsp;</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
		<div class=tablezone>
			<form name="form" action="" method="post">
				<TABLE>
				<!-- <tr><td>一键备份交易记录</td></tr> -->
				<tr><td><a href="ModelBackup?name=${user.name }">一键备份账号模型</a></td></tr>
				</TABLE>
		</DIV>
	</div>
	</form>

</body>
</html>
