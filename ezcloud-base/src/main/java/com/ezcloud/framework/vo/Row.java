package com.ezcloud.framework.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import com.ezcloud.framework.data.DBConf;
import com.ezcloud.framework.exp.JException;
import com.ezcloud.framework.util.DateUtil;
/**
 * 单行数据交互类
 * @author Tong
 *
 */
@SuppressWarnings("rawtypes")
public class Row extends HashMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8351720958872440900L;

	/**
	 * 检验是否包含指定的key
	 * @param sKeyName
	 * @return
	 */
	public boolean containsKey(String sKeyName) {
		return super.containsKey(sKeyName.toUpperCase());
	}
	
	/**
	 * 通过键取得值
	 * @param sKeyName
	 * @return
	 */
	public Object get(String sKeyName) {
		return super.get(sKeyName.toUpperCase());
	}
	
	/**
	 * 取指定键的值，没有的时候返回默认值
	 * @param sKeyName
	 * @param sDefaultValue
	 * @return
	 */
	public Object get(String sKeyName, String sDefaultValue) {
		if (containsKey(sKeyName.toUpperCase())) {
			return get(sKeyName.toUpperCase()).toString();
		}
		return sDefaultValue;
	}
	
	/**
	 * 取键值，返回字符串类型数据
	 * @param sKeyName
	 * @return
	 */
	public String getString(String sKeyName) {
		if (containsKey(sKeyName.toUpperCase())) {
			return get(sKeyName.toUpperCase()).toString();
		}
		return null;
	}
	
	/**
	 * 取键值，返回默认值字符串
	 * @param sKeyName
	 * @param sDefaultValue
	 * @return
	 */
	public String getString(String sKeyName, String sDefaultValue) {
		if ((containsKey(sKeyName.toUpperCase())) && (get(sKeyName.toUpperCase()) != null)) {
			return get(sKeyName.toUpperCase()).toString();
		}
		return sDefaultValue;
	}
	
	/**
	 * 从ResultSet取指定列的值
	 * @param oResultSet
	 * @param paramInt
	 * @return
	 * @throws SQLException
	 */
	public static String getString(ResultSet oResultSet, int paramInt) throws SQLException {
		String str = null;
		if (DBConf._DATABASE_TYPE.equalsIgnoreCase(Final._TYPE_MYSQL)) {
			str = oResultSet.getString(paramInt);
		} else if (DBConf._DATABASE_TYPE.equalsIgnoreCase(Final._TYPE_ORACLE)) {
			str = oResultSet.getString(paramInt);
		} else
			str = oResultSet.getString(paramInt);
		return str;
	}
	
	/**
	 * 从ResultSet取指定字段名的值
	 * @param oResultSet
	 * @param sFieldName
	 * @return
	 * @throws SQLException
	 */
	public static String getString(ResultSet oResultSet, String sFieldName) throws SQLException {
		String str = null;
		if (oResultSet == null) {
			return null;
		}
		if (DBConf._DATABASE_TYPE.equalsIgnoreCase(Final._TYPE_MYSQL)) {
			str = oResultSet.getString(sFieldName);
		} else if (DBConf._DATABASE_TYPE.equalsIgnoreCase(Final._TYPE_ORACLE)) {
			str = oResultSet.getString(sFieldName);
		} else {
			str = oResultSet.getString(sFieldName);
		}
		return str;
	}
	
	/**
	 * 从BaseVO中取指定的键值存入当前对象，如果BaseVO没有对应的键，则存入默认值
	 * @param oBaseVO
	 * @param sKeyName
	 * @param sDefaultValue
	 * @throws JException
	 */
	public void put(BaseVO oBaseVO, String sKeyName, String sDefaultValue) throws JException {
		put(sKeyName, oBaseVO.getString(sKeyName, sDefaultValue));
	}
	
	public void put(BaseVO oBaseVO, String sKeyName, boolean iNotNull, String sDefaultName) throws JException {
		put(sKeyName, oBaseVO.getString(sKeyName, iNotNull, sDefaultName));
	}

	/**
	 * 保存整数
	 * @param sKeyName
	 * @param iDefaultValue
	 */
	@SuppressWarnings("unchecked")
	public void put(String sKeyName, int iDefaultValue) {
		super.put(sKeyName.toUpperCase(), String.valueOf(iDefaultValue));
	}
	
	/**
	 * 保存长整数
	 * @param sKeyName
	 * @param lValue
	 */
	@SuppressWarnings("unchecked")
	public void put(String sKeyName, long lValue) {
		super.put(sKeyName.toUpperCase(), String.valueOf(lValue));
	}
	
	/**
	 * 保存长整数
	 * @param sKeyName
	 * @param lValue
	 */
	@SuppressWarnings("unchecked")
	public void put(String sKeyName, Long lValue) {
		super.put(sKeyName.toUpperCase(),String.valueOf( lValue.intValue() ));
	}
	
	/**
	 * 根据指定键名，若BaseVO存在该键值，则从BaseVO取出该键值，然后保存到当前对象
	 * @param sKeyName
	 * @param oBaseVO
	 * @throws JException
	 */
	public void put(String sKeyName, BaseVO oBaseVO) throws JException {
		if (oBaseVO == null)
			return;
		put(sKeyName, oBaseVO.getString(sKeyName, false));
	}

	public void put(String sKeyName, BaseVO oBaseVO, String sDefaultValue) throws JException {
		put(sKeyName, oBaseVO.getString(sKeyName, sDefaultValue));
	}

	public void put(String sKeyName, BaseVO oBaseVO, boolean paramBoolean, String sDefaultValue) 
			throws JException {
		put(sKeyName, oBaseVO.getString(sKeyName, paramBoolean, sDefaultValue));
	}

	/**
	 * 保存字符串
	 * @param sKeyName
	 * @param sValue
	 */
	@SuppressWarnings("unchecked")
	public void put(String sKeyName, String sValue) {
		super.put(sKeyName.toUpperCase(), sValue);
	}
	
	/**
	 * @param sKeyName
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void put(String sKeyName, Object obj) {
		super.put(sKeyName.toUpperCase(), obj);
	}
	
	/**
	 * 保存日期
	 * @param sKeyName
	 * @param sDate
	 */
	@SuppressWarnings("unchecked")
	public void put(String sKeyName, Date sDate) {
		if (sDate == null)
			return;
		super.put(sKeyName.toUpperCase(), DateUtil.toString(sDate,DateUtil._DATETIME));
	}
	
	/**保存指定格式的日期
	 * @param paramString1
	 * @param paramDate
	 * @param paramString2
	 */
	@SuppressWarnings("unchecked")
	public void put(String sKeyName, Date sDate, String sDateFormat) {
		super.put(sKeyName.toUpperCase(), DateUtil.toString(sDate, sDateFormat));
	}
	
	/**
	 * 返回Row的字符串值
	 */
	public String toString() {
		String rowStrValue = "";
		Object[] keyArray = keySet().toArray();
		Object obj=null;
		String keyName="";
		for (int i = 0; i < keyArray.length; i++) {
			keyName =(String)keyArray[i];
			obj =get(keyName);
			if(obj != null && obj.getClass().getName().equalsIgnoreCase("com.ezcloud.framework.vo.DataSet")){
				rowStrValue = rowStrValue + keyArray[i].toString() + "="	+((DataSet)obj).toString() + "\n";
			}
			else
				rowStrValue = rowStrValue + keyArray[i].toString() + "="	+obj + "\n";
		}
		return rowStrValue;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]){
		DataSet ds=new DataSet();
		for(int i=0;i<10;i++)
		{
			Row row=new Row();
			for(int j=0;j<10;j++){
				row.put("key"+j, j);
			}
			ds.add(row);
		}
		Row ovo =new Row();
		ovo.put("top_nav", ds);
		System.out.print(ovo);
	}
}