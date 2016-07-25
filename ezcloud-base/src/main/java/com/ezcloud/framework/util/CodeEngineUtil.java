package com.ezcloud.framework.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 代码工具类
 * @author kaka
 */
public class CodeEngineUtil {
	private final static String entity_import ="import java.util.*;"+"\n import javax.persistence.*; \n import java.io.*;\n";

	private CodeEngineUtil(){
		
	}
	public static  ResponseVO execute(JSONObject paramJson)
	{
		ResponseVO ovo =null;
		String tableName =paramJson.getString( "tableName" );
		String databaseTemplate =paramJson.getString( "databaseTemplate" );
		String project_path =paramJson.getString( "project_path" );
		String entity_path =paramJson.getString( "entity_path" );
		String dao_path =paramJson.getString( "dao_path" );
		String service_path =paramJson.getString( "service_path" );
		String controller_path =paramJson.getString( "controller_path" );
		String api_path =paramJson.getString( "api_path" );
		String jsp_path =paramJson.getString( "jsp_path" );
		String entityName =paramJson.getString( "entityName" );
		String entityKeyGenerator =paramJson.getString( "entityKeyGenerator" );
		String sequenceName =paramJson.getString( "sequenceName" );
		JSONArray attributeArray =JSONArray.fromObject( paramJson.getString( "entityFieldsMapArray" ) );
		String  adminControllerUri=paramJson.getString( "adminControllerUri" );
		String  apiControllerUri=paramJson.getString( "apiControllerUri" );
		String  listPageUri=paramJson.getString( "listPageUri" );
		String  listPageMethod=paramJson.getString( "listPageMethod" );
		String  listPageRemark=paramJson.getString( "listPageRemark" );
		String  listPageShowFields=paramJson.getString( "listPageShowFields" ).replace( "list-page-show-field=", "" );
		String  listPageSearchFields=paramJson.getString( "listPageSearchFields" ).replace( "list-page-search-field=", "" );
		String  listPageFunctionBtns=paramJson.getString( "listPageFunctionBtns" ).replace( "list-page-function-btn=", "" );
		String  addPageUri=paramJson.getString( "addPageUri" );
		String  addPageMethod=paramJson.getString( "addPageMethod" );
		String  addPageRemark=paramJson.getString( "addPageRemark" );
		String  addPageShowFields=paramJson.getString( "addPageShowFields" ).replace( "add-page-show-field=", "" );
		String  editPageUri=paramJson.getString( "editPageUri" );
		String  editPageMethod=paramJson.getString( "editPageMethod" );
		String  editPageRemark=paramJson.getString( "editPageRemark" );
		String  editPageShowFields=paramJson.getString( "editPageShowFields" ).replace( "edit-page-show-field=", "" );
		String  deletePageUri=paramJson.getString( "deletePageUri" );
		String  deletePageMethod=paramJson.getString( "deletePageMethod" );
		String  deletePageRemark=paramJson.getString( "deletePageRemark" );
		String  deletePageShowFields=paramJson.getString( "deletePageShowFields" ).replace( "delete-page-where-field=", "" );
		String  listApiUri=paramJson.getString( "listApiUri" );
		String  listApiMethod=paramJson.getString( "listApiMethod" );
		String  listApiRemark=paramJson.getString( "listApiRemark" );
		String  listApiShowFields=paramJson.getString( "listApiShowFields" ).replace( "list-api-show-field=", "" );
		String  addApiUri=paramJson.getString( "addApiUri" );
		String  addApiMethod=paramJson.getString( "addApiMethod" );
		String  addApiRemark=paramJson.getString( "addApiRemark" );
		String  addApiShowFields=paramJson.getString( "addApiShowFields" ).replace( "add-api-show-field=", "" );
		String  editApiUri=paramJson.getString( "editApiUri" );
		String  editApiMethod=paramJson.getString( "editApiMethod" );
		String  editApiRemark=paramJson.getString( "editApiRemark" );
		String  editApiShowFields=paramJson.getString( "editApiShowFields" ).replace( "edit-api-show-field=", "" );
		String  deleteApiUri=paramJson.getString( "deleteApiUri" );
		String  deleteApiMethod=paramJson.getString( "deleteApiMethod" );
		String  deleteApiRemark=paramJson.getString( "deleteApiRemark" );
		String  deleteApiWhereFields=paramJson.getString( "deleteApiWhereFields" ).replace( "delete-api-where-field=", "" );;
		//生成entity 文件
		FileUtil.isDirExisted( entity_path ,true);
		String entityFullPath =entity_path + entityName +".java";
		StringBuffer entitySB =new StringBuffer();
		// 包名
		String entityPackageName =entity_path.replace(project_path, "").replace("/", ".");
		entityPackageName =entityPackageName.substring(0,entityPackageName.length() -1);
		entitySB.append("package ").append(entityPackageName+"; \n");
		entitySB.append(entity_import);
		entitySB.append("@Entity").append("\n");
		entitySB.append("@Table(name = \""+ tableName+"\")").append("\n");
		entitySB.append("@SequenceGenerator(name = \"sequenceGenerator\", sequenceName = \""+sequenceName+"\")").append("\n");
		entitySB.append("public class "+ entityName +" implements Serializable {").append("\n");
		entitySB.append("}").append("\n");
		FileUtil.writeText( entityFullPath, entitySB.toString() );
		//生成dao 文件
		FileUtil.isDirExisted( dao_path ,true);
		String daoInterfaceFullPath =dao_path + entityName +"Dao.java";
		String daoImplFullPath =dao_path + entityName +"DaoImpl.java";
		FileUtil.writeText( daoInterfaceFullPath, "dao" );
		FileUtil.writeText( daoImplFullPath, "daoimpl" );
		//生成service 文件
		//生成admin controller 文件
		//生成api controller 文件
		//生成jsp 文件
		return ovo;
	}
}
