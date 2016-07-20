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
<link href="<%=basePath%>/res/css/diymen/tipswindown.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/css/diymen/tipswindown.js?version=1.4"></script>
<link href="<%=basePath%>/res/css/diymen/tipswindown.css" rel="stylesheet" type="text/css" />
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
				// 展示表格的列属性
				$.each(tableList, function (i,item){
					itemHtml +="<div class=\"field-item\" title=\""+item.COLUMN_COMMENT+"\">";
					itemHtml +="<div class=\"field-td field-w4\">"+item.COLUMN_NAME+"</div>";
					itemHtml +="<div class=\"field-td field-w2\">"+item.COLUMN_TYPE+"</div>";
					itemHtml +="<div class=\"field-td field-w2\">"+item.IS_NULLABLE+"</div>";
					itemHtml +="<div class=\"field-td field-w2\">"+item.COLUMN_KEY+"</div>";
					itemHtml +="</div>";
				});
				$("#fieldList").html( itemHtml );
				
				var entityHtml ="";
				// 展示表对应的Entity对象属性
				$.each(tableList, function (i,item){
					entityHtml +="";
					entityHtml +="    <div class=\"entity-item\">   ";
					entityHtml +="    <div class=\"entity-td entity-w1\">    ";
					//entityHtml +="    	<span>"+item.COLUMN_NAME+"</span>";
					entityHtml +="    	<input type=\"text\" name=\"columnName\" value=\""+item.COLUMN_NAME+"\" class=\"entity-field-name\" readonly /> ";
					entityHtml +="    </div> ";
					entityHtml +="    <div class=\"entity-td entity-w1\">  ";
					entityHtml +="    	<span>"+item.COLUMN_TYPE+"</span>  ";
					entityHtml +="    </div> ";
					entityHtml +="    <div class=\"entity-td entity-w1\">    ";
					entityHtml +="    	<input type=\"text\" id=\"attributeName"+item.COLUMN_NAME+"\" name=\"attributeName\" value=\""+convertColumnNameToAttributeName( item.COLUMN_NAME )+"\" class=\"entity-field-name\"/> ";
					entityHtml +="    </div> ";
					entityHtml +="    <div class=\"entity-td entity-w1\">    ";
					entityHtml +="    	<select id=\"attributeType"+item.COLUMN_NAME+"\" name=\"attributeType\"  class=\"entity-field-name\">   ";
					entityHtml += AttributeTypeSelectOptionHtml;
					entityHtml +="    	</select> ";
					entityHtml +="    </div> ";
					entityHtml +="    <div class=\"entity-td entity-w1\">    ";
					entityHtml +="    	<select id=\"mappingKind"+item.COLUMN_NAME+"\"  name=\"mappingKind\"> ";
					entityHtml +=getAttributeMappingKindOptionHtml( item.COLUMN_KEY );
					entityHtml +="    	</select> ";
					entityHtml +="    </div> ";
					entityHtml +="    <div class=\"entity-td entity-w1\">    ";
					entityHtml +="    	<select  id=\"updateable"+item.COLUMN_NAME+"\" name=\"updateable\"> ";
					entityHtml +=UpdateableSelectOptionHtml;
					entityHtml +="    	</select> ";
					entityHtml +="    </div> ";
					entityHtml +="    <div class=\"entity-td entity-w1\">    ";
					entityHtml +="    	<select  id=\"insertable"+item.COLUMN_NAME+"\" name=\"insertable\">  ";
					entityHtml +=InsertableSelectOptionHtml;
					entityHtml +="    	</select> ";
					entityHtml +="    </div> ";
					entityHtml +="    <div class=\"entity-td entity-w1\">    ";
					entityHtml +="    	<select  id=\"getterScope"+item.COLUMN_NAME+"\" name=\"getterScope\"> ";
					entityHtml +=ScopeSelectOptionHtml;
					entityHtml +="    	</select>         ";
					entityHtml +="    </div> ";
					entityHtml +="    <div class=\"entity-td entity-w1\">    ";
					entityHtml +="    	<select  id=\"setterScope"+item.COLUMN_NAME+"\" name=\"setterScope\">  ";
					entityHtml +=ScopeSelectOptionHtml;
					entityHtml +="    	</select>  ";
					entityHtml +="    </div> ";
					entityHtml +="    </div> ";
				});
				$("#entityFields").html(entityHtml);
				
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
	
	// entity 类名字
	var entityName =convertTableNameToEntityName( tbName );
	$("#entityName").val( entityName );
}


var AttributeTypeSelectOptionHtml =" "
	+" <option value=\"\">请选择...</option> "
	+" <option value=\"boolean\">boolean</option> "
	+" <option value=\"Boolean\">Boolean</option> "
	+" <option value=\"byte\">byte</option> "
	+" <option value=\"Byte\">Byte</option> "
	+" <option value=\"byte[]\">byte[]</option> "
	+" <option value=\"char\">char</option> "
	+" <option value=\"char[]\">char[]</option> "
	+" <option value=\"Character\">Character</option> "
	+" <option value=\"Character[]\">Character[]</option> "
	+" <option value=\"double\">double</option> "
	+" <option value=\"Double\">Double</option> "
	+" <option value=\"float\">float</option> "
	+" <option value=\"Float\">Float</option> "
	+" <option value=\"int\">int</option> "
	+" <option value=\"Integer\">Integer</option> "
	+" <option value=\"long\">long</option> "
	+" <option value=\"Long\">Long</option> "
	+" <option value=\"Object\">Object</option> "
	+" <option value=\"short\">short</option> "
	+" <option value=\"Short\">Short</option> "
	+" <option value=\"String\">String</option> "
	+" <option value=\"java.math.BigDecimal\">java.math.BigDecimal</option> "
	+" <option value=\"java.math.BigInteger\">java.math.BigInteger</option> "
	+" <option value=\"java.util.Calendar\">java.util.Calendar</option> "
	+" <option value=\"java.util.Date\">java.util.Date</option> "
	+" <option value=\"java.sql.Date\">java.sql.Date</option> "
	+" <option value=\"java.sql.Time\">java.sql.Time</option> "
	+" <option value=\"java.sql.Timestamp\">java.sql.Timestamp</option> "
	+" ";
	
	
	var MappingKindSelectOptionHtml=""
		+" 	<option value=\"\">请选择...</option> "
		+" 	<option value=\"id\">id</option> "
		+" 	<option value=\"basic\">basic</option> "
		+" 	<option value=\"version\">version</option> "
		+" 	";
		
	var UpdateableSelectOptionHtml=" "
		//+" 	<option value=\"\">请选择...</option> "
		+" 	<option value=\"1\">可以</option> "
		+" 	<option value=\"0\">不可以</option> "
		+" 	";
		
	var InsertableSelectOptionHtml=" "
		//+" 	<option value=\"\">请选择...</option> "
		+" 	<option value=\"1\">可以</option> "
		+" 	<option value=\"0\">不可以</option> "
		+" 	";
		
	var ScopeSelectOptionHtml ="<option value=\"public\" >public</option> "
		+" <option value=\"protected\">protected</option> "
		+" <option value=\"private\">private</option>";
	
	/**
	*根据字段类型，返回映射类型选项html
	*/
	function getAttributeMappingKindOptionHtml (COLUMN_KEY)
	{
		var html ="";
		if(typeof COLUMN_KEY != "undefined" && COLUMN_KEY == "PRI"){
			
			html =""
				+" 	<option value=\"\">请选择...</option> "
				+" 	<option value=\"id\" selected>id</option> "
				+" 	<option value=\"basic\">basic</option> "
				+" 	<option value=\"version\">version</option> "
				+" 	";
		}
		else{
			
			html =""
				+" 	<option value=\"\">请选择...</option> "
				+" 	<option value=\"id\" >id</option> "
				+" 	<option value=\"basic\" selected>basic</option> "
				+" 	<option value=\"version\">version</option> "
				+" 	";
		}
		return html;
	}
	
	/**
	*将字段名称转成Entity属性名称
	*/
	function convertColumnNameToAttributeName(columnName){
		var attributeName =columnName;
		var posi =-1;
		do{
			posi =attributeName.indexOf("_");
			if( posi != -1 ){
				var tag =attributeName.charAt(posi);
				var upperChar ="";
				if( (posi+1) <= attributeName.length){
					tag +=attributeName.charAt(posi+1);
					upperChar =attributeName.charAt(posi+1).toUpperCase();
				}
				//alert(attributeName.charAt(posi));
				//alert(attributeName.charAt(posi+1));
				attributeName =attributeName.replace(tag, upperChar  );
				posi =attributeName.indexOf("_");
			}
		}while( posi != -1 );
		return attributeName;
	}
	
	/**
	*将字段名称转成Entity属性名称
	*/
	function convertTableNameToEntityName(tableName){
		var name =convertColumnNameToAttributeName(tableName);
		var firstChar =name.charAt(0);
		name =firstChar.toUpperCase() + name.substring(1, name.length);
		return name;
	}
	
	/**
	*entity 主键生成器策略改变
	*/
	function entityKeyGeneratorChange()
	{
		var val =$("#entityKeyGenerator").val();
		if( val == "sequence"){
			$("#sequenceName").val("");
			$("#sequenceName").removeAttr("disabled");
		}
		else
		{
			$("#sequenceName").attr("disabled","disabled");
			$("#sequenceName").val("");
		}
	}
	
	/**
	* 选择文件夹
	* id 目标对象的id值 
	*/
	function chooseSystemFolder(id){
		console.log('...........');
		title ="选择路径";
		var path =$("#"+id).val();
		console.log('...........'+path);
		url="iframe:<%=basePath%>system/file/select-folder.jsp?path="+path;
		iframeName="selectWindowIframeId";
		popWindow(title,url,width,height,drag,time,showBg,cssName,iframeName);
	}
	
	
	
	/** 弹窗******************************************/
	var title;
	var url;
	var width=520;
	var height=400;
	var drag="true";
	var time="";
	var showBg="true";
	var cssName="leotheme";
	var iframeName="selectIframeId";
	function popWindow(title,url,width,height,drag,time,showBg,cssName,iframeName)
	{
		tipsWindown(title,url,width,height,drag,time,showBg,cssName,iframeName);
	}

	 function closeTipWindow()
	 {
		tipsWindown.close();
	 }
	/********************************************/
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
	height:800px;
	border-right: red solid 1px;
}

.fields{
	width: 304px;
    height: 800px;
    overflow-y: scroll;
}
.code{
	min-width: 900px;
    height: 800px;
    margin-left: 5px;
    overflow-y: scroll;
    overflow-x: hidden;
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
    max-height: 720px;
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
    margin-bottom: -1px;
    margin-left: -1px;
    font-size: medium;
    line-height: 24px;
    border-left: 1px solid #5d5656;
    border-top: 1px solid #5d5656;
    border-bottom: 1px solid #5d5656;
}

.selected {
	background-color: #d83131;
    color: white;
}

.field-title {
width: 284px;
height: 50px;
}

.field-list {
width: 284px;
max-height: 800px;
/*overflow-y: scroll;*/
}
.field-th {
    float: left;
    height: 50px;
    line-height: 50px;
    font-size: small;
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
    width: 899px;
    border-bottom: 1px solid #352d2d;
    border-right: 1px solid #2d2828;
}

.config-item  {
width : 100%;
height: 30px;
}

.config-item .config-label {
    width: 120px;
    display: block;
    float: left;
    text-align: left;
}
.config-item .config-value {
    width: 400px;
    /* display: block; */
    text-align: left;
}
.entity-field-name {
	width: 80%;
}

.entity-title {
    width: 100%;
    height: 30px;
    border-bottom: 1px solid #2d2828;
}
.entity-th {
 float:left;
}
.entity-w1 {
	width: 90px;
}
.entity-w2 {
	width: 80px;
}
.entity-w3 {
	width: 50px;
}

.entity-fields {
	min-height: 150px;
	overflow-y:scroll;
}
.entity-item {
    border-bottom: 1px solid #2d2828;
    height: 26px;
}
.entity-td {
 float:left;
}

.choose-fold-btn {
	width: 100px;
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
						<div class="config-item">
							<label class="config-label">数据库模版：</label>
							<input type="radio" name="dbModel" value="jpa" checked="checked"/>JPA模式
							<input type="radio" name="dbModel" value="jdbc"/>JDBC模式
						</div>
						<div class="config-item">
							<label class="config-label">项目路径：</label>
							<input type="text" id="project_path" name="project_path" value="" class="config-value"/>
							<input type="button" name="choose_path" value="选择..." class="choose-fold-btn" onclick="chooseSystemFolder('project_path')"/>
						</div>
						<div class="config-item">
							<label class="config-label">Entity保存路径：</label>
							<input type="text" id="entity_path" name="entity_path" value="" class="config-value" />
							<input type="button" name="choose_path" value="选择..." class="choose-fold-btn" onclick="chooseSystemFolder('entity_path')"/>
						</div>
						<div class="config-item">
							<label class="config-label">dao保存路径：</label>
							<input type="text" id="dao_path" name="dao_path" value="" class="config-value" />
							<input type="button" name="choose_path" value="选择..." class="choose-fold-btn" onclick="chooseSystemFolder('dao_path')"/>
						</div>
						<div class="config-item">
							<label class="config-label">service保存路径：</label>
							<input type="text" id="service_path" name="service_path" value="" class="config-value" />
							<input type="button" name="choose_path" value="选择..." class="choose-fold-btn" onclick="chooseSystemFolder('service_path')"/>
						</div>
						<div class="config-item">
							<label class="config-label">controller保存路径：</label>
							<input type="text" id="controller_path" name="controller_path" value="" class="config-value" />
							<input type="button" name="choose_path" value="选择..." class="choose-fold-btn" onclick="chooseSystemFolder('controller_path')"/>
						</div>
						<div class="config-item">
							<label class="config-label">api保存路径：</label>
							<input type="text" id="api_path" name="api_path" value="" class="config-value" />
							<input type="button" name="choose_path" value="选择..." class="choose-fold-btn" onclick="chooseSystemFolder('api_path')"/>
						</div>
						<div class="config-item">
							<label class="config-label">jsp页面保存路径：</label>
							<input type="text" id="jsp_path" name="jsp_path" value="" class="config-value" />
							<input type="button" name="choose_path" value="选择..." class="choose-fold-btn" onclick="chooseSystemFolder('jsp_path')"/>
						</div>
					</fieldset>
				</div>
				<div id="entity" class="page-item">
					<fieldset>
						<legend>模型</legend>
						<div class="config-item">
							<label class="config-label">Entity 名字：</label>
							<input type="text" id="entityName" name="entity_name" value="" class="config-value"/>
						</div>
						<div class="config-item">
							<label class="config-label">Entity 主键生成器：</label>
							<select id="entityKeyGenerator" name="" class="config-value" onchange="entityKeyGeneratorChange()">
								<option value="auto">auto</option>
								<option value="identity">identity</option>
								<option value="sequence">sequence</option>
								<option value="table">table</option>
								<option value="none">none</option>
							</select>
						</div>
						<div class="config-item">
							<label class="config-label">序列名称：</label>
							<input type="text" name="entity_name" value="" class="config-value" id="sequenceName" disabled="disabled"/>
						</div>
						<div>
							<div class="entity-title">
								<div class="entity-th entity-w1">字段名称</div>
								<div class="entity-th entity-w1">字段类型</div>
								<div class="entity-th entity-w1">属性名称</div>
								<div class="entity-th entity-w1">属性类型</div>
								<div class="entity-th entity-w1">映射类型</div>
								<div class="entity-th entity-w1">可更新</div>
								<div class="entity-th entity-w1">可插入</div>
								<div class="entity-th entity-w1">Getter域</div>
								<div class="entity-th entity-w1">Setter域</div>
							</div>
							<div  id="entityFields" class="entity-fields">
								<div class="entity-item">
								<div class="entity-td entity-w1">
									<span>id</span>
								</div>
								<div class="entity-td entity-w1">
									<span>int</span>
								</div>
								<div class="entity-td entity-w1">
									<input type="text" name="entity_name" value="id" class="entity-field-name"/>
								</div>
								<div class="entity-td entity-w1">
									<select name=""  class="entity-field-name">
										<option value="">请选择...</option>
										<option value="boolean">boolean</option>
										<option value="Boolean">Boolean</option>
										<option value="byte">byte</option>
										<option value="Byte">Byte</option>
										<option value="byte[]">byte[]</option>
										<option value="char">char</option>
										<option value="char[]">char[]</option>
										<option value="Character">Character</option>
										<option value="Character[]">Character[]</option>
										<option value="double">double</option>
										<option value="Double">Double</option>
										<option value="float">float</option>
										<option value="Float">Float</option>
										<option value="int">int</option>
										<option value="Integer">Integer</option>
										<option value="long">long</option>
										<option value="Long">Long</option>
										<option value="Object">Object</option>
										<option value="short">short</option>
										<option value="Short">Short</option>
										<option value="String">String</option>
										<option value="java.math.BigDecimal">java.math.BigDecimal</option>
										<option value="java.math.BigInteger">java.math.BigInteger</option>
										<option value="java.util.Calendar">java.util.Calendar</option>
										<option value="java.util.Date">java.util.Date</option>
										<option value="java.sql.Date">java.sql.Date</option>
										<option value="java.sql.Time">java.sql.Time</option>
										<option value="java.sql.Timestamp">java.sql.Timestamp</option>
									</select>
								</div>
								<div class="entity-td entity-w1">
									<select name="">
										<option value="">请选择...</option>
										<option value="id">id</option>
										<option value="basic">basic</option>
										<option value="version">version</option>
									</select>
								</div>
								<div class="entity-td entity-w1">
									<select name="">
										<option value="">请选择...</option>
										<option value="1">可以</option>
										<option value="0">不可以</option>
									</select>
								</div>
								<div class="entity-td entity-w1">
									<select name="">
										<option value="">请选择...</option>
										<option value="1">可以</option>
										<option value="0">不可以</option>
									</select>
								</div>
								<div class="entity-td entity-w1">
									<select name="">
										<option value="public" selected>public</option>
										<option value="protected">protected</option>
										<option value="private">private</option>
									</select>
								</div>
								<div class="entity-td entity-w1">
									<select name="">
										<option value="public" selected>public</option>
										<option value="protected">protected</option>
										<option value="private">private</option>
									</select>
								</div>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
				<div id="listPage" class="page-item">
					<fieldset>
							<legend>列表界面</legend>
					</fieldset>
				</div>
				<div id="addPage" class="page-item">
					<fieldset>
							<legend>添加界面</legend>
					</fieldset>
				</div>
				<div id="editPage" class="page-item">
					<fieldset>
							<legend>编辑界面</legend>
					</fieldset>
				</div>
				<div id="apiPage" class="page-item">
					<fieldset>
							<legend>API 接口</legend>
					</fieldset>
				</div>
			</div>
		</div>
		<div>
			<input type="submit" class="button" value="<cc:message key="admin.common.submit" />" />
			<input type="button" id="backButton" class="button" value="<cc:message key="admin.common.back" />" />
		</div>
	</form>
</body>
</html>