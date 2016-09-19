/**
* 深圳华强实业股份有限公司版权所有
**/
package com.ezcloud.framework.weixin.service.busi;

import org.springframework.stereotype.Component;

import com.ezcloud.framework.page.jdbc.Page;
import com.ezcloud.framework.page.jdbc.Pageable;
import com.ezcloud.framework.service.JdbcService;
import com.ezcloud.framework.util.DateUtil;
import com.ezcloud.framework.vo.DataSet;
import com.ezcloud.framework.vo.Row;

/**
* 作者: kaka
* 日期: 18 Sep 2016
* 功能说明: 
**/
@Component("frameworkWeixinUserService")
public class WechatUserService  extends JdbcService{

	/**
	 * 分页查询
	 * 
	 * @Title: queryPage
	 * @return Page
	 */
	public Page queryPage(Pageable pageable) {
		Page page = null;
		String sql = "select * from wechat_user where 1=1 ";
		String restrictions = addRestrictions(pageable);
		String orders = addOrders(pageable);
		sql += restrictions;
		sql += orders;
		String countSql = "select count(*) from wechat_user where 1=1 ";
		countSql += restrictions;
		countSql += orders;
		long total = count(countSql);
		int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
		if (totalPages < pageable.getPageNumber()) {
			pageable.setPageNumber(totalPages);
		}
		int startPos = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		sql += " limit " + startPos + " , " + pageable.getPageSize();
		DataSet dataSet = queryDataSet(sql);
		page = new Page(dataSet, total, pageable);
		return page;
	}
	
	/**
	 * 保存
	 * 
	 * @Title: save
	 * @return void
	 */
	public void insert(Row row ) {
		int id =getTableSequence("wechat_user", "id", 1);
		row.put("id", id);
		row.put("create_time", DateUtil.getCurrentDateTime());
		insert("wechat_user", row);
	}
	
	/**
	 * 根据id查找
	 * 
	 * @return Row
	 * @throws
	 */
	public Row find(String id) {
		Row row = new Row();
		String sql = "select * from wechat_user where id='" + id + "'";
		row = queryRow(sql);
		return row;
	}
	
	public Row queryByOpenId(String open_id) {
		Row row = new Row();
		String sql = "select * from wechat_user where open_id='" + open_id + "' ";
		row = queryRow(sql);
		return row;
	}
	
	public DataSet queryByUnionId(String union_id) {
		DataSet ds = new DataSet();
		String sql = "select * from wechat_user where union_id='" + union_id + "' ";
		ds = queryDataSet(sql);
		return ds;
	}

	/**
	 * 更新
	 * 
	 * @return void
	 */
	public void update(Row row ) {
		String id = row.getString("id", "");
		update("wechat_user", row, "id='" + id + "'");
	}
	
	
	/**
	 * 删除
	 * 
	 * @Title: delete
	 * @param @param ids
	 * @return void
	 */
	public void delete(String... ids) {
		String id = "";
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				if (id.length() > 0) {
					id += ",";
				}
				id += "'" + String.valueOf(ids[i]) + "'";
			}
			String sql = "delete from wechat_user where id in(" + id + ")";
			update(sql);
		}
	}
}
