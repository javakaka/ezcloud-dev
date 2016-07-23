 package com.ezcloud.framework.util;
 
 import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

import com.ezcloud.framework.vo.DataSet;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import net.sf.json.JSONObject;
 
 public class FileUtil
 {
   /**
    * 删除文件
    * @param paramFile
    * @return
    */
   private static boolean _$1(File paramFile)
   {
     try
     {
       if ((deleteFromDir(paramFile)) && (paramFile.delete()))
       {
         return true;
       }
       return false;
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }return false;
   }
 
   /**
    * 复制文件到指定目
    * @param paramFile
    * @param toPath
    * @return
    */
   private static boolean _$1(File paramFile, String toPath)
   {
     if (!paramFile.exists())
     {
       return false;
     }
     if (!paramFile.isFile())
     {
       return false;
     }
     try
     {
       int i = 0;
       FileInputStream oFileInputStream = new FileInputStream(paramFile);
       FileOutputStream oFileOutputStream = new FileOutputStream(toPath);
       byte[] arrayOfByte = new byte[1024];
       while ((i = oFileInputStream.read(arrayOfByte)) != -1)
       {
    	   oFileOutputStream.write(arrayOfByte, 0, i);
       }
       oFileOutputStream.close();
       oFileInputStream.close();
     }
     catch (Exception e)
     {
       e.printStackTrace();
       return false;
     }
     return true;
   }
   
   /**
    * 复制文件到指定文
    * @param paramFile
    * @param toPath
    * @return
    */
   private static boolean _$1(File inputFile, File outputFile)
   {
	   if (!inputFile.exists())
	   {
		   return false;
	   }
	   if (!inputFile.isFile())
	   {
		   return false;
	   }
	   try
	   {
		   int i = 0;
		   FileInputStream oFileInputStream = new FileInputStream(inputFile);
		   FileOutputStream oFileOutputStream = new FileOutputStream(outputFile);
		   byte[] arrayOfByte = new byte[1024];
		   while ((i = oFileInputStream.read(arrayOfByte)) != -1)
		   {
			   oFileOutputStream.write(arrayOfByte, 0, i);
		   }
		   oFileOutputStream.close();
		   oFileInputStream.close();
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
		   return false;
	   }
	   return true;
   }
 
   private static boolean _$2(File paramFile, String paramString)
   {
     boolean bool = true;
     new File(paramString).mkdirs();
     File[] arrayOfFile = paramFile.listFiles();
     for (int i = 0; arrayOfFile!= null && i < arrayOfFile.length; i++)
     {
       if ((arrayOfFile[i].isFile()) && 
         (!(
         bool = _$1(arrayOfFile[i], paramString + "\\" + arrayOfFile[i].getName()))))
       {
         break;
       }
       if (arrayOfFile[i].isDirectory())
       {
         String str = paramString + "\\" + arrayOfFile[i].getName();
 
         if (!(
           bool = _$2(arrayOfFile[i], str))) {
           break;
         }
       }
     }
     return bool;
   }
 
   public static boolean copy(File paramFile, String paramString)
   {
     if (!paramFile.exists())
     {
       return false;
     }
     if (paramFile.isFile())
     {
       return _$1(paramFile, paramString);
     }
 
     return _$2(paramFile, paramString);
   }
 
   public static boolean copy(String sFromFilePath, String sToFilePath)
   {
     File localFile = new File(sFromFilePath);
     return copy(localFile, sToFilePath);
   }
   
   public static boolean copy(File inputFile, File outputFile)
   {
	   if (!inputFile.exists())
	     {
	       return false;
	     }
	   return _$1(inputFile, outputFile);
   }
 
   public static boolean copy(String paramString1, String paramString2, FileFilter paramFileFilter)
   {
     File localFile = new File(paramString1);
     File[] arrayOfFile =localFile.listFiles(paramFileFilter);
     boolean bool = true;
     if (arrayOfFile != null)
     {
       for (int i = 0; i < arrayOfFile.length; i++)
       {
         if (!copy(arrayOfFile[i], paramString2 + "/" + arrayOfFile[i].getName()))
         {
           bool = false;
         }
       }
     }
     return bool;
   }
 
   public static boolean delete(File paramFile)
   {
     if (!paramFile.exists())
     {
       return false;
     }
     if (paramFile.isFile())
     {
       return paramFile.delete();
     }
 
     return _$1(paramFile);
   }
 
   public static boolean delete(String path)
   {
     File file = new File(path);
     return delete(file);
   }
 
   public static boolean deleteFromDir(File paramFile)
   {
     if (!paramFile.exists())
     {
       return false;
     }
     if (!paramFile.isDirectory())
     {
       return false;
     }
     File[] arrayOfFile = paramFile.listFiles();
     for (int i = 0; arrayOfFile!= null && i < arrayOfFile.length; i++)
     {
       if (!delete(arrayOfFile[i]))
       {
         return false;
       }
     }
     return true;
   }
 
   public static boolean deleteFromDir(String paramString)
   {
     File localFile = new File(paramString);
     return deleteFromDir(localFile);
   }
 
   public static byte[] getBytes(InputStream paramInputStream)
     throws IOException
   {
     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1024);
     byte[] arrayOfByte1 = new byte[512];
     int i;
     while ((
       i = paramInputStream.read(arrayOfByte1)) != 
       -1)
     {
       localByteArrayOutputStream.write(arrayOfByte1, 0, i);
     }
 
     byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
     localByteArrayOutputStream.close();
     return arrayOfByte2;
   }
 
   public static long getSize(String paramString)
   {
     File ocalFile = new File(paramString);
     return ocalFile.length();
   }
 
   public static boolean mkdir(String paramString)
   {
     File localFile;
     if (!(
       localFile = new File(paramString))
       .exists())
     {
       localFile.mkdirs();
     }
     return true;
   }
 
   public static boolean move(File paramFile, String paramString)
   {
     return (copy(paramFile, paramString)) && (delete(paramFile));
   }
 
   public static byte[] readByte(File paramFile)
   {
     try
     {
    	 FileInputStream oFileInputStream =new FileInputStream(paramFile);
       byte[] arrayOfByte = readByte(oFileInputStream);
       oFileInputStream.close();
       return arrayOfByte;
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }return null;
   }
 
   public static byte[] readByte(InputStream oInputStream)
   {
     try
     {
       byte[] arrayOfByte = new byte[oInputStream.available()];
       oInputStream.read(arrayOfByte);
       return arrayOfByte;
     }
     catch (Exception e)
     {
      e.printStackTrace();
     }return null;
   }
 
   public static byte[] readByte(String filePath)
   {
     try
     {
       FileInputStream fis =new FileInputStream(filePath);
       byte[] arrayOfByte = new byte[fis.available()];
       fis.close();
       return arrayOfByte;
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }
     return null;
   }
 
   public static String readText(File paramFile)
   {
     return readText(paramFile, "GBK");
   }
 
   /**
    * 根据指定的字符集读取文件类容
    * @param paramFile
    * @param charset
    * @return
    */
   public static String readText(File paramFile, String charset)
   {
     try
     {
       FileInputStream oFileInputStream;
       oFileInputStream = new FileInputStream(paramFile);
     String  localObject = readText(oFileInputStream,charset);
     oFileInputStream.close();
       return localObject;
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }return null;
   }
 
   /**
    * 根据指定的输入流读取输入流，并返回字符串
    * @param paramInputStream
    * @param charset
    * @return
    */
   public static String readText(InputStream paramInputStream, String charset)
   {
     try
     {
       BufferedReader oBufferedReader = new BufferedReader(
    		   new InputStreamReader(paramInputStream, charset));
    StringBuffer   oStringBuffer = new StringBuffer();
       String str;
       while ((str = oBufferedReader.readLine()) != null)
       {
         ((StringBuffer)oStringBuffer).append(str);
         ((StringBuffer)oStringBuffer).append("\n");
       }
       oBufferedReader.close();
       return (oStringBuffer).toString();
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }return null;
   }
 
   public static String readText(String paramString)
   {
     return readText(paramString, "GBK");
   }
 
   public static String readText(String fileName, String charset)
   {
     try
     {
       FileInputStream oFileInputStream = new FileInputStream(fileName);
       BufferedReader oBufferedReader = new BufferedReader(new InputStreamReader(oFileInputStream, charset));
      StringBuffer oStringBuffer = new StringBuffer();
       int i = oBufferedReader.read();
       if ((!charset.equalsIgnoreCase("utf-8")) || (i != 65279))
       {
         oStringBuffer.append((char)i);
       }
       String str;
       while ((str = oBufferedReader.readLine()) != null)
       {
    	   oStringBuffer.append(str);
    	   oStringBuffer.append("\n");
       }
       oBufferedReader.close();
       oFileInputStream.close();
       return oStringBuffer.toString();
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }return null;
   }
 
   public static String readURLText(String paramString)
   {
     return readURLText(paramString, "GBK");
   }
 
   /**
    * 以指定字符集读取指定url上的文件，返回改文件的字符串
    * @param url
    * @param charset
    * @return
    */
   public static String readURLText(String url, String charset)
   {
     try
     {
       URL oURL = new URL(url);
       BufferedReader oBufferedReader = new BufferedReader(new InputStreamReader(oURL.openStream(), charset));
      StringBuffer oStringBuffer = new StringBuffer();
       Object localObject2;
       while ((localObject2 = oBufferedReader.readLine()) != null)
       {
    	   oStringBuffer.append(localObject2 + "\n");
       }
       oBufferedReader.close();
       return oStringBuffer.toString();
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }return null;
   }
 
   @SuppressWarnings("restriction")
public static boolean reduceImg(String paramString1, String paramString2, int paramInt1, int paramInt2)
   {
     try
     {
		File oFile =new File(paramString1);
       if (!oFile.exists() )
       {
         return false;
       }
       BufferedImage oBufferedImage;
       oBufferedImage = ImageIO.read(oFile);
       int i = oBufferedImage.getWidth();
       int j = oBufferedImage.getHeight();
       int k = i / paramInt1;
       int m = j / paramInt2;
       if (k > m)
       {
         paramInt2 = j / k;
       }
       else
       {
         paramInt1 = i / m;
       }
       BufferedImage oBufferedImage2 = new BufferedImage(paramInt1, paramInt2, 1);
       oBufferedImage2.getGraphics().drawImage(oBufferedImage.getScaledInstance(paramInt1, paramInt2, 4), 0, 0, null);
       FileOutputStream oFileOutputStream;
       JPEGImageEncoder oJPEGImageEncoder;
       oJPEGImageEncoder = JPEGCodec.createJPEGEncoder(oFileOutputStream = new FileOutputStream(paramString2));
       oJPEGImageEncoder.encode(oBufferedImage2);
       oFileOutputStream.close();
       return true;
     }
     catch (IOException e)
     {
         e.printStackTrace();
     }
     return false;
   }
 
   public static byte[] serialize(Serializable paramSerializable)
   {
     try
     {
      ByteArrayOutputStream oByteArrayOutputStream = new ByteArrayOutputStream();
       ObjectOutputStream oObjectOutputStream = new ObjectOutputStream((OutputStream)oByteArrayOutputStream);
       oObjectOutputStream.writeObject(paramSerializable);
       oObjectOutputStream.flush();
       oObjectOutputStream.close();
       return (oByteArrayOutputStream).toByteArray();
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }
     return null;
   }
 
   public static void serialize(Serializable paramSerializable, String paramString)
   {
     try
     {
       FileOutputStream oFileOutputStream = new FileOutputStream(paramString);
       ObjectOutputStream oObjectOutputStream = new ObjectOutputStream(oFileOutputStream);
       oObjectOutputStream.writeObject(paramSerializable);
       oObjectOutputStream.flush();
       oObjectOutputStream.close();
     }
     catch (Exception e)
     {
       throw new RuntimeException(e);
     }
   }
 
   public static Object unserialize(String fileName)
   {
   	try
   	{
   		FileInputStream oFileInputStream = new FileInputStream(fileName);
   		ObjectInputStream oObjectInputStream = new ObjectInputStream(oFileInputStream);
   		Object  localObject = oObjectInputStream.readObject();
   		oObjectInputStream.close();
   		return localObject;
   	}
   	catch (Exception e)
   	{
   		e.printStackTrace();
   	}
   	return null;
   }
 
   public static Object unserialize(byte[] paramArrayOfByte)
   {
   	try
   	{
   		ByteArrayInputStream oByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
   		ObjectInputStream oObjectInputStream = new ObjectInputStream(oByteArrayInputStream);
   		Object  localObject = oObjectInputStream.readObject();
   		oObjectInputStream.close();
   		return localObject;
   	}
   	catch (Exception e)
   	{
   		e.printStackTrace();
   	}
   	return null;
   }
 
   public static boolean writeByte(File paramFile, byte[] paramArrayOfByte)
   {
     try
     {
    	 BufferedOutputStream oBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile));
    	 oBufferedOutputStream.write(paramArrayOfByte);
         oBufferedOutputStream.close();
     }
     catch (Exception e)
     {
         e.printStackTrace();
       return false;
     }
     return true;
   }
 
   public static boolean writeByte(String fileName, byte[] paramArrayOfByte)
   {
     try
     {
    	 BufferedOutputStream oBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));
    	 oBufferedOutputStream.write(paramArrayOfByte);
    	 oBufferedOutputStream.close();
     }
     catch (Exception localException)
     {
         localException.printStackTrace();
       return false;
     }
     return true;
   }
 
   public static boolean writeText(String paramString1, String paramString2)
   {
     return writeText(paramString1, paramString2, "GBK");
   }
 
   public static boolean writeText(String paramString1, String paramString2, String paramString3)
   {
     return writeText(paramString1, paramString2, paramString3, false);
   }
 
   public static boolean writeText(String fileName, String stringValue, String charset, boolean paramBoolean)
   {
     try
     {
       byte[] arrayOfByte = stringValue.getBytes(charset);
       writeByte(fileName, arrayOfByte);
     }
     catch (Exception e)
     {
    	 e.printStackTrace();
       return false;
     }
     return true;
   }
   
   public static String FormetFileSize(long fileS) {
       DecimalFormat df = new DecimalFormat("#.00");
       String fileSizeString = "";
       if (fileS < 1024) {
           fileSizeString = df.format((double) fileS) + "B";
       } else if (fileS < 1048576) {
           fileSizeString = df.format((double) fileS / 1024) + "K";
       } else if (fileS < 1073741824) {
           fileSizeString = df.format((double) fileS / 1048576) + "M";
       } else {
           fileSizeString = df.format((double) fileS / 1073741824) + "G";
       }
       return fileSizeString;
   }
	
	/**
    * 获取硬盘的每个盘符
    */
   @SuppressWarnings("unchecked")
	public static DataSet getAllDrivers(){
   		DataSet ds =new DataSet();
       // 当前文件系统类
       FileSystemView fsv = FileSystemView.getFileSystemView();
       // 列出所有windows 磁盘
       File[] fs = File.listRoots();
       // 显示磁盘卷标
       for (int i = 0; i < fs.length; i++) {
           String filePath =fsv.getSystemDisplayName(fs[i]);
           System.out.println( filePath );
           filePath =fs[i].getPath().replaceAll( "\\\\", "/" );
           System.out.println( filePath );
           System.out.println( fsv.getSystemTypeDescription( fs[i] ) );
//           listFolder( filePath );
           System.out.print("总大小" + FormetFileSize(fs[i].getTotalSpace()));
           System.out.println("剩余" + FormetFileSize(fs[i].getFreeSpace()));
           ds.add(filePath);
       }
       return ds;
   }
   
   /**
    * 读取某个目录下的全部非隐藏目录
    * @param path
    */
   @SuppressWarnings("unchecked")
	public static DataSet listFolder( String path ){
   	DataSet ds=new DataSet();
   	if (StringUtil.isEmptyOrNull(path)) {
		ds =getAllDrivers();
		return ds;
	}
   	File file =new File( path );
   	if (file.isDirectory()) 
   	{
		File chdFiles[] =file.listFiles();
		File tmpFile  =null;
		for (int i = 0; chdFiles!=null && i <  chdFiles.length; i++) {
			tmpFile =chdFiles[i];
			if ( tmpFile.isDirectory() ) {
				String tmpFilePath =tmpFile.getPath()+"/";
				tmpFilePath =tmpFilePath.replaceAll( "\\\\", "/" );
				ds.add(tmpFilePath);
			}
		}
	}
   	return ds;
   }
   
   /**
    * 读取指定目录下以及上级目录的全部文件夹
    * @param path
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   public static HashMap<String ,Object> queryAllPreFolders( String path ) {
	   HashMap<String ,Object> map =new HashMap<String ,Object>();
	   ArrayList list =new ArrayList();
	   // 如果没有指定目录，就返回系统的根目录
	   if (StringUtil.isEmptyOrNull(path)) {
		   DataSet ds =getAllDrivers();
		   map.put( "dir_name", "" );
		   map.put( "is_selected", "1" );
		   for ( int i = 0; i < ds.size(); i++ ) 
		   {
			String dirName =(String)ds.get( i );
			HashMap<String ,Object> tmpMap =new HashMap<String ,Object>();
			tmpMap.put( "dir_name", dirName );
			tmpMap.put( "is_selected", "0" );
			tmpMap.put( "children", new ArrayList() );
			list.add( tmpMap );
		   }
		   map.put( "children", list );
		}
	   // 当指定了目录，就查询包含指定目录以及它的全部上级目录的全部子目录
	   else
	   {
		   path +="/";
		   path =path.replaceAll( "\\\\", "/" );
		   String[] pathArray = path.split( "/" );
		   String currentDir ="";
		   HashMap<String ,Object> tmpMap =new HashMap<String ,Object>();
		   currentDir +=pathArray[0]+"/";
		   System.out.println( "listedPath---------------" +currentDir );
		   tmpMap.put( "dir_name", currentDir );
		   tmpMap.put( "is_selected", "0" );
		   tmpMap.put( "children", querySelectedChildFolders(path,currentDir) );
//		   map =tmpMap;
		   
		   DataSet ds =getAllDrivers();
		   map.put( "dir_name", "" );
		   map.put( "is_selected", "1" );
		   for ( int i = 0; i < ds.size(); i++ ) 
		   {
			String dirName =(String)ds.get( i );
			if ( dirName.equalsIgnoreCase( currentDir ) ) {
				list.add( tmpMap );
			}
			else
			{
				HashMap<String ,Object> rootMap =new HashMap<String ,Object>();
				rootMap.put( "dir_name", dirName );
				rootMap.put( "is_selected", "0" );
				rootMap.put( "children", new ArrayList() );
				list.add( rootMap );
			}
		   }
		   map.put( "children", list );
	   }
	   return map;
   }
   
   @SuppressWarnings({ "unchecked", "rawtypes" })
public static ArrayList querySelectedChildFolders( String path,String currentDir ){
	   ArrayList list =new ArrayList();
	   DataSet folderList =listFolder( currentDir );
	   for ( int j = 0; j < folderList.size(); j++ ) 
	   {
		   String childPath =folderList.get( j ).toString();
		   HashMap<String ,Object> tmpChildMap =new HashMap<String ,Object>();
		   tmpChildMap.put( "dir_name", childPath );
		   tmpChildMap.put( "is_selected", "0" );
		   if ( path.indexOf( childPath ) != -1) 
		   {
			   tmpChildMap.put( "children", querySelectedChildFolders(path,childPath) );
		   }
		   else
		   {
			   tmpChildMap.put( "children", new ArrayList() );
		   }
		   list.add( tmpChildMap );
	   }
	   return list;
   }
   
   public static void main(String[] args) {
//	   HashMap<String ,Object> map =queryAllPreFolders("D:/programs/adobe/");
//	   System.out.println( "---------------" +map );
//	   JSONObject json =JSONObject.fromObject( map );
//	   System.out.println( "---------------" +json.toString() );
//	   System.out.println( "---------------" +listFolder( "D:/" ) );
//	   System.out.println( "---------------" +listFolder( "D:/programs/360/" ) );
	   System.out.println( "---------------" +mkdir("/Users/TongJianbo/work/88") );
}
   
 }

