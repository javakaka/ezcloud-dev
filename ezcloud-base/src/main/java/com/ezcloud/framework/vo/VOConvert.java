package com.ezcloud.framework.vo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ezcloud.framework.data.DBConf;
import com.ezcloud.framework.exp.JException;
import com.ezcloud.framework.util.StringUtil;
import com.ezcloud.framework.util.XmlUtil;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
/**
 * 交互对象和xml转换类
 * @author Tong
 */
public class VOConvert
{
	/**
	 * 将ivo转化为xml
	 * @param ivo
	 * @return
	 * @throws JException
	 */
    @SuppressWarnings("unchecked")
	public static String ivoToXml(IVO ivo) throws JException {
	String sXmlTag = "Juts";
	Element element = null;
	//参数的<parameters>标签
	Element defaultParamsElement = null;
	String sServiceName = ivo.sService;
	String sServiceType =String.valueOf(ivo.getType());
	String sXml;
	try {
	    Row row = ivo.oForm;
	    Document document = XmlUtil.newDom(sXmlTag);
	    Element oRootElement = document.getDocumentElement();
	    XmlUtil.createAttribute(document, oRootElement, "VERSION", "4.0");
	    XmlUtil.createAttribute(document, oRootElement, "COMPANY","DEV");
	    XmlUtil.createAttribute(document, oRootElement, "COPYRIGHT","2010~2020");
	    Element eServiceElement = (Element) XmlUtil.createElement(document, oRootElement,"SERVICE", null);
	    XmlUtil.createAttribute(document, eServiceElement, "NAME",sServiceName);
	    XmlUtil.createAttribute(document, eServiceElement, "TYPE",sServiceType);
	    //取得ivo对象的所有参数的key数组
	    Object[] objects = row.keySet().toArray();
	  //控制三种类型的参数，在xml文件中只有一个总的节点
	    boolean bRequestElement = true;
	  //控制三种类型的参数的<PARAMETERS>节点分别只有一个
	    boolean bDefaultParametersElement = true;
	    //先将所有的字符串值遍历出来，存储到单值节点区域中
	    for (int i = 0; i < objects.length; i++) {
		if (objects[i].toString().indexOf("-") == -1) {
		    Object rowObject = row.get(objects[i]);
		    //Object object_10_ = rowObject;
		    //字符串
		    if (rowObject.getClass().getName().equals("java.lang.String")) 
		    {
		    //开始----------------------------------------只执行一次
		    //创建REQUEST节点，该节点在同一个xml中只能有一个
			if (bRequestElement) {
			    element= (Element) XmlUtil.createElement(document,oRootElement,"REQUEST",null);
			    bRequestElement = false;
			}
			/**
			 *创建PARAMETERS节点，当bDefaultParametersElement为true时，
			 *表示创建存储单值节点的PARAMETERS节点，NAME属性为 “DEFAULT”，此节点下的节点表示单值参数
			 */
			if (bDefaultParametersElement) {
			    defaultParamsElement= ((Element)XmlUtil.createElement(document, element,"PARAMETERS", null));
			    XmlUtil.createAttribute(document, defaultParamsElement,"NAME", "DEFAULT");
			    bDefaultParametersElement = false;
			}
			//结束----------------------------------------只执行一次
			//存储时，将键名转为大写
			XmlUtil.createElement(document, defaultParamsElement,objects[i].toString().toUpperCase(),rowObject.toString());
		    }
		}
	    }
	    //遍历所有Row类型的键值
	    for (int i = 0; i < objects.length; i++) 
	    {
			Object oRow = row.get(objects[i]);
			if (objects[i].toString().indexOf("-") == -1 && oRow.getClass().getName().equals("com.ezcloud.framework.vo.Row")) 
			{
			    if (bRequestElement) 
			    {
					element = (Element) XmlUtil.createElement(document,oRootElement,"REQUEST", null);
					bRequestElement = false;
			    }
			    boolean eRowElement = true;
			    /**
			     *创建存储Row数据的节点 ,每个 Row 对象的xml结构为
			     *<PARAMETERS NAME="key"><ROW>...</ROW></PARAMETERS>
			     */
			    if (eRowElement) 
			    {
					defaultParamsElement = ((Element)XmlUtil.createElement(document, element,"PARAMETERS", null));
					XmlUtil.createAttribute(document, defaultParamsElement,"NAME", objects[i].toString());
					//创建Row标签
					defaultParamsElement  = (Element) XmlUtil.createElement(document,defaultParamsElement,"ROW", null);
					eRowElement = false;
			    }
			    
			    HashMap<String,Object> hashmap = (HashMap<String,Object>) oRow;
			    Object[] rowProperties = hashmap.keySet().toArray();
			    //遍历Row类型参数，将其中的键值对添加到xml文件
			    for (int iRow = 0; iRow < rowProperties.length; iRow++) 
			    {
					Object rowEntity= hashmap.get(rowProperties[iRow]);
					XmlUtil.createElement(document, defaultParamsElement,rowProperties[iRow].toString().toUpperCase(),rowEntity.toString());
			    }
			}
	    }
	    
	    //遍历DataSet
	    for (int i = 0; i < objects.length; i++) 
	    {
			if (objects[i].toString().indexOf("-") == -1) 
			{
			    Object oDataSet = row.get(objects[i]);
			    Object oDataSetBak = oDataSet;
			    if (oDataSet.getClass().getName().equals("com.ezcloud.framework.vo.DataSet")) 
				{
					boolean bResponsePARAMETERSElement = true;
					//创建一个名为RESPONSE空节点，表示无返回数据
					if (bRequestElement) 
					{
					    element = (Element) XmlUtil.createElement(document,oRootElement,"RESPONSE",null);
					    bRequestElement = false;
					}
					ArrayList<Object> arraylist = (ArrayList<Object>) oDataSetBak;
					//创建DataSet类型参数的汇总信息
					/**
					 * 每个DataSet对象的xml结构为
					 * <PARAMETERS NAME="key" ROWS="length"><ROW SEQ="1">...</ROW><ROW SEQ="2">...</ROW>...<ROW SEQ="n">...</ROW></PARAMETERS>
					 */
					if (bResponsePARAMETERSElement) 
					{
					    defaultParamsElement = ((Element)XmlUtil.createElement(document, element,"PARAMETERS", null));
					    XmlUtil.createAttribute(document, defaultParamsElement,"NAME",objects[i].toString());
					    XmlUtil.createAttribute(document, defaultParamsElement, "ROWS",String.valueOf(arraylist.size()));
					    bResponsePARAMETERSElement = false;
					}
					//DataSet类型的参数包含Row类型键值对，Row的下标，从1开始
					int iDataSetRowIndex = 0;
					for (int dsRowIndex = 0; dsRowIndex < arraylist.size();dsRowIndex++) 
					{
					    Object oDataSetEntity = arraylist.get(dsRowIndex);
					    Object oDataSetEntityBak = oDataSetEntity;
					    if (oDataSetEntity.getClass().getName().equals("com.ezcloud.framework.vo.Row")) 
						{
							Row oDataSetRow = (Row) oDataSetEntityBak;
							Row oDataSetRowBak = oDataSetRow;
							Object[] oDataSetRowProperties = oDataSetRow.keySet().toArray();
							iDataSetRowIndex++;
							Element oDataSetRowElement= ((Element)XmlUtil.createElement(document,defaultParamsElement, "ROW",null));
							XmlUtil.createAttribute(document,oDataSetRowElement, "SEQ",String.valueOf(iDataSetRowIndex));
							for (int iRow = 0; iRow < oDataSetRowProperties.length;iRow++) 
							{
								//Row的键值对
							    Object oDataSetRowPropertiesEntity = oDataSetRowBak.get(oDataSetRowProperties[iRow]);
							    Object oDataSetRowPropertiesEntityBak = oDataSetRowPropertiesEntity;
							    if (oDataSetRowPropertiesEntity != null)
								XmlUtil.createElement(document, oDataSetRowElement,oDataSetRowProperties[iRow].toString(),oDataSetRowPropertiesEntityBak.toString());
							}
					    }
					}
			    }
			}
	    }
	    sXml = XmlUtil.toString(document);
	} 
	catch (Exception e) 
	{
	    e.printStackTrace();
	    throw new JException(-1,"将ivo转化为xml出错");
	}
	return sXml;
    }
    
    /**
     * 将ovo转化为xml
     * @param ovo
     * @return
     * @throws JException
     */
    @SuppressWarnings("unchecked")
	public static String ovoToXml(OVO ovo) throws JException {
	String docName = "Juts";
	Element element = null;
	Element elementParameter = null;
	String sServiceName = ovo.sService;
	String sOVOToXMLStr;
	try {
	    Row row = ovo.oForm;
	    Document oDocument = XmlUtil.newDom(docName);
	    Document oDocumentBak = oDocument;
	    Element oRootElement = oDocument.getDocumentElement();
	    XmlUtil.createAttribute(oDocumentBak, oRootElement, "VERSION","4.0");
	    XmlUtil.createAttribute(oDocumentBak, oRootElement, "COMPANY","DEV");
	    XmlUtil.createAttribute(oDocumentBak, oRootElement, "COPYRIGHT","2010~2020");
	    Element oElement = (Element) XmlUtil.createElement(oDocumentBak, oRootElement,"SERVICE", null);
	    XmlUtil.createAttribute(oDocumentBak, oElement, "NAME",sServiceName);
	    //创建返回值节点
	    oElement = (Element) XmlUtil.createElement(oDocumentBak, oRootElement, "RETURN", null);
	    XmlUtil.createElement(oDocumentBak, oElement, "CODE",String.valueOf(ovo.iCode));
	    XmlUtil.createElement(oDocumentBak, oElement, "MSG", ovo.sMsg);
	    XmlUtil.createElement(oDocumentBak, oElement, "EXP", ovo.sExp);
	    //取得ovo所携带的参数数据
	    Object[] objects = row.keySet().toArray();
	    boolean bResponseElement = true;
	    boolean bParametersElement = true;
	    //遍历单值参数
	    for (int i = 0; i < objects.length; i++) 
	    {
			if (objects[i].toString().indexOf("-") == -1) 
			{
			    Object objectDefault = row.get(objects[i]);
			    if (objectDefault.getClass().getName().equals("java.lang.String")) 
			    {
					if (bResponseElement) 
					{
					    element = (Element) XmlUtil.createElement(oDocumentBak, oRootElement, "RESPONSE", null);
					    bResponseElement = false;
					}
					if (bParametersElement) 
					{
						elementParameter = ((Element) XmlUtil.createElement(oDocumentBak, element, "PARAMETERS", null));
					    XmlUtil.createAttribute(oDocumentBak, elementParameter, "NAME", "DEFAULT");
					    bParametersElement = false;
					}
					XmlUtil.createElement(oDocumentBak, elementParameter, objects[i].toString() .toUpperCase(), objectDefault.toString());
			    }
			}
	    }
	    //遍历Row类型的参数
	    for (int i = 0; i < objects.length; i++) 
	    {
			Object objectRow = row.get(objects[i]);
			if (objects[i].toString().indexOf("-") == -1 && objectRow.getClass().getName().equals("com.ezcloud.framework.vo.Row")) 
			{
				//每个Row对象创建一个<PARAMETERS NAME="KEY"><ROW></ROW></PARAMETERS>的节点
			    boolean boolPerRow = true;
			    if (bResponseElement) 
			    {
					element = (Element) XmlUtil.createElement(oDocumentBak, oRootElement, "RESPONSE", null);
					bResponseElement = false;
			    }
			    if (boolPerRow) 
			    {
			    	elementParameter = ((Element) XmlUtil.createElement(oDocumentBak, element, "PARAMETERS", null));
					XmlUtil.createAttribute(oDocumentBak, elementParameter, "NAME", objects[i].toString());
					elementParameter = (Element) XmlUtil.createElement(oDocumentBak, elementParameter, "ROW", null);
					boolPerRow = false;
			    }
			    HashMap<String,Object> hashmap = (HashMap<String,Object>) objectRow;
			    Object[] objectsOfRow = hashmap.keySet().toArray();
			    for (int iRow = 0; iRow < objectsOfRow.length; iRow++) 
			    {
					Object objectValue = hashmap.get(objectsOfRow[iRow]);
					XmlUtil.createElement(oDocumentBak, elementParameter,objectsOfRow[iRow].toString().toUpperCase(), objectValue.toString());
			    }
			}
	    }
	  //遍历DataSet类型的参数
	    for (int i = 0; i < objects.length; i++)
	    {
			if (objects[i].toString().indexOf("-") == -1) 
			{
			    Object objectDataSet = row.get(objects[i]);
			    if (objectDataSet.getClass().getName().equals("com.ezcloud.framework.vo.DataSet")) 
			    {
			    	//为每个DataSet对象创建<PARAMETERS NAME="Key" ROW="length"><ROW>....</ROW></PARAMETERS>形式的XML节点
					boolean boolPerDataSet = true;
					if (bResponseElement) 
					{
					    element= (Element) XmlUtil.createElement(oDocumentBak, oRootElement,"RESPONSE",null);
					    bResponseElement = false;
					}
					ArrayList<Object> arraylist = (ArrayList<Object>) objectDataSet;
					if (boolPerDataSet) 
					{
						elementParameter= ((Element)XmlUtil.createElement(oDocumentBak, element,"PARAMETERS", null));
					    XmlUtil.createAttribute(oDocumentBak, elementParameter,"NAME",objects[i].toString());
					    XmlUtil.createAttribute(oDocumentBak, elementParameter, "ROWS",String.valueOf(arraylist.size()));
					    boolPerDataSet = false;
					}
					int iDataSetRowIndex = 0;
					for (int iDataSet = 0; iDataSet < arraylist.size();iDataSet++) 
					{
					    Object objectDataSet1 = arraylist.get(iDataSet);
					    //为每个Row对象创建xml节点，形式为：<ROW SEQ="1">...</ROW>
					    if (objectDataSet1.getClass().getName().equals("com.ezcloud.framework.vo.Row")) 
					    {
							HashMap<String,Object> hashmap = (HashMap<String,Object>) objectDataSet1;
							Object[] objectsOfRowOfPerDataSet = hashmap.keySet().toArray();
							iDataSetRowIndex++;
							Element elementRowOfPerDataSet = ((Element) XmlUtil.createElement(oDocumentBak, elementParameter, "ROW", null));
							XmlUtil.createAttribute(oDocumentBak, elementRowOfPerDataSet, "SEQ", String.valueOf(iDataSetRowIndex));
							for (int iRowOfDataSet = 0; iRowOfDataSet < objectsOfRowOfPerDataSet.length; iRowOfDataSet++) 
							{
							    Object objectOfRow = hashmap.get(objectsOfRowOfPerDataSet[iRowOfDataSet]);
							    if (objectOfRow != null){
							    	XmlUtil.createElement (oDocumentBak, elementRowOfPerDataSet, objectsOfRowOfPerDataSet[iRowOfDataSet].toString(), objectOfRow.toString());
							    }
							    else{
							    	XmlUtil.createElement (oDocumentBak, elementRowOfPerDataSet, objectsOfRowOfPerDataSet[iRowOfDataSet].toString(),"");
							    }
							}
					    }
					}
			    }
			}
	    }
	    sOVOToXMLStr = XmlUtil.toString(oDocumentBak);
	} catch (Exception exception) {
	    exception.printStackTrace();
	    throw new JException(-1,"将OVO对象转换为XML文档出错");
	}
	return sOVOToXMLStr;
    }
    
    /**将xml文件转化为ivo输入对象*/
    @SuppressWarnings("unchecked")
	public static IVO xmlToIvo(String string) throws JException {
	IVO ivo = new IVO();
	Document document = XmlUtil.toXml(string);
	Element element = document.getDocumentElement();
	Node node = XmlUtil.findChildByName(element, "SERVICE");
	ivo.sService = XmlUtil.findAttrByName((Element) node, "NAME").getNodeValue();
	ivo.setType(Integer.parseInt(XmlUtil.findAttrByName((Element) node, "TYPE").getNodeValue()));
	Node nodeREQUEST= XmlUtil.findChildByName(element, "REQUEST");
	if (nodeREQUEST != null) 
	{
	    NodeList nodelist = nodeREQUEST.getChildNodes();
	    for (int i = 0; i < nodelist.getLength(); i++) 
	    {
			Node oNode = nodelist.item(i);
			if (oNode != null && oNode.getNodeType() == 1) 
			{
				//根据<REQUEST>节点下的<PARAMETERS>节点的NAME属性，判断是否是单值节点数据
			    org.w3c.dom.Attr attr = XmlUtil.findAttrByName((Element) oNode, "NAME");
			    if (attr != null) 
			    {
					String sName = attr.getNodeValue();
					/**取简单值，单个值（key-value）*/
					if (sName.equalsIgnoreCase("DEFAULT")) 
					{
					    NodeList nodelist1 = oNode.getChildNodes();
					    int k = nodelist1.getLength();
					    for (int j = 0; j < k; j++) 
						{
							Node nodeTemp = nodelist1.item(j);
							if (nodeTemp.getNodeType() == 1) 
							{
							    String sTemp = nodeTemp.getNodeName().toUpperCase().trim();
							    String sValue ="";
							    try {
								sValue = nodeTemp.getFirstChild().getNodeValue();
							    } 
							    catch (org.w3c.dom.DOMException domexception) 
							    {
							    	sValue = "";
							    } catch (NullPointerException nullpointerexception) 
							    {
							    	sValue = "";
							    }
							    if (DBConf._DATABASE_TYPE.equalsIgnoreCase(Final._TYPE_MYSQL))
								sValue =StringUtil.isoToGBK(sValue,true);
							    ivo.set(sTemp, sValue);
							}
					    }
					} 
					/**取Row和DataSet格式的数据*/
					else {
					    boolean bool = false;
					    //如果<PARAMETERS>节点包含ROW属性，则这个节点表示DataSet对象的数据
					    if (XmlUtil.findAttrByName((Element) oNode,"ROWS")== null)
						bool = true;
					    NodeList nodelistRow = oNode.getChildNodes();
					    int rowNum = nodelistRow.getLength();
					    DataSet dataset = null;
					    Row row = new Row();
					    for (int m = 0; m < rowNum; m++) 
					    {
						 oNode = nodelistRow.item(m);
						if (oNode.getNodeType() == 1) 
						{
						    NodeList oNodeList1 = oNode.getChildNodes();
						    int listNum = oNodeList1.getLength();
						    row = new Row();
						    for (int n = 0; n < listNum; n++) 
						    {
								Node oNode2 = oNodeList1.item(n);
								if (oNode2.getNodeType() == 1) 
								{
								    String  srowNodeName = oNode2.getNodeName().toUpperCase().trim();
								    String sValue ="";
								    try 
								    {
								    	sValue = oNode2.getFirstChild().getNodeValue();
								    } catch (org.w3c.dom.DOMException domexception) 
								    {
								    	sValue = "";
								    } catch (NullPointerException nullpointerexception) 
								    {
								    	sValue = "";
								    }
								    row.put(srowNodeName,sValue);
								}
						    }
						    if (!bool) 
						    {
							if (dataset == null) 
							{
							    dataset = new DataSet();
							}
							dataset.add(row);
						    }
						}
					    }
					    if (bool)
						ivo.set(sName, row);
					    if (dataset != null)
						ivo.set(sName, dataset);
					}
			    }
			}
	    }
	}
	return ivo;
    }
    
    /**
     * 将xml文件转化为输出对象OVO
     * @param xmlString
     * @return
     * @throws JException
     */
    @SuppressWarnings("unchecked")
	public static OVO xmlToOvo(String xmlString) throws JException 
    {
		OVO ovo = new OVO();
		Document document = XmlUtil.toXml(xmlString);
		Element rootElement = document.getDocumentElement();
		Node node = XmlUtil.findChildByName(rootElement, "SERVICE");
		ovo.sService = XmlUtil.findAttrByName((Element) node, "NAME").getNodeValue();
		ovo.iCode = Integer.parseInt(XmlUtil.getProperty(document, "RETURN.CODE"));
		ovo.sMsg = XmlUtil.getProperty(document, "RETURN.MSG");
		ovo.sExp = XmlUtil.getProperty(document, "RETURN.EXP");
		Node nodeResponse = XmlUtil.findChildByName(rootElement, "RESPONSE");
		//遍历<RESPONSE>节点，取得所有的返回数据
		if (nodeResponse != null) 
		{
		    NodeList nodeListOfResponse = nodeResponse.getChildNodes();
		    for (int i = 0; i < nodeListOfResponse.getLength(); i++) 
		    {
				Node nodeOfResponse = nodeListOfResponse.item(i);
				if (nodeOfResponse != null && nodeOfResponse.getNodeType() == 1) 
				{
					//根据参数的节点的NAME属性判断该节点属于那种类型，String Row DataSet
				    String nodeName = XmlUtil.findAttrByName((Element) nodeOfResponse, "NAME").getNodeValue();
				    //取得单值参数节点，
				    if (nodeName.equalsIgnoreCase("DEFAULT")) 
				    {
						NodeList nodeListOfSingleNodes = nodeOfResponse.getChildNodes();
						int singleNodeNum = nodeListOfSingleNodes.getLength();
						for (int iSingleNode = 0; iSingleNode < singleNodeNum; iSingleNode++) 
						{
						    Node nodeSingle = nodeListOfSingleNodes.item(iSingleNode);
						    if (nodeSingle.getNodeType() == 1) 
						    {
							String singleNodeName = nodeSingle.getNodeName().toUpperCase().trim();
							String singleNodeValue ="";
							try {
								singleNodeValue = nodeSingle.getFirstChild().getNodeValue();
							} catch (org.w3c.dom.DOMException domexception) 
							{
								singleNodeValue = "";
							} catch (NullPointerException nullpointerexception) 
							{
								singleNodeValue = "";
							}
							if (DBConf._DATABASE_TYPE.equalsIgnoreCase(Final._TYPE_MYSQL))
								singleNodeValue = StringUtil.isoToGBK(singleNodeValue,true);
								ovo.set(singleNodeName, singleNodeValue);
						    }
						}
				    } 
				    //取Row 和DataSet,根据节点是否有ROWS属性来区分，包含ROWS属性的为DataSet类型
				    else 
				    {
				    //true表示DataSet,false表示Row
					boolean bool = false;
					if (XmlUtil.findAttrByName((Element) nodeOfResponse, "ROWS") == null)
					    bool = true;
					NodeList nodeListOfRowOrDataSet = nodeOfResponse.getChildNodes();
					int iChildrenNum = nodeListOfRowOrDataSet.getLength();
					DataSet dataset = null;
					for (int iChild = 0; iChild < iChildrenNum; iChild++) 
					{
					    Node nodeOfRowOrDataSet = nodeListOfRowOrDataSet.item(iChild);
					    if (nodeOfRowOrDataSet.getNodeType() == 1) 
					    {
						NodeList nodeListOfRow = nodeOfRowOrDataSet.getChildNodes();
						int iChildNumOfRow = nodeListOfRow.getLength();
						Row row = new Row();
						for (int iChildOfRow = 0; iChildOfRow < iChildNumOfRow; iChildOfRow++) 
						{
						    Node nodeOfRow = nodeListOfRow.item(iChildOfRow);
						    if (nodeOfRow.getNodeType() == 1) 
						    {
								String nodeName1 = nodeOfRow.getNodeName().toUpperCase().trim();
								String nodeValue1 ="";
							try 
							{
								nodeValue1 = nodeOfRow.getFirstChild().getNodeValue();
							} catch (org.w3c.dom.DOMException domexception) 
							{
								nodeValue1 = "";
							} catch (NullPointerException nullpointerexception) 
							{
								nodeValue1 = "";
							}
							row.put(nodeName1, nodeValue1);
						    }
						}
						if (dataset == null) 
						{
						    dataset = new DataSet();
						}
						dataset.add(row);
					    }
					}
					if (bool && dataset.size() == 1)
						ovo.set(nodeName, dataset.get(0));
					else if (dataset != null)
						ovo.set(nodeName, dataset);
				    }
				}
		    }
		}
		return ovo;
    }
    
    /**
	 * 将ivo转化为json
	 * @param ivo
	 * @return
	 * @throws JException
	 */
    @SuppressWarnings("unchecked")
	public static String ivoToJson(IVO ivo) throws JException {
	//参数的<parameters>标签
	String sServiceName = ivo.sService;
	String sServiceType =String.valueOf(ivo.getType());
	Map<Object,Object> mapJson =new HashMap<Object,Object>();
	Map<Object,Object> mapHeader =new HashMap<Object,Object>();
	Map<Object,Object> mapService =new HashMap<Object,Object>();
	Map<Object,Object> mapRequest =new HashMap<Object,Object>();
	Map<Object,Object> mapDefault =new HashMap<Object,Object>();
	Map<Object,Object> mapRow =new HashMap<Object,Object>();
	Map<Object,Object> mapDataSet =new HashMap<Object,Object>();
	Map<Object,Object> map =new HashMap<Object,Object>();
	ArrayList<Object> rowArrayList =new ArrayList<Object>();
	ArrayList<Object> dataSetArrayList =new ArrayList<Object>();
	try {
	    Row row = ivo.oForm;

	    //HEADER
	    mapHeader =new HashMap<Object,Object>();
	    mapHeader.put("VERSION","4.0");
	    mapHeader.put("COMPANY", "DEV");
	    mapHeader.put("COPYRIGHT", "COPYRIGHT2010-2020");
	    //SERVICE
	    mapService =new HashMap<Object,Object>();
	    mapService.put("NAME",sServiceName);
	    mapService.put("TYPE",sServiceType);
	    //取得ivo对象的所有参数的key数组
	    Object[] objects = row.keySet().toArray();
	  //控制三种类型的参数，在xml文件中只有一个总的节点
	  //控制三种类型的参数的<PARAMETERS>节点分别只有一个
	    //先将所有的字符串值遍历出来，存储到单值节点区域中
	    for (int i = 0; i < objects.length; i++) {
			if (objects[i].toString().indexOf("-") == -1) {
			    Object rowObject = row.get(objects[i]);
			    //字符串
			    if (rowObject.getClass().getName().equals("java.lang.String")) 
			    {
			    	mapDefault.put(objects[i].toString().toUpperCase(),rowObject.toString());
			    }
			}
	    }
	    mapRequest.put("DEFAULT", mapDefault);
	    //遍历所有Row类型的键值
	    for (int i = 0; i < objects.length; i++) 
	    {
			Object oRow = row.get(objects[i]);
			if (objects[i].toString().indexOf("-") == -1 && oRow.getClass().getName().equals("com.ezcloud.framework.vo.Row")) 
			{
				mapRow =new HashMap<Object,Object>();
			    HashMap<Object,Object> hashmap = (HashMap<Object,Object>) oRow;
			    Object[] rowProperties = hashmap.keySet().toArray();
			    //遍历Row类型参数，将其中的键值对添加到xml文件
			    map =new HashMap<Object,Object>();
			    for (int iRow = 0; iRow < rowProperties.length; iRow++) 
			    {
					Object rowEntity= hashmap.get(rowProperties[iRow]);
					map.put(rowProperties[iRow].toString().toUpperCase(),rowEntity.toString());
			    }
			    mapRow.put("VALUE", map);
			    rowArrayList.add(mapRow);
			}
	    }
	    mapRequest.put("ROW", rowArrayList);
	    //遍历DataSet
	    for (int i = 0; i < objects.length; i++) 
	    {
			if (objects[i].toString().indexOf("-") == -1) 
			{
			    Object oDataSet = row.get(objects[i]);
			    Object oDataSetBak = oDataSet;
			    if (oDataSet.getClass().getName().equals("com.ezcloud.framework.vo.DataSet")) 
				{
			    	mapDataSet =new HashMap<Object,Object>();
					boolean bResponsePARAMETERSElement = true;
					ArrayList<Object> arraylist = (ArrayList<Object>) oDataSetBak;
					//创建DataSet类型参数的汇总信息
					/**
					 * 每个DataSet对象的xml结构为
					 * <PARAMETERS NAME="key" ROWS="length"><ROW SEQ="1">...</ROW><ROW SEQ="2">...</ROW>...<ROW SEQ="n">...</ROW></PARAMETERS>
					 */
					if (bResponsePARAMETERSElement) 
					{
					    mapDataSet.put("NAME", objects[i].toString());
					    mapDataSet.put("ROWS",String.valueOf(arraylist.size()));
					    bResponsePARAMETERSElement = false;
					}
					//DataSet类型的参数包含Row类型键值对，Row的下标，从1开始
					int iDataSetRowIndex = 0;
					ArrayList<Object> tempList =new ArrayList<Object>();
					for (int i_25_ = 0; i_25_ < arraylist.size();i_25_++) 
					{
					    Object oDataSetEntity = arraylist.get(i_25_);
					    Object oDataSetEntityBak = oDataSetEntity;
					    if (oDataSetEntity.getClass().getName().equals("com.ezcloud.framework.vo.Row")) 
						{
					    	map =new HashMap<Object,Object>();
							Row oDataSetRow = (Row) oDataSetEntityBak;
							Row oDataSetRowBak = oDataSetRow;
							Object[] oDataSetRowProperties = oDataSetRow.keySet().toArray();
							iDataSetRowIndex =iDataSetRowIndex+1;
							for (int iRow = 0; iRow < oDataSetRowProperties.length;iRow++) 
							{
								//Row的键值对
							    Object oDataSetRowPropertiesEntity = oDataSetRowBak.get(oDataSetRowProperties[iRow]);
							    Object oDataSetRowPropertiesEntityBak = oDataSetRowPropertiesEntity;
							    if (oDataSetRowPropertiesEntity != null)
							    map.put(oDataSetRowProperties[iRow].toString(),oDataSetRowPropertiesEntityBak.toString());
							}
							tempList.add(map);
					    }
					}
					mapDataSet.put("VALUE", tempList);
					dataSetArrayList.add(mapDataSet);
			    }
			}
	    }
	    mapRequest.put("DATASET", dataSetArrayList);
	} 
	catch (Exception e) 
	{
	    e.printStackTrace();
	    throw new JException(-1,"将ivo转化为xml出错");
	}
	mapJson.put("HEADER", mapHeader);
	mapJson.put("SERVICE",mapService );
	mapJson.put("REQUEST",mapRequest);
	Gson gson =new Gson();
	String gsonStr =gson.toJson(mapJson);
	return gsonStr;
    }

    
    /**
     * 将ovo转化为Json
     * @param ovo
     * @return
     * @throws JException
     */
    @SuppressWarnings("unchecked")
	public static String ovoToJson(OVO ovo) throws JException {
	String string_36_ = "";
	String sServiceName = ovo.sService;
	string_36_ = string_36_ == null || string_36_.equals("") ? " " : string_36_;
	String sOVOToJson ;
	StringBuffer sb =new StringBuffer(1024);
	sb.append("{");
	try {
	    Row row = ovo.oForm;
	    //创建HERDER项
	    sb.append("\"HEADER\":").append("{");
	    sb.append("\"VERSION\"").append(":").append("\"4.0\"").append(",");
	    sb.append("\"COMPANY\"").append(":").append("\"DEV\"").append(",");
	    sb.append("\"COPYRIGHT\"").append(":").append("\"2010-2020\"");
	    sb.append("}");
	    //HEADER END
	    //创建SERVICE项
	    //服务名不为空时，创建Service 项
	    if(sServiceName != null && sServiceName.trim().length() > 0)
	    {
	    	sb.append(",");
	    	sb.append("\"SERVICE\":").append("{");
	 	    sb.append("\"NAME\"").append(":").append("\""+sServiceName+"\"").append(",");
	 	    sb.append("\"TYPE\"").append(":").append("\"1\"");
	 	    sb.append("}");//END OF SERVICE
	    }
	    //创建RETURN项
	    sb.append(",");
	    sb.append("\"RETURN\":").append("{");
	    sb.append("\"CODE\"").append(":").append("\""+ovo.iCode+"\"").append(",");
	    sb.append("\"MSG\"").append(":").append("\""+ovo.sMsg+"\"").append(",");
	    sb.append("\"EXP\"").append(":").append("\""+ovo.sExp+"\"");
	    sb.append("}");
	    //END of RETURN
	    //取得ovo所携带的参数数据
	    Object[] objects = row.keySet().toArray();
	    boolean bResponseElement = true;
	    boolean bParametersElement = true;
	    //遍历单值参数
	    boolean bHasDefaultParams =true;
	    boolean bHasDefaultOrRow =false;
	    int iDefaultIndex =0;
	    int dedaultFieldTotalNum =0;
	    for (int i = 0; i < objects.length; i++) 
	    {
			if (objects[i].toString().indexOf("-") == -1) 
			{
			    Object objectDefault = row.get(objects[i]);
			    if (objectDefault.getClass().getName().equals("java.lang.String")) 
			    {
			    	dedaultFieldTotalNum ++;
			    }
			}
	    }
	    for (int i = 0; i < objects.length; i++) 
	    {
			if (objects[i].toString().indexOf("-") == -1) 
			{
			    Object objectDefault = row.get(objects[i]);
			    if (objectDefault.getClass().getName().equals("java.lang.String")) 
			    {
			    	bHasDefaultParams =false;
			    	bHasDefaultOrRow =true;
			    	iDefaultIndex ++;
					if (bResponseElement) 
					{
					    sb.append(",");
					    sb.append("\"RESPONSE\":").append("{");//start of response
					    bResponseElement = false;
					}
					if (bParametersElement) 
					{
					    sb.append("\"DEFAULT\":").append("{");//start of default
					    bParametersElement = false;
					}
					if( iDefaultIndex > 1 ){
						sb.append(",");
					}
					sb.append("\""+objects[i].toString() .toUpperCase()+"\"").append(":").append("\""+StringUtil.string2Json( objectDefault.toString() )+"\"");
					if(i == objects.length -1 || iDefaultIndex ==dedaultFieldTotalNum)
					{
						 sb.append("}");//end of default parameter
						 break;
					}
			    }
			}
	    }
	    //遍历Row类型的参数
	    boolean bHasRowParams =false;
	    int iRowIndex =0;
	    for (int i = 0; i < objects.length; i++) 
	    {
			Object objectRow = row.get(objects[i]);
			if (objects[i].toString().indexOf("-") == -1 && objectRow.getClass().getName().equals("com.ezcloud.framework.vo.Row")) 
			{
				iRowIndex ++;
				bHasRowParams =true;
				bHasDefaultOrRow =true;
				//每个Row对象创建一个<PARAMETERS NAME="KEY"><ROW></ROW></PARAMETERS>的节点
			    boolean boolPerRow = true;
			    if (bResponseElement) 
			    {
				    sb.append(",");
				    sb.append("\"RESPONSE\":").append("{");//start of response
					bResponseElement = false;
			    }
			    if (boolPerRow) 
			    {
					if( !bHasDefaultParams )
					{
						sb.append(",");
						bHasDefaultParams =true;
					}
					if(iRowIndex > 1 )
					{
						sb.append(",");
					}
					sb.append("\"ROW\":").append("{");
					sb.append("\"NAME\":").append("\""+objects[i].toString()+"\"").append(",");
					sb.append("\"VALUE\":").append("{");
					boolPerRow = false;
			    }
			    HashMap<Object,Object> hashmap = (HashMap<Object,Object>) objectRow;
			    Object[] objectsOfRow = hashmap.keySet().toArray();
			    for (int iRow = 0; iRow < objectsOfRow.length; iRow++) 
			    {
					Object objectValue = hashmap.get(objectsOfRow[iRow]);
					//Row 的value 的值列表
					if(iRow > 0){
						sb.append(",");
					}
					sb.append("\""+objectsOfRow[iRow].toString().toUpperCase()+"\"").append(":").append("\""+ StringUtil.string2Json( objectValue.toString() )+"\"");
			    }
			    sb.append("}");//end of per Row's value
			    sb.append("}");//end of per Row
			}
	    }
	  //遍历DataSet类型的参数
	    int iDataSetIndex =0;
	    for (int i = 0; i < objects.length; i++)
	    {
			if (objects[i].toString().indexOf("-") == -1) 
			{
			    Object objectDataSet = row.get(objects[i]);
			    if (objectDataSet.getClass().getName().equals("com.ezcloud.framework.vo.DataSet")) 
			    {
			    	iDataSetIndex ++;
			    	//为每个DataSet对象创建<PARAMETERS NAME="Key" ROW="length"><ROW>....</ROW></PARAMETERS>形式的XML节点
					boolean boolPerDataSet = true;
					if (bResponseElement) 
					{
					    sb.append(",");
					    sb.append("\"RESPONSE\":").append("{");//start of response
					    bResponseElement = false;
					}
					ArrayList<Object> arraylist = (ArrayList<Object>) objectDataSet;
					if (boolPerDataSet) 
					{
					    if( ! bHasRowParams)
					    {
					    	sb.append(",");
					    	bHasRowParams =true;
					    }
					    else {
					    	if(bHasDefaultOrRow ==true && iDataSetIndex ==1){
					    		sb.append(",");
					    		bHasDefaultOrRow =false;
					    	}
					    }
					    if(iDataSetIndex > 1)
					    {
					    	sb.append(",");
					    }
					    //创建DATASET
						sb.append("\"DATASET\":").append("{");
						sb.append("\"NAME\":").append("\""+objects[i].toString()+"\"").append(",");
						sb.append("\"ROWS\":").append("\""+String.valueOf(arraylist.size())+"\"").append(",");
						sb.append("\"VALUE\":").append("[");
					    boolPerDataSet = false;
					}
					int iDataSetRowIndex = 0;
					for (int iDataSet = 0; iDataSet < arraylist.size();iDataSet++) 
					{
					    Object objectDataSet1 = arraylist.get(iDataSet);
					    //为每个Row对象创建json节点
					    if (objectDataSet1.getClass().getName().equals("com.ezcloud.framework.vo.Row")) 
					    {
							HashMap<Object,Object> hashmap = (HashMap<Object,Object>) objectDataSet1;
							Object[] objectsOfRowOfPerDataSet = hashmap.keySet().toArray();
							iDataSetRowIndex++;
							//创建DATASET 的ROW
							if(iDataSetRowIndex > 1 ){
								sb.append(",");
							}
							sb.append("{");
							int iRowValueIndex =0;
							for (int iRowOfDataSet = 0; iRowOfDataSet < objectsOfRowOfPerDataSet.length; iRowOfDataSet++) 
							{
							    Object objectOfRow = hashmap.get(objectsOfRowOfPerDataSet[iRowOfDataSet]);
							    if (objectOfRow != null){
							    	iRowValueIndex ++;
							    	if(iRowValueIndex > 1){
							    		sb.append(",");
							    	}
							    	sb.append("\""+objectsOfRowOfPerDataSet[iRowOfDataSet].toString()+"\"").append(":").append("\""+StringUtil.string2Json( objectOfRow.toString() )+"\"");
							    }
							    else if (objectOfRow == null || objectOfRow.toString().trim().length() == 0){
							    	iRowValueIndex ++;
							    	if(iRowValueIndex > 1){
							    		sb.append(",");
							    	}
							    	sb.append("\""+objectsOfRowOfPerDataSet[iRowOfDataSet].toString()+"\"").append(":").append("\"\"");
							    }
							}
							sb.append("}");//end of per Row
					    }
					}
					sb.append("]");//end of per DATASET's value
					sb.append("}");//end of per DATASET
			    }
			}
	    }
	    sb.append("}");//end of RESPONSE
	    sb.append("}"); //end of json
	    sOVOToJson =sb.toString();
	} catch (JSONException exception) {
	    exception.printStackTrace();
	    throw new JException(-1,"将OVO对象转换为json文档出错");
	}
	JSONObject json =JSONObject.fromObject(sOVOToJson);
	sOVOToJson =json.toString();
	return sOVOToJson;
    }    

    /**
     * 将json格式字符串转化为IVO
     * @param json
     * @return
     * @throws JException 
     */
    @SuppressWarnings("rawtypes")
	public static IVO jsonToIvo (String json) throws JException
    {
    	IVO ivo =new IVO();
    	JSONObject obj =JSONObject.fromObject(json);
    	JSONObject objTemp =null;
   	 	Iterator iter = obj.keySet().iterator();  
   	 	Iterator iterTemp =null; 
   	 	Object jsonObj =null;
        while(iter.hasNext()) {
            String key = iter.next().toString();  
            jsonObj =obj.get(key);
            //取得服务
            if(key.equalsIgnoreCase("SERVICE")){
            	objTemp =JSONObject.fromObject(jsonObj);
            	String serviceName ="";
            	try{
            		serviceName = objTemp.getString("NAME");
            	}catch(Exception je)
            	{
            		serviceName ="";
            	}
            	if(serviceName == null || serviceName.replace(" ", "").length() == 0)
            	{
            		serviceName ="";
            	}
            	ivo.sService =serviceName;
            	String sType =objTemp.getString("TYPE");
            	//默认为静态服务
            	if(sType == null || sType.trim().length() ==0){
            		sType ="1";
            	}
            }
            //取得输入参数
            if(key.equalsIgnoreCase("REQUEST"))
            {
            	objTemp =JSONObject.fromObject(jsonObj);
            	iterTemp =objTemp.keySet().iterator();
            	String key1 ="";
            	Object jsonObj1 =null;
            	while(iterTemp.hasNext())
            	{
            		key1 =iterTemp.next().toString();
            		jsonObj1 =objTemp.get(key1);
            		if(key1.equalsIgnoreCase("DEFAULT"))
            		{
            			setDefaultValueForIVO(ivo , jsonObj1);
            		}
            		if(key1.equalsIgnoreCase("ROW"))
            		{
            			setRowValueForIVO(ivo,jsonObj1);
            		}
            		if(key1.equalsIgnoreCase("DATASET"))
            		{
            			setDataSetValueForIVO(ivo,jsonObj1);
            		}
            	}
            }
        }  
    	return ivo;
    }
    
    /**
     * 将json格式字符串转化为OVO
     * @param json
     * @return
     * @throws JException 
     */
    @SuppressWarnings("rawtypes")
	public static OVO jsonToOvo (String json) throws JException
    {
    	OVO ovo =new OVO();
    	JSONObject obj =JSONObject.fromObject(json);
    	JSONObject objTemp =null;
   	 	Iterator iter = obj.keySet().iterator();  
   	 	Iterator iterTemp =null; 
   	 	Object jsonObj =null;
        while(iter.hasNext()) {
            String key = iter.next().toString();  
            jsonObj =obj.get(key);
            //取得服务
            if(key.equalsIgnoreCase("SERVICE")){
            	objTemp =JSONObject.fromObject(jsonObj);
           	 ovo.sService =objTemp.getString("NAME");
            }
            //取得汇总信息
            if(key.equalsIgnoreCase("RETURN")){
            	objTemp =JSONObject.fromObject(jsonObj);
            	ovo.iCode =Integer.parseInt(objTemp.getString("CODE"));
            	ovo.sMsg =objTemp.getString("MSG");
            	ovo.sExp =objTemp.getString("EXP");
            }
            //取得返回参数
            if(key.equalsIgnoreCase("RESPONSE"))
            {
            	objTemp =JSONObject.fromObject(jsonObj);
            	iterTemp =objTemp.keySet().iterator();
            	String key1 ="";
            	Object jsonObj1 =null;
            	while(iterTemp.hasNext())
            	{
            		key1 =iterTemp.next().toString();
            		jsonObj1 =objTemp.get(key1);
            		if(key1.equalsIgnoreCase("DEFAULT"))
            		{
            			setDefaultValueForOVO(ovo , jsonObj1);
            		}
            		if(key1.equalsIgnoreCase("ROW"))
            		{
            			setRowValueForOVO(ovo,jsonObj1);
            		}
            		if(key1.equalsIgnoreCase("DATASET"))
            		{
            			setDataSetValueForOVO(ovo,jsonObj1);
            		}
            	}
            }
        }  
    	return ovo;
    }
    
    @SuppressWarnings("rawtypes")
	public static OVO GjsonToOvo (String json) throws JException
    {
    	OVO ovo =new OVO();
    	JSONObject obj =JSONObject.fromObject(json);
    	JSONObject objTemp =null;
   	 	Iterator iter = obj.keySet().iterator();  
   	 	Iterator iterTemp =null; 
   	 	Object jsonObj =null;
        while(iter.hasNext()) {
            String key = iter.next().toString();  
            jsonObj =obj.get(key);
            //取得服务
            if(key.equalsIgnoreCase("SERVICE")){
            	objTemp =JSONObject.fromObject(jsonObj);
           	 ovo.sService =objTemp.getString("NAME");
            }
            //取得汇总信息
            if(key.equalsIgnoreCase("RETURN")){
            	objTemp =JSONObject.fromObject(jsonObj);
            	ovo.iCode =Integer.parseInt(objTemp.getString("CODE"));
            	ovo.sMsg =objTemp.getString("MSG");
            	ovo.sExp =objTemp.getString("EXP");
            }
            //取得返回参数
            if(key.equalsIgnoreCase("RESPONSE"))
            {
            	objTemp =JSONObject.fromObject(jsonObj);
            	iterTemp =objTemp.keySet().iterator();
            	String key1 ="";
            	Object jsonObj1 =null;
            	while(iterTemp.hasNext())
            	{
            		key1 =iterTemp.next().toString();
            		jsonObj1 =objTemp.get(key1);
            		System.out.println("key1==="+key1+"||value==="+jsonObj1.toString());
            		if(key1.equalsIgnoreCase("DEFAULT"))
            		{
            			setDefaultValueForOVO(ovo , jsonObj1);
            		}
            		if(key1.equalsIgnoreCase("ROW"))
            		{
            			setRowValueForOVO(ovo,jsonObj1);
            		}
            		if(key1.equalsIgnoreCase("DATASET"))
            		{
            			setDataSetValueForOVO(ovo,jsonObj1);
            		}
            	}
            }
        }  
    	return ovo;
    }
    
    @SuppressWarnings("rawtypes")
	public static void setDefaultValueForIVO(IVO ovo ,Object obj) throws JException
    {
    	JSONObject json =JSONObject.fromObject(obj);
    	Iterator iter =json.keySet().iterator();
    	String name ="";
    	Object value ="";
    	while(iter.hasNext())
    	{
    		name =iter.next().toString();
    		value =json.get(name);
    		ovo.set(name, value);
    	}
    }
    
    @SuppressWarnings("rawtypes")
	public static void setDefaultValueForOVO(OVO ovo ,Object obj) throws JException
    {
    	JSONObject json =JSONObject.fromObject(obj);
    	Iterator iter =json.keySet().iterator();
    	String name ="";
    	Object value ="";
    	while(iter.hasNext())
    	{
    		name =iter.next().toString();
    		value =json.get(name);
    		ovo.set(name, value);
    	}
    }
    
    @SuppressWarnings("rawtypes")
	public static  Row jsonToRow(Object obj)
    {
    	Row row =new Row();
    	if(obj == null){
    		return row;
    	}
    	JSONObject json =JSONObject.fromObject(obj);
    	String name ="";
    	Object value =null;
    	Iterator iter =json.keySet().iterator();
    	while(iter.hasNext())
    	{
    		name =iter.next().toString();
    		value =json.get(name);
    		row.put(name, value);
    	}
    	return row;
    }
    
    @SuppressWarnings("unchecked")
	public static  DataSet jsonToDataSet(Object obj)
    {
    	Row row =new Row();
    	JSONObject json =null;
    	JSONArray arr =JSONArray.fromObject(obj);
    	DataSet ds =new DataSet();
		if(obj == null){
			return ds;
		}
    	Object []objs =arr.toArray();
    	for(int i=0; i< objs.length ; i++)
    	{
    		obj =objs[i];
    		json =JSONObject.fromObject(obj);
    		row =jsonToRow(json);
    		ds.add(row);
    	}
    	return ds;
    }
    
    @SuppressWarnings("rawtypes")
	public static void setRowValueForIVO(IVO ovo ,Object obj) throws JException
    {
		if(obj == null){
			return ;
		}
    	JSONArray arr =JSONArray.fromObject(obj);
    	Object objs[]=arr.toArray();
    	JSONObject json =null;
    	Iterator iter =null;
    	String name ="";
    	Object value ="";
    	Row row=null;
    	String rowName ="";
    	for (int i=0; i < objs.length ; i++)
    	{
    		obj =objs[i];
    		json =JSONObject.fromObject(obj);
    		iter =json.keySet().iterator();
    		row =new Row();
        	while(iter.hasNext())
        	{
        		name =iter.next().toString();
        		value =json.get(name);
        		if(name.equalsIgnoreCase("NAME"))
        		{
        			rowName =value.toString();
        		}
        		else if (name.equalsIgnoreCase("VALUE"))
        		{
        			row =jsonToRow(value);
        		}
        		ovo.set(rowName, row);
        	}
    	}

    }
    
    @SuppressWarnings("rawtypes")
	public static void setRowValueForOVO(OVO ovo ,Object obj) throws JException
    {
		if(obj == null){
			return ;
		}
    	JSONArray arr =JSONArray.fromObject(obj);
    	Object objs[]=arr.toArray();
    	JSONObject json =null;
    	Iterator iter =null;
    	String name ="";
    	Object value ="";
    	Row row=null;
    	String rowName ="";
    	for (int i=0; i < objs.length ; i++)
    	{
    		obj =objs[i];
    		json =JSONObject.fromObject(obj);
    		iter =json.keySet().iterator();
    		row =new Row();
        	while(iter.hasNext())
        	{
        		name =iter.next().toString();
        		value =json.get(name);
        		if(name.equalsIgnoreCase("NAME"))
        		{
        			rowName =value.toString();
        		}
        		else if (name.equalsIgnoreCase("VALUE"))
        		{
        			row =jsonToRow(value);
        		}
        		ovo.set(rowName, row);
        	}
    	}
    }
    
    @SuppressWarnings("rawtypes")
	public static void setDataSetValueForIVO(IVO ovo ,Object obj) throws JException
    {
		if(obj == null){
			return ;
		}
    	String sDataSetName ="";
    	DataSet ds =new DataSet();
    	JSONObject json =null;
    	JSONArray arr =JSONArray.fromObject(obj);
    	Object []objs =arr.toArray();
    	String name ="";
    	Object value =null;
    	Iterator iter =null;
    	for(int i=0; i < objs.length; i++)
    	{
    		obj =objs[i];
    		json =JSONObject.fromObject(obj);
    		iter =json.keySet().iterator();
    		while(iter.hasNext())
    		{
    			name =iter.next().toString();
    			value =json.get(name);
    			if(name.equalsIgnoreCase("NAME"))
    			{
    				sDataSetName =value.toString();
    			}
    			else if(name.equalsIgnoreCase("VALUE"))
    			{
    				ds =jsonToDataSet(value);
    			}
    		}
    		ovo.set(sDataSetName, ds);
    	}
    }
    
    @SuppressWarnings("rawtypes")
	public static void setDataSetValueForOVO(OVO ovo ,Object obj) throws JException
    {
		if(obj == null){
			return ;
		}
    	String sDataSetName ="";
    	DataSet ds =new DataSet();
    	JSONObject json =null;
    	JSONArray arr =JSONArray.fromObject(obj);
    	Object []objs =arr.toArray();
    	String name ="";
    	Object value =null;
    	Iterator iter =null;
    	for(int i=0; i < objs.length; i++)
    	{
    		obj =objs[i];
    		json =JSONObject.fromObject(obj);
    		iter =json.keySet().iterator();
    		while(iter.hasNext())
    		{
    			name =iter.next().toString();
    			value =json.get(name);
    			if(name.equalsIgnoreCase("NAME"))
    			{
    				sDataSetName =value.toString();
    			}
    			else if(name.equalsIgnoreCase("VALUE"))
    			{
    				ds =jsonToDataSet(value);
    			}
    		}
    		ovo.set(sDataSetName, ds);
    	}
    }

    @SuppressWarnings("rawtypes")
	public static void listJsonObject(String json){
    	JSONObject obj =JSONObject.fromObject(json);
    	JSONObject objTemp =null;
   	 	Iterator iter = obj.keySet().iterator();  
   	 	Object jsonObj =null;
        while(iter.hasNext()) {
            String key = iter.next().toString();  
            jsonObj =obj.get(key);
            //取得服务
            if(key.equalsIgnoreCase("SERVICE")){
            	objTemp =JSONObject.fromObject(jsonObj);
            	System.out.println( objTemp );
            }
        }  
    	
    }
//    public static void main(String []s) throws JException 
//	{
////		String xmls ="<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"no\"?>";
////			xmls +="<Juts COMPANY=\"DEV\" COPYRIGHT=\"2010~2015\" VERSION=\"4.0\">" ;
////			xmls +="<SERVICE NAME=\"1\" TYPE=\"0\"/>";
////			xmls +="<REQUEST><PARAMETERS NAME=\"DEFAULT\"><USER_ID>3</USER_ID><ACTION_NAME>createOrder</ACTION_NAME><GOODS_ITEMS.SUM>3</GOODS_ITEMS.SUM></PARAMETERS><PARAMETERS NAME=\"GOODS_ITEMS\" ROWS=\"3\"><ROW SEQ=\"1\"><GOODS_PRICE>2</GOODS_PRICE><GOODS_NUM>3</GOODS_NUM><GOOD_ID>1</GOOD_ID><GOODS_NAME>goods1</GOODS_NAME></ROW><ROW SEQ=\"2\"><GOODS_PRICE>2</GOODS_PRICE><GOODS_NUM>5</GOODS_NUM><GOOD_ID>2</GOOD_ID><GOODS_NAME>goods2</GOODS_NAME></ROW><ROW SEQ=\"3\"><GOODS_PRICE>1</GOODS_PRICE><GOODS_NUM>10</GOODS_NUM><GOOD_ID>3</GOOD_ID><GOODS_NAME>goods3</GOODS_NAME></ROW></PARAMETERS></REQUEST></Juts>";
////			
//    	/**/
//			IVO ivo =new IVO();
//			ivo.set( "key", "name" );
//			ivo.set( "age", "27" );
//			ivo.set( "sex", "1" );
//			Row row =new Row();
//			row.put( "name", "jj" );
//			row.put( "age", "27" );
//			ivo.set( "row", row );
//			DataSet ds =new DataSet();
//			Row item =new Row();
//			item.put( "name1", "jj" );
//			item.put( "age1", "" );
//			ds.add( item );
//			ivo.set( "ds", ds );
//			System.out.println(VOConvert.ivoToJson( ivo ));
//			System.out.println(VOConvert.ivoToXml( ivo ));
//
//	}
    @SuppressWarnings("unchecked")
	public static void main(String []s) throws JException 
    {
    	OVO ovo =new OVO(0,"操作成功","");
		ovo.set("id", "111");
		ovo.set("remark", null);
		ovo.set("remark2", "");
		String json =VOConvert.ovoToJson( ovo );
		System.out.println( "==========================>>"+json );
    }

}