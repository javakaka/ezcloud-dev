package com.ezcloud.framework.util;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezcloud.framework.page.jdbc.Page;
import com.ezcloud.framework.page.jdbc.Pageable;
import com.ezcloud.framework.service.system.Language;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 代码工具类
 * @author kaka
 */
public class CodeEngineUtil {
	private final static String entity_import ="import java.util.*;"+"\nimport javax.persistence.*;\nimport java.io.*;\n";
	private final static String dao_import ="import com.ezcloud.framework.dao.JpaBaseDao;";
	private final static String daoimpl_import ="import com.ezcloud.framework.dao.JpaBaseDaoImpl;\nimport org.springframework.stereotype.*;\nimport javax.persistence.*;";
	private final static String base_dao ="JpaBaseDao";
	private final static String base_dao_impl ="JpaBaseDaoImpl";
	private final static String service_import ="import java.util.*;\nimport com.ezcloud.framework.service.JpaBaseService;\n";
	private final static String serviceimpl_import ="import com.ezcloud.framework.service.JpaBaseServiceImpl;\nimport org.springframework.stereotype.*;\nimport org.springframework.transaction.annotation.*;\nimport javax.persistence.*;\nimport javax.annotation.Resource;\n";
	private final static String base_service ="JpaBaseService";
	private final static String base_service_impl ="JpaBaseServiceImpl";
	private final static String controller_admin_import ="import javax.annotation.*;\n"
		+"import org.springframework.stereotype.Controller;\n "
		+"import org.springframework.ui.*;\n "
		+"import org.springframework.web.bind.annotation.*;\n "
		+"import org.springframework.web.servlet.mvc.support.*;\n "
		+"import com.ezcloud.framework.page.orm.Page;\n"
		+"import com.ezcloud.framework.page.orm.Pageable;\n "
		+"import com.ezcloud.framework.controller.BaseController; \n "
		+"import com.ezcloud.framework.util.*;\n"
		+"import org.apache.log4j.*;\n";
	
	private final static String base_controller_admin ="BaseController";
	private final static String controller_api_import =" import javax.annotation.*; \n "
		+" import javax.servlet.http.*; \n "
		+" import org.apache.log4j.*;\n "
		+" import org.springframework.stereotype.*;\n  "
		+" import org.springframework.web.bind.annotation.*; \n  "
		+" import com.ezcloud.framework.util.*; \n "
		+" import com.ezcloud.framework.vo.*; \n "
		+" import com.ezcloud.framework.controller.ApiBaseController; \n "
		+" import com.ezcloud.framework.exp.*; \n "
		+" import java.sql.*; \n "
		+" import java.util.*; \n "
		+" import java.text.*; \n ";
	
	private final static String base_controller_api ="ApiBaseController";
	
	private static Logger logger =Logger.getLogger( CodeEngineUtil.class );
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
		String  deleteApiWhereFields=paramJson.getString( "deleteApiWhereFields" ).replace( "delete-api-where-field=", "" );
		//生成entity 文件
		FileUtil.isDirExisted( entity_path ,true);
		String entityFullPath =entity_path + entityName +".java";
		StringBuffer entitySB =new StringBuffer();
		// 包名
		project_path =project_path.replace( "\\", "/" );
		String entityPackageName =entity_path.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "");
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
		String daoPackageName =dao_path.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "");
		daoPackageName =daoPackageName.substring(0,daoPackageName.length() -1);
		daoSB.append("package ").append(daoPackageName+"; \n");
		daoSB.append(dao_import);
		// current entity
		daoSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".").replace(".src.main.java.", "")+";\n");
		daoSB.append(writeDaoCode( entityName, attributeArray ));
		FileUtil.writeText( daoInterfaceFullPath, daoSB.toString() );
		daoSB =new StringBuffer();
		String daoImplPackageName =dao_path.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "");
		daoImplPackageName =daoImplPackageName.substring(0,daoImplPackageName.length() -1);
		daoSB.append("package ").append(daoImplPackageName+"; \n");
		daoSB.append(daoimpl_import);
		// current entity
		daoSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".").replace(".src.main.java.", "")+";\n");
		daoSB.append( writeDaoImplCode(entityName, attributeArray) );
		FileUtil.writeText( daoImplFullPath, daoSB.toString() );
		//生成service 文件
		String serviceInterfaceFullPath =service_path + entityName +"Service.java";
		String serviceImplFullPath =service_path + entityName +"ServiceImpl.java";
		StringBuffer serviceSB =new StringBuffer();
		// 包名
		String servicePackageName =service_path.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "");
		servicePackageName =servicePackageName.substring(0,servicePackageName.length() -1);
		serviceSB.append("package ").append(servicePackageName+"; \n");
		// current entity
		serviceSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".").replace(".src.main.java.", "")+";\n");
		serviceSB.append(service_import);
		// code
		serviceSB.append( writeServiceCode(entityName, attributeArray) );
		FileUtil.writeText( serviceInterfaceFullPath, serviceSB.toString() );
		serviceSB =new StringBuffer();
		// impl 
		String serviceImplPackageName =service_path.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "");
		serviceImplPackageName =serviceImplPackageName.substring(0,serviceImplPackageName.length() -1);
		serviceSB.append("package ").append(serviceImplPackageName+"; \n");
		// current entity
		serviceSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".").replace(".src.main.java.", "")+";\n");
		serviceSB.append("import ").append((dao_path + entityName+"Dao").replace(project_path, "").replace("/", ".").replace(".src.main.java.", "")+";\n");
		serviceSB.append(serviceimpl_import);
		// code
		serviceSB.append( writeServiceImplCode(entityName, attributeArray) );
		FileUtil.writeText( serviceImplFullPath, serviceSB.toString() );
		//生成admin controller 文件
		String adminControllerFullPath =controller_path + entityName +"Controller.java";
		StringBuffer adminControllerSB =new StringBuffer();
		// 包名
		String adminControllerPackageName =controller_path.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "");
		adminControllerPackageName =adminControllerPackageName.substring(0,adminControllerPackageName.length() -1);
		adminControllerSB.append("package ").append(adminControllerPackageName+"; \n");
		// import  entity service common classes
		adminControllerSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".").replace(".src.main.java.", "")+";\n");
		adminControllerSB.append("import ").append(serviceInterfaceFullPath.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "").replace(".java", "")+";\n");
		adminControllerSB.append(controller_admin_import);
		// code 
		adminControllerSB.append( writeAdminControllerCode(entityName, paramJson) );
		FileUtil.writeText( adminControllerFullPath, adminControllerSB.toString() );
		//生成api controller 文件
		String apiControllerFullPath =api_path + entityName +"Controller.java";
		StringBuffer apiControllerSB =new StringBuffer();
		// 包名
		String apiControllerPackageName =api_path.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "");
		apiControllerPackageName =apiControllerPackageName.substring(0,apiControllerPackageName.length() -1);
		apiControllerSB.append("package ").append(apiControllerPackageName+"; \n");
		// import  entity service common classes
		apiControllerSB.append("import ").append((entity_path + entityName).replace(project_path, "").replace("/", ".").replace(".src.main.java.", "")+";\n");
		apiControllerSB.append("import ").append(serviceInterfaceFullPath.replace(project_path, "").replace("/", ".").replace(".src.main.java.", "").replace(".java", "")+";\n");
		apiControllerSB.append(controller_api_import);
		apiControllerSB.append(writeApiControllerCode(entityName, paramJson));
		FileUtil.writeText( apiControllerFullPath, apiControllerSB.toString() );
		//生成jsp 文件
		writeJspPage(entityName, paramJson);
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
		String daoAttributeName =attrName +"Dao";
		content.append(" private "+entityName+"Dao "+daoAttributeName+"; \n");
		content.append(" @Resource(name = \""+entityName+"JpaDao\") \n");
		content.append(" public void setBaseDao("+entityName+"Dao "+attrName+"Dao) { \n");
		content.append(" super.setBaseDao("+attrName+"Dao); \n");
		content.append(" } \n");
		content.append(" } \n");
		return content.toString();
	}
	
	/**
	 * 后台管理控制器代码
	 * @param entityName
	 * @param attributeArray
	 * @return
	 */
	public static String writeAdminControllerCode(String entityName, JSONObject paramJson ){
		StringBuilder content =new StringBuilder();
		String adminControllerName =entityName +"Controller";
//		@Controller("frameworkLanguageController")
//		@RequestMapping("/system/language")
		content.append("@Controller(\""+entityName+"JpaAdminController\") ").append("\n");
		String  adminControllerUri=paramJson.getString( "adminControllerUri" );
		String  listPageUri=paramJson.getString( "listPageUri" );
		String  listPageMethod=paramJson.getString( "listPageMethod" );
		String  listPageRemark=paramJson.getString( "listPageRemark" );
		content.append("@RequestMapping(\""+adminControllerUri+"\")").append("\n");
		content.append("public class ").append(entityName+"Controller").append(" extends ").append(base_controller_admin).append(" {\n\n");
		String attrName =entityName.substring(0,1).toLowerCase()+entityName.substring(1);
		String entityVariableName =attrName;
		String serviceVariableName =attrName+"Service";
		String serviceClassName =entityName+"Service";
		// variable
		content.append(" @Resource(name = \""+entityName+"Service\") \n");
		content.append(" private "+serviceClassName+" "+serviceVariableName+"; \n");
		content.append("\n");
		// logger
		content.append("private static Logger logger =Logger.getLogger( "+adminControllerName+".class ); \n");
		//根据jsp存放目录，计算出jsp文件相对于项目根路径的目录名称
		String listPageViewUri ="";
		String addPageViewUri ="";
		String editPageViewUri ="";
		String jsp_path =paramJson.getString( "jsp_path" );
		String jsp_path_root ="";
		// 如果是mvn项目，则会存在目录webapp,如果是eclipse j2ee项目，则会存在WebContent目录
		int pos =jsp_path.indexOf( "webapp" );
		if ( pos != -1 ) {
			jsp_path_root =jsp_path.substring( pos +"webapp".length() );
			listPageViewUri =jsp_path_root + "/list";
			addPageViewUri =jsp_path_root + "/add";
			editPageViewUri =jsp_path_root + "/edit";
		}
		else{
			pos =jsp_path.indexOf( "WebContent" );
			jsp_path_root =jsp_path.substring( pos +"WebContent".length() );
			listPageViewUri =jsp_path_root + "/list";
			addPageViewUri =jsp_path_root + "/add";
			editPageViewUri =jsp_path_root + "/edit";
		}
		// list page
		if( StringUtil.isEmptyOrNull( listPageUri )){
			listPageUri ="list";
		}
		if( StringUtil.isEmptyOrNull( listPageMethod )){
			listPageMethod ="list";
		}
		content.append("	@RequestMapping(value = \"/"+listPageUri+"\") ").append("\n");
		content.append("	public String ").append( listPageMethod +" ( Pageable pageable, ModelMap model )  { ").append("\n");
		content.append("		Page page = "+ serviceVariableName +".findPage( pageable ); ").append("\n");
		content.append("		model.addAttribute(\"page\", page); ").append("\n");
		content.append("		return \""+listPageViewUri+"\"; ").append("\n");
		content.append("	}").append("\n");
		
		// add page
		String  addPageUri=paramJson.getString( "addPageUri" );
		String  addPageMethod=paramJson.getString( "addPageMethod" );
		String  addPageRemark=paramJson.getString( "addPageRemark" );
		String  addPageShowFields=paramJson.getString( "addPageShowFields" ).replace( "add-page-show-field=", "" );
		if ( StringUtil.isEmptyOrNull( addPageUri ) ) {
			addPageUri ="add";
		}
		if ( StringUtil.isEmptyOrNull( addPageMethod ) ) {
			addPageMethod ="add";
		}
		content.append("@RequestMapping(value = \"/"+ addPageUri +"\")").append("\n");
		content.append("public String "+ addPageMethod +"(ModelMap model) {").append("\n");
		content.append("	return \""+addPageViewUri+"\";").append("\n");
		content.append("}").append("\n");
		
		// save method
		content.append("@RequestMapping(value = \"/save\", method = RequestMethod.POST)").append("\n");
		content.append("public String save ("+entityName+" "+entityVariableName+", RedirectAttributes redirectAttributes) {").append("\n");
		content.append("	logger.info(\"save "+entityName+"...............\");" ).append("\n");
		content.append("	"+entityVariableName+".setCreateTime(DateUtil.getCurrentDateTime()) ;" ).append("\n");
		content.append("	"+serviceVariableName+".save("+ entityVariableName +") ;" ).append("\n");
		content.append("	addFlashMessage( redirectAttributes, Message.success( \"success\") );").append("\n");
		content.append("	return \"redirect:"+ listPageUri +".jhtml\";").append("\n");
		content.append("}	").append("\n");
		// edit page
		String  editPageUri=paramJson.getString( "editPageUri" );
		String  editPageMethod=paramJson.getString( "editPageMethod" );
		String  editPageRemark=paramJson.getString( "editPageRemark" );
		String  editPageShowFields=paramJson.getString( "editPageShowFields" ).replace( "edit-page-show-field=", "" );
		if ( StringUtil.isEmptyOrNull( editPageUri ) ) {
			editPageUri ="edit";
		}
		if ( StringUtil.isEmptyOrNull( editPageMethod ) ) {
			editPageMethod ="add";
		}
		content.append("@RequestMapping(value = \"/"+ editPageUri +"\")").append("\n");
		content.append("public String edit(Long id, ModelMap model) {").append("\n");
		content.append("model.addAttribute(\"entity\", "+serviceVariableName+".find(id));").append("\n");
		content.append("").append("\n");
		content.append("return \""+editPageViewUri+"\";").append("\n");
		content.append("}").append("\n");
		content.append("").append("\n");
		// update method
		content.append("@RequestMapping(value = \"/update\")").append("\n");
		content.append("public String update("+ entityName +" " + entityVariableName + ", ModelMap model, RedirectAttributes redirectAttributes) {").append("\n");
		content.append("	"+ entityVariableName +".setUpdateTime(DateUtil.getCurrentDateTime()) ;").append("\n");
		content.append("	"+serviceVariableName+".update("+ entityVariableName +");").append("\n");
		content.append("	addFlashMessage( redirectAttributes, Message.success( \"success\") );").append("\n");
		content.append("	return \"redirect:"+ listPageUri +".jhtml\";").append("\n");
		content.append("}	").append("\n");
		// delete method
		String  deletePageUri=paramJson.getString( "deletePageUri" );
		String  deletePageMethod=paramJson.getString( "deletePageMethod" );
		String  deletePageRemark=paramJson.getString( "deletePageRemark" );
		String  deletePageShowFields=paramJson.getString( "deletePageShowFields" ).replace( "delete-page-where-field=", "" );
		if ( StringUtil.isEmptyOrNull( deletePageUri ) ) {
			deletePageUri ="delete";
		}
		if ( StringUtil.isEmptyOrNull( deletePageMethod ) ) {
			deletePageMethod ="delete";
		}
		content.append("@RequestMapping(value = \"/"+ deletePageUri +"\")").append("\n");
		content.append("public @ResponseBody Message "+deletePageMethod+"(Long[] ids) {").append("\n");
		content.append("	"+serviceVariableName+".delete( ids );").append("\n");
		content.append("	return SUCCESS_MESSAGE ;").append("\n");
		content.append("	}	").append("\n");
		
		content.append("}	").append("\n");
		return content.toString();
	}
	
	/**
	 * 
	 * @param entityName
	 * @param paramJson
	 * @return
	 */
	public static void writeJspPage(String entityName, JSONObject paramJson ){
		StringBuilder content =new StringBuilder();
		String jsp_path =paramJson.getString( "jsp_path" );
		String pluginVersion =null;
		try {
			pluginVersion =paramJson.getString( "plugin_version" );
		}
		catch (Exception e) {
			pluginVersion =null;
		}
		if ( StringUtil.isEmptyOrNull( pluginVersion ) ) {
			pluginVersion ="1.0";
		}
		// 列表页面
		String listPagePath =jsp_path+"/list.jsp";
		String classRealPath =CodeEngineUtil.class.getClassLoader().getResource("").getPath();
		String rootPath ="";
		String osName =OSUtil.getOSName();
		if ( osName.equals( "windwos" ) ) {
			rootPath = classRealPath.substring(1,classRealPath.indexOf("/WEB-INF/classes"));
		}
		else{
			rootPath = classRealPath.substring(0,classRealPath.indexOf("/WEB-INF/classes"));
		}
		String robotJarRealPath = rootPath+"/WEB-INF/lib/ezcloud-robot-"+pluginVersion+".jar";
		String listPageTplContent =FileUtil.readFileFromJarByJarName( robotJarRealPath , "robot-template/default/list.jsp" );
		String  listPageFunctionBtns=paramJson.getString( "listPageFunctionBtns" ).replace( "list-page-function-btn=", "" );
		/** 页面功能按钮**/
		boolean hasAddBtn =false;
		String addBtnHtml ="";
		if ( listPageFunctionBtns.indexOf( "add" ) != -1 ) {
			hasAddBtn =true;
			addBtnHtml ="<a href=\"add.do\" class=\"iconButton\">"
				+"<span class=\"addIcon\">&nbsp;</span><cc:message key=\"admin.common.add\" />"
				+"</a>";
			listPageTplContent =listPageTplContent.replace( "${addButtonHtml}", addBtnHtml );
		}
		boolean hasDeleteBtn =false;
		String deleteBtnHtml ="";
		if ( listPageFunctionBtns.indexOf( "delete" ) != -1 ) {
			hasDeleteBtn =true;
			deleteBtnHtml="<a href=\"javascript:;\" id=\"deleteButton\" class=\"iconButton disabled\"> \n"
					+"<span class=\"deleteIcon\">&nbsp;</span><cc:message key=\"admin.common.delete\" /> \n"
					+"</a> \n";
			listPageTplContent =listPageTplContent.replace( "${deleteButtonHtml}", deleteBtnHtml );
		}
		boolean hasRefreshBtn =false;
		String refreshBtnHtml ="";
		if ( listPageFunctionBtns.indexOf( "refresh" ) != -1 ) {
			hasRefreshBtn =true;
			refreshBtnHtml ="<a href=\"javascript:;\" id=\"refreshButton\" class=\"iconButton\"> \n"
			+"<span class=\"refreshIcon\">&nbsp;</span><cc:message key=\"admin.common.refresh\" />"
			+"</a> \n";
			listPageTplContent =listPageTplContent.replace( "${refreshButtonHtml}", refreshBtnHtml );
		}
		/** 搜索块 **/
		String  listPageSearchFields=paramJson.getString( "listPageSearchFields" ).replace( "list-page-search-field=", "" );
		String searchFieldArray[] =null;
		if ( ! StringUtil.isEmptyOrNull( listPageSearchFields ) ) {
			searchFieldArray =listPageSearchFields.split( "&" );
		}
		String searchHtml ="";
		searchHtml+="<div class=\"menuWrap\"> \n";
		searchHtml+="<div class=\"search\"> \n";
		searchHtml+="	<span id=\"searchPropertySelect\" class=\"arrow\">&nbsp;</span> \n";
		searchHtml+="	<input type=\"text\" id=\"searchValue\" name=\"searchValue\" value=\"${page.searchValue}\" maxlength=\"200\" /> \n";
		searchHtml+="	<button type=\"submit\">&nbsp;</button> \n";
		searchHtml+="	</div> \n";
		searchHtml+="	<div class=\"popupMenu\"> \n";
		searchHtml+="	<ul id=\"searchPropertyOption\"> \n";
		searchHtml+="	";
		for ( int i = 0; i < searchFieldArray.length; i++ ) {
			searchHtml+=" <li>  \n";
			searchHtml+=" <a href=\"javascript:;\" <c:if test=\"${page.searchProperty == '"+searchFieldArray[i]+"'}\"> class=\"current\"</c:if> val=\""+searchFieldArray[i]+"\"></a>  \n";
			searchHtml+=" </li>  \n";
		}
		searchHtml+="	</ul> \n";
		searchHtml+="	</div> \n";
		searchHtml+="	</div> \n";
		listPageTplContent =listPageTplContent.replace( "${searchHtml}", searchHtml );
		/** 列表块 表头**/
		String  listPageShowFields=paramJson.getString( "listPageShowFields" ).replace( "list-page-show-field=", "" );
		String showFieldArray[] =null;
		if ( !StringUtil.isEmptyOrNull( listPageShowFields ) ) {
			showFieldArray =listPageShowFields.split( "&" );
		}
		String tableHtml ="";
		tableHtml +="<table id=\"listTable\" class=\"list\"> \n";
		tableHtml +="<tr> \n";
		tableHtml +="<th class=\"check\"> \n";
		tableHtml +="<input type=\"checkbox\" id=\"selectAll\" /> \n";
		tableHtml +="</th> \n";
		for ( int i = 0; i < showFieldArray.length; i++ ) {
			tableHtml +="<th> \n";
			tableHtml +="<a href=\"javascript:;\" class=\"sort\" name=\""+showFieldArray[i]+"\">"+showFieldArray[i]+"</a> \n";
			tableHtml +="</th> \n";
		}
		tableHtml +="<th> \n";
		tableHtml +="<span><cc:message key=\"admin.common.handle\" /></span> \n";
		tableHtml +="</th> \n";
		tableHtml +="</tr> \n";
		/** 列表块 表数据**/
		tableHtml +="<c:forEach items=\"${page.content}\" var=\"row\" varStatus=\"status\"> \n";
		tableHtml +="<tr> \n";
		tableHtml +="<td> \n";
		tableHtml +="<input type=\"checkbox\" name=\"ids\" value=\"${row.id}@${row.id}\" /> \n";
		tableHtml +="</td> \n";
		for ( int i = 0; i < showFieldArray.length; i++ ) {
			tableHtml +="<td> \n";
			tableHtml +="<span title=\"${row."+showFieldArray[i]+"}\">${row."+showFieldArray[i]+"}</span> \n";
			tableHtml +="</td> \n";
		}
		tableHtml +="<td> \n";
		tableHtml +="<a href=\"edit.do?id=${row.id}\"><cc:message key=\"admin.common.edit\" /></a> \n";
		tableHtml +="</td> \n";
		tableHtml +="</tr> \n";
		tableHtml +="</c:forEach> \n";
		tableHtml +="</table> \n";
		listPageTplContent =listPageTplContent.replace( "${listTableHtml}", tableHtml );
		FileUtil.writeText( listPagePath, listPageTplContent );

		// 新增页面
		String addPagePath =jsp_path+"/add.jsp";
		String addPageTplContent =FileUtil.readFileFromJarByJarName( robotJarRealPath , "robot-template/default/add.jsp" );
		String  addPageShowFields=paramJson.getString( "addPageShowFields" ).replace( "add-page-show-field=", "" );
		String addPageFieldsArray[] =null;
		if ( ! StringUtil.isEmptyOrNull( addPageShowFields ) ) {
			addPageFieldsArray =addPageShowFields.split( "&" );
		}
		String addPageFormHtml ="";
		addPageFormHtml +="<form id=\"inputForm\" action=\"save.do\" method=\"post\">\n";
		addPageFormHtml +="<table class=\"input\">\n";
		addPageFormHtml +="";
		for ( int i = 0; i < addPageFieldsArray.length; i++ ) {
			addPageFormHtml +="<tr>\n";
			addPageFormHtml +="<th>\n";
			addPageFormHtml +="<span class=\"requiredField\">*</span>"+addPageFieldsArray[i]+":\n";
			addPageFormHtml +="</th>\n";
			addPageFormHtml +="<td>\n";
			addPageFormHtml +="<input type=\"text\" name=\""+addPageFieldsArray[i]+"\" class=\"text\" value=\"\" maxlength=\"200\" />\n";
			addPageFormHtml +="</td>\n";
			addPageFormHtml +="</tr>\n";
		}
		addPageFormHtml +="<tr>\n";
		addPageFormHtml +="<th>\n&nbsp;</th>\n";
		addPageFormHtml +="<td>\n";
		addPageFormHtml +="<input type=\"submit\" class=\"button\" value=\"<cc:message key=\"admin.common.submit\" />\" />\n";
		addPageFormHtml +="<input type=\"button\" id=\"backButton\" class=\"button\" value=\"<cc:message key=\"admin.common.back\" />\" />\n";
		addPageFormHtml +="</td>\n";
		addPageFormHtml +="</tr>\n";
		addPageFormHtml +="</table>\n";
		addPageFormHtml +="</form>\n";
		addPageFormHtml +="";
		addPageTplContent =addPageTplContent.replace( "${addPageFormHtml}" , addPageFormHtml );
		FileUtil.writeText( addPagePath, addPageTplContent );
		// 编辑页面
		String editPagePath =jsp_path+"/edit.jsp";
		String editPageTplContent =FileUtil.readFileFromJarByJarName( robotJarRealPath , "robot-template/default/edit.jsp" );
		String  editPageShowFields=paramJson.getString( "editPageShowFields" ).replace( "edit-page-show-field=", "" );
		String editPageFieldArray [] =null;
		if ( ! StringUtil.isEmptyOrNull( editPageShowFields ) ) {
			editPageFieldArray =editPageShowFields.split( "&" );
		}
		String editPageFormHtml ="";
		editPageFormHtml +="<form id=\"inputForm\" action=\"update.do\" method=\"post\"> \n";
		editPageFormHtml +="<input type=\"hidden\" name=\"id\" class=\"text\" value=\"${entity.id}\"  /> \n";
		editPageFormHtml +="<table class=\"input\"> \n";
		for ( int i = 0; i < editPageFieldArray.length; i++ ) {
			
			editPageFormHtml +="<tr> \n";
			editPageFormHtml +="<th> \n";
			editPageFormHtml +="<span class=\"requiredField\">*</span>"+editPageFieldArray[i]+": \n";
			editPageFormHtml +="</th> \n";
			editPageFormHtml +="<td> \n";
			editPageFormHtml +="<input type=\"text\" name=\""+editPageFieldArray[i]+"\" class=\"text\" value=\"${entity."+editPageFieldArray[i]+"}\" maxlength=\"200\" /> \n";
			editPageFormHtml +="</td> \n";
			editPageFormHtml +="</tr> \n";
		}
		editPageFormHtml +="<tr> \n";
		editPageFormHtml +="<th>&nbsp;</th> \n";
		editPageFormHtml +="<td> \n";
		editPageFormHtml +="<input type=\"submit\" class=\"button\" value=\"提交\" /> \n";
		editPageFormHtml +="<input type=\"button\" id=\"backButton\" class=\"button\" value=\"返回\" /> \n";
		editPageFormHtml +="</td> \n";
		editPageFormHtml +="</tr> \n";
		editPageFormHtml +="</table> \n";
		editPageFormHtml +="</form> \n";
		editPageTplContent =editPageTplContent.replace( "${editPageFormHtml}" , editPageFormHtml );
		FileUtil.writeText( editPagePath, editPageTplContent );
	}
	
	public static String writeApiControllerCode(String entityName, JSONObject paramJson ){
		StringBuilder content =new StringBuilder();
		String ClassName =entityName +"Controller";
		String controllerName =entityName+"JpaApiController";
//		@Controller("frameworkLanguageController")
//		@RequestMapping("/system/language")
		String  apiControllerUri=paramJson.getString( "apiControllerUri" );
		
		content.append("@Controller(\""+controllerName+"\") ").append("\n");
		content.append("@RequestMapping(\""+apiControllerUri+"\")").append("\n");
		content.append("public class ").append(entityName+"Controller").append(" extends ").append(base_controller_api).append(" {\n\n");
		String attrName =entityName.substring(0,1).toLowerCase()+entityName.substring(1);
		String entityVariableName =attrName;
		String serviceVariableName =attrName+"Service";
		String serviceClassName =entityName+"Service";
		// variable
		content.append(" @Resource(name = \""+entityName+"Service\") \n");
		content.append(" private "+serviceClassName+" "+serviceVariableName+"; \n");
		content.append("\n");
		// logger
		content.append("private static Logger logger =Logger.getLogger( "+ClassName+".class ); \n");
		// list api
		String  listApiUri=paramJson.getString( "listApiUri" );
		String  listApiMethod=paramJson.getString( "listApiMethod" );
		String  listApiRemark=paramJson.getString( "listApiRemark" );
		String  listApiShowFields=paramJson.getString( "listApiShowFields" ).replace( "list-api-show-field=", "" );
		
		if (  StringUtil.isEmptyOrNull( listApiUri ) ) {
			listApiUri ="list";
		}
		if (  StringUtil.isEmptyOrNull( listApiMethod ) ) {
			listApiMethod ="list";
		}
		String fieldArray[] =null;
		if ( ! StringUtil.isEmptyOrNull( listApiShowFields ) ) {
			fieldArray =listApiShowFields.split( "&" );
		}
		content.append("	@RequestMapping(value = \"/"+listApiUri+"\") ").append("\n");
		content.append("	public @ResponseBody String ").append( listApiMethod +" ( HttpServletRequest request ) throws JException  { ").append("\n");
		content.append("		OVO ovo =new OVO(); ").append("\n");
		content.append("		IVO ivo =parseRequest(request); ").append("\n");
		content.append("		String page =ivo.getString(\"page\",\"1\"); ").append("\n");
		content.append("		String page_size =ivo.getString(\"page_size\",\"15\"); ").append("\n");
		content.append("		Integer count =Integer.parseInt( page_size );").append("\n");
		content.append("		Integer first =(Integer.parseInt( page ) -1) * count; ").append("\n");
		content.append("		List<"+ entityName +"> list ="+serviceVariableName+".findList(first, count, null, null); ").append("\n");
		content.append("		DataSet ds =new DataSet(); ").append("\n");
		content.append("		for(int i=0; i< list.size(); i++ ){ ").append("\n");
		content.append("		 	Row row =new Row();").append("\n");
		content.append("		 	"+entityName+" item =("+ entityName +")list.get(i);").append("\n");
		for ( int i = 0; i < fieldArray.length; i++ ) {
			String fieldName =fieldArray[i];
			System.out.println( "------------------->>" + fieldName);
			System.out.println( "fieldArray------------------->>" + fieldArray.length);
			String methodName ="get"+StringUtil.convertFirstCharToUpperCase( fieldName )+"()";
			content.append("		 	row.put(\"id\", String.valueOf( item."+methodName+" ));").append("\n");
		}
		content.append("		 ds.add(row);").append("\n");
		content.append("		 }//end of for loop").append("\n");
		content.append("		 ovo =new OVO(0,\"操作成功\",\"\");").append("\n");
		content.append("		 ovo.set(\"list\", ds);").append("\n");
		content.append("		 return AesUtil.encode(VOConvert.ovoToJson(ovo));").append("\n");
		content.append("	}").append("\n");
		
		// add api
		String  addApiUri=paramJson.getString( "addApiUri" );
		String  addApiMethod=paramJson.getString( "addApiMethod" );
		String  addApiRemark=paramJson.getString( "addApiRemark" );
		String  addApiShowFields=paramJson.getString( "addApiShowFields" ).replace( "add-api-show-field=", "" );
		if ( StringUtil.isEmptyOrNull( addApiUri ) ) {
			addApiUri ="add";
		}
		if ( StringUtil.isEmptyOrNull( addApiMethod ) ) {
			addApiMethod ="add";
		}
		String addFieldArray [] =null;
		if ( ! StringUtil.isEmptyOrNull( addApiShowFields ) ) {
			addFieldArray =addApiShowFields.split( "&" );
		}
		content.append("@RequestMapping(value = \"/"+ addApiUri +"\")").append("\n");
		content.append("public @ResponseBody String "+ addApiMethod +"(HttpServletRequest request) throws JException {").append("\n");
		content.append("	OVO ovo =new OVO();\n");
		content.append("	IVO ivo =parseRequest(request);\n");
		content.append("	"+entityName+" entity =new "+entityName+"();\n");
		for ( int i = 0; i < addFieldArray.length; i++ ) {
			String addFieldName =addFieldArray[i];
			String setAddFieldMethodName ="set"+StringUtil.convertFirstCharToUpperCase( addFieldName );
			content.append("	String "+addFieldName+" =ivo.getString(\""+addFieldName+"\",null);\n");
			content.append("	entity."+setAddFieldMethodName+"("+addFieldName+");\n");
		}
		content.append("	"+serviceVariableName+".save( entity );\n");
		content.append("	ovo =new OVO(0,\"操作成功\",\"\");\n");
		content.append("	return AesUtil.encode(VOConvert.ovoToJson(ovo));").append("\n");
		content.append("}").append("\n");
		// edit api
		String  editApiUri=paramJson.getString( "editApiUri" );
		String  editApiMethod=paramJson.getString( "editApiMethod" );
		String  editApiRemark=paramJson.getString( "editApiRemark" );
		String  editApiShowFields=paramJson.getString( "editApiShowFields" ).replace( "edit-api-show-field=", "" );
		if ( StringUtil.isEmptyOrNull( editApiUri ) ) {
			editApiUri ="edit";
		}
		if ( StringUtil.isEmptyOrNull( editApiMethod ) ) {
			editApiMethod ="edit";
		}
		String editFieldArray [] =null;
		if ( ! StringUtil.isEmptyOrNull( editApiShowFields ) ) {
			editFieldArray =editApiShowFields.split( "&" );
		}
		content.append("@RequestMapping(value = \"/"+ editApiUri +"\")").append("\n");
		content.append("public @ResponseBody String "+editApiMethod+"( HttpServletRequest request ) throws JException {").append("\n");
		content.append("	OVO ovo =new OVO(0,\"\",\"\");").append("\n");
		content.append("	IVO ivo =parseRequest(request);").append("\n");
		content.append("	String id=ivo.getString(\"id\",null);").append("\n");
		content.append("	"+entityName+" "+entityVariableName+" ="+serviceVariableName+".find(Long.parseLong(id));").append("\n");
		content.append("	if("+entityVariableName+" == null){").append("\n");
		content.append("		ovo =new OVO(-1,\"记录不存在\",\"\");").append("\n");
		content.append("		return AesUtil.encode(VOConvert.ovoToJson(ovo));").append("\n");
		content.append("	}").append("\n");
		for ( int i = 0; i < editFieldArray.length; i++ ) {
			String fieldName =editFieldArray[i];
			String getFieldMethodName ="get"+StringUtil.convertFirstCharToUpperCase( fieldName )+"()";
			content.append("	String "+fieldName+"="+entityVariableName+"."+getFieldMethodName+";").append("\n");
			content.append("	ovo.set(\""+fieldName+"\", "+fieldName+");").append("\n");
		}
		content.append("		return AesUtil.encode(VOConvert.ovoToJson(ovo));").append("\n");
		content.append("}").append("\n");
		
		// delete api 
		String  deleteApiUri=paramJson.getString( "deleteApiUri" );
		String  deleteApiMethod=paramJson.getString( "deleteApiMethod" );
		String  deleteApiRemark=paramJson.getString( "deleteApiRemark" );
		String  deleteApiWhereFields=paramJson.getString( "deleteApiWhereFields" ).replace( "delete-api-where-field=", "" );
		if ( StringUtil.isEmptyOrNull( deleteApiUri ) ) {
			deleteApiUri ="delete";
		}
		if ( StringUtil.isEmptyOrNull( deleteApiMethod ) ) {
			deleteApiMethod ="delete";
		}
		content.append("/**").append("\n");
		content.append("*方法说明:"+deleteApiRemark).append("\n");
		content.append("**/").append("\n");
		content.append("@RequestMapping(value = \"/"+ deleteApiUri +"\")").append("\n");
		content.append("public @ResponseBody String "+deleteApiMethod+"(HttpServletRequest request) throws JException {").append("\n");
		content.append("	OVO ovo =new OVO();").append("\n");
		content.append("	IVO ivo =parseRequest(request);").append("\n");
		content.append("	String id =ivo.getString(\"id\",null);").append("\n");
		content.append("	"+serviceVariableName+".delete(Long.parseLong(id));").append("\n");
		content.append("	ovo =new OVO(0,\"操作成功\",\"\");").append("\n");
		content.append("	return AesUtil.encode(VOConvert.ovoToJson(ovo));").append("\n");
		content.append("	}	").append("\n");
		
		content.append("}	").append("\n");
		return content.toString();
	}
}
