package com.ezcloud.framework.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ezcloud.framework.exp.JException;
import com.ezcloud.framework.vo.DataSet;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XmlUtil {

	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// true 对所有xml节点的转换都增加CDATA标记,false不加
				boolean cdata = false;

				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	/**
	 * @return
	 */
	public static String objectToXml(Object obj) {  
		xstream.alias("xml", obj.getClass());  
		return xstream.toXML(obj);  
	}   
	
	
	 /**
	   * 读取配置文件某个节点的内容，返回该节点下的数据的Properties
	   * @param oDocument  <p>xml文档</p>
	   * @param nodesString <p>ds.user 表示读取ds节点下的user节点的数据</p> 
	   * @param paramInt	<p>读取节点的方式，0表示通过NodeList读取<br/> 否则，通过NamedNodeMap读取</p>
	   * @return
	   * @throws JException
	   */
	   private static Properties _$1(Document oDocument, String nodesString, int paramInt)
	     throws JException
	   {
	     Properties oProperties = null;
	     NodeList oNodeList = null;
	     try
	     {
	       Element oElement = oDocument.getDocumentElement();
	       String[] arrayOfString = StringUtil.toArray(nodesString, ".");
	       for (int i = 0; i < arrayOfString.length; i++)
	       {
	    	   oNodeList = oElement.getElementsByTagName(arrayOfString[i]);
	         if (oNodeList.getLength() == 0)
	           return null;
	         oElement = (Element)oNodeList.item(0);
	       }
	       for (int j = 0; j < oNodeList.getLength(); )
	       {
	         oElement = (Element)oNodeList.item(j);
	         Node oNode;
	         if (paramInt == 0)
	         {
	           for (int k = 0; k < oElement.getChildNodes().getLength(); k++)
	           {
	             oNode = oElement.getChildNodes().item(k);
	             if (oProperties == null)
	             {
	            	 oProperties = new Properties();
	             }
	             if (oNode.getNodeType() ==Node.ELEMENT_NODE)
	             {
	               if (oNode.getFirstChild() != null)
	               {
	            	   oProperties.put(oNode.getNodeName(), 
	                   oNode.getFirstChild().getNodeValue());
	               }
	             }
	           }
	           break;
	         }
	 
	         for (int k = 0; k < oElement.getAttributes().getLength(); k++)
	         {
	           if (oProperties == null)
	           {
	        	   oProperties = new Properties();
	           }
	           oNode = oElement.getAttributes().item(k);
	           oProperties.put(oNode.getNodeName(), 
	             oElement.getAttribute(oNode.getNodeName()));
	         }
	         break;
	       }
	     }
	     catch (Exception e)
	     {
	       throw new JException(-1, "取配置文件属性时出现异常", e);
	     }
	     return oProperties;
	   }
	   
	   /**
	    * <p>给指定文档的指定元素创建属性</p>
	    * @param oDocument 指定的文档
	    * @param oElement  指定的元素
	    * @param sAttrName 属性名
	    * @param sAttrValue属性值
	    * @return org.w3c.dom.Attr
	    * @throws JException
	    */
	   public static Attr createAttribute(Document oDocument, Element oElement, String sAttrName, String sAttrValue)
	     throws JException
	   {
	     Attr oAttr = null;
	     try
	     {
	    	 oAttr = oDocument.createAttribute(sAttrName);
	       if (sAttrValue != null)
	       {
	    	   oAttr.setValue(sAttrValue);
	       }
	       else
	       {
	    	   oAttr.setValue("null_null");
	       }
	       oElement.setAttributeNode(oAttr);
	     }
	     catch (DOMException e)
	     {
	       throw new JException(-1, "创建属性时出现异常。");
	     }
	     return oAttr;
	   }
	 
	   /**
	    * <p>给文档的指定节点创建元素，并返回被创建的元素</p>
	    * @param oDocument
	    * @param oNode
	    * @param sElementName
	    * @param sElementValue
	    * @return
	    * @throws JException
	    */
	   public static Node createElement(Document oDocument, Node oNode, String sElementName, String sElementValue)
	     throws JException
	   {
	     Element oElement = null;
	     try
	     {
	    	 oElement = oDocument.createElement(sElementName);
	     }
	     catch (DOMException e)
	     {
	       throw new JException(-1, "创建元素时出现异常。");
	     }
	 
	     oNode.appendChild(oElement);
	 
	     Text oText = null;
	     if (sElementValue != null)
	     {
	    	 oText = oDocument.createTextNode(sElementValue);
	    	 oElement.appendChild(oText);
	     }
	     return oElement;
	   }
	 
	   /**
	    * 将文件转换为XML文档
	    * @param filePath 文件路径
	    * @return org.w3c.dom.Document
	    * @throws JException
	    */
	   public static Document fileToXml(String filePath)
	     throws JException
	   {
	     Document oDocument = null;
	     try
	     {
	       DocumentBuilderFactory oDocumentBuilderFactory;
	       oDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
	       DocumentBuilder oDocumentBuilder;
	       oDocumentBuilder = oDocumentBuilderFactory.newDocumentBuilder();
	       oDocument = oDocumentBuilder.parse(new File(filePath));
	     }
	     catch (Exception e)
	     {
	       throw new JException(-1, "文件（" + filePath + "）转化成XML文档时出现错误！", e);
	     }
	     return oDocument;
	   }
	 
	   /**
	    * 根据属性名查找指定元素的属性
	    * @param oElement
	    * @param attrName
	    * @return
	    * @throws JException
	    */
	   public static Attr findAttrByName(Element oElement, String attrName)
	     throws JException
	   {
	     Attr oAttr= oElement.getAttributeNode(attrName);
	     return oAttr ;
	   }
	 
	   /**
	    * 根据节点名查找指定节点的子节点
	    * @param oNode
	    * @param sNodeName
	    * @return
	    * @throws JException
	    */
	   public static Node findChildByName(Node oNode, String sNodeName)
	     throws JException
	   {
	     NodeList oNodeList;
	     oNodeList = oNode.getChildNodes();
	     int i = oNodeList.getLength();
	     for (int j = 0; j < i; j++)
	     {
	       if (oNodeList.item(j).getNodeName().equals(sNodeName))
	       {
	         return oNodeList.item(j);
	       }
	     }
	     return null;
	   }
	 
	   /**
	    * 查找属性
	    * @param oDocument
	    * @param paramString  例如:db.user
	    * @return
	    */
	   public static String getAttribute(Document oDocument, String paramString)
	   {
	     String[] arrayOfString = StringUtil.toArray(paramString, ".");
	     Element oElement = oDocument.getDocumentElement();
	     for (int i = 0; i < arrayOfString.length - 1; i++)
	     {
	       NodeList oNodeList = oElement.getElementsByTagName(arrayOfString[i]);
	       if (oNodeList.getLength() == 0)
	         return null;
	       oElement = (Element)oNodeList.item(0);
	     }
	     return oElement.getAttribute(arrayOfString[(arrayOfString.length - 1)]);
	   }
	 
	   //
	   public static Properties getAttributeProps(Document oDocument, String paramString)
	     throws JException
	   {
	     return _$1(oDocument, paramString, 0);
	   }
	 
	   public static void getDocList(Object paramObject, String[] paramArrayOfString, int paramInt, DataSet paramDataSet)
			     throws JException
			   {
			     if ((paramObject.getClass().getName().indexOf("Document") == -1) && 
			       (paramObject.getClass().getName().indexOf("Element") == -1))
			       throw new JException(-1, "非Document/Element对象类型，不能进行操作！");
			     if (paramInt == 1)
			     {
			       if (paramObject.getClass().getName().indexOf("Document") != -1)
			         paramDataSet.add(((Document)paramObject).getElementsByTagName(paramArrayOfString[(paramArrayOfString.length - paramInt)]));
			       else if (paramObject.getClass().getName().indexOf("Element") != -1) {
			         paramDataSet.add(((Element)paramObject).getElementsByTagName(paramArrayOfString[(paramArrayOfString.length - paramInt)]));
			       }
			     }
			     else
			     {
			       NodeList localNodeList = null;
			       if (paramObject.getClass().getName().indexOf("Document") != -1)
			         localNodeList = ((Document)paramObject).getElementsByTagName(paramArrayOfString[(paramArrayOfString.length - paramInt)]);
			       else if (paramObject.getClass().getName().indexOf("Element") != -1)
			         localNodeList = ((Element)paramObject).getElementsByTagName(paramArrayOfString[(paramArrayOfString.length - paramInt)]);
			       for (int i = 0; i < localNodeList.getLength(); i++)
			       {
			         getDocList((Element)localNodeList.item(i), paramArrayOfString, paramInt - 1, paramDataSet);
			       }
			     }
			   }
			 
			   public static String getProperty(Document oDocument, String paramString)
			   {
			     String[] arrayOfString = StringUtil.toArray(paramString, ".");
			     Element oElement = oDocument.getDocumentElement();
			     NodeList localObject;
			     for (int i = 0; i < arrayOfString.length; i++)
			     {
			    	 localObject = oElement.getElementsByTagName(arrayOfString[i]);
			       if (localObject .getLength() == 0)
			         return null;
			       oElement = (Element)((NodeList)localObject).item(0);
			     }
			 
			     if ((oElement.getFirstChild()) != null)
			     {
			       return (oElement.getFirstChild()).getNodeValue();
			     }
			     return null;
			   }
	 
	   public static Node getSingleNode(Document oDocument, String paramString)
	   {
	     String[] arrayOfString = StringUtil.toArray(paramString, ".");
	     Element oElement = oDocument.getDocumentElement();
	     Object obj;
	     for (int i = 0; i < arrayOfString.length; i++)
	     {
	    	 obj = oElement.getElementsByTagName(arrayOfString[i]);
	       if (((NodeList)obj).getLength() == 0)
	         return null;
	       oElement = (Element)((NodeList)obj).item(0);
	     }
	 
	     return oElement.getFirstChild();
	   }
	 
	   public static Properties getTagProps(Document paramDocument, String paramString)
	     throws JException
	   {
	     return _$1(paramDocument, paramString, 0);
	   }
	 
	   public static Document newDom(String paramString)
			     throws JException
			   {
			     DocumentBuilderFactory oDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			     DocumentBuilder oDocumentBuilder = null;
			     Document oDocument = null;
			     try
			     {
			       oDocumentBuilder  = oDocumentBuilderFactory.newDocumentBuilder();
			       DOMImplementation oDOMImplementation;
			       oDOMImplementation =oDocumentBuilder.getDOMImplementation();
			       oDocument = oDOMImplementation.createDocument(null, paramString, null);
			     }
			     catch (Exception localException)
			     {
			       throw new JException(-1, "创建XML文档时出现异常。");
			     }
			     return oDocument;
			   }
			 
			   public static String toString(Document oDocument)
			   {
			     return toString(oDocument, "gb2312");
			   }
			 
			   /**
			    * 将XML文档转换为指定字符集的字符串
			    * @param oDocument
			    * @param charset
			    * @return
			    */
			   public static String toString(Document oDocument, String charset)
			   {
			     DOMSource oDOMSource = new DOMSource(oDocument);
			     StringWriter oStringWriter = new StringWriter();
			     StreamResult oStreamResult = new StreamResult(oStringWriter);
			     TransformerFactory oTransformerFactory = TransformerFactory.newInstance();
			     Transformer oTransformer =null;
			     try {
			    	 oTransformer =oTransformerFactory.newTransformer();
			    	 oTransformer.setOutputProperty("method", "xml");
			    	 oTransformer.setOutputProperty("encoding", charset);
			    	 oTransformer.transform(oDOMSource, oStreamResult);
			     }
			     catch (Exception e)
			     {
			       e.printStackTrace();
			     }
			     oStringWriter.flush();
			     return oStringWriter.toString();
			   }
	 
			   /**
			    * 将字符串转换为XML对象
			    * @param paramString
			    * @return
			    * @throws JException
			    */
			   public static Document toXml(String xmlString)
			     throws JException
			   {
			     if (xmlString == null)
			       return null;
			     Document oDocument = null;
			     DocumentBuilderFactory oDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			     DocumentBuilder oDocumentBuilder = null;
			     try
			     {
			       oDocumentBuilder = oDocumentBuilderFactory.newDocumentBuilder();
			     }
			     catch (ParserConfigurationException localParserConfigurationException)
			     {
			       throw new JException(-1, "创建XML生成器时出现异常。");
			     }
			     StringReader oStringReader = new StringReader(xmlString);
			     InputSource oInputSource = new InputSource(oStringReader);
			     try
			     {
			       oDocument = oDocumentBuilder.parse(oInputSource);
			     }
			     catch (SAXException oSAXException)
			     {
			      // LogUtil.print(paramString);
			    	 oSAXException.printStackTrace();
			       throw new JException(-1, "SAX解析XML串时出错。", oSAXException);
			     }
			     catch (IOException oIOException)
			     {
			       throw new JException(-1, "解析XML串时出现IO异常。", oIOException);
			     }
			     catch (IllegalArgumentException oIllegalArgumentException)
			     {
			       throw new JException(-1, "解析XML串时出现参数设置异常。", oIllegalArgumentException);
			     }
			     return oDocument;
			   }
	   
	   public static HashMap<String, String> xmlToMap(String xml)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			SAXReader reader = new SAXReader();
			InputStream inputStream;
			try {
				inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
				org.dom4j.Document document;
				document = reader.read(inputStream);
				// 得到xml根元素
				org.dom4j.Element root = document.getRootElement();
				// 得到根元素的所有子节点
				List<org.dom4j.Element> elementList = root.elements();
				// 遍历所有子节点
				for (org.dom4j.Element e : elementList)
				{
					map.put(e.getName(), e.getText());
					System.out.println("String "+e.getName()+" ="+e.getText());
				}
				// 释放资源
				inputStream.close();
				inputStream = null;
			} catch (UnsupportedEncodingException e1) {
				map =null;
			}
			catch (IOException e1) {
				map =null;
			}
			catch (DocumentException e1) {
				map =null;
			}
			return map;
		}
}
