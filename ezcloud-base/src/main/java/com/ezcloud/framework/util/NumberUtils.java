package com.ezcloud.framework.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**   
 * @author shike001 
 * E-mail:510836102@qq.com   
 * @version 创建时间：2014-12-29 下午5:18:28  
 * 类说明: 
 */
public class NumberUtils {

	 final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
		      'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
		      'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	  final static Map<Character, Integer> digitMap = new HashMap<Character, Integer>();

	  static{
	    for(int i = 0; i < digits.length; i++){
	      digitMap.put(digits[i], (int) i);
	    }
	  }

	  /**
	   * 支持的最大进制数
	   */
	  public static final int MAX_RADIX = digits.length;

	  /**
	   * 支持的最小进制数
	   */
	  public static final int MIN_RADIX = 2;
		  
	public NumberUtils() {
	}
	
	/**
	 * 验证字符串是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str)
	{
		boolean bool =true;
		if(str == null)
			return false;
		try
		{
			Double.parseDouble(str);
		}
		catch(Exception e)
		{
			bool =false;
		}
		return bool;
	}
	
	/**
	 * 验证字符串是正数
	 * @param str
	 * @return
	 */
	public static boolean isPositiveNumber(String str)
	{
		boolean bool =true;
		double change =0.0;
		if(str == null)
			return false;
		try
		{
			change=Double.parseDouble(str);
			if(change < 0)
			{
				bool =false;
			}
		}
		catch(Exception e)
		{
			bool =false;
		}
		return bool;
	}
	
	/**
	 * 在字符串是数字的前提下，取两位小数
	 * @param str
	 * @return
	 */
	public static String getTwoDecimal(String str)
	{
		int posi =str.indexOf(".");
		if(posi == -1)
		{
			str =str+".00";
		}
		else
		{
			str =str+"00";
			str =str.substring(0, posi+3);
		}
		return str;
	}
	
	/**
	 * 在字符串是数字的前提下，取两位小数
	 * @param str
	 * @return
	 */
	public static String getTwoDecimalByDecimalFormat(double number)
	{
		String decimal="";
		java.text.DecimalFormat df =new java.text.DecimalFormat("#.##");
		decimal =df.format(number);
		return decimal;
	}
	
	/**
	 * 取6位随机整数
	 * @return
	 */
	public static int getSixRandomNumber()
	{
		Random random =new Random();
		int randomNumber =random.nextInt(899999);
		randomNumber =100000+randomNumber;
		return randomNumber;
	}
	
	public static void main(String[] args) {
		String str ="10000.0";
		System.out.println(""+getTwoDecimal(str));
	}
	
	/**
	   * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
	   * 
	   * @param i
	   * @param radix
	   * @return
	   */
	  public static String toString(long i, int radix){
	    if(radix < MIN_RADIX || radix > MAX_RADIX)
	      radix = 10;
	    if(radix == 10)
	      return Long.toString(i);

	    final int size = 65;
	    int charPos = 64;

	    char[] buf = new char[size];
	    boolean negative = (i < 0);

	    if(!negative){
	      i = -i;
	    }

	    while(i <= -radix){
	      buf[charPos--] = digits[(int) (-(i % radix))];
	      i = i / radix;
	    }
	    buf[charPos] = digits[(int) (-i)];

	    if(negative){
	      buf[--charPos] = '-';
	    }

	    return new String(buf, charPos, (size - charPos));
	  }
}
