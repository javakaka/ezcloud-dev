package com.ezcloud.framework.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
	   * ��ȡ�����ļ�ĳ���ڵ�����ݣ����ظýڵ��µ���ݵ�Properties
	   * @param oDocument  <p>xml�ĵ�</p>
	   * @param nodesString <p>ds.user ��ʾ��ȡds�ڵ��µ�user�ڵ�����</p> 
	   * @param paramInt	<p>��ȡ�ڵ�ķ�ʽ��0��ʾͨ��NodeList��ȡ<br/> ����ͨ��NamedNodeMap��ȡ</p>
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
	       throw new JException(-1, "ȡ�����ļ�����ʱ�����쳣", e);
	     }
	     return oProperties;
	   }
	   
	   /**
	    * <p>��ָ���ĵ���ָ��Ԫ�ش�������</p>
	    * @param oDocument ָ�����ĵ�
	    * @param oElement  ָ����Ԫ��
	    * @param sAttrName ������
	    * @param sAttrValue����ֵ
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
	       throw new JException(-1, "��������ʱ�����쳣��");
	     }
	     return oAttr;
	   }
	 
	   /**
	    * <p>���ĵ���ָ���ڵ㴴��Ԫ�أ������ر�������Ԫ��</p>
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
	       throw new JException(-1, "����Ԫ��ʱ�����쳣��");
	     }
	 
	     oNode.appendChild(oElement);
	 
	     Text oText = null;
	     if (sElementValue != null)
	     {
	    	 oText = oDocument.createTextNode(sElementValue);
	    	 oElement.appendChild(oText);
	     }
	     else  if (sElementValue == null || sElementValue.trim().length() == 0)
	     {
	    	 sElementValue ="";
	    	 oText = oDocument.createTextNode(sElementValue);
	    	 oElement.appendChild(oText);
	     }
	     return oElement;
	   }
	 
	   /**
	    * ���ļ�ת��ΪXML�ĵ�
	    * @param filePath �ļ�·��
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
	       throw new JException(-1, "�ļ���" + filePath + "��ת����XML�ĵ�ʱ���ִ���", e);
	     }
	     return oDocument;
	   }
	 
	   /**
	    * ������������ָ��Ԫ�ص�����
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
	    * ��ݽڵ������ָ���ڵ���ӽڵ�
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
	    * ��������
	    * @param oDocument
	    * @param paramString  ����:db.user
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
	 
	   @SuppressWarnings("unchecked")
	public static void getDocList(Object paramObject, String[] paramArrayOfString, int paramInt, DataSet paramDataSet)
	     throws JException
	   {
	     if ((paramObject.getClass().getName().indexOf("Document") == -1) && 
	       (paramObject.getClass().getName().indexOf("Element") == -1))
	       throw new JException(-1, "��Document/Element�������ͣ����ܽ��в�����");
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
	       for (int i = 0; localNodeList != null && i < localNodeList.getLength(); i++)
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
	       throw new JException(-1, "����XML�ĵ�ʱ�����쳣��");
	     }
	     return oDocument;
	   }
	 
	   public static String toString(Document oDocument)
	   {
	     return toString(oDocument, "gb2312");
	   }
	 
	   /**
	    * ��XML�ĵ�ת��Ϊָ���ַ���ַ�
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
	    * ���ַ�ת��ΪXML����
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
	       throw new JException(-1, "����XML�����ʱ�����쳣��");
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
	       throw new JException(-1, "SAX����XML��ʱ���?", oSAXException);
	     }
	     catch (IOException oIOException)
	     {
	       throw new JException(-1, "����XML��ʱ����IO�쳣��", oIOException);
	     }
	     catch (IllegalArgumentException oIllegalArgumentException)
	     {
	       throw new JException(-1, "����XML��ʱ���ֲ��������쳣��", oIllegalArgumentException);
	     }
	     return oDocument;
	   }
}
