package com.ezcloud.framework.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 代码工具类
 * @author kaka
 */
public class CodeEngineUtil {
	private final static String entity_import ="import java.util.*;"+"\n import javax.persistence.*; \n import java.io.*;\n";
	private final static String dao_import ="import com.ezcloud.framework.dao.JpaBaseDao;";
	private final static String daoimpl_import ="import com.ezcloud.framework.dao.JpaBaseDaoImpl;\n import org.springframework.stereotype.*;\n import javax.persistence.*;";
	private final static String base_dao ="JpaBaseDao";
	private final static String base_dao_impl ="JpaBaseDaoImpl";
	private final static String service_import ="import java.util.*;\n import com.ezcloud.framework.service.JpaBaseService;\n";
	private final static String serviceimpl_import ="import com.ezcloud.framework.service.JpaBaseServiceImpl;\n import org.springframework.stereotype.*;import org.springframework.transaction.annotation.*;\n import javax.persistence.*;\n import javax.annotation.Resource;\n";
	private final static String base_service ="JpaBaseService";
	private final static String base_service_impl ="JpaBaseServiceImpl";
	
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
		// 字段
		// {\"columnName\":\"state\",\"attributeName\":\"state\",\"attributeType\":\"Boolean\",\"mappingKind\":\"basic\",\"updateable\":\"1\",\"insertable\":\"1\",\"getterScope\":\"public\",\"setterScope\":\"public\",\"attributeRemark\":\"4\"}
		entitySB.append( parseEntityAttributes( entityKeyGenerator, sequenceName,  attributeArray ) );
		entitySB.append("}").append("\n");
		FileUtil.writeText( entityFullPath, entitySB.toString() ,"UTF-8");
		//生成dao 文件
		FileUtil.isDirExisted( dao_path ,true);
		String daoInterfaceFullPath =dao_path + entityName +"Dao.java";
		String daoImplFullPath =dao_path + entityName +"DaoImpl.java";
		StringBuffer daoSB =new StringBuffer();
		// 包名
		String daoPackageName =dao_path.replace(project_path, "").replace("/", ".");
		daoPackageName =daoPackageName.substring(0,daoPackageName.length() -1);
		daoSB.append("package ").append(daoPackageName+"; \n");
		daoSB.append(dao_import);
		// current entity
		daoSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".")+";\n");
		daoSB.append(writeDaoCode( entityName, attributeArray ));
		FileUtil.writeText( daoInterfaceFullPath, daoSB.toString() );
		daoSB =new StringBuffer();
		String daoImplPackageName =dao_path.replace(project_path, "").replace("/", ".");
		daoImplPackageName =daoImplPackageName.substring(0,daoImplPackageName.length() -1);
		daoSB.append("package ").append(daoImplPackageName+"; \n");
		daoSB.append(daoimpl_import);
		// current entity
		daoSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".")+";\n");
		daoSB.append( writeDaoImplCode(entityName, attributeArray) );
		FileUtil.writeText( daoImplFullPath, daoSB.toString() );
		//生成service 文件
		String serviceInterfaceFullPath =service_path + entityName +"Service.java";
		String serviceImplFullPath =service_path + entityName +"ServiceImpl.java";
		StringBuffer serviceSB =new StringBuffer();
		// 包名
		String servicePackageName =service_path.replace(project_path, "").replace("/", ".");
		servicePackageName =servicePackageName.substring(0,servicePackageName.length() -1);
		serviceSB.append("package ").append(servicePackageName+"; \n");
		// current entity
		serviceSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".")+";\n");
		serviceSB.append(service_import);
		// code
		serviceSB.append( writeServiceCode(entityName, attributeArray) );
		FileUtil.writeText( serviceInterfaceFullPath, serviceSB.toString() );
		serviceSB =new StringBuffer();
		// impl 
		String serviceImplPackageName =service_path.replace(project_path, "").replace("/", ".");
		serviceImplPackageName =serviceImplPackageName.substring(0,serviceImplPackageName.length() -1);
		serviceSB.append("package ").append(serviceImplPackageName+"; \n");
		// current entity
		serviceSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".")+";\n");
		serviceSB.append("import ").append((dao_path + entityName+"Dao").replace(project_path, "").replace("/", ".")+";\n");
		serviceSB.append(serviceimpl_import);
		// code
		serviceSB.append( writeServiceImplCode(entityName, attributeArray) );
		FileUtil.writeText( serviceImplFullPath, serviceSB.toString() );
		//生成admin controller 文件
		//生成api controller 文件
		//生成jsp 文件
		return ovo;
	}
	
	/**
	 * 根据jsonarray 解析出entity类的字段字符串
	 * {\"columnName\":\"state\",\"attributeName\":\"state\",\"attributeType\":\"Boolean\",\"mappingKind\":\"basic\",\"updateable\":\"1\",\"insertable\":\"1\",\"getterScope\":\"public\",\"setterScope\":\"public\",\"attributeRemark\":\"4\"}
	 * @return
	 */
	public static String parseEntityAttributes(String entityKeyGenerator,String sequenceName, JSONArray attributeArray )
	{
		StringBuilder content =new StringBuilder();
		StringBuilder attributesContent =new StringBuilder();
		StringBuilder methodsContent =new StringBuilder();
		for (int i=0 ; i< attributeArray.size();i++) {
			JSONObject json =attributeArray.getJSONObject(i);
			// 申明属性
			attributesContent.append("/** "+ json.getString("attributeRemark") +" */").append("\n");
			attributesContent.append("private "+ json.getString("attributeType") +" "+json.getString("attributeName") +";").append("\n\n");
			// 构建属性的方法
			String mappingKind =json.getString("mappingKind");
			if (mappingKind.equals("id")) {
				methodsContent.append("@Id").append("\n");
				methodsContent.append("@GeneratedValue(strategy = GenerationType."+entityKeyGenerator+")").append("\n");;
			}
			else if (mappingKind.equals("basic")) {
				String updateable =json.getString("updateable");
				String updateableValue ="";
				if( updateable.equals("1") ){
					updateableValue ="true";
				}
				else{
					updateableValue ="false";
				}
				methodsContent.append("@Column(name = \""+json.getString("columnName")+"\",updatable = "+updateableValue+")").append("\n");
			}
			else if (mappingKind.equals("version")) {
				methodsContent.append("@Version(name = \""+json.getString("columnName")+"\")").append("\n");
			}
			// getter
			methodsContent.append(json.getString("getterScope")).append(" ").append(json.getString("attributeType")).append(" ");
			String attrName =json.getString("attributeName");
			String methodAttrName =attrName.substring(0,1).toUpperCase()+attrName.substring(1);
			String getterMethodName ="get" + methodAttrName;
			methodsContent.append(getterMethodName+"() {").append("\n").append("     ").append("return "+attrName+";").append("\n}\n\n");
			// setter
			String setterMethodName ="set" + methodAttrName;
			methodsContent.append(json.getString("setterScope")).append(" ").append("void").append(" ");
			methodsContent.append(setterMethodName+"( "+json.getString("attributeType")+" "+ json.getString("attributeName") +" ) {").append("\n").append("     ").append("this."+attrName+" ="+attrName+";").append("\n}\n\n");
		}
		content.append(attributesContent.toString()).append(methodsContent.toString());
		return content.toString();
	}
	
	
	/**
	 * 生成Dao 类文件
	 * @param attributeArray
	 * @return
	 */
	public static String writeDaoCode(String entityName, JSONArray attributeArray ){
		StringBuilder content =new StringBuilder();
		String keyType ="Long";
		for (int i=0 ; i< attributeArray.size();i++) 
		{
			JSONObject json =attributeArray.getJSONObject(i);
			String mappingKind =json.getString("mappingKind");
			if (mappingKind.equals("id")) {
				keyType =json.getString("attributeType");
				break;
			}
		}
		content.append("public interface ").append(entityName+"Dao").append(" extends ").append(base_dao).append("<"+entityName+", "+keyType+">");
		content.append("{\n}\n");
		return content.toString();
	}
	
	/**
	 * 生成DaoImpl 类文件
	 * @param attributeArray
	 * @return
	 */
	public static String writeDaoImplCode(String entityName, JSONArray attributeArray ){
		StringBuilder content =new StringBuilder();
		String keyType ="Long";
		for (int i=0 ; i< attributeArray.size();i++) 
		{
			JSONObject json =attributeArray.getJSONObject(i);
			String mappingKind =json.getString("mappingKind");
			if (mappingKind.equals("id")) {
				keyType =json.getString("attributeType");
				break;
			}
		}
//		@Service("xxxx")
		content.append("@Repository(\""+entityName+"JpaDao\") ").append("\n");
		content.append("public class ").append(entityName+"DaoImpl").append(" extends ").append(base_dao_impl).append("<"+entityName+", "+keyType+">");
		content.append(" implements ").append(entityName+"Dao").append("{\n}\n");
		return content.toString();
	}
	
	public static String writeServiceCode(String entityName, JSONArray attributeArray ){
		StringBuilder content =new StringBuilder();
		String keyType ="Long";
		for (int i=0 ; i< attributeArray.size();i++) 
		{
			JSONObject json =attributeArray.getJSONObject(i);
			String mappingKind =json.getString("mappingKind");
			if (mappingKind.equals("id")) {
				keyType =json.getString("attributeType");
				break;
			}
		}
		content.append("public interface ").append(entityName+"Service").append(" extends ").append(base_service).append("<"+entityName+", "+keyType+">");
		content.append("{\n}\n");
		return content.toString();
	}
	
	public static String writeServiceImplCode(String entityName, JSONArray attributeArray ){
		StringBuilder content =new StringBuilder();
		String keyType ="Long";
		for (int i=0 ; i< attributeArray.size();i++) 
		{
			JSONObject json =attributeArray.getJSONObject(i);
			String mappingKind =json.getString("mappingKind");
			if (mappingKind.equals("id")) {
				keyType =json.getString("attributeType");
				break;
			}
		}
//		@Service("xxxx")
		content.append("@Repository(\""+entityName+"JpaService\") ").append("\n");
		content.append("public class ").append(entityName+"ServiceImpl").append(" extends ").append(base_service_impl).append("<"+entityName+", "+keyType+">");
		content.append(" implements ").append(entityName+"Service").append("{\n");
		
		content.append(" @Resource(name = \""+entityName+"JpaDao\") \n");
		String attrName =entityName.substring(0,1).toLowerCase()+entityName.substring(1);
		content.append(" private "+entityName+"Dao "+attrName+"Dao; \n");
		content.append(" @Resource(name = \""+entityName+"JpaDao\") \n");
		content.append(" public void setBaseDao("+entityName+"Dao "+attrName+"Dao) { \n");
		content.append(" super.setBaseDao("+attrName+"Dao); \n");
		content.append(" } \n");
		content.append(" } \n");
		return content.toString();
	}
}
