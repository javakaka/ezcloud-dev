package com.ezcloud.framework.service.system;

import org.springframework.stereotype.Component;

import com.ezcloud.framework.service.JdbcService;
import com.ezcloud.framework.vo.Row;
/**
 * 系统用户岗位
 * @author JianBoTong
 *
 */
@Component("frameworkStaffPositionService")
public class StaffPositionService extends JdbcService{

	public int  insert(Row row)
	{
		int rowNum =0;
		rowNum =insert("sm_staff_position",row);
		return rowNum;
	}
	

	
}
