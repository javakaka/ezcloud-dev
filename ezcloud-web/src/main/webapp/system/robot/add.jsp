<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/cctaglib" prefix="cc"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>代码生成器</title>
<link href="<%=basePath%>/res/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/input.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	//[@flash_message /]
	// 表单验证
	$inputForm.validate({
		rules: {
		}
	});
	
});
</script>
<style type="text/css">
.pageContent{
width: 100%;
height:auto;
border: 1px solid red;
overflow-y: scroll;
}
.left{ 
	float:left;
}
.right{ 
	float: right;
}

.tableList{
	width: 20%;
	height:100%;
	border-right: 1px soild red;
}

.code{
	width: 78%;
    height: 100%;
    border-left: 1px solid red;
}

。page-item{ 
	width: 100%;
	border-bottum: 1px soild red;
}
</style>
</head>
<body>
	<div class="path">
		代码管理 &raquo; 生成代码
	</div>
	<form id="inputForm" action="save.do" method="post">
		<div class="pageContent">
			<div id="left" class="left tableList">表列表</div>
			<div id="right" class="left code">
				<div id="config" class="page-item">配置项</div>
				<div id="entity" class="page-item">模型</div>
				<div id="listPage" class="page-item">列表界面</div>
				<div id="addPage" class="page-item">添加界面</div>
				<div id="editPage" class="page-item">编辑界面</div>
			</div>
		</div>
		<div>
			<input type="submit" class="button" value="<cc:message key="admin.common.submit" />" />
			<input type="button" id="backButton" class="button" value="<cc:message key="admin.common.back" />" />
		</div>
	</form>
</body>
</html>