 package com.ezcloud.framework.util;
 
 import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ezcloud.framework.exp.JException;

//import Decoder.BASE64Encoder;
//import Decoder.BASE64Decoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
//import sun.misc.CharacterDecoder;
//import sun.misc.CharacterEncoder;
 
 public class StringUtil
 {
   private static String _$1 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
 
   public static String fill(String paramString1, int paramInt1, String paramString2, int paramInt2)
   {
     String str = "";
     for (int i = paramString1.length(); i < paramInt1; i++) {
       str = str + paramString2;
     }
     if (paramInt2 == 0)
       paramString1 = str + paramString1;
     else
       paramString1 = paramString1 + str;
     return paramString1;
   }
 
   public static String gbkToISO(String paramString, boolean paramBoolean)
   {
     if ((paramBoolean) && 
       (paramString != null) && (!paramString.equals(""))) {
       try {
         paramString = new String(paramString.getBytes("GBK"), "ISO-8859-1");
       }
       catch (Exception exp)
       {
    	   exp.printStackTrace();
       }
     }
 
     return paramString;
   }
 
   @SuppressWarnings("restriction")
public static boolean generateBase64File(String paramString1, String paramString2)
     throws JException
   {
     if (paramString1 == null)
       return false;
     BASE64Decoder localBASE64Decoder = new BASE64Decoder();
     try
     {
       byte[] arrayOfByte = localBASE64Decoder.decodeBuffer(paramString1);
       for (int i = 0; i < arrayOfByte.length; i++)
       {
         if (arrayOfByte[i] < 0)
         {
           int tmp37_35 = i;
           byte[] tmp37_34 = arrayOfByte; tmp37_34[tmp37_35] = (byte)(tmp37_34[tmp37_35] + 256);
         }
       }
       FileOutputStream localFileOutputStream;
       (
         localFileOutputStream = new FileOutputStream(paramString2))
         .write(arrayOfByte);
       localFileOutputStream.flush();
       localFileOutputStream.close();
       return true;
     }
     catch (Exception localException)
     {
       throw new JException(-9812312, "在Base64解码时发生异常！", localException);
     }
   }
 
   @SuppressWarnings("restriction")
public static String getFileBase64(String paramString)
     throws JException
   {
     FileInputStream localFileInputStream = null;
     byte[] arrayOfByte = null;
     try
     {
       arrayOfByte = new byte[(
         localFileInputStream = new FileInputStream(paramString))
         .available()];
       localFileInputStream.read(arrayOfByte);
       localFileInputStream.close();
     }
     catch (IOException localIOException)
     {
       throw new JException(-9812312, "文件[" + paramString + "]在Base64转码时发生异常！", localIOException);
     }
     BASE64Encoder localBASE64Encoder = new BASE64Encoder();
     return localBASE64Encoder.encode(arrayOfByte);
   }
 
   public static String getFormatString(String paramString, Object[] paramArrayOfObject)
   {
     if (paramString == null) return paramString; return MessageFormat.format(paramString, paramArrayOfObject);
   }
 
   public static String getRandKeys(int paramInt)
   {
     String str = "";
     int i = _$1.length();
     int j = 1;
     do
     {
       str = "";
       int k = 0;
       for (int m = 0; m < paramInt; m++)
       {
         double d = Math.random() * i;
         int n = (int)Math.floor(d);
         int i1;
         if (((
           i1 = _$1.charAt(n)) >= 
           '0') && (i1 <= 57))
         {
           k++;
         }
         str = str + _$1.charAt(n);
       }
       if (k >= 2)
         j = 0;
     }
     while (j != 0);
 
     return str;
   }
 
   public static String isoToGBK(String paramString, boolean paramBoolean)
   {
     if ((paramBoolean) && 
       (paramString != null) && (!paramString.equals(""))) {
       try {
         paramString = new String(paramString.getBytes("ISO-8859-1"), "GBK");
       }
       catch (Exception exp)
       {
    	   exp.printStackTrace();
       }
     }
 
     return paramString;
   }
   
   public static String isoToUTF8(String paramString, boolean paramBoolean)
   {
     if (paramBoolean && 
       paramString != null && !paramString.equals("")) {
       try {
         paramString = new String(paramString.getBytes("ISO-8859-1"), "UTF-8");
       }
       catch (Exception e)
       {
        
           e.printStackTrace();
       }
     }
     return paramString;
   }
 
   public static String replace(String paramString1, String paramString2)
   {
     if (paramString2 == null) return paramString1;
     String str = paramString1;
     if (paramString1 == null)
       paramString1 = "null";
     if ((paramString2 != null) && (paramString2.length() > 2))
     {
       String[] arrayOfString1 = toArray(
         paramString2.substring(1, paramString2.length() - 1), "][");
 
       for (int i = 0; i < arrayOfString1.length; i++)
       {
         String[] arrayOfString2;
         if ((
           arrayOfString2 = toArray(arrayOfString1[i], ",")).length == 
           2)
         {
           if (arrayOfString2[0].equalsIgnoreCase(paramString1))
           {
             str = arrayOfString2[1];
             break;
           }
         }
       }
     }
     return str;
   }
 
   public static String replace(String paramString1, String paramString2, String paramString3)
   {
     if (paramString1 == null) {
       return null;
     }
     if (paramString1.indexOf(paramString2) == -1) {
       return paramString1;
     }
     int i = 0;
     int j = 0;
     StringBuffer localStringBuffer = new StringBuffer();
 
     int k = paramString2.length();
     while (j < paramString1.length()) {
       if (paramString1.substring(j).startsWith(paramString2)) {
         String str = paramString1.substring(i, j) + paramString3;
         localStringBuffer.append(str);
 
         i = j = j + k;
       }
       else {
         j++;
       }
     }
 
     localStringBuffer.append(paramString1.substring(i));
     return localStringBuffer.toString();
   }
 
   public static void sort(String[] paramArrayOfString)
   {
     for (int i = 0; i < paramArrayOfString.length; i++)
     {
       for (int j = paramArrayOfString.length - 1; j > i; j--)
       {
         if (paramArrayOfString[i].length() > paramArrayOfString[j].length())
         {
           String str = paramArrayOfString[i];
           paramArrayOfString[i] = paramArrayOfString[j];
           paramArrayOfString[j] = str;
         }
       }
     }
   }
 
   public static String[] toArray(String paramString1, String paramString2)
   {
     if (paramString1 == null) {
       return null;
     }
     ArrayList<Object> oArrayList = new ArrayList<Object>();
     int i = paramString1.indexOf(paramString2);
     System.out.println(paramString1);
     while (i >= 0)
     {
       oArrayList.add(paramString1.substring(0, i));
       paramString1 = paramString1.substring(i + paramString2.length());
       i = paramString1.indexOf(paramString2);
     }
     oArrayList.add(paramString1);
     String[] arrayOfString = new String[oArrayList.size()];
     for (int j = 0; j < oArrayList.size(); j++) {
       arrayOfString[j] = ((String)oArrayList.get(j));
     }
     oArrayList.clear();
     return arrayOfString;
   }
   
   public static String string2Json(String s) {
		if ( s == null || s.trim().length() == 0 ) {
			return "";
		}
	    StringBuffer sb = new StringBuffer ();
	    for (int i=0; i<s.length(); i++) {
	        char c = s.charAt(i);
	        switch (c) {
	        case '\"':
	            sb.append("\\\"");
	            break;
	        case '\\':
	            sb.append("\\\\");
	            break;
	        case '/':
	            sb.append("\\/");
	            break;
	        case '\b':
	            sb.append("\\b");
	            break;
	        case '\f':
	            sb.append("\\f");
	            break;
	        case '\n':
	            sb.append("\\n");
	            break;
	        case '\r':
	            sb.append("\\r");
	            break;
	        case '\t':
	            sb.append("\\t");
	            break;
	        default:
	            sb.append(c);
	        }
	 }
	    return sb.toString();
	}
   
   /**
	 * 验证字符串是空或者null
	 * @param str
	 * @return
	 */
	public static boolean isEmptyOrNull(String str)
	{
		boolean bool =false;
		if(str == null)
			return true;
		if(str.replace(" ", "").length() == 0)
		bool =true;
		return bool;
	}

	/**
	 * 验证字符串是否是手机号码
	 * @param telephone
	 * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147、172
    * 联通号码段:130、131、132、136、185、186、145
    * 电信号码段:133、153、180、189
	 * @return
	 */
	public static boolean isTelphone(String telephone)
	{
		boolean bool =true;
		String regex = "^((13[0-9])|(172)|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8}$";
		bool =check(telephone, regex);
		return bool;
	}

	/**
	 * 验证字符串是否是邮箱账号
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
	{
		boolean bool =false;
//		String regex = "^\\w+[-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
//		String regex = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		bool =check(email, regex);
		return bool;
	}

	/**
	 * 验证字符串是否是邮箱账号
	 * @param email
	 * @return
	 */
	public static boolean isNumber(String str)
	{
		boolean bool =false;
		String regex = "[0-9]*";
		bool =check(str, regex);
		return bool;
	}

	public static boolean check(String str, String regex)
	{
		boolean flag =false;
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**
	 * 将字串数据类型转换为整型
	 * @param str
	 * @return
	 */
	public static int stringToInt(String str){
		if(null != str && !"".equals(str) && isNumber(str)){
			return Integer.parseInt(str);
		}else{
			return 0;
		}
	}
	
	/**
	 * 将字符串的首字母转成大写
	 * @param str
	 * @return
	 */
	public static String convertFirstCharToUpperCase(String str){
		char firstChar =str.charAt( 0 );
		if ( Character.isUpperCase( firstChar ) ) {
			return str;
		}
		firstChar =Character.toUpperCase( firstChar );
		str =String.valueOf( firstChar )+str.substring( 1 );
		return str;
	}
	
	/**
	 * 将字符串的首字母转成小写
	 * @param str
	 * @return
	 */
	public static String convertFirstCharToLowerCase(String str){
		char firstChar =str.charAt( 0 );
		if ( Character.isLowerCase( firstChar ) ) {
			return str;
		}
		firstChar =Character.toLowerCase( firstChar );
		str =String.valueOf( firstChar )+str.substring( 1 );
		return str;
	}
   public static void main(String s[])
   {
	   String s1 ="fkfdhfjdsfhdsjf1=akjfdskfsf1=ajkfjfk";
	   String s2 ="1=a";
	   String s3[] ;
	   s3 =StringUtil.toArray(s1, s2);
	   for(int i=0;i<s3.length;i++)
	   {
		   System.out.println("========"+s3[i]);
	   }
   }
 }