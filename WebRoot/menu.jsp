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
			
		%>
		<!-- Item 1 Strat -->
		<dl>

			<dt>
				<a href="###" onclick="showHide('items1');" target="_self">股票管理</a>
			</dt>
			<dd id="items1" style="display:none;">
				<ul>

					<li><a href='gp/gpupload.jsp' target='main'>股票信息上传</a>
					</li>
					<li><a href='gpSelectAllAction' target='main'>当日股票详情</a>
					</li>
				</ul>
			</dd>
		</dl>
		<!-- Item 1 End -->
		<!-- Item 2 Strat -->
		<dl>

			<dt>
				<a href="###" onclick="showHide('items2');" target="_self">成交管理</a>
			</dt>
			<dd id="items2" style="display:none;">
				<ul>

					<li><a href='yonghu/yonghuadd.jsp' target='main'>成交上传</a>
					</li>
					<li><a href='yonghuSelectAllAction' target='main'>成交查看</a>
					</li>
				</ul>
			</dd>
		</dl>
		<!-- Item 2 End -->
		
	</div>
</body>
</html>
