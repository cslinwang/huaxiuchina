<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%
	request.setCharacterEncoding("gb2312");
	String qx = "";
	if (session.getAttribute("qx") != null) {
		qx = (String) session.getAttribute("qx");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="css/css_menu.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<title>无标题文档</title>

<script language="javascript">
	function getObject(objectId) {
		if (document.getElementById && document.getElementById(objectId)) {
			// W3C DOM
			return document.getElementById(objectId);
		} else if (document.all && document.all(objectId)) {
			// MSIE 4 DOM
			return document.all(objectId);
		} else if (document.layers && document.layers[objectId]) {
			// NN 4 DOM.. note: this won't find nested layers
			return document.layers[objectId];
		} else {
			return false;
		}
	}

	function showHide(objname) {
		var obj = getObject(objname);
		if (obj.style.display == "none") {
			obj.style.display = "block";
		} else {
			obj.style.display = "none";
		}
	}
</script>
</head>
<base target="main" />
<body>
	<div class="infobox"></div>
	<div class="menu">
		<%
			if (session.getAttribute("user") != null) {
				if (session.getAttribute("model").equals(0)) {
		%>
		<!-- Item 1 Strat -->
		<dl>

			<dt>
				<a href="###" onclick="showHide('items1');" target="_self">用户管理</a>
			</dt>
			<dd id="items1" style="display:none;">
				<ul>

					<li><a href='user/useradd.jsp' target='main'>用户增加</a>
						<li><a href='userSelectAll' target='main'>用户列表</a>
					</li></li>
					</li>
				</ul>
			</dd>
		</dl>
		<!-- Item 1 End -->
		<%
			}
				if (session.getAttribute("model").equals(0)
						^ session.getAttribute("model").equals(1)) {
		%>

		<!-- Item 2 Strat -->
		<dl>

			<dt>
				<a href="###" onclick="showHide('items2');" target="_self">股票管理</a>
			</dt>
			<dd id="items2" style="display:none;">
				<ul>

					<li><a href='gp/gpupload.jsp' target='main'>股票信息上传</a>
					</li>
					<li><a href='gpSelectAllAction' target='main'>当日股票详情</a>
					</li>
				</ul>
			</dd>
		</dl>
		<!-- Item 2 End -->
		<%
			}
		%>
		<!-- Item 3 Strat -->
		<dl>

			<dt>
				<a href="###" onclick="showHide('items3');" target="_self">成交管理</a>
			</dt>
			<dd id="items3" style="display:none;">
				<ul>

					<li><a href="daydeal/daydealupload.jsp" target='main'>成交上传</a>
					</li>
					<li><a href="selectByNameDate?name=${user.name }"
						target='main'>成交查看</a></li>
					<!-- <li><a href='cj/cjdownd.jsp' target='main'>成交下载</a></li> -->
				</ul>
			</dd>
		</dl>
		<!-- Item 3 End -->
		<!-- Item 4 Strat -->
		<dl>

			<dt>
				<a href="###" onclick="showHide('items4');" target="_self">模型管理</a>
			</dt>
			<dd id="items4" style="display:none;">
				<ul>
					<li><a href="modelSelectByNameDate?name=${user.name }"
						target='main'>模型查看</a></li>
					<!-- <li><a href='cj/cjdownd.jsp' target='main'>成交下载</a></li> -->
				</ul>
			</dd>
		</dl>
		<!-- Item 4 End -->
		<%
			if (session.getAttribute("model").equals(0)) {
		%>
		<!-- Item 5 Strat -->
		<dl>

			<dt>
				<a href="###" onclick="showHide('items5');" target="_self">备份管理</a>
			</dt>
			<dd id="items5" style="display:none;">
				<ul>

					<li><a href='backup/backup.jsp' target='main'>备份</a>
						<li><a href='backup/revert.jsp' target='main'>还原</a>
					</li></li>
					</li>
				</ul>
			</dd>
		</dl>
		<!-- Item 5 End -->
		<%
			}
		%>
		<%
			}
		%>
	</div>
</body>
</html>
