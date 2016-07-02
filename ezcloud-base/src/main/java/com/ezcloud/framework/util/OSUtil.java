package com.ezcloud.framework.util;

import java.util.Properties;

/**
 * 操作系统工具类
 * @author JianBoTong
 *
 */
public class OSUtil {

	public static Properties props;
	public static String getOSName()
	{
		String sOsName =null;
		props =System.getProperties();
		sOsName =props.getProperty("os.name"); 
		sOsName =sOsName.toLowerCase();
		if(sOsName.indexOf("mac") != -1)
		{
			sOsName ="mac";
		}
		else if(sOsName.indexOf("window") != -1)
		{
			sOsName ="windwos";
		}
		return sOsName;
	}
}