package com.ezcloud.framework.controller.weixin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezcloud.framework.controller.BaseController;
import com.ezcloud.framework.weixin.model.OutMessage;
import com.ezcloud.framework.weixin.service.BaseWeiXinProcessWervice;
import com.ezcloud.framework.weixin.tool.Tools;

/**
 * 微信入口
 * @author TongJianbo
 */
//@Controller("frameworkWechatApiController")
@RequestMapping("/wechat/bus")
public class WechatApiController extends BaseController {

	private static Logger logger = Logger.getLogger(WechatApiController.class);
//	@Resource(name = "frameworkWechatHandlerService")
	private BaseWeiXinProcessWervice handler;
	
	/** 微信公众帐号服务号的 token*/
	@Value(value="${framework.weixin.token}")
	private String token;
	
	public BaseWeiXinProcessWervice getHandler() {
		return handler;
	}
	
	public void setHandler(BaseWeiXinProcessWervice handler) {
		this.handler = handler;
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	@RequestMapping(value = "/api")
	public @ResponseBody 
	String wechatHandler(HttpServletRequest request,HttpServletResponse response) {
		String response_str ="";
		String httpMethod =request.getMethod();
		//验证
		if(httpMethod.equalsIgnoreCase("GET"))
		{
			String signature = request.getParameter("signature");// 微信加密签名 
	        String timestamp = request.getParameter("timestamp");// 时间戳 
	        String nonce = request.getParameter("nonce");// 随机数 
	        String echostr = request.getParameter("echostr");// 
	        logger.info("======来自微信的随机字符串======="+echostr);
	        logger.info("======token======="+token);
	        
	        if (Tools.specifySignaiture(token, signature, timestamp, nonce)) { 
	        	logger.info("======验证通过=======");
	           return echostr; 
	        }
	        else
	        {
	        	logger.info("======验证不通过=======");
	        	return "error";
	        }
		}
		Map <String ,String> map =null;
		try {
			map =Tools.parseXml(request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("请求数据解析出错=======" + e);
		}
		if(map == null){
			response_str ="error";
			return response_str;
		}
		String MsgType =null;
		MsgType =map.get("MsgType");
		Object inObj =Tools.parseMessageObject(map);
		logger.info("message type after deal....."+map.get("MsgType"));
		MsgType =map.get("MsgType");
		OutMessage outMsg =null;
		/** 处理文本消息*/
		if(MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_TEXT))
		{
			outMsg =handler.handleTextMsgRequest(inObj);
		}
		/** 处理图片消息*/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_IMAGE))
		{
			outMsg =handler.handleImageMsgRequest(inObj);
		}
		/** 处理普通语音消息*/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_VOICE))
		{
			outMsg =handler.handleVoiceMsgRequest(inObj);
		}
		/** 处理语音识别消息*/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_VOICE_RECOGNITION))
		{
			outMsg =handler.handleVoiceRecognitionMsgRequest(inObj);
		}
		/** 处理视频消息*/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_VIDEO))
		{
			outMsg =handler.handleVideoMsgRequest(inObj);
		}
		/** 处理位置消息*/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_LOCATION))
		{
			outMsg =handler.handleLocationMsgRequest(inObj);
		}
		/** 处理链接消息*/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_LINK))
		{
			outMsg =handler.handleLinkMsgRequest(inObj);
		}
		/** 处理事件消息   把事件分类处理*/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT))
		{
			outMsg =handler.handleEventMsgRequest(inObj);
		}
		/**已关注时扫描二维码**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_SCAN_SUBSCRIBE))
		{
			outMsg =handler.handleScanSubscribeEventMsgRequest(inObj);
		}
		/**未关注时扫描二维码**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_SCAN_UNSUBSCRIBE))
		{
			outMsg =handler.handleScanUnSubscribeEventMsgRequest(inObj);
		}
		/***上报地理位置**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_LOCATION))
		{
			outMsg =handler.handleLocationEventMsgRequest(inObj);
		}
		/***自定义菜单点击拉取消息事件**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_CLICK))
		{
			outMsg =handler.handleClickEventMsgRequest(inObj);
		}
		/***自定义菜单点击跳转链接事件**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_VIEW))
		{
			outMsg =handler.handleClickViewEventMsgRequest(inObj);
		}
		/***自定义菜单点击 scancode_push：扫码推事件的事件推送事件**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_SCANCODE_PUSH))
		{
			outMsg =handler.handleClickScancodePushEventMsgRequest(inObj);
		}
		/***自定义菜单点击 scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框的事件推送**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_SCANCODE_WAITMSG))
		{
			outMsg =handler.handleClickScancodeWaitmsgEventMsgRequest(inObj);
		}
		/***自定义菜单点击 pic_sysphoto：弹出系统拍照发图的事件推送**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_PIC_SYSPHOTO))
		{
			outMsg =handler.handleClickPicSysphotoEventMsgRequest(inObj);
		}
		/***自定义菜单点击 pic_photo_or_album：弹出拍照或者相册发图的事件推送**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_PIC_PHOTO_OR_ALBUM))
		{
			outMsg =handler.handleClickPicPhotoOrAlbumEventMsgRequest(inObj);
		}
		/***自定义菜单点击  pic_weixin：弹出微信相册发图器的事件推送**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_PIC_WEIXIN))
		{
			outMsg =handler.handleClickPicWeixinEventMsgRequest(inObj);
		}
		/***自定义菜单点击  location_select：弹出地理位置选择器的事件推送**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_LOCATION_SELECT))
		{
			outMsg =handler.handleClickLocationSelectEventMsgRequest(inObj);
		}
		/**关注**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_SUBSCRIBE))
		{
			outMsg =handler.handleSubscribeEventMsgRequest(inObj);
		}
		/**取消关注**/
		else if (MsgType.equals(BaseWeiXinProcessWervice.REQUEST_MSG_TYPE_EVENT_UNSUBSCRIBE))
		{
			outMsg =handler.handleUnSubscribeEventMsgRequest(inObj);
		}
	
		/**返回给微信的数据*/
		String xmlTemplate ="";
		logger.info("开始返回数据");
		if( outMsg == null ){
			logger.info("outMsg is null...... ");
			return "";
		}
		logger.info("outMsg===>>"+outMsg.getType());
		xmlTemplate =outMsg.getResponseXml();
		logger.info("数据》》"+xmlTemplate);
//		response.getWriter().write(xmlTemplate);
		response_str =xmlTemplate;
		return response_str;
	}
	
}
