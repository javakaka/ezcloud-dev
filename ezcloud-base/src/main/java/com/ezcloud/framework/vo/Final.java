package com.ezcloud.framework.vo;
/**
 * 框架常量类
 * @author Tong
 *
 */
public class Final {
	/**
	 * oracle
	 */
	public static String _TYPE_ORACLE = "oracle";
	/**
	 * sqlserver
	 */
	public static String _TYPE_SQLSERVER = "sqlserver";
	/**
	 * mysql
	 */
	public static String _TYPE_MYSQL = "mysql";
	/**
	 * 字段分隔符
	 */
	public static String _FIELD_SEPARATOR = ",";

	public static int _QUERY_TYPE = 0;
	public static int _MODIFY_TYPE = 1;
	public static int _CREATE_TYPE = 2;
	/**
	 * jdbc
	 */
	public static String _JDBC = "jdbc";
	/**
	 * pool
	 */
	public static String _POOL = "pool";
	/**
	 * proxool
	 */
	public static String _PROXOOL = "proxool";
	/**
	 * 路径分隔符
	 */
	public static String _DIR_SEPARATOR = System.getProperty("file.separator");
	/**
	 * 网站物理路径
	 */
	public static String _PHYSICAL_WEB_PATH = null;
}