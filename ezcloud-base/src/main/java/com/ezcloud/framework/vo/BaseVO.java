package com.ezcloud.framework.vo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ezcloud.framework.exp.JException;
import com.ezcloud.framework.util.StringUtil;

/**
 * 数据传输基类
 * @author Tong
 */
public class BaseVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1563223609835192623L;
	public Row oForm;
	/**
	 * 服务名
	 */
	public String sService;

	public BaseVO() {
		oForm = new Row();
		this.sService = null;
	}

	public BaseVO(String sServiceName) {
		oForm = new Row();
		this.sService = sServiceName;
	}

	@SuppressWarnings("unchecked")
	public void copy(BaseVO oBaseVO) {
		this.sService = oBaseVO.sService;
		if (oBaseVO.oForm == null) {
			oForm = null;
		} 
		else {
			if (oForm == null){
				oForm = new Row();
			}
			Object[] keyArray = oBaseVO.oForm.keySet().toArray();
			for (int i = 0; i < keyArray.length; i++) {
				oForm.put(keyArray[i], oBaseVO.oForm.get(keyArray[i]));
			}
		}
	}

	/**
	 * 用参数oBaseVO的指定键值替换当前对象的对应的key的值
	 * @param oBaseVO
	 * @param sKeyName
	 */
	public void copy(BaseVO oBaseVO, String sKeyName) {
		if (oBaseVO.oForm.containsKey(sKeyName.toUpperCase())){
			oForm.put(sKeyName, oBaseVO.oForm.get(sKeyName.toUpperCase()));
		}
	}

	public boolean exist(String sKeyName) {
		if ((oForm != null)  && ( oForm.containsKey(sKeyName.toUpperCase()))){
			return true;
		}
		return false;
	}

	public Object get(String sKeyName) throws JException {
		return get(sKeyName, false);
	}

	/**
	 * 取指定键的值，如果没有的话，则取指定的默认值
	 * @param sKeyName
	 * @param sDefaultValue
	 * @return
	 * @throws JException
	 */
	public Object get(String sKeyName, String sDefaultValue) throws JException {
		if (! oForm.containsKey( sKeyName.toUpperCase() ) ) {
			return sDefaultValue;
		}
		if ( oForm.get(sKeyName.toUpperCase() ).getClass().getName().equalsIgnoreCase("java.lang.String") ) {
			String value = null;
			value = (String) get(sKeyName);
			if ( (value != null) && (value.length() == 0) ) {
				value = sDefaultValue;
			}
			return value;
		}
		return oForm.get(sKeyName.toUpperCase());
	}

	/**
	 * 1.取指定键值，若第二个参数是true,当对象不包含指定的键时，
	 * 抛出异常，返回空字符串；
	 * 2.当指定的键对应的值是字符串
	 * 3.直接返回键值。
	 * @param sKeyName
	 * @param bIsNotNull
	 * @return
	 * @throws JException
	 */
	public Object get(String sKeyName, boolean bIsNotNull) throws JException {
		if ( !oForm.containsKey( sKeyName.toUpperCase() ) ) {
			if (bIsNotNull) {
				throw new JException(-1, "数据集中没有指定(" + sKeyName + ")的值");
			}
			return "";
		}
		if ( oForm.get( sKeyName.toUpperCase() ).getClass().getName().equalsIgnoreCase("java.lang.String") ) {
			return oForm.get(sKeyName.toUpperCase()).toString();
		}
		return oForm.get(sKeyName.toUpperCase());
	}
	
	/**
	 * 根据键取键值，
	 * 1.如果不包含该键，并且不能为空，则抛出异常提示信息；
	 * 2.当指定的键对应的值是字符串
	 * 3.直接返回键值。
	 * @param sKeyName
	 * @param bIsNotNull
	 * @param sTipStr
	 * @return
	 * @throws JException
	 */
	public Object get(String sKeyName, boolean bIsNotNull, String sTipStr) throws JException {
		if ( !oForm.containsKey( sKeyName.toUpperCase() ) ) {
			if (bIsNotNull) {
				throw new JException(-800023, "数据集中没有指定(" + sKeyName + "[" + sTipStr + "])的值");
			}
			return "";
		}
		if ( oForm.get(sKeyName.toUpperCase() ).getClass().getName().equalsIgnoreCase("java.lang.String") ) {
			return oForm.get(sKeyName.toUpperCase()).toString();
		}
		return oForm.get(sKeyName.toUpperCase());
	}

	/**
	 * 
	 * @param sKeyName
	 * @param bIsNotNull
	 * @param bIsoToGbk
	 * @return
	 * @throws JException
	 */
	public Object get(String sKeyName, boolean bIsNotNull, boolean bIsoToGbk) throws JException {
		if ( !oForm.containsKey(sKeyName.toUpperCase()) ) {
			if (bIsNotNull) {
				throw new JException(-1, "数据集中没有指定(" + sKeyName + ")的值");
			}
			return null;
		}
		if ((bIsoToGbk)&& (oForm.get(sKeyName.toUpperCase()).getClass().getName().equalsIgnoreCase("java.lang.String"))) {
			return StringUtil.isoToGBK(oForm.get(sKeyName.toUpperCase()).toString(), true);
		}
		return oForm.get(sKeyName.toUpperCase());
	}

	public int getInt(String sKeyName) throws JException {
		return getInt(sKeyName, false);
	}

	public int getInt(String sKeyName, String paramString2) throws JException {
		return Integer.parseInt((String) get(sKeyName, paramString2));
	}

	public int getInt(String sKeyName, boolean paramBoolean) throws JException {
		return Integer.parseInt((String) get(sKeyName, paramBoolean));
	}

	public int getInt(String sKeyName, boolean paramBoolean,String paramString2) throws JException {
		return Integer.parseInt((String) get(sKeyName, paramBoolean,paramString2));
	}

	public String getString(String sKeyName) throws JException {
		return getString(sKeyName, false);
	}

	public String getString(String sKeyName, String sTipStr) throws JException {
		return (String) get(sKeyName, sTipStr);
	}

	public String getString(String sKeyName, boolean paramBoolean) throws JException {
		return (String) get(sKeyName, paramBoolean);
	}

	public String getString(String sKeyName, boolean paramBoolean,
			String paramString2) throws JException {
		return (String) get(sKeyName, paramBoolean, paramString2);
	}

	public void removeByName(String sKeyName) {
		if (oForm != null)
			oForm.remove(sKeyName.toUpperCase());
	}

	public void set(String sKeyName, double paramDouble) throws JException {
		String str = String.valueOf(paramDouble);
		set(sKeyName, str, false);
	}

	public void set(String sKeyName, int paramInt) throws JException {
		String str = String.valueOf(paramInt);
		set(sKeyName, str, false);
	}

	public void set(String sKeyName, long paramLong) throws JException {
		String str = String.valueOf(paramLong);
		set(sKeyName, str, false);
	}

	public void set(String sKeyName, Object paramObject) throws JException {
		set(sKeyName, paramObject, false);
	}

	@SuppressWarnings("unchecked")
	public void set(String sKeyName, Object valueObject, boolean paramBoolean) throws JException {
		if ( (oForm.containsKey(sKeyName.toUpperCase()) ) && (paramBoolean) )
			throw new JException(-800024, "数据集中已经存在指定(" + sKeyName + ")的值");
		if (valueObject == null)
			valueObject ="";
		int currentPage;
		//把RESULTSET 转成DataSet
		if ( valueObject.getClass().getName().toUpperCase().indexOf("RESULTSET") != -1 ) {
			try {
				if ( ((ResultSet) valueObject).isBeforeFirst() ) {
					DataSet objDataSet = new DataSet(20);
					long l = 0L;
					if ( (oForm.containsKey("CURRENTPAGE")) && (oForm.containsKey("PAGESIZE")) ) {
						currentPage = Integer.parseInt((String) oForm.get("CURRENTPAGE"));
						int pageSize = Integer.parseInt((String) oForm.get("PAGESIZE"));
						l = Integer.parseInt(oForm.getString(sKeyName.toUpperCase()+ ".SUM", "-1"));
						//oform中没有sKeyName对应的数据
						if (l == -1L) {
							l = DataSet.convertRsToDataSet((ResultSet) valueObject, objDataSet, currentPage,pageSize, true);
						}
						//已经有
						else {
							DataSet.convertRsToDataSet((ResultSet) valueObject,objDataSet, currentPage, pageSize, false);
						}
					} else {
						l = DataSet.convertRsToDataSet((ResultSet) valueObject,objDataSet);
					}
					valueObject = objDataSet;
					oForm.put("SUM", String.valueOf(l));
					oForm.put(sKeyName + ".SUM", String.valueOf(l));
				}
				oForm.remove("SUM");
				return;
			} catch (SQLException exp) {
				throw new JException(-800041, "把数据集转换成动态数组的时候出现SQL异常",exp);
			} finally {
				if (valueObject != null){
					oForm.put(sKeyName.toUpperCase(), valueObject);
				}
			}
		} 
		else if (valueObject.getClass().getName().indexOf("DataSet") != -1) {
			int i;
			if ( !oForm.containsKey(sKeyName.toUpperCase() + ".SUM") ) {
				i = ((DataSet) valueObject).size();
				oForm.put(sKeyName.toUpperCase() + ".SUM", String.valueOf(i));
			}
			//分页数据
			if ( ( oForm.containsKey("CURRENTPAGE") ) && ( oForm.containsKey("PAGESIZE") ) ) {
				i = Integer.parseInt(oForm.getString("CURRENTPAGE"));
				int j = Integer.parseInt(oForm.getString("PAGESIZE"));
				int k = (i - 1) * j + 1;
				currentPage = i * j;
				DataSet objDataSet = new DataSet(20);
				for ( int i1 = k; i1 <= currentPage; i1++ ) {
					if (((DataSet) valueObject).size() < i1)
						break;
					objDataSet.add( ((DataSet) valueObject).get(i1 - 1) );
				}
				if (objDataSet.size() > 0) {
					valueObject = objDataSet;
				}
			}
		}
		 if (valueObject != null)
		 {
			 oForm.put(sKeyName.toUpperCase(), valueObject);
		 }
	}
}
