package com.ezcloud.framework.controller.robot;

import java.util.ArrayList;

/**
 * 自动生成代码 后台管理类生成器结构体
 * @author TongJianbo
 *
 */
public class AdminControllerStructure {
	// import  可动态扩展
	private ArrayList<String> importList;
	
	//Cotroller包名
	private String packageName ="";
	//Cotroller类名
	private String className ="";
	//模块路径
	private String moduleUri ="";
	//Controller 组件名称
	private String componentName ="";
	
	//服务类Class 包名称
	private String serviceClassPackageName ="";
	//服务类Class名称
	private String serviceClassName ="";
	//服务类属性名称
	private String serviceAttributeName ="";
	//服务类注解组件名称
	private String serviceAnnotationName ="";
	
	//实体类Class 包名称
	private String entityClassPackageName ="";
	//实体类Class名称
	private String entityClassName ="";
	//实体类属性名称
	private String entityAttributeName ="";
		
	//分页方法名
	private String listPageMethodName ="list";
	//分页uri
	private String listPageUriName ="list";
	//添加页面方法名
	private String addPageMethodName ="add";
	//添加页面uri
	private String addPageUriName ="add";
	//保存页面方法名
	private String savePageMethodName ="save";
	//保存页面uri
	private String savePageUriName ="save";
	//编辑页面方法名
	private String editPageMethodName ="edit";
	//编辑页面uri
	private String editPageUriName ="edit";
	//更新页面方法名
	private String updatePageMethodName ="update";
	//更新页面uri
	private String updatePageUriName ="update";
	//删除页面方法名
	private String deletePageMethodName ="delete";
	//删除页面uri
	private String deletePageUriName ="delete";
	//预览页面方法名
	private String previewPageMethodName ="preview";
	//预览页面uri
	private String previewPageUriName ="preview";
	
	public AdminControllerStructure() {
		initImportList();
	}
	
	public void initImportList(){
		importList =new ArrayList<String>();
		importList.add("import java.sql.*;\n");
		importList.add("import java.util.*;\n");
		importList.add("import javax.annotation.*;\n");
		importList.add("import org.slf4j.*;\n");
		importList.add("import org.springframework.beans.factory.annotation.*;\n");
		importList.add("import org.springframework.stereotype.*;\n");
		importList.add("import org.springframework.ui.*;\n");
		importList.add("import org.springframework.web.bind.annotation.*;\n");
		importList.add("import org.springframework.web.servlet.mvc.support.*;\n");
		importList.add("com.ezcloud.framework.controller.BaseController;\n");
	}

	/**
	 * 添加导入项目
	 * @param importContent
	 */
	public void addImportItem(String importContent){
		if (importList == null || importList.size() == 0) {
			importList =new ArrayList<String>();
			importList.add("import java.sql.*;\n");
			importList.add("import java.util.*;\n");
			importList.add("import javax.annotation.*;\n");
			importList.add("import org.slf4j.*;\n");
			importList.add("import org.springframework.beans.factory.annotation.*;\n");
			importList.add("import org.springframework.stereotype.*;\n");
			importList.add("import org.springframework.ui.*;\n");
			importList.add("import org.springframework.web.bind.annotation.*;\n");
			importList.add("import org.springframework.web.servlet.mvc.support.*;\n");
			importList.add("com.ezcloud.framework.controller.BaseController;\n");
		}
		importList.add(importContent);
	}
	
	public ArrayList<String> getImportList() {
		return importList;
	}

	public void setImportList(ArrayList<String> importList) {
		this.importList = importList;
	}

	public String getModuleUri() {
		return moduleUri;
	}

	public void setModuleUri(String moduleUri) {
		this.moduleUri = moduleUri;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getServiceClassPackageName() {
		return serviceClassPackageName;
	}

	public void setServiceClassPackageName(String serviceClassPackageName) {
		this.serviceClassPackageName = serviceClassPackageName;
	}

	public String getServiceClassName() {
		return serviceClassName;
	}

	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}

	public String getServiceAttributeName() {
		return serviceAttributeName;
	}

	public void setServiceAttributeName(String serviceAttributeName) {
		this.serviceAttributeName = serviceAttributeName;
	}

	public String getServiceAnnotationName() {
		return serviceAnnotationName;
	}

	public void setServiceAnnotationName(String serviceAnnotationName) {
		this.serviceAnnotationName = serviceAnnotationName;
	}

	public String getEntityClassPackageName() {
		return entityClassPackageName;
	}

	public void setEntityClassPackageName(String entityClassPackageName) {
		this.entityClassPackageName = entityClassPackageName;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public String getEntityAttributeName() {
		return entityAttributeName;
	}

	public void setEntityAttributeName(String entityAttributeName) {
		this.entityAttributeName = entityAttributeName;
	}

	public String getListPageMethodName() {
		return listPageMethodName;
	}

	public void setListPageMethodName(String listPageMethodName) {
		this.listPageMethodName = listPageMethodName;
	}

	public String getListPageUriName() {
		return listPageUriName;
	}

	public void setListPageUriName(String listPageUriName) {
		this.listPageUriName = listPageUriName;
	}

	public String getAddPageMethodName() {
		return addPageMethodName;
	}

	public void setAddPageMethodName(String addPageMethodName) {
		this.addPageMethodName = addPageMethodName;
	}

	public String getAddPageUriName() {
		return addPageUriName;
	}

	public void setAddPageUriName(String addPageUriName) {
		this.addPageUriName = addPageUriName;
	}

	public String getSavePageMethodName() {
		return savePageMethodName;
	}

	public void setSavePageMethodName(String savePageMethodName) {
		this.savePageMethodName = savePageMethodName;
	}

	public String getSavePageUriName() {
		return savePageUriName;
	}

	public void setSavePageUriName(String savePageUriName) {
		this.savePageUriName = savePageUriName;
	}

	public String getEditPageMethodName() {
		return editPageMethodName;
	}

	public void setEditPageMethodName(String editPageMethodName) {
		this.editPageMethodName = editPageMethodName;
	}

	public String getEditPageUriName() {
		return editPageUriName;
	}

	public void setEditPageUriName(String editPageUriName) {
		this.editPageUriName = editPageUriName;
	}

	public String getUpdatePageMethodName() {
		return updatePageMethodName;
	}

	public void setUpdatePageMethodName(String updatePageMethodName) {
		this.updatePageMethodName = updatePageMethodName;
	}

	public String getUpdatePageUriName() {
		return updatePageUriName;
	}

	public void setUpdatePageUriName(String updatePageUriName) {
		this.updatePageUriName = updatePageUriName;
	}

	public String getDeletePageMethodName() {
		return deletePageMethodName;
	}

	public void setDeletePageMethodName(String deletePageMethodName) {
		this.deletePageMethodName = deletePageMethodName;
	}

	public String getDeletePageUriName() {
		return deletePageUriName;
	}

	public void setDeletePageUriName(String deletePageUriName) {
		this.deletePageUriName = deletePageUriName;
	}

	public String getPreviewPageMethodName() {
		return previewPageMethodName;
	}

	public void setPreviewPageMethodName(String previewPageMethodName) {
		this.previewPageMethodName = previewPageMethodName;
	}

	public String getPreviewPageUriName() {
		return previewPageUriName;
	}

	public void setPreviewPageUriName(String previewPageUriName) {
		this.previewPageUriName = previewPageUriName;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * class package content
	 * @return
	 */
	public String buildPackageContent()
	{
		String content ="";
		content ="package "+this.getPackageName()+";\n";
		return content;
	}
	
	/**
	 * import 
	 * @return
	 */
	public String buildImportContent()
	{
		StringBuilder sb =new StringBuilder();
		for (int i = 0; i < importList.size(); i++) {
			sb.append(importList.get(i));
		}
		sb.append(this.getServiceClassPackageName());
		sb.append(this.getEntityClassPackageName());
		return sb.toString();
	}
	
	/**
	 * @Controller("")
	 * @RequestMapping("/admin/news/profile")  
	 * public class NewsController extends BaseController{ 
	 * @return
	 */
	public String buildControllerNameContent()
	{
		StringBuilder sb =new StringBuilder();
		sb.append("@Controller(\""+this.getComponentName()+"\")").append("\n");
		sb.append("@RequestMapping(\""+this.moduleUri+"\")").append("\n");
		sb.append("public class ").append(this.getClassName()).append(" extends BaseController{ \n");
		sb.append("private Logger logger =LoggerFactory.getLogger("+this.getClassName()+".class);\n");
		return sb.toString();
	}
	
	/**
	 * 服务类属性内容
	 * @return
	 */
	public String buildServiceAttributeContent()
	{
		StringBuilder sb =new StringBuilder();
		sb.append("@Resource(name =\""+this.getServiceAnnotationName()+"\")\n");
		sb.append("private "+this.getServiceClassName()+" "+this.getServiceAttributeName()+" ;\n");
		return sb.toString();
	}
	
	public String buildListPageMethodContent()
	{
		StringBuilder sb =new StringBuilder();
		sb.append("/**\n");
		sb.append("* 列表页面\n");
		sb.append("* @param pageable 分页对象\n");
		sb.append("* @param model\n");
		sb.append("* @return\n");
		sb.append("*/\n");
		sb.append(" @RequestMapping(\"/"+this.getListPageUriName()+"\")\n");
		sb.append("public String "+this.getListPageMethodName()+"(Pageable pageable, ModelMap model){\n");
		sb.append("model.addAttribute(\"page\", "+this.getServiceAttributeName()+".findPage(pageable));\n");
		sb.append("return \"/admin/news/profile/list\";\n");
		return sb.toString();
	}
	
	public String buildAddPageMethodContent()
	{
		StringBuilder sb =new StringBuilder();
		return sb.toString();
	}
	
	public String buildSavePageMethodContent()
	{
		StringBuilder sb =new StringBuilder();
		return sb.toString();
	}
	
	public String buildEditPageMethodContent()
	{
		StringBuilder sb =new StringBuilder();
		return sb.toString();
	}
	
	public String buildUpdatePageMethodContent()
	{
		StringBuilder sb =new StringBuilder();
		return sb.toString();
	}
	
	public String buildDeletePageMethodContent()
	{
		StringBuilder sb =new StringBuilder();
		return sb.toString();
	}
	
	/**
	 * 生成class 字符串
	 * @return
	 */
	public String toCodeString (){
		StringBuilder sb =new StringBuilder();
		sb.append( this.buildPackageContent() )
		.append(this.buildImportContent())
		.append(this.buildControllerNameContent())
		.append(this.buildServiceAttributeContent())
		.append(this.buildListPageMethodContent())
		.append(this.buildAddPageMethodContent())
		.append(this.buildSavePageMethodContent())
		.append(this.buildEditPageMethodContent())
		.append(this.buildUpdatePageMethodContent())
		.append(this.buildDeletePageMethodContent())
		.append("}\n");
		return sb.toString();
	}
}
