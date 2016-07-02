package com.ezcloud.framework.service.system;

import org.springframework.stereotype.Component;

import com.ezcloud.framework.service.JdbcService;

/**   
 * @author shike001 
 * E-mail:510836102@qq.com   
 * @version 创建时间：2015-3-13 上午10:27:32  
 * 类说明: 
 */
@Component("frameworkSystemMysqlBackUpService")
public class SystemMysqlBackUpService extends JdbcService{

	
	public SystemMysqlBackUpService() {
	}
	
	public String getBackUpBaseDir()
	{
		String baseDir ="";
		return baseDir;
	}
}
