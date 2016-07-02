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
</head>

<body>
	<form name="form" action="" method="post">
		<table class="border" width="98%" border="0" align="center"
			cellpadding="1" cellspacing="0">
			<tr>
				<td height="565" align="center" valign="top" class="bg_low"><table
						width="100%" height="25" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td align="center" valign="middle" class="bgtop"><font
								color="#FFFFFF"><b>股票详情</b> </font></td>
						</tr>
					</table>
					<TABLE cellSpacing=0 cellPadding=3 width="100%" align=center
						border=0>
						<TBODY>
<tr>
<td><a href="downloadAction">导出</a></td>
</tr>
							<tr align="center" bgcolor="#ebf0f7">
								<td height="25">代码</td>
								<td wheight="25">买入价</td>
								<td height="25">卖出价</td>
								<td height="25">所属行业</td>
								<td height="25">最高</td>
								<td height="25">最低</td>
								<td height="25">开盘</td>
								<td height="25">昨收</td>

							</tr>

							<c:forEach var="gp" items="${sessionScope.gpList }">
								<tr align='center' bgcolor='#FFFFFF'
									onmouseover='this.style.background="#F2FDFF"'
									onmouseout='this.style.background="#FFFFFF"'>
									<td height="24">${gp.dm }</td>
									<td height="24">${gp.mrj }</td>
									<td height="24">${gp.mcj }</td>
									<td height="24">${gp.sshy }</td>
									<td height="24">${gp.zg }</td>
									<td height="24">${gp.zd1 }</td>
									<td height="24">${gp.kp }</td>
									<td height="24">${gp.zs1 }</td>
									
								</tr>
							</c:forEach>
						</TBODY>
					</TABLE>
				</td>
			</tr>
		</table>
		</DIV>


		</div>
	</form>

</body>
</html>
