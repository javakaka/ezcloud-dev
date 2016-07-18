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
					itemHtml +="<div class=\"table-item\" onclick=\"tableClick('"+item.TABLE_NAME+"', this)\">"+item.TABLE_NAME+"</div>";
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

// 点击表，加载字段
function tableClick( tableName, obj){
	var $this =$(obj);
	//alert($this);
	// reset selected
	$(".table-item").removeClass("selected");
	$this.addClass("selected");
	var db_id =$("#curDatabase").val();
	if( typeof db_id == "undefined" || db_id==""){
		
		alert("请选择数据库或者新建数据库!");
		return false;
	}
	var tbName =tableName;
	var params ={dbId: db_id,tableName:tbName};
	var url ="<%=basePath%>" +"system/robot/database/query-fields.do";
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
				var tableList =ovo.oForm.FIELD_LIST;
				var itemHtml ="";
				$.each(tableList, function (i,item){
					itemHtml +="<div class=\"field-item\" title=\""+item.COLUMN_COMMENT+"\">";
					itemHtml +="<div class=\"field-td field-w4\">"+item.COLUMN_NAME+"</div>";
					itemHtml +="<div class=\"field-td field-w2\">"+item.COLUMN_TYPE+"</div>";
					itemHtml +="<div class=\"field-td field-w2\">"+item.IS_NULLABLE+"</div>";
					itemHtml +="<div class=\"field-td field-w2\">"+item.COLUMN_KEY+"</div>";
					itemHtml +="</div>";
				});
				$("#fieldList").html( itemHtml );
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

.fields{
	width: 304px;
    height: 100%;
    overflow-y: scroll;
}
.code{
	min-width: 460px;
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

.table-item-title {
    width: 100%;
    height: 30px;
    border: 1px solid #5d5656;
    margin-bottom: -1px;
    margin-left: -1px;
    font-size: medium;
    line-height: 26px;
    font-weight: 600;
}
.table-item {
    width: 100%;
    height: 30px;
    border: 1px solid #5d5656;
    margin-bottom: -1px;
    margin-left: -1px;
    font-size: medium;
    line-height: 26px;
}
.field-item {
    width: 100%;
    height: 24px;
    border: 1px solid #5d5656;
    margin-bottom: -1px;
    margin-left: -1px;
    font-size: medium;
    line-height: 24px;
}

.selected {
	background-color: #d83131;
    color: white;
}

.field-title {
width: 100%;
height: 50px;
}

.field-list {
width: 100%;
max-height: 800px;
/*overflow-y: scroll;*/
}
.field-th {
    float: left;
    height: 50px;
    line-height: 50px;
    font-size: medium;
    border-right: solid 1px #271e1e;
}

.field-td {
    float: left;
    height: 25px;
    line-height: 25px;
    font-size: small;
    border-right: solid 1px #271e1e;
}

.field-w1 {
	width: 50px;
}

.field-w2 {
	width: 60px;
}

.field-w3 {
	width: 70px;
}
.field-w4 {
	width: 100px;
}

.page-item {
    width: 100%;
    border-bottom: 1px solid #352d2d;
    border-right: 1px solid #2d2828;
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
					<div class="table-item-title">Table 列表</div>
				<div id="table_items" class="db-table ">
					
				</div>
			</div>
			<div id="center" class="left fields">
				<div class="field-title">
					<div class="field-th field-w4">字段名</div>
					<div class="field-th field-w2">类型</div>
					<div class="field-th field-w2">允许空</div>
					<div class="field-th field-w2">约束</div>
				</div>
				<div class="field-list" id="fieldList">
					<div class="field-item" title="remark">
						<div class="field-td field-w4">id</div>
						<div class="field-td field-w2">int</div>
						<div class="field-td field-w2">NO</div>
						<div class="field-td field-w2">PRI</div>
					</div>
					<div class="field-item" title="remark">
						<div class="field-td field-w4">id</div>
						<div class="field-td field-w2">int</div>
						<div class="field-td field-w2">NO</div>
						<div class="field-td field-w2">PRI</div>
					</div>
				</div>
			</div>
			<div id="right" class="left code">
				<div id="config" class="page-item">
					<fieldset>
						<legend>配置项</legend>
						<div class="">
							<label>数据库模版：</label>
							<input type="radio" name="dbModel" value="jpa" checked="checked"/>JPA模式
							<input type="radio" name="dbModel" value="jdbc"/>JDBC模式
						</div>
						<div class="">
							<label>项目路径：</label>
							<input type="textbox" name="project_path" value="" />
						</div>
						<div class="">
							<label>Entity保存路径：</label>
							<input type="textbox" name="entity_path" value="" />
						</div>
						<div class="">
							<label>dao保存路径：</label>
							<input type="textbox" name="dao_path" value="" />
						</div>
						<div class="">
							<label>service保存路径：</label>
							<input type="textbox" name="service_path" value="" />
						</div>
						<div class="">
							<label>controller保存路径：</label>
							<input type="textbox" name="controller_path" value="" />
						</div>
						<div class="">
							<label>api保存路径：</label>
							<input type="textbox" name="api_path" value="" />
						</div>
					</fieldset>
				</div>
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