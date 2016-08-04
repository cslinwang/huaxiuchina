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

<title>My JSP 'yonghuadd.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="./images/style.css" type=text/css rel="stylesheet">
<link href="./css/tinyTips.css" rel="stylesheet" type="text/css"
	media="screen" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

</head>

<body>
		<form action="daydealUploadAction?name=${sessionScope.user.name }&type=${sessionScope.user.type }" method="post" enctype="multipart/form-data">
		<div class=formzone>
			<div class=searchzone>

				<table height=30 cellSpacing=0 cellPadding=0 width="100%" border=0>
					<tbody>
						<tr>
							<td height=30>当日股票信息上传</td>
							<td align=right colSpan=2>&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class=tablezone>
				<div class=noticediv id=notice></div>
				<table cellSpacing=0 cellPadding=2 width="100%" align=center
					border=0>
					<tbody>
						<tr>
							<td align=middle width=100 height=30>请选择文件:</td>
							<td height=30><input type="file" id="dofile" name="file"
								style="FONT-SIZE: 12px; WIDTH: 300px"></td>
						</tr>

					</TBODY>
				</TABLE>
			</div>
			<DIV class=adminsubmit>
				<input type="submit" value="上传" name="B1" class="button"
					style="width:60px"  /> &nbsp;&nbsp; <input
					type="reset" value="取消" style="width:60px" name="chanel"
					class="button" />
			</div>
		</div>
	</form>
	<br>
</body>
</html>
