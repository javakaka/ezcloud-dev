package com.ezcloud.framework.service.pay;

import org.springframework.stereotype.Component;

import com.ezcloud.framework.page.jdbc.Page;
import com.ezcloud.framework.page.jdbc.Pageable;
import com.ezcloud.framework.service.JdbcService;
import com.ezcloud.framework.util.DateUtil;
import com.ezcloud.framework.vo.Row;

@Component("frameworkWeixinTicketService")
public class WeixinTicketService  extends JdbcService{
	/**
	 * 分页查询
	 * 
	 * @Title: queryPage
	 * @return Page
	 */
	public Page queryPage() {
		Page page = null;
		Pageable pageable = (Pageable) row.get("pageable");
		sql = "select * from wechat_ticket where 1=1 ";
		String restrictions = addRestrictions(pageable);
		String orders = addOrders(pageable);
		sql += restrictions;
		sql += orders;
		String countSql = "select count(*) from wechat_ticket where 1=1 ";
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
	 */
	public void insert(Row row ) {
		int id =getTableSequence("wechat_ticket", "id", 1);
		row.put("id", id);
		row.put("create_time", DateUtil.getCurrentDateTime());
		insert("wechat_ticket", row);
	}
	
	/**
	 * 根据id查找
	 * 
	 * @return Row
	 * @throws
	 */
	public Row find(String id) {
		Row row = new Row();
		sql = "select * from wechat_ticket where id='" + id + "'";
		row = queryRow(sql);
		return row;
	}
	
	public Row queryByPMAndState(String pm_id,String state) {
		Row row = new Row();
		sql = "select * from wechat_ticket where pm_id='" + pm_id + "' and state='"+state+"' ";
		row = queryRow(sql);
		return row;
	}

	/**
	 * 更新
	 * 
	 * @return void
	 */
	public void update(Row row ) {
		String id = row.getString("id", "");
		update("wechat_ticket", row, "id='" + id + "'");
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
			sql = "delete from wechat_ticket where id in(" + id + ")";
			update(sql);
		}
	}
}
