package com.ezcloud.framework.vo.android;

import com.ezcloud.framework.exp.JException;
import com.ezcloud.framework.util.FileUtil;
import com.ezcloud.framework.util.NetUtil;
import com.ezcloud.framework.vo.OVO;
import com.ezcloud.framework.vo.VOConvert;

public class ExchangeEngine {

	private static final String XML_TYPE="1";
	private static final String JSON_TYPE="2";
	public static String URL ;
	public static String CHARSET ="utf-8";
	public static String DATA_TYPE ="1";//1表示xml,2表示json
	
	public static ResponseVO execute(RequestVO reqvo) throws JException
	{
		ResponseVO resvo=new ResponseVO();
		if(URL == null || URL.trim().length() ==0){
			throw new JException (-110,"请指定服务器路径");
		}
		if(CHARSET == null || CHARSET.trim().length() == 0){
			CHARSET ="utf-8";
		}
		if(reqvo == null){
			throw new JException (-111,"请初始化请求对象");
		}
		else 
		{
			String sServiceName =reqvo.sService;
			if(sServiceName == null || sServiceName.trim().length() == 0){
				throw new JException (-112,"未指定请求服�?");
			}
		}
		if(DATA_TYPE == null || DATA_TYPE.trim().length() == 0 || "12".indexOf(DATA_TYPE) ==-1){
			throw new JException (-113,"未指定客户端与服务器交互的数据格式，1表示xml2表示json");
		}
		String reqStr ="";
		if(DATA_TYPE.equals(ExchangeEngine.XML_TYPE))
		{
			reqStr =VOConvert.ivoToXml(reqvo);
		}
		else if(DATA_TYPE.equals(ExchangeEngine.JSON_TYPE))
		{
			reqStr =VOConvert.ivoToJson(reqvo);
		}
		System.out.print("reqstr========\n"+reqStr);
		String respStr =NetUtil.getNetResponse(URL, reqStr, CHARSET);
		System.out.print("respStr========\n"+respStr);
		FileUtil.writeText("c:/uuu.txt", respStr);
		OVO ovo =null;
		if(respStr != null )
		{
			if(DATA_TYPE.equals(ExchangeEngine.XML_TYPE))
			{
				ovo =VOConvert.xmlToOvo(respStr);
			}
			else if(DATA_TYPE.equals(ExchangeEngine.JSON_TYPE))
			{
				ovo =VOConvert.jsonToOvo(respStr);
			}
			if ( ovo !=null ) {
				resvo.copy(ovo);
			}
		}
		return resvo;
	}
	
	public static void main(String s[])
	{
//		ExchangeEngine.URL ="http://192.168.1.104:8080/performance/XmlExchangeServlet";
//		ExchangeEngine.DATA_TYPE ="1";
		ExchangeEngine.URL ="http://192.168.1.104:8080/performance/JsonExchangeServlet";
		ExchangeEngine.URL ="http://www.ez-cloud.cn/JsonExchangeServlet";
		ExchangeEngine.DATA_TYPE ="2";
		
		RequestVO ivo =new RequestVO("loginSys");
//		RequestVO ivo =new RequestVO("queryNewsDetail");
		ResponseVO resVo =null;
		try {
			ivo.set("username", "1002");
			ivo.set("password", "000000");
			ivo.set("sid", "1002");
			ivo.set("news_id", "2");
			resVo =execute(ivo);
		} catch (JException e) {
			e.printStackTrace();
		}
		System.out.print("response====\n"+resVo==null?"":resVo.oForm);
	}
}
