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

<title>大学英语在线练习后台管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<frameset rows="63,*" cols="*" frameborder="no" border="0"
	framespacing="0">
	<frame src="header.jsp" name="topFrame" id="topFrame" scrolling="no"
		noresize />
	<frameset cols="176,*" name="bodyFrame" id="bodyFrame" frameborder="no"
		border="0" framespacing="0">
		<frame src="menu.jsp" name="menu" id="menu" scrolling="auto" noresize />
		<frame src="" name="main" id="main" scrolling="auto" noresize />

	</frameset>
</frameset>
<body>

	<noframes>
		<body>你的浏览器不支持框架！
		</body>
	</noframes>
</body>
</html>
