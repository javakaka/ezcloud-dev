package com.ezcloud.framework.weixin.job;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ezcloud.framework.service.JdbcService;
import com.ezcloud.framework.service.pay.WeixinTicketService;
import com.ezcloud.framework.util.StringUtils;
import com.ezcloud.framework.vo.Row;
import com.ezcloud.framework.weixin.common.WeixinUtil;
import com.ezcloud.framework.weixin.model.menu.AccessToken;

/**   
 * @author shike001 
 * E-mail:510836102@qq.com   
 * @version 创建时间：2015-4-28 上午10:49:35  
 * 类说明: 每隔5分钟检查微信接口调用票据(access_token)的状态，如果发现没有票据，生成一个，
 * 如果距离过期时间小于30分钟，则置当前有效的票据为无效，重新生成一个票据
 * 否则，不更新
 */
@Component("paimaiMonitorWeixinTicketStateJob")
@Lazy(false)
public class MonitorWeixinTicketStateJob extends JdbcService{

	private static Logger logger = Logger.getLogger(MonitorWeixinTicketStateJob.class);
	
	@Resource(name = "frameworkWeixinTicketService")
	private WeixinTicketService weixinTicketService;
	
	@Value(value="${framework.weixin.appId}")
	private String appId;
	
	@Value(value="${framework.weixin.appSecret}")
	private String appSecret;
	
	@Value(value="${framework.weixin.ticket.state.monitor.run}")
	private String run;
	
	@Scheduled(cron = "${framework.weixin.ticket.state.monitor.cron}")
	public void synWeixinTicket()
	{
		if (StringUtils.isEmptyOrNull(run) || !run.equals("1")) {
			return;
		}
		Row row=weixinTicketService.queryByPMAndState("1","1");
		AccessToken token =null;
		String access_token ="";
		String ticket ="";
		if(row == null)
		{
			logger.info("-----------拉取新票据----------------");
			Row insertRow =new Row();
			insertRow.put("pm_id", 1);
			insertRow.put("state", 1);
			token =WeixinUtil.getAccessToken(appId, appSecret);
			if(token != null)
			{
				access_token =token.getToken();
				ticket =WeixinUtil.getTicket(access_token);
			}
			if(! StringUtils.isEmptyOrNull(ticket))
			{
				insertRow.put("access_token", access_token);
				insertRow.put("ticket", ticket);
				insertRow.put("createmils", Long.toString(System.currentTimeMillis() / 1000));
				weixinTicketService.insert(insertRow);
			}
		}
		else
		{
			logger.info("-----------检查票据是否快过期---------------");
			String createmils =row.getString("createmils","0");
			long lcreatemils =Long.parseLong(createmils);
			long cur_createmils =System.currentTimeMillis() / 1000;
			long minus =cur_createmils -lcreatemils;
			if(minus <= 60*30)
			{
				row.put("state", "0");
				weixinTicketService.update(row);
				logger.info("-----------票据过期时间小于30分钟，已更新当前票据为无效----------------");
				Row insertRow =new Row();
				insertRow.put("pm_id", 1);
				insertRow.put("state", 1);
				token =WeixinUtil.getAccessToken(WeixinUtil.appId, WeixinUtil.appSecret);
				if(token != null)
				{
					access_token =token.getToken();
					ticket =WeixinUtil.getTicket(access_token);
				}
				if(! StringUtils.isEmptyOrNull(ticket))
				{
					insertRow.put("access_token", access_token);
					insertRow.put("ticket", ticket);
					insertRow.put("createmils", Long.toString(System.currentTimeMillis() / 1000));
					weixinTicketService.insert(insertRow);
					logger.info("-----------票据过期时间小于30分钟，重新生成票据---------------");
				}
			}
		}
		logger.info("-----------更新票据完毕！！----------------");
	}
	
}
