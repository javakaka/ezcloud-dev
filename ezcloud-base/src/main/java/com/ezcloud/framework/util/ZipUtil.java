 package com.ezcloud.framework.util;
 
 import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import com.ezcloud.framework.exp.JException;
 
 /**
  * 解压缩ZIP文件工具类
  * @author Administrator
  *
  */
 public class ZipUtil
 {
	 
   /**
    * 解压zip文件
    * @param paramString1
    * @param paramString2
    * @throws JException
    */
   public static void unzip(String paramString1, String paramString2)
     throws JException
   {
     int i = 2048;
     Object localObject;
     try
     {
    	 localObject =new ZipFile(paramString1);
       Enumeration entriesEnumeration = (new ZipFile(paramString1)).entries();
          
         
       int j = 0;
       while (entriesEnumeration.hasMoreElements())
       {
         ZipEntry oZipEntry;
         oZipEntry = (ZipEntry)entriesEnumeration.nextElement();
         if (oZipEntry.isDirectory())
         {
           new File(paramString2 + oZipEntry.getName()).mkdirs();
         }
         else {
           BufferedInputStream localBufferedInputStream = new BufferedInputStream(((ZipFile)localObject).getInputStream(oZipEntry));
           File oFile1;
           File oFile2;
           if (((
             oFile2 = (
             oFile1 = new File(paramString2 + oZipEntry.getName()))
             .getParentFile()) != null) && 
             (!oFile2.exists())) {
             oFile2.mkdirs();
           }
           FileOutputStream localFileOutputStream = new FileOutputStream(oFile1);
           BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream, i);
 
           byte[] arrayOfByte = new byte[i];
           int k;
           while ((k = localBufferedInputStream.read(arrayOfByte, 0, i)) != -1)
           {
             localBufferedOutputStream.write(arrayOfByte, 0, k);
           }
           localBufferedOutputStream.flush();
           localBufferedOutputStream.close();
           localBufferedInputStream.close();
         }
       }
       ((ZipFile)localObject).close();
 
       return;
     }
     catch (Exception localException)
     {
    	 localException.printStackTrace();
         
     }throw new JException(-81324132, "解压文件时出现异常!", "");
   }
 
   /**
    * 压缩文件
    * @param paramString1
    * @param paramString2
    * @return
    * @throws JException
    */
   public static boolean zip(String toPath, String fromPath)
     throws JException
   {
     boolean bool = true;
     try
     {
       File oFile1 = new File(toPath);
       File oFile2;
       oFile2 = new File(fromPath);
       if (!(oFile2.exists())) {
         throw new RuntimeException(fromPath + "不存在！");
       }
       Project localProject = new Project();
       Zip localZip;
       localZip = new Zip();
       localZip.setProject(localProject);
       localZip.setDestFile(oFile1);
       FileSet localFileSet=new FileSet();
       
       localFileSet.setProject(localProject);
       localFileSet.setDir(oFile2);
 
       localZip.addFileset(localFileSet);
       localZip.execute();
     }
     catch (Exception localException)
     {
         localException.printStackTrace();
       throw new JException(-8091231, "压缩文件时出现异常！", "");
     }
     return bool;
   }
 }