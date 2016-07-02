 package com.ezcloud.framework.exp;
 
 //import com.juts.utility.LogUtil;
 import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
 
 public class JException extends Exception
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = 8860346879998593195L;
private int _$1;
   private String _$2;
   private String _$3 = "";
 
   public JException(int paramInt, String paramString)
   {
     this._$1 = paramInt;
     this._$2 = paramString;
   }
 
   public JException(int paramInt, String paramString1, String paramString2)
   {
     this._$1 = paramInt;
     this._$2 = paramString1;
     this._$3 = paramString2;
   }
 
   public JException(int paramInt, String paramString, Throwable paramThrowable)
   {
     this._$1 = paramInt;
     this._$2 = paramString;
     this._$3 = getStackTrace(paramThrowable);
   }
 
   public JException(String paramString)
   {
     super(paramString);
   }
 
   public int getCode()
   {
     return this._$1;
   }
 
   public String getExp()
   {
     return this._$3;
   }
 
   public String getMsg()
   {
     return this._$2;
   }
 
   public static String getStackTrace(Throwable paramThrowable)
   {
     StringWriter localStringWriter = new StringWriter();
     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
     String str = "";
     try
     {
       paramThrowable.printStackTrace(localPrintWriter);
       localStringWriter.flush();
       str = localStringWriter.toString();
       localStringWriter.close();
       localPrintWriter.close();
     }
     catch (IOException localIOException)
     {
       str = "待定处理";
     }
     return str;
   }
 
   public void printStackTrace()
   {
     System.out.println("错误号：" + this._$1);
     if ((this._$2 != null) && (this._$2.length() > 0))
    	 System.out.println("错误信息：" + this._$2);
     if ((this._$3 != null) && (this._$3.length() > 0))
    	 System.out.println("异常信息：" + this._$3);
   }
 }
