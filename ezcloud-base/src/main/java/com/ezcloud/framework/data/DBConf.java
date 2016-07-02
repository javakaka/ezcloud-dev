 package com.ezcloud.framework.data;
 
 import java.util.Properties;
 
 /**
  * 数据库验证类
  * @author Tong
  *
  */
 public class DBConf
 {
   public static String _DEFAULT_CONNECTION = "_DEFAULT_CONNECTION";
 
   public static String _DATABASE_TYPE = "_DATABASE_TYPE";
 
   public static String _DATABASE_CONN_TYPE = "_DATABASE_CONN_TYPE";
 
   public static String _JDBC_URL = "_JDBC_URL";
 
   public static String _JDBC_DRIVER = "_JDBC_DRIVER";
 
   public static Properties _JDBC_PROPERTIES = null;
 
   public static String getConf()
   {
     String str = "默认数据库：" + (_DEFAULT_CONNECTION != null ? _DEFAULT_CONNECTION : "null") + 
    	       "；数据库类型：" + (_DATABASE_TYPE != null ? _DATABASE_TYPE : "null") + 
    	       "数据库链接类型：" + (_DATABASE_CONN_TYPE != null ? _DATABASE_CONN_TYPE : "null") + 
    	       "；数据库链接：" + (_JDBC_URL != null ? _JDBC_URL : "null") + 
    	       "；数据库驱动：" + (_JDBC_DRIVER != null ? _JDBC_DRIVER : "null") + 
    	       "；数据库其它属性：" + (_JDBC_PROPERTIES != null ? _JDBC_PROPERTIES.toString() : "null");
     return str;
   }
 }