 package com.ezcloud.framework.util;
 
 public class SecurityUtil
 {
	 /**
	  * 
	  * @param paramString
	  * @param paramInt 1表示加密，2表示解密
	  * @return
	  */
   public static String getPassword(String sText, int iType)
   {
     if (sText == null) {
       return null;
     }
     String str2 = "";
     int i;
     int j;
     int i1;
     int k;
     String str1;
     if (iType == 1) {
       i = sText.length();
       int n;
       n = (int)(Math.random() * 10.0D);
       if (n == 0)
       {
         n = 1;
       }
       for (j = 0; j < i; j++)
       {
    	   i1 = (int)(Math.random() * 94.0D);
        if (i1 == 0)
         {
          i1 = 1;
         }
        	int m = i1 + 32;
        	k = sText.substring(j, j + 1).charAt(0);
         str1 = String.valueOf((char)( k - j));
 
        if (k + j + n > 126) {
           if (m % 2 == 1)
             m += 1;
           str1 = String.valueOf((char)m) + String.valueOf((char)(k - j - n));
         } else {
           if (m % 2 == 0)
            m += 1;
           str1 = String.valueOf((char)m) + String.valueOf((char)(k + j + n));
         }
         str2 = str2 + str1;
       }
 
       if ((
         i1 = (int)(Math.random() * 9.0D)) == 0)
       {
         i1 = 1;
       }str2 = String.valueOf((char)(i1 * 10 + n + 40)) + str2;
     }
     else {
       i = sText.length();
       str2 = "";
       i1 = sText.substring(0, 1).charAt(0) % '\n';
       for (j = 2; j < i; j += 2) {
         k = sText.substring(j, j + 1).charAt(0);
         if (sText.substring(j - 1, j).charAt(0) % '\002' == 0)
           str1 = String.valueOf((char)(k + (j - 1) / 2 + i1));
         else {
          str1 = String.valueOf((char)(k - (j - 1) / 2 - i1));
         }
         str2 = str2 + str1;
       }
     }
     return str2;
   }
   
   
   public static void main(String s[]){
//			System.out.println(SecurityUtil.getPassword("TmXWm9owzY(%|[ySq@h\"jQoLc5u;1L\\p\\h`_52\\#|DO&P\"Y0YnIfGo?", 2));
//			System.out.println(2+ ":" + SecurityUtil.getPassword("TmXWm9owzY(%|[ySq@h\"jQoLc5u;1L\\p\\h`_52\\#|DO&P\"Y0YnIfGo?", 2));
//			System.out.println("*****************************************");
//			System.out.println(SecurityUtil.getPassword("TmXWm9owzY(%|[ySq@h\"jQoLc5u;1L\\p\\h`_52\\#|DO&P\"Y0YnIfGo?", 2));
//			System.out.println("*****************************************");
//			System.out.println("*****************************************");
//			System.out.println(2 + ":" + SecurityUtil.getPassword("TmXWm9owzY(%|[ySq@h\"jQoLc5u;1L\\p\\h`_52\\#|DO&P\"Y0YnIfGo?", 2));
//			System.out.println("*****************************************");
//			System.out.println(SecurityUtil.getPassword("yYm9kEfaiss-y#l]6$o9w5w", 2)+System.getProperty("user.home"));
//			System.out.println(SecurityUtil.getPassword("e_2Y4-6K8", 2)+System.getProperty("user.home"));
//			System.out.println(SecurityUtil.getPassword("root", 1)+System.getProperty("user.home"));
	   
	   System.out.println(SecurityUtil.getPassword("O%:;<=>5@sB+D", 2)+System.getProperty("user.home"));
		System.out.println(SecurityUtil.getPassword("123456", 1));
   }
 }
