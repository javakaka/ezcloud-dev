package com.ezcloud.framework.service.system;

import java.net.URLEncoder;

import org.springframework.stereotype.Component;

import com.ezcloud.framework.page.jdbc.Page;
import com.ezcloud.framework.page.jdbc.Pageable;
import com.ezcloud.framework.service.JdbcService;
import com.ezcloud.framework.util.AesUtil;
import com.ezcloud.framework.vo.DataSet;
import com.ezcloud.framework.vo.Row;

/**
 * 区域
 * @author JianBoTong
 *
 */
@Component("frameworkSystemBureauService")
public class Bureau  extends JdbcService{

	/**
	 * 分页查询
	 * 
	 * @Title: queryPage
	 * @return Page
	 */
	public Page queryPage() {
		Page page = null;
		Pageable pageable = (Pageable) row.get("pageable");
		sql = "select * from sm_bureau where 1=1 ";
		String restrictions = addRestrictions(pageable);
		String orders = addOrders(pageable);
		sql += restrictions;
		sql += orders;
		String countSql = "select count(*) from sm_bureau where 1=1 ";
		countSql += restrictions;
		countSql += orders;
		long total = count(countSql);
		int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
		if (totalPages < pageable.getPageNumber()) {
			pageable.setPageNumber(totalPages);
		}
		int startPos = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		sql += " limit " + startPos + " , " + pageable.getPageSize();
		dataSet = queryDataSet(sql);
		page = new Page(dataSet, total, pageable);
		return page;
	}

	/**
	 * 保存
	 * 
	 * @Title: save
	 * @return void
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	public void save() throws Exception {
		Row row = new Row();
		String BUREAU_NAME=getRow().getString("BUREAU_NAME","");
		String UP_BUREAU_NO=getRow().getString("UP_BUREAU_NO","");
		String AREA_CODE=getRow().getString("AREA_CODE","");
		String LINKS=getRow().getString("LINKS","");
		String NOTES=getRow().getString("NOTES","");
		row.put("BUREAU_NAME", BUREAU_NAME);
		row.put("UP_BUREAU_NO", UP_BUREAU_NO);
		row.put("AREA_CODE", AREA_CODE);
		row.put("LINKS", LINKS);
		
		int BUREAU_NO = getTableSequence("sm_bureau", "bureau_no", 10000);
		row.put("BUREAU_NO", BUREAU_NO);
		
		//NOTES 字段为企业登陆 url,拼接token,token的值是bureau_no的AES加密字符串
		String token= AesUtil.encode(String.valueOf(BUREAU_NO));
		token =URLEncoder.encode(token);
		NOTES =NOTES +"?token="+token;
		row.put("NOTES", NOTES);
		insert("sm_bureau", row);
		this.getRow().put("BUREAU_NO", BUREAU_NO);
	}

	/**
	 * 根据id查找
	 * 
	 * @return Row
	 * @throws
	 */
	public Row find() {
		Row row = new Row();
		String id = getRow().getString("id");
		sql = "select * from sm_bureau where bureau_no='" + id + "'";
		row = queryRow(sql);
		return row;
	}

	/**
	 * 更新
	 * 
	 * @return void
	 */
	public void update() {
		String BUREAU_NO=getRow().getString("BUREAU_NO","");
		String BUREAU_NAME=getRow().getString("BUREAU_NAME","");
		String UP_BUREAU_NO=getRow().getString("UP_BUREAU_NO","");
		String AREA_CODE=getRow().getString("AREA_CODE","");
		String LINKS=getRow().getString("LINKS","");
		String NOTES=getRow().getString("NOTES","");
		Row row = new Row();
		row.put("BUREAU_NAME", BUREAU_NAME);
		row.put("UP_BUREAU_NO", UP_BUREAU_NO);
		row.put("AREA_CODE", AREA_CODE);
		row.put("LINKS", LINKS);
		row.put("NOTES", NOTES);
		update("sm_bureau", row, "BUREAU_NO='" + BUREAU_NO + "'");
	}

	/**
	 * 删除
	 * 
	 * @Title: delete
	 * @param @param ids
	 * @return void
	 */
	public void delete(Long... ids) {
		String id = "";
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				if (id.length() > 0) {
					id += ",";
				}
				id += "'" + String.valueOf(ids[i]) + "'";
			}
			sql = "delete from sm_bureau where bureau_no in(" + id + ")";
			update(sql);
			//delete staff_role
			sql ="delete from sm_staff_role where staff_no in (select staff_no from sm_staff where bureau_no in("+id+"))  ";
			update(sql);
			//delete staff
			sql ="delete from sm_staff where bureau_no in("+id+") ";
			update(sql);
			
			//delete mcn_org_modules
			sql ="delete from mcn_org_modules where org_id in ("+id+")";
			update(sql);
		}
	}
	
	public DataSet queryAllBureau()
	{
		DataSet ds=new DataSet();
		sql ="select * from sm_bureau ";
		ds =queryDataSet(sql);
		
		return ds;
	}
}
