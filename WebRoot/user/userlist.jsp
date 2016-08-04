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
						<TD height=30>用户列表</TD>
						<TD align=right colSpan=2>&nbsp;</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
		<div class=tablezone>
			<form name="form" action="" method="post">
				<TABLE ><!-- cellSpacing=0 cellPadding=3 width="98%" align=center border=0 -->
					<TBODY>
						<tr>
						</tr>
						<tr align="center" bgcolor="#ebf0f7">
							<td width="9%" height="25">用户名</td>
							<td width="9%" height="25">密码</td>
							<td width="9%" height="25">上传文件类型</td>
							<td width="9%" height="25">权限</td>
							<td width="9%" height="25">操作</td>
						</tr>

						<c:forEach var="userlist" items="${sessionScope.userlist }">
							<tr align='center' bgcolor='#FFFFFF'
								onmouseover='this.style.background="#F2FDFF"'
								onmouseout='this.style.background="#FFFFFF"'>
								<td width="9%" height="25">${userlist.name }</td>
								<td width="9%" height="25">${userlist.pwd }</td>
								<td width="9%" height="25">${userlist.type }</td>
								<td width="9%" height="25">${userlist.model }</td>
								<td width="9%" height="25"><a href="userUpdate?uid=${userlist.uid }">编辑</a>| <a
									href="userDelete?uid=${userlist.uid }"
									onClick="javascript:if(confirm('是否删除')){return true;}else{return false;}">删除</a>
							</tr>
						</c:forEach>
					</TBODY>
				</TABLE>
		</DIV>


	</div>
	</form>

</body>
</html>
