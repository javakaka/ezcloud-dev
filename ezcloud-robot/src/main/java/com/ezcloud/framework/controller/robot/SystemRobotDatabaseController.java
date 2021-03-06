package com.ezcloud.framework.controller.robot;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezcloud.framework.controller.BaseController;
import com.ezcloud.framework.page.jdbc.Page;
import com.ezcloud.framework.page.jdbc.Pageable;
import com.ezcloud.framework.service.robot.SystemRobotDatabaseService;
import com.ezcloud.framework.util.MapUtils;
import com.ezcloud.framework.util.Message;
import com.ezcloud.framework.util.ResponseVO;
import com.ezcloud.framework.vo.DataSet;
import com.ezcloud.framework.vo.Row;

@Controller("frameworkSystemRobotDatabaseController")
@RequestMapping("/system/robot/database")
public class SystemRobotDatabaseController  extends BaseController{

	private static final String TABLE_NAME="system_robot_database";
	private static final String TABLE_PRIMARY_COLUMN="id";
	private static final String TABLE_CREATE_COLUMN="create_time";
	private static final String TABLE_UPDATE_COLUMN="update_time";
	
	@Resource(name = "frameworkSystemRobotDatabaseService")
	private SystemRobotDatabaseService databaseService;

	@RequestMapping(value = "/add")
	public String add(ModelMap model) {
		return "/system/robot/database/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam HashMap<String,String> map, RedirectAttributes redirectAttributes) {
		Row row =MapUtils.convertMaptoRowWithoutNullField(map);
		databaseService.insert( row,TABLE_NAME,TABLE_PRIMARY_COLUMN,TABLE_CREATE_COLUMN );
		addFlashMessage( redirectAttributes, Message.success( "操作成功" ) );
		return "redirect:list.do";
	}
	
	@RequestMapping(value = "/list")
	public String list(Pageable pageable, ModelMap model) {
		Page page =databaseService.queryPage( pageable );
		model.addAttribute("page", page);
		return "/system/robot/database/list";
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(String id,ModelMap model) {
		Row row =databaseService.find( id );
		model.addAttribute("row", row);
		return "/system/robot/database/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestParam HashMap<String,String> map,RedirectAttributes redirectAttributes) {
		Row row =MapUtils.convertMaptoRowWithoutNullField(map);
		databaseService.update( row,TABLE_NAME, TABLE_PRIMARY_COLUMN,TABLE_UPDATE_COLUMN);
		return "redirect:list.do";
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody 
	public int delete(String id) {
		int rowNum =0;
		Assert.notNull(id, "id不能为空!");
		rowNum=databaseService.delete(id);
		return rowNum;
	}
	
	@RequestMapping(value = "/query-tables")
	@ResponseBody
	public ResponseVO queryTablesFromDatabase(String id) {
		ResponseVO ovo =new ResponseVO();
		Row db =null;
		Assert.notNull(id, "id不能为空!");
		db=databaseService.find( id );
		String dbName =db.getString( "db_name" );
		String dbType =db.getString( "db_type" );
		DataSet tableDs =databaseService.queryTablesFromDatabase( dbType, dbName );
		ovo.setCode( 0 );
		ovo.oForm.put( "table_list", tableDs );
		return ovo;
	}
	
	@RequestMapping(value = "/query-fields")
	@ResponseBody
	public ResponseVO queryFieldsFromTable(String dbId, String tableName) {
		ResponseVO ovo =new ResponseVO();
		Row db =null;
		Assert.notNull(dbId, "id不能为空!");
		db=databaseService.find( dbId );
		String dbName =db.getString( "db_name" );
		Assert.notNull(tableName, "tableName不能为空!");
		DataSet fieldDs =databaseService.queryFieldsFromTable( dbName, tableName );
		ovo.setCode( 0 );
		ovo.oForm.put( "field_list", fieldDs );
		return ovo;
	}
	
}