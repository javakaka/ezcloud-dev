package com.ezcloud.framework.vo;

import java.util.Comparator;
/**
 * DataSet比较器
 * @author Tong
 */
@SuppressWarnings("rawtypes")
public class DataSetComparator implements Comparator {
	private String _$1 = null;
	private int _$2 = 1;
	/**
	 * DataSet比较器，iType表示类型，1表示比较整数值、其他值表示比较字符串值大小
	 * @param sKeyName Row 对象中的字段名字
	 * @param iType 类别 1 比较整数值 其他，比较字符串值
	 */
	public DataSetComparator(String sKeyName, int iType) {
		this._$1 = sKeyName;
		this._$2 = iType;
	}
	
	//比较Row 
	public int compare(Object obj1, Object obj2) {
		Row oRow1 = (Row) obj1;
		Row oRow2 = (Row) obj2;
		//比较整数值
		if (this._$2 == 1) {
			int i = new Integer(oRow1.getString(this._$1, "0")).intValue();
			int j = new Integer(oRow2.getString(this._$1, "0")).intValue();
			return i - j;
		}
		String sValue1 = oRow1.getString(this._$1, "");
		String sValue2 = oRow2.getString(this._$1, "");
		return sValue1.compareTo(sValue2);
	}
}