package com.ezcloud.framework.vo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import com.ezcloud.framework.exp.JException;

/**
 * 数据集 ：复合型数据交互对象
 * @author Tong
 */
@SuppressWarnings("rawtypes")
public class DataSet extends ArrayList {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4532074457907311004L;

	public DataSet() {
	}

	public DataSet(int initSize) {
		super(initSize);
	}

	public static long convertRsToDataSet(String sKeyName, ResultSet oResultSet, DataSet oDataSet, OVO oOVO)
			throws JException {
		long l = 0L;
		//分页 如果包含CURRENTPAGE和PAGESIZE键
		if ((oOVO.oForm.containsKey("CURRENTPAGE")) && (oOVO.oForm.containsKey("PAGESIZE"))) {
			int iCurrentPage = Integer.parseInt((String) oOVO.oForm.get("CURRENTPAGE"));
			int iPageSize = Integer.parseInt((String) oOVO.oForm.get("PAGESIZE"));
			l = Integer.parseInt(oOVO.oForm.getString(sKeyName.toUpperCase()+ ".SUM", "-1"));
			//如果oOVO不包含该键
			if (l == -1L) {
				l = convertRsToDataSet(oResultSet, oDataSet, iCurrentPage, iPageSize, true);
				oOVO.set(sKeyName + ".SUM", l);
			}
			//包含该键
			else {
				convertRsToDataSet(oResultSet, oDataSet, iCurrentPage, iPageSize, false);
			}
		}
		//不包含CURRENTPAGE和PAGESIZE键
		else {
			l = convertRsToDataSet(oResultSet, oDataSet, 0, 0, true);
			oOVO.set(sKeyName + ".SUM", l);
		}
		return l;
	}

	/**
	 * <p>
	 * 将oResultSet转化为DataSet,iCurrentPage表示数据集的起始记录下标，iPageSize表示页大小<br/>
	 * 当数据集游标超过当前分页的结束记录下标时，继续执行<br/>
	 * 返回所读取的数据集长度<br/>
	 * </p>
	 * @param oResultSet
	 * @param oDataSet
	 * @param iCurrentPage
	 * @param iPageSize
	 * @return
	 * @throws JException
	 */
	public static long convertRsToDataSet(ResultSet oResultSet,
			DataSet oDataSet) throws JException {
		return convertRsToDataSet(oResultSet, oDataSet, 0, 0, true);
	}

	/**
	 * <p>
	 * 将oResultSet转化为DataSet,iCurrentPage表示数据集的起始记录下标，iPageSize表示页大小<br/>
	 * 当数据集游标超过当前分页的结束记录下标时，跳出循环<br/>
	 * 返回所读取的数据集长度<br/>
	 * </p>
	 * @param oResultSet
	 * @param oDataSet
	 * @param iCurrentPage
	 * @param iPageSize
	 * @return
	 * @throws JException
	 */
	public static long convertRsToDataSet(ResultSet oResultSet,
			DataSet oDataSet, int iCurrentPage, int iPageSize)
			throws JException {
		return convertRsToDataSet(oResultSet, oDataSet, iCurrentPage,
				iPageSize, false);
	}

	/**
	 * <p>
	 * 将oResultSet转化为DataSet,iCurrentPage表示数据集的起始记录下标，iPageSize表示页大小<br/>
	 * bIsContinue表示当数据集游标超过当前分页的结束记录下标时，是否继续执行<br/>
	 * 返回所读取的数据集长度<br/>
	 * </p>
	 * @param oResultSet
	 * @param oDataSet
	 * @param iCurrentPage
	 * @param iPageSize
	 * @param bIsContinue
	 * @return
	 * @throws JException
	 */
	@SuppressWarnings("unchecked")
	public static long convertRsToDataSet(ResultSet oResultSet, DataSet oDataSet, 
			int iCurrentPage, int iPageSize, boolean bIsContinue) throws JException {
		try {
			//取得表的元数据
			ResultSetMetaData oResultSetMetaData = oResultSet.getMetaData();
			//取得表的总列数
			int fieldLen = oResultSetMetaData.getColumnCount();
			//存储表的字段名
			String[] fieldsArray = new String[fieldLen];
			for (int j = 1; j <= fieldLen; j++) {
				fieldsArray[(j - 1)] = oResultSetMetaData.getColumnName(j).toUpperCase();
			}
			//起始地址
			int iStart = 0;
			//结束地址
			int iEnd = 10000000;
			if ((iCurrentPage != 0) && (iPageSize != 0)) {
				iStart = (iCurrentPage - 1) * iPageSize + 1;
				iEnd = iCurrentPage * iPageSize;
			}
			long l = 0L;
			while (oResultSet.next()) {
				l += 1L;
				if (l >= iStart) {
					if (l <= iEnd) {
						Row oRow = new Row();
						for (int n = 0; n < fieldLen; n++) {
							 String	value = Row.getString(oResultSet,fieldsArray[n]);
							if (value != null) {
								oRow.put(fieldsArray[n], value);
							}
						}
						oDataSet.add(oRow);
					} else {
						if (!bIsContinue)
							break;
					}
				}
			}
			return l;
		} catch (SQLException e) {
			throw new JException(-1, "把数据集ResultSet转换成DataSet出现异常",e);
		}
	}

	public void destroy() {
		clear();
	}

	/**
	 * 排序，根据键名，键值是基本数据类型还是对象进行排序
	 * 并且在排序后根据排序类型排列，iSortType=2表示逆序排列
	 * @param sKeyName
	 * @param iCompareType
	 * @param iSortType
	 */
	@SuppressWarnings("unchecked")
	public void sorts(String sKeyName, int iCompareType, int iSortType) {
		if (size() > 0) {
			DataSetComparator oDataSetComparator = new DataSetComparator(sKeyName, iCompareType);
			Collections.sort(this, oDataSetComparator);
			if (iSortType == 2) {
				Collections.reverse(this);
			}
		}
	}

	public String toString() {
		StringBuffer oStringBuffer = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			Object obj= get(i);
			if (obj!=null && obj.getClass().getName().equalsIgnoreCase("java.lang.String")) {
				oStringBuffer.append(obj.toString() + "\n");
			} 
			else if (obj!=null && obj.getClass().getName().equalsIgnoreCase("com.ezcloud.framework.vo.Row")) {
				oStringBuffer.append(((Row) obj).toString() + "\n");
			}
		}
		return oStringBuffer.toString();
	}
}
