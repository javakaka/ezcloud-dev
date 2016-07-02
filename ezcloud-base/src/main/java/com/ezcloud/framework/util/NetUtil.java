package com.ezcloud.framework.util;
 
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ezcloud.framework.exp.JException;
 
 /**
  * 网络处理工具类
  * @author Tong
  *
  */
 public class NetUtil
 {
	 public static final int HttpGet =1;
	 public static final int HttpPost =2;
	
	 /**
	  * 下载文件
	  * @param sUrl
	  * @param sFilePath
	  */
   public static void downloadFile(String sUrl, String sFilePath)
   {
     try
     {
       URL urlAddr = new URL(sUrl);
       FilterInputStream oFilterInputStream = (FilterInputStream)urlAddr.openStream();
       File localFile = new File(sFilePath);
       FileOutputStream oFileOutputStream = new FileOutputStream(localFile);
       byte[] arrayOfByte = new byte[4096];
       int i;
       while ((i = oFilterInputStream.read(arrayOfByte)) != -1)
       {
         oFileOutputStream.write(arrayOfByte, 0, i);
       }
       oFilterInputStream.close();
       oFileOutputStream.close();
     }
     catch (Exception e)
     {
       System.out.println("下载文件时出现异常，" + e.getMessage());
       e.printStackTrace();
     }
   }
   
   public static String executeGet(String sUrl)
   {
	   ByteArrayOutputStream baos =new ByteArrayOutputStream(); 
	   try
	   {
		   URL urlAddr = new URL(sUrl);
		   FilterInputStream oFilterInputStream = (FilterInputStream)urlAddr.openStream();
		   byte[] arrayOfByte = new byte[4096];
		   int i;
		   while ((i = oFilterInputStream.read(arrayOfByte)) != -1)
		   {
			   baos.write(arrayOfByte, 0, i);
		   }
		   oFilterInputStream.close();
	   }
	   catch (Exception e)
	   {
		   System.out.println("请求出现异常，" + e.getMessage());
		   e.printStackTrace();
	   }
	   return baos.toString(); 
   }
 
   /**
    * 取得请求网络返回的数据
    * @param url
    * @param requestStr
    * @return
    * @throws JException
    */
   @SuppressWarnings("deprecation")
public static String getNetResponse(String url, String requestStr)
     throws JException
   {
     String sResponseStr = null;
     try
     {
       URL urlAddr = new URL(url);
       HttpURLConnection oHttpURLConnection = (HttpURLConnection)urlAddr.openConnection();
       System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
       System.setProperty("sun.net.client.defaultReadTimeout", "30000");
       oHttpURLConnection.setRequestMethod("POST");
       oHttpURLConnection.setRequestProperty("Proxy-Connection", "Keep-Alive");
       oHttpURLConnection.setDoOutput(true);
       oHttpURLConnection.setDoInput(true);
 
       OutputStream oOutputStream = oHttpURLConnection.getOutputStream();
       DataOutputStream oDataOutputStream = new DataOutputStream(oOutputStream);
       if (requestStr != null)
         oDataOutputStream.write(requestStr.getBytes());
       oDataOutputStream.flush();
       oDataOutputStream.close();
 
       InputStream oInputStream = oHttpURLConnection.getInputStream();
       DataInputStream oDataInputStream = new DataInputStream(oInputStream);
       StringBuffer oStringBuffer = new StringBuffer(1024);
       String str2 = null;
       while ((str2 = oDataInputStream.readLine()) != null)
       {
         oStringBuffer.append(str2);
       }
 
       sResponseStr = oStringBuffer.toString();
       oStringBuffer.setLength(0);
       oHttpURLConnection.disconnect();
     }
     catch (Exception e)
     {
    	 //e.printStackTrace();
       throw new JException(-111111, "请求网络连接[" + url + "]时出现异常！", e);
     }
     return sResponseStr;
   }
   
   @SuppressWarnings("deprecation")
public static String getNetResponse(String url, String requestStr,int method)
		   throws JException
   {
	   String sResponseStr = null;
	   if ( method == NetUtil.HttpGet ) {
		   sResponseStr =executeGet( url );
		   return sResponseStr;
	   }
	   try
	   {
		   URL urlAddr = new URL(url);
		   HttpURLConnection oHttpURLConnection = (HttpURLConnection)urlAddr.openConnection();
		   System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		   System.setProperty("sun.net.client.defaultReadTimeout", "30000");
		   oHttpURLConnection.setRequestMethod("POST");
		   oHttpURLConnection.setRequestProperty("Proxy-Connection", "Keep-Alive");
		   oHttpURLConnection.setDoOutput(true);
		   oHttpURLConnection.setDoInput(true);
//		   System.out.println( "==============>>" +oHttpURLConnection.getContent());
		   OutputStream oOutputStream = oHttpURLConnection.getOutputStream();
		   DataOutputStream oDataOutputStream = new DataOutputStream(oOutputStream);
		   if (requestStr != null)
			   oDataOutputStream.write(requestStr.getBytes());
		   oDataOutputStream.flush();
		   oDataOutputStream.close();
		   
		   InputStream oInputStream = oHttpURLConnection.getInputStream();
		   DataInputStream oDataInputStream = new DataInputStream(oInputStream);
		   StringBuffer oStringBuffer = new StringBuffer(1024);
		   String str2 = null;
		   while ((str2 = oDataInputStream.readLine()) != null)
		   {
			   oStringBuffer.append(str2);
		   }
		   
		   sResponseStr = oStringBuffer.toString();
		   oStringBuffer.setLength(0);
		   oHttpURLConnection.disconnect();
	   }
	   catch (Exception e)
	   {
		   throw new JException(-111111, "请求网络连接[" + url + "]时出现异常！", e);
	   }
	   return sResponseStr;
   }
 
   /**
    * 根据指定字符集格式取得网络返回的数据
    * @param url
    * @param requestStr
    * @param charsetName
    * @return
    * @throws JException
    */
   public static String getNetResponse(String url, String requestStr, String charsetName)
     throws JException
   {
     String sResponseStr = null;
     try
     {
       URL urlAddr = new URL(url);
       HttpURLConnection oHttpURLConnection = (HttpURLConnection)urlAddr.openConnection();
       System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
       System.setProperty("sun.net.client.defaultReadTimeout", "30000");
       oHttpURLConnection.setRequestMethod("POST");
       oHttpURLConnection.setRequestProperty("Proxy-Connection", "Keep-Alive");
       oHttpURLConnection.setDoOutput(true);
       oHttpURLConnection.setDoInput(true);
 
       OutputStream oOutputStream = oHttpURLConnection.getOutputStream();
       DataOutputStream oDataOutputStream = new DataOutputStream(oOutputStream);
       if (requestStr != null)
       {
        // oDataOutputStream.write(requestStr.getBytes());
       	 oDataOutputStream.write(requestStr.getBytes("UTF-8"));
       }
       oDataOutputStream.flush();
       oDataOutputStream.close();
 
       InputStream oInputStream = oHttpURLConnection.getInputStream();
 
       BufferedReader oBufferedReader = new BufferedReader(new InputStreamReader(
         oInputStream, charsetName));
 
       StringBuffer oStringBuffer = new StringBuffer(1024);
       String str2 = null;
       while ((str2 = oBufferedReader.readLine()) != null)
       {
         oStringBuffer.append(str2);
       }
 
       sResponseStr = oStringBuffer.toString();
       oStringBuffer.setLength(0);
       oHttpURLConnection.disconnect();
     }
     catch (Exception e)
     {
       throw new JException(-111111, "请求网络连接[" + url + "]时出现异常！", e);
     }
     return sResponseStr;
   }
   
//   public static void main(String[] args) {
////		String json="{\"sign\":\"51ffd83ba8f2acd2a82e2f8d2ad400f7\",\"data\":{\"username\":\"13826539927\",\"identifying_code\":\"\",\"password\":\"123456\",\"u_id\":\"14691\",\"invitation_code\":\"\",\"nickname\":\"ttt999aaassa99\",\"device_token\":\"ff693bef3c9bba7b2feffdb3b4dc2e2e3630184f2234b91f7344ae3de6a7fb6b\",\"device_type\":0},\"time\":1409046228437489014}";
////		String json="{\"sign\":\"bbd7106b5f105286eae6e9532303723f\",\"data\":{\"username\":\"13826539927\",\"identifying_code\":\"\",\"password\":\"123456\",\"u_id\":\"14691\",\"invitation_code\":\"\",\"nickname\":\"nissdsdooo\",\"device_token\":\"ff693bef3c9bba7b2feffdb3b4dc2e2e3630184f2234b91f7344ae3de6a7fb6b\",\"device_type\":0},\"time\":1409046927362680908}";
//		String json="{\"sign\":\"67c69f1d83dbbdd0c1cdc1bbabbd61a6\",\"data\":{\"user_id\":\"762\",\"token\":\"ff693bef3c9bba7b2feffdb3b4dc2e2e3630184f2234b91f7344ae3de6a7fb6b\"},\"time\":1409898898221389160}";
//		try {
////			String INTERFACE_URL ="http://yyltest.shike001.com:58888/api/gift/list";
//			String INTERFACE_URL ="http://yyltest.shike001.com:58888/api/interact/getbuddies";
//			String res =NetUtil.getNetResponse(INTERFACE_URL, json);
//			System.out.println("========================================");
//			System.out.print(res);
//		} catch (JException e) {
//			e.printStackTrace();
//		}
//	}
   
   public static void main(String[] args)  {
	   String url ="http://apidev.hyskb.com/common/line/list?appid=2&destAreaCode=&destCityCode=28373&originAreaCode=&originCityCode=2&page=1&pageSize=15&timestamp=1461033145&sign=61fdc182daca0330e98c2eb51d671868";
		String res;
		try {
			res = NetUtil.getNetResponse(url, null,1);
			System.out.println("========================================\n");
			System.out.print(res);
		}
		catch (JException e) {
			e.printStackTrace();
		}
		res =NetUtil.executeGet(url);
		System.out.println("11========================================\n");
		System.out.print(res);
   }
 }