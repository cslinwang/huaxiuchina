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
						<TD height=30>股票列表</TD>
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
						<TD height=30>**<a href="gpDeleteToday"><b>删除今日大盘信息</b></a>	**					
					</a></td>
					</TR>

				</TBODY>
			</TABLE>
		</DIV>
		<div class=tablezone>
			<form name="form" action="" method="post">

				<TABLE>
					<TBODY>
						<tr align="center" bgcolor="#ebf0f7">
							<td width="9%" height="25">日期</td>
							<td width="9%" height="25">代码</td>
							<td width="9%" height="25">名称</td>
							<td width="9%" height="25">最新</td>
							<td width="9%" height="25">最高</td>
							<td width="9%" height="25">最低</td>
							<td width="9%" height="25">开盘</td>
							<td width="9%" height="25">昨收</td>
							<td width="9%" height="25">k</td>
							<td width="9%" height="25">j</td>
							<td width="9%" height="25">操作</td>


						</tr>

						<c:forEach var="gp" items="${sessionScope.gplist }">
							<tr align='center' bgcolor='#FFFFFF'
								onmouseover='this.style.background="#F2FDFF"'
								onmouseout='this.style.background="#FFFFFF"'>
								<td height="24">${gp.date }</td>
								<td height="24">${gp.dm }</td>
								<td height="24">${gp.mc }</td>
								<td height="24">${gp.zx }</td>
								<td height="24">${gp.zg }</td>
								<td height="24">${gp.zd1 }</td>
								<td height="24">${gp.kp }</td>
								<td height="24">${gp.zs1 }</td>
								<td height="24">${gp.k }</td>
								<td height="24">${gp.j }</td>
								<%
									if (session.getAttribute("model").equals(0)) {
								%>
								<td><a href="gpUpdate?dm=${gp.dm }&date=${gp.date }">编辑</a>
								</td>
								<%
									}
								%>
							</tr>
						</c:forEach>
					</TBODY>
				</TABLE>
		</DIV>
	</div>
	</form>

</body>
</html>
