package com.ezcloud.framework.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import com.ezcloud.framework.plugin.pay.Unifiedorder;
import com.ezcloud.framework.vo.Row;

public class FieldUtil {

	public static String getObjectNotEmptyFieldsUrlParamsStr(Object obj)
	{
		ArrayList list = new ArrayList();
		Row row =new Row();
		String str ="";
		Field fields[] =null;
		fields =obj.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++)
		{
			fields[i].setAccessible(true);
			String field_type =fields[i].getType().toString();
//			System.out.println("field_type>>"+field_type);
			try {
				String value =(String)fields[i].get(obj);
				String name =fields[i].getName();
				if(! StringUtils.isEmptyOrNull(value))
				{
					row.put(name, value);
					list.add(name);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("before sort -----");
//		for(int i=0;i<list.size();i++)
//		{
//			System.out.println("--"+list.get(i));
//		}
		Collections.sort(list);
//		System.out.println("after sort -----");
		for(int i=0;i<list.size();i++)
		{
			String sort_name =(String)list.get(i);
			String value =row.getString(sort_name);
			if(str.length()>0)
			{
				str +="&"+sort_name+"="+value;
			}
			else
			{
				str +=sort_name+"="+value;
			}
		}
		return str;
	}
	
	public static Row getObjectNotEmptyFieldsUrlParamsStr(Object obj,String api_key)
	{
		Row paramRow =new Row();
		ArrayList list = new ArrayList();
		Row row =new Row();
		String str ="";
		Field fields[] =null;
		fields =obj.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++)
		{
			fields[i].setAccessible(true);
			String field_type =fields[i].getType().toString();
//			System.out.println("field_type>>"+field_type);
			try {
				String value =(String)fields[i].get(obj);
				String name =fields[i].getName();
				if(! StringUtils.isEmptyOrNull(value))
				{
					row.put(name, value);
					list.add(name);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("before sort -----");
//		for(int i=0;i<list.size();i++)
//		{
//			System.out.println("--"+list.get(i));
//		}
		Collections.sort(list);
//		System.out.println("after sort -----");
		String xml ="<xml>";
		for(int i=0;i<list.size();i++)
		{
			String sort_name =(String)list.get(i);
			String value =row.getString(sort_name);
			if(str.length()>0)
			{
				str +="&"+sort_name+"="+value;
			}
			else
			{
				str +=sort_name+"="+value;
			}
			xml +="<"+sort_name+">"+value+"</"+sort_name+">";
		}
		str +="&key="+api_key;
		String sign =Md5Util.get32UppercaseMD5(str);
		xml +="<sign>"+sign+"</sign>";
		xml +="</xml>";
		paramRow.put("url", str);
		paramRow.put("sign", sign);
		paramRow.put("xml", xml);
		return paramRow;
	}
	
	public static String appendFiledToUrlParam(String urlParam,String name,String value)
	{
		if(!StringUtils.isEmptyOrNull(name) && !StringUtils.isEmptyOrNull(value))
		{
			if(!StringUtils.isEmptyOrNull(urlParam))
			{
				urlParam +="&"+name+"="+value;
			}
			else
			{
				urlParam =name+"="+value;
			}
		}
		return urlParam;
	}
	
	public static String getWeixinRequestSign(ArrayList key_list,Row dataRow,String api_key)
	{
		String url_data_str="";
		Collections.sort(key_list);
		for(int i=0;i<key_list.size();i++)
		{
			String sort_name =(String)key_list.get(i);
			String value =dataRow.getString(sort_name);
			if(url_data_str.length()>0)
			{
				url_data_str +="&"+sort_name+"="+value;
			}
			else
			{
				url_data_str +=sort_name+"="+value;
			}
		}
		url_data_str +="&key="+api_key;
		String sign =Md5Util.get32UppercaseMD5(url_data_str);
		return sign;
	}
	
	public static String getWeixinRequestSignExample(ArrayList key_list,Row dataRow,String api_key)
	{
		String url_data_str="";
		Collections.sort(key_list);
		for(int i=0;i<key_list.size();i++)
		{
			String sort_name =(String)key_list.get(i);
			String value =dataRow.getString(sort_name);
			if(url_data_str.length()>0)
			{
				url_data_str +="&"+sort_name+"="+value;
			}
			else
			{
				url_data_str +=sort_name+"="+value;
			}
		}
		System.out.println("#1.生成字符串:"+url_data_str);
		url_data_str +="&key="+api_key;
		System.out.println("#2.连接商户key:"+url_data_str);
		String sign =Md5Util.get32UppercaseMD5(url_data_str);
		System.out.println("#3.md5编码并转成大写:"+sign);
		String wx_md5="21AE93F915D0E084D1BB73108E796920";
		System.out.println("是否和微信工具的签名相等:"+sign.equals(wx_md5));
		return sign;
	}
	
	public static void main(String[] args) {
		Unifiedorder obj =new Unifiedorder();
		obj.setAppid("123");
		obj.setMch_id("12312");
		obj.setBody("ahskdhaksd");
		String str =FieldUtil.getObjectNotEmptyFieldsUrlParamsStr(obj);
		System.out.println("str====>>"+str);
	}
}
