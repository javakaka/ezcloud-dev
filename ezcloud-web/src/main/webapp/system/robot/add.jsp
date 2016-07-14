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

//获取指定数据库里面的表
function queryTablesFromDataBase()
{
	var db_id =$("#curDatabase").val();
	if( typeof db_id == "undefined" || db_id==""){
		
		alert("请选择数据库或者新建数据库!");
		return false;
	}
	var params ={id: db_id};
	var url ="<%=basePath%>" +"system/robot/database/query-tables.do";
	$.ajax({
		url: url,
		type: "POST",
		data: params,
		dataType: "json",
		cache: false,
		beforeSend: function (XMLHttpRequest){
		},
		success: function(ovo, textStatus) {
			var code =ovo.code;
			if(code >=0)
			{
				var tableList =ovo.oForm.TABLE_LIST;
				var itemHtml ="";
				$.each(tableList, function (i,item){
					itemHtml +="<div class=\"table-item\">"+item.TABLE_NAME+"</div>";
				});
				$("#table_items").html( itemHtml );
			}
			else
			{
				$.message("error",ovo.msg);
			}
		},
		complete: function (XMLHttpRequest, textStatus){
		},
		error: function (){
			alert('error...');
		}
	});
	
}
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
	border-right: red solid 1px;
}

.code{
	width: 78%;
    height: 100%;
}

。page-item{ 
	width: 100%;
	border-bottum: 1px soild red;
}

.database{
 width :100%;
 height: 50px;
}

.db-table{
	width: 100%;
    max-height: 600px;
    overflow-y: scroll;
}

.table-item {
    width: 100%;
    height: 30px;
    border: 1px solid red;
    border-collapse: collapse;
}
</style>
</head>
<body>
	<div class="path">
		代码管理 &raquo; 生成代码
	</div>
	<form id="inputForm" action="save.do" method="post">
		<div class="pageContent">
			<div id="left" class="left tableList">
				<div id="db_list" class="database">
				数据库:<select id="curDatabase">
						<option value="" selected>请选择...</option>
						<c:forEach items="${db_list}" var="db" varStatus="status">
							<option value="${db.ID }" >${db.DB_NAME }</option>
						</c:forEach>
					</select>
					<input type="button" onclick="queryTablesFromDataBase()" value="连 接"></input>
					</div>
				<div id="table_items" class="db-table ">
					<div class="table-item">tb_user</div>
					<div class="table-item">tb_user</div>
					<div class="table-item">tb_user</div>
					<div class="table-item">tb_user</div>
				</div>
			</div>
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