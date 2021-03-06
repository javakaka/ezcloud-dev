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
<title>${page.title}</title>
<link href="<%=basePath%>/res/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="path">
		${module.name} &raquo;${module.list.page.name}
		<span><cc:message key="admin.page.total" args="${page.total}"/></span>
	</div>
	<form id="listForm" action="International.do" method="get">
		<div class="bar">
			<!-- 
			<a href="add.do" class="iconButton">
				<span class="addIcon">&nbsp;</span><cc:message key="admin.common.add" />
			</a>
			 -->
			<a href="add.do" class="iconButton"><span class="addIcon">&nbsp;</span><cc:message key="admin.common.add" /></a>
			<div class="buttonWrap">
				<!-- 
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span><cc:message key="admin.common.delete" />
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span><cc:message key="admin.common.refresh" />
				</a>
				 -->
				 <a href="javascript:;" id="deleteButton" class="iconButton disabled"> 
<span class="deleteIcon">&nbsp;</span><cc:message key="admin.common.delete" /> 
</a> 

				 <a href="javascript:;" id="refreshButton" class="iconButton"> 
<span class="refreshIcon">&nbsp;</span><cc:message key="admin.common.refresh" /></a> 

				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						<cc:message key="admin.page.pageSize" /><span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li>
								<a href="javascript:;" <c:if test="${page.pageSize == 10}">class="current"</c:if> val="10" >10</a>
							</li>
							<li>
								<a href="javascript:;" <c:if test="${page.pageSize == 20}">class="current"</c:if> val="20">20</a>
							</li>
							<li>
								<a href="javascript:;" <c:if test="${page.pageSize == 50}"> class="current"</c:if>val="50">50</a>
							</li>
							<li>
								<a href="javascript:;"  <c:if test="${page.pageSize == 100}">class="current"</c:if>val="100">100</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- 
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;" <c:if test="${page.searchProperty == 'RES_ITEM'}"> class="current"</c:if> val="RES_ITEM"><cc:message key="framework.i18n.res_item" /></a>
						</li>
						<li>
							<a href="javascript:;" <c:if test="${page.searchProperty == 'LAN_NO'}">class="current"</c:if> val="LAN_NO"><cc:message key="framework.i18n.lan_no" /></a>
						</li>
						<li>
							<a href="javascript:;" <c:if test="${page.searchProperty == 'RES'}">class="current"</c:if> val="RES"><cc:message key="framework.i18n.res" /></a>
						</li>
					</ul>
				</div>
			</div>
			-->
			<div class="menuWrap"> 
<div class="search"> 
	<span id="searchPropertySelect" class="arrow">&nbsp;</span> 
	<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" /> 
	<button type="submit">&nbsp;</button> 
	</div> 
	<div class="popupMenu"> 
	<ul id="searchPropertyOption"> 
	 <li>  
 <a href="javascript:;" <c:if test="${page.searchProperty == 'name'}"> class="current"</c:if> val="name"></a>  
 </li>  
 <li>  
 <a href="javascript:;" <c:if test="${page.searchProperty == 'createTime'}"> class="current"</c:if> val="createTime"></a>  
 </li>  
	</ul> 
	</div> 
	</div> 

		</div>
		<!-- 
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="RES_ITEM">词条代码</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="LAN_NO">语言代码</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="RES">国际化内容</a>
				</th>
				<th>
					<span><cc:message key="admin.common.handle" /></span>
				</th>
			</tr>
			<c:forEach items="${page.content}" var="row" varStatus="status">
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${row.RES_ITEM}@${row.LAN_NO}" />
					</td>
					<td>
						<span title="${row.RES_ITEM}">${row.RES_ITEM}</span>
					</td>
					<td>
						${row.LAN_NO}
					</td>
					<td>
						${row.RES}
					</td>
					<td>
						<a href="edit.do?RES_ITEM=${row.RES_ITEM}&LAN_NO=${row.LAN_NO}"><cc:message key="admin.common.edit" /></a>
					</td>
				</tr>
			</c:forEach>
		</table>
		-->
		<table id="listTable" class="list"> 
<tr> 
<th class="check"> 
<input type="checkbox" id="selectAll" /> 
</th> 
<th> 
<a href="javascript:;" class="sort" name="id">id</a> 
</th> 
<th> 
<a href="javascript:;" class="sort" name="name">name</a> 
</th> 
<th> 
<a href="javascript:;" class="sort" name="age">age</a> 
</th> 
<th> 
<a href="javascript:;" class="sort" name="createTime">createTime</a> 
</th> 
<th> 
<a href="javascript:;" class="sort" name="updateTime">updateTime</a> 
</th> 
<th> 
<span><cc:message key="admin.common.handle" /></span> 
</th> 
</tr> 
<c:forEach items="${page.content}" var="row" varStatus="status"> 
<tr> 
<td> 
<input type="checkbox" name="ids" value="${row.id}@${row.id}" /> 
</td> 
<td> 
<span title="${row.id}">${row.id}</span> 
</td> 
<td> 
<span title="${row.name}">${row.name}</span> 
</td> 
<td> 
<span title="${row.age}">${row.age}</span> 
</td> 
<td> 
<span title="${row.createTime}">${row.createTime}</span> 
</td> 
<td> 
<span title="${row.updateTime}">${row.updateTime}</span> 
</td> 
<td> 
<a href="edit.do?id=${row.id}"><cc:message key="admin.common.edit" /></a> 
</td> 
</tr> 
</c:forEach> 
</table> 

		<%@ include file="/include/pagination.jsp" %> 
	</form>
</body>
</html>
