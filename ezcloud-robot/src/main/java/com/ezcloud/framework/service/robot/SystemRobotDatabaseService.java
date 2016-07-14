package com.ezcloud.framework.service.robot;

import org.springframework.stereotype.Component;

import com.ezcloud.framework.page.jdbc.Page;
import com.ezcloud.framework.page.jdbc.Pageable;
import com.ezcloud.framework.service.JdbcService;
import com.ezcloud.framework.util.StringUtil;
import com.ezcloud.framework.vo.DataSet;
import com.ezcloud.framework.vo.Row;

@Component("frameworkSystemRobotDatabaseService")
public class SystemRobotDatabaseService  extends JdbcService{

	/**
	 * 分页查询
	 * 
	 * @Title: queryPage
	 * @return Page
	 */
	public Page queryPage( Pageable pageable ) {
		Page page = null;
		sql = "select * from system_robot_database where 1=1 ";
		String restrictions = addRestrictions(pageable);
		String orders = addOrders(pageable);
		sql += restrictions;
		sql += orders;
		String countSql = "select count(*) from system_robot_database where 1=1 ";
		countSql += restrictions;
		countSql += orders;
		long total = count(countSql);
		int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
		if (totalPages < pageable.getPageNumber()) {
			pageable.setPageNumber(totalPages);
		}
		int startPos = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		sql += " limit " + startPos + " , " + pageable.getPageSize();
		dataSet = queryDataSet(sql);
		page = new Page(dataSet, total, pageable);
		return page;
	}
	
	/**
	 * 保存
	 * 
	 * @Title: save
	 * @return void
	 */
	public void save() {
		Row row = new Row();
		String id = getRow().getString("id", null);
		String LAN_NAME = getRow().getString("LAN_NAME", null);
		String LAN_STATE = getRow().getString("LAN_STATE", null);
		row.put("id", id);
		row.put("LAN_NAME", LAN_NAME);
		row.put("LAN_STATE", LAN_STATE);
		insert("system_robot_database", row);
	}
	
	/**
	 * 根据id查找
	 * 
	 * @return Row
	 * @throws
	 */
	public Row find(String id) {
		Row row = new Row();
		sql = "select * from system_robot_database where id='" + id + "'";
		row = queryRow(sql);
		return row;
	}

	/**
	 * 更新
	 * 
	 * @return void
	 */
	public void update() {
		String id = getRow().getString("id", null);
		String LAN_NAME = getRow().getString("LAN_NAME", null);
		String LAN_STATE = getRow().getString("LAN_STATE", null);
		Row row = new Row();
		row.put("LAN_NAME", LAN_NAME);
		row.put("LAN_STATE", LAN_STATE);
		update("system_robot_database", row, "id='" + id + "'");
	}
	
	
	/**
	 * 删除
	 * 
	 * @Title: delete
	 * @param @param ids
	 * @return void
	 */
	public int delete(String... ids) {
		int rowNum =0;
		String id = "";
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				if (id.length() > 0) {
					id += ",";
				}
				id += "'" + String.valueOf(ids[i]) + "'";
			}
			sql = "delete from system_robot_database where id in(" + id + ")";
			rowNum =update(sql);
		}
		return rowNum;
	}
	
	public DataSet queryAllItems()
	{
		DataSet ds =new DataSet();
		String sql ="select * from system_robot_database ";
		ds =queryDataSet(sql);
		return ds;
	}
	
	/**
	 * 查询指定数据库的全部表名字 
	 * @param dbType 数据库类型。1 mysql,2 sql server,3 oracle
	 * @param dbName 数据库名字
	 * @return
	 */
	public DataSet queryTablesFromDatabase(String dbType, String dbName)
	{
		DataSet ds =new DataSet();
		String sql =null;
		if ( StringUtil.isEmptyOrNull( dbType ) ) {
			dbType ="1";
		}
		if ( dbType.equals( "1" ) ) {
			sql =" select table_name from information_schema.tables where table_schema = '"+dbName+"' ";
		}
		ds =queryDataSet(sql);
		return ds;
	}
}
