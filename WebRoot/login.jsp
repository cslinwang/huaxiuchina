<%@ page language="java" import="java.util.*,com.huaxiuchina.model.*"
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

<title>华秀山西后台管理系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="./css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<script language="javascript">
		function check() {
			if (form.name.value == "") {
				alert("请输入用户名");
				return;
			}
			if (form.pwd.value == "") {
				alert("请输入密码");
				return;
			}
			form.submit();
		}
	</script>
	<form name="form" action="login" method="post">
		<div class="m_ctr">
			<div class="t_left"></div>
			<div class="t_mid">华秀山西后台管理系统</div>
			<div class="t_right"></div>
			<div class="m_mid">
				<div class="m_pic"></div>
				<div class="m_txt" style="margin-top:10px;">
					用户名： <input type="text" name="name" />
				</div>
				<div class="m_txt">
					密&nbsp;码： <input type="password" name="pwd" />
				</div>

				<div class="m_txt" style="width:200px;padding-left:56px;">

					<input type="button" name="Submit" value="提交" onclick=check() /> <input
						type="reset" name="Submit2" value="重置" />
				</div>
			</div>
			<div class="m_foot"></div>
		</div>
		<div class="m_cp">
			Powered by &copy; 2016, <a href="" target="_blank">登陆首页</a>
		</div>
		</div>
	</form>
</body>
</html>
